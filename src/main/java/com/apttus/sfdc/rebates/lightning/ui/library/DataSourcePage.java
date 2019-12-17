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

	@FindBy(css = "[title='New']")
	public WebElement btnCreateNewDataSource;

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

	@FindBy(xpath = "//label[contains(text(),'Data Source Name')]//following::div[1]/input")
	public WebElement txtDataSource;

	@FindBy(xpath = "//button[@class='slds-button slds-button_brand']")
	public WebElement btnSubmitDataSource;

	@FindBy(xpath = "//span[contains(text(),'Data Source created')]")
	public WebElement successResponse;

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

	@FindBy(css = "button[title='Close']")
	public WebElement btnCloseToastResponse;

	@FindBy(xpath = "//option[text()='Order Line Item']")
	public WebElement ddlOrder;

	@FindBy(xpath = "//option[text()='Ready for Activation Date']")
	public WebElement ddlCalculationDate;

	/* .............Expected Validations................... */
	public final String success = "Data Source created";

	GenericPage genericPage;
	WebDriverWait wait;

	public DataSourcePage(WebDriver driver) {
		super(driver);
		wait = new WebDriverWait(driver, 60);
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
		sfdcAcolyte.waitTillElementIsVisible(txtFileSuffix).click(txtFileSuffix).sendKeysTo(txtFileSuffix, FileSuffix);
		sfdcAcolyte.click(ddlSelectProduct).selectComboByText(ddlSelectProduct, Product).click(ddlSelectAccount)
				.selectComboByText(ddlSelectAccount, ProgramAccount);
		sfdcAcolyte.click(ddlMultiFileExtension).selectComboByText(ddlMultiFileExtension, FileExtenstion1)
				.selectComboByText(ddlMultiFileExtension, FileExtenstion2).click(ddlSelectDelimter)
				.selectComboByText(ddlSelectDelimter, Delimiter).click(btnSubmitDataSource);
		sfdcAcolyte.waitTillElementIsVisible(successResponse);
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

	public DataSourcePage deleteFilterRecord() throws Exception {

		wait.until(ExpectedConditions.visibilityOf(btnShowMore));
		sfdcAcolyte.waitTillElementIsVisible(btnShowMore).jsClick(btnShowMore);
		wait.until(ExpectedConditions.visibilityOf(btnShowDeleteAction));
		sfdcAcolyte.jsClick(btnShowDeleteAction).jsClick(btnConfirmDeleteAction);
		return PageFactory.initElements(driver, DataSourcePage.class);
	}

	public DataSourcePage dataSourceRefresh() throws Exception {
		sfdcAcolyte.refreshPage();
		return PageFactory.initElements(driver, DataSourcePage.class);
	}

	public DataSourcePage removeFilter() throws Exception {
		wait.until(ExpectedConditions.visibilityOf(filterIcon));
		sfdcAcolyte.click(filterIcon);
		sfdcAcolyte.waitTillElementIsVisible(lnkRemoveAllFilter).click(lnkRemoveAllFilter).click(btnFilterSave);
		return PageFactory.initElements(driver, DataSourcePage.class);
	}

}
