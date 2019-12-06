package com.apttus.sfdc.rebates.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.apttus.sfdc.rebates.lightning.HomePage;
import com.apttus.ui.fundamentals.Acolyte;

public class StartUpPage {

	public WebDriver driver;
	public Acolyte sfdcAcolyte;
		
	public StartUpPage(WebDriver driver){
		this.driver=driver;
		sfdcAcolyte=new Acolyte(driver);
		sfdcAcolyte.setWaitTime(60);
		
	}
	
	public LoginPage navigateToLoginPage(String LoginURL) throws Exception {
		sfdcAcolyte.maximizeWindow();
		sfdcAcolyte.navigateTo(LoginURL);
		return PageFactory.initElements(driver, LoginPage.class);
	}
			
}