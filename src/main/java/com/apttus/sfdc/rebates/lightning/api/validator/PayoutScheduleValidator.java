package com.apttus.sfdc.rebates.lightning.api.validator;

import org.testng.asserts.SoftAssert;

import com.apttus.sfdc.rebates.lightning.generic.utils.RebatesConstants;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.restassured.response.Response;

public class PayoutScheduleValidator {

	protected JsonParser parser = new JsonParser();

	public void validatMonthlyFrequencyForOneMonthProgram(Response response) {
		SoftAssert softassert = new SoftAssert();
		String responseBody = response.getBody().asString();
		JsonArray schedulesResponse = parser.parse(responseBody).getAsJsonArray();

		int responseSize = schedulesResponse.size();
		softassert.assertEquals(responseSize, 1, "Validate response size, Response should have single record");

		JsonObject schedule = schedulesResponse.get(0).getAsJsonObject();
		String scheduleStatus = schedule.get("Status__c").getAsString();
		softassert.assertEquals(scheduleStatus, RebatesConstants.scheduleStatusOpen, "Validate schedule status");

		softassert.assertAll();
	}

	public void validatMonthlyFrequencyForFiveMonthProgram(Response response) {
		SoftAssert softassert = new SoftAssert();
		String responseBody = response.getBody().asString();
		JsonArray schedulesResponse = parser.parse(responseBody).getAsJsonArray();

		int responseSize = schedulesResponse.size();
		softassert.assertEquals(responseSize, 5, "Validate response size, Response should have five record");
		int openStatusCount = 0;
		int pendingStatusCount = 0;
		for (int i = 0; i < 5; i++) {
			JsonObject schedule = schedulesResponse.get(i).getAsJsonObject();
			String scheduleStatus = schedule.get("Status__c").getAsString();

			if (scheduleStatus.equalsIgnoreCase(RebatesConstants.scheduleStatusOpen)) {
				openStatusCount++;
			} else if (scheduleStatus.equalsIgnoreCase(RebatesConstants.scheduleStatusPending)) {
				pendingStatusCount++;
			}
		}
		softassert.assertEquals(openStatusCount, 3, "Validate 'Open' status schedules, should have three records");
		softassert.assertEquals(pendingStatusCount, 2, "Validate 'Pending' status schedules, should have two records");
		softassert.assertAll();
	}

}
