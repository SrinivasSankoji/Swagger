package com.isuite.rjil.iagent.jiomoney.dao.impl;


import instinq.com.ril.rpsl.entities.header_v1_0.TBusinessApplicationContext;
import instinq.com.ril.rpsl.entities.header_v1_0.TMessageContext;
import instinq.com.ril.rpsl.entities.header_v1_0.TRqHeader;
import instinq.com.ril.rpsl.services.instrumentinquiry_v1_0.CardDetails;
import instinq.com.ril.rpsl.services.instrumentinquiry_v1_0.GetCardListRq;
import instinq.com.ril.rpsl.services.instrumentinquiry_v1_0.GetCardListRs;
import instinq.com.ril.rpsl.services.instrumentinquiry_v1_0.GetPhysicalCardListRq;
import instinq.com.ril.rpsl.services.instrumentinquiry_v1_0.GetPhysicalCardListRs;
import instinq.com.ril.rpsl.wsdls.instrumentinquiry_v1_0.InstrumentInquiryFault;
import instinq.com.ril.rpsl.wsdls.instrumentinquiry_v1_0.InstrumentInquiryServiceagent;
import instinq.com.ril.rpsl.wsdls.instrumentinquiry_v1_0.Operations;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.xml.ws.BindingProvider;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.common.Address;
import com.isuite.rjil.iagent.jiomoney.common.Bank;
import com.isuite.rjil.iagent.jiomoney.common.Customer;
import com.isuite.rjil.iagent.jiomoney.dao.InstrumentInquiryDao;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;

import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;
import com.isuite.rjil.iagent.jiomoney.util.PropertiesUtil;
import com.isuite.rjil.iagent.jiomoney.util.WSHandlerResolver;
import com.isuite.rjil.iagent.jiomoney.util.XMLGregorianCalendarUtil;

/**
 * Title: InstrumentInquiryDaoImpl.java <br>
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
public class InstrumentInquiryDaoImpl implements InstrumentInquiryDao {
	private static Logger logger = Logger
			.getLogger(InstrumentInquiryDaoImpl.class);
	private static final Logger soapLogger = Logger
			.getLogger(LOVUtil.SOAPLOGGER);

	private Properties platformProperties = PropertiesUtil
			.getProperties(PropertiesUtil.platform);

	@Override
	public Customer getPhysicalCardList(String custId, String agentId,long requestId)
			throws DataAccessException {
		String lClassName =  this.getClass().getName();
		String lMethodName = "getPhysicalCardList";
		
		soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		try {

			/******************* Setting the Header Request ******************/
			TRqHeader headerRequest = new TRqHeader();
			TMessageContext messageRequest = new TMessageContext();
			messageRequest.setMessageID(String.valueOf(UUID.randomUUID()));
			messageRequest.setCorrelationID(String.valueOf(UUID.randomUUID()));
			messageRequest
					.setExtCorrelationID(String.valueOf(UUID.randomUUID()));
			messageRequest.setTimeStamp(XMLGregorianCalendarUtil
					.getSystemTime());
			headerRequest.setMessageContext(messageRequest);
			TBusinessApplicationContext businessApplicationRequest = new TBusinessApplicationContext();
			businessApplicationRequest.setOperationName("getPhysicalCardList");
			businessApplicationRequest.setMessageType("REQ");
			businessApplicationRequest.setSrcApplicationname("WEB");
			headerRequest
					.setBusinessApplicationContext(businessApplicationRequest);
			/***************************************************************/
			/******************* Setting the WSDL URL ******************/
			String instrumentInquiryWsdlUrl = platformProperties
					.getProperty("instrumentInquiry");

			URL wsdlUrl = new URL(instrumentInquiryWsdlUrl);
			logger.info("************************Testing wsdlUrl" + wsdlUrl);

			InstrumentInquiryServiceagent serviceagent = new InstrumentInquiryServiceagent(
					wsdlUrl);
			serviceagent.setHandlerResolver(new WSHandlerResolver(agentId,requestId				));
			String endPointURL = platformProperties
					.getProperty("instrumentInquiryEndPoint");
			Operations endPoint = serviceagent.getOperationsEndpoint();
			/******************* Setting the END POINT URL ******************/
			BindingProvider bindingProvider = (BindingProvider) endPoint;
			// logger.error("findDealerProfile :- Setting the end point for dealer Inquiry");
			bindingProvider.getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointURL);
			/***************************************** Setting the endpoint URL For InstrumentInquiry ******************/

			GetPhysicalCardListRq request = new GetPhysicalCardListRq();
			request.setCustId(custId);
			request.setRqHeader(headerRequest);

			/************************ Response ************************************************/
			GetPhysicalCardListRs response = endPoint
					.getPhysicalCardList(request);

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
			Customer customerPojo = null;
			if (response != null) {
				/******************* InstrumentInquriy Info ******************/
				customerPojo = new Customer();
				List<com.isuite.rjil.iagent.jiomoney.common.CardDetails> cardDetailsList = null;
				if (response.getCardDetails() != null
						&& response.getCardDetails().size() > 0) {
					cardDetailsList = new ArrayList<com.isuite.rjil.iagent.jiomoney.common.CardDetails>();
					for (CardDetails cardDetails : response.getCardDetails()) {
						com.isuite.rjil.iagent.jiomoney.common.CardDetails cardDetailsPojo = new com.isuite.rjil.iagent.jiomoney.common.CardDetails();
						cardDetailsPojo
								.setCustomerId(response.getCustId() != null ? response
										.getCustId() : "");
						cardDetailsPojo
								.setNickName(cardDetails.getNickName() != null ? cardDetails
										.getNickName() : "");
						cardDetailsPojo
								.setAlias(cardDetails.getCardAlias() != null ? cardDetails
										.getCardAlias() : "");
						cardDetailsPojo.setExpMM(cardDetails.getCard()
								.getExpMM() != null ? cardDetails.getCard()
								.getExpMM() : "");
						cardDetailsPojo.setExpYY(cardDetails.getCard()
								.getExpYY() != null ? cardDetails.getCard()
								.getExpYY() : "");
						cardDetailsPojo.setExpieryDate(cardDetails.getCard()
								.getExpMM()
								+ "-"
								+ cardDetails.getCard().getExpYY());
						cardDetailsPojo
								.setStatus(cardDetails.getStatus() != null ? cardDetails
										.getStatus() : "");

						if (cardDetails.getCard() != null
								&& cardDetails.getCard().getCardNum() != null
								&& cardDetails.getCard().getCardNum()
										.getCardNo() != null) {
							cardDetailsPojo.setCardNo(cardDetails.getCard()
									.getCardNum().getCardNo());
						} else {
							cardDetailsPojo.setCardNo("");
						}
						cardDetailsPojo
								.setId(cardDetails.getCard().getCardId() != null ? cardDetails
										.getCard().getCardId() : "");
						cardDetailsPojo
								.setStatus(cardDetails.getStatus() != null ? cardDetails
										.getStatus() : "");
						cardDetailsList.add(cardDetailsPojo);
					}
				}
				if (cardDetailsList != null && cardDetailsList.size() > 0) {
					customerPojo.setCardDetails(cardDetailsList);
				}
				/*************************** ResponseDetails **************************/
				/****************************** ResponseStatus ******************
				if (response.getResponseStatus() != null) {
					if (response.getResponseStatus().getInfos() != null) {
						for (TInfo tInfoResponse : response.getResponseStatus()
								.getInfos().getInfo()) {

							customerPojo.setProviderStatus(HeaderStatus
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
					/****************************** MessageContext *******************
					if (response.getRsHeader().getMessageContext() != null) {
						customerPojo.setHeaderMessage(HeaderStatus.setMessage(
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

					/********************** BusinessApplicationContext *****************
					if (response.getRsHeader().getBusinessApplicationContext() != null) {
						customerPojo.setBusinessApplication(HeaderStatus
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
				}*/
				
			}
			return customerPojo;
			
		} catch (InstrumentInquiryFault e) 
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
			soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
			
		}
	}

	@Override
	public Customer getCardList(String custId, String agentId,long requestId)
			throws DataAccessException {
		String lClassName =  this.getClass().getName();
		String lMethodName = "getCardList";
		
		soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		try {
			/******************* Setting the Header Request ******************/
			TRqHeader headerRequest = new TRqHeader();
			TMessageContext messageRequest = new TMessageContext();
			messageRequest.setMessageID(String.valueOf(UUID.randomUUID()));
			messageRequest.setCorrelationID(String.valueOf(UUID.randomUUID()));
			messageRequest
					.setExtCorrelationID(String.valueOf(UUID.randomUUID()));
			messageRequest.setTimeStamp(XMLGregorianCalendarUtil
					.getSystemTime());
			headerRequest.setMessageContext(messageRequest);
			TBusinessApplicationContext businessApplicationRequest = new TBusinessApplicationContext();
			businessApplicationRequest.setOperationName("getCardList");
			businessApplicationRequest.setMessageType("REQ");
			businessApplicationRequest.setSrcApplicationname("WEB");
			headerRequest
					.setBusinessApplicationContext(businessApplicationRequest);
			GetCardListRq request = new GetCardListRq();

			request.setCustId(custId.toUpperCase());
			request.setRqHeader(headerRequest);
			/******************* Setting the WSDL URL ******************/
			String instrumentInquiryWsdlUrl = platformProperties
					.getProperty("instrumentInquiry");

			URL wsdlUrl = new URL(instrumentInquiryWsdlUrl);
			InstrumentInquiryServiceagent serviceagent = new InstrumentInquiryServiceagent(
					wsdlUrl);
			serviceagent.setHandlerResolver(new WSHandlerResolver(agentId,requestId				));

			Operations endPoint = serviceagent.getOperationsEndpoint();

			String endPointURL = platformProperties
					.getProperty("instrumentInquiryEndPoint");
			BindingProvider bindingProvider = (BindingProvider) endPoint;
			bindingProvider.getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointURL);
			/************************ Response ************************************************/
			GetCardListRs response = endPoint.getCardList(request);

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
			Customer customerPojo = null;
			if (response != null) {
				customerPojo = new Customer();
				List<com.isuite.rjil.iagent.jiomoney.common.CardDetails> cardDetailsList = new ArrayList<com.isuite.rjil.iagent.jiomoney.common.CardDetails>();
				if (response.getCardDetails() != null
						&& response.getCardDetails().size() > 0) {
					for (CardDetails cardDetailsResp : response
							.getCardDetails()) {
						/************************ CustomerDetails Address ************************************************/
						if (cardDetailsResp.getBillingAddress() != null) {
							Address addressPojo = new Address();
							addressPojo
									.setCity(cardDetailsResp
											.getBillingAddress().getCity() != null ? cardDetailsResp
											.getBillingAddress().getCity() : "");
							addressPojo
									.setState(cardDetailsResp
											.getBillingAddress().getState() != null ? cardDetailsResp
											.getBillingAddress().getState()
											: "");
							addressPojo
									.setCountry(cardDetailsResp
											.getBillingAddress().getCountry() != null ? cardDetailsResp
											.getBillingAddress().getCountry()
											: "");
							addressPojo
									.setPostCode(cardDetailsResp
											.getBillingAddress().getPostCode() != null ? cardDetailsResp
											.getBillingAddress().getPostCode()
											: "");
							customerPojo.setAddress(addressPojo);
						} else {
							Address addressPojo = new Address();
							addressPojo.setCity("");
							addressPojo.setState("");
							addressPojo.setCountry("");
							addressPojo.setPostCode("");
							customerPojo.setAddress(addressPojo);
						}
						/************************ Setting Card Details ************************************************/
						if (cardDetailsResp.getCard() != null) {
							com.isuite.rjil.iagent.jiomoney.common.CardDetails cardDetailsPojo = new com.isuite.rjil.iagent.jiomoney.common.CardDetails();
							cardDetailsPojo
									.setEmbossName(cardDetailsResp.getCard()
											.getEmbossName() != null ? cardDetailsResp
											.getCard().getEmbossName() : "");
							cardDetailsPojo
									.setCardType(cardDetailsResp.getCard()
											.getCardType() != null ? cardDetailsResp
											.getCard().getCardType() : "");
							cardDetailsPojo
									.setCardCategory(cardDetailsResp.getCard()
											.getCardCategory() != null ? cardDetailsResp
											.getCard().getCardCategory() : "");
							cardDetailsPojo.setExpMM(cardDetailsResp.getCard()
									.getExpMM() != null ? cardDetailsResp
									.getCard().getExpMM() : "");
							cardDetailsPojo.setExpYY(cardDetailsResp.getCard()
									.getExpYY() != null ? cardDetailsResp
									.getCard().getExpYY() : "");

							cardDetailsPojo.setExpieryDate(cardDetailsPojo
									.getExpMM()
									+ "-"
									+ cardDetailsPojo.getExpYY());

							cardDetailsPojo
									.setCardCategory(cardDetailsResp.getCard()
											.getCardCategory() != null ? cardDetailsResp
											.getCard().getCardCategory() : "");
							cardDetailsPojo.setId(cardDetailsResp.getCard()
									.getCardId() != null ? cardDetailsResp
									.getCard().getCardId() : "");
							cardDetailsPojo.setNickName(cardDetailsResp
									.getNickName() != null ? cardDetailsResp
									.getNickName() : "");
							cardDetailsPojo
									.setCardNo(cardDetailsResp.getCard()
											.getCardNum().getCardNo() != null ? cardDetailsResp
											.getCard().getCardNum().getCardNo()
											: "");
							/************************ Bank Details ************************************************/
							Bank bankPojo = new Bank();
							if(cardDetailsResp.getCard().getBank()!=null){
							bankPojo.setBankName(cardDetailsResp.getCard()
									.getBank().getBankName() != null ? cardDetailsResp
									.getCard().getBank().getBankName()
									: "");
							}
							cardDetailsPojo.setBank(bankPojo);
							cardDetailsList.add(cardDetailsPojo);
						} else {
							com.isuite.rjil.iagent.jiomoney.common.CardDetails cardDetailsPojo = new com.isuite.rjil.iagent.jiomoney.common.CardDetails();
							cardDetailsPojo.setEmbossName("");
							cardDetailsPojo.setCardNo("");
							cardDetailsPojo.setCardType("");
							cardDetailsPojo.setCardCategory("");
							cardDetailsPojo.setExpMM("");
							cardDetailsPojo.setExpYY("");
							cardDetailsPojo.setExpieryDate("");
							cardDetailsPojo.setCardCategory("");
							cardDetailsPojo.setId("");
							cardDetailsPojo.setNickName("");
							cardDetailsList.add(cardDetailsPojo);
						}
						customerPojo.setCardDetails(cardDetailsList);

						customerPojo
								.setCustId(response.getCustId() != null ? response
										.getCustId() : "");
						// customerPersonalDetailsPojo.setCardDetails(cardDetailsPojo);
					}
				}
				/*************************** ResponseDetails **************************/
				/****************************** ResponseStatus ******************
				if (response.getResponseStatus() != null) {
					if (response.getResponseStatus().getInfos() != null) {
						for (TInfo tInfoResponse : response.getResponseStatus()
								.getInfos().getInfo()) {

							customerPojo.setProviderStatus(HeaderStatus
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
					/****************************** MessageContext ******************
					if (response.getRsHeader().getMessageContext() != null) {
						customerPojo.setHeaderMessage(HeaderStatus.setMessage(
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
					/********************** BusinessApplicationContext *******************
					if (response.getRsHeader().getBusinessApplicationContext() != null) {
						customerPojo.setBusinessApplication(HeaderStatus
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
				}*/
			}
			return customerPojo;
		} catch (InstrumentInquiryFault e) {
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
			soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
			
		}
	}
}
