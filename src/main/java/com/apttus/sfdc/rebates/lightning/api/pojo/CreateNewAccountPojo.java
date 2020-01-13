package com.apttus.sfdc.rebates.lightning.api.pojo;

import com.apttus.sfdc.rebates.lightning.generic.utils.SFDCHelper;
import com.google.gson.Gson;

public class CreateNewAccountPojo {
	private String Type;
	private String AccountNumber;
	private String Name;
	private String Active__c;

	public String getType() {
		return Type;
	}

	public void setType(String Type) {
		this.Type = Type;
	}

	public String getAccountNumber() {
		return AccountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.AccountNumber = accountNumber;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		this.Name = name;
	}

	public String getActive__c() {
		return Active__c;
	}

	public void setActive__c(String active__c) {
		this.Active__c = active__c;
	}

	public String createNewAccountRequest(String accountName) {
		CreateNewAccountPojo createAccount = new CreateNewAccountPojo();
		String account = "Rebates_Auto_Account_" + SFDCHelper.randomNumberGenerator();
		createAccount.setAccountNumber(account);
		createAccount.setType("Customer");
		createAccount.setName(accountName);
		if (accountName.equalsIgnoreCase("{RANDOM}")) {
			createAccount.setName(account);
		}		
		createAccount.setActive__c("Yes");
		return new Gson().toJson(createAccount);
	}
}

/*
---------------------------- Create New Account Request --------------------
{
    "AccountNumber": "AUTO1234567",
    "Name": "Automation_Account_1",
    "Active__c":true,
    "Type": "Customer"
}
*/