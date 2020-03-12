package com.apttus.sfdc.rebates.lightning.ui.library;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.apttus.sfdc.rebates.lightning.common.GenericPage;

public class LoginPage extends GenericPage {

	@FindBy(id = "username")
	@CacheLookup
	public WebElement txtUserName;

	@FindBy(id = "password")
	@CacheLookup
	public WebElement txtPassword;

	@FindBy(id = "Login")
	public WebElement btnLogin;

	public LoginPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public LoginPage waitForLoginPageLoad() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(btnLogin).waitTillElementIsClickable(btnLogin);
		return PageFactory.initElements(driver, LoginPage.class);
	}

	public void loginToApp(String userName, String password) throws Exception {
		sfdcAcolyte.waitTillElementIsClickable(txtUserName);
		txtUserName.sendKeys(userName);
		txtPassword.sendKeys(password);
		btnLogin.click();
	}

	public LoginPage navigateToLoginPage(String LoginURL) throws Exception {
		sfdcAcolyte.maximizeWindow();
		sfdcAcolyte.navigateTo(LoginURL);
		return PageFactory.initElements(driver, LoginPage.class);
	}
}
