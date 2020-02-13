package com.apttus.sfdc.rebates.lightning.api.library;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import com.apttus.customException.ApplicationException;
import com.apttus.sfdc.rebates.lightning.api.pojo.ActivateIncentivePojo;
import com.apttus.sfdc.rebates.lightning.api.pojo.AddParticipantPojo;
import com.apttus.sfdc.rebates.lightning.api.pojo.CreateNewAccountPojo;
import com.apttus.sfdc.rebates.lightning.api.pojo.CreateNewIncentivePojo;
import com.apttus.sfdc.rebates.lightning.generic.utils.RebatesConstants;
import com.apttus.sfdc.rebates.lightning.generic.utils.SFDCHelper;
import com.apttus.sfdc.rudiments.utils.SFDCRestUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jayway.restassured.response.Response;

public class CIM extends CIMAdmin {

	private String requestString;
	private Response response;
	public Map<String, String> mapRequestResponse;
	public Map<String, String> sectionIdMap;
	private Map<String, String> mapData = new HashMap<String, String>();
	public CreateNewIncentivePojo incentiveData = new CreateNewIncentivePojo();
	public CreateNewAccountPojo account = new CreateNewAccountPojo();
	public AddParticipantPojo participantsData = new AddParticipantPojo();
	public ActivateIncentivePojo activateIncentive = new ActivateIncentivePojo();


	public void setRequestValue(String key, String value) {
		mapRequestResponse.put(key, value);
	}

	public String getRequestValue(String key) {
		return mapRequestResponse.get(key);
	}

	public CreateNewIncentivePojo getIncentiveData() {
		return incentiveData;
	}

	public void setIncentiveData(CreateNewIncentivePojo incentiveData) {
		this.incentiveData = incentiveData;
	}

	public AddParticipantPojo getParticipantData() {
		return participantsData;
	}

	public void setParticipantData(AddParticipantPojo participantData) {
		this.participantsData = participantData;
	}

	public CIM(String baseURL, SFDCRestUtils sfdcRestUtils) {
		super(baseURL, sfdcRestUtils);
	}

	public String getCIMDateValue(String dateValue) throws ApplicationException {
		SFDCHelper sfdcHelper = new SFDCHelper();
		String returnDate = null;
		int year;
		try {
			boolean checkDate = sfdcHelper.checkValidDate(dateValue, null);
			if (checkDate) {
				returnDate = dateValue;
			} else {
				String date = dateValue.toLowerCase();
				if (date.contains("today")) {
					returnDate = sfdcHelper.getTodaysDate();
				} else if (date.contains("startofcurrentyear")) {
					year = Calendar.getInstance().get(Calendar.YEAR);
					returnDate = String.valueOf(year) + "-01-01";
				} else if (date.contains("endofcurrentyear")) {
					year = Calendar.getInstance().get(Calendar.YEAR);
					returnDate = String.valueOf(year) + "-12-31";
				} else if (date.contains("midofcurrentyear")) {
					year = Calendar.getInstance().get(Calendar.YEAR);
					returnDate = String.valueOf(year) + "-07-01";
				} else if (date.contains("startofcurrentmonth")) {
					returnDate = sfdcHelper.firstDayOfCurrentMonth();
				} else if (date.contains("endofcurrentmonth")) {
					returnDate = sfdcHelper.lastDayOfCurrentMonth();
				} else if (date.contains("startofpreviousmonth")) {
					returnDate = sfdcHelper.firstDayOfPreviousMonth();
				} else if (date.contains("startofprevioustwomonth")) {
					returnDate = sfdcHelper.firstDayOfPreviousTwoMonth();
				} else if (date.contains("endofnextmonth")) {
					returnDate = sfdcHelper.lastDayOfNextMonth();
				} else if (date.contains("incentivestartdate")) {
					returnDate = incentiveData.getApttus_Config2__EffectiveDate__c();
				} else if (date.contains("incentiveenddate")) {
					returnDate = incentiveData.getApttus_Config2__ExpirationDate__c();
				}
				if (date.contains("=")) {
					returnDate = sfdcHelper.getPastorFutureDate(returnDate, date.split("=")[1]);
				}
			}
			return returnDate;
		} catch (Exception e) {
			throw new ApplicationException("Getting run time error while using getCIMDateValue : " + e);
		}
	}
	
	public String getTemplateIdForIncentives(Map<String, String> testData) throws ApplicationException {
		String templateId = null, status, inactiveLinkTemplateId;
		JsonObject resp;
		JsonArray records;
		int count;
		mapData.put("IncentiveType__c", testData.get("IncentiveType__c"));
		mapData.put("IncentiveSubType__c", testData.get("IncentiveSubType__c"));
		try {
			response = getLinkTemplatesViaIncentiveTypeAndSubtype(mapData);
			resp = parser.parse(response.getBody().asString()).getAsJsonObject();
			count = resp.get("totalSize").getAsInt();
			records = resp.getAsJsonArray("records");
			if (count > 0) {
				// Get TemplateId from active linkTemplate
				for (int i = 0; i < count; i++) {
					status = records.get(i).getAsJsonObject().get("Status__c").getAsString();
					if (status.equals("Active")) {
						templateId = records.get(i).getAsJsonObject().get("TemplateId__c").getAsString();
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
							// Active the Inactive linkTemplate and get the TemplateId
							activateLinkTemplate();
							templateId = records.get(i).getAsJsonObject().get("TemplateId__c").getAsString();
							break;
						}
					}
				}
			}
			return templateId;
		} catch (Exception e) {
			throw new ApplicationException(
					"No Active/Inactive LinkTemplate Exists for IncentiveType : " + testData.get("IncentiveType__c")
							+ " and IncentiveSubType : " + testData.get("IncentiveSubType__c") + ". " + e);
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

	public Response addParticipants(Map<String, String> testData) throws ApplicationException {
		String participantId;
		try {
			requestString = participantsData.addParticipantsRequest(testData, this);
			response = sfdcRestUtils.postWithoutAppUrl(urlGenerator.addParticipantsURL, requestString);
			validateResponseCode(response, RebatesConstants.responseCreated);
			participantId = (parser.parse(response.getBody().asString())).getAsJsonObject().get("id").getAsString();
			participantsData.setParticipantsId(participantId);
			return response;

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

	public Response deleteParticipants() throws ApplicationException {
		try {
			response = sfdcRestUtils
					.deleteWithoutPayload(urlGenerator.addParticipantsURL + participantsData.getParticipantsId());
			validateResponseCode(response, RebatesConstants.responseNocontent);
		} catch (Exception e) {
			throw new ApplicationException("Delete Participant API call failed with exception trace : " + e);
		}
		return response;
	}

	public Response getParticipantIdViaIncentiveId() throws ApplicationException {
		try {
			response = sfdcRestUtils.getData(urlGenerator.getParticipantsViaIncentiveIdURL.replace("{IncentiveId}",
					participantsData.getIncentive__c()));
			validateResponseCode(response, RebatesConstants.responseOk);
		} catch (Exception e) {
			throw new ApplicationException("Get Participant Details API call failed with exception trace : " + e);
		}
		return response;
	}

	public String deactivateLinkTemplateForIncentives(Map<String, String> testData) throws ApplicationException {
		String templateId = null, status, activeLinkTemplateId;
		JsonObject resp;
		JsonArray records;
		int count;
		mapData.put("IncentiveType__c", testData.get("IncentiveType__c"));
		mapData.put("IncentiveSubType__c", testData.get("IncentiveSubType__c"));
		try {
			response = getLinkTemplatesViaIncentiveTypeAndSubtype(mapData);
			resp = parser.parse(response.getBody().asString()).getAsJsonObject();
			count = resp.get("totalSize").getAsInt();
			records = resp.getAsJsonArray("records");
			if (count > 0) {
				// Get TemplateId from active linkTemplate
				for (int i = 0; i < count; i++) {
					status = records.get(i).getAsJsonObject().get("Status__c").getAsString();
					if (status.equals("Active")) {
						activeLinkTemplateId = records.get(i).getAsJsonObject().get("Id").getAsString();
						deactivateLinkTemplateViaId(activeLinkTemplateId);
						break;
					}
				}
			}
			return templateId;
		} catch (Exception e) {
			throw new ApplicationException(
					"Not able to deactivate link template for : " + testData.get("IncentiveType__c")
							+ " and IncentiveSubType : " + testData.get("IncentiveSubType__c") + ". " + e);
		}
	}

	public String getProductId(String productName) throws ApplicationException {
		String productId;
		try {
			response = sfdcRestUtils.getData(urlGenerator.getProductIdURL.replace("{Product}", productName));
			validateResponseCode(response, RebatesConstants.responseOk);
			productId = (parser.parse(response.getBody().asString())).getAsJsonObject().get("records").getAsJsonArray()
					.get(0).getAsJsonObject().get("Id").getAsString();
			return productId;
		} catch (Exception e) {
			throw new ApplicationException("Get Product ID API call failed with exception trace : " + e);
		}
	}
	
	public Response activateIncentive() throws ApplicationException {
		try {
			requestString = activateIncentive.activateIncentiveRequest(getIncentiveData().incentiveId);
			response = sfdcRestUtils.postWithoutAppUrl(urlGenerator.activateIncentiveURL, requestString);
			validateResponseCode(response, RebatesConstants.responseOk);
			return response;
		} catch (Exception e) {
			throw new ApplicationException("Activate Incentive API call failed with exception trace : " + e);
		}
	}
}
