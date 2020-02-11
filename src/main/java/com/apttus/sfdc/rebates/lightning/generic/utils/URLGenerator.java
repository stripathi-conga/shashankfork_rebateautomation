package com.apttus.sfdc.rebates.lightning.generic.utils;

public class URLGenerator {
	
	//----------------- URL for SObjects as API ---------------------
	public String baseURL = null;
	public String REST_ENDPOINT = "/services/data";
	public String API_VERSION = "/v47.0";
	public String dataSourceURL = "/sobjects/DataSource__c/";
	public String getDataSourceURL = "/query/?q=SELECT Id,Name__c FROM DataSource__c WHERE id = '{DataSourceId}'";
	public String fieldExpressionIdURL = "/sobjects/Apttus_Config2__FieldExpression__c/";
	public String calcFormulaIdURL = "/sobjects/CalculationFormula__c/";
	public String linkCalcFormulaIdURL = "/sobjects/CalculationFormulaStep__c/";
	public String linkDatasourceIdURL = "/sobjects/DataSourceFormulaMapping__c/";
	public String getqnblayoutURL = "/query/?q=SELECT Id FROM QnBLayout__c where type__c='{QnBLayoutType}' and tier__c='{QnBLayoutTier}'";
	public String qnbLayoutIdURL = "/sobjects/QnBLayout__c/";
	public String templateURL = "/sobjects/IncentiveProgramTemplate__c/";
	public String getTemplateURL = "/query/?q=SELECT Id,Name ,status__c,Description__c ,QnBLayoutId__c FROM IncentiveProgramTemplate__c WHERE id = '{TemplateId}'";
	public String mapTemplateToDatasourceURL = "/sobjects/TemplateDataSourceMapping__c/";
	public String linkTemplatesURL = "/sobjects/IncentiveProgramTemplateMap__c/";
	public String getLinkTemplatesViaIDURL = "/query/?q=SELECT Id,Name,IncentiveSubType__c,IncentiveType__c,Status__c,TemplateId__c FROM IncentiveProgramTemplateMap__c where id = '{LinkTemplateId}'";
	public String getLinkTemplatesViaIncentiveTypeAndSubtypeURL = "/query/?q=SELECT Id,Name,IncentiveSubType__c,IncentiveType__c,Status__c,TemplateId__c FROM IncentiveProgramTemplateMap__c where IncentiveType__c = '{IncentiveType}' and IncentiveSubType__c = '{IncentiveSubType}'";
	public String incentiveURL = "/sobjects/Apttus_Config2__Incentive__c/";
	public String getIncentiveURL = "/query/?q=SELECT Apttus_Config2__EffectiveDate__c,Apttus_Config2__ExpirationDate__c,BenefitLevel__c,Currency__c,Id,MeasurementFrequency__c,MeasurementLevel__c,Name,Apttus_Config2__UseType__c,IncentiveType__c,IncentiveSubType__c,ProgramTemplateId__c FROM Apttus_Config2__Incentive__c where id='{incentiveId}'";
	public String createAccountURL = "/sobjects/Account/";
	public String getAccountURL = "/query/?q=SELECT AccountNumber,Active__c,Id,Name FROM Account where Name = '{AccountName}'";
	public String addParticipantsURL = "/sobjects/IncentiveParticipant__C/";
	public String getParticipantsURL = "/query/?q=SELECT Id,AccountNumber__c,ExpirationDate__c,Incentive__c,Name,Account__c,EffectiveDate__c from IncentiveParticipant__C where id='{participantId}'";
	public String getParticipantsViaIncentiveIdURL = "/query/?q=SELECT Id,AccountNumber__c,ExpirationDate__c,Incentive__c,Name,Account__c,EffectiveDate__c from IncentiveParticipant__C where Incentive__c='{IncentiveId}'";
	public String getProductIdURL = "/query/?q=SELECT Name,ProductCode,Id,IsActive FROM Product2 where name = '{Product}'";	
	
	//----------------- URL Exposed for apex rest Custom API ------------------
	public String addIncentiveQnBURL = "/services/apexrest/api/QnB";
	public String getIncentiveQnBURL = "/services/apexrest/api/QnB?incentiveId={incentiveId}";
	
	public URLGenerator(String instanceURL) {		
		//----------------- URL for SObjects as API ---------------------
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
		this.getLinkTemplatesViaIncentiveTypeAndSubtypeURL = baseURL + this.getLinkTemplatesViaIncentiveTypeAndSubtypeURL;
		this.incentiveURL = baseURL + this.incentiveURL;
		this.getIncentiveURL = baseURL + this.getIncentiveURL;
		this.createAccountURL = baseURL + this.createAccountURL;
		this.getAccountURL = baseURL + this.getAccountURL;
		this.addParticipantsURL = baseURL + this.addParticipantsURL;
		this.getParticipantsURL = baseURL + this.getParticipantsURL;
		this.getParticipantsViaIncentiveIdURL = baseURL+ this.getParticipantsViaIncentiveIdURL;
		this.getProductIdURL = baseURL + this.getProductIdURL;
		
		//----------------- URL Exposed for apex rest Custom API -------------------
		this.addIncentiveQnBURL = instanceURL + this.addIncentiveQnBURL;
		this.getIncentiveQnBURL = instanceURL + this.getIncentiveQnBURL;
	}
}
