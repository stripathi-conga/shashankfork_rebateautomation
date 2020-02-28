package com.apttus.sfdc.rebates.lightning.generic.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import com.apttus.helpers.Efficacies;
import com.apttus.sfdc.rebates.lightning.api.library.BenefitProductQnB;
import com.apttus.sfdc.rebates.lightning.api.validator.BenefitProductValidator;
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
	private JsonParser parser = new JsonParser();	
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private SFDCHelper sfdcHelper = new SFDCHelper();
	private Efficacies efficacies;
	private BenefitProductValidator qnbResponseValidator;
	public BenefitProductQnB benefitProductQnB;	
	
	public IncentiveCreationHelper() throws Exception {
		sfdcRestUtils = new SFDCRestUtils();
		instanceURL = SFDCHelper.setAccessToken(sfdcRestUtils);	
		responseValidator = new ResponseValidatorBase();
		qnbResponseValidator = new BenefitProductValidator();		
		benefitProductQnB = new BenefitProductQnB(instanceURL, sfdcRestUtils);
		efficacies = new Efficacies();
	}
	 
	
	public Response createIncentiveAndFetchSchedules(Map<String, String> createIncentiveJson,String templateId,String startDate, String endDate, String paymentFrequency)
			throws Exception {
		
		// -------- Set-up JSON data for creating Incentive -----------------
		createIncentiveJson.put("ProgramTemplateId__c", templateId);
		createIncentiveJson.put("Apttus_Config2__EffectiveDate__c", startDate);
		createIncentiveJson.put("Apttus_Config2__ExpirationDate__c", endDate);
		createIncentiveJson.put("PayoutFrequency__c", paymentFrequency);

		// -------- Create and Validate Incentive -----------------
		benefitProductQnB.createNewIncentive(createIncentiveJson);
		response = benefitProductQnB.getIncentiveDetails();
		responseValidator.validateIncentiveDetails(createIncentiveJson, response, benefitProductQnB);
		
		// -------- Add QnB Line to the Incentive ------------------
		List<Map<String,String>> jsonArrayData = SFDCHelper.readJsonArray("CIMIncentiveQnBData.json", "XXDBenefitProduct");
		benefitProductQnB.addIncentiveQnB(jsonArrayData);
		response = benefitProductQnB.getIncentiveQnB();
		qnbResponseValidator.validateIncentiveQnB(benefitProductQnB.getRequestValue("addQnBRequest"), response);

		// -------- Add Participant to the Incentive ------------------
		Map<String,String> jsonData = efficacies.readJsonElement("CIMTemplateData.json", "addParticipants");
		jsonData.put("EffectiveDate__c", startDate);
		jsonData.put("ExpirationDate__c", endDate);
		benefitProductQnB.addParticipants(jsonData);
		response = benefitProductQnB.getParticipantsDetails();
		responseValidator.validateParticipantsDetails(jsonData, response, benefitProductQnB);
		
		// -------- Activate the incentive which will generate payout schedules -----------------
		benefitProductQnB.activateIncentive();
		response = benefitProductQnB.getIncentiveDetails();
		responseValidator.validateIncentiveStatus(RebatesConstants.statusActivated, response, benefitProductQnB.getIncentiveData().incentiveId);
		return benefitProductQnB.getPayoutSchedules();
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
				benefitProductQnB.updatePayoutScheduleStatusToPending(scheduleId);
			}
		}
	}

}
