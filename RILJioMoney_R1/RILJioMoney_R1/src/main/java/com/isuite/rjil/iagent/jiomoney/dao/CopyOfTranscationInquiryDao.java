package com.isuite.rjil.iagent.jiomoney.dao;

import java.util.List;

import com.isuite.rjil.iagent.jiomoney.common.PaginationValues;
import com.isuite.rjil.iagent.jiomoney.common.TranscationInquiry;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;

/**
 * 
 * @author NovelVox
 * 
 */
public interface CopyOfTranscationInquiryDao {

	/**
	 * 
	 * @param fromDate
	 * @param toDate
	 * @param customerID
	 * @param cardNumber
	 * @return
	 * @throws DataAccessException
	 */
	public List<TranscationInquiry> getTransactionData(String fromDate,
			String toDate, String customerID, String cardNumber,String pPageIndex,String pMaxRecords,PaginationValues lValues,String agentId,long requestId)
			throws DataAccessException;

	/**
	 * 
	 * @param startDateTime
	 * @param endDateTime
	 * @param merchantIdoperator
	 * @param terminalIdoperator
	 * @return
	 * @throws DataAccessException
	 */
	public List<TranscationInquiry> getTransactionSummary(String startDateTime,
			String endDateTime, String merchantIdoperator,
			String terminalIdoperator,String agentId,long requestId) throws DataAccessException;

}
