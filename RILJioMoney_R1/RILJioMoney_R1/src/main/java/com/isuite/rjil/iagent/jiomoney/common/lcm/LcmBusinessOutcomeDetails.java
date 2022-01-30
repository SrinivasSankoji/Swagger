package com.isuite.rjil.iagent.jiomoney.common.lcm;

import java.io.Serializable;


public class LcmBusinessOutcomeDetails implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String outcomeGroup;
    private String groupDescription;
    private int parentId;
    private String parentName;
    private String outComeId;
    private String description;
    
    
	public String getOutcomeGroup() {
		return outcomeGroup;
	}
	public void setOutcomeGroup(String outcomeGroup) {
		this.outcomeGroup = outcomeGroup;
	}
	public String getGroupDescription() {
		return groupDescription;
	}
	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getOutComeId() {
		return outComeId;
	}
	public void setOutComeId(String outComeId) {
		this.outComeId = outComeId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

    
}
