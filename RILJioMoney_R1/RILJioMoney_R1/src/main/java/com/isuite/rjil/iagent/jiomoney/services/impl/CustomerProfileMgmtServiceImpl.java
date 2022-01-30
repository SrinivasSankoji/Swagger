package com.isuite.rjil.iagent.jiomoney.services.impl;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.common.Customer;
import com.isuite.rjil.iagent.jiomoney.dao.CustomerProfileMgmtDao;
import com.isuite.rjil.iagent.jiomoney.dao.DaoLocator;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.exception.ServiceException;
import com.isuite.rjil.iagent.jiomoney.services.CustomerProfileMgmtService;

/**
 * 
 * @author NovelVox
 * 
 */
public class CustomerProfileMgmtServiceImpl implements
		CustomerProfileMgmtService {
	private static final Logger logger = Logger
			.getLogger(CustomerProfileMgmtServiceImpl.class);

	private static final String dao = "CustomerProfileMgmtDao";

	@Override
	public Customer updateCustomerProfile(String entityId, String oldEmailId,
			String addressLine, String country, String mobileNo,
			String newEmailId,String agentId,long requestId) throws DataAccessException, ServiceException {
		try {
			Customer updateCustomerProfile = ((CustomerProfileMgmtDao) DaoLocator
					.getDao(dao)).updateCustomerProfile(entityId, oldEmailId,
					addressLine, country, mobileNo, newEmailId,agentId,requestId);
			return updateCustomerProfile;
		} catch (DataAccessException e) {
			logger.error("Exception catched while doing updateCustomerprofile",
					e);
			throw e;
		}
	}
/*start abhishek*/
	@Override
	public Customer retrieveCustomerDetails(String entityId,String mobileNo,String accountType
			,String orderRefNo,	String agentId,long requestId) throws DataAccessException,
			ServiceException {
		// TODO Auto-generated method stub
		try {
			Customer retrieveCustomerDetails = ((CustomerProfileMgmtDao) DaoLocator
					.getDao(dao)).retrieveCustomerDetails(entityId, mobileNo, accountType, orderRefNo, agentId, requestId);
			return retrieveCustomerDetails;
		} catch (DataAccessException e) {
			logger.error("Exception catched while doing retrieveCustomerDetails",
					e);
			throw e;
		}
	}
	/*End abhishek*/
}
