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

	@Test(description = "TC 377 Verify adding a participant to a program", groups = { "Regression", "API", "High" })
	public void addIncentiveParticipant() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "createNewProgram");
		String programTemplateId = cim.getTemplateIdForProgram(jsonData);
		jsonData.put("Program_Template_Id__c", programTemplateId);
		cim.creatNewProgram(jsonData);
		response = cim.getProgramDetails();
		String incentiveProgram = cim.programData.getProgramId();
		responseValidator.validateProgramDetails(jsonData, response, cim);

		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "addIncentiveParticiant");
		cim.addIncentiveParticipant(jsonData, incentiveProgram);
		response = cim.getIncentiveParticipantDetails();
		responseValidator.validateIncentiveParticipantDetails(jsonData, response, cim);
	}
}
