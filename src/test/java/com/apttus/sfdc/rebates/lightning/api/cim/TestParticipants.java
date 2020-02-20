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
import com.apttus.sfdc.rebates.lightning.generic.utils.RebatesConstants;
import com.apttus.sfdc.rebates.lightning.generic.utils.SFDCHelper;
import com.apttus.sfdc.rudiments.utils.SFDCRestUtils;
import com.jayway.restassured.response.Response;

public class TestParticipants {
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
		
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "activeTemplateIdForRebateTiered");
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() throws Exception {
		responseValidator = new ResponseValidatorBase();
		jsonArrayData = new ArrayList<Map<String,String>>();
	}

	@Test(description = "TC 377 Verify adding a participant to a Incentive", groups = { "Regression", "API", "High" })
	public void addParticipant() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "createIncentiveIndividualParticipantBenefitProductTiered");		
		jsonData.put("ProgramTemplateId__c", RebatesConstants.incentiveTemplateIdBenefitProductTiered);
		cim.createNewIncentive(jsonData);
		response = cim.getIncentiveDetails();
		responseValidator.validateIncentiveDetails(jsonData, response, cim);
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "addParticipants");
		cim.addParticipants(jsonData);
		response = cim.getParticipantsDetails();
		responseValidator.validateParticipantsDetails(jsonData, response, cim);
	}

	@Test(description = "TC-384 Verify that the user is able to delete a participant added from the grid", groups = {
			"Regression", "API", "Medium" })
	public void deleteParticipant() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "createIncentiveIndividualParticipantBenefitProductTiered");
		jsonData.put("ProgramTemplateId__c", RebatesConstants.incentiveTemplateIdBenefitProductTiered);
		cim.createNewIncentive(jsonData);
		response = cim.getIncentiveDetails();
		responseValidator.validateIncentiveDetails(jsonData, response, cim);

		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "addParticipants");
		cim.addParticipants(jsonData);
		jsonArrayData.add(jsonData);
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "addParticipantTwo");
		cim.addParticipants(jsonData);
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
		jsonData = efficacies.readJsonElement("CIMTemplateData.json",
				"createNewIncentiveAgreementAccountBenefitProductDiscrete");
		jsonData.put("ProgramTemplateId__c", RebatesConstants.incentiveTemplateIdBenefitProductDiscrete);
		cim.createNewIncentive(jsonData);
		response = cim.getIncentiveDetails();
		responseValidator.validateIncentiveDetails(jsonData, response, cim);
		
		//----------- Add Participants with dates outside Incentive dates ------------
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "addParticipantsOutsideIncentiveDates");
		response = cim.addParticipantsNegative(jsonData);
		responseValidator.validateFailureResponse(response, RebatesConstants.errorCodeCustomValidation,
				RebatesConstants.messageParticipantsDateOutOfRange);

		//----------- Add Participants with dates same as Incentive dates ------------
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "addParticipantsSameAsIncentiveDates");
		cim.addParticipants(jsonData);
		response = cim.getParticipantsDetails();
		responseValidator.validateParticipantsDetails(jsonData, response, cim);

		//----------- Update Participants dates outside Incentive dates ------------
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "addParticipantsOutsideIncentiveDates");
		response = cim.updateParticipantsNegative(jsonData);
		responseValidator.validateFailureResponse(response, RebatesConstants.errorCodeCustomValidation,
				RebatesConstants.messageParticipantsDateOutOfRange);
	}
}
