package com.apttus.sfdc.rebates.lightning.api.validator;


import java.util.Map;
import org.testng.asserts.SoftAssert;

import com.apttus.customException.ApplicationException;
import com.apttus.sfdc.rebates.lightning.api.library.CIM;
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
		softassert.assertAll();
		JsonObject records = resp.getAsJsonArray("records").get(0).getAsJsonObject();
		softassert.assertEquals(records.get("Id").getAsString(), cimAdmin.getDataSourceData().getDataSourceId(),
				"Validate datasource id");
		softassert.assertEquals(records.get("Name__c").getAsString(), cimAdmin.getDataSourceData().getName__c(),
				"Validate datasource name");
		softassert.assertAll();
	}

	//TDOD -  Shashank will check this methos and optimize it
	public void validateDeleteSuccess(Response response) {
		softassert = new SoftAssert();
		JsonObject resp = parser.parse(response.getBody().asString()).getAsJsonObject();
		softassert.assertEquals(resp.get("totalSize").getAsInt(), 0, "Validate response size");
		softassert.assertAll();
	}

	public void validateGetTemplate(Response response, CIMAdmin cimAdmin) {
		softassert = new SoftAssert();
		JsonObject resp = parser.parse(response.getBody().asString()).getAsJsonObject();
		softassert.assertEquals(resp.get("totalSize").getAsInt(), 1, "Validate response size");
		softassert.assertAll();
		JsonObject records = resp.getAsJsonArray("records").get(0).getAsJsonObject();
		softassert.assertEquals(records.get("Id").getAsString(), cimAdmin.getTemplateData().getTemplateId(),
				"Validate Template id");
		softassert.assertEquals(records.get("Name").getAsString(), cimAdmin.getTemplateData().getName(),
				"Validate Template Name");
		softassert.assertAll();
	}

	public void validateGetLinkTemplates(Map<String, String> testData, Response response, CIMAdmin cimAdmin) {
		softassert = new SoftAssert();
		JsonObject resp = parser.parse(response.getBody().asString()).getAsJsonObject();
		softassert.assertEquals(resp.get("totalSize").getAsInt(), 1, "Validate response size");
		softassert.assertAll();
		JsonObject records = resp.getAsJsonArray("records").get(0).getAsJsonObject();
		softassert.assertEquals(records.get("Id").getAsString(), cimAdmin.linkTemplatesData.getLinkTemplateId(),
				"Validate linkTemplate id");
		softassert.assertEquals(records.get("ProgramType__c").getAsString(), testData.get("ProgramType__c"),
				"Validate ProgramType in linkTemplate");
		softassert.assertEquals(records.get("ProgramSubType__c").getAsString(), testData.get("ProgramSubType__c"),
				"Validate ProgramSub_Type in linkTemplate");
		softassert.assertAll();
	}

	public void validateLinkTemplatesStatus(Response response, CIMAdmin cimAdmin, String status) {
		softassert = new SoftAssert();
		JsonObject resp = parser.parse(response.getBody().asString()).getAsJsonObject();
		softassert.assertEquals(resp.get("totalSize").getAsInt(), 1, "Validate response size");
		softassert.assertAll();
		JsonObject records = resp.getAsJsonArray("records").get(0).getAsJsonObject();
		softassert.assertEquals(records.get("Status__c").getAsString(), status, "Validate status in linkTemplate");
		softassert.assertAll();
	}

	public void validateIncentiveDetails(Map<String, String> testData, Response response, CIM cim) {
		softassert = new SoftAssert();
		JsonObject resp = parser.parse(response.getBody().asString()).getAsJsonObject();
		softassert.assertEquals(resp.get("totalSize").getAsInt(), 1,
				"Validate response size, Response does not have single record");
		softassert.assertAll();
		JsonObject records = resp.getAsJsonArray("records").get(0).getAsJsonObject();
		System.out.println("Records--"+records);
		softassert.assertEquals(records.get("Apttus_Config2__EffectiveDate__c").getAsString(),
				cim.incentiveData.getApttus_Config2__EffectiveDate__c(), "Validate Incentive Start Date");

		softassert.assertEquals(records.get("Apttus_Config2__ExpirationDate__c").getAsString(),
				cim.incentiveData.getApttus_Config2__ExpirationDate__c(), "Validate Incentive End Date");
		softassert.assertEquals(records.get("BenefitLevel__c").getAsString(), testData.get("BenefitLevel__c"),
				"Validate Incentive BenefitLevel");
		softassert.assertEquals(records.get("MeasurementLevel__c").getAsString(), testData.get("MeasurementLevel__c"),
				"Validate Incentive MeasurementLevel");
		softassert.assertEquals(records.get("Currency__c").getAsString(), testData.get("Currency__c"),
				"Validate Incentive Currency");
		softassert.assertEquals(records.get("Id").getAsString(), cim.incentiveData.getIncentiveId(),
				"Validate Incentive Id");
		softassert.assertEquals(records.get("Name").getAsString(), cim.incentiveData.getName(),
				"Validate Incentive Name");
		softassert.assertEquals(records.get("Apttus_Config2__UseType__c").getAsString(),
				testData.get("Apttus_Config2__UseType__c"), "Validate Program Type");
		softassert.assertEquals(records.get("Apttus_Config2__SubUseType__c").getAsString(),
				testData.get("Apttus_Config2__SubUseType__c"), "Validate Program SubType");
		softassert.assertEquals(records.get("ProgramTemplateId__c").getAsString(),
				cim.incentiveData.getProgramTemplateId__c(), "Validate Program TemplateId");
		softassert.assertAll();

	}

	public void validateTemplateStatus(Response response, CIMAdmin cimAdmin, String Status) {
		softassert = new SoftAssert();
		JsonObject resp = parser.parse(response.getBody().asString()).getAsJsonObject();
		softassert.assertEquals(resp.get("totalSize").getAsInt(), 1, "Validate response size");
		softassert.assertAll();
		JsonObject records = resp.getAsJsonArray("records").get(0).getAsJsonObject();
		softassert.assertEquals(records.get("Id").getAsString(), cimAdmin.getTemplateData().getTemplateId(),
				"Validate Template id");
		softassert.assertEquals(records.get("Status__c").getAsString(), Status,
				"Validate Template Status-Draft/Active/Inactive");
		softassert.assertAll();
	}

	public void validateUpdatedTemplate(Response response, CIMAdmin cimAdmin, Map<String, String> testData,
			String qnbLayoutId) {
		softassert = new SoftAssert();
		JsonObject resp = parser.parse(response.getBody().asString()).getAsJsonObject();
		softassert.assertEquals(resp.get("totalSize").getAsInt(), 1,
				"Validate response size, Response does not have single record");
		softassert.assertAll();
		JsonObject records = resp.getAsJsonArray("records").get(0).getAsJsonObject();
		softassert.assertEquals(records.get("Id").getAsString(), cimAdmin.getTemplateData().getTemplateId(),
				"Validate Template id");
		softassert.assertEquals(records.get("Description__c").getAsString(), testData.get("Description__c"),
				"Validate updated Template Decription");
		softassert.assertEquals(records.get("Name").getAsString(), cimAdmin.getTemplateData().getName(),
				"Validate template Name");
		softassert.assertEquals(records.get("QnBLayoutId__c").getAsString(), qnbLayoutId,
				"Validate Updated Template QnB Layout Id");
		softassert.assertAll();
	}

	public void validateFailureResponse(Response response, String errorcode, String message) {
		softassert = new SoftAssert();
		JsonArray responsebody = parser.parse(response.getBody().asString()).getAsJsonArray();
		softassert.assertEquals(responsebody.get(0).getAsJsonObject().get("message").getAsString(), message,
				"Verify failure message");
		softassert.assertEquals(responsebody.get(0).getAsJsonObject().get("errorCode").getAsString(), errorcode,
				"Verify failure Errorcode");
		softassert.assertAll();
	}

	public void validateParticipantsDetails(Map<String, String> testData, Response response, CIM cim)
			throws ApplicationException {
		softassert = new SoftAssert();
		JsonObject resp = parser.parse(response.getBody().asString()).getAsJsonObject();
		softassert.assertEquals(resp.get("totalSize").getAsInt(), 1,
				"Validate response size, Response does not have single record");
		softassert.assertAll();
		JsonObject records = resp.getAsJsonArray("records").get(0).getAsJsonObject();
		softassert.assertEquals(records.get("EffectiveDate__c").getAsString(),
				cim.participantsData.getEffectiveDate__c(), "Validate Participant Effective Date");
		softassert.assertEquals(records.get("ExpirationDate__c").getAsString(),
				cim.participantsData.getExpirationDate__c(), "Validate Participant Expired Date");
		softassert.assertEquals(records.get("Id").getAsString(), cim.participantsData.getParticipantsId(),
				"Validate Incentive Participant Id");
		softassert.assertEquals(records.get("Incentive__c").getAsString(), cim.incentiveData.getIncentiveId(),
				"Validate Incentive Id");
		softassert.assertAll();
	}

	public void validateAvailableParticipant(Map<String, String> testData, Response response)
			throws ApplicationException {
		softassert = new SoftAssert();
		JsonObject resp = parser.parse(response.getBody().asString()).getAsJsonObject();
		softassert.assertEquals(resp.get("totalSize").getAsInt(), 2,
				"Validate response size, Response does not have single record");
		softassert.assertAll();
		JsonObject recordsParticipant1 = resp.getAsJsonArray("records").get(0).getAsJsonObject();
		softassert.assertEquals(recordsParticipant1.get("Account__c").getAsString(),
				testData.get("Automation_Participant_Account_1"), "Validate Incentive Participant Id");
		JsonObject recordsParticipant2 = resp.getAsJsonArray("records").get(1).getAsJsonObject();
		softassert.assertEquals(recordsParticipant2.get("Account__c").getAsString(),
				testData.get("Automation_Participant_Account_2"), "Validate Incentive Participant Id");
		softassert.assertAll();

	}
}

