package com.apttus.sfdc.rebates.lightning.ui.admin;

import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.apttus.helpers.Efficacies;
import com.apttus.selenium.WebDriverUtils;
import com.apttus.sfdc.rebates.lightning.api.library.BenefitProductQnB;
import com.apttus.sfdc.rebates.lightning.api.library.CIM;
import com.apttus.sfdc.rebates.lightning.api.validator.BenefitProductValidator;
import com.apttus.sfdc.rebates.lightning.generic.utils.RebatesConstants;
import com.apttus.sfdc.rebates.lightning.generic.utils.RebatesUIConstants;
import com.apttus.sfdc.rebates.lightning.generic.utils.SFDCHelper;
import com.apttus.sfdc.rebates.lightning.main.UnifiedFramework;
import com.apttus.sfdc.rebates.lightning.ui.library.HomePage;
import com.apttus.sfdc.rebates.lightning.ui.library.Incentivepage;
import com.apttus.sfdc.rebates.lightning.ui.library.LoginPage;
import com.apttus.sfdc.rudiments.utils.SFDCRestUtils;
import com.jayway.restassured.response.Response;

public class TestIncentiveUI extends UnifiedFramework {
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
	public Incentivepage incentivepage;
	private String incentiveid;
	WebDriver driver;
	LoginPage loginPage;
	public Map<String, String> testData;
	public HomePage homepage;
	SoftAssert softassert;

	@BeforeSuite(alwaysRun = true)
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
		homepage = new HomePage(driver);

		SFDCHelper.setMasterProperty(configProperties);
		instanceURL = SFDCHelper.setAccessToken(sfdcRestUtils);
		cim = new CIM(instanceURL, sfdcRestUtils);
		responseValidator = new BenefitProductValidator();

		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "activeTemplateIdForRebateTiered");
		cim.deactivateLinkTemplateForIncentives(jsonData);

		// -------- Create Benefit formulaId for SubType as Tiered -----------------
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createFieldExpressionId");
		String fieldExpressionId = cim.getFieldExpressionId(jsonData);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createCalcFormulaIdBenefit");
		calcFormulaIdBenefitTiered = cim.getCalcFormulaId(jsonData);
		RebatesConstants.benefitFormulaId = calcFormulaIdBenefitTiered;

		// -------- Create Qualification formulaId for SubType as Tiered
		// -----------------
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createCalcFormulaIdQualification");
		calcFormulaIdQualificationTiered = cim.getCalcFormulaId(jsonData);
		RebatesConstants.qualificationFormulaId = calcFormulaIdQualificationTiered;

		// -------- Link formulaId to Data Source-----------------
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "linkCalcFormulaToExpressionId");
		cim.linkCalcFormulaToExpression(jsonData, calcFormulaIdBenefitTiered, fieldExpressionId);
		cim.linkCalcFormulaToExpression(jsonData, calcFormulaIdQualificationTiered, fieldExpressionId);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewDataSourceAPI");
		cim.createDataSource(jsonData);
		cim.linkDatasourceToCalcFormula(calcFormulaIdBenefitTiered);
		cim.linkDatasourceToCalcFormula(calcFormulaIdQualificationTiered);

		// -------- Create and activate Template for Benefit Only Tiered
		// -----------------
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "benefitOnlyTieredQnBLayoutAPI");
		String qnbLayoutId = cim.getQnBLayoutId(jsonData);

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewTemplateAPI");
		response = cim.createTemplate(jsonData, qnbLayoutId);
		responseValidator.validateCreateSuccess(response);
		response = cim.getTemplate();
		responseValidator.validateGetTemplate(response, cim);
		jsonData.put("FormulaId__c", calcFormulaIdBenefitTiered);
		jsonData.put("DataSourceId__c", cim.getDataSourceData().getDataSourceId());
		cim.mapProgramTemplateDataSource(jsonData);

		jsonData.put("FormulaId__c", calcFormulaIdQualificationTiered);
		jsonData.put("DataSourceId__c", cim.getDataSourceData().getDataSourceId());
		cim.mapProgramTemplateDataSource(jsonData);

		cim.activateTemplate(RebatesConstants.responseNocontent);
		RebatesConstants.incentiveTemplateIdBenefitProductTiered = cim.getTemplateData().getTemplateId();

		// -------- Create and activate Link Template for Subtype as
		// Tiered-----------------
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewLinkTemplateSubTypeTieredAPI");
		response = cim.createLinkTemplates(jsonData);
		cim.activateLinkTemplate();
		response = cim.getLinkTemplatesViaId();
		responseValidator.validateLinkTemplatesStatus(response, cim, RebatesConstants.activate);

		// --Deactivate the Active Link Template for LinkTemplate with SubType Discrete
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "activeTemplateIdForRebateDiscrete");
		cim.deactivateLinkTemplateForIncentives(jsonData);

		// -------- Create and activate Template for Benefit Only Discrete

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "benefitOnlyDiscreteQnBLayoutAPI");
		qnbLayoutId = cim.getQnBLayoutId(jsonData);

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewTemplateAPI");
		response = cim.createTemplate(jsonData, qnbLayoutId);

		responseValidator.validateCreateSuccess(response);
		response = cim.getTemplate();
		responseValidator.validateGetTemplate(response, cim);
		jsonData.put("FormulaId__c", calcFormulaIdBenefitTiered);
		jsonData.put("DataSourceId__c", cim.getDataSourceData().getDataSourceId());
		cim.mapProgramTemplateDataSource(jsonData);

		jsonData.put("FormulaId__c", calcFormulaIdQualificationTiered);
		jsonData.put("DataSourceId__c", cim.getDataSourceData().getDataSourceId());
		cim.mapProgramTemplateDataSource(jsonData);

		cim.activateTemplate(RebatesConstants.responseNocontent);
		RebatesConstants.incentiveTemplateIdBenefitProductDiscrete = cim.getTemplateData().getTemplateId();

		// -- Create and activate Link Template for Subtype as Discrete-----------------
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewLinkTemplateSubTypeDiscreteAPI");
		response = cim.createLinkTemplates(jsonData);
		cim.activateLinkTemplate();
		response = cim.getLinkTemplatesViaId();
		responseValidator.validateLinkTemplatesStatus(response, cim, RebatesConstants.activate);
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeClass() throws Exception {
		benefitProductQnB = new BenefitProductQnB(instanceURL, sfdcRestUtils);
	}

	@Test(description = "TC-490 Program will not be activated if Q&B is added but participants are empty", groups = {
			"Regression", "High", "UI" })
	public void verifyIncentiveWithoutParticipant() throws Exception {
		try {
			softassert = new SoftAssert();
			jsonData = efficacies.readJsonElement("CIMTemplateData.json",
					"createNewIncentiveAgreementAccountBenefitProductDiscrete");
			jsonData.put("ProgramTemplateId__c", RebatesConstants.incentiveTemplateIdBenefitProductDiscrete);
			incentiveid = benefitProductQnB.createNewIncentive(jsonData);
			response = benefitProductQnB.getIncentiveDetails();
			responseValidator.validateIncentiveDetails(jsonData, response, benefitProductQnB);
			jsonArrayData = SFDCHelper.readJsonArray("CIMIncentiveQnBData.json", "XXDBenefitProduct");
			benefitProductQnB.addIncentiveQnB(jsonArrayData);
			response = benefitProductQnB.getIncentiveQnB();
			responseValidator.validateIncentiveQnB(benefitProductQnB.getRequestValue("addQnBRequest"), response);

			incentivepage = homepage.navigateToIncentiveEdit(configProperties, incentiveid);
			incentivepage.activateIncentive();
			assertEquals(RebatesUIConstants.messageFailToActivateWithoutParticipant,
					incentivepage.txtToastMessage.getText());
			softassert.assertAll();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Test(description = "TC- 491 Program will not be activated if Q&B is empty but  participants are there", groups = {
			"Regression", "High", "UI" })
	public void verifyIncentiveWithoutQnB() throws Exception {
		try {

			jsonData = efficacies.readJsonElement("CIMTemplateData.json",
					"createIncentiveIndividualParticipantForPayeeAndMeasurementLevelBenefitProductTiered");
			jsonData.put("ProgramTemplateId__c", RebatesConstants.incentiveTemplateIdBenefitProductTiered);
			incentiveid = cim.createNewIncentive(jsonData);
			response = cim.getIncentiveDetails();
			responseValidator.validateIncentiveDetails(jsonData, response, cim);
			jsonData = efficacies.readJsonElement("CIMTemplateData.json", "addParticipantsSameAsIncentiveDates");
			cim.addParticipants(jsonData);
			response = cim.getParticipantsDetails();
			responseValidator.validateParticipantsDetails(jsonData, response, cim);

			incentivepage = homepage.navigateToIncentiveEdit(configProperties,incentiveid);
			incentivepage.activateIncentive();
			assertEquals(RebatesUIConstants.messageFailToActivateWithoutQnB, incentivepage.txtToastMessage.getText());
			softassert.assertAll();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		driver.quit();
	}
}
