package com.apttus.sfdc.rebates.lightning.ui.library;

import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.apttus.selenium.NGHelper;
import com.apttus.sfdc.rebates.lightning.common.GenericPage;
import com.apttus.sfdc.rebates.lightning.generic.utils.RebatesConstants;
import com.apttus.sfdc.rebates.lightning.generic.utils.RebatesUIConstants;
import com.apttus.sfdc.rebates.lightning.generic.utils.URLGenerator;


public class HomePage extends GenericPage {

	@FindBy(css = ".logout ")
	public WebElement logOutLink;

	@FindBy(css = "[title='Close this window']")
	public WebElement closeWindow;

	@FindBy(css = ".oneUserProfileCardTrigger span .uiImage")
	public WebElement userProfileIcon;

	public URLGenerator urlGenerator;

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

	public HomePage landOnHomepage() throws Exception {
		return PageFactory.initElements(driver, HomePage.class);
	}

	public HomePage navigateBackToHomepage(Properties properties) throws Exception {
		NGHelper ngHelper = new NGHelper(driver);
		ngHelper.NavigateTo(properties.getProperty("LoginURL"));
		return PageFactory.initElements(driver, HomePage.class);
	}

	public TemplatePage navigateToTemplates(Properties properties) throws Exception {
				
		sfdcAcolyte.navigateTo(properties.getProperty("cimAdminURL")+ RebatesConstants.Templates);
		
		return PageFactory.initElements(driver, TemplatePage.class);
	}
	

       public Incentivepage navigateToIncentiveEdit(Properties properties, String incentiveId) throws Exception {

              sfdcAcolyte.navigateTo(
                               properties.getProperty("cimURL") + RebatesUIConstants.incentive.replace("{incentiveId}", incentiveId));

               return PageFactory.initElements(driver, Incentivepage.class);
       }

}
