package com.apttus.sfdc.rebates.lightning.api.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.apttus.customException.ApplicationException;
import com.apttus.sfdc.rebates.lightning.api.library.CIM;
import com.apttus.sfdc.rebates.lightning.generic.utils.SFDCHelper;
import com.google.gson.Gson;

public class AddParticipantPojo {

	public IncentiveParticipantPojo incentiveParticipant;
	private List<String> errorMessages;
	private List<String> errorFields;

	public IncentiveParticipantPojo getIncentiveParticipant() {
		return incentiveParticipant;
	}

	public void setIncentiveParticipant(IncentiveParticipantPojo incentiveParticipant) {
		this.incentiveParticipant = incentiveParticipant;
	}

	public List<String> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public List<String> getErrorFields() {
		return errorFields;
	}

	public void setErrorFields(List<String> error) {
		this.errorFields = error;
	}

	public String addParticipantsRequest(Map<String, String> testData, CIM cim) throws ApplicationException {
		String startDate, endDate;
		AddParticipantPojo addParticipantPojo = new AddParticipantPojo();
		IncentiveParticipantPojo incentiveParticipant = new IncentiveParticipantPojo();
		if (testData.get("EffectiveDate__c") != null) {
			startDate = SFDCHelper.getCIMDateValue(testData.get("EffectiveDate__c"), cim);
			incentiveParticipant.setEffectiveDate__c(startDate);
		}
		if (testData.get("ExpirationDate__c") != null) {
			endDate = SFDCHelper.getCIMDateValue(testData.get("ExpirationDate__c"), cim);
			incentiveParticipant.setExpirationDate__c(endDate);
		}
		incentiveParticipant.setAccount__c(cim.getAccountId(testData.get("AccountName")));
		incentiveParticipant.setIncentive__c(cim.getIncentiveData().incentiveId);
		incentiveParticipant.setId(testData.get("Id"));
		addParticipantPojo.setIncentiveParticipant(incentiveParticipant);
		List<String> error = new ArrayList<String>();
		addParticipantPojo.setErrorFields(error);
		addParticipantPojo.setErrorMessages(error);

		List<AddParticipantPojo> listpojo = new ArrayList<AddParticipantPojo>();
		listpojo.add(addParticipantPojo);

		cim.setParticipantData(addParticipantPojo);
		return new Gson().toJson(listpojo);
	}
}
/*
 * ----Add Incentive Participant --- {"incentiveParticipantStr":
 "[{\"incentiveParticipant\":{\"EffectiveDate__c\":\"2020-03-03\",\"ExpirationDate__c\":\"2020-03-31\",
 \"Account__c\":\"0013i00000GHOoIAAX\",\"Incentive__c\":\"a1k3i000001VbfsAAC\"},\"errorMessages\":[],
 \"errorFields\":[]}]"}
 */