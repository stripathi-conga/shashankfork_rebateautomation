package com.apttus.sfdc.rebates.ui.library;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.apttus.selenium.NGHelper;
import com.apttus.sfdc.rebates.lightning.HomePage;

public class Rebatesinit {

	public static  WebDriver driver;
	public static  NGHelper ngHelper;

	
	@FindBy(name="loginfmt")
	@CacheLookup
	public WebElement typeUsername;
	
	@FindBy(id="idSIButton9")
	@CacheLookup
	public WebElement clickNext;
	
	@FindBy(name="passwd")
	public WebElement typePassword;
	
	@FindBy(id="idSIButton9")
	@CacheLookup
	public WebElement clickSubmit;
	
	@FindBy(css="span.loadImageText")
	public WebElement loaderGIFcsselem;
	
	@FindBy(xpath="//*[@id='ngProgress']")
	public WebElement progressBar;
	
	@FindBy(id="idBtn_Back")
	@CacheLookup
	public WebElement btnStaySignInNo;
		
	public Rebatesinit(WebDriver driver) {
		this.driver=driver;
	   PageFactory.initElements(driver, this);
	}
	
	public HomePage landOnHomepage() throws Exception {
		return PageFactory.initElements(driver, HomePage.class);
	}
	

	public HomePage navigateBackToHomepage(Properties properties) throws Exception {
		ngHelper.NavigateTo(properties.getProperty("LoginURL"));
		return PageFactory.initElements(driver, HomePage.class);
	}
	
	public void closeBrowser() {
		driver.quit();
	}
	
}
