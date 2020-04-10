package com.apttus.sfdc.rebates.lightning.api.cim;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.apttus.helpers.Efficacies;
import com.apttus.sfdc.rebates.lightning.api.library.BenefitProductQnB;
import com.apttus.sfdc.rebates.lightning.api.validator.BenefitProductValidator;
import com.apttus.sfdc.rebates.lightning.generic.utils.CIMHelper;
import com.apttus.sfdc.rebates.lightning.generic.utils.DataHelper;
import com.apttus.sfdc.rebates.lightning.generic.utils.RebatesConstants;
import com.apttus.sfdc.rebates.lightning.generic.utils.SFDCHelper;
import com.apttus.sfdc.rebates.lightning.main.UnifiedFramework;
import com.apttus.sfdc.rudiments.utils.SFDCRestUtils;
import com.jayway.restassured.response.Response;

public class TestIncentiveQnB extends UnifiedFramework {
	private Properties configProperties;
	private Efficacies efficacies;
	private SFDCRestUtils sfdcRestUtils;
	private String instanceURL;
	private BenefitProductValidator responseValidator;
	private Map<String, String> jsonData;
	private List<Map<String, String>> jsonArrayData;
	private Response response;
	private BenefitProductQnB benefitProductQnB;
	public String calcFormulaIdBenefitTiered, calcFormulaIdQualificationTiered;
	private CIMHelper cimHelper;

	@BeforeClass(alwaysRun = true)
	@Parameters({ "runParallel", "environment", "browser", "hubURL" })
	public void beforeClass(String runParallel, String environment, String browser, String hubURL) throws Exception {
		efficacies = new Efficacies();
		sfdcRestUtils = new SFDCRestUtils();
		configProperties = efficacies.loadPropertyFile(environment);
		SFDCHelper.setMasterProperty(configProperties);
		instanceURL = SFDCHelper.setAccessToken(sfdcRestUtils);
		cimHelper = new CIMHelper();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeClass() throws Exception {
		benefitProductQnB = new BenefitProductQnB(instanceURL, sfdcRestUtils);
		responseValidator = new BenefitProductValidator();
	}

	@Test(description = "TC-459 Verify searching and adding the product to the Benefit only and Tiered programs", groups = {
			"Smoke", "API" })
	public void addQnBBenefitOnlyXXT() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json",
				"createIncentiveIndividualParticipantBenefitProductTiered");
		cimHelper.addAndValidateIncentive(jsonData, DataHelper.getIncentiveTemplateIdBenefitProductTiered() ,
				benefitProductQnB);

		// ------------ Add QnB Benefit Lines -------------------
		cimHelper.addAndValidateQnBOnIncentive(benefitProductQnB, "XXTBenefitProduct");
	}

	@Test(description = "TC-441 Validation adding the product for Benefit only and Discrete programs", groups = {
			"Regression", "High", "API" })
	public void addQnBBenefitOnlyXXD() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json",
				"createNewIncentiveAgreementAccountBenefitProductDiscrete");
		cimHelper.addAndValidateIncentive(jsonData, DataHelper.getIncentiveTemplateIdBenefitProductDiscrete(),
				benefitProductQnB);

		// ------------ Add QnB Benefit Lines -------------------
		cimHelper.addAndValidateQnBOnIncentive(benefitProductQnB, "XXDBenefitProduct");
	}

	@Test(description = "TC-438 Verify for the deletion of the Product added from the QnB layout", groups = {
			"Regression", "Medium", "API" })
	public void deleteQnBBenefitLine() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json",
				"createIncentiveIndividualParticipantBenefitProductTiered");
		cimHelper.addAndValidateIncentive(jsonData, DataHelper.getIncentiveTemplateIdBenefitProductTiered(),
				benefitProductQnB);

		// ------------ Add QnB Benefit Lines -------------------
		response = cimHelper.addAndValidateQnBOnIncentive(benefitProductQnB, "XXTBenefitProduct");
		benefitProductQnB.setQnBSectionId(response);

		// -------------- Delete QnB Benefit Line ----------------
		cimHelper.deleteQnBAndValidateOnIncenive(benefitProductQnB, "deleteBenefitLine");
	}

	@Test(description = "TC-442 Verify deletion of the Product from the QnB layout", groups = { "Regression", "Medium",
			"API" })
	public void deleteQnBLayoutProduct() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json",
				"createIncentiveIndividualParticipantBenefitProductTiered");
		cimHelper.addAndValidateIncentive(jsonData, DataHelper.getIncentiveTemplateIdBenefitProductTiered(),
				benefitProductQnB);

		// ------------ Add QnB Benefit Lines -------------------
		response = cimHelper.addAndValidateQnBOnIncentive(benefitProductQnB, "XXTBenefitProductAddDletedProduct");
		benefitProductQnB.setQnBSectionId(response);

		// -------------- Delete QnB Benefit Line ----------------
		cimHelper.deleteQnBAndValidateOnIncenive(benefitProductQnB, "deleteBenefitLine");

		// ------- Added previously deleted Product ------------
		cimHelper.addAndValidateQnBOnIncentive(benefitProductQnB, "XXTBenefitProductAddDletedProduct");
	}

	@Test(description = "TC-538 Validate for dates on the QnB for Benefit only and Tiered Incentive", groups = {
			"Regression", "Medium", "API" })
	public void addQnBBenefitOnlyXXDOutsideIncentiveDates() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json",
				"createIncentiveIndividualParticipantBenefitProductTiered");
		cimHelper.addAndValidateIncentive(jsonData, DataHelper.getIncentiveTemplateIdBenefitProductTiered(),
				benefitProductQnB);

		// Add QnB Benefit line with dates outside incentive dates
		jsonArrayData = SFDCHelper.readJsonArray("CIMIncentiveQnBData.json", "XXTBenefitProductOutsideIncentiveDates");
		response = benefitProductQnB.addIncentiveQnBFailure(jsonArrayData);
		responseValidator.validateFailureResponse(response, RebatesConstants.errorCodeApexError,
				RebatesConstants.messageBenefitDatesOutOfRange);
	}

	@Test(description = "TC-461 QnB line update validation on the Benefit only and Tiered programs using in-line edit", groups = {
			"Regression", "High", "API" })
	public void updateQnBBenefitLineXXT() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json",
				"createIncentiveIndividualParticipantBenefitProductTiered");
		cimHelper.addAndValidateIncentive(jsonData, DataHelper.getIncentiveTemplateIdBenefitProductTiered(),
				benefitProductQnB);

		// ------------ Add QnB Benefit Lines -------------------
		response = cimHelper.addAndValidateQnBOnIncentive(benefitProductQnB, "XXTBenefitProduct");
		benefitProductQnB.setQnBSectionId(response);
		benefitProductQnB.setQualificationBenefitAndTierIds(response);

		// -------------- Update QnB Benefit Line ----------------
		cimHelper.addAndValidateQnBOnIncentive(benefitProductQnB, "XXTUpdateBenefitProduct");
	}

	@Test(description = "TC-443 QnB line update validation on the Benefit only and Discrete programs using in-line edit", groups = {
			"Regression", "Medium", "API" })
	public void updateQnBBenefitLineXXD() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json",
				"createNewIncentiveAgreementAccountBenefitProductDiscrete");
		cimHelper.addAndValidateIncentive(jsonData, DataHelper.getIncentiveTemplateIdBenefitProductDiscrete(),
				benefitProductQnB);

		// ------------ Add QnB Benefit Lines -------------------
		response = cimHelper.addAndValidateQnBOnIncentive(benefitProductQnB, "XXDBenefitProduct");
		benefitProductQnB.setQnBSectionId(response);
		benefitProductQnB.setQualificationBenefitAndTierIds(response);

		// -------------- Update QnB Benefit Line ----------------
		cimHelper.addAndValidateQnBOnIncentive(benefitProductQnB, "XXDUpdateBenefitProduct");
	}
}
