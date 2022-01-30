package com.isuite.rjil.iagent.jiomoney.services.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.common.Customer;
import com.isuite.rjil.iagent.jiomoney.dao.CustomerInquiryDao;
import com.isuite.rjil.iagent.jiomoney.dao.DaoLocator;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.exception.ServiceException;
import com.isuite.rjil.iagent.jiomoney.services.CustomerInquiryService;

public class CustomerInquiryServiceImpl implements CustomerInquiryService {

	private static final Logger logger = Logger
			.getLogger(CustomerInquiryServiceImpl.class);

	private static final String dao = "CustomerInquiryDao";

	@Override
	public Customer getPrepaidAccountInfo(String customerId,
			String prepaidAccountId, String currency, String branchId,
			String institutionCode,String agentId,long requestId) throws DataAccessException,
			ServiceException {
		try {
			if (customerId != null && customerId.isEmpty()) {
				throw new ServiceException("Enter Customer ID");
			}
			Customer getPrepaidAccountInfo = ((CustomerInquiryDao) DaoLocator
					.getDao(dao)).getPrepaidAccountInfo(customerId,
					prepaidAccountId, currency, branchId, institutionCode, agentId,requestId);

			return getPrepaidAccountInfo;
		} catch (DataAccessException e) {
			logger.error(
					"Exception catched while doing getPrepaidAccountInfo ", e);
			throw e;
		}
	}

	@Override
	public List<Customer> searchCustomer(String mobileNo, String emailAddress,
			String panNumber, String firstName, String lastName,
			String dateofBirth, String customerId,String agentId,long requestId, boolean prospectSearch) throws DataAccessException,
			ServiceException {
		try {
			List<Customer> searchCustomer = ((CustomerInquiryDao) DaoLocator
					.getDao(dao)).searchCustomer(mobileNo, emailAddress,
					panNumber, firstName, lastName, dateofBirth, customerId, agentId,requestId,prospectSearch);

			return searchCustomer;
		} catch (DataAccessException e) {
			logger.error("Exception catched while doing searchCustomer ", e);
			throw e;
		}
	}

	@Override
	public Customer displayCustomer(String customerId,String agentId,long requestId)
			throws DataAccessException, ServiceException {
		try {
			Customer getcustomerdetails = ((CustomerInquiryDao) DaoLocator
					.getDao(dao)).displayCustomer(customerId, agentId,requestId);
			return getcustomerdetails;

		} catch (DataAccessException e) {
			logger.error("Exception catched while doing diplayCustomer ", e);
			throw e;
		}
	}
}
