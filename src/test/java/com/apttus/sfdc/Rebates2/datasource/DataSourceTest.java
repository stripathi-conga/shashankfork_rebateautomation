package com.apttus.sfdc.Rebates2.datasource;

import static org.testng.Assert.assertEquals;

import java.util.Map;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.apttus.sfdc.Rebates2.library.Rebatesinit;
import com.apttus.helpers.Efficacies;
import com.apttus.helpers.JavaHelpers;
import com.apttus.selenium.WebDriverUtils;
import com.apttus.sfdc.Rebates2.common.LoginPage;
import com.apttus.sfdc.Rebates2.common.StartUpPage;
import com.apttus.sfdc.Rebates2.library.DataSourcePage;
import com.apttus.sfdc.Rebates2.lightning.HomePage;
import com.apttus.sfdc.rudiments.utils.GeneralHelper;



public class DataSourceTest {
	
	WebDriver driver;
	public  Rebatesinit rebatesinit;
	public HomePage homePage;
	LoginPage loginPage;
	Efficacies efficacies;
	public Map<String, String> testData;
	
	public DataSourcePage datasourcePage;
	public HomePage homepage;
	public String homeURL;
	public String baseURL;
	Properties configProperty;
	StartUpPage startUpPage;


	@BeforeClass(alwaysRun = true)
	@Parameters({ "runParallel", "environment", "browser", "hubURL" })
	public void setUp(String runParallel, String environment, String browser, String hubURL) throws Exception {
		
		Properties configProperty;
		WebDriverUtils utils = new WebDriverUtils();
		utils.initializeDriver(browser, hubURL);
		driver = utils.getDriver();
		efficacies = new Efficacies();
		startUpPage = new StartUpPage(driver);
		
		configProperty = GeneralHelper.loadPropertyFile(environment);
	
		loginPage = startUpPage.navigateToLoginPage(configProperty.getProperty("sfloginURL"));
		loginPage.waitForLoginPageLoad().loginToApp(configProperty.getProperty("username"),configProperty.getProperty("password"));
		rebatesinit= new Rebatesinit(driver);
		homepage = rebatesinit.landOnHomepage();
			
	}
		
	@Test()
	public void CreateNewDataSource() throws Exception {
		try {
			datasourcePage=homepage.navigateToDataSource();
			testData = new Efficacies().readJsonFile("datasource.json");
			long ln = JavaHelpers.generateRandomNumber();
			datasourcePage.CreateSaveNewDataSource(testData.get("DataSourceName")+ln, testData.get("FileExtenstion1"), 
					       testData.get("FileExtenstion2"),testData.get("TransMetaData"),testData.get("FileSuffix"),
					       testData.get("CalculationDate"),testData.get("Product"),testData.get("ProgramAccount"),testData.get("Delimiter"));
		   
			assertEquals(datasourcePage.success, datasourcePage.successresponse.getText());
		    String createdDataSource=testData.get("DataSourceName")+ln;
		    System.out.println("createdDataSource: "+createdDataSource);
			datasourcePage.SearchDataSource(createdDataSource);
			
		    assertEquals(createdDataSource, datasourcePage.Namecolmn.getText());
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Test()
	public void DuplicateDataSource() throws Exception {
		try {
			datasourcePage=homepage.navigateToDataSource();
			testData = new Efficacies().readJsonFile("datasource.json");
			long ln = JavaHelpers.generateRandomNumber();
			datasourcePage.DuplicateSaveNewDataSource(testData.get("DupDataSourceName")+ln, testData.get("FileExtenstion1"), 
					       testData.get("FileExtenstion2"),testData.get("TransMetaData"),testData.get("FileSuffix"),
					       testData.get("CalculationDate"),testData.get("Product"),testData.get("ProgramAccount"),testData.get("Delimiter"));
		   
			
		    String DuplicateDataSource=testData.get("DupDataSourceName")+ln;
		    System.out.println("createdDataSource: "+DuplicateDataSource);
			datasourcePage.Verifyduplicate(DuplicateDataSource, testData.get("FileExtenstion1"), 
				       testData.get("FileExtenstion2"),testData.get("TransMetaData"),testData.get("FileSuffix"),
				       testData.get("CalculationDate"),testData.get("Product"),testData.get("ProgramAccount"),testData.get("Delimiter"));
			Thread.sleep(3000);
			assertEquals(datasourcePage.Duplicate, datasourcePage.Duplicateresponse.getText());
			
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	@Test
	public void VerifyValidationMsz() throws Exception {
		
		datasourcePage=homepage.navigateToDataSource();
		testData = new Efficacies().readJsonFile("datasource.json");
		
		datasourcePage.VerifyValidation_DataSourceName();
		assertEquals(datasourcePage.ResponseDataSrc, datasourcePage.DataSrcResponse.getText());
		
		datasourcePage.VerifyValidation_TransactionMetaData(testData.get("DataSourceName"));
		assertEquals(datasourcePage.ResponseMetaData, datasourcePage.MetadataResponse.getText());
		
		datasourcePage.VerifyValidation_CalculationDate(testData.get("TransMetaData"));
		assertEquals(datasourcePage.ResponseCalcDate, datasourcePage.CalDatecResponse.getText());
		
		datasourcePage.VeriyValidation_ProgramAccount(testData.get("CalculationDate"));
		assertEquals(datasourcePage.ResponsePrgmAccount, datasourcePage.ProgramAcctesponse.getText());
		
		datasourcePage.VeriyValidation_Product(testData.get("ProgramAccount"));
		assertEquals(datasourcePage.ResponseProduct, datasourcePage.ProductResponse.getText());
		
		datasourcePage.VeriyValidation_Suffix(testData.get("Product"));
		assertEquals(datasourcePage.ResponseSuffix, datasourcePage.SuffixResponse.getText());
		
		datasourcePage.VeriyValidation_FileExtension(testData.get("FileSuffix"));
		assertEquals(datasourcePage.ResponseFileExt, datasourcePage.FileExtResponse.getText());
		
		datasourcePage.VeriyValidation_Delimiter(testData.get("FileExtenstion"));
		assertEquals(datasourcePage.ResponseDelimiter, datasourcePage.DelimiterResponse.getText());
		
	}
	
	@DataProvider(name="VerifyDiffrentDelimiterSuffix")
	
	public Object getdata() {
		
		
	Object[][] DelimterSuffix	=new Object[4][2];
	
	DelimterSuffix[0][0]="_part";
	DelimterSuffix[0][1]="semicolon";
	
	DelimterSuffix[1][0]="suffix1234";
	DelimterSuffix[1][1]="open braces";
	
	DelimterSuffix[2][0]="_#$%^&**suff676";
	DelimterSuffix[2][1]="forward slash";
	
	DelimterSuffix[3][0]="tab_tab";
	DelimterSuffix[3][1]="tab";
	
	return DelimterSuffix;	
	}
	
    @Test(dataProvider="VerifyDiffrentDelimiterSuffix")
    	
    	public void VerifyDelimiterSuffix(String Suffix,String Delimiter) throws Exception {
    	
    	datasourcePage=homepage.navigateToDataSource();
		testData = new Efficacies().readJsonFile("datasource.json");
		long ln = JavaHelpers.generateRandomNumber();
		datasourcePage.VerifydiffrentsetofDelimterSuffix(testData.get("DataSourceName")+ln, testData.get("FileExtenstion1"), 
				       testData.get("FileExtenstion2"),testData.get("TransMetaData"),Suffix,
				       testData.get("CalculationDate"),testData.get("Product"),testData.get("ProgramAccount"),Delimiter);
		//assert missing
    	
    }
    
	@Test()
	public void FilterDataSource() throws Exception {
		try {
			datasourcePage=homepage.navigateToDataSource();
			testData = new Efficacies().readJsonFile("datasource.json");
			datasourcePage.DataSdourceFilter(testData.get("ColumnName"),testData.get("ColumnOperator"),testData.get("FilterValue"));
			//assert missing
			datasourcePage.deleteFilter();
			//assert missing
		   
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




