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

public class TestAdminTemplates extends UnifiedFramework {

	private Properties configProperties;
	protected String baseURL;
	private Efficacies efficacies;
	private SFDCRestUtils sfdcRestUtils;
	protected SFDCHelper sfdcHelper;
	private String instanceURL;
	private ResponseValidatorBase responseValidator;
	private CIMAdmin cimAdmin;
	private Map<String, String> jsonData;
	Response response;

	@BeforeClass(alwaysRun = true)
	@Parameters({ "runParallel", "environment", "browser", "hubURL" })
	public void beforeClass(String runParallel, String environment, String browser, String hubURL) throws Exception {
		efficacies = new Efficacies();
		sfdcRestUtils = new SFDCRestUtils();
		configProperties = efficacies.loadPropertyFile(environment);
		baseURL = configProperties.getProperty("baseURL");
		SFDCHelper.setMasterProperty(configProperties);
		instanceURL = SFDCHelper.setAccessToken(sfdcRestUtils);
		sfdcHelper = new SFDCHelper(instanceURL);
		cimAdmin = new CIMAdmin(instanceURL, sfdcRestUtils);
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() throws Exception {
		responseValidator = new ResponseValidatorBase();
	}

	@Test(description = "TC428-Verify for the creation of the Admin Template and List page", groups = { "Smoke",
			"API" })
	public void createNewAdminTemplate() throws Exception {

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
		cimAdmin.deleteAdminTemplate();
		response = cimAdmin.getAdminTemplate();
		responseValidator.validateDeleteSuccess(response);
	}

	@Test(description = "TC409-Unable to Delete  Active Admin Template", groups = { "Regression", "API", "High" })
	public void verifyActiveAdminTemplateDelete() throws Exception {

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
		cimAdmin.activateAdminTemplate();
		response = cimAdmin.deleteActiveInactiveTemplate();
		responseValidator.validateDeleteFailure(response, RebatesConstants.messageDeleteActiveInactiveTemplate,
				RebatesConstants.messageerrorcodeTemplate);
	}

	@Test(description = "TC411-Delete Template_Status_Draft_detail page", groups = { "Regression", "API", "Medium" })
	public void verifyDraftAdminTemplateDelete() throws Exception {

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
		cimAdmin.deleteAdminTemplate();
		response = cimAdmin.getAdminTemplate();
		responseValidator.validateDeleteSuccess(response);
	}

	@Test(description = "TTC432-Verify Template When the template is activated", groups = { "Regression", "API", "Medium" })
	public void verifyActiveAdminTemplateDeactivation() throws Exception {

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
		cimAdmin.activateAdminTemplate();
		cimAdmin.deActivateAdminTemplate();
		response = cimAdmin.getAdminTemplate();
		responseValidator.validateInActivateTemplateStatus(response, cimAdmin,RebatesConstants.Deactivate);
		response = cimAdmin.deleteActiveInactiveTemplate();
		responseValidator.validateDeleteFailure(response, RebatesConstants.messageDeleteActiveInactiveTemplate,
				RebatesConstants.messageerrorcodeTemplate);

	}

}
