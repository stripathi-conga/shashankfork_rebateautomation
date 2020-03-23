package com.apttus.sfdc.rebates.lightning.generic.utils;

import java.util.HashMap;
import java.util.Map;

public class RebatesConstants {

	// These values will be set in @BeforeSuite and used in all Incentive classes
	public static String incentiveTemplateIdBenefitProductTiered;
	public static String incentiveTemplateIdBenefitProductDiscrete;
	public static Map<String, String> formulaDataMap = new HashMap<String, String>();

	public static final int responseOk = 200;
	public static final int responseCreated = 201;
	public static final int responseNocontent = 204;
	public static final int responseBadRequest = 400;
	public static final int responseServerError = 500;
	public static final String activate = "Active";
	public static final String deactivate = "Inactive";
	public static final String draft = "Draft";
	public static final String statusActivated = "Activated";
	public static final String errorCodeCustomValidation = "FIELD_CUSTOM_VALIDATION_EXCEPTION";
	public static final String errorCodeMissingFields = "REQUIRED_FIELD_MISSING";
	public static final String errorCodeApexError = "APEX_ERROR";
	public static final String messageDeleteActiveInactiveTemplate = "Cannot delete Active or Inactive Template.";
	public static final String messageMandatoryTemplatefields = "Mandatory fields missing to activate the template";
	public static final String messageUpdateActiveInactiveTemplate = "Cannot change status from Active/Inactive to Draft";
	public static final String messageDeleteActiveInactiveLinkTemplate = "Cannot delete Active or Inactive link.";
	public static final String messageCreateDataSourceWithoutFields = "Required fields are missing: [FileExtension__c, FileSuffixToignore__c, TransactionMetaData__c]";
	public static final String messageChangeLinkTemplateStatusToDraft = "Cannot change Status from \"Active\" to \"Draft\"";
	public static final String messageActiveMappingExists = "An active mapping already exists.";
	public static final String messageBenefitDatesOutOfRange = "Benefits with dates outside the range of Incentive dates found";
	public static final String messageParticipantsDateOutOfRange = "Participants Dates are outside the range of Incentive Dates.";
	public static final String messageOverlappingParticipants = "Same participants with overlapping dates found.";
	public static final String errorFieldsForDates = "[\"EffectiveDate__c\",\"ExpirationDate__c\"]";
	public static final String messageActivateLinkTemplateForInactiveTemplate = "Only active templates can be mapped";

	public static final String scheduleStatusOpen = "Open";
	public static final String scheduleStatusPending = "Pending";
	public static final String scheduleStatusReady = "Ready";
	public static final String paymentFrequencyMonthly = "Monthly";
	public static final String paymentFrequencyYearly = "Yearly";
	public static final String paymentFrequencyQuarterly = "Quarterly";
	public static final String paymentFrequencyHalfYearly = "Half Yearly";

	/* UI Constants ------------------------------------------ */
	public static final String messagequalificationformulavalidation = "No data to display";
	public static final String mandatoryMessageBenefitFormula = "Benefit formula is required";
	public static final String mandatoryMessageUnsavedChanges = "Please save the unsaved changes to activate the Incentive";
	public static final String messageFailToActivateWithoutParticipant = "Failed to activate incentive - Incentive should have at least one participant configured.";
	public static final String messageFailToActivateWithoutQnB = "Failed to activate incentive - Incentive should have at least one benefit configured.";
	public static final String viewPath = "view";
	public static final String homePath = "home";
	public static final String newParticipant = "New";
	public static final String messageMandatoryDataSource = "Please enter Data Source Name to save";
	public static final String messageMandatoryTransactionLineObject = "Please select Transaction Line Object to save";
	public static final String messageMandatoryCalculationDate = "Please select Calculation Date Field to save";
	public static final String messageMandatoryIncentiveAccount = "Please select Incentive Account Field to save";
	public static final String messageMandatoryFileSuffix = "Please enter File Suffix To ignore to save";
	public static final String messageMandatoryFileExtension = "Please select File Extension to save";
	public static final String messageMandatoryRecordDelimter = "Please select Record Delimiter to save";
	public static final String messageMandatoryProduct = "Please select Product Field to save";
	public static final String messageActivateSuccessfully = "Template activated successfully";
	public static final String messageSavedSuccessfully = "Template saved successfully";
	public static final String newPath = "new";
	public static final String editPath = "edit";

}