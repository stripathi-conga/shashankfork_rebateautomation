package com.apttus.sfdc.rebates.lightning.ui.admin;

import java.util.List;
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
import com.apttus.sfdc.rebates.lightning.common.GenericPage;
import com.apttus.sfdc.rebates.lightning.generic.utils.RebatesConstants;
import com.apttus.sfdc.rebates.lightning.generic.utils.SFDCHelper;
import com.apttus.sfdc.rebates.lightning.ui.library.AuditLogsPage;
import com.apttus.sfdc.rebates.lightning.ui.library.HomePage;
import com.apttus.sfdc.rebates.lightning.ui.library.LoginPage;

public class TestAuditLogsUI {
	public WebDriver driver;
	public LoginPage loginPage;
	public Map<String, String> testData;
	public HomePage homepage;
	public Properties configProperty;
	public Efficacies efficacies;
	public String baseURL;
	private SoftAssert softassert;
	private Map<String, String> jsonData;
	public GenericPage genericPage;
	public AuditLogsPage auditLogsPage;

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
		SFDCHelper.setMasterProperty(configProperty);
		softassert = new SoftAssert();
		genericPage = new GenericPage(driver);
		auditLogsPage = new AuditLogsPage(driver);
	}

	@Test(description = "TC529 - Verify file audit log data shows up in file audit log grid view", groups = {
			"Regression", "Medium", "UI" })
	public void verifyFileIngestionAuditLogGridView() throws Exception {

		auditLogsPage = homepage.navigatetoFileIngestionAuditLogsListView(RebatesConstants.listViewAll);
		jsonData = efficacies.readJsonElement("CIMAdminUIData.json", "fileAuditLogsHeaders");
		List<String> headerValues = SFDCHelper.convertStringToList(jsonData.get("Headers"));
		softassert.assertEquals(auditLogsPage.verifyHeaders(headerValues), true,
				"Verify File Audit Logs Page Header");
		softassert.assertEquals(auditLogsPage.getGridViewData(), true,
				"Verify the data is availabe on Audit Logs Page");
		softassert.assertAll();
	}
	
	@Test(description = "TC-600 Verify the Calculation Engine Audit Logs Grid View", groups = {
			"Regression", "Medium", "UI" })
	public void verifyCalculationEngineAuditLogGridView() throws Exception {

		auditLogsPage = homepage.navigatetoCalculationEngineAuditLogsListView(RebatesConstants.listViewAll);
		jsonData = efficacies.readJsonElement("CIMAdminUIData.json", "fileAuditLogsHeaders");
		List<String> headerValues = SFDCHelper.convertStringToList(jsonData.get("Headers"));
		softassert.assertEquals(auditLogsPage.verifyHeaders(headerValues), true,
				"Verify Calculation Engine Audit Logs Page Header");
		softassert.assertEquals(auditLogsPage.getGridViewData(), true,
				"Verify the data is availabe on Calculation Engine Audit Logs Page");
		softassert.assertAll();
	}
	
	@AfterClass(alwaysRun = true)
	public void tearDown() {
		driver.manage().deleteAllCookies();
		driver.quit();
	}
}
