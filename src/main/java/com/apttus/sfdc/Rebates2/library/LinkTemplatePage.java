package com.apttus.sfdc.Rebates2.library;

import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.apttus.sfdc.Rebates2.common.StartUpPage;

import junit.framework.Assert;

import com.apttus.sfdc.Rebates2.common.GenericPage;

public class LinkTemplatePage extends StartUpPage {
	
	@FindBy(xpath="//*[text()='Link Template']")
	public WebElement Newtemplatelabel;
	 
	@FindBy(css="div[title='New']")
	public WebElement NewLnkbtn;
	
	@FindBy(xpath="//span[@class='slds-media__body']/span[@title='Rebates 2.0']")
	public WebElement PgmTypedpdn;
	
	@FindBy(xpath="//span[@class='slds-media__body']/span[@title='Point Accrual']")
	public WebElement prgrmSubTypedpdn;
	@FindBy(xpath="//span[@class='slds-media__body']/span[@title='Promotion']")
	public WebElement prgrmSubTypedpdn2;
	
	@FindBy(xpath="//span[@class='slds-media__body']/span[contains(text(),'Active')]")
	public WebElement PgmTemplatedpdn;
	
	@FindBy(xpath="//span[@class='slds-media__body']/span[contains(text(),'Two')]")
	public WebElement PgmTemplatedpdn2;
	@FindBy(xpath="//span[@class='slds-media__body']/span[contains(text(),'One')]")
	public WebElement PgmTemplatedpdn1;
	
	@FindBy(xpath="//*[@name='programType']")
	public WebElement prgType;
	
	@FindBy(xpath="//*[@name='programSubType']")
	public WebElement prgsubType;
	
	@FindBy(xpath="//*[@name='programTemplate']")
	public WebElement prgtemplate;
	
	@FindBy(xpath="//button[text()='Save']")
	public  WebElement SaveLnk;
		
	@FindBy(xpath="//*[text()='Q & B Layout']//..//lightning-base-combobox-item[@data-value='Benefit Product']")
	public  WebElement BenftPrdtValue;
	
	@FindBy(xpath="//*[text()='Tier']//..//lightning-base-combobox-item[@data-value='Discrete']")
	public  WebElement TierDiscrete;
	
	@FindBy(xpath="//*[text()='Tier']//..//lightning-base-combobox-item[@data-value='Tiered']")
	public  WebElement TierTiered;
	
	@FindBy(xpath="//*[text()='Data Source']//..//span[text()='DS-Test']")
	public  WebElement DatasourceValue;
	
	@FindBy(xpath="//*[text()='Q & B Layout']//..//lightning-base-combobox-item[@data-value='Qualification & Benefit Product']")
	public  WebElement QualftnBenftFormula;
	
	@FindBy(css="span[class*='toastMessage']")
	public WebElement successresponse;
	
	@FindBy(css="span[class='triggerLinkText selectedListView uiOutputText']")
	public WebElement Recentlyviewedlnk;
    
    @FindBy(xpath="//span[text()='All']")
	public WebElement Allviewedlnk;
    
    @FindBy(xpath="//*[@data-key='filterList']")
   	public WebElement filtericon;
    
    @FindBy(css="[class*=' addFilter']")
   	public WebElement AddFilterlnk;
    
    @FindBy(xpath="//span[text()='Cancel']")
   	public WebElement Cancelsts;
    
    @FindBy(xpath="//*[@id=\"brandBand_1\"]/div/div[1]/div[5]//section/div/footer/button[1]")
   	public WebElement Canceledt;
  
    
    @FindBy(xpath="//*[text()='Field']/../..//input[@type='text']")
   	public WebElement SelectField;
    
    @FindBy(xpath="//*[text()='Operator']/../..//input[@class='slds-input slds-combobox__input']")
   	public WebElement SelectOperator;
    
    @FindBy(css="[class='filterTextInput valueInput input uiInput uiInputText uiInput--default uiInput--input']")
   	public WebElement EnterValue;
    
    @FindBy(css="button[class='slds-button slds-button--neutral doneButton uiButton'] span")    
   	public WebElement flrDonebtn;
  
    @FindBy(css="[class='slds-button slds-button_brand saveButton headerButton']")
   	public WebElement flrSavebtn;
    
    @FindBy(css="[class='slds-text-color_weak slds-text-body_small slds-truncate virtualAutocompleteOptionSubtext']")
   	public WebElement AlllnkPinnedlist;
    
    @FindBy(css="[class='triggerLinkTextAndIconWrapper slds-p-right--x-large']")
   	public WebElement Alllink;
    
    @FindBy(css="span[class='countSortedByFilteredBy']")
   	public WebElement PageSortedbynamelabel;

    @FindBy(css="[class='removeAll']")
   	public WebElement RemoveAllftr;
    
	@FindBy(css = "a[title='Delete']")
	public WebElement DeleteShowAction;
	
	@FindBy(css = "span[class='genericSummary uiOutputText']")
	public WebElement ErrorOccur;
	
	
	@FindBy(xpath = "//a[@title='Delete']")
	public WebElement showDeleteAction;
	
	@FindBy(xpath = "//button[@title='Delete']")
	public WebElement ConfirmDeleteAction;
	
	
	@FindBy(css = "a[title='Edit']")
	public WebElement EditShowAction;
	
	@FindBy(css = "button[title='Delete'] span[class=' label bBody']")
	public WebElement DeleteAction;
	
	@FindBy(xpath = "//span[text()='1 item • Sorted by Name • Filtered by my templates - Name • ']")
	public WebElement SortedItemlabel;
	 
	@FindBy(xpath="//*[@id=\"brandBand_1\"]//..//span/div/a/lightning-icon/lightning-primitive-icon")
	public  WebElement ShowMore;
	
	@FindBy(css="svg[class='slds-icon slds-icon_small']")
   	public WebElement Cancelbtn;
	
	@FindBy(css="a[title='Edit']")
   	public WebElement EdtshowMore;
	
	
	@FindBy(css="button[title='Close']")
   	public WebElement CloseToastResponse;
	
	@FindBy(css="span[class*='toastMessage']")
   	public WebElement ToastResponse;
	
	@FindBy(css="heading--small")
   	public WebElement ToastErrorTitle;
	
	@FindBy(css="button[class='slds-button slds-button_neutral']")
   	public WebElement Editbtn2;
			
	@FindBy(linkText="/lightning/o/Program_Template__c/home")
   	public WebElement Templatelnk;	
	
	@FindBy(xpath="//*[@name='programTemplate']")
   	public WebElement Pgmtemplatedpdn;
	
	@FindBy(xpath="//*[contains(text(),'Edit Linked Status')]")
   	public WebElement EditStatusbtn;
	
	@FindBy(css="a[class='select']")
   	public WebElement draftlnk;
	
	@FindBy(css="a[title='Active']")
   	public WebElement Activelnk;
	@FindBy(css="a[title='Inactive']")
   	public WebElement InActivelnk;
	
	@FindBy(css="span[title='Id']")
   	public WebElement sortedid;
	
	@FindBy(css="button[class='slds-button slds-button--neutral test-saveButton uiButton--default uiButton--brand uiButton']")
   	public WebElement SaveStatuslnk;
	
	@FindBy(css="button[title='Stay on this List']")
   	public WebElement StayListlnk;
	
	@FindBy(xpath="//button[@title='Close this window']")
   	public WebElement CloseStayListlnk;
	
	@FindBy(css="a[class='uiButton--default uiButton--neutral uiButton picklistButton']")
   	public WebElement Valuedropdown;
	
	@FindBy(css="a[title='Inactive']")
   	public WebElement Inactivefltr;
	
	
	
	@FindBy(css="a[title*='PGT']")
   	public WebElement Pgromlnk;
	
	@FindBy(css="a[title*='Delete']")
   	public WebElement Deletelnk;
		
	public String success="Template saved successfully";
	public String NewTemplate="New Template";
	public String ActiveMapExist="An active mapping already exists.";
	public String CanNotSaveRecord="Can't save records with errors.";
	public String SuccessEdit="Program template is linked";
	public String EdtSuccess;
	public String Duplicatesavefail="Name already exists. Please provide a different name.";
	public String CanNotModify="Cannot modify the mapping for an";
    GenericPage genericPage;

	public String TemplateFailResponse;
	public String TemplateFailValidation="Required fields are missing: [Template Name]";
	public String ProgramSubTypeFailValidation="Invalid Program Sub Type";
	public String ProgramFailValidation="Invalid Program Type";
	public String ProgramTypeFailResponse;

	public String ProgramSubTypeFailResponse;
    

	public LinkTemplatePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		sfdcAcolyte.setWaitTime(60);
		genericPage = new GenericPage(driver);
	}

	public LinkTemplatePage MovetoNewLinkTemplatePage(WebElement newbutton, WebElement Labelnewtemplate) throws Exception {
		Thread.sleep(3000);//remove
		sfdcAcolyte.waitTillElementIsVisible(newbutton).click(newbutton);
		sfdcAcolyte.waitTillElementIsVisible(Labelnewtemplate);
		
		return PageFactory.initElements(driver, LinkTemplatePage.class);
		
	}

	public LinkTemplatePage FillLinkTemplate(WebElement prgrmType, WebElement prgrmSubType, WebElement prgrmTemplate) throws Exception {
		
		sfdcAcolyte.waitTillElementIsVisible(prgrmType).click(prgrmType);
		WebDriverWait wait = new WebDriverWait(driver, 15);
		WebElement ExpWaitProgramType=wait.until(ExpectedConditions.visibilityOf(PgmTypedpdn));
		sfdcAcolyte.jsClick(ExpWaitProgramType);
				sfdcAcolyte.
		            waitTillElementIsVisible(prgrmSubType).click(prgrmSubType);
		Thread.sleep(1000);//remove
		sfdcAcolyte.
		            waitTillElementIsVisible(prgrmSubTypedpdn).click(prgrmSubTypedpdn);
		Thread.sleep(1000);//remove
		sfdcAcolyte.
		            waitTillElementIsVisible(prgrmTemplate).click(prgrmTemplate).
		            waitTillElementIsVisible(PgmTemplatedpdn1).jsClick(PgmTemplatedpdn1);
		
		return PageFactory.initElements(driver, LinkTemplatePage.class);
	}
	
     public LinkTemplatePage FillmultipleLinkTemplate(WebElement prgrmType, WebElement prgrmSubType, WebElement prgrmTemplate) throws Exception {
		
		sfdcAcolyte.waitTillElementIsVisible(prgrmType).click(prgrmType).
		            waitTillElementIsVisible(PgmTypedpdn).click(PgmTypedpdn).
		            waitTillElementIsVisible(prgrmSubType).click(prgrmSubType).
		            waitTillElementIsVisible(prgrmSubTypedpdn).click(prgrmSubTypedpdn).
		            waitTillElementIsVisible(prgrmTemplate).click(prgrmTemplate).
		            waitTillElementIsVisible(PgmTemplatedpdn2).jsClick(PgmTemplatedpdn2);
		
		return PageFactory.initElements(driver, LinkTemplatePage.class);
	}

	public LinkTemplatePage SaveLink() throws Exception {
		
		sfdcAcolyte.waitTillElementIsVisible(SaveLnk).click(SaveLnk);
		Thread.sleep(2000);
		sfdcAcolyte.waitTillElementIsVisible(successresponse);
		CloseToastMessage();
		Thread.sleep(1000);
		return PageFactory.initElements(driver, LinkTemplatePage.class);
		
	}

	public void VerifyActiveTemplate( WebElement prgrmType,WebElement prgrmTemplate) throws Exception {
		 sfdcAcolyte.waitTillElementIsVisible(prgrmType).click(prgrmType).
         waitTillElementIsVisible(PgmTypedpdn).click(PgmTypedpdn).
         waitTillElementIsVisible(prgrmTemplate).click(prgrmTemplate); 
				
	}

	public LinkTemplatePage SFDCFilter(String ColumnName, String ColumnOperator, String FilterValue) throws Exception {
		
		sfdcAcolyte.waitTillElementIsVisible(Recentlyviewedlnk).click(Recentlyviewedlnk).waitTillElementIsVisible(Allviewedlnk).
	                waitTillElementIsVisible(Allviewedlnk).click(Allviewedlnk).waitTillElementIsVisible(filtericon).click(filtericon);
		sfdcAcolyte.waitTillElementIsVisible(AddFilterlnk).click(AddFilterlnk);
		sfdcAcolyte.waitTillElementIsVisible(SelectField).click(SelectField).sendKeysTo(SelectField, ColumnName).sendBoardKeys(Keys.ENTER);
		sfdcAcolyte.waitTillElementIsVisible(SelectOperator).click(SelectOperator).sendKeysTo(SelectOperator, ColumnOperator).sendBoardKeys(Keys.ENTER).
                    waitTillElementIsVisible(EnterValue).sendKeysTo(EnterValue, FilterValue).
                    waitTillElementIsVisible(flrDonebtn).click(flrDonebtn).
                    waitTillElementIsVisible(flrSavebtn).click(flrSavebtn);
		
		return PageFactory.initElements(driver, LinkTemplatePage.class);
		
	}
public LinkTemplatePage SFDCFilter2(String ColumnName, String ColumnOperator, String FilterValue) throws Exception {
		
		sfdcAcolyte.waitTillElementIsVisible(Recentlyviewedlnk).click(Recentlyviewedlnk).waitTillElementIsVisible(Allviewedlnk).
	                waitTillElementIsVisible(Allviewedlnk).click(Allviewedlnk).waitTillElementIsVisible(filtericon).click(filtericon);
		sfdcAcolyte.waitTillElementIsVisible(AddFilterlnk).click(AddFilterlnk);
		sfdcAcolyte.waitTillElementIsVisible(SelectField).click(SelectField).sendKeysTo(SelectField, ColumnName).sendBoardKeys(Keys.ENTER);
		sfdcAcolyte.waitTillElementIsVisible(SelectOperator).click(SelectOperator).sendKeysTo(SelectOperator, ColumnOperator).sendBoardKeys(Keys.ENTER);
		
		sfdcAcolyte.waitTillElementIsVisible(Valuedropdown).jsClick(Inactivefltr).
                    waitTillElementIsVisible(flrDonebtn).click(flrDonebtn).
                    waitTillElementIsVisible(flrSavebtn).click(flrSavebtn);
		
		return PageFactory.initElements(driver, LinkTemplatePage.class);
		
	}

	 public LinkTemplatePage DeleteSFDCFilter() throws Exception {
		
		 Thread.sleep(2000);
	      sfdcAcolyte.waitTillElementIsVisible(ShowMore).
	      jsClick(ShowMore);
	      /*Thread.sleep(5000);*/
	      WebDriverWait wait = new WebDriverWait(driver, 15);
			WebElement ExpWaitDelete=wait.until(ExpectedConditions.visibilityOf(DeleteShowAction));
	      sfdcAcolyte.jsClick(ExpWaitDelete).
	      jsClick(ConfirmDeleteAction);
	     
	   
	      return PageFactory.initElements(driver, LinkTemplatePage.class);
	}

      public  LinkTemplatePage CloseToastMessage() throws Exception {
		
		sfdcAcolyte.waitTillElementIsClickable(CloseToastResponse).click(CloseToastResponse);
		
		return PageFactory.initElements(driver, LinkTemplatePage.class);
		
	}


	public LinkTemplatePage deleteFilter() throws Exception {
		
		sfdcAcolyte.waitTillElementIsVisible(RemoveAllftr).click(RemoveAllftr).click(flrSavebtn);
	
	return PageFactory.initElements(driver, LinkTemplatePage.class);
}

	public void ChangeStatusActive() throws Exception {
		Thread.sleep(1000);
		sfdcAcolyte.jsClick(EditStatusbtn);		
		sfdcAcolyte.jsClick(draftlnk).jsClick(Activelnk);
		sfdcAcolyte.jsClick(SaveStatuslnk);
		Thread.sleep(1000);
	}

	public void RemoveAllFilter() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(Recentlyviewedlnk).click(Recentlyviewedlnk).waitTillElementIsVisible(Allviewedlnk).
        waitTillElementIsVisible(Allviewedlnk).click(Allviewedlnk).waitTillElementIsVisible(filtericon).click(filtericon);

        sfdcAcolyte.waitTillElementIsVisible(RemoveAllftr).click(RemoveAllftr).click(flrSavebtn);
        sfdcAcolyte.waitTillElementIsVisible(Alllink).click(Alllink).
                    waitTillElementIsVisible(AlllnkPinnedlist).click(AlllnkPinnedlist);
		
	}

	public void RefreshLinkTemplate() throws Exception {
		sfdcAcolyte.refreshPage();
		
	}

	public void MoveToFilter() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(Recentlyviewedlnk).click(Recentlyviewedlnk).waitTillElementIsVisible(Allviewedlnk).
        waitTillElementIsVisible(Allviewedlnk).click(Allviewedlnk).waitTillElementIsVisible(filtericon).click(filtericon);
		
	}

	public void DeleteActiveLinkAndFilter() throws Exception {
		
		sfdcAcolyte.waitTillElementIsVisible(Pgromlnk);
		Thread.sleep(8000);
		sfdcAcolyte.click(Pgromlnk).click(Deletelnk);
		sfdcAcolyte.waitTillElementIsVisible(DeleteAction).click(DeleteAction);
		
		sfdcAcolyte.waitTillElementIsVisible(Recentlyviewedlnk).click(Recentlyviewedlnk).waitTillElementIsVisible(Allviewedlnk).
        waitTillElementIsVisible(Allviewedlnk).click(Allviewedlnk).waitTillElementIsVisible(filtericon).click(filtericon);

sfdcAcolyte.waitTillElementIsVisible(RemoveAllftr).click(RemoveAllftr).click(flrSavebtn);
sfdcAcolyte.waitTillElementIsVisible(Alllink).click(Alllink).
        waitTillElementIsVisible(AlllnkPinnedlist).click(AlllnkPinnedlist);
		
	
	}

	public void waitTillPageLoad() throws Exception {
		sfdcAcolyte.wait(4000);
	
	}

	public void ChangeStatustoInActive() throws Exception {
		
		Thread.sleep(1000);
		sfdcAcolyte.jsClick(EditStatusbtn).
		            jsClick(draftlnk).jsClick(InActivelnk);
		
		sfdcAcolyte.jsClick(SaveStatuslnk);
		Thread.sleep(2000);
		 
	}
	public void ChangeStatusInactivetoctive() throws Exception {
		Thread.sleep(1000);
		sfdcAcolyte.jsClick(EditStatusbtn).
		            jsClick(draftlnk).jsClick(Activelnk);
		
		sfdcAcolyte.jsClick(SaveStatuslnk);
		sfdcAcolyte.waitTillElementIsVisible(ErrorOccur);
		
	}

	public void CancelChangeStatus() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(Cancelsts).click(Cancelsts);
		
	}
	public void CancelEdit() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(Canceledt).click(Canceledt);
		
	}

	public void EditLinkAssociation() throws Exception {
		/*sfdcAcolyte.waitTillElementIsVisible(Pgromlnk).click(Pgromlnk);
		sfdcAcolyte.waitTillElementIsVisible(EditShowAction).click(EditShowAction);
		UpdateLinkTemplate();
		sfdcAcolyte.waitTillElementIsVisible(SaveLnk).
		waitTillElementIsClickable(SaveLnk).click(SaveLnk);*/
		
		sfdcAcolyte.waitTillElementIsVisible(ShowMore).
	      jsClick(ShowMore);
		sfdcAcolyte.waitTillElementIsVisible(EdtshowMore);
		sfdcAcolyte.jsClick(EdtshowMore);
		Thread.sleep(3000);
		
		sfdcAcolyte.waitTillElementIsVisible(prgsubType).click(prgsubType);
        sfdcAcolyte.waitTillElementIsVisible(prgrmSubTypedpdn2);
		
        sfdcAcolyte.waitTillElementIsVisible(SaveLnk).waitTillElementIsClickable(SaveLnk).click(SaveLnk);
		sfdcAcolyte.waitTillElementIsVisible(successresponse);
		EdtSuccess=successresponse.getText();
		CloseToastMessage();
		
	}
	public void EditUpdateLinkAssociation() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(Pgromlnk).click(Pgromlnk);
		sfdcAcolyte.waitTillElementIsVisible(EditShowAction).click(EditShowAction);
		UpdateLinkTemplate();
		sfdcAcolyte.waitTillElementIsVisible(SaveLnk).click(SaveLnk);
		
		sfdcAcolyte.waitTillElementIsVisible(successresponse);
		EdtSuccess=successresponse.getText();
		NavigateToLinkAssociation();
		
		
	}

	private void UpdateLinkTemplate() throws Exception {
		String Currenturl=sfdcAcolyte.getCurrentURL();
		sfdcAcolyte.navigateTo(Currenturl);
		sfdcAcolyte.navigateTo(Currenturl);
		sfdcAcolyte.waitTillElementIsVisible(prgsubType).click(prgsubType);
        sfdcAcolyte.waitTillElementIsVisible(prgrmSubTypedpdn2);
        Thread.sleep(3000);
        sfdcAcolyte.click(prgrmSubTypedpdn2);	
	}

	public void NavigateToLinkAssociation() throws Exception {
		String LinkPageurl=sfdcAcolyte.getCurrentURL();
		sfdcAcolyte.navigateTo(LinkPageurl);
		
	}

	public void VerifyValidationTemplate() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(prgType).click(prgType);
		
		sfdcAcolyte.waitTillElementIsVisible(PgmTypedpdn).click(PgmTypedpdn);
		
		sfdcAcolyte. waitTillElementIsVisible(prgsubType).click(prgsubType);
		
		sfdcAcolyte. waitTillElementIsVisible(prgrmSubTypedpdn).click(prgrmSubTypedpdn);
        sfdcAcolyte.waitTillElementIsVisible(SaveLnk).click(SaveLnk);
		
		sfdcAcolyte.waitTillElementIsVisible(successresponse);
		TemplateFailResponse=successresponse.getText();
		CloseToastMessage();
		
	}

	public void VerifyValidationProgramType() throws Exception {
		
		sfdcAcolyte.waitTillElementIsVisible(prgtemplate).click(prgtemplate).
        waitTillElementIsVisible(PgmTemplatedpdn1).jsClick(PgmTemplatedpdn1);
		sfdcAcolyte. waitTillElementIsVisible(prgsubType).jsClick(prgsubType);
		
		WebDriverWait wait = new WebDriverWait(driver, 15);
		WebElement ExpWaitSubtype=wait.until(ExpectedConditions.visibilityOf(prgrmSubTypedpdn));
		sfdcAcolyte.jsClick(ExpWaitSubtype);
        sfdcAcolyte.waitTillElementIsVisible(SaveLnk).click(SaveLnk);
		sfdcAcolyte.waitTillElementIsVisible(successresponse);
		ProgramTypeFailResponse=successresponse.getText();
		CloseToastMessage();
		
	}

	public void VerifyvalidationProgramSubType() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(prgtemplate).click(prgtemplate).
        waitTillElementIsVisible(PgmTemplatedpdn1).jsClick(PgmTemplatedpdn1);
        sfdcAcolyte.waitTillElementIsVisible(prgType).click(prgType);
		
		sfdcAcolyte.waitTillElementIsVisible(PgmTypedpdn).click(PgmTypedpdn);;
        sfdcAcolyte.waitTillElementIsVisible(SaveLnk).click(SaveLnk);
		sfdcAcolyte.waitTillElementIsVisible(successresponse);
		ProgramSubTypeFailResponse=successresponse.getText();
		CloseToastMessage();
		
	}

	public void CancelLink() throws Exception {
		
		
	}
	}
	

	


	
