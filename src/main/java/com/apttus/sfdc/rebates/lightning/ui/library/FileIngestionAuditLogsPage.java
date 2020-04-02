package com.apttus.sfdc.rebates.lightning.ui.library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.apttus.sfdc.rebates.lightning.common.GenericPage;
import com.apttus.sfdc.rebates.lightning.generic.utils.RebatesConstants;

public class FileIngestionAuditLogsPage extends GenericPage {

	@CacheLookup
	@FindBy(xpath = "//table//th/div[@class='slds-cell-fixed']/a/span[@class='slds-truncate']")
	public List<WebElement> tblNonSortableHeader;

	public String tblSortableHeader = "(//table//th/div[@class='slds-cell-fixed']/div/span[@class='slds-truncate'])[INDEX]";
	public String tblAuditLogData = "//table[contains(@class,'slds-table')]/tbody/tr[ROW]/td[COLUMN]";
	public WebDriverWait wait;
	
	public FileIngestionAuditLogsPage(WebDriver driver) {
		super(driver);
		wait = new WebDriverWait(driver, 120);
		PageFactory.initElements(driver, this);
	}

	public boolean verifyHeaders(List<String> headerData) throws Exception {
		waitTillPageContentLoad(RebatesConstants.waitFor20Sec); 
		List<String> headerValues = new ArrayList<String>();
		int i = 2;
		for (WebElement e : tblNonSortableHeader) {
			sfdcAcolyte.waitTillElementIsVisible(e);
			headerValues.add(e.getText());
			headerValues.add(driver.findElement(By.xpath(tblSortableHeader.replace("INDEX", Integer.toString(i)))).getText());
			i++;
		}
		Collections.sort(headerData);
		Collections.sort(headerValues);
		boolean verifyHeader = headerData.equals(headerValues);		
		return verifyHeader;
	}
	
	public boolean getGridViewData() throws Exception {
		boolean compareResult = false;
		for (int i = 1; i <= 5; i++) {
			String row = tblAuditLogData.replace("ROW", Integer.toString(i));
			compareResult = verifyDataForEachRow(row);
		}
		return compareResult;
	}

	public boolean verifyDataForEachRow(String rowData) throws Exception {
		boolean dataVisibility = false;
		for (int i = 1; i <= 6; i++) {
			if (sfdcAcolyte
					.waitTillElementIsVisible(By.xpath(rowData.replace("COLUMN", Integer.toString(i)))) != null) {
				dataVisibility = true;
			} else {
				dataVisibility = false;
				break;
			}
		}
		return dataVisibility;
	}
}
