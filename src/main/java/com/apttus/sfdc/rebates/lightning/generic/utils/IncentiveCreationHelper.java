package com.apttus.sfdc.rebates.lightning.generic.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import com.apttus.customException.ApplicationException;
import com.apttus.sfdc.rebates.lightning.api.library.CIM;
import com.apttus.sfdc.rebates.lightning.api.validator.ResponseValidatorBase;
import com.apttus.sfdc.rudiments.utils.SFDCRestUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.restassured.response.Response;

public class IncentiveCreationHelper {
	
	private String instanceURL;
	private SFDCRestUtils sfdcRestUtils;
	private Response response;
	private ResponseValidatorBase responseValidator;
	protected JsonParser parser = new JsonParser();	
	protected DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	protected SFDCHelper sfdcHelper = new SFDCHelper();
	public CIM cim;
	
	public IncentiveCreationHelper() throws Exception {
		sfdcRestUtils = new SFDCRestUtils();
		instanceURL = SFDCHelper.setAccessToken(sfdcRestUtils);	
		responseValidator = new ResponseValidatorBase();
		cim = new CIM(instanceURL, sfdcRestUtils);
	}
	 
	
	public Response createIncentiveAndFetchSchedules(Map<String, String> createIncentiveJson,String templateId,String startDate, String endDate, String paymentFrequency)
			throws Exception {
		
		// -------- Set-up JSON data for creating Incentive -----------------
		createIncentiveJson.put("ProgramTemplateId__c", templateId);
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
	
	public void createIncentiveAndUpdateSchedules(Map<String, String> createIncentiveJson,String templateId,String startDate, String endDate, String paymentFrequency)
			throws Exception {
		
		Response scheduleResponse = createIncentiveAndFetchSchedules(createIncentiveJson, RebatesConstants.incentiveTemplateIdBenefitProductDiscrete,startDate,endDate, RebatesConstants.paymentFrequencyMonthly);
		String responseBody = scheduleResponse.getBody().asString();
		JsonArray schedulesResponse = parser.parse(responseBody).getAsJsonArray();
		int responseSize = schedulesResponse.size();
		
		//By default all payout schedules having date less than today are created in Open status
		//To be able to test, manually update such schedules to pending before calling the job
		for (int i = 0; i < responseSize; i++) {
			JsonObject schedule = schedulesResponse.get(i).getAsJsonObject();
			String scheduleId = schedule.get("Id").getAsString();
			String scheduleStartDate = schedule.get("PeriodStartDate__c").getAsString();
			
			if(dateFormat.parse(scheduleStartDate).compareTo(dateFormat.parse(sfdcHelper.getTodaysDate())) < 0) {
				cim.updatePayoutScheduleStatusToPending(scheduleId);
			}
		}
	}

}
