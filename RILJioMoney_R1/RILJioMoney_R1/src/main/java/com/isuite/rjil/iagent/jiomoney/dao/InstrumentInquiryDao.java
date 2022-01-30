package com.isuite.rjil.iagent.jiomoney.dao;

import com.isuite.rjil.iagent.jiomoney.common.Customer;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;

/**
 * 
 * @author NovelVox
 * 
 */
public interface InstrumentInquiryDao {

	/**
	 * 
	 * @param custId
	 * @return Customer
	 * @throws DataAccessException
	 */
	public Customer getPhysicalCardList(String custId,String agentId,long requestId)
			throws DataAccessException;

	/**
	 * 
	 * @param custId
	 * @return Customer
	 * @throws DataAccessException
	 */
	public Customer getCardList(String custId,String agentId,long requestId) throws DataAccessException;
}
