package com.isuite.rjil.iagent.jiomoney.services;

import com.isuite.rjil.iagent.jiomoney.common.CustomerProblem;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.exception.ServiceException;

public interface ServiceRequestManagementService 
{
	/**
	 * 
	 * @param custId
	 * @param problemRefNo
	 * @param status
	 * @param impact
	 * @param urgency
	 * @param channel
	 * @param agentCode
	 * @param note
	 * @return
	 * @throws DataAccessException
	 * @throws ServiceException
	 */

	CustomerProblem updateServiceRequest(String custId, String problemRefNo,
			String status, String impact, String urgency, String channel,
			String agentCode, String note, String mobileNumber, String email,
			String owner, String teamMember,long requestId) throws DataAccessException,
			ServiceException;

	/**
	 * 
	 * @param custId
	 * @param category
	 * @param subCategory
	 * @param subSubCategory
	 * @param description
	 * @param problemRefNo
	 * @param impact
	 * @param urgency
	 * @param channel
	 * @param agentCode
	 * @param note
	 * @return
	 * @throws DataAccessException
	 * @throws ServiceException
	 */

	CustomerProblem createServiceRequest(String entityId, String category,
			String subCategory, String subSubCategory,
			String subSubSubCategory, String description, String problemRefNo,
			String impact, String urgency, String channel, String agentCode,
			String note, String mobileNo, String emailId, String entityType,
			String status, String communicationMode,long requestId)
			throws DataAccessException, ServiceException;

}
