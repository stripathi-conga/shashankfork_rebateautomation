package com.apttus.sfdc.rebates.lightning.ui.admin;

import java.util.Map;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.apttus.helpers.Efficacies;
import com.apttus.selenium.WebDriverUtils;
import com.apttus.sfdc.rebates.lightning.api.library.CIMAdmin;
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
	SoftAssert softassert;

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
		homepage = new HomePage(driver);
		sfdcRestUtils = new SFDCRestUtils();
		SFDCHelper.setMasterProperty(configProperty);
		instanceURL = SFDCHelper.setAccessToken(sfdcRestUtils);
		cimAdmin = new CIMAdmin(instanceURL, sfdcRestUtils);
	}

	@Test(description = "TC-463 Verify  Qualification formulas on the Benefit only templates",groups = { "Regression",
			"Medium", "UI" })
	public void verifyTemplateQualificationOnDiscrete() throws Exception {
		try {

			softassert = new SoftAssert();
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
			cimAdmin.getQnBLayoutId(jsonData);
			jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json",
					"createNewLinkTemplateSubTypeDiscreteAPI");
			templatepage = homepage.navigateToTemplates(configProperty);
			templatepage.moveToTemplate(templatepage.Newbtn);
			templatepage.qnbLayoutDefinition(templatepage.QBselct, templatepage.BenftPrdtValue, templatepage.TierSelect,
					templatepage.TierDiscrete);
			templatepage.addQualificationOnDiscrete(cimAdmin);
			softassert.assertEquals(RebatesConstants.messagequalificationformulavalidation,
					templatepage.txtNodatadisplay.get(0).getText());
			templatepage.addQualificationOnTiered(cimAdmin);
			
			softassert.assertAll();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		driver.quit();
	}
}
