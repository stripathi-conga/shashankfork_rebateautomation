package com.apttus.sfdc.rebates.lightning.api.pojo;

public class IncentivePayloadPojo {
	private String Apttus_Config2__Status__c;
	private String Id;

	public String getApttus_Config2__Status__c() {
		return Apttus_Config2__Status__c;
	}

	public void setApttus_Config2__Status__c(String apttus_Config2__Status__c) {
		this.Apttus_Config2__Status__c = apttus_Config2__Status__c;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		this.Id = id;
	}
}