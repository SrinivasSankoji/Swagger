package com.isuite.rjil.iagent.jiomoney.dao.impl;

import instmng.com.ril.rpsl.entities.commontypes_v1_0.TCardNo;
import instmng.com.ril.rpsl.entities.commontypes_v1_0.TInfo;
import instmng.com.ril.rpsl.entities.commontypes_v1_0.TStatus;
import instmng.com.ril.rpsl.entities.header_v1_0.TBusinessApplicationContext;
import instmng.com.ril.rpsl.entities.header_v1_0.TMessageContext;
import instmng.com.ril.rpsl.entities.header_v1_0.TRqHeader;
import instmng.com.ril.rpsl.services.instrumentmanagement_v1_0.ChangePhysicalCardStatusRq;
import instmng.com.ril.rpsl.services.instrumentmanagement_v1_0.ChangePhysicalCardStatusRs;
import instmng.com.ril.rpsl.services.instrumentmanagement_v1_0.UnlinkPhysicalCardRq;
import instmng.com.ril.rpsl.services.instrumentmanagement_v1_0.UnlinkPhysicalCardRs;
import instmng.com.ril.rpsl.wsdls.instrumentmanagement_v1_0.InstrumentManagementFault;
import instmng.com.ril.rpsl.wsdls.instrumentmanagement_v1_0.InstrumentManagementServiceagent;
import instmng.com.ril.rpsl.wsdls.instrumentmanagement_v1_0.Operations;

import java.net.URL;
import java.util.Properties;
import java.util.UUID;

import javax.xml.ws.BindingProvider;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.common.CardDetails;
import com.isuite.rjil.iagent.jiomoney.dao.InstrumentManagementDao;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.exception.ServiceException;
import com.isuite.rjil.iagent.jiomoney.util.HeaderStatus;
import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;
import com.isuite.rjil.iagent.jiomoney.util.PropertiesUtil;
import com.isuite.rjil.iagent.jiomoney.util.WSHandlerResolver;
import com.isuite.rjil.iagent.jiomoney.util.XMLGregorianCalendarUtil;

/**
 * Title: InstrumentManagementDaoImpl.java <br>
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
public class InstrumentManagementDaoImpl implements InstrumentManagementDao {

	private static final Logger logger = Logger
			.getLogger(InstrumentManagementDaoImpl.class);
	private static final Logger soapLogger = Logger
			.getLogger(LOVUtil.SOAPLOGGER);

	private Properties platformProperties = PropertiesUtil
			.getProperties(PropertiesUtil.platform);

	@Override
	public CardDetails changePhysicalCardStatus(String cardAlias,
			String status, String initiatedBy, String remarks, String agentId,long requestId)
			throws DataAccessException {
		try {
			logger.info("Request------------InstrumentManagementDaoImpl-----------changePhysicalCardStatus()--------");
			soapLogger
					.info("Request & Response for------------InstrumentManagementDaoImpl-----------changePhysicalCardStatus()--------");
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

			/*
			 * 
			 * String instrumentInquiryWsdlUrl = platformProperties
			 * .getProperty("instrumentManagement");
			 * 
			 * URL wsdlUrl = new URL(instrumentInquiryWsdlUrl);
			 * InstrumentManagementServiceagent serviceagent = new
			 * InstrumentManagementServiceagent( wsdlUrl);
			 * serviceagent.setHandlerResolver(new WSHandlerResolver("", ""));
			 * endPoint = serviceagent.getOperationsEndpoint();
			 *//******************* Setting the endpoint URL For InstrumentManagement ******************/

			businessApplicationRequest
					.setOperationName("changePhysicalCardStatus");
			businessApplicationRequest.setMessageType("REQ");
			businessApplicationRequest.setSrcApplicationname("PORTAL");
			headerRequest
					.setBusinessApplicationContext(businessApplicationRequest);
			/***************************************************************/

			/******************* set the initiatedByValue By properties File ******************/
			ChangePhysicalCardStatusRq request = new ChangePhysicalCardStatusRq();
			if (status != null && status.equalsIgnoreCase("INACTIVE")) {
				request.setStatus(TStatus.INACTIVE);
			}
			if (status != null && status.equalsIgnoreCase("ACTIVE")) {
				request.setStatus(TStatus.ACTIVE);
			}

			/******************* set the Request for ChangePhysicalCardStatusRq ******************/
			request.setRqHeader(headerRequest);
			request.setCardAlias(cardAlias);
			request.setInitiatedBy(initiatedBy);
			request.setRemarks(remarks);
			/******************* Setting the WSDL URL ******************/

			String instrumentInquiryWsdlUrl = platformProperties
					.getProperty("instrumentManagement");

			URL wsdlUrl = new URL(instrumentInquiryWsdlUrl);
			InstrumentManagementServiceagent serviceagent = new InstrumentManagementServiceagent(
					wsdlUrl);
			serviceagent.setHandlerResolver(new WSHandlerResolver(agentId,requestId				));
			Operations endPoint = serviceagent.getOperationsEndpoint();
			/******************* Setting the endpoint URL For InstrumentManagement ******************/
			String endPointURL = platformProperties
					.getProperty("instrumentManagementEndPoint");
			BindingProvider bindingProvider = (BindingProvider) endPoint;
			bindingProvider.getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointURL);
			/************************ Response ************************************************/
			ChangePhysicalCardStatusRs response = endPoint
					.changePhysicalCardStatus(request);

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
					logger.error("EXCEPTION__________STATUS__________IN_RESPONSE_____________FAILED ");
					throw new DataAccessException(response.getResponseStatus()
							.getErrors().getError().get(0).getErrorMsg());
				}

			}
			CardDetails cardDetailsPojo = null;
			if (response != null) {
				cardDetailsPojo = new CardDetails();
				cardDetailsPojo
						.setCustomerId(response.getCardId() != null ? response
								.getCardId() : "");
				cardDetailsPojo.setId(response.getCardId() != null ? response
						.getCardId() : "");
				cardDetailsPojo
						.setCardKeyRef(response.getCardNo() != null ? response
								.getCardNo().getCardKeyRef() != null ? response
								.getCardNo().getCardKeyRef() : "" : "");
				cardDetailsPojo
						.setCardKeyType(response.getCardNo() != null ? response
								.getCardNo().getCardKeyType() != null ? response
								.getCardNo().getCardKeyType() : ""
								: "");
				cardDetailsPojo
						.setAlias(response.getCardAlias() != null ? response
								.getCardAlias() : "");
				cardDetailsPojo
						.setNickName(response.getNickName() != null ? response
								.getNickName() : "");

			}

			/*************************** ResponseDetails **************************/
			/****************************** ResponseStatus ********************/
			if (response != null && response.getResponseStatus() != null) {
				if (response.getResponseStatus().getInfos() != null) {

					for (TInfo tInfoResponse : response.getResponseStatus()
							.getInfos().getInfo()) {
						cardDetailsPojo.setProviderStatus(HeaderStatus
								.setStatus(tInfoResponse.getCode(),
										tInfoResponse.getMsg(), tInfoResponse
												.getProvider()
												.getProviderName(),
										tInfoResponse.getProvider()
												.getProviderCode()));
					}
				}
			}
			if (response != null && response.getRsHeader() != null) {
				/****************************** MessageContext ********************/
				if (response.getRsHeader().getMessageContext() != null) {
					cardDetailsPojo.setHeaderMessage(HeaderStatus.setMessage(
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

				/********************** BusinessApplicationContext ********************/
				if (response.getRsHeader().getBusinessApplicationContext() != null) {
					cardDetailsPojo.setBusinessApplication(HeaderStatus
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
			logger.info("Response------------InstrumentManagementDaoImpl-----------changePhysicalCardStatus()--------");
			return cardDetailsPojo;
		} catch (InstrumentManagementFault e) {
			logger.error("Exception in DAO--> getting InstrumentManagementDaoImpl:--changePhysicalCardStatus");
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
			logger.error(
					"Exception :- DAO --> catched in :- changePhysicalCardStatus",
					t);
			throw new DataAccessException(t.getMessage(), t);
		}
	}

	@Override
	public CardDetails unLinkPhysicalCard(String cardAlias, String cardNo,
			String agentId,long requestId) throws DataAccessException {
		try {
			logger.info("Request in DAO--> getting InstrumentManagementDaoImpl:--unLinkPhysicalCard");
			soapLogger
					.debug("Request & Response for --> getting InstrumentManagementDaoImpl:--unLinkPhysicalCard");
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
			businessApplicationRequest.setOperationName("unLinkPhysicalCard");
			businessApplicationRequest.setMessageType("REQ");
			businessApplicationRequest.setSrcApplicationname("PORTAL");
			headerRequest
					.setBusinessApplicationContext(businessApplicationRequest);
			/***************************************************************/

			TCardNo cardNoRequest = new TCardNo();
			cardNoRequest.setCardNo(cardNo);

			/******************************* Setting Request ***************************************/
			UnlinkPhysicalCardRq request = new UnlinkPhysicalCardRq();
			request.setCardNo(cardNoRequest);
			request.setRqHeader(headerRequest);
			request.setCardAlias(cardAlias);
			/******************* Setting the WSDL URL ******************/
			String instrumentInquiryWsdlUrl = platformProperties
					.getProperty("instrumentManagement");

			URL wsdlUrl = new URL(instrumentInquiryWsdlUrl);
			InstrumentManagementServiceagent serviceagent = new InstrumentManagementServiceagent(
					wsdlUrl);
			serviceagent.setHandlerResolver(new WSHandlerResolver(agentId,requestId				));
			Operations endPoint = serviceagent.getOperationsEndpoint();
			/******************* Setting the endpoint URL For InstrumentManagement ******************/
			String endPointURL = platformProperties
					.getProperty("instrumentManagementEndPoint");
			BindingProvider bindingProvider = (BindingProvider) endPoint;
			bindingProvider.getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointURL);

			/************************ Response ************************************************/
			UnlinkPhysicalCardRs response = endPoint
					.unlinkPhysicalCard(request);

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
					logger.error("EXCEPTION__________STATUS__________IN_RESPONSE_____________FAILED ");
					throw new DataAccessException(response.getResponseStatus()
							.getErrors().getError().get(0).getErrorMsg());
				}

			}
			CardDetails cardDetailsPojo = null;
			if (response != null) {
				cardDetailsPojo = new CardDetails();
				cardDetailsPojo
						.setAlias(response.getCardAlias() != null ? response
								.getCardAlias() : "");
			}

			if (response != null && response.getResponseStatus() != null) {
				if (response.getResponseStatus().getInfos() != null) {

					for (TInfo tInfoResponse : response.getResponseStatus()
							.getInfos().getInfo()) {
						cardDetailsPojo.setProviderStatus(HeaderStatus
								.setStatus(tInfoResponse.getCode(),
										tInfoResponse.getMsg(), tInfoResponse
												.getProvider()
												.getProviderName(),
										tInfoResponse.getProvider()
												.getProviderCode()));
					}
				}
			}

			if (response != null && response.getRsHeader() != null) {
				/****************************** MessageContext ********************/
				cardDetailsPojo.setHeaderMessage(HeaderStatus.setMessage(
						response.getRsHeader().getMessageContext()
								.getBusinessID(), response.getRsHeader()
								.getMessageContext().getEsbID(), response
								.getRsHeader().getMessageContext()
								.getMessageID(), response.getRsHeader()
								.getMessageContext().getCorrelationID(),
						response.getRsHeader().getMessageContext()
								.getExtCorrelationID(), response.getRsHeader()
								.getMessageContext().getTimeStamp()));

				/********************** BusinessApplicationContext ********************/

				if (response.getRsHeader().getBusinessApplicationContext() != null) {

					cardDetailsPojo.setBusinessApplication(HeaderStatus
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
			logger.info("Response in DAO--> getting InstrumentManagementDaoImpl:--unLinkPhysicalCard");
			return cardDetailsPojo;
		} catch (InstrumentManagementFault e) {
			logger.error("Exception in DAO--> getting InstrumentManagementDaoImpl:--unLinkPhysicalCard");
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
			logger.error(
					"Exception :- DAO --> catched in :- unLinkPhysicalCard", t);
			throw new DataAccessException(t.getMessage(), t);
		}
	}

	@Override
	public CardDetails changePhysicalCardStatusForWallet(String custId,
			String status, String initiatedBy, String remarks, String agentId,long requestId)
			throws DataAccessException, ServiceException {
		try {
			logger.info("Request------------InstrumentManagementDaoImpl-----------changePhysicalCardStatusForWallet()--------");
			soapLogger
					.debug("Request & Response for------------InstrumentManagementDaoImpl-----------changePhysicalCardStatusForWallet()--------");
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

			businessApplicationRequest
					.setOperationName("changePhysicalCardStatus");
			businessApplicationRequest.setMessageType("REQ");
			businessApplicationRequest.setSrcApplicationname("PORTAL");
			headerRequest
					.setBusinessApplicationContext(businessApplicationRequest);
			/***************************************************************/

			/******************* set the initiatedByValue By properties File ******************/
			ChangePhysicalCardStatusRq request = new ChangePhysicalCardStatusRq();
			if (status != null && status.equalsIgnoreCase("INACTIVE")) {
				request.setStatus(TStatus.INACTIVE);
			}
			if (status != null && status.equalsIgnoreCase("ACTIVE")) {
				request.setStatus(TStatus.ACTIVE);
			}

			/******************* set the Request for ChangePhysicalCardStatusRq ******************/
			request.setRqHeader(headerRequest);
			request.setCustId(custId);
			request.setInitiatedBy(initiatedBy);
			request.setRemarks(remarks);

			/******************* Setting the WSDL URL ******************/
			String instrumentInquiryWsdlUrl = platformProperties
					.getProperty("instrumentManagement");

			URL wsdlUrl = new URL(instrumentInquiryWsdlUrl);
			InstrumentManagementServiceagent serviceagent = new InstrumentManagementServiceagent(
					wsdlUrl);
			serviceagent.setHandlerResolver(new WSHandlerResolver(agentId,requestId	));
			Operations endPoint = serviceagent.getOperationsEndpoint();
			/******************* Setting the endpoint URL For InstrumentManagement ******************/
			String endPointURL = platformProperties
					.getProperty("instrumentManagementEndPoint");
			BindingProvider bindingProvider = (BindingProvider) endPoint;
			bindingProvider.getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointURL);

			/************************ Response ************************************************/
			ChangePhysicalCardStatusRs response = endPoint
					.changePhysicalCardStatus(request);

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
					logger.error("EXCEPTION__________STATUS__________IN_RESPONSE_____________FAILED ");
					throw new DataAccessException(response.getResponseStatus()
							.getErrors().getError().get(0).getErrorMsg());
				}

			}
			CardDetails cardDetailsPojo = null;
			if (response != null) {
				cardDetailsPojo = new CardDetails();
				cardDetailsPojo
						.setCustomerId(response.getCardId() != null ? response
								.getCardId() : "");
				cardDetailsPojo.setId(response.getCardId() != null ? response
						.getCardId() : "");
				cardDetailsPojo
						.setCardKeyRef(response.getCardNo() != null ? response
								.getCardNo().getCardKeyRef() != null ? response
								.getCardNo().getCardKeyRef() : "" : "");
				cardDetailsPojo
						.setCardKeyType(response.getCardNo() != null ? response
								.getCardNo().getCardKeyType() != null ? response
								.getCardNo().getCardKeyType() : ""
								: "");
				cardDetailsPojo
						.setAlias(response.getCardAlias() != null ? response
								.getCardAlias() : "");
				cardDetailsPojo
						.setNickName(response.getNickName() != null ? response
								.getNickName() : "");

			}

			/*************************** ResponseDetails **************************/
			/****************************** ResponseStatus ********************/
			if (response != null && response.getResponseStatus() != null) {
				if (response.getResponseStatus().getInfos() != null) {

					for (TInfo tInfoResponse : response.getResponseStatus()
							.getInfos().getInfo()) {
						cardDetailsPojo.setProviderStatus(HeaderStatus
								.setStatus(tInfoResponse.getCode(),
										tInfoResponse.getMsg(), tInfoResponse
												.getProvider()
												.getProviderName(),
										tInfoResponse.getProvider()
												.getProviderCode()));
					}
				}
			}
			if (response != null && response.getRsHeader() != null) {
				/****************************** MessageContext ********************/
				if (response.getRsHeader().getMessageContext() != null) {
					cardDetailsPojo.setHeaderMessage(HeaderStatus.setMessage(
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

				/********************** BusinessApplicationContext ********************/
				if (response.getRsHeader().getBusinessApplicationContext() != null) {
					cardDetailsPojo.setBusinessApplication(HeaderStatus
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
			logger.info("Response------------InstrumentManagementDaoImpl-----------changePhysicalCardStatusForWallet()--------");
			return cardDetailsPojo;
		} catch (InstrumentManagementFault e) {
			logger.error("Exception in DAO--> getting InstrumentManagementDaoImpl:--changePhysicalCardStatusForWallet");
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
			logger.error(
					"Exception :- DAO --> catched in :- changePhysicalCardStatusForWallet",
					t);
			throw new DataAccessException(t.getMessage(), t);
		}
	}
}
