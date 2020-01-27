package com.apttus.sfdc.rebates.lightning.api.pojo;

import java.util.Map;

import com.apttus.sfdc.rebates.lightning.api.library.CIMAdmin;
import com.google.gson.Gson;

public class MapTemplateAndDataSourcePojo {

	private String TemplateId__c;
	private String FormulaId__c;
	private String DataSourceId__c;
	
	public String getTemplateId__c() {
		return TemplateId__c;
	}

	public void setTemplateId__c(String templateId__c) {
		this.TemplateId__c = templateId__c;
	}

	public String getFormulaId__c() {
		return FormulaId__c;
	}

	public void setFormulaId__c(String formulaId__c) {
		this.FormulaId__c = formulaId__c;
	}

	public String getDataSourceId__c() {
		return DataSourceId__c;
	}

	public void setDataSourceId__c(String dataSourceId__c) {
		this.DataSourceId__c = dataSourceId__c;
	}

	public String createTemplateDataSourceRequest(Map<String, String> testData, CIMAdmin cimAdmin) {

		MapTemplateAndDataSourcePojo mapTemplateAndDataSourcepojo = new MapTemplateAndDataSourcePojo();
		mapTemplateAndDataSourcepojo.setTemplateId__c(cimAdmin.getTemplateData().getTemplateId());
		mapTemplateAndDataSourcepojo.setFormulaId__c(testData.get("FormulaId__c"));
		mapTemplateAndDataSourcepojo.setDataSourceId__c(testData.get("DataSourceId__c"));
		return new Gson().toJson(mapTemplateAndDataSourcepojo);
	}
}
/*--------------------Map dataSource and Template Id
  { "DataSource_Id__c": "a543i0000001fgPAAQ", 
    "TemplateId__c":"a593i000000LCdxAAG", 
    "FormulaId__c": "a523i000000TfOMAA0"
  }
 */
