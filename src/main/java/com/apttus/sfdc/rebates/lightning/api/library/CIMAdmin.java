package com.apttus.sfdc.rebates.lightning.api.library;

import java.util.Map;
import com.apttus.customException.ApplicationException;
import com.apttus.sfdc.rebates.lightning.api.pojo.CreateLinkTemplatesPojo;
import com.apttus.sfdc.rebates.lightning.api.pojo.CreateNewDataSourcePojo;
import com.apttus.sfdc.rebates.lightning.api.pojo.CreateQnBLayoutIdPojo;
import com.apttus.sfdc.rebates.lightning.api.pojo.CreateTemplatePojo;
import com.apttus.sfdc.rebates.lightning.api.pojo.GetCalculationFormulaIdPojo;
import com.apttus.sfdc.rebates.lightning.api.pojo.GetFieldExpressionIdPojo;
import com.apttus.sfdc.rebates.lightning.api.pojo.LinkCalculationFormulaPojo;
import com.apttus.sfdc.rebates.lightning.api.pojo.LinkDatasourceToCalculationIdPojo;
import com.apttus.sfdc.rebates.lightning.api.pojo.MapTemplateAndDataSourcePojo;
import com.apttus.sfdc.rebates.lightning.generic.utils.RebatesConstants;
import com.apttus.sfdc.rebates.lightning.generic.utils.URLGenerator;
import com.apttus.sfdc.rudiments.utils.SFDCRestUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.restassured.response.Response;

public class CIMAdmin {

	public SFDCRestUtils sfdcRestUtils;
	public JsonParser parser;
	public String templateId;
	private String requestString;
	private Response response;
	public URLGenerator urlGenerator;
	public CreateNewDataSourcePojo dataSourceData = new CreateNewDataSourcePojo();
	public CreateTemplatePojo templateData = new CreateTemplatePojo();
	public CreateLinkTemplatesPojo linkTemplatesData = new CreateLinkTemplatesPojo();
	public GetFieldExpressionIdPojo createNewFieldExpressionId = new GetFieldExpressionIdPojo();
	public GetCalculationFormulaIdPojo createCalcFormulaId = new GetCalculationFormulaIdPojo();
	public LinkCalculationFormulaPojo linkCalcFormula = new LinkCalculationFormulaPojo();
	public MapTemplateAndDataSourcePojo mapTemplateAndDataSourcePojo = new MapTemplateAndDataSourcePojo();
	public LinkDatasourceToCalculationIdPojo linkDatasource = new LinkDatasourceToCalculationIdPojo();
	public CreateQnBLayoutIdPojo createQnBLayoutIdPojo = new CreateQnBLayoutIdPojo();

	public CreateNewDataSourcePojo getDataSourceData() {
		return dataSourceData;
	}

	public CreateTemplatePojo getTemplateData() {
		return templateData;
	}

	public void setDataSourceData(CreateNewDataSourcePojo dataSourceData) {
		this.dataSourceData = dataSourceData;
	}

	public void setTemplateData(CreateTemplatePojo templateData) {
		this.templateData = templateData;
	}

	public CreateLinkTemplatesPojo getLinkTemplatesData() {
		return linkTemplatesData;
	}

	public void setLinkTemplatesData(CreateLinkTemplatesPojo linkTemplatesData) {
		this.linkTemplatesData = linkTemplatesData;
	}

	public CIMAdmin(String baseURL, SFDCRestUtils sfdcRestUtils) {
		urlGenerator = new URLGenerator(baseURL);
		this.sfdcRestUtils = sfdcRestUtils;
		parser = new JsonParser();
	}

	public void validateResponseCode(Response response, int expectedCode) throws Exception {
		if (response.getStatusCode() != expectedCode) {
			throw new Exception("CorelationID : [" + response.header("CorelationId") + "], The response code was : "
					+ response.getStatusCode() + " and the response body received is : "
					+ response.getBody().asString());
		}
	}

	public Response createDataSource(Map<String, String> testData) throws ApplicationException {
		String dataSourceId;
		try {
			requestString = dataSourceData.createDataSourceRequest(testData, this);
			response = sfdcRestUtils.postWithoutAppUrl(urlGenerator.dataSourceURL, requestString);
			validateResponseCode(response, RebatesConstants.responseCreated);
			dataSourceId = (parser.parse(response.getBody().asString())).getAsJsonObject().get("id").getAsString();
			dataSourceData.setDataSourceId(dataSourceId);
			return response;
		} catch (Exception e) {
			throw new ApplicationException("Create New DataSource API call failed with exception trace : " + e);
		}
	}

	public Response getDataSource() throws ApplicationException {
		try {
			response = sfdcRestUtils
					.getData(urlGenerator.getDataSourceURL.replace("{DataSourceId}", dataSourceData.getDataSourceId()));
			validateResponseCode(response, RebatesConstants.responseOk);
			return response;
		} catch (Exception e) {
			throw new ApplicationException("Get DataSource API call failed with exception trace : " + e);
		}
	}

	public void deleteDataSource() throws ApplicationException {
		try {
			response = sfdcRestUtils
					.deleteWithoutPayload(urlGenerator.dataSourceURL + dataSourceData.getDataSourceId());
			validateResponseCode(response, RebatesConstants.responseNocontent);
		} catch (Exception e) {
			throw new ApplicationException("Delete DataSource API call failed with exception trace : " + e);
		}
	}

	public Response createDataSourceWithoutAnyFields() throws ApplicationException {
		try {
			response = sfdcRestUtils.postWithoutAppUrl(urlGenerator.dataSourceURL, "{}");
			validateResponseCode(response, RebatesConstants.responseBadRequest);
			return response;
		} catch (Exception e) {
			throw new ApplicationException("Create Data Source Without Any Fields API call did not fail with exception trace : " + e);
		}
	}
	
	public String getFieldExpressionId(Map<String, String> testData) throws ApplicationException {
		String fieldExpressionId = null;
		try {
			requestString = createNewFieldExpressionId.getExpressionIdRequest(testData);
			response = sfdcRestUtils.postWithoutAppUrl(urlGenerator.fieldExpressionIdURL, requestString);
			validateResponseCode(response, RebatesConstants.responseCreated);
			fieldExpressionId = (parser.parse(response.getBody().asString())).getAsJsonObject().get("id").getAsString();
			
			return fieldExpressionId;
		} catch (Exception e) {
			throw new ApplicationException("Create FieldExpressionId API call failed with exception trace : " + e);
		}
	}

	public String getCalcFormulaId(Map<String, String> testData) throws ApplicationException {
		String calcFormulaId = null;
		try {
			requestString = createCalcFormulaId.getCalculationFormulaIdRequest(testData);
			response = sfdcRestUtils.postWithoutAppUrl(urlGenerator.calcFormulaIdURL, requestString);
			validateResponseCode(response, RebatesConstants.responseCreated);
			calcFormulaId = (parser.parse(response.getBody().asString())).getAsJsonObject().get("id").getAsString();
			return calcFormulaId;
		} catch (Exception e) {
			throw new ApplicationException("Create CalcFormulaId API call failed with exception trace : " + e);
		}
	}

	public void linkCalcFormulaToExpression(Map<String, String> testData, String calculationFormulaId,
			String expressionId) throws ApplicationException {
		try {
			requestString = linkCalcFormula.linkCalculationFormulaPojoRequest(testData, calculationFormulaId,
					expressionId);
			response = sfdcRestUtils.postWithoutAppUrl(urlGenerator.linkCalcFormulaIdURL, requestString);
			validateResponseCode(response, RebatesConstants.responseCreated);
		} catch (Exception e) {
			throw new ApplicationException(
					"Link CalcFormulaId To ExpressionId API call failed with exception trace : " + e);
		}
	}

	public void linkDatasourceToCalcFormula(String calculationFormulaId) throws ApplicationException {
		try {
			requestString = linkDatasource.linkDatasourceIdRequest(calculationFormulaId, this);
			response = sfdcRestUtils.postWithoutAppUrl(urlGenerator.linkDatasourceIdURL, requestString);
			validateResponseCode(response, RebatesConstants.responseCreated);
		} catch (Exception e) {
			throw new ApplicationException(
					"Link DatasourceId To CalcFormulaId API call failed with exception trace : " + e);
		}
	}

	public Response createTemplate(Map<String, String> testData, String qnbLayoutId) throws ApplicationException {
		try {
			requestString = templateData.createTemplateRequest(testData, this, qnbLayoutId);
			response = sfdcRestUtils.postWithoutAppUrl(urlGenerator.templateURL, requestString);
			validateResponseCode(response, RebatesConstants.responseCreated);
			templateId = (parser.parse(response.getBody().asString())).getAsJsonObject().get("id").getAsString();
			templateData.setTemplateId(templateId);
			return response;
		} catch (Exception e) {
			throw new ApplicationException("Create New Template API call failed with exception trace : " + e);
		}
	}

	public Response getTemplate() throws ApplicationException {
		try {
			response = sfdcRestUtils
					.getData(urlGenerator.getTemplateURL.replace("{TemplateId}", templateData.getTemplateId()));
			validateResponseCode(response, RebatesConstants.responseOk);
			return response;
		} catch (Exception e) {
			throw new ApplicationException("Get Template API call failed with exception trace : " + e);
		}
	}

	public void deleteTemplate() throws ApplicationException {
		try {
			response = sfdcRestUtils
					.deleteWithoutPayload(urlGenerator.templateURL + templateData.getTemplateId());
			validateResponseCode(response, RebatesConstants.responseNocontent);
		} catch (Exception e) {
			throw new ApplicationException("Delete Template API call failed with exception trace : " + e);
		}
	}

	public Response deleteActiveInactiveTemplate() throws ApplicationException {
		try {
			response = sfdcRestUtils
					.deleteWithoutPayload(urlGenerator.templateURL + templateData.getTemplateId());
			validateResponseCode(response, RebatesConstants.responseBadRequest);
			return response;
		} catch (Exception e) {
			throw new ApplicationException("Failed to verify the deletion of Active/Inactive template : " + e);
		}
	}

	public Response mapProgramTemplateDataSource(Map<String, String> testData) throws ApplicationException {
		try {
			requestString = mapTemplateAndDataSourcePojo.createTemplateDataSourceRequest(testData, this);
			response = sfdcRestUtils.postWithoutAppUrl(urlGenerator.mapTemplateToDatasourceURL, requestString);
			validateResponseCode(response, RebatesConstants.responseCreated);
			return response;
		} catch (Exception e) {
			throw new ApplicationException(
					"Template and DataSource mapping API call failed with exception trace : " + e);
		}
	}

	public Response activateTemplate(int resposecode) throws ApplicationException {
		try {
			requestString = "{\"Status__c\": \"" + RebatesConstants.activate + "\"}";
			response = sfdcRestUtils.patchWithoutAppUrl(
					urlGenerator.templateURL + templateData.getTemplateId(), requestString);
			
			validateResponseCode(response, resposecode);
			return response;
		} catch (Exception e) {
			throw new ApplicationException("Activate Template API call failed with exception trace : " + e);
		}
	}

	public Response createLinkTemplates(Map<String, String> testData) throws ApplicationException {
		response = getLinkTemplatesViaIncentiveTypeAndSubtype(testData);
		String linkTemplateId = getActiveTemplateIdFromGetLinkTemplates(response, testData);
		if (!(linkTemplateId == null)) {
			deactivateLinkTemplate();
		}
		try {
			requestString = linkTemplatesData.createLinkTemplateRequest(testData, this);
			response = sfdcRestUtils.postWithoutAppUrl(urlGenerator.linkTemplatesURL, requestString);
			validateResponseCode(response, RebatesConstants.responseCreated);
			linkTemplateId = (parser.parse(response.getBody().asString())).getAsJsonObject().get("id").getAsString();
			linkTemplatesData.setLinkTemplateId(linkTemplateId);
			response = getLinkTemplatesViaId();
			return response;
		} catch (Exception e) {
			throw new ApplicationException("Create New Link Templates API call failed with exception trace : " + e);
		}
	}

	public Response getLinkTemplatesViaId() throws ApplicationException {
		String linkTemplateName;
		try {
			response = sfdcRestUtils.getData(urlGenerator.getLinkTemplatesViaIDURL.replace("{LinkTemplateId}",
					linkTemplatesData.getLinkTemplateId()));
			validateResponseCode(response, RebatesConstants.responseOk);
			linkTemplateName = parser.parse(response.getBody().asString()).getAsJsonObject().getAsJsonArray("records")
					.get(0).getAsJsonObject().get("Name").getAsString();
			linkTemplatesData.setLinkTemplateName(linkTemplateName);
			return response;
		} catch (Exception e) {
			throw new ApplicationException("Get LinkTemplates using ID, API call failed with exception trace : " + e);
		}
	}

	public Response getLinkTemplatesViaIncentiveTypeAndSubtype(Map<String, String> testData) throws ApplicationException {
		try {
			response = sfdcRestUtils.getData(urlGenerator.getLinkTemplatesViaIncentiveTypeAndSubtypeURL
					.replace("{IncentiveType}", testData.get("IncentiveType__c")).replace("{IncentiveSubType}", testData.get("IncentiveSubType__c")));
			validateResponseCode(response, RebatesConstants.responseOk);
			return response;
		} catch (Exception e) {
			throw new ApplicationException(
					"Get LinkTemplates using IncentiveType and IncentiveSubType, API call failed with exception trace : "
							+ e);
		}
	}

	public String getActiveTemplateIdFromGetLinkTemplates(Response response, Map<String, String> testData)
			throws ApplicationException {
		String activeInactiveLinkTemplateId = null, status;
		JsonObject resp;
		JsonArray records;
		int count;
		try {
			resp = parser.parse(response.getBody().asString()).getAsJsonObject();
			count = resp.get("totalSize").getAsInt();
			records = resp.getAsJsonArray("records");
			for (int i = 0; i < count; i++) {
				status = records.get(i).getAsJsonObject().get("Status__c").getAsString();
				if (status.equals("Active")) {
					activeInactiveLinkTemplateId = records.get(i).getAsJsonObject().get("Id").getAsString();
					linkTemplatesData.setLinkTemplateId(activeInactiveLinkTemplateId);
					break;
				}
			}
			return activeInactiveLinkTemplateId;
		} catch (Exception e) {
			throw new ApplicationException(
					"No Active LinkTemplate Exists for IncentiveType : " + testData.get("IncentiveType__c")
							+ " and IncentiveSubType : " + testData.get("IncentiveSubType__c") + ". " + e);
		}
	}

	public Response activateLinkTemplate() throws ApplicationException {
		response = getLinkTemplatesViaId();
		String linkTemplateStatus;
		try {
			linkTemplateStatus = parser.parse(response.getBody().asString()).getAsJsonObject().getAsJsonArray("records")
					.get(0).getAsJsonObject().get("Status__c").getAsString();
			if (!linkTemplateStatus.equals("Active")) {
				requestString = "{\"Status__c\": \"" + RebatesConstants.activate + "\"}";
				response = sfdcRestUtils.patchWithoutAppUrl(
						urlGenerator.linkTemplatesURL + linkTemplatesData.getLinkTemplateId(), requestString);
				validateResponseCode(response, RebatesConstants.responseNocontent);
			}
			return response;
		} catch (Exception e) {
			throw new ApplicationException("Activate Link Template API call failed with exception trace : " + e);
		}
	}
	
	public Response deactivateLinkTemplate() throws ApplicationException {
		response = getLinkTemplatesViaId();
		String linkTemplateStatus;
		try {
			linkTemplateStatus = parser.parse(response.getBody().asString()).getAsJsonObject().getAsJsonArray("records")
					.get(0).getAsJsonObject().get("Status__c").getAsString();
			if (!linkTemplateStatus.equals("Inctive") || !linkTemplateStatus.equals("Draft")) {
				requestString = "{\"Status__c\": \"" + RebatesConstants.deactivate + "\"}";
				response = sfdcRestUtils.patchWithoutAppUrl(
						urlGenerator.linkTemplatesURL + linkTemplatesData.getLinkTemplateId(), requestString);
				validateResponseCode(response, RebatesConstants.responseNocontent);
			}
			return response;
		} catch (Exception e) {
			throw new ApplicationException("Deactivate Link Template API call failed with exception trace : " + e);
		}
	}
	
	public Response deactivateLinkTemplateViaId(String linkTemplateId) throws ApplicationException {
		try {
			requestString = "{\"Status__c\": \"" + RebatesConstants.deactivate + "\"}";
			response = sfdcRestUtils.patchWithoutAppUrl(urlGenerator.linkTemplatesURL + linkTemplateId, requestString);
			validateResponseCode(response, RebatesConstants.responseNocontent);
			return response;
		} catch (Exception e) {
			throw new ApplicationException("Deactivate Link Template API call failed with exception trace : " + e);
		}
	}

	public String getQnBLayoutId(Map<String, String> testData) throws ApplicationException {
		String qnblayoutId = null;
		int recordsize;
		JsonObject responsebody;
		try {
			response = sfdcRestUtils
					.getData(urlGenerator.getqnblayoutURL.replace("{QnBLayoutType}", testData.get("type__c"))
							.replace("{QnBLayoutTier}", testData.get("tier__c")));
			validateResponseCode(response, RebatesConstants.responseOk);
			responsebody = parser.parse(response.getBody().asString()).getAsJsonObject();
			recordsize = responsebody.get("totalSize").getAsInt();
			if (recordsize > 0) {
				qnblayoutId = responsebody.getAsJsonArray("records").get(0).getAsJsonObject().get("Id").getAsString();
			} else {
				requestString = createQnBLayoutIdPojo.createQnBLayoutIdRequest(testData);
				response = sfdcRestUtils.postWithoutAppUrl(urlGenerator.qnbLayoutIdURL, requestString);
				validateResponseCode(response, RebatesConstants.responseCreated);
				qnblayoutId = (parser.parse(response.getBody().asString())).getAsJsonObject().get("id").getAsString();
			}
			return qnblayoutId;
		} catch (Exception e) {
			throw new ApplicationException("Get QnB Layout Id API call failed with exception trace : " + e);
		}
	}

	public Response deactivateTemplate() throws ApplicationException {
		try {
			requestString = "{\"Status__c\": \"" + RebatesConstants.deactivate + "\"}";
			response = sfdcRestUtils.patchWithoutAppUrl(
					urlGenerator.templateURL + templateData.getTemplateId(), requestString);
			validateResponseCode(response, RebatesConstants.responseNocontent);
			return response;
		} catch (Exception e) {
			throw new ApplicationException("Deactivate Template API call failed with exception trace : " + e);
		}
	}

	public Response editTemplate(Map<String, String> testData, String qnbLayoutId, int responsecode)
			throws ApplicationException {
		try {
			String templateId = templateData.getTemplateId();
			requestString = templateData.createTemplateRequest(testData, this, qnbLayoutId);
			response = sfdcRestUtils.patchWithoutAppUrl(urlGenerator.templateURL + templateId, requestString);
			validateResponseCode(response, responsecode);
			String Name = templateData.getName();
			templateData.setTemplateId(templateId);
			templateData.setName(Name);
			return response;
		} catch (Exception e) {
			throw new ApplicationException("Update Template API call failed with exception trace : " + e);
		}
	}

	public Response deleteLinkTemplate(int responseCode) throws ApplicationException {
		try {
			response = sfdcRestUtils
					.deleteWithoutPayload(urlGenerator.linkTemplatesURL + linkTemplatesData.getLinkTemplateId());
			validateResponseCode(response, responseCode);
		} catch (Exception e) {
			throw new ApplicationException("Delete Template API call failed with exception trace : " + e);
		}
		return response;
	}

	public Response draftLinkTemplateNegative() throws ApplicationException {
		String currentLinkTemplateId = linkTemplatesData.getLinkTemplateId();
		try {
			requestString = "{\"Status__c\": \"" + RebatesConstants.draft + "\"}";
			response = sfdcRestUtils.patchWithoutAppUrl(
					urlGenerator.linkTemplatesURL + linkTemplatesData.getLinkTemplateId(), requestString);
			validateResponseCode(response, RebatesConstants.responseBadRequest);
			linkTemplatesData.setLinkTemplateId(currentLinkTemplateId);
			return response;
		} catch (Exception e) {
			throw new ApplicationException(
					"Link Template Status should not be changed to Draft from Active/Inactive, exception trace : " + e);
		}
	}

	public Response createLinkTemplatesNegative(Map<String, String> testData) throws ApplicationException {
		String currentLinkTemplateId = linkTemplatesData.getLinkTemplateId();
		try {
			requestString = linkTemplatesData.createLinkTemplateRequest(testData, this);
			response = sfdcRestUtils.postWithoutAppUrl(urlGenerator.linkTemplatesURL, requestString);
			validateResponseCode(response, RebatesConstants.responseBadRequest);
			linkTemplatesData.setLinkTemplateId(currentLinkTemplateId);
			return response;
		} catch (Exception e) {
			throw new ApplicationException("Create New Link Templates API call did not fail for Negative Scenario with exception trace : " + e);
		}
	}
}
