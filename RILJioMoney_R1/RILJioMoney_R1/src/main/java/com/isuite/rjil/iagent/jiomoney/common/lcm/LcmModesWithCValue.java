package com.isuite.rjil.iagent.jiomoney.common.lcm;

import java.io.Serializable;


public class LcmModesWithCValue implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String outcomeID;
    private String desc;
    private String campaignID;
    private String cValue;
    
    
	public String getOutcomeID() {
		return outcomeID;
	}
	public void setOutcomeID(String outcomeID) {
		this.outcomeID = outcomeID;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getCampaignID() {
		return campaignID;
	}
	public void setCampaignID(String campaignID) {
		this.campaignID = campaignID;
	}
	public String getcValue() {
		return cValue;
	}
	public void setcValue(String cValue) {
		this.cValue = cValue;
	}

    
}
