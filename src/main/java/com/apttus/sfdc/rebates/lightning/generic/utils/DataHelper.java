package com.apttus.sfdc.rebates.lightning.generic.utils;

import java.util.HashMap;
import java.util.Map;

public class DataHelper {
	// These values will be set in @BeforeSuite and used in all Incentive classes
	public static String incentiveDataSourceId;
	public static String incentiveTemplateIdBenefitProductTiered;
	public static String incentiveTemplateIdBenefitProductDiscrete;
	public static Map<String, String> formulaDataMap = new HashMap<String, String>();
	
	
	public static String getIncentiveDataSourceId() {
		return incentiveDataSourceId;
	}
	public static void setIncentiveDataSourceId(String incentiveDataSourceId) {
		DataHelper.incentiveDataSourceId = incentiveDataSourceId;
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

}
