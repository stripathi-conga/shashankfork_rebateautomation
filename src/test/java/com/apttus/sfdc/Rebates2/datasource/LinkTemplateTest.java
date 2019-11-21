package com.apttus.sfdc.Rebates2.datasource;

import static org.testng.Assert.assertEquals;

import java.util.Map;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
import com.apttus.sfdc.Rebates2.library.LinkTemplatePage;
import com.apttus.sfdc.Rebates2.lightning.HomePage;
import com.apttus.sfdc.rudiments.utils.GeneralHelper;
import com.apttus.sfdc.rudiments.utils.SFDCRestUtils;

public class LinkTemplateTest {
	
	WebDriver driver;
	public  Rebatesinit rebatesinit;
	public HomePage homePage;
	LoginPage loginPage;
	Efficacies efficacies;
	public Map<String, String> testData;
	
	public LinkTemplatePage lnkTemplatepage;
	public AdminTemplatePage AdmTemplatepage;
	public HomePage homepage;
	public String homeURL;
	public String baseURL;
	Properties configProperty;
	StartUpPage startUpPage;
	public GenericPage genericpage;
	

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
	public void VerifyActiveTemplateName() throws Exception {
		try {
			AdmTemplatepage=homepage.navigateToAdminTemplate();
			testData = new Efficacies().readJsonFile("AdminTemplate.json");
			long ln = JavaHelpers.generateRandomNumber();
			AdmTemplatepage.MovetoNewTemplatePage(AdmTemplatepage.Newbtn ,AdmTemplatepage.Newtemplatelabel);
			AdmTemplatepage.FillTemplateDetails(AdmTemplatepage.TemplateName ,testData.get("TemplateNameDiscreteBenftProd")+ln,AdmTemplatepage.TemplateDescription,testData.get("TemplateDescValue1"));
			AdmTemplatepage.QBLayoutDefinition(AdmTemplatepage.QBselct,AdmTemplatepage.BenftPrdtValue,AdmTemplatepage.TierSelect,AdmTemplatepage.TierDiscrete);
			AdmTemplatepage.DataSourceFormula(AdmTemplatepage.DataSourcedorpdwn,AdmTemplatepage.DatasourceValue , AdmTemplatepage.BenifitCheckbox);
			AdmTemplatepage.SaveAdminTemplate(AdmTemplatepage.SaveAdmin);
			
			String ActvTemplate=testData.get("TemplateNameDiscreteBenftProd")+ln;
			AdmTemplatepage.CloseToastMessage();
			AdmTemplatepage.SFDCFilter(testData.get("ColumnName"),testData.get("ColumnOperator"),ActvTemplate);
			AdmTemplatepage.CloseToastMessage();
			
			AdmTemplatepage.ActivateTemplate();
			AdmTemplatepage.CloseToastMessage();
			lnkTemplatepage=homepage.navigateToLinkTemplate();
			testData = new Efficacies().readJsonFile("TemplateAssociation.json");
			lnkTemplatepage.RefreshLinkTemplate();
			lnkTemplatepage.MovetoNewLinkTemplatePage(lnkTemplatepage.NewLnkbtn ,lnkTemplatepage.Newtemplatelabel);
			lnkTemplatepage.VerifyActiveTemplate(lnkTemplatepage.prgType,lnkTemplatepage.prgtemplate);
			assertEquals(lnkTemplatepage.PgmTemplatedpdn.isDisplayed(), true);
			
			AdmTemplatepage=homepage.navigateToAdminTemplate();
			AdmTemplatepage.RemoveAllFilter();
			AdmTemplatepage.SFDCFilter(testData.get("ColumnName"),testData.get("ColumnOperator"),ActvTemplate);
			AdmTemplatepage.DeleteSFDCFilter();
			AdmTemplatepage.CloseToastMessage();
			AdmTemplatepage.deleteFilter();
			AdmTemplatepage.CloseToastMessage();
			
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Test(description = "Verify for the re-activation of the template association for the Active template, when there is further active association of the templates to the combination of program type and program sub-type selected during association",groups = {"Regression","Smoke","SFDC"})
	public void VerifySingleActiveTemplateAssociation() throws Exception {
		try {
			AdmTemplatepage=homepage.navigateToAdminTemplate();
			testData = new Efficacies().readJsonFile("TemplateAssociation.json");
			long ln = JavaHelpers.generateRandomNumber();
			AdmTemplatepage.MovetoNewTemplatePage(AdmTemplatepage.Newbtn ,AdmTemplatepage.Newtemplatelabel);
			AdmTemplatepage.FillTemplateDetails(AdmTemplatepage.TemplateName ,testData.get("TemplateNameDiscreteBenftProd")+ln,AdmTemplatepage.TemplateDescription,testData.get("TemplateDescValue1"));
			AdmTemplatepage.QBLayoutDefinition(AdmTemplatepage.QBselct,AdmTemplatepage.BenftPrdtValue,AdmTemplatepage.TierSelect,AdmTemplatepage.TierTiered);
			AdmTemplatepage.DataSourceFormula(AdmTemplatepage.DataSourcedorpdwn,AdmTemplatepage.DatasourceValue , AdmTemplatepage.BenifitCheckbox);
			AdmTemplatepage.SaveAdminTemplate(AdmTemplatepage.SaveAdmin);
			
			String ActvTemplate=testData.get("TemplateNameDiscreteBenftProd")+ln;
			AdmTemplatepage.CloseToastMessage();
			AdmTemplatepage.SFDCFilter(testData.get("ColumnName"),testData.get("ColumnOperator"),ActvTemplate);
			AdmTemplatepage.CloseToastMessage();
			AdmTemplatepage.ActivateTemplate();
			AdmTemplatepage.CloseToastMessage();
			
			AdmTemplatepage=homepage.navigateToAdminTemplate();
			AdmTemplatepage.RefreshLinkTemplate();
			AdmTemplatepage.RemoveFilterSave();
			AdmTemplatepage=homepage.navigateToAdminTemplate();
			AdmTemplatepage.RefreshLinkTemplate();
			AdmTemplatepage.MovetoNewTemplatePage(AdmTemplatepage.Newbtn ,AdmTemplatepage.Newtemplatelabel);
			AdmTemplatepage.FillTemplateDetails(AdmTemplatepage.TemplateName ,testData.get("TemplateNameTierQualifBenftProd")+ln,AdmTemplatepage.TemplateDescription,testData.get("TemplateDescValue1"));
			AdmTemplatepage.QBLayoutDefinition(AdmTemplatepage.QBselct,AdmTemplatepage.BenftPrdtValue,AdmTemplatepage.TierSelect,AdmTemplatepage.TierTiered);
			AdmTemplatepage.DataSourceFormula(AdmTemplatepage.DataSourcedorpdwn,AdmTemplatepage.DatasourceValue , AdmTemplatepage.BenifitCheckbox);
			AdmTemplatepage.SaveAdminTemplate(AdmTemplatepage.SaveAdmin);
			
			String Actv2Template=testData.get("TemplateNameTierQualifBenftProd")+ln;
			AdmTemplatepage.CloseToastMessage();
			AdmTemplatepage.SFDCFilter(testData.get("ColumnName"),testData.get("ColumnOperator"),Actv2Template);
			AdmTemplatepage.CloseToastMessage();
			AdmTemplatepage.WaitForPageToLoad();
			AdmTemplatepage.ActivateTemplate();
			AdmTemplatepage.CloseToastMessage();
			
			lnkTemplatepage=homepage.navigateToLinkTemplate();
			lnkTemplatepage.RefreshLinkTemplate();
			testData = new Efficacies().readJsonFile("TemplateAssociation.json");
			lnkTemplatepage.MovetoNewLinkTemplatePage(lnkTemplatepage.NewLnkbtn ,lnkTemplatepage.Newtemplatelabel);
			lnkTemplatepage.FillLinkTemplate(lnkTemplatepage.prgType,lnkTemplatepage.prgsubType,lnkTemplatepage.prgtemplate);
			lnkTemplatepage.SaveLink();
		
			lnkTemplatepage.SFDCFilter(testData.get("TemplateName"),testData.get("Operator"),Actv2Template);
			lnkTemplatepage=homepage.navigateToLinkTemplate();		
			lnkTemplatepage.ChangeStatusActive();
			
			AdmTemplatepage=homepage.navigateToAdminTemplate();
			lnkTemplatepage=homepage.navigateToLinkTemplate();
			lnkTemplatepage.RefreshLinkTemplate();
			lnkTemplatepage.MovetoNewLinkTemplatePage(lnkTemplatepage.NewLnkbtn ,lnkTemplatepage.Newtemplatelabel);
			lnkTemplatepage.FillLinkTemplate(lnkTemplatepage.prgType,lnkTemplatepage.prgsubType,lnkTemplatepage.prgtemplate);
			lnkTemplatepage.SaveLink();
			
			assertEquals(lnkTemplatepage.successresponse.getText(), lnkTemplatepage.ActiveMapExist, "An active mapping already exists.");
			lnkTemplatepage.CloseToastMessage();
			
			AdmTemplatepage=homepage.navigateToAdminTemplate();
			AdmTemplatepage.RemoveAllFilter();
			AdmTemplatepage.SFDCFilter(testData.get("ColumnName"),testData.get("ColumnOperator"),ActvTemplate);
			AdmTemplatepage.DeleteSFDCFilter();
			AdmTemplatepage.CloseToastMessage();
						
			lnkTemplatepage=homepage.navigateToLinkTemplate();
			lnkTemplatepage.RemoveAllFilter();
			lnkTemplatepage.SFDCFilter(testData.get("TemplateName"),testData.get("ColumnOperator"),Actv2Template);
			lnkTemplatepage.DeleteSFDCFilter();
			lnkTemplatepage.RemoveAllFilter();
			
			AdmTemplatepage=homepage.navigateToAdminTemplate();
			AdmTemplatepage.RemoveAllFilter();
			AdmTemplatepage.SFDCFilter(testData.get("ColumnName"),testData.get("ColumnOperator"),Actv2Template);
			AdmTemplatepage.DeleteSFDCFilter();
			AdmTemplatepage.CloseToastMessage();
			AdmTemplatepage.deleteFilter();
			AdmTemplatepage.CloseToastMessage();	
			
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Test(description ="Verify for the linking of active templates when the link status of the previously linked templates is Draft",groups = {"Regression","Smoke","SFDC"})
	public void VerifyMoreThanOneActiveTemplateAssociation() throws Exception {
		try {
			AdmTemplatepage=homepage.navigateToAdminTemplate();
			testData = new Efficacies().readJsonFile("TemplateAssociation.json");
			long ln = JavaHelpers.generateRandomNumber();
			AdmTemplatepage.MovetoNewTemplatePage(AdmTemplatepage.Newbtn ,AdmTemplatepage.Newtemplatelabel);
			AdmTemplatepage.FillTemplateDetails(AdmTemplatepage.TemplateName ,testData.get("TemplateNameActiveOne")+ln,AdmTemplatepage.TemplateDescription,testData.get("TemplateDescValue1"));
			AdmTemplatepage.QBLayoutDefinition(AdmTemplatepage.QBselct,AdmTemplatepage.BenftPrdtValue,AdmTemplatepage.TierSelect,AdmTemplatepage.TierTiered);
			AdmTemplatepage.DataSourceFormula(AdmTemplatepage.DataSourcedorpdwn,AdmTemplatepage.DatasourceValue , AdmTemplatepage.BenifitCheckbox);
			AdmTemplatepage.SaveAdminTemplate(AdmTemplatepage.SaveAdmin);
			
			String ActvTemplate=testData.get("TemplateNameActiveOne")+ln;
			AdmTemplatepage.CloseToastMessage();
			AdmTemplatepage.SFDCFilter(testData.get("ColumnName"),testData.get("ColumnOperator"),ActvTemplate);
			AdmTemplatepage.CloseToastMessage();
			AdmTemplatepage=homepage.navigateToAdminTemplate();
			AdmTemplatepage.ActivateTemplate();
			
			AdmTemplatepage=homepage.navigateToAdminTemplate();
			AdmTemplatepage.WaitForPageToLoad();
			AdmTemplatepage.RemoveFilterSave();
			AdmTemplatepage=homepage.navigateToAdminTemplate();
			AdmTemplatepage.RefreshLinkTemplate();
			AdmTemplatepage.MovetoNewTemplatePage(AdmTemplatepage.Newbtn ,AdmTemplatepage.Newtemplatelabel);
			AdmTemplatepage.FillTemplateDetails(AdmTemplatepage.TemplateName ,testData.get("TemplateNameActiveTwo")+ln,AdmTemplatepage.TemplateDescription,testData.get("TemplateDescValue1"));
			AdmTemplatepage.QBLayoutDefinition(AdmTemplatepage.QBselct,AdmTemplatepage.BenftPrdtValue,AdmTemplatepage.TierSelect,AdmTemplatepage.TierTiered);
			AdmTemplatepage.DataSourceFormula(AdmTemplatepage.DataSourcedorpdwn,AdmTemplatepage.DatasourceValue , AdmTemplatepage.BenifitCheckbox);
			AdmTemplatepage.SaveAdminTemplate(AdmTemplatepage.SaveAdmin);
			
			String Actv2Template=testData.get("TemplateNameActiveTwo")+ln;
			AdmTemplatepage.CloseToastMessage();
			AdmTemplatepage.SFDCFilter(testData.get("ColumnName"),testData.get("ColumnOperator"),Actv2Template);
			AdmTemplatepage.CloseToastMessage();
			AdmTemplatepage.WaitForPageToLoad();
			AdmTemplatepage.ActivateTemplate();
					
			lnkTemplatepage=homepage.navigateToLinkTemplate();
			testData = new Efficacies().readJsonFile("TemplateAssociation.json");
			lnkTemplatepage.RefreshLinkTemplate();
			lnkTemplatepage.MovetoNewLinkTemplatePage(lnkTemplatepage.NewLnkbtn ,lnkTemplatepage.Newtemplatelabel);
			lnkTemplatepage.FillLinkTemplate(lnkTemplatepage.prgType,lnkTemplatepage.prgsubType,lnkTemplatepage.prgtemplate);
			lnkTemplatepage.SaveLink();
			lnkTemplatepage.CloseToastMessage();
			lnkTemplatepage=homepage.navigateToLinkTemplate();
			lnkTemplatepage.RefreshLinkTemplate();
			
			lnkTemplatepage.MovetoNewLinkTemplatePage(lnkTemplatepage.NewLnkbtn ,lnkTemplatepage.Newtemplatelabel);
			lnkTemplatepage.FillmultipleLinkTemplate(lnkTemplatepage.prgType,lnkTemplatepage.prgsubType,lnkTemplatepage.prgtemplate);
			lnkTemplatepage.SaveLink();
			lnkTemplatepage=homepage.navigateToLinkTemplate();
			lnkTemplatepage.SFDCFilter(testData.get("TemplateName"),testData.get("ColumnOperator"),Actv2Template);
			lnkTemplatepage.ChangeStatusActive();
						
			AdmTemplatepage=homepage.navigateToAdminTemplate();
			AdmTemplatepage.RefreshLinkTemplate();
			lnkTemplatepage=homepage.navigateToLinkTemplate();
			lnkTemplatepage.DeleteSFDCFilter();
			lnkTemplatepage=homepage.navigateToLinkTemplate();
						
			AdmTemplatepage=homepage.navigateToAdminTemplate();
			AdmTemplatepage.RemoveAllFilter();
			AdmTemplatepage.SFDCFilter(testData.get("ColumnName"),testData.get("ColumnOperator"),ActvTemplate);
			
			AdmTemplatepage.DeleteSFDCFilter();
			AdmTemplatepage.CloseToastMessage();
			AdmTemplatepage.deleteFilter();
			AdmTemplatepage.CloseToastMessage();
			
			lnkTemplatepage=homepage.navigateToLinkTemplate();
			lnkTemplatepage.DeleteSFDCFilter();
			lnkTemplatepage=homepage.navigateToLinkTemplate();
			lnkTemplatepage.RemoveAllFilter();
			
			AdmTemplatepage.SFDCFilter(testData.get("ColumnName"),testData.get("ColumnOperator"),Actv2Template);
			AdmTemplatepage.DeleteSFDCFilter();
			AdmTemplatepage.CloseToastMessage();
			AdmTemplatepage.deleteFilter();
			AdmTemplatepage.CloseToastMessage();
			
			
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




