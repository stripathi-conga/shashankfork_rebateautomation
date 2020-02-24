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
import com.apttus.sfdc.rebates.lightning.main.UnifiedFramework;
import com.apttus.sfdc.rudiments.utils.SFDCRestUtils;
import com.jayway.restassured.response.Response;

public class TestLinkTemplates extends UnifiedFramework {

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

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "benefitOnlyTieredQnBLayoutAPI");
		String qnbLayoutId = cimAdmin.getQnBLayoutId(jsonData);

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewTemplateAPI");
		response = cimAdmin.createTemplate(jsonData, qnbLayoutId);
		responseValidator.validateCreateSuccess(response);
		response = cimAdmin.getTemplate();
		responseValidator.validateGetTemplate(response, cimAdmin);
		jsonData.put("FormulaId__c", calcFormulaIdBenefit);
		jsonData.put("DataSourceId__c", cimAdmin.getDataSourceData().getDataSourceId());
		cimAdmin.mapProgramTemplateDataSource(jsonData);
		jsonData.put("FormulaId__c", calcFormulaIdQualification);
		jsonData.put("DataSourceId__c", cimAdmin.getDataSourceData().getDataSourceId());
		cimAdmin.mapProgramTemplateDataSource(jsonData);
		cimAdmin.activateTemplate(RebatesConstants.responseNocontent);

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "autoNewLinkTemplateSubTypeTieredAPI");
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

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "benefitOnlyTieredQnBLayoutAPI");
		String qnbLayoutId = cimAdmin.getQnBLayoutId(jsonData);

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewTemplateAPI");
		response = cimAdmin.createTemplate(jsonData, qnbLayoutId);
		responseValidator.validateCreateSuccess(response);
		response = cimAdmin.getTemplate();
		responseValidator.validateGetTemplate(response, cimAdmin);
		jsonData.put("FormulaId__c", calcFormulaIdBenefit);
		jsonData.put("DataSourceId__c", cimAdmin.getDataSourceData().getDataSourceId());
		cimAdmin.mapProgramTemplateDataSource(jsonData);
		jsonData.put("FormulaId__c", calcFormulaIdQualification);
		jsonData.put("DataSourceId__c", cimAdmin.getDataSourceData().getDataSourceId());
		cimAdmin.mapProgramTemplateDataSource(jsonData);
		cimAdmin.activateTemplate(RebatesConstants.responseNocontent);

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "autoNewLinkTemplateSubTypeTieredAPI");
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

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "benefitOnlyTieredQnBLayoutAPI");
		String qnbLayoutId = cimAdmin.getQnBLayoutId(jsonData);

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewTemplateAPI");
		response = cimAdmin.createTemplate(jsonData, qnbLayoutId);
		responseValidator.validateCreateSuccess(response);
		response = cimAdmin.getTemplate();
		responseValidator.validateGetTemplate(response, cimAdmin);
		jsonData.put("FormulaId__c", calcFormulaIdBenefit);
		jsonData.put("DataSourceId__c", cimAdmin.getDataSourceData().getDataSourceId());
		cimAdmin.mapProgramTemplateDataSource(jsonData);
		jsonData.put("FormulaId__c", calcFormulaIdQualification);
		jsonData.put("DataSourceId__c", cimAdmin.getDataSourceData().getDataSourceId());
		cimAdmin.mapProgramTemplateDataSource(jsonData);
		cimAdmin.activateTemplate(RebatesConstants.responseNocontent);

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "autoNewLinkTemplateSubTypeDiscreteAPI");
		response = cimAdmin.createLinkTemplates(jsonData);
		responseValidator.validateLinkTemplatesStatus(response, cimAdmin, RebatesConstants.draft);
		cimAdmin.deleteLinkTemplate(RebatesConstants.responseNocontent);		
	}
	
	@Test(description = "TC-462 Deactivation of Active template association automatically when Template is deactivated", groups = {
			"Regression", "Medium", "API" })
	public void deactivateTemplateOfActivateLinkTemplate() throws Exception {
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

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "benefitOnlyTieredQnBLayoutAPI");
		String qnbLayoutId = cimAdmin.getQnBLayoutId(jsonData);

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewTemplateAPI");
		response = cimAdmin.createTemplate(jsonData, qnbLayoutId);
		responseValidator.validateCreateSuccess(response);
		response = cimAdmin.getTemplate();
		responseValidator.validateGetTemplate(response, cimAdmin);
		jsonData.put("FormulaId__c", calcFormulaIdBenefit);
		jsonData.put("DataSourceId__c", cimAdmin.getDataSourceData().getDataSourceId());
		cimAdmin.mapProgramTemplateDataSource(jsonData);
		jsonData.put("FormulaId__c", calcFormulaIdQualification);
		jsonData.put("DataSourceId__c", cimAdmin.getDataSourceData().getDataSourceId());
		cimAdmin.mapProgramTemplateDataSource(jsonData);
		cimAdmin.activateTemplate(RebatesConstants.responseNocontent);

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json",
				"autoNewLinkTemplateSubTypeDiscreteAPI");
		response = cimAdmin.createLinkTemplates(jsonData);
		responseValidator.validateLinkTemplatesStatus(response, cimAdmin, RebatesConstants.draft);

		cimAdmin.deactivateTemplate();
		response = cimAdmin.getTemplate();
		responseValidator.validateTemplateStatus(response, cimAdmin, RebatesConstants.deactivate);

		response = cimAdmin.getLinkTemplatesViaId();
		responseValidator.validateLinkTemplatesStatus(response, cimAdmin, RebatesConstants.draft);

		response = cimAdmin.deleteLinkTemplate(RebatesConstants.responseNocontent);

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewTemplateAPI");
		response = cimAdmin.createTemplate(jsonData, qnbLayoutId);
		responseValidator.validateCreateSuccess(response);
		response = cimAdmin.getTemplate();
		responseValidator.validateGetTemplate(response, cimAdmin);
		jsonData.put("FormulaId__c", calcFormulaIdBenefit);
		jsonData.put("DataSourceId__c", cimAdmin.getDataSourceData().getDataSourceId());
		cimAdmin.mapProgramTemplateDataSource(jsonData);
		jsonData.put("FormulaId__c", calcFormulaIdQualification);
		jsonData.put("DataSourceId__c", cimAdmin.getDataSourceData().getDataSourceId());
		cimAdmin.mapProgramTemplateDataSource(jsonData);
		cimAdmin.activateTemplate(RebatesConstants.responseNocontent);

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json",
				"autoNewLinkTemplateSubTypeTieredAPI");
		response = cimAdmin.createLinkTemplates(jsonData);
		responseValidator.validateLinkTemplatesStatus(response, cimAdmin, RebatesConstants.draft);

		cimAdmin.activateLinkTemplate();
		response = cimAdmin.getLinkTemplatesViaId();
		responseValidator.validateLinkTemplatesStatus(response, cimAdmin, RebatesConstants.activate);

		cimAdmin.deactivateTemplate();
		response = cimAdmin.getTemplate();
		responseValidator.validateTemplateStatus(response, cimAdmin, RebatesConstants.deactivate);

		response = cimAdmin.getLinkTemplatesViaId();
		responseValidator.validateLinkTemplatesStatus(response, cimAdmin, RebatesConstants.deactivate);
	}
	
	@Test(description = "TC-410 Unable to Delete Link Template which is in Active or Inactive Status from spillover menu", groups = {
			"Regression", "Medium", "API" })
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

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "benefitOnlyTieredQnBLayoutAPI");
		String qnbLayoutId = cimAdmin.getQnBLayoutId(jsonData);

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewTemplateAPI");
		response = cimAdmin.createTemplate(jsonData, qnbLayoutId);
		responseValidator.validateCreateSuccess(response);
		response = cimAdmin.getTemplate();
		responseValidator.validateGetTemplate(response, cimAdmin);
		jsonData.put("FormulaId__c", calcFormulaIdBenefit);
		jsonData.put("DataSourceId__c", cimAdmin.getDataSourceData().getDataSourceId());
		cimAdmin.mapProgramTemplateDataSource(jsonData);
		jsonData.put("FormulaId__c", calcFormulaIdQualification);
		jsonData.put("DataSourceId__c", cimAdmin.getDataSourceData().getDataSourceId());
		cimAdmin.mapProgramTemplateDataSource(jsonData);
		cimAdmin.activateTemplate(RebatesConstants.responseNocontent);

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "autoNewLinkTemplateSubTypeDiscreteAPI");
		response = cimAdmin.createLinkTemplates(jsonData);
		cimAdmin.activateLinkTemplate();
		response = cimAdmin.getLinkTemplatesViaId();
		responseValidator.validateLinkTemplatesStatus(response, cimAdmin, RebatesConstants.activate);

		response = cimAdmin.deleteLinkTemplate(RebatesConstants.responseBadRequest);
		responseValidator.validateFailureResponse(response, RebatesConstants.errorCodeCustomValidation,
				RebatesConstants.messageDeleteActiveInactiveLinkTemplate);
		
		cimAdmin.deactivateLinkTemplate();
		response = cimAdmin.getLinkTemplatesViaId();
		responseValidator.validateLinkTemplatesStatus(response, cimAdmin, RebatesConstants.deactivate);

		response = cimAdmin.deleteLinkTemplate(RebatesConstants.responseBadRequest);
		responseValidator.validateFailureResponse(response, RebatesConstants.errorCodeCustomValidation,
				RebatesConstants.messageDeleteActiveInactiveLinkTemplate);
	}

	@Test(description = "TC-284 Verify the linking of active templates when the link status of the previously linked templates is Draft", groups = {
			"Regression", "Medium", "API" })
	public void addInactiveLinkTemplateAgain() throws Exception {
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

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "benefitOnlyTieredQnBLayoutAPI");
		String qnbLayoutId = cimAdmin.getQnBLayoutId(jsonData);

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewTemplateAPI");
		response = cimAdmin.createTemplate(jsonData, qnbLayoutId);
		responseValidator.validateCreateSuccess(response);
		jsonData.put("FormulaId__c", calcFormulaIdBenefit);
		jsonData.put("DataSourceId__c", cimAdmin.getDataSourceData().getDataSourceId());
		cimAdmin.mapProgramTemplateDataSource(jsonData);
		jsonData.put("FormulaId__c", calcFormulaIdQualification);
		jsonData.put("DataSourceId__c", cimAdmin.getDataSourceData().getDataSourceId());
		cimAdmin.mapProgramTemplateDataSource(jsonData);
		cimAdmin.activateTemplate(RebatesConstants.responseNocontent);

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json",
				"autoNewLinkTemplateSubTypeDiscreteAPI");
		response = cimAdmin.createLinkTemplates(jsonData);

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewTemplateAPI");
		response = cimAdmin.createTemplate(jsonData, qnbLayoutId);
		responseValidator.validateCreateSuccess(response);
		jsonData.put("FormulaId__c", calcFormulaIdBenefit);
		jsonData.put("DataSourceId__c", cimAdmin.getDataSourceData().getDataSourceId());
		cimAdmin.mapProgramTemplateDataSource(jsonData);
		jsonData.put("FormulaId__c", calcFormulaIdQualification);
		jsonData.put("DataSourceId__c", cimAdmin.getDataSourceData().getDataSourceId());
		cimAdmin.mapProgramTemplateDataSource(jsonData);
		cimAdmin.activateTemplate(RebatesConstants.responseNocontent);

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json",
				"autoNewLinkTemplateSubTypeDiscreteAPI");
		response = cimAdmin.createLinkTemplates(jsonData);

		cimAdmin.activateLinkTemplate();
		response = cimAdmin.getLinkTemplatesViaId();
		responseValidator.validateLinkTemplatesStatus(response, cimAdmin, RebatesConstants.activate);

		cimAdmin.deactivateLinkTemplate();
		response = cimAdmin.getLinkTemplatesViaId();
		responseValidator.validateLinkTemplatesStatus(response, cimAdmin, RebatesConstants.deactivate);

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewTemplateAPI");
		response = cimAdmin.createTemplate(jsonData, qnbLayoutId);
		responseValidator.validateCreateSuccess(response);
		jsonData.put("FormulaId__c", calcFormulaIdBenefit);
		jsonData.put("DataSourceId__c", cimAdmin.getDataSourceData().getDataSourceId());
		cimAdmin.mapProgramTemplateDataSource(jsonData);
		jsonData.put("FormulaId__c", calcFormulaIdQualification);
		jsonData.put("DataSourceId__c", cimAdmin.getDataSourceData().getDataSourceId());
		cimAdmin.mapProgramTemplateDataSource(jsonData);
		cimAdmin.activateTemplate(RebatesConstants.responseNocontent);

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json",
				"autoNewLinkTemplateSubTypeDiscreteAPI");
		response = cimAdmin.createLinkTemplates(jsonData);
		cimAdmin.activateLinkTemplate();
		response = cimAdmin.getLinkTemplatesViaId();
		responseValidator.validateLinkTemplatesStatus(response, cimAdmin, RebatesConstants.activate);

		response = cimAdmin.draftLinkTemplateNegative();
		responseValidator.validateFailureResponse(response, RebatesConstants.errorCodeCustomValidation,
				RebatesConstants.messageChangeLinkTemplateStatusToDraft);

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json",
				"autoNewLinkTemplateSubTypeDiscreteAPI");
		response = cimAdmin.createLinkTemplatesNegative(jsonData);
		responseValidator.validateFailureResponse(response, RebatesConstants.errorCodeCustomValidation,
				RebatesConstants.messageActiveMappingExists);

		cimAdmin.deactivateLinkTemplate();
		response = cimAdmin.getLinkTemplatesViaId();
		responseValidator.validateLinkTemplatesStatus(response, cimAdmin, RebatesConstants.deactivate);
	}

}
