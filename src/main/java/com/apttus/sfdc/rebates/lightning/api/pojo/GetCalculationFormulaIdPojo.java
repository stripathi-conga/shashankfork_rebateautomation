package com.apttus.sfdc.rebates.lightning.api.pojo;

import java.util.Map;
import com.google.gson.Gson;

public class GetCalculationFormulaIdPojo {

	private String Type__c;
	private String IsStep__c;
	private String Name;
	private String Status__c;
	
	public String getStatus__c() {
		return Status__c;
	}

	public void setStatus__c(String status__c) {
		Status__c = status__c;
	}

	public String getType__c() {
		return Type__c;
	}

	public void setType__c(String type__c) {
		this.Type__c = type__c;
	}

	public String getIsStep__c() {
		return IsStep__c;
	}

	public void setIsStep__c(String isStep__c) {
		this.IsStep__c = isStep__c;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		this.Name = name;
	}

	public String getCalculationFormulaIdRequest(Map<String, String> testData) {
		GetCalculationFormulaIdPojo getCalcFormulaId = new GetCalculationFormulaIdPojo();
		getCalcFormulaId.setIsStep__c("true");
		getCalcFormulaId.setType__c(testData.get("Type__c"));
		getCalcFormulaId.setName(testData.get("Name"));
		getCalcFormulaId.setStatus__c(testData.get("Status__c"));
		return new Gson().toJson(getCalcFormulaId);
	}

	
}

/*------------------ GetCalculationFormulaId Request ----------------------
{
    "IsStep__c": true,
    "Type__c": "Benefit",
    "Name": "AutoBenefit SUM(Rebate_Amount)"
}*/
