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
import com.apttus.sfdc.rebates.lightning.generic.utils.CIMAdminHelper;
import com.apttus.sfdc.rebates.lightning.generic.utils.RebatesConstants;
import com.apttus.sfdc.rebates.lightning.generic.utils.SFDCHelper;
import com.apttus.sfdc.rebates.lightning.main.UnifiedFramework;
import com.apttus.sfdc.rudiments.utils.SFDCRestUtils;
import com.jayway.restassured.response.Response;

public class TestTemplates extends UnifiedFramework {

	private Properties configProperties;
	private Efficacies efficacies;
	private SFDCRestUtils sfdcRestUtils;
	private String instanceURL;
	private ResponseValidatorBase responseValidator;
	private CIMAdmin cimAdmin;
	private Map<String, String> jsonData;
	private Response response;
	private CIMAdminHelper cimAdminHelper;

	@BeforeClass(alwaysRun = true)
	@Parameters({ "runParallel", "environment", "browser", "hubURL" })
	public void beforeClass(String runParallel, String environment, String browser, String hubURL) throws Exception {
		efficacies = new Efficacies();
		sfdcRestUtils = new SFDCRestUtils();
		configProperties = efficacies.loadPropertyFile(environment);
		SFDCHelper.setMasterProperty(configProperties);
		instanceURL = SFDCHelper.setAccessToken(sfdcRestUtils);
		cimAdmin = new CIMAdmin(instanceURL, sfdcRestUtils);
		cimAdminHelper = new CIMAdminHelper();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() throws Exception {
		responseValidator = new ResponseValidatorBase();
	}

	@Test(description = "TC428-Verify for the creation of the Template and List page", groups = { "Smoke", "API" })
	public void createNewTemplate() throws Exception {
		
		cimAdminHelper.createDataSourceAndFormulasForTiered(cimAdmin);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "benefitOnlyTieredQnBLayoutAPI");
		String qnbLayoutId = cimAdmin.getQnBLayoutId(jsonData);
		cimAdminHelper.createAndValidateTemplate(cimAdmin, qnbLayoutId);
		cimAdminHelper.mapDataSourceAndFormulaToTemplateTiered(cimAdmin);
		cimAdmin.deleteTemplate();
		response = cimAdmin.getTemplate();
		responseValidator.validateDeleteSuccess(response);
	}

	@Test(description = "TC409-Unable to Delete  Active Template", groups = { "Regression", "API", "High" })
	public void verifyActiveTemplateDelete() throws Exception {
		
		cimAdminHelper.createDataSourceAndFormulasForTiered(cimAdmin);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "benefitOnlyTieredQnBLayoutAPI");
		String qnbLayoutId = cimAdmin.getQnBLayoutId(jsonData);
		cimAdminHelper.createAndValidateTemplate(cimAdmin, qnbLayoutId);
		cimAdminHelper.mapDataSourceAndFormulaToTemplateTiered(cimAdmin);
		cimAdmin.activateTemplate(RebatesConstants.responseNocontent);
		response = cimAdmin.deleteActiveInactiveTemplate();
		responseValidator.validateFailureResponse(response, RebatesConstants.errorCodeCustomValidation,
				RebatesConstants.messageDeleteActiveInactiveTemplate);
	}

	@Test(description = "TC411-Verify the Delete for Draft Template", groups = { "Regression", "API", "Medium" })
	public void verifyDraftTemplateDelete() throws Exception {
		
		cimAdminHelper.createDataSourceAndFormulasForTiered(cimAdmin);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "benefitOnlyTieredQnBLayoutAPI");
		String qnbLayoutId = cimAdmin.getQnBLayoutId(jsonData);
		cimAdminHelper.createAndValidateTemplate(cimAdmin, qnbLayoutId);
		cimAdmin.deleteTemplate();
		response = cimAdmin.getTemplate();
		responseValidator.validateDeleteSuccess(response);
	}

	@Test(description = "TC432-Verify that user should allow to deactivate the active Template", groups = {
			"Regression", "API", "Medium" })
	public void verifyActiveTemplateDeactivation() throws Exception {
		
		cimAdminHelper.createDataSourceAndFormulasForTiered(cimAdmin);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "benefitOnlyTieredQnBLayoutAPI");
		String qnbLayoutId = cimAdmin.getQnBLayoutId(jsonData);
		response = cimAdminHelper.createAndValidateTemplate(cimAdmin, qnbLayoutId);
		cimAdminHelper.mapDataSourceAndFormulaToTemplateTiered(cimAdmin);
		cimAdmin.activateTemplate(RebatesConstants.responseNocontent);
		response = cimAdmin.getTemplate();
		responseValidator.validateTemplateStatus(response, cimAdmin, RebatesConstants.activate);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "updateTemplateAPI");
		Response editresponse = cimAdmin.editTemplate(jsonData, qnbLayoutId, RebatesConstants.responseBadRequest);
		responseValidator.validateFailureResponse(editresponse, RebatesConstants.errorCodeCustomValidation,
				RebatesConstants.messageUpdateActiveInactiveTemplate);
		cimAdmin.deactivateTemplate();
		response = cimAdmin.getTemplate();
		responseValidator.validateTemplateStatus(response, cimAdmin, RebatesConstants.deactivate);
	}

	@Test(description = "TC-431 Verify Edit for the Template in Draft Status", groups = { "Regression", "API",
			"Medium" })
	public void verifyEditTemplate() throws Exception {
		
		cimAdminHelper.createDataSourceAndFormulasForTiered(cimAdmin);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "benefitOnlyTieredQnBLayoutAPI");
		String qnbLayoutId = cimAdmin.getQnBLayoutId(jsonData);
		response = cimAdminHelper.createAndValidateTemplate(cimAdmin, qnbLayoutId);
		cimAdminHelper.mapDataSourceAndFormulaToTemplateDiscrete(cimAdmin);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "benefitOnlyDiscreteQnBLayoutAPI");
		String discreteQnBLayoutId = cimAdmin.getQnBLayoutId(jsonData);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "updateTemplateAPI");
		cimAdmin.editTemplate(jsonData, discreteQnBLayoutId, RebatesConstants.responseNocontent);
		response = cimAdmin.getTemplate();
		responseValidator.validateTemplateStatus(response, cimAdmin, RebatesConstants.draft);
		responseValidator.validateUpdatedTemplate(response, cimAdmin, jsonData, discreteQnBLayoutId);
		cimAdmin.deleteTemplate();
		response = cimAdmin.getTemplate();
		responseValidator.validateDeleteSuccess(response);
	}

	@Test(description = "TC-501 Verify Mandatory fields for Template Activation", groups = { "Regression", "API",
			"High" })
	public void verifyMandatoryFieldsforActivateTemplate() throws Exception {
		
		cimAdminHelper.createDataSourceAndFormulasForTiered(cimAdmin);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "benefitOnlyTieredQnBLayoutAPI");
		String qnbLayoutId = cimAdmin.getQnBLayoutId(jsonData);
		cimAdminHelper.createAndValidateTemplate(cimAdmin, qnbLayoutId);
		response = cimAdmin.activateTemplate(RebatesConstants.responseBadRequest);
		responseValidator.validateFailureResponse(response, RebatesConstants.errorCodeCustomValidation,
				RebatesConstants.messageMandatoryTemplatefields);
	}

	@Test(description = "TC413-Unable to Delete Inactivated Template", groups = { "Regression", "API", "Medium" })
	public void verifyInactiveTemplateDelete() throws Exception {
		
		cimAdminHelper.createDataSourceAndFormulasForTiered(cimAdmin);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "benefitOnlyTieredQnBLayoutAPI");
		String qnbLayoutId = cimAdmin.getQnBLayoutId(jsonData);
		cimAdminHelper.createAndValidateTemplate(cimAdmin, qnbLayoutId);
		cimAdminHelper.mapDataSourceAndFormulaToTemplateTiered(cimAdmin);
		cimAdmin.activateTemplate(RebatesConstants.responseNocontent);
		cimAdmin.deactivateTemplate();
		response = cimAdmin.deleteActiveInactiveTemplate();
		responseValidator.validateFailureResponse(response, RebatesConstants.errorCodeCustomValidation,
				RebatesConstants.messageDeleteActiveInactiveTemplate);
	}

	@Test(description = "TC-556 Benefit Product and Discrete Edit Activate Details", groups = { "Regression", "API",
			"Medium" })
	public void verifyEditTemplateActivation() throws Exception {
		
		cimAdminHelper.createDataSourceAndFormulasForDiscrete(cimAdmin);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "benefitOnlyDiscreteQnBLayoutAPI");
		String discreteQnBLayoutId = cimAdmin.getQnBLayoutId(jsonData);
		cimAdminHelper.createAndValidateTemplate(cimAdmin, discreteQnBLayoutId);
		cimAdminHelper.mapDataSourceAndFormulaToTemplateDiscrete(cimAdmin);

		// ------------ Update Template Name --------------------
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewTemplateAPI");
		String updatedTemplateName = cimAdmin.getTemplateData().getName() + "_Updated";
		jsonData.put("Name", updatedTemplateName);
		cimAdmin.editTemplate(jsonData, discreteQnBLayoutId, RebatesConstants.responseNocontent);
		response = cimAdmin.getTemplate();
		responseValidator.validateTemplateStatus(response, cimAdmin, RebatesConstants.draft);
		responseValidator.validateUpdatedTemplate(response, cimAdmin, jsonData, discreteQnBLayoutId);

		// ------------ Activate Template --------------------
		cimAdmin.activateTemplate(RebatesConstants.responseNocontent);
		response = cimAdmin.getTemplate();
		responseValidator.validateTemplateStatus(response, cimAdmin, RebatesConstants.activate);
	}

	@Test(description = "TC-561 Activate template with layout as Qualification & Benefit Product and Tier value as Tiered", groups = {
			"Regression", "API", "Medium" })
	public void verifyNewTemplateActivationForQnBTiered() throws Exception {
		
		cimAdminHelper.createDataSourceAndFormulasForTiered(cimAdmin);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "qualificationAndBenefitTieredQnBLayoutAPI");
		String tieredQnBLayoutId = cimAdmin.getQnBLayoutId(jsonData);

		// ------------ Create New Template --------------------
		cimAdminHelper.createAndValidateTemplate(cimAdmin, tieredQnBLayoutId);
		cimAdminHelper.mapDataSourceAndFormulaToTemplateTiered(cimAdmin);

		// ------------ Activate Template --------------------
		cimAdmin.activateTemplate(RebatesConstants.responseNocontent);
		response = cimAdmin.getTemplate();
		responseValidator.validateTemplateStatus(response, cimAdmin, RebatesConstants.activate);
	}

	@Test(description = "TC-560 Activate template with layout as Qualification & Benefit Product and Tier value as Discrete", groups = {
			"Regression", "API", "Medium" })
	public void verifyNewTemplateActivationForQnBDiscrete() throws Exception {
		
		cimAdminHelper.createDataSourceAndFormulasForDiscrete(cimAdmin);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json",
				"qualificationAndBenefitDiscreteQnBLayoutAPI");
		String discreteQnBLayoutId = cimAdmin.getQnBLayoutId(jsonData);

		// ------------ Create New Template --------------------
		cimAdminHelper.createAndValidateTemplate(cimAdmin, discreteQnBLayoutId);
		jsonData.put("DataSourceId__c", cimAdmin.getDataSourceData().getDataSourceId());
		cimAdmin.mapProgramTemplateDataSource(jsonData);

		// ----- Activate Template failed without Qualification and Benefit Formula -----
		response = cimAdmin.activateTemplate(RebatesConstants.responseBadRequest);
		responseValidator.validateFailureResponse(response, RebatesConstants.errorCodeCustomValidation,
				RebatesConstants.messageMandatoryTemplatefields);

		// ------- Add Qualification and Benefit Formula and then Activate the Template -----
		cimAdminHelper.mapDataSourceAndFormulaToTemplateDiscrete(cimAdmin);

		cimAdmin.activateTemplate(RebatesConstants.responseNocontent);
		response = cimAdmin.getTemplate();
		responseValidator.validateTemplateStatus(response, cimAdmin, RebatesConstants.activate);
	}

	@Test(description = "TC-558 Edit Discrete Benefit Product and change QnBLayout to Qualification and Benefit Product then change it to MQMB", groups = {
			"Regression", "API", "High" })
	public void editBenefitTemplateChangeToQnBProductAndActivate() throws Exception {
		
		cimAdminHelper.createDataSourceAndFormulasForDiscrete(cimAdmin);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "benefitOnlyDiscreteQnBLayoutAPI");
		String qnbLayoutId = cimAdmin.getQnBLayoutId(jsonData);
		cimAdminHelper.createAndValidateTemplate(cimAdmin, qnbLayoutId);

		jsonData.put("DataSourceId__c", cimAdmin.getDataSourceData().getDataSourceId());
		cimAdmin.mapProgramTemplateDataSource(jsonData);

		// ---- Activate Template failed without Qualification and Benefit Formula ----
		response = cimAdmin.activateTemplate(RebatesConstants.responseBadRequest);
		responseValidator.validateFailureResponse(response, RebatesConstants.errorCodeCustomValidation,
				RebatesConstants.messageMandatoryTemplatefields);

		// -- Edit Template and change QnB Layout to Qualification and Benefit Product --
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json",
				"qualificationAndBenefitDiscreteQnBLayoutAPI");
		qnbLayoutId = cimAdmin.getQnBLayoutId(jsonData);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "updateTemplateAPI");
		cimAdmin.editTemplate(jsonData, qnbLayoutId, RebatesConstants.responseNocontent);
		response = cimAdmin.getTemplate();
		responseValidator.validateGetTemplate(response, cimAdmin);

		// ---- Activate Template failed without Qualification and Benefit Formula ----
		response = cimAdmin.activateTemplate(RebatesConstants.responseBadRequest);
		responseValidator.validateFailureResponse(response, RebatesConstants.errorCodeCustomValidation,
				RebatesConstants.messageMandatoryTemplatefields);

		// --- Edit Template and change QnB Layout to Multiple Qualification & Benefit Products ---
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json",
				"multipleQualificationAndBenefitDiscreteQnBLayoutAPI");
		qnbLayoutId = cimAdmin.getQnBLayoutId(jsonData);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "updateTemplateAPI");
		cimAdmin.editTemplate(jsonData, qnbLayoutId, RebatesConstants.responseNocontent);
		response = cimAdmin.getTemplate();
		responseValidator.validateGetTemplate(response, cimAdmin);

		// ---- Activate Template failed without Qualification and Benefit Formula ----
		response = cimAdmin.activateTemplate(RebatesConstants.responseBadRequest);
		responseValidator.validateFailureResponse(response, RebatesConstants.errorCodeCustomValidation,
				RebatesConstants.messageMandatoryTemplatefields);
	}

	@Test(description = "TC-559 Activation failed for Tiered Benefit Product without Qualification and Benefit formula", groups = {
			"Regression", "API", "Medium" })
	public void benefitProductActivationFailedWithoutFormulas() throws Exception {
		
		cimAdminHelper.createDataSourceAndFormulasForTiered(cimAdmin);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "benefitOnlyTieredQnBLayoutAPI");
		String qnbLayoutId = cimAdmin.getQnBLayoutId(jsonData);

		// ------------ Create New Template --------------------
		cimAdminHelper.createAndValidateTemplate(cimAdmin, qnbLayoutId);
		jsonData.put("DataSourceId__c", cimAdmin.getDataSourceData().getDataSourceId());
		cimAdmin.mapProgramTemplateDataSource(jsonData);

		// ---- Activate Template failed without Qualification and Benefit Formula -----
		response = cimAdmin.activateTemplate(RebatesConstants.responseBadRequest);
		responseValidator.validateFailureResponse(response, RebatesConstants.errorCodeCustomValidation,
				RebatesConstants.messageMandatoryTemplatefields);

		// -- Add Qualification and Benefit Formula and then Activate the Template --
		cimAdminHelper.mapDataSourceAndFormulaToTemplateTiered(cimAdmin);

		cimAdmin.activateTemplate(RebatesConstants.responseNocontent);
		response = cimAdmin.getTemplate();
		responseValidator.validateTemplateStatus(response, cimAdmin, RebatesConstants.activate);
	}
	
	@Test(description = "TC-565 Verify Creation and Activation of Multiple Qualification and Benefit Product Tiered", groups = {
			"Regression", "API", "Medium" })
	public void verifyActivationOfMQMBTiered() throws Exception {
		
		cimAdminHelper.createDataSourceAndFormulasForTiered(cimAdmin);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "multipleQualificationAndBenefitTieredQnBLayoutAPI");
		String qnbLayoutId = cimAdmin.getQnBLayoutId(jsonData);

		// ------------ Create New Template and map formulas --------------------
		cimAdminHelper.createAndValidateTemplate(cimAdmin, qnbLayoutId);
		cimAdminHelper.mapDataSourceAndFormulaToTemplateTiered(cimAdmin);

		// ---- Activate Template  -----
		cimAdmin.activateTemplate(RebatesConstants.responseNocontent);
		response = cimAdmin.getTemplate();
		responseValidator.validateTemplateStatus(response, cimAdmin, RebatesConstants.activate);
	}
}