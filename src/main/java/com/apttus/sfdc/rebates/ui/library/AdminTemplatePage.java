package com.apttus.sfdc.rebates.ui.library;

import java.util.List;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.apttus.sfdc.rebates.common.GenericPage;
import com.apttus.sfdc.rebates.common.StartUpPage;

public class AdminTemplatePage extends StartUpPage {

	@FindBy(xpath = "//*[text()='New Template']")
	public WebElement lblNewTemplate;

	@FindBy(css = "div[title='New']")
	public WebElement btnNew;

	@FindBy(xpath = "//input[@placeholder='Enter a name']")
	public WebElement ddlTemplateName;

	@FindBy(xpath = "//textarea[@name='Description']")
	public WebElement txtTemplateDescription;

	@FindBy(xpath = "//input[@name='Q&B Layout']")
	public WebElement ddlQbSelect;

	@FindBy(xpath = "//*[text()='Tier']//..//input[@type='text']")
	public WebElement ddlTierSelect;

	@FindBy(xpath = "//span[@class='slds-checkbox_faux']")
	public List<WebElement> chkFormulas;

	@FindBy(xpath = "//*[text()='Data Source']//..//input[@placeholder='Select an Option']")
	public WebElement ddlDataSource;

	@FindBy(xpath = "//button[@class='slds-button slds-button_brand']")
	public WebElement btnSaveAdmin;

	@FindBy(xpath = "//span[@title='Benefit Product']")
	public WebElement ddlBenftProductValue;

	@FindBy(xpath = "//span[@title='Discrete']")
	public WebElement ddlTierDiscrete;

	@FindBy(xpath = "//*[text()='Data Source']//..//span[text()='DS-Test']")
	public WebElement ddlDataSourceValue;

	@FindBy(xpath = "//*[text()='Q & B Layout']//..//lightning-base-combobox-item[@data-value='Qualification & Benefit Product']")
	public WebElement txtQualificationBenftFormula;

	@FindBy(css = "span[class*='toastMessage']")
	public WebElement successResponse;

	@FindBy(css = "span[class='triggerLinkText selectedListView uiOutputText']")
	public WebElement lnkRecentlyViewed;

	@FindBy(xpath = "//span[text()='All']")
	public WebElement lnkAllViewed;

	@FindBy(css = "[title*='Automation']")
	public WebElement lnkEdit;

	@FindBy(css = "span[title='Name']")
	public WebElement titleNameColumn;

	@FindBy(xpath = "//*[@data-key='filterList']")
	public WebElement filterIcon;

	@FindBy(css = "[class*=' addFilter']")
	public WebElement lnkAddFilter;

	@FindBy(xpath = "//*[text()='Field']/../..//input[@type='text']")
	public WebElement ddlSelectField;

	@FindBy(xpath = "//*[text()='Operator']/../..//input[@class='slds-input slds-combobox__input']")
	public WebElement ddlSelectOperator;

	@FindBy(css = "[class='filterTextInput valueInput input uiInput uiInputText uiInput--default uiInput--input']")
	public WebElement txtEnterValue;

	@FindBy(css = "button[class='slds-button slds-button--neutral doneButton uiButton'] span")
	public WebElement btnFilterDone;

	@FindBy(css = "[class='slds-button slds-button_brand saveButton headerButton']")
	public WebElement btnFilterSave;

	@FindBy(css = "[class='slds-text-color_weak slds-text-body_small slds-truncate virtualAutocompleteOptionSubtext']")
	public WebElement lnkAlllnkPinnedList;

	@FindBy(css = "[class='triggerLinkTextAndIconWrapper slds-p-right--x-large']")
	public WebElement lnkAll;

	@FindBy(css = "span[class='countSortedByFilteredBy']")
	public WebElement lblPageSortedbyName;

	@FindBy(css = "[class='removeAll']")
	public WebElement lnkRemoveAllFilter;

	@FindBy(css = "a[title='Delete']")
	public WebElement btnDeleteShowAction;

	@FindBy(css = "a[title='Edit']")
	public WebElement editShowAction;

	@FindBy(xpath = "//a[@title='Delete']")
	public WebElement btnShowDeleteAction;

	@FindBy(xpath = "//button[@title='Delete']")
	public WebElement btnConfirmDeleteAction;

	@FindBy(css = "button[title='Delete'] span[class=' label bBody']")
	public WebElement btnDeleteAction;

	@FindBy(xpath = "//span[text()='1 item • Sorted by Name • Filtered by my templates - Name • ']")
	public WebElement lblSortedItem;

	@FindBy(xpath = "//*[@id=\"brandBand_1\"]//..//span/div/a/lightning-icon/lightning-primitive-icon")
	public WebElement btnShowMore;

	@FindBy(xpath = "//*[text()='Activate']")
	public WebElement btnActivate;

	@FindBy(css = "svg[class='slds-icon slds-icon_small']")
	public WebElement btnCancel;

	@FindBy(xpath = "//span[text()='close']")
	public WebElement btnClose;

	@FindBy(css = "button[title='Close']")
	public WebElement closeToastResponse;

	@FindBy(css = "span[class*='toastMessage']")
	public WebElement toastResponse;

	@FindBy(css = "heading--small")
	public WebElement titleToastError;

	@FindBy(xpath = "//h3[text()='Matching all of these filters']")
	public WebElement existingFilter;
	
	@FindBy(xpath = "//*[text()='Id']")
	public WebElement sortId;

	public String success = "Template saved successfully";
	public String newTemplate = "New Template";
	public String templateEmpty = "Program Template name cannot be empty";
    public String duplicateSaveFail = "Name already exists. Please provide a different name.";
	GenericPage genericPage;
	WebDriverWait wait;

	public AdminTemplatePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		sfdcAcolyte.setWaitTime(60);
		genericPage = new GenericPage(driver);
		wait = new WebDriverWait(driver, 60);
	}

	public AdminTemplatePage moveToNewTemplatePage(WebElement newbutton, WebElement Labelnewtemplate) throws Exception {

		sfdcAcolyte.waitTillElementIsVisible(newbutton).jsClick(newbutton);
		sfdcAcolyte.waitTillElementIsVisible(Labelnewtemplate);

		return PageFactory.initElements(driver, AdminTemplatePage.class);
	}

	public AdminTemplatePage qbLayoutDefinition(WebElement QBselct, WebElement QBValue, WebElement TierSelect,
			WebElement TierValue) throws Exception {
		sfdcAcolyte.waitTillElementIsClickable(QBselct).click(QBselct).waitTillElementIsVisible(QBValue).click(QBValue)
				.waitTillElementIsVisible(TierSelect).click(TierSelect).waitTillElementIsVisible(TierValue).click(TierValue);

		return PageFactory.initElements(driver, AdminTemplatePage.class);

	}

	public AdminTemplatePage dataSourceFormula(WebElement DataSourcedorpdwn, WebElement DatasourceValue,
			List<WebElement> BenifitCheckbox) throws Exception {

		sfdcAcolyte.waitTillElementIsVisible(DataSourcedorpdwn).waitTillElementIsClickable(DataSourcedorpdwn)
				.click(DataSourcedorpdwn).waitTillElementIsVisible(DatasourceValue).jsScrollAndClick(DatasourceValue);
		
		Thread.sleep(1000);
		for (int i = 0; i < BenifitCheckbox.size(); i++) {
			sfdcAcolyte.waitTillElementIsVisible(BenifitCheckbox.get(0));
			BenifitCheckbox.get(i).click();
			}

		
		return PageFactory.initElements(driver, AdminTemplatePage.class);

	}

	public AdminTemplatePage saveDataSourceFormula(WebElement DataSourcedorpdwn, WebElement DatasourceValue,
			List<WebElement> BenifitCheckbox) throws Exception {

		sfdcAcolyte.waitTillElementIsVisible(DataSourcedorpdwn).waitTillElementIsClickable(DataSourcedorpdwn)
				.click(DataSourcedorpdwn).waitTillElementIsVisible(DatasourceValue).jsScrollAndClick(DatasourceValue);

		sfdcAcolyte.click(btnSaveAdmin);
		sfdcAcolyte.waitTillElementIsVisible(closeToastResponse).waitTillElementIsClickable(closeToastResponse)
				.click(closeToastResponse);

		return PageFactory.initElements(driver, AdminTemplatePage.class);

	}

	public AdminTemplatePage saveAdminTemplate(WebElement btnSaveAdmin) throws Exception {
		wait.until(ExpectedConditions.visibilityOf(btnSaveAdmin));
		sfdcAcolyte.waitTillElementIsVisible(btnSaveAdmin).jsClick(btnSaveAdmin);
		sfdcAcolyte.waitTillElementIsVisible(successResponse);
		return PageFactory.initElements(driver, AdminTemplatePage.class);

	}

	public void waitAdminTemplate(WebElement btnSaveAdmin) throws Exception {

		sfdcAcolyte.waitTillElementIsVisible(successResponse);

	}

	public AdminTemplatePage filterAdminTemplate(String ColumnName, String ColumnOperator, String FilterValue)
			throws Exception {

		sfdcAcolyte.waitTillElementIsVisible(lnkRecentlyViewed).click(lnkRecentlyViewed)
				.waitTillElementIsVisible(lnkAllViewed).waitTillElementIsVisible(lnkAllViewed).click(lnkAllViewed)
				.waitTillElementIsVisible(filterIcon).click(filterIcon);

		sfdcAcolyte.waitTillElementIsVisible(lnkAddFilter).click(lnkAddFilter);
		sfdcAcolyte.waitTillElementIsVisible(ddlSelectField).click(ddlSelectField)
				.sendKeysTo(ddlSelectField, ColumnName).sendBoardKeys(Keys.ENTER);
		sfdcAcolyte.waitTillElementIsVisible(ddlSelectOperator).click(ddlSelectOperator)
				.sendKeysTo(ddlSelectOperator, ColumnOperator).sendBoardKeys(Keys.ENTER)
				.waitTillElementIsVisible(txtEnterValue).sendKeysTo(txtEnterValue, FilterValue)
				.waitTillElementIsVisible(btnFilterDone).click(btnFilterDone).waitTillElementIsVisible(btnFilterSave)
				.click(btnFilterSave);

		return PageFactory.initElements(driver, AdminTemplatePage.class);

	}

	public AdminTemplatePage deleteAdminTemplateFilter() throws Exception {	
		
		wait.until(ExpectedConditions.visibilityOf(btnShowMore));
		sfdcAcolyte.waitTillElementIsVisible(btnShowMore).jsClick(btnShowMore);
		wait.until(ExpectedConditions.visibilityOf(btnShowDeleteAction));
		sfdcAcolyte.jsClick(btnShowDeleteAction).jsClick(btnConfirmDeleteAction);

		return PageFactory.initElements(driver, AdminTemplatePage.class);

	}

	public AdminTemplatePage moveToEditPage() throws Exception {
		wait.until(ExpectedConditions.visibilityOf(btnShowMore));
		sfdcAcolyte.waitTillElementIsVisible(btnShowMore).waitTillElementIsClickable(btnShowMore).click(btnShowMore)
				.waitTillElementIsClickable(editShowAction).click(editShowAction);

		return PageFactory.initElements(driver, AdminTemplatePage.class);

	}

	public AdminTemplatePage closeToastMessage() throws Exception {

		sfdcAcolyte.waitTillElementIsClickable(closeToastResponse).click(closeToastResponse);

		return PageFactory.initElements(driver, AdminTemplatePage.class);

	}

	public AdminTemplatePage deleteFilter() throws Exception {

		sfdcAcolyte.waitTillElementIsVisible(lnkRemoveAllFilter).click(lnkRemoveAllFilter).click(btnFilterSave);

		return PageFactory.initElements(driver, AdminTemplatePage.class);
	}

	public AdminTemplatePage deleteOpenFilter() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(btnShowMore).click(btnShowMore)
				.waitTillElementIsVisible(btnDeleteShowAction).click(btnDeleteShowAction).click(btnDeleteAction);

		return PageFactory.initElements(driver, AdminTemplatePage.class);

	}

	public void cancelAdminTemplate() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(btnCancel).click(btnCancel);

	}

	public void saveCancelAdminTemplate(WebElement btnSaveAdmin2) throws Exception {
		sfdcAcolyte.click(btnSaveAdmin);
		Assert.assertEquals(duplicateSaveFail, toastResponse.getText(),"Name already exists. Please provide a different name.");
		sfdcAcolyte.waitTillElementIsVisible(btnCancel).click(btnCancel);

	}

	public void waitForPageToLoad() throws Exception {
		Thread.sleep(4000);

	}

	public AdminTemplatePage editAdminTemplate(WebElement templateName, String UpdatedTemplateName) throws Exception {

		sfdcAcolyte.navigateTo(sfdcAcolyte.getCurrentURL());
		sfdcAcolyte.waitTillElementIsVisible(templateName).jsClick(templateName).clear(templateName)
				.sendKeysTo(templateName, UpdatedTemplateName).clear(templateName)
				.sendKeysTo(templateName, UpdatedTemplateName);

		return PageFactory.initElements(driver, AdminTemplatePage.class);
	}

	public void removeAllFilter() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(lnkAllViewed).click(lnkAllViewed).waitTillElementIsVisible(filterIcon)
				.click(filterIcon);

		sfdcAcolyte.waitTillElementIsVisible(lnkRemoveAllFilter).click(lnkRemoveAllFilter).click(btnFilterSave);
		sfdcAcolyte.waitTillElementIsVisible(lnkAll).click(lnkAll).waitTillElementIsVisible(lnkAlllnkPinnedList)
				.click(lnkAlllnkPinnedList);

	}

	public void activateAdminTemplate() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(lnkEdit);
		genericPage.waitTillStaleElementToBeVisible(lnkEdit);
		wait.until(ExpectedConditions.visibilityOf(btnActivate));
		sfdcAcolyte.waitTillElementIsVisible(btnActivate).click(btnActivate);

	}

	public void removeFilterSave() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(lnkRecentlyViewed).click(lnkRecentlyViewed)
				.waitTillElementIsVisible(lnkAllViewed).waitTillElementIsClickable(lnkAllViewed).click(lnkAllViewed)
				.waitTillElementIsVisible(filterIcon).click(filterIcon);
		sfdcAcolyte.waitTillElementIsVisible(lnkRemoveAllFilter).click(lnkRemoveAllFilter).click(btnFilterSave);

	}

	public void refreshAdminTemplate() throws Exception {
		sfdcAcolyte.refreshPage();

	}

	public void deleteExistingFilter() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(lnkRecentlyViewed).click(lnkRecentlyViewed)
				.waitTillElementIsVisible(lnkAllViewed).waitTillElementIsVisible(lnkAllViewed).click(lnkAllViewed)
				.waitTillElementIsVisible(filterIcon).click(filterIcon);
		sfdcAcolyte.waitTillElementIsVisible(lnkRemoveAllFilter).click(lnkRemoveAllFilter).click(btnFilterSave);
	}

	public void moveToAllFilter() throws Exception {
		sfdcAcolyte.refreshPage();
		sfdcAcolyte.waitTillElementIsVisible(lnkRecentlyViewed).click(lnkRecentlyViewed)
				.waitTillElementIsVisible(lnkAllViewed).waitTillElementIsVisible(lnkAllViewed).click(lnkAllViewed)
				.waitTillElementIsVisible(filterIcon).click(filterIcon);
	}

	public void launchAdminTemplate() throws Exception {
		String currentURL = sfdcAcolyte.getCurrentURL();
		sfdcAcolyte.navigateTo(currentURL);

	}

	public void sort() throws Exception {
		sfdcAcolyte.jsClick(sortId);

	}

	public AdminTemplatePage fillTemplateDetails(WebElement templateNametxt, String TemplateNameValue,
			WebElement txtTemplateDescriptiontxt, String TemplateDescValue) throws Exception {

		sfdcAcolyte.waitTillElementIsVisible(templateNametxt).sendKeysTo(templateNametxt, TemplateNameValue)
				.waitTillElementIsClickable(txtTemplateDescription)
				.clickAndSendkeys(txtTemplateDescription, TemplateDescValue);

		return PageFactory.initElements(driver, AdminTemplatePage.class);

	}
}
