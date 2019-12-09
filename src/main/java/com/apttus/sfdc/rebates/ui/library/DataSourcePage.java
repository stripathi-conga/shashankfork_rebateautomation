package com.apttus.sfdc.rebates.ui.library;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.apttus.sfdc.rebates.common.GenericPage;
import com.apttus.sfdc.rebates.common.StartUpPage;

public class DataSourcePage extends StartUpPage {

	@FindBy(css = "a[title='New']")
	public WebElement btnNewDataSource;

	@FindBy(css = "input[class='slds-input'][type='text'][aria-describedby='help-message-109']")
	public WebElement btndataSourceName;

	@FindBy(css = "[title='New']")
	public WebElement btncreateNewDataSource;

	@FindBy(css = "a[title='Delete'][role='button']")
	public WebElement btndeleteDataSource;

	@FindBy(xpath = "//select[@class='slds-select metaData']")
	public WebElement ddlselctTransMetaData;

	@FindBy(xpath = "//select[@class='slds-select calcDateAttr']")
	public WebElement ddlselectcalculationdateAttribute;

	@FindBy(xpath = "//select[@class='slds-select prodAttr']")
	public WebElement ddlselectproduct;

	@FindBy(xpath = "//select[@class='slds-select proAcctAttr']")
	public WebElement ddlselectAccount;

	@FindBy(xpath = "//select[@class='slds-select delimiter']")
	public WebElement ddlselectdelimter;

	@FindBy(xpath = "//select[@class='slds-select fileExtension']")
	public WebElement ddlmultiFileExtension;

	@FindBy(xpath = "//label[contains(text(),'File Suffix To ignore')]//following::div[1]/input")
	public WebElement txtfilesuffix;

	@FindBy(xpath = "//label[contains(text(),'File Suffix To ignore')]//following::div[1]/input")
	public WebElement duplicatefilesuffix;

	@FindBy(xpath = "//label[contains(text(),'Data Source Name')]//following::div[1]/input")
	public WebElement txtdataSource;

	@FindBy(xpath = "//button[@class='slds-button slds-button_brand']")
	public WebElement btnsubmitdataSource;

	@FindBy(xpath = "//button[@class='slds-button slds-button_neutral']")
	public WebElement btnCancel;

	@FindBy(xpath = "//span[contains(text(),'Data Source created')]")
	public WebElement successresponse;

	@FindBy(xpath = "//span[contains(text(),'Duplicate Data Source Name Found')]")
	public WebElement duplicateresponse;

	/* .............ListPage................... */
	@FindBy(css = "span.triggerLinkText.selectedListView.uiOutputText")
	public WebElement lnkrecentlyviewed;

	@FindBy(xpath = "//span[text()='All']")
	public WebElement lnkAllviewed;

	@FindBy(xpath = "//select[3]/option[2]")
	public WebElement ddloptionProd;

	@FindBy(css = "span[title*='DSrcAutomation']")
	public WebElement namecolmn;

	@FindBy(xpath = "//*[@data-key='filterList']")
	public WebElement filtericon;

	@FindBy(css = "[class*=' addFilter']")
	public WebElement lnkaddFilter;

	@FindBy(xpath = "//*[text()='Field']/../..//input[@type='text']")
	public WebElement txtselectField;

	@FindBy(xpath = "//*[text()='Operator']/../..//input[@class='slds-input slds-combobox__input']")
	public WebElement txtselectOperator;

	@FindBy(css = "[class='filterTextInput valueInput input uiInput uiInputText uiInput--default uiInput--input']")
	public WebElement txtenterValue;

	@FindBy(css = "button[class='slds-button slds-button--neutral doneButton uiButton'] span")
	public WebElement btnfilterDone;

	@FindBy(css = "[class='slds-button slds-button_brand saveButton headerButton']")
	public WebElement btnfilterSave;

	@FindBy(css = "[class='slds-text-color_weak slds-text-body_small slds-truncate virtualAutocompleteOptionSubtext']")
	public WebElement alllnkPinnedlist;

	@FindBy(css = "[class='removeAll']")
	public WebElement lnkremoveAllfilter;

	@FindBy(xpath = "//*[@id=\"brandBand_1\"]//..//span/div/a/lightning-icon/lightning-primitive-icon")
	public WebElement btnshowMore;

	@FindBy(xpath = "//a[@title='Delete']")
	public WebElement btnshowDeleteAction;

	@FindBy(xpath = "//button[@title='Delete']")
	public WebElement btnconfirmDeleteAction;

	/* .............XpathValidations................... */
	@FindBy(css = "span[class*='toastMessage']")
	public WebElement tempDataSrcResponse;

	@FindBy(css = "button[title='Close']")
	public WebElement btncloseToastResponse;

	@FindBy(xpath = "//span[contains(text(),'Please enter Data Source Name')]")
	public WebElement dataSourceResponse;

	@FindBy(xpath = "//*[contains(text(),'Error creating record')]")
	public WebElement metadataResponse;

	@FindBy(xpath = "//*[contains(text(),'Please select Calculation Date')]")
	public WebElement calculationDateResponse;

	@FindBy(xpath = "//*[contains(text(),'Please select Program')]")
	public WebElement programAcctesponse;

	@FindBy(xpath = "//*[contains(text(),'Please select Product .')]")
	public WebElement productResponse;

	@FindBy(xpath = "//*[contains(text(),'Please enter File Suffix to Ignore')]")
	public WebElement suffixResponse;

	@FindBy(xpath = "//*[contains(text(),'Please select File Extension.')]")
	public WebElement fileExtResponse;

	@FindBy(xpath = "//*[contains(text(),'Please select Delimiter')]")
	public WebElement delimiterResponse;

	@FindBy(xpath = "//*[text()='List view updated.']")
	public WebElement filterResponse;

	@FindBy(xpath = "//*[text()='List view updated.']")
	public WebElement newFilterResponse;

	@FindBy(xpath = "//option[text()='Order Line Item']")
	public WebElement ddlOrder;

	@FindBy(xpath = "//option[text()='Ready for Activation Date']")
	public WebElement ddlcalculationdate;

	/* .............Expected Validations................... */
	public final String getProgram = "Please select Program Account .";
	public final String success = "Data Source created";
	public final String duplicate = "Duplicate Data Source Name Found";
	public final String responseDataSource = "Please enter Data Source Name";
	public final String responseMetaData = "Error creating record";
	public final String responsecalculationdate = "Please select Calculation Date .";
	public final String responsePrgmAccount = "Please select Program Account .";
	public final String responseProduct = "Please select Product .";
	public final String responseSuffix = "Please enter File Suffix to Ignore.";
	public final String responseFileExtension = "Please select File Extension.";
	public final String responseDelimiter = "Please select Delimiter.";
	public final String responseFilter = "List view updated.";
	public final String newResponseFilter = "List view updated.";
	public String duplicateRecord;
	public String getDelimiterResponse;
	public String getFileExtensionResponse;
	public String getSuffixResponse;
	public String getdatasrcResponse;
	public String getMetadataResponse;
	public String getCaldateResponse;
	GenericPage genericPage;
	WebDriverWait wait;

	public DataSourcePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		sfdcAcolyte.setWaitTime(60);
		genericPage = new GenericPage(driver);
		wait = new WebDriverWait(driver, 60);
		

	}

	public DataSourcePage createDataSource(String DataSourceName, String TransMetaData, String CalculationDate,
			String Product, String ProgramAccount, String FileSuffix, String FileExtenstion1, String FileExtenstion2,
			String Delimiter) throws Exception {

		String dataSourceURL = sfdcAcolyte.getCurrentURL();
		sfdcAcolyte.navigateTo(dataSourceURL);
		sfdcAcolyte.waitTillElementIsVisible(btncreateNewDataSource).waitTillElementIsClickable(btncreateNewDataSource)
				.jsClick(btncreateNewDataSource);
		sfdcAcolyte.waitTillElementIsVisible(txtdataSource).click(txtdataSource).sendKeysTo(txtdataSource,
				DataSourceName);
		sfdcAcolyte.click(ddlselctTransMetaData).waitTillElementIsVisible(ddlOrder).click(ddlselctTransMetaData)
				.selectComboByText(ddlselctTransMetaData, TransMetaData).click(ddlselectcalculationdateAttribute)
				.waitTillElementIsVisible(ddlcalculationdate)
				.selectComboByText(ddlselectcalculationdateAttribute, CalculationDate);

		sfdcAcolyte.click(ddlselectproduct).selectComboByText(ddlselectproduct, Product).click(ddlselectAccount)
				.selectComboByText(ddlselectAccount, ProgramAccount);
		sfdcAcolyte.waitTillElementIsClickable(txtfilesuffix).click(txtfilesuffix).sendKeysTo(txtfilesuffix,
				FileSuffix);
		sfdcAcolyte.click(ddlmultiFileExtension).selectComboByText(ddlmultiFileExtension, FileExtenstion1)
				.selectComboByText(ddlmultiFileExtension, FileExtenstion2).click(ddlselectdelimter)
				.selectComboByText(ddlselectdelimter, Delimiter).click(btnsubmitdataSource);
		sfdcAcolyte.waitTillElementIsVisible(successresponse);
		return PageFactory.initElements(driver, DataSourcePage.class);

	}

	public DataSourcePage verifyDuplicate(String duplicateDataSource, String TransMetaData, String CalculationDate,
			String Product, String ProgramAccount, String FileSuffix, String FileExtenstion1, String FileExtenstion2,
			String Delimiter) throws Exception {

		sfdcAcolyte.waitTillElementIsVisible(btncreateNewDataSource).waitTillElementIsClickable(btncreateNewDataSource)
				.jsClick(btncreateNewDataSource);
		sfdcAcolyte.waitTillElementIsVisible(txtdataSource).click(txtdataSource).sendKeysTo(txtdataSource,
				duplicateDataSource);
		sfdcAcolyte.click(ddlselctTransMetaData).waitTillElementIsVisible(ddlOrder).click(ddlselctTransMetaData)
				.selectComboByText(ddlselctTransMetaData, TransMetaData).click(ddlselectcalculationdateAttribute)
				.waitTillElementIsVisible(ddlcalculationdate)
				.selectComboByText(ddlselectcalculationdateAttribute, CalculationDate);
		sfdcAcolyte.click(ddlselectproduct).selectComboByText(ddlselectproduct, Product).click(ddlselectAccount)
				.selectComboByText(ddlselectAccount, ProgramAccount);
		sfdcAcolyte.click(ddlmultiFileExtension).selectComboByText(ddlmultiFileExtension, FileExtenstion1)
				.selectComboByText(ddlmultiFileExtension, FileExtenstion2).waitTillElementIsVisible(ddlselectdelimter)
				.click(ddlselectdelimter).selectComboByText(ddlselectdelimter, Delimiter)
				.waitTillElementIsVisible(duplicatefilesuffix).click(duplicatefilesuffix)
				.sendKeysTo(duplicatefilesuffix, FileSuffix).click(btnsubmitdataSource);
		sfdcAcolyte.waitTillElementIsVisible(duplicateresponse);
		closeToastMessage();
		duplicateRecord = duplicateresponse.getText();
		return PageFactory.initElements(driver, DataSourcePage.class);

	}

	public void duplicateDataSource(String DupDataSourceName, String TransMetaData, String CalculationDate,
			String Product, String ProgramAccount, String FileSuffix, String FileExtenstion1, String FileExtenstion2,
			String Delimiter) throws Exception {

		sfdcAcolyte.waitTillElementIsVisible(btncreateNewDataSource).waitTillElementIsClickable(btncreateNewDataSource)
				.jsClick(btncreateNewDataSource);
		sfdcAcolyte.waitTillElementIsClickable(txtdataSource).click(txtdataSource).sendKeysTo(txtdataSource,
				DupDataSourceName);
		sfdcAcolyte.click(ddlselctTransMetaData).waitTillElementIsVisible(ddlOrder).click(ddlselctTransMetaData)
				.selectComboByText(ddlselctTransMetaData, TransMetaData).click(ddlselectcalculationdateAttribute)
				.waitTillElementIsVisible(ddlcalculationdate)
				.selectComboByText(ddlselectcalculationdateAttribute, CalculationDate);
		sfdcAcolyte.click(ddlselectproduct).selectComboByText(ddlselectproduct, Product).click(ddlselectAccount)
				.selectComboByText(ddlselectAccount, ProgramAccount);
		sfdcAcolyte.waitTillElementIsClickable(txtfilesuffix).click(txtfilesuffix).sendKeysTo(txtfilesuffix,
				FileSuffix);
		sfdcAcolyte.click(ddlmultiFileExtension).selectComboByText(ddlmultiFileExtension, FileExtenstion1)
				.selectComboByText(ddlmultiFileExtension, FileExtenstion2).click(ddlselectdelimter)
				.selectComboByText(ddlselectdelimter, Delimiter).click(btnsubmitdataSource);
		sfdcAcolyte.waitTillElementIsVisible(successresponse);
		closeToastMessage();

	}

	public void verifyValidationDataSourceName() throws Exception {

		sfdcAcolyte.waitTillElementIsVisible(btncreateNewDataSource).waitTillElementIsClickable(btncreateNewDataSource)
				.jsClick(btncreateNewDataSource).waitTillElementIsVisible(btnsubmitdataSource);
		sfdcAcolyte.click(ddlselctTransMetaData).waitTillElementIsVisible(ddlOrder).click(ddlselctTransMetaData)
				.waitTillElementIsClickable(btnsubmitdataSource).click(btnsubmitdataSource).click(btnCancel);
		getdatasrcResponse = dataSourceResponse.getText();
		closeToastMessage();
	}

	public void verifyValidationTransactionMetaData(String DataSourceName) throws Exception {

		sfdcAcolyte.waitTillElementIsVisible(btncreateNewDataSource).waitTillElementIsClickable(btncreateNewDataSource)
				.jsClick(btncreateNewDataSource).waitTillElementIsVisible(btnsubmitdataSource);
		sfdcAcolyte.click(ddlselctTransMetaData).waitTillElementIsVisible(ddlOrder)
				.sendKeysTo(txtdataSource, DataSourceName).clear(txtdataSource)
				.sendKeysTo(txtdataSource, DataSourceName).click(btnsubmitdataSource).click(btnCancel);
		getMetadataResponse = metadataResponse.getText();
		closeToastMessage();
	}

	public void verifyValidationCalculationDate(String DataSourceName, String TransMetaData) throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(btncreateNewDataSource).waitTillElementIsClickable(btncreateNewDataSource)
				.jsClick(btncreateNewDataSource);
		sfdcAcolyte.waitTillElementIsVisible(btnsubmitdataSource);
		sfdcAcolyte.click(ddlselctTransMetaData).waitTillElementIsVisible(ddlOrder).sendKeysTo(txtdataSource,
				DataSourceName);
		sfdcAcolyte.click(ddlselctTransMetaData).selectComboByText(ddlselctTransMetaData, TransMetaData)
				.click(btnsubmitdataSource).click(btnCancel);
		getCaldateResponse = calculationDateResponse.getText();

	}

	public void verifyValidationProgramAccount(String DataSourceName, String TransMetaData, String CalculationDate)
			throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(btncreateNewDataSource).waitTillElementIsClickable(btncreateNewDataSource)
				.jsClick(btncreateNewDataSource);
		sfdcAcolyte.waitTillElementIsClickable(txtdataSource).click(txtdataSource).sendKeysTo(txtdataSource,
				DataSourceName);
		sfdcAcolyte.click(ddlselctTransMetaData).waitTillElementIsVisible(ddlOrder).click(ddlselctTransMetaData)
				.selectComboByText(ddlselctTransMetaData, TransMetaData).click(ddlselectcalculationdateAttribute)
				.waitTillElementIsVisible(ddlcalculationdate)
				.selectComboByText(ddlselectcalculationdateAttribute, CalculationDate).click(btnsubmitdataSource);
		closeToastMessage();
	}

	public void verifyValidationProduct(String ProgramAccount) throws Exception {
		sfdcAcolyte.click(ddlselctTransMetaData).waitTillElementIsVisible(ddlOrder);
		sfdcAcolyte.click(txtdataSource);
		sfdcAcolyte.click(ddlselectAccount).selectComboByText(ddlselectAccount, ProgramAccount)
				.click(btnsubmitdataSource);
		sfdcAcolyte.waitTillElementIsVisible(productResponse);
		closeToastMessage();

	}

	public DataSourcePage verifyValidationSuffix(String DataSourceName, String TransMetaData, String CalculationDate,
			String Product, String ProgramAccount) throws Exception {

		sfdcAcolyte.waitTillElementIsVisible(btncreateNewDataSource).waitTillElementIsClickable(btncreateNewDataSource)
				.jsClick(btncreateNewDataSource);
		sfdcAcolyte.waitTillElementIsClickable(txtdataSource).click(txtdataSource).sendKeysTo(txtdataSource,
				DataSourceName);
		sfdcAcolyte.click(ddlselctTransMetaData).waitTillElementIsVisible(ddlOrder).click(ddlselctTransMetaData)
				.selectComboByText(ddlselctTransMetaData, TransMetaData).click(ddlselectcalculationdateAttribute)
				.waitTillElementIsVisible(ddlcalculationdate)
				.selectComboByText(ddlselectcalculationdateAttribute, CalculationDate);
		sfdcAcolyte.click(ddlselectproduct).selectComboByText(ddlselectproduct, Product).click(ddlselectAccount)
				.selectComboByText(ddlselectAccount, ProgramAccount);
		sfdcAcolyte.clickAndSendkeys(txtdataSource, DataSourceName).clear(txtdataSource)
				.clickAndSendkeys(txtdataSource, DataSourceName).click(btnsubmitdataSource).click(btnCancel);
		getSuffixResponse = suffixResponse.getText();
		closeToastMessage();
		return PageFactory.initElements(driver, DataSourcePage.class);
	}

	public DataSourcePage verifyValidationFileExtension(String DataSourceName, String TransMetaData,
			String CalculationDate, String Product, String ProgramAccount, String FileSuffix, String Delimiter)
			throws Exception {

		sfdcAcolyte.waitTillElementIsVisible(btncreateNewDataSource).waitTillElementIsClickable(btncreateNewDataSource)
				.jsClick(btncreateNewDataSource);
		sfdcAcolyte.waitTillElementIsClickable(txtdataSource).click(txtdataSource).sendKeysTo(txtdataSource,
				DataSourceName);
		sfdcAcolyte.click(ddlselctTransMetaData).waitTillElementIsVisible(ddlOrder).click(ddlselctTransMetaData)
				.selectComboByText(ddlselctTransMetaData, TransMetaData).click(ddlselectcalculationdateAttribute)
				.waitTillElementIsVisible(ddlcalculationdate)
				.selectComboByText(ddlselectcalculationdateAttribute, CalculationDate);
		sfdcAcolyte.click(ddlselectproduct).selectComboByText(ddlselectproduct, Product).click(ddlselectAccount)
				.selectComboByText(ddlselectAccount, ProgramAccount);
		sfdcAcolyte.click(ddlselectdelimter).selectComboByText(ddlselectdelimter, Delimiter)
				.waitTillElementIsClickable(txtfilesuffix).click(txtfilesuffix).sendKeysTo(txtfilesuffix, FileSuffix)
				.click(btnsubmitdataSource).click(btnCancel);
		getFileExtensionResponse = fileExtResponse.getText();
		closeToastMessage();
		return PageFactory.initElements(driver, DataSourcePage.class);

	}

	public DataSourcePage filterDataSource(String ColumnName, String ColumnOperator, String FilterValue)
			throws Exception {

		sfdcAcolyte.waitTillElementIsVisible(lnkrecentlyviewed).click(lnkrecentlyviewed)
				.waitTillElementIsVisible(lnkAllviewed).click(lnkAllviewed);
		sfdcAcolyte.waitTillElementIsVisible(filtericon).waitTillElementIsClickable(filtericon).click(filtericon);
		sfdcAcolyte.waitTillElementIsVisible(lnkaddFilter).click(lnkaddFilter);
		sfdcAcolyte.waitTillElementIsVisible(txtselectField).click(txtselectField)
				.sendKeysTo(txtselectField, ColumnName).sendBoardKeys(Keys.ENTER);
		sfdcAcolyte.waitTillElementIsVisible(txtselectOperator).click(txtselectOperator)
				.sendKeysTo(txtselectOperator, ColumnOperator).sendBoardKeys(Keys.ENTER)
				.sendKeysTo(txtenterValue, FilterValue).click(btnfilterDone).click(btnfilterSave);
		sfdcAcolyte.waitTillElementIsVisible(filterResponse);

		return PageFactory.initElements(driver, DataSourcePage.class);

	}

	public DataSourcePage duplicateDataSourceFilter(String ColumnName, String ColumnOperator, String FilterValue)
			throws Exception {

		sfdcAcolyte.click(lnkrecentlyviewed).waitTillElementIsVisible(lnkAllviewed).click(lnkAllviewed);
		sfdcAcolyte.waitTillElementIsVisible(filtericon).click(filtericon);
		sfdcAcolyte.waitTillElementIsVisible(lnkaddFilter).click(lnkaddFilter);
		sfdcAcolyte.waitTillElementIsVisible(txtselectField).click(txtselectField)
				.sendKeysTo(txtselectField, ColumnName).sendBoardKeys(Keys.ENTER);
		sfdcAcolyte.waitTillElementIsVisible(txtselectOperator).click(txtselectOperator)
				.sendKeysTo(txtselectOperator, ColumnOperator).sendBoardKeys(Keys.ENTER)
				.sendKeysTo(txtenterValue, FilterValue).click(btnfilterDone).click(btnfilterSave);
		sfdcAcolyte.waitTillElementIsVisible(filterResponse);

		return PageFactory.initElements(driver, DataSourcePage.class);
	}

	public DataSourcePage deleteFilter() throws Exception {

		sfdcAcolyte.waitTillElementIsVisible(lnkremoveAllfilter);
		sfdcAcolyte.click(lnkremoveAllfilter).click(btnfilterSave);
		return PageFactory.initElements(driver, DataSourcePage.class);
	}

	public DataSourcePage verifyCancel() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(btncreateNewDataSource).waitTillElementIsClickable(btncreateNewDataSource)
				.jsClick(btncreateNewDataSource).click(btnCancel);
		sfdcAcolyte.waitTillElementIsVisible(successresponse);
		return PageFactory.initElements(driver, DataSourcePage.class);
	}

	public DataSourcePage verifyValidationDelimiter(String DataSourceName, String TransMetaData, String CalculationDate,
			String Product, String ProgramAccount, String FileSuffix, String FileExtenstion1) throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(btncreateNewDataSource).waitTillElementIsClickable(btncreateNewDataSource)
				.jsClick(btncreateNewDataSource);
		sfdcAcolyte.waitTillElementIsVisible(txtdataSource).waitTillElementIsClickable(txtdataSource)
				.click(txtdataSource).sendKeysTo(txtdataSource, DataSourceName);
		sfdcAcolyte.click(ddlselctTransMetaData).waitTillElementIsVisible(ddlOrder).click(ddlselctTransMetaData)
				.selectComboByText(ddlselctTransMetaData, TransMetaData).click(ddlselectcalculationdateAttribute)
				.waitTillElementIsVisible(ddlcalculationdate)
				.selectComboByText(ddlselectcalculationdateAttribute, CalculationDate);
		sfdcAcolyte.click(ddlselectproduct).selectComboByText(ddlselectproduct, Product).click(ddlselectproduct)
				.selectComboByText(ddlselectAccount, ProgramAccount);
		sfdcAcolyte.waitTillElementIsClickable(txtfilesuffix).click(txtfilesuffix).sendKeysTo(txtfilesuffix,
				FileSuffix);
		sfdcAcolyte.click(ddlmultiFileExtension).selectComboByText(ddlmultiFileExtension, FileExtenstion1)
				.click(btnsubmitdataSource).click(btnCancel);
		getDelimiterResponse = delimiterResponse.getText();
		closeToastMessage();
		return PageFactory.initElements(driver, DataSourcePage.class);

	}

	public void closeToastMessage() throws Exception {
		sfdcAcolyte.waitTillElementIsClickable(btncloseToastResponse).click(btncloseToastResponse);

	}

	public void deleteFilterRecord() throws Exception {

		wait.until(ExpectedConditions.visibilityOf(btnshowMore));
		sfdcAcolyte.waitTillElementIsVisible(btnshowMore).jsClick(btnshowMore);
		wait.until(ExpectedConditions.visibilityOf(btnshowDeleteAction));
		sfdcAcolyte.jsClick(btnshowDeleteAction).jsClick(btnconfirmDeleteAction);

	}

	public void cancelDataSource() throws Exception {
		sfdcAcolyte.click(btnCancel);

	}

	public void dataSourceRefresh() throws Exception {
		sfdcAcolyte.refreshPage();

	}

	public void removeFilter() throws Exception {
		wait.until(ExpectedConditions.visibilityOf(filtericon));
		sfdcAcolyte.click(filtericon);
		sfdcAcolyte.waitTillElementIsVisible(lnkremoveAllfilter).click(lnkremoveAllfilter).click(btnfilterSave);

	}

	public void deleteDataSource() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(btndeleteDataSource).click(btndeleteDataSource);

	}

}
