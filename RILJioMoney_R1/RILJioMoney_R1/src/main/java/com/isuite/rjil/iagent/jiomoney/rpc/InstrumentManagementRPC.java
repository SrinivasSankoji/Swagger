package com.isuite.rjil.iagent.jiomoney.rpc;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.common.CardDetails;
import com.isuite.rjil.iagent.jiomoney.common.ServiceResponse;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.services.InstrumentManagementService;
import com.isuite.rjil.iagent.jiomoney.services.ServiceLocator;
import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;
import com.isuite.rjil.iagent.jiomoney.util.Util;

public class InstrumentManagementRPC {
	private static final Logger soapLogger = Logger.getLogger(LOVUtil.SOAPLOGGER);
	private static final String service = "InstrumentManagementService";

	public ServiceResponse changePhysicalCardStatus(String cardAlias,
			String status, String initiatedBy, String remarks,String agentId) {
		ServiceResponse response = null;
		long requestId = Util.getRequestId();
		String lClassName =  this.getClass().getName();
		String lMethodName = "changePhysicalCardStatus";
		soapLogger.info("************************");
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		try {
			CardDetails changePhysicalCardStatus = ((InstrumentManagementService) ServiceLocator
					.getService(service)).changePhysicalCardStatus(cardAlias,
					status, initiatedBy, remarks,agentId,requestId);
			if (changePhysicalCardStatus != null) {
				response = new ServiceResponse("Status for the Card Name "
						+ changePhysicalCardStatus.getAlias()
						+ " is successfully change");
			} else {
				response = new ServiceResponse(1,
						"No record Found in Instrument Management");
			}
		}catch (Throwable t) 
		{
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			response = new ServiceResponse(1, t.getMessage());
		}
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
		soapLogger.info("************************");
		return response;
	}

	public ServiceResponse changePhysicalCardStatusForWallet(String custId,
			String status, String initiatedBy, String remarks,String agentId) {
		ServiceResponse response = null;
		String lClassName =  this.getClass().getName();
		long requestId = Util.getRequestId();
		String lMethodName = "changePhysicalCardStatusForWallet";
		soapLogger.info("************************");
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		try {

			CardDetails changeWalletCardStatus = ((InstrumentManagementService) ServiceLocator
					.getService(service)).changePhysicalCardStatus(custId,
					status, initiatedBy, remarks,agentId,requestId);
			if (changeWalletCardStatus != null) {
				response = new ServiceResponse("Status for the Wallet "
						+ changeWalletCardStatus.getAlias()
						+ " is successfully change");
			} else {
				response = new ServiceResponse(1,
						"No record Found in Instrument Management");
			}
		} 
		catch(Throwable t) 
		{
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			response = new ServiceResponse(1, t.getMessage());
		}
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
		soapLogger.info("************************");
		return response;
	}

	public ServiceResponse unLinkPhysicalCard(String cardAlias, String cardNo,String agentId)
			throws DataAccessException {
		String lClassName =  this.getClass().getName();
		long requestId = Util.getRequestId();
		String lMethodName = "unLinkPhysicalCard";
		soapLogger.info("************************");
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");

		ServiceResponse response = null;
		try {
			CardDetails unLinkPhysicalCard = ((InstrumentManagementService) ServiceLocator
					.getService(service)).unLinkPhysicalCard(cardAlias, cardNo,agentId,requestId);

			if (unLinkPhysicalCard != null) {
				response = new ServiceResponse("The card Name "
						+ unLinkPhysicalCard.getAlias()
						+ " is succesfully unLink");
			} else {
				response = new ServiceResponse(1,
						"No record Found in Instrument Management ");
			}
		} catch (Throwable t) {
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			response = new ServiceResponse(1, t.getMessage());
		}
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
		soapLogger.info("************************");
		return response;
	}
}
