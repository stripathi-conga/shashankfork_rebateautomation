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

	@FindBy(css = "input[name='Qualification Formulas']")
	public WebElement lblQualification;

	@FindBy(css = "[name='Benefit Formulas']")
	public WebElement lblBenefitFormulas;

	@FindBy(xpath = "//b[text()='No data to display']")
	public List<WebElement> txtNoDataDisplay;

	@FindBy(xpath = "//*[text()='Benefit Product']")
	public WebElement lblBenefitProductDetails;

	@FindBy(xpath = "//*[contains(text(),'Rebates_Auto_DataSource_')]")
	public WebElement lblDataSourceDetails;

	@FindBy(xpath = "//div[contains(text(),'Automation')]")
	public WebElement lblDescriptionDetails;

	@FindBy(xpath = "//*[text()='Formulas']")
	public WebElement lblFormulaTab;

	@FindBy(xpath = "//div[contains(text(),'Tiered')]")
	public WebElement lblTierDetails;

	@FindBy(xpath = "//*[@name='Qualification Formulas'][@id]")
	public List<WebElement> chkQualificationValue;

	@FindBy(xpath = "//*[@name='Benefit Formulas'][@id]")
	public List<WebElement> chkBenefitFormulaValue;

	@FindBy(css = "//*[text()='Edit']")
	public WebElement btnEdit;

	public String lblStepQualificationFormulaPath;
	public String lblStepBenefitFormulaIdPath;
	public String lblNonStepQualificationFormulaPath;
	public String lblNonStepBenefitFormulaIdPath;
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
		sfdcAcolyte.sendTextKeys(cimAdmin.getDataSourceData().getName()).sendBoardKeys(Keys.ENTER);
	}

	public void addQualificationOnTiered(CIMAdmin cimAdmin) throws Exception {

		String tieredPath = cmbTxt.replace("OPTION", tiered);
		sfdcAcolyte.waitTillElementIsClickable(ddlTierSelect).click(ddlTierSelect)
				.waitTillElementIsVisible(By.xpath(tieredPath)).waitTillElementIsClickable(By.xpath(tieredPath))
				.click(By.xpath(tieredPath));
		sfdcAcolyte.click(ddlDataSource);
		sfdcAcolyte.sendTextKeys(cimAdmin.getDataSourceData().getName()).sendBoardKeys(Keys.ENTER);

	}

	public void moveToTemplateViaIdClick(String templateId) throws Exception {

		String lnkTemplateIdPath = lnkTemplateId.replace("OPTION", templateId);
		sfdcAcolyte.waitTillElementIsVisible(By.xpath(lnkTemplateIdPath));
		sfdcAcolyte.jsClick(By.xpath(lnkTemplateIdPath));
		sfdcAcolyte.waitTillElementIsVisible(lblDataSourceDetails).waitTillElementIsVisible(lblDataSourceDetails);
		templateEditURL = sfdcAcolyte.getCurrentURL().contains(templateId + "/view");
		sfdcAcolyte.waitTillElementIsVisible(lblDescriptionDetails);
	}

	public void moveToFormulaTab(String stepQualificationFormula, String stepBenefitFormula) throws Exception {

		sfdcAcolyte.waitTillElementIsClickable(lblFormulaTab).click(lblFormulaTab);
		lblStepQualificationFormulaPath = lnkFormulaTab.replace("OPTION", stepQualificationFormula);
		lblStepBenefitFormulaIdPath = lnkFormulaTab.replace("OPTION", stepBenefitFormula);
		sfdcAcolyte.waitTillElementIsVisible(By.xpath(lblStepQualificationFormulaPath));
		sfdcAcolyte.waitTillElementIsVisible(By.xpath(lblStepBenefitFormulaIdPath));
	}

	public void moveToEditTemplate(String templateId) throws Exception {
		templateEditURL = sfdcAcolyte.getCurrentURL().contains(templateId + "/Edit");
	}

}