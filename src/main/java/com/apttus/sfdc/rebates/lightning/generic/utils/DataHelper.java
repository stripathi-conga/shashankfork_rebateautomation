package com.apttus.sfdc.rebates.lightning.generic.utils;

import java.util.HashMap;
import java.util.Map;

public class DataHelper {
	// These values will be set in @BeforeSuite and used in all Incentive classes
	private static String incentiveDataSourceId;
	private static String incentiveDataSourceName;
	private static String incentiveTemplateIdBenefitProductTiered;
	private static String incentiveTemplateIdBenefitProductDiscrete;
	private static Map<String, String> formulaDataMap = new HashMap<String, String>();
	
	
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

}
