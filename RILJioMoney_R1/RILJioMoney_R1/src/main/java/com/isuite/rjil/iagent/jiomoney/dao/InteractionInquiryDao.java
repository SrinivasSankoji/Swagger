package com.isuite.rjil.iagent.jiomoney.dao;

import java.util.List;

import com.isuite.rjil.iagent.jiomoney.common.CustomerInteraction;
import com.isuite.rjil.iagent.jiomoney.common.VerificationQuestion;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;

/**
 * 
 * @author NovelVox
 * 
 */
public interface InteractionInquiryDao {
	/**
	 * 
	 * @param interactionRefNo
	 * @param entityType
	 * @return CustomerInteraction
	 * @throws DataAccessException
	 */
	public CustomerInteraction viewInteractionRequest(String interactionRefNo,
			String entityType,String agentId,long requestId) throws DataAccessException;

	/**
	 * 
	 * @param custId
	 * @param entityType
	 * @param pageSize
	 * @param offSet
	 * @return List<CustomerInteraction>
	 * @throws DataAccessException
	 */
	public List<CustomerInteraction> searchInteractionRequest(String custId,
			String entityType, String pageSize, String offSet,String agentId,long requestId)
			throws DataAccessException;
/**
 * 
 * @param categoryId2
 * @param categoryId3
 * @param entityType
 * @param custId
 * @return
 * @throws DataAccessException
 */
	public List<VerificationQuestion> getVerificationQuestionList(String categoryId2,
			String categoryId3, String entityType, String custId,String agentId,long requestId) throws DataAccessException;
}
