package com.apttus.sfdc.rebates.lightning.api.pojo;

import com.apttus.sfdc.rebates.lightning.generic.utils.RebatesConstants;
import com.google.gson.Gson;

public class ActivateIncentivePojo {
	private IncentivePayloadPojo incentivePayload;

	public IncentivePayloadPojo getIncentivePayload() {
		return incentivePayload;
	}

	public void setIncentivePayload(IncentivePayloadPojo incentivePayload) {
		this.incentivePayload = incentivePayload;
	}

	public String activateIncentiveRequest(String incentiveId) {
		
		ActivateIncentivePojo activateIncentive = new ActivateIncentivePojo();
		IncentivePayloadPojo incentivePayload = new IncentivePayloadPojo();
		incentivePayload.setId(incentiveId);
		incentivePayload.setApttus_Config2__Status__c(RebatesConstants.statusActivated);
		activateIncentive.setIncentivePayload(incentivePayload);
		return new Gson().toJson(activateIncentive);
	}
}

/*-------------- Activate Incentive Request ----------------
{
   "incentivePayload":{
      "Id":"a1k3i0000015NoKAAU",
      "Apttus_Config2__Status__c":"Activated"
   }
}
*/