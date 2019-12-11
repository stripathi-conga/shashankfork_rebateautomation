package com.apttus.sfdc.rebates.lightning.datasource;

import static org.testng.Assert.assertEquals;
import java.util.Map;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.apttus.customException.CustomException;
import com.apttus.helpers.Efficacies;
import com.apttus.selenium.WebDriverUtils;
import com.apttus.sfdc.rebates.lightning.ui.library.DataSourcePage;
import com.apttus.sfdc.rebates.lightning.ui.library.HomePage;
import com.apttus.sfdc.rebates.lightning.ui.library.LoginPage;
import com.apttus.sfdc.rudiments.utils.GeneralHelper;

public class DataSourceTest {

	WebDriver driver;
	LoginPage loginPage;
	public Map<String, String> testData;
	public DataSourcePage dataSourcePage;
	public HomePage homepage;
	Properties configProperty;

	@BeforeClass(alwaysRun = true)
	@Parameters({ "runParallel", "environment", "browser", "hubURL" })
	public void setUp(String runParallel, String environment, String browser, String hubURL) throws Exception {

		WebDriverUtils utils = new WebDriverUtils();
		utils.initializeDriver(browser, hubURL);
		driver = utils.getDriver();
		loginPage = new LoginPage(driver);
		configProperty = GeneralHelper.loadPropertyFile(environment);
		loginPage = loginPage.navigateToLoginPage(configProperty.getProperty("LoginURL"));
		loginPage.waitForLoginPageLoad().loginToApp(configProperty.getProperty("LoginUser"),
				configProperty.getProperty("LoginPassword"));
		homepage = new HomePage(driver);
	}

	@Test(description = "Verify the Data Source Save Feature", groups = { "Smoke" })

	public void createNewDataSource() throws Exception {
		try {
			homepage.launchCIMApttusAdmin();
			dataSourcePage = homepage.navigateToDataSource();
			testData = new Efficacies().readJsonFile("datasource.json");
			
			dataSourcePage.createDataSource(testData.get("DataSourceName"), testData.get("TransMetaData"),
					testData.get("CalculationDate"), testData.get("Product"), testData.get("ProgramAccount"),
					testData.get("FileSuffix"), testData.get("FileExtenstion1"), testData.get("FileExtenstion2"),
					testData.get("Delimiter"));
			assertEquals(dataSourcePage.success, dataSourcePage.successResponse.getText());
			dataSourcePage.closeToastMessage();
			dataSourcePage = homepage.navigateToDataSource();
			dataSourcePage.filterDataSource(testData.get("ColumnName"), testData.get("ColumnOperator"),
					testData.get("DataSourceName"));
			dataSourcePage.dataSourceRefresh();
			dataSourcePage.deleteFilterRecord();
			dataSourcePage.removeFilter();

		} catch (Exception e) {
			throw new CustomException(e,driver);
		}
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		driver.quit();
	}
}
