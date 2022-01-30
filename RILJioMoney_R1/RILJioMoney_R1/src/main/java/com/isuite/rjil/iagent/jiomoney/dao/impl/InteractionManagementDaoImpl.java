package com.isuite.rjil.iagent.jiomoney.dao.impl;



import im.com.ril.rpsl.entities.interaction_v1_0.TEntityType;
import im.com.ril.rpsl.entities.interaction_v1_0.TInteraction;
import im.com.ril.rpsl.services.interactionmanagement_v1_0.CreateInteractionRq;
import im.com.ril.rpsl.services.servicerequestmanagement_v1_0.CreateServiceRequestRs;
import im.com.ril.rpsl.wsdls.interactionmanagement_v1_0.InteractionManagementFault;
import im.com.ril.rpsl.wsdls.interactionmanagement_v1_0.InteractionManagementServiceagent;
import im.com.ril.rpsl.wsdls.interactionmanagement_v1_0.Operations;

import java.net.URL;
import java.util.List;
import java.util.Properties;


import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.common.Channel;
import com.isuite.rjil.iagent.jiomoney.common.CustomerInteraction;
import com.isuite.rjil.iagent.jiomoney.dao.InteractionManagementDao;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;
import com.isuite.rjil.iagent.jiomoney.util.PropertiesUtil;
import com.isuite.rjil.iagent.common.util.RequestUtils;
import com.isuite.rjil.iagent.jiomoney.util.Util;
import com.isuite.rjil.iagent.jiomoney.util.WSHandlerResolver;
import com.isuite.rjil.iagent.jiomoney.util.XMLGregorianCalendarUtil;


/**
 * Title: InteractionManagementDaoImpl.java <br>
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
public class InteractionManagementDaoImpl implements InteractionManagementDao {
	private static final Logger logger = Logger.getLogger(InteractionManagementDaoImpl.class);
	private static final Logger soapLogger = Logger.getLogger(LOVUtil.SOAPLOGGER);
	private Properties platformProperties = PropertiesUtil.getProperties(PropertiesUtil.platform);

	@Override
	public CustomerInteraction createInteraction(String interactionRefNo,String interactionDate, String category, String subCategory,String subSubCategory, String category4, String description, String callDuration,
			String custId, String channel, String agentCode,String verificationQuestionAir, String action, String notes,List<String> verificationQuestion,String pCommunicationMode,long requestId) throws DataAccessException 
	{
		String lClassName =  this.getClass().getName();
		String lMethodName = "createInteraction";
		
		soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentCode+"|["+lClassName +"."+lMethodName+" ]| Start");
		try 
		{

			/************** Setting All Request *******/
			TInteraction interactionRequest = new TInteraction();
			// interactionRequest.setInteractionRefNo(interactionRefNo);
			// XMLGregorianCalendarUtil.setTime(true);
			XMLGregorianCalendar dateObj = XMLGregorianCalendarUtil.getDateTime();
			interactionRequest.setInteractionDate(dateObj);
			interactionRequest.setCategory1(category);
			interactionRequest.setAction(action);
			interactionRequest.setCategory2(subCategory);
			interactionRequest.setCategory3(subSubCategory);
			interactionRequest.setCategory4(category4);
			interactionRequest.setEntityId(custId.toUpperCase());
			interactionRequest.setNote(notes);
			interactionRequest.setInteractionTypeDesc(description);
			// interactionRequest.setChannel(properties.getProperty("channel"));
			String lstrChanel = Channel.getValue(channel);
			interactionRequest.setChannel(lstrChanel);
			if(Util.isEmptyString(lstrChanel))
			{
				interactionRequest.setChannel(Channel.CALL_CENTRE.getValue());
			}
			interactionRequest.setNote(notes);
			interactionRequest.setAgentId(agentCode);
			interactionRequest.setEntityType(TEntityType.C);
			if (verificationQuestion != null & verificationQuestion.size() > 0) {
				for (String string : verificationQuestion) {
					interactionRequest.getVerificationQuestionair().add(string);
				}

			}
			CreateInteractionRq request = new CreateInteractionRq();
			//String lstrCommunicationMode = CommunicationMode.getValue(pCommunicationMode);
			//TMiscEntities lEntities = new TMiscEntities();
			//TNameValue lNameValue = new TNameValue();
			//lNameValue.setName(CommunicationMode.COMMUNICATION_MODE_PARAM_NAME);
//			if(Util.isEmptyString(lstrCommunicationMode)==false)
//			{
//				lNameValue.setValue(lstrCommunicationMode);
//				if(lEntities.getNameValue() != null)
//				{
//					lEntities.getNameValue().add(lNameValue);
//					
//				}
//			}
//			else
//			{
//				if(lEntities.getNameValue() != null)
//				{
//					lNameValue.setValue(CommunicationMode.CALL.getValue());
//					lEntities.getNameValue().add(lNameValue);
//					
//				}
//			}
//			request.setMiscEntities(lEntities);
			/************************* Setting Request ********************/
			
			request.setInteraction(interactionRequest);
			request.setRqHeader(RequestUtils.getRequestHeader());

			/******************* Setting the WSDL URL ******************/
			String interactionManagementWSDL_URL = platformProperties
					.getProperty("interactionManagement");
			URL wsdlURL = new URL(interactionManagementWSDL_URL);
			InteractionManagementServiceagent serviceagent = new InteractionManagementServiceagent(
					wsdlURL);
			serviceagent.setHandlerResolver(new WSHandlerResolver(agentCode,requestId	));
			Operations endPoint = serviceagent.getOperationsEndpoint();
			/******************* Setting the endpoint URL For InteractionManagement ******************/
			String interactionManagementEndPoint = platformProperties
					.getProperty("interactionManagementEndPoint");
			BindingProvider bindingProvider = (BindingProvider) endPoint;
			bindingProvider.getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
					interactionManagementEndPoint);

			/************************ Response ************************************************/
			CreateServiceRequestRs response = endPoint
					.createInteraction(request);
			if (response != null
					&& response.getResponseStatus() != null
					&& response.getResponseStatus().getStatus() != null
					&& response.getResponseStatus().getStatus()
							.equalsIgnoreCase("FAILED")) {
				if (response.getResponseStatus().getErrors() != null
						&& response.getResponseStatus().getErrors().getError() != null
						&& response.getResponseStatus().getErrors().getError()
								.size() > 0
						&& response.getResponseStatus().getErrors().getError()
								.get(0).getErrorMsg() != null) {
					throw new DataAccessException(response.getResponseStatus()
							.getErrors().getError().get(0).getErrorMsg());
				}
			}
			CustomerInteraction createIRPojo = null;

			if (response != null) {
				createIRPojo = new CustomerInteraction();
				createIRPojo.setInteractionRefNo(response.getProblemRefNo() != null ? response
								.getProblemRefNo() : "");
				/*
				 * createIRPojo .setEstResolutionTime(response
				 * .getEstimatedCompletionTime() != null ?
				 * XMLGregorianCalendarUtil.convertDateFormat(
				 * response.getEstimatedCompletionTime(), "yyyymmddhhmmss",
				 * "dd-MM-yyyy HH:mm:ss") : "");
				 */
			}
		/*	if (response != null && response.getResponseStatus() != null) {
				if (response.getResponseStatus().getInfos() != null) {
					for (TInfo tInfoResponse : response.getResponseStatus()
							.getInfos().getInfo()) {
						createIRPojo.setProviderStatus(HeaderStatus.setStatus(
								tInfoResponse.getCode(),
								tInfoResponse.getMsg(), tInfoResponse
										.getProvider().getProviderName(),
								tInfoResponse.getProvider().getProviderCode()));
					}
				}
			}
			if (response != null && response.getRsHeader() != null) {
			
				if (response.getRsHeader().getMessageContext() != null) {
					createIRPojo.setHeaderMessage(HeaderStatus.setMessage(
							response.getRsHeader().getMessageContext()
									.getBusinessID(), response.getRsHeader()
									.getMessageContext().getEsbID(), response
									.getRsHeader().getMessageContext()
									.getMessageID(), response.getRsHeader()
									.getMessageContext().getCorrelationID(),
							response.getRsHeader().getMessageContext()
									.getExtCorrelationID(), response
									.getRsHeader().getMessageContext()
									.getTimeStamp()));
				}

				
				if (response.getRsHeader().getBusinessApplicationContext() != null) {
					createIRPojo.setBusinessApplication(HeaderStatus
							.setBusinessApplication(response.getRsHeader()
									.getBusinessApplicationContext()
									.getServiceVersion(), response
									.getRsHeader()
									.getBusinessApplicationContext()
									.getOperationVersion(), response
									.getRsHeader()
									.getBusinessApplicationContext()
									.getOperationName(), response.getRsHeader()
									.getBusinessApplicationContext()
									.getMessageType(), response.getRsHeader()
									.getBusinessApplicationContext()
									.getSrcApplicationname()));
				}
			}
			*/
			return createIRPojo;
		} catch (InteractionManagementFault e) 
		{
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", e);
			logger.error("Exception in ["+lClassName+"."+lMethodName+"]", e);
			String exception = "";
			if (e.getFaultInfo() != null
					&& e.getFaultInfo().getErrors() != null
					&& e.getFaultInfo().getErrors().getError() != null
					&& e.getFaultInfo().getErrors().getError().size() > 0
					&& e.getFaultInfo().getErrors().getError().get(0)
							.getErrorMsg() != null) {
				exception = e.getFaultInfo().getErrors().getError().get(0)
						.getErrorMsg();
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
			soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentCode+"|["+lClassName +"."+lMethodName+"] | End");
			
		}

	}

}