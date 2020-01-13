package com.apttus.sfdc.rebates.lightning.api.pojo;

import java.util.Map;
import com.apttus.sfdc.rebates.lightning.api.library.CIMAdmin;
import com.apttus.sfdc.rebates.lightning.generic.utils.SFDCHelper;
import com.google.gson.Gson;

public class CreateAdminTemplatePojo {

	private String Description__c;
	private String Status__c;
	private String QnB_Layout_Id__c;
	private String Name;
	public String adminTemplateId;

	public String getAdminTemplateId() {
		return adminTemplateId;
	}

	public void setAdminTemplateId(String templateId) {
		adminTemplateId = templateId;
	}

	public String getDescription__c() {
		return Description__c;
	}

	public void setDescription__c(String description__c) {
		this.Description__c = description__c;
	}

	public String getStatus__c() {
		return Status__c;
	}

	public void setStatus__c(String status__c) {
		this.Status__c = status__c;
	}

	public String getQnB_Layout_Id__c() {
		return QnB_Layout_Id__c;
	}

	public void setQnB_Layout_Id__c(String qnb_Layout_Id__c) {
		this.QnB_Layout_Id__c = qnb_Layout_Id__c;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		this.Name = name;
	}

	public String createAdminTemplateRequest(Map<String, String> testData, CIMAdmin cimAdmin, String qnbLayoutId) {

		CreateAdminTemplatePojo createAdminTemplatePojo = new CreateAdminTemplatePojo();
		createAdminTemplatePojo.setName(testData.get("Name"));
		if (testData.get("Name").equalsIgnoreCase("{RANDOM}")) {
			createAdminTemplatePojo.setName("Rebates_Auto_AdminTemplate_" + SFDCHelper.randomNumberGenerator());
		}
		createAdminTemplatePojo.setDescription__c(testData.get("Description__c"));
		createAdminTemplatePojo.setQnB_Layout_Id__c(qnbLayoutId);
		createAdminTemplatePojo.setStatus__c(testData.get("Status__c"));
		cimAdmin.setAdmintemplatedata(createAdminTemplatePojo);
		return new Gson().toJson(createAdminTemplatePojo);
	}
}
/*
 * {
 * "Description__c": "Shashank T3", "Name": "Test",
 * "QnB_Layout_Id__c": "a593i000000LC0tAAG", "Status__c": "Draft"
 * }
 */