/**
 * 
 */
package com.isuite.rjil.iagent.jiomoney.common;

import java.io.Serializable;

/**
 * @author NovelVox
 * 
 */
public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5658660143945000613L;

	private String messageId;
	private String esbId;
	private String correlationId;
	private String extCorrelationId;
	private String businessId;
	private String timeStamp;

	/**
	 * @return the messageId
	 */
	public String getMessageId() {
		return messageId;
	}

	/**
	 * @param messageId
	 *            the messageId to set
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	/**
	 * @return the esbId
	 */
	public String getEsbId() {
		return esbId;
	}

	/**
	 * @param esbId
	 *            the esbId to set
	 */
	public void setEsbId(String esbId) {
		this.esbId = esbId;
	}

	/**
	 * @return the correlationId
	 */
	public String getCorrelationId() {
		return correlationId;
	}

	/**
	 * @param correlationId
	 *            the correlationId to set
	 */
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	/**
	 * @return the extCorrelationId
	 */
	public String getExtCorrelationId() {
		return extCorrelationId;
	}

	/**
	 * @param extCorrelationId
	 *            the extCorrelationId to set
	 */
	public void setExtCorrelationId(String extCorrelationId) {
		this.extCorrelationId = extCorrelationId;
	}

	/**
	 * @return the businessId
	 */
	public String getBusinessId() {
		return businessId;
	}

	/**
	 * @param businessId
	 *            the businessId to set
	 */
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	/**
	 * @return the timeStamp
	 */
	public String getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @param timeStamp
	 *            the timeStamp to set
	 */
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((businessId == null) ? 0 : businessId.hashCode());
		result = prime * result
				+ ((correlationId == null) ? 0 : correlationId.hashCode());
		result = prime * result + ((esbId == null) ? 0 : esbId.hashCode());
		result = prime
				* result
				+ ((extCorrelationId == null) ? 0 : extCorrelationId.hashCode());
		result = prime * result
				+ ((messageId == null) ? 0 : messageId.hashCode());
		result = prime * result
				+ ((timeStamp == null) ? 0 : timeStamp.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		Message other = (Message) obj;
		if (businessId == null) {
			if (other.businessId != null)
				return false;
		} else if (!businessId.equals(other.businessId))
			return false;
		if (correlationId == null) {
			if (other.correlationId != null)
				return false;
		} else if (!correlationId.equals(other.correlationId))
			return false;
		if (esbId == null) {
			if (other.esbId != null)
				return false;
		} else if (!esbId.equals(other.esbId))
			return false;
		if (extCorrelationId == null) {
			if (other.extCorrelationId != null)
				return false;
		} else if (!extCorrelationId.equals(other.extCorrelationId))
			return false;
		if (messageId == null) {
			if (other.messageId != null)
				return false;
		} else if (!messageId.equals(other.messageId))
			return false;
		if (timeStamp == null) {
			if (other.timeStamp != null)
				return false;
		} else if (!timeStamp.equals(other.timeStamp))
			return false;
		return true;
	}


}
