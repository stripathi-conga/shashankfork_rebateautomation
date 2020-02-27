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
		response = new IncentiveCreationHelper().createIncentiveAndFetchSchedules(createIncentiveJson,RebatesConstants.incentiveTemplateIdBenefitProductDiscrete, incentiveStartDate, incentiveEndDate,
				RebatesConstants.paymentFrequencyMonthly);
		payoutScheduleValidator.validateSchedulesCount(response, 1);
		payoutScheduleValidator.validatePayoutSchedules(response, incentiveStartDate, incentiveEndDate, 1);

		// -------- Scenario 2 -Monthly frequency with Incentive date spanning 5 months---------
		String incentive2StartDate = sfdcHelper.getPastorFutureDate(sfdcHelper.firstDayOfPreviousTwoMonth(), "10");
		String incentive2EndDate = sfdcHelper.getPastorFutureDate(sfdcHelper.lastDayOfNextTwoMonth(), "-10");
		response = new IncentiveCreationHelper().createIncentiveAndFetchSchedules(createIncentiveJson, RebatesConstants.incentiveTemplateIdBenefitProductDiscrete,incentive2StartDate,incentive2EndDate, RebatesConstants.paymentFrequencyMonthly);
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
		response = new IncentiveCreationHelper().createIncentiveAndFetchSchedules(createIncentiveJson, RebatesConstants.incentiveTemplateIdBenefitProductDiscrete,incentiveStartDate,incentiveEndDate, RebatesConstants.paymentFrequencyMonthly);
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
		
		response = new IncentiveCreationHelper().createIncentiveAndFetchSchedules(createIncentiveJson,RebatesConstants.incentiveTemplateIdBenefitProductDiscrete, incentiveStartDate, incentiveEndDate,
				RebatesConstants.paymentFrequencyYearly);
		
		payoutScheduleValidator.validateSchedulesCount(response, 5);
		payoutScheduleValidator.validatePayoutSchedules(response, incentiveStartDate, incentiveEndDate,12);
		
		// -------- Scenario 2 - Incentive date spanning just 1 month---------
		String incentive2StartDate = sfdcHelper.getPastorFutureDate(sfdcHelper.firstDayOfYearForDate(sfdcHelper.getTodaysDate()), "14");
		String incentive2EndDate = sfdcHelper.getPastorFutureDate(sfdcHelper.lastDayOfMonthForDate(incentive2StartDate),"-1");
		response = new IncentiveCreationHelper().createIncentiveAndFetchSchedules(createIncentiveJson,RebatesConstants.incentiveTemplateIdBenefitProductDiscrete, incentive2StartDate, incentive2EndDate,
				RebatesConstants.paymentFrequencyYearly);
		payoutScheduleValidator.validateSchedulesCount(response, 1);
		payoutScheduleValidator.validatePayoutSchedules(response, incentive2StartDate, incentive2EndDate,12);
		
		// -------- Scenario 3 Incentive date spanning 5 years, start date 1st March and end date 15 Dec---------
		String incentive3StartDate = sfdcHelper.addMonthsToDate(sfdcHelper.firstDayOfYearForDate(sfdcHelper.getTodaysDate()), 2);
		//Set end date to 15th December 4 years from now		
		String incentive3EndDate = sfdcHelper.getPastorFutureDate(sfdcHelper.lastDayOfYearForDate(sfdcHelper.addYearsToDate(incentive3StartDate,4)),"-16");
				
		response = new IncentiveCreationHelper().createIncentiveAndFetchSchedules(createIncentiveJson,RebatesConstants.incentiveTemplateIdBenefitProductDiscrete, incentive3StartDate, incentive3EndDate,
						RebatesConstants.paymentFrequencyYearly);
		
		payoutScheduleValidator.validateSchedulesCount(response, 5);
		payoutScheduleValidator.validatePayoutSchedules(response, incentive3StartDate, incentive3EndDate,12);
		
		// -------- Scenario 4 - Incentive date spanning just 1 year with start and end dates mid of month---------
		String incentive4StartDate = sfdcHelper.getPastorFutureDate(sfdcHelper.firstDayOfYearForDate(sfdcHelper.getTodaysDate()), "14");
		String incentive4EndDate = sfdcHelper.getPastorFutureDate(sfdcHelper.lastDayOfYearForDate(incentive4StartDate),"-16");
		response = new IncentiveCreationHelper().createIncentiveAndFetchSchedules(createIncentiveJson,RebatesConstants.incentiveTemplateIdBenefitProductDiscrete, incentive4StartDate, incentive4EndDate,
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
		
		response = new IncentiveCreationHelper().createIncentiveAndFetchSchedules(createIncentiveJson,RebatesConstants.incentiveTemplateIdBenefitProductDiscrete, incentiveStartDate, incentiveEndDate,
				RebatesConstants.paymentFrequencyQuarterly);
		payoutScheduleValidator.validateSchedulesCount(response, 4);
		payoutScheduleValidator.validatePayoutSchedules(response, incentiveStartDate, incentiveEndDate,3);
		
		// -------- Scenario 2 - Incentive date spanning just 1 month---------
		String incentive2StartDate = sfdcHelper.getPastorFutureDate(sfdcHelper.firstDayOfYearForDate(sfdcHelper.getTodaysDate()), "14");
		String incentive2EndDate = sfdcHelper.getPastorFutureDate(sfdcHelper.lastDayOfMonthForDate(incentive2StartDate),"-1");
		response = new IncentiveCreationHelper().createIncentiveAndFetchSchedules(createIncentiveJson,RebatesConstants.incentiveTemplateIdBenefitProductDiscrete, incentive2StartDate, incentive2EndDate,
					RebatesConstants.paymentFrequencyQuarterly);
		payoutScheduleValidator.validateSchedulesCount(response, 1);
		payoutScheduleValidator.validatePayoutSchedules(response, incentive2StartDate, incentive2EndDate,3);
		
		// -------- Scenario 3 - Incentive date spanning 1 year with start 1-Jan and end 15-Dec---------
		String incentive3StartDate = sfdcHelper.firstDayOfYearForDate(sfdcHelper.getTodaysDate());
		String incentive3EndDate = sfdcHelper.getPastorFutureDate(sfdcHelper.lastDayOfYearForDate(incentive3StartDate),"-16");
		response = new IncentiveCreationHelper().createIncentiveAndFetchSchedules(createIncentiveJson,RebatesConstants.incentiveTemplateIdBenefitProductDiscrete, incentive3StartDate, incentive3EndDate,
					RebatesConstants.paymentFrequencyQuarterly);
		payoutScheduleValidator.validateSchedulesCount(response, 4);
		payoutScheduleValidator.validatePayoutSchedules(response, incentive3StartDate, incentive3EndDate,3);
		
		// -------- Scenario 4 - Incentive date spanning multiple months within a quarter ---------
		String incentive4StartDate = sfdcHelper.firstDayOfYearForDate(sfdcHelper.getTodaysDate());
		String incentive4EndDate = sfdcHelper.getPastorFutureDate(sfdcHelper.addMonthsToDate(incentive4StartDate, 2),"14");
		response = new IncentiveCreationHelper().createIncentiveAndFetchSchedules(createIncentiveJson,RebatesConstants.incentiveTemplateIdBenefitProductDiscrete, incentive4StartDate, incentive4EndDate,
					RebatesConstants.paymentFrequencyQuarterly);
		payoutScheduleValidator.validateSchedulesCount(response, 1);
		payoutScheduleValidator.validatePayoutSchedules(response, incentive4StartDate, incentive4EndDate,3);
	}
	
	@Test(description = "TC-465 Verify the schedule generated when the Payment frequency selected as Half Yearly", groups = {
			"Regression", "API", "Medium" })
	public void generatePayoutSchedulesForHalfYearlyFrequency() throws Exception {
		
		Map<String, String> createIncentiveJson = efficacies.readJsonElement("CIMTemplateData.json","createNewIncentiveAgreementAccountBenefitProductDiscrete");
		
		// -------- Scenario 1 - Incentive date spanning 1 year with start date 15-Jan and end 31-Dec---------
		String incentiveStartDate = sfdcHelper.getPastorFutureDate(sfdcHelper.firstDayOfYearForDate(sfdcHelper.getTodaysDate()), "14");	
		String incentiveEndDate = sfdcHelper.lastDayOfYearForDate(incentiveStartDate);
		
		response = new IncentiveCreationHelper().createIncentiveAndFetchSchedules(createIncentiveJson,RebatesConstants.incentiveTemplateIdBenefitProductDiscrete, incentiveStartDate, incentiveEndDate,
				RebatesConstants.paymentFrequencyHalfYearly);
		payoutScheduleValidator.validateSchedulesCount(response, 2);
		payoutScheduleValidator.validatePayoutSchedules(response, incentiveStartDate, incentiveEndDate,6);
		
		// -------- Scenario 2 - Incentive date spanning just 1 month---------
		String incentive2StartDate = sfdcHelper.getPastorFutureDate(sfdcHelper.firstDayOfYearForDate(sfdcHelper.getTodaysDate()), "14");
		String incentive2EndDate = sfdcHelper.getPastorFutureDate(sfdcHelper.lastDayOfMonthForDate(incentive2StartDate),"-1");
		response = new IncentiveCreationHelper().createIncentiveAndFetchSchedules(createIncentiveJson,RebatesConstants.incentiveTemplateIdBenefitProductDiscrete, incentive2StartDate, incentive2EndDate,
					RebatesConstants.paymentFrequencyHalfYearly);
		payoutScheduleValidator.validateSchedulesCount(response, 1);
		payoutScheduleValidator.validatePayoutSchedules(response, incentive2StartDate, incentive2EndDate,6);
		
		// -------- Scenario 3 - Incentive date spanning 1 year with start 1-Jan and end 15-Dec---------
		String incentive3StartDate = sfdcHelper.firstDayOfYearForDate(sfdcHelper.getTodaysDate());
		String incentive3EndDate = sfdcHelper.getPastorFutureDate(sfdcHelper.lastDayOfYearForDate(incentive3StartDate),"-16");
		response = new IncentiveCreationHelper().createIncentiveAndFetchSchedules(createIncentiveJson,RebatesConstants.incentiveTemplateIdBenefitProductDiscrete, incentive3StartDate, incentive3EndDate,
					RebatesConstants.paymentFrequencyHalfYearly);
		payoutScheduleValidator.validateSchedulesCount(response, 2);
		payoutScheduleValidator.validatePayoutSchedules(response, incentive3StartDate, incentive3EndDate,6);
		
		// -------- Scenario 4 - Incentive date spanning just one day ---------
		String incentive4StartDate = sfdcHelper.firstDayOfYearForDate(sfdcHelper.getTodaysDate());
		String incentive4EndDate = incentive4StartDate;
		response = new IncentiveCreationHelper().createIncentiveAndFetchSchedules(createIncentiveJson,RebatesConstants.incentiveTemplateIdBenefitProductDiscrete, incentive4StartDate, incentive4EndDate,
						RebatesConstants.paymentFrequencyHalfYearly);
		payoutScheduleValidator.validateSchedulesCount(response, 1);
		payoutScheduleValidator.validatePayoutSchedules(response, incentive4StartDate, incentive4EndDate,6);
	}
	
	@Test(description = "TC-474 Schedule status will change from 'Pending' to 'Open' when Start date > Current date", groups = {
			"Smoke", "API" })
	public void payoutSchedulesFromPendingToOpen() throws Exception {
		
		Map<String, String> createIncentiveJson = efficacies.readJsonElement("CIMTemplateData.json","createNewIncentiveAgreementAccountBenefitProductDiscrete");
		
		// -------- Monthly frequency with Incentive date spanning 8 months---------
		String incentiveStartDate =sfdcHelper.addMonthsToDate(sfdcHelper.getTodaysDate(), -1);
		String incentiveEndDate = sfdcHelper.addMonthsToDate(sfdcHelper.getTodaysDate(), 6);
		
		// -------- Create incentive and manually update schedule status to pending
		//--------- as schedules are always created in correct state ---------
		IncentiveCreationHelper incentiveCreationHelper = new IncentiveCreationHelper();
		incentiveCreationHelper.createIncentiveAndUpdateSchedules(createIncentiveJson, RebatesConstants.incentiveTemplateIdBenefitProductDiscrete,incentiveStartDate,incentiveEndDate, RebatesConstants.paymentFrequencyMonthly);
		
		// -------- call pending to open status modifier job---------
		incentiveCreationHelper.benefitProductQnB.pendingToOpenStatusModifier();
		
		// -------- get latest schedules and verify that they are in correct state---------
		response = incentiveCreationHelper.benefitProductQnB.getPayoutSchedules();		
		payoutScheduleValidator.validateSchedulesCount(response, 8);
		payoutScheduleValidator.validatePayoutSchedules(response, incentiveStartDate, incentiveEndDate, 1);
	}
	
	@Test(description = "TC-475 Schedule status will change from 'Open' to 'Ready' when current date > end date + program grace period", groups = {
			"Smoke", "API" })
	public void payoutSchedulesFromOpenToReady() throws Exception {
		
		Map<String, String> createIncentiveJson = efficacies.readJsonElement("CIMTemplateData.json","createNewIncentiveAgreementAccountBenefitProductDiscrete");
		int gracePeriod = 2;
		createIncentiveJson.put("GracePeriod__c", String.valueOf(gracePeriod));
		
		// -------- Monthly frequency with grace period 2 days ---------		
		String incentiveStartDate =sfdcHelper.addMonthsToDate(sfdcHelper.getTodaysDate(), -1);
		String incentiveEndDate = sfdcHelper.addMonthsToDate(sfdcHelper.getTodaysDate(), 6);
		
		// -------- Create incentive ---------
		IncentiveCreationHelper incentiveCreationHelper = new IncentiveCreationHelper();
		incentiveCreationHelper.createIncentiveAndFetchSchedules(createIncentiveJson, RebatesConstants.incentiveTemplateIdBenefitProductDiscrete,incentiveStartDate,incentiveEndDate, RebatesConstants.paymentFrequencyMonthly);
		
		// -------- call open to ready status modifier job ---------
		incentiveCreationHelper.benefitProductQnB.openToReadyStatusModifier();
		
		// -------- get latest schedules and verify that they are in correct state---------
		response = incentiveCreationHelper.benefitProductQnB.getPayoutSchedules();
		payoutScheduleValidator.validateReadyPayoutSchedules(response, incentiveStartDate, incentiveEndDate, 1,gracePeriod);
	}
}
