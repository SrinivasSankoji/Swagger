/**
 * 
 */
package com.isuite.rjil.iagent.jiomoney.services.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.common.Account;
import com.isuite.rjil.iagent.jiomoney.common.Bank;
import com.isuite.rjil.iagent.jiomoney.dao.AccountInquiryDao;
import com.isuite.rjil.iagent.jiomoney.dao.DaoLocator;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.exception.ServiceException;
import com.isuite.rjil.iagent.jiomoney.services.AccountInquiryService;

/**
 * @author NovelVox
 * 
 */
public class AccountInquiryServiceImpl implements AccountInquiryService {
	private static final Logger logger = Logger
			.getLogger(AccountInquiryServiceImpl.class);

	private static final String dao = "AccountInquiryDao";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.isuite.rjil.iagent.jiomoney.services.AccountInquiryService#
	 * getAccountBalance(java.lang.String)
	 */
	@Override
	public Account getAccountBalance(String accountId, String custId,
			String currency, String branchId, String institutionCode,String agentId,long requestId)
			throws DataAccessException, ServiceException {
		Account accountBalance = null;
		try {
			// if(accountId==null || accountId.isEmpty() || custId ==null ||
			// custId.isEmpty()){
			// throw new
			// ServiceException("Mandatory Parameter require getAccountBalance for Account ID & cust ID");
			// }
			accountBalance = ((AccountInquiryDao) DaoLocator.getDao(dao))
					.getAccountBalance(accountId, custId, currency, branchId,
							institutionCode, agentId,requestId);
		} catch (DataAccessException e) {
			logger.error("Exception catched while doing getAccountBalance ", e);
			throw e;
		}
		return accountBalance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.isuite.rjil.iagent.jiomoney.services.AccountInquiryService#
	 * validateLoadMoney(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Bank> validateLoadMoney(String custId, String accountNo,
			String mobileNo, String amount, String loadType, String loadSource,
			String currency, String branchId, String institutionCode,String agentId,long requestId)
			throws DataAccessException, ServiceException {
		List<Bank> validateLoadMoney = null;
		try {
			// if(custId==null || custId.isEmpty() || accountNo ==null ||
			// accountNo.isEmpty()){
			// throw new
			// ServiceException("Mandatory Parameter require for cust ID & Account No");
			// }

			validateLoadMoney = ((AccountInquiryDao) DaoLocator.getDao(dao))
					.validateLoadMoney(custId, accountNo, mobileNo, amount,
							loadType, loadSource, currency, branchId,
							institutionCode, agentId,requestId);
		} catch (DataAccessException e) {
			logger.error("Exception catched while doing validateLoadMoney ", e);
			throw e;
		}

		return validateLoadMoney;

	}
}
