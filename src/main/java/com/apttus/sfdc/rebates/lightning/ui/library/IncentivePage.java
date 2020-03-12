package com.apttus.sfdc.rebates.lightning.ui.library;

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
}
