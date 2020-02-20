package com.apttus.sfdc.rebates.lightning.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.apttus.ui.fundamentals.Acolyte;

public class GenericPage {

	public WebDriver driver;
	public Acolyte sfdcAcolyte;
	public int waitTime = 40;
	public String fieldLabel = ".labelCol";

	public GenericPage(WebDriver driver) {

		this.driver = driver;
		sfdcAcolyte = new Acolyte(driver);
		sfdcAcolyte.setWaitTime(60);
		PageFactory.initElements(driver, this);
	}

	}
