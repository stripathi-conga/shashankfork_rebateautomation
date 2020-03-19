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
	public String lblStepQualificationFormulaPath;
	public String lblStepBenefitFormulaIdPath;
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
	public void moveToFormulaTab(String stepQualificationFormula, String stepBenefitFormula,WebElement element) throws Exception {

		sfdcAcolyte.waitTillElementIsClickable(element).click(element);
		lblStepQualificationFormulaPath = lnkFormulaTab.replace("OPTION", stepQualificationFormula);
		lblStepBenefitFormulaIdPath = lnkFormulaTab.replace("OPTION", stepBenefitFormula);
		sfdcAcolyte.waitTillElementIsVisible(By.xpath(lblStepQualificationFormulaPath));
		sfdcAcolyte.waitTillElementIsVisible(By.xpath(lblStepBenefitFormulaIdPath));
	}
}
