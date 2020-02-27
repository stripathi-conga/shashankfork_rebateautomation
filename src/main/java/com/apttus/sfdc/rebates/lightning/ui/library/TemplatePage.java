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
	public WebElement ddldataSource;

	@FindBy(css = "span[class='slds-checkbox_faux']")
	public List<WebElement> chkqualification;

	@FindBy(css = "input[name='Qualification Formulas']")
	public WebElement lblqualification;

	@FindBy(css = "[name='Benefit Formulas']")
	public WebElement lblBenefitFormulas;

	@FindBy(xpath = "//b[text()='No data to display']")
	public List<WebElement> txtNodatadisplay;

	String BenefitProduct = "Benefit Product";
	String Discrete = "Discrete";
	String Tiered = "Tiered";

	String cmbTxt = "//*[@data-value='OPTION']";
	String benefitproductpath = cmbTxt.replace("OPTION", BenefitProduct);
	String discretepath = cmbTxt.replace("OPTION", Discrete);
	String tieredpath = cmbTxt.replace("OPTION", Tiered);

	GenericPage genericPage;
	WebDriverWait wait;
	NGHelper nghelper;

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
		sfdcAcolyte.waitTillElementIsVisible(ddlQBselect).waitTillElementIsClickable(ddlQBselect).jsScroll(ddlQBselect)
				.click(ddlQBselect).waitTillElementIsVisible(By.xpath(benefitproductpath))
				.waitTillElementIsClickable(By.xpath(benefitproductpath)).click(By.xpath(benefitproductpath));
		sfdcAcolyte.waitTillElementIsClickable(ddlTierSelect).click(ddlTierSelect)
				.waitTillElementIsVisible(By.xpath(discretepath)).waitTillElementIsClickable(By.xpath(discretepath))
				.click(By.xpath(discretepath));
		return PageFactory.initElements(driver, TemplatePage.class);
	}

	public void addQualificationOnDiscrete(CIMAdmin cimAdmin) throws Exception {

		sfdcAcolyte.waitTillElementIsVisible(ddldataSource).waitTillElementIsClickable(ddldataSource)
				.click(ddldataSource);
		sfdcAcolyte.sendTextKeys(cimAdmin.getDataSourceData().getName__c()).sendBoardKeys(Keys.ENTER);

	}

	public void addQualificationOnTiered(CIMAdmin cimAdmin) throws Exception {

		sfdcAcolyte.waitTillElementIsClickable(ddlTierSelect).click(ddlTierSelect)
				.waitTillElementIsVisible(By.xpath(tieredpath)).waitTillElementIsClickable(By.xpath(tieredpath))
				.click(By.xpath(tieredpath));
		sfdcAcolyte.click(ddldataSource);
		sfdcAcolyte.sendTextKeys(cimAdmin.getDataSourceData().getName__c()).sendBoardKeys(Keys.ENTER);
		for (int i = 0; i < chkqualification.size(); i++) {
			sfdcAcolyte.waitTillElementIsClickable(chkqualification.get(i)).jsClick(chkqualification.get(i));
		}

	}

}