package com.apttus.sfdc.rebates.lightning.ui.library;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.apttus.sfdc.rebates.lightning.api.library.CIMAdmin;
import com.apttus.sfdc.rebates.lightning.common.GenericPage;

public class TemplatePage extends GenericPage {

	@FindBy(css = "div[title='New']")
	public WebElement btnNew;

	@FindBy(xpath = "//*[text()='Q&B Layout']/..//input")
	public WebElement ddlQBselect;

	@FindBy(xpath = "//*[text()='Tier']//..//input")
	public WebElement ddlTierSelect;

	@FindBy(xpath = "//*[text()='Data Source']//..//input")
	public WebElement ddlDataSource;

	@FindBy(css = "input[name='Qualification Formulas']")
	public WebElement lblQualification;

	@FindBy(css = "[name='Benefit Formulas']")
	public WebElement lblBenefitFormulas;

	@FindBy(xpath = "//b[text()='No data to display']")
	public List<WebElement> txtNoDataDisplay;

	@FindBy(xpath = "//*[text()='Save'][@id]")
	public WebElement btnSave;

	@FindBy(xpath = "//*[text()='Edit']")
	public WebElement btnEdit;

	@FindBy(xpath = "//*[text()='Benefit Product']")
	public WebElement lblBenefitProductDetails;

	@FindBy(xpath = "//*[contains(text(),'Rebates_Auto_DataSource_')]")
	public WebElement lblDataSourceDetails;

	@FindBy(xpath = "//div[contains(text(),'Automation')]")
	public WebElement lblDescriptionDetails;

	@FindBy(xpath = "//*[text()='Formulas']")
	public WebElement lblFormulaTab;

	@FindBy(xpath = "//*[text()='Edit Template']")
	public WebElement lblEdit;

	@FindBy(xpath = "//div[contains(text(),'Tiered')]")
	public WebElement lblTierDetails;

	@FindBy(xpath = "//*[@name='Qualification Formulas'][@id]")
	public List<WebElement> chkQualificationValue;

	@FindBy(xpath = "//*[@name='Benefit Formulas'][@id]")
	public List<WebElement> chkBenefitFormulaValue;

	@FindBy(xpath = "//button[text()='Activate']")
	public WebElement btnActive;

	@FindBy(xpath = "//a[contains(@href,'/IncentiveProgramTemplate__c')]")
	public WebElement lnkTemplates;

	@FindBy(xpath = "//span[text()='Draft']")
	public WebElement lnkInlineEdit;

	@FindBy(xpath = "//a[text()='Draft']")
	public WebElement lnkInlineDraft;

	@FindBy(xpath = "//a[text()='Active']")
	public WebElement lnkInlineActive;

	@FindBy(xpath = "//*[text()='Save']")
	public WebElement btnInLineSave;

	@FindBy(xpath = "//input[@placeholder='Search this list...']")
	public WebElement txtSearchBox;

	@FindBy(xpath = "//div/span[text()='Recently Viewed']")
	public WebElement btnRecentlyViewed;

	@FindBy(xpath = "	//*[@class='forceInlineEditButton']")
	public WebElement btnInLineEdit;

	@FindBy(xpath = "//div[@title='List View Controls']")
	public WebElement btnsetting;

	@FindBy(xpath = "//lightning-combobox/div/lightning-base-combobox/div/div/lightning-base-combobox-item[1]")
	public WebElement dpDiscreteValue;

	String lnkTemplateId = "//*[@data-recordid='OPTION']";
	GenericPage genericPage;
	WebDriverWait wait;
	public boolean templateEditURL;

	public TemplatePage(WebDriver driver) {
		super(driver);
		wait = new WebDriverWait(driver, 120);
		genericPage = new GenericPage(driver);
		PageFactory.initElements(driver, this);
	}

	public TemplatePage moveToNewtemplatepage(WebElement newbutton, WebElement Labelnewtemplate) throws Exception {

		sfdcAcolyte.waitTillElementIsVisible(newbutton).click(newbutton).waitTillElementIsVisible(Labelnewtemplate);
		return PageFactory.initElements(driver, TemplatePage.class);
	}

	public TemplatePage moveToTemplate(WebElement newbutton) throws Exception {

		sfdcAcolyte.waitTillElementIsVisible(newbutton).waitTillElementIsClickable(newbutton).click(newbutton);
		return PageFactory.initElements(driver, TemplatePage.class);
	}

	public TemplatePage moveToTemplateViaIdClick(String templateId) throws Exception {

		String lnkTemplateIdPath = lnkTemplateId.replace("OPTION", templateId);
		sfdcAcolyte.waitTillElementIsVisible(By.xpath(lnkTemplateIdPath));
		sfdcAcolyte.jsClick(By.xpath(lnkTemplateIdPath));
		sfdcAcolyte.waitTillElementIsVisible(lblDataSourceDetails).waitTillElementIsVisible(lblDataSourceDetails);
		templateEditURL = sfdcAcolyte.getCurrentURL().contains(templateId + "/view");
		sfdcAcolyte.waitTillElementIsVisible(lblDescriptionDetails);
		return PageFactory.initElements(driver, TemplatePage.class);
	}

	public void moveToEditTemplate(String templateId) throws Exception {
		templateEditURL = sfdcAcolyte.getCurrentURL().contains(templateId + "/Edit");
	}

	public void searchTemplateName(String templateName) throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(txtSearchBox).click(txtSearchBox);
		sfdcAcolyte.sendTextKeys(templateName).sendBoardKeys(Keys.ENTER);
		genericPage.clickElement(btnsetting).clickElement(btnRecentlyViewed);
	}

	public TemplatePage changeInLineStatus() throws Exception {
		genericPage.waitTillPageContentLoad();
		genericPage.doubleClick(lnkInlineEdit);
		genericPage.clickElement(lnkInlineDraft).clickWhenElementIsVisibleAndClickable(lnkInlineActive)
				.clickWhenElementIsVisibleAndClickable(btnInLineSave);
		sfdcAcolyte.waitTillElementIsVisible(genericPage.txtToastMessage);
		return PageFactory.initElements(driver, TemplatePage.class);
	}

	public TemplatePage selectTier(CIMAdmin cimAdmin, WebElement element) throws Exception {
		genericPage.moveToElementAndClick(ddlTierSelect, ddlTierSelect);
		genericPage.doubleClick(dpDiscreteValue);
		return PageFactory.initElements(driver, TemplatePage.class);

	}
}