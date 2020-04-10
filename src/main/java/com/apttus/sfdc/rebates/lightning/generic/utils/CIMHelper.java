package com.apttus.sfdc.rebates.lightning.generic.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.apttus.customException.ApplicationException;
import com.apttus.helpers.Efficacies;
import com.apttus.sfdc.rebates.lightning.api.library.BenefitProductQnB;
import com.apttus.sfdc.rebates.lightning.api.library.CIM;
import com.apttus.sfdc.rebates.lightning.api.library.CIMAdmin;
import com.apttus.sfdc.rebates.lightning.api.validator.BenefitProductValidator;
import com.jayway.restassured.response.Response;

public class CIMHelper {

	public Efficacies efficacies;
	public Map<String, String> jsonData;
	public String stepCalcFormulaIdBenefit;
	public String stepCalcFormulaIdQualification;
	public String nonStepCalcFormulaIdBenefit;
	public String nonStepCalcFormulaIdQualification;
	public Response response;
	public BenefitProductValidator responseValidator;
    public String volQtyExpressionId, volSumExpressionId, volumeCalcFormulaIdQualificationNonStep;
    public String revBasedQtyExpressionId, revBasedSumExpressionId, revBasedCalcFormulaIdQualificationNonStep; 
    public String revPerctAdjustmentAmtExpressionId, revPerctSumExpressionId, revPerctCalcFormulaIdBenefitNonStep;
    public String amtPerUnitQtyExpressionId, amtPerUnitSumExpressionId, amtPerUnitCalcFormulaIdBenefitNonStep;
	public List<Map<String, String>> jsonArrayData;
	public Map<String, String> formulaMap;

	public CIMHelper() throws Exception {
		efficacies = new Efficacies();
		responseValidator = new BenefitProductValidator();
		jsonArrayData = new ArrayList<Map<String, String>>();
		formulaMap = new HashMap<String, String>();
	}
	
	public void deleteDataSourceForIncentive(CIMAdmin cimAdmin) throws Exception {
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewDataSourceOrderLineItem");
		String id = cimAdmin.getDataSourceForTransactionLineObjectName(jsonData);
		if (id != null) {
			cimAdmin.deleteDataSource(id);
			response = cimAdmin.getDataSourceForId(id);
			responseValidator.validateDeleteSuccess(response);
		}
	}

	public void createDataSourceAndFormulasForIncentives(CIMAdmin cimAdmin) throws Exception {
		// ----- Create New DataSource -------------------
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewDataSourceOrderLineItem");
		DataHelper.setIncentiveDataSourceId(cimAdmin.createDataSource(jsonData));
		DataHelper.setIncentiveDataSourceName(cimAdmin.getDataSourceData().getName());

		// Create Formula Expression, Calculation Formula and Link Formula for Volume Based
		jsonData = efficacies.readJsonElement("CIMFormulaData.json", "volQtyExpressionId");
		volQtyExpressionId = cimAdmin.getFieldExpressionId(jsonData);

		jsonData = efficacies.readJsonElement("CIMFormulaData.json", "volSumExpressionId");
		volSumExpressionId = cimAdmin.getFieldExpressionId(jsonData);

		jsonData = efficacies.readJsonElement("CIMFormulaData.json", "volumeCalcFormulaIdQualificationNonStep");
		volumeCalcFormulaIdQualificationNonStep = cimAdmin.getCalcFormulaId(jsonData);
		formulaMap.put("volumeCalcFormulaIdQualificationNonStep", volumeCalcFormulaIdQualificationNonStep);
		DataHelper.setFormulaDataMap(formulaMap);

		jsonData = efficacies.readJsonElement("CIMFormulaData.json", "linkVolumeCalcFormulaToVolQtyExpressionId");
		cimAdmin.linkCalcFormulaToExpression(jsonData,
				DataHelper.getFormulaDataMap().get("volumeCalcFormulaIdQualificationNonStep"), volQtyExpressionId);

		jsonData = efficacies.readJsonElement("CIMFormulaData.json", "linkVolumeCalcFormulaToVolSumExpressionId");
		cimAdmin.linkCalcFormulaToExpression(jsonData,
				DataHelper.getFormulaDataMap().get("volumeCalcFormulaIdQualificationNonStep"), volSumExpressionId);

		// Link DataSource to Calculation Formula
		cimAdmin.linkDatasourceToCalcFormula(DataHelper.getIncentiveDataSourceId(),
				DataHelper.getFormulaDataMap().get("volumeCalcFormulaIdQualificationNonStep"));

		// Create Formula Expression, Calculation Formula and Link Formula for Revenue Based
		jsonData = efficacies.readJsonElement("CIMFormulaData.json", "revBasedQtyExpressionId");
		revBasedQtyExpressionId = cimAdmin.getFieldExpressionId(jsonData);

		jsonData = efficacies.readJsonElement("CIMFormulaData.json", "revBasedSumExpressionId");
		revBasedSumExpressionId = cimAdmin.getFieldExpressionId(jsonData);

		jsonData = efficacies.readJsonElement("CIMFormulaData.json", "revBasedCalcFormulaIdQualificationNonStep");
		revBasedCalcFormulaIdQualificationNonStep = cimAdmin.getCalcFormulaId(jsonData);
		formulaMap.put("revBasedCalcFormulaIdQualificationNonStep", revBasedCalcFormulaIdQualificationNonStep);

		DataHelper.setFormulaDataMap(formulaMap);

		jsonData = efficacies.readJsonElement("CIMFormulaData.json",
				"linkRevBasedCalcFormulaToRevBasedQtyExpressionId");
		cimAdmin.linkCalcFormulaToExpression(jsonData,
				DataHelper.getFormulaDataMap().get("revBasedCalcFormulaIdQualificationNonStep"),
				revBasedQtyExpressionId);

		jsonData = efficacies.readJsonElement("CIMFormulaData.json",
				"linkRevBasedCalcFormulaToRevBasedSumExpressionId");
		cimAdmin.linkCalcFormulaToExpression(jsonData,
				DataHelper.getFormulaDataMap().get("revBasedCalcFormulaIdQualificationNonStep"),
				revBasedSumExpressionId);

		// Link DataSource to Calculation Formula
		cimAdmin.linkDatasourceToCalcFormula(DataHelper.getIncentiveDataSourceId(),
				DataHelper.getFormulaDataMap().get("revBasedCalcFormulaIdQualificationNonStep"));

		// Create Formula Expression, Calculation Formula and Link Formula for % of Revenue
		jsonData = efficacies.readJsonElement("CIMFormulaData.json", "revPerctAdjustmentAmtExpressionId");
		revPerctAdjustmentAmtExpressionId = cimAdmin.getFieldExpressionId(jsonData);

		jsonData = efficacies.readJsonElement("CIMFormulaData.json", "revPerctSumExpressionId");
		revPerctSumExpressionId = cimAdmin.getFieldExpressionId(jsonData);

		jsonData = efficacies.readJsonElement("CIMFormulaData.json", "revPerctCalcFormulaIdBenefitNonStep");
		revPerctCalcFormulaIdBenefitNonStep = cimAdmin.getCalcFormulaId(jsonData);
		formulaMap.put("revPerctCalcFormulaIdBenefitNonStep", revPerctCalcFormulaIdBenefitNonStep);

		DataHelper.setFormulaDataMap(formulaMap);

		jsonData = efficacies.readJsonElement("CIMFormulaData.json",
				"linkRevPercentCalcFormulaToRevPerctAdjustmentAmtExpressionId");
		cimAdmin.linkCalcFormulaToExpression(jsonData,
				DataHelper.getFormulaDataMap().get("revPerctCalcFormulaIdBenefitNonStep"),
				revPerctAdjustmentAmtExpressionId);

		jsonData = efficacies.readJsonElement("CIMFormulaData.json",
				"linkRevPercentCalcFormulaToRevPerctSumExpressionId");
		cimAdmin.linkCalcFormulaToExpression(jsonData,
				DataHelper.getFormulaDataMap().get("revPerctCalcFormulaIdBenefitNonStep"), revPerctSumExpressionId);

		// Link DataSource to Calculation Formula
		cimAdmin.linkDatasourceToCalcFormula(DataHelper.getIncentiveDataSourceId(),
				DataHelper.getFormulaDataMap().get("revPerctCalcFormulaIdBenefitNonStep"));

		// Create Formula Expression, Calculation Formula and Link Formula for Amount per unit
		jsonData = efficacies.readJsonElement("CIMFormulaData.json", "amtPerUnitQtyExpressionId");
		amtPerUnitQtyExpressionId = cimAdmin.getFieldExpressionId(jsonData);

		jsonData = efficacies.readJsonElement("CIMFormulaData.json", "amtPerUnitSumExpressionId");
		amtPerUnitSumExpressionId = cimAdmin.getFieldExpressionId(jsonData);

		jsonData = efficacies.readJsonElement("CIMFormulaData.json", "amtPerUnitCalcFormulaIdBenefitNonStep");
		amtPerUnitCalcFormulaIdBenefitNonStep = cimAdmin.getCalcFormulaId(jsonData);
		formulaMap.put("amtPerUnitCalcFormulaIdBenefitNonStep", amtPerUnitCalcFormulaIdBenefitNonStep);

		DataHelper.setFormulaDataMap(formulaMap);

		jsonData = efficacies.readJsonElement("CIMFormulaData.json",
				"linkAmtPerUnitCalcFormulaToAmtPerUnitQtyExpressionId");
		cimAdmin.linkCalcFormulaToExpression(jsonData,
				DataHelper.getFormulaDataMap().get("amtPerUnitCalcFormulaIdBenefitNonStep"), amtPerUnitQtyExpressionId);

		jsonData = efficacies.readJsonElement("CIMFormulaData.json",
				"linkAmtPerUnitCalcFormulaToAmtPerUnitSumExpressionId");
		cimAdmin.linkCalcFormulaToExpression(jsonData,
				DataHelper.getFormulaDataMap().get("amtPerUnitCalcFormulaIdBenefitNonStep"), amtPerUnitSumExpressionId);

		// Link DataSource to Calculation Formula
		cimAdmin.linkDatasourceToCalcFormula(DataHelper.getIncentiveDataSourceId(),
				DataHelper.getFormulaDataMap().get("amtPerUnitCalcFormulaIdBenefitNonStep"));
	}

	public Response createAndValidateTemplate(CIMAdmin cimAdmin, String qnbLayoutId) throws Exception {
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewTemplateAPI");
		response = cimAdmin.createTemplate(jsonData, qnbLayoutId);
		responseValidator.validateCreateSuccess(response);
		response = cimAdmin.getTemplate();
		responseValidator.validateGetTemplate(response, cimAdmin);
		responseValidator.validateTemplateStatus(response, cimAdmin, RebatesConstants.draft);
		return response;
	}

	public void mapDataSourceAndFormulaToTemplate(CIMAdmin cimAdmin) throws ApplicationException {
		jsonData.put("FormulaId__c", DataHelper.getFormulaDataMap().get("volumeCalcFormulaIdQualificationNonStep"));
		jsonData.put("DataSourceId__c", DataHelper.getIncentiveDataSourceId());
		cimAdmin.mapProgramTemplateDataSource(jsonData);
		jsonData.put("FormulaId__c", DataHelper.getFormulaDataMap().get("revBasedCalcFormulaIdQualificationNonStep"));
		jsonData.put("DataSourceId__c", DataHelper.getIncentiveDataSourceId());
		cimAdmin.mapProgramTemplateDataSource(jsonData);
		jsonData.put("FormulaId__c", DataHelper.getFormulaDataMap().get("revPerctCalcFormulaIdBenefitNonStep"));
		jsonData.put("DataSourceId__c", DataHelper.getIncentiveDataSourceId());
		cimAdmin.mapProgramTemplateDataSource(jsonData);
		jsonData.put("FormulaId__c", DataHelper.getFormulaDataMap().get("amtPerUnitCalcFormulaIdBenefitNonStep"));
		jsonData.put("DataSourceId__c", DataHelper.getIncentiveDataSourceId());
		cimAdmin.mapProgramTemplateDataSource(jsonData);
	}
	
	public void activateTemplateAndSetIdForIncentive(CIMAdmin cimAdmin) throws ApplicationException {
		cimAdmin.activateTemplate(RebatesConstants.responseNocontent);
		response = cimAdmin.getTemplate();
		responseValidator.validateTemplateStatus(response, cimAdmin, RebatesConstants.activate);
	}

	public void addAndValidateIncentive(Map<String, String> jsonDataMap, String incentiveTemplateId, CIM cim)
			throws Exception {
		jsonData = jsonDataMap;
		jsonData.put("ProgramTemplateId__c", incentiveTemplateId);
		cim.createNewIncentive(jsonData);
		response = cim.getIncentiveDetails();
		responseValidator.validateIncentiveDetails(jsonData, response, cim);
	}

	public Response addAndValidateQnBOnIncentive(BenefitProductQnB benefitProductQnB, String addQnBJsonArray)
			throws Exception {
		jsonArrayData = SFDCHelper.readJsonArray("CIMIncentiveQnBData.json", addQnBJsonArray);
		benefitProductQnB.addIncentiveQnB(jsonArrayData);
		response = benefitProductQnB.getIncentiveQnB();
		responseValidator.validateIncentiveQnB(benefitProductQnB.getRequestValue("addQnBRequest"), response);
		return response;
	}

	public void addAndValidateParticipant(CIM benefitProductQnB, String addParticipantsJson) throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", addParticipantsJson);
		benefitProductQnB.addParticipants(jsonData);
		response = benefitProductQnB.getParticipantsDetails();
		responseValidator.validateParticipantsDetails(jsonData, response, benefitProductQnB);
	}

	public void activateAndValidateIncentive(BenefitProductQnB benefitProductQnB) throws ApplicationException {
		benefitProductQnB.activateIncentive();
		response = benefitProductQnB.getIncentiveDetails();
		responseValidator.validateIncentiveStatus(RebatesConstants.statusActivated, response,
				benefitProductQnB.getIncentiveData().incentiveId);
	}

	public void deleteQnBAndValidateOnIncenive(BenefitProductQnB benefitProductQnB, String addQnBJsonArray)
			throws Exception {
		jsonData = efficacies.readJsonElement("CIMIncentiveQnBData.json", "deleteBenefitLine");
		benefitProductQnB.deleteQnBBenefitLine(jsonData.get("SectionId"));
		response = benefitProductQnB.getIncentiveQnB();
		responseValidator.validateDeleteQnBBenefitLine(response, jsonData.get("SectionId"));
	}
}
