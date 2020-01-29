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
	private Response response;
	private String incentiveTemplateIdTiered;
	private String incentiveTemplateIdDiscrete;

	@BeforeClass(alwaysRun = true)
	@Parameters({ "runParallel", "environment", "browser", "hubURL" })
	public void beforeClass(String runParallel, String environment, String browser, String hubURL) throws Exception {
		efficacies = new Efficacies();
		sfdcRestUtils = new SFDCRestUtils();
		configProperties = efficacies.loadPropertyFile(environment);
		SFDCHelper.setMasterProperty(configProperties);
		instanceURL = SFDCHelper.setAccessToken(sfdcRestUtils);
		cim = new CIM(instanceURL, sfdcRestUtils);
		
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "activeTemplateIdForRebateDiscrete");
		incentiveTemplateIdDiscrete = cim.getTemplateIdForIncentives(jsonData);
		
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "activeTemplateIdForRebateTiered");
		incentiveTemplateIdTiered = cim.getTemplateIdForIncentives(jsonData);
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() throws Exception {
		responseValidator = new ResponseValidatorBase();
	}
	
	@Test(description = "TC-419 Verify Incentive creation for different Payee values", groups = { "Regression",
			"Medium", "API" })
	public void addIncentiveWithDifferentPayeeValues() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "createNewIncentiveIndividualParticipant");
		jsonData.put("ProgramTemplateId__c", incentiveTemplateIdTiered);
		cim.createNewIncentive(jsonData);
		response = cim.getIncentiveDetails();
		responseValidator.validateIncentiveDetails(jsonData, response, cim);

		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "createNewIncentiveAgreementAccount");
		jsonData.put("ProgramTemplateId__c", incentiveTemplateIdDiscrete);
		cim.createNewIncentive(jsonData);
		response = cim.getIncentiveDetails();
		responseValidator.validateIncentiveDetails(jsonData, response, cim);
	}

	@Test(description = "TC345-Verify the creation of new Incentive", groups = { "Smoke", "API" })
	public void createNewLoyaltyIncentive() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "createNewIncentiveIndividualParticipant");
		jsonData.put("ProgramTemplateId__c", incentiveTemplateIdTiered);
		cim.createNewIncentive(jsonData);
		response = cim.getIncentiveDetails();
		responseValidator.validateIncentiveDetails(jsonData, response, cim);
	}

	@Test(description = "TC420-Update Incentive Payee field on Edit page", groups = { "Regression", "High", "API" })
	public void updateIncentivePayee() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "createNewIncentiveIndividualParticipant");		
		jsonData.put("ProgramTemplateId__c", incentiveTemplateIdTiered);
		cim.createNewIncentive(jsonData);
		jsonDataTemp = efficacies.readJsonElement("CIMTemplateData.json", "updateIncentive");
		jsonData = SFDCHelper.overrideJSON(jsonData, jsonDataTemp);
		cim.updateIncentive(jsonData);
		response = cim.getIncentiveDetails();
		responseValidator.validateIncentiveDetails(jsonData, response, cim);
	}	 
}
