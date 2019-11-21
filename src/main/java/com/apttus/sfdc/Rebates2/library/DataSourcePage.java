package com.apttus.sfdc.Rebates2.library;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.apttus.sfdc.Rebates2.common.StartUpPage;
import com.apttus.sfdc.Rebates2.common.GenericPage;

public class DataSourcePage extends StartUpPage {
	  
    /*.........Create Data Source..........*/
    
    @FindBy(css="a[title='New']")
	public WebElement NewDataSrcbtn;
    
    @FindBy(css="input[class='slds-input'][type='text'][aria-describedby='help-message-109']")
	public WebElement DataSrcNametxt;
    
    @FindBy(xpath="//span[@class='slds-truncate'][text()='Data Sources']")
	public WebElement Datasrclink;
    
    @FindBy(css="[title='New'][class='forceActionLink']")
	public WebElement CreateNewDataSrcbtn;
    
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
   
    @FindBy(css="[class='removeAll']")
   	public WebElement RemoveAllftr;
    
    @FindBy(xpath="//*[@id=\"brandBand_1\"]//..//span/div/a/lightning-icon/lightning-primitive-icon")
  	public  WebElement ShowMore;
     
    @FindBy(xpath = "//a[@title='Delete']")
  	public WebElement showDeleteAction;
  	
  	@FindBy(xpath = "//button[@title='Delete']")
  	public WebElement ConfirmDeleteAction;
    
    /* .............XpathValidations...................*/
    
    @FindBy(css="span[class*='toastMessage']")
   	public WebElement TempDataSrcResponse;
    
    @FindBy(css="button[title='Close']")
   	public WebElement CloseToastResponse;
    
    @FindBy(xpath="//span[contains(text(),'Please enter Data Source Name')]")
   	public WebElement DataSrcResponse;
    
    @FindBy(xpath="//*[contains(text(),'Error creating record')]")
   	public WebElement MetadataResponse;
    
    @FindBy(xpath="//*[contains(text(),'Please select Calculation Date')]")
   	public WebElement CalDatecResponse;
    
    @FindBy(xpath="//*[contains(text(),'Please select Program')]")
   	public WebElement ProgramAcctesponse;
    
    @FindBy(xpath="//*[contains(text(),'Please select Product .')]")
   	public WebElement ProductResponse;
    
    @FindBy(xpath="//*[contains(text(),'Please enter File Suffix to Ignore')]")
   	public WebElement SuffixResponse;
    
    @FindBy(xpath="//*[contains(text(),'Please select File Extension.')]")
   	public WebElement FileExtResponse;
    
    @FindBy(xpath="//*[contains(text(),'Please select Delimiter')]")
   	public WebElement DelimiterResponse;
    
    @FindBy(xpath="//*[text()='List view updated.']")
   	public WebElement FilterResponse;
    
    @FindBy(xpath="//*[text()='List view updated.']")
   	public WebElement nwFilterResponse;
    
    @FindBy(xpath="//option[text()='Order Line Item']")
   	public WebElement Ordr;
    
    @FindBy(xpath="//option[text()='Ready for Activation Date']")
   	public WebElement calcdate;
    
  
    /* .............Validations...................*/
    public String getProgram="Please select Program Account .";
    public String success="Data Source created";
    public String Duplicate="Duplicate Data Source Name Found";
    public String ResponseDataSrc="Please enter Data Source Name";
    public String ResponseMetaData="Error creating record";
    public String ResponseCalcDate="Please select Calculation Date .";
    public String ResponsePrgmAccount="Please select Program Account .";
    public String ResponseProduct="Please select Product .";
    public String ResponseSuffix="Please enter File Suffix to Ignore.";
    public String ResponseFileExt="Please select File Extension.";
    public String ResponseDelimiter="Please select Delimiter.";
    public String ResponseFilter="List view updated.";
    public String nwResponseFilter="List view updated.";
    public String duplicateRecord;
    public String getDelimiterResponse;
    public String getFileExtensionResponse;
    public String getSuffixResponse;
    public String getdatasrcResponse;
    public String getMetadataResponse;
    public String getCaldateResponse;
    
    
    
    GenericPage genericPage;

	public DataSourcePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		sfdcAcolyte.setWaitTime(60);
		genericPage = new GenericPage(driver);
	}
	
	public DataSourcePage CreateSaveNewDataSource(String DataSourceName, String TransMetaData, String CalculationDate, String Product,
			                                      String ProgramAccount, String FileSuffix, String FileExtenstion1, String FileExtenstion2,String Delimiter) throws Exception {
		
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

	public DataSourcePage SearchDataSource(String createdDataSource) throws Exception {
			
		sfdcAcolyte.click(Recentlyviewedlnk);
		sfdcAcolyte.waitTillElementIsVisible(Allviewedlnk).click(Allviewedlnk).
		            click(searchtxt);
		searchtxt.sendKeys(createdDataSource);
		sfdcAcolyte.sendBoardKeys(Keys.ENTER); 
		Thread.sleep(8000);
		
				
		return PageFactory.initElements(driver, DataSourcePage.class);
				
	}

	public DataSourcePage Verifyduplicate(String duplicateDataSource, String TransMetaData, String CalculationDate, String Product,
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
        CloseToastMessage();
        duplicateRecord=Duplicateresponse.getText();
		
		return PageFactory.initElements(driver, DataSourcePage.class);
		
	}

	public void DuplicateSaveNewDataSource(String DupDataSourceName, String TransMetaData, String CalculationDate, String Product,
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
        CloseToastMessage();
 		
	}

	public DataSourcePage VerifydiffrentsetofDelimterSuffixB(String DataSourceName, String TransMetaData, String CalculationDate, String Product,
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
                          click(selctdelimter).selectComboByText(selctdelimter, DelimiterB).click(btnsubmitdatasrc).click(btnCancel);

              sfdcAcolyte.waitTillElementIsVisible(successresponse);

       return PageFactory.initElements(driver, DataSourcePage.class);
		
	}
	
	public DataSourcePage VerifydiffrentsetofDelimterSuffixC(String DataSourceName, String TransMetaData, String CalculationDate, String Product,
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
                          click(selctdelimter).selectComboByText(selctdelimter, DelimiterC).jsClick(btnsubmitdatasrc).jsClick(btnCancel);


       return PageFactory.initElements(driver, DataSourcePage.class);
		
	}
	
	public DataSourcePage VerifydiffrentsetofDelimterSuffixD(String DataSourceName, String TransMetaData, String CalculationDate, String Product,
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
                          click(selctdelimter).selectComboByText(selctdelimter, DelimiterD).jsClick(btnsubmitdatasrc).jsClick(btnCancel);


       return PageFactory.initElements(driver, DataSourcePage.class);
		
	}
	
	public DataSourcePage VerifydiffrentsetofDelimterSuffixE(String DataSourceName, String TransMetaData, String CalculationDate, String Product,
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
                          click(selctdelimter).selectComboByText(selctdelimter, DelimiterE).jsClick(btnsubmitdatasrc).jsClick(btnCancel);


             
       return PageFactory.initElements(driver, DataSourcePage.class);
		
	}

	public void VerifyValidation_DataSourceName() throws Exception {
		 
		sfdcAcolyte.waitTillElementIsVisible(CreateNewDataSrcbtn).waitTillElementIsClickable(CreateNewDataSrcbtn).jsClick(CreateNewDataSrcbtn).
		            waitTillElementIsVisible(btnsubmitdatasrc);
		sfdcAcolyte.click(selctTransMetaData).waitTillElementIsVisible(Ordr).click(selctTransMetaData).waitTillElementIsClickable(btnsubmitdatasrc).
		click(btnsubmitdatasrc).click(btnCancel);
		
		getdatasrcResponse=DataSrcResponse.getText();
		CloseToastMessage();
	}

	public void VerifyValidationTransactionMetaData(String DataSourceName) throws Exception {
		
		sfdcAcolyte.waitTillElementIsVisible(CreateNewDataSrcbtn).waitTillElementIsClickable(CreateNewDataSrcbtn).jsClick(CreateNewDataSrcbtn).
                    waitTillElementIsVisible(btnsubmitdatasrc);
        sfdcAcolyte.click(selctTransMetaData).waitTillElementIsVisible(Ordr).sendKeysTo(txtdatasrc, DataSourceName).clear(txtdatasrc).sendKeysTo(txtdatasrc, DataSourceName).click(btnsubmitdatasrc).click(btnCancel);
        getMetadataResponse=MetadataResponse.getText();
        CloseToastMessage();
	}

	public void VerifyValidation_CalculationDate(String DataSourceName,String TransMetaData) throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(CreateNewDataSrcbtn).waitTillElementIsClickable(CreateNewDataSrcbtn).jsClick(CreateNewDataSrcbtn);
        Thread.sleep(2000);
        sfdcAcolyte.waitTillElementIsVisible(btnsubmitdatasrc);
        sfdcAcolyte.click(selctTransMetaData).waitTillElementIsVisible(Ordr).sendKeysTo(txtdatasrc, DataSourceName);
		sfdcAcolyte.click(selctTransMetaData).selectComboByText(selctTransMetaData, TransMetaData).click(btnsubmitdatasrc).click(btnCancel);
		
		getCaldateResponse=CalDatecResponse.getText();
		
	}

	public void VeriyValidation_ProgramAccount(String DataSourceName,String TransMetaData, String CalculationDate) throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(CreateNewDataSrcbtn).waitTillElementIsClickable(CreateNewDataSrcbtn).jsClick(CreateNewDataSrcbtn);
		sfdcAcolyte.waitTillElementIsClickable(txtdatasrc).click(txtdatasrc).sendKeysTo(txtdatasrc, DataSourceName);
        sfdcAcolyte.click(selctTransMetaData).waitTillElementIsVisible(Ordr).click(selctTransMetaData).
                    selectComboByText(selctTransMetaData, TransMetaData).
                    click(selctcalcdateAttr).waitTillElementIsVisible(calcdate).selectComboByText(selctcalcdateAttr, CalculationDate).
                    click(btnsubmitdatasrc);
		
	}

	public void VeriyValidation_Product(String ProgramAccount) throws Exception {
		 sfdcAcolyte.click(selctTransMetaData).waitTillElementIsVisible(Ordr);
		sfdcAcolyte.click(selctprodAccount).selectComboByText(selctprodAccount, ProgramAccount).click(btnsubmitdatasrc);
				
	}

	public DataSourcePage VeriyValidationSuffix(String DataSourceName, String TransMetaData, String CalculationDate, String Product, String ProgramAccount) throws Exception {
		
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
        getSuffixResponse=SuffixResponse.getText();
        CloseToastMessage();
		return PageFactory.initElements(driver, DataSourcePage.class);	
	}

	public DataSourcePage VeriyValidationFileExtension( String DataSourceName, String TransMetaData, String CalculationDate, String Product, String ProgramAccount, String FileSuffix,String Delimiter) throws Exception {
		
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
        getFileExtensionResponse=FileExtResponse.getText();
        CloseToastMessage();
        return PageFactory.initElements(driver, DataSourcePage.class);	
	    
	}
		public DataSourcePage DataSdourceFilter(String ColumnName, String ColumnOperator, String FilterValue) throws Exception {
				
		sfdcAcolyte.waitTillElementIsVisible(Recentlyviewedlnk).click(Recentlyviewedlnk).
		waitTillElementIsVisible(Allviewedlnk).click(Allviewedlnk);
		sfdcAcolyte.waitTillElementIsVisible(filtericon).waitTillElementIsClickable(filtericon).click(filtericon);
		sfdcAcolyte.waitTillElementIsVisible(AddFilterlnk).click(AddFilterlnk);
		
		sfdcAcolyte.waitTillElementIsVisible(SelectField).click(SelectField).sendKeysTo(SelectField, ColumnName).sendBoardKeys(Keys.ENTER);
		
		sfdcAcolyte.waitTillElementIsVisible(SelectOperator).click(SelectOperator).sendKeysTo(SelectOperator, ColumnOperator).sendBoardKeys(Keys.ENTER).
        sendKeysTo(EnterValue, FilterValue).click(flrDonebtn).click(flrSavebtn);
	    sfdcAcolyte.waitTillElementIsVisible(FilterResponse);

        return PageFactory.initElements(driver, DataSourcePage.class);	
		
	}
		
		public DataSourcePage DuplicateSdourceFilter(String ColumnName, String ColumnOperator, String FilterValue) throws Exception {
			
			sfdcAcolyte.click(Recentlyviewedlnk).
			waitTillElementIsVisible(Allviewedlnk).click(Allviewedlnk);
			sfdcAcolyte.waitTillElementIsVisible(filtericon).click(filtericon);
			sfdcAcolyte.waitTillElementIsVisible(AddFilterlnk).click(AddFilterlnk);
			Thread.sleep(10000);
			sfdcAcolyte.waitTillElementIsVisible(SelectField).click(SelectField).sendKeysTo(SelectField, ColumnName).sendBoardKeys(Keys.ENTER);
			sfdcAcolyte.waitTillElementIsVisible(SelectOperator).click(SelectOperator).sendKeysTo(SelectOperator, ColumnOperator).sendBoardKeys(Keys.ENTER).
	        sendKeysTo(EnterValue, FilterValue).click(flrDonebtn).click(flrSavebtn);
		    sfdcAcolyte.waitTillElementIsVisible(FilterResponse);

	        return PageFactory.initElements(driver, DataSourcePage.class);	
			
		}

	public DataSourcePage deleteFilter() throws Exception {
	        Thread.sleep(1000);
			sfdcAcolyte.waitTillElementIsVisible(RemoveAllftr).waitTillElementIsClickable(RemoveAllftr).click(RemoveAllftr).click(flrSavebtn);
			Thread.sleep(1000);
		
		return PageFactory.initElements(driver, DataSourcePage.class);
		
	}

	public DataSourcePage TempNewDataSource() throws Exception {
		 sfdcAcolyte.waitTillElementIsVisible(CreateNewDataSrcbtn).
                     waitTillElementIsClickable(CreateNewDataSrcbtn).jsClick(CreateNewDataSrcbtn);
         sfdcAcolyte.waitTillElementIsClickable(txtdatasrc).click(txtdatasrc).sendKeysTo(txtdatasrc, "klklkl");
         sfdcAcolyte.click(selctTransMetaData).waitTillElementIsVisible(Ordr).click(selctTransMetaData).
                     selectComboByText(selctTransMetaData, "Order Line Item").
                     click(selctcalcdateAttr).waitTillElementIsVisible(calcdate).selectComboByText(selctcalcdateAttr, "Ready for Activation Date");

        return PageFactory.initElements(driver, DataSourcePage.class);
		
	}

	public DataSourcePage VerifyCancel() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(CreateNewDataSrcbtn).
        waitTillElementIsClickable(CreateNewDataSrcbtn).jsClick(CreateNewDataSrcbtn).click(btnCancel);
		sfdcAcolyte.waitTillElementIsVisible(successresponse);
		return PageFactory.initElements(driver, DataSourcePage.class);
	}

	public DataSourcePage VeriyValidationDelimiter(String DataSourceName, String TransMetaData, String CalculationDate, String Product,
            String ProgramAccount, String FileSuffix, String FileExtenstion1) throws Exception {
		  sfdcAcolyte.waitTillElementIsVisible(CreateNewDataSrcbtn).
                      waitTillElementIsClickable(CreateNewDataSrcbtn).jsClick(CreateNewDataSrcbtn);
          sfdcAcolyte.waitTillElementIsClickable(txtdatasrc).click(txtdatasrc).sendKeysTo(txtdatasrc, DataSourceName);
          sfdcAcolyte.click(selctTransMetaData).waitTillElementIsVisible(Ordr).click(selctTransMetaData).
                      selectComboByText(selctTransMetaData, TransMetaData).
                      click(selctcalcdateAttr).waitTillElementIsVisible(calcdate).selectComboByText(selctcalcdateAttr, CalculationDate);

          sfdcAcolyte.click(selctproduct).selectComboByText(selctproduct, Product).click(selctprodAccount).
                       selectComboByText(selctprodAccount, ProgramAccount);
          sfdcAcolyte.waitTillElementIsClickable(txtfilesuffix).click(txtfilesuffix).sendKeysTo(txtfilesuffix, FileSuffix);
          sfdcAcolyte.click(MultiFileExtension).selectComboByText(MultiFileExtension, FileExtenstion1).
                      click(btnsubmitdatasrc).click(btnCancel);
          getDelimiterResponse=DelimiterResponse.getText();
          CloseToastMessage();
          return PageFactory.initElements(driver, DataSourcePage.class);	
		
	}

	public void CloseToastMessage() throws Exception {
		sfdcAcolyte.waitTillElementIsClickable(CloseToastResponse).click(CloseToastResponse);
		
	}

	public void DeleteSFDCFilter() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(ShowMore).
	      jsClick(ShowMore);
	      Thread.sleep(2000);
	      sfdcAcolyte.jsClick(showDeleteAction).
	      jsClick(ConfirmDeleteAction);
		
	}

	public void CancelDatasource() throws Exception {
		sfdcAcolyte.click(btnCancel);
		
	}
	

}
	


	
