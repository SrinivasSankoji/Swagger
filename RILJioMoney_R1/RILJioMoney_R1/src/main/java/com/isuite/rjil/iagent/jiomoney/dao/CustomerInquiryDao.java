package com.isuite.rjil.iagent.jiomoney.dao;

import java.util.List;

import com.isuite.rjil.iagent.jiomoney.common.Customer;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.exception.ServiceException;

/**
 * @author NovelVox
 * 
 */

public interface CustomerInquiryDao {

	/**
	 * 
	 * @param customerId
	 * @param prepaidAccountId
	 * @param currency
	 * @param branchId
	 * @param institutionCode
	 * @return Customer
	 * @throws DataAccessException
	 */
	public Customer getPrepaidAccountInfo(String customerId,
			String prepaidAccountId, String currency, String branchId,
			String institutionCode,String agentId,long requestId) throws DataAccessException;

	/**
	 * 
	 * @param mobileNo
	 * @param emailAddress
	 * @param panNumber
	 * @param firstName
	 * @param lastName
	 * @param dateofBirth
	 * @param customerId
	 * @return List<Customer>
	 * @throws DataAccessException
	 * @throws ServiceException
	 */

	public List<Customer> searchCustomer(String mobileNo, String emailAddress,
			String panNumber, String firstName, String lastName,
			String dateofBirth, String customerId,String agentId,long requestId,boolean prospectSearch) throws DataAccessException,
			ServiceException;

	/**
	 * 
	 * @param customerId
	 * @return Customer
	 * @throws DataAccessException
	 * @throws ServiceException
	 */

	public Customer displayCustomer(String customerId, String agentId,long requestId)
			throws DataAccessException, ServiceException;
}
