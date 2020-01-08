package com.apttus.sfdc.rebates.lightning.generic.utils;

public class URLGenerator {

	public String baseURL = null;
	public String REST_ENDPOINT = "/services/data";
	public String API_VERSION = "/v46.0";
	public String dataSourceURL = "/sobjects/Data_Source__c/";
	public String getDataSourceURL = "/query/?q=SELECT Id,Name__c FROM Data_Source__c WHERE id = '{DataSourceId}'";
	public String getqnblayoutURL = "/query/?q=SELECT Id FROM Qualification_and_Benefit_Layout__c where type__c='{QnBLayoutType}' and tier__c='{QnBLayoutTier}'";
	public String fieldExpressionId = "/sobjects/Apttus_Config2__FieldExpression__c/";
	public String calcFormulaId = "/sobjects/Calculation_Formula__c/";
	public String qnbLayoutId = "/sobjects/Qualification_and_Benefit_Layout__c/";
	public String linkCalcFormulaId = "/sobjects/Calculation_Formula_Step__c/";
	public String linkDatasourceId = "/sobjects/Data_Source_Formula__c/";
	public String adminTemplateURL = "/sobjects/Program_Template__c/";
	public String getAdminTemplateURL = "/query/?q=SELECT Id,Name FROM Program_Template__c WHERE id = '{AdminTemplateId}'";
	public String mapAdminTemplateToDatasourceURL = "/sobjects/Program_Template_Data_Source__c/";
	public String linkTemplatesURL = "/sobjects/Program_Template_Map__c/";
	public String getLinkTemplatesViaIDURL = "/query/?q=SELECT Id,Name,Program_Sub_Type__c,Program_Type__c,Status__c,Template_Id__c FROM Program_Template_Map__c where id = '{LinkTemplateId}'";
	public String getLinkTemplatesViaProgramTypeURL = "/query/?q=SELECT Id,Name,Program_Sub_Type__c,Program_Type__c,Status__c,Template_Id__c FROM Program_Template_Map__c where Program_Type__c = '{ProgramType}' and Program_Sub_Type__c = '{ProgramSubType}'";
	

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
		
		this.linkTemplatesURL = baseURL + this.linkTemplatesURL;

		
		this.qnbLayoutId=baseURL + this.qnbLayoutId;
		this.getqnblayoutURL=baseURL + this.getqnblayoutURL;
		

		this.getLinkTemplatesViaIDURL = baseURL + this.getLinkTemplatesViaIDURL;
		this.getLinkTemplatesViaProgramTypeURL = baseURL + this.getLinkTemplatesViaProgramTypeURL;

	}
}
