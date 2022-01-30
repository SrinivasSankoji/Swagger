/**
 * 
 */
package com.isuite.rjil.iagent.jiomoney.common;

import java.io.Serializable;

/**
 * @author Novelvox
 * 
 */
public class Merchant implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 173643463388543499L;

	private String name;
	private String id;
	private String merchantRRN;
	private String settlementDate;
	private String settlementAccount;
	private String settlementBank;
	private String transactionAmount;
	private String commission;
	private String serviceTax;

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
	 * @return the merchantRRN
	 */
	public String getMerchantRRN() {
		return merchantRRN;
	}

	/**
	 * @param merchantRRN
	 *            the merchantRRN to set
	 */
	public void setMerchantRRN(String merchantRRN) {
		this.merchantRRN = merchantRRN;
	}

	/**
	 * @return the settlementDate
	 */
	public String getSettlementDate() {
		return settlementDate;
	}

	/**
	 * @param settlementDate
	 *            the settlementDate to set
	 */
	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}

	/**
	 * @return the settlementAccount
	 */
	public String getSettlementAccount() {
		return settlementAccount;
	}

	/**
	 * @param settlementAccount
	 *            the settlementAccount to set
	 */
	public void setSettlementAccount(String settlementAccount) {
		this.settlementAccount = settlementAccount;
	}

	/**
	 * @return the settlementBank
	 */
	public String getSettlementBank() {
		return settlementBank;
	}

	/**
	 * @param settlementBank
	 *            the settlementBank to set
	 */
	public void setSettlementBank(String settlementBank) {
		this.settlementBank = settlementBank;
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
	 * @return the commission
	 */
	public String getCommission() {
		return commission;
	}

	/**
	 * @param commission
	 *            the commission to set
	 */
	public void setCommission(String commission) {
		this.commission = commission;
	}

	/**
	 * @return the serviceTax
	 */
	public String getServiceTax() {
		return serviceTax;
	}

	/**
	 * @param serviceTax
	 *            the serviceTax to set
	 */
	public void setServiceTax(String serviceTax) {
		this.serviceTax = serviceTax;
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
				+ ((commission == null) ? 0 : commission.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((merchantRRN == null) ? 0 : merchantRRN.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((serviceTax == null) ? 0 : serviceTax.hashCode());
		result = prime
				* result
				+ ((settlementAccount == null) ? 0 : settlementAccount
						.hashCode());
		result = prime * result
				+ ((settlementBank == null) ? 0 : settlementBank.hashCode());
		result = prime * result
				+ ((settlementDate == null) ? 0 : settlementDate.hashCode());
		result = prime
				* result
				+ ((transactionAmount == null) ? 0 : transactionAmount
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
		Merchant other = (Merchant) obj;
		if (commission == null) {
			if (other.commission != null)
				return false;
		} else if (!commission.equals(other.commission))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (merchantRRN == null) {
			if (other.merchantRRN != null)
				return false;
		} else if (!merchantRRN.equals(other.merchantRRN))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (serviceTax == null) {
			if (other.serviceTax != null)
				return false;
		} else if (!serviceTax.equals(other.serviceTax))
			return false;
		if (settlementAccount == null) {
			if (other.settlementAccount != null)
				return false;
		} else if (!settlementAccount.equals(other.settlementAccount))
			return false;
		if (settlementBank == null) {
			if (other.settlementBank != null)
				return false;
		} else if (!settlementBank.equals(other.settlementBank))
			return false;
		if (settlementDate == null) {
			if (other.settlementDate != null)
				return false;
		} else if (!settlementDate.equals(other.settlementDate))
			return false;
		if (transactionAmount == null) {
			if (other.transactionAmount != null)
				return false;
		} else if (!transactionAmount.equals(other.transactionAmount))
			return false;
		return true;
	}

}
