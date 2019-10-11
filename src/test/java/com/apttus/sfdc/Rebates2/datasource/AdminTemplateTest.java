package com.apttus.sfdc.Rebates2.datasource;

import static org.testng.Assert.assertEquals;

import java.util.Map;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
import com.apttus.sfdc.Rebates2.common.GenericPage;
import com.apttus.sfdc.Rebates2.common.LoginPage;
import com.apttus.sfdc.Rebates2.common.StartUpPage;
import com.apttus.sfdc.Rebates2.library.AdminTemplatePage;
import com.apttus.sfdc.Rebates2.library.DataSourcePage;
import com.apttus.sfdc.Rebates2.lightning.HomePage;
import com.apttus.sfdc.rudiments.utils.GeneralHelper;



public class AdminTemplateTest {
	
	WebDriver driver;
	public  Rebatesinit rebatesinit;
	public HomePage homePage;
	LoginPage loginPage;
	Efficacies efficacies;
	public Map<String, String> testData;
	
	public AdminTemplatePage AdmTemplatepage;
	public HomePage homepage;
	public String homeURL;
	public String baseURL;
	Properties configProperty;
	StartUpPage startUpPage;
	public GenericPage genericpage;
	public DataSourcePage datasourcePage;


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
	public void CreateNewAdminTemplate() throws Exception {
		try {
			AdmTemplatepage=homepage.navigateToAdminTemplate();
			testData = new Efficacies().readJsonFile("AdminTemplate.json");
			long ln = JavaHelpers.generateRandomNumber();
			AdmTemplatepage.MovetoNewTemplatePage(AdmTemplatepage.Newbtn ,AdmTemplatepage.Newtemplatelabel);
			AdmTemplatepage.FillTemplateDetails(AdmTemplatepage.TemplateName ,testData.get("TemplateNameDiscreteBenftProd")+ln,AdmTemplatepage.TemplateDescription,testData.get("TemplateDescValue1"));
			AdmTemplatepage.QBLayoutDefinition(AdmTemplatepage.QBselct,AdmTemplatepage.BenftPrdtValue,AdmTemplatepage.TierSelect,AdmTemplatepage.TierDiscrete);
			AdmTemplatepage.DataSourceFormula(AdmTemplatepage.DataSourcedorpdwn,AdmTemplatepage.DatasourceValue , AdmTemplatepage.BenifitCheckbox);
			AdmTemplatepage.SaveAdminTemplate(AdmTemplatepage.SaveAdmin);
		    
			assertEquals(AdmTemplatepage.NewTemplate, AdmTemplatepage.Newtemplatelabel.getText());
			assertEquals(AdmTemplatepage.success, AdmTemplatepage.successresponse.getText());
			/*Above Template for Discrete + Benefit Product 
			*/
			Thread.sleep(2000);
			AdmTemplatepage.MovetoNewTemplatePage(AdmTemplatepage.Newbtn ,AdmTemplatepage.Newtemplatelabel);
			AdmTemplatepage.FillTemplateDetails(AdmTemplatepage.TemplateName ,testData.get("TemplateNameTierQualifBenftProd")+ln,AdmTemplatepage.TemplateDescription,testData.get("TemplateDescValue2"));
			AdmTemplatepage.QBLayoutDefinition(AdmTemplatepage.QBselct,AdmTemplatepage.QualftnBenftFormula ,AdmTemplatepage.TierSelect,AdmTemplatepage.TierTiered);
			AdmTemplatepage.DataSourceFormula(AdmTemplatepage.DataSourcedorpdwn,AdmTemplatepage.DatasourceValue , AdmTemplatepage.BenifitCheckbox);
			AdmTemplatepage.SaveAdminTemplate(AdmTemplatepage.SaveAdmin);
			
			assertEquals(AdmTemplatepage.NewTemplate, AdmTemplatepage.Newtemplatelabel.getText());
			assertEquals(AdmTemplatepage.success, AdmTemplatepage.successresponse.getText());
     		/*Above Template for Tiered + Qualification & Benefit Product */
			
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Test()
	public void Filter_DeleteTemplate() throws Exception  {
		
		
		AdmTemplatepage=homepage.navigateToAdminTemplate();
		testData = new Efficacies().readJsonFile("AdminTemplate.json");
		
		AdmTemplatepage.SFDCFilter(testData.get("ColumnName"),testData.get("ColumnOperator"),testData.get("FilterValue"));
		AdmTemplatepage.DeleteSFDCFilter();
	}
	@Test()
	public void EditAdminTemplate() throws Exception {
		
		AdmTemplatepage=homepage.navigateToAdminTemplate();
		testData = new Efficacies().readJsonFile("AdminTemplate.json");
		AdmTemplatepage.MoveToEditPage();
		
		
	}

	@AfterMethod(alwaysRun = true)
	public void cleanUp() throws Exception {
		
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		startUpPage.killDriver();
	}
}




