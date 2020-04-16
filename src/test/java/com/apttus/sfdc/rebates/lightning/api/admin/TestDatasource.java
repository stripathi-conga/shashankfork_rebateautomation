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
import com.apttus.sfdc.rebates.lightning.generic.utils.CIMAdminHelper;
import com.apttus.sfdc.rebates.lightning.generic.utils.DataHelper;
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
	private CIMAdminHelper cimAdminHelper;

	@BeforeClass(alwaysRun = true)
	@Parameters({ "runParallel", "environment", "browser", "hubURL" })
	public void beforeClass(String runParallel, String environment, String browser, String hubURL) throws Exception {
		DataHelper dataHelper = DataHelper.getInstance();
		dataHelper.getData(environment);
		
		efficacies = new Efficacies();
		sfdcRestUtils = new SFDCRestUtils();
		configProperties = efficacies.loadPropertyFile(environment);
		SFDCHelper.setMasterProperty(configProperties);
		instanceURL = SFDCHelper.setAccessToken(sfdcRestUtils);
		cimAdmin = new CIMAdmin(instanceURL, sfdcRestUtils);
		cimAdminHelper = new CIMAdminHelper();
		
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() throws Exception {
		responseValidator = new ResponseValidatorBase();
	}

	@Test(description = "TC215-Verify Create New Data Source", groups = { "Smoke", "API" })
	public void createNewDataSource() throws Exception {
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewDataSourceAccountBillingSummary");
		String dataSourceId = cimAdmin.createDataSource(jsonData);
		response = cimAdmin.getDataSourceForId(dataSourceId);
		responseValidator.validateGetDataSource(response, dataSourceId);
		cimAdmin.deleteDataSource(dataSourceId);
		response = cimAdmin.getDataSourceForId(dataSourceId);
		responseValidator.validateDeleteSuccess(response);
	}

	@Test(description = "TC436-Verify creation of formula and link to data source", groups = { "Regression", "High",
			"API" })
	public void createCalculationFormula() throws Exception {
		String dataSourceId = cimAdminHelper.createDataSourceAndFormulasForDiscrete(cimAdmin);
		cimAdmin.deleteDataSource(dataSourceId);
		response = cimAdmin.getDataSourceForId(dataSourceId);
		responseValidator.validateDeleteSuccess(response);
	}

	@Test(description = "TC371- Verify the Data Source with multiple combination", groups = { "Regression", "Medium",
			"API" })
	public void createDataSourceMultipleFileExtension() throws Exception {
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewDataSourceAccountBillingSummary");
		jsonDataTemp = efficacies.readJsonElement("CIMAdminTemplateData.json",
				"createNewDataSourceMultipleFileExtension");
		jsonData = SFDCHelper.overrideJSON(jsonData, jsonDataTemp);
		String dataSourceId = cimAdmin.createDataSource(jsonData);
		response = cimAdmin.getDataSourceForId(dataSourceId);
		responseValidator.validateGetDataSource(response, dataSourceId);
		cimAdmin.deleteDataSource(dataSourceId);
		response = cimAdmin.getDataSourceForId(dataSourceId);
		responseValidator.validateDeleteSuccess(response);
	}

	@Test(description = "TC225-Verify different delimiters for the record delimiter field", groups = { "Regression",
			"Medium", "API" })
	public void verifyDataSourceMandatoryFields() throws Exception {
		response = cimAdmin.createDataSourceWithMissingFields();
		responseValidator.validateFailureResponse(response, RebatesConstants.errorCodeMissingFields,
				RebatesConstants.messageCreateDataSourceWithMissingFields);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewDataSourceAccountBillingSummary");
		jsonDataTemp = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewDataSourceTwoFileExtension");
		jsonData = SFDCHelper.overrideJSON(jsonData, jsonDataTemp);
		String dataSourceId = cimAdmin.createDataSource(jsonData);
		response = cimAdmin.getDataSourceForId(dataSourceId);
		responseValidator.validateGetDataSource(response, dataSourceId);
		cimAdmin.deleteDataSource(dataSourceId);
		response = cimAdmin.getDataSourceForId(dataSourceId);
		responseValidator.validateDeleteSuccess(response);
	}
	
	@Test(description = "TC-615 Verify the uniqueness of Transaction line object value when creating a data source", groups = {
			"Regression", "Medium", "API" })
	public void verifyUniquenessOfTransactionLineObject() throws Exception {
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewDataSourceOrderLineItem");
		response = cimAdmin.createDataSourceFailure(jsonData);
		responseValidator.validateFailureResponse(response, RebatesConstants.errorCodeCustomValidation,
				(RebatesConstants.messageCreateDataSourceDuplicateTransactionLineObject
						.replace("{TransactionLineObjectName}", jsonData.get("TransactionLineObjectName__c")))
						.replace("{DataSourceName}", DataHelper.getIncentiveDataSourceName()));
	}
}