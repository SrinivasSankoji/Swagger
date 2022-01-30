package com.isuite.rjil.iagent.jiomoney.services;

import java.util.List;

import com.isuite.rjil.iagent.jiomoney.common.PaginationValues;
import com.isuite.rjil.iagent.jiomoney.common.TranscationInquiry;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.exception.ServiceException;

public interface CopyOfTranscationInquiryService {

	/**
	 * 
	 * @param startDateTime
	 * @param endDateTime
	 * @param merchantIdoperator
	 * @param terminalIdoperator
	 * @return
	 * @throws DataAccessException
	 * @throws com.isuite.rjil.iagent.jiomoney.exception.ServiceException
	 */
	public List<TranscationInquiry> getTransactionData(String fromDate,
			String toDate, String customerID, String cardNumber,String pPageIndex,String pMaxRecords,PaginationValues lValues,String agentId,long requestId)
			throws DataAccessException,
			com.isuite.rjil.iagent.jiomoney.exception.ServiceException;

	/**
	 * 
	 * @param startDateTime
	 * @param endDateTime
	 * @param merchantIdoperator
	 * @param terminalIdoperator
	 * @return
	 * @throws DataAccessException
	 * @throws ServiceException
	 */
	public List<TranscationInquiry> getTransactionSummary(String startDateTime,
			String endDateTime, String merchantIdoperator,
			String terminalIdoperator,String agentId,long requestId) throws DataAccessException,
			ServiceException;

}
