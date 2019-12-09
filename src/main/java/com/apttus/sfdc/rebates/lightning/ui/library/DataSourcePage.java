package com.apttus.sfdc.rebates.lightning.ui.library;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.apttus.sfdc.rebates.lightning.common.GenericPage;

public class DataSourcePage extends GenericPage {

	@FindBy(css = "a[title='New']")
	public WebElement btnNewDataSource;

	@FindBy(css = "input[class='slds-input'][type='text'][aria-describedby='help-message-109']")
	public WebElement btnDataSourceName;

	@FindBy(css = "[title='New']")
	public WebElement btnCreateNewDataSource;

	@FindBy(css = "a[title='Delete'][role='button']")
	public WebElement btnDeleteDataSource;

	@FindBy(xpath = "//select[@class='slds-select metaData']")
	public WebElement ddlSelectTransMetaData;

	@FindBy(xpath = "//select[@class='slds-select calcDateAttr']")
	public WebElement ddlSelectCalculationDateAttribute;

	@FindBy(xpath = "//select[@class='slds-select prodAttr']")
	public WebElement ddlSelectProduct;

	@FindBy(xpath = "//select[@class='slds-select proAcctAttr']")
	public WebElement ddlSelectAccount;

	@FindBy(xpath = "//select[@class='slds-select delimiter']")
	public WebElement ddlSelectDelimter;

	@FindBy(xpath = "//select[@class='slds-select fileExtension']")
	public WebElement ddlMultiFileExtension;

	@FindBy(xpath = "//label[contains(text(),'File Suffix To ignore')]//following::div[1]/input")
	public WebElement txtFileSuffix;

	@FindBy(xpath = "//label[contains(text(),'File Suffix To ignore')]//following::div[1]/input")
	public WebElement duplicateFileSuffix;

	@FindBy(xpath = "//label[contains(text(),'Data Source Name')]//following::div[1]/input")
	public WebElement txtDataSource;

	@FindBy(xpath = "//button[@class='slds-button slds-button_brand']")
	public WebElement btnSubmitDataSource;

	@FindBy(xpath = "//button[@class='slds-button slds-button_neutral']")
	public WebElement btnCancel;

	@FindBy(xpath = "//span[contains(text(),'Data Source created')]")
	public WebElement successResponse;

	@FindBy(xpath = "//span[contains(text(),'Duplicate Data Source Name Found')]")
	public WebElement duplicateResponse;

	/* .............ListPage................... */
	@FindBy(css = "span.triggerLinkText.selectedListView.uiOutputText")
	public WebElement lnkRecentlyViewed;

	@FindBy(xpath = "//span[text()='All']")
	public WebElement lnkAllViewed;

	@FindBy(xpath = "//select[3]/option[2]")
	public WebElement ddlOptionProd;

	@FindBy(css = "span[title*='DSrcAutomation']")
	public WebElement lnkNameColumn;

	@FindBy(xpath = "//*[@data-key='filterList']")
	public WebElement filterIcon;

	@FindBy(css = "[class*=' addFilter']")
	public WebElement lnkAddFilter;

	@FindBy(xpath = "//*[text()='Field']/../..//input[@type='text']")
	public WebElement txtSelectField;

	@FindBy(xpath = "//*[text()='Operator']/../..//input[@class='slds-input slds-combobox__input']")
	public WebElement txtSelectOperator;

	@FindBy(css = "[class='filterTextInput valueInput input uiInput uiInputText uiInput--default uiInput--input']")
	public WebElement txtEnterValue;

	@FindBy(css = "button[class='slds-button slds-button--neutral doneButton uiButton'] span")
	public WebElement btnFilterDone;

	@FindBy(css = "[class='slds-button slds-button_brand saveButton headerButton']")
	public WebElement btnFilterSave;

	@FindBy(css = "[class='slds-text-color_weak slds-text-body_small slds-truncate virtualAutocompleteOptionSubtext']")
	public WebElement lnkAllPinnedList;

	@FindBy(css = "[class='removeAll']")
	public WebElement lnkRemoveAllFilter;

	@FindBy(xpath = "//*[@id=\"brandBand_1\"]//..//span/div/a/lightning-icon/lightning-primitive-icon")
	public WebElement btnShowMore;

	@FindBy(xpath = "//a[@title='Delete']")
	public WebElement btnShowDeleteAction;

	@FindBy(xpath = "//button[@title='Delete']")
	public WebElement btnConfirmDeleteAction;

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
	public WebElement programAcceptResponse;

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
	public WebElement ddlCalculationDate;

	/* .............Expected Validations................... */
	public final String getProgram = "Please select Program Account .";
	public final String success = "Data Source created";
	public final String duplicate = "Duplicate Data Source Name Found";
	public final String responseDataSource = "Please enter Data Source Name";
	public final String responseMetadata = "Error creating record";
	public final String responseCalculationDate  = "Please select Calculation Date .";
	public final String responsePrgmAccount = "Please select Program Account .";
	public final String responseProduct = "Please select Product .";
	public final String responseSuffix = "Please enter File Suffix to Ignore.";
	public final String responseFileExtension = "Please select File Extension.";
	public final String responseDelimiter = "Please select Delimiter.";
	public final String responseFilter = "List view updated.";
	public String duplicateRecord;
	public String getDelimiterResponse;
	public String getFileExtensionResponse;
	public String getSuffixResponse;
	public String getdatasrcResponse;
	public String getMetadataResponse;
	public String getCalDateResponse;
	GenericPage genericPage;
	WebDriverWait wait;

	public DataSourcePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public DataSourcePage createDataSource(String DataSourceName, String TransMetaData, String CalculationDate,
			String Product, String ProgramAccount, String FileSuffix, String FileExtenstion1, String FileExtenstion2,
			String Delimiter) throws Exception {

		String dataSourceURL = sfdcAcolyte.getCurrentURL();
		sfdcAcolyte.navigateTo(dataSourceURL);
		sfdcAcolyte.waitTillElementIsVisible(btnCreateNewDataSource).waitTillElementIsClickable(btnCreateNewDataSource)
				.jsClick(btnCreateNewDataSource);
		sfdcAcolyte.waitTillElementIsVisible(txtDataSource).click(txtDataSource).sendKeysTo(txtDataSource,
				DataSourceName);
		sfdcAcolyte.click(ddlSelectTransMetaData).waitTillElementIsVisible(ddlOrder).click(ddlSelectTransMetaData)
				.selectComboByText(ddlSelectTransMetaData, TransMetaData).click(ddlSelectCalculationDateAttribute)
				.waitTillElementIsVisible(ddlCalculationDate)
				.selectComboByText(ddlSelectCalculationDateAttribute, CalculationDate);

		sfdcAcolyte.click(ddlSelectProduct).selectComboByText(ddlSelectProduct, Product).click(ddlSelectAccount)
				.selectComboByText(ddlSelectAccount, ProgramAccount);
		sfdcAcolyte.waitTillElementIsClickable(txtFileSuffix).click(txtFileSuffix).sendKeysTo(txtFileSuffix,
				FileSuffix);
		sfdcAcolyte.click(ddlMultiFileExtension).selectComboByText(ddlMultiFileExtension, FileExtenstion1)
				.selectComboByText(ddlMultiFileExtension, FileExtenstion2).click(ddlSelectDelimter)
				.selectComboByText(ddlSelectDelimter, Delimiter).click(btnSubmitDataSource);
		sfdcAcolyte.waitTillElementIsVisible(successResponse);
		return PageFactory.initElements(driver, DataSourcePage.class);
	}

	public DataSourcePage verifyDuplicate(String duplicateDataSource, String TransMetaData, String CalculationDate,
			String Product, String ProgramAccount, String FileSuffix, String FileExtenstion1, String FileExtenstion2,
			String Delimiter) throws Exception {

		sfdcAcolyte.waitTillElementIsVisible(btnCreateNewDataSource).waitTillElementIsClickable(btnCreateNewDataSource)
				.jsClick(btnCreateNewDataSource);
		sfdcAcolyte.waitTillElementIsVisible(txtDataSource).click(txtDataSource).sendKeysTo(txtDataSource,
				duplicateDataSource);
		sfdcAcolyte.click(ddlSelectTransMetaData).waitTillElementIsVisible(ddlOrder).click(ddlSelectTransMetaData)
				.selectComboByText(ddlSelectTransMetaData, TransMetaData).click(ddlSelectCalculationDateAttribute)
				.waitTillElementIsVisible(ddlCalculationDate)
				.selectComboByText(ddlSelectCalculationDateAttribute, CalculationDate);
		sfdcAcolyte.click(ddlSelectProduct).selectComboByText(ddlSelectProduct, Product).click(ddlSelectAccount)
				.selectComboByText(ddlSelectAccount, ProgramAccount);
		sfdcAcolyte.click(ddlMultiFileExtension).selectComboByText(ddlMultiFileExtension, FileExtenstion1)
				.selectComboByText(ddlMultiFileExtension, FileExtenstion2).waitTillElementIsVisible(ddlSelectDelimter)
				.click(ddlSelectDelimter).selectComboByText(ddlSelectDelimter, Delimiter)
				.waitTillElementIsVisible(duplicateFileSuffix).click(duplicateFileSuffix)
				.sendKeysTo(duplicateFileSuffix, FileSuffix).click(btnSubmitDataSource);
		sfdcAcolyte.waitTillElementIsVisible(duplicateResponse);
		closeToastMessage();
		duplicateRecord = duplicateResponse.getText();
		return PageFactory.initElements(driver, DataSourcePage.class);
	}

	public void duplicateDataSource(String DupDataSourceName, String TransMetaData, String CalculationDate,
			String Product, String ProgramAccount, String FileSuffix, String FileExtenstion1, String FileExtenstion2,
			String Delimiter) throws Exception {

		sfdcAcolyte.waitTillElementIsVisible(btnCreateNewDataSource).waitTillElementIsClickable(btnCreateNewDataSource)
				.jsClick(btnCreateNewDataSource);
		sfdcAcolyte.waitTillElementIsClickable(txtDataSource).click(txtDataSource).sendKeysTo(txtDataSource,
				DupDataSourceName);
		sfdcAcolyte.click(ddlSelectTransMetaData).waitTillElementIsVisible(ddlOrder).click(ddlSelectTransMetaData)
				.selectComboByText(ddlSelectTransMetaData, TransMetaData).click(ddlSelectCalculationDateAttribute)
				.waitTillElementIsVisible(ddlCalculationDate)
				.selectComboByText(ddlSelectCalculationDateAttribute, CalculationDate);
		sfdcAcolyte.click(ddlSelectProduct).selectComboByText(ddlSelectProduct, Product).click(ddlSelectAccount)
				.selectComboByText(ddlSelectAccount, ProgramAccount);
		sfdcAcolyte.waitTillElementIsClickable(txtFileSuffix).click(txtFileSuffix).sendKeysTo(txtFileSuffix,
				FileSuffix);
		sfdcAcolyte.click(ddlMultiFileExtension).selectComboByText(ddlMultiFileExtension, FileExtenstion1)
				.selectComboByText(ddlMultiFileExtension, FileExtenstion2).click(ddlSelectDelimter)
				.selectComboByText(ddlSelectDelimter, Delimiter).click(btnSubmitDataSource);
		sfdcAcolyte.waitTillElementIsVisible(successResponse);
		closeToastMessage();
	}

	public void verifyValidationDataSourceName() throws Exception {

		sfdcAcolyte.waitTillElementIsVisible(btnCreateNewDataSource).waitTillElementIsClickable(btnCreateNewDataSource)
				.jsClick(btnCreateNewDataSource).waitTillElementIsVisible(btnSubmitDataSource);
		sfdcAcolyte.click(ddlSelectTransMetaData).waitTillElementIsVisible(ddlOrder).click(ddlSelectTransMetaData)
				.waitTillElementIsClickable(btnSubmitDataSource).click(btnSubmitDataSource).click(btnCancel);
		getdatasrcResponse = dataSourceResponse.getText();
		closeToastMessage();
	}

	public void verifyValidationTransactionMetaData(String DataSourceName) throws Exception {

		sfdcAcolyte.waitTillElementIsVisible(btnCreateNewDataSource).waitTillElementIsClickable(btnCreateNewDataSource)
				.jsClick(btnCreateNewDataSource).waitTillElementIsVisible(btnSubmitDataSource);
		sfdcAcolyte.click(ddlSelectTransMetaData).waitTillElementIsVisible(ddlOrder)
				.sendKeysTo(txtDataSource, DataSourceName).clear(txtDataSource)
				.sendKeysTo(txtDataSource, DataSourceName).click(btnSubmitDataSource).click(btnCancel);
		getMetadataResponse = metadataResponse.getText();
		closeToastMessage();
	}

	public void verifyValidationCalculationDate(String DataSourceName, String TransMetaData) throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(btnCreateNewDataSource).waitTillElementIsClickable(btnCreateNewDataSource)
				.jsClick(btnCreateNewDataSource);
		sfdcAcolyte.waitTillElementIsVisible(btnSubmitDataSource);
		sfdcAcolyte.click(ddlSelectTransMetaData).waitTillElementIsVisible(ddlOrder).sendKeysTo(txtDataSource,
				DataSourceName);
		sfdcAcolyte.click(ddlSelectTransMetaData).selectComboByText(ddlSelectTransMetaData, TransMetaData)
				.click(btnSubmitDataSource).click(btnCancel);
		getCalDateResponse = calculationDateResponse.getText();
	}

	public void verifyValidationProgramAccount(String DataSourceName, String TransMetaData, String CalculationDate)
			throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(btnCreateNewDataSource).waitTillElementIsClickable(btnCreateNewDataSource)
				.jsClick(btnCreateNewDataSource);
		sfdcAcolyte.waitTillElementIsClickable(txtDataSource).click(txtDataSource).sendKeysTo(txtDataSource,
				DataSourceName);
		sfdcAcolyte.click(ddlSelectTransMetaData).waitTillElementIsVisible(ddlOrder).click(ddlSelectTransMetaData)
				.selectComboByText(ddlSelectTransMetaData, TransMetaData).click(ddlSelectCalculationDateAttribute)
				.waitTillElementIsVisible(ddlCalculationDate)
				.selectComboByText(ddlSelectCalculationDateAttribute, CalculationDate).click(btnSubmitDataSource);
		closeToastMessage();
	}

	public void verifyValidationProduct(String ProgramAccount) throws Exception {
		sfdcAcolyte.click(ddlSelectTransMetaData).waitTillElementIsVisible(ddlOrder);
		sfdcAcolyte.click(txtDataSource);
		sfdcAcolyte.click(ddlSelectAccount).selectComboByText(ddlSelectAccount, ProgramAccount)
				.click(btnSubmitDataSource);
		sfdcAcolyte.waitTillElementIsVisible(productResponse);
		closeToastMessage();
	}

	public DataSourcePage verifyValidationSuffix(String DataSourceName, String TransMetaData, String CalculationDate,
			String Product, String ProgramAccount) throws Exception {

		sfdcAcolyte.waitTillElementIsVisible(btnCreateNewDataSource).waitTillElementIsClickable(btnCreateNewDataSource)
				.jsClick(btnCreateNewDataSource);
		sfdcAcolyte.waitTillElementIsClickable(txtDataSource).click(txtDataSource).sendKeysTo(txtDataSource,
				DataSourceName);
		sfdcAcolyte.click(ddlSelectTransMetaData).waitTillElementIsVisible(ddlOrder).click(ddlSelectTransMetaData)
				.selectComboByText(ddlSelectTransMetaData, TransMetaData).click(ddlSelectCalculationDateAttribute)
				.waitTillElementIsVisible(ddlCalculationDate)
				.selectComboByText(ddlSelectCalculationDateAttribute, CalculationDate);
		sfdcAcolyte.click(ddlSelectProduct).selectComboByText(ddlSelectProduct, Product).click(ddlSelectAccount)
				.selectComboByText(ddlSelectAccount, ProgramAccount);
		sfdcAcolyte.clickAndSendkeys(txtDataSource, DataSourceName).clear(txtDataSource)
				.clickAndSendkeys(txtDataSource, DataSourceName).click(btnSubmitDataSource).click(btnCancel);
		getSuffixResponse = suffixResponse.getText();
		closeToastMessage();
		return PageFactory.initElements(driver, DataSourcePage.class);
	}

	public DataSourcePage verifyValidationFileExtension(String DataSourceName, String TransMetaData,
			String CalculationDate, String Product, String ProgramAccount, String FileSuffix, String Delimiter)
			throws Exception {

		sfdcAcolyte.waitTillElementIsVisible(btnCreateNewDataSource).waitTillElementIsClickable(btnCreateNewDataSource)
				.jsClick(btnCreateNewDataSource);
		sfdcAcolyte.waitTillElementIsClickable(txtDataSource).click(txtDataSource).sendKeysTo(txtDataSource,
				DataSourceName);
		sfdcAcolyte.click(ddlSelectTransMetaData).waitTillElementIsVisible(ddlOrder).click(ddlSelectTransMetaData)
				.selectComboByText(ddlSelectTransMetaData, TransMetaData).click(ddlSelectCalculationDateAttribute)
				.waitTillElementIsVisible(ddlCalculationDate)
				.selectComboByText(ddlSelectCalculationDateAttribute, CalculationDate);
		sfdcAcolyte.click(ddlSelectProduct).selectComboByText(ddlSelectProduct, Product).click(ddlSelectAccount)
				.selectComboByText(ddlSelectAccount, ProgramAccount);
		sfdcAcolyte.click(ddlSelectDelimter).selectComboByText(ddlSelectDelimter, Delimiter)
				.waitTillElementIsClickable(txtFileSuffix).click(txtFileSuffix).sendKeysTo(txtFileSuffix, FileSuffix)
				.click(btnSubmitDataSource).click(btnCancel);
		getFileExtensionResponse = fileExtResponse.getText();
		closeToastMessage();
		return PageFactory.initElements(driver, DataSourcePage.class);
	}

	public DataSourcePage filterDataSource(String ColumnName, String ColumnOperator, String FilterValue)
			throws Exception {

		sfdcAcolyte.waitTillElementIsVisible(lnkRecentlyViewed).click(lnkRecentlyViewed)
				.waitTillElementIsVisible(lnkAllViewed).click(lnkAllViewed);
		sfdcAcolyte.waitTillElementIsVisible(filterIcon).waitTillElementIsClickable(filterIcon).click(filterIcon);
		sfdcAcolyte.waitTillElementIsVisible(lnkAddFilter).click(lnkAddFilter);
		sfdcAcolyte.waitTillElementIsVisible(txtSelectField).click(txtSelectField)
				.sendKeysTo(txtSelectField, ColumnName).sendBoardKeys(Keys.ENTER);
		sfdcAcolyte.waitTillElementIsVisible(txtSelectOperator).click(txtSelectOperator)
				.sendKeysTo(txtSelectOperator, ColumnOperator).sendBoardKeys(Keys.ENTER)
				.sendKeysTo(txtEnterValue, FilterValue).click(btnFilterDone).click(btnFilterSave);
		sfdcAcolyte.waitTillElementIsVisible(filterResponse);
		return PageFactory.initElements(driver, DataSourcePage.class);
	}

	public DataSourcePage duplicateDataSourceFilter(String ColumnName, String ColumnOperator, String FilterValue)
			throws Exception {

		sfdcAcolyte.click(lnkRecentlyViewed).waitTillElementIsVisible(lnkAllViewed).click(lnkAllViewed);
		sfdcAcolyte.waitTillElementIsVisible(filterIcon).click(filterIcon);
		sfdcAcolyte.waitTillElementIsVisible(lnkAddFilter).click(lnkAddFilter);
		sfdcAcolyte.waitTillElementIsVisible(txtSelectField).click(txtSelectField)
				.sendKeysTo(txtSelectField, ColumnName).sendBoardKeys(Keys.ENTER);
		sfdcAcolyte.waitTillElementIsVisible(txtSelectOperator).click(txtSelectOperator)
				.sendKeysTo(txtSelectOperator, ColumnOperator).sendBoardKeys(Keys.ENTER)
				.sendKeysTo(txtEnterValue, FilterValue).click(btnFilterDone).click(btnFilterSave);
		sfdcAcolyte.waitTillElementIsVisible(filterResponse);
		return PageFactory.initElements(driver, DataSourcePage.class);
	}

	public DataSourcePage deleteFilter() throws Exception {

		sfdcAcolyte.waitTillElementIsVisible(lnkRemoveAllFilter);
		sfdcAcolyte.click(lnkRemoveAllFilter).click(btnFilterSave);
		return PageFactory.initElements(driver, DataSourcePage.class);
	}

	public DataSourcePage verifyCancel() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(btnCreateNewDataSource).waitTillElementIsClickable(btnCreateNewDataSource)
				.jsClick(btnCreateNewDataSource).click(btnCancel);
		sfdcAcolyte.waitTillElementIsVisible(successResponse);
		return PageFactory.initElements(driver, DataSourcePage.class);
	}

	public DataSourcePage verifyValidationDelimiter(String DataSourceName, String TransMetaData, String CalculationDate,
			String Product, String ProgramAccount, String FileSuffix, String FileExtenstion1) throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(btnCreateNewDataSource).waitTillElementIsClickable(btnCreateNewDataSource)
				.jsClick(btnCreateNewDataSource);
		sfdcAcolyte.waitTillElementIsVisible(txtDataSource).waitTillElementIsClickable(txtDataSource)
				.click(txtDataSource).sendKeysTo(txtDataSource, DataSourceName);
		sfdcAcolyte.click(ddlSelectTransMetaData).waitTillElementIsVisible(ddlOrder).click(ddlSelectTransMetaData)
				.selectComboByText(ddlSelectTransMetaData, TransMetaData).click(ddlSelectCalculationDateAttribute)
				.waitTillElementIsVisible(ddlCalculationDate)
				.selectComboByText(ddlSelectCalculationDateAttribute, CalculationDate);
		sfdcAcolyte.click(ddlSelectProduct).selectComboByText(ddlSelectProduct, Product).click(ddlSelectProduct)
				.selectComboByText(ddlSelectAccount, ProgramAccount);
		sfdcAcolyte.waitTillElementIsClickable(txtFileSuffix).click(txtFileSuffix).sendKeysTo(txtFileSuffix,
				FileSuffix);
		sfdcAcolyte.click(ddlMultiFileExtension).selectComboByText(ddlMultiFileExtension, FileExtenstion1)
				.click(btnSubmitDataSource).click(btnCancel);
		getDelimiterResponse = delimiterResponse.getText();
		closeToastMessage();
		return PageFactory.initElements(driver, DataSourcePage.class);
	}

	public void closeToastMessage() throws Exception {

		sfdcAcolyte.waitTillElementIsClickable(btncloseToastResponse).click(btncloseToastResponse);
	}

	public void deleteFilterRecord() throws Exception {

		wait.until(ExpectedConditions.visibilityOf(btnShowMore));
		sfdcAcolyte.waitTillElementIsVisible(btnShowMore).jsClick(btnShowMore);
		wait.until(ExpectedConditions.visibilityOf(btnShowDeleteAction));
		sfdcAcolyte.jsClick(btnShowDeleteAction).jsClick(btnConfirmDeleteAction);
	}

	public void cancelDataSource() throws Exception {
		sfdcAcolyte.click(btnCancel);
	}

	public void dataSourceRefresh() throws Exception {
		sfdcAcolyte.refreshPage();
	}

	public void removeFilter() throws Exception {
		wait.until(ExpectedConditions.visibilityOf(filterIcon));
		sfdcAcolyte.click(filterIcon);
		sfdcAcolyte.waitTillElementIsVisible(lnkRemoveAllFilter).click(lnkRemoveAllFilter).click(btnFilterSave);
	}

	public void deleteDataSource() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(btnDeleteDataSource).click(btnDeleteDataSource);
	}

}
