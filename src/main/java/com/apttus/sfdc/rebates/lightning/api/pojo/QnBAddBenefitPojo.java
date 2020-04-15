package com.apttus.sfdc.rebates.lightning.api.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.apttus.sfdc.rebates.lightning.api.library.CIM;
import com.apttus.sfdc.rebates.lightning.generic.utils.DataHelper;
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
		addQnB.setTotalSections(testData.size());		

		for (Map<String, String> row : testData) {
			qnbData = new QnbDataPojo();
			listQnBLines = new ArrayList<QnBLinesPojo>();
			qualification = new QnBLinesPojo();
			benefit = new QnBLinesPojo();
			qnbData.setSectionId(row.get("SectionId"));
			
			if(cim.qualificationIdMap.size()==0)
				qnbData.setIsNew(true);
			else
				qnbData.setIsNew(false);
			qnbData.setId(cim.sectionIdMap.get(row.get("SectionId")));
			qnbData.setSequence("1");
			tierCount = row.get("TierCount");
			if (tierCount != null) {
				qnbData.setTierCount(tierCount);
			} else
				qnbData.setTierCount("1");

			startDate = SFDCHelper.getCIMDateValue(row.get("StartDate"),cim);
			endDate = SFDCHelper.getCIMDateValue(row.get("EndDate"),cim);

			// ------------- Set Qualification Data ----------------
			qualification.setStartDate(startDate);
			qualification.setEndDate(endDate);
			qualification.setFormulaId(DataHelper.getFormulaDataMap().get(row.get("QualificationFormula")));
			qualification.setAliasName(row.get("QualificationAliasName"));
			Map<String, String> qualificationIdMap;
			qualificationIdMap = setItemId(qualification, row.get("QualificationType"),
					row.get("QualificationCode"), row.get("QualificationName"), "Qualification",
					row.get("QualificationFormula"), true, cim);

			qualification.setId(cim.qualificationIdMap.get(row.get("QualificationName")));
			qualification.setItemId(qualificationIdMap.get("itemId"));
			qualification.setTiers(setTierValue(row.get("QualificationQuantity"), row.get("QualificationName"), true, cim));

			// ------------- Set benefit Data ------------------
			benefit.setStartDate(startDate);
			benefit.setEndDate(endDate);
			benefit.setFormulaId(DataHelper.getFormulaDataMap().get(row.get("BenefitFormula")));
			benefit.setAliasName(row.get("BenefitAliasName"));
			Map<String, String> benefitIdMap;
			benefitIdMap = setItemId(benefit, row.get("BenefitType"), row.get("BenefitCode"),
					row.get("BenefitName"), "Benefit", row.get("BenefitFormula"), false, cim);

			benefit.setId(cim.benefitIdMap.get(row.get("BenefitName")));
			benefit.setItemId(benefitIdMap.get("itemId"));
			benefit.setTiers(setTierValue(row.get("BenefitAmount"), row.get("BenefitName"), false, cim));

			listQnBLines.add(qualification);
			listQnBLines.add(benefit);
			
			qnbData.setQnBLines(listQnBLines);
			listQnBData.add(qnbData);
		}
		addQnB.setQnbData(listQnBData);
		return new Gson().toJson(addQnB);
	}

	public Map<String, String> setItemId(QnBLinesPojo object, String itemType, String itemCode,
			String itemName, String lineType, String formulaId, boolean isQualification, CIM cim) throws Exception {

		QnBLinesPojo benefitLineData = object;
		Map<String, String> ids = new HashMap<>();
		switch (itemType.trim().toLowerCase()) {
		case "product":
			benefitLineData.setItemType(itemType);
			benefitLineData.setItemCode(itemCode);
			benefitLineData.setName(itemName);
			break;
		default:
			break;
		}
		benefitLineData.setLineType(lineType);
		benefitLineData.setIsQualification(isQualification);
		ids.put("itemId", cim.getItemId(itemName));
		return ids;
	}

	public List<TiersPojo> setTierValue(String value, String itemName, boolean isQualification, CIM cim) {
		String splitter = ";";
		TiersPojo tiervalues;
		List<TiersPojo> tierList = new ArrayList<TiersPojo>();
		int i = 1;
		String valueArr[] = value.split(splitter);
		List<String> tierId = new ArrayList<String>();
		if(isQualification) 
			tierId = cim.qualificationTierIdMap.get(itemName);
		else
			tierId = cim.benefitTierIdMap.get(itemName);
		
		for (String val : valueArr) {
			tiervalues = new TiersPojo();
			tiervalues.setSequence(Integer.toString(i));
			tiervalues.setValue(val);
			if (tierId!=null)
				tiervalues.setId(tierId.get(i-1));
			tierList.add(tiervalues);
			i++;
		}
		return tierList;
	}
}

/*----------------------- Add QnB XXT Benefit Line Request -----------------------------
 
{
  "qnbData": [
    {
      "Id": "a3m3i0000001GkyAAE",
      "TierCount": 2,
      "IsNew": false,
      "Sequence": 1,
      "SectionId": "SEC-00000001",
      "QnBLines": [
        {
          "StartDate": "2020-04-06",
          "EndDate": "2020-12-26",
          "ItemId": "01t3i000001GOeoAAG",
          "ItemCode": "IN7080",
          "Name": "Installation: Industrial - High",
          "ItemType": "Product",
          "FormulaId": "a4q3i0000000gNpAAI",
          "Tiers": [
            {
              "Id": "a3k3i0000000u4zAAA",
              "Sequence": 1,
              "Value": 1
            },
            {
              "Id": "a3k3i0000000u50AAA",
              "Sequence": 2,
              "Value": 30
            }
          ],
          "LineType": "Qualification",
          "IsQualification": true,
          "AliasName": "Q1",
          "Id": "a3l3i0000001MvOAAU"
        },
        {
          "StartDate": "2020-04-06",
          "EndDate": "2020-12-26",
          "ItemId": "01t3i000001GOeoAAG",
          "ItemCode": "IN7080",
          "Name": "Installation: Industrial - High",
          "ItemType": "Product",
          "FormulaId": "a4q3i0000000gO4AAI",
          "Tiers": [
            {
              "Id": "a3k3i0000000u51AAA",
              "Sequence": 1,
              "Value": 120
            },
            {
              "Id": "a3k3i0000000u52AAA",
              "Sequence": 2,
              "Value": 100
            }
          ],
          "LineType": "Benefit",
          "IsQualification": false,
          "AliasName": "B1",
          "Id": "a3l3i0000001MvPAAU"
        }
      ]
    },
    ],
  "incentiveId": "a303i0000002j4OAAQ",
  "totalSections": 1
}


------------------------ Add QnB XXD Benefit Line Request -------------------------------------------
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
          "ItemCode": "IN7080",
          "IsQualification": true,
          "AliasName": "Q1",
          "ItemType": "Product",
          "ItemId": "01t3i000001GOf7AAG",
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
          "ItemCode": "IN7080",
          "IsQualification": false,
          "AliasName": "B1",
          "ItemType": "Product",
          "ItemId": "01t3i000001GOf7AAG",
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
          "ItemCode": "GC1040",
          "IsQualification": true,
          "AliasName": "Q2",
          "ItemType": "Product",
          "ItemId": "01t3i000001GOf5AAG",
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
          "ItemCode": "GC1040",
          "IsQualification": false,
          "AliasName": "B2",
          "ItemType": "Product",
          "ItemId": "01t3i000001GOf5AAG",
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
  "totalSections": 2
}
*/
