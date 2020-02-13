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
import com.apttus.sfdc.rebates.lightning.api.library.CIM;
import com.apttus.sfdc.rebates.lightning.api.validator.BenefitProductValidator;
import com.apttus.sfdc.rebates.lightning.generic.utils.RebatesConstants;
import com.apttus.sfdc.rebates.lightning.generic.utils.SFDCHelper;
import com.apttus.sfdc.rudiments.utils.SFDCRestUtils;
import com.jayway.restassured.response.Response;

public class TestIncentives {
	private Properties configProperties;
	private Efficacies efficacies;
	private SFDCRestUtils sfdcRestUtils;
	private String instanceURL;
	private BenefitProductValidator responseValidator;
	private CIM cim;
	private Map<String, String> jsonData;
	private Map<String, String> jsonDataTemp;	
	private Response response;
	private BenefitProductQnB benefitProductQnB;
	private List<Map<String, String>> jsonArrayData;

	@BeforeClass(alwaysRun = true)
	@Parameters({ "runParallel", "environment", "browser", "hubURL" })
	public void beforeClass(String runParallel, String environment, String browser, String hubURL) throws Exception {
		efficacies = new Efficacies();
		sfdcRestUtils = new SFDCRestUtils();
		configProperties = efficacies.loadPropertyFile(environment);
		SFDCHelper.setMasterProperty(configProperties);
		instanceURL = SFDCHelper.setAccessToken(sfdcRestUtils);
		cim = new CIM(instanceURL, sfdcRestUtils);
		benefitProductQnB = new BenefitProductQnB(instanceURL, sfdcRestUtils);
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() throws Exception {
		responseValidator = new BenefitProductValidator();
	}
	
	@Test(description = "TC-419 Verify Incentive creation for different Payee values", groups = { "Regression",
			"Medium", "API" })
	public void addIncentiveWithDifferentPayeeValues() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "createIncentiveIndividualParticipantBenefitProductTiered");
		jsonData.put("ProgramTemplateId__c", RebatesConstants.incentiveTemplateIdBenefitProductTiered);
		cim.createNewIncentive(jsonData);
		response = cim.getIncentiveDetails();
		responseValidator.validateIncentiveDetails(jsonData, response, cim);

		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "createNewIncentiveAgreementAccountBenefitProductDiscrete");
		jsonData.put("ProgramTemplateId__c", RebatesConstants.incentiveTemplateIdBenefitProductDiscrete);
		cim.createNewIncentive(jsonData);
		response = cim.getIncentiveDetails();
		responseValidator.validateIncentiveDetails(jsonData, response, cim);
	}

	@Test(description = "TC-345 Verify the creation of new Incentive", groups = { "Smoke", "API" })
	public void createNewBenefitProductTieredIncentive() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "createIncentiveIndividualParticipantBenefitProductTiered");
		jsonData.put("ProgramTemplateId__c", RebatesConstants.incentiveTemplateIdBenefitProductTiered);
		cim.createNewIncentive(jsonData);
		response = cim.getIncentiveDetails();
		responseValidator.validateIncentiveDetails(jsonData, response, cim);
	}

	@Test(description = "TC- 420 Update Incentive Payee field on Edit page", groups = { "Regression", "High", "API" })
	public void updateIncentivePayee() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "createIncentiveIndividualParticipantBenefitProductTiered");		
		jsonData.put("ProgramTemplateId__c", RebatesConstants.incentiveTemplateIdBenefitProductTiered);
		cim.createNewIncentive(jsonData);
		jsonDataTemp = efficacies.readJsonElement("CIMTemplateData.json", "updateIncentive");
		jsonData = SFDCHelper.overrideJSON(jsonData, jsonDataTemp);
		cim.updateIncentive(jsonData);
		response = cim.getIncentiveDetails();
		responseValidator.validateIncentiveDetails(jsonData, response, cim);
	}
	
	@Test(description = "TC-536 Verify for the Program Activation Using the Activate button", groups = { "Smoke",
			"API" })
	public void activateIncentive() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json",
				"createIncentiveIndividualParticipantBenefitProductTiered");
		jsonData.put("ProgramTemplateId__c", RebatesConstants.incentiveTemplateIdBenefitProductTiered);
		benefitProductQnB.createNewIncentive(jsonData);
		response = benefitProductQnB.getIncentiveDetails();
		responseValidator.validateIncentiveDetails(jsonData, response, benefitProductQnB);

		// ------------ Add QnB Benefit Lines -------------------
		jsonArrayData = SFDCHelper.readJsonArray("CIMIncentiveQnBData.json", "XXTBenefitProduct");
		benefitProductQnB.addIncentiveQnB(jsonArrayData);
		response = benefitProductQnB.getIncentiveQnB();
		responseValidator.validateIncentiveQnB(benefitProductQnB.getRequestValue("addQnBRequest"), response);

		// ------------ Add Incentive Participants -------------------
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "addParticipants");
		benefitProductQnB.addParticipants(jsonData);
		response = benefitProductQnB.getParticipantsDetails();
		responseValidator.validateParticipantsDetails(jsonData, response, benefitProductQnB);

		// ------------ Activate Incentive -------------------
		benefitProductQnB.activateIncentive();
		response = benefitProductQnB.getIncentiveDetails();
		responseValidator.validateIncentiveStatus(RebatesConstants.statusActivated, response,
				benefitProductQnB.getIncentiveData().incentiveId);
	}
}
