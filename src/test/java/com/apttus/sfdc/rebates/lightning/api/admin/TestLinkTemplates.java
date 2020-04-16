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
import com.apttus.sfdc.rebates.lightning.generic.utils.DataHelper;
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
	private CIMAdminHelper cimAdminHelper;

	@BeforeClass(alwaysRun = true)
	@Parameters({ "runParallel", "environment", "browser", "hubURL" })
	public void beforeClass(String runParallel, String environment, String browser, String hubURL) throws Exception {
		DataHelper dataHelper = DataHelper.getInstance();
		dataHelper.getData(environment);
		
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

	@Test(description = "TC-279 Verify for the Link Template Create and List page", groups = { "Regression", "High",
			"API" })
	public void createLinkTemplate() throws Exception {
		
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "benefitOnlyTieredQnBLayoutAPI");
		String qnbLayoutId = cimAdmin.getQnBLayoutId(jsonData);		
		cimAdminHelper.templateActivationForLinkTemplate(cimAdmin, qnbLayoutId);		
		String linkTemplateDataFromJson = "autoNewLinkTemplateSubTypeTieredAPI";
		cimAdminHelper.createAndValidateLinkTemplate(cimAdmin, linkTemplateDataFromJson);
	}

	@Test(description = "TC-445 Verify Activation of Link Template", groups = { "Regression", "High", "API" })
	public void activateLinkTemplate() throws Exception {

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "benefitOnlyTieredQnBLayoutAPI");
		String qnbLayoutId = cimAdmin.getQnBLayoutId(jsonData);
		cimAdminHelper.templateActivationForLinkTemplate(cimAdmin, qnbLayoutId);
		String linkTemplateDataFromJson = "autoNewLinkTemplateSubTypeTieredAPI";
		cimAdminHelper.createAndValidateLinkTemplate(cimAdmin, linkTemplateDataFromJson);
		cimAdminHelper.activateAndVerifyLinkTemplate(cimAdmin);
	}

	@Test(description = "TC-408 Verify delete Link Template when status is Draft via spillover menu", groups = { "Regression",
			"Medium", "API" })
	public void deleteLinkTemplateDraft() throws Exception {
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "benefitOnlyDiscreteQnBLayoutAPI");
		String qnbLayoutId = cimAdmin.getQnBLayoutId(jsonData);
		cimAdminHelper.templateActivationForLinkTemplate(cimAdmin, qnbLayoutId);
		String linkTemplateDataFromJson = "autoNewLinkTemplateSubTypeDiscreteAPI";
		cimAdminHelper.createAndValidateLinkTemplate(cimAdmin, linkTemplateDataFromJson);	
		cimAdmin.deleteLinkTemplate(RebatesConstants.responseNocontent);		
	}
	
	@Test(description = "TC-462 Deactivation of Active template association automatically when Template is deactivated", groups = {
			"Regression", "Medium", "API" })
	public void deactivateTemplateOfActivateLinkTemplate() throws Exception {
		
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "benefitOnlyTieredQnBLayoutAPI");
		String qnbLayoutId = cimAdmin.getQnBLayoutId(jsonData);
		cimAdminHelper.templateActivationForLinkTemplate(cimAdmin, qnbLayoutId);
		
		String linkTemplateDataFromJson = "autoNewLinkTemplateSubTypeDiscreteAPI";
		cimAdminHelper.createAndValidateLinkTemplate(cimAdmin, linkTemplateDataFromJson);
		
		cimAdmin.deactivateTemplate();		
		response = cimAdmin.getTemplate();
		responseValidator.validateTemplateStatus(response, cimAdmin, RebatesConstants.deactivate);
		response = cimAdmin.getLinkTemplatesViaId();
		responseValidator.validateLinkTemplatesStatus(response, cimAdmin, RebatesConstants.draft);
		response = cimAdmin.deleteLinkTemplate(RebatesConstants.responseNocontent);	
		
		cimAdminHelper.createAndValidateTemplate(cimAdmin, qnbLayoutId);
		cimAdminHelper.mapDataSourceAndFormulaToTemplate(cimAdmin);		
		cimAdmin.activateTemplate(RebatesConstants.responseNocontent);
		
		// ---- Create and Activate link template -----
		linkTemplateDataFromJson = "autoNewLinkTemplateSubTypeTieredAPI";
		cimAdminHelper.createAndValidateLinkTemplate(cimAdmin, linkTemplateDataFromJson);
		
		cimAdminHelper.activateAndVerifyLinkTemplate(cimAdmin);
		
		// ---- Deactivate template, will automatically deactivates the Link Template -----
		cimAdmin.deactivateTemplate();
		response = cimAdmin.getTemplate();		
		
		responseValidator.validateTemplateStatus(response, cimAdmin, RebatesConstants.deactivate);
		response = cimAdmin.getLinkTemplatesViaId();
		responseValidator.validateLinkTemplatesStatus(response, cimAdmin, RebatesConstants.deactivate);
	}
	
	@Test(description = "TC-410 Unable to Delete Link Template which is in Active or Inactive Status from spillover menu", groups = {
			"Regression", "Medium", "API" })
	public void deleteActivateLinkTemplate() throws Exception {

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "benefitOnlyTieredQnBLayoutAPI");
		String qnbLayoutId = cimAdmin.getQnBLayoutId(jsonData);
		cimAdminHelper.templateActivationForLinkTemplate(cimAdmin, qnbLayoutId);
		String linkTemplateDataFromJson = "autoNewLinkTemplateSubTypeDiscreteAPI";
		cimAdminHelper.createAndValidateLinkTemplate(cimAdmin, linkTemplateDataFromJson);		
		cimAdminHelper.activateAndVerifyLinkTemplate(cimAdmin);
		response = cimAdmin.deleteLinkTemplate(RebatesConstants.responseBadRequest);
		responseValidator.validateFailureResponse(response, RebatesConstants.errorCodeCustomValidation,
				RebatesConstants.messageDeleteActiveInactiveLinkTemplate);		
		cimAdminHelper.deactivateAndVerifyLinkTemplate(cimAdmin);		
		response = cimAdmin.deleteLinkTemplate(RebatesConstants.responseBadRequest);
		responseValidator.validateFailureResponse(response, RebatesConstants.errorCodeCustomValidation,
				RebatesConstants.messageDeleteActiveInactiveLinkTemplate);
	}

	@Test(description = "TC-284 Verify the linking of active templates when the link status of the previously linked templates is Draft", groups = {
			"Regression", "Medium", "API" })
	public void addInactiveLinkTemplateAgain() throws Exception {

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "benefitOnlyTieredQnBLayoutAPI");
		String qnbLayoutId = cimAdmin.getQnBLayoutId(jsonData);
		cimAdminHelper.templateActivationForLinkTemplate(cimAdmin, qnbLayoutId);
		
		String linkTemplateDataFromJson = "autoNewLinkTemplateSubTypeTieredAPI";
		cimAdminHelper.createAndValidateLinkTemplate(cimAdmin, linkTemplateDataFromJson);	
		
		linkTemplateDataFromJson = "autoNewLinkTemplateSubTypeTieredAPI";
		cimAdminHelper.createAndValidateLinkTemplate(cimAdmin, linkTemplateDataFromJson);	
		
		cimAdminHelper.activateAndVerifyLinkTemplate(cimAdmin);
		cimAdminHelper.deactivateAndVerifyLinkTemplate(cimAdmin);		
		
		cimAdminHelper.createAndValidateTemplate(cimAdmin, qnbLayoutId);
		cimAdminHelper.mapDataSourceAndFormulaToTemplate(cimAdmin);

		cimAdmin.activateTemplate(RebatesConstants.responseNocontent);
		linkTemplateDataFromJson = "autoNewLinkTemplateSubTypeTieredAPI";
		cimAdminHelper.createAndValidateLinkTemplate(cimAdmin, linkTemplateDataFromJson);
		
		cimAdminHelper.activateAndVerifyLinkTemplate(cimAdmin);
		response = cimAdmin.draftLinkTemplateFailure();
		responseValidator.validateFailureResponse(response, RebatesConstants.errorCodeCustomValidation,
				RebatesConstants.messageChangeLinkTemplateStatusToDraft);
		
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "autoNewLinkTemplateSubTypeTieredAPI");
		response = cimAdmin.createLinkTemplatesFailure(jsonData);
		responseValidator.validateFailureResponse(response, RebatesConstants.errorCodeCustomValidation,
				RebatesConstants.messageActiveMappingExists);
		cimAdminHelper.deactivateAndVerifyLinkTemplate(cimAdmin);
	}
	
	@Test(description = "TC-446 Verify Link Template behavior in different Status", groups = { "Regression", "Low",
			"API" })
	public void deactivateTemplateWillNotAllowLinkTemplateActivation() throws Exception {

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "benefitOnlyTieredQnBLayoutAPI");
		String qnbLayoutId = cimAdmin.getQnBLayoutId(jsonData);
		cimAdminHelper.templateActivationForLinkTemplate(cimAdmin, qnbLayoutId);

		String linkTemplateDataFromJson = "autoNewLinkTemplateSubTypeDiscreteAPI";
		cimAdminHelper.createAndValidateLinkTemplate(cimAdmin, linkTemplateDataFromJson);

		// ---- Deactivate Template of LinkTemplate -----
		cimAdmin.deactivateTemplate();
		response = cimAdmin.getTemplate();
		responseValidator.validateTemplateStatus(response, cimAdmin, RebatesConstants.deactivate);

		// ---- LinkTemplate activation fails when template is inactive -----
		response = cimAdmin.activateLinkTemplateViaId(cimAdmin.getLinkTemplatesData().linkTemplateId,
				RebatesConstants.responseBadRequest);
		responseValidator.validateFailureResponse(response, RebatesConstants.errorCodeCustomValidation,
				RebatesConstants.messageActivateLinkTemplateForInactiveTemplate);
	}
}
