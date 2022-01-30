package com.isuite.rjil.iagent.jiomoney.dao.impl;

import static com.isuite.rjil.iagent.jiomoney.util.Util.isEmptyString;

import java.net.URL;
import java.util.Properties;

import javax.xml.ws.BindingProvider;

import org.apache.log4j.Logger;

import servicerm.com.ril.rpsl.entities.commontypes_v1_0.TInfo;
import servicerm.com.ril.rpsl.entities.servicerequest_v1_0.TEntityType;
import servicerm.com.ril.rpsl.entities.servicerequest_v1_0.TService;
import servicerm.com.ril.rpsl.services.servicerequestmanagement_v1_0.CreateServiceRequestRq;
import servicerm.com.ril.rpsl.services.servicerequestmanagement_v1_0.CreateServiceRequestRs;
import servicerm.com.ril.rpsl.services.servicerequestmanagement_v1_0.UpdateServiceRequestRq;
import servicerm.com.ril.rpsl.services.servicerequestmanagement_v1_0.UpdateServiceRequestRs;
import servicerm.com.example.xmlns._1465280432796.ServiceRequestManagementFault;
import servicerm.com.example.xmlns._1465280432796.ServiceRequestManagementServiceagent;

import com.isuite.rjil.iagent.jiomoney.common.Channel;
import com.isuite.rjil.iagent.jiomoney.common.CustomerProblem;
import com.isuite.rjil.iagent.jiomoney.dao.ServiceRequestManagementDao;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.util.HeaderStatus;
import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;
import com.isuite.rjil.iagent.jiomoney.util.PropertiesUtil;
import com.isuite.rjil.iagent.jiomoney.util.Util;
import com.isuite.rjil.iagent.jiomoney.util.WSHandlerResolver;
import com.isuite.rjil.iagent.jiomoney.util.XMLGregorianCalendarUtil;

import static com.isuite.rjil.iagent.common.util.RequestUtils.*;

public class ServiceRequesManagementDaoImpl implements ServiceRequestManagementDao
{
	private static final Logger logger = Logger.getLogger(ServiceRequesManagementDaoImpl.class);
	private static final Logger soapLogger = Logger.getLogger(LOVUtil.SOAPLOGGER);
	private Properties platformProperties = PropertiesUtil
			.getProperties(PropertiesUtil.platform);

	public CustomerProblem createServiceRequest(String entityId,
			String category, String subCategory, String subSubCategory,String subSubSubCategory,
			String description, String problemRefNo, String impact,
			String urgency, String channel, String agentCode, String note,
			String mobileNo, String emailId, String entityType, String status,
			String communicationMode,long requestId) throws DataAccessException 
	{
		String lClassName =  this.getClass().getName();
		String lMethodName = "createServiceRequest";
		
		soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentCode+"|["+lClassName +"."+lMethodName+" ]| Start");
		try 
		{
			
			/*//******************* Setting the WSDL URL ******************/

			TService serviceRequest = new TService();
			serviceRequest.setDescription(description);
			serviceRequest.setProblemRefNo(problemRefNo);
			serviceRequest.setImpact(impact);
			serviceRequest.setUrgency(urgency);
			String lstrChanel = Channel.getValue(channel);
			serviceRequest.setChannel(lstrChanel);
			if(isEmptyString(lstrChanel))
			{
				serviceRequest.setChannel(Channel.CALL_CENTRE.getValue());
			}
			serviceRequest.setNote(note);
			serviceRequest.setCategory3(subSubCategory);
			serviceRequest.setCategory4(subSubSubCategory);
			serviceRequest.setEntityId(entityId);
			serviceRequest.setAgentId(agentCode);
			//String lstrCommunicationMode = CommunicationMode.getValue(communicationMode);
			//serviceRequest.setCommunicationMode(lstrCommunicationMode);
//			if(isEmptyString(lstrCommunicationMode))
//			{
//				serviceRequest.setCommunicationMode(CommunicationMode.CALL.getValue());
//			}
			serviceRequest.setCreationDate(XMLGregorianCalendarUtil
					.getDateTime());

			if (entityType.equalsIgnoreCase("C")) {
				serviceRequest.setEntityType(TEntityType.C);
			}
			if (entityType.equalsIgnoreCase(TEntityType.M.toString())) {
				serviceRequest.setEntityType(TEntityType.M);
			}
			if (entityType.equalsIgnoreCase(TEntityType.D.toString())) {
				serviceRequest.setEntityType(TEntityType.D);
			}
			// serviceRequest.setStatus(status);

			CreateServiceRequestRq request = new CreateServiceRequestRq();
			request.setService(serviceRequest);
			request.setRqHeader(getRequestHeader());
			request.setRegisteredEmail(emailId);
			request.setRegisteredMobileNumber(mobileNo);
			/************************ Response ************************************************/
			String serviceRequestManagement_WS_URL = platformProperties
					.getProperty("serviceRequestManagement");
			URL serviceRequestManagementWsdlLocation = new URL(
					serviceRequestManagement_WS_URL);

			ServiceRequestManagementServiceagent SRManagementServiceAgent = new ServiceRequestManagementServiceagent(
					serviceRequestManagementWsdlLocation);
			SRManagementServiceAgent.setHandlerResolver(new WSHandlerResolver(agentCode,requestId));
			servicerm.com.example.xmlns._1465280432796.Operations SRManagementEndPoint = SRManagementServiceAgent
					.getOperationsEndpoint();

			/******************* Setting the endpoint URL For service Request Management ******************/
			String SRManagementendPointURL = platformProperties
					.getProperty("serviceRequestManagementEndPoint");
			BindingProvider SRManagementbindingProvider = (BindingProvider) SRManagementEndPoint;
			SRManagementbindingProvider.getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
					SRManagementendPointURL);
			CreateServiceRequestRs response = SRManagementEndPoint
					.createServiceRequest(request);
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
			CustomerProblem createSRPojo = null;
			if (response != null) {
				createSRPojo = new CustomerProblem();
				createSRPojo
						.setProblemRefNo(response.getProblemRefNo() != null ? response
								.getProblemRefNo() : "");
				if(!(Util.isEmptyString(response.getEstimatedCompletionTime()))&& !(response.getEstimatedCompletionTime().equals("0"))) {
					createSRPojo.setEstResolutionTime(XMLGregorianCalendarUtil
							.convertDateFormat(
									response.getEstimatedCompletionTime(),
									"yyyyMMddhhmmss", "dd-MM-yyyy hh:mm:ss"));
				}else{
					createSRPojo.setEstResolutionTime("");
				}
				
				createSRPojo.setServiceRequestId("");
				createSRPojo.setIsSRExist(response.getSRExist() != null ? response
								.getSRExist() : "");
				createSRPojo.setSRExistReturnMsg(response.getReturnMsg() != null ? response
						.getReturnMsg() : "");

				// ***************************
				// ResponseDetails**************************/
				/****************************** ResponseStatus ********************/
				if (response.getResponseStatus() != null) {
					if (response.getResponseStatus().getInfos() != null) {
						for (TInfo tInfoResponse : response.getResponseStatus()
								.getInfos().getInfo()) {
							createSRPojo.setProviderStatus(HeaderStatus
									.setStatus(tInfoResponse.getCode(),
											tInfoResponse.getMsg(),
											tInfoResponse.getProvider()
													.getProviderName(),
											tInfoResponse.getProvider()
													.getProviderCode()));
						}
					}
				}
				if (response.getRsHeader() != null) {
					/****************************** MessageContext ********************/
					if (response.getRsHeader().getMessageContext() != null) {
						createSRPojo.setHeaderMessage(HeaderStatus.setMessage(
								response.getRsHeader().getMessageContext()
										.getBusinessID(), response
										.getRsHeader().getMessageContext()
										.getEsbID(), response.getRsHeader()
										.getMessageContext().getMessageID(),
								response.getRsHeader().getMessageContext()
										.getCorrelationID(), response
										.getRsHeader().getMessageContext()
										.getExtCorrelationID(), response
										.getRsHeader().getMessageContext()
										.getTimeStamp()));
					}
					/********************** BusinessApplicationContext ********************/
					if (response.getRsHeader().getBusinessApplicationContext() != null) {
						createSRPojo.setBusinessApplication(HeaderStatus
								.setBusinessApplication(response.getRsHeader()
										.getBusinessApplicationContext()
										.getServiceVersion(), response
										.getRsHeader()
										.getBusinessApplicationContext()
										.getOperationVersion(), response
										.getRsHeader()
										.getBusinessApplicationContext()
										.getOperationName(), response
										.getRsHeader()
										.getBusinessApplicationContext()
										.getMessageType(), response
										.getRsHeader()
										.getBusinessApplicationContext()
										.getSrcApplicationname()));

					}
				}

			}
			return createSRPojo;
		} catch (ServiceRequestManagementFault e) {
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
			logger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			throw new DataAccessException(t.getMessage(), t);
		}
		
		finally
		{
			soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentCode+"|["+lClassName +"."+lMethodName+"] | End");
			
		}
	}


	public CustomerProblem updateServiceRequest(String custId,
			String problemRefNo, String status, String impact, String urgency,
			String channel, String agentCode, String note, String mobileNumber,
			String email, String owner, String teamMember,long requestId)
			throws DataAccessException 
	{
		String lClassName =  this.getClass().getName();
		String lMethodName = "updateServiceRequest";
		
		soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentCode+"|["+lClassName +"."+lMethodName+" ]| Start");
		try {
			TService service = new TService();
			service.setEntityType(TEntityType.C);
			service.setProblemRefNo(problemRefNo);
			if(!Util.isEmptyString(status)){
			service.setStatus(status);
			}
			if(!Util.isEmptyString(impact)){
			service.setImpact(impact);
			}
			if(!Util.isEmptyString(urgency)){
			service.setUrgency(urgency);
			}
			//service.setChannel(platformProperties.getProperty("channel"));
			if(!Util.isEmptyString(note)){
				String strDate = XMLGregorianCalendarUtil.convertDateFormat(XMLGregorianCalendarUtil.getSystemTime(),"yyyy-MM-dd HH:mm:ss", "dd-MM-yyyy '|' HH:mm:ss");
			String	strNote= agentCode+"|"+strDate+":"+note;
			service.setNote(strNote);
			}
			if(!Util.isEmptyString(custId)){
			service.setEntityId(custId.toUpperCase());
			}
			if(!Util.isEmptyString(agentCode)){
			service.setAgentId(agentCode);
			}
			if(!Util.isEmptyString(owner)){
			service.setOwner(owner);
			}
			if(!Util.isEmptyString(teamMember)){
			service.setServiceTeam(teamMember);
			}
			UpdateServiceRequestRq request = new UpdateServiceRequestRq();
			request.setService(service);
			if(!Util.isEmptyString(email)){
			request.setRegisteredEmail(email);
			}
			if(!Util.isEmptyString(mobileNumber)){
			request.setRegisteredMobileNumber(mobileNumber);
			}
			request.setRqHeader(getRequestHeader());
			/******************* Setting the WSDL URL ******************/
			String serviceRequestManagement_WS_URL = platformProperties
					.getProperty("serviceRequestManagement");
			URL serviceRequestManagementWsdlLocation = new URL(
					serviceRequestManagement_WS_URL);
			ServiceRequestManagementServiceagent SRManagementServiceAgent = new ServiceRequestManagementServiceagent(
					serviceRequestManagementWsdlLocation);
			SRManagementServiceAgent.setHandlerResolver(new WSHandlerResolver(agentCode,requestId));
			servicerm.com.example.xmlns._1465280432796.Operations SRManagementEndPoint = SRManagementServiceAgent
					.getOperationsEndpoint();

			/******************* Setting the endpoint URL For service Request Management ******************/
			String SRManagementendPointURL = platformProperties
					.getProperty("serviceRequestManagementEndPoint");
			BindingProvider SRManagementbindingProvider = (BindingProvider) SRManagementEndPoint;
			SRManagementbindingProvider.getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
					SRManagementendPointURL);

			/************************ Response ************************************************/
			UpdateServiceRequestRs response = SRManagementEndPoint
					.updateServiceRequest(request);

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

			CustomerProblem updateSRPojo = null;
			if (response != null) {
				updateSRPojo = new CustomerProblem();
				updateSRPojo
						.setProblemRefNo(response.getProblemRefNo() != null ? response
								.getProblemRefNo() : "");

				// ***************************
				// ResponseDetails**************************/
				/****************************** ResponseStatus ********************/
				if (response.getResponseStatus() != null) {
					if (response.getResponseStatus().getInfos() != null) {
						for (TInfo tInfoResponse : response.getResponseStatus()
								.getInfos().getInfo()) {
							updateSRPojo.setProviderStatus(HeaderStatus
									.setStatus(tInfoResponse.getCode(),
											tInfoResponse.getMsg(),
											tInfoResponse.getProvider()
													.getProviderName(),
											tInfoResponse.getProvider()
													.getProviderCode()));
						}
					}
				}
				if (response.getRsHeader() != null) {
					/****************************** MessageContext ********************/
					if (response.getRsHeader().getMessageContext() != null) {
						updateSRPojo.setHeaderMessage(HeaderStatus.setMessage(
								response.getRsHeader().getMessageContext()
										.getBusinessID(), response
										.getRsHeader().getMessageContext()
										.getEsbID(), response.getRsHeader()
										.getMessageContext().getMessageID(),
								response.getRsHeader().getMessageContext()
										.getCorrelationID(), response
										.getRsHeader().getMessageContext()
										.getExtCorrelationID(), response
										.getRsHeader().getMessageContext()
										.getTimeStamp()));
					}

					/********************** BusinessApplicationContext ********************/
					if (response.getRsHeader().getBusinessApplicationContext() != null) {
						updateSRPojo.setBusinessApplication(HeaderStatus
								.setBusinessApplication(response.getRsHeader()
										.getBusinessApplicationContext()
										.getServiceVersion(), response
										.getRsHeader()
										.getBusinessApplicationContext()
										.getOperationVersion(), response
										.getRsHeader()
										.getBusinessApplicationContext()
										.getOperationName(), response
										.getRsHeader()
										.getBusinessApplicationContext()
										.getMessageType(), response
										.getRsHeader()
										.getBusinessApplicationContext()
										.getSrcApplicationname()));
					}
				}

			}
			return updateSRPojo;
		} catch (ServiceRequestManagementFault e) {
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
			logger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			throw new DataAccessException(t.getMessage(), t);
		}finally
		{
			soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentCode+"|["+lClassName +"."+lMethodName+"] | End");
			
		}
	}
}
