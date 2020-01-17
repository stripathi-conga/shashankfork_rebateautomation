package com.apttus.sfdc.rebates.lightning.api.admin;

import java.util.Map;
import java.util.Properties;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.apttus.helpers.Efficacies;
import com.apttus.sfdc.rebates.lightning.api.library.CIMAdmin;
import com.apttus.sfdc.rebates.lightning.api.validator.ResponseValidatorBase;
import com.apttus.sfdc.rebates.lightning.generic.utils.RebatesConstants;
import com.apttus.sfdc.rebates.lightning.generic.utils.SFDCHelper;
import com.apttus.sfdc.rudiments.utils.SFDCRestUtils;
import com.jayway.restassured.response.Response;

public class TestLinkTemplates {

	private Properties configProperties;
	private Efficacies efficacies;
	private SFDCRestUtils sfdcRestUtils;
	private String instanceURL;
	private ResponseValidatorBase responseValidator;
	private CIMAdmin cimAdmin;
	private Map<String, String> jsonData;
	private Response response;

	@BeforeClass(alwaysRun = true)
	@Parameters({ "runParallel", "environment", "browser", "hubURL" })
	public void beforeClass(String runParallel, String environment, String browser, String hubURL) throws Exception {
		efficacies = new Efficacies();
		sfdcRestUtils = new SFDCRestUtils();
		configProperties = efficacies.loadPropertyFile(environment);
		SFDCHelper.setMasterProperty(configProperties);
		instanceURL = SFDCHelper.setAccessToken(sfdcRestUtils);
		cimAdmin = new CIMAdmin(instanceURL, sfdcRestUtils);
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() throws Exception {
		responseValidator = new ResponseValidatorBase();
	}

	@Test(description = "TC-279 Verify for the Link Template Create and List page", groups = { "Regression", "High",
			"API" })
	public void createLinkTemplate() throws Exception {

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createFieldExpressionId");
		String fieldExpressionId = cimAdmin.getFieldExpressionId(jsonData);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createCalcFormulaIdBenefit");
		String calcFormulaIdBenefit = cimAdmin.getCalcFormulaId(jsonData);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createCalcFormulaIdQualification");
		String calcFormulaIdQualification = cimAdmin.getCalcFormulaId(jsonData);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "linkCalcFormulaToExpressionId");
		cimAdmin.linkCalcFormulaToExpression(jsonData, calcFormulaIdBenefit, fieldExpressionId);
		cimAdmin.linkCalcFormulaToExpression(jsonData, calcFormulaIdQualification, fieldExpressionId);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewDataSourceAPI");
		cimAdmin.createDataSource(jsonData);
		cimAdmin.linkDatasourceToCalcFormula(calcFormulaIdBenefit);
		cimAdmin.linkDatasourceToCalcFormula(calcFormulaIdQualification);

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createQnBLayoutAPI");
		String qnbLayoutId = cimAdmin.getQnBLayoutId(jsonData);

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewAdminTemplateAPI");
		response = cimAdmin.createAdminTemplate(jsonData, qnbLayoutId);
		responseValidator.validateCreateSuccess(response);
		response = cimAdmin.getAdminTemplate();
		responseValidator.validateGetAdminTemplate(response, cimAdmin);
		jsonData.put("Formula_Id__c", calcFormulaIdBenefit);
		jsonData.put("Data_Source_Id__c", cimAdmin.getDataSourceData().getDataSourceId());
		cimAdmin.mapProgramTemplateDataSource(jsonData);
		cimAdmin.activateAdminTemplate(RebatesConstants.responseNocontent);

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewLinkTemplateSubTypeRebateAPI");
		cimAdmin.createLinkTemplates(jsonData);
		response = cimAdmin.getLinkTemplatesViaId();
		responseValidator.validateGetLinkTemplates(jsonData, response, cimAdmin);
	}

	@Test(description = "TC-445 Verify Activation of Link Template", groups = { "Regression", "High", "API" })
	public void activateLinkTemplate() throws Exception {

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createFieldExpressionId");
		String fieldExpressionId = cimAdmin.getFieldExpressionId(jsonData);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createCalcFormulaIdBenefit");
		String calcFormulaIdBenefit = cimAdmin.getCalcFormulaId(jsonData);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createCalcFormulaIdQualification");
		String calcFormulaIdQualification = cimAdmin.getCalcFormulaId(jsonData);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "linkCalcFormulaToExpressionId");
		cimAdmin.linkCalcFormulaToExpression(jsonData, calcFormulaIdBenefit, fieldExpressionId);
		cimAdmin.linkCalcFormulaToExpression(jsonData, calcFormulaIdQualification, fieldExpressionId);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewDataSourceAPI");
		cimAdmin.createDataSource(jsonData);
		cimAdmin.linkDatasourceToCalcFormula(calcFormulaIdBenefit);
		cimAdmin.linkDatasourceToCalcFormula(calcFormulaIdQualification);

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createQnBLayoutAPI");
		String qnbLayoutId = cimAdmin.getQnBLayoutId(jsonData);

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewAdminTemplateAPI");
		response = cimAdmin.createAdminTemplate(jsonData, qnbLayoutId);
		responseValidator.validateCreateSuccess(response);
		response = cimAdmin.getAdminTemplate();
		responseValidator.validateGetAdminTemplate(response, cimAdmin);
		jsonData.put("Formula_Id__c", calcFormulaIdBenefit);
		jsonData.put("Data_Source_Id__c", cimAdmin.getDataSourceData().getDataSourceId());
		cimAdmin.mapProgramTemplateDataSource(jsonData);
		cimAdmin.activateAdminTemplate(RebatesConstants.responseNocontent);

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewLinkTemplateSubTypeLoyaltyAPI");
		response = cimAdmin.createLinkTemplates(jsonData);
		cimAdmin.activateLinkTemplate();
		response = cimAdmin.getLinkTemplatesViaId();
		responseValidator.validateLinkTemplatesStatus(response, cimAdmin, RebatesConstants.activate);
	}

	@Test(description = "TC-408 Verify delete Link Template when status is Draft via spillover menu", groups = { "Regression",
			"High", "API" })
	public void deleteLinkTemplateDraft() throws Exception {

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createFieldExpressionId");
		String fieldExpressionId = cimAdmin.getFieldExpressionId(jsonData);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createCalcFormulaIdBenefit");
		String calcFormulaIdBenefit = cimAdmin.getCalcFormulaId(jsonData);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createCalcFormulaIdQualification");
		String calcFormulaIdQualification = cimAdmin.getCalcFormulaId(jsonData);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "linkCalcFormulaToExpressionId");
		cimAdmin.linkCalcFormulaToExpression(jsonData, calcFormulaIdBenefit, fieldExpressionId);
		cimAdmin.linkCalcFormulaToExpression(jsonData, calcFormulaIdQualification, fieldExpressionId);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewDataSourceAPI");
		cimAdmin.createDataSource(jsonData);
		cimAdmin.linkDatasourceToCalcFormula(calcFormulaIdBenefit);
		cimAdmin.linkDatasourceToCalcFormula(calcFormulaIdQualification);

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createQnBLayoutAPI");
		String qnbLayoutId = cimAdmin.getQnBLayoutId(jsonData);

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewAdminTemplateAPI");
		response = cimAdmin.createAdminTemplate(jsonData, qnbLayoutId);
		responseValidator.validateCreateSuccess(response);
		response = cimAdmin.getAdminTemplate();
		responseValidator.validateGetAdminTemplate(response, cimAdmin);
		jsonData.put("Formula_Id__c", calcFormulaIdBenefit);
		jsonData.put("Data_Source_Id__c", cimAdmin.getDataSourceData().getDataSourceId());
		cimAdmin.mapProgramTemplateDataSource(jsonData);
		cimAdmin.activateAdminTemplate(RebatesConstants.responseNocontent);

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewLinkTemplateSubTypePromotionAPI");
		response = cimAdmin.createLinkTemplates(jsonData);
		responseValidator.validateLinkTemplatesStatus(response, cimAdmin, RebatesConstants.draft);
		cimAdmin.deleteLinkTemplate(RebatesConstants.responseNocontent);
		response = cimAdmin.getLinkTemplatesViaProgramType(jsonData); 
		responseValidator.validateDeleteSuccess(response);
	}
	
    @Test(description = "TC-410 Unable to Delete Link Template which is in Active or Inactive Status", groups = { "Regression", "Medium", "API" })
    public void deleteActivateLinkTemplate() throws Exception {
        jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createFieldExpressionId");
        String fieldExpressionId = cimAdmin.getFieldExpressionId(jsonData);
        jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createCalcFormulaIdBenefit");
        String calcFormulaIdBenefit = cimAdmin.getCalcFormulaId(jsonData);
        jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createCalcFormulaIdQualification");
        String calcFormulaIdQualification = cimAdmin.getCalcFormulaId(jsonData);
        jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "linkCalcFormulaToExpressionId");
        cimAdmin.linkCalcFormulaToExpression(jsonData, calcFormulaIdBenefit, fieldExpressionId);
        cimAdmin.linkCalcFormulaToExpression(jsonData, calcFormulaIdQualification, fieldExpressionId);
        jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewDataSourceAPI");
        cimAdmin.createDataSource(jsonData);
        cimAdmin.linkDatasourceToCalcFormula(calcFormulaIdBenefit);
        cimAdmin.linkDatasourceToCalcFormula(calcFormulaIdQualification);

        jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createQnBLayoutAPI");
        String qnbLayoutId = cimAdmin.getQnBLayoutId(jsonData);

        jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewAdminTemplateAPI");
        response = cimAdmin.createAdminTemplate(jsonData, qnbLayoutId);
        responseValidator.validateCreateSuccess(response);
        response = cimAdmin.getAdminTemplate();
        responseValidator.validateGetAdminTemplate(response, cimAdmin);
        jsonData.put("Formula_Id__c", calcFormulaIdBenefit);
        jsonData.put("Data_Source_Id__c", cimAdmin.getDataSourceData().getDataSourceId());
        cimAdmin.mapProgramTemplateDataSource(jsonData);
        cimAdmin.activateAdminTemplate(RebatesConstants.responseNocontent);

        jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewLinkTemplateSubTypeSalesAPI");
        response = cimAdmin.createLinkTemplates(jsonData);
        cimAdmin.activateLinkTemplate();
        response = cimAdmin.getLinkTemplatesViaId();
        responseValidator.validateLinkTemplatesStatus(response, cimAdmin, RebatesConstants.activate);
        
        cimAdmin.deActivateLinkTemplate();
        response = cimAdmin.getLinkTemplatesViaId();
        responseValidator.validateLinkTemplatesStatus(response, cimAdmin, RebatesConstants.Deactivate);
        
        response = cimAdmin.deleteLinkTemplate(RebatesConstants.responseBadRequest);
        responseValidator.validateFailureResponse(response, RebatesConstants.errorCodeCustomValidation, RebatesConstants.messageDeleteActiveInactiveLinkTemplate);
    }
}
