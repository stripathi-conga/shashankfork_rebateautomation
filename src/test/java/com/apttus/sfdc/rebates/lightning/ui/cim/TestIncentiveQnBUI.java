package com.apttus.sfdc.rebates.lightning.ui.cim;

import java.util.List;
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
import com.apttus.sfdc.rebates.lightning.api.validator.BenefitProductValidator;
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
import com.jayway.restassured.response.Response;

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
	public IncentivePage incentivePage;
	WebDriver driver;
	LoginPage loginPage;
	public String baseUIURL;
	public Map<String, String> testData;
	public HomePage homepage;
	SoftAssert softassert;
	GenericPage genericPage;
	private List<Map<String, String>> jsonArrayData;
	private Response response;
	private BenefitProductValidator responseValidator;

	@BeforeClass(alwaysRun = true)
	@Parameters({ "runParallel", "environment", "browser", "hubURL" })
	public void beforeClass(String runParallel, String environment, String browser, String hubURL) throws Exception {
		DataHelper obj= DataHelper.getInstance();
		obj.getData(environment);
		
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
		responseValidator = new BenefitProductValidator();
	}

	@Test(description = "TC-525 Validation fields available for Qualification And Benefits-Add Product", groups = {
			"Regression", "UI", "Low" })
	public void VerifyQnBFields() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json",
				"createIncentiveIndividualParticipantBenefitProductTiered");
		cimHelper.addAndValidateIncentive(jsonData, DataHelper.getIncentiveTemplateIdBenefitProductTiered(),
				benefitProductQnB);

		cimHelper.addAndValidateQnBOnIncentive(benefitProductQnB, "XXTBenefitProduct");

		incentivePage = homepage.navigateToIncentiveEdit(benefitProductQnB.getIncentiveData().incentiveId);
		incentivePage.waitTillAllQnBElementLoad();

		softassert.assertEquals(true, incentivePage.colValueAliasName.isDisplayed());
		softassert.assertEquals(true, incentivePage.colValueDate.get(0).isDisplayed());
		softassert.assertEquals(true, incentivePage.colValueDate.get(1).isDisplayed());
		softassert.assertEquals(true, incentivePage.colValueNameCodeType.get(0).isDisplayed());
		softassert.assertEquals(true, incentivePage.colValueNameCodeType.get(1).isDisplayed());
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

		incentivePage = homepage.navigateToIncentiveEdit(benefitProductQnB.getIncentiveData().incentiveId);
		incentivePage.waitTillAllQnBElementLoad();
		jsonData = efficacies.readJsonElement("CIMIncentiveQnBData.json", "tiersIncrementalOrder");
		incentivePage.addProductWithMultipleTiers(jsonData.get("productName"), jsonData.get("tierCount"));
		incentivePage.addFormula(incentivePage.gridFormula, incentivePage.ddlQualificationFormulaEdit,
				incentivePage.ddlQualificationFormula, incentivePage.ddlFormulaValue);
		incentivePage.addTiers(incentivePage.gridT1, incentivePage.pencilEditTierT1T2T3.get(0), incentivePage.txtT1,
				jsonData.get("tier1"));
		incentivePage.addTiers(incentivePage.gridT2, incentivePage.pencilEditTierT1T2T3.get(1), incentivePage.txtT1,
				jsonData.get("tier2"));
		incentivePage.addTiers(incentivePage.gridT3, incentivePage.pencilEditTierT1T2T3.get(2), incentivePage.txtT1,
				jsonData.get("tier3"));

		incentivePage.addFormula(incentivePage.gridFormula, incentivePage.ddlBenefitFormulaEdit,
				incentivePage.ddlBenefitFormula, incentivePage.ddlFormulaValue);
		incentivePage.addTiers(incentivePage.gridT1, incentivePage.pencilEditBenefitT1T2T3.get(0), incentivePage.txtT1,
				jsonData.get("Benefit1"));
		incentivePage.addTiers(incentivePage.gridT2, incentivePage.pencilEditBenefitT1T2T3.get(1), incentivePage.txtT1,
				jsonData.get("Benefit2"));
		incentivePage.addTiers(incentivePage.gridT3, incentivePage.pencilEditBenefitT1T2T3.get(2), incentivePage.txtT1,
				jsonData.get("Benefit3"));

		incentivePage.save(incentivePage.btnSave);
		incentivePage.getErrorMessage(incentivePage.gridErrorMessage.get(0), incentivePage.txtErrorMessage);
		softassert.assertEquals("Tier value should be incremental", incentivePage.txtErrorMessage.getText());
		softassert.assertAll();
	}

	@Test(description = "TC 574 Adding multiple products with multiple searches on Benefit only and Tiered incentive", groups = {
			"Regression", "UI", "Medium" })
	public void VerifyMultipleSearchTieredIncentive() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json",
				"createIncentiveIndividualParticipantBenefitProductTiered");
		cimHelper.addAndValidateIncentive(jsonData, DataHelper.getIncentiveTemplateIdBenefitProductTiered(),
				benefitProductQnB);
		incentivePage = homepage.navigateToIncentiveEdit(benefitProductQnB.getIncentiveData().incentiveId);
		genericPage.clickWhenElementIsVisibleAndClickable(incentivePage.btnAddproduct);
		jsonData = efficacies.readJsonElement("CIMIncentiveQnBData.json", "tiersIncrementalOrder");
		genericPage.clickWhenElementIsVisibleAndClickable(incentivePage.ddlSelectOption);
		softassert.assertEquals(true, incentivePage.ddldefaultProduct.isDisplayed());
		softassert.assertEquals(jsonData.get("productValue"), incentivePage.comboboxvalue.get(0).getAttribute("title"));
		softassert.assertEquals(jsonData.get("categoryValue"),
				incentivePage.comboboxvalue.get(1).getAttribute("title"));

		genericPage.clickWhenElementIsVisibleAndClickable(incentivePage.ddlSelectOption);
		genericPage.waitTillPageContentLoad(RebatesConstants.waitFor2Sec);
		genericPage.doubleClick(incentivePage.checkbox.get(0));
		softassert.assertEquals(jsonData.get("Selected(1)"), incentivePage.btnSelected.getText());
		genericPage.doubleClick(incentivePage.checkbox.get(0));
		softassert.assertEquals(jsonData.get("Selected(2)"), incentivePage.btnSelected.getText());
		genericPage.doubleClick(incentivePage.checkbox.get(0));
		softassert.assertEquals(jsonData.get("Selected(3)"), incentivePage.btnSelected.getText());
		incentivePage.btnSelected.click();
		genericPage.doubleClick(incentivePage.checkbox.get(0));
		softassert.assertEquals(jsonData.get("Selected(2)"), incentivePage.btnSelected.getText());
		genericPage.clearAndSendKeys(incentivePage.txtEditNoOfTiers, jsonData.get("tierCount"));
		genericPage.clickWhenElementIsVisibleAndClickable(incentivePage.btnAdd);
		softassert.assertEquals(true, incentivePage.gridT1.isDisplayed());
		softassert.assertEquals(true, incentivePage.gridT2.isDisplayed());
		softassert.assertEquals(true, incentivePage.gridT3.isDisplayed());
		softassert.assertAll();
	}

	@Test(description = "TC-522 Verify for the Alias names and the Section IDs provided for the QnB Line Items", groups = {
			"Regression", "UI", "Medium" })
	public void VerifyAliasNameAndSectionId() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json",
				"createNewIncentiveAgreementAccountBenefitProductDiscrete");
		cimHelper.addAndValidateIncentive(jsonData, DataHelper.getIncentiveTemplateIdBenefitProductTiered(),
				benefitProductQnB);

		cimHelper.addAndValidateQnBOnIncentive(benefitProductQnB, "XXDBenefitProduct");
		incentivePage = homepage.navigateToIncentiveEdit(benefitProductQnB.getIncentiveData().incentiveId);
		incentivePage.waitTillAllQnBElementLoad();

		genericPage.clickWhenElementIsVisibleAndClickable(incentivePage.btnAddproduct);
		genericPage.clickWhenElementIsVisibleAndClickable(incentivePage.ddlSelectOption);
		genericPage.waitTillPageContentLoad(RebatesConstants.waitFor2Sec);
		genericPage.doubleClick(incentivePage.checkbox.get(0));
		genericPage.doubleClick(incentivePage.checkbox.get(0));
		genericPage.doubleClick(incentivePage.checkbox.get(0));
		genericPage.clickButton(incentivePage.btnSelected).clickButton(incentivePage.btnAdd);
		genericPage.waitTillPageContentLoad(RebatesConstants.waitFor2Sec);

		genericPage.clickButton(incentivePage.btnInLineEdit).moveToElementAndClick(incentivePage.btnDelete,
				incentivePage.btnDelete);
		incentivePage.btnConfirmDelete.click();
		genericPage.clickWhenElementIsVisibleAndClickable(incentivePage.btnAddproduct);
		genericPage.clickWhenElementIsVisibleAndClickable(incentivePage.ddlSelectOption)
				.doubleClick(incentivePage.checkbox.get(0)).clickButton(incentivePage.btnSelected)
				.clickButton(incentivePage.btnAdd);
		jsonData = efficacies.readJsonElement("CIMIncentiveQnBData.json", "tiersIncrementalOrder");
		softassert.assertEquals(jsonData.get("sectionId"), incentivePage.txtSectionID.getText());
		softassert.assertAll();
	}

	@Test(description = "TC-552 Verify for fields on  QnB Edit pop up for Benefit only and Discrete Incentive", groups = {
			"Regression", "UI", "Medium" })
	public void verifyEditModelPopUpForDiscrete() throws Exception {

		jsonData = efficacies.readJsonElement("CIMTemplateData.json",
				"createNewIncentiveAgreementAccountBenefitProductDiscrete");
		cimHelper.addAndValidateIncentive(jsonData, DataHelper.getIncentiveTemplateIdBenefitProductDiscrete(),
				benefitProductQnB);
		cimHelper.addAndValidateQnBOnIncentive(benefitProductQnB, "XXDBenefitProduct");
		incentivePage = homepage.navigateToIncentiveEdit(benefitProductQnB.getIncentiveData().incentiveId);
		incentivePage.waitTillAllQnBElementLoad();
		genericPage.clickButton(incentivePage.btnShowAction).moveToElementAndClick(incentivePage.btnEditSpillover,
				incentivePage.btnEditSpillover);
		softassert.assertEquals(incentivePage.btnCancel.getText(), RebatesConstants.cancel);
		softassert.assertEquals(incentivePage.btnUpdate.getText(), RebatesConstants.update);
		// verify Cancel and update button on Edit Model

		incentivePage.btnCancel.click();
		jsonData = efficacies.readJsonElement("CIMFormulaData.json", "revPerctCalcFormulaIdBenefitNonStep");
		softassert.assertEquals(jsonData.get("Name"), incentivePage.txtformula.get(0).getText());
		// verify No update on Inline record after Cancel button click

		genericPage.clickButton(incentivePage.btnShowAction).moveToElementAndClick(incentivePage.btnEditSpillover,
				incentivePage.btnEditSpillover);
		softassert.assertEquals(jsonData.get("Name"), incentivePage.txtFormulaEditModel.getAttribute("value"));
		jsonData = efficacies.readJsonElement("CIMIncentiveQnBData.json", "tiersIncrementalOrder");
		softassert.assertEquals(incentivePage.txtBenefitEditModel.getAttribute("value"), jsonData.get("Benefit3"));

		// verify Edit Model data with respect to Inline product details.
		incentivePage.txtBenefitEditModel.clear();
		incentivePage.txtBenefitEditModel.sendKeys(jsonData.get("Benefit1"));
		incentivePage.btnUpdate.click();
		softassert.assertEquals(incentivePage.txtBenefit.get(1).getText(), jsonData.get("Benefit1"));
		// verifying updated data

		genericPage.clickButton(incentivePage.btnShowAction).moveToElementAndClick(incentivePage.btnEditSpillover,
				incentivePage.btnEditSpillover);
		incentivePage.txtBenefitEditModel.clear();
		incentivePage.txtBenefitEditModel.sendKeys(jsonData.get("Benefit3"));
		incentivePage.btnCancel.click();
		softassert.assertEquals(incentivePage.txtBenefit.get(1).getText(), jsonData.get("Benefit1"));
		incentivePage.btnSave.click();
		softassert.assertAll();
	}

	@Test(description = "TC 553 Verify QnB Edit pop up for Benefit only and Tiered Incentive", groups = { "Regression",
			"UI", "Medium" })
	public void verifyEditModelPopUpForTiered() throws Exception {

		jsonData = efficacies.readJsonElement("CIMTemplateData.json",
				"createIncentiveIndividualParticipantBenefitProductTiered");
		cimHelper.addAndValidateIncentive(jsonData, DataHelper.getIncentiveTemplateIdBenefitProductTiered(),
				benefitProductQnB);
		cimHelper.addAndValidateQnBOnIncentive(benefitProductQnB, "XXDBenefitProduct");
		incentivePage = homepage.navigateToIncentiveEdit(benefitProductQnB.getIncentiveData().incentiveId);
		incentivePage.waitTillAllQnBElementLoad();
		genericPage.clickButton(incentivePage.btnTierShowAction).moveToElementAndClick(incentivePage.btnEditSpillover,
				incentivePage.btnEditSpillover);
		softassert.assertEquals(incentivePage.btnCancel.getText(), RebatesConstants.cancel);
		softassert.assertEquals(incentivePage.btnUpdate.getText(), RebatesConstants.update);
		// verify Cancel and update button on Edit Model

		incentivePage.btnCancel.click();
		jsonData = efficacies.readJsonElement("CIMFormulaData.json", "revPerctCalcFormulaIdBenefitNonStep");
		softassert.assertEquals(incentivePage.txtformula.get(0).getText(), RebatesConstants.RevenueBased);
		// verify No update on Inline record after Cancel button click

		genericPage.clickButton(incentivePage.btnTierShowAction).moveToElementAndClick(incentivePage.btnEditSpillover,
				incentivePage.btnEditSpillover);
		softassert.assertEquals(incentivePage.ddlQualificationBenefitFormula.get(0).getAttribute("value"),
				RebatesConstants.RevenueBased);
		softassert.assertEquals(incentivePage.ddlQualificationBenefitFormula.get(1).getAttribute("value"),
				jsonData.get("Name"));
		jsonData = efficacies.readJsonElement("CIMIncentiveQnBData.json", "tiersIncrementalOrder");
		softassert.assertEquals(incentivePage.txtTierBenefitEditModel.get(1).getAttribute("value"),
				jsonData.get("Benefit3"));
		// verify Edit Model data with respect to Inline product details.

		incentivePage.txtTierBenefitEditModel.get(1).clear();
		incentivePage.txtTierBenefitEditModel.get(1).sendKeys(jsonData.get("Benefit1"));
		incentivePage.btnUpdate.click();
		softassert.assertEquals(incentivePage.txtTierBenefit.getText(), jsonData.get("Benefit1"));

		// verifying updated data
		genericPage.clickButton(incentivePage.btnTierShowAction).moveToElementAndClick(incentivePage.btnEditSpillover,
				incentivePage.btnEditSpillover);
		incentivePage.txtTierBenefitEditModel.get(1).clear();
		incentivePage.txtTierBenefitEditModel.get(1).sendKeys(jsonData.get("Benefit3"));
		incentivePage.btnCancel.click();
		softassert.assertEquals(incentivePage.txtTierBenefit.getText(), jsonData.get("Benefit1"));
		incentivePage.btnSave.click();
		softassert.assertAll();
	}
	
	@Test(description = "TC-437 Verify for the QnB grid View", groups = { "Regression", "High", "UI" })
	public void verifyQnBValidationForDatesAndNegativeTier() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json",
				"createIncentiveIndividualParticipantBenefitProductTiered");
		cimHelper.addAndValidateIncentive(jsonData, DataHelper.getIncentiveTemplateIdBenefitProductTiered(),
				benefitProductQnB);

		// ------ Add QnB Benefit Lines ---------
		response = cimHelper.addAndValidateQnBOnIncentive(benefitProductQnB, "XXTBenefitProduct");
		benefitProductQnB.setQnBSectionId(response);
		benefitProductQnB.setQualificationBenefitAndTierIds(response);

		// -------- Update QnB Benefit Line ------
		cimHelper.addAndValidateQnBOnIncentive(benefitProductQnB, "XXTUpdateBenefitProduct");

		// -------- Update QnB Benefit Line without Start Date ------
		jsonArrayData = SFDCHelper.readJsonArray("CIMIncentiveQnBData.json", "XXTUpdateBenefitWithoutStartDate");
		response = benefitProductQnB.addIncentiveQnBFailure(jsonArrayData);
		responseValidator.validateFailureResponse(response, RebatesConstants.errorCodeApexError,
				RebatesConstants.messageBenefitStartDateIsRequired);

		// -------- Update QnB Benefit Line without End Date -------
		jsonArrayData = SFDCHelper.readJsonArray("CIMIncentiveQnBData.json", "XXTUpdateBenefitWithoutEndDate");
		response = benefitProductQnB.addIncentiveQnBFailure(jsonArrayData);
		responseValidator.validateFailureResponse(response, RebatesConstants.errorCodeApexError,
				RebatesConstants.messageBenefitEndDateIsRequired);		
		
		// -------- Update QnB Benefit Line Negative Tier Values --------
		incentivePage = homepage.navigateToIncentiveEdit(benefitProductQnB.getIncentiveData().incentiveId);
		incentivePage.waitTillAllQnBElementLoad();
		jsonData = efficacies.readJsonElement("CIMIncentiveQnBData.json", "negativeTiersQuantity");
		incentivePage.updateTiers(incentivePage.gridT1, incentivePage.pencilEditTierT1T2T3.get(0), incentivePage.txtT1,
				jsonData.get("tier1"));
		incentivePage.save(incentivePage.btnSave);
		incentivePage.getErrorMessage(incentivePage.gridErrorMessage.get(0), incentivePage.txtErrorMessageTierValueNegative);
		softassert.assertEquals(incentivePage.txtErrorMessageTierValueNegative.getText(), "Tier boundary values should be positive",
				"Verify error message when tier quantity is negative");
		
		// ------- Update QnB Benefit Line with Tier Value as null -------
		incentivePage = homepage.navigateToIncentiveEdit(benefitProductQnB.getIncentiveData().incentiveId);
		incentivePage.waitTillAllQnBElementLoad();
		incentivePage.deleteTierValue(incentivePage.gridT1, incentivePage.pencilEditTierT1T2T3.get(0), incentivePage.txtT1);
		incentivePage.save(incentivePage.btnSave);
		incentivePage.getErrorMessage(incentivePage.gridErrorMessage.get(0), incentivePage.txtErrorMessageTierValueRequired);
		softassert.assertEquals(incentivePage.txtErrorMessageTierValueRequired.getText(), "Tier value is required",
				"Verify error message when tier quantity is null");
		softassert.assertAll();
	}
	
	@AfterClass(alwaysRun = true)
	public void tearDown() {
		driver.quit();
	}	 
}
