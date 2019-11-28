package com.apttus.sfdc.Rebates2.library;

import static org.testng.Assert.assertEquals;
import java.util.List;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.apttus.sfdc.Rebates2.common.StartUpPage;
import com.apttus.sfdc.Rebates2.common.GenericPage;

public class AdminTemplatePage extends StartUpPage {
	
	@FindBy(xpath="//*[text()='New Template']")
	public WebElement Newtemplatelabel;
	 
	@FindBy(css="div[title='New']")
	public WebElement Newbtn;
	 
	@FindBy(xpath="//input[@placeholder='Enter a name']")
	public WebElement TemplateName;
	
	@FindBy(xpath="//textarea[@name='Description']")
	public WebElement TemplateDescription;
	
	
		
	@FindBy(xpath="//input[@name='Q&B Layout']")
	public WebElement QBselct;
	
	@FindBy(xpath="//*[text()='Tier']//..//input[@type='text']")
	public WebElement TierSelect;
	
	@FindBy(xpath="//span[@class='slds-checkbox_faux']")
	public  List<WebElement> BenifitCheckbox;
	
	@FindBy(xpath="//*[text()='Data Source']//..//input[@placeholder='Select an Option']")
	public  WebElement DataSourcedorpdwn;
	
	@FindBy(xpath="//button[@class='slds-button slds-button_brand']")
	public  WebElement SaveAdmin;
	
	@FindBy(xpath="//span[@title='Benefit Product']")
	public  WebElement BenftPrdtValue;
	
	@FindBy(xpath="//span[@title='Discrete']")
	public  WebElement TierDiscrete;
	
	@FindBy(xpath="//span[@title='Tier']")
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
    
    @FindBy(css="[title*='Automation']")
	public WebElement Edtlink;
    
    @FindBy(css="span[title='Name']")
   	public WebElement titleNamecolmn;
    
    @FindBy(xpath="//*[@data-key='filterList']")
   	public WebElement filtericon;
    
    @FindBy(css="[class*=' addFilter']")
   	public WebElement AddFilterlnk;
    
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
	
	@FindBy(css = "a[title='Edit']")
	public WebElement EditShowAction;
	
	@FindBy(xpath = "//a[@title='Delete']")
	public WebElement showDeleteAction;
	
	@FindBy(xpath = "//button[@title='Delete']")
	public WebElement ConfirmDeleteAction;
	
	@FindBy(css = "button[title='Delete'] span[class=' label bBody']")
	public WebElement DeleteAction;
	
	@FindBy(xpath = "//span[text()='1 item • Sorted by Name • Filtered by my templates - Name • ']")
	public WebElement SortedItemlabel;
	 
	@FindBy(xpath="//*[@id=\"brandBand_1\"]//..//span/div/a/lightning-icon/lightning-primitive-icon")
	public  WebElement ShowMore;
	
	@FindBy(xpath="//*[text()='Activate']")
	public  WebElement Activatebtn;
		
	@FindBy(css="svg[class='slds-icon slds-icon_small']")
   	public WebElement Cancelbtn;
		
	@FindBy(xpath="//span[text()='close']")
   	public WebElement Closebtn;
	
	
	@FindBy(css="button[title='Close']")
   	public WebElement CloseToastResponse;
	
	@FindBy(css="span[class*='toastMessage']")
   	public WebElement ToastResponse;
	
	@FindBy(css="heading--small")
   	public WebElement ToastErrorTitle;
	
	@FindBy(css="button[class='slds-button slds-button_neutral']")
   	public WebElement Editbtn2;
	
	@FindBy(xpath="//h3[text()='Matching all of these filters']")
   	public WebElement ExistingFilter;
	
			
	@FindBy(linkText="/lightning/o/Program_Template__c/home")
   	public WebElement Templatelnk;		
			
	
	public String success="Template saved successfully";
	public String NewTemplate="New Template";
	public String TemplateEmpty="Program name cannot be empty";
	
	public String Duplicatesavefail="Name already exists. Please provide a different name.";
    GenericPage genericPage;

	public AdminTemplatePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		sfdcAcolyte.setWaitTime(60);
		genericPage = new GenericPage(driver);
	}

	public AdminTemplatePage MovetoNewTemplatePage(WebElement newbutton, WebElement Labelnewtemplate) throws Exception {
		
		sfdcAcolyte.waitTillElementIsVisible(newbutton).jsClick(newbutton);
		sfdcAcolyte.waitTillElementIsVisible(Labelnewtemplate);
		
		return PageFactory.initElements(driver, AdminTemplatePage.class);
	}

	public AdminTemplatePage FillTemplateDetails(WebElement templateNametxt,String TemplateNameValue, WebElement templateDescriptiontxt,String TemplateDescValue) throws Exception {
		
		sfdcAcolyte.waitTillElementIsVisible(templateNametxt).
		            sendKeysTo(templateNametxt, TemplateNameValue).
		            waitTillElementIsClickable(TemplateDescription).clickAndSendkeys(TemplateDescription, TemplateDescValue);
		
		return PageFactory.initElements(driver, AdminTemplatePage.class);	
	}

	public AdminTemplatePage QBLayoutDefinition(WebElement QBselct, WebElement QBValue,WebElement TierSelect,WebElement TierValue) throws Exception {
		sfdcAcolyte.waitTillElementIsClickable(QBselct).
		           click(QBselct).
		           waitTillElementIsVisible(QBValue).
		           click(QBValue)
                   .waitTillElementIsClickable(TierSelect).
                   click(TierSelect).waitTillElementIsVisible(TierValue).
                   click(TierValue);

       return PageFactory.initElements(driver, AdminTemplatePage.class);
		
	}

	public AdminTemplatePage DataSourceFormula(WebElement DataSourcedorpdwn,WebElement DatasourceValue ,List<WebElement> BenifitCheckbox) throws Exception {
		
		sfdcAcolyte.waitTillElementIsVisible(DataSourcedorpdwn).
		            waitTillElementIsClickable(DataSourcedorpdwn).
		            click(DataSourcedorpdwn).
		            waitTillElementIsVisible(DatasourceValue).
		            jsScrollAndClick(DatasourceValue);
		Thread.sleep(2000);
		
		for (int i = 0; i < BenifitCheckbox.size()-3; i++) {
			sfdcAcolyte.waitTillElementIsClickable(BenifitCheckbox.get(0));
			BenifitCheckbox.get(i).click();
			
		}
		return PageFactory.initElements(driver, AdminTemplatePage.class);
		
	}
         public AdminTemplatePage SaveDataSourceFormula(WebElement DataSourcedorpdwn,WebElement DatasourceValue ,List<WebElement> BenifitCheckbox) throws Exception {
		
		sfdcAcolyte.waitTillElementIsVisible(DataSourcedorpdwn).
		            waitTillElementIsClickable(DataSourcedorpdwn).
		            click(DataSourcedorpdwn).
		            waitTillElementIsVisible(DatasourceValue).
		            jsScrollAndClick(DatasourceValue);
		
		sfdcAcolyte.click(SaveAdmin);
		sfdcAcolyte.waitTillElementIsVisible(CloseToastResponse).waitTillElementIsClickable(CloseToastResponse).click(CloseToastResponse);	
		
		return PageFactory.initElements(driver, AdminTemplatePage.class);
		
	}
	public AdminTemplatePage SaveAdminTemplate(WebElement SaveAdmin) throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(SaveAdmin).click(SaveAdmin);
		
		sfdcAcolyte.waitTillElementIsVisible(successresponse);
		return PageFactory.initElements(driver, AdminTemplatePage.class);		
		
	}
	public void WaitTemplate(WebElement SaveAdmin) throws Exception {
		
		sfdcAcolyte.waitTillElementIsVisible(successresponse);
		
	}
	 public AdminTemplatePage SFDCFilter(String ColumnName, String ColumnOperator, String FilterValue) throws Exception {
			

			sfdcAcolyte.waitTillElementIsVisible(Recentlyviewedlnk).click(Recentlyviewedlnk).waitTillElementIsVisible(Allviewedlnk).
		                waitTillElementIsVisible(Allviewedlnk).click(Allviewedlnk).waitTillElementIsVisible(filtericon).click(filtericon);
			
			sfdcAcolyte.waitTillElementIsVisible(AddFilterlnk).click(AddFilterlnk);
			sfdcAcolyte.waitTillElementIsVisible(SelectField).click(SelectField).sendKeysTo(SelectField, ColumnName).sendBoardKeys(Keys.ENTER);
			sfdcAcolyte.waitTillElementIsVisible(SelectOperator).click(SelectOperator).sendKeysTo(SelectOperator, ColumnOperator).sendBoardKeys(Keys.ENTER).
	                    waitTillElementIsVisible(EnterValue).sendKeysTo(EnterValue, FilterValue).
	                    waitTillElementIsVisible(flrDonebtn).click(flrDonebtn).
	                    waitTillElementIsVisible(flrSavebtn).click(flrSavebtn);
			
			return PageFactory.initElements(driver, AdminTemplatePage.class);

}
	 public AdminTemplatePage DeleteSFDCFilter() throws Exception {
		
	      sfdcAcolyte.waitTillElementIsVisible(ShowMore).
	      jsClick(ShowMore);
	      Thread.sleep(4000);
	      sfdcAcolyte.jsClick(showDeleteAction).
	      jsClick(ConfirmDeleteAction);
	      
	      return PageFactory.initElements(driver, AdminTemplatePage.class);
	
}

	public AdminTemplatePage MoveToEditPage() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(ShowMore).waitTillElementIsClickable(ShowMore).
	      click(ShowMore).
	      waitTillElementIsClickable(EditShowAction).
	      click(EditShowAction);
	      
		return PageFactory.initElements(driver, AdminTemplatePage.class);
		
	}

	public  AdminTemplatePage CloseToastMessage() throws Exception {
		
		sfdcAcolyte.waitTillElementIsClickable(CloseToastResponse).click(CloseToastResponse);
		
		return PageFactory.initElements(driver, AdminTemplatePage.class);
		
	}

	public AdminTemplatePage deleteFilter() throws Exception {
	
			sfdcAcolyte.waitTillElementIsVisible(RemoveAllftr).click(RemoveAllftr).click(flrSavebtn);
		
		return PageFactory.initElements(driver, AdminTemplatePage.class);
}

	public AdminTemplatePage DeleteOpenFilter() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(ShowMore).
	      click(ShowMore).
	      waitTillElementIsVisible(DeleteShowAction).
	      click(DeleteShowAction).
	      click(DeleteAction);
	      	      
	      return PageFactory.initElements(driver, AdminTemplatePage.class);
		
	}

	public void VerifySaveAdminTemplate(WebElement saveAdmin2) throws Exception {
		Thread.sleep(5000);
		sfdcAcolyte.waitTillElementIsVisible(SaveAdmin).waitTillElementIsClickable(SaveAdmin);
		
		sfdcAcolyte.click(SaveAdmin);	
		sfdcAcolyte.waitTillElementIsVisible(ToastResponse);
	}

	public void CancelAdminTemplate() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(Cancelbtn).click(Cancelbtn);
		
	}

	public void SaveCancelTemplate(WebElement saveAdmin2) throws Exception {
		sfdcAcolyte.click(SaveAdmin);
		assertEquals(Duplicatesavefail, ToastResponse.getText(), "Name already exists. Please provide a different name.");
		sfdcAcolyte.waitTillElementIsVisible(Cancelbtn).click(Cancelbtn);
		
	}

	public void WaitForPageToLoad() throws Exception {
		Thread.sleep(4000);
		
	}

	public AdminTemplatePage EditAdminTemplate(WebElement templateName, String UpdatedTemplateName) throws Exception {
		
		sfdcAcolyte.refreshPage();
		sfdcAcolyte.waitTillElementIsVisible(TemplateName).
                    clear(TemplateName).
                    sendKeysTo(TemplateName, UpdatedTemplateName).
                    clear(TemplateName).sendKeysTo(TemplateName, UpdatedTemplateName);
		
		return PageFactory.initElements(driver, AdminTemplatePage.class);
	}

	public AdminTemplatePage NavigateToTemplatePage() throws Exception {
		
		sfdcAcolyte.refreshPage();
		sfdcAcolyte.waitTillElementIsVisible(Templatelnk).
		           click(Templatelnk);
		
		sfdcAcolyte.waitTillElementIsVisible(Recentlyviewedlnk);
		return PageFactory.initElements(driver, AdminTemplatePage.class);
	}


	public void RemoveAllFilter() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(Recentlyviewedlnk).click(Recentlyviewedlnk).waitTillElementIsVisible(Allviewedlnk).
                    waitTillElementIsVisible(Allviewedlnk).click(Allviewedlnk).waitTillElementIsVisible(filtericon).click(filtericon);
		
		sfdcAcolyte.waitTillElementIsVisible(RemoveAllftr).click(RemoveAllftr).click(flrSavebtn);
		sfdcAcolyte.waitTillElementIsVisible(Alllink).click(Alllink).
		            waitTillElementIsVisible(AlllnkPinnedlist).click(AlllnkPinnedlist);
		
	}

	public void ActivateTemplate() throws Exception {
		sfdcAcolyte.jsClick(Edtlink).waitTillElementIsVisible(Activatebtn).
		            click(Activatebtn);
		
	}

	public void RemoveFilterSave() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(Recentlyviewedlnk).click(Recentlyviewedlnk).waitTillElementIsVisible(Allviewedlnk).
                    waitTillElementIsClickable(Allviewedlnk).click(Allviewedlnk).waitTillElementIsVisible(filtericon).click(filtericon);
        sfdcAcolyte.waitTillElementIsVisible(RemoveAllftr).click(RemoveAllftr).click(flrSavebtn);
		
	}

	public void RefreshLinkTemplate() throws Exception {
		sfdcAcolyte.refreshPage();
		
	}

	public void DeleteExistingFilter() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(Recentlyviewedlnk).click(Recentlyviewedlnk).waitTillElementIsVisible(Allviewedlnk).
                    waitTillElementIsVisible(Allviewedlnk).click(Allviewedlnk).waitTillElementIsVisible(filtericon).click(filtericon);
		sfdcAcolyte.waitTillElementIsVisible(RemoveAllftr).click(RemoveAllftr).click(flrSavebtn);
	}

	public void MoveToAllFilter() throws Exception {
		sfdcAcolyte.refreshPage();
		sfdcAcolyte.waitTillElementIsVisible(Recentlyviewedlnk).click(Recentlyviewedlnk).waitTillElementIsVisible(Allviewedlnk).
        waitTillElementIsVisible(Allviewedlnk).click(Allviewedlnk).waitTillElementIsVisible(filtericon).click(filtericon);
	}

	public void LaunchAdminTemplate() throws Exception {
		String Currenturl=sfdcAcolyte.getCurrentURL();
		sfdcAcolyte.navigateTo(Currenturl);
		
	}
}
	


	
