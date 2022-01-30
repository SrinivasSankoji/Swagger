package com.isuite.rjil.iagent.jiomoney.common;

import java.io.Serializable;

/**
 * @author NovelVox
 * 
 */
public class BusinessApplication implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7887619189060269190L;

	private String serviceVersion;
	private String operationVersion;
	private String messageType;
	private String srcApplicationname;
	private String operationName;
	private String businessEntitiesName;
	private String businessEntitiesValue;
	private String businessEntities;
	private String nameValuePairs;
	private String name;
	private String value;

	/**
	 * @return the serviceVersion
	 */
	public String getServiceVersion() {
		return serviceVersion;
	}

	/**
	 * @param serviceVersion
	 *            the serviceVersion to set
	 */
	public void setServiceVersion(String serviceVersion) {
		this.serviceVersion = serviceVersion;
	}

	/**
	 * @return the operationVersion
	 */
	public String getOperationVersion() {
		return operationVersion;
	}

	/**
	 * @param operationVersion
	 *            the operationVersion to set
	 */
	public void setOperationVersion(String operationVersion) {
		this.operationVersion = operationVersion;
	}

	/**
	 * @return the messageType
	 */
	public String getMessageType() {
		return messageType;
	}

	/**
	 * @param messageType
	 *            the messageType to set
	 */
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	/**
	 * @return the srcApplicationname
	 */
	public String getSrcApplicationname() {
		return srcApplicationname;
	}

	/**
	 * @param srcApplicationname
	 *            the srcApplicationname to set
	 */
	public void setSrcApplicationname(String srcApplicationname) {
		this.srcApplicationname = srcApplicationname;
	}

	/**
	 * @return the operationName
	 */
	public String getOperationName() {
		return operationName;
	}

	/**
	 * @param operationName
	 *            the operationName to set
	 */
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	/**
	 * @return the businessEntitiesName
	 */
	public String getBusinessEntitiesName() {
		return businessEntitiesName;
	}

	/**
	 * @param businessEntitiesName
	 *            the businessEntitiesName to set
	 */
	public void setBusinessEntitiesName(String businessEntitiesName) {
		this.businessEntitiesName = businessEntitiesName;
	}

	/**
	 * @return the businessEntitiesValue
	 */
	public String getBusinessEntitiesValue() {
		return businessEntitiesValue;
	}

	/**
	 * @param businessEntitiesValue
	 *            the businessEntitiesValue to set
	 */
	public void setBusinessEntitiesValue(String businessEntitiesValue) {
		this.businessEntitiesValue = businessEntitiesValue;
	}

	/**
	 * @return the businessEntities
	 */
	public String getBusinessEntities() {
		return businessEntities;
	}

	/**
	 * @param businessEntities
	 *            the businessEntities to set
	 */
	public void setBusinessEntities(String businessEntities) {
		this.businessEntities = businessEntities;
	}

	/**
	 * @return the nameValuePairs
	 */
	public String getNameValuePairs() {
		return nameValuePairs;
	}

	/**
	 * @param nameValuePairs
	 *            the nameValuePairs to set
	 */
	public void setNameValuePairs(String nameValuePairs) {
		this.nameValuePairs = nameValuePairs;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((businessEntities == null) ? 0 : businessEntities.hashCode());
		result = prime
				* result
				+ ((businessEntitiesName == null) ? 0 : businessEntitiesName
						.hashCode());
		result = prime
				* result
				+ ((businessEntitiesValue == null) ? 0 : businessEntitiesValue
						.hashCode());
		result = prime * result
				+ ((messageType == null) ? 0 : messageType.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((nameValuePairs == null) ? 0 : nameValuePairs.hashCode());
		result = prime * result
				+ ((operationName == null) ? 0 : operationName.hashCode());
		result = prime
				* result
				+ ((operationVersion == null) ? 0 : operationVersion.hashCode());
		result = prime * result
				+ ((serviceVersion == null) ? 0 : serviceVersion.hashCode());
		result = prime
				* result
				+ ((srcApplicationname == null) ? 0 : srcApplicationname
						.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BusinessApplication other = (BusinessApplication) obj;
		if (businessEntities == null) {
			if (other.businessEntities != null)
				return false;
		} else if (!businessEntities.equals(other.businessEntities))
			return false;
		if (businessEntitiesName == null) {
			if (other.businessEntitiesName != null)
				return false;
		} else if (!businessEntitiesName.equals(other.businessEntitiesName))
			return false;
		if (businessEntitiesValue == null) {
			if (other.businessEntitiesValue != null)
				return false;
		} else if (!businessEntitiesValue.equals(other.businessEntitiesValue))
			return false;
		if (messageType == null) {
			if (other.messageType != null)
				return false;
		} else if (!messageType.equals(other.messageType))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nameValuePairs == null) {
			if (other.nameValuePairs != null)
				return false;
		} else if (!nameValuePairs.equals(other.nameValuePairs))
			return false;
		if (operationName == null) {
			if (other.operationName != null)
				return false;
		} else if (!operationName.equals(other.operationName))
			return false;
		if (operationVersion == null) {
			if (other.operationVersion != null)
				return false;
		} else if (!operationVersion.equals(other.operationVersion))
			return false;
		if (serviceVersion == null) {
			if (other.serviceVersion != null)
				return false;
		} else if (!serviceVersion.equals(other.serviceVersion))
			return false;
		if (srcApplicationname == null) {
			if (other.srcApplicationname != null)
				return false;
		} else if (!srcApplicationname.equals(other.srcApplicationname))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
