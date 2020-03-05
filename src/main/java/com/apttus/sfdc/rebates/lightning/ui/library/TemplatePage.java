package com.apttus.sfdc.rebates.lightning.ui.library;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.apttus.selenium.NGHelper;
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

	@FindBy(css = "span[class='slds-checkbox_faux']")
	public List<WebElement> chkQualification;

	@FindBy(css = "input[name='Qualification Formulas']")
	public WebElement lblQualification;

	@FindBy(css = "[name='Benefit Formulas']")
	public WebElement lblBenefitFormulas;

	@FindBy(xpath = "//b[text()='No data to display']")
	public List<WebElement> txtNoDataDisplay;

	@FindBy(xpath = "//*[text()='Benefit Product']")
	public WebElement lbBenefitProductDetails;

	@FindBy(xpath = "//*[contains(text(),'Rebates_Auto_DataSource_')]")
	public WebElement lbDataSourceDetails;

	@FindBy(xpath = "//div[contains(text(),'Automation')]")
	public WebElement lbDescriptionDetails;

	@FindBy(xpath = "//*[text()='Formulas']")
	public WebElement lbFormulaTab;

	@FindBy(xpath = "//div[contains(text(),'Tiered')]")
	public WebElement lbTierDetails;
	
	String benefitProduct = "Benefit Product";
	String discrete = "Discrete";
	String tiered = "Tiered";

	String cmbTxt = "//*[@data-value='OPTION']";
	String lnkTemplateId = "//*[@data-recordid='OPTION']";
	String lnkFormulaTab = "//*[@value='OPTION']";

	GenericPage genericPage;
	WebDriverWait wait;
	NGHelper nghelper;
	public boolean templateEditURL;
	public String lblQualificationFormulaPath;
	public String lblBenefitFormulaIdPath;

	public TemplatePage(WebDriver driver) {
		super(driver);
		wait = new WebDriverWait(driver, 120);
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

	public TemplatePage qnbLayoutDefinition(WebElement ddlQBselect, WebElement ddlTierSelect) throws Exception {

		String benefitProductPath = cmbTxt.replace("OPTION", benefitProduct);
		String discretePath = cmbTxt.replace("OPTION", discrete);

		sfdcAcolyte.waitTillElementIsVisible(ddlQBselect).waitTillElementIsClickable(ddlQBselect).jsScroll(ddlQBselect)
				.click(ddlQBselect).waitTillElementIsVisible(By.xpath(benefitProductPath))
				.waitTillElementIsClickable(By.xpath(benefitProductPath)).click(By.xpath(benefitProductPath));
		sfdcAcolyte.waitTillElementIsClickable(ddlTierSelect).click(ddlTierSelect)
				.waitTillElementIsVisible(By.xpath(discretePath)).waitTillElementIsClickable(By.xpath(discretePath))
				.click(By.xpath(discretePath));
		return PageFactory.initElements(driver, TemplatePage.class);
	}

	public void addQualificationOnDiscrete(CIMAdmin cimAdmin) throws Exception {

		sfdcAcolyte.waitTillElementIsVisible(ddlDataSource).waitTillElementIsClickable(ddlDataSource)
				.click(ddlDataSource);
		sfdcAcolyte.sendTextKeys(cimAdmin.getDataSourceData().getName__c()).sendBoardKeys(Keys.ENTER);

	}

	public void addQualificationOnTiered(CIMAdmin cimAdmin) throws Exception {

		String tieredPath = cmbTxt.replace("OPTION", tiered);

		sfdcAcolyte.waitTillElementIsClickable(ddlTierSelect).click(ddlTierSelect)
				.waitTillElementIsVisible(By.xpath(tieredPath)).waitTillElementIsClickable(By.xpath(tieredPath))
				.click(By.xpath(tieredPath));
		sfdcAcolyte.click(ddlDataSource);
		sfdcAcolyte.sendTextKeys(cimAdmin.getDataSourceData().getName__c()).sendBoardKeys(Keys.ENTER);
		for (int i = 0; i < chkQualification.size(); i++) {
			sfdcAcolyte.waitTillElementIsClickable(chkQualification.get(i)).jsClick(chkQualification.get(i));
		}

	}

	public void moveToTemplateViaIdClick(String templateId) throws Exception {

		String lnkTemplateIdPath = lnkTemplateId.replace("OPTION", templateId);
		sfdcAcolyte.waitTillElementIsVisible(By.xpath(lnkTemplateIdPath));
		sfdcAcolyte.jsClick(By.xpath(lnkTemplateIdPath));

		sfdcAcolyte.waitTillElementIsVisible(lbDataSourceDetails).waitTillElementIsVisible(lbDataSourceDetails);

		templateEditURL = sfdcAcolyte.getCurrentURL().contains(templateId + "/view");
		sfdcAcolyte.waitTillElementIsVisible(lbDescriptionDetails);
	}

	public void moveToFormulaTab(String calcFormulaIdQualification, String calcFormulaIdBenefit) throws Exception {

		sfdcAcolyte.waitTillElementIsClickable(lbFormulaTab).click(lbFormulaTab);
		lblQualificationFormulaPath = lnkFormulaTab.replace("OPTION", calcFormulaIdQualification);
		lblBenefitFormulaIdPath = lnkFormulaTab.replace("OPTION", calcFormulaIdBenefit);
		sfdcAcolyte.waitTillElementIsVisible(By.xpath(lblQualificationFormulaPath));
		sfdcAcolyte.waitTillElementIsVisible(By.xpath(lblBenefitFormulaIdPath));

	}
}