package com.apttus.sfdc.rebates.lightning.ui.library;

import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.apttus.selenium.NGHelper;
import com.apttus.sfdc.rebates.lightning.common.GenericPage;

public class HomePage extends GenericPage {

	@FindBy(css = ".logout ")
	public WebElement logOutLink;

	@FindBy(css = "[title='Close this window']")
	public WebElement closeWindow;

	@FindBy(css = ".oneUserProfileCardTrigger span .uiImage")
	public WebElement userProfileIcon;

	@FindBy(xpath = "//span[text()='Data Sources']")
	public WebElement lnkDataSource;
	
	@FindBy(xpath="//div[@class='slds-icon-waffle']")
    public WebElement btnAppLauncher;
   
    @FindBy(xpath="//a[text()='Apttus CIM Admin']")
    public WebElement lnkApttusCIMAdmin;

	public HomePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public HomePage logout() throws Exception {
		sfdcAcolyte.waitTillElementIsClickable(closeWindow).jsClick(closeWindow);
		sfdcAcolyte.waitTillElementIsClickable(userProfileIcon).jsClick(userProfileIcon);
		sfdcAcolyte.waitTillElementIsClickable(logOutLink).jsClick(logOutLink);
		return PageFactory.initElements(driver, HomePage.class);
	}

	public DataSourcePage navigateToDataSource() throws Exception {

		sfdcAcolyte.waitTillElementIsClickable(lnkDataSource).jsClick(lnkDataSource);
		return PageFactory.initElements(driver, DataSourcePage.class);
	}

	public HomePage landOnHomepage() throws Exception {
		return PageFactory.initElements(driver, HomePage.class);
	}

	public HomePage navigateBackToHomepage(Properties properties) throws Exception {
		NGHelper ngHelper = new NGHelper(driver);
		ngHelper.NavigateTo(properties.getProperty("LoginURL"));
		return PageFactory.initElements(driver, HomePage.class);
	}

	
	public HomePage launchCIMApttusAdmin() throws Exception {
       
        sfdcAcolyte.waitTillElementIsClickable(btnAppLauncher).click(btnAppLauncher)
        .waitTillElementIsClickable(lnkApttusCIMAdmin).click(lnkApttusCIMAdmin);
        return PageFactory.initElements(driver, HomePage.class);
       
       
    }
}
