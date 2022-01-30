package com.isuite.rjil.iagent.jiomoney.services.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.common.CustomerInteraction;
import com.isuite.rjil.iagent.jiomoney.dao.DaoLocator;
import com.isuite.rjil.iagent.jiomoney.dao.InteractionManagementDao;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.exception.ServiceException;
import com.isuite.rjil.iagent.jiomoney.services.InteractionManagementService;

public class InteractionManagementServiceImpl implements
		InteractionManagementService {
	private static final Logger logger = Logger
			.getLogger(InteractionManagementServiceImpl.class);

	private static final String dao = "InteractionManagementDao";

	@Override
	public CustomerInteraction createInteraction(String interactionRefNo,
			String interactionDate, String category, String subCategory,
			String subSubCategory, String category4, String description, String callDuration,
			String custId, String channel, String agentCode,
			String verificationQuestionAir, String action, String notes,
			List<String> verificationQuestion,String pCommunicationMode,long requestId) throws DataAccessException,
			ServiceException {
		try {
			CustomerInteraction createInteraction = ((InteractionManagementDao) DaoLocator
					.getDao(dao)).createInteraction(interactionRefNo,
					interactionDate, category, subCategory, subSubCategory,category4,
					description, callDuration, custId, channel, agentCode,
					verificationQuestionAir, action, notes,
					verificationQuestion,pCommunicationMode,requestId);

			return createInteraction;
		} catch (Throwable e) {
			logger.error("Exception catched while doing createInteraction ", e);
			throw e;
		}
	}
}
