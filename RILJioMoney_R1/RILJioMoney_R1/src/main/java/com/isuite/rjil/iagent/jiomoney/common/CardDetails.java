package com.isuite.rjil.iagent.jiomoney.common;

import java.io.Serializable;

/**
 * 
 * @author Novelvox
 * 
 */
public class CardDetails implements Serializable {

	private static final long serialVersionUID = -6086326547133936666L;

	private String alias;
	private String id;
	private String embossName;
	private String cardNo;
	private String expMM;
	private String expYY;
	// private String typeValue;
	private String category;
	private String bankName;
	private String nickName;
	private String expieryDate;
	private String customerId;
	private String cardCategory;
	private String cardType;

	private String cardKeyRef;
	private String cardKeyType;
	private String status;
	private Status providerStatus;

	private Bank bank;
	private Message headerMessage;
	private BusinessApplication businessApplication;

	/**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @param alias
	 *            the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the embossName
	 */
	public String getEmbossName() {
		return embossName;
	}

	/**
	 * @param embossName
	 *            the embossName to set
	 */
	public void setEmbossName(String embossName) {
		this.embossName = embossName;
	}

	/**
	 * @return the cardNo
	 */
	public String getCardNo() {
		return cardNo;
	}

	/**
	 * @param cardNo
	 *            the cardNo to set
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	/**
	 * @return the expMM
	 */
	public String getExpMM() {
		return expMM;
	}

	/**
	 * @param expMM
	 *            the expMM to set
	 */
	public void setExpMM(String expMM) {
		this.expMM = expMM;
	}

	/**
	 * @return the expYY
	 */
	public String getExpYY() {
		return expYY;
	}

	/**
	 * @param expYY
	 *            the expYY to set
	 */
	public void setExpYY(String expYY) {
		this.expYY = expYY;
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
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName
	 *            the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * @param nickName
	 *            the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * @return the expieryDate
	 */
	public String getExpieryDate() {
		return expieryDate;
	}

	/**
	 * @param expieryDate
	 *            the expieryDate to set
	 */
	public void setExpieryDate(String expieryDate) {
		this.expieryDate = expieryDate;
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
	 * @return the cardCategory
	 */
	public String getCardCategory() {
		return cardCategory;
	}

	/**
	 * @param cardCategory
	 *            the cardCategory to set
	 */
	public void setCardCategory(String cardCategory) {
		this.cardCategory = cardCategory;
	}

	/**
	 * @return the cardType
	 */
	public String getCardType() {
		return cardType;
	}

	/**
	 * @param cardType
	 *            the cardType to set
	 */
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	/**
	 * @return the cardKeyRef
	 */
	public String getCardKeyRef() {
		return cardKeyRef;
	}

	/**
	 * @param cardKeyRef
	 *            the cardKeyRef to set
	 */
	public void setCardKeyRef(String cardKeyRef) {
		this.cardKeyRef = cardKeyRef;
	}

	/**
	 * @return the cardKeyType
	 */
	public String getCardKeyType() {
		return cardKeyType;
	}

	/**
	 * @param cardKeyType
	 *            the cardKeyType to set
	 */
	public void setCardKeyType(String cardKeyType) {
		this.cardKeyType = cardKeyType;
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
	 * @return the bank
	 */
	public Bank getBank() {
		return bank;
	}

	/**
	 * @param bank
	 *            the bank to set
	 */
	public void setBank(Bank bank) {
		this.bank = bank;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alias == null) ? 0 : alias.hashCode());
		result = prime * result + ((bank == null) ? 0 : bank.hashCode());
		result = prime * result
				+ ((bankName == null) ? 0 : bankName.hashCode());
		result = prime
				* result
				+ ((businessApplication == null) ? 0 : businessApplication
						.hashCode());
		result = prime * result
				+ ((cardCategory == null) ? 0 : cardCategory.hashCode());
		result = prime * result
				+ ((cardKeyRef == null) ? 0 : cardKeyRef.hashCode());
		result = prime * result
				+ ((cardKeyType == null) ? 0 : cardKeyType.hashCode());
		result = prime * result + ((cardNo == null) ? 0 : cardNo.hashCode());
		result = prime * result
				+ ((cardType == null) ? 0 : cardType.hashCode());
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result
				+ ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result
				+ ((embossName == null) ? 0 : embossName.hashCode());
		result = prime * result + ((expMM == null) ? 0 : expMM.hashCode());
		result = prime * result + ((expYY == null) ? 0 : expYY.hashCode());
		result = prime * result
				+ ((expieryDate == null) ? 0 : expieryDate.hashCode());
		result = prime * result
				+ ((headerMessage == null) ? 0 : headerMessage.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((nickName == null) ? 0 : nickName.hashCode());
		result = prime * result
				+ ((providerStatus == null) ? 0 : providerStatus.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		CardDetails other = (CardDetails) obj;
		if (alias == null) {
			if (other.alias != null)
				return false;
		} else if (!alias.equals(other.alias))
			return false;
		if (bank == null) {
			if (other.bank != null)
				return false;
		} else if (!bank.equals(other.bank))
			return false;
		if (bankName == null) {
			if (other.bankName != null)
				return false;
		} else if (!bankName.equals(other.bankName))
			return false;
		if (businessApplication == null) {
			if (other.businessApplication != null)
				return false;
		} else if (!businessApplication.equals(other.businessApplication))
			return false;
		if (cardCategory == null) {
			if (other.cardCategory != null)
				return false;
		} else if (!cardCategory.equals(other.cardCategory))
			return false;
		if (cardKeyRef == null) {
			if (other.cardKeyRef != null)
				return false;
		} else if (!cardKeyRef.equals(other.cardKeyRef))
			return false;
		if (cardKeyType == null) {
			if (other.cardKeyType != null)
				return false;
		} else if (!cardKeyType.equals(other.cardKeyType))
			return false;
		if (cardNo == null) {
			if (other.cardNo != null)
				return false;
		} else if (!cardNo.equals(other.cardNo))
			return false;
		if (cardType == null) {
			if (other.cardType != null)
				return false;
		} else if (!cardType.equals(other.cardType))
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		if (embossName == null) {
			if (other.embossName != null)
				return false;
		} else if (!embossName.equals(other.embossName))
			return false;
		if (expMM == null) {
			if (other.expMM != null)
				return false;
		} else if (!expMM.equals(other.expMM))
			return false;
		if (expYY == null) {
			if (other.expYY != null)
				return false;
		} else if (!expYY.equals(other.expYY))
			return false;
		if (expieryDate == null) {
			if (other.expieryDate != null)
				return false;
		} else if (!expieryDate.equals(other.expieryDate))
			return false;
		if (headerMessage == null) {
			if (other.headerMessage != null)
				return false;
		} else if (!headerMessage.equals(other.headerMessage))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nickName == null) {
			if (other.nickName != null)
				return false;
		} else if (!nickName.equals(other.nickName))
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
		return true;
	}

}
