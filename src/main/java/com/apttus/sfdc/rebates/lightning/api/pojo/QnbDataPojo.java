package com.apttus.sfdc.rebates.lightning.api.pojo;

import java.util.List;

public class QnbDataPojo {

	private List<QnBLinesPojo> QnBLines;
	private String Id;
	private String Sequence;
	private boolean IsNew;
	private String SectionId;
	private String TierCount;

	public List<QnBLinesPojo> getQnBLines() {
		return QnBLines;
	}

	public void setQnBLines(List<QnBLinesPojo> qnbLines) {
		this.QnBLines = qnbLines;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		this.Id = id;
	}

	public String getSeiquence() {
		return Sequence;
	}

	public void setSequence(String sequence) {
		this.Sequence = sequence;
	}

	public boolean getIsNew() {
		return IsNew;
	}

	public void setIsNew(boolean isNew) {
		this.IsNew = isNew;
	}

	public String getSectionId() {
		return SectionId;
	}

	public void setSectionId(String sectionId) {
		this.SectionId = sectionId;
	}

	public String getTierCount() {
		return TierCount;
	}

	public void setTierCount(String tierCount) {
		this.TierCount = tierCount;
	}
}