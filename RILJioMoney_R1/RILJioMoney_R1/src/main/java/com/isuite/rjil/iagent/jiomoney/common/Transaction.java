/**
 * 
 */
package com.isuite.rjil.iagent.jiomoney.common;

import java.io.Serializable;

/**
 * @author Novelvox
 * 
 */
public class Transaction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5191617043789963857L;

	private String transactionRecipient;
	private String transactionDateTime;
	private String transactionType;
	private String transactionId;
	private String transactionChannel;
	private String transactionAmount;
	private String transactionStatus;
	private String transactionDisposition;
	private String transactionNote;
	private String transactionDate;
	private String transactionCount;

	/**
	 * @return the transactionRecipient
	 */
	public String getTransactionRecipient() {
		return transactionRecipient;
	}

	/**
	 * @param transactionRecipient
	 *            the transactionRecipient to set
	 */
	public void setTransactionRecipient(String transactionRecipient) {
		this.transactionRecipient = transactionRecipient;
	}

	/**
	 * @return the transactionDateTime
	 */
	public String getTransactionDateTime() {
		return transactionDateTime;
	}

	/**
	 * @param transactionDateTime
	 *            the transactionDateTime to set
	 */
	public void setTransactionDateTime(String transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}

	/**
	 * @return the transactionType
	 */
	public String getTransactionType() {
		return transactionType;
	}

	/**
	 * @param transactionType
	 *            the transactionType to set
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	/**
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId
	 *            the transactionId to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return the transactionAmount
	 */
	public String getTransactionAmount() {
		return transactionAmount;
	}

	/**
	 * @param transactionAmount
	 *            the transactionAmount to set
	 */
	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	/**
	 * @return the transactionCount
	 */
	public String getTransactionCount() {
		return transactionCount;
	}

	/**
	 * @param transactionCount
	 *            the transactionCount to set
	 */
	public void setTransactionCount(String transactionCount) {
		this.transactionCount = transactionCount;
	}

	/**
	 * @return the transactionNote
	 */
	public String getTransactionNote() {
		return transactionNote;
	}

	/**
	 * @param transactionNote
	 *            the transactionNote to set
	 */
	public void setTransactionNote(String transactionNote) {
		this.transactionNote = transactionNote;
	}

	/**
	 * @return the transactionChannel
	 */
	public String getTransactionChannel() {
		return transactionChannel;
	}

	/**
	 * @param transactionChannel
	 *            the transactionChannel to set
	 */
	public void setTransactionChannel(String transactionChannel) {
		this.transactionChannel = transactionChannel;
	}

	/**
	 * @return the transactionStatus
	 */
	public String getTransactionStatus() {
		return transactionStatus;
	}

	/**
	 * @param transactionStatus
	 *            the transactionStatus to set
	 */
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	/**
	 * @return the transactionDisposition
	 */
	public String getTransactionDisposition() {
		return transactionDisposition;
	}

	/**
	 * @param transactionDisposition
	 *            the transactionDisposition to set
	 */
	public void setTransactionDisposition(String transactionDisposition) {
		this.transactionDisposition = transactionDisposition;
	}

	/**
	 * @return the transactionDate
	 */
	public String getTransactionDate() {
		return transactionDate;
	}

	/**
	 * @param transactionDate
	 *            the transactionDate to set
	 */
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
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
				+ ((transactionAmount == null) ? 0 : transactionAmount
						.hashCode());
		result = prime
				* result
				+ ((transactionChannel == null) ? 0 : transactionChannel
						.hashCode());
		result = prime
				* result
				+ ((transactionCount == null) ? 0 : transactionCount.hashCode());
		result = prime * result
				+ ((transactionDate == null) ? 0 : transactionDate.hashCode());
		result = prime
				* result
				+ ((transactionDateTime == null) ? 0 : transactionDateTime
						.hashCode());
		result = prime
				* result
				+ ((transactionDisposition == null) ? 0
						: transactionDisposition.hashCode());
		result = prime * result
				+ ((transactionId == null) ? 0 : transactionId.hashCode());
		result = prime * result
				+ ((transactionNote == null) ? 0 : transactionNote.hashCode());
		result = prime
				* result
				+ ((transactionRecipient == null) ? 0 : transactionRecipient
						.hashCode());
		result = prime
				* result
				+ ((transactionStatus == null) ? 0 : transactionStatus
						.hashCode());
		result = prime * result
				+ ((transactionType == null) ? 0 : transactionType.hashCode());
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
		Transaction other = (Transaction) obj;
		if (transactionAmount == null) {
			if (other.transactionAmount != null)
				return false;
		} else if (!transactionAmount.equals(other.transactionAmount))
			return false;
		if (transactionChannel == null) {
			if (other.transactionChannel != null)
				return false;
		} else if (!transactionChannel.equals(other.transactionChannel))
			return false;
		if (transactionCount == null) {
			if (other.transactionCount != null)
				return false;
		} else if (!transactionCount.equals(other.transactionCount))
			return false;
		if (transactionDate == null) {
			if (other.transactionDate != null)
				return false;
		} else if (!transactionDate.equals(other.transactionDate))
			return false;
		if (transactionDateTime == null) {
			if (other.transactionDateTime != null)
				return false;
		} else if (!transactionDateTime.equals(other.transactionDateTime))
			return false;
		if (transactionDisposition == null) {
			if (other.transactionDisposition != null)
				return false;
		} else if (!transactionDisposition.equals(other.transactionDisposition))
			return false;
		if (transactionId == null) {
			if (other.transactionId != null)
				return false;
		} else if (!transactionId.equals(other.transactionId))
			return false;
		if (transactionNote == null) {
			if (other.transactionNote != null)
				return false;
		} else if (!transactionNote.equals(other.transactionNote))
			return false;
		if (transactionRecipient == null) {
			if (other.transactionRecipient != null)
				return false;
		} else if (!transactionRecipient.equals(other.transactionRecipient))
			return false;
		if (transactionStatus == null) {
			if (other.transactionStatus != null)
				return false;
		} else if (!transactionStatus.equals(other.transactionStatus))
			return false;
		if (transactionType == null) {
			if (other.transactionType != null)
				return false;
		} else if (!transactionType.equals(other.transactionType))
			return false;
		return true;
	}

}
