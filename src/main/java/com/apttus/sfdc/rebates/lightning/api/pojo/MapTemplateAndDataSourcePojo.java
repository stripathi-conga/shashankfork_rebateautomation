package com.apttus.sfdc.rebates.lightning.api.pojo;

import java.util.Map;

import com.apttus.sfdc.rebates.lightning.api.library.CIMAdmin;
import com.google.gson.Gson;

public class MapTemplateAndDataSourcePojo {

	private String Template_Id__c;

	private String Formula_Id__c;

	private String Data_Source_Id__c;

	/*
	 * public String TemplateDataSourceId;
	 * 
	 * public String getTemplateDataSourceId() { return TemplateDataSourceId; }
	 * 
	 * public void setTemplateDataSourceId(String templateDataSourceId) {
	 * TemplateDataSourceId = templateDataSourceId; }
	 */

	public String getTemplate_Id__c() {
		return Template_Id__c;
	}

	public void setTemplate_Id__c(String template_Id__c) {
		this.Template_Id__c = template_Id__c;
	}

	public String getFormula_Id__c() {
		return Formula_Id__c;
	}

	public void setFormula_Id__c(String formula_Id__c) {
		this.Formula_Id__c = formula_Id__c;
	}

	public String getData_Source_Id__c() {
		return Data_Source_Id__c;
	}

	public void setData_Source_Id__c(String data_Source_Id__c) {
		this.Data_Source_Id__c = data_Source_Id__c;
	}

	public static String createTemplateDataSourceRequest(Map<String, String> testData, CIMAdmin cimAdmin) {

		MapTemplateAndDataSourcePojo mapTemplateAndDataSourcepojo = new MapTemplateAndDataSourcePojo();
		mapTemplateAndDataSourcepojo.setTemplate_Id__c(cimAdmin.getAdminTemplateData().getTemplateId());
		mapTemplateAndDataSourcepojo.setFormula_Id__c(testData.get("Formula_Id__c"));
		mapTemplateAndDataSourcepojo.setData_Source_Id__c(testData.get("Data_Source_Id__c"));

		cimAdmin.setAdminTemplateDataSourcedata(mapTemplateAndDataSourcepojo);
		return new Gson().toJson(mapTemplateAndDataSourcepojo);
	}

}
