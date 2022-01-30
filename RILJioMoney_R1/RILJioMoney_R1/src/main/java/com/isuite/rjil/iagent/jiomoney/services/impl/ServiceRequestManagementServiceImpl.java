package com.isuite.rjil.iagent.jiomoney.services.impl;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.common.CustomerProblem;
import com.isuite.rjil.iagent.jiomoney.dao.DaoLocator;
import com.isuite.rjil.iagent.jiomoney.dao.ServiceRequestManagementDao;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.exception.ServiceException;
import com.isuite.rjil.iagent.jiomoney.services.ServiceRequestManagementService;

public class ServiceRequestManagementServiceImpl implements ServiceRequestManagementService
{
	private static final Logger logger = Logger
			.getLogger(ServiceRequestManagementServiceImpl.class);

	private static String DAO = "ServiceRequestManagementDAO";
	@Override
	public CustomerProblem updateServiceRequest(String custId,
			String problemRefNo, String status, String impact, String urgency,
			String channel, String agentCode, String note, String mobileNumber,
			String email, String owner, String teamMember,long requestId)
			throws DataAccessException, ServiceException {
		CustomerProblem updateServiceRequest = null;
		try {

			updateServiceRequest = ((ServiceRequestManagementDao) DaoLocator
					.getDao(DAO)).updateServiceRequest(custId, problemRefNo,
					status, impact, urgency, channel, agentCode, note,
					mobileNumber, email, owner, teamMember,requestId);

		} catch (DataAccessException e) {
			logger.error("Exception catched while doing updateServiceRequest ",
					e);
			throw e;
		}
		return updateServiceRequest;

	}

	@Override
	public CustomerProblem createServiceRequest(String entityId,
			String category, String subCategory, String subSubCategory,String subSubSubCategory,
			String description, String problemRefNo, String impact,
			String urgency, String channel, String agentCode, String note,
			String mobileNo, String emailId, String entityType, String status,
			String communicationMode,long requestId) throws DataAccessException,
			ServiceException {
		CustomerProblem createServiceRequest = null;
		try {

			createServiceRequest = ((ServiceRequestManagementDao) DaoLocator
					.getDao(DAO)).createServiceRequest(entityId, category,
					subCategory, subSubCategory,subSubSubCategory, description, problemRefNo,
					impact, urgency, channel, agentCode, note, mobileNo,
					emailId, entityType, status, communicationMode,requestId);

		} catch (DataAccessException e) {
			logger.error("Exception catched while doing createServiceRequest ",
					e);
			throw e;
		}
		return createServiceRequest;

	}

}
