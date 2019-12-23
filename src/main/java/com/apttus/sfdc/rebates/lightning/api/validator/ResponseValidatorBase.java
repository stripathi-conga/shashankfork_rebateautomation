package com.apttus.sfdc.rebates.lightning.api.validator;

import org.testng.asserts.SoftAssert;
import com.apttus.sfdc.rebates.lightning.api.library.CIMAdmin;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.restassured.response.Response;

public class ResponseValidatorBase {
	protected JsonParser parser = new JsonParser();
	protected SoftAssert softassert;

	public void validateCreateDataSource(Response response) {
		softassert = new SoftAssert();
		String success = (parser.parse(response.getBody().asString())).getAsJsonObject().get("success").getAsString();
		softassert.assertEquals(Boolean.parseBoolean(success), true, "Validate success flag");
		softassert.assertAll();
	}

	public void validateGetDataSource(Response response, CIMAdmin cimAdmin) {
		softassert = new SoftAssert();
		JsonObject resp = parser.parse(response.getBody().asString()).getAsJsonObject();
		softassert.assertEquals(resp.get("totalSize").getAsInt(), 1, "Validate response size");
		JsonObject records = resp.getAsJsonArray("records").get(0).getAsJsonObject();
		softassert.assertEquals(records.get("Id").getAsString(), cimAdmin.getDataSourceData().getDataSourceId(),
				"Validate datasource id");
		softassert.assertEquals(records.get("Name__c").getAsString(), cimAdmin.getDataSourceData().getName__c(),
				"Validate datasorce name");
		softassert.assertAll();
	}

	public void validateDeleteDataSource(Response response) {
		softassert = new SoftAssert();
		JsonObject resp = parser.parse(response.getBody().asString()).getAsJsonObject();
		softassert.assertEquals(resp.get("totalSize").getAsInt(), 0, "Validate response size");
		softassert.assertAll();
	}
}
