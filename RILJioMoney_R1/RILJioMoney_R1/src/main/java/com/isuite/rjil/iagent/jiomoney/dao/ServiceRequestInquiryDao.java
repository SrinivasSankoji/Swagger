package com.isuite.rjil.iagent.jiomoney.dao;

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
public interface ServiceRequestInquiryDao {
	/**
	 * 
	 * @param problemRefNo
	 * @param custType
	 * @return
	 * @throws DataAccessException
	 */
	CustomerProblem viewServiceRequest(String problemRefNo, String custType,String agentId,long requestId)
			throws DataAccessException;

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
	 */
	List<CustomerProblem> searchServiceRequest(String entityType,String entityId,String urgency ,String impact,String isSummaryRq,String owner,String serviceTeam, String status,
			String fromDate, String toDate, String pageSize, String offSet,String agentId,long requestId)
			throws DataAccessException;

	

	
	/**
	 * 
	 * @param entityType
	 * @return
	 * @throws DataAccessException
	 */
	
	 List<ReferenceData> getServiceCategory1(String entityType,String agentId,long requestId)throws DataAccessException;
	 /**
	  * 
	  * @param entityType
	  * @param categoryId1
	  * @return
	  * @throws DataAccessException
	  */
	
	  List<ReferenceData> getServiceCategory2(String entityType,String categoryId1,String agentId,long requestId)throws DataAccessException;
	  /**
	   * 
	   * @param categoryId1
	   * @param categoryId2
	   * @param entityType
	   * @return
	   * @throws DataAccessException
	   */
	  List<ReferenceData> getServiceCategory3(String categoryId1,String categoryId2,String entityType,String agentId,long requestId)throws DataAccessException;


	  List<ReferenceData> getServiceCategory4(String categoryId1,String categoryId2,String categoryId3,String entityType,String agentId,long requestId)throws DataAccessException;
	  public List<ReferenceData> getStatesOrDistrictList(String pState,String agentId,long requestId)
				throws DataAccessException;
	  
	  public String validateServiceRequest(String entityId,String subSubSubCategory, 
				String entityType, String agentCode,long requestId)throws DataAccessException, ServiceException;

}
