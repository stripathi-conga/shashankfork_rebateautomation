package com.apttus.sfdc.rebates.lightning.api.cim;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.apttus.helpers.Efficacies;
import com.apttus.sfdc.rebates.lightning.api.library.CIM;
import com.apttus.sfdc.rebates.lightning.api.validator.ResponseValidatorBase;
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
	private HashMap<String, String> tempjsonData;
	private Response response;
	private String incentiveTemplateIdTiered;

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
		incentiveTemplateIdTiered = cim.getTemplateIdForIncentives(jsonData);
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() throws Exception {
		responseValidator = new ResponseValidatorBase();
		tempjsonData = new HashMap<String, String>();
	}

	@Test(description = "TC 377 Verify adding a participant to a Incentive", groups = { "Regression", "API", "High" })
	public void addParticipant() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "createNewIncentiveIndividualParticipant");		
		jsonData.put("ProgramTemplateId__c", incentiveTemplateIdTiered);
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
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "createNewIncentiveIndividualParticipant");
		jsonData.put("ProgramTemplateId__c", incentiveTemplateIdTiered);
		cim.createNewIncentive(jsonData);
		response = cim.getIncentiveDetails();
		responseValidator.validateIncentiveDetails(jsonData, response, cim);

		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "addParticipants");
		cim.addParticipants(jsonData);
		String accountId = cim.getParticipantData().getAccount__c();
		tempjsonData.put("Automation_Participant_Account_1", accountId);
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "addParticipantTwo");
		cim.addParticipants(jsonData);
		accountId = cim.getParticipantData().getAccount__c();
		tempjsonData.put("Automation_Participant_Account_2", accountId);
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "addParticipantThree");
		cim.addParticipants(jsonData);
		cim.deleteParticipants();
		response = cim.getParticipantIdViaIncentiveId();
		responseValidator.validateAvailableParticipant(tempjsonData, response);
	}
}
