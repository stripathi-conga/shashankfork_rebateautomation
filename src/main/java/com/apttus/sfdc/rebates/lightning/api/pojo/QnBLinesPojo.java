package com.apttus.sfdc.rebates.lightning.api.pojo;

import java.util.List;

public class QnBLinesPojo {

	private String StartDate;
	private List<TiersPojo> Tiers;
	private String FormulaId;
	private String ItemCode;
	private boolean IsQualification;
	private String AliasName;
	private String ItemType;
	private String ItemId;
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

	public String getItemCode() {
		return ItemCode;
	}

	public void setItemCode(String itemCode) {
		this.ItemCode = itemCode;
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

	public String getItemType() {
		return ItemType;
	}

	public void setItemType(String itemType) {
		this.ItemType = itemType;
	}

	public String getItemId() {
		return ItemId;
	}

	public void setItemId(String itemId) {
		this.ItemId = itemId;
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
