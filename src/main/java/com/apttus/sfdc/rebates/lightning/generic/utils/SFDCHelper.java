package com.apttus.sfdc.rebates.lightning.generic.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import com.apttus.customException.ApplicationException;
import com.apttus.sfdc.rebates.lightning.api.library.CIM;
import com.apttus.sfdc.rudiments.utils.SFDCRestUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SFDCHelper {

	private static Properties masterProperty;
	public static String envName;
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private Date date = new Date();	

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
	
	public String firstDayOfYearForDate(String actualDate) throws ParseException{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(actualDate));
		calendar.set(Calendar.MONTH, calendar.getActualMinimum(Calendar.MONTH));//In java, January starts with zero
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return dateFormat.format(calendar.getTime());
	}
	
	public String lastDayOfYearForDate(String actualDate) throws ParseException{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(actualDate));
		calendar.set(Calendar.MONTH, calendar.getActualMaximum(Calendar.MONTH));
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return dateFormat.format(calendar.getTime());
	}
	
	public String addYearsToDate(String actualDate,int yearsToAdd) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(actualDate));
		calendar.add(Calendar.YEAR, yearsToAdd);
		return dateFormat.format(calendar.getTime());
	}
	
	public String firstDayOfMonthForDate(String actualDate) throws ParseException{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(actualDate));
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return dateFormat.format(calendar.getTime());
	}
	
	public String lastDayOfMonthForDate(String actualDate) throws ParseException{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(actualDate));
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return dateFormat.format(calendar.getTime());
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
	
	public String addMonthsToDate(String actualDate, int monthsToAdd) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(actualDate));
		calendar.add(Calendar.MONTH, monthsToAdd);
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
	
	public String lastDayOfNextTwoMonth() throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 2);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
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
	
	public static String getResourceFolderPath()	{
		return System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator +
				"resources" + File.separator + "rebates" + File.separator; 
	}
	
	public static synchronized List<Map<String, String>> readJsonArray(String jsonFileName, String elementName)
			throws Exception {
		String filePath = getResourceFolderPath() + jsonFileName;
		JsonElement root = new JsonParser().parse(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
		JsonObject jsonObject = root.getAsJsonObject();
		JsonElement some = jsonObject.get(elementName);
		JsonArray testData = some.getAsJsonArray();
		List<Map<String, String>> jsonData = new ArrayList<Map<String, String>>();
		for (int i = 0; i < testData.size(); i++) {
			Map<String, String> testDataMap = new HashMap<String, String>();
			JsonObject objElement = testData.get(i).getAsJsonObject();
			for (Map.Entry<String, JsonElement> entry : objElement.entrySet()) {
				testDataMap.put(((String) entry.getKey()).toString(), ((JsonElement) entry.getValue()).getAsString());
			}
			jsonData.add(testDataMap);
		}
		return jsonData;
	}
	public static String getCIMDateValue(String dateValue, CIM cim) throws ApplicationException {
		SFDCHelper sfdcHelper = new SFDCHelper();
		String returnDate = null;
		int year;
		try {
			boolean checkDate = sfdcHelper.checkValidDate(dateValue, null);
			if (checkDate) {
				returnDate = dateValue;
			} else {
				String date = dateValue.toLowerCase();
				if (date.contains("today")) {
					returnDate = sfdcHelper.getTodaysDate();
				} else if (date.contains("startofcurrentyear")) {
					year = Calendar.getInstance().get(Calendar.YEAR);
					returnDate = String.valueOf(year) + "-01-01";
				} else if (date.contains("endofcurrentyear")) {
					year = Calendar.getInstance().get(Calendar.YEAR);
					returnDate = String.valueOf(year) + "-12-31";
				} else if (date.contains("midofcurrentyear")) {
					year = Calendar.getInstance().get(Calendar.YEAR);
					returnDate = String.valueOf(year) + "-07-01";
				} else if (date.contains("startofcurrentmonth")) {
					returnDate = sfdcHelper.firstDayOfCurrentMonth();
				} else if (date.contains("endofcurrentmonth")) {
					returnDate = sfdcHelper.lastDayOfCurrentMonth();
				} else if (date.contains("startofpreviousmonth")) {
					returnDate = sfdcHelper.firstDayOfPreviousMonth();
				} else if (date.contains("startofprevioustwomonth")) {
					returnDate = sfdcHelper.firstDayOfPreviousTwoMonth();
				} else if (date.contains("endofnextmonth")) {
					returnDate = sfdcHelper.lastDayOfNextMonth();
				} else if (date.contains("incentivestartdate")) {
					returnDate = cim.incentiveData.getApttus_Config2__EffectiveDate__c();
				} else if (date.contains("incentiveenddate")) {
					returnDate = cim.incentiveData.getApttus_Config2__ExpirationDate__c();
				}
				if (date.contains("=")) {
					returnDate = sfdcHelper.getPastorFutureDate(returnDate, date.split("=")[1]);
				}
			}
			return returnDate;
		} catch (Exception e) {
			throw new ApplicationException("Getting run time error while using getCIMDateValue : " + e);
		}
	}

	public static List<String> getListOfString(String inputData) {
		List<String> data = new ArrayList<String>();
		String[] arrData = inputData.split(";");
		for (String value : arrData) {
			data.add(value);
		}
		return data;
	}
}
