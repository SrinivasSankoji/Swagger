/**
 * 
 */
package com.isuite.rjil.iagent.jiomoney.common;

import java.io.Serializable;
import java.util.List;

/**
 * @author NovelVox
 * 
 */
public class CustomerInteraction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2446654413409968684L;

	private String interationType;
	private String totalNoRecords;
	private String interationTypeDesc;
	private String interactionIdentifier;
	private String interactionDate;
	private String status;
	private String statusDesc;
	private String category;
	private String categoryDescription;
	private String subCategory;
	private String subCategoryDescription;
	private String subSubCategory;
	private String subSubcategoryDescription;
	private String channel;
	private String agentCode;
	private String interactionRefNo;
	private String interactionType;
	private String interactionTypeDesc;
	private String customerId;
	private String notes;
	private String estResolutionTime;
	private String mstrCommunicationMode = "";

	private List<VerificationQuestion> verificationQuestion;

	private Status providerStatus;
	private Message headerMessage;
	private BusinessApplication businessApplication;
	private String category4;
	
	public String getCommunicationMode() {
		return mstrCommunicationMode;
	}

	public void setCommunicationMode(String pCommunicationMode) {
		mstrCommunicationMode = pCommunicationMode;
	}

	/**
	 * @return the interationType
	 */
	public String getInterationType() {
		return interationType;
	}

	/**
	 * @param interationType
	 *            the interationType to set
	 */
	public void setInterationType(String interationType) {
		this.interationType = interationType;
	}

	/**
	 * @return the totalNoRecords
	 */
	public String getTotalNoRecords() {
		return totalNoRecords;
	}

	/**
	 * @param totalNoRecords
	 *            the totalNoRecords to set
	 */
	public void setTotalNoRecords(String totalNoRecords) {
		this.totalNoRecords = totalNoRecords;
	}

	/**
	 * @return the interationTypeDesc
	 */
	public String getInterationTypeDesc() {
		return interationTypeDesc;
	}

	/**
	 * @param interationTypeDesc
	 *            the interationTypeDesc to set
	 */
	public void setInterationTypeDesc(String interationTypeDesc) {
		this.interationTypeDesc = interationTypeDesc;
	}

	/**
	 * @return the interactionIdentifier
	 */
	public String getInteractionIdentifier() {
		return interactionIdentifier;
	}

	/**
	 * @param interactionIdentifier
	 *            the interactionIdentifier to set
	 */
	public void setInteractionIdentifier(String interactionIdentifier) {
		this.interactionIdentifier = interactionIdentifier;
	}

	/**
	 * @return the interactionDate
	 */
	public String getInteractionDate() {
		return interactionDate;
	}

	/**
	 * @param interactionDate
	 *            the interactionDate to set
	 */
	public void setInteractionDate(String interactionDate) {
		this.interactionDate = interactionDate;
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

	/**
	 * @return the interactionRefNo
	 */
	public String getInteractionRefNo() {
		return interactionRefNo;
	}

	/**
	 * @param interactionRefNo
	 *            the interactionRefNo to set
	 */
	public void setInteractionRefNo(String interactionRefNo) {
		this.interactionRefNo = interactionRefNo;
	}

	/**
	 * @return the interactionType
	 */
	public String getInteractionType() {
		return interactionType;
	}

	/**
	 * @param interactionType
	 *            the interactionType to set
	 */
	public void setInteractionType(String interactionType) {
		this.interactionType = interactionType;
	}

	/**
	 * @return the interactionTypeDesc
	 */
	public String getInteractionTypeDesc() {
		return interactionTypeDesc;
	}

	/**
	 * @param interactionTypeDesc
	 *            the interactionTypeDesc to set
	 */
	public void setInteractionTypeDesc(String interactionTypeDesc) {
		this.interactionTypeDesc = interactionTypeDesc;
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

	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes
	 *            the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
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

	public List<VerificationQuestion> getVerificationQuestion() {
		return verificationQuestion;
	}

	public void setVerificationQuestion(
			List<VerificationQuestion> verificationQuestion) {
		this.verificationQuestion = verificationQuestion;
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
				+ ((category == null) ? 0 : category.hashCode());
		result = prime
				* result
				+ ((categoryDescription == null) ? 0 : categoryDescription
						.hashCode());
		result = prime * result + ((channel == null) ? 0 : channel.hashCode());
		result = prime * result
				+ ((customerId == null) ? 0 : customerId.hashCode());
		result = prime
				* result
				+ ((estResolutionTime == null) ? 0 : estResolutionTime
						.hashCode());
		result = prime * result
				+ ((headerMessage == null) ? 0 : headerMessage.hashCode());
		result = prime * result
				+ ((interactionDate == null) ? 0 : interactionDate.hashCode());
		result = prime
				* result
				+ ((interactionIdentifier == null) ? 0 : interactionIdentifier
						.hashCode());
		result = prime
				* result
				+ ((interactionRefNo == null) ? 0 : interactionRefNo.hashCode());
		result = prime * result
				+ ((interactionType == null) ? 0 : interactionType.hashCode());
		result = prime
				* result
				+ ((interactionTypeDesc == null) ? 0 : interactionTypeDesc
						.hashCode());
		result = prime * result
				+ ((interationType == null) ? 0 : interationType.hashCode());
		result = prime
				* result
				+ ((interationTypeDesc == null) ? 0 : interationTypeDesc
						.hashCode());
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		result = prime * result
				+ ((providerStatus == null) ? 0 : providerStatus.hashCode());
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
		result = prime * result
				+ ((totalNoRecords == null) ? 0 : totalNoRecords.hashCode());
		result = prime
				* result
				+ ((verificationQuestion == null) ? 0 : verificationQuestion
						.hashCode());
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
		CustomerInteraction other = (CustomerInteraction) obj;
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
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		if (estResolutionTime == null) {
			if (other.estResolutionTime != null)
				return false;
		} else if (!estResolutionTime.equals(other.estResolutionTime))
			return false;
		if (headerMessage == null) {
			if (other.headerMessage != null)
				return false;
		} else if (!headerMessage.equals(other.headerMessage))
			return false;
		if (interactionDate == null) {
			if (other.interactionDate != null)
				return false;
		} else if (!interactionDate.equals(other.interactionDate))
			return false;
		if (interactionIdentifier == null) {
			if (other.interactionIdentifier != null)
				return false;
		} else if (!interactionIdentifier.equals(other.interactionIdentifier))
			return false;
		if (interactionRefNo == null) {
			if (other.interactionRefNo != null)
				return false;
		} else if (!interactionRefNo.equals(other.interactionRefNo))
			return false;
		if (interactionType == null) {
			if (other.interactionType != null)
				return false;
		} else if (!interactionType.equals(other.interactionType))
			return false;
		if (interactionTypeDesc == null) {
			if (other.interactionTypeDesc != null)
				return false;
		} else if (!interactionTypeDesc.equals(other.interactionTypeDesc))
			return false;
		if (interationType == null) {
			if (other.interationType != null)
				return false;
		} else if (!interationType.equals(other.interationType))
			return false;
		if (interationTypeDesc == null) {
			if (other.interationTypeDesc != null)
				return false;
		} else if (!interationTypeDesc.equals(other.interationTypeDesc))
			return false;
		if (notes == null) {
			if (other.notes != null)
				return false;
		} else if (!notes.equals(other.notes))
			return false;
		if (providerStatus == null) {
			if (other.providerStatus != null)
				return false;
		} else if (!providerStatus.equals(other.providerStatus))
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
		if (totalNoRecords == null) {
			if (other.totalNoRecords != null)
				return false;
		} else if (!totalNoRecords.equals(other.totalNoRecords))
			return false;
		if (verificationQuestion == null) {
			if (other.verificationQuestion != null)
				return false;
		} else if (!verificationQuestion.equals(other.verificationQuestion))
			return false;
		return true;
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
