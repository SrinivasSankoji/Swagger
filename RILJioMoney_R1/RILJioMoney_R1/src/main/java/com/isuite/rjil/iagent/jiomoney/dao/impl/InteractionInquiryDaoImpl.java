package com.isuite.rjil.iagent.jiomoney.dao.impl;

import ii.com.ril.rpsl.entities.commontypes_v1_0.TNameValue;


import ii.com.ril.rpsl.entities.interaction_v1_0.TEntityType;
import ii.com.ril.rpsl.entities.interaction_v1_0.TInteraction;
import ii.com.ril.rpsl.entities.interaction_v1_0.TVerificationQuestion;
import ii.com.ril.rpsl.services.interactioninquiry_v1_0.FilterCriteria;
import ii.com.ril.rpsl.services.interactioninquiry_v1_0.GetVerificationQuestionListRq;
import ii.com.ril.rpsl.services.interactioninquiry_v1_0.GetVerificationQuestionListRs;
import ii.com.ril.rpsl.services.interactioninquiry_v1_0.PageData;
import ii.com.ril.rpsl.services.interactioninquiry_v1_0.SearchInteractionRq;
import ii.com.ril.rpsl.services.interactioninquiry_v1_0.SearchInteractionRs;
import ii.com.ril.rpsl.services.interactioninquiry_v1_0.ViewInteractionRq;
import ii.com.ril.rpsl.services.interactioninquiry_v1_0.ViewInteractionRs;
import ii.com.ril.rpsl.wsdls.interactioninquiry_v1_0.InteractionInquiryFault;
import ii.com.ril.rpsl.wsdls.interactioninquiry_v1_0.InteractionInquiry;
import ii.com.ril.rpsl.wsdls.interactioninquiry_v1_0.Operations;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


import javax.xml.ws.BindingProvider;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.common.Channel;
import com.isuite.rjil.iagent.jiomoney.common.CommunicationMode;
import com.isuite.rjil.iagent.jiomoney.common.CustomerInteraction;
import com.isuite.rjil.iagent.jiomoney.common.VerificationQuestion;
import com.isuite.rjil.iagent.jiomoney.dao.InteractionInquiryDao;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;
import com.isuite.rjil.iagent.jiomoney.util.PropertiesUtil;
import com.isuite.rjil.iagent.common.util.RequestUtils;
import com.isuite.rjil.iagent.jiomoney.util.Util;
import com.isuite.rjil.iagent.jiomoney.util.WSHandlerResolver;
import com.isuite.rjil.iagent.jiomoney.util.XMLGregorianCalendarUtil;

/**
 * Title: InteractionInquiryDaoImpl.java <br>
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
public class InteractionInquiryDaoImpl implements InteractionInquiryDao 
{
	private static final Logger logger = Logger.getLogger(InteractionInquiryDaoImpl.class);
	private static final Logger soapLogger = Logger.getLogger(LOVUtil.SOAPLOGGER);

	private Properties platformProperties = PropertiesUtil.getProperties(PropertiesUtil.platform);

	@Override
	public CustomerInteraction viewInteractionRequest(String interactionRefNo,String entityType, String agentId,long requestId) throws DataAccessException 
	{
		String lClassName =  this.getClass().getName();
		String lMethodName = "viewInteractionRequest";
		
		soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		try 
		{
			/******************* Setting the WSDL URL ******************/
			String dealerInquiry_WS_URL = platformProperties.getProperty("interactionInquiry");
			URL wsdlURL = new URL(dealerInquiry_WS_URL);

			InteractionInquiry serviceagent = new InteractionInquiry(wsdlURL);
			serviceagent.setHandlerResolver(new WSHandlerResolver(agentId,requestId	));
			Operations endPoint = serviceagent.getOperationsEndpoint();
			String endPointURL = platformProperties.getProperty("interactionInquiryEndPoint");
			BindingProvider bindingProvider = (BindingProvider) endPoint;
			bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointURL);
			/******************* Setting the endpoint URL For InteractionInquiry ******************/
			TEntityType entityTypeRequest = null;
			if (entityType.equalsIgnoreCase("C")) 
			{
				entityTypeRequest = TEntityType.C;
			}
			if (entityType.equalsIgnoreCase("M")) 
			{
				entityTypeRequest = TEntityType.M;
			}
			if (entityType.equalsIgnoreCase("A")) 
			{
				entityTypeRequest = TEntityType.A;
			}

			/**** Setting All Request ****/
			ViewInteractionRq request = new ViewInteractionRq();
			request.setInteractionRefNo(interactionRefNo);
			request.setEntityType(entityTypeRequest);
			request.setRqHeader(RequestUtils.getRequestHeader());

			/************************ Response *******************/
			ViewInteractionRs response = endPoint.viewInteraction(request);
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
			CustomerInteraction viewIRPojo = null;
			if (response != null) {
				if (response.getInteraction() != null) {
					viewIRPojo = new CustomerInteraction();
					viewIRPojo.setInteractionRefNo(response.getInteraction()
							.getInteractionRefNo() != null ? response
							.getInteraction().getInteractionRefNo() : "");
					viewIRPojo.setInteractionType(response.getInteraction()
							.getInteractionType() != null ? response
							.getInteraction().getInteractionType() : "");
					if (response.getInteraction().getInteractionDate() != null) {
						viewIRPojo.setInteractionDate(XMLGregorianCalendarUtil
								.parseDateFormat(response.getInteraction()
										.getInteractionDate()));
					} else {
						viewIRPojo.setInteractionDate("");
					}

					viewIRPojo
							.setStatus(response.getInteraction().getStatus() != null ? response
									.getInteraction().getStatus() : "");
					viewIRPojo.setStatusDesc(response.getInteraction()
							.getStatusDesc() != null ? response
							.getInteraction().getStatusDesc() : "");
					viewIRPojo.setCategory(response.getInteraction()
							.getCategory1() != null ? response.getInteraction()
							.getCategory1() : "");

					viewIRPojo.setCategoryDescription(response.getInteraction()
							.getCategoryDesc1() != null ? response
							.getInteraction().getCategoryDesc1() : "");

					viewIRPojo.setSubCategory(response.getInteraction()
							.getCategory2() != null ? response.getInteraction()
							.getCategory2() : "");

					viewIRPojo
							.setSubCategoryDescription(response
									.getInteraction().getCategoryDesc2() != null ? response
									.getInteraction().getCategoryDesc2() : "");

					viewIRPojo.setSubSubCategory(response.getInteraction()
							.getCategory3() != null ? response.getInteraction()
							.getCategory3() : "");
					viewIRPojo
							.setSubSubcategoryDescription(response
									.getInteraction().getCategoryDesc3() != null ? response
									.getInteraction().getCategoryDesc3() : "");
					viewIRPojo
					.setCategory4(response
							.getInteraction().getCategoryDesc4() != null ? response
							.getInteraction().getCategoryDesc4() : "");

					viewIRPojo.setCustomerId(response.getInteraction()
							.getEntityId() != null ? response.getInteraction()
							.getEntityId() : "");
					viewIRPojo.setInteractionTypeDesc(response.getInteraction()
							.getInteractionTypeDesc() != null ? response
							.getInteraction().getInteractionTypeDesc() : "");
					viewIRPojo
							.setNotes(response.getInteraction().getNote() != null ? response
									.getInteraction().getNote() : "");
					
					String lstrChannel = response.getInteraction().getChannel();
					if(Util.isEmptyString(lstrChannel) == false)
					{
						viewIRPojo.setChannel(Channel.getDesc(lstrChannel));
					}
					else
					{
						viewIRPojo.setChannel(Channel.CALL_CENTRE.getDesc());
					}
					viewIRPojo.setAgentCode(response.getInteraction()
							.getAgentId() != null ? response.getInteraction()
							.getAgentId() : "");
				}
				viewIRPojo.setCommunicationMode(CommunicationMode.CALL.getDesc());
				if(response.getMiscEntities() != null && response.getMiscEntities().getNameValue()!= null && response.getMiscEntities().getNameValue().isEmpty() == false)
				{
					List<TNameValue> lNameValues = response.getMiscEntities().getNameValue();
					for (TNameValue lTNameValue : lNameValues) 
					{
						if(CommunicationMode.COMMUNICATION_MODE_PARAM_NAME.equalsIgnoreCase(lTNameValue.getName()) && viewIRPojo != null)
						{
							viewIRPojo.setCommunicationMode(CommunicationMode.getDesc((lTNameValue.getValue())));
						}
					}
				}
			}
			/*if (response != null && response.getResponseStatus() != null) {
				if (response.getResponseStatus().getInfos() != null) {
					for (ii.com.ril.rpsl.entities.commontypes_v1_0.TInfo tInfoResponse : response
							.getResponseStatus().getInfos().getInfo()) {
						viewIRPojo.setProviderStatus(HeaderStatus.setStatus(
								tInfoResponse.getCode(),
								tInfoResponse.getMsg(), tInfoResponse
										.getProvider().getProviderName(),
								tInfoResponse.getProvider().getProviderCode()));
					}
				}
			}
			if (response != null && response.getRsHeader() != null) {
				if (response.getRsHeader().getMessageContext() != null) {
					viewIRPojo.setHeaderMessage(HeaderStatus.setMessage(
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
					viewIRPojo.setBusinessApplication(HeaderStatus
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

			}*/
			
			return viewIRPojo;
		} catch (InteractionInquiryFault e) {
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
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]",t);
			logger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			throw new DataAccessException(t.getMessage(), t);
		}
		finally
		{
			soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
			
		}

	}

	public List<CustomerInteraction> searchInteractionRequest(String custId,String entityType, String pageSize, String offSet, String agentCode,long requestId)throws DataAccessException 
	{
		String lClassName =  this.getClass().getName();
		String lMethodName = "searchInteractionRequest";
		
		soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentCode+"|["+lClassName +"."+lMethodName+" ]| Start");
		try {			
			
			/******************* Setting the WSDL URL ******************/
			String dealerInquiry_WS_URL = platformProperties
					.getProperty("interactionInquiry");
			URL wsdlURL = new URL(dealerInquiry_WS_URL);

			InteractionInquiry serviceagent = new InteractionInquiry(wsdlURL);
			serviceagent.setHandlerResolver(new WSHandlerResolver(agentCode,requestId	));
			Operations endPoint = serviceagent.getOperationsEndpoint();
			String endPointURL = platformProperties
					.getProperty("interactionInquiryEndPoint");
			BindingProvider bindingProvider = (BindingProvider) endPoint;
			bindingProvider.getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointURL);

			/******************* Setting the endpoint URL For InteractionInquiry ******************/
			FilterCriteria criteriaRequest = new FilterCriteria();
			//criteriaRequest.setEntityId(custId);
			criteriaRequest.setFilterValue(custId);
			criteriaRequest.setFilterName("entityid");
			if (entityType.equalsIgnoreCase("C")) {
				criteriaRequest.setEntityType(TEntityType.C);
			}
			if (entityType.equalsIgnoreCase("M")) {
				criteriaRequest.setEntityType(TEntityType.M);
			}
			if (entityType.equalsIgnoreCase("A")) {
				criteriaRequest.setEntityType(TEntityType.A);
			}

			PageData pageData = new PageData();
			pageData.setOffSet(offSet);
			pageData.setMaxRow(pageSize);

			SearchInteractionRq request = new SearchInteractionRq();
			request.setFilterCriteria(criteriaRequest);
			request.setRqHeader(RequestUtils.getRequestHeader());
			request.setPageData(pageData);

			/************************* Response ***********************************/
			SearchInteractionRs response = endPoint.searchInteraction(request);

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
			List<CustomerInteraction> searchCustomerInteractionList = null;
			if (response != null) {
				searchCustomerInteractionList = new ArrayList<CustomerInteraction>();
				CustomerInteraction searchIRPojo = null;
				/*if (response.getResponseStatus() != null) {
					if (response.getResponseStatus().getInfos() != null) {
						for (TInfo tInfoResponse : response.getResponseStatus()
								.getInfos().getInfo()) {
							searchIRPojo = new CustomerInteraction();
							searchIRPojo.setProviderStatus(HeaderStatus
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
					if (response.getRsHeader().getMessageContext() != null) {
						searchIRPojo = new CustomerInteraction();
						searchIRPojo.setHeaderMessage(HeaderStatus.setMessage(
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

				if (response.getRsHeader().getBusinessApplicationContext() != null) {
						searchIRPojo = new CustomerInteraction();
						searchIRPojo.setBusinessApplication(HeaderStatus
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
				*/
				if (response.getInteraction() != null
						&& response.getInteraction().size() > 0) {
					for (TInteraction searchInteractionResponse : response
							.getInteraction()) {
						searchIRPojo = new CustomerInteraction();
						searchIRPojo.setTotalNoRecords(response
								.getTotalRecordCount() != null ? response
								.getTotalRecordCount() : "");
						searchIRPojo
								.setInteractionRefNo(searchInteractionResponse
										.getInteractionRefNo() != null ? searchInteractionResponse
										.getInteractionRefNo() : "");

						searchIRPojo
								.setInteractionType(searchInteractionResponse
										.getInteractionType() != null ? searchInteractionResponse
										.getInteractionType() : "");
						searchIRPojo
								.setInteractionTypeDesc(searchInteractionResponse
										.getInteractionTypeDesc() != null ? searchInteractionResponse
										.getInteractionTypeDesc() : "");

						if (searchInteractionResponse.getInteractionDate() != null) {
							searchIRPojo
									.setInteractionDate(XMLGregorianCalendarUtil
											.parseDateFormat(searchInteractionResponse
													.getInteractionDate()));
						} else {
							searchIRPojo.setInteractionDate("");
						}

						searchIRPojo
								.setStatus(searchInteractionResponse
										.getStatus() != null ? searchInteractionResponse
										.getStatus() : "");
						searchIRPojo
								.setStatusDesc(searchInteractionResponse
										.getStatusDesc() != null ? searchInteractionResponse
										.getStatusDesc() : "");
						searchIRPojo
								.setCategory(searchInteractionResponse
										.getCategory1() != null ? searchInteractionResponse
										.getCategory1() : "");
						searchIRPojo
								.setCategoryDescription(searchInteractionResponse
										.getCategoryDesc1() != null ? searchInteractionResponse
										.getCategoryDesc1() : "");
						searchIRPojo
								.setSubCategory(searchInteractionResponse
										.getCategory2() != null ? searchInteractionResponse
										.getCategory2() : "");
						searchIRPojo
								.setSubCategoryDescription(searchInteractionResponse
										.getCategoryDesc2() != null ? searchInteractionResponse
										.getCategoryDesc2() : "");
						searchIRPojo
								.setSubSubCategory(searchInteractionResponse
										.getCategory3() != null ? searchInteractionResponse
										.getCategory3() : "");
						searchIRPojo
								.setSubSubcategoryDescription(searchInteractionResponse
										.getCategoryDesc3() != null ? searchInteractionResponse
										.getCategoryDesc3() : "");
						
						searchIRPojo
						.setCategory4(searchInteractionResponse
								.getCategoryDesc4() != null ? searchInteractionResponse
								.getCategoryDesc4() : "");
						
//						if (searchInteractionResponse.getChannel() != null
//								&& searchInteractionResponse.getChannel()
//										.equalsIgnoreCase("01")) {
//							searchIRPojo.setChannel("Call Centre");
//						} else {
//							searchIRPojo.setChannel("");
//						}
						String lstrChannel = searchInteractionResponse.getChannel();
						if(Util.isEmptyString(lstrChannel) == false)
						{
							searchIRPojo.setChannel(Channel.getDesc(lstrChannel));
						}
						else
						{
							searchIRPojo.setChannel(Channel.CALL_CENTRE.getDesc());
						}
						searchIRPojo
								.setAgentCode(searchInteractionResponse
										.getAgentId() != null ? searchInteractionResponse
										.getAgentId() : "");
						searchCustomerInteractionList.add(searchIRPojo);
					}
				}
				
			}
			return searchCustomerInteractionList;
		} catch (InteractionInquiryFault e) 
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
		} catch (Throwable t) 
		{
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]",t);
			logger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			throw new DataAccessException(t.getMessage(), t);
		}
		finally
		{
			soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentCode+"|["+lClassName +"."+lMethodName+"] | End");
			
		}
	}

	@Override
	public List<VerificationQuestion> getVerificationQuestionList(String categoryId2, String categoryId3, String entityType,String custId, String agentCode,long requestId) throws DataAccessException {
		String lClassName =  this.getClass().getName();
		String lMethodName = "getVerificationQuestionList";
		
		soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentCode+"|["+lClassName +"."+lMethodName+" ]| Start");
		try {
		
			/******************* Setting the WSDL URL ******************/
			String dealerInquiry_WS_URL = platformProperties
					.getProperty("interactionInquiry");
			URL wsdlURL = new URL(dealerInquiry_WS_URL);

			InteractionInquiry serviceagent = new InteractionInquiry(wsdlURL);
			serviceagent.setHandlerResolver(new WSHandlerResolver(agentCode,requestId	));
			Operations endPoint = serviceagent.getOperationsEndpoint();
			/******************* Setting the endpoint URL For InteractionInquiry ******************/
			String endPointURL = platformProperties
					.getProperty("interactionInquiryEndPoint");
			BindingProvider bindingProvider = (BindingProvider) endPoint;
			bindingProvider.getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointURL);

			/************************** Setting Request ***************************/
			GetVerificationQuestionListRq request = new GetVerificationQuestionListRq();
			request.setCategoryId2(categoryId2);
			request.setCategoryId3(categoryId3);
			if (entityType.equalsIgnoreCase(TEntityType.C.toString())) {
				request.setEntityType(TEntityType.C);
			}
			if (entityType.equalsIgnoreCase(TEntityType.M.toString())) {
				request.setEntityType(TEntityType.M);
			}
			if (entityType.equalsIgnoreCase(TEntityType.A.toString())) {
				request.setEntityType(TEntityType.A);
			}
			request.setRqHeader(RequestUtils.getRequestHeader());

			/************************ Response ************************************************/

			GetVerificationQuestionListRs response = endPoint
					.getVerificationQuestions(request);
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

			List<VerificationQuestion> returnList = null;
			if (response != null) {
				returnList = new ArrayList<VerificationQuestion>();
				int count = 0;
				if (response.getVerificationQuestion() != null
						&& response.getVerificationQuestion().size() > 0) {
					for (TVerificationQuestion cardDetails : response
							.getVerificationQuestion()) {
						count++;
						VerificationQuestion questionPojo = new VerificationQuestion();
						questionPojo
								.setQuestionId(cardDetails
										.getVerificationQuestionId() != null ? cardDetails
										.getVerificationQuestionId() : "");
						questionPojo
								.setQuestion(cardDetails
										.getVerificationQuestionDesc() != null ? cardDetails
										.getVerificationQuestionDesc() : "");
						questionPojo.setCustAnswer("");
						questionPojo.setSrNo(String.valueOf(count));
						questionPojo.setIsSelected(false);
						returnList.add(questionPojo);
					}
				}

			}
			return returnList;
		} catch (InteractionInquiryFault e) {
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
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]",t);
			logger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			throw new DataAccessException(t.getMessage(), t);
		}
		finally
		{
			soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentCode+"|["+lClassName +"."+lMethodName+"] | End");
			
		}
	}
}
