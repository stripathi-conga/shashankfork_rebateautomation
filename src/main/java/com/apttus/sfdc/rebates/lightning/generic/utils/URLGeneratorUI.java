package com.apttus.sfdc.rebates.lightning.generic.utils;

import java.util.Properties;

public class URLGeneratorUI {

	public String incentiveURL="Apttus_Config2__Incentive__c/{incentiveId}/{view}";
	public String templatesURL="IncentiveProgramTemplate__c/{templateId}/{view}";
	public String editPath="r";
	public String homePath="o";
	public String incentiveEditURL;
	public String incentiveHomeURL;
	public String templateHomeURL;
	
	public URLGeneratorUI(Properties configProperties) {
		
		incentiveEditURL = configProperties.getProperty("baseURLUI").replace("{path}", editPath) + incentiveURL;
		incentiveHomeURL= configProperties.getProperty("baseURLUI").replace("{path}", homePath)+ incentiveURL;
		templateHomeURL=configProperties.getProperty("baseURLUI").replace("{path}", homePath)+ templatesURL;
	}


	

}
