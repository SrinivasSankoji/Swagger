package com.isuite.rjil.iagent.jiomoney.services;

import com.isuite.rjil.iagent.jiomoney.common.Customer;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.exception.ServiceException;

/**
 * @author NovelVox
 * 
 */

public interface InstrumentInquiryService {

	/**
	 * 
	 * @param custId
	 * @return Customer
	 * @throws DataAccessException
	 * @throws ServiceException
	 */
	public Customer getPhysicalCardList(String custId,String agentId,long requestId)
			throws DataAccessException, ServiceException;

	/**
	 * 
	 * @param custId
	 * @return Customer
	 * @throws DataAccessException
	 * @throws ServiceException
	 */
	public Customer getCardList(String custId,String agentId,long requestId) throws DataAccessException,
			ServiceException;

}
