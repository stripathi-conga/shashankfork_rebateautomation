package com.apttus.sfdc.rebates.lightning.api.pojo;

import java.util.Map;

import com.apttus.helpers.JavaHelpers;
import com.apttus.sfdc.rebates.lightning.api.library.CIMAdmin;
import com.google.gson.Gson;

public class CreateAdminTemplatePojo {

	private String Description__c;
	private String Status__c;
	private String QnB_Layout_Id__c;
	private String Name;
	public String TemplateId;
	CreateAdminTemplatePojo createAdminTemplatePojo;
	private String adminTemplateName;
	public String getTemplateId() {
		return TemplateId;
	}

	public void setTemplateId(String templateId) {
		TemplateId = templateId;
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

	public String createAdminTemplateRequest(Map<String, String> testData, CIMAdmin cimAdmin) {

		createAdminTemplatePojo = new CreateAdminTemplatePojo();
		adminTemplateName = testData.get("Name");
		if (adminTemplateName.equalsIgnoreCase("{RANDOM}")) {
			adminTemplateName = "Rebates_Auto_AdminTemplate_" + JavaHelpers.generateRandomNumber();
		}
		createAdminTemplatePojo.setName(adminTemplateName);
		createAdminTemplatePojo.setDescription__c(testData.get("Description__c"));
		createAdminTemplatePojo.setQnB_Layout_Id__c(testData.get("QnB_Layout_Id__c"));
		createAdminTemplatePojo.setStatus__c(testData.get("Status__c"));
		
		cimAdmin.setAdmintemplatedata(createAdminTemplatePojo);
		return new Gson().toJson(createAdminTemplatePojo);
	}
	}


