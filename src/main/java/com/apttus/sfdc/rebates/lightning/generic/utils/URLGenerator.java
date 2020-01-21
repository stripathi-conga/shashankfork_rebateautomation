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
	public String templateURL = "/sobjects/Program_Template__c/";
	public String getTemplateURL = "/query/?q=SELECT Id,Name ,status__c,Description__c ,QnB_Layout_Id__c FROM Program_Template__c WHERE id = '{TemplateId}'";
	public String mapTemplateToDatasourceURL = "/sobjects/Program_Template_Data_Source__c/";
	public String linkTemplatesURL = "/sobjects/Program_Template_Map__c/";
	public String getLinkTemplatesViaIDURL = "/query/?q=SELECT Id,Name,Program_Sub_Type__c,Program_Type__c,Status__c,Template_Id__c FROM Program_Template_Map__c where id = '{LinkTemplateId}'";
	public String getLinkTemplatesViaProgramTypeURL = "/query/?q=SELECT Id,Name,Program_Sub_Type__c,Program_Type__c,Status__c,Template_Id__c FROM Program_Template_Map__c where Program_Type__c = '{ProgramType}' and Program_Sub_Type__c = '{ProgramSubType}'";
	public String incentiveURL = "/sobjects/Apttus_Config2__Incentive__c/";
	public String getIncentiveURL = "/query/?q=SELECT Apttus_Config2__EffectiveDate__c,Apttus_Config2__ExpirationDate__c,BenefitLevel__c,Currency__c,Id,MeasurementFrequency__c,MeasurementLevel__c,Name,Apttus_Config2__UseType__c,Apttus_Config2__SubUseType__c,Program_Template_Id__c FROM Apttus_Config2__Incentive__c where id='{incentiveId}'";
	public String createAccountURL = "/sobjects/Account/";
	public String getAccountURL = "/query/?q=SELECT AccountNumber,Active__c,Id,Name FROM Account where Name = '{AccountName}'";
	public String addParticipantsURL = "/sobjects/IncentiveParticipant__C/";
	public String getParticipantsURL = "/query/?q=SELECT Id,AccountNumber__c,ExpirationDate__c,Account__c,EffectiveDate__c from IncentiveParticipant__C where id='{participantId}'";
	
	public URLGenerator(String instanceURL) {
		this.baseURL = instanceURL + this.REST_ENDPOINT + this.API_VERSION;
		this.dataSourceURL = baseURL + this.dataSourceURL;
		this.getDataSourceURL = baseURL + this.getDataSourceURL;
		this.fieldExpressionIdURL = baseURL + this.fieldExpressionIdURL;
		this.calcFormulaIdURL = baseURL + this.calcFormulaIdURL;
		this.linkCalcFormulaIdURL = baseURL + this.linkCalcFormulaIdURL;
		this.linkDatasourceIdURL = baseURL + this.linkDatasourceIdURL;
		this.getTemplateURL = baseURL + this.getTemplateURL;
		this.templateURL = baseURL + this.templateURL;
		this.mapTemplateToDatasourceURL = baseURL + this.mapTemplateToDatasourceURL;
		this.linkTemplatesURL = baseURL + this.linkTemplatesURL;
		this.qnbLayoutIdURL = baseURL + this.qnbLayoutIdURL;
		this.getqnblayoutURL = baseURL + this.getqnblayoutURL;
		this.getLinkTemplatesViaIDURL = baseURL + this.getLinkTemplatesViaIDURL;
		this.getLinkTemplatesViaProgramTypeURL = baseURL + this.getLinkTemplatesViaProgramTypeURL;
		this.incentiveURL = baseURL + this.incentiveURL;
		this.getIncentiveURL = baseURL + this.getIncentiveURL;
		this.createAccountURL = baseURL + this.createAccountURL;
		this.getAccountURL = baseURL + this.getAccountURL;
		this.addParticipantsURL= baseURL + this.addParticipantsURL;
		this.getParticipantsURL= baseURL + this.getParticipantsURL;

	}
}
