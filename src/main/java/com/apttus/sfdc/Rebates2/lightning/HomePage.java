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
	
	@FindBy(xpath="//span[text()='Templates']")
	public WebElement templatelnk;
	
	@FindBy(xpath="//span[text()='Link Templates']")
	public WebElement lnkTemplatelnk;
	
	@FindBy(xpath="//span[text()='Data Sources']")
	public WebElement datasrclnk;
	
	@FindBy(xpath="//*[@data-aura-class='uiOutputText forceBreadCrumbItem'][text()='Link Templates']")
	public WebElement lnkTemplatepagelabel;
	
	
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
		
		
		sfdcAcolyte.waitTillElementIsClickable(datasrclnk).
        jsClick(datasrclnk);
			
		return PageFactory.initElements(driver, DataSourcePage.class);
		
	}
		
		
}

