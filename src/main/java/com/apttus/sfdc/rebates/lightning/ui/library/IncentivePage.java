package com.apttus.sfdc.rebates.lightning.ui.library;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.apttus.sfdc.rebates.lightning.common.GenericPage;

public class IncentivePage extends GenericPage {

	@FindBy(xpath = "//*[text()='Activate']")
	public WebElement btnActivate;

	@FindBy(css = ".forceActionsText")
	public WebElement txtToastMessage;

	@FindBy(xpath = "//*[text()='Add']")
	public WebElement btnAddProduct;

	@FindBy(xpath = "//*[@placeholder='Enter product name or code to search']")
	public WebElement txtbxSearchProduct;

	@FindBy(xpath = "//*[text()='Select All']")
	public WebElement chkProduct;

	@FindBy(xpath = "//footer/button[text()='Add']")
	public WebElement btnAdd;

	@FindBy(xpath = "//button[text()='Add']")
	public WebElement btnAddproduct;

	@FindBy(xpath = "//*[text()='Save']")
	public WebElement btnSave;

	@FindBy(xpath = "//*[text()='Participants']")
	public WebElement btnParticipant;

	@FindBy(xpath = "//*[text()='New']")
	public WebElement btnNew;

	@FindBy(xpath = "//*[@title='Account Name']")
	public WebElement colAccountName;

	@FindBy(xpath = "//*[@title='Account Number']")
	public WebElement colAccountNumber;

	@FindBy(xpath = "//*[@title='Account Type']")
	public WebElement colAccountType;

	@FindBy(xpath = "//*[@title='Effective Start Date']")
	public WebElement colEffectiveStartDate;

	@FindBy(xpath = "//*[@title='Effective End Date']")
	public WebElement colEffectiveEndDate;

	@FindBy(xpath = "//lightning-formatted-url")
	public List<WebElement> lnkAccountName;

	@FindBy(xpath = "//lightning-formatted-text")
	public List<WebElement> lnkAccount;

	@FindBy(xpath = "//*[@class='slds-button slds-button_icon slds-cell-edit__button slds-m-left_x-small']")
	public List<WebElement> effectiveDate;

	@FindBy(xpath = "//input[@type='number']")
	public WebElement txtEditNoOfTiers;
	// This locator will identify the Name ,Code and Type
	@FindBy(xpath = "//lightning-formatted-text")
	public List<WebElement> colValueNameCodeType;

	@FindBy(xpath = "//*[text()='B1']")
	public WebElement colValueAliasName;

	// This Locator will identify Start and EndDate
	@FindBy(xpath = "//span/div/lightning-formatted-date-time")
	public List<WebElement> colValueDate;

	@FindBy(xpath = "//c-core-data-table-formula")
	public List<WebElement> colValueFormula;

	@FindBy(xpath = "//*[@class='slds-grid slds-grid_align-spread cell-height slds-border_bottom']/button")
	public WebElement ddlQualificationFormulaEdit;

	@FindBy(xpath = "//*[@class='slds-grid slds-grid_align-spread cell-height']/button")
	public WebElement ddlBenefitFormulaEdit;

	@FindBy(xpath = "//*[@placeholder='Select Qualification Formula']")
	public WebElement ddlQualificationFormula;

	@FindBy(xpath = "//*[@placeholder='Select Benefit Formula']")
	public WebElement ddlBenefitFormula;

	@FindBy(xpath = "//*[@class='slds-media slds-listbox__option slds-media_center slds-media_small slds-listbox__option_plain']")
	public WebElement ddlFormulaValue;

	@FindBy(xpath = "//*[text()='Product']")
	public WebElement ddldefaultProduct;

	@FindBy(xpath = "//*[@placeholder='Select an Option']")
	public WebElement ddlSelectOption;

	@FindBy(xpath = "//div[@class='slds-grid slds-grid_align-spread cell-height slds-border_bottom']")
	public WebElement gridFormula;

	@FindBy(xpath = "//*[@class='col slds-grid slds-grid_align-spread cell-height slds-border_bottom']/button/lightning-icon")
	public List<WebElement> pencilEditTierT1T2T3;
	@FindBy(xpath = "//*[@class='col slds-grid slds-grid_align-spread cell-height']/div/button")
	public List<WebElement> pencilEditBenefitT1T2T3;

	@FindBy(xpath = "//td[@data-label='T1']")
	public WebElement gridT1;
	@FindBy(xpath = "//td[@data-label='T2']")
	public WebElement gridT2;
	@FindBy(xpath = "//td[@data-label='T3']")
	public WebElement gridT3;

	@FindBy(xpath = "//*[@class='slds-form-element__control slds-grow']")
	public WebElement txtT1;
	@FindBy(xpath = "//*[text()='Rebate']")
	public WebElement txtRebate;
	@FindBy(xpath = "//*[@class='slds-button slds-button_icon slds-button_icon-error']")
	public List<WebElement> gridErrorMessage;
	@FindBy(xpath = "//*[text()='Tier value should be incremental']")
	public WebElement txtErrorMessage;
	
	@FindBy(xpath = "//lightning-base-combobox-item/span[2]/span")
	public List<WebElement> comboboxvalue;

	@FindBy(xpath = "//button[contains(text(),'Selected')]")
	public WebElement btnSelected;
	@FindBy(xpath = "//lightning-primitive-cell-checkbox")
	public List<WebElement> checkbox;
	
	//div 10 is show Action button for Benefit Only. 
	@FindBy(xpath = "//tr[1]/td[10]//button")
	public WebElement btnShowAction;
	
	@FindBy(xpath = "//*[text()='Delete']")
	public WebElement btnDelete;
	
	@FindBy(xpath = "//tr[1]/th[@data-label='Section Id']//*[text()]")
	public WebElement txtSectionID;
	
	@FindBy(xpath = "//*[@class='slds-button slds-button_brand slds-button_destructive']")
	public WebElement btnConfirmDelete;
	
	
	//*[@placeholder='Enter name to search']
	//label[text()='Result based on search "3853"']
	//span[text()='Select Item 1']
	//lightning-primitive-cell-button
	
	GenericPage genericPage;
	WebDriverWait wait;

	public IncentivePage(WebDriver driver) {
		super(driver);
		wait = new WebDriverWait(driver, 60);
		genericPage = new GenericPage(driver);
		PageFactory.initElements(driver, this);
	}

	public IncentivePage activateIncentive() throws Exception {

		sfdcAcolyte.waitTillElementIsVisible(btnActivate).waitTillElementIsClickable(btnActivate).jsClick(btnActivate)
				.waitTillElementIsVisible(txtToastMessage);
		return PageFactory.initElements(driver, IncentivePage.class);
	}

	public void addQualificationBenefit() throws Exception {
		sfdcAcolyte.waitTillElementIsClickable(btnAddProduct).click(btnAddProduct);
		sfdcAcolyte.waitTillElementIsClickable(txtbxSearchProduct).click(txtbxSearchProduct);
		sfdcAcolyte.waitTillElementIsVisible(chkProduct).waitTillElementIsVisible(chkProduct);
		sfdcAcolyte.jsClick(chkProduct).waitTillElementIsClickable(btnAdd).click(btnAdd);
	}

	public IncentivePage moveToParticipantTab() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(btnParticipant).waitTillElementIsClickable(btnParticipant)
				.jsClick(btnParticipant).waitTillElementIsVisible(btnNew);
		return PageFactory.initElements(driver, IncentivePage.class);
	}

	public void waitTillAllParticipantElementLoad() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(colAccountName);
		sfdcAcolyte.waitTillElementIsVisible(colAccountNumber);
		sfdcAcolyte.waitTillElementIsVisible(colAccountType);
		sfdcAcolyte.waitTillElementIsVisible(colEffectiveEndDate);
		sfdcAcolyte.waitTillElementIsVisible(colEffectiveStartDate);
	}

	public void waitTillAllQnBElementLoad() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(colValueAliasName);
		sfdcAcolyte.waitTillElementIsVisible(colValueDate.get(0));
		sfdcAcolyte.waitTillElementIsVisible(colValueNameCodeType.get(0));
		sfdcAcolyte.waitTillElementIsVisible(colValueFormula.get(0));
	}

	public void addProductWithMultipleTiers(String product, String tiercount) throws Exception {
		genericPage.clickWhenElementIsVisibleAndClickable(btnAddproduct)
				.WaitClickAndSendKey(txtbxSearchProduct, product).clearAndSendKeys(txtEditNoOfTiers, tiercount);
		genericPage.waitTillPageContentLoad(2000); // Wait for 2 seconds
		sfdcAcolyte.click(ddlSelectOption).click(ddldefaultProduct);
		genericPage.clickWhenElementIsVisibleAndClickable(chkProduct).clickWhenElementIsVisibleAndClickable(btnAdd);

	}

	public void addFormula(WebElement gridFormula, WebElement formulaEditButton, WebElement formulaDropdown,
			WebElement formulaValue) throws Exception {
		genericPage.moveToElementAndClick(gridFormula, formulaEditButton);
		sfdcAcolyte.click(formulaDropdown).click(formulaValue);
	}

	public void addTiers(WebElement gridTier, WebElement tierEditButton, WebElement tierDropdown, String tierValue)
			throws Exception {

		genericPage.moveToElementAndClick(gridTier, tierEditButton);
		sfdcAcolyte.clickAndSendkeys(tierDropdown, tierValue);
		genericPage.doubleClick(txtRebate);
	}

	public void save(WebElement save) throws Exception {
		genericPage.doubleClick(save);
	}

	public void getErrorMessage(WebElement errorGrid, WebElement validationMessage) throws Exception {

		sfdcAcolyte.jsScrollAndClick(errorGrid);
		sfdcAcolyte.waitTillElementIsVisible(validationMessage);
	}
}
