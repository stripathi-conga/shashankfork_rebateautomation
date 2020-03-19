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
			qualification.setFormulaId(RebatesConstants.formulaDataMap.get(row.get("QualificationFormula")));
			qualification.setAliasName(row.get("QualificationAliasName"));
			Map<String, String> qualificationIdMap;
			qualificationIdMap = setProductId(qualification, row.get("QualificationProductType"),
					row.get("QualificationProductCode"), row.get("QualificationProduct"), "Qualification",
					row.get("QualificationFormulaId"), true, cim);

			qualification.setId(cim.qualificationIdMap.get(row.get("QualificationProduct")));
			qualification.setProductId(qualificationIdMap.get("productId"));
			qualification.setTiers(setTierValue(row.get("QualificationQuantity"), row.get("QualificationProduct"), true, cim));

			// ------------- Set benefit Data ------------------
			benefit.setStartDate(startDate);
			benefit.setEndDate(endDate);
			benefit.setFormulaId((RebatesConstants.formulaDataMap.get(row.get("BenefitFormula"))));
			benefit.setAliasName(row.get("BenefitAliasName"));
			Map<String, String> benefitIdMap;
			benefitIdMap = setProductId(benefit, row.get("BenefitProductType"), row.get("BenefitProductCode"),
					row.get("BenefitProduct"), "Benefit", row.get("BenefitFormulaId"), false, cim);

			benefit.setId(cim.benefitIdMap.get(row.get("BenefitProduct")));
			benefit.setProductId(benefitIdMap.get("productId"));
			benefit.setTiers(setTierValue(row.get("BenefitAmount"), row.get("BenefitProduct"), false, cim));

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

	public List<TiersPojo> setTierValue(String value, String productName, boolean isQualification, CIM cim) {
		String splitter = ";";
		TiersPojo tiervalues;
		List<TiersPojo> tierList = new ArrayList<TiersPojo>();
		int i = 1;
		String valueArr[] = value.split(splitter);
		List<String> tierId = new ArrayList<String>();
		if(isQualification) 
			tierId = cim.qualificationTierIdMap.get(productName);
		else
			tierId = cim.benefitTierIdMap.get(productName);
		
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

/*----------------------- Add QnB XXT Benefit Product Request -----------------------------
 
{
  "incentiveId": "a1k3i0000015Q0jAAE",
  "qnbData": [
    {
      "QnBLines": [
        {
          "StartDate": "2020-02-06",
          "Tiers": [
            {
              "Value": "1",
              "Id": "a2U3i000000DfR7EAK",
              "Sequence": "1"
            },
            {
              "Value": "20",
              "Id": "a2U3i000000DfR8EAK",
              "Sequence": "2"
            }
          ],
          "FormulaId": "a5C3i000000Dm61EAC",
          "ProductCode": "IN7080",
          "IsQualification": true,
          "AliasName": "Q1",
          "ProductType": "Product",
          "ProductId": "01t3i000001GOf7AAG",
          "Id": "a2V3i000000Dh2iEAC",
          "EndDate": "2020-12-26",
          "LineType": "Qualification",
          "Name": "Installation: Industrial - High"
        },
        {
          "StartDate": "2020-02-06",
          "Tiers": [
            {
              "Value": "220",
              "Id": "a2U3i000000DfR9EAK",
              "Sequence": "1"
            },
            {
              "Value": "200",
              "Id": "a2U3i000000DfRAEA0",
              "Sequence": "2"
            }
          ],
          "FormulaId": "a5C3i000000Dm5wEAC",
          "ProductCode": "IN7080",
          "IsQualification": false,
          "AliasName": "B1",
          "ProductType": "Product",
          "ProductId": "01t3i000001GOf7AAG",
          "Id": "a2V3i000000Dh2jEAC",
          "EndDate": "2020-12-26",
          "LineType": "Benefit",
          "Name": "Installation: Industrial - High"
        }
      ],
      "Id": "a2W3i000000DltMEAS",
      "Sequence": "1",
      "IsNew": false,
      "SectionId": "SEC-00000001",
      "TierCount": "2"
    },
    {
      "QnBLines": [
        {
          "StartDate": "2020-02-03",
          "Tiers": [
            {
              "Value": "1",
              "Id": "a2U3i000000DfRBEA0",
              "Sequence": "1"
            },
            {
              "Value": "15",
              "Id": "a2U3i000000DfRCEA0",
              "Sequence": "2"
            },
            {
              "Value": "40",
              "Id": "a2U3i000000DfRDEA0",
              "Sequence": "3"
            }
          ],
          "FormulaId": "a5C3i000000Dm61EAC",
          "ProductCode": "GC1040",
          "IsQualification": true,
          "AliasName": "Q2",
          "ProductType": "Product",
          "ProductId": "01t3i000001GOf5AAG",
          "Id": "a2V3i000000Dh2kEAC",
          "EndDate": "2020-12-29",
          "LineType": "Qualification",
          "Name": "GenWatt Diesel 200kW"
        },
        {
          "StartDate": "2020-02-03",
          "Tiers": [
            {
              "Value": "200",
              "Id": "a2U3i000000DfREEA0",
              "Sequence": "1"
            },
            {
              "Value": "190",
              "Id": "a2U3i000000DfRFEA0",
              "Sequence": "2"
            },
            {
              "Value": "180",
              "Id": "a2U3i000000DfRGEA0",
              "Sequence": "3"
            }
          ],
          "FormulaId": "a5C3i000000Dm5wEAC",
          "ProductCode": "GC1040",
          "IsQualification": false,
          "AliasName": "B2",
          "ProductType": "Product",
          "ProductId": "01t3i000001GOf5AAG",
          "Id": "a2V3i000000Dh2lEAC",
          "EndDate": "2020-12-29",
          "LineType": "Benefit",
          "Name": "GenWatt Diesel 200kW"
        }
      ],
      "Id": "a2W3i000000DltNEAS",
      "Sequence": "1",
      "IsNew": false,
      "SectionId": "SEC-00000002",
      "TierCount": "3"
    }
  ],
  "totalSections": 2
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
  "totalSections": 2
}
*/
