package com.apttus.sfdc.rebates.ui.library;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.apttus.sfdc.rebates.common.GenericPage;
import com.apttus.sfdc.rebates.common.StartUpPage;

public class DataSourcePage extends StartUpPage {
	  
    
    @FindBy(css="a[title='New']")
	public WebElement NewDataSourcebtn;
    
    @FindBy(css="input[class='slds-input'][type='text'][aria-describedby='help-message-109']")
	public WebElement dataSourceNametxt;
     
    @FindBy(css="[title='New']")
	public WebElement createNewDataSourcebtn;
    
    @FindBy(css="a[title='Delete'][role='button']")
	public WebElement deleteDataSourcebtn;
        
    @FindBy(xpath = "//select[@class='slds-select metaData']")  
	public WebElement selctTransMetaData;
    
    @FindBy(xpath="//select[@class='slds-select calcDateAttr']")
	public WebElement selectcalculationdateAttribute;
    
    @FindBy(xpath="//select[@class='slds-select prodAttr']")
	public WebElement selectproduct;
    
    @FindBy(xpath="//select[@class='slds-select proAcctAttr']")
	public WebElement selectproductAccount;
    
    @FindBy(xpath="//select[@class='slds-select delimiter']")
	public WebElement selectdelimter;
    
    @FindBy(xpath="//select[@class='slds-select fileExtension']")
	public WebElement multiFileExtension;
   
    @FindBy(xpath="//label[contains(text(),'File Suffix To ignore')]//following::div[1]/input")
	public WebElement txtfilesuffix;
    
    @FindBy(xpath="//label[contains(text(),'File Suffix To ignore')]//following::div[1]/input")
	public WebElement duplicatefilesuffix;
    
    @FindBy(xpath="//label[contains(text(),'Data Source Name')]//following::div[1]/input")
	public WebElement txtdataSource;
    
    @FindBy(xpath="//button[@class='slds-button slds-button_brand']")
	public WebElement btnsubmitdataSource;
    
    @FindBy(xpath="//button[@class='slds-button slds-button_neutral']")
	public WebElement btnCancel;
   
    @FindBy(xpath="//span[contains(text(),'Data Source created')]")
	public WebElement successresponse;
    
    @FindBy(xpath="//span[contains(text(),'Duplicate Data Source Name Found')]")
	public WebElement duplicateresponse;
    
   /* .............ListPage...................*/
    @FindBy(css = "span.triggerLinkText.selectedListView.uiOutputText")
	public WebElement recentlyviewedlnk;
    
	@FindBy(xpath="//span[text()='All']")
	public WebElement allviewedlnk;
    
    @FindBy(css="input[placeholder='Search this list...']")
	public WebElement searchtxt;
    
    @FindBy(xpath="//select[3]/option[2]")
	public WebElement optionProd;
  
    @FindBy(css="span[title*='DSrcAutomation']")
    public WebElement namecolmn;
    
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
   	public WebElement removeAllfilter;
    
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
   	public WebElement calculationDateResponse;
    
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
     
    /* .............Expected Validations...................*/
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
    
    GenericPage genericPage;
	public DataSourcePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		sfdcAcolyte.setWaitTime(60);
		genericPage = new GenericPage(driver);
			
	}
	
	public DataSourcePage createSaveNewDataSource(String DataSourceName, String TransMetaData, String CalculationDate, String Product,
			                                      String ProgramAccount, String FileSuffix, String FileExtenstion1, String FileExtenstion2,String Delimiter) throws Exception {
		
		String dataSourceURL=sfdcAcolyte.getCurrentURL();
		sfdcAcolyte.navigateTo(dataSourceURL);
		sfdcAcolyte.waitTillElementIsVisible(createNewDataSourcebtn).
                    waitTillElementIsClickable(createNewDataSourcebtn).jsClick(createNewDataSourcebtn);
        sfdcAcolyte.waitTillElementIsVisible(txtdataSource).click(txtdataSource).sendKeysTo(txtdataSource, DataSourceName);
        sfdcAcolyte.click(selctTransMetaData).waitTillElementIsVisible(Ordr).click(selctTransMetaData).
                    selectComboByText(selctTransMetaData, TransMetaData).
                    click(selectcalculationdateAttribute).waitTillElementIsVisible(calcdate).selectComboByText(selectcalculationdateAttribute, CalculationDate);

       sfdcAcolyte.click(selectproduct).selectComboByText(selectproduct, Product).click(selectproductAccount).
                   selectComboByText(selectproductAccount, ProgramAccount);
       sfdcAcolyte.waitTillElementIsClickable(txtfilesuffix).click(txtfilesuffix).sendKeysTo(txtfilesuffix, FileSuffix);
       sfdcAcolyte.click(multiFileExtension).selectComboByText(multiFileExtension, FileExtenstion1).selectComboByText(multiFileExtension, FileExtenstion2).
                   click(selectdelimter).selectComboByText(selectdelimter, Delimiter).click(btnsubmitdataSource);
		sfdcAcolyte.waitTillElementIsVisible(successresponse);	
		return PageFactory.initElements(driver, DataSourcePage.class);
	
	}


	public DataSourcePage verifyduplicate(String duplicateDataSource, String TransMetaData, String CalculationDate, String Product,
            String ProgramAccount, String FileSuffix, String FileExtenstion1, String FileExtenstion2,String Delimiter) throws Exception {
			
		sfdcAcolyte.waitTillElementIsVisible(createNewDataSourcebtn).waitTillElementIsClickable(createNewDataSourcebtn).jsClick(createNewDataSourcebtn);
        sfdcAcolyte.waitTillElementIsVisible(txtdataSource).click(txtdataSource).sendKeysTo(txtdataSource, duplicateDataSource);
        sfdcAcolyte.click(selctTransMetaData).waitTillElementIsVisible(Ordr).click(selctTransMetaData).selectComboByText(selctTransMetaData, TransMetaData).
                    click(selectcalculationdateAttribute).waitTillElementIsVisible(calcdate).selectComboByText(selectcalculationdateAttribute, CalculationDate);
        sfdcAcolyte.click(selectproduct).selectComboByText(selectproduct, Product).click(selectproductAccount).
                    selectComboByText(selectproductAccount, ProgramAccount);
        sfdcAcolyte.click(multiFileExtension).selectComboByText(multiFileExtension, FileExtenstion1).selectComboByText(multiFileExtension, FileExtenstion2).
                    waitTillElementIsVisible(selectdelimter).click(selectdelimter).selectComboByText(selectdelimter, Delimiter).
                    waitTillElementIsVisible(duplicatefilesuffix).click(duplicatefilesuffix).sendKeysTo(duplicatefilesuffix, FileSuffix).
                    click(btnsubmitdataSource);
        sfdcAcolyte.waitTillElementIsVisible(duplicateresponse);	
        closeToastMessage();
        duplicateRecord=duplicateresponse.getText();
		return PageFactory.initElements(driver, DataSourcePage.class);
		
	}

	public void duplicateSaveNewDataSource(String DupDataSourceName, String TransMetaData, String CalculationDate, String Product,
                  String ProgramAccount, String FileSuffix, String FileExtenstion1, String FileExtenstion2,String Delimiter) throws Exception { 
		
         sfdcAcolyte.waitTillElementIsVisible(createNewDataSourcebtn).
                     waitTillElementIsClickable(createNewDataSourcebtn).jsClick(createNewDataSourcebtn);
         sfdcAcolyte.waitTillElementIsClickable(txtdataSource).click(txtdataSource).sendKeysTo(txtdataSource, DupDataSourceName);
         sfdcAcolyte.click(selctTransMetaData).waitTillElementIsVisible(Ordr).click(selctTransMetaData).
                     selectComboByText(selctTransMetaData, TransMetaData).
                     click(selectcalculationdateAttribute).waitTillElementIsVisible(calcdate).selectComboByText(selectcalculationdateAttribute, CalculationDate);
         sfdcAcolyte.click(selectproduct).selectComboByText(selectproduct, Product).click(selectproductAccount).
                    selectComboByText(selectproductAccount, ProgramAccount);
         sfdcAcolyte.waitTillElementIsClickable(txtfilesuffix).click(txtfilesuffix).sendKeysTo(txtfilesuffix, FileSuffix);
         sfdcAcolyte.click(multiFileExtension).selectComboByText(multiFileExtension, FileExtenstion1).selectComboByText(multiFileExtension, FileExtenstion2).
                    click(selectdelimter).selectComboByText(selectdelimter, Delimiter).click(btnsubmitdataSource);
         sfdcAcolyte.waitTillElementIsVisible(successresponse);
         closeToastMessage();
 		
	}

	public void verifyValidation_DataSourceName() throws Exception {
		 
		sfdcAcolyte.waitTillElementIsVisible(createNewDataSourcebtn).waitTillElementIsClickable(createNewDataSourcebtn).jsClick(createNewDataSourcebtn).
		            waitTillElementIsVisible(btnsubmitdataSource);
		sfdcAcolyte.click(selctTransMetaData).waitTillElementIsVisible(Ordr).click(selctTransMetaData).waitTillElementIsClickable(btnsubmitdataSource).
		click(btnsubmitdataSource).click(btnCancel);
		getdatasrcResponse=dataSrcResponse.getText();
		closeToastMessage();
	}

	public void verifyValidationTransactionMetaData(String DataSourceName) throws Exception {
		
		sfdcAcolyte.waitTillElementIsVisible(createNewDataSourcebtn).waitTillElementIsClickable(createNewDataSourcebtn).jsClick(createNewDataSourcebtn).
                    waitTillElementIsVisible(btnsubmitdataSource);
        sfdcAcolyte.click(selctTransMetaData).waitTillElementIsVisible(Ordr).sendKeysTo(txtdataSource, DataSourceName).clear(txtdataSource).sendKeysTo(txtdataSource, DataSourceName).click(btnsubmitdataSource).click(btnCancel);
        getMetadataResponse=metadataResponse.getText();
        closeToastMessage();
	}

	public void verifyValidation_CalculationDate(String DataSourceName,String TransMetaData) throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(createNewDataSourcebtn).waitTillElementIsClickable(createNewDataSourcebtn).jsClick(createNewDataSourcebtn);
        sfdcAcolyte.waitTillElementIsVisible(btnsubmitdataSource);
        sfdcAcolyte.click(selctTransMetaData).waitTillElementIsVisible(Ordr).sendKeysTo(txtdataSource, DataSourceName);
		sfdcAcolyte.click(selctTransMetaData).selectComboByText(selctTransMetaData, TransMetaData).click(btnsubmitdataSource).click(btnCancel);
		getCaldateResponse=calculationDateResponse.getText();
		
	}

	public void verifyValidation_ProgramAccount(String DataSourceName,String TransMetaData, String CalculationDate) throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(createNewDataSourcebtn).waitTillElementIsClickable(createNewDataSourcebtn).jsClick(createNewDataSourcebtn);
		sfdcAcolyte.waitTillElementIsClickable(txtdataSource).click(txtdataSource).sendKeysTo(txtdataSource, DataSourceName);
        sfdcAcolyte.click(selctTransMetaData).waitTillElementIsVisible(Ordr).click(selctTransMetaData).
                    selectComboByText(selctTransMetaData, TransMetaData).
                    click(selectcalculationdateAttribute).waitTillElementIsVisible(calcdate).selectComboByText(selectcalculationdateAttribute, CalculationDate).click(btnsubmitdataSource);
        closeToastMessage();	
	}

	public void verifyValidation_Product(String ProgramAccount) throws Exception {
		 sfdcAcolyte.click(selctTransMetaData).waitTillElementIsVisible(Ordr);
		 sfdcAcolyte.click(txtdataSource);
		 sfdcAcolyte.click(selectproductAccount).selectComboByText(selectproductAccount, ProgramAccount).click(btnsubmitdataSource);
		 sfdcAcolyte.waitTillElementIsVisible(productResponse);
		 closeToastMessage();
				
	}

	public DataSourcePage verifyValidationSuffix(String DataSourceName, String TransMetaData, String CalculationDate, String Product, String ProgramAccount) throws Exception {
		
		sfdcAcolyte.waitTillElementIsVisible(createNewDataSourcebtn).
                    waitTillElementIsClickable(createNewDataSourcebtn).jsClick(createNewDataSourcebtn);
        sfdcAcolyte.waitTillElementIsClickable(txtdataSource).click(txtdataSource).sendKeysTo(txtdataSource, DataSourceName);
        sfdcAcolyte.click(selctTransMetaData).waitTillElementIsVisible(Ordr).click(selctTransMetaData).
                    selectComboByText(selctTransMetaData, TransMetaData).
                    click(selectcalculationdateAttribute).waitTillElementIsVisible(calcdate).selectComboByText(selectcalculationdateAttribute, CalculationDate);
        sfdcAcolyte.click(selectproduct).selectComboByText(selectproduct, Product).click(selectproductAccount).
                   selectComboByText(selectproductAccount, ProgramAccount);
        sfdcAcolyte.clickAndSendkeys(txtdataSource, DataSourceName).clear(txtdataSource).clickAndSendkeys(txtdataSource, DataSourceName).
                    click(btnsubmitdataSource).click(btnCancel);
        getSuffixResponse=suffixResponse.getText();
        closeToastMessage();
		return PageFactory.initElements(driver, DataSourcePage.class);	
	}

	public DataSourcePage verifyValidationFileExtension( String DataSourceName, String TransMetaData, String CalculationDate, String Product, String ProgramAccount, String FileSuffix,String Delimiter) throws Exception {
		
		sfdcAcolyte.waitTillElementIsVisible(createNewDataSourcebtn).
                    waitTillElementIsClickable(createNewDataSourcebtn).jsClick(createNewDataSourcebtn);
        sfdcAcolyte.waitTillElementIsClickable(txtdataSource).click(txtdataSource).sendKeysTo(txtdataSource, DataSourceName);
        sfdcAcolyte.click(selctTransMetaData).waitTillElementIsVisible(Ordr).click(selctTransMetaData).
                   selectComboByText(selctTransMetaData, TransMetaData).
                   click(selectcalculationdateAttribute).waitTillElementIsVisible(calcdate).selectComboByText(selectcalculationdateAttribute, CalculationDate);
        sfdcAcolyte.click(selectproduct).selectComboByText(selectproduct, Product).click(selectproductAccount).
                   selectComboByText(selectproductAccount, ProgramAccount);
        sfdcAcolyte.click(selectdelimter).selectComboByText(selectdelimter, Delimiter).
                    waitTillElementIsClickable(txtfilesuffix).click(txtfilesuffix).sendKeysTo(txtfilesuffix, FileSuffix).
                    click(btnsubmitdataSource).click(btnCancel);
        getFileExtensionResponse=fileExtResponse.getText();
        closeToastMessage();
        return PageFactory.initElements(driver, DataSourcePage.class);	
	    
	}
		public DataSourcePage dataSourceFilter(String ColumnName, String ColumnOperator, String FilterValue) throws Exception {
				
		sfdcAcolyte.waitTillElementIsVisible(recentlyviewedlnk).click(recentlyviewedlnk).
		waitTillElementIsVisible(allviewedlnk).click(allviewedlnk);
		sfdcAcolyte.waitTillElementIsVisible(filtericon).waitTillElementIsClickable(filtericon).click(filtericon);
		sfdcAcolyte.waitTillElementIsVisible(addFilterlnk).click(addFilterlnk);
		sfdcAcolyte.waitTillElementIsVisible(selectField).click(selectField).sendKeysTo(selectField, ColumnName).sendBoardKeys(Keys.ENTER);
		sfdcAcolyte.waitTillElementIsVisible(selectOperator).click(selectOperator).sendKeysTo(selectOperator, ColumnOperator).sendBoardKeys(Keys.ENTER).
        sendKeysTo(enterValue, FilterValue).click(filterDonebtn).click(filterSavebtn);
	    sfdcAcolyte.waitTillElementIsVisible(filterResponse);

        return PageFactory.initElements(driver, DataSourcePage.class);	
		
	}
		
		public DataSourcePage duplicateDataSourceFilter(String ColumnName, String ColumnOperator, String FilterValue) throws Exception {
			
			sfdcAcolyte.click(recentlyviewedlnk).
			waitTillElementIsVisible(allviewedlnk).click(allviewedlnk);
			sfdcAcolyte.waitTillElementIsVisible(filtericon).click(filtericon);
			sfdcAcolyte.waitTillElementIsVisible(addFilterlnk).click(addFilterlnk);
			sfdcAcolyte.waitTillElementIsVisible(selectField).click(selectField).sendKeysTo(selectField, ColumnName).sendBoardKeys(Keys.ENTER);
			sfdcAcolyte.waitTillElementIsVisible(selectOperator).click(selectOperator).sendKeysTo(selectOperator, ColumnOperator).sendBoardKeys(Keys.ENTER).
	        sendKeysTo(enterValue, FilterValue).click(filterDonebtn).click(filterSavebtn);
		    sfdcAcolyte.waitTillElementIsVisible(filterResponse);

	        return PageFactory.initElements(driver, DataSourcePage.class);			
		}

	public DataSourcePage deleteFilter() throws Exception {
	       
		sfdcAcolyte.waitTillElementIsVisible(removeAllfilter);
		sfdcAcolyte.click(removeAllfilter).click(filterSavebtn);
		return PageFactory.initElements(driver, DataSourcePage.class);
		
	}

	public DataSourcePage tempNewDataSource() throws Exception {
		 sfdcAcolyte.waitTillElementIsVisible(createNewDataSourcebtn).
                     waitTillElementIsClickable(createNewDataSourcebtn).jsClick(createNewDataSourcebtn);
         sfdcAcolyte.waitTillElementIsClickable(txtdataSource).click(txtdataSource).sendKeysTo(txtdataSource, "klklkl");
         sfdcAcolyte.click(selctTransMetaData).waitTillElementIsVisible(Ordr).click(selctTransMetaData).
                     selectComboByText(selctTransMetaData, "Order Line Item").
                     click(selectcalculationdateAttribute).waitTillElementIsVisible(calcdate).selectComboByText(selectcalculationdateAttribute, "Ready for Activation Date");

        return PageFactory.initElements(driver, DataSourcePage.class);	
	}

	public DataSourcePage verifyCancel() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(createNewDataSourcebtn).
        waitTillElementIsClickable(createNewDataSourcebtn).jsClick(createNewDataSourcebtn).click(btnCancel);
		sfdcAcolyte.waitTillElementIsVisible(successresponse);
		return PageFactory.initElements(driver, DataSourcePage.class);
	}

	public DataSourcePage verifyValidationDelimiter(String DataSourceName, String TransMetaData, String CalculationDate, String Product,
            String ProgramAccount, String FileSuffix, String FileExtenstion1) throws Exception {
		  sfdcAcolyte.waitTillElementIsVisible(createNewDataSourcebtn).
                      waitTillElementIsClickable(createNewDataSourcebtn).jsClick(createNewDataSourcebtn);
          sfdcAcolyte.waitTillElementIsVisible(txtdataSource).waitTillElementIsClickable(txtdataSource).click(txtdataSource).sendKeysTo(txtdataSource, DataSourceName);
          sfdcAcolyte.click(selctTransMetaData).waitTillElementIsVisible(Ordr).click(selctTransMetaData).
                      selectComboByText(selctTransMetaData, TransMetaData).
                      click(selectcalculationdateAttribute).waitTillElementIsVisible(calcdate).selectComboByText(selectcalculationdateAttribute, CalculationDate);
          sfdcAcolyte.click(selectproduct).selectComboByText(selectproduct, Product).click(selectproduct).
                       selectComboByText(selectproductAccount, ProgramAccount);
          sfdcAcolyte.waitTillElementIsClickable(txtfilesuffix).click(txtfilesuffix).sendKeysTo(txtfilesuffix, FileSuffix);
          sfdcAcolyte.click(multiFileExtension).selectComboByText(multiFileExtension, FileExtenstion1).
                      click(btnsubmitdataSource).click(btnCancel);
          getDelimiterResponse=delimiterResponse.getText();
          closeToastMessage();
          return PageFactory.initElements(driver, DataSourcePage.class);	
		
	}

	public void closeToastMessage() throws Exception {
		sfdcAcolyte.waitTillElementIsClickable(closeToastResponse).click(closeToastResponse);
		
	}

	public void deleteFilterRecord() throws Exception {
		
		Thread.sleep(2000);
		sfdcAcolyte.waitTillElementIsVisible(showMore).
	    jsClick(showMore);
		Thread.sleep(2000);
	     sfdcAcolyte.jsClick(showDeleteAction).
	     jsClick(confirmDeleteAction);
		
	}
	public void cancelDataSource() throws Exception {
		sfdcAcolyte.click(btnCancel);
		
	}
	
	public void dataSourceRefresh() throws Exception {
		sfdcAcolyte.refreshPage();
		
	}

	public void removeFilter() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(filtericon).click(filtericon);
        sfdcAcolyte.waitTillElementIsVisible(removeAllfilter).click(removeAllfilter).click(filterSavebtn);
		
	}

	public void deleteDataSource() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(deleteDataSourcebtn).click(deleteDataSourcebtn);
		
	}
	

}
	


	
