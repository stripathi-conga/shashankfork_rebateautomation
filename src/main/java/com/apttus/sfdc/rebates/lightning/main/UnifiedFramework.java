package com.apttus.sfdc.rebates.lightning.main;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

public class UnifiedFramework {

	@BeforeSuite(alwaysRun = true)
	@Parameters({ "buildName", "qTestPropertyFile", "runParallel", "environment", "browser", "hubURL" })
	public void beforeSuite(String buildName, String qTestPropertyFile, String runParallel, String environment,
			String browser, String hubURL) throws Exception {
		 
	}

	public void afterSuite() {
		
	}
}
