package com.isuite.rjil.iagent.jiomoney.services.impl;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.common.Customer;
import com.isuite.rjil.iagent.jiomoney.dao.CustomerAccountManagementDao;
import com.isuite.rjil.iagent.jiomoney.dao.DaoLocator;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.exception.ServiceException;
import com.isuite.rjil.iagent.jiomoney.services.CustomerAccountManagementService;

public class CustomerAccountManagementServiceImpl implements
		CustomerAccountManagementService {
	private static final Logger logger = Logger
			.getLogger(CustomerAccountManagementServiceImpl.class);
	private static final String dao = "CustomerAccountManagementDao";

	@Override
	public Customer changeAccountStatus(String custID, String accountNumber,
			String mobileNumber, String emailId, String status,
			String initiatedBy, String remarks,String agentId,long requestId) throws ServiceException,
			DataAccessException {
		try {
			// if (custID == null || custID.isEmpty() || accountNumber == null
			// || accountNumber.isEmpty()) {
			// throw new
			// ServiceException("Mandatory Parameter require for Customer Id or Account Number");
			// }
			Customer changeAccountStatus = ((CustomerAccountManagementDao) DaoLocator
					.getDao(dao)).changeAccountStatus(custID, accountNumber,
					mobileNumber, emailId, status, initiatedBy, remarks,agentId,requestId);

			return changeAccountStatus;
		} catch (DataAccessException e) {
			logger.error(
					"Exception catched while doing Change Account Status ", e);
			throw e;
		}
	}

	@Override
	public Customer blockOrUnblockAccount(String custID, String accountNumber,
			String mobileNumber, String emailId, String status,
			String initiatedBy, String remarks,String agentId,long requestId) throws DataAccessException {
		try {
			// if (custID == null || custID.isEmpty() || accountNumber == null
			// || accountNumber.isEmpty()) {
			//
			// throw new
			// ServiceException("Mandatory Parameter require for Customer Id or Account Number");
			// }

			Customer blockOrUnblockAccount = ((CustomerAccountManagementDao) DaoLocator
					.getDao(dao)).blockOrUnblockAccount(custID, accountNumber,
					mobileNumber, emailId, status, initiatedBy, remarks,agentId,requestId);

			return blockOrUnblockAccount;
		} catch (DataAccessException e) {
			logger.error(
					"Exception catched while doing Block Or Unblock Account ",
					e);
			throw e;
		}
	}

	@Override
	public Customer terminateAccount(String custID, String mobileNumber,
			String initiatedBy, String remarks,String agentId,long requestId) throws ServiceException,
			DataAccessException {
		try {
			Customer terminateAccount = ((CustomerAccountManagementDao) DaoLocator
					.getDao(dao)).terminateAccount(custID, mobileNumber,
					initiatedBy, remarks,agentId,requestId);

			return terminateAccount;
		} catch (DataAccessException e) {
			logger.error("Exception catched while doing terminate Account ", e);
			throw e;
		}
	}
}
