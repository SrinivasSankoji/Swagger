package com.isuite.rjil.iagent.jiomoney.dao.impl;

import java.net.URL;
import java.util.Properties;
import java.util.UUID;

import javax.xml.ws.BindingProvider;

import org.apache.log4j.Logger;

import cam.com.ril.rpsl.entities.commontypes_v1_0.TStatus;
import cam.com.ril.rpsl.entities.header_v1_0.TBusinessApplicationContext;
import cam.com.ril.rpsl.entities.header_v1_0.TMessageContext;
import cam.com.ril.rpsl.entities.header_v1_0.TRqHeader;
import cam.com.ril.rpsl.services.customeraccountmangement_v1_0.BlockOrUnblockAccountRq;
import cam.com.ril.rpsl.services.customeraccountmangement_v1_0.BlockOrUnblockAccountRs;
import cam.com.ril.rpsl.services.customeraccountmangement_v1_0.ChangeAccountStatusRq;
import cam.com.ril.rpsl.services.customeraccountmangement_v1_0.ChangeAccountStatusRs;
import cam.com.ril.rpsl.services.customeraccountmangement_v1_0.TerminateAccountRq;
import cam.com.ril.rpsl.services.customeraccountmangement_v1_0.TerminateAccountRs;
import cam.com.ril.rpsl.wsdls.customeraccountmanagement_v1_0.CustomerAccountManagement;
import cam.com.ril.rpsl.wsdls.customeraccountmanagement_v1_0.CustomerAccountManagementFault;
import cam.com.ril.rpsl.wsdls.customeraccountmanagement_v1_0.Operations;

import com.isuite.rjil.iagent.jiomoney.common.Account;
import com.isuite.rjil.iagent.jiomoney.common.Customer;
import com.isuite.rjil.iagent.jiomoney.dao.CustomerAccountManagementDao;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;
import com.isuite.rjil.iagent.jiomoney.util.PropertiesUtil;
import com.isuite.rjil.iagent.jiomoney.util.WSHandlerResolver;

/**
 * Title: CustomerAccountManagementDaoImpl.java <br>
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
public class CustomerAccountManagementDaoImpl implements
		CustomerAccountManagementDao {
	private static final Logger logger = Logger
			.getLogger(CustomerAccountManagementDaoImpl.class);
	private static final Logger soapLogger = Logger
			.getLogger(LOVUtil.SOAPLOGGER);
	private Properties platformProperties = PropertiesUtil
			.getProperties(PropertiesUtil.platform);

	@Override
	public Customer changeAccountStatus(String custID, String accountNumber,
			String mobileNumber, String emailId, String status,
			String initiatedBy, String remarks, String agentId,long requestId)
			throws DataAccessException 
	{
		String lClassName =  this.getClass().getName();
		String lMethodName = "changeAccountStatus";
		
		soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");

		try {
			/******************* Setting the Header Request ******************/
			TRqHeader headerRequest = new TRqHeader();
			TMessageContext messageRequest = new TMessageContext();
			messageRequest.setMessageID(String.valueOf(UUID.randomUUID()));
			messageRequest.setCorrelationID(String.valueOf(UUID.randomUUID()));
			headerRequest.setMessageContext(messageRequest);
			TBusinessApplicationContext businessApplicationRequest = new TBusinessApplicationContext();

			businessApplicationRequest.setOperationName("changeAccountStatus");
			businessApplicationRequest.setMessageType("REQ");
			businessApplicationRequest.setSrcApplicationname("PORTAL");
			headerRequest
					.setBusinessApplicationContext(businessApplicationRequest);

			TStatus statusRequest = TStatus.valueOf(status);

			/******************* Sending the Request ******************/
			ChangeAccountStatusRq request = new ChangeAccountStatusRq();
			request.setStatus(statusRequest);
			request.setInitiatedBy(initiatedBy);
			request.setRemarks(remarks);
			request.setRqHeader(headerRequest);
			request.setCustId(custID.toUpperCase());
			request.setAccountNumber(accountNumber);
			request.setMobileNumber(mobileNumber);
			request.setEmailId(emailId);

			/******************* Setting the WSDL URL ******************/
			String customerAccountManagementWSDLURL = platformProperties
					.getProperty("customerAccountManagement");
			URL wsdlURL = new URL(customerAccountManagementWSDLURL);
			CustomerAccountManagement serviceagent = new CustomerAccountManagement(
					wsdlURL);
			serviceagent.setHandlerResolver(new WSHandlerResolver(agentId,requestId			));
			Operations endPoint = serviceagent.getOperationsEndpoint();
			/**************************************************************/

			/******************* Setting the endpoint URL For CustomerAccountManagement ******************/
			String endPointURL = platformProperties
					.getProperty("customerAccountManagementEndPoint");
			BindingProvider bindingProvider = (BindingProvider) endPoint;
			bindingProvider.getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointURL);

			/************************ Response ************************************************/
			ChangeAccountStatusRs response = endPoint
					.changeAccountStatus(request);

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
				// ******************Customer account Details
				// *******************************//*
				Account accountPojo = new Account();
				accountPojo
						.setAccountNumber(response.getAccountNumber() != null ? response
								.getAccountNumber() : "");
				customerPojo.setAccount(accountPojo);
				customerPojo.setCustId(response.getCustId() != null ? response
						.getCustId() : "");

				/*************************** ResponseDetails **************************/
				// isma card ka koi details nahi ha
				// isliye usma i.e grid ma conflict ho raha ha
				// service chala in SOAP UI ma
				/****************************** ResponseStatus *****************
				if (response.getResponseStatus() != null) {
					accountPojo
							.setStatus(response.getResponseStatus() != null ? response
									.getResponseStatus().getStatus() != null ? response
									.getResponseStatus().getStatus() : ""
									: "");
					if (response.getResponseStatus().getInfos() != null) {
						for (TInfo tInfoResponse : response.getResponseStatus()
								.getInfos().getInfo()) {
							accountPojo.setProviderStatus(HeaderStatus
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
					/****************************** MessageContext ****************
					if (response.getRsHeader().getMessageContext() != null) {
						accountPojo.setHeaderMessage(HeaderStatus.setMessage(
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

					/********************** BusinessApplicationContext ****************
					if (response.getRsHeader().getBusinessApplicationContext() != null) {
						accountPojo.setBusinessApplication(HeaderStatus
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
		} catch (CustomerAccountManagementFault e) {
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
	public Customer blockOrUnblockAccount(String custID, String accountNumber,
			String mobileNumber, String emailId, String status,
			String initiatedBy, String remarks, String agentId,long requestId)
			throws DataAccessException {
		String lClassName =  this.getClass().getName();
		String lMethodName = "blockOrUnblockAccount";
		
		soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		try {
			
			/******************* Setting the Header Request ******************/
			TRqHeader headerRequest = new TRqHeader();
			TMessageContext messageRequest = new TMessageContext();
			messageRequest.setMessageID(String.valueOf(UUID.randomUUID()));
			messageRequest.setCorrelationID(String.valueOf(UUID.randomUUID()));
			headerRequest.setMessageContext(messageRequest);
			TBusinessApplicationContext businessApplicationRequest = new TBusinessApplicationContext();

			businessApplicationRequest.setOperationName("changeAccountStatus");
			businessApplicationRequest.setMessageType("REQ");
			businessApplicationRequest.setSrcApplicationname("PORTAL");
			headerRequest
					.setBusinessApplicationContext(businessApplicationRequest);

			/******************* Sending the Request ******************/

			cam.com.ril.rpsl.services.customeraccountmangement_v1_0.TStatus statusRequest = cam.com.ril.rpsl.services.customeraccountmangement_v1_0.TStatus
					.valueOf(status);

			//  Check with below code to set Status
			// if(status.equalsIgnoreCase("BLOCK")){
			// statusRequest=cam.com.ril.rpsl.services.customeraccountmangement_v1_0.TStatus.BLOCK;
			// }
			// if(status.equalsIgnoreCase("UNBLOCK")){
			// statusRequest=cam.com.ril.rpsl.services.customeraccountmangement_v1_0.TStatus.UNBLOCK;
			// }

			BlockOrUnblockAccountRq request = new BlockOrUnblockAccountRq();
			request.setCustId(custID.toUpperCase());
			request.setAccountNumber(accountNumber);
			request.setMobileNumber(mobileNumber);
			request.setEmailId(emailId);
			request.setStatus(statusRequest);
			request.setInitiatedBy(platformProperties
					.getProperty("initiatedBy"));
			request.setRemarks(remarks);
			/******************* Setting the WSDL URL ******************/
			String customerAccountManagementWSDLURL = platformProperties
					.getProperty("customerAccountManagement");
			URL wsdlURL = new URL(customerAccountManagementWSDLURL);
			CustomerAccountManagement serviceagent = new CustomerAccountManagement(
					wsdlURL);
			serviceagent.setHandlerResolver(new WSHandlerResolver(agentId,requestId				));
			Operations endPoint = serviceagent.getOperationsEndpoint();
			/********************** End the WSDL URL **************/

			/******************* Setting the endpoint URL For CustomerAccountManagement ******************/
			String endPointURL = platformProperties
					.getProperty("customerAccountManagementEndPoint");
			BindingProvider bindingProvider = (BindingProvider) endPoint;
			bindingProvider.getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointURL);

			/************************ Response ************************************************/
			BlockOrUnblockAccountRs response = endPoint
					.blockOrUnblockAccount(request);

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
			Customer customerAccountPojo = null;
			if (response != null) {
				customerAccountPojo = new Customer();
				/******************* blockOrUnblockAccount Status Info ******************/
				Account accountPojo = new Account();
				accountPojo
						.setAccountNumber(response.getAccountNumber() != null ? response
								.getAccountNumber() : "");
				customerAccountPojo.setAccount(accountPojo);
				customerAccountPojo
						.setCustId(response.getCustId() != null ? response
								.getCustId() : "");

				/*************************** ResponseDetails**************AccountTransaction **********
				if (response.getResponseStatus() != null) {
					accountPojo
							.setStatus(response.getResponseStatus() != null ? response
									.getResponseStatus().getStatus() != null ? response
									.getResponseStatus().getStatus() : ""
									: "");
					if (response.getResponseStatus().getInfos() != null) {
						for (TInfo tInfoResponse : response.getResponseStatus()
								.getInfos().getInfo()) {
							accountPojo.setProviderStatus(HeaderStatus
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
					/****************************** MessageContext ***************
					if (response.getRsHeader().getMessageContext() != null) {
						accountPojo.setHeaderMessage(HeaderStatus.setMessage(
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
						/********************** BusinessApplicationContext ******************

						accountPojo.setBusinessApplication(HeaderStatus
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
			return customerAccountPojo;
		} catch (CustomerAccountManagementFault e) {
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
	public Customer terminateAccount(String custID, String mobileNumber,
			String initiatedBy, String remarks, String agentId,long requestId)
			throws DataAccessException {
		String lClassName =  this.getClass().getName();
		String lMethodName = "terminateAccount";
		
		soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		try {
						/******************* Setting the Header Request ******************/
			TRqHeader headerRequest = new TRqHeader();
			TMessageContext messageRequest = new TMessageContext();
			messageRequest.setMessageID(String.valueOf(UUID.randomUUID()));
			messageRequest.setCorrelationID(String.valueOf(UUID.randomUUID()));
			headerRequest.setMessageContext(messageRequest);
			TBusinessApplicationContext businessApplicationRequest = new TBusinessApplicationContext();
			businessApplicationRequest.setOperationName("changeAccountStatus");
			businessApplicationRequest.setMessageType("REQ");
			businessApplicationRequest.setSrcApplicationname("PORTAL");
			headerRequest
					.setBusinessApplicationContext(businessApplicationRequest);
			businessApplicationRequest.setMessageType(platformProperties
					.getProperty("terminateAccount_messageType"));
			businessApplicationRequest.setSrcApplicationname(platformProperties
					.getProperty("terminateAccount_applicationName"));
			headerRequest
					.setBusinessApplicationContext(businessApplicationRequest);

			/********************************** setting the Request ************************/
			TerminateAccountRq request = new TerminateAccountRq();
			request.setRqHeader(headerRequest);
			if (custID != null && !custID.isEmpty()) {
				request.setCustId(custID);
			}
			request.setMobileNumber(mobileNumber);
			request.setInitiatedBy(initiatedBy);
			request.setRemarks(remarks);
			/******************* Setting the WSDL URL ******************/
			String customerAccountManagementWSDLURL = platformProperties
					.getProperty("customerAccountManagement");
			URL wsdlURL = new URL(customerAccountManagementWSDLURL);
			CustomerAccountManagement serviceagent = new CustomerAccountManagement(
					wsdlURL);
			serviceagent.setHandlerResolver(new WSHandlerResolver(agentId,requestId				));
			Operations endPoint = serviceagent.getOperationsEndpoint();
			/********************** End the WSDL URL **************/

			/******************* Setting the endpoint URL For CustomerAccountManagement ******************/
			String endPointURL = platformProperties
					.getProperty("customerAccountManagementEndPoint");
			BindingProvider bindingProvider = (BindingProvider) endPoint;
			bindingProvider.getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointURL);

			/************************ Response ************************************************/
			TerminateAccountRs response = endPoint.terminateAccount(request);

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
			Customer customerPojo = null;
			if (response != null) {
				customerPojo = new Customer();
				Account accountPojo = new Account();
				accountPojo
						.setAccountNumber(response.getAccountNumber() != null ? response
								.getAccountNumber() : "");
				customerPojo.setAccount(accountPojo);
				customerPojo.setCustId(response.getCustId() != null ? response
						.getCustId() : "");

				/******************* TerminateAccount Status Info ******************/
				/*if (response.getResponseStatus() != null) {
					customerPojo
							.setStatus(response.getResponseStatus() != null ? response
									.getResponseStatus().getStatus() != null ? response
									.getResponseStatus().getStatus() : ""
									: "");
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
				}*/
				/*if (response.getRsHeader() != null) {
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
		} catch (CustomerAccountManagementFault e) {
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
