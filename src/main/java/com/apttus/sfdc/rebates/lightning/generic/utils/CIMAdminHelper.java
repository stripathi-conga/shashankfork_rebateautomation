package com.apttus.sfdc.rebates.lightning.generic.utils;

import java.util.Map;
import com.apttus.customException.ApplicationException;
import com.apttus.helpers.Efficacies;
import com.apttus.sfdc.rebates.lightning.api.library.CIMAdmin;
import com.apttus.sfdc.rebates.lightning.api.validator.ResponseValidatorBase;
import com.jayway.restassured.response.Response;

public class CIMAdminHelper {

	public Efficacies efficacies;
	public Map<String, String> jsonData;
	public String stepCalcFormulaIdBenefit;
	public String stepCalcFormulaIdQualification;
	public String nonStepCalcFormulaIdBenefit;
	public String nonStepCalcFormulaIdQualification;
	public Response response;
	public ResponseValidatorBase responseValidator;
	public String fieldExpressionId;
	public String dataSourceIdCIMAdmin;

	public CIMAdminHelper() throws Exception {
		efficacies = new Efficacies();
		responseValidator = new ResponseValidatorBase();
	}

	public String createDataSourceAndFormulasForTiered(CIMAdmin cimAdmin) throws Exception {
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createFieldExpressionId");
		fieldExpressionId = cimAdmin.getFieldExpressionId(jsonData);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createStepCalcFormulaIdBenefit");
		stepCalcFormulaIdBenefit = cimAdmin.getCalcFormulaId(jsonData);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNonStepCalcFormulaIdBenefit");
		nonStepCalcFormulaIdBenefit = cimAdmin.getCalcFormulaId(jsonData);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createStepCalcFormulaIdQualification");
		stepCalcFormulaIdQualification = cimAdmin.getCalcFormulaId(jsonData);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNonStepCalcFormulaIdQualification");
		nonStepCalcFormulaIdQualification = cimAdmin.getCalcFormulaId(jsonData);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "linkCalcFormulaToExpressionId");
		cimAdmin.linkCalcFormulaToExpression(jsonData, stepCalcFormulaIdBenefit, fieldExpressionId);
		cimAdmin.linkCalcFormulaToExpression(jsonData, nonStepCalcFormulaIdBenefit, fieldExpressionId);
		cimAdmin.linkCalcFormulaToExpression(jsonData, stepCalcFormulaIdQualification, fieldExpressionId);
		cimAdmin.linkCalcFormulaToExpression(jsonData, nonStepCalcFormulaIdQualification, fieldExpressionId);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewDataSourceAccountBillingSummary");
		dataSourceIdCIMAdmin = cimAdmin.createDataSource(jsonData);
		cimAdmin.linkDatasourceToCalcFormula(stepCalcFormulaIdBenefit);
		cimAdmin.linkDatasourceToCalcFormula(nonStepCalcFormulaIdBenefit);
		cimAdmin.linkDatasourceToCalcFormula(stepCalcFormulaIdQualification);
		cimAdmin.linkDatasourceToCalcFormula(nonStepCalcFormulaIdQualification);
		return dataSourceIdCIMAdmin;
	}

	public String createDataSourceAndFormulasForDiscrete(CIMAdmin cimAdmin) throws Exception {
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createFieldExpressionId");
		fieldExpressionId = cimAdmin.getFieldExpressionId(jsonData);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNonStepCalcFormulaIdBenefit");
		nonStepCalcFormulaIdBenefit = cimAdmin.getCalcFormulaId(jsonData);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNonStepCalcFormulaIdQualification");
		nonStepCalcFormulaIdQualification = cimAdmin.getCalcFormulaId(jsonData);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "linkCalcFormulaToExpressionId");
		cimAdmin.linkCalcFormulaToExpression(jsonData, nonStepCalcFormulaIdBenefit, fieldExpressionId);
		cimAdmin.linkCalcFormulaToExpression(jsonData, nonStepCalcFormulaIdQualification, fieldExpressionId);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewDataSourceAccountBillingSummary");
		dataSourceIdCIMAdmin = cimAdmin.createDataSource(jsonData);
		cimAdmin.linkDatasourceToCalcFormula(nonStepCalcFormulaIdBenefit);
		cimAdmin.linkDatasourceToCalcFormula(nonStepCalcFormulaIdQualification);
		return dataSourceIdCIMAdmin;
	}

	public Response createAndValidateTemplate(CIMAdmin cimAdmin, String qnbLayoutId) throws Exception {
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewTemplateAPI");
		response = cimAdmin.createTemplate(jsonData, qnbLayoutId);
		responseValidator.validateCreateSuccess(response);		
		response = cimAdmin.getTemplate();		
		responseValidator.validateGetTemplate(response, cimAdmin);		
		responseValidator.validateTemplateStatus(response, cimAdmin, RebatesConstants.draft);
		return response;
	}

	public void mapDataSourceAndFormulaToTemplateTiered(CIMAdmin cimAdmin) throws ApplicationException {
		jsonData.put("FormulaId__c", RebatesConstants.formulaDataMap.get("stepCalcFormulaIdBenefit"));
		jsonData.put("DataSourceId__c", RebatesConstants.incentiveDataSourceId);
		cimAdmin.mapProgramTemplateDataSource(jsonData);
		jsonData.put("FormulaId__c", RebatesConstants.formulaDataMap.get("stepCalcFormulaIdQualification"));
		jsonData.put("DataSourceId__c", RebatesConstants.incentiveDataSourceId);
		cimAdmin.mapProgramTemplateDataSource(jsonData);
		jsonData.put("FormulaId__c", RebatesConstants.formulaDataMap.get("nonStepCalcFormulaIdBenefit"));
		jsonData.put("DataSourceId__c", RebatesConstants.incentiveDataSourceId);
		cimAdmin.mapProgramTemplateDataSource(jsonData);
		jsonData.put("FormulaId__c", RebatesConstants.formulaDataMap.get("nonStepCalcFormulaIdQualification"));
		jsonData.put("DataSourceId__c", RebatesConstants.incentiveDataSourceId);
		cimAdmin.mapProgramTemplateDataSource(jsonData);
	}

	public void mapDataSourceAndFormulaToTemplateDiscrete(CIMAdmin cimAdmin) throws ApplicationException {
		jsonData.put("FormulaId__c", RebatesConstants.formulaDataMap.get("nonStepCalcFormulaIdBenefit"));
		jsonData.put("DataSourceId__c", RebatesConstants.incentiveDataSourceId);
		cimAdmin.mapProgramTemplateDataSource(jsonData);
		jsonData.put("FormulaId__c", RebatesConstants.formulaDataMap.get("nonStepCalcFormulaIdQualification"));
		jsonData.put("DataSourceId__c", RebatesConstants.incentiveDataSourceId);
		cimAdmin.mapProgramTemplateDataSource(jsonData);
	}

	public void templateActivationForLinkTemplateTiered(CIMAdmin cimAdmin, String qnbLayoutId) throws Exception {
		createAndValidateTemplate(cimAdmin, qnbLayoutId);
		mapDataSourceAndFormulaToTemplateTiered(cimAdmin);
		cimAdmin.activateTemplate(RebatesConstants.responseNocontent);
		response = cimAdmin.getTemplate();
		responseValidator.validateTemplateStatus(response, cimAdmin, RebatesConstants.activate);
	}

	public void templateActivationForLinkTemplateDiscrete(CIMAdmin cimAdmin, String qnbLayoutId) throws Exception {
		createAndValidateTemplate(cimAdmin, qnbLayoutId);
		mapDataSourceAndFormulaToTemplateDiscrete(cimAdmin);
		cimAdmin.activateTemplate(RebatesConstants.responseNocontent);
		response = cimAdmin.getTemplate();
		responseValidator.validateTemplateStatus(response, cimAdmin, RebatesConstants.activate);
	}

	public void createAndValidateLinkTemplate(CIMAdmin cimAdmin, String linkTemplateDataFromJson) throws Exception {
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", linkTemplateDataFromJson);
		cimAdmin.createLinkTemplates(jsonData);
		response = cimAdmin.getLinkTemplatesViaId();
		responseValidator.validateGetLinkTemplates(jsonData, response, cimAdmin);
		responseValidator.validateLinkTemplatesStatus(response, cimAdmin, RebatesConstants.draft);
	}

	public void activateAndVerifyLinkTemplate(CIMAdmin cimAdmin) throws ApplicationException {
		cimAdmin.activateLinkTemplate();
		response = cimAdmin.getLinkTemplatesViaId();
		responseValidator.validateLinkTemplatesStatus(response, cimAdmin, RebatesConstants.activate);
	}

	public void deactivateAndVerifyLinkTemplate(CIMAdmin cimAdmin) throws ApplicationException {
		cimAdmin.deactivateLinkTemplate();
		response = cimAdmin.getLinkTemplatesViaId();
		responseValidator.validateLinkTemplatesStatus(response, cimAdmin, RebatesConstants.deactivate);
	}
}
