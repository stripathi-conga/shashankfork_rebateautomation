package com.apttus.sfdc.Rebates2.library;


import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.apttus.helpers.JavaHelpers;
import com.apttus.selenium.NGHelper;
import com.apttus.selenium.WebDriverUtils;
import com.apttus.sfdc.Rebates2.lightning.HomePage;
import com.apttus.ui.fundamentals.Acolyte;

public class Rebatesinit {

	public static WebDriver driver;
	public static NGHelper ngHelper;
	public static WebDriverUtils utils;
	public Actions actions;
	public static File folder;
	public Acolyte sfdcAcolyte;
	
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
		sfdcAcolyte=new Acolyte(driver);
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
