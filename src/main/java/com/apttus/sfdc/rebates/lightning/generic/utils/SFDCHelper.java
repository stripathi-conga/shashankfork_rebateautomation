package com.apttus.sfdc.rebates.lightning.generic.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private Date date = new Date();	

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

	public static long randomNumberGenerator() {
		return System.nanoTime();
	}

	public String getTodaysDate() {
		return dateFormat.format(new Date());
	}

	public Boolean checkValidDate(String date, String dateFormat) {
		if (date == null) {
			return false;
		}
		if (dateFormat == null) {
			dateFormat = "yyyy-MM-dd";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		sdf.setLenient(false);
		try {
			sdf.parse(date);

		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public String getPastorFutureDate(String actualDate, String days) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		if (actualDate == null) {
			calendar.setTime(date);
		} else {
			calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(actualDate));
		}
		calendar.add(Calendar.DATE, +Integer.parseInt(days));
		return dateFormat.format(calendar.getTime());
	}

	public String firstDayOfCurrentMonth() throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return dateFormat.format(calendar.getTime());
	}

	public String lastDayOfCurrentMonth() throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DATE, -1);
		return dateFormat.format(calendar.getTime());
	}

	public String firstDayOfLastMonth() throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return dateFormat.format(calendar.getTime());
	}

	public String firstDayOfPreviousMonth() throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return dateFormat.format(calendar.getTime());
	}

	public String firstDayOfPreviousTwoMonth() throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -2);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return dateFormat.format(calendar.getTime());
	}

	public String lastDayOfNextMonth() throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return dateFormat.format(calendar.getTime());
	}
	
	public static Map<String, String> overrideJSON(Map<String, String> allData, Map<String, String> dataToOverride) {
		for (Map.Entry<String, String> dataToOverrideKeys : dataToOverride.entrySet())
			allData.put(dataToOverrideKeys.getKey(), dataToOverride.get(dataToOverrideKeys.getKey()));
		return allData;
	}
}
