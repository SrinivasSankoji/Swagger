package com.isuite.rjil.iagent.jiomoney.dao;

import java.util.List;

import com.isuite.rjil.iagent.jiomoney.common.CustomerInteraction;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;

public interface InteractionManagementDao {

	/**
	 * 
	 * @param interactionRefNo
	 * @param interactionDate
	 * @param category
	 * @param subCategory
	 * @param subSubCategory
	 * @param description
	 * @param callDuration
	 * @param custId
	 * @param channel
	 * @param agentCode
	 * @param verificationQuestionAir
	 * @param action
	 * @param notes
	 * @return CustomerInteraction
	 * @throws DataAccessException
	 */
	public CustomerInteraction createInteraction(String interactionRefNo,
			String interactionDate, String category, String subCategory,
			String subSubCategory,  String category4, String description, String callDuration,
			String custId, String channel, String agentCode,
			String verificationQuestionAir, String action, String notes,
			List<String> verificationQuestion,String pCommunicationMode,long requestId) throws DataAccessException;

}
