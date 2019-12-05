package com.apttus.sfdc.rebates.datasource;

import static org.testng.Assert.assertEquals;

import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.apttus.customException.CustomException;
import com.apttus.helpers.Efficacies;
import com.apttus.helpers.JavaHelpers;
import com.apttus.selenium.WebDriverUtils;
import com.apttus.sfdc.rebates.common.LoginPage;
import com.apttus.sfdc.rebates.common.StartUpPage;
import com.apttus.sfdc.rebates.lightning.HomePage;
import com.apttus.sfdc.rebates.ui.library.DataSourcePage;
import com.apttus.sfdc.rebates.ui.library.Rebatesinit;
import com.apttus.sfdc.rudiments.utils.GeneralHelper;
import com.apttus.selenium.NGHelper;
public class DataSourceTest {
	public NGHelper ngHelper;
	WebDriver driver;
	public  Rebatesinit rebatesinit;
	LoginPage loginPage;
	public Map<String, String> testData;
	public DataSourcePage dataSourcePage;
	public HomePage homepage;
	Properties configProperty;
	StartUpPage startUpPage;
	
	@BeforeClass(alwaysRun = true)
	@Parameters({ "runParallel", "environment", "browser", "hubURL" })
	public void setUp(String runParallel, String environment, String browser, String hubURL) throws Exception {
		
		WebDriverUtils utils = new WebDriverUtils();
		utils.initializeDriver(browser, hubURL);
		driver = utils.getDriver();
		startUpPage = new StartUpPage(driver);
		configProperty = GeneralHelper.loadPropertyFile(environment);
		loginPage = startUpPage.navigateToLoginPage(configProperty.getProperty("sfloginURL"));
		loginPage.waitForLoginPageLoad().loginToApp(configProperty.getProperty("username"),configProperty.getProperty("password"));
		rebatesinit= new Rebatesinit(driver);
		homepage = rebatesinit.landOnHomepage();
			
	}
		
	@Test(description = "Verify the Data Source Save Feature",groups = {"Smoke"})
	
	public void createNewDataSource() throws Exception {
		try {
			dataSourcePage=homepage.navigateToDataSource();
			testData = new Efficacies().readJsonFile("datasource.json");
			long ln = JavaHelpers.generateRandomNumber();
			dataSourcePage.createSaveNewDataSource(testData.get("DataSourceName")+ln, testData.get("TransMetaData"), testData.get("CalculationDate"),
					                               testData.get("Product"),testData.get("ProgramAccount"),testData.get("FileSuffix"),testData.get("FileExtenstion1"), 
					                               testData.get("FileExtenstion2"),testData.get("Delimiter"));
			assertEquals(dataSourcePage.success, dataSourcePage.successresponse.getText());
			dataSourcePage.closeToastMessage();
			dataSourcePage=homepage.navigateToDataSource();
			dataSourcePage.dataSourceFilter(testData.get("ColumnName"),testData.get("ColumnOperator"),testData.get("DataSourceName")+ln);
			dataSourcePage.dataSourceRefresh();
			dataSourcePage.deleteFilterRecord();
			dataSourcePage.removeFilter();
			    
		} catch (Exception e) {
			throw new CustomException(e,driver);
		}
	}
		
	@Test(description = "Verify the Data Source Validation",groups = {"Smoke"})
	public void duplicateDataSource() throws Exception {
		try {
			dataSourcePage=homepage.navigateToDataSource();
			testData = new Efficacies().readJsonFile("datasource.json");
			long ln = JavaHelpers.generateRandomNumber();
			dataSourcePage.dataSourceRefresh();
			dataSourcePage.duplicateSaveNewDataSource(testData.get("DupDataSourceName")+ln, testData.get("TransMetaData"), testData.get("CalculationDate"),
                           testData.get("Product"),testData.get("ProgramAccount"),testData.get("FileSuffix"),testData.get("FileExtenstion1"), 
                           testData.get("FileExtenstion2"),testData.get("Delimiter"));
			
		    String DuplicateDataSource=testData.get("DupDataSourceName")+ln;
		    dataSourcePage=homepage.navigateToDataSource();	
		    dataSourcePage.dataSourceRefresh();
		    dataSourcePage.verifyduplicate(DuplicateDataSource, testData.get("TransMetaData"), testData.get("CalculationDate"),
                           testData.get("Product"),testData.get("ProgramAccount"),testData.get("FileSuffix"),testData.get("FileExtenstion1"), 
                           testData.get("FileExtenstion2"),testData.get("Delimiter"));
			assertEquals(dataSourcePage.duplicate, dataSourcePage.duplicateRecord);
			dataSourcePage.cancelDataSource();
		    
			dataSourcePage.duplicateDataSourceFilter("Name","contains",DuplicateDataSource);
			dataSourcePage.dataSourceRefresh();
			dataSourcePage.deleteFilterRecord();
			dataSourcePage.removeFilter();
			
		} catch (Exception e) {
			throw new CustomException(e,driver);
		}
	}
	@Test(description = "Verify mandatory & Field Validation-Data Source File Ingestion attribute",groups = {"Smoke"})
	public void verifyDataSourceValidation() throws Exception {
		try {
			dataSourcePage=homepage.navigateToDataSource();
		testData = new Efficacies().readJsonFile("datasource.json");
		dataSourcePage.dataSourceRefresh();
		dataSourcePage.verifyValidationDelimiter(testData.get("DataSourceName"), testData.get("TransMetaData"), testData.get("CalculationDate"),
                                               testData.get("Product"),testData.get("ProgramAccount"),testData.get("FileSuffix"),testData.get("FileExtenstion1"));
	    assertEquals(dataSourcePage.responseDelimiter, dataSourcePage.getDelimiterResponse);
		
	    dataSourcePage.verifyValidationFileExtension("ValidationAutomation", "Order Line Item", "Ready for Activation Date","Base Product","Bill To","_AutoSuffix","commas");
		assertEquals(dataSourcePage.responseFileExtension, dataSourcePage.getFileExtensionResponse);
		
		dataSourcePage.dataSourceRefresh();
		dataSourcePage.verifyValidationSuffix("ValidationAutomation", "Order Line Item", "Ready for Activation Date","Base Product","Bill To");
		assertEquals(dataSourcePage.responseSuffix, dataSourcePage.getSuffixResponse);
		
		dataSourcePage=homepage.navigateToDataSource();
		dataSourcePage.dataSourceRefresh();
		dataSourcePage.verifyValidation_DataSourceName();
		assertEquals(dataSourcePage.responseDataSource, dataSourcePage.getdatasrcResponse);
		
		dataSourcePage=homepage.navigateToDataSource();
		dataSourcePage.dataSourceRefresh();
		dataSourcePage.verifyValidationTransactionMetaData("ValidationAutomation");
		assertEquals(dataSourcePage.responseMetaData, dataSourcePage.getMetadataResponse);
		
		dataSourcePage.verifyValidation_CalculationDate("ValidationAutomation", "Order Line Item");
		assertEquals(dataSourcePage.responsecalculationdate, dataSourcePage.getCaldateResponse);
		dataSourcePage.dataSourceRefresh();
		dataSourcePage.verifyValidation_ProgramAccount("ValidationAutomation","Order Line Item","Ready for Activation Date");
		assertEquals(dataSourcePage.responsePrgmAccount, dataSourcePage.getProgram);
		
		dataSourcePage.verifyValidation_Product(testData.get("ProgramAccount"));
		assertEquals(dataSourcePage.responseProduct, dataSourcePage.productResponse.getText());
			
	}catch (Exception e) {
		throw new CustomException(e,driver);
	}}	
	
    
	@Test(description = "Verify Save- Search filter to filter and view related Rebate records in List view",groups = {"Smoke"})
	public void filterDataSource() throws Exception {
		try {
			dataSourcePage=homepage.navigateToDataSource();
			testData = new Efficacies().readJsonFile("datasource.json");
			dataSourcePage.dataSourceRefresh();
			dataSourcePage.dataSourceFilter(testData.get("ColumnName"),testData.get("ColumnOperator"),testData.get("FilterValue"));
			dataSourcePage.deleteFilter();
			assertEquals(dataSourcePage.newResponseFilter, dataSourcePage.responseFilter);
		   
		} catch (Exception e) {
			throw new CustomException(e,driver);
		}
	}
	
	@AfterMethod(alwaysRun = true)
	public void cleanUp() throws Exception {
		
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		startUpPage.killDriver();
	}
}




