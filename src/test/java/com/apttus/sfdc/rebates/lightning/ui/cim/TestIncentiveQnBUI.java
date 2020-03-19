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
	private BenefitProductValidator responseValidator;
	public CIM cim;
	private Map<String, String> jsonData;
	private List<Map<String, String>> jsonArrayData;
	private Response response;
	private BenefitProductQnB benefitProductQnB;
	public String calcFormulaIdBenefitTiered, calcFormulaIdQualificationTiered;
	public IncentivePage incentivepage;
	private String incentiveid;
	WebDriver driver;
	LoginPage loginPage;
	public String baseUIURL;
	public Map<String, String> testData;
	public HomePage homepage;
	SoftAssert softassert;

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
		responseValidator = new BenefitProductValidator();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeClass() throws Exception {
		benefitProductQnB = new BenefitProductQnB(instanceURL, sfdcRestUtils);
		softassert = new SoftAssert();
	}

	@Test(description = "TC-525 Validation fields available for Qualification And Benefits-Add Product", groups = {
			"Regression", "UI", "Low" })
	public void VerifyQnBFields() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json",
				"createIncentiveIndividualParticipantBenefitProductTiered");
		jsonData.put("ProgramTemplateId__c", RebatesConstants.incentiveTemplateIdBenefitProductTiered);
		incentiveid = benefitProductQnB.createNewIncentive(jsonData);
		response = benefitProductQnB.getIncentiveDetails();
		responseValidator.validateIncentiveDetails(jsonData, response, benefitProductQnB);
		jsonArrayData = SFDCHelper.readJsonArray("CIMIncentiveQnBData.json", "XXTBenefitProduct");
		benefitProductQnB.addIncentiveQnB(jsonArrayData);
		response = benefitProductQnB.getIncentiveQnB();
		responseValidator.validateIncentiveQnB(benefitProductQnB.getRequestValue("addQnBRequest"), response);

		incentivepage = homepage.navigateToIncentiveEdit(incentiveid);
		incentivepage.waitTillAllQnBElementLoad();

		softassert.assertEquals(true, incentivepage.colValueAliasName.isDisplayed());
		softassert.assertEquals(true, incentivepage.colValueDate.get(0).isDisplayed());
		softassert.assertEquals(true, incentivepage.colValueDate.get(1).isDisplayed());
		softassert.assertEquals(true, incentivepage.colValueNameCodeType.get(0).isDisplayed());
		softassert.assertEquals(true, incentivepage.colValueNameCodeType.get(1).isDisplayed());
		softassert.assertAll();
	}
	@AfterClass(alwaysRun = true)
	public void tearDown() {
		driver.quit();
	}
}
