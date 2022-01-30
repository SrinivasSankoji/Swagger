package com.isuite.rjil.iagent.jiomoney.rpc;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.common.Notification;
import com.isuite.rjil.iagent.jiomoney.common.ServiceResponse;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.exception.ServiceException;
import com.isuite.rjil.iagent.jiomoney.services.NotificationMgmtService;
import com.isuite.rjil.iagent.jiomoney.services.ServiceLocator;
import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;
import com.isuite.rjil.iagent.jiomoney.util.Util;

public class NotificationMgmtRpc {
	private static final Logger soapLogger = Logger.getLogger(LOVUtil.SOAPLOGGER);
	private static final String service = "NotificationMgmtService";

	public ServiceResponse submitNotification(String Notification_Template_ID,
			String Channel, String Communication_Type,
			String Destination_Address, String Name, String Value,String agentId)
			throws ServiceException {
		ServiceResponse response = null;
		String lClassName =  this.getClass().getName();
		String lMethodName = "submitNotification";
		soapLogger.info("************************");
		long requestId = Util.getRequestId();
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		try {

			Notification submitNotification = ((NotificationMgmtService) ServiceLocator
					.getService(service)).submitNotification(
					Notification_Template_ID, Channel, Communication_Type,
					Destination_Address, Name, Value,agentId,requestId);

			if (submitNotification != null) {
				response = new ServiceResponse(submitNotification);
			} else {
				response = new ServiceResponse(1,
						"No record Found in Notification Management ");
			}
		} catch (ServiceException | DataAccessException t) {
			soapLogger.error(
					"Exception catched while doing get Instrument Inquiry ---> submitNotification ",
					t);
			response = new ServiceResponse(1, t.getMessage());
		}
		soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
		soapLogger.info("************************");
		return response;
	}
	
	public ServiceResponse submitNotificationbyEmailId(String Notification_Template_ID,
			String Channel, String Communication_Type,
			String Destination_Address, String Name, String Value,String smsemailidname,String smsemailidvalue,String agentId)
			throws ServiceException {
		ServiceResponse response = null;
		String lClassName =  this.getClass().getName();
		String lMethodName = "submitNotificationbyEmailId";
		soapLogger.info("************************");
		long requestId = Util.getRequestId();
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		try {

			Notification submitNotification = ((NotificationMgmtService) ServiceLocator
					.getService(service)).submitNotificationbyEmailId(
					Notification_Template_ID, Channel, Communication_Type,
					Destination_Address, Name, Value,smsemailidname,smsemailidvalue,agentId,requestId);

			if (submitNotification != null) {
				response = new ServiceResponse(submitNotification);
			} else {
				response = new ServiceResponse(1,
						"No record Found in Notification Management by emailid ");
			}
		} catch (ServiceException | DataAccessException t) {
			soapLogger.error(
					"Exception catched while doing get Instrument Inquiry ---> submitNotification ",
					t);
			response = new ServiceResponse(1, t.getMessage());
		}
		soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
		soapLogger.info("************************");
		return response;
	}
	
}
