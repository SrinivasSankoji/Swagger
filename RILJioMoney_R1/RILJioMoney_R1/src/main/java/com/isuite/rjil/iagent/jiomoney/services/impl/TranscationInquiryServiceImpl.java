package com.isuite.rjil.iagent.jiomoney.services.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.common.PaginationValues;
import com.isuite.rjil.iagent.jiomoney.common.TranscationInquiry;
import com.isuite.rjil.iagent.jiomoney.dao.DaoLocator;
import com.isuite.rjil.iagent.jiomoney.dao.TranscationInquiryDao;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.services.TranscationInquiryService;

public class TranscationInquiryServiceImpl implements TranscationInquiryService {
	private static final Logger logger = Logger
			.getLogger(TranscationInquiryServiceImpl.class);

	private static final String dao = "TranscationInquiryDao";

	@Override
	public List<TranscationInquiry> getTransactionData(String fromDate,
			String toDate, String customerID, String cardNumber,String pPageIndex,String pMaxRecords,PaginationValues lValues,String agentId,boolean isVerificationRequest,long requestId)
			throws DataAccessException {
		List<TranscationInquiry> getTransactionData = null;
		try {

			// terminalIdoperator is Customer Id
			// if (merchantIdoperator == null || merchantIdoperator.isEmpty() ||
			// terminalIdoperator == null
			// || terminalIdoperator.isEmpty()) {
			//
			// throw new
			// ServiceException("Mandatory Parameter require for Customer Id ");
			// }
			

			getTransactionData = ((TranscationInquiryDao) DaoLocator
					.getDao(dao)).getTransactionData(fromDate, toDate,
					customerID, cardNumber, pPageIndex, pMaxRecords,lValues, agentId, isVerificationRequest,requestId);

		} catch (DataAccessException e) {
			logger.error("Exception catched while doing getTranscationData ", e);
			throw e;
		}
		return getTransactionData;
	}

	@Override
	public List<TranscationInquiry> getTransactionSummary(String startDateTime,
			String endDateTime, String merchantIdoperator,
			String terminalIdoperator,String agentId,long requestId) throws DataAccessException {
		List<TranscationInquiry> getTransactionSummary = null;
		try {
			// terminalIdoperator is Customer Id
			// if (merchantIdoperator == null || merchantIdoperator.isEmpty() ||
			// terminalIdoperator == null
			// || terminalIdoperator.isEmpty()) {
			//
			// throw new
			// ServiceException("Mandatory Parameter require for Customer Id ");
			// }
			getTransactionSummary = ((TranscationInquiryDao) DaoLocator
					.getDao(dao)).getTransactionSummary(startDateTime,
					endDateTime, merchantIdoperator, terminalIdoperator,agentId,requestId);

		} catch (DataAccessException e) {
			logger.error(
					"Exception catched while doing getTranscationSummary ", e);
			throw e;
		}
		return getTransactionSummary;
	}

}
