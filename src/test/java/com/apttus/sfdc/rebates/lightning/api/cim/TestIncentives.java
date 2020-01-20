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
	private Efficacies efficacies;
	private SFDCRestUtils sfdcRestUtils;
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
		SFDCHelper.setMasterProperty(configProperties);
		instanceURL = SFDCHelper.setAccessToken(sfdcRestUtils);
		cim = new CIM(instanceURL, sfdcRestUtils);
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() throws Exception {
		responseValidator = new ResponseValidatorBase();
	}

	@Test(description = "TC345-Verify the creation of New Rebate Program", groups = { "Smoke", "API" })
	public void createNewLoyaltyIncentive() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "createNewIncentive");
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "createNewIncentive");
		String programTemplateId = cim.getTemplateIdForIncentives(jsonData);
		jsonData.put("Program_Template_Id__c", programTemplateId);
		cim.createNewIncentive(jsonData);
		response = cim.getIncentiveDetails();
		responseValidator.validateIncentiveDetails(jsonData, response, cim);
	}

	@Test(description = "TC420-Update Program Payee field on Edit page", groups = { "Regression", "High", "API" })
	public void updateIncentivePayee() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "createNewIncentive");
		String programTemplateId = cim.getTemplateIdForIncentives(jsonData);
		jsonData.put("Program_Template_Id__c", programTemplateId);
		cim.createNewIncentive(jsonData);
		jsonDataTemp = efficacies.readJsonElement("CIMTemplateData.json", "updateIncentive");
		jsonData = SFDCHelper.overrideJSON(jsonData, jsonDataTemp);
		cim.updateIncentive(jsonData);
		response = cim.getIncentiveDetails();
		responseValidator.validateIncentiveDetails(jsonData, response, cim);
	}
}
