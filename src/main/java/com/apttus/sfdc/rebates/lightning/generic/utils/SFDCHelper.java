package com.apttus.sfdc.rebates.lightning.generic.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import com.apttus.customException.ApplicationException;
import com.apttus.sfdc.rudiments.utils.SFDCRestUtils;

public class SFDCHelper {

	private static Properties masterProperty;
	public static String envName;
	public static String accessToken;
	public URLGenerator urlGenerator;

	public SFDCHelper(String baseURL) {
		urlGenerator = new URLGenerator(baseURL);
	}

	public static void setMasterProperty(Properties property) {
		masterProperty = property;
		envName = masterProperty.getProperty("Environment");
	}

	public static Properties getMasterProperty() {
		return masterProperty;
	}

	public static String getBaseUrl() {
		return masterProperty.getProperty("baseURL");
	}

	public static String setAccessToken(SFDCRestUtils sfdcRestUtils) throws ApplicationException {
		try {
			Map<String, String> testData = new HashMap<String, String>();
			testData.put("url", masterProperty.getProperty("SFURL"));
			testData.put("client_id", masterProperty.getProperty("client_id"));
			testData.put("client_secret", masterProperty.getProperty("client_secret"));
			testData.put("username", masterProperty.getProperty("LoginUser"));
			testData.put("password", masterProperty.getProperty("LoginPassword"));
			return sfdcRestUtils.generateSFDCAccessToken(testData);
		} catch (Exception e) {
			throw new ApplicationException("Not able to set access token " + e.getMessage());
		}
	}

}
