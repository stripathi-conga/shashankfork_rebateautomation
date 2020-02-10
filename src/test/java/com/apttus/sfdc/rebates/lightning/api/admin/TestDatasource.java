package com.apttus.sfdc.rebates.lightning.api.admin;

import java.util.Map;
import java.util.Properties;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.apttus.helpers.Efficacies;
import com.apttus.sfdc.rebates.lightning.api.library.CIMAdmin;
import com.apttus.sfdc.rebates.lightning.api.validator.ResponseValidatorBase;
import com.apttus.sfdc.rebates.lightning.generic.utils.RebatesConstants;
import com.apttus.sfdc.rebates.lightning.generic.utils.SFDCHelper;
import com.apttus.sfdc.rebates.lightning.main.UnifiedFramework;
import com.apttus.sfdc.rudiments.utils.SFDCRestUtils;
import com.jayway.restassured.response.Response;

public class TestDatasource extends UnifiedFramework {

	private Properties configProperties;
	private Efficacies efficacies;
	private SFDCRestUtils sfdcRestUtils;
	private String instanceURL;
	private ResponseValidatorBase responseValidator;
	private CIMAdmin cimAdmin;
	private Map<String, String> jsonData;
	private Map<String, String> jsonDataTemp;
	private Response response;

	@BeforeClass(alwaysRun = true)
	@Parameters({ "runParallel", "environment", "browser", "hubURL" })
	public void beforeClass(String runParallel, String environment, String browser, String hubURL) throws Exception {
		efficacies = new Efficacies();
		sfdcRestUtils = new SFDCRestUtils();
		configProperties = efficacies.loadPropertyFile(environment);
		SFDCHelper.setMasterProperty(configProperties);
		instanceURL = SFDCHelper.setAccessToken(sfdcRestUtils);
		cimAdmin = new CIMAdmin(instanceURL, sfdcRestUtils);
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() throws Exception {
		responseValidator = new ResponseValidatorBase();
	}

	
	@Test(description = "TC215-Verify Create New Data Source", groups = { "Smoke", "API" })
	public void createNewDataSource() throws Exception {
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewDataSourceAPI");
		response = cimAdmin.createDataSource(jsonData);
		responseValidator.validateCreateSuccess(response);
		response = cimAdmin.getDataSource();
		responseValidator.validateGetDataSource(response, cimAdmin);
		cimAdmin.deleteDataSource();
		response = cimAdmin.getDataSource();
		responseValidator.validateDeleteSuccess(response);
	}

	@Test(description = "TC436-Verify creation of formula and link to data source", groups = { "Regression", "High",
			"API" })
	public void createCalculationFormula() throws Exception {
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createFieldExpressionId");
		String fieldExpressionId = cimAdmin.getFieldExpressionId(jsonData);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createCalcFormulaIdBenefit");
		String calcFormulaIdBenefit = cimAdmin.getCalcFormulaId(jsonData);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createCalcFormulaIdQualification");
		String calcFormulaIdQualification = cimAdmin.getCalcFormulaId(jsonData);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "linkCalcFormulaToExpressionId");
		cimAdmin.linkCalcFormulaToExpression(jsonData, calcFormulaIdBenefit, fieldExpressionId);
		cimAdmin.linkCalcFormulaToExpression(jsonData, calcFormulaIdQualification, fieldExpressionId);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewDataSourceAPI");
		cimAdmin.createDataSource(jsonData);
		cimAdmin.linkDatasourceToCalcFormula(calcFormulaIdBenefit);
		cimAdmin.linkDatasourceToCalcFormula(calcFormulaIdQualification);
		cimAdmin.deleteDataSource();
		response = cimAdmin.getDataSource();
		responseValidator.validateDeleteSuccess(response);
		 
	}

	@Test(description = "TC371- Verify the Data Source with multiple combination", groups = { "Regression", "Medium",
			"API" })
	public void createDataSourceMultipleFileExtension() throws Exception {
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewDataSourceAPI");
		jsonDataTemp = efficacies.readJsonElement("CIMAdminTemplateData.json",
				"createNewDataSourceMultipleFileExtension");
		jsonData = SFDCHelper.overrideJSON(jsonData, jsonDataTemp);
		response = cimAdmin.createDataSource(jsonData);
		responseValidator.validateCreateSuccess(response);
		response = cimAdmin.getDataSource();
		responseValidator.validateGetDataSource(response, cimAdmin);
		cimAdmin.deleteDataSource();
		response = cimAdmin.getDataSource();
		responseValidator.validateDeleteSuccess(response);
	}
	 
	@Test(description = "TC-225 Verify mandatory fields in Data Source", groups = { "Regression", "Medium", "API" })
	public void verifyDataSourceMandatoryFields() throws Exception {
		response = cimAdmin.createDataSourceWithoutAnyFields();
		responseValidator.validateFailureResponse(response, RebatesConstants.errorCodeMissingFields,
				RebatesConstants.messageCreateDataSourceWithoutFields);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewDataSourceAPI");
		jsonDataTemp = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewDataSourceTwoFileExtension");
		jsonData = SFDCHelper.overrideJSON(jsonData, jsonDataTemp);
		response = cimAdmin.createDataSource(jsonData);
		responseValidator.validateCreateSuccess(response);
		response = cimAdmin.getDataSource();
		responseValidator.validateGetDataSource(response, cimAdmin);
		cimAdmin.deleteDataSource();
		response = cimAdmin.getDataSource();
		responseValidator.validateDeleteSuccess(response);
	}

}
