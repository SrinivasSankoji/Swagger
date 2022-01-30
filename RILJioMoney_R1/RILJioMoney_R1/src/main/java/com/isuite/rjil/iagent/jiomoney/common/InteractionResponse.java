package com.isuite.rjil.iagent.jiomoney.common;

import java.util.List;

public class InteractionResponse 
{
	private String categoriesAvailable = "true";
	private String interactionRefNumber = "";
	private String msg = "";
	private String notes = "";
	private String desc = "";
	
	private List<ReferenceData> category1 = null;
	private String category2Code = null;
	private String category3Code = null;
	private String category4Code = null;
	private List<ReferenceData> impact = null;
	private List<ReferenceData> urgency = null;
	
	
	public String getCategoriesAvailable() {
		return categoriesAvailable;
	}
	public void setCategoriesAvailable(String categoriesAvailable) {
		this.categoriesAvailable = categoriesAvailable;
	}
	
	
	public List<ReferenceData> getCategory1() {
		return category1;
	}
	public void setCategory1(List<ReferenceData> category1) {
		this.category1 = category1;
	}
	public String getCategory2() {
		return category2Code;
	}
	public void setCategory2(String category2) {
		this.category2Code = category2;
	}
	public String getCategory3() {
		return category3Code;
	}
	public void setCategory3(String category3) {
		this.category3Code = category3;
	}
	public String getCategory4() {
		return category4Code;
	}
	public void setCategory4(String category4) {
		this.category4Code = category4;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String pNotes) {
		notes = pNotes;
	}
	
	
	public String getDesc() {
		return desc;
	}
	public void setDesc(String pDesc) {
		desc = pDesc;
	}
	
	public String getInteractionRefNumber() {
		return interactionRefNumber;
	}
	public void setInteractionRefNumber(String pInteractionRefNumber) {
		interactionRefNumber = pInteractionRefNumber;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String pSuccessMsg) {
		msg = pSuccessMsg;
	}
	public List<ReferenceData>  getImpact() {
		return impact;
	}
	public void setImpact(List<ReferenceData>  pImpact) {
		impact = pImpact;
	}
	
	public List<ReferenceData>  getUrgency() {
		return urgency;
	}
	public void setUrgency(List<ReferenceData> urgency) {
		this.urgency = urgency;
	}

}
