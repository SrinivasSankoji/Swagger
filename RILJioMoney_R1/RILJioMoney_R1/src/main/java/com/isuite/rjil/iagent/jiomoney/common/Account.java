/**
 * 
 */
package com.isuite.rjil.iagent.jiomoney.common;

import java.io.Serializable;

/**
 * @author NovelVox
 * 
 */
public class Account implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3639233002966972685L;
	private String accountId;
	private String accountNumber;
	private String accountBalance;
	private String status;
	private String currency;
	private String institutionId;
	private String amountBill;
	private String amountBillCurrency;
	private String blkAmount;
	private String extCode;
	private String accountType;
	private String accountName;
	private String accountOpenDate;
	private String accountStatus;
	private String accountTermDate;
	private String branchId;
	private String branchName;
	private String ledgerBal;
	
	//Changed for showing Agg amount on UI
	private String aggAmount;

	public String getAggAmount() {
		return aggAmount;
	}

	public void setAggAmount(String aggAmount) {
		this.aggAmount = aggAmount;
	}

	public String getLedgerBal() {
		return ledgerBal;
	}

	public void setLedgerBal(String ledgerBal) {
		this.ledgerBal = ledgerBal;
	}

	private Message headerMessage;
	private BusinessApplication businessApplication;

	private Status providerStatus;

	/**
	 * @return the accountId
	 */
	public String getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId
	 *            the accountId to set
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
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
	 * @return the accountBalance
	 */
	public String getAccountBalance() {
		return accountBalance;
	}

	/**
	 * @param accountBalance
	 *            the accountBalance to set
	 */
	public void setAccountBalance(String accountBalance) {
		this.accountBalance = accountBalance;
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
	 * @return the institutionId
	 */
	public String getInstitutionId() {
		return institutionId;
	}

	/**
	 * @param institutionId
	 *            the institutionId to set
	 */
	public void setInstitutionId(String institutionId) {
		this.institutionId = institutionId;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency
	 *            the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the amountBillCurrency
	 */
	public String getAmountBillCurrency() {
		return amountBillCurrency;
	}

	/**
	 * @param amountBillCurrency
	 *            the amountBillCurrency to set
	 */
	public void setAmountBillCurrency(String amountBillCurrency) {
		this.amountBillCurrency = amountBillCurrency;
	}

	/**
	 * @return the amountBill
	 */
	public String getAmountBill() {
		return amountBill;
	}

	/**
	 * @param amountBill
	 *            the amountBill to set
	 */
	public void setAmountBill(String amountBill) {
		this.amountBill = amountBill;
	}

	/**
	 * @return the extCode
	 */
	public String getExtCode() {
		return extCode;
	}

	/**
	 * @param extCode
	 *            the extCode to set
	 */
	public void setExtCode(String extCode) {
		this.extCode = extCode;
	}

	/**
	 * @return the blkAmount
	 */
	public String getBlkAmount() {
		return blkAmount;
	}

	/**
	 * @param blkAmount
	 *            the blkAmount to set
	 */
	public void setBlkAmount(String blkAmount) {
		this.blkAmount = blkAmount;
	}

	/**
	 * @return the accountType
	 */
	public String getAccountType() {
		return accountType;
	}

	/**
	 * @param accountType
	 *            the accountType to set
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * @param accountName
	 *            the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * @return the accountOpenDate
	 */
	public String getAccountOpenDate() {
		return accountOpenDate;
	}

	/**
	 * @param accountOpenDate
	 *            the accountOpenDate to set
	 */
	public void setAccountOpenDate(String accountOpenDate) {
		this.accountOpenDate = accountOpenDate;
	}

	/**
	 * @return the accountStatus
	 */
	public String getAccountStatus() {
		return accountStatus;
	}

	/**
	 * @param accountStatus
	 *            the accountStatus to set
	 */
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	/**
	 * @return the accountTermDate
	 */
	public String getAccountTermDate() {
		return accountTermDate;
	}

	/**
	 * @param accountTermDate
	 *            the accountTermDate to set
	 */
	public void setAccountTermDate(String accountTermDate) {
		this.accountTermDate = accountTermDate;
	}

	/**
	 * @return the branchId
	 */
	public String getBranchId() {
		return branchId;
	}

	/**
	 * @param branchId
	 *            the branchId to set
	 */
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	/**
	 * @return the branchName
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * @param branchName
	 *            the branchName to set
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
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
		result = prime * result
				+ ((accountBalance == null) ? 0 : accountBalance.hashCode());
		result = prime * result
				+ ((accountId == null) ? 0 : accountId.hashCode());
		result = prime * result
				+ ((accountName == null) ? 0 : accountName.hashCode());
		result = prime * result
				+ ((accountNumber == null) ? 0 : accountNumber.hashCode());
		result = prime * result
				+ ((accountOpenDate == null) ? 0 : accountOpenDate.hashCode());
		result = prime * result
				+ ((accountStatus == null) ? 0 : accountStatus.hashCode());
		result = prime * result
				+ ((accountTermDate == null) ? 0 : accountTermDate.hashCode());
		result = prime * result
				+ ((accountType == null) ? 0 : accountType.hashCode());
		result = prime * result
				+ ((amountBill == null) ? 0 : amountBill.hashCode());
		result = prime
				* result
				+ ((amountBillCurrency == null) ? 0 : amountBillCurrency
						.hashCode());
		result = prime * result
				+ ((blkAmount == null) ? 0 : blkAmount.hashCode());
		result = prime * result
				+ ((branchId == null) ? 0 : branchId.hashCode());
		result = prime * result
				+ ((branchName == null) ? 0 : branchName.hashCode());
		result = prime
				* result
				+ ((businessApplication == null) ? 0 : businessApplication
						.hashCode());
		result = prime * result
				+ ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((extCode == null) ? 0 : extCode.hashCode());
		result = prime * result
				+ ((headerMessage == null) ? 0 : headerMessage.hashCode());
		result = prime * result
				+ ((institutionId == null) ? 0 : institutionId.hashCode());
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
		Account other = (Account) obj;
		if (accountBalance == null) {
			if (other.accountBalance != null)
				return false;
		} else if (!accountBalance.equals(other.accountBalance))
			return false;
		if (accountId == null) {
			if (other.accountId != null)
				return false;
		} else if (!accountId.equals(other.accountId))
			return false;
		if (accountName == null) {
			if (other.accountName != null)
				return false;
		} else if (!accountName.equals(other.accountName))
			return false;
		if (accountNumber == null) {
			if (other.accountNumber != null)
				return false;
		} else if (!accountNumber.equals(other.accountNumber))
			return false;
		if (accountOpenDate == null) {
			if (other.accountOpenDate != null)
				return false;
		} else if (!accountOpenDate.equals(other.accountOpenDate))
			return false;
		if (accountStatus == null) {
			if (other.accountStatus != null)
				return false;
		} else if (!accountStatus.equals(other.accountStatus))
			return false;
		if (accountTermDate == null) {
			if (other.accountTermDate != null)
				return false;
		} else if (!accountTermDate.equals(other.accountTermDate))
			return false;
		if (accountType == null) {
			if (other.accountType != null)
				return false;
		} else if (!accountType.equals(other.accountType))
			return false;
		if (amountBill == null) {
			if (other.amountBill != null)
				return false;
		} else if (!amountBill.equals(other.amountBill))
			return false;
		if (amountBillCurrency == null) {
			if (other.amountBillCurrency != null)
				return false;
		} else if (!amountBillCurrency.equals(other.amountBillCurrency))
			return false;
		if (blkAmount == null) {
			if (other.blkAmount != null)
				return false;
		} else if (!blkAmount.equals(other.blkAmount))
			return false;
		if (branchId == null) {
			if (other.branchId != null)
				return false;
		} else if (!branchId.equals(other.branchId))
			return false;
		if (branchName == null) {
			if (other.branchName != null)
				return false;
		} else if (!branchName.equals(other.branchName))
			return false;
		if (businessApplication == null) {
			if (other.businessApplication != null)
				return false;
		} else if (!businessApplication.equals(other.businessApplication))
			return false;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (extCode == null) {
			if (other.extCode != null)
				return false;
		} else if (!extCode.equals(other.extCode))
			return false;
		if (headerMessage == null) {
			if (other.headerMessage != null)
				return false;
		} else if (!headerMessage.equals(other.headerMessage))
			return false;
		if (institutionId == null) {
			if (other.institutionId != null)
				return false;
		} else if (!institutionId.equals(other.institutionId))
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
