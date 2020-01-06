package com.apttus.sfdc.rebates.lightning.api.pojo;

import java.util.Map;

import com.apttus.sfdc.rebates.lightning.api.library.CIMAdmin;
import com.google.gson.Gson;

public class CreateQnBLayoutIdPojo {

	private String tier__c;
	private String type__c;
	public String qnbLayoutId;

	public String getQnbLayoutId() {
		return qnbLayoutId;
	}

	public void setQnbLayoutId(String qnbLayoutId) {
		this.qnbLayoutId = qnbLayoutId;
	}

	public String getTier__c() {
		return tier__c;
	}

	public void setTier__c(String tier__c) {
		this.tier__c = tier__c;
	}

	public String getType__c() {
		return type__c;
	}

	public void setType__c(String type__c) {
		this.type__c = type__c;
	}

	public String createQnBLayoutIdRequest(Map<String, String> testData, CIMAdmin cimAdmin) {
		CreateQnBLayoutIdPojo createQnBLayoutId = new CreateQnBLayoutIdPojo();
		createQnBLayoutId.setTier__c(testData.get("tier__c"));
		createQnBLayoutId.setType__c(testData.get("type__c"));
		cimAdmin.setQnblayoutData(createQnBLayoutId);
		return new Gson().toJson(createQnBLayoutId);
	}
}
