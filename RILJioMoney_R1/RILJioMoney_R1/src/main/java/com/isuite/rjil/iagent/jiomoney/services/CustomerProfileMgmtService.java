package com.isuite.rjil.iagent.jiomoney.services;

import com.isuite.rjil.iagent.jiomoney.common.Customer;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.exception.ServiceException;

public interface CustomerProfileMgmtService {

	/**
	 * 
	 * @param entityId
	 * @param primEmailId
	 * @param addressLine
	 * @param country
	 * @param mobileNo
	 * @return Customer
	 * @throws DataAccessException
	 * @throws ServiceException
	 */
	public Customer updateCustomerProfile(String entityId, String oldEmailId,
			String addressLine, String country, String mobileNo,
			String newEmailId,String agentId,long requestId) throws DataAccessException, ServiceException;
	/*Start by Abhishek*/
	public Customer retrieveCustomerDetails(String entityId,String mobileNo,String accountType
			,String orderRefNo,	String agentId,long requestId) throws DataAccessException, ServiceException;
	/*End by Abhishek*/
}
