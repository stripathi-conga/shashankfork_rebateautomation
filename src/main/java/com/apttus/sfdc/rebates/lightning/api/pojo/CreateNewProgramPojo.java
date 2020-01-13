package com.apttus.sfdc.rebates.lightning.api.pojo;

import java.util.Map;
import com.apttus.customException.ApplicationException;
import com.apttus.sfdc.rebates.lightning.api.library.CIM;
import com.apttus.sfdc.rebates.lightning.generic.utils.SFDCHelper;
import com.google.gson.Gson;

public class CreateNewProgramPojo {
	
	private String Apttus_Config2__Description__c;
	private String MeasurementFrequency__c;
	private String Program_Template_Id__c;
	private String Apttus_Config2__Status__c;
	private String Apttus_Config2__ExpirationDate__c;
	private String Calendar__c;
	private String PaymentMethod__c;
	private String GracePeriod__c;
	private String Apttus_Config2__UseType__c;
	private String Name;
	private String PayoutFrequency__c;
	private String Currency__c;
	private String MeasurementLevel__c;
	private String BenefitLevel__c;
	private String AccountId__c;
	private String Apttus_Config2__EffectiveDate__c;
	private String Apttus_Config2__SubUseType__c;
	private String Apttus_Config2__Sequence__c;
	public String programId;

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public String getApttus_Config2__Description__c() {
		return Apttus_Config2__Description__c;
	}

	public void setApttus_Config2__Description__c(String apttus_Config2__Description__c) {
		this.Apttus_Config2__Description__c = apttus_Config2__Description__c;
	}

	public String getMeasurementFrequency__c() {
		return MeasurementFrequency__c;
	}

	public void setMeasurementFrequency__c(String measurementFrequency__c) {
		this.MeasurementFrequency__c = measurementFrequency__c;
	}

	public String getProgram_Template_Id__c() {
		return Program_Template_Id__c;
	}

	public void setProgram_Template_Id__c(String program_Template_Id__c) {
		this.Program_Template_Id__c = program_Template_Id__c;
	}

	public String getApttus_Config2__Status__c() {
		return Apttus_Config2__Status__c;
	}

	public void setApttus_Config2__Status__c(String apttus_Config2__Status__c) {
		this.Apttus_Config2__Status__c = apttus_Config2__Status__c;
	}

	public String getApttus_Config2__ExpirationDate__c() {
		return Apttus_Config2__ExpirationDate__c;
	}

	public void setApttus_Config2__ExpirationDate__c(String apttus_Config2__ExpirationDate__c) {
		this.Apttus_Config2__ExpirationDate__c = apttus_Config2__ExpirationDate__c;
	}

	public String getCalendar__c() {
		return Calendar__c;
	}

	public void setCalendar__c(String calendar__c) {
		this.Calendar__c = calendar__c;
	}

	public String getPaymentMethod__c() {
		return PaymentMethod__c;
	}

	public void setPaymentMethod__c(String paymentMethod__c) {
		this.PaymentMethod__c = paymentMethod__c;
	}

	public String getGracePeriod__c() {
		return GracePeriod__c;
	}

	public void setGracePeriod__c(String gracePeriod__c) {
		this.GracePeriod__c = gracePeriod__c;
	}

	public String getApttus_Config2__UseType__c() {
		return Apttus_Config2__UseType__c;
	}

	public void setApttus_Config2__UseType__c(String apttus_Config2__UseType__c) {
		this.Apttus_Config2__UseType__c = apttus_Config2__UseType__c;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		this.Name = name;
	}

	public String getPayoutFrequency__c() {
		return PayoutFrequency__c;
	}

	public void setPayoutFrequency__c(String payoutFrequency__c) {
		this.PayoutFrequency__c = payoutFrequency__c;
	}

	public String getCurrency__c() {
		return Currency__c;
	}

	public void setCurrency__c(String currency__c) {
		this.Currency__c = currency__c;
	}

	public String getMeasurementLevel__c() {
		return MeasurementLevel__c;
	}

	public void setMeasurementLevel__c(String measurementLevel__c) {
		this.MeasurementLevel__c = measurementLevel__c;
	}

	public String getAccountId__c() {
		return AccountId__c;
	}

	public void setAccountId__c(String accountId__c) {
		AccountId__c = accountId__c;
	}

	public String getBenefitLevel__c() {
		return BenefitLevel__c;
	}

	public void setBenefitLevel__c(String benefitLevel__c) {
		this.BenefitLevel__c = benefitLevel__c;
	}

	public String getApttus_Config2__EffectiveDate__c() {
		return Apttus_Config2__EffectiveDate__c;
	}

	public void setApttus_Config2__EffectiveDate__c(String apttus_Config2__EffectiveDate__c) {
		this.Apttus_Config2__EffectiveDate__c = apttus_Config2__EffectiveDate__c;
	}

	public String getApttus_Config2__SubUseType__c() {
		return Apttus_Config2__SubUseType__c;
	}

	public void setApttus_Config2__SubUseType__c(String apttus_Config2__SubUseType__c) {
		this.Apttus_Config2__SubUseType__c = apttus_Config2__SubUseType__c;
	}

	public String getApttus_Config2__Sequence__c() {
		return Apttus_Config2__Sequence__c;
	}

	public void setApttus_Config2__Sequence__c(String apttus_Config2__Sequence__c) {
		this.Apttus_Config2__Sequence__c = apttus_Config2__Sequence__c;
	}

	public String createNewProgramRequest(Map<String, String> testData, CIM cim) throws ApplicationException {
		String startDate, endDate;
		CreateNewProgramPojo createNewProgram = new CreateNewProgramPojo();
		createNewProgram.setName(testData.get("Name"));
		if (testData.get("Name") != null) {
			if (testData.get("Name").equalsIgnoreCase("{RANDOM}")) {
				createNewProgram.setName("Rebates_Auto_Program_" + SFDCHelper.randomNumberGenerator());
			}
		}
		if (testData.get("Apttus_Config2__EffectiveDate__c") != null) {
			startDate = cim.getCIMDateValue(testData.get("Apttus_Config2__EffectiveDate__c"));
			createNewProgram.setApttus_Config2__EffectiveDate__c(startDate);
		}
		if (testData.get("Apttus_Config2__ExpirationDate__c") != null) {
			endDate = cim.getCIMDateValue(testData.get("Apttus_Config2__ExpirationDate__c"));
			createNewProgram.setApttus_Config2__ExpirationDate__c(endDate);
		}		
		createNewProgram.setCurrency__c(testData.get("Currency__c"));
		createNewProgram.setApttus_Config2__Description__c("Automation Program");
		createNewProgram.setApttus_Config2__Status__c("New");
		createNewProgram.setApttus_Config2__UseType__c(testData.get("Apttus_Config2__UseType__c"));
		createNewProgram.setApttus_Config2__SubUseType__c(testData.get("Apttus_Config2__SubUseType__c"));
		createNewProgram.setApttus_Config2__Sequence__c("1");
		createNewProgram.setCalendar__c(testData.get("Calendar__c"));
		createNewProgram.setPaymentMethod__c(testData.get("PaymentMethod__c"));
		createNewProgram.setPayoutFrequency__c(testData.get("PayoutFrequency__c"));
		createNewProgram.setMeasurementFrequency__c(testData.get("MeasurementFrequency__c"));
		createNewProgram.setMeasurementLevel__c(testData.get("MeasurementLevel__c"));
		createNewProgram.setProgram_Template_Id__c(testData.get("Program_Template_Id__c"));
		createNewProgram.setBenefitLevel__c(testData.get("BenefitLevel__c"));
		if (testData.get("BenefitLevel__c") != null) {
			if (testData.get("BenefitLevel__c").equals("Agreement Account")) {
				createNewProgram.setAccountId__c(cim.getAccountId(testData.get("AccountName")));
			}
		}
		cim.setProgramData(createNewProgram);
		return new Gson().toJson(createNewProgram);
	}
}

/*
------------------------------ Create New Program Request -------------------------------{
    "Name": "TEST AUTO MJ_01",
    "Apttus_Config2__EffectiveDate__c": "2020-01-01",
    "Apttus_Config2__ExpirationDate__c": "2020-12-31",
    "Currency__c": "Dollar",
    "Apttus_Config2__Description__c": "TEST AUTO MJ",
    "Apttus_Config2__Status__c": "New",
    "Calendar__c": "Gregorian",
    "BenefitLevel__c": "Individual Participants",
    "PayoutFrequency__c": "Monthly",
    "PaymentMethod__c": "Credit Memo",
    "MeasurementLevel__c": "Agreement Account",
    "MeasurementFrequency__c": "Monthly",
    "Program_Template_Id__c": "a593i000000LC4lAAG",
    "Apttus_Config2__Sequence__c": 1,
    "Apttus_Config2__UseType__c": "Rebates 2.0",
    "Apttus_Config2__SubUseType__c": "Loyalty"
}

{
    "Name": "Mj Test",
    "Apttus_Config2__EffectiveDate__c": "2020-01-01",
    "Apttus_Config2__ExpirationDate__c": "2020-01-31",
    "Currency__c": "Dollar",
    "Apttus_Config2__Description__c": "Testing",
    "Apttus_Config2__Status__c": "New",
    "Calendar__c": "Gregorian",
    "BenefitLevel__c": "Agreement Account",
    "PayoutFrequency__c": "Monthly",
    "PaymentMethod__c": "Credit Memo",
    "MeasurementLevel__c": "Agreement Account",
    "MeasurementFrequency__c": "Monthly",
    "AccountId__c": "0013i00000DDWZ0AAP",
    "Program_Template_Id__c": "a593i000000LC4lAAG",
    "Apttus_Config2__Sequence__c": 1,
    "Apttus_Config2__UseType__c": "Rebates 2.0",
    "Apttus_Config2__SubUseType__c": "Loyalty"
}
*/