package com.apttus.sfdc.rebates.lightning.common;

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
	public void waitTillElementIsVisible(WebElement element) throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(element);
	}
}
