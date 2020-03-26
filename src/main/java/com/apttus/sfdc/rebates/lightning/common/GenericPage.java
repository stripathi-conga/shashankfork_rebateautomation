package com.apttus.sfdc.rebates.lightning.common;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.apttus.ui.fundamentals.Acolyte;

public class GenericPage {

	@FindBy(css = ".forceActionsText")
	public WebElement txtToastMessage;

	@FindBy(xpath = "//div/button[@title='Close']")
	public WebElement btnCloseToastMessage;

	public WebDriver driver;
	public Acolyte sfdcAcolyte;
	public int waitTime = 40;
	public String fieldLabel = ".labelCol";
	public String lnkTemplateId = "//*[@data-recordid='OPTION']";
	public String lnkFormulaTab = "//*[@data-row-key-value='OPTION']";
	public String lblQualificationFormulaPath;
	public String lblBenefitFormulaIdPath;

	public GenericPage(WebDriver driver) {
		this.driver = driver;
		sfdcAcolyte = new Acolyte(driver);
		sfdcAcolyte.setWaitTime(60);
		PageFactory.initElements(driver, this);
	}

	public GenericPage clickButton(WebElement button) throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(button).click(button);
		return PageFactory.initElements(driver, GenericPage.class);
	}

	public GenericPage clickButtonAndWait(WebElement button, WebElement waitElement) throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(button).click(button);
		sfdcAcolyte.waitTillElementIsVisible(waitElement);
		return PageFactory.initElements(driver, GenericPage.class);
	}

	public void moveToFormulaTab(String qualificationFormula, String benefitFormula, WebElement element)
			throws Exception {

		sfdcAcolyte.waitTillElementIsClickable(element).click(element);
		lblQualificationFormulaPath = lnkFormulaTab.replace("OPTION", qualificationFormula);
		lblBenefitFormulaIdPath = lnkFormulaTab.replace("OPTION", benefitFormula);
		sfdcAcolyte.waitTillElementIsVisible(By.xpath(lblQualificationFormulaPath));
		sfdcAcolyte.waitTillElementIsVisible(By.xpath(lblBenefitFormulaIdPath));
	}

	public void WaitAndEnterElement(WebElement element, String value) throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(element).waitTillElementIsClickable(element).clickAndSendkeys(element,
				value);
	}

	public GenericPage WaitClickAndSendKey(WebElement element, String value) throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(element).click(element).sendTextKeys(value).sendBoardKeys(Keys.ENTER);
		return PageFactory.initElements(driver, GenericPage.class);
	}

	public GenericPage doubleClick(WebElement element) throws Exception {
		Actions builder = new Actions(driver);
		sfdcAcolyte.waitTillElementIsVisible(element);
		builder.doubleClick(element).build().perform();
		return PageFactory.initElements(driver, GenericPage.class);
	}

	public GenericPage clickWhenElementIsVisibleAndClickable(WebElement element) throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(element).waitTillElementIsClickable(element).jsClick(element);
		return PageFactory.initElements(driver, GenericPage.class);
	}

	public GenericPage clickElement(WebElement element) throws Exception {
		sfdcAcolyte.jsClick(element);
		return PageFactory.initElements(driver, GenericPage.class);
	}

	public GenericPage clearAndSendKeys(WebElement element, String value) throws Exception {
		sfdcAcolyte.clear(element).waitTillElementIsVisible(element).click(element).sendTextKeys(value)
				.sendBoardKeys(Keys.ENTER);
		return PageFactory.initElements(driver, GenericPage.class);
	}

	public GenericPage moveToElementAndClick(WebElement element, WebElement ele) throws Exception {
		Actions action = new Actions(driver);
		sfdcAcolyte.waitTillElementIsVisible(element);
		action.moveToElement(element).moveToElement(ele).click().build().perform();
		return PageFactory.initElements(driver, GenericPage.class);
	}

	public void waitTillPageContentLoad() throws InterruptedException {		
		Thread.sleep(5000);	// Wait for 5 seconds
	}
}
