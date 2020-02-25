package com.apttus.sfdc.rebates.lightning.api.validator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.testng.asserts.SoftAssert;

import com.apttus.sfdc.rebates.lightning.generic.utils.RebatesConstants;
import com.apttus.sfdc.rebates.lightning.generic.utils.SFDCHelper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.restassured.response.Response;

public class PayoutScheduleValidator {

	protected JsonParser parser = new JsonParser();
	protected SFDCHelper sfdcHelper = new SFDCHelper();
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	
	public void validateSchedulesCount(Response response, int expectedScheduleCount) {
		SoftAssert softassert = new SoftAssert();
		String responseBody = response.getBody().asString();
		JsonArray schedulesResponse = parser.parse(responseBody).getAsJsonArray();

		int responseSize = schedulesResponse.size();
		softassert.assertEquals(responseSize, expectedScheduleCount,
				"Validate response size, Response should have " + expectedScheduleCount + " record");
		softassert.assertAll();		
	}

	public void validatePayoutSchedules(Response response, String incentiveStartDate, String incentiveEndDate,
			int intervalInMonths) throws Exception {
		SoftAssert softassert = new SoftAssert();
		String responseBody = response.getBody().asString();
		JsonArray schedulesResponse = parser.parse(responseBody).getAsJsonArray();
		int responseSize = schedulesResponse.size();

		String expectedScheduleStartDate = incentiveStartDate;
		String expectedScheduleEndDate = expectedScheduleStartDate;

		for (int i = 0; i < responseSize; i++) {
			// Read schedule start date and end date
			JsonObject schedule = schedulesResponse.get(i).getAsJsonObject();
			String scheduleStartDate = schedule.get("PeriodStartDate__c").getAsString();
			String scheduleEndDate = schedule.get("PeriodEndDate__c").getAsString();
			String scheduleStatus = schedule.get("Status__c").getAsString();
			
			expectedScheduleEndDate = sfdcHelper
					.firstDayOfMonthForDate(sfdcHelper.addMonthsToDate(expectedScheduleStartDate, intervalInMonths));
			if (i == (responseSize - 1)) {
				expectedScheduleEndDate = incentiveEndDate;
			} else {
				expectedScheduleEndDate = sfdcHelper.getPastorFutureDate(expectedScheduleEndDate, "-1");
			}
			softassert.assertEquals(expectedScheduleStartDate, scheduleStartDate, "Validate schedule start date");
			softassert.assertEquals(expectedScheduleEndDate, scheduleEndDate, "Validate schedule end date");
			if(dateFormat.parse(scheduleStartDate).compareTo(dateFormat.parse(sfdcHelper.getTodaysDate())) > 0) {
				softassert.assertEquals(RebatesConstants.scheduleStatusPending,scheduleStatus,"Validate schedule status, Status should be Pending");
			}
			else {
				softassert.assertEquals(RebatesConstants.scheduleStatusOpen,scheduleStatus,"Validate schedule status, Status should be Open");
			}
			expectedScheduleStartDate = sfdcHelper.getPastorFutureDate(expectedScheduleEndDate, "1");
			softassert.assertAll();
		}
	}

}
