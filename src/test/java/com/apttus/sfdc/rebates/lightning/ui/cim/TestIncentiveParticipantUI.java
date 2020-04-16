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
import com.apttus.sfdc.rebates.lightning.api.validator.BenefitProductValidator;
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

public class TestIncentiveParticipantUI extends UnifiedFramework {
	private Properties configProperties;
	private Efficacies efficacies;
	private SFDCRestUtils sfdcRestUtils;
	private String instanceURL;
	private BenefitProductValidator responseValidator;
	public CIM cim;
	private Map<String, String> jsonData;
	private Response response;
	public BenefitProductQnB benefitProductQnB;
	public String calcFormulaIdBenefitTiered, calcFormulaIdQualificationTiered;
	public IncentivePage incentivepage;
	WebDriver driver;
	LoginPage loginPage;
	public String baseUIURL;
	public Map<String, String> testData;
	public HomePage homepage;
	private SoftAssert softassert;
	private CIMHelper cimHelper;

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
		responseValidator = new BenefitProductValidator();
		cimHelper = new CIMHelper();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeClass() throws Exception {
		benefitProductQnB = new BenefitProductQnB(instanceURL, sfdcRestUtils);
		softassert = new SoftAssert();
	}

	@Test(description = "TC-570 Add Participants button on the participant page", groups = { "Regression", "Medium",
			"UI" })
	public void verifyAddParticipant() throws Exception {

		jsonData = efficacies.readJsonElement("CIMTemplateData.json",
				"createIncentiveIndividualParticipantForPayeeAndMeasurementLevelBenefitProductTiered");
		cimHelper.addAndValidateIncentive(jsonData,
				DataHelper.getIncentiveTemplateIdBenefitProductDiscrete(), cim);		
		cimHelper.addAndValidateParticipant(cim, "addParticipantsSameAsIncentiveDates");

		incentivepage = homepage.navigateToIncentiveEdit(cim.getIncentiveData().incentiveId);
		incentivepage.moveToParticipantTab();
		softassert.assertEquals(RebatesConstants.newParticipant, incentivepage.btnNew.getText());
		softassert.assertAll();
	}

	@Test(description = "TC 379 - Verify the participant data in the participants grid view", groups = { "Regression",
			"UI", "Medium" })
	public void verifyParticipantGridData() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json",
				"createIncentiveIndividualParticipantForPayeeAndMeasurementLevelBenefitProductTiered");		
		cimHelper.addAndValidateIncentive(jsonData,
				DataHelper.getIncentiveTemplateIdBenefitProductDiscrete(), cim);		
		cimHelper.addAndValidateParticipant(cim, "addParticipantsForOverlappingDates");

		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "addParticipantsForOverlappingDates");
		response = cim.addParticipantsFailure(jsonData);
		responseValidator.validateParticipantFailureResponse(response, RebatesConstants.errorFieldsForDates,
				RebatesConstants.messageOverlappingParticipants);
		jsonData.put("ExpirationDate__c", "incentiveenddate");
		jsonData.put("EffectiveDate__c", "incentivestartdate=+5");
		response = cim.addParticipantsFailure(jsonData);
		responseValidator.validateParticipantFailureResponse(response, RebatesConstants.errorFieldsForDates,
				RebatesConstants.messageOverlappingParticipants);
		jsonData.put("ExpirationDate__c", "incentiveenddate");
		jsonData.put("EffectiveDate__c", "incentivestartdate=+11");
		cim.addParticipants(jsonData);
		response = cim.getParticipantsDetails();
		responseValidator.validateParticipantsDetails(jsonData, response, cim);

		incentivepage = homepage.navigateToIncentiveEdit(cim.getIncentiveData().incentiveId);
		incentivepage.moveToParticipantTab();
		incentivepage.waitTillAllParticipantElementLoad();
		softassert.assertEquals(true, incentivepage.colAccountName.isDisplayed());
		softassert.assertEquals(true, incentivepage.colAccountNumber.isDisplayed());
		softassert.assertEquals(true, incentivepage.colAccountType.isDisplayed());
		softassert.assertEquals(true, incentivepage.colEffectiveEndDate.isDisplayed());
		softassert.assertEquals(true, incentivepage.colEffectiveStartDate.isDisplayed());

		softassert.assertEquals("enable", incentivepage.lnkAccountName.get(0).getAttribute("data-navigation"));
		softassert.assertEquals(true, incentivepage.lnkAccount.get(0).isEnabled());
		softassert.assertEquals(true, incentivepage.lnkAccount.get(1).isEnabled());
		softassert.assertEquals("enable", incentivepage.effectiveDate.get(0).getAttribute("data-navigation"));
		softassert.assertEquals("enable", incentivepage.effectiveDate.get(1).getAttribute("data-navigation"));
		softassert.assertAll();
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		driver.quit();
	}
}
