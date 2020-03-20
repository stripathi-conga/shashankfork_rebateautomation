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
import com.apttus.sfdc.rebates.lightning.generic.utils.RebatesConstants;
import com.apttus.sfdc.rebates.lightning.generic.utils.SFDCHelper;
import com.apttus.sfdc.rebates.lightning.main.UnifiedFramework;
import com.apttus.sfdc.rebates.lightning.ui.library.DataSourcePage;
import com.apttus.sfdc.rebates.lightning.ui.library.HomePage;
import com.apttus.sfdc.rebates.lightning.ui.library.LoginPage;
import com.apttus.sfdc.rudiments.utils.SFDCRestUtils;

public class TestDataSourceUI extends UnifiedFramework {

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
		softassert.assertEquals(RebatesConstants.messageMandatoryDataSource, dataSourcePage.txtToastMessage.getText());

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewDataSourceAPI");
		dataSourcePage.verifyValidationMessageForTransactionLineObject();

		softassert.assertEquals(RebatesConstants.messageMandatoryTransactionLineObject,
				dataSourcePage.txtToastMessage.getText());
		dataSourcePage.verifyValidationMessageForCalculationDate();
		softassert.assertEquals(RebatesConstants.messageMandatoryCalculationDate,
				dataSourcePage.txtToastMessage.getText());
		dataSourcePage.verifyValidationMessageForProduct();
		softassert.assertEquals(RebatesConstants.messageMandatoryProduct, dataSourcePage.txtToastMessage.getText());
		dataSourcePage.verifyValidationMessageForIncentiveAccount();
		softassert.assertEquals(RebatesConstants.messageMandatoryIncentiveAccount,
				dataSourcePage.txtToastMessage.getText());
		dataSourcePage.verifyValidationMessageForFileSuffixToIgnore();
		softassert.assertEquals(RebatesConstants.messageMandatoryFileSuffix, dataSourcePage.txtToastMessage.getText());
		dataSourcePage.verifyValidationMessageForFileExtension(jsonData);
		softassert.assertEquals(RebatesConstants.messageMandatoryFileExtension,
				dataSourcePage.txtToastMessage.getText());
		dataSourcePage.verifyValidationMessageForRecordDelimter();
		softassert.assertEquals(RebatesConstants.messageMandatoryRecordDelimter,
				dataSourcePage.txtToastMessage.getText());

		softassert.assertAll();
	}

	@Test(description = "TC-584 Verify data on the Related sub-tab on the data source details page", groups = {
			"Regression", "Medium", "UI" })
	public void verifyDataSourceMappedFormula() throws Exception {

		cimAdminHelper.createDataSourceAndFormulasForTiered(cimAdmin);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "benefitOnlyTieredQnBLayoutAPI");
		String qnbLayoutId = cimAdmin.getQnBLayoutId(jsonData);
		cimAdminHelper.createAndValidateTemplate(cimAdmin, qnbLayoutId);
		cimAdminHelper.mapDataSourceAndFormulaToTemplateTiered(cimAdmin);

		dataSourcePage = homepage.navigateToEditDataSource(cimAdmin.dataSourceData.getDataSourceId());
		genericPage.moveToFormulaTab(cimAdminHelper.stepCalcFormulaIdQualification,
				cimAdminHelper.stepCalcFormulaIdBenefit, dataSourcePage.lblRelatedTab);
		genericPage.moveToFormulaTab(cimAdminHelper.nonStepCalcFormulaIdBenefit,
				cimAdminHelper.nonStepCalcFormulaIdQualification, dataSourcePage.lblRelatedTab);
		softassert.assertAll();
	}

	@Test(description = "TC-572 Data Source Details records should display as Name", groups = { "Regression0", "Low",
			"UI" })
	public void verifyDataSourceFieldsNameInDetails() throws Exception {

		cimAdminHelper.createDataSourceAndFormulasForTiered(cimAdmin);
		dataSourcePage = homepage.navigateToEditDataSource(cimAdmin.dataSourceData.getDataSourceId());
		dataSourcePage.VerifyTillAllElementLoaded(cimAdmin.getDataSourceData().getTransactionLineObjectName__c());

		dataSourcePage.VerifyTillAllElementLoaded(cimAdmin.getDataSourceData().getFileExtension__c());
		dataSourcePage.VerifyTillAllElementLoaded(cimAdmin.getDataSourceData().getCalculationDateName__c());
		dataSourcePage.VerifyTillAllElementLoaded(cimAdmin.getDataSourceData().getIncentiveAccountFieldName__c());
		dataSourcePage.VerifyTillAllElementLoaded(cimAdmin.getDataSourceData().getProductFieldName__c());
		dataSourcePage.VerifyTillAllElementLoaded(cimAdmin.getDataSourceData().getFileSuffixToignore__c());
		dataSourcePage.VerifyTillAllElementLoaded(cimAdmin.getDataSourceData().getRecordDelimiterName__c());

		softassert.assertEquals(false, dataSourcePage.transactionLineObjectPath.isEmpty());
		softassert.assertEquals(false, dataSourcePage.fileExtensionPath.isEmpty());
		softassert.assertEquals(false, dataSourcePage.calculationDatePath.isEmpty());
		softassert.assertEquals(false, dataSourcePage.incentiveAccountPath.isEmpty());
		softassert.assertEquals(false, dataSourcePage.productFieldPath.isEmpty());
		softassert.assertEquals(false, dataSourcePage.fileSuffixtoIgnoretPath.isEmpty());
		softassert.assertEquals(false, dataSourcePage.recordDelimitertPath.isEmpty());
		
		softassert.assertEquals(RebatesConstants.lblTransactionLineObject, dataSourcePage.lblTransactionLineObject.getText());
		softassert.assertEquals(RebatesConstants.lblFileExtension, dataSourcePage.lblFileExtension.getText());
		softassert.assertEquals(RebatesConstants.lblFileSuffixToIgnore, dataSourcePage.lblFileSuffixToIgnore.getText());
		softassert.assertEquals(RebatesConstants.lblCalculationDate, dataSourcePage.lblCalculationDate.getText());
		softassert.assertEquals(RebatesConstants.lblRecordDelimiter, dataSourcePage.lblRecordDelimiter.getText());
		softassert.assertEquals(RebatesConstants.lblProductField, dataSourcePage.lblProductField.getText());
		softassert.assertEquals(RebatesConstants.lblIncentiveAccountField, dataSourcePage.lblIncentiveAccountField.getText());
		softassert.assertAll();
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		driver.manage().deleteAllCookies();
		driver.quit();
	}
}
