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
import com.apttus.sfdc.rebates.lightning.api.library.CIM;
import com.apttus.sfdc.rebates.lightning.api.validator.ResponseValidatorBase;
import com.apttus.sfdc.rebates.lightning.generic.utils.CIMHelper;
import com.apttus.sfdc.rebates.lightning.generic.utils.RebatesConstants;
import com.apttus.sfdc.rebates.lightning.generic.utils.SFDCHelper;
import com.apttus.sfdc.rebates.lightning.main.UnifiedFramework;
import com.apttus.sfdc.rudiments.utils.SFDCRestUtils;
import com.jayway.restassured.response.Response;

public class TestParticipants extends UnifiedFramework {
	private Properties configProperties;
	protected String baseURL;
	private Efficacies efficacies;
	private SFDCRestUtils sfdcRestUtils;
	protected SFDCHelper sfdcHelper;
	private String instanceURL;
	private ResponseValidatorBase responseValidator;
	private CIM cim;
	private Map<String, String> jsonData;
	private List<Map<String, String>> jsonArrayData;
	private Response response;
	private CIMHelper cimHelper;

	@BeforeClass(alwaysRun = true)
	@Parameters({ "runParallel", "environment", "browser", "hubURL" })
	public void beforeClass(String runParallel, String environment, String browser, String hubURL) throws Exception {
		efficacies = new Efficacies();
		sfdcRestUtils = new SFDCRestUtils();
		configProperties = efficacies.loadPropertyFile(environment);
		baseURL = configProperties.getProperty("baseURL");
		SFDCHelper.setMasterProperty(configProperties);
		instanceURL = SFDCHelper.setAccessToken(sfdcRestUtils);
		cim = new CIM(instanceURL, sfdcRestUtils);
		cimHelper = new CIMHelper();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() throws Exception {
		responseValidator = new ResponseValidatorBase();
		jsonArrayData = new ArrayList<Map<String, String>>();
	}

	@Test(description = "TC 377 Verify adding a participant to a Incentive", groups = { "Regression", "API", "High" })
	public void addParticipant() throws Exception {		
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "createIncentiveIndividualParticipantBenefitProductTiered");
		cimHelper.addAndValidateIncentive(jsonData,
				RebatesConstants.incentiveTemplateIdBenefitProductTiered, cim);	
		cimHelper.addAndValidateParticipant(cim, "addParticipants");
	}

	@Test(description = "TC-384 Verify that the user is able to delete a participant added from the grid", groups = {
			"Regression", "API", "Medium" })
	public void deleteParticipant() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "createIncentiveIndividualParticipantBenefitProductTiered");
		cimHelper.addAndValidateIncentive(jsonData,
				RebatesConstants.incentiveTemplateIdBenefitProductTiered, cim);

		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "addParticipants");
		cim.addParticipants(jsonData);
		jsonData.put("Account__c", cim.getParticipantData().getIncentiveParticipant().getAccount__c());
		jsonData.put("EffectiveDate__c", cim.getParticipantData().getIncentiveParticipant().getEffectiveDate__c());
		jsonData.put("ExpirationDate__c", cim.getParticipantData().getIncentiveParticipant().getExpirationDate__c());
		jsonArrayData.add(jsonData);
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "addParticipantTwo");
		cim.addParticipants(jsonData);
		jsonData.put("Account__c", cim.getParticipantData().getIncentiveParticipant().getAccount__c());
		jsonData.put("EffectiveDate__c", cim.getParticipantData().getIncentiveParticipant().getEffectiveDate__c());
		jsonData.put("ExpirationDate__c", cim.getParticipantData().getIncentiveParticipant().getExpirationDate__c());
		jsonArrayData.add(jsonData);
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "addParticipantThree");
		cim.addParticipants(jsonData);
		cim.deleteParticipants();
		response = cim.getParticipantIdViaIncentiveId();
		responseValidator.validateAvailableParticipant(jsonArrayData, response, cim);
	}

	@Test(description = "TC-381 Check validations on the effective start date and end date of the Participants", groups = {
			"Regression", "API", "Medium" })
	public void addParticipantDatevalidation() throws Exception {		
		jsonData = efficacies.readJsonElement("CIMTemplateData.json","createNewIncentiveAgreementAccountBenefitProductDiscrete");
		cimHelper.addAndValidateIncentive(jsonData,
				RebatesConstants.incentiveTemplateIdBenefitProductDiscrete, cim);

		// ----------- Add Participants with dates outside Incentive dates ------------
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "addParticipantsOutsideIncentiveDates");
		response = cim.addParticipantsFailure(jsonData);
		responseValidator.validateParticipantFailureResponse(response, RebatesConstants.errorFieldsForDates,
				RebatesConstants.messageParticipantsDateOutOfRange);

		// ----------- Add Participants with dates same as Incentive dates ------------
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "addParticipantsSameAsIncentiveDates");
		cim.addParticipants(jsonData);
		response = cim.getParticipantsDetails();
		responseValidator.validateParticipantsDetails(jsonData, response, cim);

		// ----------- Update Participants dates outside Incentive dates ------------
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "addParticipantsOutsideIncentiveDates");
		response = cim.addParticipantsFailure(jsonData);
		responseValidator.validateParticipantFailureResponse(response, RebatesConstants.errorFieldsForDates,
				RebatesConstants.messageParticipantsDateOutOfRange);
	}

	@Test(description = "TC-388 Verify for the error message displayed when the same participant is added multiple times with an overlapping time period", groups = {
			"Regression", "API", "Medium" })
	public void addOverlappingParticipant() throws Exception {		
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "createIncentiveIndividualParticipantForPayeeAndMeasurementLevelBenefitProductTiered");
		cimHelper.addAndValidateIncentive(jsonData,
				RebatesConstants.incentiveTemplateIdBenefitProductTiered, cim);

		// -------- Add Participant 1-----------------
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "addParticipantsForOverlappingDates");
		cim.addParticipants(jsonData);
		response = cim.getParticipantsDetails();
		responseValidator.validateParticipantsDetails(jsonData, response, cim);

		// -------- Add Participant 1 with same dates -----------------
		response = cim.addParticipantsFailure(jsonData);
		responseValidator.validateParticipantFailureResponse(response, RebatesConstants.errorFieldsForDates,
				RebatesConstants.messageOverlappingParticipants);

		// -------- Add Participant 1 with overlapping dates -----------------
		jsonData.put("ExpirationDate__c", "incentiveenddate");
		jsonData.put("EffectiveDate__c", "incentivestartdate=+5");
		response = cim.addParticipantsFailure(jsonData);
		responseValidator.validateParticipantFailureResponse(response, RebatesConstants.errorFieldsForDates,
				RebatesConstants.messageOverlappingParticipants);

		// -------- Add Participant 1 again with No overlapping dates -----------------
		jsonData.put("ExpirationDate__c", "incentiveenddate");
		jsonData.put("EffectiveDate__c", "incentivestartdate=+11");
		cim.addParticipants(jsonData);
		response = cim.getParticipantsDetails();
		responseValidator.validateParticipantsDetails(jsonData, response, cim);
	}

	@Test(description = "TC-554 Verify Editing a participant to a program", groups = { "Regression", "API", "Medium" })
	public void updateEditParticipant() throws Exception {		
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "createIncentiveIndividualParticipantForPayeeAndMeasurementLevelBenefitProductTiered");
		cimHelper.addAndValidateIncentive(jsonData,
				RebatesConstants.incentiveTemplateIdBenefitProductTiered, cim);

		// -------- Add Participant 1-----------------
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "addParticipantsSameAsIncentiveDates");
		cim.addParticipants(jsonData);
		response = cim.getParticipantsDetails();
		responseValidator.validateParticipantsDetails(jsonData, response, cim);

		// ------- Edit Participant 1 and replace it with participant 2-------------
		jsonData.put("AccountName", "Automation_Participant_Account_2");
		cim.updateParticipants(jsonData);
		response = cim.getParticipantsDetails();
		responseValidator.validateParticipantsDetails(jsonData, response, cim);

		// -------- Update Participant 2 Start and End date -----------------
		jsonData.put("ExpirationDate__c", "incentiveenddate=-5");
		jsonData.put("EffectiveDate__c", "incentivestartdate=+5");
		cim.updateParticipants(jsonData);
		response = cim.getParticipantsDetails();
		responseValidator.validateParticipantsDetails(jsonData, response, cim);
	}
}
