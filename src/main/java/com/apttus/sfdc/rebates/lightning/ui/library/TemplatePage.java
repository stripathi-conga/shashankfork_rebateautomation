package com.apttus.sfdc.rebates.lightning.ui.library;

import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.apttus.selenium.NGHelper;
import com.apttus.sfdc.rebates.lightning.api.library.CIMAdmin;
import com.apttus.sfdc.rebates.lightning.common.GenericPage;

public class TemplatePage extends GenericPage  {

	@FindBy(css = "div[title='New']")
	public WebElement Newbtn;

	@FindBy(xpath = "//*[text()='Q&B Layout']//..//input[@placeholder='Select an Option']")
	public WebElement QBselct;

	@FindBy(xpath = "//*[text()='Tier']//..//input[@type='text']")
	public WebElement TierSelect;

	@FindBy(xpath = "//*[text()='Q&B Layout']//..//lightning-base-combobox-item[@data-value='Benefit Product']")
	public WebElement BenftPrdtValue;

	@FindBy(xpath = "//*[text()='Tier']//..//lightning-base-combobox-item[@data-value='Discrete']")
	public WebElement TierDiscrete;

	@FindBy(xpath = "//*[text()='Data Source']//..//input[@placeholder='Select an Option']")
	public WebElement ddldataSource;

	@FindBy(xpath = "//*[text()='Tier']//..//lightning-base-combobox-item[@data-value='Tiered']")
	public WebElement TierTiered;

	@FindBy(css = "span[class='slds-checkbox_faux']")
	public List<WebElement> chkqualification;

	@FindBy(css = "input[name='Qualification Formulas']")
	public WebElement lblqualification;

	@FindBy(css = "[name='Benefit Formulas']")
	public WebElement lblBenefitFormulas;

	@FindBy(xpath = "//b[text()='No data to display']")
	public List<WebElement> txtNodatadisplay;

	GenericPage genericPage;
	WebDriverWait wait;
	NGHelper nghelper;

	public TemplatePage(WebDriver driver) {
		super(driver);
		wait = new WebDriverWait(driver, 60);
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

	public TemplatePage qnbLayoutDefinition(WebElement QBselct, WebElement QBValue, WebElement TierSelect,
			WebElement TierValue) throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(QBselct).waitTillElementIsClickable(QBselct).click(QBselct)
				.waitTillElementIsVisible(BenftPrdtValue).waitTillElementIsClickable(BenftPrdtValue)
				.click(BenftPrdtValue).waitTillElementIsClickable(TierSelect).click(TierSelect)
				.waitTillElementIsVisible(TierValue).waitTillElementIsClickable(TierValue).click(TierValue);
		return PageFactory.initElements(driver, TemplatePage.class);
	}

	public void addQualificationOnDiscrete(CIMAdmin cimAdmin) throws Exception {

		sfdcAcolyte.waitTillElementIsVisible(ddldataSource).waitTillElementIsClickable(ddldataSource)
				.click(ddldataSource);
		sfdcAcolyte.sendTextKeys(cimAdmin.getDataSourceData().getName__c()).sendBoardKeys(Keys.ENTER);

	}

	public void addQualificationOnTiered(CIMAdmin cimAdmin) throws Exception {

		sfdcAcolyte.waitTillElementIsClickable(TierSelect).click(TierSelect).waitTillElementIsVisible(TierTiered)
				.waitTillElementIsClickable(TierTiered).click(TierTiered);
		sfdcAcolyte.click(ddldataSource);
		sfdcAcolyte.sendTextKeys(cimAdmin.getDataSourceData().getName__c()).sendBoardKeys(Keys.ENTER);
		for (int i = 0; i < chkqualification.size(); i++) {
			sfdcAcolyte.waitTillElementIsClickable(chkqualification.get(i)).jsClick(chkqualification.get(i));
		}

	}

}
