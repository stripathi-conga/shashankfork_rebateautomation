package com.apttus.sfdc.rebates.lightning.api.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.apttus.sfdc.rebates.lightning.api.library.CIM;
import com.apttus.sfdc.rebates.lightning.generic.utils.RebatesConstants;
import com.apttus.sfdc.rebates.lightning.generic.utils.SFDCHelper;
import com.google.gson.Gson;

public class QnBAddBenefitPojo {

	private String incentiveId;
	private List<QnbDataPojo> qnbData;
	private int totalSections;

	public String getIncentiveId() {
		return incentiveId;
	}

	public void setIncentiveId(String incentiveId) {
		this.incentiveId = incentiveId;
	}

	public List<QnbDataPojo> getQnbData() {
		return qnbData;
	}

	public void setQnbData(List<QnbDataPojo> qnbData) {
		this.qnbData = qnbData;
	}

	public int getTotalSections() {
		return totalSections;
	}

	public void setTotalSections(int totalSections) {
		this.totalSections = totalSections;
	}

	public String addBenefitRequest(List<Map<String, String>> testData, CIM cim) throws Exception {

		QnBAddBenefitPojo addQnB = new QnBAddBenefitPojo();
		List<QnbDataPojo> listQnBData = new ArrayList<QnbDataPojo>();
		QnbDataPojo qnbData;
		List<QnBLinesPojo> listQnBLines;
		QnBLinesPojo qualification;
		QnBLinesPojo benefit;
		String startDate, endDate, tierCount = null;

		addQnB.setIncentiveId(cim.getIncentiveData().getIncentiveId());
		addQnB.setTotalSections(0);

		for (Map<String, String> row : testData) {
			qnbData = new QnbDataPojo();
			listQnBLines = new ArrayList<QnBLinesPojo>();
			qualification = new QnBLinesPojo();
			benefit = new QnBLinesPojo();
			qnbData.setSectionId(row.get("SectionId"));
			qnbData.setIsNew(true);
			qnbData.setId("");
			qnbData.setSequence("1");
			tierCount = row.get("TierCount");
			if (tierCount != null) {
				qnbData.setTierCount(tierCount);
			} else
				qnbData.setTierCount("1");

			startDate = SFDCHelper.getCIMDateValue(row.get("StartDate"),cim);
			endDate = SFDCHelper.getCIMDateValue(row.get("EndDate"),cim);

			// ------------- Set Qualification Data ------------------------
			qualification.setStartDate(startDate);
			qualification.setEndDate(endDate);
			qualification.setFormulaId(RebatesConstants.qualificationFormulaId);
			qualification.setAliasName(row.get("QualificationAliasName"));
			Map<String, String> qualificationIdMap;
			qualificationIdMap = setProductId(qualification, row.get("QualificationProductType"),
					row.get("QualificationProductCode"), row.get("QualificationProduct"), "Qualification",
					row.get("QualificationFormulaId"), true, cim);

			qualification.setProductId(qualificationIdMap.get("productId"));
			qualification.setTiers(setTierValue(row.get("QualificationQuantity")));

			// ------------- Set benefit Data ------------------------
			benefit.setStartDate(startDate);
			benefit.setEndDate(endDate);
			benefit.setFormulaId(RebatesConstants.benefitFormulaId);
			benefit.setAliasName(row.get("BenefitAliasName"));
			Map<String, String> benefitIdMap;
			benefitIdMap = setProductId(benefit, row.get("BenefitProductType"), row.get("BenefitProductCode"),
					row.get("BenefitProduct"), "Benefit", row.get("BenefitFormulaId"), false, cim);

			benefit.setProductId(benefitIdMap.get("productId"));
			benefit.setTiers(setTierValue(row.get("BenefitAmount")));

			listQnBLines.add(qualification);
			listQnBLines.add(benefit);

			qnbData.setQnBLines(listQnBLines);
			listQnBData.add(qnbData);
		}
		addQnB.setQnbData(listQnBData);
		return new Gson().toJson(addQnB);
	}

	public Map<String, String> setProductId(QnBLinesPojo object, String productType, String productCode,
			String productName, String lineType, String formulaId, boolean isQualification, CIM cim) throws Exception {

		QnBLinesPojo benefitLineData = object;
		Map<String, String> ids = new HashMap<>();
		switch (productType.trim().toLowerCase()) {
		case "product":
			benefitLineData.setProductType(productType);
			benefitLineData.setProductCode(productCode);
			benefitLineData.setName(productName);
			break;
		default:
			break;
		}
		benefitLineData.setLineType(lineType);
		benefitLineData.setIsQualification(isQualification);
		ids.put("productId", cim.getProductId(productName));
		return ids;
	}

	public List<TiersPojo> setTierValue(String value) {
		String splitter = ";";
		TiersPojo tiervalues;
		List<TiersPojo> tierList = new ArrayList<TiersPojo>();
		int i = 1;
		String valueArr[] = value.split(splitter);
		for (String val : valueArr) {
			tiervalues = new TiersPojo();
			tiervalues.setSequence(Integer.toString(i));
			tiervalues.setValue(val);
			tierList.add(tiervalues);
			i++;
		}
		return tierList;
	}
}

/*----------------------- Add QnB XXT Benefit Product Request -----------------------------
 
{
  "incentiveId": "a1k3i0000015Ml0AAE",
  "qnbData": [
    {
      "QnBLines": [
        {
          "StartDate": "2020-01-01",
          "Tiers": [
            {
              "Value": "10",
              "Sequence": "1"
            },
            {
              "Value": "30",
              "Sequence": "2"
            }
          ],
          "FormulaId": "a5C3i000000DhmHEAS",
          "ProductCode": "IN7080",
          "IsQualification": true,
          "AliasName": "Q1",
          "ProductType": "Product",
          "ProductId": "01t3i000001GOf7AAG",
          "EndDate": "2020-01-31",
          "LineType": "Qualification",
          "Name": "Installation: Industrial - High"
        },
        {
          "StartDate": "2020-01-01",
          "Tiers": [
            {
              "Value": "120",
              "Sequence": "1"
            },
            {
              "Value": "100",
              "Sequence": "2"
            }
          ],
          "FormulaId": "a5C3i000000DhmCEAS",
          "ProductCode": "IN7080",
          "IsQualification": false,
          "AliasName": "B1",
          "ProductType": "Product",
          "ProductId": "01t3i000001GOf7AAG",
          "EndDate": "2020-01-31",
          "LineType": "Benefit",
          "Name": "Installation: Industrial - High"
        }
      ],
      "Id": "",
      "Sequence": "1",
      "IsNew": true,
      "SectionId": "SEC-00000001",
      "TierCount": "2"
    },
    {
      "QnBLines": [
        {
          "StartDate": "2020-01-01",
          "Tiers": [
            {
              "Value": "10",
              "Sequence": "1"
            },
            {
              "Value": "20",
              "Sequence": "2"
            },
            {
              "Value": "30",
              "Sequence": "3"
            }
          ],
          "FormulaId": "a5C3i000000DhmHEAS",
          "ProductCode": "GC1040",
          "IsQualification": true,
          "AliasName": "Q2",
          "ProductType": "Product",
          "ProductId": "01t3i000001GOf5AAG",
          "EndDate": "2020-01-31",
          "LineType": "Qualification",
          "Name": "GenWatt Diesel 200kW"
        },
        {
          "StartDate": "2020-01-01",
          "Tiers": [
            {
              "Value": "100",
              "Sequence": "1"
            },
            {
              "Value": "90",
              "Sequence": "2"
            },
            {
              "Value": "80",
              "Sequence": "3"
            }
          ],
          "FormulaId": "a5C3i000000DhmCEAS",
          "ProductCode": "GC1040",
          "IsQualification": false,
          "AliasName": "B2",
          "ProductType": "Product",
          "ProductId": "01t3i000001GOf5AAG",
          "EndDate": "2020-01-31",
          "LineType": "Benefit",
          "Name": "GenWatt Diesel 200kW"
        }
      ],
      "Id": "",
      "Sequence": "1",
      "IsNew": true,
      "SectionId": "SEC-00000002",
      "TierCount": "3"
    }
  ],
  "totalSections": 0
}


------------------------ Add QnB XXD Benefit Product Request -------------------------------------------
{
  "incentiveId": "a1k3i0000015MkvAAE",
  "qnbData": [
    {
      "QnBLines": [
        {
          "StartDate": "2020-01-01",
          "Tiers": [
            {
              "Value": "10",
              "Sequence": "1"
            }
          ],
          "FormulaId": "a5C3i000000DhmHEAS",
          "ProductCode": "IN7080",
          "IsQualification": true,
          "AliasName": "Q1",
          "ProductType": "Product",
          "ProductId": "01t3i000001GOf7AAG",
          "EndDate": "2020-01-31",
          "LineType": "Qualification",
          "Name": "Installation: Industrial - High"
        },
        {
          "StartDate": "2020-01-01",
          "Tiers": [
            {
              "Value": "120",
              "Sequence": "1"
            }
          ],
          "FormulaId": "a5C3i000000DhmCEAS",
          "ProductCode": "IN7080",
          "IsQualification": false,
          "AliasName": "B1",
          "ProductType": "Product",
          "ProductId": "01t3i000001GOf7AAG",
          "EndDate": "2020-01-31",
          "LineType": "Benefit",
          "Name": "Installation: Industrial - High"
        }
      ],
      "Id": "",
      "Sequence": "1",
      "IsNew": true,
      "SectionId": "SEC-00000001",
      "TierCount": "1"
    },
    {
      "QnBLines": [
        {
          "StartDate": "2020-01-01",
          "Tiers": [
            {
              "Value": "10",
              "Sequence": "1"
            }
          ],
          "FormulaId": "a5C3i000000DhmHEAS",
          "ProductCode": "GC1040",
          "IsQualification": true,
          "AliasName": "Q2",
          "ProductType": "Product",
          "ProductId": "01t3i000001GOf5AAG",
          "EndDate": "2020-01-31",
          "LineType": "Qualification",
          "Name": "GenWatt Diesel 200kW"
        },
        {
          "StartDate": "2020-01-01",
          "Tiers": [
            {
              "Value": "100",
              "Sequence": "1"
            }
          ],
          "FormulaId": "a5C3i000000DhmCEAS",
          "ProductCode": "GC1040",
          "IsQualification": false,
          "AliasName": "B2",
          "ProductType": "Product",
          "ProductId": "01t3i000001GOf5AAG",
          "EndDate": "2020-01-31",
          "LineType": "Benefit",
          "Name": "GenWatt Diesel 200kW"
        }
      ],
      "Id": "",
      "Sequence": "1",
      "IsNew": true,
      "SectionId": "SEC-00000002",
      "TierCount": "1"
    }
  ],
  "totalSections": 0
}
*/
