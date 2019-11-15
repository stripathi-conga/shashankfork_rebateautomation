package com.apttus.sfdc.Rebates2.datasource;

import static org.testng.Assert.assertEquals;

import java.util.Map;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
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
		
	@Test(description = "Verify that the Admin user can create and save a new template",groups = {"Regression","Smoke","SFDC"})
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
		
			//assertEquals(AdmTemplatepage.NewTemplate, AdmTemplatepage.Newtemplatelabel.getText());
			assertEquals(AdmTemplatepage.success, AdmTemplatepage.successresponse.getText());
			AdmTemplatepage.CloseToastMessage();
			AdmTemplatepage.SFDCFilter(testData.get("ColumnName"),testData.get("ColumnOperator"),testData.get("TemplateNameDiscreteBenftProd")+ln);
			AdmTemplatepage.DeleteSFDCFilter();
			AdmTemplatepage.CloseToastMessage();
			AdmTemplatepage.deleteFilter();
			AdmTemplatepage.CloseToastMessage();
			/*Above Template for Discrete + Benefit Product */
			
			
			AdmTemplatepage.MovetoNewTemplatePage(AdmTemplatepage.Newbtn ,AdmTemplatepage.Newtemplatelabel);
			AdmTemplatepage.FillTemplateDetails(AdmTemplatepage.TemplateName ,testData.get("TemplateNameTierQualifBenftProd")+ln,AdmTemplatepage.TemplateDescription,testData.get("TemplateDescValue2"));
			AdmTemplatepage.QBLayoutDefinition(AdmTemplatepage.QBselct,AdmTemplatepage.QualftnBenftFormula ,AdmTemplatepage.TierSelect,AdmTemplatepage.TierTiered);
			AdmTemplatepage.DataSourceFormula(AdmTemplatepage.DataSourcedorpdwn,AdmTemplatepage.DatasourceValue , AdmTemplatepage.BenifitCheckbox);
			AdmTemplatepage.SaveAdminTemplate(AdmTemplatepage.SaveAdmin);
			
			assertEquals(AdmTemplatepage.NewTemplate, AdmTemplatepage.Newtemplatelabel.getText());
			assertEquals(AdmTemplatepage.success, AdmTemplatepage.successresponse.getText());
			
			AdmTemplatepage.CloseToastMessage();
			AdmTemplatepage.SFDCFilter(testData.get("ColumnName"),testData.get("ColumnOperator"),testData.get("TemplateNameTierQualifBenftProd")+ln);
			AdmTemplatepage.DeleteSFDCFilter();
			AdmTemplatepage.CloseToastMessage();
			AdmTemplatepage.deleteFilter();
			AdmTemplatepage.CloseToastMessage();
     		/*Above Template for Tiered + Qualification & Benefit Product*/ 
			
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	
	
	@Test(description = "Verify that the Admin user can edit and update the existing template",groups = {"Regression","Smoke","SFDC"})
	public void EditAdminTemplate() throws Exception {
		
		AdmTemplatepage=homepage.navigateToAdminTemplate();
		testData = new Efficacies().readJsonFile("AdminTemplate.json");
		long ln = JavaHelpers.generateRandomNumber();
		
		String Admintemplate=testData.get("TemplateNameDiscreteBenftProd")+ln;
		
		AdmTemplatepage.MovetoNewTemplatePage(AdmTemplatepage.Newbtn ,AdmTemplatepage.Newtemplatelabel);
		AdmTemplatepage.FillTemplateDetails(AdmTemplatepage.TemplateName ,Admintemplate,AdmTemplatepage.TemplateDescription,testData.get("TemplateDescValue1"));
		AdmTemplatepage.QBLayoutDefinition(AdmTemplatepage.QBselct,AdmTemplatepage.BenftPrdtValue,AdmTemplatepage.TierSelect,AdmTemplatepage.TierDiscrete);
		AdmTemplatepage.DataSourceFormula(AdmTemplatepage.DataSourcedorpdwn,AdmTemplatepage.DatasourceValue , AdmTemplatepage.BenifitCheckbox);
		AdmTemplatepage.SaveAdminTemplate(AdmTemplatepage.SaveAdmin);
				
		AdmTemplatepage.SFDCFilter(testData.get("ColumnName"),testData.get("ColumnOperator"),Admintemplate);
		AdmTemplatepage.CloseToastMessage();
		AdmTemplatepage.MoveToEditPage();
		AdmTemplatepage.EditAdminTemplate(AdmTemplatepage.TemplateName ,testData.get("TemplateNameDiscreteBenftProd")+ln);
		AdmTemplatepage.SaveAdminTemplate(AdmTemplatepage.SaveAdmin);
		assertEquals(AdmTemplatepage.success, AdmTemplatepage.successresponse.getText());
		
		AdmTemplatepage=homepage.navigateToAdminTemplate();
		AdmTemplatepage.RemoveAllFilter();
		AdmTemplatepage.SFDCFilter(testData.get("ColumnName"),testData.get("ColumnOperator"),testData.get("TemplateNameDiscreteBenftProd")+ln);
		AdmTemplatepage.DeleteSFDCFilter();
		AdmTemplatepage.CloseToastMessage();
		AdmTemplatepage.deleteFilter();
		AdmTemplatepage.CloseToastMessage();
		
		
	}
	
	@Test(description = "Verifying for the Validation of Template Name on the Template View list",groups = {"Regression","Smoke","SFDC"})
	public void ValidationAdmintemplate() throws Exception {
		AdmTemplatepage=homepage.navigateToAdminTemplate();
		testData = new Efficacies().readJsonFile("AdminTemplate.json");
		AdmTemplatepage.MovetoNewTemplatePage(AdmTemplatepage.Newbtn ,AdmTemplatepage.Newtemplatelabel);
		AdmTemplatepage.VerifySaveAdminTemplate(AdmTemplatepage.SaveAdmin);
	    
		assertEquals(AdmTemplatepage.TemplateEmpty, AdmTemplatepage.ToastResponse.getText(), "Program name cannot be empty");	
		AdmTemplatepage.CloseToastMessage();
		
		AdmTemplatepage.FillTemplateDetails(AdmTemplatepage.TemplateName ,"TemplateNameDiscreteBenftProd",AdmTemplatepage.TemplateDescription,"TemplateDescValue1");
		AdmTemplatepage.QBLayoutDefinition(AdmTemplatepage.QBselct,AdmTemplatepage.BenftPrdtValue,AdmTemplatepage.TierSelect,AdmTemplatepage.TierDiscrete);
		AdmTemplatepage.DataSourceFormula(AdmTemplatepage.DataSourcedorpdwn,AdmTemplatepage.DatasourceValue , AdmTemplatepage.BenifitCheckbox);
		
		AdmTemplatepage.SaveAdminTemplate(AdmTemplatepage.SaveAdmin);
		AdmTemplatepage.CloseToastMessage();
		
		AdmTemplatepage.MovetoNewTemplatePage(AdmTemplatepage.Newbtn ,AdmTemplatepage.Newtemplatelabel);
		AdmTemplatepage.FillTemplateDetails(AdmTemplatepage.TemplateName ,"TemplateNameDiscreteBenftProd",AdmTemplatepage.TemplateDescription,"TemplateDescValue1");
		AdmTemplatepage.QBLayoutDefinition(AdmTemplatepage.QBselct,AdmTemplatepage.BenftPrdtValue,AdmTemplatepage.TierSelect,AdmTemplatepage.TierDiscrete);
		AdmTemplatepage.SaveDataSourceFormula(AdmTemplatepage.DataSourcedorpdwn,AdmTemplatepage.DatasourceValue , AdmTemplatepage.BenifitCheckbox);
		
		AdmTemplatepage.SFDCFilter(testData.get("ColumnName"),testData.get("ColumnOperator"),"TemplateNameDiscreteBenftProd");
		AdmTemplatepage.WaitForPageToLoad();
		AdmTemplatepage.DeleteSFDCFilter();
		AdmTemplatepage.CloseToastMessage();
		AdmTemplatepage.deleteFilter();
		AdmTemplatepage.CloseToastMessage();
	}

	@Test(description = "Verify for the Activate button on the Template details page",groups = {"Regression","Smoke","SFDC"})
	public void ActivateAdminTemplate() throws Exception {
		
		AdmTemplatepage=homepage.navigateToAdminTemplate();
		testData = new Efficacies().readJsonFile("AdminTemplate.json");
		long ln = JavaHelpers.generateRandomNumber();
		AdmTemplatepage.MovetoNewTemplatePage(AdmTemplatepage.Newbtn ,AdmTemplatepage.Newtemplatelabel);
		AdmTemplatepage.FillTemplateDetails(AdmTemplatepage.TemplateName ,testData.get("ActiveTemplate")+ln,AdmTemplatepage.TemplateDescription,testData.get("TemplateDescValue1"));
		AdmTemplatepage.QBLayoutDefinition(AdmTemplatepage.QBselct,AdmTemplatepage.BenftPrdtValue,AdmTemplatepage.TierSelect,AdmTemplatepage.TierDiscrete);
		AdmTemplatepage.DataSourceFormula(AdmTemplatepage.DataSourcedorpdwn,AdmTemplatepage.DatasourceValue , AdmTemplatepage.BenifitCheckbox);
		AdmTemplatepage.SaveAdminTemplate(AdmTemplatepage.SaveAdmin);
		
		String ActvTemplate=testData.get("ActiveTemplate")+ln;
		AdmTemplatepage.CloseToastMessage();
		AdmTemplatepage.SFDCFilter(testData.get("ColumnName"),testData.get("ColumnOperator"),ActvTemplate);
		AdmTemplatepage.CloseToastMessage();
		AdmTemplatepage.ActivateTemplate();
		AdmTemplatepage=homepage.navigateToAdminTemplate();
		AdmTemplatepage.RemoveAllFilter();
		AdmTemplatepage.SFDCFilter(testData.get("ColumnName"),testData.get("ColumnOperator"),ActvTemplate);
		AdmTemplatepage.DeleteSFDCFilter();
		AdmTemplatepage.CloseToastMessage();
		AdmTemplatepage.deleteFilter();
		AdmTemplatepage.CloseToastMessage();
		
		
	}
	
	@Test(description = "Verify for the edit button on the Template details page when the template status is 'Draft'",groups = {"Regression","Smoke","SFDC"})
	public void EditDraftTemplate() throws Exception {
		
		AdmTemplatepage=homepage.navigateToAdminTemplate();
		testData = new Efficacies().readJsonFile("AdminTemplate.json");
		long ln = JavaHelpers.generateRandomNumber();
		
		String Admintemplate=testData.get("TemplateNameDiscreteBenftProd")+ln;
		
		AdmTemplatepage.MovetoNewTemplatePage(AdmTemplatepage.Newbtn ,AdmTemplatepage.Newtemplatelabel);
		AdmTemplatepage.FillTemplateDetails(AdmTemplatepage.TemplateName ,Admintemplate,AdmTemplatepage.TemplateDescription,testData.get("TemplateDescValue1"));
		AdmTemplatepage.QBLayoutDefinition(AdmTemplatepage.QBselct,AdmTemplatepage.BenftPrdtValue,AdmTemplatepage.TierSelect,AdmTemplatepage.TierDiscrete);
		AdmTemplatepage.DataSourceFormula(AdmTemplatepage.DataSourcedorpdwn,AdmTemplatepage.DatasourceValue , AdmTemplatepage.BenifitCheckbox);
		AdmTemplatepage.SaveAdminTemplate(AdmTemplatepage.SaveAdmin);
				
		AdmTemplatepage.SFDCFilter(testData.get("ColumnName"),testData.get("ColumnOperator"),Admintemplate);
		AdmTemplatepage.CloseToastMessage();
		AdmTemplatepage.MoveToEditPage();
		AdmTemplatepage.EditAdminTemplate(AdmTemplatepage.TemplateName ,testData.get("TemplateNameDiscreteBenftProd")+ln);
		AdmTemplatepage.SaveAdminTemplate(AdmTemplatepage.SaveAdmin);
		assertEquals(AdmTemplatepage.success, AdmTemplatepage.successresponse.getText());
		
		AdmTemplatepage=homepage.navigateToAdminTemplate();
		AdmTemplatepage.RemoveAllFilter();
		AdmTemplatepage.SFDCFilter(testData.get("ColumnName"),testData.get("ColumnOperator"),testData.get("TemplateNameDiscreteBenftProd")+ln);
		AdmTemplatepage.DeleteSFDCFilter();
		AdmTemplatepage.CloseToastMessage();
		AdmTemplatepage.deleteFilter();
		AdmTemplatepage.CloseToastMessage();
		
		
	}
	@AfterMethod(alwaysRun = true)
	public void cleanUp() throws Exception {
		
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		startUpPage.killDriver();
	}
}




