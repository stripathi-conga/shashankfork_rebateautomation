package com.apttus.sfdc.rebates.lightning.api.pojo;

import java.util.Map;
import com.google.gson.Gson;

public class LinkCalculationFormulaPojo {

	private String CalculationFormula__c;
	private String Type__c;
	private String Sequence__c;
	private String ReferenceName__c;
	private String ExpressionId__c;
	private String Status__c;
	
	public String getStatus__c() {
		return Status__c;
	}

	public void setStatus__c(String status__c) {
		Status__c = status__c;
	}
	public String getCalculationFormula__c() {
		return CalculationFormula__c;
	}

	public void setCalculationFormula__c(String calculationFormula__c) {
		this.CalculationFormula__c = calculationFormula__c;
	}

	public String getType__c() {
		return Type__c;
	}

	public void setType__c(String type__c) {
		this.Type__c = type__c;
	}

	public String getSequence__c() {
		return Sequence__c;
	}

	public void setSequence__c(String sequence__c) {
		this.Sequence__c = sequence__c;
	}

	public String getReferenceName__c() {
		return ReferenceName__c;
	}

	public void setReferenceName__c(String referenceName__c) {
		this.ReferenceName__c = referenceName__c;
	}

	public String getExpression_Id__c() {
		return ExpressionId__c;
	}

	public void setExpression_Id__c(String expressionId__c) {
		this.ExpressionId__c = expressionId__c;
	}

	public String linkCalculationFormulaPojoRequest(Map<String, String> testData, String calculationFormulaId,
			String expressionId) {
		LinkCalculationFormulaPojo linkCalcFormulaId = new LinkCalculationFormulaPojo();
		linkCalcFormulaId.setCalculationFormula__c(calculationFormulaId);
		linkCalcFormulaId.setExpression_Id__c(expressionId);
		linkCalcFormulaId.setReferenceName__c(testData.get("ReferenceName__c"));
		linkCalcFormulaId.setSequence__c(testData.get("Sequence__c"));
		linkCalcFormulaId.setType__c(testData.get("Type__c"));
		linkCalcFormulaId.setStatus__c(testData.get("Status__c"));
		return new Gson().toJson(linkCalcFormulaId);
	}
}

/*---------------- Link Field ExpressionId and CalculationFormlaId Request --------------
{
    "Calculation_Formula__c": "a523i000000TfObAAK",
    "Expression_Id__c": "a1O3i000000IVAfEAO",
    "ReferenceName__c": "AutoBenefit SUM(Rebate_Amount)",
    "Sequence__c":"1",
    "Type__c": "Line"
}*/