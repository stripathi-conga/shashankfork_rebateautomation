package com.apttus.sfdc.Rebates2.library;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.apttus.selenium.NGHelper;
import com.apttus.sfdc.Rebates2.common.GenericPage;
import com.apttus.sfdc.Rebates2.common.StartUpPage;

public class DataSourcePage extends StartUpPage {
	  
    /*.........Create Data Source..........*/
    
    @FindBy(css="a[title='New']")
	public WebElement NewDataSrcbtn;
    
    @FindBy(css="input[class='slds-input'][type='text'][aria-describedby='help-message-109']")
	public WebElement DataSrcNametxt;
    
    @FindBy(xpath="//span[@class='slds-truncate'][text()='Data Sources']")
	public WebElement Datasrclink;
    
    @FindBy(css="[title='New']")
	public WebElement CreateNewDataSrcbtn;
    
    @FindBy(css="a[title='Delete'][role='button']")
	public WebElement DeleteDataSrcbtn;
        
    @FindBy(xpath = "//select[@class='slds-select metaData']")  
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
    
    @FindBy(xpath="//label[contains(text(),'File Suffix To ignore')]//following::div[1]/input")
	public WebElement dpfilesuffix;
    
    @FindBy(xpath="//label[contains(text(),'Data Source Name')]//following::div[1]/input")
	public WebElement txtdatasrc;
    
    @FindBy(xpath="//button[@class='slds-button slds-button_brand']")
	public WebElement btnsubmitdatasrc;
    
    @FindBy(xpath="//button[@class='slds-button slds-button_neutral']")
	public WebElement btnCancel;
   
    @FindBy(xpath="//span[contains(text(),'Data Source created')]")
	public WebElement successresponse;
    
    @FindBy(xpath="//span[contains(text(),'Duplicate Data Source Name Found')]")
	public WebElement Duplicateresponse;
    
   /* .............ListPage...................*/
    @FindBy(css = "span.triggerLinkText.selectedListView.uiOutputText")
	public WebElement Recentlyviewedlnk;
    
    
	@FindBy(xpath="//span[text()='All']")
	public WebElement Allviewedlnk;
    
    @FindBy(css="input[placeholder='Search this list...']")
	public WebElement searchtxt;
    
    @FindBy(xpath="//select[3]/option[2]")
	public WebElement optionProd;
  
    @FindBy(css="span[title*='DSrcAutomation']")
    public WebElement Namecolmn;
    
    @FindBy(xpath="//*[@data-key='filterList']")
   	public WebElement filtericon;
    
    @FindBy(css="[class*=' addFilter']")
   	public WebElement addFilterlnk;
    
    @FindBy(xpath="//*[text()='Field']/../..//input[@type='text']")
   	public WebElement selectField;
    
    @FindBy(xpath="//*[text()='Operator']/../..//input[@class='slds-input slds-combobox__input']")
   	public WebElement selectOperator;
    
    @FindBy(css="[class='filterTextInput valueInput input uiInput uiInputText uiInput--default uiInput--input']")
   	public WebElement enterValue;
    
    @FindBy(css="button[class='slds-button slds-button--neutral doneButton uiButton'] span")  
   	public WebElement filterDonebtn;
  
    @FindBy(css="[class='slds-button slds-button_brand saveButton headerButton']")
   	public WebElement filterSavebtn;
    
    @FindBy(css="[class='slds-text-color_weak slds-text-body_small slds-truncate virtualAutocompleteOptionSubtext']")
   	public WebElement alllnkPinnedlist;
   
    @FindBy(css="[class='removeAll']")
   	public WebElement removeAllftr;
    
    @FindBy(xpath="//*[@id=\"brandBand_1\"]//..//span/div/a/lightning-icon/lightning-primitive-icon")
  	public  WebElement showMore;
     
    @FindBy(xpath = "//a[@title='Delete']")
  	public WebElement showDeleteAction;
  	
  	@FindBy(xpath = "//button[@title='Delete']")
  	public WebElement confirmDeleteAction;
    
    /* .............XpathValidations...................*/
    
    @FindBy(css="span[class*='toastMessage']")
   	public WebElement tempDataSrcResponse;
    
    @FindBy(css="button[title='Close']")
   	public WebElement closeToastResponse;
    
    @FindBy(xpath="//span[contains(text(),'Please enter Data Source Name')]")
   	public WebElement dataSrcResponse;
    
    @FindBy(xpath="//*[contains(text(),'Error creating record')]")
   	public WebElement metadataResponse;
    
    @FindBy(xpath="//*[contains(text(),'Please select Calculation Date')]")
   	public WebElement calDatecResponse;
    
    @FindBy(xpath="//*[contains(text(),'Please select Program')]")
   	public WebElement programAcctesponse;
    
    @FindBy(xpath="//*[contains(text(),'Please select Product .')]")
   	public WebElement productResponse;
    
    @FindBy(xpath="//*[contains(text(),'Please enter File Suffix to Ignore')]")
   	public WebElement suffixResponse;
    
    @FindBy(xpath="//*[contains(text(),'Please select File Extension.')]")
   	public WebElement fileExtResponse;
    
    @FindBy(xpath="//*[contains(text(),'Please select Delimiter')]")
   	public WebElement delimiterResponse;
    
    @FindBy(xpath="//*[text()='List view updated.']")
   	public WebElement filterResponse;
    
    @FindBy(xpath="//*[text()='List view updated.']")
   	public WebElement newFilterResponse;
    
    @FindBy(xpath="//option[text()='Order Line Item']")
   	public WebElement Ordr;
    
    @FindBy(xpath="//option[text()='Ready for Activation Date']")
   	public WebElement calcdate;
    
  
    /* .............Validations...................*/
    public String getProgram="Please select Program Account .";
    public String success="Data Source created";
    public String duplicate="Duplicate Data Source Name Found";
    public String responseDataSrc="Please enter Data Source Name";
    public String responseMetaData="Error creating record";
    public String responseCalcDate="Please select Calculation Date .";
    public String responsePrgmAccount="Please select Program Account .";
    public String responseProduct="Please select Product .";
    public String responseSuffix="Please enter File Suffix to Ignore.";
    public String responseFileExt="Please select File Extension.";
    public String responseDelimiter="Please select Delimiter.";
    public String responseFilter="List view updated.";
    public String newResponseFilter="List view updated.";
    public String duplicateRecord;
    public String getDelimiterResponse;
    public String getFileExtensionResponse;
    public String getSuffixResponse;
    public String getdatasrcResponse;
    public String getMetadataResponse;
    public String getCaldateResponse;
    
    
    NGHelper ngHelper;
    GenericPage genericPage;
    
	public DataSourcePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		sfdcAcolyte.setWaitTime(60);
		genericPage = new GenericPage(driver);
		
	}
	
	public DataSourcePage createSaveNewDataSource(String DataSourceName, String TransMetaData, String CalculationDate, String Product,
			                                      String ProgramAccount, String FileSuffix, String FileExtenstion1, String FileExtenstion2,String Delimiter) throws Exception {
		
		String DataSrcURL=sfdcAcolyte.getCurrentURL();
		sfdcAcolyte.navigateTo(DataSrcURL);
		sfdcAcolyte.waitTillElementIsVisible(CreateNewDataSrcbtn).
                    waitTillElementIsClickable(CreateNewDataSrcbtn).jsClick(CreateNewDataSrcbtn);
        sfdcAcolyte.waitTillElementIsVisible(txtdatasrc).click(txtdatasrc).sendKeysTo(txtdatasrc, DataSourceName);
        sfdcAcolyte.click(selctTransMetaData).waitTillElementIsVisible(Ordr).click(selctTransMetaData).
                    selectComboByText(selctTransMetaData, TransMetaData).
                    click(selctcalcdateAttr).waitTillElementIsVisible(calcdate).selectComboByText(selctcalcdateAttr, CalculationDate);

       sfdcAcolyte.click(selctproduct).selectComboByText(selctproduct, Product).click(selctprodAccount).
                   selectComboByText(selctprodAccount, ProgramAccount);
       sfdcAcolyte.waitTillElementIsClickable(txtfilesuffix).click(txtfilesuffix).sendKeysTo(txtfilesuffix, FileSuffix);
       sfdcAcolyte.click(MultiFileExtension).selectComboByText(MultiFileExtension, FileExtenstion1).selectComboByText(MultiFileExtension, FileExtenstion2).
                   click(selctdelimter).selectComboByText(selctdelimter, Delimiter).click(btnsubmitdatasrc);
		
		sfdcAcolyte.waitTillElementIsVisible(successresponse);	
		return PageFactory.initElements(driver, DataSourcePage.class);
	
	}


	public DataSourcePage verifyduplicate(String duplicateDataSource, String TransMetaData, String CalculationDate, String Product,
            String ProgramAccount, String FileSuffix, String FileExtenstion1, String FileExtenstion2,String Delimiter) throws Exception {
		
		
		sfdcAcolyte.waitTillElementIsVisible(CreateNewDataSrcbtn).waitTillElementIsClickable(CreateNewDataSrcbtn).jsClick(CreateNewDataSrcbtn);
        sfdcAcolyte.waitTillElementIsVisible(txtdatasrc).click(txtdatasrc).sendKeysTo(txtdatasrc, duplicateDataSource);
        sfdcAcolyte.click(selctTransMetaData).waitTillElementIsVisible(Ordr).click(selctTransMetaData).selectComboByText(selctTransMetaData, TransMetaData).
                    click(selctcalcdateAttr).waitTillElementIsVisible(calcdate).selectComboByText(selctcalcdateAttr, CalculationDate);
        sfdcAcolyte.click(selctproduct).selectComboByText(selctproduct, Product).click(selctprodAccount).
                    selectComboByText(selctprodAccount, ProgramAccount);
        
        sfdcAcolyte.click(MultiFileExtension).selectComboByText(MultiFileExtension, FileExtenstion1).selectComboByText(MultiFileExtension, FileExtenstion2).
                    waitTillElementIsVisible(selctdelimter).click(selctdelimter).selectComboByText(selctdelimter, Delimiter).
                    waitTillElementIsVisible(dpfilesuffix).click(dpfilesuffix).sendKeysTo(dpfilesuffix, FileSuffix).
                    click(btnsubmitdatasrc);

        sfdcAcolyte.waitTillElementIsVisible(Duplicateresponse);	
        closeToastMessage();
        duplicateRecord=Duplicateresponse.getText();
		return PageFactory.initElements(driver, DataSourcePage.class);
		
	}

	public void duplicateSaveNewDataSource(String DupDataSourceName, String TransMetaData, String CalculationDate, String Product,
                  String ProgramAccount, String FileSuffix, String FileExtenstion1, String FileExtenstion2,String Delimiter) throws Exception { 
		
         sfdcAcolyte.waitTillElementIsVisible(CreateNewDataSrcbtn).
                     waitTillElementIsClickable(CreateNewDataSrcbtn).jsClick(CreateNewDataSrcbtn);
         sfdcAcolyte.waitTillElementIsClickable(txtdatasrc).click(txtdatasrc).sendKeysTo(txtdatasrc, DupDataSourceName);
         sfdcAcolyte.click(selctTransMetaData).waitTillElementIsVisible(Ordr).click(selctTransMetaData).
                     selectComboByText(selctTransMetaData, TransMetaData).
                     click(selctcalcdateAttr).waitTillElementIsVisible(calcdate).selectComboByText(selctcalcdateAttr, CalculationDate);

        sfdcAcolyte.click(selctproduct).selectComboByText(selctproduct, Product).click(selctprodAccount).
                    selectComboByText(selctprodAccount, ProgramAccount);
        sfdcAcolyte.waitTillElementIsClickable(txtfilesuffix).click(txtfilesuffix).sendKeysTo(txtfilesuffix, FileSuffix);
        sfdcAcolyte.click(MultiFileExtension).selectComboByText(MultiFileExtension, FileExtenstion1).selectComboByText(MultiFileExtension, FileExtenstion2).
                    click(selctdelimter).selectComboByText(selctdelimter, Delimiter).click(btnsubmitdatasrc);
        sfdcAcolyte.waitTillElementIsVisible(successresponse);
        closeToastMessage();
 		
	}

	public DataSourcePage verifydiffrentsetofDelimterSuffixB(String DataSourceName, String TransMetaData, String CalculationDate, String Product,
            String ProgramAccount, String SuffixB, String FileExtenstion1, String FileExtenstion2,String DelimiterB) throws Exception {
		
		      sfdcAcolyte.waitTillElementIsVisible(CreateNewDataSrcbtn).
                          waitTillElementIsClickable(CreateNewDataSrcbtn).jsClick(CreateNewDataSrcbtn);
              sfdcAcolyte.waitTillElementIsClickable(txtdatasrc).click(txtdatasrc).sendKeysTo(txtdatasrc, DataSourceName);
              sfdcAcolyte.click(selctTransMetaData).waitTillElementIsVisible(Ordr).click(selctTransMetaData).
                          selectComboByText(selctTransMetaData, TransMetaData).
                          click(selctcalcdateAttr).waitTillElementIsVisible(calcdate).selectComboByText(selctcalcdateAttr, CalculationDate);

              sfdcAcolyte.click(selctproduct).click(selctproduct).selectComboByText(selctproduct, "Base Product").click(selctprodAccount).
                          selectComboByText(selctprodAccount, "Bill To");
              sfdcAcolyte.waitTillElementIsClickable(txtfilesuffix).click(txtfilesuffix).sendKeysTo(txtfilesuffix, SuffixB);
              sfdcAcolyte.click(MultiFileExtension).selectComboByText(MultiFileExtension, FileExtenstion1).selectComboByText(MultiFileExtension, FileExtenstion2).
                          click(selctdelimter).selectComboByText(selctdelimter, DelimiterB);
              sfdcAcolyte.waitTillElementIsVisible(btnsubmitdatasrc); sfdcAcolyte.click(selctproduct).click(btnsubmitdatasrc);
       
              sfdcAcolyte.waitTillElementIsVisible(successresponse);

       return PageFactory.initElements(driver, DataSourcePage.class);
		
	}
	
	public DataSourcePage verifydiffrentsetofDelimterSuffixC(String DataSourceName, String TransMetaData, String CalculationDate, String Product,
            String ProgramAccount, String SuffixC, String FileExtenstion1, String FileExtenstion2,String DelimiterC) throws Exception {
		
		      sfdcAcolyte.waitTillElementIsVisible(CreateNewDataSrcbtn).
                          waitTillElementIsClickable(CreateNewDataSrcbtn).jsClick(CreateNewDataSrcbtn);
              sfdcAcolyte.waitTillElementIsClickable(txtdatasrc).click(txtdatasrc).sendKeysTo(txtdatasrc, DataSourceName);
              sfdcAcolyte.click(selctTransMetaData).waitTillElementIsVisible(Ordr).click(selctTransMetaData).
                          selectComboByText(selctTransMetaData, TransMetaData).
                          click(selctcalcdateAttr).waitTillElementIsVisible(calcdate).selectComboByText(selctcalcdateAttr, CalculationDate);

              sfdcAcolyte.click(selctproduct).click(selctproduct).selectComboByText(selctproduct, "Base Product").click(selctprodAccount).
              selectComboByText(selctprodAccount, "Bill To");
              sfdcAcolyte.waitTillElementIsClickable(txtfilesuffix).click(txtfilesuffix).sendKeysTo(txtfilesuffix, SuffixC);
              sfdcAcolyte.click(MultiFileExtension).selectComboByText(MultiFileExtension, FileExtenstion1).selectComboByText(MultiFileExtension, FileExtenstion2).
                          click(selctdelimter).selectComboByText(selctdelimter, DelimiterC).click(selctproduct).jsClick(btnsubmitdatasrc);
              sfdcAcolyte.waitTillElementIsVisible(successresponse); 
       return PageFactory.initElements(driver, DataSourcePage.class);	
	}
	
	public DataSourcePage verifydiffrentsetofDelimterSuffixD(String DataSourceName, String TransMetaData, String CalculationDate, String Product,
            String ProgramAccount, String SuffixD, String FileExtenstion1, String FileExtenstion2,String DelimiterD) throws Exception {
		
		      sfdcAcolyte.waitTillElementIsVisible(CreateNewDataSrcbtn).
                          waitTillElementIsClickable(CreateNewDataSrcbtn).jsClick(CreateNewDataSrcbtn);
              sfdcAcolyte.waitTillElementIsClickable(txtdatasrc).click(txtdatasrc).sendKeysTo(txtdatasrc, DataSourceName);
              sfdcAcolyte.click(selctTransMetaData).waitTillElementIsVisible(Ordr).click(selctTransMetaData).
                          selectComboByText(selctTransMetaData, TransMetaData).
                          click(selctcalcdateAttr).waitTillElementIsVisible(calcdate).selectComboByText(selctcalcdateAttr, CalculationDate);

              sfdcAcolyte.click(selctproduct).click(selctproduct).selectComboByText(selctproduct, "Base Product").click(selctprodAccount).
              selectComboByText(selctprodAccount, "Bill To");
              sfdcAcolyte.waitTillElementIsClickable(txtfilesuffix).click(txtfilesuffix).sendKeysTo(txtfilesuffix, SuffixD);
              sfdcAcolyte.click(MultiFileExtension).selectComboByText(MultiFileExtension, FileExtenstion1).selectComboByText(MultiFileExtension, FileExtenstion2).
                          click(selctdelimter).selectComboByText(selctdelimter, DelimiterD).click(selctproduct).jsClick(btnsubmitdatasrc);
              sfdcAcolyte.waitTillElementIsVisible(successresponse);
       return PageFactory.initElements(driver, DataSourcePage.class);
		
	}
	
	public DataSourcePage verifydiffrentsetofDelimterSuffixE(String DataSourceName, String TransMetaData, String CalculationDate, String Product,
            String ProgramAccount, String SuffixE, String FileExtenstion1, String FileExtenstion2,String DelimiterE) throws Exception {
		
		      sfdcAcolyte.waitTillElementIsVisible(CreateNewDataSrcbtn).
                          waitTillElementIsClickable(CreateNewDataSrcbtn).jsClick(CreateNewDataSrcbtn);
              sfdcAcolyte.waitTillElementIsClickable(txtdatasrc).click(txtdatasrc).sendKeysTo(txtdatasrc, DataSourceName);
              sfdcAcolyte.click(selctTransMetaData).waitTillElementIsVisible(Ordr).click(selctTransMetaData).
                          selectComboByText(selctTransMetaData, TransMetaData).
                          click(selctcalcdateAttr).waitTillElementIsVisible(calcdate).selectComboByText(selctcalcdateAttr, CalculationDate);

              sfdcAcolyte.click(selctproduct).click(selctproduct).selectComboByText(selctproduct, "Base Product").click(selctprodAccount).
              selectComboByText(selctprodAccount, "Bill To");
              sfdcAcolyte.waitTillElementIsClickable(txtfilesuffix).click(txtfilesuffix).sendKeysTo(txtfilesuffix, SuffixE);
              sfdcAcolyte.click(MultiFileExtension).selectComboByText(MultiFileExtension, FileExtenstion1).selectComboByText(MultiFileExtension, FileExtenstion2).
                          click(selctdelimter).selectComboByText(selctdelimter, DelimiterE).click(selctproduct).click(btnsubmitdatasrc); 
              sfdcAcolyte.waitTillElementIsVisible(successresponse);
       return PageFactory.initElements(driver, DataSourcePage.class);
		
	}

	public void verifyValidation_DataSourceName() throws Exception {
		 
		sfdcAcolyte.waitTillElementIsVisible(CreateNewDataSrcbtn).waitTillElementIsClickable(CreateNewDataSrcbtn).jsClick(CreateNewDataSrcbtn).
		            waitTillElementIsVisible(btnsubmitdatasrc);
		sfdcAcolyte.click(selctTransMetaData).waitTillElementIsVisible(Ordr).click(selctTransMetaData).waitTillElementIsClickable(btnsubmitdatasrc).
		click(btnsubmitdatasrc).click(btnCancel);
		
		getdatasrcResponse=dataSrcResponse.getText();
		closeToastMessage();
	}

	public void verifyValidationTransactionMetaData(String DataSourceName) throws Exception {
		
		sfdcAcolyte.waitTillElementIsVisible(CreateNewDataSrcbtn).waitTillElementIsClickable(CreateNewDataSrcbtn).jsClick(CreateNewDataSrcbtn).
                    waitTillElementIsVisible(btnsubmitdatasrc);
        sfdcAcolyte.click(selctTransMetaData).waitTillElementIsVisible(Ordr).sendKeysTo(txtdatasrc, DataSourceName).clear(txtdatasrc).sendKeysTo(txtdatasrc, DataSourceName).click(btnsubmitdatasrc).click(btnCancel);
        getMetadataResponse=metadataResponse.getText();
        closeToastMessage();
	}

	public void verifyValidation_CalculationDate(String DataSourceName,String TransMetaData) throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(CreateNewDataSrcbtn).waitTillElementIsClickable(CreateNewDataSrcbtn).jsClick(CreateNewDataSrcbtn);
        sfdcAcolyte.waitTillElementIsVisible(btnsubmitdatasrc);
        sfdcAcolyte.click(selctTransMetaData).waitTillElementIsVisible(Ordr).sendKeysTo(txtdatasrc, DataSourceName);
		sfdcAcolyte.click(selctTransMetaData).selectComboByText(selctTransMetaData, TransMetaData).click(btnsubmitdatasrc).click(btnCancel);
		getCaldateResponse=calDatecResponse.getText();
		
	}

	public void veriyValidation_ProgramAccount(String DataSourceName,String TransMetaData, String CalculationDate) throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(CreateNewDataSrcbtn).waitTillElementIsClickable(CreateNewDataSrcbtn).jsClick(CreateNewDataSrcbtn);
		sfdcAcolyte.waitTillElementIsClickable(txtdatasrc).click(txtdatasrc).sendKeysTo(txtdatasrc, DataSourceName);
        sfdcAcolyte.click(selctTransMetaData).waitTillElementIsVisible(Ordr).click(selctTransMetaData).
                    selectComboByText(selctTransMetaData, TransMetaData).
                    click(selctcalcdateAttr).waitTillElementIsVisible(calcdate).selectComboByText(selctcalcdateAttr, CalculationDate).
                    click(btnsubmitdatasrc);
        closeToastMessage();	
	}

	public void veriyValidation_Product(String ProgramAccount) throws Exception {
		 sfdcAcolyte.click(selctTransMetaData).waitTillElementIsVisible(Ordr);
		 sfdcAcolyte.click(txtdatasrc);
		sfdcAcolyte.click(selctprodAccount).selectComboByText(selctprodAccount, ProgramAccount).click(btnsubmitdatasrc);
				
	}

	public DataSourcePage veriyValidationSuffix(String DataSourceName, String TransMetaData, String CalculationDate, String Product, String ProgramAccount) throws Exception {
		
		sfdcAcolyte.waitTillElementIsVisible(CreateNewDataSrcbtn).
                    waitTillElementIsClickable(CreateNewDataSrcbtn).jsClick(CreateNewDataSrcbtn);
        sfdcAcolyte.waitTillElementIsClickable(txtdatasrc).click(txtdatasrc).sendKeysTo(txtdatasrc, DataSourceName);
        sfdcAcolyte.click(selctTransMetaData).waitTillElementIsVisible(Ordr).click(selctTransMetaData).
                    selectComboByText(selctTransMetaData, TransMetaData).
                    click(selctcalcdateAttr).waitTillElementIsVisible(calcdate).selectComboByText(selctcalcdateAttr, CalculationDate);

        sfdcAcolyte.click(selctproduct).selectComboByText(selctproduct, Product).click(selctprodAccount).
                   selectComboByText(selctprodAccount, ProgramAccount);

        sfdcAcolyte.clickAndSendkeys(txtdatasrc, DataSourceName).clear(txtdatasrc).clickAndSendkeys(txtdatasrc, DataSourceName).
                    click(btnsubmitdatasrc).click(btnCancel);
        getSuffixResponse=suffixResponse.getText();
        closeToastMessage();
		return PageFactory.initElements(driver, DataSourcePage.class);	
	}

	public DataSourcePage veriyValidationFileExtension( String DataSourceName, String TransMetaData, String CalculationDate, String Product, String ProgramAccount, String FileSuffix,String Delimiter) throws Exception {
		
		sfdcAcolyte.waitTillElementIsVisible(CreateNewDataSrcbtn).
                    waitTillElementIsClickable(CreateNewDataSrcbtn).jsClick(CreateNewDataSrcbtn);
        sfdcAcolyte.waitTillElementIsClickable(txtdatasrc).click(txtdatasrc).sendKeysTo(txtdatasrc, DataSourceName);
        sfdcAcolyte.click(selctTransMetaData).waitTillElementIsVisible(Ordr).click(selctTransMetaData).
                   selectComboByText(selctTransMetaData, TransMetaData).
                   click(selctcalcdateAttr).waitTillElementIsVisible(calcdate).selectComboByText(selctcalcdateAttr, CalculationDate);

        sfdcAcolyte.click(selctproduct).selectComboByText(selctproduct, Product).click(selctprodAccount).
                   selectComboByText(selctprodAccount, ProgramAccount);
        
        sfdcAcolyte.click(selctdelimter).selectComboByText(selctdelimter, Delimiter).
                    waitTillElementIsClickable(txtfilesuffix).click(txtfilesuffix).sendKeysTo(txtfilesuffix, FileSuffix).
                    
                    click(btnsubmitdatasrc).click(btnCancel);
        getFileExtensionResponse=fileExtResponse.getText();
        closeToastMessage();
        return PageFactory.initElements(driver, DataSourcePage.class);	
	    
	}
		public DataSourcePage dataSdourceFilter(String ColumnName, String ColumnOperator, String FilterValue) throws Exception {
				
		sfdcAcolyte.waitTillElementIsVisible(Recentlyviewedlnk).click(Recentlyviewedlnk).
		waitTillElementIsVisible(Allviewedlnk).click(Allviewedlnk);
		sfdcAcolyte.waitTillElementIsVisible(filtericon).waitTillElementIsClickable(filtericon).click(filtericon);
		sfdcAcolyte.waitTillElementIsVisible(addFilterlnk).click(addFilterlnk);
		sfdcAcolyte.waitTillElementIsVisible(selectOperator).click(selectOperator).sendKeysTo(selectOperator, ColumnOperator).sendBoardKeys(Keys.ENTER).
        sendKeysTo(enterValue, FilterValue).click(filterDonebtn).click(filterSavebtn);
	    sfdcAcolyte.waitTillElementIsVisible(filterResponse);

        return PageFactory.initElements(driver, DataSourcePage.class);	
		
	}
		
		public DataSourcePage duplicateSdourceFilter(String ColumnName, String ColumnOperator, String FilterValue) throws Exception {
			
			sfdcAcolyte.click(Recentlyviewedlnk).
			waitTillElementIsVisible(Allviewedlnk).click(Allviewedlnk);
			sfdcAcolyte.waitTillElementIsVisible(filtericon).click(filtericon);
			sfdcAcolyte.waitTillElementIsVisible(addFilterlnk).click(addFilterlnk);
			sfdcAcolyte.waitTillElementIsVisible(selectField).click(selectField).sendKeysTo(selectField, ColumnName).sendBoardKeys(Keys.ENTER);
			sfdcAcolyte.waitTillElementIsVisible(selectOperator).click(selectOperator).sendKeysTo(selectOperator, ColumnOperator).sendBoardKeys(Keys.ENTER).
	        sendKeysTo(enterValue, FilterValue).click(filterDonebtn).click(filterSavebtn);
		    sfdcAcolyte.waitTillElementIsVisible(filterResponse);

	        return PageFactory.initElements(driver, DataSourcePage.class);	
			
		}

	public DataSourcePage deleteFilter() throws Exception {
	       
	        WebDriverWait wait = new WebDriverWait(driver, 15);
			WebElement expWaitRemoveallfltr=wait.until(ExpectedConditions.visibilityOf(removeAllftr));
			sfdcAcolyte.click(expWaitRemoveallfltr).click(filterSavebtn);
			
		
		return PageFactory.initElements(driver, DataSourcePage.class);
		
	}

	public DataSourcePage tempNewDataSource() throws Exception {
		 sfdcAcolyte.waitTillElementIsVisible(CreateNewDataSrcbtn).
                     waitTillElementIsClickable(CreateNewDataSrcbtn).jsClick(CreateNewDataSrcbtn);
         sfdcAcolyte.waitTillElementIsClickable(txtdatasrc).click(txtdatasrc).sendKeysTo(txtdatasrc, "klklkl");
         sfdcAcolyte.click(selctTransMetaData).waitTillElementIsVisible(Ordr).click(selctTransMetaData).
                     selectComboByText(selctTransMetaData, "Order Line Item").
                     click(selctcalcdateAttr).waitTillElementIsVisible(calcdate).selectComboByText(selctcalcdateAttr, "Ready for Activation Date");

        return PageFactory.initElements(driver, DataSourcePage.class);	
	}

	public DataSourcePage verifyCancel() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(CreateNewDataSrcbtn).
        waitTillElementIsClickable(CreateNewDataSrcbtn).jsClick(CreateNewDataSrcbtn).click(btnCancel);
		sfdcAcolyte.waitTillElementIsVisible(successresponse);
		return PageFactory.initElements(driver, DataSourcePage.class);
	}

	public DataSourcePage veriyValidationDelimiter(String DataSourceName, String TransMetaData, String CalculationDate, String Product,
            String ProgramAccount, String FileSuffix, String FileExtenstion1) throws Exception {
		  sfdcAcolyte.waitTillElementIsVisible(CreateNewDataSrcbtn).
                      waitTillElementIsClickable(CreateNewDataSrcbtn).jsClick(CreateNewDataSrcbtn);
          sfdcAcolyte.waitTillElementIsVisible(txtdatasrc).waitTillElementIsClickable(txtdatasrc).click(txtdatasrc).sendKeysTo(txtdatasrc, DataSourceName);
          sfdcAcolyte.click(selctTransMetaData).waitTillElementIsVisible(Ordr).click(selctTransMetaData).
                      selectComboByText(selctTransMetaData, TransMetaData).
                      click(selctcalcdateAttr).waitTillElementIsVisible(calcdate).selectComboByText(selctcalcdateAttr, CalculationDate);

          sfdcAcolyte.click(selctproduct).selectComboByText(selctproduct, Product).click(selctprodAccount).
                       selectComboByText(selctprodAccount, ProgramAccount);
          sfdcAcolyte.waitTillElementIsClickable(txtfilesuffix).click(txtfilesuffix).sendKeysTo(txtfilesuffix, FileSuffix);
          sfdcAcolyte.click(MultiFileExtension).selectComboByText(MultiFileExtension, FileExtenstion1).
                      click(btnsubmitdatasrc).click(btnCancel);
          getDelimiterResponse=delimiterResponse.getText();
          closeToastMessage();
          return PageFactory.initElements(driver, DataSourcePage.class);	
		
	}

	public void closeToastMessage() throws Exception {
		sfdcAcolyte.waitTillElementIsClickable(closeToastResponse).click(closeToastResponse);
		
	}

	public void deleteSfdcFilter() throws Exception {
		genericPage.waitTillStaleElementToBeVisible(showMore);
		sfdcAcolyte.waitTillElementIsVisible(showMore).
	    jsClick(showMore);
		WebDriverWait wait = new WebDriverWait(driver, 15);
		WebElement expWaitshowDeleteAction=wait.until(ExpectedConditions.visibilityOf(showDeleteAction));
		
	     sfdcAcolyte.jsClick(expWaitshowDeleteAction).
	     jsClick(confirmDeleteAction);
		
	}

	public void cancelDatasource() throws Exception {
		sfdcAcolyte.click(btnCancel);
		
	}
	
	public void dataSourceRefresh() throws Exception {
		sfdcAcolyte.refreshPage();
		
	}

	public void removeFilter() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(filtericon).click(filtericon);
        sfdcAcolyte.waitTillElementIsVisible(removeAllftr).click(removeAllftr).click(filterSavebtn);
		
	}

	public void deleteDataSource() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(DeleteDataSrcbtn).click(DeleteDataSrcbtn);
		
	}
	

}
	


	
