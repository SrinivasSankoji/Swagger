package com.isuite.rjil.iagent.jiomoney.common;

import java.io.Serializable;

/**
 * 
 * @author NovelVox
 * 
 */
public class CustomerProblem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1159385464656486128L;
	private String problemRefNo;
	private String serviceRequestId;
	private String category;
	private String categoryDescription;
	private String subCategory;
	private String subCategoryDescription;
	private String subSubCategory;
	private String subSubcategoryDescription;

	private String customerId;
	private String interactionDate;
	private String callDuration;

	private String description;
	private String status;
	private String statusDesc;
	private String creationDate;
	private String channel;
	private String estResolutionTime;
	private String agentCode;
	private String productId;
	private String productName;
	private String impact;
	private String urgency;
	private String serviceTeam;
	private String estSLA;
	private String interactionNumber;

	private Status providerStatus;
	private Message headerMessage;
	private BusinessApplication businessApplication;
	private String owner;
	private String communicationMode;
	private String tatUnit;
	private String tat;
	private String category4;	
	private String notes;
	private String mstrComments = "";
	
	//For already created SR including SRExist flag and returnMsg
	private String isSRExist;
	private String srExistReturnMsg;
	
	public String getIsSRExist() {
		return isSRExist;
	}

	public void setIsSRExist(String isSRExist) {
		this.isSRExist = isSRExist;
	}

	public String getSRExistReturnMsg() {
		return srExistReturnMsg;
	}

	public void setSRExistReturnMsg(String srExistReturnMsg) {
		this.srExistReturnMsg = srExistReturnMsg;
	}

	public String getComments() {
		return mstrComments;
	}

	public void setComments(String Commantes) {
		this.mstrComments = Commantes;
	}
	
	
	public String getTat() {
		return tat;
	}

	public void setTat(String tat) {
		this.tat = tat;
	}

	public String getTatUnit() {
		return tatUnit;
	}

	public void setTatUnit(String tatUnit) {
		this.tatUnit = tatUnit;
	}

	public String getCommunicationMode() {
		return communicationMode;
	}

	public void setCommunicationMode(String communicationMode) {
		this.communicationMode = communicationMode;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * @return the problemRefNo
	 */
	public String getProblemRefNo() {
		return problemRefNo;
	}

	/**
	 * @param problemRefNo
	 *            the problemRefNo to set
	 */
	public void setProblemRefNo(String problemRefNo) {
		this.problemRefNo = problemRefNo;
	}

	/**
	 * @return the serviceRequestId
	 */
	public String getServiceRequestId() {
		return serviceRequestId;
	}

	/**
	 * @param serviceRequestId
	 *            the serviceRequestId to set
	 */
	public void setServiceRequestId(String serviceRequestId) {
		this.serviceRequestId = serviceRequestId;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the categoryDescription
	 */
	public String getCategoryDescription() {
		return categoryDescription;
	}

	/**
	 * @param categoryDescription
	 *            the categoryDescription to set
	 */
	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	/**
	 * @return the subCategory
	 */
	public String getSubCategory() {
		return subCategory;
	}

	/**
	 * @param subCategory
	 *            the subCategory to set
	 */
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	/**
	 * @return the subCategoryDescription
	 */
	public String getSubCategoryDescription() {
		return subCategoryDescription;
	}

	/**
	 * @param subCategoryDescription
	 *            the subCategoryDescription to set
	 */
	public void setSubCategoryDescription(String subCategoryDescription) {
		this.subCategoryDescription = subCategoryDescription;
	}

	/**
	 * @return the subSubCategory
	 */
	public String getSubSubCategory() {
		return subSubCategory;
	}

	/**
	 * @param subSubCategory
	 *            the subSubCategory to set
	 */
	public void setSubSubCategory(String subSubCategory) {
		this.subSubCategory = subSubCategory;
	}

	/**
	 * @return the subSubcategoryDescription
	 */
	public String getSubSubcategoryDescription() {
		return subSubcategoryDescription;
	}

	/**
	 * @param subSubcategoryDescription
	 *            the subSubcategoryDescription to set
	 */
	public void setSubSubcategoryDescription(String subSubcategoryDescription) {
		this.subSubcategoryDescription = subSubcategoryDescription;
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

	/**
	 * @return the statusDesc
	 */
	public String getStatusDesc() {
		return statusDesc;
	}

	/**
	 * @param statusDesc
	 *            the statusDesc to set
	 */
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	/**
	 * @return the creationDate
	 */
	public String getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate
	 *            the creationDate to set
	 */
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the channel
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * @param channel
	 *            the channel to set
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}

	/**
	 * @return the estResolutionTime
	 */
	public String getEstResolutionTime() {
		return estResolutionTime;
	}

	/**
	 * @param estResolutionTime
	 *            the estResolutionTime to set
	 */
	public void setEstResolutionTime(String estResolutionTime) {
		this.estResolutionTime = estResolutionTime;
	}

	/**
	 * @return the agentCode
	 */
	public String getAgentCode() {
		return agentCode;
	}

	/**
	 * @param agentCode
	 *            the agentCode to set
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId
	 *            the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName
	 *            the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the impact
	 */
	public String getImpact() {
		return impact;
	}

	/**
	 * @param impact
	 *            the impact to set
	 */
	public void setImpact(String impact) {
		this.impact = impact;
	}

	/**
	 * @return the urgency
	 */
	public String getUrgency() {
		return urgency;
	}

	/**
	 * @param urgency
	 *            the urgency to set
	 */
	public void setUrgency(String urgency) {
		this.urgency = urgency;
	}

	/**
	 * @return the serviceTeam
	 */
	public String getServiceTeam() {
		return serviceTeam;
	}

	/**
	 * @param serviceTeam
	 *            the serviceTeam to set
	 */
	public void setServiceTeam(String serviceTeam) {
		this.serviceTeam = serviceTeam;
	}

	/**
	 * @return the estSLA
	 */
	public String getEstSLA() {
		return estSLA;
	}

	/**
	 * @param estSLA
	 *            the estSLA to set
	 */
	public void setEstSLA(String estSLA) {
		this.estSLA = estSLA;
	}

	/**
	 * @return the providerStatus
	 */
	public Status getProviderStatus() {
		return providerStatus;
	}

	/**
	 * @param providerStatus
	 *            the providerStatus to set
	 */
	public void setProviderStatus(Status providerStatus) {
		this.providerStatus = providerStatus;
	}

	/**
	 * @return the headerMessage
	 */
	public Message getHeaderMessage() {
		return headerMessage;
	}

	/**
	 * @param headerMessage
	 *            the headerMessage to set
	 */
	public void setHeaderMessage(Message headerMessage) {
		this.headerMessage = headerMessage;
	}

	/**
	 * @return the businessApplication
	 */
	public BusinessApplication getBusinessApplication() {
		return businessApplication;
	}

	/**
	 * @param businessApplication
	 *            the businessApplication to set
	 */
	public void setBusinessApplication(BusinessApplication businessApplication) {
		this.businessApplication = businessApplication;
	}

	public String getInteractionNumber() {
		return interactionNumber;
	}

	public void setInteractionNumber(String interactionNumber) {
		this.interactionNumber = interactionNumber;
	}

	public String getInteractionDate() {
		return interactionDate;
	}

	public void setInteractionDate(String interactionDate) {
		this.interactionDate = interactionDate;
	}

	public String getCallDuration() {
		return callDuration;
	}

	public void setCallDuration(String callDuration) {
		this.callDuration = callDuration;
	}

	/**
	 * @return the customerId
	 */
	public String getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId
	 *            the customerId to set
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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
		result = prime * result
				+ ((agentCode == null) ? 0 : agentCode.hashCode());
		result = prime
				* result
				+ ((businessApplication == null) ? 0 : businessApplication
						.hashCode());
		result = prime * result
				+ ((callDuration == null) ? 0 : callDuration.hashCode());
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime
				* result
				+ ((categoryDescription == null) ? 0 : categoryDescription
						.hashCode());
		result = prime * result + ((channel == null) ? 0 : channel.hashCode());
		result = prime * result
				+ ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result
				+ ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime
				* result
				+ ((estResolutionTime == null) ? 0 : estResolutionTime
						.hashCode());
		result = prime * result + ((estSLA == null) ? 0 : estSLA.hashCode());
		result = prime * result
				+ ((headerMessage == null) ? 0 : headerMessage.hashCode());
		result = prime * result + ((impact == null) ? 0 : impact.hashCode());
		result = prime * result
				+ ((interactionDate == null) ? 0 : interactionDate.hashCode());
		result = prime
				* result
				+ ((interactionNumber == null) ? 0 : interactionNumber
						.hashCode());
		result = prime * result
				+ ((problemRefNo == null) ? 0 : problemRefNo.hashCode());
		result = prime * result
				+ ((productId == null) ? 0 : productId.hashCode());
		result = prime * result
				+ ((productName == null) ? 0 : productName.hashCode());
		result = prime * result
				+ ((providerStatus == null) ? 0 : providerStatus.hashCode());
		result = prime
				* result
				+ ((serviceRequestId == null) ? 0 : serviceRequestId.hashCode());
		result = prime * result
				+ ((serviceTeam == null) ? 0 : serviceTeam.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((statusDesc == null) ? 0 : statusDesc.hashCode());
		result = prime * result
				+ ((subCategory == null) ? 0 : subCategory.hashCode());
		result = prime
				* result
				+ ((subCategoryDescription == null) ? 0
						: subCategoryDescription.hashCode());
		result = prime * result
				+ ((subSubCategory == null) ? 0 : subSubCategory.hashCode());
		result = prime
				* result
				+ ((subSubcategoryDescription == null) ? 0
						: subSubcategoryDescription.hashCode());
		result = prime * result + ((urgency == null) ? 0 : urgency.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
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
		CustomerProblem other = (CustomerProblem) obj;
		if (agentCode == null) {
			if (other.agentCode != null)
				return false;
		} else if (!agentCode.equals(other.agentCode))
			return false;
		if (businessApplication == null) {
			if (other.businessApplication != null)
				return false;
		} else if (!businessApplication.equals(other.businessApplication))
			return false;
		if (callDuration == null) {
			if (other.callDuration != null)
				return false;
		} else if (!callDuration.equals(other.callDuration))
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (categoryDescription == null) {
			if (other.categoryDescription != null)
				return false;
		} else if (!categoryDescription.equals(other.categoryDescription))
			return false;
		if (channel == null) {
			if (other.channel != null)
				return false;
		} else if (!channel.equals(other.channel))
			return false;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (estResolutionTime == null) {
			if (other.estResolutionTime != null)
				return false;
		} else if (!estResolutionTime.equals(other.estResolutionTime))
			return false;
		if (estSLA == null) {
			if (other.estSLA != null)
				return false;
		} else if (!estSLA.equals(other.estSLA))
			return false;
		if (headerMessage == null) {
			if (other.headerMessage != null)
				return false;
		} else if (!headerMessage.equals(other.headerMessage))
			return false;
		if (impact == null) {
			if (other.impact != null)
				return false;
		} else if (!impact.equals(other.impact))
			return false;
		if (interactionDate == null) {
			if (other.interactionDate != null)
				return false;
		} else if (!interactionDate.equals(other.interactionDate))
			return false;
		if (interactionNumber == null) {
			if (other.interactionNumber != null)
				return false;
		} else if (!interactionNumber.equals(other.interactionNumber))
			return false;
		if (problemRefNo == null) {
			if (other.problemRefNo != null)
				return false;
		} else if (!problemRefNo.equals(other.problemRefNo))
			return false;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		if (productName == null) {
			if (other.productName != null)
				return false;
		} else if (!productName.equals(other.productName))
			return false;
		if (providerStatus == null) {
			if (other.providerStatus != null)
				return false;
		} else if (!providerStatus.equals(other.providerStatus))
			return false;
		if (serviceRequestId == null) {
			if (other.serviceRequestId != null)
				return false;
		} else if (!serviceRequestId.equals(other.serviceRequestId))
			return false;
		if (serviceTeam == null) {
			if (other.serviceTeam != null)
				return false;
		} else if (!serviceTeam.equals(other.serviceTeam))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (statusDesc == null) {
			if (other.statusDesc != null)
				return false;
		} else if (!statusDesc.equals(other.statusDesc))
			return false;
		if (subCategory == null) {
			if (other.subCategory != null)
				return false;
		} else if (!subCategory.equals(other.subCategory))
			return false;
		if (subCategoryDescription == null) {
			if (other.subCategoryDescription != null)
				return false;
		} else if (!subCategoryDescription.equals(other.subCategoryDescription))
			return false;
		if (subSubCategory == null) {
			if (other.subSubCategory != null)
				return false;
		} else if (!subSubCategory.equals(other.subSubCategory))
			return false;
		if (subSubcategoryDescription == null) {
			if (other.subSubcategoryDescription != null)
				return false;
		} else if (!subSubcategoryDescription
				.equals(other.subSubcategoryDescription))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner
				.equals(other.owner))
			return false;
		if (urgency == null) {
			if (other.urgency != null)
				return false;
		} else if (!urgency.equals(other.urgency))
			return false;
		return true;
	}

	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return the category4
	 */
	public String getCategory4() {
		return category4;
	}

	/**
	 * @param category4 the category4 to set
	 */
	public void setCategory4(String category4) {
		this.category4 = category4;
	}

}