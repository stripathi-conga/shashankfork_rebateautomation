package com.apttus.sfdc.rebates.lightning.api.cim;

import java.util.ArrayList;
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
import com.apttus.sfdc.rebates.lightning.main.UnifiedFramework;
import com.apttus.sfdc.rudiments.utils.SFDCRestUtils;
import com.jayway.restassured.response.Response;

public class TestIncentives extends UnifiedFramework {
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
		jsonArrayData = new ArrayList<Map<String,String>>();
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
	public void updateIncentivePayeeToAgreementAccount() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "createIncentiveIndividualParticipantBenefitProductTiered");		
		jsonData.put("ProgramTemplateId__c", RebatesConstants.incentiveTemplateIdBenefitProductTiered);
		cim.createNewIncentive(jsonData);
		jsonDataTemp = efficacies.readJsonElement("CIMTemplateData.json", "updateIncentivePayeeToAgreementAccount");
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

	@Test(description = "TC-536 Verify for the Program Activation Using the Activate button", groups = { "Regression",
			"High", "API" })
	public void activateIncentiveWithMultipleQnBAndParticipantsXXD() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json",
				"createNewIncentiveAgreementAccountBenefitProductDiscrete");
		jsonDataTemp = efficacies.readJsonElement("CIMTemplateData.json",
				"createNewIncentiveAgreementAccountBenefitProductDiscreteTwoMonths");
		jsonData = SFDCHelper.overrideJSON(jsonData, jsonDataTemp);

		jsonData.put("ProgramTemplateId__c", RebatesConstants.incentiveTemplateIdBenefitProductDiscrete);
		benefitProductQnB.createNewIncentive(jsonData);
		response = benefitProductQnB.getIncentiveDetails();
		responseValidator.validateIncentiveDetails(jsonData, response, benefitProductQnB);

		// ------------ Add QnB Benefit Lines -------------------
		jsonArrayData = SFDCHelper.readJsonArray("CIMIncentiveQnBData.json", "XXDBenefitProductFourBenefits");
		benefitProductQnB.addIncentiveQnB(jsonArrayData);
		response = benefitProductQnB.getIncentiveQnB();
		responseValidator.validateIncentiveQnB(benefitProductQnB.getRequestValue("addQnBRequest"), response);

		// ------------ Add Incentive Participants -------------------
		jsonArrayData.clear();
		jsonDataTemp = efficacies.readJsonElement("CIMTemplateData.json", "setParticipantDatesForTwoMonth");
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "addParticipants");
		jsonData = SFDCHelper.overrideJSON(jsonData, jsonDataTemp);
		benefitProductQnB.addParticipants(jsonData);
		jsonData.put("Account__c", benefitProductQnB.getParticipantData().getAccount__c());
		jsonData.put("EffectiveDate__c", benefitProductQnB.getParticipantData().getEffectiveDate__c());
		jsonData.put("ExpirationDate__c", benefitProductQnB.getParticipantData().getExpirationDate__c());
		jsonArrayData.add(jsonData);

		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "addParticipantTwo");
		jsonData = SFDCHelper.overrideJSON(jsonData, jsonDataTemp);
		benefitProductQnB.addParticipants(jsonData);
		jsonData.put("Account__c", benefitProductQnB.getParticipantData().getAccount__c());
		jsonData.put("EffectiveDate__c", benefitProductQnB.getParticipantData().getEffectiveDate__c());
		jsonData.put("ExpirationDate__c", benefitProductQnB.getParticipantData().getExpirationDate__c());
		jsonArrayData.add(jsonData);
		response = benefitProductQnB.getParticipantIdViaIncentiveId();
		responseValidator.validateAvailableParticipant(jsonArrayData, response, cim);

		// ------------ Activate Incentive -------------------
		benefitProductQnB.activateIncentive();
		response = benefitProductQnB.getIncentiveDetails();
		responseValidator.validateIncentiveStatus(RebatesConstants.statusActivated, response,
				benefitProductQnB.getIncentiveData().incentiveId);
	}

	@Test(description = "TC- 549 Select measurement level as Individual Participant", groups = { "Regression", "Medium",
			"API" })
	public void addIncentiveWithPayeeAndMeasurementAsIndividualParticipantBenefitOnly() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json",
				"createIncentiveIndividualParticipantForPayeeAndMeasurementLevelBenefitProductTiered");
		jsonData.put("ProgramTemplateId__c", RebatesConstants.incentiveTemplateIdBenefitProductTiered);
		cim.createNewIncentive(jsonData);
		response = cim.getIncentiveDetails();
		responseValidator.validateIncentiveDetails(jsonData, response, cim);
	}

	@Test(description = "TC-548 Select measurement level Agreement Account first to select payee value", groups = {
			"Regression", "Medium", "API" })
	public void updateIncentivePayeeToIndividualparticipant() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json",
				"createIncentiveAgreementAccountForPayeeAndMeasurementLevelBenefitProductDiscrete");
		jsonData.put("ProgramTemplateId__c", RebatesConstants.incentiveTemplateIdBenefitProductDiscrete);
		cim.createNewIncentive(jsonData);
		jsonData.put("BenefitLevel__c", "Individual Participants");
		cim.updateIncentive(jsonData);
		response = cim.getIncentiveDetails();
		responseValidator.validateIncentiveDetails(jsonData, response, cim);
	}
}
