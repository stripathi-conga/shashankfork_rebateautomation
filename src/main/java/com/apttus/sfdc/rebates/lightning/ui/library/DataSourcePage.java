package com.apttus.sfdc.rebates.lightning.ui.library;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.apttus.sfdc.rebates.lightning.common.GenericPage;
import com.apttus.sfdc.rebates.lightning.generic.utils.SFDCHelper;

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
	public WebElement ddlSelectTransMetaData;

	@FindBy(xpath = "//input[@name='Product Field']")
	public WebElement ddlSelectProductField;

	@FindBy(xpath = "//input[@name='Incentive Account Field']")
	public WebElement ddlIncentiveAccount;

	@FindBy(xpath = "//input[@name='Calculation Date Field']")
	public WebElement ddlCalculationDate;

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
	
	@FindBy(xpath = "//div/span[text()='Transaction Line Object']")
	public WebElement lblTransactionLineObject;
	
	@FindBy(xpath = "//div/span[text()='Calculation Date Field']")
	public WebElement lblCalculationDate;
	
	@FindBy(xpath = "//div/span[text()='Product Field']")
	public WebElement lblProductField;
	
	@FindBy(xpath = "//div/span[text()='Incentive Account Field']")
	public WebElement lblIncentiveAccountField;
	
	@FindBy(xpath = "//div/span[text()='Record Delimiter']")
	public WebElement lblRecordDelimiter;
	
	@FindBy(xpath = "//div/span[text()='File Extension']")
	public WebElement lblFileExtension;
	
	@FindBy(xpath = "//div/span[text()='File Suffix To ignore']")
	public WebElement lblFileSuffixToIgnore;
	  
	@FindBy(xpath = "//*[text()='Related']")
	public WebElement lblRelatedTab;
	WebDriverWait wait;
	String cmbTxt = "//span[@class='uiOutputText'][text()='OPTION']";
	String lnkTemplateId = "//*[@data-recordid='OPTION']";
	String lnkFormulaTab = "//*[@data-row-key-value='OPTION']";
	public String transactionLineObjectPath;
	public String calculationDatePath;
	public String productFieldPath;
	public String incentiveAccountPath;
	public String recordDelimitertPath;
	public String fileExtensionPath;
	public String fileSuffixtoIgnoretPath;
	public String nameDataSource = "Rebates_Auto_DataSource_" + SFDCHelper.randomNumberGenerator();
	public DataSourcePage(WebDriver driver) {
		super(driver);
		wait = new WebDriverWait(driver, 120);
		PageFactory.initElements(driver, this);
	}

	public void clickSave() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(btnSave).waitTillElementIsClickable(btnSave).click(btnSave);
		sfdcAcolyte.waitTillElementIsVisible(txtToastMessage);
	}

	public void verifyValidationMessageForTransactionLineObject() throws Exception {

		nameDataSource = "Rebates_Auto_DataSource_" + SFDCHelper.randomNumberGenerator();
		sfdcAcolyte.click(btnCloseToastMessage);
		sfdcAcolyte.waitTillElementIsVisible(txtDataSource).clickAndSendkeys(txtDataSource, nameDataSource)
				.click(btnSave);
		sfdcAcolyte.waitTillElementIsVisible(txtToastMessage);
	}

	public void verifyValidationMessageForCalculationDate() throws Exception {

		sfdcAcolyte.click(btnCloseToastMessage);
		nameDataSource = "Rebates_Auto_DataSource_" + SFDCHelper.randomNumberGenerator();
		sfdcAcolyte.waitTillElementIsVisible(txtDataSource).clickAndSendkeys(txtDataSource, nameDataSource);
		sfdcAcolyte.click(ddlSelectTransMetaData).click(ddlSelectTransMetaData).click(ddlSelectTransMetaData)
				.waitTillElementIsVisible(ddlOrder).waitTillElementIsClickable(ddlOrder).jsScrollAndClick(ddlOrder)
				.click(btnSave);
		sfdcAcolyte.waitTillElementIsVisible(txtToastMessage);

	}

	public void verifyValidationMessageForProduct() throws Exception {
		sfdcAcolyte.click(btnCloseToastMessage);
		sfdcAcolyte.click(ddlCalculationDate).waitTillElementIsClickable(ddlCalculationDateValue)
				.jsScroll(ddlCalculationDateValue).click(ddlCalculationDateValue).click(btnSave);
		sfdcAcolyte.waitTillElementIsVisible(txtToastMessage);
	}

	public void verifyValidationMessageForIncentiveAccount() throws Exception {
		sfdcAcolyte.click(btnCloseToastMessage);
		sfdcAcolyte.click(ddlSelectProductField).waitTillElementIsClickable(ddlProductValue).jsScroll(ddlProductValue)
				.click(ddlProductValue).click(btnSave);
		sfdcAcolyte.waitTillElementIsVisible(txtToastMessage);
	}

	public void verifyValidationMessageForFileSuffixToIgnore() throws Exception {
		sfdcAcolyte.click(btnCloseToastMessage);
		sfdcAcolyte.click(ddlIncentiveAccount).waitTillElementIsClickable(ddlIncentiveAccountValue)
				.jsScroll(ddlIncentiveAccountValue).click(ddlIncentiveAccountValue).click(btnSave);
		sfdcAcolyte.waitTillElementIsVisible(txtToastMessage);
	}

	public void verifyValidationMessageForFileExtension(Map<String, String> testData) throws Exception {
		sfdcAcolyte.click(btnCloseToastMessage);
		sfdcAcolyte.clickAndSendkeys(txtFileSuffix, testData.get("FileSuffixToignore__c")).click(btnSave)
				.click(btnSave);
		sfdcAcolyte.waitTillElementIsVisible(txtToastMessage);
	}

	public void verifyValidationMessageForRecordDelimter() throws Exception {
		sfdcAcolyte.click(btnCloseToastMessage);
		sfdcAcolyte.click(fileExtension).click(btnSave).click(btnSave);
		sfdcAcolyte.waitTillElementIsVisible(txtToastMessage);
	}

	public void VerifyTillAllElementLoaded(String element) throws Exception {
		transactionLineObjectPath = cmbTxt.replace("OPTION", element);
		calculationDatePath = cmbTxt.replace("OPTION", element);
		productFieldPath = cmbTxt.replace("OPTION", element);
		incentiveAccountPath = cmbTxt.replace("OPTION", element);
		recordDelimitertPath = cmbTxt.replace("OPTION", element);
		fileExtensionPath = cmbTxt.replace("OPTION", element);
		fileSuffixtoIgnoretPath = cmbTxt.replace("OPTION", element);
		sfdcAcolyte.waitTillElementIsVisible(By.xpath(transactionLineObjectPath));
		sfdcAcolyte.waitTillElementIsVisible(By.xpath(calculationDatePath));
		sfdcAcolyte.waitTillElementIsVisible(By.xpath(productFieldPath));
		sfdcAcolyte.waitTillElementIsVisible(By.xpath(fileSuffixtoIgnoretPath));
		sfdcAcolyte.waitTillElementIsVisible(By.xpath(fileExtensionPath));
		sfdcAcolyte.waitTillElementIsVisible(By.xpath(recordDelimitertPath));
		sfdcAcolyte.waitTillElementIsVisible(By.xpath(incentiveAccountPath));
	}
}