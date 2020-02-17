package com.apttus.sfdc.rebates.lightning.api.pojo;

import java.util.Map;
import com.apttus.customException.ApplicationException;
import com.apttus.sfdc.rebates.lightning.api.library.CIM;
import com.apttus.sfdc.rebates.lightning.generic.utils.SFDCHelper;
import com.google.gson.Gson;

public class AddParticipantPojo {

	private String ExpirationDate__c;
	private String Account__c;
	private String Incentive__c;
	private String EffectiveDate__c;
	public String participantsId;

	public String getParticipantsId() {
		return participantsId;
	}

	public void setParticipantsId(String participantsId) {
		this.participantsId = participantsId;
	}

	public String getExpirationDate__c() {
		return ExpirationDate__c;
	}

	public void setExpirationDate__c(String expirationDate__c) {
		this.ExpirationDate__c = expirationDate__c;
	}

	public String getAccount__c() {
		return Account__c;
	}

	public void setAccount__c(String account__c) {
		this.Account__c = account__c;
	}

	public String getIncentive__c() {
		return Incentive__c;
	}

	public void setIncentive__c(String incentive__c) {
		this.Incentive__c = incentive__c;
	}

	public String getEffectiveDate__c() {
		return EffectiveDate__c;
	}

	public void setEffectiveDate__c(String effectiveDate__c) {
		this.EffectiveDate__c = effectiveDate__c;
	}

	public String addParticipantsRequest(Map<String, String> testData, CIM cim) throws ApplicationException {
		String startDate, endDate;
		AddParticipantPojo addParticipantpojo = new AddParticipantPojo();
		if (testData.get("EffectiveDate__c") != null) {
			startDate = SFDCHelper.getCIMDateValue(testData.get("EffectiveDate__c"), cim);
			addParticipantpojo.setEffectiveDate__c(startDate);
		}
		if (testData.get("ExpirationDate__c") != null) {
			endDate = SFDCHelper.getCIMDateValue(testData.get("ExpirationDate__c"), cim);
			addParticipantpojo.setExpirationDate__c(endDate);
		}
		addParticipantpojo.setAccount__c(cim.getAccountId(testData.get("AccountName")));
		addParticipantpojo.setIncentive__c(cim.getIncentiveData().incentiveId);
		cim.setParticipantData(addParticipantpojo);
		return new Gson().toJson(addParticipantpojo);
	}
}
/*
 * ----Add Incentive Participant ---
 * 
 * { "Account__c": "0012f00000RPq9ZAAT", "EffectiveDate__c": "2019-12-01",
 * "ExpirationDate__c": "2019-12-31", "Incentive__c": "a3r2f0000000N5yAAE" }
 */