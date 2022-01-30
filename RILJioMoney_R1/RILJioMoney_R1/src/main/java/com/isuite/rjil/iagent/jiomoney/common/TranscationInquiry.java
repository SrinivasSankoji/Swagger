package com.isuite.rjil.iagent.jiomoney.common;

import java.io.Serializable;

/**
 * 
 * @author NovelVox
 * 
 */

public class TranscationInquiry implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 973378505152067820L;
	private String customerId = "" ;
	private String cardNumber = "" ;
	private String accountNumber = "" ;
	private String instrumentType = "" ;
	private String cardType = "" ;
	private String mcc = "" ;
	private String terminalId = "" ;
	private String cardNumberSuffix = "" ;
	private String issuerImpact = "" ;
	private String issuerInstitution = "" ;
	private String issuerBank = "" ;
	private String acquirerInstitution = "" ;
	private String acquirerBank = "" ;
	private String invoiceNumber = "" ;

	private String posVerficationMethod = "" ;
	private String posReadingMethod = "" ;
	private String primaryKey = "" ;
	private String authCode = "" ;
	private String paymentReferenceNumber = "" ;
	private String cardNumberPrefix = "" ;
	private String cardScheme = "" ;
	private String retrievalReferenceNumber = "" ;
	private String pageOffset = "" ;

	private Message headerMessage;
	private Status status;
	private Transaction transaction;
	private Merchant merchant;
	private BusinessApplication businessApplication;
	private String transactionRecipient = "" ;
	private String transactionDateTime = "" ;
	private String transactionType = "" ;
	private String mstrTransactionDesc = "" ;
	private String mstrTransactionDescShort = "" ;
	private String mstrTransactioncategory = "";
	private String transactionId = "" ;
	private String transactionChannel = "" ;
	private String transactionAmount = "" ;
	private String transactionStatus = "" ;
	private String transactionDisposition = "" ;
	private String transactionNote = "" ;
	private String transactionDate = "" ;
	private String transactionCount = "" ;

	public String getTransactionCategory() {
		return mstrTransactioncategory;
	}

	public void setTransactionCategory(String mstrTransactioncategory) {
		this.mstrTransactioncategory = mstrTransactioncategory;
	}

	public String getTransactionRecipient() {
		return transactionRecipient;
	}

	public void setTransactionRecipient(String transactionRecipient) {
		this.transactionRecipient = transactionRecipient;
	}

	public String getTransactionDateTime() {
		return transactionDateTime;
	}

	public void setTransactionDateTime(String transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionChannel() {
		return transactionChannel;
	}

	public void setTransactionChannel(String transactionChannel) {
		this.transactionChannel = transactionChannel;
	}

	public String getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public String getTransactionDisposition() {
		return transactionDisposition;
	}

	public void setTransactionDisposition(String transactionDisposition) {
		this.transactionDisposition = transactionDisposition;
	}

	public String getTransactionNote() {
		return transactionNote;
	}

	public void setTransactionNote(String transactionNote) {
		this.transactionNote = transactionNote;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionCount() {
		return transactionCount;
	}

	public void setTransactionCount(String transactionCount) {
		this.transactionCount = transactionCount;
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
	 * @return the cardNumber
	 */
	public String getCardNumber() {
		return cardNumber;
	}

	/**
	 * @param cardNumber
	 *            the cardNumber to set
	 */
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param accountNumber
	 *            the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the instrumentType
	 */
	public String getInstrumentType() {
		return instrumentType;
	}

	/**
	 * @param instrumentType
	 *            the instrumentType to set
	 */
	public void setInstrumentType(String instrumentType) {
		this.instrumentType = instrumentType;
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
	 * @return the mcc
	 */
	public String getMcc() {
		return mcc;
	}

	/**
	 * @param mcc
	 *            the mcc to set
	 */
	public void setMcc(String mcc) {
		this.mcc = mcc;
	}

	/**
	 * @return the terminalId
	 */
	public String getTerminalId() {
		return terminalId;
	}

	/**
	 * @param terminalId
	 *            the terminalId to set
	 */
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	/**
	 * @return the cardNumberSuffix
	 */
	public String getCardNumberSuffix() {
		return cardNumberSuffix;
	}

	/**
	 * @param cardNumberSuffix
	 *            the cardNumberSuffix to set
	 */
	public void setCardNumberSuffix(String cardNumberSuffix) {
		this.cardNumberSuffix = cardNumberSuffix;
	}

	/**
	 * @return the issuerImpact
	 */
	public String getIssuerImpact() {
		return issuerImpact;
	}

	/**
	 * @param issuerImpact
	 *            the issuerImpact to set
	 */
	public void setIssuerImpact(String issuerImpact) {
		this.issuerImpact = issuerImpact;
	}

	/**
	 * @return the issuerInstitution
	 */
	public String getIssuerInstitution() {
		return issuerInstitution;
	}

	/**
	 * @param issuerInstitution
	 *            the issuerInstitution to set
	 */
	public void setIssuerInstitution(String issuerInstitution) {
		this.issuerInstitution = issuerInstitution;
	}

	/**
	 * @return the issuerBank
	 */
	public String getIssuerBank() {
		return issuerBank;
	}

	/**
	 * @param issuerBank
	 *            the issuerBank to set
	 */
	public void setIssuerBank(String issuerBank) {
		this.issuerBank = issuerBank;
	}

	/**
	 * @return the acquirerInstitution
	 */
	public String getAcquirerInstitution() {
		return acquirerInstitution;
	}

	/**
	 * @param acquirerInstitution
	 *            the acquirerInstitution to set
	 */
	public void setAcquirerInstitution(String acquirerInstitution) {
		this.acquirerInstitution = acquirerInstitution;
	}

	/**
	 * @return the acquirerBank
	 */
	public String getAcquirerBank() {
		return acquirerBank;
	}

	/**
	 * @param acquirerBank
	 *            the acquirerBank to set
	 */
	public void setAcquirerBank(String acquirerBank) {
		this.acquirerBank = acquirerBank;
	}

	/**
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	/**
	 * @param invoiceNumber
	 *            the invoiceNumber to set
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	/**
	 * @return the posVerficationMethod
	 */
	public String getPosVerficationMethod() {
		return posVerficationMethod;
	}

	/**
	 * @param posVerficationMethod
	 *            the posVerficationMethod to set
	 */
	public void setPosVerficationMethod(String posVerficationMethod) {
		this.posVerficationMethod = posVerficationMethod;
	}

	/**
	 * @return the posReadingMethod
	 */
	public String getPosReadingMethod() {
		return posReadingMethod;
	}

	/**
	 * @param posReadingMethod
	 *            the posReadingMethod to set
	 */
	public void setPosReadingMethod(String posReadingMethod) {
		this.posReadingMethod = posReadingMethod;
	}

	/**
	 * @return the primaryKey
	 */
	public String getPrimaryKey() {
		return primaryKey;
	}

	/**
	 * @param primaryKey
	 *            the primaryKey to set
	 */
	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	/**
	 * @return the authCode
	 */
	public String getAuthCode() {
		return authCode;
	}

	/**
	 * @param authCode
	 *            the authCode to set
	 */
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	/**
	 * @return the paymentReferenceNumber
	 */
	public String getPaymentReferenceNumber() {
		return paymentReferenceNumber;
	}

	/**
	 * @param paymentReferenceNumber
	 *            the paymentReferenceNumber to set
	 */
	public void setPaymentReferenceNumber(String paymentReferenceNumber) {
		this.paymentReferenceNumber = paymentReferenceNumber;
	}

	/**
	 * @return the cardNumberPrefix
	 */
	public String getCardNumberPrefix() {
		return cardNumberPrefix;
	}

	/**
	 * @param cardNumberPrefix
	 *            the cardNumberPrefix to set
	 */
	public void setCardNumberPrefix(String cardNumberPrefix) {
		this.cardNumberPrefix = cardNumberPrefix;
	}

	/**
	 * @return the cardScheme
	 */
	public String getCardScheme() {
		return cardScheme;
	}

	/**
	 * @param cardScheme
	 *            the cardScheme to set
	 */
	public void setCardScheme(String cardScheme) {
		this.cardScheme = cardScheme;
	}

	/**
	 * @return the retrievalReferenceNumber
	 */
	public String getRetrievalReferenceNumber() {
		return retrievalReferenceNumber;
	}

	/**
	 * @param retrievalReferenceNumber
	 *            the retrievalReferenceNumber to set
	 */
	public void setRetrievalReferenceNumber(String retrievalReferenceNumber) {
		this.retrievalReferenceNumber = retrievalReferenceNumber;
	}

	/**
	 * @return the pageOffset
	 */
	public String getPageOffset() {
		return pageOffset;
	}

	/**
	 * @param pageOffset
	 *            the pageOffset to set
	 */
	public void setPageOffset(String pageOffset) {
		this.pageOffset = pageOffset;
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
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @return the transaction
	 */
	public Transaction getTransaction() {
		return transaction;
	}

	/**
	 * @param transaction
	 *            the transaction to set
	 */
	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	/**
	 * @return the merchant
	 */
	public Merchant getMerchant() {
		return merchant;
	}

	/**
	 * @param merchant
	 *            the merchant to set
	 */
	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
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
				+ ((accountNumber == null) ? 0 : accountNumber.hashCode());
		result = prime * result
				+ ((acquirerBank == null) ? 0 : acquirerBank.hashCode());
		result = prime
				* result
				+ ((acquirerInstitution == null) ? 0 : acquirerInstitution
						.hashCode());
		result = prime * result
				+ ((authCode == null) ? 0 : authCode.hashCode());
		result = prime
				* result
				+ ((businessApplication == null) ? 0 : businessApplication
						.hashCode());
		result = prime * result
				+ ((cardNumber == null) ? 0 : cardNumber.hashCode());
		result = prime
				* result
				+ ((cardNumberPrefix == null) ? 0 : cardNumberPrefix.hashCode());
		result = prime
				* result
				+ ((cardNumberSuffix == null) ? 0 : cardNumberSuffix.hashCode());
		result = prime * result
				+ ((cardScheme == null) ? 0 : cardScheme.hashCode());
		result = prime * result
				+ ((cardType == null) ? 0 : cardType.hashCode());
		result = prime * result
				+ ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result
				+ ((headerMessage == null) ? 0 : headerMessage.hashCode());
		result = prime * result
				+ ((instrumentType == null) ? 0 : instrumentType.hashCode());
		result = prime * result
				+ ((invoiceNumber == null) ? 0 : invoiceNumber.hashCode());
		result = prime * result
				+ ((issuerBank == null) ? 0 : issuerBank.hashCode());
		result = prime * result
				+ ((issuerImpact == null) ? 0 : issuerImpact.hashCode());
		result = prime
				* result
				+ ((issuerInstitution == null) ? 0 : issuerInstitution
						.hashCode());
		result = prime * result + ((mcc == null) ? 0 : mcc.hashCode());
		result = prime * result
				+ ((merchant == null) ? 0 : merchant.hashCode());
		result = prime * result
				+ ((pageOffset == null) ? 0 : pageOffset.hashCode());
		result = prime
				* result
				+ ((paymentReferenceNumber == null) ? 0
						: paymentReferenceNumber.hashCode());
		result = prime
				* result
				+ ((posReadingMethod == null) ? 0 : posReadingMethod.hashCode());
		result = prime
				* result
				+ ((posVerficationMethod == null) ? 0 : posVerficationMethod
						.hashCode());
		result = prime * result
				+ ((primaryKey == null) ? 0 : primaryKey.hashCode());
		result = prime
				* result
				+ ((retrievalReferenceNumber == null) ? 0
						: retrievalReferenceNumber.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((terminalId == null) ? 0 : terminalId.hashCode());
		result = prime * result
				+ ((transaction == null) ? 0 : transaction.hashCode());
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

	public String getTransactionDesc() {
		return mstrTransactionDesc;
	}

	public void setTransactionDesc(String mstrTransactionDesc) {
		this.mstrTransactionDesc = mstrTransactionDesc;
	}

	public String getTransactionDescShort() {
		return mstrTransactionDescShort;
	}

	public void setTransactionDescShort(String mstrTransactionDescShort) {
		this.mstrTransactionDescShort = mstrTransactionDescShort;
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
		TranscationInquiry other = (TranscationInquiry) obj;
		if (accountNumber == null) {
			if (other.accountNumber != null)
				return false;
		} else if (!accountNumber.equals(other.accountNumber))
			return false;
		if (acquirerBank == null) {
			if (other.acquirerBank != null)
				return false;
		} else if (!acquirerBank.equals(other.acquirerBank))
			return false;
		if (acquirerInstitution == null) {
			if (other.acquirerInstitution != null)
				return false;
		} else if (!acquirerInstitution.equals(other.acquirerInstitution))
			return false;
		if (authCode == null) {
			if (other.authCode != null)
				return false;
		} else if (!authCode.equals(other.authCode))
			return false;
		if (businessApplication == null) {
			if (other.businessApplication != null)
				return false;
		} else if (!businessApplication.equals(other.businessApplication))
			return false;
		if (cardNumber == null) {
			if (other.cardNumber != null)
				return false;
		} else if (!cardNumber.equals(other.cardNumber))
			return false;
		if (cardNumberPrefix == null) {
			if (other.cardNumberPrefix != null)
				return false;
		} else if (!cardNumberPrefix.equals(other.cardNumberPrefix))
			return false;
		if (cardNumberSuffix == null) {
			if (other.cardNumberSuffix != null)
				return false;
		} else if (!cardNumberSuffix.equals(other.cardNumberSuffix))
			return false;
		if (cardScheme == null) {
			if (other.cardScheme != null)
				return false;
		} else if (!cardScheme.equals(other.cardScheme))
			return false;
		if (cardType == null) {
			if (other.cardType != null)
				return false;
		} else if (!cardType.equals(other.cardType))
			return false;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		if (headerMessage == null) {
			if (other.headerMessage != null)
				return false;
		} else if (!headerMessage.equals(other.headerMessage))
			return false;
		if (instrumentType == null) {
			if (other.instrumentType != null)
				return false;
		} else if (!instrumentType.equals(other.instrumentType))
			return false;
		if (invoiceNumber == null) {
			if (other.invoiceNumber != null)
				return false;
		} else if (!invoiceNumber.equals(other.invoiceNumber))
			return false;
		if (issuerBank == null) {
			if (other.issuerBank != null)
				return false;
		} else if (!issuerBank.equals(other.issuerBank))
			return false;
		if (issuerImpact == null) {
			if (other.issuerImpact != null)
				return false;
		} else if (!issuerImpact.equals(other.issuerImpact))
			return false;
		if (issuerInstitution == null) {
			if (other.issuerInstitution != null)
				return false;
		} else if (!issuerInstitution.equals(other.issuerInstitution))
			return false;
		if (mcc == null) {
			if (other.mcc != null)
				return false;
		} else if (!mcc.equals(other.mcc))
			return false;
		if (merchant == null) {
			if (other.merchant != null)
				return false;
		} else if (!merchant.equals(other.merchant))
			return false;
		if (pageOffset == null) {
			if (other.pageOffset != null)
				return false;
		} else if (!pageOffset.equals(other.pageOffset))
			return false;
		if (paymentReferenceNumber == null) {
			if (other.paymentReferenceNumber != null)
				return false;
		} else if (!paymentReferenceNumber.equals(other.paymentReferenceNumber))
			return false;
		if (posReadingMethod == null) {
			if (other.posReadingMethod != null)
				return false;
		} else if (!posReadingMethod.equals(other.posReadingMethod))
			return false;
		if (posVerficationMethod == null) {
			if (other.posVerficationMethod != null)
				return false;
		} else if (!posVerficationMethod.equals(other.posVerficationMethod))
			return false;
		if (primaryKey == null) {
			if (other.primaryKey != null)
				return false;
		} else if (!primaryKey.equals(other.primaryKey))
			return false;
		if (retrievalReferenceNumber == null) {
			if (other.retrievalReferenceNumber != null)
				return false;
		} else if (!retrievalReferenceNumber
				.equals(other.retrievalReferenceNumber))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (terminalId == null) {
			if (other.terminalId != null)
				return false;
		} else if (!terminalId.equals(other.terminalId))
			return false;
		if (transaction == null) {
			if (other.transaction != null)
				return false;
		} else if (!transaction.equals(other.transaction))
			return false;
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
