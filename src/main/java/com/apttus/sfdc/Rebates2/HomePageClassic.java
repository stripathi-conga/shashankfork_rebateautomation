package com.apttus.sfdc.Rebates2;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.PageFactory;

import com.apttus.sfdc.Rebates2.common.StartUpPage;
import com.apttus.ui.fundamentals.WebElementBuilder;

public class HomePageClassic extends StartUpPage {
		
	@FindBy(css=".sidebarModuleBody ul li a")
	public List<WebElement> apttusLinks;
	
	@FindBy(id="p3")
	public WebElement ddlRecordType;

	@FindBy(css="a[title='Agreements Tab']")
	public WebElement menuAgreementsTab;

	@FindBy(css="a[title='Accounts Tab']")
	public WebElement menuAccountsTab;
	
	@FindBy(css=".sidebar")
	public WebElement sideBar;
	
	@FindBy(id = "chatterfeedshell")
	public WebElement panelLoad;
	
	@FindBy(css="li[id*='AllTab_Tab']")
	public WebElement allTab;
	
	@FindBy(css="img[title='Opportunities']")
	public WebElement menuOpportunityTab;
	
	@FindBy(css=".pbBody span[id*='RecordType'] select")
	public WebElement opportunityRecordType;
	
	@FindBy(className = "allTabsArrow")
	public WebElement menuAllTabs;
	
	@FindBy(linkText = "Agreement Protection")
	public WebElement linkAgmtProtection;
	
	@FindBy(css="a[title='Agreements Tab - Selected']")
	public WebElement menuAgrTab;
	
	@FindBy(css="img[title='Intelligent Import Settings']")
	public WebElement intImportSetting;
	
	@FindBy(css = "input[title='Create Import Offline']")
	@CacheLookup
	public WebElement btnCreateImpOffline;

	@FindAll({@FindBy(id="userNavLabel"), @FindBy(id="globalHeaderNameMink")})
	@CacheLookup
	public WebElement userMenuTab;

	@FindBy(css="a[title='Logout']")
	@CacheLookup
	public WebElement btnLogout;

	@FindBy(css = "a[title*='Wizard Component Library Tab']")
	public WebElement wizardComponentLibraryTab;
	
	@FindBy(css=".menuButtonMenu a")
	public List<WebElement> createNewLinks;

	@FindBy(id="createNewButton")
	public WebElement btnCreateNew;

	@FindBy(css = "a[title='Wizard Designs Tab']")
	@CacheLookup
	public WebElement wizardDesignsTab;

	@FindBy(css = "a[title='Wizards Tab']")
	@CacheLookup
	public WebElement wizardTab;

	@FindBy(css = "img[title='Agreement Document Search']")
	public WebElement lnkAgrDocSearch;

	@FindBy(id = "home_Tab")
	public WebElement homeTab;
	
	@FindBy(css=".pbBody .requiredInput select")
	public WebElement opportunityRecord;

	public HomePageClassic(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		sfdcAcolyte.setWaitTime(60);
	}
	
	/**
	 * Wait for Home page to load
	 * @return
	 * @throws Exception
	 */
	public HomePageClassic waitForClassicHomePageToLoad() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(panelLoad);
		return PageFactory.initElements(driver, HomePageClassic.class);
	}
	
	/**
	 * Click on links available at left pane 
	 * @param  String Value
	 * @return Page
	 * @throws Exception
	 */
	/*public AgreementDetailsPageClassic clickAgrLink(String lnkName) throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(sideBar);
		WebElement selector = new WebElementBuilder(driver).getElementContainingText(apttusLinks, lnkName)
				.getWebElement();
		sfdcAcolyte.waitTillElementIsClickable(selector)
		.jsClick(selector);
		return PageFactory.initElements(driver, AgreementDetailsPageClassic.class);
	}*/

	/**
	 * Select Record Type from RecordType dropdown 
	 * @param  String Value
	 * @return Page
	 * @throws Exception
	 */
	/*public AgreementDetailsPageClassic selectClassicRecordType(WebElement element, String recordType) throws Exception {
		sfdcAcolyte.waitTillElementIsClickable(element)
		.selectComboByText(element, recordType);
		return PageFactory.initElements(driver, AgreementDetailsPageClassic.class);
	}*/

	/**
	 * Navigate to Menu Tabs 
	 * @param  
	 * @return Page
	 * @throws Exception
	 */
	/*public AgreementDetailsPageClassic navigateToMenuTab(WebElement menuTabName) throws Exception {
		sfdcAcolyte.waitTillElementIsClickable(menuTabName)
		.click(menuTabName);
		return PageFactory.initElements(driver, AgreementDetailsPageClassic.class);
	}	*/
	
	/**
	 * Navigate to All Tabs (+)
	 * @param  
	 * @return Page
	 * @throws Exception
	 */
	/*public AgreementProtectionPageClassic navigateToProtectionType() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(linkAgmtProtection)
					.waitTillElementIsClickable(linkAgmtProtection)
					.click(linkAgmtProtection);		
		return PageFactory.initElements(driver, AgreementProtectionPageClassic.class);
	}*/

	/**
	 * Navigate to Account page from HomePage Menu.
	 * @return page
	 * @throws Exception
	 * @author Sanjay.Panwar
	 */
	public HomePageClassic accountLogOut() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(userMenuTab)
				.waitTillElementIsClickable(userMenuTab)
				.jsClick(userMenuTab)
				.waitTillElementIsClickable(btnLogout)
				.jsClick(btnLogout);
		return PageFactory.initElements(driver, HomePageClassic.class);
	}

	/**
	 * Click on Sidebar links available under Create New 
	 * @param  String Value
	 * @return Page
	 * @throws Exception
	 */
	/*public AgreementDetailsPageClassic clickCreateNewLink(String lnkName) throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(sideBar)
		.jsClick(btnCreateNew);
		WebElement selector = new WebElementBuilder(driver).getElementContainingText(createNewLinks, lnkName)
				.getWebElement();
		sfdcAcolyte.waitTillElementIsClickable(selector)
		.jsClick(selector);
		return PageFactory.initElements(driver, AgreementDetailsPageClassic.class);
	}*/
	
}
