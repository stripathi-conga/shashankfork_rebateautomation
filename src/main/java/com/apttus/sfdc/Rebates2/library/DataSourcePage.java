package com.apttus.sfdc.Rebates2.library;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.apttus.sfdc.Rebates2.common.StartUpPage;
import com.apttus.sfdc.Rebates2.common.GenericPage;

public class DataSourcePage extends StartUpPage {
	  
    /*.........Create Data Source..........*/
    
    @FindBy(css="a[title='New']")
	@CacheLookup
	public WebElement NewDataSrcbtn;
    
    @FindBy(css="input[class='slds-input'][type='text'][aria-describedby='help-message-109']")
	@CacheLookup
	public WebElement DataSrcNametxt;
    
    @FindBy(xpath="//span[@class='slds-truncate'][text()='Data Sources']")
	@CacheLookup
	public WebElement Datasrclink;
    
    @FindBy(css="[title='New'][class='forceActionLink']")
	public WebElement CreateNewDataSrcbtn;
    
    @FindBy(xpath="//select[@class='slds-select metaData']")
	public WebElement selctTransMetaData;
    
    @FindBy(xpath="//select[@class='slds-select calcDateAttr']")
	public WebElement selctcalcdateAttr;
    
    @FindBy(xpath="//select[@class='slds-select prodAttr']")
	public WebElement selctproduct;
    
    @FindBy(xpath="//select[@class='slds-select proAcctAttr']")
	public WebElement selctprodAccount;
    
    @FindBy(xpath="//select[@class='slds-select delimiter']")
	public WebElement selctdelimter;
    
    @FindBy(xpath="//select[@class='slds-select fileExtension']")

	public WebElement MultiFileExtension;
   
    @FindBy(xpath="//label[contains(text(),'File Suffix To ignore')]//following::div[1]/input")
	public WebElement txtfilesuffix;
    
    @FindBy(xpath="//label[contains(text(),'Data Source Name')]//following::div[1]/input")
	
	public WebElement txtdatasrc;
    
    @FindBy(xpath="//button[text()='Submit']")
	public WebElement btnsubmitdatasrc;
    
    @FindBy(xpath="//span[contains(text(),'Data Source created')]")
	@CacheLookup
	public WebElement successresponse;
    
    @FindBy(xpath="//span[contains(text(),'Duplicate Data Source Name Found')]")
	@CacheLookup
	public WebElement Duplicateresponse;
    
   /* .............ListPage...................*/
    
    @FindBy(xpath="//span[text()='Recently Viewed']")
	public WebElement Recentlyviewedlnk;
    
    @FindBy(xpath="//span[text()='All']")
	public WebElement Allviewedlnk;
    
    @FindBy(xpath="//input[@placeholder='Search this list...']")
	public WebElement searchtxt;
    
    @FindBy(xpath="//span[@title='Apttus CIM Admin']")
	public WebElement ApttustCIMtitle;
    
    @FindBy(xpath="//span[@text='Refresh']")
	public WebElement Refreshicon;
    
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
    
  //*[@data-aura-class='forceOutputLookup']
  
  //a[@class='removeAll']
    /* .............XpathValidations...................*/
    
    @FindBy(xpath="//span[contains(text(),'Please enter Data Source Name')]")
   	public WebElement DataSrcResponse;
    
    @FindBy(xpath="//*[contains(text(),'Please select Transaction MetaData')]")
   	public WebElement MetadataResponse;
    
    @FindBy(xpath="//*[contains(text(),'Please select Calculation Date')]")
   	public WebElement CalDatecResponse;
    
    @FindBy(xpath="//*[contains(text(),'Please select Program Account')]")
   	public WebElement ProgramAcctesponse;
    
    @FindBy(xpath="//*[contains(text(),'Please select Product .')]")
   	public WebElement ProductResponse;
    
    @FindBy(xpath="//*[contains(text(),'Please enter File Suffix to Ignore')]")
   	public WebElement SuffixResponse;
    
    @FindBy(xpath="//*[contains(text(),'Please select File Extension.')]")
   	public WebElement FileExtResponse;
    
    @FindBy(xpath="//*[contains(text(),'Please select Delimiter.')]")
   	public WebElement DelimiterResponse;
    
    /* .............Validations...................*/
    public String success="Data Source created";
    public String Duplicate="Duplicate Data Source Name Found";
    public String ResponseDataSrc="Please enter Data Source Name";
    public String ResponseMetaData="Please select Transaction MetaData";
    public String ResponseCalcDate="Please select Calculation Date .";
    public String ResponsePrgmAccount="Please select Program Account .";
    public String ResponseProduct="Please select Product .";
    public String ResponseSuffix="Please enter File Suffix to Ignore.";
    public String ResponseFileExt="Please select File Extension.";
    public String ResponseDelimiter="Please select Delimiter.";
    
    
    
    GenericPage genericPage;

	public DataSourcePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		sfdcAcolyte.setWaitTime(60);
		genericPage = new GenericPage(driver);
	}
	
	public DataSourcePage CreateSaveNewDataSource(String DataSourceName, String FileExtenstion1, String FileExtenstion2, String TransMetaData,
			                                      String FileSuffix, String CalculationDate, String Product, String ProgramAccount,String Delimiter) throws Exception {
		
		
		sfdcAcolyte.waitTillElementIsVisible(CreateNewDataSrcbtn).
		            waitTillElementIsClickable(CreateNewDataSrcbtn).
		            jsClick(CreateNewDataSrcbtn);
		Thread.sleep(2000);
		sfdcAcolyte.waitTillElementIsClickable(txtdatasrc).
		            click(txtdatasrc).sendKeysTo(txtdatasrc, DataSourceName).
		            click(MultiFileExtension).selectComboByText(MultiFileExtension, FileExtenstion1).
		            selectComboByText(MultiFileExtension, FileExtenstion2);
		Thread.sleep(3000);
		sfdcAcolyte.click(selctTransMetaData).
		            selectComboByText(selctTransMetaData, TransMetaData).
		            waitTillElementIsClickable(txtfilesuffix).click(txtfilesuffix).sendKeysTo(txtfilesuffix, FileSuffix);
		Thread.sleep(3000);
        sfdcAcolyte.click(selctcalcdateAttr).selectComboByValue(selctcalcdateAttr,CalculationDate).
		            click(selctproduct).selectComboByText(selctproduct, Product).click(selctprodAccount).
		            selectComboByText(selctprodAccount, ProgramAccount).
		            click(selctdelimter).selectComboByText(selctdelimter, Delimiter).click(btnsubmitdatasrc);
		
		sfdcAcolyte.waitTillElementIsVisible(successresponse);		
		
		return PageFactory.initElements(driver, DataSourcePage.class);
	
	}

	public DataSourcePage SearchDataSource(String createdDataSource) throws Exception {
		
		Thread.sleep(1000);
		
		sfdcAcolyte.click(Recentlyviewedlnk);
		Thread.sleep(1000);
		sfdcAcolyte.click(Allviewedlnk).
		            click(searchtxt);
		searchtxt.sendKeys(createdDataSource);
		sfdcAcolyte.sendBoardKeys(Keys.ENTER);
		/*sfdcAcolyte.click(filtericon);*/
		            
		Thread.sleep(4000);
		
		return PageFactory.initElements(driver, DataSourcePage.class);
		
		
	}

	public DataSourcePage Verifyduplicate(String duplicateDataSource, String FileExtenstion1, String FileExtenstion2, String TransMetaData,
            String FileSuffix, String CalculationDate, String Product, String ProgramAccount,String Delimiter) throws Exception {
		Thread.sleep(5000);
		sfdcAcolyte.waitTillElementIsVisible(CreateNewDataSrcbtn).
        waitTillElementIsClickable(CreateNewDataSrcbtn).
        jsClick(CreateNewDataSrcbtn);
        
        sfdcAcolyte.waitTillElementIsClickable(txtdatasrc).
        click(txtdatasrc).
        sendKeysTo(txtdatasrc, duplicateDataSource);
        Thread.sleep(1000);  
        sfdcAcolyte.click(MultiFileExtension).selectComboByText(MultiFileExtension, FileExtenstion1);

        sfdcAcolyte.selectComboByText(MultiFileExtension, FileExtenstion2);
        Thread.sleep(1000);
        sfdcAcolyte.click(selctTransMetaData).
        selectComboByText(selctTransMetaData, TransMetaData).
        waitTillElementIsClickable(txtfilesuffix).
        click(txtfilesuffix).
        sendKeysTo(txtfilesuffix, FileSuffix);
        Thread.sleep(1000);
        sfdcAcolyte.click(selctcalcdateAttr).
        selectComboByValue(selctcalcdateAttr,CalculationDate).
        click(selctproduct).
        selectComboByText(selctproduct, Product);

        sfdcAcolyte.click(selctprodAccount).
        selectComboByText(selctprodAccount, ProgramAccount).
        click(selctdelimter).
        selectComboByText(selctdelimter, Delimiter).click(btnsubmitdatasrc);
		
		return PageFactory.initElements(driver, DataSourcePage.class);
		
	}

	public void DuplicateSaveNewDataSource(String DupDataSourceName, String FileExtenstion1, String FileExtenstion2, String TransMetaData,
            String FileSuffix, String CalculationDate, String Product, String ProgramAccount,String Delimiter) throws Exception { {
		
		sfdcAcolyte.waitTillElementIsVisible(CreateNewDataSrcbtn).
                    waitTillElementIsClickable(CreateNewDataSrcbtn).
                    jsClick(CreateNewDataSrcbtn);
                    
        sfdcAcolyte.waitTillElementIsClickable(txtdatasrc).
                    click(txtdatasrc).
                    sendKeysTo(txtdatasrc, DupDataSourceName);
        Thread.sleep(1000);  
        sfdcAcolyte.click(MultiFileExtension).selectComboByText(MultiFileExtension, FileExtenstion1);

        sfdcAcolyte.selectComboByText(MultiFileExtension, FileExtenstion2);
        Thread.sleep(4000);
        sfdcAcolyte.click(selctTransMetaData).
        selectComboByText(selctTransMetaData, TransMetaData).
        waitTillElementIsClickable(txtfilesuffix).
        click(txtfilesuffix).
        sendKeysTo(txtfilesuffix, FileSuffix);
        Thread.sleep(4000);
        sfdcAcolyte.click(selctcalcdateAttr).
        selectComboByValue(selctcalcdateAttr,CalculationDate).
        click(selctproduct).
        selectComboByText(selctproduct, Product);

        sfdcAcolyte.click(selctprodAccount).
        selectComboByText(selctprodAccount, ProgramAccount).
        click(selctdelimter).
        selectComboByText(selctdelimter, Delimiter).click(btnsubmitdatasrc);
		
	}

}

	public DataSourcePage VerifydiffrentsetofDelimterSuffix(String DataSourceName, String FileExtenstion1, String FileExtenstion2, String TransMetaData,
            String Suffix, String CalculationDate, String Product, String ProgramAccount,String Delimiter) throws Exception {
		
		sfdcAcolyte.waitTillElementIsVisible(CreateNewDataSrcbtn).
                   waitTillElementIsClickable(CreateNewDataSrcbtn).
                   jsClick(CreateNewDataSrcbtn);
        Thread.sleep(2000);
        sfdcAcolyte.waitTillElementIsClickable(txtdatasrc).click(txtdatasrc).sendKeysTo(txtdatasrc, DataSourceName);
        Thread.sleep(3000);  
        sfdcAcolyte.click(MultiFileExtension).selectComboByText(MultiFileExtension, FileExtenstion1);
        Thread.sleep(3000);
        sfdcAcolyte.selectComboByText(MultiFileExtension, FileExtenstion2);
        Thread.sleep(3000);
        sfdcAcolyte.click(selctTransMetaData).
        selectComboByText(selctTransMetaData, TransMetaData).waitTillElementIsClickable(txtfilesuffix).
        click(txtfilesuffix).sendKeysTo(txtfilesuffix, Suffix);
        Thread.sleep(3000);
        sfdcAcolyte.click(selctcalcdateAttr).selectComboByValue(selctcalcdateAttr,CalculationDate).
        click(selctproduct).selectComboByText(selctproduct, Product);

        sfdcAcolyte.click(selctprodAccount).selectComboByText(selctprodAccount, ProgramAccount).click(selctdelimter).
        selectComboByText(selctdelimter, Delimiter).click(btnsubmitdatasrc);

        sfdcAcolyte.waitTillElementIsVisible(successresponse);		

       return PageFactory.initElements(driver, DataSourcePage.class);
		
	}

	public void VerifyValidation_DataSourceName() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(CreateNewDataSrcbtn).waitTillElementIsClickable(CreateNewDataSrcbtn).jsClick(CreateNewDataSrcbtn).
		            waitTillElementIsVisible(btnsubmitdatasrc).waitTillElementIsClickable(btnsubmitdatasrc).click(btnsubmitdatasrc);
	    Thread.sleep(1000);
	}

	public void VerifyValidation_TransactionMetaData(String DataSourceName) throws Exception {
		sfdcAcolyte.click(txtdatasrc).sendKeysTo(txtdatasrc, DataSourceName).click(btnsubmitdatasrc);
		Thread.sleep(2000);
	}

	public void VerifyValidation_CalculationDate(String TransMetaData) throws Exception {
		
		sfdcAcolyte.click(selctTransMetaData).selectComboByText(selctTransMetaData, TransMetaData).click(btnsubmitdatasrc);
		Thread.sleep(3000);
	}

	public void VeriyValidation_ProgramAccount( String CalculationDate) throws Exception {
		sfdcAcolyte.click(selctcalcdateAttr).selectComboByValue(selctcalcdateAttr,CalculationDate).click(btnsubmitdatasrc);
		Thread.sleep(2000);
	}

	public void VeriyValidation_Product(String ProgramAccount) throws Exception {
		sfdcAcolyte.click(selctprodAccount).selectComboByText(selctprodAccount, ProgramAccount).click(btnsubmitdatasrc);
		Thread.sleep(2000);
		
	}

	public void VeriyValidation_Suffix(String Product) throws Exception {
		
		sfdcAcolyte.click(selctproduct).selectComboByText(selctproduct, Product).click(btnsubmitdatasrc);
		Thread.sleep(5000);
	}

	public void VeriyValidation_FileExtension(String FileSuffix) throws Exception {
		sfdcAcolyte.waitTillElementIsClickable(txtfilesuffix).
		waitTillElementIsVisible(txtfilesuffix).
		click(txtfilesuffix).
        sendKeysTo(txtfilesuffix, FileSuffix).click(btnsubmitdatasrc);
	    Thread.sleep(1000);
	    
	}
	
	public void VeriyValidation_Delimiter(String FileExtenstion) throws Exception {
		sfdcAcolyte.click(MultiFileExtension).selectComboByText(MultiFileExtension, FileExtenstion).click(btnsubmitdatasrc);
		Thread.sleep(1000);
		
	}

	

	public DataSourcePage DataSdourceFilter(String ColumnName, String ColumnOperator, String FilterValue) throws Exception {
		
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

        return PageFactory.initElements(driver, DataSourcePage.class);	
		
	}

	public DataSourcePage deleteFilter() throws Exception {
	
			sfdcAcolyte.click(RemoveAllftr).click(flrSavebtn);
			Thread.sleep(3000);
		
		return PageFactory.initElements(driver, DataSourcePage.class);
		
	}
	

}
	


	
