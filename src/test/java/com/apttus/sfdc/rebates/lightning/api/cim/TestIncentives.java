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

public class TestIncentives {
	private Properties configProperties;
	protected String baseURL;
	private Efficacies efficacies;
	private SFDCRestUtils sfdcRestUtils;
	protected SFDCHelper sfdcHelper;
	private String instanceURL;
	private ResponseValidatorBase responseValidator;
	private CIM cim;
	private Map<String, String> jsonData;
	private Map<String, String> jsonDataTemp;
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
		sfdcHelper = new SFDCHelper(instanceURL);
		cim = new CIM(instanceURL, sfdcRestUtils);
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() throws Exception {
		responseValidator = new ResponseValidatorBase();
	}

	@Test(description = "TC345-Verify the creation of New Rebate Program", groups = { "Smoke", "API" })
	public void createNewLoyaltyProgram() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "createNewProgram");
		String programTemplateId = cim.getTemplateIdForProgram(jsonData);
		jsonData.put("Program_Template_Id__c", programTemplateId);
		cim.creatNewProgram(jsonData);
		response = cim.getProgramDetails();
		responseValidator.validateProgramDetails(jsonData, response, cim);
	}

	@Test(description = "TC420-Update Program Payee field on Edit page", groups = { "Regression", "High", "API" })
	public void updateProgramPayee() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "createNewProgram");
		String programTemplateId = cim.getTemplateIdForProgram(jsonData);
		jsonData.put("Program_Template_Id__c", programTemplateId);
		cim.creatNewProgram(jsonData);
		jsonDataTemp = efficacies.readJsonElement("CIMTemplateData.json", "updateProgram");
		jsonData = SFDCHelper.overrideJSON(jsonData, jsonDataTemp);
		cim.updateProgram(jsonData);
		response = cim.getProgramDetails();
		responseValidator.validateProgramDetails(jsonData, response, cim);
	}
}
