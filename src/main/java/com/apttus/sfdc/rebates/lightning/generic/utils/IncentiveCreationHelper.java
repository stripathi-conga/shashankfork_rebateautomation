package com.apttus.sfdc.rebates.lightning.generic.utils;

import java.util.Map;

import com.apttus.helpers.Efficacies;
import com.apttus.sfdc.rebates.lightning.api.library.CIM;
import com.apttus.sfdc.rebates.lightning.api.validator.ResponseValidatorBase;
import com.apttus.sfdc.rudiments.utils.SFDCRestUtils;
import com.jayway.restassured.response.Response;

public class IncentiveCreationHelper {
	
	private static String instanceURL;
	private static SFDCRestUtils sfdcRestUtils;
	private static Efficacies efficacies;
	private static Response response;
	private static ResponseValidatorBase responseValidator;
	
	public static Response createIncentiveAndFetchSchedules(Map<String, String> createIncentiveJson, String startDate, String endDate, String paymentFrequency)
			throws Exception {
		
		sfdcRestUtils = new SFDCRestUtils();
		instanceURL = SFDCHelper.setAccessToken(sfdcRestUtils);
		efficacies = new Efficacies();		
		responseValidator = new ResponseValidatorBase();
		
		CIM cim = new CIM(instanceURL, sfdcRestUtils);		
		
		// -------- Set-up JSON data for creating Incentive -----------------
		String incentiveTemplateIdBenefitProductDiscrete = cim.getTemplateIdForIncentives(createIncentiveJson);
		createIncentiveJson.put("ProgramTemplateId__c", incentiveTemplateIdBenefitProductDiscrete);
		createIncentiveJson.put("Apttus_Config2__EffectiveDate__c", startDate);
		createIncentiveJson.put("Apttus_Config2__ExpirationDate__c", endDate);
		createIncentiveJson.put("PayoutFrequency__c", paymentFrequency);

		// -------- Create and Validate Incentive -----------------
		cim.createNewIncentive(createIncentiveJson);
		response = cim.getIncentiveDetails();
		responseValidator.validateIncentiveDetails(createIncentiveJson, response, cim);

		// -------- Activate the incentive which will generate payout schedules -----------------
		cim.activateIncentive();
		response = cim.getIncentiveDetails();
		responseValidator.validateIncentiveStatus(RebatesConstants.statusActivated, response, cim.getIncentiveData().incentiveId);
		return cim.getPayoutSchedules();
	}

}
