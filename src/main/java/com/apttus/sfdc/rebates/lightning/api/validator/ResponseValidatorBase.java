package com.apttus.sfdc.rebates.lightning.api.validator;

import java.util.Map;
import org.testng.asserts.SoftAssert;

import com.apttus.sfdc.rebates.lightning.api.library.CIM;
import com.apttus.sfdc.rebates.lightning.api.library.CIMAdmin;
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
		softassert.assertEquals(records.get("Program_Type__c").getAsString(),
				testData.get("Program_Type__c"), "Validate Program_Type in linkTemplate");
		softassert.assertEquals(records.get("Program_Sub_Type__c").getAsString(),
				testData.get("Program_Sub_Type__c"), "Validate Program_Sub_Type in linkTemplate");
		softassert.assertAll();
	}
	
	public void validateLinkTemplatesStatus(Response response, CIMAdmin cimAdmin, String status) {
		softassert = new SoftAssert();
		JsonObject resp = parser.parse(response.getBody().asString()).getAsJsonObject();
		softassert.assertEquals(resp.get("totalSize").getAsInt(), 1, "Validate response size");
		JsonObject records = resp.getAsJsonArray("records").get(0).getAsJsonObject();
		softassert.assertEquals(records.get("Status__c").getAsString(),
				status, "Validate status in linkTemplate");
		softassert.assertAll();
	}
	
	public void validateProgramDetails(Map<String, String> testData, Response response, CIM cim) {
		softassert = new SoftAssert();
		JsonObject resp = parser.parse(response.getBody().asString()).getAsJsonObject();
		softassert.assertEquals(resp.get("totalSize").getAsInt(), 1,
				"Validate response size, Response does not have single record");
		softassert.assertAll();
		JsonObject records = resp.getAsJsonArray("records").get(0).getAsJsonObject();
		softassert.assertEquals(records.get("Apttus_Config2__EffectiveDate__c").getAsString(),
				cim.programData.getApttus_Config2__EffectiveDate__c(), "Validate Program Start Date");
		softassert.assertEquals(records.get("Apttus_Config2__ExpirationDate__c").getAsString(),
				cim.programData.getApttus_Config2__ExpirationDate__c(), "Validate Program End Date");
		softassert.assertEquals(records.get("BenefitLevel__c").getAsString(), testData.get("BenefitLevel__c"),
				"Validate Program BenefitLevel");
		softassert.assertEquals(records.get("MeasurementLevel__c").getAsString(), testData.get("MeasurementLevel__c"),
				"Validate Program MeasurementLevel");
		softassert.assertEquals(records.get("Currency__c").getAsString(), testData.get("Currency__c"),
				"Validate Program Currency");
		softassert.assertEquals(records.get("Id").getAsString(), cim.programData.getProgramId(), "Validate Program Id");
		softassert.assertEquals(records.get("Name").getAsString(), cim.programData.getName(), "Validate Program Name");
		softassert.assertEquals(records.get("Apttus_Config2__UseType__c").getAsString(),
				testData.get("Apttus_Config2__UseType__c"), "Validate Program Type");
		softassert.assertEquals(records.get("Apttus_Config2__SubUseType__c").getAsString(),
				testData.get("Apttus_Config2__SubUseType__c"), "Validate Program SubType");
		softassert.assertEquals(records.get("Program_Template_Id__c").getAsString(),
				cim.programData.getProgram_Template_Id__c(), "Validate Program TemplateId");
		softassert.assertEquals(records.get("Id").getAsString(), cim.programData.getProgramId(), "Validate Program Id");
		softassert.assertAll();
	}
}
