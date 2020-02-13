package com.apttus.sfdc.rebates.lightning.api.pojo;

import java.util.Map;
import com.google.gson.Gson;

public class GetCalculationFormulaIdPojo {

	private String FormulaType__c;
	private String IsStepBased__c;
	private String Name;
	private String Status__c;
	
	public String getStatus__c() {
		return Status__c;
	}

	public void setStatus__c(String status__c) {
		Status__c = status__c;
	}

	public String getFormulaType__c() {
		return FormulaType__c;
	}

	public void setFormulaType__c(String formulaType__c) {
		this.FormulaType__c = formulaType__c;
	}

	public String getIsStepBased__c() {
		return IsStepBased__c;
	}

	public void setIsStepBased__c(String isStep__c) {
		this.IsStepBased__c = isStep__c;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		this.Name = name;
	}

	public String getCalculationFormulaIdRequest(Map<String, String> testData) {
		GetCalculationFormulaIdPojo getCalcFormulaId = new GetCalculationFormulaIdPojo();
		getCalcFormulaId.setIsStepBased__c("true");
		getCalcFormulaId.setFormulaType__c(testData.get("FormulaType__c"));
		getCalcFormulaId.setName(testData.get("Name"));
		getCalcFormulaId.setStatus__c(testData.get("Status__c"));
		return new Gson().toJson(getCalcFormulaId);
	}

	
}

/*------------------ GetCalculationFormulaId Request ----------------------
{
    "IsStepBased__c": true,
    "Type__c": "Benefit",
    "Name": "AutoBenefit SUM(Rebate_Amount)"
}*/
