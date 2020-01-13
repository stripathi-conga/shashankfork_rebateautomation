package com.apttus.sfdc.rebates.lightning.api.pojo;

import java.util.Map;
import com.apttus.sfdc.rebates.lightning.api.library.CIMAdmin;
import com.apttus.sfdc.rebates.lightning.generic.utils.SFDCHelper;
import com.google.gson.Gson;

public class CreateNewDataSourcePojo {
	
	private String Delimiter__c;
	private String Name__c;
	private String Product_Attr__c;
	private String File_Suffix_To_ignore__c;
	private String Transaction_MetaData__c;
	private String File_Extension__c;
	private String Calculation_Date_Attr__c;
	private String Program_Account_Attr__c;
	public String DataSourceId;

	public String getDataSourceId() {
		return DataSourceId;
	}

	public void setDataSourceId(String dataSourceId) {
		DataSourceId = dataSourceId;
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

	public String getProduct_Attr__c() {
		return Product_Attr__c;
	}

	public void setProduct_Attr__c(String product_Attr__c) {
		this.Product_Attr__c = product_Attr__c;
	}

	public String getFile_Suffix_To_ignore__c() {
		return File_Suffix_To_ignore__c;
	}

	public void setFile_Suffix_To_ignore__c(String file_Suffix_To_ignore__c) {
		this.File_Suffix_To_ignore__c = file_Suffix_To_ignore__c;
	}

	public String getTransaction_MetaData__c() {
		return Transaction_MetaData__c;
	}

	public void setTransaction_MetaData__c(String transaction_MetaData__c) {
		this.Transaction_MetaData__c = transaction_MetaData__c;
	}

	public String getFile_Extension__c() {
		return File_Extension__c;
	}

	public void setFile_Extension__c(String file_Extension__c) {
		this.File_Extension__c = file_Extension__c;
	}

	public String getCalculation_Date_Attr__c() {
		return Calculation_Date_Attr__c;
	}

	public void setCalculation_Date_Attr__c(String calculation_Date_Attr__c) {
		this.Calculation_Date_Attr__c = calculation_Date_Attr__c;
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
		createDataSource.setCalculation_Date_Attr__c(testData.get("Calculation_Date_Attr__c"));
		createDataSource.setDelimiter__c(testData.get("Delimiter__c"));
		createDataSource.setFile_Extension__c(testData.get("File_Extension__c"));
		createDataSource.setFile_Suffix_To_ignore__c(testData.get("File_Suffix_To_ignore__c"));
		createDataSource.setProduct_Attr__c(testData.get("Product_Attr__c"));
		createDataSource.setProgram_Account_Attr__c(testData.get("Program_Account_Attr__c"));
		createDataSource.setTransaction_MetaData__c(testData.get("Transaction_MetaData__c"));
		cimAdmin.setDataSourceData(createDataSource);
		return new Gson().toJson(createDataSource);
	}
}

/*------------------ Create DataSource Request Body --------------------------
{
    "Name__c": "Mj Auto221",
    "Transaction_MetaData__c": "Apttus_Config2__OrderLineItem__c",
    "Calculation_Date_Attr__c": "Apttus_Config2__PricingDate__c",
    "Program_Account_Attr__c": "Apttus_Config2__BillToAccountId__c",
    "Product_Attr__c": "Apttus_Config2__ProductId__c",
    "File_Extension__c": "csv",
    "File_Suffix_To_ignore__c": "test",
    "Delimiter__c": "\\u002c"
}
*/
