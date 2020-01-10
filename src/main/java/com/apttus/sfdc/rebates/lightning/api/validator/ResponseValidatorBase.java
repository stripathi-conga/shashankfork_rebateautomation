package com.apttus.sfdc.rebates.lightning.api.validator;

import java.util.Map;

import org.testng.asserts.SoftAssert;

import com.apttus.sfdc.rebates.lightning.api.library.CIMAdmin;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.restassured.response.Response;

public class ResponseValidatorBase {
	protected JsonParser parser = new JsonParser();
	protected SoftAssert softassert;

	public void validateCreateSuccess(Response response) {
		softassert = new SoftAssert();
		boolean success = (parser.parse(response.getBody().asString())).getAsJsonObject().get("success").getAsBoolean();
		softassert.assertEquals(success, true, "Validate success flag");
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
				"Validate datasource name");
		softassert.assertAll();
	}

	public void validateDeleteSuccess(Response response) {
		softassert = new SoftAssert();
		JsonObject resp = parser.parse(response.getBody().asString()).getAsJsonObject();
		softassert.assertEquals(resp.get("totalSize").getAsInt(), 0, "Validate response size");
		softassert.assertAll();
	}

	public void validateDeleteFailure(Response response, String message,String errorcode) {

		softassert = new SoftAssert();
		JsonArray responsebody = parser.parse(response.getBody().asString()).getAsJsonArray();
		softassert.assertEquals(responsebody.get(0).getAsJsonObject().get("message").getAsString(), message,
				"Verify delete failure message");
		softassert.assertEquals(responsebody.get(0).getAsJsonObject().get("errorCode").getAsString(), errorcode,
				"Verify delete failure code");
		softassert.assertAll();
	}

	public void validateGetAdminTemplate(Response response, CIMAdmin cimAdmin) {
		softassert = new SoftAssert();
		JsonObject resp = parser.parse(response.getBody().asString()).getAsJsonObject();
		softassert.assertEquals(resp.get("totalSize").getAsInt(), 1, "Validate response size");
		JsonObject records = resp.getAsJsonArray("records").get(0).getAsJsonObject();
		softassert.assertEquals(records.get("Id").getAsString(), cimAdmin.getAdminTemplateData().getAdminTemplateId(),
				"Validate Admin Template id");
		softassert.assertEquals(records.get("Name").getAsString(), cimAdmin.getAdminTemplateData().getName(),
				"Validate Admin template Name");
		softassert.assertAll();
	}

	public void validateGetLinkTemplates(Map<String, String> testData, Response response, CIMAdmin cimAdmin) {
		softassert = new SoftAssert();
		JsonObject resp = parser.parse(response.getBody().asString()).getAsJsonObject();
		softassert.assertEquals(resp.get("totalSize").getAsInt(), 1, "Validate response size");
		JsonObject records = resp.getAsJsonArray("records").get(0).getAsJsonObject();
		softassert.assertEquals(records.get("Id").getAsString(), cimAdmin.linkTemplatesData.getLinkTemplateId(),
				"Validate linkTemplate id");
		softassert.assertEquals(records.get("Program_Type__c").getAsString(), testData.get("Program_Type__c"),
				"Validate Program_Type in linkTemplate");
		softassert.assertEquals(records.get("Program_Sub_Type__c").getAsString(), testData.get("Program_Sub_Type__c"),
				"Validate Program_Sub_Type in linkTemplate");
		softassert.assertAll();
	}

	public void validateLinkTemplatesStatus(Response response, CIMAdmin cimAdmin, String status) {
		softassert = new SoftAssert();
		JsonObject resp = parser.parse(response.getBody().asString()).getAsJsonObject();
		softassert.assertEquals(resp.get("totalSize").getAsInt(), 1, "Validate response size");
		JsonObject records = resp.getAsJsonArray("records").get(0).getAsJsonObject();
		softassert.assertEquals(records.get("Status__c").getAsString(), status, "Validate status in linkTemplate");
		softassert.assertAll();
	}

	public String  validateAdminTemplate(Response response, CIMAdmin cimAdmin) {
		softassert = new SoftAssert();
		JsonObject resp = parser.parse(response.getBody().asString()).getAsJsonObject();
		softassert.assertEquals(resp.get("totalSize").getAsInt(), 1, "Validate response size");
		JsonObject records = resp.getAsJsonArray("records").get(0).getAsJsonObject();
		softassert.assertEquals(records.get("Id").getAsString(), cimAdmin.getAdminTemplateData().getAdminTemplateId(),
				"Validate Admin Template id");
		softassert.assertEquals(records.get("Name").getAsString(), cimAdmin.getAdminTemplateData().getName(),
				"Validate Admin template Name");
		softassert.assertAll();
		return records.get("Id").getAsString();
	}
}
