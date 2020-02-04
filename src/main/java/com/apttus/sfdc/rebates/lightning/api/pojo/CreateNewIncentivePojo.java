package com.apttus.sfdc.rebates.lightning.api.pojo;

import java.util.Map;
import com.apttus.customException.ApplicationException;
import com.apttus.sfdc.rebates.lightning.api.library.CIM;
import com.apttus.sfdc.rebates.lightning.generic.utils.SFDCHelper;
import com.google.gson.Gson;

public class CreateNewIncentivePojo {
	
	private String Apttus_Config2__Description__c;
	private String MeasurementFrequency__c;
	private String ProgramTemplateId__c; 
	
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
	private String Apttus_Config2__Sequence__c;
	public String incentiveId;
	private String IncentiveType__c;
	private String IncentiveSubType__c;
	
	public String geIncentiveSubType__c() {
		return IncentiveSubType__c;
	}

	public void setIncentiveSubType__c(String incentiveSubType__c) {
		IncentiveSubType__c = incentiveSubType__c;
	}

	public String getIncentiveType__c() {
		return IncentiveType__c;
	}

	public void setProgramType__c(String incentiveType__c) {
		IncentiveType__c = incentiveType__c;
	}
	
	public String getIncentiveId() {
		return incentiveId;
	}

	public void setIncentiveId(String incentiveId) {
		this.incentiveId = incentiveId;
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

	public String getProgramTemplateId__c() {
		return ProgramTemplateId__c;
	}

	public void setProgramTemplateId__c(String programTemplateId__c) {
		this.ProgramTemplateId__c = programTemplateId__c;
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

	public String getApttus_Config2__Sequence__c() {
		return Apttus_Config2__Sequence__c;
	}

	public void setApttus_Config2__Sequence__c(String apttus_Config2__Sequence__c) {
		this.Apttus_Config2__Sequence__c = apttus_Config2__Sequence__c;
	}

	public String createNewIncentiveRequest(Map<String, String> testData, CIM cim) throws ApplicationException {
		String startDate, endDate;
		CreateNewIncentivePojo createNewIncentive = new CreateNewIncentivePojo();
		createNewIncentive.setName(testData.get("Name"));
		if (testData.get("Name") != null) {
			if (testData.get("Name").equalsIgnoreCase("{RANDOM}")) {
				createNewIncentive.setName("Rebates_Auto_Incentive_" + SFDCHelper.randomNumberGenerator());
			}
		}
		if (testData.get("Apttus_Config2__EffectiveDate__c") != null) {
			startDate = cim.getCIMDateValue(testData.get("Apttus_Config2__EffectiveDate__c"));
			createNewIncentive.setApttus_Config2__EffectiveDate__c(startDate);
		}
		if (testData.get("Apttus_Config2__ExpirationDate__c") != null) {
			endDate = cim.getCIMDateValue(testData.get("Apttus_Config2__ExpirationDate__c"));
			createNewIncentive.setApttus_Config2__ExpirationDate__c(endDate);
		}		
		createNewIncentive.setCurrency__c(testData.get("Currency__c"));
		createNewIncentive.setApttus_Config2__Description__c("Automation Program");
		createNewIncentive.setApttus_Config2__Status__c("New");
		createNewIncentive.setApttus_Config2__UseType__c(testData.get("Apttus_Config2__UseType__c"));
		createNewIncentive.setApttus_Config2__Sequence__c("1");
		createNewIncentive.setCalendar__c(testData.get("Calendar__c"));
		createNewIncentive.setPaymentMethod__c(testData.get("PaymentMethod__c"));
		createNewIncentive.setPayoutFrequency__c(testData.get("PayoutFrequency__c"));
		createNewIncentive.setMeasurementFrequency__c(testData.get("MeasurementFrequency__c"));
		createNewIncentive.setMeasurementLevel__c(testData.get("MeasurementLevel__c"));
		createNewIncentive.setProgramTemplateId__c(testData.get("Id"));
		createNewIncentive.setBenefitLevel__c(testData.get("BenefitLevel__c"));
		createNewIncentive.setProgramType__c(testData.get("IncentiveType__c"));
		createNewIncentive.setIncentiveSubType__c(testData.get("IncentiveSubType__c"));
		if (testData.get("BenefitLevel__c") != null) {
			if (testData.get("BenefitLevel__c").equals("Agreement Account")) {
				createNewIncentive.setAccountId__c(cim.getAccountId(testData.get("AccountName")));
			}
		}
		cim.setIncentiveData(createNewIncentive);
		return new Gson().toJson(createNewIncentive);
	}

	
}

/*
------------------------------ Create New Incentive Request -------------------------------{
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
  
}
*/