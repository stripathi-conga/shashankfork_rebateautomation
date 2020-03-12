package com.apttus.sfdc.rebates.lightning.ui.admin;

import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.apttus.helpers.Efficacies;
import com.apttus.selenium.WebDriverUtils;
import com.apttus.sfdc.rebates.lightning.api.library.CIMAdmin;
import com.apttus.sfdc.rebates.lightning.api.validator.ResponseValidatorBase;
import com.apttus.sfdc.rebates.lightning.generic.utils.RebatesConstants;
import com.apttus.sfdc.rebates.lightning.generic.utils.SFDCHelper;
import com.apttus.sfdc.rebates.lightning.main.UnifiedFramework;
import com.apttus.sfdc.rebates.lightning.ui.library.HomePage;
import com.apttus.sfdc.rebates.lightning.ui.library.LoginPage;
import com.apttus.sfdc.rebates.lightning.ui.library.TemplatePage;
import com.apttus.sfdc.rudiments.utils.SFDCRestUtils;
import com.jayway.restassured.response.Response;

public class TestTemplateUI extends UnifiedFramework {

	WebDriver driver;
	LoginPage loginPage;
	public Map<String, String> testData;
	public TemplatePage templatepage;
	public HomePage homepage;
	public Properties configProperty;
	Efficacies efficacies;
	private CIMAdmin cimAdmin;
	private Map<String, String> jsonData;
	private SFDCRestUtils sfdcRestUtils;
	private String instanceURL;
	public String baseURL;
	SoftAssert softassert;
	private ResponseValidatorBase responseValidator;
	private Response response;

	@BeforeClass(alwaysRun = true)
	@Parameters({ "runParallel", "environment", "browser", "hubURL" })
	public void setUp(String runParallel, String environment, String browser, String hubURL) throws Exception {

		WebDriverUtils utils = new WebDriverUtils();
		utils.initializeDriver(browser, hubURL);
		driver = utils.getDriver();
		efficacies = new Efficacies();
		loginPage = new LoginPage(driver);
		configProperty = efficacies.loadPropertyFile(environment);
		loginPage = loginPage.navigateToLoginPage(configProperty.getProperty("LoginURL"));
		loginPage.waitForLoginPageLoad().loginToApp(configProperty.getProperty("LoginUser"),
				configProperty.getProperty("LoginPassword"));
		homepage = new HomePage(driver, configProperty);
		homepage.navigateToCIMAdmin();
		sfdcRestUtils = new SFDCRestUtils();
		SFDCHelper.setMasterProperty(configProperty);
		instanceURL = SFDCHelper.setAccessToken(sfdcRestUtils);
		cimAdmin = new CIMAdmin(instanceURL, sfdcRestUtils);
		softassert = new SoftAssert();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() throws Exception {
		responseValidator = new ResponseValidatorBase();
	}

	@Test(description = "TC-463 Verify  Qualification formulas on the Benefit only templates", groups = { "Regression",
			"Medium", "UI" })
	public void verifyTemplateQualificationOnDiscrete() throws Exception {

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createFieldExpressionId");
		String fieldExpressionId = cimAdmin.getFieldExpressionId(jsonData);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createStepCalcFormulaIdBenefit");
		String stepCalcFormulaIdBenefit = cimAdmin.getCalcFormulaId(jsonData);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNonStepCalcFormulaIdBenefit");
		String nonStepCalcFormulaIdBenefit = cimAdmin.getCalcFormulaId(jsonData);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createStepCalcFormulaIdQualification");
		String stepCalcFormulaIdQualification = cimAdmin.getCalcFormulaId(jsonData);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNonStepCalcFormulaIdQualification");
		String nonStepCalcFormulaIdQualification = cimAdmin.getCalcFormulaId(jsonData);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "linkCalcFormulaToExpressionId");
		cimAdmin.linkCalcFormulaToExpression(jsonData, stepCalcFormulaIdBenefit, fieldExpressionId);
		cimAdmin.linkCalcFormulaToExpression(jsonData, nonStepCalcFormulaIdBenefit, fieldExpressionId);
		cimAdmin.linkCalcFormulaToExpression(jsonData, stepCalcFormulaIdQualification, fieldExpressionId);
		cimAdmin.linkCalcFormulaToExpression(jsonData, nonStepCalcFormulaIdQualification, fieldExpressionId);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewDataSourceAPI");
		cimAdmin.createDataSource(jsonData);
		cimAdmin.linkDatasourceToCalcFormula(stepCalcFormulaIdBenefit);
		cimAdmin.linkDatasourceToCalcFormula(nonStepCalcFormulaIdBenefit);
		cimAdmin.linkDatasourceToCalcFormula(stepCalcFormulaIdQualification);
		cimAdmin.linkDatasourceToCalcFormula(nonStepCalcFormulaIdQualification);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "benefitOnlyTieredQnBLayoutAPI");
		String qnbLayoutId = cimAdmin.getQnBLayoutId(jsonData);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewTemplateAPI");
		response = cimAdmin.createTemplate(jsonData, qnbLayoutId);
		responseValidator.validateCreateSuccess(response);
		response = cimAdmin.getTemplate();
		responseValidator.validateGetTemplate(response, cimAdmin);
		jsonData.put("FormulaId__c", stepCalcFormulaIdBenefit);
		jsonData.put("DataSourceId__c", cimAdmin.getDataSourceData().getDataSourceId());
		cimAdmin.mapProgramTemplateDataSource(jsonData);
		jsonData.put("FormulaId__c", stepCalcFormulaIdQualification);
		jsonData.put("DataSourceId__c", cimAdmin.getDataSourceData().getDataSourceId());
		cimAdmin.mapProgramTemplateDataSource(jsonData);
		jsonData.put("FormulaId__c", nonStepCalcFormulaIdBenefit);
		jsonData.put("DataSourceId__c", cimAdmin.getDataSourceData().getDataSourceId());
		cimAdmin.mapProgramTemplateDataSource(jsonData);
		jsonData.put("FormulaId__c", nonStepCalcFormulaIdQualification);
		jsonData.put("DataSourceId__c", cimAdmin.getDataSourceData().getDataSourceId());
		cimAdmin.mapProgramTemplateDataSource(jsonData);
		responseValidator.validateTemplateStatus(response, cimAdmin, RebatesConstants.draft);
		templatepage = homepage.navigateToEditTemplate(cimAdmin.templateData.getTemplateId());
		templatepage.moveToEditTemplate(cimAdmin.getTemplateData().getTemplateId());

		templatepage.addQualificationOnTiered(cimAdmin);
		softassert.assertEquals(false, templatepage.chkQualificationValue.isEmpty());
		softassert.assertEquals(false, templatepage.chkBenefitFormulaValue.isEmpty());

		templatepage.qnbLayoutDefinition(templatepage.ddlQBselect, templatepage.ddlTierSelect);
		softassert.assertEquals(false, templatepage.chkBenefitFormulaValue.isEmpty());
		softassert.assertAll();
	}

	@Test(description = "TC263 - Verify that user is able to access all the template details by clicking on the Template ID from the Template List view", groups = {
			"Regression", "Medium", "UI" })
	public void verifyEditTemplateViaNameClick() throws Exception {

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createFieldExpressionId");
		String fieldExpressionId = cimAdmin.getFieldExpressionId(jsonData);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createStepCalcFormulaIdBenefit");
		String stepCalcFormulaIdBenefit = cimAdmin.getCalcFormulaId(jsonData);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNonStepCalcFormulaIdBenefit");
		String nonStepCalcFormulaIdBenefit = cimAdmin.getCalcFormulaId(jsonData);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createStepCalcFormulaIdQualification");
		String stepCalcFormulaIdQualification = cimAdmin.getCalcFormulaId(jsonData);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNonStepCalcFormulaIdQualification");
		String nonStepCalcFormulaIdQualification = cimAdmin.getCalcFormulaId(jsonData);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "linkCalcFormulaToExpressionId");
		cimAdmin.linkCalcFormulaToExpression(jsonData, stepCalcFormulaIdBenefit, fieldExpressionId);
		cimAdmin.linkCalcFormulaToExpression(jsonData, nonStepCalcFormulaIdBenefit, fieldExpressionId);
		cimAdmin.linkCalcFormulaToExpression(jsonData, stepCalcFormulaIdQualification, fieldExpressionId);
		cimAdmin.linkCalcFormulaToExpression(jsonData, nonStepCalcFormulaIdQualification, fieldExpressionId);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewDataSourceAPI");
		cimAdmin.createDataSource(jsonData);
		cimAdmin.linkDatasourceToCalcFormula(stepCalcFormulaIdBenefit);
		cimAdmin.linkDatasourceToCalcFormula(nonStepCalcFormulaIdBenefit);
		cimAdmin.linkDatasourceToCalcFormula(stepCalcFormulaIdQualification);
		cimAdmin.linkDatasourceToCalcFormula(nonStepCalcFormulaIdQualification);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "benefitOnlyTieredQnBLayoutAPI");
		String qnbLayoutId = cimAdmin.getQnBLayoutId(jsonData);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewTemplateAPI");
		response = cimAdmin.createTemplate(jsonData, qnbLayoutId);
		responseValidator.validateCreateSuccess(response);
		response = cimAdmin.getTemplate();
		responseValidator.validateGetTemplate(response, cimAdmin);
		jsonData.put("FormulaId__c", stepCalcFormulaIdBenefit);
		jsonData.put("DataSourceId__c", cimAdmin.getDataSourceData().getDataSourceId());
		cimAdmin.mapProgramTemplateDataSource(jsonData);
		jsonData.put("FormulaId__c", stepCalcFormulaIdQualification);
		jsonData.put("DataSourceId__c", cimAdmin.getDataSourceData().getDataSourceId());
		cimAdmin.mapProgramTemplateDataSource(jsonData);
		jsonData.put("FormulaId__c", nonStepCalcFormulaIdBenefit);
		jsonData.put("DataSourceId__c", cimAdmin.getDataSourceData().getDataSourceId());
		cimAdmin.mapProgramTemplateDataSource(jsonData);
		jsonData.put("FormulaId__c", nonStepCalcFormulaIdQualification);
		jsonData.put("DataSourceId__c", cimAdmin.getDataSourceData().getDataSourceId());
		cimAdmin.mapProgramTemplateDataSource(jsonData);
		responseValidator.validateTemplateStatus(response, cimAdmin, RebatesConstants.draft);
		cimAdmin.activateTemplate(RebatesConstants.responseNocontent);
		templatepage = homepage.navigateToTemplates();
		templatepage.moveToTemplateViaIdClick(cimAdmin.getTemplateData().getTemplateId());
		softassert.assertTrue(templatepage.templateEditURL, "Verify the template Edit Page navigation via Name");

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewTemplateAPI");
		softassert.assertEquals(jsonData.get("Description__c"), templatepage.lblDescriptionDetails.getText());
		softassert.assertEquals(cimAdmin.getDataSourceData().getName(), templatepage.lb1DataSourceDetails.getText());

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "benefitOnlyTieredQnBLayoutAPI");
		softassert.assertEquals(jsonData.get("type__c"), templatepage.lblBenefitProductDetails.getText());
		softassert.assertEquals(jsonData.get("tier__c"), templatepage.lblTierDetails.getText());
		softassert.assertEquals(jsonData.get("type__c"), templatepage.lblBenefitProductDetails.getText());
		templatepage.moveToFormulaTab(stepCalcFormulaIdBenefit, nonStepCalcFormulaIdBenefit);
		softassert.assertEquals(false, templatepage.lblStepQualificationFormulaPath.isEmpty());
		softassert.assertEquals(false, templatepage.lblStepBenefitFormulaIdPath.isEmpty());
		softassert.assertAll();
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		driver.quit();
	}
}
