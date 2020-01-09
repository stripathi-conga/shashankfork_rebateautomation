package com.apttus.sfdc.rebates.lightning.api.library;

import java.util.HashMap;
import java.util.Map;
import com.apttus.customException.ApplicationException;
import com.apttus.sfdc.rebates.lightning.api.pojo.CreateNewProgramPojo;
import com.apttus.sfdc.rudiments.utils.SFDCRestUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jayway.restassured.response.Response;

public class CIM extends CIMAdmin {

	private Map<String, String> mapData = new HashMap<String, String>();
	public CreateNewProgramPojo programData = new CreateNewProgramPojo();
	private String requestString;
	private Response response;

	public CreateNewProgramPojo getProgramData() {
		return programData;
	}

	public void setProgramData(CreateNewProgramPojo programData) {
		this.programData = programData;
	}

	public CIM(String baseURL, SFDCRestUtils sfdcRestUtils) {
		super(baseURL, sfdcRestUtils);
	}

	public String getTemplateIdForProgram(Map<String, String> testData) throws ApplicationException {
		String templateId = null, status, activeInactiveLinkTemplateId;
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
				for (int i = 0; i < count; i++) {
					status = records.get(i).getAsJsonObject().get("Status__c").getAsString();
					if (status.equals("Active")) {
						templateId = records.get(i).getAsJsonObject().get("Template_Id__c").getAsString();
						break;
					}
					if (status.equals("Inactive")) {
						activeInactiveLinkTemplateId = records.get(i).getAsJsonObject().get("Id").getAsString();
						linkTemplatesData.setLinkTemplateId(activeInactiveLinkTemplateId);
						activateLinkAdminTemplate();
						templateId = records.get(i).getAsJsonObject().get("Template_Id__c").getAsString();
						break;
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

	public String creatNewProgram(Map<String, String> testData) throws ApplicationException {
		String programId;
		try {
			requestString = programData.createNewProgramRequest(testData, this);
			response = sfdcRestUtils.postWithoutAppUrl(urlGenerator.programURL, requestString);
			validateResponseCode(response, 201);
			programId = (parser.parse(response.getBody().asString())).getAsJsonObject().get("id").getAsString();
			programData.setProgramId(programId);
			return programId;
		} catch (Exception e) {
			throw new ApplicationException("Create New Rebates Program API call failed with exception trace : " + e);
		}
	}

	public Response getProgramDetails() throws ApplicationException {
		try {
			response = sfdcRestUtils
					.getData(urlGenerator.getProgramURL.replace("{ProgramId}", programData.getProgramId()));
			validateResponseCode(response, 200);
			return response;
		} catch (Exception e) {
			throw new ApplicationException("Get Program Details API call failed with exception trace : " + e);
		}
	}
}
