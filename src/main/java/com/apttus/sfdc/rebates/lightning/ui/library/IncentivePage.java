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
	@FindBy(xpath = "//*[text()='B1']")
	public WebElement colValueType;
	
	@FindBy(xpath = "//*[text()='B1']")
	public WebElement colValueName;
	
	@FindBy(xpath = "//*[text()='B1']")
	public WebElement colValueCode;
	
	@FindBy(xpath = "//*[text()='B1']")
	public WebElement colValueAliasName;
	
	@FindBy(xpath = "//span/div/ightning-formatted-date-time")
	public WebElement colValueDate;
	
	@FindBy(xpath = "//c-coredatatableformula_coredatatableformula-host")
	public WebElement colValueFormula;
	
	GenericPage genericPage;
	WebDriverWait wait;

	public IncentivePage(WebDriver driver) {
		super(driver);
		wait = new WebDriverWait(driver, 60);
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
}
