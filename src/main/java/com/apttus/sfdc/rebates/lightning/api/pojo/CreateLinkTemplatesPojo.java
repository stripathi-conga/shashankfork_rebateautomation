package com.apttus.sfdc.rebates.lightning.api.pojo;

import java.util.Map;
import com.apttus.sfdc.rebates.lightning.api.library.CIMAdmin;
import com.google.gson.Gson;

public class CreateLinkTemplatesPojo {
	private String ProgramType__c;
	private String TemplateId__c;
	private String ProgramSubType__c;
	public String linkTemplateId;
	public String linkTemplateName;
	private String Status__c;
	
	public String getStatus__c() {
		return Status__c;
	}

	public void setStatus__c(String status__c) {
		Status__c = status__c;
	}

	public String getLinkTemplateName() {
		return linkTemplateName;
	}

	public void setLinkTemplateName(String linkTemplateName) {
		this.linkTemplateName = linkTemplateName;
	}

	public String getLinkTemplateId() {
		return linkTemplateId;
	}

	public void setLinkTemplateId(String linkTemplateId) {
		this.linkTemplateId = linkTemplateId;
	}

	public String getProgramType__c() {
		return ProgramType__c;
	}

	public void setProgramType__c(String programType__c) {
		this.ProgramType__c = programType__c;
	}

	public String getTemplateId__c() {
		return TemplateId__c;
	}

	public void setTemplateId__c(String templateId__c) {
		this.TemplateId__c = templateId__c;
	}

	public String getProgramSubType__c() {
		return ProgramSubType__c;
	}

	public void setProgramSubType__c(String programSubType__c) {
		this.ProgramSubType__c = programSubType__c;
	}

	public String createLinkTemplateRequest(Map<String, String> testData, CIMAdmin cimAdmin) {

		CreateLinkTemplatesPojo linkTemplate = new CreateLinkTemplatesPojo();
		linkTemplate.setProgramType__c(testData.get("ProgramType__c"));
		linkTemplate.setProgramSubType__c(testData.get("ProgramSubType__c"));
		linkTemplate.setTemplateId__c(cimAdmin.getTemplateData().getTemplateId());
		linkTemplate.setStatus__c(testData.get("Status__c"));
		cimAdmin.setLinkTemplatesData(linkTemplate);
		return new Gson().toJson(linkTemplate);
	}
}

/*------------------- Link Admin Template Request ------------------
{
	  "Program_Type__c": "Rebates 2.0",
	  "Program_Sub_Type__c": "Rebate",
	  "Template_Id__c": "a583i000000AJhiAAG"
}*/
