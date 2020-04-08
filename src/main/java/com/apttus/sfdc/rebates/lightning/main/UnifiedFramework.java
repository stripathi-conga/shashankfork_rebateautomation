package com.apttus.sfdc.rebates.lightning.main;

import java.util.Map;
import java.util.Properties;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import com.apttus.extent.report.ExtentTestNGIReporterListener;
import com.apttus.helpers.Efficacies;
import com.apttus.sfdc.rebates.lightning.api.library.CIM;
import com.apttus.sfdc.rebates.lightning.api.library.CIMAdmin;
import com.apttus.sfdc.rebates.lightning.generic.utils.CIMAdminHelper;
import com.apttus.sfdc.rebates.lightning.generic.utils.CIMHelper;
import com.apttus.sfdc.rebates.lightning.generic.utils.DataHelper;
import com.apttus.sfdc.rebates.lightning.generic.utils.SFDCHelper;
import com.apttus.sfdc.rudiments.utils.SFDCRestUtils;

public class UnifiedFramework {
	private Properties configProperties;
	private Efficacies efficacies;
	private SFDCRestUtils sfdcRestUtils;
	private String instanceURL;
	public CIM cim;
	private Map<String, String> jsonData;
	public String calcFormulaIdBenefitTiered, calcFormulaIdQualificationTiered;
	public CIMAdmin cimAdmin;
	public CIMHelper cimHelper;
	public CIMAdminHelper cimAdminHelper;

	@BeforeSuite(alwaysRun = true)
	@Parameters({ "buildName", "qTestPropertyFile", "runParallel", "environment", "browser", "hubURL" })
	public void beforeSuite(String buildName, String qTestPropertyFile, String runParallel, String environment,
			String browser, String hubURL) throws Exception {
		ExtentTestNGIReporterListener.setBuildName(buildName);

		efficacies = new Efficacies();
		sfdcRestUtils = new SFDCRestUtils();
		configProperties = efficacies.loadPropertyFile(environment);
		SFDCHelper.setMasterProperty(configProperties);
		instanceURL = SFDCHelper.setAccessToken(sfdcRestUtils);
		cim = new CIM(instanceURL, sfdcRestUtils);
		cimAdmin = new CIMAdmin(instanceURL, sfdcRestUtils);
		cimHelper = new CIMHelper();
		cimAdminHelper = new CIMAdminHelper();

		// ---- Deactivate the Active Link Template for LinkTemplate with SubType as Benefit Only Tiered ----
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "activeTemplateIdForRebateTiered");
		cim.deactivateLinkTemplateForIncentives(jsonData);
		
		//----- Delete Existing DataSource ------
		cimHelper.deleteDataSourceForIncentive(cimAdmin);

		// ----- Create Step and NonStep Formula Id's -----
		cimHelper.createDataSourceAndFormulasForIncentives(cimAdmin);

		// ----- Create and activate Template for Benefit Only Tiered -----
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "benefitOnlyTieredQnBLayoutAPI");
		String benefitOnlyTieredQnBLayoutId = cim.getQnBLayoutId(jsonData);
		cimHelper.createAndValidateTemplate(cimAdmin, benefitOnlyTieredQnBLayoutId);
		cimHelper.mapDataSourceAndFormulaToTemplate(cimAdmin);
		cimHelper.activateTemplateAndSetIdForIncentive(cimAdmin);
		DataHelper.setIncentiveTemplateIdBenefitProductTiered(cimAdmin.getTemplateData().getTemplateId());	
		
		// ---- Create and activate Link Template for SubType as Benefit Only Tiered ----
		String linkTemplateDataFromJson = "createNewLinkTemplateSubTypeTieredAPI";
		cimAdminHelper.createAndValidateLinkTemplate(cimAdmin, linkTemplateDataFromJson);
		cimAdminHelper.activateAndVerifyLinkTemplate(cimAdmin);

		// ---- Deactivate the Active Link Template for LinkTemplate with SubType as Benefit Only Discrete ----
		jsonData = efficacies.readJsonElement("CIMTemplateData.json", "activeTemplateIdForRebateDiscrete");
		cim.deactivateLinkTemplateForIncentives(jsonData);

		// ---- Create and activate Template for Benefit Only Discrete -----
		jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "benefitOnlyDiscreteQnBLayoutAPI");
		String benefitOnlyDiscreteQnBLayoutId = cim.getQnBLayoutId(jsonData);
		cimHelper.createAndValidateTemplate(cimAdmin, benefitOnlyDiscreteQnBLayoutId);
		cimHelper.mapDataSourceAndFormulaToTemplate(cimAdmin);
		cimHelper.activateTemplateAndSetIdForIncentive(cimAdmin);
		DataHelper.setIncentiveTemplateIdBenefitProductDiscrete(cimAdmin.getTemplateData().getTemplateId());

		// ----- Create and activate Link Template for SubType as Benefit Only Tiered ----
		linkTemplateDataFromJson = "createNewLinkTemplateSubTypeDiscreteAPI";
		cimAdminHelper.createAndValidateLinkTemplate(cimAdmin, linkTemplateDataFromJson);
		cimAdminHelper.activateAndVerifyLinkTemplate(cimAdmin);
	}

	public void afterSuite() {
		
	}

}
