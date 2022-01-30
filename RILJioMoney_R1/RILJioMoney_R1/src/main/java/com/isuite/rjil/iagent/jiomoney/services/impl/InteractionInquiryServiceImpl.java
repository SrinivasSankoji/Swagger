package com.isuite.rjil.iagent.jiomoney.services.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.common.CustomerInteraction;
import com.isuite.rjil.iagent.jiomoney.common.VerificationQuestion;
import com.isuite.rjil.iagent.jiomoney.dao.DaoLocator;
import com.isuite.rjil.iagent.jiomoney.dao.InteractionInquiryDao;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.exception.ServiceException;
import com.isuite.rjil.iagent.jiomoney.services.InteractionInquiryService;

public class InteractionInquiryServiceImpl implements InteractionInquiryService {
	private static final Logger logger = Logger
			.getLogger(InteractionInquiryServiceImpl.class);

	private static final String dao = "InteractionInquiryDao";

	@Override
	public CustomerInteraction viewInteractionRequest(String interactionRefNo,
			String entityType,String agentId,long requestId) throws ServiceException, DataAccessException {

		// if (interactionRefNo == null || interactionRefNo.isEmpty() ||
		// entityType == null
		// || entityType.isEmpty()) {
		//
		// throw new ServiceException("Interaction Ref No or Entity Type");
		// }

		try {
			CustomerInteraction viewInteractionRequest = ((InteractionInquiryDao) DaoLocator
					.getDao(dao)).viewInteractionRequest(interactionRefNo,
					entityType, agentId,requestId);

			return viewInteractionRequest;
		} catch (DataAccessException e) {
			logger.error(
					"Exception catched while doing viewInteractionRequest ", e);
			throw e;
		}
	}

	@Override
	public List<CustomerInteraction> searchInteractionRequest(String custId,
			String entityType, String pageSize, String offSet,String agentId,long requestId)
			throws ServiceException, DataAccessException {
		// if (custId == null || custId.isEmpty() || entityType == null
		// || entityType.isEmpty()) {
		//
		// throw new ServiceException("Enter customer Id or Entity Type");
		// }
		try {
			List<CustomerInteraction> searchInteractionRequest = ((InteractionInquiryDao) DaoLocator
					.getDao(dao)).searchInteractionRequest(custId, entityType,
					pageSize, offSet, agentId,requestId);
			return searchInteractionRequest;
		} catch (DataAccessException e) {
			logger.error(
					"Exception catched while doing searchInteractionRequest ",
					e);
			throw e;
		}

	}

	@Override
	public List<VerificationQuestion> getVerificationQuestionList(String categoryId2,
			String categoryId3, String entityType, String custId,String agentId,long requestId) throws ServiceException,
			DataAccessException {
		try {
			List<VerificationQuestion> getVerificationQuestionList = ((InteractionInquiryDao) DaoLocator
					.getDao(dao)).getVerificationQuestionList(categoryId2,
					categoryId3, entityType, custId, agentId,requestId);

			
			return getVerificationQuestionList;
		} catch (DataAccessException e) {
			logger.error(
					"Exception catched while doing getVerificationQuestionList ", e);
			throw e;
		}
	}

	
}
