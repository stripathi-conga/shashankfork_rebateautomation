package com.apttus.sfdc.rebates.lightning.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
	String lnkTemplateId = "//*[@data-recordid='OPTION']";
	String lnkFormulaTab = "//*[@data-row-key-value='OPTION']";
	public String lblQualificationFormulaPath;
	public String lblBenefitFormulaIdPath;
	public GenericPage(WebDriver driver) {

		this.driver = driver;
		sfdcAcolyte = new Acolyte(driver);
		sfdcAcolyte.setWaitTime(60);
		PageFactory.initElements(driver, this);
	}

	public void clickButton(WebElement button) throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(button).click(button);

	}
	public void clickButtonAndWait(WebElement button, WebElement waitElement) throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(button).click(button);
		sfdcAcolyte.waitTillElementIsVisible(waitElement);
	}
	public void moveToFormulaTab(String qualificationFormula, String benefitFormula,WebElement element) throws Exception {

		sfdcAcolyte.waitTillElementIsClickable(element).click(element);
		lblQualificationFormulaPath = lnkFormulaTab.replace("OPTION", qualificationFormula);
		lblBenefitFormulaIdPath = lnkFormulaTab.replace("OPTION", benefitFormula);
		sfdcAcolyte.waitTillElementIsVisible(By.xpath(lblQualificationFormulaPath));
		sfdcAcolyte.waitTillElementIsVisible(By.xpath(lblBenefitFormulaIdPath));
	}
}
