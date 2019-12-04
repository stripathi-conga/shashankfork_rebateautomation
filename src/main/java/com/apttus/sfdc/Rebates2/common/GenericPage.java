package com.apttus.sfdc.Rebates2.common;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.apttus.ui.fundamentals.WebElementBuilder;


public class GenericPage extends StartUpPage {
	
	@FindBy(xpath="//span[text()='Recently Viewed']")
	public WebElement recentlyviewedlnk;
    
    @FindBy(xpath="//span[text()='All']")
	public WebElement allviewedlnk;
    
    @FindBy(xpath="//span[@class='slds-truncate uiOutputText'][contains(text(),'DSrcAutomation')]")
   	public WebElement namecolmn;
    
    @FindBy(xpath="//span[@title='Name']")
   	public WebElement titleNamecolmn;
    
    @FindBy(xpath="//*[@data-key='filterList']")
   	public WebElement filtericon;
    
    @FindBy(xpath="//a[text()='Add Filter']")
   	public WebElement addFilterlnk;
    
	@FindBy(css=".labelCol label")
	public List<WebElement> agrFieldLbl;
	
	@FindBy(id = "findInput")
	public WebElement searchBoxInPdf;
	
	@FindBy(id = "clearText")
	public WebElement clearSearchText;
	
	@FindBy(css = ".highlight")
	public List<WebElement> searchResult;
	
	@FindBy(id = "findHighlightAll")
	public WebElement chekboxHighLightAll;
	
	@FindBy(id = "findNext")
	public WebElement btnfindNextInPDF;

	@FindBy(id = "findResultsCount")
	public WebElement resultCount;
	
	@FindBy(css = ".textLayer div")
	public WebElement textToSelect;
	
	@FindBy(id = "viewerContainer")
	public WebElement pdfViewerContainer;
	
	@FindBy(id = "numPages")
	public WebElement numPages;
	
	@FindBy(css = ".clauseCls span")
	public WebElement txtClause;
	
	@FindBy(css = "[id*='ClauseDropdownId']")
	@CacheLookup
	public WebElement dropDownClause;
	
	@FindBy(css = "[id*='ClauseChangeSection']")
	@CacheLookup
	public WebElement dpnClause;
	
	@FindBy(id = "addClauseId")
	public WebElement btnAddClause;
	
	@FindBy(css = "[value='New']+label")
	@CacheLookup
	public WebElement lblNewClause;

	@FindBy(css = "[value='Select']+label")
	@CacheLookup
	public WebElement lblSelectClause;
	
	@FindBy(css="input[placeholder='Select an Option'][input]")
	public WebElement selectField;
	        
	@FindBy(css="div:nth-child(2) > lightning-combobox > div")
	public WebElement selectOperator;
	
	@FindBy(css="input[data-aura-class*='uiInput uiInputText ui']") 
    public WebElement enterValue;
    
    @FindBy(css="button[class$='doneButton uiButton']>span[dir='ltr']")
   	public WebElement filterDonebtn;
    
    @FindBy(xpath="//*[text()='List view updated.']")
   	public WebElement filterResponse;
  
    @FindBy(css="button[class$='saveButton headerButton']")
   	public WebElement filterSavebtn;
    
    @FindBy(xpath="//*[@id=\"brandBand_1\"]//..//span/div/a/lightning-icon/lightning-primitive-icon")
	public  WebElement showMore;
   
    @FindBy(xpath = "//a[@title='Delete']")
	public WebElement showDeleteAction;
	
	@FindBy(xpath = "//button[@title='Delete']")
	public WebElement confirmDeleteAction;
	
	@FindBy(css="[class='removeAll']")
   	public WebElement removeAllfilter;
	@FindBy(css="button[title='Close']")
   	public WebElement closeToastResponse;
	
	public int waitTime = 40;
	public String fieldLabel = ".labelCol";

	public GenericPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		sfdcAcolyte.setWaitTime(60);
	}	
	
	
	public boolean checkVisibility(WebElement element) throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(element);
		return sfdcAcolyte.checkVisibility(element);
	}
	
	public boolean checkInVisibility(WebElement element) throws Exception {
		boolean isExists=false;
		try {
			isExists= sfdcAcolyte.checkVisibility(element);
			return isExists;
		}catch(NoSuchElementException e) {
			return false;
		}
	}
	
	
	
	public void doubleClick(WebElement element) throws Exception {
		Actions builder = new Actions(driver);
		sfdcAcolyte.waitTillElementIsVisible(element);
		builder.doubleClick(element).build().perform();
		sfdcAcolyte.waitTillElementIsVisible(element);
	}
	
	
	/**
	 * To refresh Page
	 * @return
	 * @throws Exception
	 */
	public void refreshPage() throws Exception {
		sfdcAcolyte.refreshPage();
	}
	
	
     public WebElement inputAutoCompleteFieldwithLabel(String label) throws Exception {
	    return (new WebElementBuilder(this.driver))
	                 .getElementWithCSSText("label", label)
	                 .getParentElement()
	                 .getFirstChildElement()
	                 .getNextSiblingElement()
	                 .getElementWithCSS("input").getWebElement();
     }
     /**
 	 * To clear given Field
 	 * param WebElement   
 	 * @return
 	 * @throws Exception
 	 */	
 	public void clearField(WebElement eleToClear) throws Exception
 	{	
 		sfdcAcolyte.waitTillElementIsVisible(eleToClear);
 		eleToClear.sendKeys(Keys.CONTROL + "a");
 		eleToClear.sendKeys(Keys.DELETE);
 	}	
     
 	/*
 	 * @param String Value
 	 * @return WebElement
 	 * @throws Exception
 	 */
 	public WebElement getTextFieldElementWithLabel(String fieldLabel,String value) throws Exception {
 		WebElement element=new WebElementBuilder(driver).getElementContainingText(agrFieldLbl, fieldLabel)
 				.getParentElement()
 				.getNextSiblingElement()
 				.getElementWithCSS(value)
 				.getWebElement();
 		return element;   
 	}
 	
 	/**
 	 * To delete the temp directory created
 	 * @throws IOException
 	 */
 	public void deleteTempDirectory() throws IOException {
 		File tempDirectory = new File(System.getProperty("user.dir") + File.separator + "temp" + File.separator);;
 		if (tempDirectory.exists()) 
 			FileUtils.deleteDirectory(tempDirectory); 
 	}
 	/**
	 * To split string with required parameter of given index
	 * @param String textString
	 * @param String splitText 
	 * @param Integer index
 	 * @return String
	 * @throws Exception
	 */
	public String splitString(String textString, String splitText, int index) throws Exception {
		return textString.split(splitText)[index].trim();
	}

    
    /**
     * Update dropdown values
     * @param  field name, field value
     * @return webElement
     * @throws Exception
     */
     public void updateAgrmtDDFieldValues(String fieldName, String fieldValue) throws Exception {
         sfdcAcolyte.selectComboByText(sfdcAcolyte.getComboboxElementWithLabel(fieldName), fieldValue);
     }
     
     /**
   	 * Verify Inner Text Matches On Page
   	 * @throws Exception
   	 */
   	public GenericPage checkInnerTextMatches(WebElement element, String value) throws Exception {
   		sfdcAcolyte.waitTillInnerTextMatches(element, value);
   		return PageFactory.initElements(driver, GenericPage.class);
   	}

    /**
 	 * Get active action panel buttons
 	 * @param  lstElement, frameElement
 	 * @return action panel buttons displayed
 	 * @throws Exception
 	 */
 	public List<String> getActiveActionPanelBtn(List<WebElement> lstElement, WebElement frameElement) throws Exception {
 		sfdcAcolyte.waitTillFrameIsAvailableAndSwitch(frameElement);
 		List<String> activeActionPanelBtn = new ArrayList<String>();
 		for (WebElement actionBtn : lstElement) {
 			if (actionBtn.isDisplayed())
 				activeActionPanelBtn.add(actionBtn.getText());
 		}
 		sfdcAcolyte.switchToDefaultContent();
 		return activeActionPanelBtn;
 	}
 	
 	public int getWidthValue(String sizeAfterZoomOut, Map<String, String> testData) throws NumberFormatException, Exception {
		return Integer.parseInt(splitString(
				splitString(splitString(sizeAfterZoomOut, testData.get("SplitTextSemiCol"),
						Integer.parseInt(testData.get("SplitIndexZero"))), testData.get("SplitTextCol"), Integer.parseInt(testData.get("SplitIndexOne"))),
				testData.get("SplitText"), Integer.parseInt(testData.get("SplitIndexZero"))));
	}
 	
 	/**
	 * This method will search text in PDF and return matching text from pdf.
	 * 
	 * @param textToSearch
	 * @return
	 * @throws Exception
	 */
	public String searchTextInPdf(String textToSearch) throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(pdfViewerContainer);
		sfdcAcolyte.waitTillElementIsVisible(numPages);
		sfdcAcolyte.waitTillElementIsVisible(textToSelect).waitTillElementIsClickable(clearSearchText)
				.click(clearSearchText);
		sfdcAcolyte.waitTillElementIsVisible(searchBoxInPdf).sendKeysTo(searchBoxInPdf, textToSearch)
				.sendBoardKeys(Keys.ENTER);
		if (!chekboxHighLightAll.isDisplayed()) {
			sfdcAcolyte.click(searchBoxInPdf);
			sfdcAcolyte.waitTillElementIsClickable(chekboxHighLightAll);
			chekboxHighLightAll.click();
		} else {
			sfdcAcolyte.waitTillElementIsClickable(chekboxHighLightAll);
			chekboxHighLightAll.click();
		}
		sfdcAcolyte.waitTillElementIsVisible(btnfindNextInPDF);
		sfdcAcolyte.waitTillElementIsVisible(textToSelect);
		sfdcAcolyte.waitTillElementIsVisible(resultCount);

		return resultCount.getText();
	}
	

	
	
	
	/**
	 * Verify Activity History with OR condition
	 * @return page
	 * @throws Exception
	 */
	public boolean verifyActivityStatus(String activity, String statusCompleted, String esignedStatus)
			throws Exception {
		boolean status = false;
		if ((activity.contains(statusCompleted)) || (activity.contains(esignedStatus))) {
			status = true;
		}
		return status;
	}
	
	/**
	 * Get attribute value
	 * 
	 * @param element
	 * @return Attribute Value.
	 */

	public String getAttributeValue(WebElement field, String attributeName) throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(field);
		return field.getAttribute(attributeName);
	}

	/**
	 * Select text from pdf.
	 * @param testData 
	 * @throws Exception
	 */
	public void selectElement(Map<String, String> testData) throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(pdfViewerContainer);
		sfdcAcolyte.waitTillElementIsVisible(numPages);
		waitForLoadPdf(numPages,testData.get("PageCount"));
		sfdcAcolyte.waitTillElementIsVisible(textToSelect);
		Actions action = new Actions(driver);
		action.doubleClick(textToSelect).perform();
		action.contextClick(textToSelect).perform();
	}

	/**
	 * Select clause from drop-down on review screen.
	 * @param clauseName
	 * @throws Exception
	 */
	public void selectClauseDropDown(String clauseName) throws Exception {
		sfdcAcolyte.waitTillElementIsClickable(dpnClause)
		.click(dpnClause);
		Select select = new Select(dropDownClause);
		select.selectByVisibleText(clauseName);
	}
	
	/**
	 * Wait for PDF to load.
	 * @param pageCount 
	 * @param numPages 
	 * @throws Exception
	 */
	public void waitForLoadPdf(WebElement numPages, String pageCount) throws Exception {
		boolean checkCond = false;
		Instant start = Instant.now();
		Instant now;
		do {
			now = Instant.now();
			if(Duration.between(start, now).toMillis() >= 10000) {
				checkCond = true;
				break;
			}
			if(numPages.getText().contains(pageCount)) {
				checkCond = true;
				break;
			}
			sfdcAcolyte.waitTillElementIsVisible(pdfViewerContainer);
			sfdcAcolyte.waitTillElementIsVisible(numPages);
		} while (checkCond==false);
	}

	/**
	 * Check Record type
	 * @param  String value and Array
	 * @return boolean
	 * @throws Exception
	 */
	public boolean checkRecordType(String arrayRecType[], String recordTypeName) throws Exception{
		ArrayList<String> recordType = new ArrayList<String>(Arrays.asList(arrayRecType));
		return recordType.contains(recordTypeName)?true : false;
	}

	/**
	 * @param URL
	 * @param uniqueId
	 * @param element
	 * @throws Exception
	 */
	public void navigateURL(String URL,String uniqueId, WebElement element) throws Exception {
		sfdcAcolyte.navigateTo(URL+uniqueId);
		sfdcAcolyte.waitTillElementIsVisible(element);
	}
	
	/**
 	 * Method to get the text of list of elements
 	 * @param List<WebElement> lstFieldName   
 	 * @return list of text of WebElement
 	 * @throws Exception
 	 */	
 	public List<String> getTextOfListOfElements(List<WebElement> lstFieldName) throws Exception {
 		List<String> lstOfFiles = new ArrayList<String>();
 		for(WebElement ele:lstFieldName )	
 			lstOfFiles.add(ele.getText().trim());
 		return lstOfFiles;
 	}
 	
 	/**
	 * Get Title's text from element
	 * 
	 * @param Web Element
	 * @return
	 * @throws Exception
	 */
	public List<String> getListOfTitlesForColumn(List<WebElement> lstFieldName, String attributeName) throws Exception {
		List<String> lstOfColtxt = new ArrayList<String>();
		for (WebElement ele : lstFieldName)
			lstOfColtxt.add(ele.getAttribute(attributeName));
		return lstOfColtxt;
	}
	
	/**
	 * To check element is present.
	 * @param WebElement btn
	 * @param WebElement frame
	 * @return GenericPage
	 * @throws Exception
	 */	
	public GenericPage switchToFrame(WebElement btn, WebElement frame) throws Exception {
		boolean checkCond = false;
		Instant start = Instant.now();
		do{	
			Instant now = Instant.now();
			if (Duration.between(start, now).toMinutes() >= 1) {
				checkCond = true;
				break;
			}
			sfdcAcolyte.waitTillFrameIsAvailableAndSwitch(frame);
			try {if(btn.isDisplayed()==true)
				checkCond=true;
			break;
			} catch (NoSuchElementException e) {
				checkCond = false;
			}
		}while(checkCond==false);
		return PageFactory.initElements(driver, GenericPage.class);
	}
	
	/**
	 * This method will return Current URL on browser.
	 * @return
	 * @throws Exception
	 */
	public String getCurrentUrl() throws Exception {
		return sfdcAcolyte.getCurrentURL();
	}

	/**
	 * Select Combo box value
	 * @param fieldLabel
	 * @return
	 * @throws Exception
	 */
	public GenericPage selectComboByValue(WebElement element, String fieldLabel) throws Exception {
		sfdcAcolyte.selectComboByValue(element, fieldLabel);
		return PageFactory.initElements(driver, GenericPage.class);
	}
	
	/**
	 * Check File exists or not
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public boolean checkFileExists(String fileName) throws IOException {
		boolean checkCond = false;
		Instant start = Instant.now();
		do {
			Instant now = Instant.now();
			if (Duration.between(start, now).toMillis() >= 25) {
				break;
			}
			if (new File(fileName).exists()) {
				checkCond = true;
				break;
			} 
		} while(checkCond==false);
		return checkCond;
	}
	/**
	 * To verify action button Enabled
	 * @param List
	 * all Button
	 * @throws Exception
	 */
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

	/**
	 * To get the object of drop down list
	 * @param element
	 * @return
	 * @throws Exception
	 */
	public Select getObjOfDropDownElement(WebElement element) throws Exception {
		return new Select(element);
	}

	/**
	 * Get text list from WebElement
	 * @param element
	 * @return List
	 * @throws Exception
	 */
	public List<String> getTextFromWebElementList(List<WebElement> element) throws Exception {
		List<String> txtList = new ArrayList<String>();
		for (WebElement e : element) {
			txtList.add(e.getText());
		}
		return txtList;
	}

	/**
	 * Get First selected text of DropDown list
	 * @param element
	 * @return
	 * @throws Exception
	 */
	public String getDropdownSelectedTxt(WebElement element) throws Exception {
		return getObjOfDropDownElement(element).getFirstSelectedOption().getText();
	}

	/**
	 * Select Combo box by text
	 * @param fieldLabel
	 * @return GenericPage
	 * @throws Exception
	 */
	public GenericPage selectComboByText(WebElement element, String ddlValue) throws Exception {
		sfdcAcolyte.selectComboByText(element, ddlValue);
		return PageFactory.initElements(driver, GenericPage.class);
	}

	/**
	 * To get String text from WebElement List
	 * @param elementList
	 * @param expectedValue
	 * @return
	 */
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

	/**
	 * Get any Window
	 * @param String value
	 * @return
	 * @throws Exception
	 
	 */
	public GenericPage getWindow(int windowNum) throws Exception {
		Set<String> handles = sfdcAcolyte.getWindowHandles();
		ArrayList<String> lstHandles = new ArrayList<String>();
		lstHandles.addAll(handles);
		sfdcAcolyte.switchToWindow(lstHandles.get(windowNum));
		return PageFactory.initElements(driver, GenericPage.class);
	}

	/**
	* Check the element and Click on it
	* @return
	*/
	public GenericPage checkAndClickOnBtn(List<WebElement> elementList) {
		while (elementList.size() > 0) {
			elementList.get(0).click();
			break;
		}
		return PageFactory.initElements(driver, GenericPage.class);
	}
	
	/**
	* Check element is exists or not.
	* @param element Name as a String.
	* @return true or False
	* @throws Exception
	*/
	public boolean isElementExists(String element) throws Exception {
		return (sfdcAcolyte.findTheElements(By.cssSelector(element)).size() != 0);   
	}
	
	/**
	 * This method will check all CheckBoxes with same locator.
	 * @param chkReviewers
	 * @return GenericPage
	 */
	public void checkAllChckBox(List<WebElement> chkReviewers) {
		for (WebElement ele : chkReviewers) {
			if (!ele.isSelected())
				ele.click();
		}
	}
	
	/**
	* Wait till attribute contains the value
	* @param WebElement
	* @param Atrribute
	* @param Value
	* @return 
	* @throws Exception
	*/
	public GenericPage waitTillAttributeContains(WebElement element, String attribute, String value) throws Exception {
	sfdcAcolyte.waitTillAttributeContains(element, attribute, value);
	return PageFactory.initElements(driver, GenericPage.class);
	}
	

	/**
	 * Navigate to Previous page
	 * @param WebElement
	 * @throws Exception
	 * @return 
	 */
	public void navigatBack(WebElement element) throws Exception {
		driver.navigate().back();
		sfdcAcolyte.waitTillElementIsVisible(element);

	}
		
	
    public GenericPage SFDCSearchName(String ColumnName, String ColumnOperator, String FilterValue) throws Exception {
    	
    	sfdcAcolyte.click(recentlyviewedlnk).
		waitTillElementIsVisible(allviewedlnk).click(allviewedlnk);
		sfdcAcolyte.waitTillElementIsVisible(filtericon).click(filtericon);
		sfdcAcolyte.waitTillElementIsVisible(addFilterlnk).click(addFilterlnk);	
        sfdcAcolyte.waitTillElementIsVisible(selectField).click(selectField).sendKeysTo(selectField, ColumnName).sendBoardKeys(Keys.ENTER);
		
		sfdcAcolyte.waitTillElementIsVisible(selectOperator).click(selectOperator).sendKeysTo(selectOperator, ColumnOperator).sendBoardKeys(Keys.ENTER).
        sendKeysTo(enterValue, FilterValue).click(filterDonebtn).click(filterSavebtn);
	    sfdcAcolyte.waitTillElementIsVisible(filterResponse);
	    return PageFactory.initElements(driver, GenericPage.class);
    }


	public GenericPage deleteName() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(showMore).
	      jsClick(showMore);
	      Thread.sleep(4000);
	      sfdcAcolyte.jsClick(showDeleteAction).
	      jsClick(confirmDeleteAction);
	      return PageFactory.initElements(driver, GenericPage.class);
		
	}

	public GenericPage recordDelete() throws Exception {
		sfdcAcolyte.waitTillElementIsVisible(removeAllfilter).click(removeAllfilter).click(filterSavebtn);
		return PageFactory.initElements(driver, GenericPage.class);
	}


	public GenericPage closeToastMessage() throws Exception {
		sfdcAcolyte.waitTillElementIsClickable(closeToastResponse).click(closeToastResponse);
		
		return PageFactory.initElements(driver, GenericPage.class);
	}   
	public void waitTillStaleElementToBeVisible(WebElement ele) {
 		new WebDriverWait(driver, waitTime)
 		.until(ExpectedConditions.refreshed(
 				ExpectedConditions.visibilityOf(ele)));
 	}
        

}
