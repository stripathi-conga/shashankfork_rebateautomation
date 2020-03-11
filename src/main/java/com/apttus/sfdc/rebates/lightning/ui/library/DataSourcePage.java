package com.apttus.sfdc.rebates.lightning.ui.library;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.apttus.sfdc.rebates.lightning.common.GenericPage;
import com.apttus.sfdc.rebates.lightning.generic.utils.RebatesConstants;

public class DataSourcePage extends GenericPage {

	
	@FindBy(xpath = "//*[text()='Save']")
	public WebElement btnSave;

	@FindBy(css = ".forceActionsText")
	public WebElement txtToastMessage;

	@FindBy(xpath = "//div/button[@title='Close']")
	public WebElement btnCloseToastMessage;

	@FindBy(xpath = "//*[@data-field='DataSourceName']/div[1]")
	public WebElement txtDataSource;
	
	@FindBy(xpath = "//*[@data-field='fileSuffix']/div[1]")
	public WebElement txtFileSuffix;
	
	@FindBy(xpath = "//input[@name='Transaction Line Object']")
	public WebElement ddlselctTransMetaData;
	
	@FindBy(xpath = "//input[@name='Product Field']")
	public WebElement ddlselctProductField;
	
	@FindBy(xpath = "//input[@name='Incentive Account Field']")
	public WebElement ddlIncentiveAccount;
	
	@FindBy(xpath = "//input[@name='Calculation Date Field']")
	public WebElement ddlCalculationdate;
	
	@FindBy(xpath = "//*[text()='Order Line Item']")
	public WebElement ddlOrder;
	
	@FindBy(xpath = "//*[text()='Start Date']")
	public WebElement ddlCalculationDateValue;
	
	@FindBy(xpath = "//*[text()='Option']")
	public WebElement ddlProductValue;
	
	@FindBy(xpath = "//*[text()='Bill To']")
	public WebElement ddlIncentiveAccountValue;
	
	@FindBy(xpath = "//option[text()='txt']")
	public WebElement fileExtension;
		WebDriverWait wait;

	public DataSourcePage(WebDriver driver) {
		super(driver);
		wait = new WebDriverWait(driver, 120);
		PageFactory.initElements(driver, this);
	}

	public void clickSave() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(btnSave).waitTillElementIsClickable(btnSave).click(btnSave);
		sfdcAcolyte.waitTillElementIsVisible(txtToastMessage);

	}

	public void VerifyValidationMessageForTransactionLineObject() throws Exception {
		sfdcAcolyte.click(btnCloseToastMessage);
		sfdcAcolyte.waitTillElementIsVisible(txtDataSource).clickAndSendkeys(txtDataSource, RebatesConstants.dataSource).click(btnSave);
		sfdcAcolyte.waitTillElementIsVisible(txtToastMessage);
	}

	public void verifyValidationMessageForCalculationDate() throws Exception {
		sfdcAcolyte.click(btnCloseToastMessage);
     
		sfdcAcolyte.click(ddlselctTransMetaData).waitTillElementIsVisible(ddlOrder).waitTillElementIsClickable(ddlOrder)
				.jsScroll(ddlOrder).click(ddlOrder).click(btnSave);	sfdcAcolyte.waitTillElementIsVisible(txtToastMessage);

	}

	public void verifyValidationMessageForProduct() throws Exception {
		sfdcAcolyte.click(btnCloseToastMessage);

		sfdcAcolyte.click(ddlCalculationdate).waitTillElementIsClickable(ddlCalculationDateValue)
				.jsScroll(ddlCalculationDateValue).click(ddlCalculationDateValue).click(btnSave);
		sfdcAcolyte.waitTillElementIsVisible(txtToastMessage);
		
	}
	

	public void verifyValidationMessageForIncentiveAccount() throws Exception {
		sfdcAcolyte.click(btnCloseToastMessage);
		sfdcAcolyte.click(ddlselctProductField).waitTillElementIsClickable(ddlProductValue)
		.jsScroll(ddlProductValue).click(ddlProductValue).click(btnSave);
		sfdcAcolyte.waitTillElementIsVisible(txtToastMessage);
		
	}

	public void verifyValidationMessageForFileSuffixToIgnore() throws Exception {
		sfdcAcolyte.click(btnCloseToastMessage);
		sfdcAcolyte.click(ddlIncentiveAccount).waitTillElementIsClickable(ddlIncentiveAccountValue).jsScroll(ddlIncentiveAccountValue)
		.click(ddlIncentiveAccountValue).click(btnSave);
		sfdcAcolyte.waitTillElementIsVisible(txtToastMessage);
		
	}
	public void verifyValidationMessageForFileExtension() throws Exception {
		sfdcAcolyte.click(btnCloseToastMessage);
		sfdcAcolyte.clickAndSendkeys(txtFileSuffix, RebatesConstants.suffix).click(btnSave);
		sfdcAcolyte.waitTillElementIsVisible(txtToastMessage);
		
	}
	public void verifyValidationMessageForRecordDelimter() throws Exception {
		sfdcAcolyte.click(btnCloseToastMessage);
		sfdcAcolyte.click(fileExtension).click(btnSave);
		sfdcAcolyte.waitTillElementIsVisible(txtToastMessage);
		
	}

}