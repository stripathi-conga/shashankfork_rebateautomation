package com.apttus.sfdc.rebates.lightning.api.pojo;

import java.util.Map;
import com.apttus.sfdc.rebates.lightning.api.library.CIMAdmin;
import com.apttus.sfdc.rebates.lightning.generic.utils.SFDCHelper;
import com.google.gson.Gson;

public class CreateNewDataSourcePojo {
	
	private String Delimiter__c;
	private String Name__c;
	private String ProductAttr__c;
	private String FileSuffixToignore__c;
	private String TransactionMetaData__c;
	private String FileExtension__c;
	private String CalculationDateAttr__c;
	private String Program_Account_Attr__c;
	public String dataSourceId;

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

	public String getName__c() {
		return Name__c;
	}

	public void setName__c(String name__c) {
		this.Name__c = name__c;
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

	public String getProgram_Account_Attr__c() {
		return Program_Account_Attr__c;
	}

	public void setProgram_Account_Attr__c(String program_Account_Attr__c) {
		this.Program_Account_Attr__c = program_Account_Attr__c;
	}

	public String createDataSourceRequest(Map<String, String> testData, CIMAdmin cimAdmin) {
		CreateNewDataSourcePojo createDataSource = new CreateNewDataSourcePojo();
		createDataSource.setName__c(testData.get("Name__c"));
		if (testData.get("Name__c").equalsIgnoreCase("{RANDOM}")) {
			createDataSource.setName__c("Rebates_Auto_DataSource_" + SFDCHelper.randomNumberGenerator());
		}		
		createDataSource.setCalculationDateAttr__c(testData.get("CalculationDateAttr__c"));
		createDataSource.setDelimiter__c(testData.get("Delimiter__c"));
		createDataSource.setFileExtension__c(testData.get("FileExtension__c"));
		createDataSource.setFileSuffixToignore__c(testData.get("FileSuffixToignore__c"));
		createDataSource.setProductAttr__c(testData.get("ProductAttr__c"));
		createDataSource.setProgram_Account_Attr__c(testData.get("Program_Account_Attr__c"));
		createDataSource.setTransactionMetaData__c(testData.get("TransactionMetaData__c"));
		cimAdmin.setDataSourceData(createDataSource);
		return new Gson().toJson(createDataSource);
	}
}

/*------------------ Create DataSource Request Body --------------------------
{
    "Name__c": "Mj Auto221",
    "TransactionMetaData__c": "Apttus_Config2__OrderLineItem__c",
    "CalculationDateAttr__c": "Apttus_Config2__PricingDate__c",
    "Program_Account_Attr__c": "Apttus_Config2__BillToAccountId__c",
    "ProductAttr__c": "Apttus_Config2__ProductId__c",
    "FileExtension__c": "csv",
    "FileSuffixToignore__c": "test",
    "Delimiter__c": "\\u002c"
}
*/
