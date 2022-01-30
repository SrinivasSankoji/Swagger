package com.isuite.rjil.iagent.jiomoney.services.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.common.PaginationValues;
import com.isuite.rjil.iagent.jiomoney.common.TransactionInquiryPaymentBank;
import com.isuite.rjil.iagent.jiomoney.common.TranscationInquiry;
import com.isuite.rjil.iagent.jiomoney.dao.DaoLocator;
import com.isuite.rjil.iagent.jiomoney.dao.TranscationInquiryJioPaymentBankDao;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.services.TranscationInquiryServicePaymentBank;

public class TranscationInquiryPaymentBankServiceImpl implements TranscationInquiryServicePaymentBank{
	private static final Logger logger = Logger
			.getLogger(TranscationInquiryPaymentBankServiceImpl.class);
	private static final String dao = "TranscationInquiryJioPaymentBankDao";
	
	public List<TransactionInquiryPaymentBank> getTransactionDataForPaymentBank(String fromDate,
			String toDate, String customerID, String cardNumber,String pPageIndex,String pMaxRecords,String lValues,String agentId,boolean isVerificationRequest,long requestId)
			throws DataAccessException {
		List<TransactionInquiryPaymentBank> getPaymentBankTransactionData = null;
		
		try {

			getPaymentBankTransactionData = ((TranscationInquiryJioPaymentBankDao) DaoLocator
					.getDao(dao)).getTransactionDataForPaymentBank(fromDate, toDate,
					customerID, cardNumber, pPageIndex, pMaxRecords,lValues, agentId, isVerificationRequest,requestId);

		} catch (DataAccessException e) {
			logger.error("Exception catched while doing getTranscationData ", e);
			throw e;
		}
		
		return getPaymentBankTransactionData;
	}

}
