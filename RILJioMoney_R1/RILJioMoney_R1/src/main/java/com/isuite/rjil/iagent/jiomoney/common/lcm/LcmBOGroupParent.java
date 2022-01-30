package com.isuite.rjil.iagent.jiomoney.common.lcm;

import java.io.Serializable;

public class LcmBOGroupParent  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int parentId;
	private String parentName;
	
	
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
	
	
}
