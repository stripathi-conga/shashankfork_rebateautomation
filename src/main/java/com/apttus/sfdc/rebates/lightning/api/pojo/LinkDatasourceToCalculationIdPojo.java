package com.apttus.sfdc.rebates.lightning.api.pojo;

import com.apttus.sfdc.rebates.lightning.api.library.CIMAdmin;
import com.google.gson.Gson;

public class LinkDatasourceToCalculationIdPojo {

	private String Calculation_Formula__c;
	private String Data_Source__c;

	public String getCalculation_Formula__c() {
		return Calculation_Formula__c;
	}

	public void setCalculation_Formula__c(String calculation_Formula__c) {
		this.Calculation_Formula__c = calculation_Formula__c;
	}

	public String getData_Source__c() {
		return Data_Source__c;
	}

	public void setData_Source__c(String data_Source__c) {
		this.Data_Source__c = data_Source__c;
	}

	public String linkDatasourceIdRequest(String calculationFormulaId, CIMAdmin cimAdmin) {
		LinkDatasourceToCalculationIdPojo linkDatasourceId = new LinkDatasourceToCalculationIdPojo();
		linkDatasourceId.setCalculation_Formula__c(calculationFormulaId);
		linkDatasourceId.setData_Source__c(cimAdmin.getDataSourceData().getDataSourceId());
		return new Gson().toJson(linkDatasourceId);
	}
}

/*------------ Link DataSourceId to CalculationId request ---------------
{
    "Calculation_Formula__c": "a523i000000TfObAAK",
    "Data_Source__c": "a543i0000001foEAAQ"
}*/