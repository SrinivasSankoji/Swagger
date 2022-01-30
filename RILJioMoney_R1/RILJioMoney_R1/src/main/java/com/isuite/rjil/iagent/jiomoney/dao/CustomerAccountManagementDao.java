package com.isuite.rjil.iagent.jiomoney.dao;

/**
 * @author NovelVox
 * 
 */
import com.isuite.rjil.iagent.jiomoney.common.Customer;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;

public interface CustomerAccountManagementDao {

	/**
	 * 
	 * @param custID
	 * @param accountNumber
	 * @param mobileNumber
	 * @param emailId
	 * @param status
	 * @param initiatedBy
	 * @param remarks
	 * @return Customer
	 * @throws DataAccessException
	 */
	public Customer changeAccountStatus(String custID, String accountNumber,
			String mobileNumber, String emailId, String status,
			String initiatedBy, String remarks,String agentId,long requestId) throws DataAccessException;

	/**
	 * 
	 * @param custID
	 * @param accountNumber
	 * @param mobileNumber
	 * @param emailId
	 * @param status
	 * @param initiatedBy
	 * @param remarks
	 * @return Customer
	 * @throws DataAccessException
	 */
	public Customer blockOrUnblockAccount(String custID, String accountNumber,
			String mobileNumber, String emailId, String status,
			String initiatedBy, String remarks,String agentId,long requestId) throws DataAccessException;

	/**
	 * 
	 * @param custID
	 * @param mobileNumber
	 * @param initiatedBy
	 * @param remarks
	 * @return Customer
	 * @throws DataAccessException
	 */
	public Customer terminateAccount(String custID, String mobileNumber,
			String initiatedBy, String remarks,String agentId,long requestId) throws DataAccessException;

}
