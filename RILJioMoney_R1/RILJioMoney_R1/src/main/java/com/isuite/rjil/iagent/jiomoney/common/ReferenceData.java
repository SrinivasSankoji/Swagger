/*
 * 
 */
package com.isuite.rjil.iagent.jiomoney.common;

import java.io.Serializable;

/**
 * 
 * @author NovelVox
 * 
 */
public final class ReferenceData implements Serializable {

	/**
	 * s
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private String value;
	private String description;
	private String type;
	private String mstrImpact;
	private String mstrVerifyUser;
	private String mstrUrgency;


	public String getUrgency() {
		return mstrUrgency;
	}

	public void setUrgency(String pUrgency) {
		mstrUrgency = pUrgency;
	}
	

	public String getVerifyUser() {
		return mstrVerifyUser;
	}

	public void setVerifyUser(String pVerifyUser) {
		mstrVerifyUser = pVerifyUser;
	}

	
	public String getImpact() {
		return mstrImpact;
	}

	public void setImpact(String pImpact) {
		mstrImpact = pImpact;
	}
	
	
	public ReferenceData() {

	}

	/**
	 * @param code
	 */
	public ReferenceData(String code) {
		super();
		this.code = code;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Overriding equals method to ensure the check of equality only on code. So
	 * that, retrieval can be optimized rather than traversing entire list to
	 * search the value.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ReferenceData))
			return false;
		ReferenceData rd = (ReferenceData) obj;
		return rd.getCode().equals(this.getCode());
	}

	/**
	 * 
	 */
	@Override
	public int hashCode() {
		int result = 17;
		return result = 31 * result + this.getCode().hashCode();
	}
	@Override
	public String toString() 
	{
		return "\nCode :"+code+"\nDesc : "+description+"\nverify flag : "+mstrVerifyUser+"\nimpact:"+mstrImpact+"\nurgency : "+mstrUrgency;
	}
}
