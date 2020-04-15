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
import com.apttus.sfdc.rebates.lightning.generic.utils.CIMHelper;
import com.apttus.sfdc.rebates.lightning.generic.utils.DataHelper;
import com.apttus.sfdc.rebates.lightning.generic.utils.RebatesConstants;
import com.apttus.sfdc.rebates.lightning.generic.utils.SFDCHelper;
import com.apttus.sfdc.rebates.lightning.main.UnifiedFramework;
import com.apttus.sfdc.rebates.lightning.ui.library.HomePage;
import com.apttus.sfdc.rebates.lightning.ui.library.IncentivePage;
import com.apttus.sfdc.rebates.lightning.ui.library.LoginPage;
import com.apttus.sfdc.rudiments.utils.SFDCRestUtils;

public class TestIncentiveUI extends UnifiedFramework {
	private Properties configProperties;
	private Efficacies efficacies;
	private SFDCRestUtils sfdcRestUtils;
	private String instanceURL;
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
	private SoftAssert softassert;
	private CIMHelper cimHelper;

	@BeforeClass(alwaysRun = true)
	@Parameters({ "runParallel", "environment", "browser", "hubURL" })
	public void beforeClass(String runParallel, String environment, String browser, String hubURL) throws Exception {
		DataHelper obj= DataHelper.getInstanceOfDataHelper();
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
	}

	@Test(description = "TC- 491 Program will not be activated if Q&B is empty but  participants are there", groups = {
			"Regression", "Medium", "UI" })
	public void verifyIncentiveWithoutQnB() throws Exception {

		jsonData = efficacies.readJsonElement("CIMTemplateData.json",
				"createIncentiveIndividualParticipantForPayeeAndMeasurementLevelBenefitProductTiered");
		cimHelper.addAndValidateIncentive(jsonData, DataHelper.getIncentiveTemplateIdBenefitProductDiscrete(), cim);
		cimHelper.addAndValidateParticipant(cim, "addParticipantsSameAsIncentiveDates");

		incentivepage = homepage.navigateToIncentiveEdit(cim.getIncentiveData().incentiveId);
		incentivepage.activateIncentive();
		softassert.assertEquals(RebatesConstants.messageFailToActivateWithoutQnB,
				incentivepage.txtToastMessage.getText());
		softassert.assertAll();

	}

	@Test(description = "TC-490 Program will not be activated if Q&B is added but participants are empty", groups = {
			"Regression", "Medium", "UI" })
	public void verifyIncentiveWithoutParticipant() throws Exception {

		jsonData = efficacies.readJsonElement("CIMTemplateData.json",
				"createNewIncentiveAgreementAccountBenefitProductDiscrete");
		cimHelper.addAndValidateIncentive(jsonData, DataHelper.getIncentiveTemplateIdBenefitProductDiscrete(),
				benefitProductQnB);

		cimHelper.addAndValidateQnBOnIncentive(benefitProductQnB, "XXDBenefitProduct");

		incentivepage = homepage.navigateToIncentiveEdit(benefitProductQnB.getIncentiveData().incentiveId);
		incentivepage.activateIncentive();
		softassert.assertEquals(RebatesConstants.messageFailToActivateWithoutParticipant,
				incentivepage.txtToastMessage.getText());
		softassert.assertAll();
	}

	@Test(description = "TC- 537 Verify error message when user tries to activate the program with unsaved changes", groups = {
			"Regression", "Medium", "UI" })
	public void verifyActivateIncentiveWithUnsavedChanges() throws Exception {

		jsonData = efficacies.readJsonElement("CIMTemplateData.json",
				"createNewIncentiveAgreementAccountBenefitProductDiscrete");
		cimHelper.addAndValidateIncentive(jsonData, DataHelper.getIncentiveTemplateIdBenefitProductDiscrete(), cim);

		incentivepage = homepage.navigateToIncentiveEdit(cim.getIncentiveData().incentiveId);
		incentivepage.addQualificationBenefit();
		incentivepage.activateIncentive();
		softassert.assertEquals(RebatesConstants.mandatoryMessageUnsavedChanges,
				incentivepage.txtToastMessage.getText());
		softassert.assertAll();
	}
	
	@AfterClass(alwaysRun = true)
	public void tearDown() {
		driver.quit();
	}
}
