package com.apttus.sfdc.rebates.lightning.common;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.apttus.ui.fundamentals.Acolyte;

public class GenericPage {

	public WebDriver driver;
	public Acolyte sfdcAcolyte;

	@FindBy(xpath = "//span[text()='Recently Viewed']")
	public WebElement lnkRecentlyViewed;

	@FindBy(xpath = "//span[text()='All']")
	public WebElement lnkAllViewed;

	@FindBy(xpath = "//span[@class='slds-truncate uiOutputText'][contains(text(),'DSrcAutomation')]")
	public WebElement txtColumnName;

	@FindBy(xpath = "//span[@title='Name']")
	public WebElement titleNameColumn;

	@FindBy(xpath = "//*[@data-key='filterList']")
	public WebElement filterIcon;

	@FindBy(xpath = "//a[text()='Add Filter']")
	public WebElement lnkAddFilter;

	@FindBy(css = "input[placeholder='Select an Option'][input]")
	public WebElement ddlSelectField;

	@FindBy(css = "div:nth-child(2) > lightning-combobox > div")
	public WebElement ddlSelectOperator;

	@FindBy(css = "input[data-aura-class*='uiInput uiInputText ui']")
	public WebElement txtEnterValue;

	@FindBy(css = "button[class$='doneButton uiButton']>span[dir='ltr']")
	public WebElement btnFilterDone;

	@FindBy(xpath = "//*[text()='List view updated.']")
	public WebElement filterResponse;

	@FindBy(css = "button[class$='saveButton headerButton']")
	public WebElement btnFilterSave;

	@FindBy(xpath = "//*[@id=\"brandBand_1\"]//..//span/div/a/lightning-icon/lightning-primitive-icon")
	public WebElement btnshowMore;

	@FindBy(xpath = "//a[@title='Delete']")
	public WebElement btnShowDeleteAction;

	@FindBy(xpath = "//button[@title='Delete']")
	public WebElement btnConfirmDeleteAction;

	@FindBy(css = "[class='removeAll']")
	public WebElement removeAllfilter;
	
	@FindBy(css = "button[title='Close']")
	public WebElement closeToastResponse;

	public int waitTime = 40;
	public String fieldLabel = ".labelCol";

	public GenericPage(WebDriver driver) {

		this.driver = driver;
		sfdcAcolyte = new Acolyte(driver);
		sfdcAcolyte.setWaitTime(60);
		PageFactory.initElements(driver, this);
	}

	public boolean checkVisibility(WebElement element) throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(element);
		return sfdcAcolyte.checkVisibility(element);
	}

	public boolean checkInVisibility(WebElement element) throws Exception {
		boolean isExists = false;
		try {
			isExists = sfdcAcolyte.checkVisibility(element);
			return isExists;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public void doubleClick(WebElement element) throws Exception {
		Actions builder = new Actions(driver);
		sfdcAcolyte.waitTillElementIsVisible(element);
		builder.doubleClick(element).build().perform();
		sfdcAcolyte.waitTillElementIsVisible(element);
	}

	public void refreshPage() throws Exception {
		sfdcAcolyte.refreshPage();
	}

	public GenericPage checkInnerTextMatches(WebElement element, String value) throws Exception {
		sfdcAcolyte.waitTillInnerTextMatches(element, value);
		return PageFactory.initElements(driver, GenericPage.class);
	}

	public GenericPage switchToFrame(WebElement btn, WebElement frame) throws Exception {
		boolean checkCond = false;
		Instant start = Instant.now();
		do {
			Instant now = Instant.now();
			if (Duration.between(start, now).toMinutes() >= 1) {
				checkCond = true;
				break;
			}
			sfdcAcolyte.waitTillFrameIsAvailableAndSwitch(frame);
			try {
				if (btn.isDisplayed() == true)
					checkCond = true;
				break;
			} catch (NoSuchElementException e) {
				checkCond = false;
			}
		} while (checkCond == false);
		return PageFactory.initElements(driver, GenericPage.class);
	}

	public String getCurrentUrl() throws Exception {
		return sfdcAcolyte.getCurrentURL();
	}

	public GenericPage selectComboByValue(WebElement element, String fieldLabel) throws Exception {
		sfdcAcolyte.selectComboByValue(element, fieldLabel);
		return PageFactory.initElements(driver, GenericPage.class);
	}

	public boolean actionButtonEnabled(List<WebElement> buttonList) throws Exception {
		boolean flag = true;
		for (WebElement button : buttonList) {
			if (!sfdcAcolyte.checkEnabled(button)) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	public Select getObjOfDropDownElement(WebElement element) throws Exception {
		return new Select(element);
	}

	public List<String> getTextFromWebElementList(List<WebElement> element) throws Exception {
		List<String> txtList = new ArrayList<String>();
		for (WebElement e : element) {
			txtList.add(e.getText());
		}
		return txtList;
	}

	public String getDropdownSelectedTxt(WebElement element) throws Exception {
		return getObjOfDropDownElement(element).getFirstSelectedOption().getText();
	}

	public GenericPage selectComboByText(WebElement element, String ddlValue) throws Exception {
		sfdcAcolyte.selectComboByText(element, ddlValue);
		return PageFactory.initElements(driver, GenericPage.class);
	}

	public String getStrValueFromWebEleList(List<WebElement> elementList, String expectedValue) {
		String text = null;
		for (WebElement element : elementList) {
			if (element.getText().contains(expectedValue)) {
				text = element.getText();
				break;
			}
		}
		return text;
	}

	public GenericPage getWindow(int windowNum) throws Exception {
		Set<String> handles = sfdcAcolyte.getWindowHandles();
		ArrayList<String> lstHandles = new ArrayList<String>();
		lstHandles.addAll(handles);
		sfdcAcolyte.switchToWindow(lstHandles.get(windowNum));
		return PageFactory.initElements(driver, GenericPage.class);
	}

	public GenericPage checkAndClickOnBtn(List<WebElement> elementList) {
		while (elementList.size() > 0) {
			elementList.get(0).click();
			break;
		}
		return PageFactory.initElements(driver, GenericPage.class);
	}

	public boolean isElementExists(String element) throws Exception {
		return (sfdcAcolyte.findTheElements(By.cssSelector(element)).size() != 0);
	}

	public void checkAllChckBox(List<WebElement> chkReviewers) {
		for (WebElement ele : chkReviewers) {
			if (!ele.isSelected())
				ele.click();
		}
	}

	public GenericPage waitTillAttributeContains(WebElement element, String attribute, String value) throws Exception {
		sfdcAcolyte.waitTillAttributeContains(element, attribute, value);
		return PageFactory.initElements(driver, GenericPage.class);
	}

	public void navigatBack(WebElement element) throws Exception {
		driver.navigate().back();
		sfdcAcolyte.waitTillElementIsVisible(element);

	}

	public GenericPage searchName(String ColumnName, String ColumnOperator, String FilterValue) throws Exception {

		sfdcAcolyte.click(lnkRecentlyViewed).waitTillElementIsVisible(lnkAllViewed).click(lnkAllViewed);
		sfdcAcolyte.waitTillElementIsVisible(filterIcon).click(filterIcon);
		sfdcAcolyte.waitTillElementIsVisible(lnkAddFilter).click(lnkAddFilter);
		sfdcAcolyte.waitTillElementIsVisible(ddlSelectField).click(ddlSelectField)
				.sendKeysTo(ddlSelectField, ColumnName).sendBoardKeys(Keys.ENTER);

		sfdcAcolyte.waitTillElementIsVisible(ddlSelectOperator).click(ddlSelectOperator)
				.sendKeysTo(ddlSelectOperator, ColumnOperator).sendBoardKeys(Keys.ENTER)
				.sendKeysTo(txtEnterValue, FilterValue).click(btnFilterDone).click(btnFilterSave);
		sfdcAcolyte.waitTillElementIsVisible(filterResponse);
		return PageFactory.initElements(driver, GenericPage.class);
	}

	public GenericPage deleteName() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(btnshowMore).jsClick(btnshowMore);
		sfdcAcolyte.jsClick(btnShowDeleteAction).jsClick(btnConfirmDeleteAction);
		return PageFactory.initElements(driver, GenericPage.class);

	}

	public GenericPage deleteRecord() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(removeAllfilter).click(removeAllfilter).click(btnFilterSave);
		return PageFactory.initElements(driver, GenericPage.class);
	}

	public void closeToastMessage() throws Exception {
		sfdcAcolyte.waitTillElementIsClickable(closeToastResponse).click(closeToastResponse);
	}

	public void waitTillStaleElementToBeVisible(WebElement ele) {
		new WebDriverWait(driver, waitTime).until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(ele)));
	}

}
