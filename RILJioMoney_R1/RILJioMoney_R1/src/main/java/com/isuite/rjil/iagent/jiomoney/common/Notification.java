package com.isuite.rjil.iagent.jiomoney.common;

import java.io.Serializable;

/**
 * 
 * @author Novelvox
 * 
 */
public class Notification implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3624723654755160687L;

	private String notificationRefId;
	private String notificationTemplateId;
	private String status;

	/**
	 * @return the notificationRefId
	 */
	public String getNotificationRefId() {
		return notificationRefId;
	}

	/**
	 * @param notificationRefId
	 *            the notificationRefId to set
	 */
	public void setNotificationRefId(String notificationRefId) {
		this.notificationRefId = notificationRefId;
	}

	/**
	 * @return the notificationTemplateId
	 */
	public String getNotificationTemplateId() {
		return notificationTemplateId;
	}

	/**
	 * @param notificationTemplateId
	 *            the notificationTemplateId to set
	 */
	public void setNotificationTemplateId(String notificationTemplateId) {
		this.notificationTemplateId = notificationTemplateId;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((notificationRefId == null) ? 0 : notificationRefId
						.hashCode());
		result = prime
				* result
				+ ((notificationTemplateId == null) ? 0
						: notificationTemplateId.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Notification other = (Notification) obj;
		if (notificationRefId == null) {
			if (other.notificationRefId != null)
				return false;
		} else if (!notificationRefId.equals(other.notificationRefId))
			return false;
		if (notificationTemplateId == null) {
			if (other.notificationTemplateId != null)
				return false;
		} else if (!notificationTemplateId.equals(other.notificationTemplateId))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
	

}
