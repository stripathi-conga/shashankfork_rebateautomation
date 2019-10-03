package com.apttus.sfdc.Rebates2.lightning;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.apttus.sfdc.Rebates2.common.StartUpPage;
import com.apttus.sfdc.Rebates2.library.DataSourcePage;


public class HomePage extends StartUpPage {
	
	@FindBy(className="switch-to-lightning")
	public WebElement linkSwitchToLight;
	
	@FindBy(css=".logout ")
	public WebElement logOutLink;
	
	@FindBy(css="[title='Close this window']")
	public WebElement closeWindow;
	
	@FindBy(css=".oneUserProfileCardTrigger span .uiImage")
	public WebElement userProfileIcon;

	
	public WebElement lnkAgrDocSearch;	
	
	public HomePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		sfdcAcolyte.setWaitTime(60);
	}
	
	
	 	
	/**
	 * Log out from Application
	 * @return
	 * @throws Exception
	 */
	public HomePage logout() throws Exception {
		sfdcAcolyte.waitTillElementIsClickable(closeWindow)
		.jsClick(closeWindow);
		sfdcAcolyte.waitTillElementIsClickable(userProfileIcon)
		.jsClick(userProfileIcon);
		sfdcAcolyte.waitTillElementIsClickable(logOutLink)
		.jsClick(logOutLink);
		return PageFactory.initElements(driver, HomePage.class);
	}
	
	/**
	 * DataSourceNavigation
	 * Need to add steps for datasource navigate in place of direct URL Launch
	 */

		public DataSourcePage navigateToDataSource() throws Exception {
		
		
		sfdcAcolyte.navigateTo("https://rebateqa--rebates.lightning.force.com/lightning/o/Data_Source__c/list?filterName=Recent");
			
		return PageFactory.initElements(driver, DataSourcePage.class);
		
	}
}

