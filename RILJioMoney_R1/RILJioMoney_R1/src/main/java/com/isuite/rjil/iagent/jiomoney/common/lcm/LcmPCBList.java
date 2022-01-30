package com.isuite.rjil.iagent.jiomoney.common.lcm;

import java.io.Serializable;


public class LcmPCBList implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int rowNumber;
    private String campaignID;
    private long contactID;
    private int modeNumber;
    private String modeDesc;
    private String cValue;
    private String callStartDate;
    private String callEndDate;
    private String callStartTime;
    private String callEndTime;
	
    
    public int getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}
	public String getCampaignID() {
		return campaignID;
	}
	public void setCampaignID(String campaignID) {
		this.campaignID = campaignID;
	}
	public long getContactID() {
		return contactID;
	}
	public void setContactID(long contactID) {
		this.contactID = contactID;
	}
	public int getModeNumber() {
		return modeNumber;
	}
	public void setModeNumber(int modeNumber) {
		this.modeNumber = modeNumber;
	}
	public String getModeDesc() {
		return modeDesc;
	}
	public void setModeDesc(String modeDesc) {
		this.modeDesc = modeDesc;
	}
	public String getcValue() {
		return cValue;
	}
	public void setcValue(String cValue) {
		this.cValue = cValue;
	}
	public String getCallStartDate() {
		return callStartDate;
	}
	public void setCallStartDate(String callStartDate) {
		this.callStartDate = callStartDate;
	}
	public String getCallEndDate() {
		return callEndDate;
	}
	public void setCallEndDate(String callEndDate) {
		this.callEndDate = callEndDate;
	}
	public String getCallStartTime() {
		return callStartTime;
	}
	public void setCallStartTime(String callStartTime) {
		this.callStartTime = callStartTime;
	}
	public String getCallEndTime() {
		return callEndTime;
	}
	public void setCallEndTime(String callEndTime) {
		this.callEndTime = callEndTime;
	}


}
