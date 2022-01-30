package com.isuite.rjil.iagent.jiomoney.rpc;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.common.Customer;
import com.isuite.rjil.iagent.jiomoney.common.ServiceResponse;
import com.isuite.rjil.iagent.jiomoney.services.CustomerAccountManagementService;
import com.isuite.rjil.iagent.jiomoney.services.ServiceLocator;
import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;
import com.isuite.rjil.iagent.jiomoney.util.Util;

public class CustomerAccountManagementRPC {
	private static final Logger soapLogger = Logger.getLogger(LOVUtil.SOAPLOGGER);
	private static final String service = "CustomerAccountManagementService";

	public ServiceResponse changeAccountStatus(String custID,
			String accountNumber, String mobileNumber, String emailId,
			String status, String initiatedBy, String remarks,String agentId) 
	{
		long requestID = Util.getRequestId();
		ServiceResponse response = null;
		String lClassName =  this.getClass().getName();
		String lMethodName = "changeAccountStatus";
		soapLogger.info("************************");
		soapLogger.info("Request ID : "+requestID+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		try {
			Customer changeAccountStatus = ((CustomerAccountManagementService) ServiceLocator
					.getService(service)).changeAccountStatus(custID,
					accountNumber, mobileNumber, emailId, status, initiatedBy,
					remarks,agentId,requestID);

			if (changeAccountStatus != null) {
				response = new ServiceResponse(changeAccountStatus);
			} else {
				response = new ServiceResponse(1,
						"No record Found in Customer Account Management");
			}
		} catch (Throwable t) {
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			response = new ServiceResponse(1, t.getMessage());
		}
		soapLogger.info("Request ID : "+requestID+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
		soapLogger.info("************************");
		return response;
	}

	// Working
	public ServiceResponse blockOrUnblockAccount(String custID,
			String accountNumber, String mobileNumber, String emailId,
			String status, String initiatedBy, String remarks,String agentId) {
		String lClassName =  this.getClass().getName();
		long requestID = Util.getRequestId();
		String lMethodName = "blockOrUnblockAccount";
		soapLogger.info("************************");
		soapLogger.info("Request ID : "+requestID+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		ServiceResponse response = null;
		try {
			Customer blockOrUnblockAccount = ((CustomerAccountManagementService) ServiceLocator
					.getService(service)).blockOrUnblockAccount(custID,
					accountNumber, mobileNumber, emailId, status, initiatedBy,
					remarks,agentId,requestID);
			if (blockOrUnblockAccount != null) {
				response = new ServiceResponse(blockOrUnblockAccount);
			} else {
				response = new ServiceResponse(1,
						"No record Found in Customer Account Management");
			}

			/*
			 * } catch (DataAccessException e) { logger.error(
			 * "Exception catched while doing get Customer Account Management ",
			 * e); response = new ServiceResponse(1,
			 * "No record Found in Customer Account Management"); }
			 */
		}catch (Throwable t) {
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			response = new ServiceResponse(1, t.getMessage());
		}

		soapLogger.info("Request ID : "+requestID+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
		soapLogger.info("************************");
		return response;
	}

	public ServiceResponse terminateAccount(String custID, String mobileNumber,
			String initiatedBy, String remarks,String agentId) 
	{
		long requestId = Util.getRequestId();
		String lClassName =  this.getClass().getName();
		String lMethodName = "terminateAccount";
		soapLogger.info("************************");
		soapLogger.info("Request ID : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		ServiceResponse response = null;
		try 
		{
			Customer terminateAccount = ((CustomerAccountManagementService) ServiceLocator
					.getService(service)).terminateAccount(custID,
					mobileNumber, initiatedBy, remarks,agentId,requestId);

			if (terminateAccount != null) {
				response = new ServiceResponse("Wallet Succesfully Close");
			} else {
				response = new ServiceResponse(1,
						"No record Found in Customer Account Management");
			}
		}catch (Throwable t) 
		{
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			response = new ServiceResponse(1, t.getMessage());
		}
		soapLogger.info("Request ID : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
		soapLogger.info("************************");
		return response;
	}
}
