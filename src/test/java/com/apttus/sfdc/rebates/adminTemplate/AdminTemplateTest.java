package com.apttus.sfdc.rebates.adminTemplate;

import static org.testng.Assert.assertEquals;

import java.util.Map;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.apttus.sfdc.rebates.ui.library.Rebatesinit;

import com.apttus.helpers.Efficacies;
import com.apttus.helpers.JavaHelpers;
import com.apttus.selenium.WebDriverUtils;
import com.apttus.sfdc.rebates.common.GenericPage;
import com.apttus.sfdc.rebates.common.LoginPage;
import com.apttus.sfdc.rebates.common.StartUpPage;
import com.apttus.sfdc.rebates.ui.library.AdminTemplatePage;

import com.apttus.sfdc.rebates.lightning.HomePage;
import com.apttus.sfdc.rudiments.utils.GeneralHelper;

public class AdminTemplateTest {
	
	WebDriver driver;
	GenericPage genericpage;
	public  Rebatesinit rebatesinit;
	LoginPage loginPage;
	public Map<String, String> testData;
	public AdminTemplatePage adminTemplatepage;
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
		loginPage = startUpPage.navigateToLoginPage(configProperty.getProperty("LoginURL"));
		loginPage.waitForLoginPageLoad().loginToApp(configProperty.getProperty("LoginUser"),configProperty.getProperty("LoginPassword"));
		rebatesinit= new Rebatesinit(driver);
		homepage = rebatesinit.landOnHomepage();
				
	}
	    @Test(description = "Verify that the Admin user can create and save a new template",groups = {"Smoke"})
	    public void createAdminTemplate() throws Exception {
		try {
			
			adminTemplatepage=homepage.navigateToAdminTemplate();
			adminTemplatepage.launchAdminTemplate();
			testData = new Efficacies().readJsonFile("AdminTemplate.json");
			long ln = JavaHelpers.generateRandomNumber();
			adminTemplatepage.moveToNewTemplatePage(adminTemplatepage.btnNew ,adminTemplatepage.lblNewTemplate);
			adminTemplatepage.fillTemplateDetails(adminTemplatepage.ddlTemplateName ,testData.get("TemplateNameDiscreteBenftProd")+ln,adminTemplatepage.txtTemplateDescription,testData.get("TemplateDescValue1"));
			adminTemplatepage.qbLayoutDefinition(adminTemplatepage.ddlQbSelect,adminTemplatepage.ddlBenftProductValue,adminTemplatepage.ddlTierSelect,adminTemplatepage.ddlTierDiscrete);
			adminTemplatepage.dataSourceFormula(adminTemplatepage.ddlDataSource,adminTemplatepage.ddlDataSourceValue , adminTemplatepage.chkFormulas);
			adminTemplatepage.saveAdminTemplate(adminTemplatepage.btnSaveAdmin);
		
			assertEquals(adminTemplatepage.success, adminTemplatepage.successResponse.getText());
			adminTemplatepage.closeToastMessage();
			adminTemplatepage=homepage.navigateToAdminTemplate();
			adminTemplatepage.launchAdminTemplate();
			adminTemplatepage.deleteAdminTemplateFilter();
			adminTemplatepage.closeToastMessage();
				
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	    @Test(description = "Verify that the Admin user can edit and update the existing template",groups = {"Smoke"})
	    public void editAdminTemplate() throws Exception {
	    try {
	    	
	    	adminTemplatepage=homepage.navigateToAdminTemplate();
		    adminTemplatepage.launchAdminTemplate();
		    testData = new Efficacies().readJsonFile("AdminTemplate.json");
		    long ln = JavaHelpers.generateRandomNumber();
		    String Admintemplate=testData.get("TemplateNameDiscreteBenftProd")+ln;
		
		    adminTemplatepage.moveToNewTemplatePage(adminTemplatepage.btnNew ,adminTemplatepage.lblNewTemplate);
		    adminTemplatepage.fillTemplateDetails(adminTemplatepage.ddlTemplateName ,Admintemplate,adminTemplatepage.txtTemplateDescription,testData.get("TemplateDescValue1"));
		    adminTemplatepage.qbLayoutDefinition(adminTemplatepage.ddlQbSelect,adminTemplatepage.ddlBenftProductValue,adminTemplatepage.ddlTierSelect,adminTemplatepage.ddlTierDiscrete);
		    adminTemplatepage.dataSourceFormula(adminTemplatepage.ddlDataSource,adminTemplatepage.ddlDataSourceValue , adminTemplatepage.chkFormulas);
		    adminTemplatepage.saveAdminTemplate(adminTemplatepage.btnSaveAdmin);
		    adminTemplatepage.closeToastMessage();
		    adminTemplatepage=homepage.navigateToAdminTemplate();		
		    adminTemplatepage.moveToEditPage();
		    adminTemplatepage.editAdminTemplate(adminTemplatepage.ddlTemplateName ,testData.get("TemplateNameDiscretebenftProd")+ln);
		    adminTemplatepage.saveAdminTemplate(adminTemplatepage.btnSaveAdmin);
		    assertEquals(adminTemplatepage.success, adminTemplatepage.successResponse.getText());
		
		    adminTemplatepage=homepage.navigateToAdminTemplate();
		    adminTemplatepage.deleteAdminTemplateFilter();
		    adminTemplatepage.closeToastMessage();		
	}catch (Exception e) {
		throw new Exception(e);
	}
	    }
	
	    @Test(description = "Verifying for the Validation of Template Name on the Template View list",groups = {"Smoke"})
	    public void validationAdmintemplate() throws Exception {
	    	try {
		
	    		adminTemplatepage=homepage.navigateToAdminTemplate();
		        adminTemplatepage.launchAdminTemplate();
		        testData = new Efficacies().readJsonFile("AdminTemplate.json");
		        adminTemplatepage.moveToNewTemplatePage(adminTemplatepage.btnNew ,adminTemplatepage.lblNewTemplate);
		        adminTemplatepage.saveAdminTemplate(adminTemplatepage.btnSaveAdmin);
	    
		        assertEquals(adminTemplatepage.templateEmpty, adminTemplatepage.toastResponse.getText(), "Program name cannot be empty");	
		
		        adminTemplatepage.closeToastMessage();
		        adminTemplatepage.fillTemplateDetails(adminTemplatepage.ddlTemplateName ,"TemplateNameDiscreteBenftProd",adminTemplatepage.txtTemplateDescription,"TemplateDescValue1");
		        adminTemplatepage.qbLayoutDefinition(adminTemplatepage.ddlQbSelect,adminTemplatepage.ddlBenftProductValue,adminTemplatepage.ddlTierSelect,adminTemplatepage.ddlTierDiscrete);
		        adminTemplatepage.dataSourceFormula(adminTemplatepage.ddlDataSource,adminTemplatepage.ddlDataSourceValue , adminTemplatepage.chkFormulas);
		        adminTemplatepage.saveAdminTemplate(adminTemplatepage.btnSaveAdmin);
		        adminTemplatepage.closeToastMessage();
	
		        adminTemplatepage=homepage.navigateToAdminTemplate();
		        adminTemplatepage.launchAdminTemplate();
		        adminTemplatepage.moveToNewTemplatePage(adminTemplatepage.btnNew ,adminTemplatepage.lblNewTemplate);
		        adminTemplatepage.fillTemplateDetails(adminTemplatepage.ddlTemplateName ,"TemplateNameDiscreteBenftProd",adminTemplatepage.txtTemplateDescription,"TemplateDescValue1");
		        adminTemplatepage.qbLayoutDefinition(adminTemplatepage.ddlQbSelect,adminTemplatepage.ddlBenftProductValue,adminTemplatepage.ddlTierSelect,adminTemplatepage.ddlTierDiscrete);
		        adminTemplatepage.saveDataSourceFormula(adminTemplatepage.ddlDataSource,adminTemplatepage.ddlDataSourceValue , adminTemplatepage.chkFormulas);
		        adminTemplatepage=homepage.navigateToAdminTemplate();
		        adminTemplatepage.filterAdminTemplate(testData.get("ColumnName"),testData.get("ColumnOperator"),"TemplateNameDiscreteBenftProd");
		        adminTemplatepage.deleteFilter();
		        adminTemplatepage.closeToastMessage();
	}catch (Exception e) {
		throw new Exception(e);
	}}

	    @Test(description = "Verify for the Activate button on the Template details page",groups = {"Smoke"})
	    public void activateAdminTemplate() throws Exception {
		try {
		
			adminTemplatepage=homepage.navigateToAdminTemplate();
		    testData = new Efficacies().readJsonFile("AdminTemplate.json");
		    long ln = JavaHelpers.generateRandomNumber();
		    adminTemplatepage.launchAdminTemplate();
		    adminTemplatepage.moveToNewTemplatePage(adminTemplatepage.btnNew ,adminTemplatepage.lblNewTemplate);
		    adminTemplatepage.fillTemplateDetails(adminTemplatepage.ddlTemplateName ,testData.get("ActiveTemplate")+ln,adminTemplatepage.txtTemplateDescription,testData.get("TemplateDescValue1"));
		   
		    adminTemplatepage.qbLayoutDefinition(adminTemplatepage.ddlQbSelect,adminTemplatepage.ddlBenftProductValue,adminTemplatepage.ddlTierSelect,adminTemplatepage.ddlTierDiscrete);
		    adminTemplatepage.dataSourceFormula(adminTemplatepage.ddlDataSource,adminTemplatepage.ddlDataSourceValue , adminTemplatepage.chkFormulas);
		    adminTemplatepage.saveAdminTemplate(adminTemplatepage.btnSaveAdmin);
		    adminTemplatepage.closeToastMessage();
		    adminTemplatepage=homepage.navigateToAdminTemplate();
		    adminTemplatepage.activateAdminTemplate();
		    adminTemplatepage=homepage.navigateToAdminTemplate();
		    adminTemplatepage.launchAdminTemplate();
		    
		    adminTemplatepage.deleteAdminTemplateFilter();
		    adminTemplatepage.closeToastMessage();
			
	    }catch (Exception e) {
		throw new Exception(e);
	}
		}
	@Test(description = "Verify for the edit button on the Template details page when the template status is 'Draft'",groups = {"Smoke"})
	public void editDraftTemplate() throws Exception {
		try {
		
			adminTemplatepage=homepage.navigateToAdminTemplate();
		    adminTemplatepage.launchAdminTemplate();
		    testData = new Efficacies().readJsonFile("AdminTemplate.json");
		    long ln = JavaHelpers.generateRandomNumber();
		
		    String Admintemplate=testData.get("TemplateNameDiscreteBenftProd")+ln;
		    adminTemplatepage.moveToNewTemplatePage(adminTemplatepage.btnNew ,adminTemplatepage.lblNewTemplate);
		    adminTemplatepage.fillTemplateDetails(adminTemplatepage.ddlTemplateName ,Admintemplate,adminTemplatepage.txtTemplateDescription,testData.get("TemplateDescValue1"));
		    adminTemplatepage.qbLayoutDefinition(adminTemplatepage.ddlQbSelect,adminTemplatepage.ddlBenftProductValue,adminTemplatepage.ddlTierSelect,adminTemplatepage.ddlTierDiscrete);
		    adminTemplatepage.dataSourceFormula(adminTemplatepage.ddlDataSource,adminTemplatepage.ddlDataSourceValue , adminTemplatepage.chkFormulas);
		    adminTemplatepage.saveAdminTemplate(adminTemplatepage.btnSaveAdmin);
		    adminTemplatepage.closeToastMessage();
		    adminTemplatepage=homepage.navigateToAdminTemplate();		
		    adminTemplatepage.moveToEditPage();
		    adminTemplatepage.editAdminTemplate(adminTemplatepage.ddlTemplateName ,testData.get("TemplateNameDiscreteBenftProd")+ln);
		    adminTemplatepage.saveAdminTemplate(adminTemplatepage.btnSaveAdmin);
		    assertEquals(adminTemplatepage.success, adminTemplatepage.successResponse.getText());
		
		    adminTemplatepage=homepage.navigateToAdminTemplate();
		    adminTemplatepage.deleteAdminTemplateFilter();
		    adminTemplatepage.closeToastMessage();	
	}catch (Exception e) {
		throw new Exception(e);
	}}
	

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		driver.quit();
	}
}




