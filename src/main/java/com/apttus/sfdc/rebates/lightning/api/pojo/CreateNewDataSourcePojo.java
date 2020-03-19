package com.apttus.sfdc.rebates.lightning.api.pojo;

import java.util.Map;
import com.apttus.sfdc.rebates.lightning.api.library.CIMAdmin;
import com.apttus.sfdc.rebates.lightning.generic.utils.SFDCHelper;
import com.google.gson.Gson;

public class CreateNewDataSourcePojo {
	
	private String Delimiter__c;
	private String Name;
	private String ProductAttr__c;
	private String FileSuffixToignore__c;
	private String TransactionMetaData__c;
	private String FileExtension__c;
	private String CalculationDateAttr__c;	
	private String IncentiveAccountAttr__c;
	private String CalculationDateName__c;
	private String IncentiveAccountFieldName__c;
	private String ProductFieldName__c;
	private String RecordDelimiterName__c;
	private String TransactionLineObjectName__c;
	public String dataSourceId;
	
	public String getCalculationDateName__c() {
		return CalculationDateName__c;
	}

	public void setCalculationDateName__c(String calculationDateName__c) {
		CalculationDateName__c = calculationDateName__c;
	}

	public String getIncentiveAccountFieldName__c() {
		return IncentiveAccountFieldName__c;
	}

	public void setIncentiveAccountFieldName__c(String incentiveAccountFieldName__c) {
		IncentiveAccountFieldName__c = incentiveAccountFieldName__c;
	}

	public String getProductFieldName__c() {
		return ProductFieldName__c;
	}

	public void setProductFieldName__c(String productFieldName__c) {
		ProductFieldName__c = productFieldName__c;
	}

	public String getRecordDelimiterName__c() {
		return RecordDelimiterName__c;
	}

	public void setRecordDelimiterName__c(String recordDelimiterName__c) {
		RecordDelimiterName__c = recordDelimiterName__c;
	}

	public String getTransactionLineObjectName__c() {
		return TransactionLineObjectName__c;
	}

	public void setTransactionLineObjectName__c(String transactionLineObjectName__c) {
		TransactionLineObjectName__c = transactionLineObjectName__c;
	}
	
	public String getDataSourceId() {
		return dataSourceId;
	}

	public void setDataSourceId(String dataSourceId) {
		this.dataSourceId = dataSourceId;
	}

	public String getDelimiter__c() {
		return Delimiter__c;
	}

	public void setDelimiter__c(String delimiter__c) {
		this.Delimiter__c = delimiter__c;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		this.Name = name;
	}

	public String getProductAttr__c() {
		return ProductAttr__c;
	}

	public void setProductAttr__c(String productAttr__c) {
		this.ProductAttr__c = productAttr__c;
	}

	public String getFileSuffixToignore__c() {
		return FileSuffixToignore__c;
	}

	public void setFileSuffixToignore__c(String fileSuffixToignore__c) {
		this.FileSuffixToignore__c = fileSuffixToignore__c;
	}

	public String getTransactionMetaData__c() {
		return TransactionMetaData__c;
	}

	public void setTransactionMetaData__c(String transactionMetaData__c) {
		this.TransactionMetaData__c = transactionMetaData__c;
	}

	public String getFileExtension__c() {
		return FileExtension__c;
	}

	public void setFileExtension__c(String fileExtension__c) {
		this.FileExtension__c = fileExtension__c;
	}

	public String getCalculationDateAttr__c() {
		return CalculationDateAttr__c;
	}

	public void setCalculationDateAttr__c(String calculationDateAttr__c) {
		this.CalculationDateAttr__c = calculationDateAttr__c;
	}

	public String getIncentiveAccountAttr__c() {
		return IncentiveAccountAttr__c;
	}

	public void setIncentiveAccountAttr__c(String incentiveAccountAttr__c) {
		this.IncentiveAccountAttr__c = incentiveAccountAttr__c;
	}

	public String createDataSourceRequest(Map<String, String> testData, CIMAdmin cimAdmin) {
		CreateNewDataSourcePojo createDataSource = new CreateNewDataSourcePojo();
		createDataSource.setName(testData.get("Name"));
		if (testData.get("Name").equalsIgnoreCase("{RANDOM}")) {
			createDataSource.setName("Rebates_Auto_DataSource_" + SFDCHelper.randomNumberGenerator());
		}		
		createDataSource.setCalculationDateAttr__c(testData.get("CalculationDateAttr__c"));
		createDataSource.setDelimiter__c(testData.get("Delimiter__c"));
		createDataSource.setFileExtension__c(testData.get("FileExtension__c"));
		createDataSource.setFileSuffixToignore__c(testData.get("FileSuffixToignore__c"));
		createDataSource.setProductAttr__c(testData.get("ProductAttr__c"));
		createDataSource.setIncentiveAccountAttr__c(testData.get("IncentiveAccountAttr__c"));
		createDataSource.setTransactionMetaData__c(testData.get("TransactionMetaData__c"));
		createDataSource.setTransactionLineObjectName__c(testData.get("TransactionLineObjectName__c"));
		createDataSource.setCalculationDateName__c(testData.get("CalculationDateName__c"));
		createDataSource.setIncentiveAccountFieldName__c(testData.get("IncentiveAccountFieldName__c"));
		createDataSource.setDelimiter__c(testData.get("Delimiter__c"));
		createDataSource.setRecordDelimiterName__c(testData.get("RecordDelimiterName__c"));
		createDataSource.setProductFieldName__c(testData.get("ProductFieldName__c"));
		cimAdmin.setDataSourceData(createDataSource);
		return new Gson().toJson(createDataSource);
	}
}

/*------------------ Create DataSource Request Body --------------------------
{
  "Name": "MJ Test DS",
  "TransactionMetaData__c": "Apttus_Config2__OrderLineItem__c",
  "TransactionLineObjectName__c": "Order Line Item",
  "CalculationDateAttr__c": "Apttus_Config2__PricingDate__c",
  "CalculationDateName__c": "Pricing Date",
  "IncentiveAccountAttr__c": "Apttus_Config2__BillToAccountId__c",
  "IncentiveAccountFieldName__c": "Bill To",
  "ProductAttr__c": "Apttus_Config2__ProductId__c",
  "ProductFieldName__c": "Product",
  "FileSuffixToignore__c": "Test",
  "Delimiter__c": "\\u002c",
  "RecordDelimiterName__c": "commas",
  "FileExtension__c": "csv"
}
*/
