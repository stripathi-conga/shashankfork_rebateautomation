package com.apttus.sfdc.rebates.lightning.api.cim;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.apttus.sfdc.rebates.lightning.main.UnifiedFramework;
import com.jayway.restassured.response.Response;

public class TestPayoutSchedules extends UnifiedFramework {

	private Properties configProperties;
	protected String baseURL;
	private Efficacies efficacies;
	protected SFDCHelper sfdcHelper;
	private PayoutScheduleValidator payoutScheduleValidator;
	private Response response;
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

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
			"Smoke", "API" })
	public void generatePayoutSchedulesForMonthlyFrequency() throws Exception {

		// -------- Scenario 1 -Monthly frequency with Incentive date spanning one month---------
		Map<String, String> createIncentiveJson = efficacies.readJsonElement("CIMTemplateData.json","createNewIncentiveAgreementAccountBenefitProductDiscrete");
		
		String incentiveStartDate = sfdcHelper.firstDayOfCurrentMonth();
		String incentiveEndDate = sfdcHelper.lastDayOfCurrentMonth();
		response = IncentiveCreationHelper.createIncentiveAndFetchSchedules(createIncentiveJson,RebatesConstants.incentiveTemplateIdBenefitProductDiscrete, incentiveStartDate, incentiveEndDate,
				RebatesConstants.paymentFrequencyMonthly);
		payoutScheduleValidator.validateSchedulesCount(response, 1);
		payoutScheduleValidator.validatePayoutSchedules(response, incentiveStartDate, incentiveEndDate, 1);

		// -------- Scenario 2 -Monthly frequency with Incentive date spanning 5 months---------
		String incentive2StartDate = sfdcHelper.getPastorFutureDate(sfdcHelper.firstDayOfPreviousTwoMonth(), "10");
		String incentive2EndDate = sfdcHelper.getPastorFutureDate(sfdcHelper.lastDayOfNextTwoMonth(), "-10");
		response = IncentiveCreationHelper.createIncentiveAndFetchSchedules(createIncentiveJson, RebatesConstants.incentiveTemplateIdBenefitProductDiscrete,incentive2StartDate,incentive2EndDate, RebatesConstants.paymentFrequencyMonthly);
		payoutScheduleValidator.validateSchedulesCount(response, 5);
		payoutScheduleValidator.validatePayoutSchedules(response, incentive2StartDate, incentive2EndDate, 1);
	}
	
	@Test(description = "TC-473 Schedules staus will be Pending when Start and End dates in the future based on the current date ", groups = {
			"Regression", "API", "High" })
	public void generatePayoutSchedulesWithPendingStatus() throws Exception {

		// -------- Scenario 1 -Monthly frequency with start date 1 month prior to today and end date 6 months in future---------
		Map<String, String> createIncentiveJson = efficacies.readJsonElement("CIMTemplateData.json","createNewIncentiveAgreementAccountBenefitProductDiscrete");

		String incentiveStartDate = sfdcHelper.addMonthsToDate(sfdcHelper.getTodaysDate(), -1);
		String incentiveEndDate = sfdcHelper.addMonthsToDate(sfdcHelper.getTodaysDate(),6);
		response = IncentiveCreationHelper.createIncentiveAndFetchSchedules(createIncentiveJson, RebatesConstants.incentiveTemplateIdBenefitProductDiscrete,incentiveStartDate,incentiveEndDate, RebatesConstants.paymentFrequencyMonthly);
		payoutScheduleValidator.validateSchedulesCount(response, 8);
		payoutScheduleValidator.validatePayoutSchedules(response, incentiveStartDate, incentiveEndDate, 1);
	}
	
	@Test(description = "TC-466 Verify the schedule generated when the Payment frequency selected as Yearly ", groups = {
			"Regression", "API", "Medium" })
	public void generatePayoutSchedulesForYearlyFrequency() throws Exception {
		
		Map<String, String> createIncentiveJson = efficacies.readJsonElement("CIMTemplateData.json","createNewIncentiveAgreementAccountBenefitProductDiscrete");
		
		// -------- Scenario 1 - Incentive date spanning 4 years with start date 16-Jan and end 31-Dec---------
		String incentiveStartDate = sfdcHelper.getPastorFutureDate(sfdcHelper.firstDayOfYearForDate(sfdcHelper.getTodaysDate()), "15");	
		String incentiveEndDate = sfdcHelper.lastDayOfYearForDate(sfdcHelper.addYearsToDate(incentiveStartDate,4));
		
		response = IncentiveCreationHelper.createIncentiveAndFetchSchedules(createIncentiveJson,RebatesConstants.incentiveTemplateIdBenefitProductDiscrete, incentiveStartDate, incentiveEndDate,
				RebatesConstants.paymentFrequencyYearly);
		
		payoutScheduleValidator.validateSchedulesCount(response, 5);
		payoutScheduleValidator.validatePayoutSchedules(response, incentiveStartDate, incentiveEndDate,12);
		
		// -------- Scenario 2 - Incentive date spanning just 1 month---------
		String incentive2StartDate = sfdcHelper.getPastorFutureDate(sfdcHelper.firstDayOfYearForDate(sfdcHelper.getTodaysDate()), "14");
		String incentive2EndDate = sfdcHelper.getPastorFutureDate(sfdcHelper.lastDayOfMonthForDate(incentive2StartDate),"-1");
		response = IncentiveCreationHelper.createIncentiveAndFetchSchedules(createIncentiveJson,RebatesConstants.incentiveTemplateIdBenefitProductDiscrete, incentive2StartDate, incentive2EndDate,
				RebatesConstants.paymentFrequencyYearly);
		payoutScheduleValidator.validateSchedulesCount(response, 1);
		payoutScheduleValidator.validatePayoutSchedules(response, incentive2StartDate, incentive2EndDate,12);
		
		// -------- Scenario 3 Incentive date spanning 5 years, start date 1st March and end date 15 Dec---------
		String incentive3StartDate = sfdcHelper.addMonthsToDate(sfdcHelper.firstDayOfYearForDate(sfdcHelper.getTodaysDate()), 2);
		//Set end date to 15th December 4 years from now		
		String incentive3EndDate = sfdcHelper.getPastorFutureDate(sfdcHelper.lastDayOfYearForDate(sfdcHelper.addYearsToDate(incentive3StartDate,4)),"-16");
				
		response = IncentiveCreationHelper.createIncentiveAndFetchSchedules(createIncentiveJson,RebatesConstants.incentiveTemplateIdBenefitProductDiscrete, incentive3StartDate, incentive3EndDate,
						RebatesConstants.paymentFrequencyYearly);
		
		payoutScheduleValidator.validateSchedulesCount(response, 5);
		payoutScheduleValidator.validatePayoutSchedules(response, incentive3StartDate, incentive3EndDate,12);
		
		// -------- Scenario 4 - Incentive date spanning just 1 year with start and end dates mid of month---------
		String incentive4StartDate = sfdcHelper.getPastorFutureDate(sfdcHelper.firstDayOfYearForDate(sfdcHelper.getTodaysDate()), "14");
		String incentive4EndDate = sfdcHelper.getPastorFutureDate(sfdcHelper.lastDayOfYearForDate(incentive4StartDate),"-16");
		response = IncentiveCreationHelper.createIncentiveAndFetchSchedules(createIncentiveJson,RebatesConstants.incentiveTemplateIdBenefitProductDiscrete, incentive4StartDate, incentive4EndDate,
					RebatesConstants.paymentFrequencyYearly);
		
		payoutScheduleValidator.validateSchedulesCount(response, 1);
		payoutScheduleValidator.validatePayoutSchedules(response, incentive4StartDate, incentive4EndDate,12);		
	}
	
	@Test(description = "TC-464 Verify the schedule generated when the Payment frequency selected as Quarterly", groups = {
			"Regression", "API", "Medium" })
	public void generatePayoutSchedulesForQuarterlyFrequency() throws Exception {
		
		Map<String, String> createIncentiveJson = efficacies.readJsonElement("CIMTemplateData.json","createNewIncentiveAgreementAccountBenefitProductDiscrete");
		
		// -------- Scenario 1 - Incentive date spanning 1 year with start date 15-Jan and end 31-Dec---------
		String incentiveStartDate = sfdcHelper.getPastorFutureDate(sfdcHelper.firstDayOfYearForDate(sfdcHelper.getTodaysDate()), "14");	
		String incentiveEndDate = sfdcHelper.lastDayOfYearForDate(incentiveStartDate);
		
		response = IncentiveCreationHelper.createIncentiveAndFetchSchedules(createIncentiveJson,RebatesConstants.incentiveTemplateIdBenefitProductDiscrete, incentiveStartDate, incentiveEndDate,
				RebatesConstants.paymentFrequencyQuarterly);
		payoutScheduleValidator.validateSchedulesCount(response, 4);
		payoutScheduleValidator.validatePayoutSchedules(response, incentiveStartDate, incentiveEndDate,3);
		
		// -------- Scenario 2 - Incentive date spanning just 1 month---------
		String incentive2StartDate = sfdcHelper.getPastorFutureDate(sfdcHelper.firstDayOfYearForDate(sfdcHelper.getTodaysDate()), "14");
		String incentive2EndDate = sfdcHelper.getPastorFutureDate(sfdcHelper.lastDayOfMonthForDate(incentive2StartDate),"-1");
		response = IncentiveCreationHelper.createIncentiveAndFetchSchedules(createIncentiveJson,RebatesConstants.incentiveTemplateIdBenefitProductDiscrete, incentive2StartDate, incentive2EndDate,
					RebatesConstants.paymentFrequencyQuarterly);
		payoutScheduleValidator.validateSchedulesCount(response, 1);
		payoutScheduleValidator.validatePayoutSchedules(response, incentive2StartDate, incentive2EndDate,3);
		
		// -------- Scenario 3 - Incentive date spanning 1 year with start 1-Jan and end 15-Dec---------
		String incentive3StartDate = sfdcHelper.firstDayOfYearForDate(sfdcHelper.getTodaysDate());
		String incentive3EndDate = sfdcHelper.getPastorFutureDate(sfdcHelper.lastDayOfYearForDate(incentive3StartDate),"-16");
		response = IncentiveCreationHelper.createIncentiveAndFetchSchedules(createIncentiveJson,RebatesConstants.incentiveTemplateIdBenefitProductDiscrete, incentive3StartDate, incentive3EndDate,
					RebatesConstants.paymentFrequencyQuarterly);
		payoutScheduleValidator.validateSchedulesCount(response, 4);
		payoutScheduleValidator.validatePayoutSchedules(response, incentive3StartDate, incentive3EndDate,3);
		
		// -------- Scenario 4 - Incentive date spanning multiple months within a quarter ---------
		String incentive4StartDate = sfdcHelper.firstDayOfYearForDate(sfdcHelper.getTodaysDate());
		String incentive4EndDate = sfdcHelper.getPastorFutureDate(sfdcHelper.addMonthsToDate(incentive4StartDate, 2),"14");
		response = IncentiveCreationHelper.createIncentiveAndFetchSchedules(createIncentiveJson,RebatesConstants.incentiveTemplateIdBenefitProductDiscrete, incentive4StartDate, incentive4EndDate,
					RebatesConstants.paymentFrequencyQuarterly);
		payoutScheduleValidator.validateSchedulesCount(response, 1);
		payoutScheduleValidator.validatePayoutSchedules(response, incentive4StartDate, incentive4EndDate,3);
	}
}
