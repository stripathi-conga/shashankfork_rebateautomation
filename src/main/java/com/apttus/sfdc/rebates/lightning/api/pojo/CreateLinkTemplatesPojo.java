package com.apttus.sfdc.rebates.lightning.api.pojo;

import java.util.Map;
import com.apttus.sfdc.rebates.lightning.api.library.CIMAdmin;
import com.google.gson.Gson;

public class CreateLinkTemplatesPojo {
	private String Program_Type__c;
	private String Template_Id__c;
	private String Program_Sub_Type__c;
	public String linkTemplateId;
	public String linkTemplateName;

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

	public String getProgram_Type__c() {
		return Program_Type__c;
	}

	public void setProgram_Type__c(String program_Type__c) {
		this.Program_Type__c = program_Type__c;
	}

	public String getTemplate_Id__c() {
		return Template_Id__c;
	}

	public void setTemplate_Id__c(String template_Id__c) {
		this.Template_Id__c = template_Id__c;
	}

	public String getProgram_Sub_Type__c() {
		return Program_Sub_Type__c;
	}

	public void setProgram_Sub_Type__c(String program_Sub_Type__c) {
		this.Program_Sub_Type__c = program_Sub_Type__c;
	}

	public String createLinkTemplateRequest(Map<String, String> testData, CIMAdmin cimAdmin) {

		CreateLinkTemplatesPojo linkTemplate = new CreateLinkTemplatesPojo();
		linkTemplate.setProgram_Type__c(testData.get("Program_Type__c"));
		linkTemplate.setProgram_Sub_Type__c(testData.get("Program_Sub_Type__c"));
		linkTemplate.setTemplate_Id__c(cimAdmin.getAdminTemplateData().getAdminTemplateId());
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
