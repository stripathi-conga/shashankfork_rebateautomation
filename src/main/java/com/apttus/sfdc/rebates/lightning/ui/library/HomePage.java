package com.apttus.sfdc.rebates.lightning.ui.library;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.apttus.selenium.NGHelper;
import com.apttus.sfdc.rebates.lightning.common.GenericPage;
import com.apttus.sfdc.rebates.lightning.generic.utils.RebatesConstants;
import com.apttus.sfdc.rebates.lightning.generic.utils.URLGeneratorUI;

public class HomePage extends GenericPage {

	@FindBy(css = ".logout ")
	public WebElement logOutLink;

	@FindBy(css = "[title='Close this window']")
	public WebElement closeWindow;

	@FindBy(css = ".oneUserProfileCardTrigger span .uiImage")
	public WebElement userProfileIcon;
	@FindBy(xpath = "//*[text()='App Launcher']")
	public WebElement btnAppLauncher;

	@FindBy(xpath = "//*[text()='View All']")
	public WebElement lnkViewAll;

	@FindBy(xpath = "//p[text()='Apttus CIM']")
	public WebElement lnkApttusCIM;

	@FindBy(xpath = "//p[text()='Apttus CIM Admin']")
	public WebElement lnkApttusAdminCIM;

	public URLGeneratorUI urlGeneratorUI;
	public Properties configProperty;

	public HomePage(WebDriver driver, Properties configProperty) {
		super(driver);
		urlGeneratorUI = new URLGeneratorUI(configProperty);
		PageFactory.initElements(driver, this);
	}

	public HomePage logout() throws Exception {
		sfdcAcolyte.waitTillElementIsClickable(closeWindow).jsClick(closeWindow);
		sfdcAcolyte.waitTillElementIsClickable(userProfileIcon).jsClick(userProfileIcon);
		sfdcAcolyte.waitTillElementIsClickable(logOutLink).jsClick(logOutLink);
		return PageFactory.initElements(driver, HomePage.class);
	}

	public HomePage landOnHomepage() throws Exception {
		return PageFactory.initElements(driver, HomePage.class);
	}

	public HomePage navigateBackToHomepage(Properties properties) throws Exception {
		NGHelper ngHelper = new NGHelper(driver);
		ngHelper.NavigateTo(properties.getProperty("LoginURL"));
		return PageFactory.initElements(driver, HomePage.class);
	}

	public TemplatePage navigateToTemplates() throws Exception {

		sfdcAcolyte.navigateTo(urlGeneratorUI.templateHomeURL.replace("{templateId}", RebatesConstants.homePath)
				.replace("{view}", ""));

		return PageFactory.initElements(driver, TemplatePage.class);
	}

	public IncentivePage navigateToIncentiveEdit(String incentiveId) throws Exception {

		sfdcAcolyte.navigateTo(urlGeneratorUI.incentiveEditURL.replace("{incentiveId}", incentiveId).replace("{view}",
				RebatesConstants.viewPath));
		return PageFactory.initElements(driver, IncentivePage.class);
	}

	public void navigateToCIM() throws Exception {

		sfdcAcolyte.waitTillElementIsVisible(btnAppLauncher).waitTillElementIsClickable(btnAppLauncher)
				.jsClick(btnAppLauncher);
		sfdcAcolyte.waitTillElementIsVisible(lnkViewAll).waitTillElementIsClickable(lnkViewAll).jsClick(lnkViewAll);
		sfdcAcolyte.waitTillElementIsVisible(lnkApttusCIM).waitTillElementIsClickable(lnkApttusCIM)
				.jsClick(lnkApttusCIM);
	}

	public void navigateToCIMAdmin() throws Exception {

		sfdcAcolyte.waitTillElementIsVisible(btnAppLauncher).waitTillElementIsClickable(btnAppLauncher)
				.jsClick(btnAppLauncher);
		sfdcAcolyte.waitTillElementIsVisible(lnkViewAll).waitTillElementIsClickable(lnkViewAll).jsClick(lnkViewAll);
		sfdcAcolyte.waitTillElementIsVisible(lnkApttusCIM).waitTillElementIsClickable(lnkApttusCIM)
				.jsClick(lnkApttusAdminCIM);
	}

	public DataSourcePage navigateToNewDataSource() throws Exception {
		sfdcAcolyte.navigateTo(urlGeneratorUI.dataSourceNewURL.replace("{dataSourceId}", RebatesConstants.newPath)
				.replace("{view}", ""));
	
		return PageFactory.initElements(driver, DataSourcePage.class);
	}

	public TemplatePage navigateToEditTemplate(String templateId) throws Exception {
		sfdcAcolyte.navigateTo(urlGeneratorUI.incentiveEditURL.replace("{templateId}", templateId).replace("{view}",
				RebatesConstants.editPath));
	
		return PageFactory.initElements(driver, TemplatePage.class);
	}


}
