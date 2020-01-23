package com.apttus.sfdc.rebates.lightning.api.library;

import java.util.HashMap;
import java.util.Map;

import com.apttus.customException.ApplicationException;
import com.apttus.sfdc.rebates.lightning.api.pojo.AddParticipantPojo;
import com.apttus.sfdc.rebates.lightning.api.pojo.CreateNewAccountPojo;
import com.apttus.sfdc.rebates.lightning.api.pojo.CreateNewIncentivePojo;
import com.apttus.sfdc.rebates.lightning.generic.utils.RebatesConstants;
import com.apttus.sfdc.rudiments.utils.SFDCRestUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jayway.restassured.response.Response;

public class CIM extends CIMAdmin {

	private String requestString;
	private Response response;
	private Map<String, String> mapData = new HashMap<String, String>();
	public CreateNewIncentivePojo incentiveData = new CreateNewIncentivePojo();
	public CreateNewIncentivePojo getIncentiveData() {
		return incentiveData;
	}

	public void setIncentiveData(CreateNewIncentivePojo incentiveData) {
		this.incentiveData = incentiveData;
	}

	public CreateNewAccountPojo account = new CreateNewAccountPojo();
	public AddParticipantPojo participantsData = new AddParticipantPojo();

	public AddParticipantPojo getParticipantData() {
		return participantsData;
	}

	public void setParticipantData(AddParticipantPojo participantData) {
		this.participantsData = participantData;
	}

	

	public CIM(String baseURL, SFDCRestUtils sfdcRestUtils) {
		super(baseURL, sfdcRestUtils);
	}

	public String getTemplateIdForIncentives(Map<String, String> testData) throws ApplicationException {
		String templateId = null, status, inactiveLinkTemplateId;
		JsonObject resp;
		JsonArray records;
		int count;
		mapData.put("Program_Type__c", testData.get("Apttus_Config2__UseType__c"));
		mapData.put("Program_Sub_Type__c", testData.get("Apttus_Config2__SubUseType__c"));
		try {
			response = getLinkTemplatesViaProgramType(mapData);
			resp = parser.parse(response.getBody().asString()).getAsJsonObject();
			count = resp.get("totalSize").getAsInt();
			records = resp.getAsJsonArray("records");
			if (count > 0) {
				// Get TemplateId from active linkTemplate
				for (int i = 0; i < count; i++) {
					status = records.get(i).getAsJsonObject().get("Status__c").getAsString();
					if (status.equals("Active")) {
						templateId = records.get(i).getAsJsonObject().get("Template_Id__c").getAsString();
						break;
					}
				}
				if (templateId == null) {
					// Get Inactive linkTemplate
					for (int i = 0; i < count; i++) {
						status = records.get(i).getAsJsonObject().get("Status__c").getAsString();
						if (status.equals("Inactive")) {
							inactiveLinkTemplateId = records.get(i).getAsJsonObject().get("Id").getAsString();
							linkTemplatesData.setLinkTemplateId(inactiveLinkTemplateId);
							// Active  the Inactive linkTemplate and get the TemplateId
							activateLinkTemplate();
							templateId = records.get(i).getAsJsonObject().get("Template_Id__c").getAsString();
							break;
						}
					}
				}
			}
			return templateId;
		} catch (Exception e) {
			throw new ApplicationException(
					"No Active/Inactive LinkTemplate Exists for ProgramType : " + testData.get("Program_Type__c")
							+ " and ProgramSubType : " + testData.get("Program_Sub_Type__c") + ". " + e);
		}
	}

	public String createNewIncentive(Map<String, String> testData) throws ApplicationException {
		String incentiveId;
		try {
			requestString = incentiveData.createNewIncentiveRequest(testData, this);
			response = sfdcRestUtils.postWithoutAppUrl(urlGenerator.incentiveURL, requestString);
			validateResponseCode(response, RebatesConstants.responseCreated);
			incentiveId = (parser.parse(response.getBody().asString())).getAsJsonObject().get("id").getAsString();
			incentiveData.setIncentiveId(incentiveId);
			return incentiveId;
		} catch (Exception e) {
			throw new ApplicationException("Create New Incentive API call failed with exception trace : " + e);
		}
	}

	public Response getIncentiveDetails() throws ApplicationException {
		try {
			response = sfdcRestUtils
					.getData(urlGenerator.getIncentiveURL.replace("{incentiveId}", incentiveData.getIncentiveId()));
			validateResponseCode(response, RebatesConstants.responseOk);
			return response;
		} catch (Exception e) {
			throw new ApplicationException("Get Incentive Details API call failed with exception trace : " + e);
		}
	}

	public String getAccountId(String accountName) throws ApplicationException {
		String accountId;
		int count;
		try {
			response = sfdcRestUtils.getData(urlGenerator.getAccountURL.replace("{AccountName}", accountName));
			validateResponseCode(response, RebatesConstants.responseOk);
			JsonObject resp = parser.parse(response.getBody().asString()).getAsJsonObject();
			count = resp.get("totalSize").getAsInt();
			if (count > 0) {
				accountId = (parser.parse(response.getBody().asString())).getAsJsonObject().get("records")
						.getAsJsonArray().get(0).getAsJsonObject().get("Id").getAsString();
			} else {
				accountId = createNewAccount(accountName);
			}
			return accountId;
		} catch (Exception e) {
			throw new ApplicationException("Get Account Details API call failed with exception trace : " + e);
		}
	}

	public String createNewAccount(String accountName) throws ApplicationException {
		String accountId;
		try {
			requestString = account.createNewAccountRequest(accountName);
			response = sfdcRestUtils.postWithoutAppUrl(urlGenerator.createAccountURL, requestString);
			validateResponseCode(response, RebatesConstants.responseCreated);
			accountId = (parser.parse(response.getBody().asString())).getAsJsonObject().get("id").getAsString();
			return accountId;
		} catch (Exception e) {
			throw new ApplicationException("Create New Account API call failed with exception trace : " + e);
		}
	}

	public void updateIncentive(Map<String, String> testData) throws ApplicationException {
		String updateincentive = incentiveData.getIncentiveId();
		
		try {
			requestString = incentiveData.createNewIncentiveRequest(testData, this);
			response = sfdcRestUtils.patchWithoutAppUrl(urlGenerator.incentiveURL + updateincentive, requestString);
			validateResponseCode(response, RebatesConstants.responseNocontent);
			incentiveData.setIncentiveId(updateincentive);
		} catch (Exception e) {
			throw new ApplicationException("Update Incentive details API call failed with exception trace : " + e);
		}
	}

	public void addParticipants(Map<String, String> testData) throws ApplicationException {
		String participantid;
		try {
			requestString = participantsData.addParticipantsRequest(testData, this);
			response = sfdcRestUtils.postWithoutAppUrl(urlGenerator.addParticipantsURL, requestString);
			validateResponseCode(response, RebatesConstants.responseCreated);
			participantid = (parser.parse(response.getBody().asString())).getAsJsonObject().get("id").getAsString();
			participantsData.setParticipantsId(participantid);
		} catch (Exception e) {
			throw new ApplicationException("Add Participant API call failed with exception trace : " + e);
		}
	}

	public Response getParticipantsDetails() throws ApplicationException {
		try {
			response = sfdcRestUtils.getData(
					urlGenerator.getParticipantsURL.replace("{participantId}", participantsData.getParticipantsId()));
			validateResponseCode(response, RebatesConstants.responseOk);
			return response;
		} catch (Exception e) {
			throw new ApplicationException("Get  Participant Details API call failed with exception trace : " + e);
		}
	}

	public void deleteParticipants() throws ApplicationException {
		try {
			response = sfdcRestUtils
					.deleteWithoutPayload(urlGenerator.addParticipantsURL + participantsData.getParticipantsId());
			validateResponseCode(response, RebatesConstants.responseNocontent);
		} catch (Exception e) {
			throw new ApplicationException("Delete Participant API call failed with exception trace : " + e);
		}
	}
}
