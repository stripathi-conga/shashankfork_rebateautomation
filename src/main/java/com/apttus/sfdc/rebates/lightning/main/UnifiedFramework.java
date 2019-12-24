package com.apttus.sfdc.rebates.lightning.main;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.apttus.extent.report.ExtentTestNGIReporterListener;

public class UnifiedFramework {
	// Properties properties;
	// public JavaHelpers javaHelpers = new JavaHelpers();

	@BeforeSuite(alwaysRun = true)
	@Parameters({ "buildName", "qTestPropertyFile" })
	public void beforeSuite(String buildName, String qTestPropertyFile) {
		ExtentTestNGIReporterListener.setBuildName(buildName);
		// QTestUtils qTestUtils = new QTestUtils();
		// qTest Integration:
		/*
		 * try { properties = javaHelpers.loadPropertiesFromResources("cim/" +
		 * qTestPropertyFile); if
		 * (Boolean.valueOf(properties.getProperty("updateQTestFlag")) == true) {
		 * QTestListener.setNeedQTestIntegration(Boolean.valueOf(properties.getProperty(
		 * "updateQTestFlag"))); qTestUtils.setAccessToken();
		 * qTestUtils.setCycleName(properties.getProperty("cycleName") +
		 * APIHelper.getCurrentDate("dd-MM-yyyy")); String testSuiteName =
		 * properties.getProperty("testSuiteName") +
		 * APIHelper.getCurrentDate("dd-MM-yyyy HH:mm:ss");
		 * qTestUtils.setTestSuiteName(testSuiteName.trim());
		 * qTestUtils.setModule(properties.getProperty("moduleQuery"));
		 * qTestUtils.setTestCaseCategory(properties.getProperty("testCaseCategory"));
		 * qTestUtils.setParentCycleID(properties.getProperty("parentCycleID"));
		 * qTestUtils.setProjectID(properties.getProperty("qTestProjectID")); // Create
		 * Suite in qTest Integer testCycleId =
		 * qTestUtils.fetchSingleCycleByName(qTestUtils.getAllTestCyclesUnderTestCycle()
		 * ); if (testCycleId == null) { qTestUtils.createTestCycleUnderTestCycle();
		 * testCycleId =
		 * qTestUtils.fetchSingleCycleByName(qTestUtils.getAllTestCyclesUnderTestCycle()
		 * ); } qTestUtils.createTestSuiteUnderTestCycle(testCycleId);
		 * 
		 * // Get All Test Suite Map<String, Integer> testSuiteNameAndID =
		 * qTestUtils.getAllTestSuitesUnderTestCycle(testCycleId); Integer suiteID =
		 * testSuiteNameAndID.get(testSuiteName.trim());
		 * 
		 * qTestUtils.setTestSuiteID(suiteID); Map<Integer, String> testCaseNameAndID =
		 * qTestUtils.getAllTestCaseDetailsByDataQuery();
		 * qTestUtils.addTestRunUnderTestSuite(suiteID, testCaseNameAndID); }
		 * 
		 * } catch (Exception e) { e.printStackTrace(); }
		 */

	}

	public void afterSuite() {

	}

}
