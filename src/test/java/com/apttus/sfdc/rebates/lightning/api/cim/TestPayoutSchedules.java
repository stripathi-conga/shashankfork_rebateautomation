package com.apttus.sfdc.rebates.lightning.api.cim;

import java.util.Map;
import java.util.Properties;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.apttus.helpers.Efficacies;
import com.apttus.sfdc.rebates.lightning.api.library.CIM;
import com.apttus.sfdc.rebates.lightning.api.validator.PayoutScheduleValidator;
import com.apttus.sfdc.rebates.lightning.api.validator.ResponseValidatorBase;
import com.apttus.sfdc.rebates.lightning.generic.utils.RebatesConstants;
import com.apttus.sfdc.rebates.lightning.generic.utils.SFDCHelper;
import com.apttus.sfdc.rudiments.utils.SFDCRestUtils;
import com.jayway.restassured.response.Response;

public class TestPayoutSchedules {

	private Properties configProperties;
	protected String baseURL;
	private Efficacies efficacies;
	private SFDCRestUtils sfdcRestUtils;
	protected SFDCHelper sfdcHelper;
	private String instanceURL;
	private ResponseValidatorBase responseValidator;
	private PayoutScheduleValidator payoutScheduleValidator;
	private Response response;

	@BeforeClass(alwaysRun = true)
	@Parameters({ "runParallel", "environment", "browser", "hubURL" })
	public void beforeClass(String runParallel, String environment, String browser, String hubURL) throws Exception {
		efficacies = new Efficacies();
		sfdcRestUtils = new SFDCRestUtils();
		configProperties = efficacies.loadPropertyFile(environment);
		baseURL = configProperties.getProperty("baseURL");
		SFDCHelper.setMasterProperty(configProperties);
		instanceURL = SFDCHelper.setAccessToken(sfdcRestUtils);
		sfdcHelper = new SFDCHelper();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() throws Exception {
		responseValidator = new ResponseValidatorBase();
		payoutScheduleValidator = new PayoutScheduleValidator();
	}

	@Test(description = "TC417-Verify the schedule generated when the Payment frequency selected as \"Monthly\" ", groups = {
			"Regression", "High" })
	public void generatePayoutSchedulesForMonthlyFrequency() throws Exception {

		// -------- Scenario 1 -Monthly frequency with Incentive date spanning one month---------
		response = CreateIncentiveAndFetchSchedules(sfdcHelper.firstDayOfCurrentMonth(),
				sfdcHelper.lastDayOfCurrentMonth(), RebatesConstants.paymentFrequencyMonthly);
		payoutScheduleValidator.validatePayoutSchedules(response, 1, 1, 0);

		// -------- Scenario 2 -Monthly frequency with Incentive date spanning 5 months---------
		String incentiveStartDate = sfdcHelper.getPastorFutureDate(sfdcHelper.firstDayOfPreviousTwoMonth(), "10");
		String incentiveEndDate = sfdcHelper.getPastorFutureDate(sfdcHelper.lastDayOfNextTwoMonth(), "-10");
		response = CreateIncentiveAndFetchSchedules(incentiveStartDate, incentiveEndDate, RebatesConstants.paymentFrequencyMonthly);
		payoutScheduleValidator.validatePayoutSchedules(response, 5, 3, 2);
	}	

	private Response CreateIncentiveAndFetchSchedules(String startDate, String endDate, String paymentFrequency)
			throws Exception {

		CIM cim = new CIM(instanceURL, sfdcRestUtils);

		// -------- Set-up JSON data for creating Incentive -----------------
		Map<String, String> createIncentiveJson = efficacies.readJsonElement("CIMTemplateData.json", "activeTemplateIdForRebateDiscrete");
		String incentiveTemplateIdBenefitProductDiscrete = cim.getTemplateIdForIncentives(createIncentiveJson);
		createIncentiveJson = efficacies.readJsonElement("CIMTemplateData.json", "createNewIncentiveAgreementAccountBenefitProductDiscrete");
		createIncentiveJson.put("ProgramTemplateId__c", incentiveTemplateIdBenefitProductDiscrete);
		createIncentiveJson.put("Apttus_Config2__EffectiveDate__c", startDate);
		createIncentiveJson.put("Apttus_Config2__ExpirationDate__c", endDate);
		createIncentiveJson.put("PayoutFrequency__c", paymentFrequency);

		// -------- Create and Validate Incentive -----------------
		cim.createNewIncentive(createIncentiveJson);
		response = cim.getIncentiveDetails();
		responseValidator.validateIncentiveDetails(createIncentiveJson, response, cim);

		// -------- Activate the incentive which will generate payout schedules -----------------
		cim.activateIncentive();
		response = cim.getIncentiveDetails();
		responseValidator.validateIncentiveStatus(RebatesConstants.statusActivated, response, cim.getIncentiveData().incentiveId);
		return cim.getPayoutSchedules();
	}

}
