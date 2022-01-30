package com.isuite.rjil.iagent.jiomoney.services;

import com.isuite.rjil.iagent.jiomoney.common.CardDetails;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.exception.ServiceException;

public interface InstrumentManagementService {
	/**
	 * 
	 * @param cardAlias
	 * @param status
	 * @param initiatedBy
	 * @param remarks
	 * @return CardDetails
	 * @throws DataAccessException
	 * @throws ServiceException
	 */
	public CardDetails changePhysicalCardStatus(String cardAlias,
			String status, String initiatedBy, String remarks,String agentId,long requestId)
			throws DataAccessException, ServiceException;

	/**
	 * 
	 * @param custId
	 * @param status
	 * @param initiatedBy
	 * @param remarks
	 * @return CardDetails
	 * @throws DataAccessException
	 * @throws ServiceException
	 */
	public CardDetails changePhysicalCardStatusForWallet(String custId,
			String status, String initiatedBy, String remarks,String agentId,long requestId)
			throws DataAccessException, ServiceException;

	/**
	 * 
	 * @param cardAliaso
	 * @param cardNo
	 * @return CardDetails
	 * @throws DataAccessException
	 * @throws ServiceException
	 */
	public CardDetails unLinkPhysicalCard(String cardAliaso, String cardNo,String agentId,long requestId)
			throws DataAccessException, ServiceException;

}
