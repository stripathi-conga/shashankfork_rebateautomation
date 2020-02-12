package com.apttus.sfdc.rebates.lightning.api.library;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.apttus.customException.ApplicationException;
import com.apttus.sfdc.rebates.lightning.api.pojo.QnBAddBenefitPojo;
import com.apttus.sfdc.rebates.lightning.generic.utils.RebatesConstants;
import com.apttus.sfdc.rudiments.utils.SFDCRestUtils;
import com.jayway.restassured.response.Response;

public class BenefitProductQnB extends CIM {

	public String requestString;
	public Response response;
	public QnBAddBenefitPojo qnbData = new QnBAddBenefitPojo();
	public String benefitFormulaIdTiered;
	public String qualificationFormulaIdTiered;

	public String getQualificationFormulaIdTiered() {
		return qualificationFormulaIdTiered;
	}

	public void setQualificationFormulaIdTiered(String qualificationFormulaIdTiered) {
		this.qualificationFormulaIdTiered = qualificationFormulaIdTiered;
	}

	public String getBenefitFormulaIdTiered() {
		return benefitFormulaIdTiered;
	}

	public void setBenefitFormulaIdTiered(String benefitFormulaIdTiered) {
		this.benefitFormulaIdTiered = benefitFormulaIdTiered;
	}

	public QnBAddBenefitPojo getQnbData() {
		return qnbData;
	}

	public void setQnbData(QnBAddBenefitPojo qnbData) {
		this.qnbData = qnbData;
	}

	public BenefitProductQnB(String baseURL, SFDCRestUtils sfdcRestUtils) {
		super(baseURL, sfdcRestUtils);
		mapRequestResponse = new HashMap<String, String>();
	}

	public Response addIncentiveQnB(List<Map<String, String>> testData) throws ApplicationException {
		try {
			requestString = qnbData.addBenefitRequest(testData, this);
			setRequestValue("addQnBRequest", requestString);
			response = sfdcRestUtils.postWithoutAppUrl(urlGenerator.addIncentiveQnBURL, requestString);
			validateResponseCode(response, RebatesConstants.responseOk);
			return response;
		} catch (Exception e) {
			throw new ApplicationException("Add Incentive QnB API call failed with exception trace : " + e);
		}
	}

	public Response getIncentiveQnB() throws ApplicationException {
		try {
			response = sfdcRestUtils
					.getData(urlGenerator.getIncentiveQnBURL.replace("{incentiveId}", incentiveData.getIncentiveId()));
			validateResponseCode(response, RebatesConstants.responseOk);
			return response;
		} catch (Exception e) {
			throw new ApplicationException("Get Incentive QnB API call failed with exception trace : " + e);
		}
	}
}
