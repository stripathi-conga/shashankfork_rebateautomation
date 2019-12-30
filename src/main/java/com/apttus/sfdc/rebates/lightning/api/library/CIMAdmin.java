package com.apttus.sfdc.rebates.lightning.api.library;

import java.util.Map;
import com.apttus.customException.ApplicationException;
import com.apttus.sfdc.rebates.lightning.api.pojo.CreateNewDataSourcePojo;
import com.apttus.sfdc.rebates.lightning.api.pojo.GetCalculationFormulaIdPojo;
import com.apttus.sfdc.rebates.lightning.api.pojo.GetFieldExpressionIdPojo;
import com.apttus.sfdc.rebates.lightning.api.pojo.LinkCalculationFormulaPojo;
import com.apttus.sfdc.rebates.lightning.api.pojo.LinkDatasourceToCalculationIdPojo;
import com.apttus.sfdc.rebates.lightning.generic.utils.URLGenerator;
import com.apttus.sfdc.rudiments.utils.SFDCRestUtils;
import com.google.gson.JsonParser;
import com.jayway.restassured.response.Response;

public class CIMAdmin {

	public SFDCRestUtils sfdcRestUtils;
	public URLGenerator urlGenerator;
	public CreateNewDataSourcePojo dataSourceData;
	public JsonParser parser;

	public CreateNewDataSourcePojo getDataSourceData() {
		return dataSourceData;
	}

	public void setDataSourceData(CreateNewDataSourcePojo dataSourceData) {
		this.dataSourceData = dataSourceData;
	}

	public CIMAdmin(String baseURL, SFDCRestUtils sfdcRestUtils) {
		urlGenerator = new URLGenerator(baseURL);
		this.sfdcRestUtils = sfdcRestUtils;
		parser = new JsonParser();
	}

	public void validateResponseCode(Response response, int expectedCode) throws Exception {
		if (response.getStatusCode() != expectedCode) {
			throw new Exception("CorelationID : [" + response.header("CorelationId") + "], The response code was : "
					+ response.getStatusCode() + " and the response body received is : "
					+ response.getBody().asString());
		}
	}

	public Response createDataSource(Map<String, String> testData) throws ApplicationException {
		String dataSourceId, requestString;
		CreateNewDataSourcePojo createDataSource = new CreateNewDataSourcePojo(); 
		try {
			requestString = createDataSource.createDataSourceRequest(testData, this);
			Response response = sfdcRestUtils.postWithoutAppUrl(urlGenerator.dataSourceURL, requestString);
			validateResponseCode(response, 201);
			dataSourceId = (parser.parse(response.getBody().asString())).getAsJsonObject().get("id").getAsString();
			dataSourceData.setDataSourceId(dataSourceId);
			return response;
		} catch (Exception e) {
			throw new ApplicationException("Create New DataSource API call failed with exception trace : " + e);
		}
	}

	public Response getDataSource() throws ApplicationException {
		try {
			Response response = sfdcRestUtils
					.getData(urlGenerator.getDataSourceURL.replace("{DataSourceId}", dataSourceData.getDataSourceId()));
			validateResponseCode(response, 200);
			return response;
		} catch (Exception e) {
			throw new ApplicationException("Get DataSource API call failed with exception trace : " + e);
		}
	}

	public void deleteDataSource() throws ApplicationException {
		try {
			Response response = sfdcRestUtils
					.deleteWithoutPayload(urlGenerator.dataSourceURL + dataSourceData.getDataSourceId());
			validateResponseCode(response, 204);
		} catch (Exception e) {
			throw new ApplicationException("Delete DataSource API call failed with exception trace : " + e);
		}
	}
	
	public String getFieldExpressionId(Map<String, String> testData) throws ApplicationException {
		String fieldExpressionId = null, requestString;
		GetFieldExpressionIdPojo createNewFieldExpressionId = new GetFieldExpressionIdPojo();
		try {
			requestString = createNewFieldExpressionId.getExpressionIdRequest(testData);
			Response response = sfdcRestUtils
					.postWithoutAppUrl(urlGenerator.fieldExpressionId, requestString);
			validateResponseCode(response, 201);
			fieldExpressionId = (parser.parse(response.getBody().asString())).getAsJsonObject().get("id").getAsString();
			return fieldExpressionId;
		} catch (Exception e) {
			throw new ApplicationException("Create FieldExpressionId API call failed with exception trace : " + e);
		}		
	}
	
	public String getCalcFormulaId(Map<String, String> testData) throws ApplicationException {
		String calcFormulaId = null, requestString;
		GetCalculationFormulaIdPojo createCalcFormulaId = new GetCalculationFormulaIdPojo();
		try {
			requestString = createCalcFormulaId.getCalculationFormulaIdRequest(testData);
			Response response = sfdcRestUtils
					.postWithoutAppUrl(urlGenerator.calcFormulaId, requestString);
			validateResponseCode(response, 201);
			calcFormulaId = (parser.parse(response.getBody().asString())).getAsJsonObject().get("id").getAsString();
			return calcFormulaId;
		} catch (Exception e) {
			throw new ApplicationException("Create CalcFormulaId API call failed with exception trace : " + e);
		}		
	}
	
	public void linkCalcFormulaToExpression(Map<String, String> testData, String calculationFormulaId,
			String expressionId) throws ApplicationException {
		String requestString;
		LinkCalculationFormulaPojo linkCalcFormula = new LinkCalculationFormulaPojo();
		try {
			requestString = linkCalcFormula.linkCalculationFormulaPojoRequest(testData, calculationFormulaId, expressionId);
			Response response = sfdcRestUtils
					.postWithoutAppUrl(urlGenerator.linkCalcFormulaId, requestString);
			validateResponseCode(response, 201);
		} catch (Exception e) {
			throw new ApplicationException("Link CalcFormulaId To ExpressionId API call failed with exception trace : " + e);
		}		
	}
	
	public void linkDatasourceToCalcFormula(String calculationFormulaId) throws ApplicationException {
		String requestString;
		LinkDatasourceToCalculationIdPojo linkDatasource = new LinkDatasourceToCalculationIdPojo();
		try {
			requestString = linkDatasource.linkDatasourceIdRequest(calculationFormulaId, this);
			Response response = sfdcRestUtils
					.postWithoutAppUrl(urlGenerator.linkDatasourceId, requestString);
			validateResponseCode(response, 201);
		} catch (Exception e) {
			throw new ApplicationException("Link DatasourceId To CalcFormulaId API call failed with exception trace : " + e);
		}		
	}
}
