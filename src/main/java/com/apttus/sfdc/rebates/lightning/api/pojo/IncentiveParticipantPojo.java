package com.apttus.sfdc.rebates.lightning.api.pojo;

public class IncentiveParticipantPojo {

	private String EffectiveDate__c;
	private String ExpirationDate__c;
	private String Account__c;
	private String Incentive__c;
	public String Id;

	public String getEffectiveDate__c() {
		return EffectiveDate__c;
	}

	public void setEffectiveDate__c(String effectiveDate__c) {
		this.EffectiveDate__c = effectiveDate__c;
	}

	public String getExpirationDate__c() {
		return ExpirationDate__c;
	}

	public void setExpirationDate__c(String expirationDate__c) {
		this.ExpirationDate__c = expirationDate__c;
	}

	public String getAccount__c() {
		return Account__c;
	}

	public void setAccount__c(String account__c) {
		this.Account__c = account__c;
	}

	public String getIncentive__c() {
		return Incentive__c;
	}

	public void setIncentive__c(String incentive__c) {
		this.Incentive__c = incentive__c;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		this.Id = id;
	}
}
