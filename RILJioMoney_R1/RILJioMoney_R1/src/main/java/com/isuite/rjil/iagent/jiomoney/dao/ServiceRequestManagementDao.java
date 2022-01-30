package com.isuite.rjil.iagent.jiomoney.dao;

import com.isuite.rjil.iagent.jiomoney.common.CustomerProblem;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;

public interface ServiceRequestManagementDao
{
	/**
	 * @param partner_NO
	 * @param category
	 * @param description
	 * @param problem_Ref_No
	 * @param status
	 * @param impact
	 * @param urgency
	 * @param channel
	 * @return
	 * @throws DataAccessException
	 */

	CustomerProblem createServiceRequest(String entityId, String category,
			String subCategory, String subSubCategory,String subSubSubCategory, String description,
			String problemRefNo, String impact, String urgency, String channel,
			String agentCode, String note, String mobileNo, String emailId,
			String entityType, String status,String communicationMode,long requestId)  throws DataAccessException;
	
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
	 */

	CustomerProblem updateServiceRequest(String custId, String problemRefNo,
			String status, String impact, String urgency, String channel,
			String agentCode, String note,String mobileNumber, String email,String owner, String teamMember,long requestId) throws DataAccessException;

}
