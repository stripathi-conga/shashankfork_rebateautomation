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

	public void validatePayoutSchedules(Response response, int expectedScheduleCount, int expectedOpenSchedules,
			int expectedPendingSchedules) {
		SoftAssert softassert = new SoftAssert();
		String responseBody = response.getBody().asString();
		JsonArray schedulesResponse = parser.parse(responseBody).getAsJsonArray();

		int responseSize = schedulesResponse.size();
		softassert.assertEquals(responseSize, expectedScheduleCount,
				"Validate response size, Response should have" + expectedScheduleCount + " record");
		softassert.assertAll();
		int openStatusScedules = 0;
		int pendingStatusSchedules = 0;
		for (int i = 0; i < responseSize; i++) {
			JsonObject schedule = schedulesResponse.get(i).getAsJsonObject();
			String scheduleStatus = schedule.get("Status__c").getAsString();

			if (scheduleStatus.equalsIgnoreCase(RebatesConstants.scheduleStatusOpen)) {
				openStatusScedules++;
			} else if (scheduleStatus.equalsIgnoreCase(RebatesConstants.scheduleStatusPending)) {
				pendingStatusSchedules++;
			}
		}
		softassert.assertEquals(openStatusScedules, expectedOpenSchedules,
				"Validate 'Open' status schedule count, should have " + expectedOpenSchedules + "records");
		softassert.assertEquals(pendingStatusSchedules, expectedPendingSchedules,
				"Validate 'Pending' status schedule count, should have " + expectedPendingSchedules + "records");
		softassert.assertAll();
	}

	public void validatePayoutScheduleDates(Response response, String incentiveStartDate, String incentiveEndDate,
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

			expectedScheduleEndDate = sfdcHelper
					.firstDayOfMonthForDate(sfdcHelper.addMonthsToDate(expectedScheduleStartDate, intervalInMonths));
			if (i == (responseSize - 1)) {
				expectedScheduleEndDate = incentiveEndDate;
			} else {
				expectedScheduleEndDate = sfdcHelper.getPastorFutureDate(expectedScheduleEndDate, "-1");
			}
			softassert.assertEquals(expectedScheduleStartDate, scheduleStartDate, "Validate schedule start date");
			softassert.assertEquals(expectedScheduleEndDate, scheduleEndDate, "Validate schedule end date");
			expectedScheduleStartDate = sfdcHelper.getPastorFutureDate(expectedScheduleEndDate, "1");
			softassert.assertAll();
		}
	}

}
