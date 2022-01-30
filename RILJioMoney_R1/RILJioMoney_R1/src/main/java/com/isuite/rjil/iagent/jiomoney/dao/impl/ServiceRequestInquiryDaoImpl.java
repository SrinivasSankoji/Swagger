package com.isuite.rjil.iagent.jiomoney.dao.impl;

import java.net.URL;

import static com.isuite.rjil.iagent.common.util.RequestUtils.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.xml.ws.BindingProvider;

import org.apache.log4j.Logger;

import sri.com.ril.rpsl.entities.commontypes_v1_0.TDataRecord;

import sri.com.ril.rpsl.entities.servicerequest_v1_0.TCategoryDetails;
import sri.com.ril.rpsl.services.servicerequestinquiry_v1_0.FilterCriteria;
import sri.com.ril.rpsl.services.servicerequestinquiry_v1_0.GetDistrictRq;
import sri.com.ril.rpsl.services.servicerequestinquiry_v1_0.GetDistrictRs;
import sri.com.ril.rpsl.services.servicerequestinquiry_v1_0.GetServiceCategory1Rq;
import sri.com.ril.rpsl.services.servicerequestinquiry_v1_0.GetServiceCategory1Rs;
import sri.com.ril.rpsl.services.servicerequestinquiry_v1_0.GetServiceCategory2Rq;
import sri.com.ril.rpsl.services.servicerequestinquiry_v1_0.GetServiceCategory2Rs;
import sri.com.ril.rpsl.services.servicerequestinquiry_v1_0.GetServiceCategory3Rq;
import sri.com.ril.rpsl.services.servicerequestinquiry_v1_0.GetServiceCategory3Rs;
import sri.com.ril.rpsl.services.servicerequestinquiry_v1_0.GetServiceCategory4Rq;
import sri.com.ril.rpsl.services.servicerequestinquiry_v1_0.GetServiceCategory4Rs;
import sri.com.ril.rpsl.services.servicerequestinquiry_v1_0.SearchServiceRequestRq;
import sri.com.ril.rpsl.services.servicerequestinquiry_v1_0.SearchServiceRequestRs;
import sri.com.ril.rpsl.services.servicerequestinquiry_v1_0.StateVal;
import sri.com.ril.rpsl.services.servicerequestinquiry_v1_0.ValidateServiceRequestRq;
import sri.com.ril.rpsl.services.servicerequestinquiry_v1_0.ValidateServiceRequestRs;
import sri.com.ril.rpsl.services.servicerequestinquiry_v1_0.ViewServiceRequestRq;
import sri.com.ril.rpsl.services.servicerequestinquiry_v1_0.ViewServiceRequestRs;
import sri.com.ril.rpsl.wsdls.servicerequestinquiry_v1_0.Operations;
import sri.com.ril.rpsl.wsdls.servicerequestinquiry_v1_0.ServiceRequestInquiryFault;
import sri.com.ril.rpsl.wsdls.servicerequestinquiry_v1_0.ServiceRequestInquiry;

import com.isuite.rjil.iagent.jiomoney.common.Channel;
import com.isuite.rjil.iagent.jiomoney.common.CommunicationMode;
import com.isuite.rjil.iagent.jiomoney.common.CustomerProblem;
import com.isuite.rjil.iagent.jiomoney.common.ReferenceData;
import com.isuite.rjil.iagent.jiomoney.dao.ServiceRequestInquiryDao;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.exception.ServiceException;
import com.isuite.rjil.iagent.jiomoney.util.HeaderStatus;
import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;
import com.isuite.rjil.iagent.jiomoney.util.PropertiesUtil;

import com.isuite.rjil.iagent.jiomoney.util.WSHandlerResolver;
import com.isuite.rjil.iagent.jiomoney.util.XMLGregorianCalendarUtil;

import static com.isuite.rjil.iagent.jiomoney.util.Util.*;

/**
 * Title: ServiceRequestInquiryDaoImpl.java <br>
 * Company: 2014-15 NovelVox Software Pvt Ltd<br>
 * 
 * @author Anand Raman(AR)
 * @version Build 0.5 <br>
 *          Changes and requirement:<br>
 *          <ul>
 * 
 *          </ul>
 *          s
 * @version ReleaseR1-Sprint 3
 * 
 */
public class ServiceRequestInquiryDaoImpl implements ServiceRequestInquiryDao 
{
	private static final Logger soapLogger = Logger.getLogger(LOVUtil.SOAPLOGGER);
	private Properties platformProperties = PropertiesUtil.getProperties(PropertiesUtil.platform);
	private static final Logger logger = Logger.getLogger(ServiceRequestInquiryDaoImpl.class);
	URL mWSDLLocation = null;
	String mServiceEndPointURL = "";
	
	public ServiceRequestInquiryDaoImpl() 
	{
		try 
		{
			String serviceRequestInquiryWS_URL = platformProperties.getProperty("serviceRequestInquiry");
			mWSDLLocation = new URL(serviceRequestInquiryWS_URL);
			mServiceEndPointURL = platformProperties.getProperty("serviceRequestInquiryEndPoint");
		} 
		catch (Exception e) 
		{
			soapLogger.error("Error While Initializing ServiceRequestInquiryDaoImpl",e);
		}
	}

	@Override
	public CustomerProblem viewServiceRequest(String problemRefNo,String custType, String agentId,long requestId) throws DataAccessException 
	{
		String lClassName =  this.getClass().getName();
		String lMethodName = "viewServiceRequest";
		
		soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		try 
		{
			ViewServiceRequestRq request = new ViewServiceRequestRq();
			request.setProblemRefNo(problemRefNo);
			if (custType.equalsIgnoreCase(sri.com.ril.rpsl.entities.servicerequest_v1_0.TEntityType.C.toString())) 
			{
				request.setEntityType(sri.com.ril.rpsl.entities.servicerequest_v1_0.TEntityType.C);
			}
			if (custType.equalsIgnoreCase(sri.com.ril.rpsl.entities.servicerequest_v1_0.TEntityType.M.toString())) 
			{
				request.setEntityType(sri.com.ril.rpsl.entities.servicerequest_v1_0.TEntityType.M);
			}
			if (custType.equalsIgnoreCase(sri.com.ril.rpsl.entities.servicerequest_v1_0.TEntityType.D.toString())) 
			{
				request.setEntityType(sri.com.ril.rpsl.entities.servicerequest_v1_0.TEntityType.D);
			}
			request.setRqHeader(getRequestHeader());

			/******************* Setting the WSDL URL For SR Inquiry ******************/
			
			ServiceRequestInquiry serviceagent = new ServiceRequestInquiry(mWSDLLocation);
			serviceagent.setHandlerResolver(new WSHandlerResolver(agentId,requestId));
			Operations SRInquiryEndPoint = serviceagent.getOperationsEndpoint();
			/******************* Setting the END POINT URL For SR Inquiry ******************/
			BindingProvider bindingProvider = (BindingProvider) SRInquiryEndPoint;
			bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, mServiceEndPointURL);
			/************************ Response ************************************************/
			ViewServiceRequestRs response = SRInquiryEndPoint.viewServiceRequest(request);

			if (response != null&& response.getResponseStatus() != null && response.getResponseStatus().getStatus() != null && response.getResponseStatus().getStatus().equalsIgnoreCase("FAILED")) 
			{
				if (response.getResponseStatus().getErrors() != null && response.getResponseStatus().getErrors().getError() != null && response.getResponseStatus().getErrors().getError().size() > 0
						&& response.getResponseStatus().getErrors().getError().get(0).getErrorMsg() != null) 
				{
					throw new DataAccessException(response.getResponseStatus().getErrors().getError().get(0).getErrorMsg());
				}
			}
			CustomerProblem viewSRPojo = null;
			if (response != null) 
			{
				if (response.getService() != null) 
				{
					sri.com.ril.rpsl.entities.servicerequest_v1_0.TService lService = response.getService();
					viewSRPojo = new CustomerProblem();
					viewSRPojo.setProblemRefNo(getStringRepresentation(lService.getProblemRefNo()));
					viewSRPojo.setCategory(lService != null ? lService.getCategory1() != null ? lService.getCategory1() : "": "");
					viewSRPojo.setCategoryDescription(lService != null ? lService.getCategoryDesc1() != null ? lService.getCategoryDesc1() : "": "");
					viewSRPojo.setSubCategory(lService != null ? lService.getCategory2() != null ? lService.getCategory2() : "": "");
					viewSRPojo.setSubCategoryDescription(lService != null ? lService.getCategoryDesc2() != null ? lService.getCategoryDesc2() : "": "");
					viewSRPojo.setSubSubCategory(lService != null ? lService.getCategory3() != null ? lService.getCategory3() : "": "");
					viewSRPojo.setSubSubcategoryDescription(lService != null ? lService.getCategoryDesc3() != null ? lService.getCategoryDesc3() : "" : "");
					viewSRPojo.setCategory4(lService != null ? lService.getCategoryDesc4() != null ? lService.getCategoryDesc4() : "" : "");
					viewSRPojo.setDescription(lService != null ? lService.getDescription() != null ?lService.getDescription() : "": "");
					viewSRPojo.setCustomerId(lService != null ? lService.getEntityId() != null ? lService.getEntityId() : "": "");
					viewSRPojo.setStatus(lService != null ? lService.getStatus() != null ? lService.getStatus() : "": "");
					viewSRPojo.setStatusDesc(lService != null ? lService.getStatusDesc() != null ? lService.getStatusDesc() : "": "");
					viewSRPojo.setCreationDate(lService != null ? response.getService().getCreationDate() != null ? XMLGregorianCalendarUtil.parseDateFormat(lService.getCreationDate()) : "": "");

					String lstrChannel = lService.getChannel();
					if(isEmptyString(lstrChannel) == false)
					{
						viewSRPojo.setChannel(Channel.getDesc(lstrChannel));
					}
					else
					{
						viewSRPojo.setChannel(Channel.CALL_CENTRE.getDesc());
					}
					
//					if (lService.getChannel()
//							.equalsIgnoreCase("01")) {
//						viewSRPojo.setChannel("Call Centre");
//					}
					if (lService.getEstimatedResolutionTime() != null && "0".equals(lService.getEstimatedResolutionTime().toString())) 
					{
						viewSRPojo.setEstResolutionTime("");
					} 
					else 
					{
						viewSRPojo.setEstResolutionTime(lService != null ? lService.getEstimatedResolutionTime() != null ? XMLGregorianCalendarUtil.convertDateFormat(lService.getEstimatedResolutionTime(),"yyyyMMddHHmmss","dd-MM-yyyy HH:mm:ss") : "": "");
					}

					viewSRPojo.setAgentCode(lService != null ? lService.getAgentId() != null ? lService.getAgentId() : "": "");
					viewSRPojo.setProductId(lService != null ? lService.getProductId() != null ? lService.getProductId() : "": "");
					viewSRPojo.setProductName(lService != null ? lService.getProductName() != null ? lService.getProductName() : "": "");

					viewSRPojo.setImpact(lService != null ? lService.getImpact() != null ? lService.getImpact() : "": "");
					viewSRPojo.setUrgency(lService != null ? lService.getUrgency() != null ? lService.getUrgency() : "": "");

					viewSRPojo.setServiceTeam(lService != null ? lService.getServiceTeam() != null ? lService.getServiceTeam() : "": "");
					viewSRPojo.setEstSLA(lService != null ? lService.getEstimatedSLA() != null ? XMLGregorianCalendarUtil.convertDateFormat(lService.getEstimatedResolutionTime(),
							"yyyyMMddhhmmss",
							"dd-MM-yyyy HH:mm:ss") : "": "");
					viewSRPojo.setTat((String) (lService != null ? lService.getTat() != null ? lService.getTat() : "" : ""));
					viewSRPojo.setTat(lService != null ? lService.getTatUnit() != null ? lService.getTatUnit() : "" : "");
					viewSRPojo.setCommunicationMode(CommunicationMode.CALL.getDesc());
					if(lService.getCommunicationMode() != null && isEmptyString(lService.getCommunicationMode()) == false)
					{
						viewSRPojo.setCommunicationMode(CommunicationMode.getDesc(lService.getCommunicationMode()));
					}
					lService.getCommunicationMode();
					viewSRPojo.setOwner(lService.getOwner() != null ? lService.getOwner() : "");
					
					viewSRPojo.setComments(lService.getNote()!=null?lService.getNote():"");
					if(response.getMiscEntities()!= null && response.getMiscEntities().getNameValue() != null && response.getMiscEntities().getNameValue().isEmpty() ==false)
					{
						List<sri.com.ril.rpsl.entities.commontypes_v1_0.TNameValue> lList = response.getMiscEntities().getNameValue();
						for (sri.com.ril.rpsl.entities.commontypes_v1_0.TNameValue lNameValue : lList) 
						{
							if(lNameValue.getName().equalsIgnoreCase("Problem_Desc_Note"))
							{
								viewSRPojo.setNotes(getStringRepresentation(lNameValue.getValue()));
								break;
							}
						}
					}

				}
				// ***************************
				// ResponseDetails**************************/
				/****************************** ResponseStatus ********************/
//				if (response.getResponseStatus() != null) {
//					if (response.getResponseStatus().getInfos() != null) {
//						for (sri.com.ril.rpsl.entities.commontypes_v1_0.TInfo tInfoResponse : response
//								.getResponseStatus().getInfos().getInfo()) {
//							viewSRPojo.setProviderStatus(HeaderStatus
//									.setStatus(tInfoResponse.getCode(),
//											tInfoResponse.getMsg(),
//											tInfoResponse.getProvider()
//													.getProviderName(),
//											tInfoResponse.getProvider()
//													.getProviderCode()));
//						}
//					}
//				}
//				if (response.getRsHeader() != null) {
//					/****************************** MessageContext ********************/
//					if (response.getRsHeader().getMessageContext() != null) {
//						viewSRPojo.setHeaderMessage(HeaderStatus.setMessage(
//								response.getRsHeader().getMessageContext()
//										.getBusinessID(), response
//										.getRsHeader().getMessageContext()
//										.getEsbID(), response.getRsHeader()
//										.getMessageContext().getMessageID(),
//								response.getRsHeader().getMessageContext()
//										.getCorrelationID(), response
//										.getRsHeader().getMessageContext()
//										.getExtCorrelationID(), response
//										.getRsHeader().getMessageContext()
//										.getTimeStamp()));
//					}
//
//					/********************** BusinessApplicationContext ********************/
////					if (response.getRsHeader().getBusinessApplicationContext() != null) {
////						viewSRPojo.setBusinessApplication(HeaderStatus
////								.setBusinessApplication(response.getRsHeader()
////										.getBusinessApplicationContext()
////										.getServiceVersion(), response
////										.getRsHeader()
////										.getBusinessApplicationContext()
////										.getOperationVersion(), response
////										.getRsHeader()
////										.getBusinessApplicationContext()
////										.getOperationName(), response
////										.getRsHeader()
////										.getBusinessApplicationContext()
////										.getMessageType(), response
////										.getRsHeader()
////										.getBusinessApplicationContext()
////										.getSrcApplicationname()));
////					}
//				}
			}
			return viewSRPojo;
		} 
		catch (ServiceRequestInquiryFault e) 
		{
			logger.error("Exception in ["+lClassName+"."+lMethodName+"]", e);
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", e);
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
			} else 
			{
				throw new DataAccessException(e.getMessage(), e);
			}
		} 
		catch (Throwable t) 
		{
			logger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			throw new DataAccessException(t.getMessage(), t);
		}
		finally
		{
			soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
			
		}
	}

	@Override
	public List<CustomerProblem> searchServiceRequest(String entityType,
			String entityId, String urgency, String impact, String isSummaryRq,
			String owner, String serviceTeam, String status, String fromDate,
			String toDate, String pageSize, String offSet, String agentId,long requestId)
			throws DataAccessException 
	{
		String lClassName =  this.getClass().getName();
		String lMethodName = "searchServiceRequest";
		
		soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		List<CustomerProblem> customerProblemList = null;

		try 
		{
			FilterCriteria criteriaRequest = new FilterCriteria();
			if (entityType
					.equalsIgnoreCase(sri.com.ril.rpsl.entities.servicerequest_v1_0.TEntityType.C
							.toString())) {
				criteriaRequest
						.setEntityType(sri.com.ril.rpsl.entities.servicerequest_v1_0.TEntityType.C);
			}
			if (entityType
					.equalsIgnoreCase(sri.com.ril.rpsl.entities.servicerequest_v1_0.TEntityType.M
							.toString())) {
				criteriaRequest
						.setEntityType(sri.com.ril.rpsl.entities.servicerequest_v1_0.TEntityType.M);
			}
			if (entityType
					.equalsIgnoreCase(sri.com.ril.rpsl.entities.servicerequest_v1_0.TEntityType.D
							.toString())) {
				criteriaRequest
						.setEntityType(sri.com.ril.rpsl.entities.servicerequest_v1_0.TEntityType.D);
			}
			if ((urgency != null && !urgency.isEmpty())) {
				criteriaRequest.setUrgency(urgency);
			}
			if ((entityId != null && !entityId.isEmpty())) {
				//criteriaRequest.setEntityId(entityId);
				criteriaRequest.setFilterValue(entityId);
			}
			if ((impact != null && !impact.isEmpty())) {
				criteriaRequest.setImpact(impact);
			}

			if (status != null && !status.isEmpty()) {
				criteriaRequest.getStatus().add(status);
			}

			criteriaRequest.setFilterName("entityid");
			// if (isSummaryRq
			// .equalsIgnoreCase(sri.com.ril.rpsl.entities.servicerequest_v1_0.TFlag.Y
			// .toString())) {
			// criteriaRequest.setIsSummaryRq(TFlag.Y);
			// }
			if ((fromDate != null && !fromDate.isEmpty())
					&& (toDate != null && !toDate.isEmpty())) {
				// XMLGregorianCalendarUtil.setTime(true);
				//criteriaRequest = new FilterCriteria();
				criteriaRequest.setFromDate(XMLGregorianCalendarUtil
						.convertDateFormat(fromDate, "dd/MM/yyyy", "yyyyMMdd"));

				criteriaRequest.setToDate(XMLGregorianCalendarUtil
						.convertDateFormat(toDate, "dd/MM/yyyy", "yyyyMMdd"));

			}

			TDataRecord dataRecord = new TDataRecord();
			dataRecord.setOffsetValue(platformProperties
					.getProperty("pageOffSet"));
			dataRecord.setPagingSize(platformProperties
					.getProperty("maxRecord"));

			SearchServiceRequestRq request = new SearchServiceRequestRq();
			request.setFilterCriteria(criteriaRequest);
			request.setPageData(dataRecord);
			request.setRqHeader(getRequestHeader());

			/******************* Setting the WSDL URL For SR Inquiry ******************/
			
			

			ServiceRequestInquiry serviceagent = new ServiceRequestInquiry(mWSDLLocation);
			serviceagent.setHandlerResolver(new WSHandlerResolver(agentId, requestId			));
			Operations SRInquiryEndPoint = serviceagent.getOperationsEndpoint();
			BindingProvider bindingProvider = (BindingProvider) SRInquiryEndPoint;
			bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, mServiceEndPointURL);

			/************************ Response ************************************************/
			SearchServiceRequestRs response = SRInquiryEndPoint
					.searchServiceRequest(request);

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
			if (response != null) {
				CustomerProblem searchSRPojo = null;
				customerProblemList = new ArrayList<CustomerProblem>();
				/*************************** ResponseDetails **************************/
				/****************************** ResponseStatus ********************/
				if (response.getResponseStatus() != null) {
					if (response.getResponseStatus().getInfos() != null) {
						for (sri.com.ril.rpsl.entities.commontypes_v1_0.TInfo tInfoResponse : response
								.getResponseStatus().getInfos().getInfo()) {
							searchSRPojo = new CustomerProblem();
							searchSRPojo.setProviderStatus(HeaderStatus
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
						searchSRPojo = new CustomerProblem();
						searchSRPojo.setHeaderMessage(HeaderStatus.setMessage(
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
						searchSRPojo = new CustomerProblem();
						searchSRPojo.setBusinessApplication(HeaderStatus
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

				/**************************************** RESPONSE ******************************************/
				if (response.getService() != null
						&& response.getService().size() > 0) {
					for (sri.com.ril.rpsl.entities.servicerequest_v1_0.TService serviceResponse : response
							.getService()) {
						searchSRPojo = new CustomerProblem();
						searchSRPojo.setProblemRefNo(serviceResponse
								.getProblemRefNo() != null ? serviceResponse
								.getProblemRefNo() : "");
						searchSRPojo.setDescription(serviceResponse
								.getDescription() != null ? serviceResponse
								.getDescription() : "");
						searchSRPojo
								.setStatus(serviceResponse.getStatus() != null ? serviceResponse
										.getStatus() : "");
						searchSRPojo.setStatusDesc(getStringRepresentation(serviceResponse.getStatusDesc()));
						if(isEmptyString(serviceResponse.getStatusDesc()))
						{

							for (ReferenceData data : getStatus()) {
								if (serviceResponse.getStatus().equalsIgnoreCase(
										data.getCode())) {
									searchSRPojo.setStatusDesc(data
											.getDescription() != null ? data
											.getDescription() : "");
									// searchServiceRequest.add(custprob);
								}
							}
						}

						searchSRPojo
								.setCreationDate(serviceResponse
										.getCreationDate() != null ? XMLGregorianCalendarUtil
										.parseDateFormat(serviceResponse
												.getCreationDate()) : "");
						searchSRPojo
								.setCategory(serviceResponse.getCategory1() != null ? serviceResponse
										.getCategory1() : "");
						searchSRPojo.setCategoryDescription(serviceResponse
								.getCategoryDesc1() != null ? serviceResponse
								.getCategoryDesc1() : "");
						searchSRPojo.setSubCategory(serviceResponse
								.getCategory2() != null ? serviceResponse
								.getCategory2() : "");
						searchSRPojo.setSubCategoryDescription(serviceResponse
								.getCategoryDesc2() != null ? serviceResponse
								.getCategoryDesc2() : "");
						searchSRPojo.setSubSubCategory(serviceResponse
								.getCategory3() != null ? serviceResponse
								.getCategory3() : "");
						searchSRPojo
								.setSubSubcategoryDescription(serviceResponse
										.getCategoryDesc3() != null ? serviceResponse
										.getCategoryDesc3() : "");
						
						searchSRPojo
						.setCategory4(serviceResponse
								.getCategoryDesc4()!= null ? serviceResponse
								.getCategoryDesc4() : "");
						
						searchSRPojo
								.setAgentCode(serviceResponse.getAgentId() != null ? serviceResponse
										.getAgentId() : "");
						searchSRPojo
								.setOwner(serviceResponse.getOwner() != null ? serviceResponse
										.getOwner() : "");
						searchSRPojo
								.setUrgency(serviceResponse.getUrgency() != null ? serviceResponse
										.getUrgency() : "");
						searchSRPojo
								.setImpact(serviceResponse.getImpact() != null ? serviceResponse
										.getImpact() : "");
						searchSRPojo.setServiceTeam(serviceResponse
								.getServiceTeam() != null ? serviceResponse
								.getServiceTeam() : "");

						String lstrChannel = serviceResponse.getChannel();
						if(isEmptyString(lstrChannel) == false)
						{
							searchSRPojo.setChannel(Channel.getDesc(lstrChannel));
						}
						else
						{
							searchSRPojo.setChannel(Channel.CALL_CENTRE.getDesc());
						}
						
						customerProblemList.add(searchSRPojo);
					}
				}

			}

			
		} catch (ServiceRequestInquiryFault e) {
			logger.error("Exception in ["+lClassName+"."+lMethodName+"]", e);
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", e);
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
			soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
			
		}
		return customerProblemList;	

	}

	private List<ReferenceData> getStatus() 
	{
		Properties properties = PropertiesUtil.getProperties(PropertiesUtil.jiomoneyutil);
		List<ReferenceData> returnList = new ArrayList<ReferenceData>();
		String status = properties.getProperty("status");
		List<String> statusKey = Arrays.asList(status.split(","));
		for (int i = 0; i < (statusKey.size() - 1); i++) 
		{
			ReferenceData data = new ReferenceData();
			data.setCode(statusKey.get(i));
			i = i + 1;
			data.setDescription(statusKey.get(i));
			returnList.add(data);
		}
		return returnList;
	}

	public List<ReferenceData> getServiceCategory1(String entityType,
			String agentId,long requestId) throws DataAccessException {
		List<ReferenceData> referenceDataList = null;
		String lClassName =  this.getClass().getName();
		String lMethodName = "getServiceCategory1";
		
		soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");

		try 
		{
			GetServiceCategory1Rq request = new GetServiceCategory1Rq();
			request.setEntityType(sri.com.ril.rpsl.entities.servicerequest_v1_0.TEntityType.C);
			request.setRqHeader(getRequestHeader());

			/******************* Setting the WSDL URL For SR Inquiry ******************/

			ServiceRequestInquiry serviceagent = new ServiceRequestInquiry(mWSDLLocation);
			serviceagent.setHandlerResolver(new WSHandlerResolver(agentId,requestId			));
			Operations category1EndPoint = serviceagent.getOperationsEndpoint();
			/******************* Setting the endpoint URL For SR Inquiry ******************/
			BindingProvider bindingProvider = (BindingProvider) category1EndPoint;
			// logger.error("findDealerProfile :- Setting the end point for dealer Inquiry");
			bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, mServiceEndPointURL);
			/************************ Response ************************************************/
			GetServiceCategory1Rs response = category1EndPoint
					.getServiceCategory1(request);
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
			/**************************************** RESPONSE ******************************************/

			if (response != null) {

				ReferenceData referenceDataPojo = null;
				referenceDataList = new ArrayList<ReferenceData>();

				if (response.getCategoryDetails() != null
						&& response.getCategoryDetails().size() > 0) {
					for (TCategoryDetails serviceResponse : response
							.getCategoryDetails()) {

						referenceDataPojo = new ReferenceData();
						referenceDataPojo.setCode(serviceResponse
								.getCategoryCode() != null ? serviceResponse
								.getCategoryCode() : "");
						referenceDataPojo.setDescription(serviceResponse
								.getCategoryDesc() != null ? serviceResponse
								.getCategoryDesc() : "");
						referenceDataPojo.setValue(serviceResponse
								.getCategoryDesc() != null ? serviceResponse
								.getCategoryDesc() : "");
						referenceDataPojo.setType(serviceResponse
								.getCategoryId() != null ? serviceResponse
								.getCategoryId() : "");
						/*referenceDataPojo.setImpact(serviceResponse
						.getImpact() != null ? serviceResponse
						.getImpact() : "");
				referenceDataPojo.setVerifyUser(serviceResponse
						.getVerifyUser() != null ? serviceResponse
						.getVerifyUser() : "");*/
						referenceDataList.add(referenceDataPojo);

					}
				}

			}
		} catch (ServiceRequestInquiryFault e) {
			logger.error("Exception in ["+lClassName+"."+lMethodName+"]", e);
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", e);
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
			soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
			
		}
		return referenceDataList;

	}

	public List<ReferenceData> getServiceCategory2(String entityType,
			String categoryId1, String agentId,long requestId) throws DataAccessException {
		String lClassName =  this.getClass().getName();
		String lMethodName = "getServiceCategory2";
		
		soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		List<ReferenceData> referenceDataList = null;

			try 
			{
			
			/******************* Setting the WSDL URL For SR Inquiry ******************/
			ServiceRequestInquiry serviceagent = new ServiceRequestInquiry(mWSDLLocation);
			serviceagent.setHandlerResolver(new WSHandlerResolver(agentId,requestId			));
			Operations SRInquiryEndPoint = serviceagent.getOperationsEndpoint();
			/******************* Setting the endpoint URL For SR Inquiry ******************/

			BindingProvider bindingProvider = (BindingProvider) SRInquiryEndPoint;
			// logger.error("findDealerProfile :- Setting the end point for dealer Inquiry");
			bindingProvider.getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY, mServiceEndPointURL);

			GetServiceCategory2Rq request = new GetServiceCategory2Rq();
			request.setEntityType(sri.com.ril.rpsl.entities.servicerequest_v1_0.TEntityType.C);

			if (categoryId1 != null && !categoryId1.isEmpty()) {
				request.setCategoryId1(categoryId1);
			}
			request.setRqHeader(getRequestHeader());

			/************************ Response ************************************************/
			GetServiceCategory2Rs response = SRInquiryEndPoint
					.getServiceCategory2(request);
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
			/**************************************** RESPONSE ******************************************/

			if (response != null) {

				ReferenceData referenceDataPojo = null;
				referenceDataList = new ArrayList<ReferenceData>();
				if (response.getCategoryDetails() != null
						&& response.getCategoryDetails().size() > 0) {
					for (TCategoryDetails serviceResponse : response
							.getCategoryDetails()) {

						referenceDataPojo = new ReferenceData();
						referenceDataPojo.setCode(serviceResponse
								.getCategoryCode() != null ? serviceResponse
								.getCategoryCode() : "");
						referenceDataPojo.setDescription(serviceResponse
								.getCategoryDesc() != null ? serviceResponse
								.getCategoryDesc() : "");
						referenceDataPojo.setValue(serviceResponse
								.getCategoryDesc() != null ? serviceResponse
								.getCategoryDesc() : "");
						referenceDataPojo.setType(serviceResponse
								.getCategoryId() != null ? serviceResponse
								.getCategoryId() : "");
						/*referenceDataPojo.setImpact(serviceResponse
						.getImpact() != null ? serviceResponse
						.getImpact() : "");
				referenceDataPojo.setVerifyUser(serviceResponse
						.getVerifyUser() != null ? serviceResponse
						.getVerifyUser() : "");*/
						referenceDataList.add(referenceDataPojo);
					}
				}

			}

		} catch (ServiceRequestInquiryFault e) {
			logger.error("Exception in ["+lClassName+"."+lMethodName+"]", e);
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", e);
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
				soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
				
			}
		return referenceDataList;

	}

		public List<ReferenceData> getServiceCategory3(String categoryId1,
			String categoryId2, String entityType, String agentId,long requestId)
			throws DataAccessException {
			String lClassName =  this.getClass().getName();
			String lMethodName = "getServiceCategory3";
			
			soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		List<ReferenceData> referenceDataList = null;

		try {
			
			ServiceRequestInquiry serviceagent = new ServiceRequestInquiry(
					mWSDLLocation);
			serviceagent.setHandlerResolver(new WSHandlerResolver(agentId,requestId			));
			Operations SRInquiryEndPoint = serviceagent.getOperationsEndpoint();
			
			BindingProvider bindingProvider = (BindingProvider) SRInquiryEndPoint;
			// logger.error("findDealerProfile :- Setting the end point for dealer Inquiry");
			bindingProvider.getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY, mServiceEndPointURL);
			/******************* Setting the endpoint URL For SR Inquiry ******************/
			/**************************************************************/
			GetServiceCategory3Rq request = new GetServiceCategory3Rq();
			request.setCategoryId1(categoryId1);
			request.setCategoryId2(categoryId2);
			request.setEntityType(sri.com.ril.rpsl.entities.servicerequest_v1_0.TEntityType.C);
			request.setRqHeader(getRequestHeader());

			/************************ Response ************************************************/
			GetServiceCategory3Rs response = SRInquiryEndPoint
					.getServiceCategory3(request);
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
			/**************************************** RESPONSE ******************************************/

			if (response != null) {

				ReferenceData referenceDataPojo = null;
				referenceDataList = new ArrayList<ReferenceData>();
				if (response.getCategoryDetails() != null
						&& response.getCategoryDetails().size() > 0) {
					for (TCategoryDetails serviceResponse : response
							.getCategoryDetails()) {
						referenceDataPojo = new ReferenceData();
						referenceDataPojo.setCode(serviceResponse
								.getCategoryCode() != null ? serviceResponse
								.getCategoryCode() : "");
						referenceDataPojo.setDescription(serviceResponse
								.getCategoryDesc() != null ? serviceResponse
								.getCategoryDesc() : "");
						referenceDataPojo.setValue(serviceResponse
								.getCategoryDesc() != null ? serviceResponse
								.getCategoryDesc() : "");
						referenceDataPojo.setType(serviceResponse
								.getCategoryId() != null ? serviceResponse
								.getCategoryId() : "");
						/*referenceDataPojo.setImpact(serviceResponse
						.getImpact() != null ? serviceResponse
						.getImpact() : "");
				referenceDataPojo.setVerifyUser(serviceResponse
						.getVerifyUser() != null ? serviceResponse
						.getVerifyUser() : "");*/
						referenceDataList.add(referenceDataPojo);
					}
				}

			}

		} catch (ServiceRequestInquiryFault e) {
			logger.error("Exception in ["+lClassName+"."+lMethodName+"]", e);
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", e);
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
			soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
			
		}
		return referenceDataList;

	}
		public List<ReferenceData> getServiceCategory4(String categoryId1,
				String categoryId2,String categoryId3, String entityType, String agentId,long requestId)
				throws DataAccessException {
			String lClassName =  this.getClass().getName();
			String lMethodName = "getServiceCategory4";
			
			soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
			List<ReferenceData> referenceDataList = null;

			try {
				
				/******************* Setting the WSDL URL For SR Inquiry ******************/
				ServiceRequestInquiry serviceagent = new ServiceRequestInquiry(mWSDLLocation);
				serviceagent.setHandlerResolver(new WSHandlerResolver(agentId,requestId));
				Operations SRInquiryEndPoint = serviceagent.getOperationsEndpoint();
				BindingProvider bindingProvider = (BindingProvider) SRInquiryEndPoint;
				bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, mServiceEndPointURL);
				
				GetServiceCategory4Rq request = new GetServiceCategory4Rq();
				request.setCategoryId1(categoryId1);
				request.setCategoryId2(categoryId2);
				request.setCategoryId3(categoryId3);
				if(entityType.equalsIgnoreCase("C")){
					request.setEntityType(sri.com.ril.rpsl.entities.servicerequest_v1_0.TEntityType.C);
				}
				request.setRqHeader(getRequestHeader());

				/************************ Response ************************************************/
				GetServiceCategory4Rs response = SRInquiryEndPoint
						.getServiceCategory4(request);
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
				/**************************************** RESPONSE ******************************************/

				if (response != null) 
				{
					ReferenceData referenceDataPojo = null;
					referenceDataList = new ArrayList<ReferenceData>();
					if (response.getCategoryDetails() != null && response.getCategoryDetails().size() > 0) 
					{
						List<TCategoryDetails> lCategories = response.getCategoryDetails();
						for (TCategoryDetails lCategory : lCategories) 
						{
							referenceDataPojo = new ReferenceData();
							referenceDataPojo.setCode(getStringRepresentation(lCategory.getCategoryCode()));
							referenceDataPojo.setDescription(getStringRepresentation(lCategory.getCategoryDesc()));
							referenceDataPojo.setValue(getStringRepresentation(lCategory.getCategoryDesc() ));
							referenceDataPojo.setType(getStringRepresentation(lCategory.getCategoryId() ));
							referenceDataPojo.setImpact(getStringRepresentation(lCategory.getImpact()));
							referenceDataPojo.setVerifyUser(getStringRepresentation(lCategory.getVerifyFlag()));
							referenceDataPojo.setUrgency(getStringRepresentation(lCategory.getUrgency()));
							referenceDataList.add(referenceDataPojo);
						}
					}
				}
			} 
			catch (ServiceRequestInquiryFault e) 
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
				logger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
				soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
				throw new DataAccessException(t.getMessage(), t);
			}
			finally
			{
				soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
				
			}
			
			return referenceDataList;

		}
		
		public List<ReferenceData> getStatesOrDistrictList(String pState,String agentId,long requestId)
				throws DataAccessException {
			String lClassName =  this.getClass().getName();
			String lMethodName = "getStatesOrDistrictList";
			
			soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
			List<ReferenceData> referenceDataList = null;

			try 
			{
				
				/******************* Setting the WSDL URL For SR Inquiry ******************/
				ServiceRequestInquiry serviceagent = new ServiceRequestInquiry(mWSDLLocation);
				serviceagent.setHandlerResolver(new WSHandlerResolver(agentId,requestId));
				Operations SRInquiryEndPoint = serviceagent.getOperationsEndpoint();
				BindingProvider bindingProvider = (BindingProvider) SRInquiryEndPoint;
				bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, mServiceEndPointURL);
				
				GetDistrictRq request = new GetDistrictRq();
				request.setCountry("IN");
				if(!isEmptyString(pState))
				{
					request.setState(pState);
				}
				request.setRqHeader(getRequestHeader());

				/************************ Response ************************************************/
				GetDistrictRs response = SRInquiryEndPoint.getDistrict(request);
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
				/**************************************** RESPONSE ******************************************/

				if (response != null) 
				{
					ReferenceData referenceDataPojo = null;
					referenceDataList = new ArrayList<ReferenceData>();
					if(!isEmptyString(pState))
					{
						//process for districts
						List<String> lstDistricts = response.getDistrict();
						for (String string : lstDistricts) 
						{
							referenceDataPojo = new ReferenceData();
							referenceDataPojo.setCode(string);
							referenceDataPojo.setDescription(string);
							referenceDataList.add(referenceDataPojo);
						}
					}
					else
					{
						// process for states
						List<StateVal>  lstStates = response.getStateVal();
						if(lstStates == null)
							throw new DataAccessException("Enpty State List.");
						for (StateVal lState : lstStates) 
						{
							referenceDataPojo = new ReferenceData();
							referenceDataPojo.setCode(lState.getStateCode());
							referenceDataPojo.setDescription(lState.getStateName());
							referenceDataList.add(referenceDataPojo);
						}
					}
				}
			} 
			catch (ServiceRequestInquiryFault e) 
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
				logger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
				soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
				throw new DataAccessException(t.getMessage(), t);
			}
			finally
			{
				soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
				
			}
			
			return referenceDataList;

		}
		
		public String validateServiceRequest(String entityId,String subSubSubCategory, 
				String entityType, String agentCode,long requestId) throws DataAccessException,
				ServiceException {
			
			String lClassName =  this.getClass().getName();
			String lMethodName = "validateServiceRequest";
			String lSRExistsFlag = "";
			
			try 
			{
				
				/******************* Setting the WSDL URL For SR Inquiry ******************/
				ServiceRequestInquiry serviceagent = new ServiceRequestInquiry(mWSDLLocation);
				serviceagent.setHandlerResolver(new WSHandlerResolver(agentCode,requestId));
				Operations SRInquiryEndPoint = serviceagent.getOperationsEndpoint();
				BindingProvider bindingProvider = (BindingProvider) SRInquiryEndPoint;
				bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, mServiceEndPointURL);
				
				ValidateServiceRequestRq request = new ValidateServiceRequestRq();
				request.setEntityId(entityId);
				request.setCategory(subSubSubCategory);
				request.setRqHeader(getRequestHeader());

				/************************ Response ************************************************/
				ValidateServiceRequestRs response = SRInquiryEndPoint.validateServiceRequest(request);
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
				/**************************************** RESPONSE ******************************************/

				if (response != null) 
				{
					
					if (response != null&& response.getResponseStatus() != null && response.getResponseStatus().getStatus() != null && response.getResponseStatus().getStatus().equalsIgnoreCase("FAILED")) 
					{
						if (response.getResponseStatus().getErrors() != null && response.getResponseStatus().getErrors().getError() != null && response.getResponseStatus().getErrors().getError().size() > 0
								&& response.getResponseStatus().getErrors().getError().get(0).getErrorMsg() != null) 
						{
							throw new DataAccessException(response.getResponseStatus().getErrors().getError().get(0).getErrorMsg());
						}
					}
				}
				lSRExistsFlag =  response.getSrExists();
				
			} 
			catch (ServiceRequestInquiryFault e) 
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
				logger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
				soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
				throw new DataAccessException(t.getMessage(), t);
			}
			finally
			{
				soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentCode+"|["+lClassName +"."+lMethodName+"] | End");
				
			}
			return lSRExistsFlag;
		}
}
