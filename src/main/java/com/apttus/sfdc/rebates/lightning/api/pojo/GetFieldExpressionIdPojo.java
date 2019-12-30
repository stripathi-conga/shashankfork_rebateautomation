package com.apttus.sfdc.rebates.lightning.api.pojo;

import java.util.Map;
import com.google.gson.Gson;

public class GetFieldExpressionIdPojo {
	
	private String Apttus_Config2__Expression__c;
	private String Apttus_Config2__Active__c;
	private String Name;

	public String getApttus_Config2__Expression__c() {
		return Apttus_Config2__Expression__c;
	}

	public void setApttus_Config2__Expression__c(String apttus_Config2__Expression__c) {
		this.Apttus_Config2__Expression__c = apttus_Config2__Expression__c;
	}

	public String getApttus_Config2__Active__c() {
		return Apttus_Config2__Active__c;
	}

	public void setApttus_Config2__Active__c(String apttus_Config2__Active__c) {
		this.Apttus_Config2__Active__c = apttus_Config2__Active__c;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		this.Name = name;
	}
	
	public String getExpressionIdRequest(Map<String, String> testData) {
		GetFieldExpressionIdPojo getExpressionId = new GetFieldExpressionIdPojo();
		getExpressionId.setApttus_Config2__Active__c("true");
		getExpressionId.setApttus_Config2__Expression__c(testData.get("Apttus_Config2__Expression__c"));
		getExpressionId.setName(testData.get("Name"));
		return new Gson().toJson(getExpressionId);
	}
}

/*-----------------GetFieldExpressionId Request ----------------------------
{
    "Apttus_Config2__Active__c": "true",
    "Apttus_Config2__Expression__c": "SUM(Rebate_Amount)",
    "Name": "Auto SUM(Rebate_Amount)"
}*/
