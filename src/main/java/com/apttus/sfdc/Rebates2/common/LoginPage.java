package com.apttus.sfdc.Rebates2.common;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.apttus.sfdc.Rebates2.HomePageClassic;
//import com.apttus.sfdc.clm.common.LoginPage;
import com.apttus.sfdc.Rebates2.lightning.HomePage;




public class LoginPage extends StartUpPage {
	
	@FindBy(id="username")
	@CacheLookup
	public WebElement txtUserName;
	
	@FindBy(id="password")
	@CacheLookup
	public WebElement txtPassword;
	
	@FindBy(id="Login")
	public WebElement btnLogin;
	

	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	public LoginPage waitForLoginPageLoad() throws Exception {
		
		sfdcAcolyte.waitTillElementIsVisible(btnLogin)
				   .waitTillElementIsClickable(btnLogin);
		return PageFactory.initElements(driver, LoginPage.class);
	}
	
	public HomePage loginToApp(String userName, String password) throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(txtUserName)
		.clear(txtUserName)
		.clear(txtPassword);
		txtUserName.sendKeys(userName);
		
		txtPassword.sendKeys(password);
		btnLogin.click();
		Thread.sleep(10000);
		return PageFactory.initElements(driver, HomePage.class);
	}
	
}
