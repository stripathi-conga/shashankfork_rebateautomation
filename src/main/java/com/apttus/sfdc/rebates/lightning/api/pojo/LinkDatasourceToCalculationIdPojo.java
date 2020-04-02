package com.apttus.sfdc.rebates.lightning.api.pojo;

import com.apttus.sfdc.rebates.lightning.api.library.CIMAdmin;
import com.apttus.sfdc.rebates.lightning.generic.utils.RebatesConstants;
import com.google.gson.Gson;

public class LinkDatasourceToCalculationIdPojo {

	private String CalculationFormula__c;
	private String DataSource__c;

	public String getCalculationFormula__c() {
		return CalculationFormula__c;
	}

	public void setCalculationFormula__c(String calculationFormula__c) {
		this.CalculationFormula__c = calculationFormula__c;
	}

	public String getDataSource__c() {
		return DataSource__c;
	}

	public void setDataSource__c(String dataSource__c) {
		this.DataSource__c = dataSource__c;
	}

	public String linkDatasourceIdRequest(String calculationFormulaId, CIMAdmin cimAdmin) {
		LinkDatasourceToCalculationIdPojo linkDatasourceId = new LinkDatasourceToCalculationIdPojo();
		linkDatasourceId.setCalculationFormula__c(calculationFormulaId);
		linkDatasourceId.setDataSource__c(RebatesConstants.incentiveDataSourceId);
		return new Gson().toJson(linkDatasourceId);
	}
}

/*------------ Link DataSourceId to CalculationId request ---------------
{
    "Calculation_Formula__c": "a523i000000TfObAAK",
    "Data_Source__c": "a543i0000001foEAAQ"
}*/