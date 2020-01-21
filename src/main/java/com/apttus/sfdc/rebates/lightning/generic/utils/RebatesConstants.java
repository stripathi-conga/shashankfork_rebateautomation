package com.apttus.sfdc.rebates.lightning.generic.utils;

public class RebatesConstants {

	public static final int responseOk = 200;
	public static final int responseCreated = 201;
	public static final int responseNocontent = 204;
	public static final int responseBadRequest = 400;	
	public static final String activate = "Active";
	public static final String deactivate = "Inactive";
	public static final String draft = "Draft";
	public static final String errorCodeCustomValidation = "FIELD_CUSTOM_VALIDATION_EXCEPTION";
	public static final String errorCodeMissingFields = "REQUIRED_FIELD_MISSING";
	public static final String messageDeleteActiveInactiveTemplate = "Cannot delete Active or Inactive Template.";
	public static final String messageMandatoryTemplatefields = "Template must have at least one benefit formula";
	public static final String messageUpdateActiveInactiveTemplate = "Cannot change status from Active/Inactive to Draft";
	public static final String messageDeleteActiveInactiveLinkTemplate = "Cannot delete Active or Inactive link.";
	public static final String messageCreateDataSourceWithoutFields = "Required fields are missing: [File_Extension__c, File_Suffix_To_ignore__c, Name__c, Transaction_MetaData__c]";
	public static final String messageChangeLinkTemplateStatusToDraft = "Cannot change Status from \"Active\" to \"Draft\"";
	public static final String messageActiveMappingExists = "An active mapping already exists.";
}