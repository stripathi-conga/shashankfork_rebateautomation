package com.apttus.sfdc.rebates.lightning.api.validator;

import java.text.DecimalFormat;

import org.testng.asserts.SoftAssert;
import com.apttus.customException.ApplicationException;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.restassured.response.Response;

public class BenefitProductValidator extends ResponseValidatorBase {
	protected JsonParser parser = new JsonParser();
	protected SoftAssert softassert;
	protected static final DecimalFormat decimalFormatter = new DecimalFormat(".##");

	public void validateIncentiveQnB(String request, Response response) throws ApplicationException {
		softassert = new SoftAssert();
		String responseBody = response.getBody().asString();
		JsonArray qnbResArray = parser.parse(responseBody).getAsJsonArray();
		int responseSize = qnbResArray.size();

		if (responseSize > 0) {
			JsonArray qnbReqArray = parser.parse(request).getAsJsonObject().get("qnbData").getAsJsonArray();

			for (int i = 0; i < responseSize; i++) {
				JsonObject reqQnbLinesData = qnbReqArray.get(i).getAsJsonObject();
				JsonObject resQnbLinesData = qnbResArray.get(i).getAsJsonObject();

				int reqTierCount = reqQnbLinesData.get("TierCount").getAsInt();
				int resTierCount = reqQnbLinesData.get("TierCount").getAsInt();

				softassert.assertEquals(resTierCount, reqTierCount, "Benefit Line Tier count did not match");
				softassert.assertAll();

				// ------------ Verify Qualification Details --------------------------
				JsonObject reqQualification = reqQnbLinesData.get("QnBLines").getAsJsonArray().get(0).getAsJsonObject();
				JsonObject resQualification = resQnbLinesData.get("QnBLines").getAsJsonArray().get(0).getAsJsonObject();

				JsonArray reqQualificationTierArray = reqQualification.get("Tiers").getAsJsonArray();
				JsonArray resQualificationTierArray = resQualification.get("Tiers").getAsJsonArray();
				for (int j = 0; j < reqTierCount; j++) {
					String reqQualificationTierValue = reqQualificationTierArray.get(j).getAsJsonObject().get("Value")
							.getAsString();
					String resQualificationTierValue = resQualificationTierArray.get(j).getAsJsonObject().get("Value")
							.getAsString();
					softassert.assertEquals(resQualificationTierValue, reqQualificationTierValue,
							"Verify Qualification Tier Quantity");
				}
				softassert.assertAll();

				softassert.assertEquals(resQualification.get("StartDate").getAsString(),
						reqQualification.get("StartDate").getAsString(), "Verify Qualification StartDate");
				softassert.assertEquals(resQualification.get("EndDate").getAsString(),
						reqQualification.get("EndDate").getAsString(), "Verify Qualification EndDate");
				softassert.assertEquals(resQualification.get("ProductType").getAsString(),
						reqQualification.get("ProductType").getAsString(), "Verify Qualification ProductType");
				softassert.assertEquals(resQualification.get("ProductCode").getAsString(),
						reqQualification.get("ProductCode").getAsString(), "Verify Qualification ProductCode");
				softassert.assertEquals(resQualification.get("Name").getAsString(),
						reqQualification.get("Name").getAsString(), "Verify Qualification Product Name");
				softassert.assertEquals(resQualification.get("ProductId").getAsString(),
						reqQualification.get("ProductId").getAsString(), "Verify Qualification ProductId");
				softassert.assertEquals(resQualification.get("FormulaId").getAsString(),
						reqQualification.get("FormulaId").getAsString(), "Verify Qualification FormulaId");
				softassert.assertEquals(resQualification.get("AliasName").getAsString(),
						reqQualification.get("AliasName").getAsString(), "Verify Qualification AliasName");
				softassert.assertEquals(resQualification.get("IsQualification").getAsString(),
						reqQualification.get("IsQualification").getAsString(),
						"Verify IsQualification falg value for Qualification");
				softassert.assertAll();

				// ------------ Verify Benefit Details --------------------------
				JsonObject reqBenefit = reqQnbLinesData.get("QnBLines").getAsJsonArray().get(1).getAsJsonObject();
				JsonObject resBenefit = resQnbLinesData.get("QnBLines").getAsJsonArray().get(1).getAsJsonObject();

				JsonArray reqBenefitTierArray = reqBenefit.get("Tiers").getAsJsonArray();
				JsonArray resBenefitTierArray = resBenefit.get("Tiers").getAsJsonArray();
				for (int j = 0; j < reqTierCount; j++) {
					double reqBenefitTierValue = reqBenefitTierArray.get(j).getAsJsonObject().get("Value")
							.getAsDouble();
					double resBenefitTierValue = resBenefitTierArray.get(j).getAsJsonObject().get("Value")
							.getAsDouble();
					softassert.assertEquals(decimalFormatter.format(resBenefitTierValue), decimalFormatter.format(reqBenefitTierValue), "Verify Benefit Tier Amount");
				}
				softassert.assertAll();

				softassert.assertEquals(resBenefit.get("StartDate").getAsString(),
						reqBenefit.get("StartDate").getAsString(), "Verify Benefit StartDate");
				softassert.assertEquals(resBenefit.get("EndDate").getAsString(),
						reqBenefit.get("EndDate").getAsString(), "Verify Benefit EndDate");
				softassert.assertEquals(resBenefit.get("ProductType").getAsString(),
						reqBenefit.get("ProductType").getAsString(), "Verify Benefit ProductType");
				softassert.assertEquals(resBenefit.get("ProductCode").getAsString(),
						reqBenefit.get("ProductCode").getAsString(), "Verify Benefit ProductCode");
				softassert.assertEquals(resBenefit.get("Name").getAsString(), reqBenefit.get("Name").getAsString(),
						"Verify Benefit Product Name");
				softassert.assertEquals(resBenefit.get("ProductId").getAsString(),
						reqBenefit.get("ProductId").getAsString(), "Verify Benefit ProductId");
				softassert.assertEquals(resBenefit.get("FormulaId").getAsString(),
						reqBenefit.get("FormulaId").getAsString(), "Verify Benefit FormulaId");
				softassert.assertEquals(resBenefit.get("AliasName").getAsString(),
						reqBenefit.get("AliasName").getAsString(), "Verify Benefit AliasName");
				softassert.assertEquals(resBenefit.get("IsQualification").getAsString(),
						reqBenefit.get("IsQualification").getAsString(),
						"Verify IsQualification falg value for Benefit");
				softassert.assertAll();
			}
		} else {
			throw new ApplicationException("QnB Validation fails, benefit lines are not added on QnB Page");
		}
	}
	
	public void validateDeleteQnBBenefitLine(Response response, String sectionId) {
		softassert = new SoftAssert();
		boolean flag = true;
		String responseBody = response.getBody().asString();
		JsonArray qnbResArray = parser.parse(responseBody).getAsJsonArray();
		int responseSize = qnbResArray.size();
		if (responseSize > 0) {
			for (int i = 0; i < responseSize; i++) {
				JsonObject resQnbLinesData = qnbResArray.get(i).getAsJsonObject();
				String sectiondIdName = resQnbLinesData.get("SectionId").getAsString();
				flag = sectiondIdName.equalsIgnoreCase(sectionId);
				softassert.assertEquals(flag, false, "Benefit Line is not Deleted");
			}
		}
		softassert.assertAll();
	}
}