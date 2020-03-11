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
import com.apttus.sfdc.rebates.lightning.generic.utils.RebatesConstants;
import com.apttus.sfdc.rebates.lightning.main.UnifiedFramework;
import com.apttus.sfdc.rebates.lightning.ui.library.DataSourcePage;
import com.apttus.sfdc.rebates.lightning.ui.library.HomePage;
import com.apttus.sfdc.rebates.lightning.ui.library.LoginPage;

public class TestDataSourceUI extends UnifiedFramework {

	WebDriver driver;
	LoginPage loginPage;
	public DataSourcePage dataSourcePage;
	public HomePage homepage;
	public Properties configProperty;
	Efficacies efficacies;
	private Map<String, String> jsonData;
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
		homepage = new HomePage(driver, configProperty);
		homepage.navigateToCIMAdmin();
		softassert = new SoftAssert();
	}

	@Test(description = "TC216-Verify the Data Source Validation", groups = { "Regression1", "Low", "UI" })
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

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		driver.manage().deleteAllCookies();
		driver.quit();
	}
}
