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
	public TemplatePage templatePage;
	public HomePage homepage;
	public Properties configProperty;
	Efficacies efficacies;
	private CIMAdmin cimAdmin;
	private Map<String, String> jsonData;
	private SFDCRestUtils sfdcRestUtils;
	private String instanceURL;
	public String baseURL;
	SoftAssert softassert;
	GenericPage genericPage;
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
		genericPage = new GenericPage(driver);
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() throws Exception {

		softassert = new SoftAssert();
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
		templatePage = homepage.navigateToEditTemplate(cimAdmin.templateData.getTemplateId());
		genericPage.waitTillPageContentLoad();
	    genericPage.clickButton(templatePage.ddlQBselect);
	    genericPage.clickButton(templatePage.ddlBenefitProductDetails);
	    templatePage.addDataSource(cimAdmin);
	  
		softassert.assertEquals(false, templatePage.chkQualificationValue.isEmpty());
		softassert.assertEquals(false, templatePage.chkBenefitFormulaValue.isEmpty());

		templatePage.selectTier( templatePage.ddlDiscreteValue);
		softassert.assertEquals(false, templatePage.chkBenefitFormulaValue.isEmpty());
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

		templatePage = homepage.navigateToTemplates();
		templatePage.moveToTemplateViaIdClick(cimAdmin.getTemplateData().getTemplateId());
		softassert.assertTrue(templatePage.templateEditURL, "Verify the URL of Template Edit page");
		softassert.assertAll();
	}

	@Test(description = "TC-562 Verify creation and Activation of QnB and Tier template Via Details View page", groups = {
			"Regression", "Medium", "UI" })
	public void verifyTemplateTierQualificationBenefit() throws Exception {

		cimAdminHelper.createDataSourceAndFormulasForTiered(cimAdmin);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "qualificationAndBenefitTieredQnBLayoutAPI");
		String qnbLayoutId = cimAdmin.getQnBLayoutId(jsonData);
		cimAdminHelper.createAndValidateTemplate(cimAdmin, qnbLayoutId);
		cimAdminHelper.mapDataSourceAndFormulaToTemplateTiered(cimAdmin);

		templatePage = homepage.navigateToEditTemplateView(cimAdmin.templateData.getTemplateId());
		genericPage.clickButtonAndWait(templatePage.btnActive, genericPage.txtToastMessage);
		softassert.assertEquals(RebatesConstants.messageActivateSuccessfully, genericPage.txtToastMessage.getText());
		softassert.assertAll();
	}

	@Test(description = "TC 566 Verify Activation of Multiple Benefit Product and Discrete Template via Detail View Page", groups = {
			"Regression", "Medium", "UI" })
	public void verifyTemplateMultipleQualificationDiscreteActivationViaDetailView() throws Exception {

		cimAdminHelper.createDataSourceAndFormulasForTiered(cimAdmin);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json",
				"multipleQualificationAndBenefitDiscreteQnBLayoutAPI");
		String qnbLayoutId = cimAdmin.getQnBLayoutId(jsonData);
		cimAdminHelper.createAndValidateTemplate(cimAdmin, qnbLayoutId);
		cimAdminHelper.mapDataSourceAndFormulaToTemplateTiered(cimAdmin);

		templatePage = homepage.navigateToEditTemplateView(cimAdmin.templateData.getTemplateId());
		genericPage.clickButtonAndWait(templatePage.btnActive, genericPage.txtToastMessage);
		softassert.assertEquals(RebatesConstants.messageActivateSuccessfully, genericPage.txtToastMessage.getText());
		softassert.assertAll();
	}

	@Test(description = "TC557-Verify Benefit Product and Discrete  Template Activation via list view", groups = {
			"Regression", "UI", "High" })
	public void verifyBenefitProductTemplateActivationViaListViewPage() throws Exception {

		cimAdminHelper.createDataSourceAndFormulasForTiered(cimAdmin);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "benefitOnlyDiscreteQnBLayoutAPI");
		String qnbLayoutId = cimAdmin.getQnBLayoutId(jsonData);
		cimAdminHelper.createAndValidateTemplate(cimAdmin, qnbLayoutId);
		cimAdminHelper.mapDataSourceAndFormulaToTemplateTiered(cimAdmin);

		templatePage = homepage.navigateToTemplates();
		templatePage.searchTemplateName(cimAdmin.getTemplateData().getName());
		templatePage.changeInLineStatus();
		softassert.assertEquals(RebatesConstants.messageChangesSuccessfull, genericPage.txtToastMessage.getText());
		softassert.assertAll();
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		driver.quit();
	}
}
