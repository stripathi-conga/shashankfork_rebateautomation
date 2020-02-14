package com.apttus.sfdc.rebates.lightning.api.validator;

import org.testng.asserts.SoftAssert;

import com.apttus.sfdc.rebates.lightning.generic.utils.RebatesConstants;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.restassured.response.Response;

public class PayoutScheduleValidator {

	protected JsonParser parser = new JsonParser();

	public void validatePayoutSchedules(Response response,int expectedScheduleCount, int expectedOpenSchedules,int expectedPendingSchedules) {
		SoftAssert softassert = new SoftAssert();
		String responseBody = response.getBody().asString();
		JsonArray schedulesResponse = parser.parse(responseBody).getAsJsonArray();

		int responseSize = schedulesResponse.size();
		softassert.assertEquals(responseSize, expectedScheduleCount, "Validate response size, Response should have"+expectedScheduleCount+" record");
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
		softassert.assertEquals(openStatusScedules, expectedOpenSchedules, "Validate 'Open' status schedules, should have "+expectedOpenSchedules+"records");
		softassert.assertEquals(pendingStatusSchedules, expectedPendingSchedules, "Validate 'Pending' status schedules, should have "+expectedPendingSchedules+ "records");
		softassert.assertAll();
	}

}
