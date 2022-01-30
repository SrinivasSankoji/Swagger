/**
 * 
 */
package com.isuite.rjil.iagent.jiomoney.services;

import java.util.List;

import com.isuite.rjil.iagent.jiomoney.common.Account;
import com.isuite.rjil.iagent.jiomoney.common.Bank;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.exception.ServiceException;

/**
 * @author NovelVox
 * 
 */
public interface AccountInquiryService {
	/**
	 * 
	 * @param accountId
	 * @param custId
	 * @param currency
	 * @param branchId
	 * @param institutionCode
	 * @return Account
	 * @throws DataAccessException
	 * @throws ServiceException
	 */
	public Account getAccountBalance(String accountId, String custId,
			String currency, String branchId, String institutionCode,String agentId,long requestId)
			throws DataAccessException, ServiceException;

	/**
	 * 
	 * @param custId
	 * @param accountNo
	 * @param mobileNo
	 * @param amount
	 * @param loadType
	 * @param loadSource
	 * @param currency
	 * @param branchId
	 * @param institutionCode
	 * @return List<Bank>
	 * @throws DataAccessException
	 * @throws ServiceException
	 */
	public List<Bank> validateLoadMoney(String custId, String accountNo,
			String mobileNo, String amount, String loadType, String loadSource,
			String currency, String branchId, String institutionCode,String agentId,long requestId)
			throws DataAccessException, ServiceException;

}
