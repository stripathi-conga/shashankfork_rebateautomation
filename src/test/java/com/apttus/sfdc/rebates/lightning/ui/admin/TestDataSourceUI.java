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
import com.apttus.sfdc.rebates.lightning.common.GenericPage;
import com.apttus.sfdc.rebates.lightning.generic.utils.CIMAdminHelper;
import com.apttus.sfdc.rebates.lightning.generic.utils.DataHelper;
import com.apttus.sfdc.rebates.lightning.generic.utils.RebatesConstants;
import com.apttus.sfdc.rebates.lightning.generic.utils.SFDCHelper;
import com.apttus.sfdc.rebates.lightning.main.UnifiedFramework;
import com.apttus.sfdc.rebates.lightning.ui.library.DataSourcePage;
import com.apttus.sfdc.rebates.lightning.ui.library.HomePage;
import com.apttus.sfdc.rebates.lightning.ui.library.LoginPage;
import com.apttus.sfdc.rudiments.utils.SFDCRestUtils;

public class TestDataSourceUI extends UnifiedFramework{

	WebDriver driver;
	LoginPage loginPage;
	public Map<String, String> testData;
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
	public DataSourcePage dataSourcePage;

	@BeforeClass(alwaysRun = true)
	@Parameters({ "runParallel", "environment", "browser", "hubURL" })
	public void setUp(String runParallel, String environment, String browser, String hubURL) throws Exception {
		DataHelper obj= DataHelper.getInstanceOfDataHelper();
		obj.getData(environment);
		
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
		cimAdminHelper = new CIMAdminHelper();
		genericPage = new GenericPage(driver);
	}

	@Test(description = "TC216-Verify the Data Source Validation", groups = { "Regression", "Low", "UI" })
	public void verifyDataSourceValidations() throws Exception {

		dataSourcePage = homepage.navigateToNewDataSource();
		dataSourcePage.clickSave();
		softassert.assertEquals(dataSourcePage.txtToastMessage.getText(), RebatesConstants.messageMandatoryDataSource,
				"Verify DataSource Name");

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewDataSourceAccountLocation");

		dataSourcePage.verifyValidationMessageForTransactionLineObject();
		softassert.assertEquals(dataSourcePage.txtToastMessage.getText(),
				RebatesConstants.messageMandatoryTransactionLineObject, "Verify Transaction Line Object");

		dataSourcePage.verifyValidationMessageForCalculationDate();
		softassert.assertEquals(dataSourcePage.txtToastMessage.getText(),
				RebatesConstants.messageMandatoryCalculationDate, "Verify Calculation Date Field");

		dataSourcePage.verifyValidationMessageForProduct();
		softassert.assertEquals(dataSourcePage.txtToastMessage.getText(), RebatesConstants.messageMandatoryProduct,
				"Verify Product Field");

		dataSourcePage.verifyValidationMessageForIncentiveAccount();
		softassert.assertEquals(dataSourcePage.txtToastMessage.getText(),
				RebatesConstants.messageMandatoryIncentiveAccount, "Verify Incentive Account Field");

		dataSourcePage.verifyValidationMessageForFileSuffixToIgnore();
		softassert.assertEquals(dataSourcePage.txtToastMessage.getText(), RebatesConstants.messageMandatoryFileSuffix,
				"Verify File Suffix To Ignore");

		dataSourcePage.verifyValidationMessageForFileExtension(jsonData);
		softassert.assertEquals(dataSourcePage.txtToastMessage.getText(),
				RebatesConstants.messageMandatoryFileExtension, "Verify File Extension");

		dataSourcePage.verifyValidationMessageForRecordDelimter();
		softassert.assertEquals(dataSourcePage.txtToastMessage.getText(),
				RebatesConstants.messageMandatoryRecordDelimter, "Verify Record Delimter");
		softassert.assertAll();
	}

	@Test(description = "TC-584 Verify data on the Related sub-tab on the data source details page", groups = {
			"Regression", "Medium", "UI" })
	public void verifyDataSourceMappedFormula() throws Exception {

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "benefitOnlyTieredQnBLayoutAPI");
		String qnbLayoutId = cimAdmin.getQnBLayoutId(jsonData);
		cimAdminHelper.createAndValidateTemplate(cimAdmin, qnbLayoutId);
		cimAdminHelper.mapDataSourceAndFormulaToTemplate(cimAdmin);

		dataSourcePage = homepage.navigateToEditDataSource(DataHelper.getIncentiveDataSourceId());
		genericPage.moveToFormulaTab(DataHelper.getFormulaDataMap().get("volumeCalcFormulaIdQualificationNonStep"),
				DataHelper.getFormulaDataMap().get("amtPerUnitCalcFormulaIdBenefitNonStep"),
				dataSourcePage.lblRelatedTab);
		genericPage.moveToFormulaTab(DataHelper.getFormulaDataMap().get("revBasedCalcFormulaIdQualificationNonStep"),
				DataHelper.getFormulaDataMap().get("revPerctCalcFormulaIdBenefitNonStep"),
				dataSourcePage.lblRelatedTab);
		softassert.assertAll();
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	  
}
