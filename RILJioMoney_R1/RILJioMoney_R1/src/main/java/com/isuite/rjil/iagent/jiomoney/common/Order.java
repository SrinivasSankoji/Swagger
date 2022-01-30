package com.isuite.rjil.iagent.jiomoney.common;

import java.io.Serializable;

/**
 * @author NovelVox
 * 
 */
public class Order implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8138378496945635481L;
	private String dealerId;
	private String agentId;
	private String agentName;
	private String customerKYC;
	private String orderRefNumber;
	private String orderAction;
	private String orderCategory;
	private String orderType;

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getCustomerKYC() {
		return customerKYC;
	}

	public void setCustomerKYC(String customerKYC) {
		this.customerKYC = customerKYC;
	}

	public String getOrderRefNumber() {
		return orderRefNumber;
	}

	public void setOrderRefNumber(String orderRefNumber) {
		this.orderRefNumber = orderRefNumber;
	}

	public String getOrderAction() {
		return orderAction;
	}

	public void setOrderAction(String orderAction) {
		this.orderAction = orderAction;
	}

	public String getOrderCategory() {
		return orderCategory;
	}

	public void setOrderCategory(String orderCategory) {
		this.orderCategory = orderCategory;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agentId == null) ? 0 : agentId.hashCode());
		result = prime * result
				+ ((agentName == null) ? 0 : agentName.hashCode());
		result = prime * result
				+ ((customerKYC == null) ? 0 : customerKYC.hashCode());
		result = prime * result
				+ ((dealerId == null) ? 0 : dealerId.hashCode());
		result = prime * result
				+ ((orderAction == null) ? 0 : orderAction.hashCode());
		result = prime * result
				+ ((orderCategory == null) ? 0 : orderCategory.hashCode());
		result = prime * result
				+ ((orderRefNumber == null) ? 0 : orderRefNumber.hashCode());
		result = prime * result
				+ ((orderType == null) ? 0 : orderType.hashCode());
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
		Order other = (Order) obj;
		if (agentId == null) {
			if (other.agentId != null)
				return false;
		} else if (!agentId.equals(other.agentId))
			return false;
		if (agentName == null) {
			if (other.agentName != null)
				return false;
		} else if (!agentName.equals(other.agentName))
			return false;
		if (customerKYC == null) {
			if (other.customerKYC != null)
				return false;
		} else if (!customerKYC.equals(other.customerKYC))
			return false;
		if (dealerId == null) {
			if (other.dealerId != null)
				return false;
		} else if (!dealerId.equals(other.dealerId))
			return false;
		if (orderAction == null) {
			if (other.orderAction != null)
				return false;
		} else if (!orderAction.equals(other.orderAction))
			return false;
		if (orderCategory == null) {
			if (other.orderCategory != null)
				return false;
		} else if (!orderCategory.equals(other.orderCategory))
			return false;
		if (orderRefNumber == null) {
			if (other.orderRefNumber != null)
				return false;
		} else if (!orderRefNumber.equals(other.orderRefNumber))
			return false;
		if (orderType == null) {
			if (other.orderType != null)
				return false;
		} else if (!orderType.equals(other.orderType))
			return false;
		return true;
	}

}
