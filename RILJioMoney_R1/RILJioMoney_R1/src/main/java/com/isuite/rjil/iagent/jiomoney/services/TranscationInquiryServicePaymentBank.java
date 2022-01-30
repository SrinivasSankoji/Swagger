package com.isuite.rjil.iagent.jiomoney.services;

import java.util.List;

import com.isuite.rjil.iagent.jiomoney.common.PaginationValues;
import com.isuite.rjil.iagent.jiomoney.common.TransactionInquiryPaymentBank;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;

public interface TranscationInquiryServicePaymentBank {
	
	public List<TransactionInquiryPaymentBank> getTransactionDataForPaymentBank(String fromDate,
			String toDate, String customerID, String cardNumber,String pPageIndex,String pMaxRecords,String lValues,String agentId,boolean isVerificationRequest,long requestId)
			throws DataAccessException,
			com.isuite.rjil.iagent.jiomoney.exception.ServiceException;
}
