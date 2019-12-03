package com.apttus.sfdc.Rebates2.datasource;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

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
import com.apttus.customException.CustomException;
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
		
	@Test(description = "Verify for the mapping creation for one single Active Template with the unique combination of program Type and Program Sub-type selected",groups = {"Regression"})
	
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
			AdmTemplatepage.CloseToastMessage();
			AdmTemplatepage=homepage.navigateToAdminTemplate();
			
			AdmTemplatepage.ActivateTemplate();
			AdmTemplatepage.CloseToastMessage();
			lnkTemplatepage=homepage.navigateToLinkTemplate();
			testData = new Efficacies().readJsonFile("TemplateAssociation.json");
			lnkTemplatepage.MovetoNewLinkTemplatePage(lnkTemplatepage.NewLnkbtn ,lnkTemplatepage.Newtemplatelabel);
			lnkTemplatepage.VerifyActiveTemplate(lnkTemplatepage.prgType,lnkTemplatepage.prgtemplate);
			assertEquals(lnkTemplatepage.PgmTemplatedpdn.isDisplayed(), true);
			
			AdmTemplatepage=homepage.navigateToAdminTemplate();
			AdmTemplatepage.DeleteSFDCFilter();
			AdmTemplatepage.CloseToastMessage();
			
		} catch (Exception e) {
			throw new CustomException(e, driver);
		}
	}
	
	@Test(description = "Verify for the re-activation of the template association for the Active template, when there is further active association of the templates to the combination of program type and program sub-type selected during association",groups = {"Regression"})
	public void VerifySingleActiveTemplateAssociation() throws Exception {
		try {
	     	AdmTemplatepage=homepage.navigateToAdminTemplate();
			testData = new Efficacies().readJsonFile("TemplateAssociation.json");
			long ln = JavaHelpers.generateRandomNumber();
			AdmTemplatepage.RefreshLinkTemplate();
			AdmTemplatepage.MovetoNewTemplatePage(AdmTemplatepage.Newbtn ,AdmTemplatepage.Newtemplatelabel);
			AdmTemplatepage.FillTemplateDetails(AdmTemplatepage.TemplateName ,testData.get("TemplateNameActiveOne")+ln,AdmTemplatepage.TemplateDescription,testData.get("TemplateDescValue1"));
			AdmTemplatepage.QBLayoutDefinition(AdmTemplatepage.QBselct,AdmTemplatepage.BenftPrdtValue,AdmTemplatepage.TierSelect,AdmTemplatepage.TierTiered);
			AdmTemplatepage.DataSourceFormula(AdmTemplatepage.DataSourcedorpdwn,AdmTemplatepage.DatasourceValue , AdmTemplatepage.BenifitCheckbox);
			AdmTemplatepage.SaveAdminTemplate(AdmTemplatepage.SaveAdmin);
			AdmTemplatepage=homepage.navigateToAdminTemplate();
			
			String ActvTemplate=testData.get("TemplateNameActiveOne")+ln;
			AdmTemplatepage.CloseToastMessage();
			AdmTemplatepage.SFDCFilter(testData.get("ColumnName"),testData.get("ColumnOperator"),ActvTemplate);
			AdmTemplatepage.CloseToastMessage();
			AdmTemplatepage=homepage.navigateToAdminTemplate();
			AdmTemplatepage.ActivateTemplate();
		   /* Created and Activated a Template*/
			
			lnkTemplatepage=homepage.navigateToLinkTemplate();
			testData = new Efficacies().readJsonFile("TemplateAssociation.json");
			lnkTemplatepage.RefreshLinkTemplate();
			lnkTemplatepage.MovetoNewLinkTemplatePage(lnkTemplatepage.NewLnkbtn ,lnkTemplatepage.Newtemplatelabel);
			lnkTemplatepage.FillLinkTemplate(lnkTemplatepage.prgType,lnkTemplatepage.prgsubType,lnkTemplatepage.prgtemplate);
			lnkTemplatepage.SaveLink();
			lnkTemplatepage.ChangeStatusActive();
			lnkTemplatepage=homepage.navigateToLinkTemplate();
			/*Linked the 1sttemplate and Activated */
			
			lnkTemplatepage.ChangeStatustoInActive();
			
			/*Inactivate the1st activated Link*/
			lnkTemplatepage.RefreshLinkTemplate();
			lnkTemplatepage=homepage.navigateToLinkTemplate();
			lnkTemplatepage.MovetoNewLinkTemplatePage(lnkTemplatepage.NewLnkbtn ,lnkTemplatepage.Newtemplatelabel);
			lnkTemplatepage.FillLinkTemplate(lnkTemplatepage.prgType,lnkTemplatepage.prgsubType,lnkTemplatepage.prgtemplate);
			lnkTemplatepage.SaveLink();
			
			lnkTemplatepage.ChangeStatusActive();
			lnkTemplatepage.RefreshLinkTemplate();
			/*Activated 2nd link*/
			lnkTemplatepage.MoveToFilter();
			lnkTemplatepage.ChangeStatusInactivetoctive();
			assertEquals(lnkTemplatepage.CanNotSaveRecord, lnkTemplatepage.ErrorOccur.getText());
			
			lnkTemplatepage.CancelChangeStatus();	
			lnkTemplatepage.RefreshLinkTemplate();
			lnkTemplatepage.DeleteSFDCFilter();
			lnkTemplatepage.RefreshLinkTemplate();			
			lnkTemplatepage.DeleteSFDCFilter();
			
			AdmTemplatepage=homepage.navigateToAdminTemplate();
			AdmTemplatepage.SFDCFilter(testData.get("ColumnName"),testData.get("ColumnOperator"),ActvTemplate);
			AdmTemplatepage.RefreshLinkTemplate();
			AdmTemplatepage.DeleteSFDCFilter();
			/*AdmTemplatepage.CloseToastMessage();*/
			AdmTemplatepage.RemoveAllFilter();
			AdmTemplatepage.CloseToastMessage();
			
			
		} catch (Exception e) {
			throw new CustomException(e, driver);
		}
	}
	
	@Test(description ="Verify for the linking of active templates when the link status of the previously linked templates is Draft",groups = {"Regression"})
	public void VerifyMoreThanOneActiveTemplateAssociation() throws Exception {
		try {
			AdmTemplatepage=homepage.navigateToAdminTemplate();
			testData = new Efficacies().readJsonFile("TemplateAssociation.json");
			long ln = JavaHelpers.generateRandomNumber();
			AdmTemplatepage.RefreshLinkTemplate();
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
					
			lnkTemplatepage=homepage.navigateToLinkTemplate();
			testData = new Efficacies().readJsonFile("TemplateAssociation.json");
			lnkTemplatepage.RefreshLinkTemplate();
			lnkTemplatepage.MovetoNewLinkTemplatePage(lnkTemplatepage.NewLnkbtn ,lnkTemplatepage.Newtemplatelabel);
			lnkTemplatepage.FillLinkTemplate(lnkTemplatepage.prgType,lnkTemplatepage.prgsubType,lnkTemplatepage.prgtemplate);
			lnkTemplatepage.SaveLink();
			lnkTemplatepage.ChangeStatusActive();
			lnkTemplatepage=homepage.navigateToLinkTemplate();
			
			lnkTemplatepage.MovetoNewLinkTemplatePage(lnkTemplatepage.NewLnkbtn ,lnkTemplatepage.Newtemplatelabel);
			lnkTemplatepage.FillLinkTemplate(lnkTemplatepage.prgType,lnkTemplatepage.prgsubType,lnkTemplatepage.prgtemplate);
			lnkTemplatepage.SaveLink();
			assertEquals(lnkTemplatepage.successresponse.getText(), lnkTemplatepage.ActiveMapExist, "An active mapping already exists.");			
			
			lnkTemplatepage=homepage.navigateToLinkTemplate();
			lnkTemplatepage.DeleteSFDCFilter();			
			AdmTemplatepage=homepage.navigateToAdminTemplate();
			AdmTemplatepage.SFDCFilter(testData.get("ColumnName"),testData.get("ColumnOperator"),ActvTemplate);
			AdmTemplatepage.RefreshLinkTemplate();
			AdmTemplatepage.DeleteSFDCFilter();
			AdmTemplatepage.CloseToastMessage();
			AdmTemplatepage.RemoveAllFilter();
			AdmTemplatepage.CloseToastMessage();
			AdmTemplatepage.RefreshLinkTemplate();
			
		} catch (Exception e) {
			throw new CustomException(e, driver);
		}
	}
       @Test(description = "Verify for the inline edit on the Link Template Tab",groups = {"Regression"})
	
	   public void VerifyInlineEditDraft() throws Exception {
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
			
			lnkTemplatepage=homepage.navigateToLinkTemplate();
			testData = new Efficacies().readJsonFile("TemplateAssociation.json");
			lnkTemplatepage.RefreshLinkTemplate();
			lnkTemplatepage.MovetoNewLinkTemplatePage(lnkTemplatepage.NewLnkbtn ,lnkTemplatepage.Newtemplatelabel);
			lnkTemplatepage.FillLinkTemplate(lnkTemplatepage.prgType,lnkTemplatepage.prgsubType,lnkTemplatepage.prgtemplate);
			lnkTemplatepage.SaveLink();
			lnkTemplatepage.EditLinkAssociation();
			assertEquals(lnkTemplatepage.SuccessEdit,lnkTemplatepage.EdtSuccess );
			lnkTemplatepage.DeleteSFDCFilter();
			lnkTemplatepage.RefreshLinkTemplate();
			
			lnkTemplatepage=homepage.navigateToLinkTemplate();
			testData = new Efficacies().readJsonFile("TemplateAssociation.json");
			lnkTemplatepage.RefreshLinkTemplate();
			lnkTemplatepage.NavigateToLinkAssociation();
			lnkTemplatepage.MovetoNewLinkTemplatePage(lnkTemplatepage.NewLnkbtn ,lnkTemplatepage.Newtemplatelabel);
			lnkTemplatepage.FillLinkTemplate(lnkTemplatepage.prgType,lnkTemplatepage.prgsubType,lnkTemplatepage.prgtemplate);
			lnkTemplatepage.SaveLink();
			lnkTemplatepage.ChangeStatusActive();
			lnkTemplatepage.EditUpdateLinkAssociation();
			assertTrue(lnkTemplatepage.EdtSuccess.contains(lnkTemplatepage.CanNotModify));
			lnkTemplatepage.DeleteSFDCFilter();
			lnkTemplatepage.RefreshLinkTemplate();
			
			lnkTemplatepage=homepage.navigateToLinkTemplate();
			testData = new Efficacies().readJsonFile("TemplateAssociation.json");
			lnkTemplatepage.RefreshLinkTemplate();
			lnkTemplatepage.MovetoNewLinkTemplatePage(lnkTemplatepage.NewLnkbtn ,lnkTemplatepage.Newtemplatelabel);
			lnkTemplatepage.FillLinkTemplate(lnkTemplatepage.prgType,lnkTemplatepage.prgsubType,lnkTemplatepage.prgtemplate);
			lnkTemplatepage.SaveLink();
			lnkTemplatepage.ChangeStatustoInActive();
			lnkTemplatepage.EditUpdateLinkAssociation();
			assertTrue(lnkTemplatepage.EdtSuccess.contains(lnkTemplatepage.CanNotModify));
			lnkTemplatepage.DeleteSFDCFilter();
			lnkTemplatepage.RefreshLinkTemplate();
			
			AdmTemplatepage=homepage.navigateToAdminTemplate();
			AdmTemplatepage.SFDCFilter(testData.get("ColumnName"),testData.get("ColumnOperator"),ActvTemplate);
			AdmTemplatepage.RefreshLinkTemplate();
			AdmTemplatepage.DeleteSFDCFilter();
			AdmTemplatepage.CloseToastMessage();
			AdmTemplatepage.RemoveAllFilter();
			AdmTemplatepage.CloseToastMessage();
			
		} catch (Exception e) {
			throw new CustomException(e, driver);
		}
	}
	
       @Test(description = "Verify for the inline edit on the Link Template Tab",groups = {"Regression"})
   	
	   public void VerifyMandatoryFields() throws Exception {
		try {
			lnkTemplatepage=homepage.navigateToLinkTemplate();
			lnkTemplatepage.RefreshLinkTemplate();
			lnkTemplatepage.NavigateToLinkAssociation();
			
			lnkTemplatepage.MovetoNewLinkTemplatePage(lnkTemplatepage.NewLnkbtn ,lnkTemplatepage.Newtemplatelabel);
			lnkTemplatepage.VerifyValidationTemplate();
			assertEquals(lnkTemplatepage.TemplateFailValidation, lnkTemplatepage.TemplateFailResponse);
			lnkTemplatepage.RefreshLinkTemplate();
			lnkTemplatepage.VerifyValidationProgramType();
			assertEquals(lnkTemplatepage.ProgramTypeFailResponse, lnkTemplatepage.ProgramFailValidation);
			lnkTemplatepage.RefreshLinkTemplate();
			lnkTemplatepage.VerifyvalidationProgramSubType();
			assertEquals(lnkTemplatepage.ProgramSubTypeFailResponse, lnkTemplatepage.ProgramSubTypeFailValidation);
			lnkTemplatepage.NavigateToLinkAssociation();;
			
			
		}catch (Exception e) {
			throw new CustomException(e, driver);
		}
	}
       @Test(description ="Verify for the linking of active templates when the link status of the previously linked templates is active",groups = {"Regression"})
   	public void VerifyMoreThanOneActiveTemplateAssociation_Active() throws Exception {
   		try {
   					
   			lnkTemplatepage=homepage.navigateToLinkTemplate();
   			testData = new Efficacies().readJsonFile("TemplateAssociation.json");
   			lnkTemplatepage.MovetoNewLinkTemplatePage(lnkTemplatepage.NewLnkbtn ,lnkTemplatepage.Newtemplatelabel);
   			lnkTemplatepage.FillLinkTemplate(lnkTemplatepage.prgType,lnkTemplatepage.prgsubType,lnkTemplatepage.prgtemplate);
   			lnkTemplatepage.SaveLink();
   			
   			lnkTemplatepage.RefreshLinkTemplate();
   			lnkTemplatepage.MovetoNewLinkTemplatePage(lnkTemplatepage.NewLnkbtn ,lnkTemplatepage.Newtemplatelabel);
   			lnkTemplatepage.FillmultipleLinkTemplate(lnkTemplatepage.prgType,lnkTemplatepage.prgsubType,lnkTemplatepage.prgtemplate);
   			lnkTemplatepage.SaveLink();
   			
   			lnkTemplatepage.ChangeStatusActive();
   			lnkTemplatepage=homepage.navigateToLinkTemplate();
   			lnkTemplatepage.SFDCFilter(testData.get("TemplateName"),testData.get("ColumnOperator"),testData.get("FilterValue"));
   			lnkTemplatepage.ChangeStatusActive();
   			assertEquals(lnkTemplatepage.CanNotSaveRecord, lnkTemplatepage.ErrorOccur.getText());
   			
   			lnkTemplatepage.CancelChangeStatus();	
			lnkTemplatepage.RefreshLinkTemplate();
			lnkTemplatepage.DeleteSFDCFilter();
			lnkTemplatepage.RemoveAllFilter();
			lnkTemplatepage.DeleteSFDCFilter();
   			
   		} catch (Exception e) {
			throw new CustomException(e, driver);
		}
   	}
       
       @Test(description ="Verify for the linking of active templates when the link status of the previously linked templates is Inactive",groups = {"Regression"})
      	public void VerifyMoreThanOneActiveTemplateAssociation_InActive() throws Exception {
      		try {
      					
      			lnkTemplatepage=homepage.navigateToLinkTemplate();
      			testData = new Efficacies().readJsonFile("TemplateAssociation.json");
      			lnkTemplatepage.MovetoNewLinkTemplatePage(lnkTemplatepage.NewLnkbtn ,lnkTemplatepage.Newtemplatelabel);
      			lnkTemplatepage.FillLinkTemplate(lnkTemplatepage.prgType,lnkTemplatepage.prgsubType,lnkTemplatepage.prgtemplate);
      			lnkTemplatepage.SaveLink();
      			lnkTemplatepage.ChangeStatustoInActive();
      			
      			lnkTemplatepage.MovetoNewLinkTemplatePage(lnkTemplatepage.NewLnkbtn ,lnkTemplatepage.Newtemplatelabel);
      			lnkTemplatepage.FillmultipleLinkTemplate(lnkTemplatepage.prgType,lnkTemplatepage.prgsubType,lnkTemplatepage.prgtemplate);
      			lnkTemplatepage.SaveLink();
   			    lnkTemplatepage.DeleteSFDCFilter();
   			    lnkTemplatepage.DeleteSFDCFilter();
      			
      		} catch (Exception e) {
    			throw new CustomException(e, driver);
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




