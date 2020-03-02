package com.apttus.sfdc.rebates.lightning.ui.library;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.apttus.selenium.NGHelper;
import com.apttus.sfdc.rebates.lightning.common.GenericPage;

public class Incentivepage extends GenericPage  {

	@FindBy(xpath = "//*[text()='Activate']")
	public WebElement btnActivate;
	
	@FindBy(css = ".forceActionsText")
	public WebElement txtToastMessage;
	
	GenericPage genericPage;
	WebDriverWait wait;
	NGHelper nghelper;

	public Incentivepage(WebDriver driver) {
		super(driver);
		wait = new WebDriverWait(driver, 60);
		PageFactory.initElements(driver, this);
	}

	public void activateIncentive() throws Exception {
		
		sfdcAcolyte.waitTillElementIsVisible(btnActivate).waitTillElementIsClickable(btnActivate).
		            jsClick(btnActivate).waitTillElementIsVisible(txtToastMessage);
		
	}
}
