package com.apttus.sfdc.rebates.lightning.api.cim;

import java.util.Map;
import java.util.Properties;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.apttus.helpers.Efficacies;
import com.apttus.sfdc.rebates.lightning.api.validator.PayoutScheduleValidator;
import com.apttus.sfdc.rebates.lightning.generic.utils.IncentiveCreationHelper;
import com.apttus.sfdc.rebates.lightning.generic.utils.RebatesConstants;
import com.apttus.sfdc.rebates.lightning.generic.utils.SFDCHelper;
import com.jayway.restassured.response.Response;

public class TestPayoutSchedules {

	private Properties configProperties;
	protected String baseURL;
	private Efficacies efficacies;
	protected SFDCHelper sfdcHelper;
	private PayoutScheduleValidator payoutScheduleValidator;
	private Response response;

	@BeforeClass(alwaysRun = true)
	@Parameters({ "runParallel", "environment", "browser", "hubURL" })
	public void beforeClass(String runParallel, String environment, String browser, String hubURL) throws Exception {
		efficacies = new Efficacies();
		configProperties = efficacies.loadPropertyFile(environment);
		baseURL = configProperties.getProperty("baseURL");
		SFDCHelper.setMasterProperty(configProperties);
		sfdcHelper = new SFDCHelper();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() throws Exception {
		payoutScheduleValidator = new PayoutScheduleValidator();
	}

	@Test(description = "TC-417 Verify the schedule generated when the Payment frequency selected as Monthly ", groups = {
			"Smoke", "API", "Urgent" })
	public void generatePayoutSchedulesForMonthlyFrequency() throws Exception {

		// -------- Scenario 1 -Monthly frequency with Incentive date spanning one month---------
		Map<String, String> createIncentiveJson = efficacies.readJsonElement("CIMTemplateData.json","createNewIncentiveAgreementAccountBenefitProductDiscrete");

		response = IncentiveCreationHelper.createIncentiveAndFetchSchedules(createIncentiveJson,RebatesConstants.incentiveTemplateIdBenefitProductDiscrete, sfdcHelper.firstDayOfCurrentMonth(), sfdcHelper.lastDayOfCurrentMonth(),
				RebatesConstants.paymentFrequencyMonthly);
		payoutScheduleValidator.validatePayoutSchedules(response, 1, 1, 0);

		// -------- Scenario 2 -Monthly frequency with Incentive date spanning 5 months---------
		String incentiveStartDate = sfdcHelper.getPastorFutureDate(sfdcHelper.firstDayOfPreviousTwoMonth(), "10");
		String incentiveEndDate = sfdcHelper.getPastorFutureDate(sfdcHelper.lastDayOfNextTwoMonth(), "-10");
		response = IncentiveCreationHelper.createIncentiveAndFetchSchedules(createIncentiveJson, RebatesConstants.incentiveTemplateIdBenefitProductDiscrete,incentiveStartDate,incentiveEndDate, RebatesConstants.paymentFrequencyMonthly);
		payoutScheduleValidator.validatePayoutSchedules(response, 5, 3, 2);
	}
	
	@Test(description = "TC-473 Schedules staus will be Pending when Start and End dates in the future based on the current date ", groups = {
			"Regression", "API", "High" })
	public void generatePayoutSchedulesWithPendingStatus() throws Exception {

		// -------- Scenario 1 -Monthly frequency with start date 1 month prior to today and end date 4 months in future---------
		Map<String, String> createIncentiveJson = efficacies.readJsonElement("CIMTemplateData.json","createNewIncentiveAgreementAccountBenefitProductDiscrete");

		String incentiveStartDate = sfdcHelper.addMonthsToCurrentDate(-1);
		String incentiveEndDate = sfdcHelper.addMonthsToCurrentDate(6);
		response = IncentiveCreationHelper.createIncentiveAndFetchSchedules(createIncentiveJson, RebatesConstants.incentiveTemplateIdBenefitProductDiscrete,incentiveStartDate,incentiveEndDate, RebatesConstants.paymentFrequencyMonthly);
		payoutScheduleValidator.validatePayoutSchedules(response, 8, 2, 6);
	}
}
