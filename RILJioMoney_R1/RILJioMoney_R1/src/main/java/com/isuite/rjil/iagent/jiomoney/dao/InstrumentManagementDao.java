package com.isuite.rjil.iagent.jiomoney.dao;

import com.isuite.rjil.iagent.jiomoney.common.CardDetails;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.exception.ServiceException;

/**
 * @author NovelVox
 * 
 */
public interface InstrumentManagementDao {

	/**
	 * 
	 * @param cardAlias
	 * @param status
	 * @param initiatedBy
	 * @param remarks
	 * @return CardDetails
	 * @throws DataAccessException
	 */
	public CardDetails changePhysicalCardStatus(String cardAlias,
			String status, String initiatedBy, String remarks,String agentId,long requestId)
			throws DataAccessException;

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
	 * @param cardAlias
	 * @param cardNo
	 * @return CardDetails
	 * @throws DataAccessException
	 */
	public CardDetails unLinkPhysicalCard(String cardAlias, String cardNo,String agentId,long requestId)
			throws DataAccessException;

}
