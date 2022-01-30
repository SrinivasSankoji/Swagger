package com.isuite.rjil.iagent.jiomoney.dao.impl;

import java.net.URL;
import java.util.Properties;
import java.util.UUID;

import javax.xml.ws.BindingProvider;

import notimanag.com.ril.rpsl.services.notificationmanagement_v1_0.SubmitNotificationRequest;
import notimanag.com.ril.rpsl.services.notificationmanagement_v1_0.SubmitNotificationRequest.Characterstics;
import notimanag.com.ril.rpsl.services.notificationmanagement_v1_0.SubmitNotificationRequest.Characterstics.Item;
import notimanag.com.ril.rpsl.services.notificationmanagement_v1_0.SubmitNotificationResponse;
import notimanag.com.ril.rpsl.wsdls.notificationmanagement_v1_0.NotificationManagementServiceagent;
import notimanag.com.ril.rpsl.wsdls.notificationmanagement_v1_0.Operations;
import notimanag.com.ril.rpsl.wsdls.notificationmanagement_v1_0.SubmitNotificationFault;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.common.Notification;
import com.isuite.rjil.iagent.jiomoney.dao.NotificationMgmtDao;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;
import com.isuite.rjil.iagent.jiomoney.util.PropertiesUtil;
import com.isuite.rjil.iagent.jiomoney.util.WSHandlerResolver;

/**
 * Title: NotificationMgmtDaoImpl.java <br>
 * Company: 2014-15 NovelVox Software Pvt Ltd<br>
 * 
 * @author Anand Raman(AR)
 * @version Build 0.5 <br>
 *          Changes and requirement:<br>
 *          <ul>
 * 
 *          </ul>
 * @version ReleaseR1-Sprint 3
 * 
 */
public class NotificationMgmtDaoImpl implements NotificationMgmtDao {
	private static final Logger logger = Logger
			.getLogger(NotificationMgmtDaoImpl.class);
	private static final Logger soapLogger = Logger
			.getLogger(LOVUtil.SOAPLOGGER);
	private Properties platformProperties = PropertiesUtil
			.getProperties(PropertiesUtil.platform);

	@Override
	public Notification submitNotification(String notification_Template_ID,
			String channel, String communication_Type,
			String destination_Address, String name, String value,
			String agentId,long requestId) throws DataAccessException {
		String lClassName =  this.getClass().getName();
		String lMethodName = "submitNotification";
		
		soapLogger.info("Request ID : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		Notification nmPojo = null;
		try {
		
			Characterstics charactersticsRequest = new Characterstics();
			Item itemRequest = new Item();
			itemRequest.setName(name);
			itemRequest.setValue(value);
			charactersticsRequest.getItem().add(itemRequest);

			SubmitNotificationRequest request = new SubmitNotificationRequest();
			UUID uid = UUID.randomUUID();
			request.setNotificationRefID(String.valueOf(uid));
			request.setNotificationTemplateID(notification_Template_ID);
			request.setChannel(channel);
			request.setCommunicationType(communication_Type);
			request.setDestinationAddress(destination_Address);
			request.setCharacterstics(charactersticsRequest);
			/******************* Setting the WSDL URL ******************/

			String notificationManagement_WS_URL = platformProperties
					.getProperty("notificationManagement");
			URL wsdlURL = new URL(notificationManagement_WS_URL);
			NotificationManagementServiceagent serviceagent = new NotificationManagementServiceagent(
					wsdlURL);

			serviceagent.setHandlerResolver(new WSHandlerResolver(agentId,requestId				));
			Operations endPoint = serviceagent.getOperationsEndpoint1();

			/******************* Setting the endpoint URL For NotificationMgmt ******************/
			String notificationManagementEndPointURL = platformProperties
					.getProperty("notificationManagementImplEndPoint");
			BindingProvider bindingProvider = (BindingProvider) endPoint;
			bindingProvider.getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
					notificationManagementEndPointURL);
			/************************ Response ************************************************/
			SubmitNotificationResponse response = endPoint
					.submitNotification(request);
			if (response != null) {
				nmPojo = new Notification();
				/******************* Notification mgmt Info ******************/
				nmPojo.setNotificationRefId(response.getNotificationRefID() != null ? response
						.getNotificationRefID() : "");
				nmPojo.setNotificationTemplateId(response
						.getNotificationTemplateID() != null ? response
						.getNotificationTemplateID() : "");
				nmPojo.setStatus(response.getStatus() != null ? response
						.getStatus() : "");
			}
		} catch (SubmitNotificationFault e) 
		{
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", e);
			logger.error("Exception in ["+lClassName+"."+lMethodName+"]", e);
			String exception = "";
			if (e.getFaultInfo() != null
					&& e.getFaultInfo().getDescription() != null
					&& e.getFaultInfo().getDescription() != null
					&& e.getFaultInfo().getDescription() != null) {
				exception = e.getFaultInfo().getDescription();
				throw new DataAccessException(exception, e);
			} else {
				throw new DataAccessException(e.getMessage(), e);
			}
		} catch (Throwable t) {
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			logger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			throw new DataAccessException(t.getMessage(), t);
		}
		finally
		{
			soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
			
		}
		return nmPojo;
	}

	@Override
	public Notification submitNotificationbyEmailId(
			String notification_Template_ID, String channel,
			String communication_Type, String destination_Address, String name,
			String value, String smsemailidname, String smsemailidvalue,
			String agentId,long requestId) throws DataAccessException {
		String lClassName =  this.getClass().getName();
		String lMethodName = "submitNotificationbyEmailId";
		
		soapLogger.info("Request ID : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		Notification nmPojo = null;
		try {
			
			Characterstics charactersticsRequest = new Characterstics();
			Item itemRequest = null;
			if (name != null && !name.isEmpty() && value != null
					&& !value.isEmpty()) {
				itemRequest = new Item();
				itemRequest.setName(name);
				itemRequest.setValue(value);
			}
			Item itemRequest1 = null;
			if (smsemailidname != null && !smsemailidname.isEmpty()
					&& smsemailidvalue != null && !smsemailidvalue.isEmpty()) {
				itemRequest1 = new Item();
				itemRequest1.setName(smsemailidname);
				itemRequest1.setValue(smsemailidvalue);
			}
			charactersticsRequest.getItem().add(itemRequest);
			charactersticsRequest.getItem().add(itemRequest1);

			SubmitNotificationRequest request = new SubmitNotificationRequest();
			UUID uid = UUID.randomUUID();
			request.setNotificationRefID(String.valueOf(uid));
			request.setNotificationTemplateID(notification_Template_ID);
			request.setChannel(channel);
			request.setCommunicationType(communication_Type);
			request.setDestinationAddress(destination_Address);
			request.setCharacterstics(charactersticsRequest);
			/******************* Setting the WSDL URL ******************/

			String notificationManagement_WS_URL = platformProperties
					.getProperty("notificationManagement");
			URL wsdlURL = new URL(notificationManagement_WS_URL);
			NotificationManagementServiceagent serviceagent = new NotificationManagementServiceagent(
					wsdlURL);

			serviceagent.setHandlerResolver(new WSHandlerResolver(agentId,requestId				));
			Operations endPoint = serviceagent.getOperationsEndpoint1();
			/******************* Setting the endpoint URL For NotificationMgmt ******************/
			String notificationManagementEndPointURL = platformProperties
					.getProperty("notificationManagementImplEndPoint");
			BindingProvider bindingProvider = (BindingProvider) endPoint;
			bindingProvider.getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
					notificationManagementEndPointURL);
			/************************ Response ************************************************/
			SubmitNotificationResponse response = endPoint
					.submitNotification(request);
			if (response != null) {
				nmPojo = new Notification();
				/******************* Notification mgmt Info ******************/
				nmPojo.setNotificationRefId(response.getNotificationRefID() != null ? response
						.getNotificationRefID() : "");
				nmPojo.setNotificationTemplateId(response
						.getNotificationTemplateID() != null ? response
						.getNotificationTemplateID() : "");
				nmPojo.setStatus(response.getStatus() != null ? response
						.getStatus() : "");
			}
		} catch (SubmitNotificationFault e) {
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", e);
			logger.error("Exception in ["+lClassName+"."+lMethodName+"]", e);
			String exception = "";
			if (e.getFaultInfo() != null
					&& e.getFaultInfo().getDescription() != null
					&& e.getFaultInfo().getDescription() != null
					&& e.getFaultInfo().getDescription() != null) {
				exception = e.getFaultInfo().getDescription();
				throw new DataAccessException(exception, e);
			} else {
				throw new DataAccessException(e.getMessage(), e);
			}
		} catch (Throwable t) {
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			logger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			throw new DataAccessException(t.getMessage(), t);
		}
		finally
		{
			soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
			
		}
		return nmPojo;

	}

}
