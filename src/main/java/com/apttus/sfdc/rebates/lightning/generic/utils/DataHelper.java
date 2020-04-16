package com.apttus.sfdc.rebates.lightning.generic.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import com.apttus.helpers.Efficacies;
import com.apttus.sfdc.rebates.lightning.api.library.CIM;
import com.apttus.sfdc.rebates.lightning.api.library.CIMAdmin;
import com.apttus.sfdc.rudiments.utils.SFDCRestUtils;

public class DataHelper {
	// These values will be set Before Suite execution and used in most of the classes
	private static String incentiveDataSourceId;
	private static String incentiveDataSourceName;
	private static String incentiveTemplateIdBenefitProductTiered;
	private static String incentiveTemplateIdBenefitProductDiscrete;
	private static Map<String, String> formulaDataMap = new HashMap<String, String>();

	private static Properties configProperties;
	private static Efficacies efficacies;
	private static SFDCRestUtils sfdcRestUtils;
	private static String instanceURL;
	private static CIM cim;
	private static Map<String, String> jsonData;
	private static CIMAdmin cimAdmin;
	private static CIMHelper cimHelper;
	private static CIMAdminHelper cimAdminHelper;
	private static DataHelper instance = null;
	private static boolean createDataFlag = false;

	public static String getIncentiveDataSourceId() {
		return incentiveDataSourceId;
	}

	public static void setIncentiveDataSourceId(String incentiveDataSourceId) {
		DataHelper.incentiveDataSourceId = incentiveDataSourceId;
	}

	public static String getIncentiveDataSourceName() {
		return incentiveDataSourceName;
	}

	public static void setIncentiveDataSourceName(String incentiveDataSourceName) {
		DataHelper.incentiveDataSourceName = incentiveDataSourceName;
	}

	public static String getIncentiveTemplateIdBenefitProductTiered() {
		return incentiveTemplateIdBenefitProductTiered;
	}

	public static void setIncentiveTemplateIdBenefitProductTiered(String incentiveTemplateIdBenefitProductTiered) {
		DataHelper.incentiveTemplateIdBenefitProductTiered = incentiveTemplateIdBenefitProductTiered;
	}

	public static String getIncentiveTemplateIdBenefitProductDiscrete() {
		return incentiveTemplateIdBenefitProductDiscrete;
	}

	public static void setIncentiveTemplateIdBenefitProductDiscrete(String incentiveTemplateIdBenefitProductDiscrete) {
		DataHelper.incentiveTemplateIdBenefitProductDiscrete = incentiveTemplateIdBenefitProductDiscrete;
	}

	public static Map<String, String> getFormulaDataMap() {
		return formulaDataMap;
	}

	public static void setFormulaDataMap(Map<String, String> formulaDataMap) {
		DataHelper.formulaDataMap = formulaDataMap;
	}

	synchronized public static DataHelper getInstance() {
		if (instance == null) {
			instance = new DataHelper();
			createDataFlag = true;
		}
		return instance;
	}

	synchronized public void getData(String environment) throws Exception {
		if (createDataFlag == false)
			return;
		else {
			efficacies = new Efficacies();
			sfdcRestUtils = new SFDCRestUtils();
			configProperties = efficacies.loadPropertyFile(environment);
			SFDCHelper.setMasterProperty(configProperties);
			instanceURL = SFDCHelper.setAccessToken(sfdcRestUtils);
			cim = new CIM(instanceURL, sfdcRestUtils);
			cimAdmin = new CIMAdmin(instanceURL, sfdcRestUtils);
			cimHelper = new CIMHelper();
			cimAdminHelper = new CIMAdminHelper();

			// Deactivate the Active Link Template for LinkTemplate with SubType as Benefit Only Tiered
			jsonData = efficacies.readJsonElement("CIMTemplateData.json", "activeTemplateIdForRebateTiered");
			cim.deactivateLinkTemplateForIncentives(jsonData);

			// Delete Existing DataSource
			cimHelper.deleteDataSourceForIncentive(cimAdmin);

			// Create Step and NonStep Formula Id's
			cimHelper.createDataSourceAndFormulasForIncentives(cimAdmin);

			// Create and activate Template for Benefit Only Tiered
			jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "benefitOnlyTieredQnBLayoutAPI");
			String benefitOnlyTieredQnBLayoutId = cim.getQnBLayoutId(jsonData);
			cimHelper.createAndValidateTemplate(cimAdmin, benefitOnlyTieredQnBLayoutId);
			cimHelper.mapDataSourceAndFormulaToTemplate(cimAdmin);
			cimHelper.activateTemplateAndSetIdForIncentive(cimAdmin);
			DataHelper.setIncentiveTemplateIdBenefitProductTiered(cimAdmin.getTemplateData().getTemplateId());

			// Create and activate Link Template for SubType as Benefit Only Tiered
			String linkTemplateDataFromJson = "createNewLinkTemplateSubTypeTieredAPI";
			cimAdminHelper.createAndValidateLinkTemplate(cimAdmin, linkTemplateDataFromJson);
			cimAdminHelper.activateAndVerifyLinkTemplate(cimAdmin);

			// Deactivate the Active Link Template for LinkTemplate with SubType as Benefit Only Discrete
			jsonData = efficacies.readJsonElement("CIMTemplateData.json", "activeTemplateIdForRebateDiscrete");
			cim.deactivateLinkTemplateForIncentives(jsonData);

			// Create and activate Template for Benefit Only Discrete
			jsonData = efficacies.readJsonElement("CIMAdminTemplateData.json", "benefitOnlyDiscreteQnBLayoutAPI");
			String benefitOnlyDiscreteQnBLayoutId = cim.getQnBLayoutId(jsonData);
			cimHelper.createAndValidateTemplate(cimAdmin, benefitOnlyDiscreteQnBLayoutId);
			cimHelper.mapDataSourceAndFormulaToTemplate(cimAdmin);
			cimHelper.activateTemplateAndSetIdForIncentive(cimAdmin);
			DataHelper.setIncentiveTemplateIdBenefitProductDiscrete(cimAdmin.getTemplateData().getTemplateId());

			// Create and activate Link Template for SubType as Benefit Only Tiered
			linkTemplateDataFromJson = "createNewLinkTemplateSubTypeDiscreteAPI";
			cimAdminHelper.createAndValidateLinkTemplate(cimAdmin, linkTemplateDataFromJson);
			cimAdminHelper.activateAndVerifyLinkTemplate(cimAdmin);
			createDataFlag = false;
		}
	}
}
