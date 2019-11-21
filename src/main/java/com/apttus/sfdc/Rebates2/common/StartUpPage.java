package com.apttus.sfdc.Rebates2.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.apttus.selenium.NGHelper;
import com.apttus.sfdc.Rebates2.common.LoginPage;
import com.apttus.sfdc.Rebates2.lightning.HomePage;
import com.apttus.ui.fundamentals.Acolyte;

public class StartUpPage {

	public WebDriver driver;
	public Acolyte sfdcAcolyte;
	public NGHelper nghelper;
	
	public StartUpPage(WebDriver driver){
		this.driver=driver;
		
		sfdcAcolyte=new Acolyte(driver);
		sfdcAcolyte.setWaitTime(60);
	}
	
	public LoginPage navigateToLoginPage(String baseURL) throws Exception {
		sfdcAcolyte.maximizeWindow()
				  .navigateTo(baseURL);
		
		return PageFactory.initElements(driver, LoginPage.class);
	}
	
	public HomePage navigateToHomePage(String homeURI) throws Exception {
		
		sfdcAcolyte.navigateTo(homeURI);	
	
		return PageFactory.initElements(driver, HomePage.class);
	}
		
		
	public void killDriver() {
		driver.quit();
	}
}