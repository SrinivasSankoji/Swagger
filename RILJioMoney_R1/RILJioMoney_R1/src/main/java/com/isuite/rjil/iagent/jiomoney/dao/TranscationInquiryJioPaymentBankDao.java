package com.isuite.rjil.iagent.jiomoney.dao;

import java.util.List;

import com.isuite.rjil.iagent.jiomoney.common.PaginationValues;
import com.isuite.rjil.iagent.jiomoney.common.TransactionInquiryPaymentBank;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;

public interface TranscationInquiryJioPaymentBankDao {
	public List<TransactionInquiryPaymentBank> getTransactionDataForPaymentBank(String fromDate,
			String toDate, String customerID, String cardNumber,String pPageIndex,String pMaxRecords,String lValues,String agentId,boolean isVerificationRequest,long requestId)
			throws DataAccessException;

}
