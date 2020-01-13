package com.apttus.sfdc.rebates.lightning.generic.utils;

public class URLGenerator {

	public String baseURL = null;
	public String REST_ENDPOINT = "/services/data";
	public String API_VERSION = "/v47.0";
	public String dataSourceURL = "/sobjects/Data_Source__c/";
	public String getDataSourceURL = "/query/?q=SELECT Id,Name__c FROM Data_Source__c WHERE id = '{DataSourceId}'";
	public String fieldExpressionIdURL = "/sobjects/Apttus_Config2__FieldExpression__c/";
	public String calcFormulaIdURL = "/sobjects/Calculation_Formula__c/";
	public String linkCalcFormulaIdURL = "/sobjects/Calculation_Formula_Step__c/";
	public String linkDatasourceIdURL = "/sobjects/Data_Source_Formula__c/";
	public String getqnblayoutURL = "/query/?q=SELECT Id FROM Qualification_and_Benefit_Layout__c where type__c='{QnBLayoutType}' and tier__c='{QnBLayoutTier}'";
	public String qnbLayoutIdURL = "/sobjects/Qualification_and_Benefit_Layout__c/";
	public String adminTemplateURL = "/sobjects/Program_Template__c/";
	public String getAdminTemplateURL = "/query/?q=SELECT Id,Name ,status__c  FROM Program_Template__c WHERE id = '{AdminTemplateId}'";
	public String mapAdminTemplateToDatasourceURL = "/sobjects/Program_Template_Data_Source__c/";
	public String linkTemplatesURL = "/sobjects/Program_Template_Map__c/";
	public String getLinkTemplatesViaIDURL = "/query/?q=SELECT Id,Name,Program_Sub_Type__c,Program_Type__c,Status__c,Template_Id__c FROM Program_Template_Map__c where id = '{LinkTemplateId}'";
	public String getLinkTemplatesViaProgramTypeURL = "/query/?q=SELECT Id,Name,Program_Sub_Type__c,Program_Type__c,Status__c,Template_Id__c FROM Program_Template_Map__c where Program_Type__c = '{ProgramType}' and Program_Sub_Type__c = '{ProgramSubType}'";
	public String programURL = "/sobjects/Apttus_Config2__Incentive__c/";
	public String getProgramURL = "/query/?q=SELECT Apttus_Config2__EffectiveDate__c,Apttus_Config2__ExpirationDate__c,BenefitLevel__c,Currency__c,Id,MeasurementFrequency__c,MeasurementLevel__c,Name,Apttus_Config2__UseType__c,Apttus_Config2__SubUseType__c,Program_Template_Id__c FROM Apttus_Config2__Incentive__c where id='{ProgramId}'";
	public String createAccountURL = "/sobjects/Account/";
	public String getAccountURL = "/query/?q=SELECT AccountNumber,Active__c,Id,Name FROM Account where Name = '{AccountName}'";
	
	public URLGenerator(String instanceURL) {
		this.baseURL = instanceURL + this.REST_ENDPOINT + this.API_VERSION;
		this.dataSourceURL = baseURL + this.dataSourceURL;
		this.getDataSourceURL = baseURL + this.getDataSourceURL;
		this.fieldExpressionIdURL = baseURL + this.fieldExpressionIdURL;
		this.calcFormulaIdURL = baseURL + this.calcFormulaIdURL;
		this.linkCalcFormulaIdURL = baseURL + this.linkCalcFormulaIdURL;
		this.linkDatasourceIdURL = baseURL + this.linkDatasourceIdURL;
		this.getAdminTemplateURL = baseURL + this.getAdminTemplateURL;
		this.adminTemplateURL = baseURL + this.adminTemplateURL;
		this.mapAdminTemplateToDatasourceURL = baseURL + this.mapAdminTemplateToDatasourceURL;
		this.linkTemplatesURL = baseURL + this.linkTemplatesURL;
		this.qnbLayoutIdURL = baseURL + this.qnbLayoutIdURL;
		this.getqnblayoutURL = baseURL + this.getqnblayoutURL;
		this.getLinkTemplatesViaIDURL = baseURL + this.getLinkTemplatesViaIDURL;
		this.getLinkTemplatesViaProgramTypeURL = baseURL + this.getLinkTemplatesViaProgramTypeURL;
		this.programURL = baseURL + this.programURL;
		this.getProgramURL = baseURL + this.getProgramURL;
		this.createAccountURL = baseURL + this.createAccountURL;
		this.getAccountURL = baseURL + this.getAccountURL;

	}
}
