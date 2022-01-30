/**
 * 
 */
package com.isuite.rjil.iagent.jiomoney.services.impl;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.common.CardDetails;
import com.isuite.rjil.iagent.jiomoney.dao.DaoLocator;
import com.isuite.rjil.iagent.jiomoney.dao.InstrumentManagementDao;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.exception.ServiceException;
import com.isuite.rjil.iagent.jiomoney.services.InstrumentManagementService;

/**
 * @author NovelVox
 * 
 */
public class InstrumentManagementServiceImpl implements
		InstrumentManagementService {
	private static final Logger logger = Logger
			.getLogger(InstrumentManagementServiceImpl.class);

	private static final String dao = "InstrumentManagementDao";

	@Override
	public CardDetails changePhysicalCardStatus(String cardAlias,
			String status, String initiatedBy, String remarks,String agentId,long requestId)
			throws DataAccessException, ServiceException {
		try {
			CardDetails changePhysicalCardStatus = ((InstrumentManagementDao) DaoLocator
					.getDao(dao)).changePhysicalCardStatus(cardAlias, status,
					initiatedBy, remarks,agentId,requestId);

			return changePhysicalCardStatus;
		} catch (DataAccessException e) {
			logger.error(
					"Exception catched while doing Change Physical Card Status in Instrument Management Service Impl",
					e);
			throw e;
		}
	}

	@Override
	public CardDetails unLinkPhysicalCard(String cardAlias, String cardNo,String agentId,long requestId)
			throws DataAccessException, ServiceException {
		try {

			CardDetails unLinkPhysicalCard = ((InstrumentManagementDao) DaoLocator
					.getDao(dao)).unLinkPhysicalCard(cardAlias, cardNo,agentId,requestId);

			return unLinkPhysicalCard;
		} catch (DataAccessException e) {
			logger.error("Exception catched while doing unLinkPhysicalCard ", e);
			throw e;
		}
	}

	@Override
	public CardDetails changePhysicalCardStatusForWallet(String custId,
			String status, String initiatedBy, String remarks,String agentId,long requestId)
			throws DataAccessException, ServiceException {
		try {
			CardDetails changeWalletCardStatus = ((InstrumentManagementDao) DaoLocator
					.getDao(dao)).changePhysicalCardStatus(custId, status,
					initiatedBy, remarks,agentId,requestId);

			return changeWalletCardStatus;
		} catch (DataAccessException e) {
			logger.error(
					"Exception catched while doing Change Physical Card Status For Wallet in Instrument Management Service Impl",
					e);
			throw e;
		}
	}

}
