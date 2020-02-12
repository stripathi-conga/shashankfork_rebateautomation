package com.apttus.sfdc.rebates.lightning.api.pojo;

public class TiersPojo {

	private String Value;
	private String Id;
	private String Sequence;

	public String getValue() {
		return Value;
	}

	public void setValue(String value) {
		this.Value = value;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		this.Id = id;
	}

	public String getSequence() {
		return Sequence;
	}

	public void setSequence(String sequence) {
		this.Sequence = sequence;
	}
}
