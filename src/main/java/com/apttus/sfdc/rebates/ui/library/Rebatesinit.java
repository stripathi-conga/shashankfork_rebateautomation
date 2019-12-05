package com.apttus.sfdc.rebates.ui.library;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import com.apttus.selenium.NGHelper;
import com.apttus.sfdc.rebates.lightning.HomePage;

public class Rebatesinit {

	public static  WebDriver driver;
	public static  NGHelper ngHelper;

		
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
