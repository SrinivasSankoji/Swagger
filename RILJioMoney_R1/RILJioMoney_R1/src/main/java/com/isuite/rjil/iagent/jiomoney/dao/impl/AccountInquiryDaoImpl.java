/**
 * 
 */
package com.isuite.rjil.iagent.jiomoney.dao.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.annotation.processing.SupportedSourceVersion;
import javax.xml.ws.BindingProvider;

import org.apache.log4j.Logger;

import acci.com.ril.rpsl.entities.commontypes_v1_0.TAccount;
import acci.com.ril.rpsl.entities.header_v1_0.TBusinessApplicationContext;
import acci.com.ril.rpsl.entities.header_v1_0.TMessageContext;
import acci.com.ril.rpsl.entities.header_v1_0.TRqHeader;
import acci.com.ril.rpsl.services.accountinquiry_v1_0.GetAccountBalanceRq;
import acci.com.ril.rpsl.services.accountinquiry_v1_0.GetAccountBalanceRs;
import acci.com.ril.rpsl.services.accountinquiry_v1_0.ValidateLoadMoneyRq;
import acci.com.ril.rpsl.services.accountinquiry_v1_0.ValidateLoadMoneyRs;
import acci.com.ril.rpsl.wsdls.accountinquiry_v1_0.AccountInquiry;
import acci.com.ril.rpsl.wsdls.accountinquiry_v1_0.AccountInquiryFault;
import acci.com.ril.rpsl.wsdls.accountinquiry_v1_0.Operations;

import com.isuite.rjil.iagent.jiomoney.common.Account;
import com.isuite.rjil.iagent.jiomoney.common.Bank;
import com.isuite.rjil.iagent.jiomoney.dao.AccountInquiryDao;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;
import com.isuite.rjil.iagent.jiomoney.util.PropertiesUtil;
import com.isuite.rjil.iagent.jiomoney.util.Util;
import com.isuite.rjil.iagent.jiomoney.util.WSHandlerResolver;
import com.isuite.rjil.iagent.jiomoney.util.XMLGregorianCalendarUtil;


/**
 * Title: AccountInquiryDaoImpl.java <br>
 * Company: 2014-15 NovelVox Software Pvt Ltd<br>
 * 
 * @author Anand Raman [AR]
 * @version Build 0.5 <br>
 *          Changes and requirement:<br>
 *          <ul>
 * 
 *          </ul>
 * @version ReleaseR1-Sprint 3 Changes and requirement:<br>
 *          <ul>
 *          <li>1-9-20115 [AR] [CR] Change in WSDL end Point
 *          </ul>
 * 
 */
public class AccountInquiryDaoImpl implements AccountInquiryDao {
	private static final Logger logger = Logger.getLogger(AccountInquiryDaoImpl.class);
	private static final Logger soapLogger = Logger.getLogger(LOVUtil.SOAPLOGGER);
	private Properties platformProperties = PropertiesUtil.getProperties(PropertiesUtil.platform);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.isuite.rjil.iagent.jiomoney.dao.AccountInquiryDao#getAccountBalance
	 * (java.lang.String)
	 */
	@Override
	public Account getAccountBalance(String accountId, String custId,
			String currency, String branchId, String institutionCode,
			String agentId,long requestId) throws DataAccessException {
		String lClassName =  this.getClass().getName();
		String lMethodName = "getAccountBalance";
		
		soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		try {
			
			/******************* Setting the Header Request ******************/

			TRqHeader headerRequest = new TRqHeader();
			TMessageContext messageContextRequest = new TMessageContext();
			messageContextRequest.setCorrelationID(String.valueOf(UUID
					.randomUUID()));
			// messageContextRequest.setEsbID("");
			messageContextRequest.setTimeStamp(XMLGregorianCalendarUtil
					.getSystemTime());
			messageContextRequest
					.setMessageID(String.valueOf(UUID.randomUUID()));
			headerRequest.setMessageContext(messageContextRequest);
			TBusinessApplicationContext businessApplicationContext = new TBusinessApplicationContext();
			headerRequest
					.setBusinessApplicationContext(businessApplicationContext);

			/***** Setting Account Details **************/
			TAccount accountRequest = new TAccount();
			accountRequest.setAccountId(accountId);
			accountRequest.setCurrency(currency);
			accountRequest.setBranchId(branchId);
			accountRequest.setInstitutionId(institutionCode);

			/******** Setting All Request *******************/
			GetAccountBalanceRq request = new GetAccountBalanceRq();
			request.setAccount(accountRequest);
			request.setRqHeader(headerRequest);
			request.setCustomerId(custId.toUpperCase());

			/******************* Setting the WSDL URL ******************/
			platformProperties = PropertiesUtil
					.getProperties(PropertiesUtil.platform);
			String validateLoadMoneyWSDLURL = platformProperties
					.getProperty("accountInquiry");
			URL wsdlURL = new URL(validateLoadMoneyWSDLURL);
			// 1-9-2015 [AR] [CH] Change in wsdl
			// AccountInquiryServiceagent serviceagent = new
			// AccountInquiryServiceagent(
			// wsdlURL);
			AccountInquiry serviceagent = new AccountInquiry(wsdlURL);
			serviceagent.setHandlerResolver(new WSHandlerResolver(agentId,requestId));

			Operations opedot1 = serviceagent.getOperationsEndpoint();
			/******************* Setting the endpoint URL For AccountInquiry ******************/
			String endPointURL = platformProperties
					.getProperty("accountInquiryEndPoint");
			BindingProvider bindingProvider = (BindingProvider) opedot1;
			bindingProvider.getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointURL);

			/************************ Response ************************************************/
			GetAccountBalanceRs response = opedot1.getAccountBalance(request);

			if (response != null
					&& response.getResponseDetails() != null
					&& response.getResponseDetails().getStatus() != null
					&& response.getResponseDetails().getStatus()
							.equalsIgnoreCase("FAILED")) {
				if (response.getResponseDetails().getErrors() != null
						&& response.getResponseDetails().getErrors().getError() != null
						&& response.getResponseDetails().getErrors().getError()
								.size() > 0
						&& response.getResponseDetails().getErrors().getError()
								.get(0).getErrorMsg() != null) {
					throw new DataAccessException(response.getResponseDetails()
							.getErrors().getError().get(0).getErrorMsg());
				}
			}

			Account accountPojo = null;
			if (response != null) {
				accountPojo = new Account();
				/******************* Account Info ******************/
				accountPojo
						.setAccountBalance(response.getAccountBalance() != null ? response
								.getAccountBalance() : "");
				accountPojo
						.setAccountId(response.getAccountId() != null ? response
								.getAccountId() : "");
				accountPojo
						.setAccountStatus(response.getStatus() != null ? response
								.getStatus() : "");

				
				accountPojo
				.setBlkAmount(response.getBlkAmount() != null ? response
						.getBlkAmount() : "");
		
//				if(Util.isValidInt(accountPojo.getAccountBalance()) && Util.isValidInt(accountPojo.getAccountBalance()) ){
//				int ledgerBal = Integer.parseInt(accountPojo.getAccountBalance()) - Integer.parseInt(accountPojo.getBlkAmount());
//				accountPojo.setLedgerBal(ledgerBal+"");
//			}
			
			try{
				
				double ledgerBal = Double.parseDouble(accountPojo.getAccountBalance()) - Double.parseDouble(accountPojo.getBlkAmount());
				accountPojo.setLedgerBal(ledgerBal+"");
				
			}catch(Exception e)
			{
				accountPojo.setLedgerBal("");
			}
			
				
				/*************************** ResponseDetails **************************/
				/****************************** ResponseStatus ********************/
				/*if (response.getResponseDetails() != null) {
					accountPojo
							.setStatus(response.getResponseDetails() != null ? response
									.getResponseDetails().getStatus() != null ? response
									.getResponseDetails().getStatus() : ""
									: "");
					if (response.getResponseDetails().getInfos() != null) {
						for (TInfo tInfoResponse : response
								.getResponseDetails().getInfos().getInfo()) {

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

						
						if (response.getRsHeader()
								.getBusinessApplicationContext() != null) {
							accountPojo.setBusinessApplication(HeaderStatus
									.setBusinessApplication(response
											.getRsHeader()
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
				}*/
			}
			
			return accountPojo;
		} catch (AccountInquiryFault e) 
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.isuite.rjil.iagent.jiomoney.dao.AccountInquiryDao#validateLoadMoney
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public List<Bank> validateLoadMoney(String custId, String accountNo,
			String mobileNo, String amount, String loadType, String loadSource,
			String currency, String branchId, String institutionCode,
			String agentId,long requestId) throws DataAccessException 
			{
		String lClassName =  this.getClass().getName();
		String lMethodName = "validateLoadMoney";
		
		soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		try {

			/******************* Setting the Header Request ******************/
			TRqHeader headerRequest = new TRqHeader();
			TMessageContext messageContextRequest = new TMessageContext();
			messageContextRequest.setCorrelationID(String.valueOf(UUID
					.randomUUID()));
			// messageContextRequest.setEsbID("");
			messageContextRequest.setTimeStamp(XMLGregorianCalendarUtil
					.getSystemTime());
			messageContextRequest
					.setMessageID(String.valueOf(UUID.randomUUID()));
			headerRequest.setMessageContext(messageContextRequest);
			TBusinessApplicationContext businessApplicationContext = new TBusinessApplicationContext();
			businessApplicationContext.setOperationName("validateLoadMoney");
			headerRequest
					.setBusinessApplicationContext(businessApplicationContext);

			/************* Setting Account Details ******/
			TAccount accountRequest = new TAccount();
			accountRequest.setAccountNumber(accountNo);
			accountRequest.setCurrency(currency);
			accountRequest.setBranchId(branchId);
			accountRequest.setInstitutionId(institutionCode);
			//accountRequest.setAccountBalance(amount);
			accountRequest.setAccountBalance("0");

			/******************* Setting the Request Values ******************/
			ValidateLoadMoneyRq request = new ValidateLoadMoneyRq();
			request.setCustomerId(custId.toUpperCase());
			request.setAccount(accountRequest);
			request.setLoadType(loadType);
			request.setLoadSrc(loadSource);
			request.setRqHeader(headerRequest);
			request.setExtCode("1");
			request.setLocalDate("");
			request.setLocalTime("");

			/******************* Setting the WSDL URL ******************/
			platformProperties = PropertiesUtil
					.getProperties(PropertiesUtil.platform);
			String validateLoadMoneyWSDLURL = platformProperties
					.getProperty("accountInquiry");
			URL wsdlURL = new URL(validateLoadMoneyWSDLURL);
			// 1-9-2015 [AR] [CH] Change in wsdl
			// AccountInquiryServiceagent serviceagent = new
			// AccountInquiryServiceagent(
			// wsdlURL);
			AccountInquiry serviceagent = new AccountInquiry(wsdlURL);
			serviceagent.setHandlerResolver(new WSHandlerResolver(agentId,requestId));
			Operations opedot1 = serviceagent.getOperationsEndpoint();
			/******************* Setting the endpoint URL For AccountInquiry ******************/
			String endPointURL = platformProperties
					.getProperty("accountInquiryEndPoint");
			BindingProvider bindingProvider = (BindingProvider) opedot1;
			bindingProvider.getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointURL);

			/************************ Response ************************************************/
			ValidateLoadMoneyRs response = opedot1.validateLoadMoney(request);
			if (response != null
					&& response.getResponseDetails() != null
					&& response.getResponseDetails().getStatus() != null
					&& response.getResponseDetails().getStatus()
							.equalsIgnoreCase("FAILED")) {
				if (response.getResponseDetails().getErrors() != null
						&& response.getResponseDetails().getErrors().getError() != null
						&& response.getResponseDetails().getErrors().getError()
								.size() > 0
						&& response.getResponseDetails().getErrors().getError()
								.get(0).getErrorMsg() != null) {
					throw new DataAccessException(response.getResponseDetails()
							.getErrors().getError().get(0).getErrorMsg());
				}
			}
			List<Bank> returnList = null;
			if (response != null) {

				returnList = new ArrayList<Bank>();

				/******************* Bank Info ******************/
				Bank bankPojo = new Bank();
				bankPojo.setBankId(response.getAccount() != null ? response
						.getAccount().getBank() != null ? response.getAccount()
						.getBank().getBankId() != null ? response.getAccount()
						.getBank().getBankId() : "" : "" : "");
				bankPojo.setBankName(response.getAccount() != null ? response
						.getAccount().getBank() != null ? response.getAccount()
						.getBank().getBankName() != null ? response
						.getAccount().getBank().getBankName() : "" : "" : "");
				bankPojo.setBankType(response.getAccount() != null ? response
						.getAccount().getBank() != null ? response.getAccount()
						.getBank().getBankType() != null ? response
						.getAccount().getBank().getBankType() : "" : "" : "");

				/******************* Account Info ******************/
				Account accountPojo = new Account();
				accountPojo
						.setAccountNumber(response.getAccount() != null ? response
								.getAccount().getAccountNumber() != null ? response
								.getAccount().getAccountNumber() : ""
								: "");
				accountPojo
						.setCurrency(response.getAccount() != null ? response
								.getAccount().getCurrency() != null ? response
								.getAccount().getCurrency() : "" : "");
				accountPojo
						.setAmountBill(response.getAmountBill() != null ? response
								.getAmountBill() : "");
				accountPojo
						.setBlkAmount(response.getBlkAmount() != null ? response
								.getBlkAmount() : "");
				accountPojo
						.setAccountBalance(response.getAccount() != null ? response
								.getAccount().getAccountBalance() != null ? response
								.getAccount().getAccountBalance() : ""
								: "");
				accountPojo
						.setAmountBillCurrency(response.getAmountBillCurr() != null ? response
								.getAmountBillCurr() : "");

				accountPojo
						.setAccountId(response.getAccount() != null ? response
								.getAccount().getAccountId() != null ? response
								.getAccount().getAccountId() : "" : "");
				accountPojo
						.setAccountName(response.getAccount() != null ? response
								.getAccount().getAccountName() != null ? response
								.getAccount().getAccountName() : ""
								: "");
				accountPojo
						.setAccountOpenDate(response.getAccount() != null ? response
								.getAccount().getAccountOpenDate() != null ? response
								.getAccount().getAccountOpenDate() : ""
								: "");
				accountPojo
						.setAccountStatus(response.getAccount() != null ? response
								.getAccount().getAccountStatus() != null ? response
								.getAccount().getAccountStatus() : ""
								: "");
				accountPojo
						.setAccountTermDate(response.getAccount() != null ? response
								.getAccount().getAccountTermDate() != null ? response
								.getAccount().getAccountTermDate() : ""
								: "");
				accountPojo
						.setAccountType(response.getAccount() != null ? response
								.getAccount().getAccountType() != null ? response
								.getAccount().getAccountType() : ""
								: "");
				accountPojo
						.setBranchId(response.getAccount() != null ? response
								.getAccount().getBranchId() != null ? response
								.getAccount().getBranchId() : "" : "");
				accountPojo
						.setBranchName(response.getAccount() != null ? response
								.getAccount().getBranchName() != null ? response
								.getAccount().getBranchName() : ""
								: "");
				accountPojo
						.setInstitutionId(response.getAccount() != null ? response
								.getAccount().getInstitutionId() != null ? response
								.getAccount().getInstitutionId() : ""
								: "");
				accountPojo.setExtCode(response.getExtCode() != null ? response
						.getExtCode() : "");
				bankPojo.setIfscCode(response.getAccount() != null ? response
						.getAccount().getIfscCode() != null ? response
						.getAccount().getIfscCode() : "" : "");
				
				//Changed for showing Agg amount on UI
				accountPojo.setAggAmount(Util.isEmptyString(response.getAggAmount())== false ? response.getAggAmount() :"0");

				/*************************** ResponseDetails **************************/
				/****************************** ResponseStatus ********************/
				/*if (response.getResponseDetails() != null) {
					accountPojo
							.setStatus(response.getResponseDetails() != null ? response
									.getResponseDetails().getStatus() != null ? response
									.getResponseDetails().getStatus() : ""
									: "");
					if (response.getResponseDetails().getInfos() != null) {
						for (TInfo tInfoResponse : response
								.getResponseDetails().getInfos().getInfo()) {
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
				bankPojo.setAccount(accountPojo);
				returnList.add(bankPojo);
			}
			return returnList;
		} catch (AccountInquiryFault e) {
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
		}finally
		{
			soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
			
		}
	}
}
