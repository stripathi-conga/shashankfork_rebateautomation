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

public class DataSourceTest {
	
	WebDriver driver;
	public  Rebatesinit rebatesinit;
	LoginPage loginPage;
	public Map<String, String> testData;
	public DataSourcePage datasourcePage;
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
			datasourcePage=homepage.navigateToDataSource();
			testData = new Efficacies().readJsonFile("datasource.json");
			long ln = JavaHelpers.generateRandomNumber();
			datasourcePage.createSaveNewDataSource(testData.get("DataSourceName")+ln, testData.get("TransMetaData"), testData.get("CalculationDate"),
					                               testData.get("Product"),testData.get("ProgramAccount"),testData.get("FileSuffix"),testData.get("FileExtenstion1"), 
					                               testData.get("FileExtenstion2"),testData.get("Delimiter"));
			assertEquals(datasourcePage.success, datasourcePage.successresponse.getText());
			datasourcePage.closeToastMessage();
			datasourcePage=homepage.navigateToDataSource();
			datasourcePage.dataSdourceFilter(testData.get("ColumnName"),testData.get("ColumnOperator"),testData.get("DataSourceName")+ln);
			datasourcePage.dataSourceRefresh();
			datasourcePage.deleteSfdcFilter();
			datasourcePage.removeFilter();
			    
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
		
	@Test(description = "Verify the Data Source Validation",groups = {"Smoke"})
	public void duplicateDataSource() throws Exception {
		try {
			datasourcePage=homepage.navigateToDataSource();
			testData = new Efficacies().readJsonFile("datasource.json");
			long ln = JavaHelpers.generateRandomNumber();
			datasourcePage.dataSourceRefresh();
			datasourcePage.duplicateSaveNewDataSource(testData.get("DupDataSourceName")+ln, testData.get("TransMetaData"), testData.get("CalculationDate"),
                           testData.get("Product"),testData.get("ProgramAccount"),testData.get("FileSuffix"),testData.get("FileExtenstion1"), 
                           testData.get("FileExtenstion2"),testData.get("Delimiter"));
			
		    String DuplicateDataSource=testData.get("DupDataSourceName")+ln;
		    datasourcePage=homepage.navigateToDataSource();	
		    datasourcePage.dataSourceRefresh();
			datasourcePage.verifyduplicate(DuplicateDataSource, testData.get("TransMetaData"), testData.get("CalculationDate"),
                           testData.get("Product"),testData.get("ProgramAccount"),testData.get("FileSuffix"),testData.get("FileExtenstion1"), 
                           testData.get("FileExtenstion2"),testData.get("Delimiter"));
			assertEquals(datasourcePage.duplicate, datasourcePage.duplicateRecord);
		    datasourcePage.cancelDatasource();
			datasourcePage.duplicateSdourceFilter("Name","contains",DuplicateDataSource);
			datasourcePage.closeToastMessage();
			datasourcePage.deleteSfdcFilter();
			datasourcePage.deleteFilter();
			
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	@Test(description = "Verify mandatory & Field Validation-Data Source File Ingestion attribute",groups = {"Smoke"})
	public void verifyValidationMsz() throws Exception {
		try {
		datasourcePage=homepage.navigateToDataSource();
		testData = new Efficacies().readJsonFile("datasource.json");
		datasourcePage.dataSourceRefresh();
		datasourcePage.veriyValidationDelimiter(testData.get("DataSourceName"), testData.get("TransMetaData"), testData.get("CalculationDate"),
                                               testData.get("Product"),testData.get("ProgramAccount"),testData.get("FileSuffix"),testData.get("FileExtenstion1"));
	    assertEquals(datasourcePage.responseDelimiter, datasourcePage.getDelimiterResponse);
		
		datasourcePage.veriyValidationFileExtension("ValidationAutomation", "Order Line Item", "Ready for Activation Date","Base Product","Bill To","_AutoSuffix","commas");
		assertEquals(datasourcePage.responseFileExt, datasourcePage.getFileExtensionResponse);
		
		datasourcePage.dataSourceRefresh();
		datasourcePage.veriyValidationSuffix("ValidationAutomation", "Order Line Item", "Ready for Activation Date","Base Product","Bill To");
		assertEquals(datasourcePage.responseSuffix, datasourcePage.getSuffixResponse);
		
		datasourcePage=homepage.navigateToDataSource();
		datasourcePage.dataSourceRefresh();
		datasourcePage.verifyValidation_DataSourceName();
		assertEquals(datasourcePage.responseDataSrc, datasourcePage.getdatasrcResponse);
		
		datasourcePage=homepage.navigateToDataSource();
		datasourcePage.verifyValidationTransactionMetaData("ValidationAutomation");
		assertEquals(datasourcePage.responseMetaData, datasourcePage.getMetadataResponse);
		
		datasourcePage.verifyValidation_CalculationDate("ValidationAutomation", "Order Line Item");
		assertEquals(datasourcePage.responseCalcDate, datasourcePage.getCaldateResponse);
		datasourcePage.dataSourceRefresh();
		datasourcePage.veriyValidation_ProgramAccount("ValidationAutomation","Order Line Item","Ready for Activation Date");
		assertEquals(datasourcePage.responsePrgmAccount, datasourcePage.getProgram);
		
		datasourcePage.veriyValidation_Product(testData.get("ProgramAccount"));
		assertEquals(datasourcePage.responseProduct, datasourcePage.productResponse.getText());
			
	}catch (Exception e) {
		throw new Exception(e);
	}}	
	
	 @Test(description = "Verify the Data Source with multiplecombination",groups = {"Smoke"})
     public void verifyDelimiterSuffix() throws Exception {
		 try {
    	datasourcePage=homepage.navigateToDataSource();
		testData = new Efficacies().readJsonFile("datasource.json");
		long ln = JavaHelpers.generateRandomNumber();
		datasourcePage.dataSourceRefresh();
		datasourcePage.verifydiffrentsetofDelimterSuffixB(testData.get("DataSourceName")+"B"+ln, testData.get("TransMetaData"),testData.get("CalculationDate"),testData.get("SuffixB"),
				       testData.get("Product"),testData.get("ProgramAccount"),testData.get("FileExtenstion1"), 
				       testData.get("FileExtenstion2"),testData.get("DelimiterB"));
		datasourcePage=homepage.navigateToDataSource();
		assertEquals(datasourcePage.success, datasourcePage.successresponse.getText());
		datasourcePage.closeToastMessage();
		datasourcePage.dataSourceRefresh();
		datasourcePage.verifydiffrentsetofDelimterSuffixC(testData.get("DataSourceName")+"C"+ln, testData.get("TransMetaData"),testData.get("CalculationDate"),testData.get("SuffixC"),
				       testData.get("Product"),testData.get("ProgramAccount"),testData.get("FileExtenstion1"), 
			           testData.get("FileExtenstion2"),testData.get("DelimiterC"));
		assertEquals(datasourcePage.success, datasourcePage.successresponse.getText());
		datasourcePage.closeToastMessage();
		datasourcePage=homepage.navigateToDataSource();	  
		datasourcePage.dataSourceRefresh();
		datasourcePage.verifydiffrentsetofDelimterSuffixD(testData.get("DataSourceName")+"D"+ln, testData.get("TransMetaData"),testData.get("CalculationDate"),testData.get("SuffixD"),
				       testData.get("Product"),testData.get("ProgramAccount"),testData.get("FileExtenstion1"), 
			           testData.get("FileExtenstion2"),testData.get("DelimiterD"));
		assertEquals(datasourcePage.success, datasourcePage.successresponse.getText());
		datasourcePage.closeToastMessage();
		datasourcePage=homepage.navigateToDataSource();
		datasourcePage.dataSourceRefresh();
		datasourcePage.verifydiffrentsetofDelimterSuffixE(testData.get("DataSourceName")+"E"+ln, testData.get("TransMetaData"),testData.get("CalculationDate"),testData.get("SuffixE"),
				       testData.get("Product"),testData.get("ProgramAccount"),testData.get("FileExtenstion1"), 
			           testData.get("FileExtenstion2"),testData.get("DelimiterE"));
		assertEquals(datasourcePage.success, datasourcePage.successresponse.getText());
		datasourcePage.closeToastMessage();
    	
    }catch (Exception e) {
		throw new Exception(e);
	}}
    
	@Test(description = "Verify Save- Search filter to filter and view related Rebate records in List view",groups = {"Smoke"})
	public void filterDataSource() throws Exception {
		try {
			datasourcePage=homepage.navigateToDataSource();
			testData = new Efficacies().readJsonFile("datasource.json");
			datasourcePage.dataSourceRefresh();
			datasourcePage.dataSdourceFilter(testData.get("ColumnName"),testData.get("ColumnOperator"),testData.get("FilterValue"));
			datasourcePage.deleteFilter();
			assertEquals(datasourcePage.newResponseFilter, datasourcePage.responseFilter);
		   
		} catch (Exception e) {
			throw new Exception(e);
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




