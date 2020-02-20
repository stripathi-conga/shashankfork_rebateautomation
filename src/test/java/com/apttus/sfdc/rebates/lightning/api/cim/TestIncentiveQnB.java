package com.apttus.sfdc.rebates.lightning.api.cim;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.apttus.helpers.Efficacies;
import com.apttus.sfdc.rebates.lightning.api.library.BenefitProductQnB;
import com.apttus.sfdc.rebates.lightning.api.library.CIM;
import com.apttus.sfdc.rebates.lightning.api.validator.BenefitProductValidator;
import com.apttus.sfdc.rebates.lightning.generic.utils.RebatesConstants;
import com.apttus.sfdc.rebates.lightning.generic.utils.SFDCHelper;
import com.apttus.sfdc.rudiments.utils.SFDCRestUtils;
import com.jayway.restassured.response.Response;


public class TestIncentiveQnB {
	private Properties configProperties;
	private Efficacies efficacies;
	private SFDCRestUtils sfdcRestUtils;
	private String instanceURL;
	private BenefitProductValidator responseValidator;
	public CIM cim;
	private Map<String, String> jsonData;
	private List<Map<String, String>> jsonArrayData;
	private Response response;
	private BenefitProductQnB benefitProductQnB;
	public String calcFormulaIdBenefitTiered, calcFormulaIdQualificationTiered;

	@BeforeSuite(alwaysRun = true)
	@Parameters({ "runParallel", "environment", "browser", "hubURL" })
	public void beforeClass(String runParallel, String environment, String browser, String hubURL) throws Exception {
		efficacies = new Efficacies();
		sfdcRestUtils = new SFDCRestUtils();
		configProperties = efficacies.loadPropertyFile(environment);
		SFDCHelper.setMasterProperty(configProperties);
		instanceURL = SFDCHelper.setAccessToken(sfdcRestUtils);
		cim = new CIM(instanceURL, sfdcRestUtils);
		responseValidator = new BenefitProductValidator();
		
		//------ Deactivate the Active Link Template for LinkTemplate with SubType as Tiered -------
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "activeTemplateIdForRebateTiered");		
		cim.deactivateLinkTemplateForIncentives(jsonData);
		
		//-------- Create Benefit formulaId for SubType as Tiered -----------------
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createFieldExpressionId");
		String fieldExpressionId = cim.getFieldExpressionId(jsonData);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createCalcFormulaIdBenefit");
		calcFormulaIdBenefitTiered = cim.getCalcFormulaId(jsonData);
		RebatesConstants.benefitFormulaId = calcFormulaIdBenefitTiered;
		
		//-------- Create Qualification formulaId for SubType as Tiered -----------------
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createCalcFormulaIdQualification");
		calcFormulaIdQualificationTiered = cim.getCalcFormulaId(jsonData);
		RebatesConstants.qualificationFormulaId = calcFormulaIdQualificationTiered;
		
		//-------- Link formulaId to Data Source-----------------
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "linkCalcFormulaToExpressionId");
		cim.linkCalcFormulaToExpression(jsonData, calcFormulaIdBenefitTiered, fieldExpressionId);
		cim.linkCalcFormulaToExpression(jsonData, calcFormulaIdQualificationTiered, fieldExpressionId);
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewDataSourceAPI");
		cim.createDataSource(jsonData);
		cim.linkDatasourceToCalcFormula(calcFormulaIdBenefitTiered);
		cim.linkDatasourceToCalcFormula(calcFormulaIdQualificationTiered);

		//-------- Create and activate Template for Benefit Only Tiered -----------------
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createQnBLayoutAPI");
		String qnbLayoutId = cim.getQnBLayoutId(jsonData);

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewTemplateAPI");
		response = cim.createTemplate(jsonData, qnbLayoutId);
		responseValidator.validateCreateSuccess(response);
		response = cim.getTemplate();
		responseValidator.validateGetTemplate(response, cim);
		jsonData.put("FormulaId__c", calcFormulaIdBenefitTiered);
		jsonData.put("DataSourceId__c", cim.getDataSourceData().getDataSourceId());
		cim.mapProgramTemplateDataSource(jsonData);
		
		jsonData.put("FormulaId__c", calcFormulaIdQualificationTiered);
		jsonData.put("DataSourceId__c", cim.getDataSourceData().getDataSourceId());
		cim.mapProgramTemplateDataSource(jsonData);
		
		cim.activateTemplate(RebatesConstants.responseNocontent);
		RebatesConstants.incentiveTemplateIdBenefitProductTiered = cim.getTemplateData().getTemplateId();		
		
		//-------- Create and activate Link Template for Subtype as Tiered-----------------
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewLinkTemplateSubTypeTieredAPI");
		response = cim.createLinkTemplates(jsonData);
		cim.activateLinkTemplate();
		response = cim.getLinkTemplatesViaId();
		responseValidator.validateLinkTemplatesStatus(response, cim, RebatesConstants.activate);
		
		//------ Deactivate the Active Link Template for LinkTemplate with SubType as Discrete -------
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "activeTemplateIdForRebateDiscrete");
		cim.deactivateLinkTemplateForIncentives(jsonData);
		
		//-------- Create and activate Template for Benefit Only Discrete -----------------
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createDiscreteQnBLayoutAPI");
		qnbLayoutId = cim.getQnBLayoutId(jsonData);

		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewTemplateAPI");
		response = cim.createTemplate(jsonData, qnbLayoutId);		
		
		responseValidator.validateCreateSuccess(response);
		response = cim.getTemplate();
		responseValidator.validateGetTemplate(response, cim);
		jsonData.put("FormulaId__c", calcFormulaIdBenefitTiered);
		jsonData.put("DataSourceId__c", cim.getDataSourceData().getDataSourceId());
		cim.mapProgramTemplateDataSource(jsonData);

		jsonData.put("FormulaId__c", calcFormulaIdQualificationTiered);
		jsonData.put("DataSourceId__c", cim.getDataSourceData().getDataSourceId());
		cim.mapProgramTemplateDataSource(jsonData);

		cim.activateTemplate(RebatesConstants.responseNocontent);
		RebatesConstants.incentiveTemplateIdBenefitProductDiscrete = cim.getTemplateData().getTemplateId();

		// -------- Create and activate Link Template for Subtype as Discrete-----------------
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "createNewLinkTemplateSubTypeDiscreteAPI");
		response = cim.createLinkTemplates(jsonData);
		cim.activateLinkTemplate();
		response = cim.getLinkTemplatesViaId();
		responseValidator.validateLinkTemplatesStatus(response, cim, RebatesConstants.activate);
	}
	
	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws Exception {
		benefitProductQnB = new BenefitProductQnB(instanceURL, sfdcRestUtils);
	}

	@Test(description = "TC-459 Verify searching and adding the product to the Benefit only and Tiered programs", groups = {
			"Smoke", "API" })
	public void addQnBBenefitOnlyXXT() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json",
				"createIncentiveIndividualParticipantBenefitProductTiered");
		jsonData.put("ProgramTemplateId__c", RebatesConstants.incentiveTemplateIdBenefitProductTiered);
		benefitProductQnB.createNewIncentive(jsonData);
		response = benefitProductQnB.getIncentiveDetails();
		responseValidator.validateIncentiveDetails(jsonData, response, benefitProductQnB);
		jsonArrayData = SFDCHelper.readJsonArray("CIMIncentiveQnBData.json", "XXTBenefitProduct");
		benefitProductQnB.addIncentiveQnB(jsonArrayData);
		response = benefitProductQnB.getIncentiveQnB();
		responseValidator.validateIncentiveQnB(benefitProductQnB.getRequestValue("addQnBRequest"), response);
	}

	@Test(description = "TC-441 Validation adding the product for Benefit only and Discrete programs", groups = {
			"Regression", "High", "API" })
	public void addQnBBenefitOnlyXXD() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json",
				"createNewIncentiveAgreementAccountBenefitProductDiscrete");
		jsonData.put("ProgramTemplateId__c", RebatesConstants.incentiveTemplateIdBenefitProductDiscrete);
		benefitProductQnB.createNewIncentive(jsonData);
		response = benefitProductQnB.getIncentiveDetails();
		responseValidator.validateIncentiveDetails(jsonData, response, benefitProductQnB);
		jsonArrayData = SFDCHelper.readJsonArray("CIMIncentiveQnBData.json", "XXDBenefitProduct");
		benefitProductQnB.addIncentiveQnB(jsonArrayData);
		response = benefitProductQnB.getIncentiveQnB();
		responseValidator.validateIncentiveQnB(benefitProductQnB.getRequestValue("addQnBRequest"), response);
	}
	
	@Test(description = "TC-438 Verify for the deletion of the Product added from the QnB layout", groups = {
			"Regression", "Medium", "API" })
	public void deleteQnBBenefitLine() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json",
				"createIncentiveIndividualParticipantBenefitProductTiered");
		jsonData.put("ProgramTemplateId__c", RebatesConstants.incentiveTemplateIdBenefitProductTiered);
		benefitProductQnB.createNewIncentive(jsonData);
		
		//------------ Add QnB Benefit Lines -------------------
		response = benefitProductQnB.getIncentiveDetails();
		responseValidator.validateIncentiveDetails(jsonData, response, benefitProductQnB);
		jsonArrayData = SFDCHelper.readJsonArray("CIMIncentiveQnBData.json", "XXTBenefitProduct");
		benefitProductQnB.addIncentiveQnB(jsonArrayData);
		response = benefitProductQnB.getIncentiveQnB();
		responseValidator.validateIncentiveQnB(benefitProductQnB.getRequestValue("addQnBRequest"), response);
		benefitProductQnB.setQnBSectionId(response);
		
		//-------------- Delete QnB Benefit Line ----------------
		jsonData = efficacies.readJsonElement("CIMIncentiveQnBData.json", "deleteBenefitLine");
		benefitProductQnB.deleteQnBBenefitLine(jsonData.get("SectionId"));
		response = benefitProductQnB.getIncentiveQnB();
		responseValidator.validateDeleteQnBBenefitLine(response, jsonData.get("SectionId"));
	}
	
	@Test(description = "TC-442 Verify deletion of the Product from the QnB layout", groups = {
			"Regression", "Medium", "API" })
	public void deleteQnBLayoutproduct() throws Exception {
		jsonData = efficacies.readJsonElement("CIMTemplateData.json",
				"createIncentiveIndividualParticipantBenefitProductTiered");
		jsonData.put("ProgramTemplateId__c", RebatesConstants.incentiveTemplateIdBenefitProductTiered);
		benefitProductQnB.createNewIncentive(jsonData);
		
		//------------ Add QnB Benefit Lines -------------------
		response = benefitProductQnB.getIncentiveDetails();
		responseValidator.validateIncentiveDetails(jsonData, response, benefitProductQnB);
		jsonArrayData = SFDCHelper.readJsonArray("CIMIncentiveQnBData.json", "XXTBenefitProduct");
		benefitProductQnB.addIncentiveQnB(jsonArrayData);
		response = benefitProductQnB.getIncentiveQnB();
		responseValidator.validateIncentiveQnB(benefitProductQnB.getRequestValue("addQnBRequest"), response);
		benefitProductQnB.setQnBSectionId(response);
		
		//-------------- Delete QnB Benefit Line ----------------
		jsonData = efficacies.readJsonElement("CIMIncentiveQnBData.json", "deleteBenefitLine");
		benefitProductQnB.deleteQnBBenefitLine(jsonData.get("SectionId"));
		response = benefitProductQnB.getIncentiveQnB();
		responseValidator.validateDeleteQnBBenefitLine(response, jsonData.get("SectionId"));
		
		//-------------Added previously deleted Product
		response = benefitProductQnB.getIncentiveQnB();
		responseValidator.validateIncentiveQnB(benefitProductQnB.getRequestValue("addQnBRequest"), response);
	}
	
    @Test(description = "TC-538 Validate for dates on the QnB for Benefit only and Tiered Incentive", groups = {
            "Regression", "Medium", "API" })
    public void addQnBBenefitOnlyXXDOutsideIncentiveDates() throws Exception {
        jsonData = efficacies.readJsonElement("CIMTemplateData.json",
                "createIncentiveIndividualParticipantBenefitProductTiered");
        jsonData.put("ProgramTemplateId__c", RebatesConstants.incentiveTemplateIdBenefitProductTiered);
        benefitProductQnB.createNewIncentive(jsonData);
        response = benefitProductQnB.getIncentiveDetails();
        responseValidator.validateIncentiveDetails(jsonData, response, benefitProductQnB);
        jsonArrayData = SFDCHelper.readJsonArray("CIMIncentiveQnBData.json", "XXTBenefitProductOutsideIncentiveDates");
        response = benefitProductQnB.addIncentiveQnBNegative(jsonArrayData);
        //TODO - Mitu to update errorCode and message after the fix of REBATE-3358
        responseValidator.validateFailureResponse(response, RebatesConstants.errorCodeApexError,
                RebatesConstants.messageBenefitDatesOutOfRange);
    }
}
