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
import com.apttus.sfdc.rebates.lightning.common.GenericPage;
import com.apttus.sfdc.rebates.lightning.generic.utils.CIMAdminHelper;
import com.apttus.sfdc.rebates.lightning.generic.utils.RebatesConstants;
import com.apttus.sfdc.rebates.lightning.generic.utils.SFDCHelper;
import com.apttus.sfdc.rebates.lightning.main.UnifiedFramework;
import com.apttus.sfdc.rebates.lightning.ui.library.HomePage;
import com.apttus.sfdc.rebates.lightning.ui.library.LoginPage;
import com.apttus.sfdc.rebates.lightning.ui.library.TemplatePage;
import com.apttus.sfdc.rudiments.utils.SFDCRestUtils;

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
	public GenericPage genericPage;
	private CIMAdminHelper cimAdminHelper;

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
		genericPage = new GenericPage(driver);
		cimAdminHelper = new CIMAdminHelper();
	}

	@Test(description = "TC-463 Verify  Qualification formulas on the Benefit only templates", groups = { "Regression",
			"Medium", "UI" })
	public void verifyTemplateQualificationOnDiscrete() throws Exception {

		cimAdminHelper.createDataSourceAndFormulasForTiered(cimAdmin);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "benefitOnlyTieredQnBLayoutAPI");
		String qnbLayoutId = cimAdmin.getQnBLayoutId(jsonData);
		cimAdminHelper.createAndValidateTemplate(cimAdmin, qnbLayoutId);
		cimAdminHelper.mapDataSourceAndFormulaToTemplateTiered(cimAdmin);				
		
		templatepage = homepage.navigateToEditTemplate(cimAdmin.templateData.getTemplateId());
		templatepage.moveToEditTemplate(cimAdmin.getTemplateData().getTemplateId());

		templatepage.addQualificationOnTiered(cimAdmin);
		softassert.assertEquals(false, templatepage.chkQualificationValue.isEmpty());
		softassert.assertEquals(false, templatepage.chkBenefitFormulaValue.isEmpty());

		templatepage.qnbLayoutDefinition(templatepage.ddlQBselect, templatepage.ddlTierSelect);
		softassert.assertEquals(false, templatepage.chkBenefitFormulaValue.isEmpty());
		softassert.assertAll();
	}

	@Test(description = "TC 555-Validate activation of new Benefit formula Discrete template and its navigation via Template Name", groups = {
			"Regression", "UI", "High" })
	public void verifyTemplateActivationAndNavigationViaTemplateName() throws Exception {

		cimAdminHelper.createDataSourceAndFormulasForTiered(cimAdmin);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "benefitOnlyTieredQnBLayoutAPI");
		String qnbLayoutId = cimAdmin.getQnBLayoutId(jsonData);
		cimAdminHelper.createAndValidateTemplate(cimAdmin, qnbLayoutId);
		cimAdminHelper.mapDataSourceAndFormulaToTemplateTiered(cimAdmin);		
		cimAdmin.activateTemplate(RebatesConstants.responseNocontent);

		templatepage = homepage.navigateToTemplates();
		templatepage.moveToTemplateViaIdClick(cimAdmin.getTemplateData().getTemplateId());
		softassert.assertTrue(templatepage.templateEditURL, "Verify the URL of Template Edit page");
		softassert.assertAll();
	}

	@Test(description = "TC-565 Verify creation and Activation of Multiple Qualification & Benefit and Tier", groups = {
			"Regression", "Medium", "UI" })
	public void verifyTemplateTierMultipleQualification() throws Exception {

		cimAdminHelper.createDataSourceAndFormulasForTiered(cimAdmin);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json",
				"multipleQualificationAndBenefitTieredQnBLayoutAPI");
		String qnbLayoutId = cimAdmin.getQnBLayoutId(jsonData);		
		cimAdminHelper.createAndValidateTemplate(cimAdmin, qnbLayoutId);
		
		templatepage = homepage.navigateToEditTemplate(cimAdmin.templateData.getTemplateId());
		templatepage.addDataSource(cimAdmin);
		templatepage.addQualificationOnTiered(cimAdmin);
		templatepage.selectQualificationAndBenefitFormula();
		genericPage.clickButtonAndWait(templatepage.btnSave, genericPage.txtToastMessage);
		softassert.assertEquals(RebatesConstants.messageSavedSuccessfully, genericPage.txtToastMessage.getText());
		genericPage.clickButton(genericPage.btnCloseToastMessage);
		templatepage = homepage.navigateToEditTemplateView(cimAdmin.templateData.getTemplateId());
		genericPage.clickButtonAndWait(templatepage.btnActive, genericPage.txtToastMessage);
		softassert.assertEquals(RebatesConstants.messageActivateSuccessfully, genericPage.txtToastMessage.getText());
		softassert.assertAll();
	}

	@Test(description = "TC 566 Verify Activation of Multiple Benefit Product and Discrete Template via Detail View Page", groups = { "Regression",
			"Medium", "UI" })
	public void verifyTemplateTierMultipleQualificationActivationViaDetailView() throws Exception {

		cimAdminHelper.createDataSourceAndFormulasForTiered(cimAdmin);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json",
				"multipleQualificationAndBenefitTieredQnBLayoutAPI");
		String qnbLayoutId = cimAdmin.getQnBLayoutId(jsonData);		
		cimAdminHelper.createAndValidateTemplate(cimAdmin, qnbLayoutId);
		cimAdminHelper.mapDataSourceAndFormulaToTemplateTiered(cimAdmin);
		
		templatepage = homepage.navigateToEditTemplateView(cimAdmin.templateData.getTemplateId());
		genericPage.clickButtonAndWait(templatepage.btnActive, genericPage.txtToastMessage);
		softassert.assertEquals(RebatesConstants.messageActivateSuccessfully, genericPage.txtToastMessage.getText());
		softassert.assertAll();
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		driver.quit();
	}
}
