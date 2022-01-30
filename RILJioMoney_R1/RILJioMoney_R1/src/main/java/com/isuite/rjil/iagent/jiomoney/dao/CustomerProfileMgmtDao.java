package com.isuite.rjil.iagent.jiomoney.dao;

import com.isuite.rjil.iagent.jiomoney.common.Customer;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.exception.ServiceException;

/**
 * 
 * @author NovelVox
 * 
 */
public interface CustomerProfileMgmtDao {

	/**
	 * 
	 * @param entityId
	 * @param primEmailId
	 * @param addressLine
	 * @param country
	 * @param mobileNo
	 * @return Customer
	 * @throws DataAccessException
	 */
	public Customer updateCustomerProfile(String entityId, String oldEmailId,
			String addressLine, String country, String mobileNo,
			String newEmailId,String agentId,long requestId) throws DataAccessException;
	/*Start by Abhishek*/
	public Customer retrieveCustomerDetails(String entityId,String mobileNo,String accountType
			,String orderRefNo,	String agentId,long requestId) throws DataAccessException;
	/*End by Abhishek*/
}
