package com.isuite.rjil.iagent.jiomoney.services;

import java.util.List;

import com.isuite.rjil.iagent.jiomoney.common.CustomerInteraction;
import com.isuite.rjil.iagent.jiomoney.common.VerificationQuestion;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.exception.ServiceException;

/**
 * 
 * @author NovelVox
 * 
 */
public interface InteractionInquiryService {
	/**
	 * 
	 * @param interactionRefNo
	 * @param entityType
	 * @return CustomerInteraction
	 * @throws ServiceException
	 * @throws DataAccessException
	 */

	public CustomerInteraction viewInteractionRequest(String interactionRefNo,
			String entityType,String agentId,long requestId) throws ServiceException, DataAccessException;

	/**
	 * 
	 * @param custId
	 * @param entityType
	 * @param pageSize
	 * @param offSet
	 * @return List<CustomerInteraction>
	 * @throws ServiceException
	 * @throws DataAccessException
	 */

	public List<CustomerInteraction> searchInteractionRequest(String custId,
			String entityType, String pageSize, String offSet,String agentId,long requestId)
			throws ServiceException, DataAccessException;

	 /**
	  * 
	  * @param categoryId2
	  * @param categoryId3
	  * @param entityType
	  * @param custId
	  * @return
	  * @throws ServiceException
	  * @throws DataAccessException
	  */
	public List<VerificationQuestion> getVerificationQuestionList(String categoryId2,
			String categoryId3, String entityType, String custId,String agentId,long requestId) throws ServiceException,
			DataAccessException;

}
