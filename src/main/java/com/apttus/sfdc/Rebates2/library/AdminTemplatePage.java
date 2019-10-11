package com.apttus.sfdc.Rebates2.library;

import static org.testng.Assert.assertEquals;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.apttus.sfdc.Rebates2.common.StartUpPage;
import com.apttus.sfdc.Rebates2.common.GenericPage;

public class AdminTemplatePage extends StartUpPage {
	 
	@FindBy(xpath="//b[text()='New Template']")
	public WebElement Newtemplatelabel;
	
	@FindBy(xpath="//div[@title='New']")
	public WebElement Newbtn;
	
	@FindBy(xpath="//input[@placeholder='Enter a name']")
	public WebElement TemplateName;
	
	@FindBy(xpath="//*[text()='Description']//..//*[@name='template-description']")
	public WebElement TemplateDescription;
		
	@FindBy(xpath="//*[text()='Q & B Layout']//..//input[@placeholder='Please select']")
	public WebElement QBselct;
	
	@FindBy(xpath="//*[text()='Tier']//..//input[@type='text']")
	public WebElement TierSelect;
	
	@FindBy(xpath="//span[@class='slds-checkbox_faux']")
	public  List<WebElement> BenifitCheckbox;
	
	@FindBy(xpath="//*[text()='Data Source']//..//input[@placeholder='Please select']")
	public  WebElement DataSourcedorpdwn;
	
	@FindBy(xpath="//button[@class='slds-button slds-button_brand']")
	public  WebElement SaveAdmin;
	
	@FindBy(xpath="//*[text()='Q & B Layout']//..//lightning-base-combobox-item[@data-value='Benefit Product']")
	public  WebElement BenftPrdtValue;
	
	@FindBy(xpath="//*[text()='Tier']//..//lightning-base-combobox-item[@data-value='Discrete']")
	public  WebElement TierDiscrete;
	
	@FindBy(xpath="//*[text()='Tier']//..//lightning-base-combobox-item[@data-value='Tiered']")
	public  WebElement TierTiered;
	
	@FindBy(xpath="//*[text()='Data Source']//..//span[text()='DS-0103']")
	public  WebElement DatasourceValue;
	
	@FindBy(xpath="//*[text()='Q & B Layout']//..//lightning-base-combobox-item[@data-value='Qualification & Benefit Product']")
	public  WebElement QualftnBenftFormula;
	
	@FindBy(xpath="//span[contains(text(),'Program template saved')]")
	public WebElement successresponse;
	
	@FindBy(xpath="//span[text()='Recently Viewed']")
	public WebElement Recentlyviewedlnk;
    
    @FindBy(xpath="//span[text()='All']")
	public WebElement Allviewedlnk;
    
    @FindBy(xpath="//span[@class='slds-truncate uiOutputText'][contains(text(),'DSrcAutomation')]")
   	public WebElement Namecolmn;
    
    @FindBy(xpath="//span[@title='Name']")
   	public WebElement titleNamecolmn;
    
    @FindBy(xpath="//*[@data-key='filterList']")
   	public WebElement filtericon;
    
    @FindBy(xpath="//a[text()='Add Filter']")
   	public WebElement AddFilterlnk;
    
    @FindBy(xpath="//*[text()='Field']/../..//input[@type='text']")
   	public WebElement SelectField;
    
    @FindBy(xpath="//*[text()='Operator']/../..//input[@class='slds-input slds-combobox__input']")
   	public WebElement SelectOperator;
    
    @FindBy(xpath="//*[text()='Value']/../..//input[@type='text']")
   	public WebElement EnterValue;
    
    @FindBy(xpath="//span[text()='Done']")
   	public WebElement flrDonebtn;
  
    @FindBy(xpath="//*[@class='slds-button slds-button_brand saveButton headerButton']")
   	public WebElement flrSavebtn;
   
    @FindBy(xpath="//a[text()='Remove All']")
   	public WebElement RemoveAllftr;
    
	@FindBy(xpath = "//ul[@class='scrollable']/li[2]/a")
	public WebElement DeleteShowAction;
	
	@FindBy(xpath = "//ul[@class='scrollable']/li[1]/a")
	public WebElement EditShowAction;
	
	@FindBy(xpath = "//span[text()='Delete']")
	@CacheLookup
	public WebElement DeleteAction;
	
	@FindBy(xpath="//*[@id=\"brandBand_1\"]//..//span/div/a/lightning-icon/lightning-primitive-icon")
	public  WebElement ShowMore;
	
	public String success="Program template saved";
	public String NewTemplate="New Template";
    GenericPage genericPage;

	public AdminTemplatePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		sfdcAcolyte.setWaitTime(60);
		genericPage = new GenericPage(driver);
	}

	public AdminTemplatePage MovetoNewTemplatePage(WebElement newbutton, WebElement Labelnewtemplate) throws Exception {
		
		sfdcAcolyte.waitTillElementIsVisible(newbutton).click(newbutton).
		            waitTillElementIsVisible(Labelnewtemplate);
		
		return PageFactory.initElements(driver, AdminTemplatePage.class);
	}

	public AdminTemplatePage FillTemplateDetails(WebElement templateNametxt,String TemplateNameValue, WebElement templateDescriptiontxt,String TemplateDescValue) throws Exception {
		
		sfdcAcolyte.waitTillElementIsVisible(TemplateName).
		            clickAndSendkeys(TemplateName, TemplateNameValue).
		            waitTillElementIsClickable(TemplateDescription).clickAndSendkeys(TemplateDescription, TemplateDescValue);
		
		return PageFactory.initElements(driver, AdminTemplatePage.class);	
	}

	public AdminTemplatePage QBLayoutDefinition(WebElement QBselct, WebElement QBValue,WebElement TierSelect,WebElement TierValue) throws Exception {
		sfdcAcolyte.waitTillElementIsClickable(QBselct).
		           click(QBselct).
		           click(QBValue)
                   .waitTillElementIsClickable(TierSelect).
                   click(TierSelect).
                   click(TierValue);

       return PageFactory.initElements(driver, AdminTemplatePage.class);
		
	}

	public AdminTemplatePage DataSourceFormula(WebElement DataSourcedorpdwn,WebElement DatasourceValue ,List<WebElement> BenifitCheckbox) throws Exception {
		
		sfdcAcolyte.waitTillElementIsVisible(DataSourcedorpdwn).
		            click(DataSourcedorpdwn).
		            jsScrollAndClick(DatasourceValue);
		Thread.sleep(1000);
		for (int i = 0; i < BenifitCheckbox.size()-2; i++) {
			BenifitCheckbox.get(i).click();
			
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("scroll(0, 250)");
			
			Thread.sleep(2000);
			
			
					
		}
		return PageFactory.initElements(driver, AdminTemplatePage.class);
		
	}
	public void SaveAdminTemplate(WebElement SaveAdmin) throws Exception {
		sfdcAcolyte.click(SaveAdmin);
		sfdcAcolyte.waitTillElementIsVisible(successresponse);
		
		
	}

	 public AdminTemplatePage SFDCFilter(String ColumnName, String ColumnOperator, String FilterValue) throws Exception {
			
	        Thread.sleep(5000);
			
			sfdcAcolyte.click(Recentlyviewedlnk);
			Thread.sleep(1000);
			sfdcAcolyte.click(Allviewedlnk);
			Thread.sleep(2000);
			sfdcAcolyte.click(filtericon);
			Thread.sleep(5000);
			sfdcAcolyte.click(AddFilterlnk);
			Thread.sleep(2000);
			sfdcAcolyte.click(SelectField).sendKeysTo(SelectField, ColumnName).sendBoardKeys(Keys.ENTER);
			Thread.sleep(2000);
			sfdcAcolyte.click(SelectOperator).sendKeysTo(SelectOperator, ColumnOperator).sendBoardKeys(Keys.ENTER).
	        sendKeysTo(EnterValue, FilterValue).click(flrDonebtn).click(flrSavebtn);
			Thread.sleep(3000);
			return PageFactory.initElements(driver, AdminTemplatePage.class);

}
	 public AdminTemplatePage DeleteSFDCFilter() throws Exception {
			
	      sfdcAcolyte.waitTillElementIsClickable(ShowMore).
	      click(ShowMore).
	      waitTillElementIsClickable(DeleteShowAction).
	      click(DeleteShowAction).
	      click(DeleteAction);
	      
	      return PageFactory.initElements(driver, AdminTemplatePage.class);
	
}

	public AdminTemplatePage MoveToEditPage() throws Exception {
		sfdcAcolyte.waitTillElementIsClickable(ShowMore).
	      click(ShowMore).
	      waitTillElementIsClickable(DeleteShowAction).
	      click(EditShowAction);
		return PageFactory.initElements(driver, AdminTemplatePage.class);
		
	}
}
	


	
