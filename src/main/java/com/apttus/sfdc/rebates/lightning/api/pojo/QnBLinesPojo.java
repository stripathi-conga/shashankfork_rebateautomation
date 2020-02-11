package com.apttus.sfdc.rebates.lightning.api.pojo;

import java.util.List;

public class QnBLinesPojo {

	private String StartDate;
	private List<TiersPojo> Tiers;
	private String FormulaId;
	private String ProductCode;
	private boolean IsQualification;
	private String AliasName;
	private String ProductType;
	private String ProductId;
	private String Id;
	private String EndDate;
	private String LineType;
	private String Name;

	public String getStartDate() {
		return StartDate;
	}

	public void setStartDate(String startDate) {
		this.StartDate = startDate;
	}

	public List<TiersPojo> getTiers() {
		return Tiers;
	}

	public void setTiers(List<TiersPojo> tiers) {
		this.Tiers = tiers;
	}

	public String getFormulaId() {
		return FormulaId;
	}

	public void setFormulaId(String formulaId) {
		this.FormulaId = formulaId;
	}

	public String getProductCode() {
		return ProductCode;
	}

	public void setProductCode(String productCode) {
		this.ProductCode = productCode;
	}

	public boolean getIsQualification() {
		return IsQualification;
	}

	public void setIsQualification(boolean isQualification) {
		this.IsQualification = isQualification;
	}

	public String getAliasName() {
		return AliasName;
	}

	public void setAliasName(String aliasName) {
		this.AliasName = aliasName;
	}

	public String getProductType() {
		return ProductType;
	}

	public void setProductType(String productType) {
		this.ProductType = productType;
	}

	public String getProductId() {
		return ProductId;
	}

	public void setProductId(String productId) {
		this.ProductId = productId;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		this.Id = id;
	}

	public String getEndDate() {
		return EndDate;
	}

	public void setEndDate(String endDate) {
		this.EndDate = endDate;
	}

	public String getLineType() {
		return LineType;
	}

	public void setLineType(String lineType) {
		this.LineType = lineType;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		this.Name = name;
	}
}
