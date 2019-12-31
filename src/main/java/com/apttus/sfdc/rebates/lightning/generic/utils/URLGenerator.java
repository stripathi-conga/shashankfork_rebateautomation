package com.apttus.sfdc.rebates.lightning.generic.utils;

public class URLGenerator {

	public String baseURL = null;
	public String REST_ENDPOINT = "/services/data";
	public String API_VERSION = "/v46.0";
	public String dataSourceURL = "/sobjects/Data_Source__c/";
	public String getDataSourceURL = "/query/?q=SELECT Id,Name__c FROM Data_Source__c WHERE id = '{DataSourceId}'";
	public String fieldExpressionId = "/sobjects/Apttus_Config2__FieldExpression__C/";
	public String calcFormulaId = "/sobjects/Calculation_Formula__C/";
	public String linkCalcFormulaId = "/sobjects/Calculation_Formula_Step__C/";
	public String linkDatasourceId = "/sobjects/Data_Source_Formula__C/";
	public String adminTemplateURL = "/sobjects/Program_Template__c/";
	public String getAdminTemplateURL = "/query/?q=SELECT Id,Name FROM Program_Template__c WHERE id = '{AdminTemplateId}'";
	public String mapAdminTemplateToDatasourceURL = "/sobjects/Program_Template_Data_Source__c/";

	public URLGenerator(String instanceURL) {
		this.baseURL = instanceURL + this.REST_ENDPOINT + this.API_VERSION;
		this.dataSourceURL = baseURL + this.dataSourceURL;
		this.getDataSourceURL = baseURL + this.getDataSourceURL;

		this.fieldExpressionId = baseURL + this.fieldExpressionId;
		this.calcFormulaId = baseURL + this.calcFormulaId;
		this.linkCalcFormulaId = baseURL + this.linkCalcFormulaId;
		this.linkDatasourceId = baseURL + this.linkDatasourceId;

		this.getAdminTemplateURL = baseURL + this.getAdminTemplateURL;
		this.adminTemplateURL = baseURL + this.adminTemplateURL;
		this.mapAdminTemplateToDatasourceURL = baseURL + this.mapAdminTemplateToDatasourceURL;

	}
}
