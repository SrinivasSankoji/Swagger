package com.isuite.rjil.iagent.jiomoney.services;

import java.util.List;

import com.isuite.rjil.iagent.jiomoney.common.CustomerProblem;
import com.isuite.rjil.iagent.jiomoney.common.ReferenceData;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.exception.ServiceException;

/**
 * 
 * @author NovelVox
 * 
 */
public interface ServiceRequestInquiryService {
	/**
	 * 
	 * @param problemRefNo
	 * @param custType
	 * @return
	 * @throws DataAccessException
	 * @throws ServiceException
	 */
	CustomerProblem viewServiceRequest(String problemRefNo, String custType,
			String agentId,long requestId) throws DataAccessException, ServiceException;

	/**
	 * 
	 * @param custId
	 * @param status
	 * @param fromDate
	 * @param toDate
	 * @param pageSize
	 * @param offSet
	 * @return
	 * @throws DataAccessException
	 * @throws ServiceException
	 */

	List<CustomerProblem> searchServiceRequest(String entityType,
			String entityId, String urgency, String impact, String isSummaryRq,
			String owner, String serviceTeam, String status, String fromDate,
			String toDate, String pageSize, String offSet, String agentId,long requestId)
			throws DataAccessException, ServiceException;


	/**
	 * 
	 * @param entityType
	 * @return
	 * @throws DataAccessException
	 * @throws ServiceException
	 */

	List<ReferenceData> getServiceCategory1(String entityType, String agentId,long requestId)
			throws DataAccessException, ServiceException;

	/**
	 * 
	 * @param entityType
	 * @param categoryId1
	 * @return
	 * @throws DataAccessException
	 * @throws ServiceException
	 */

	List<ReferenceData> getServiceCategory2(String entityType,
			String categoryId1, String agentId,long requestId) throws DataAccessException,
			ServiceException;

	/**
	 * 
	 * @param categoryId1
	 * @param categoryId2
	 * @param entityType
	 * @return
	 * @throws DataAccessException
	 * @throws ServiceException
	 */
	List<ReferenceData> getServiceCategory3(String categoryId1,
			String categoryId2, String entityType, String agentId,long requestId)
			throws DataAccessException, ServiceException;
	/**
	 * 
	 * @param categoryId1
	 * @param categoryId2
	 * @param categoryId3
	 * @param entityType
	 * @param agentId
	 * @return
	 * @throws DataAccessException
	 * @throws ServiceException
	 */
	List<ReferenceData> getServiceCategory4(String categoryId1,
			String categoryId2, String categoryId3, String entityType,
			String agentId,long requestId) throws DataAccessException, ServiceException;
	
	List<ReferenceData> getStatesOrDistrictList(String state,String agentId,long requestId) throws DataAccessException, ServiceException;
	
	public String validateServiceRequest(String entityId,String subSubSubCategory, 
			String entityType, String agentCode,long requestId)throws DataAccessException, ServiceException;
}