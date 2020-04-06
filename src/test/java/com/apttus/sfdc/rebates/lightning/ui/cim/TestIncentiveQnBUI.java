package com.apttus.sfdc.rebates.lightning.ui.cim;

import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.apttus.helpers.Efficacies;
import com.apttus.selenium.WebDriverUtils;
import com.apttus.sfdc.rebates.lightning.api.library.BenefitProductQnB;
import com.apttus.sfdc.rebates.lightning.api.library.CIM;
import com.apttus.sfdc.rebates.lightning.common.GenericPage;
import com.apttus.sfdc.rebates.lightning.generic.utils.CIMHelper;
import com.apttus.sfdc.rebates.lightning.generic.utils.DataHelper;
import com.apttus.sfdc.rebates.lightning.generic.utils.RebatesConstants;
import com.apttus.sfdc.rebates.lightning.generic.utils.SFDCHelper;
import com.apttus.sfdc.rebates.lightning.main.UnifiedFramework;
import com.apttus.sfdc.rebates.lightning.ui.library.HomePage;
import com.apttus.sfdc.rebates.lightning.ui.library.IncentivePage;
import com.apttus.sfdc.rebates.lightning.ui.library.LoginPage;
import com.apttus.sfdc.rudiments.utils.SFDCRestUtils;

public class TestIncentiveQnBUI extends UnifiedFramework {
	private Properties configProperties;
	private Efficacies efficacies;
	private SFDCRestUtils sfdcRestUtils;
	private String instanceURL;
	private CIMHelper cimHelper;
	public CIM cim;
	private Map<String, String> jsonData;
	private BenefitProductQnB benefitProductQnB;
	public String calcFormulaIdBenefitTiered, calcFormulaIdQualificationTiered;
	public IncentivePage incentivepage;
	WebDriver driver;
	LoginPage loginPage;
	public String baseUIURL;
	public Map<String, String> testData;
	public HomePage homepage;
	SoftAssert softassert;
	GenericPage genericPage;

	@BeforeClass(alwaysRun = true)
	@Parameters({ "runParallel", "environment", "browser", "hubURL" })
	public void beforeClass(String runParallel, String environment, String browser, String hubURL) throws Exception {
		efficacies = new Efficacies();
		sfdcRestUtils = new SFDCRestUtils();
		WebDriverUtils utils = new WebDriverUtils();
		utils.initializeDriver(browser, hubURL);
		driver = utils.getDriver();
		configProperties = efficacies.loadPropertyFile(environment);
		loginPage = new LoginPage(driver);
		loginPage = loginPage.navigateToLoginPage(configProperties.getProperty("LoginURL"));
		loginPage.waitForLoginPageLoad().loginToApp(configProperties.getProperty("LoginUser"),
				configProperties.getProperty("LoginPassword"));
		homepage = new HomePage(driver, configProperties);
		homepage.navigateToCIM();
		SFDCHelper.setMasterProperty(configProperties);
		instanceURL = SFDCHelper.setAccessToken(sfdcRestUtils);
		cim = new CIM(instanceURL, sfdcRestUtils);
		cimHelper = new CIMHelper();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeClass() throws Exception {
		benefitProductQnB = new BenefitProductQnB(instanceURL, sfdcRestUtils);
		softassert = new SoftAssert();
		genericPage = new GenericPage(driver);
	}

	@Test(description = "TC-525 Validation fields available for Qualification And Benefits-Add Product", groups = {
			"Regression", "UI", "Low" })
	public void VerifyQnBFields() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json",
				"createIncentiveIndividualParticipantBenefitProductTiered");
		cimHelper.addAndValidateIncentive(jsonData, DataHelper.getIncentiveTemplateIdBenefitProductTiered(),
				benefitProductQnB);

		cimHelper.addAndValidateQnBOnIncentive(benefitProductQnB, "XXTBenefitProduct");

		incentivepage = homepage.navigateToIncentiveEdit(benefitProductQnB.getIncentiveData().incentiveId);
		incentivepage.waitTillAllQnBElementLoad();

		softassert.assertEquals(true, incentivepage.colValueAliasName.isDisplayed());
		softassert.assertEquals(true, incentivepage.colValueDate.get(0).isDisplayed());
		softassert.assertEquals(true, incentivepage.colValueDate.get(1).isDisplayed());
		softassert.assertEquals(true, incentivepage.colValueNameCodeType.get(0).isDisplayed());
		softassert.assertEquals(true, incentivepage.colValueNameCodeType.get(1).isDisplayed());
		softassert.assertAll();
	}

	@Test(description = "TC 539 Verify for the mandatory fields and tier values on the Benefit only and Tiered Incentive", groups = {
			"Regression", "UI", "Medium" })
	public void VerifyBenefitTieredIncentiveQnBMandatoryFieldValidation() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json",
				"createIncentiveIndividualParticipantBenefitProductTiered");
		cimHelper.addAndValidateIncentive(jsonData, DataHelper.getIncentiveTemplateIdBenefitProductTiered(),
				benefitProductQnB);
		cimHelper.addAndValidateQnBOnIncentive(benefitProductQnB, "XXTBenefitProduct");

		incentivepage = homepage.navigateToIncentiveEdit(benefitProductQnB.getIncentiveData().incentiveId);
		incentivepage.waitTillAllQnBElementLoad();
		jsonData = efficacies.readJsonElement("CIMIncentiveQnBData.json", "tiersIncrementalOrder");
		incentivepage.addProductWithMultipleTiers(jsonData.get("productName"), jsonData.get("tierCount"));
		incentivepage.addFormula(incentivepage.gridFormula, incentivepage.ddlQualificationFormulaEdit,
				incentivepage.ddlQualificationFormula, incentivepage.ddlFormulaValue);
		incentivepage.addTiers(incentivepage.gridT1, incentivepage.pencilEditTierT1T2T3.get(0), incentivepage.txtT1,
				jsonData.get("tier1"));
		incentivepage.addTiers(incentivepage.gridT2, incentivepage.pencilEditTierT1T2T3.get(1), incentivepage.txtT1,
				jsonData.get("tier2"));
		incentivepage.addTiers(incentivepage.gridT3, incentivepage.pencilEditTierT1T2T3.get(2), incentivepage.txtT1,
				jsonData.get("tier3"));

		incentivepage.addFormula(incentivepage.gridFormula, incentivepage.ddlBenefitFormulaEdit,
				incentivepage.ddlBenefitFormula, incentivepage.ddlFormulaValue);
		incentivepage.addTiers(incentivepage.gridT1, incentivepage.pencilEditBenefitT1T2T3.get(0), incentivepage.txtT1,
				jsonData.get("Benefit1"));
		incentivepage.addTiers(incentivepage.gridT2, incentivepage.pencilEditBenefitT1T2T3.get(1), incentivepage.txtT1,
				jsonData.get("Benefit2"));
		incentivepage.addTiers(incentivepage.gridT3, incentivepage.pencilEditBenefitT1T2T3.get(2), incentivepage.txtT1,
				jsonData.get("Benefit3"));

		incentivepage.save(incentivepage.btnSave);
		incentivepage.getErrorMessage(incentivepage.gridErrorMessage.get(0), incentivepage.txtErrorMessage);
		softassert.assertEquals("Tier value should be incremental", incentivepage.txtErrorMessage.getText());
		softassert.assertAll();
	}

	@Test(description = "TC 574 Adding multiple products with multiple searches on Benefit only and Tiered incentive", groups = {
			"Regression", "UI", "Medium" })
	public void VerifyMultipleSearchTieredIncentive() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json",
				"createIncentiveIndividualParticipantBenefitProductTiered");
		cimHelper.addAndValidateIncentive(jsonData, DataHelper.getIncentiveTemplateIdBenefitProductTiered(),
				benefitProductQnB);
		incentivepage = homepage.navigateToIncentiveEdit(benefitProductQnB.getIncentiveData().incentiveId);
		genericPage.clickWhenElementIsVisibleAndClickable(incentivepage.btnAddproduct);
		jsonData = efficacies.readJsonElement("CIMIncentiveQnBData.json", "tiersIncrementalOrder");
		genericPage.clickWhenElementIsVisibleAndClickable(incentivepage.ddlSelectOption);
		softassert.assertEquals(true, incentivepage.ddldefaultProduct.isDisplayed());
		softassert.assertEquals(jsonData.get("productValue"), incentivepage.comboboxvalue.get(0).getAttribute("title"));
		softassert.assertEquals(jsonData.get("categoryValue"),
				incentivepage.comboboxvalue.get(1).getAttribute("title"));

		genericPage.clickWhenElementIsVisibleAndClickable(incentivepage.ddlSelectOption);
		genericPage.waitTillPageContentLoad(RebatesConstants.waitFor2Sec); 
		genericPage.doubleClick(incentivepage.checkbox.get(0));
		softassert.assertEquals(jsonData.get("Selected(1)"), incentivepage.btnSelected.getText());
		genericPage.doubleClick(incentivepage.checkbox.get(0));
		softassert.assertEquals(jsonData.get("Selected(2)"), incentivepage.btnSelected.getText());
		genericPage.doubleClick(incentivepage.checkbox.get(0));
		softassert.assertEquals(jsonData.get("Selected(3)"), incentivepage.btnSelected.getText());
		incentivepage.btnSelected.click();
		genericPage.doubleClick(incentivepage.checkbox.get(0));
		softassert.assertEquals(jsonData.get("Selected(2)"), incentivepage.btnSelected.getText());
		genericPage.clearAndSendKeys(incentivepage.txtEditNoOfTiers, jsonData.get("tierCount"));
		genericPage.clickWhenElementIsVisibleAndClickable(incentivepage.btnAdd);
		softassert.assertEquals(true, incentivepage.gridT1.isDisplayed());
		softassert.assertEquals(true, incentivepage.gridT2.isDisplayed());
		softassert.assertEquals(true, incentivepage.gridT3.isDisplayed());
		softassert.assertAll();
	}

	@Test(description = "TC-522 Verify for the Alias names and the Section IDs provided for the QnB Line Items", groups = {
			"Regression", "UI", "Low" })
	public void VerifyAliasNameAndSectionId() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json",
				"createNewIncentiveAgreementAccountBenefitProductDiscrete");
		cimHelper.addAndValidateIncentive(jsonData, DataHelper.getIncentiveTemplateIdBenefitProductTiered(),
				benefitProductQnB);

		cimHelper.addAndValidateQnBOnIncentive(benefitProductQnB, "XXDBenefitProduct");
		incentivepage = homepage.navigateToIncentiveEdit(benefitProductQnB.getIncentiveData().incentiveId);
		incentivepage.waitTillAllQnBElementLoad();

		genericPage.clickWhenElementIsVisibleAndClickable(incentivepage.btnAddproduct);
		genericPage.clickWhenElementIsVisibleAndClickable(incentivepage.ddlSelectOption);
		genericPage.waitTillPageContentLoad(RebatesConstants.waitFor2Sec); 
		genericPage.doubleClick(incentivepage.checkbox.get(0));
		genericPage.doubleClick(incentivepage.checkbox.get(0));
		genericPage.doubleClick(incentivepage.checkbox.get(0));
		genericPage.clickButton(incentivepage.btnSelected).clickButton(incentivepage.btnAdd);
		genericPage.waitTillPageContentLoad(RebatesConstants.waitFor2Sec);
		genericPage.clickButton(incentivepage.btnShowAction)
				.moveToElementAndClick(incentivepage.btnDelete, incentivepage.btnDelete);
		incentivepage.btnConfirmDelete.click();
		genericPage.clickWhenElementIsVisibleAndClickable(incentivepage.btnAddproduct);
		genericPage.clickWhenElementIsVisibleAndClickable(incentivepage.ddlSelectOption)
				.doubleClick(incentivepage.checkbox.get(0)).clickButton(incentivepage.btnSelected)
				.clickButton(incentivepage.btnAdd);
		jsonData = efficacies.readJsonElement("CIMIncentiveQnBData.json", "tiersIncrementalOrder");
		softassert.assertEquals(jsonData.get("sectionId"), incentivepage.txtSectionID.getText());
		softassert.assertAll();
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		driver.quit();
	}

}
