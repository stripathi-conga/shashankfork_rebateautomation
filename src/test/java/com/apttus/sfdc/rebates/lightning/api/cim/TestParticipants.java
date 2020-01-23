package com.apttus.sfdc.rebates.lightning.api.cim;

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
	Response response;

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
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() throws Exception {
		responseValidator = new ResponseValidatorBase();
	}

	@Test(description = "TC 377 Verify adding a participant to a Incentive", groups = { "Regression", "API", "High" })
	public void addParticipant() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "createNewIncentive");
		String incentiveTemplateId = cim.getTemplateIdForIncentives(jsonData);
		jsonData.put("Program_Template_Id__c", incentiveTemplateId);
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
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "createNewIncentive");
		String incentiveTemplateId = cim.getTemplateIdForIncentives(jsonData);
		jsonData.put("Program_Template_Id__c", incentiveTemplateId);
		cim.createNewIncentive(jsonData);
		response = cim.getIncentiveDetails();
		
		responseValidator.validateIncentiveDetails(jsonData, response, cim);
		
		 Map<String, String> jsonDataParticipant1 = efficacies.readJsonElement("CIMTemplateData.json", "addParticipants");
		cim.addParticipants(jsonDataParticipant1);
		
		 Map<String, String> jsonDataParticipant2=efficacies.readJsonElement("CIMTemplateData.json", "addParticipantTwo");
		cim.addParticipants(jsonDataParticipant2);
		
		 Map<String, String> jsonData3=efficacies.readJsonElement("CIMTemplateData.json", "addParticipantThree");
		cim.addParticipants(jsonData3);
		
		response = cim.getParticipantsDetails();
		responseValidator.validateParticipantsDetails(jsonData, response, cim);
		
		cim.deleteParticipants();
		response = cim.getParticipantsDetails();
		
		response=cim.getParticipantIdViaIncentiveId();
		responseValidator.validateAvailableParticipant(jsonData, response, cim,jsonDataParticipant1,jsonDataParticipant2);
		
	}
}
