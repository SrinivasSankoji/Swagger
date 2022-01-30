package com.isuite.rjil.iagent.jiomoney.dao.impl;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.xml.ws.BindingProvider;

import org.apache.log4j.Logger;

import ti.com.ril.rpsl.wsdls.transactioninquiry_v1_0.Operations;
import ti.com.ril.rpsl.wsdls.transactioninquiry_v1_0.TransactionInquiry;

import com.example.xmlns._1482847273364.CBSTxnInquiryService;
import com.example.xmlns._1482847273364.CustomerAccountEnquiryV0Dot1;
import com.isuite.rjil.iagent.jiomoney.common.TransactionInquiryPaymentBank;
import com.isuite.rjil.iagent.jiomoney.dao.TranscationInquiryJioPaymentBankDao;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;
import com.isuite.rjil.iagent.jiomoney.util.PropertiesUtil;
import com.isuite.rjil.iagent.jiomoney.util.WSHandlerResolver;
import com.isuite.rjil.iagent.jiomoney.util.XMLGregorianCalendarUtil;
import com.ril.jio.information.jiomoneybank.canonicaldatamodel.customeraccountenquiry.Account;
import com.ril.jio.information.jiomoneybank.canonicaldatamodel.customeraccountenquiry.Header;
import com.ril.jio.information.jiomoneybank.canonicaldatamodel.customeraccountenquiry.Individual;
import com.ril.jio.information.jiomoneybank.canonicaldatamodel.customeraccountenquiry.MessageContext;
import com.ril.jio.integration.jiomoneybank.services.billing.customeraccountenquiry.GetBalanceComposite;
import com.ril.jio.integration.jiomoneybank.services.billing.schema.customeraccountenquiry.GetBalanceCompositeResponse;
import com.ril.jio.integration.jiomoneybank.services.billing.schema.customeraccountenquiry.GetBalanceCompositeRequest;

public class TranscationInquiryJioPaymentBankDaoImpl implements TranscationInquiryJioPaymentBankDao{
	private static final Logger logger = Logger.getLogger(TranscationInquiryDaoImpl.class);
	private static final Logger soapLogger = Logger.getLogger(LOVUtil.SOAPLOGGER);
	private Properties platformProperties = PropertiesUtil.getProperties(PropertiesUtil.platform);
	public List<TransactionInquiryPaymentBank> getTransactionDataForPaymentBank(String fromDate,
			String toDate, String customerID, String cardNumber,String pPageIndex,String pMaxRecords,String lValues,String agentId,boolean isVerificationRequest,long requestId)
			throws DataAccessException {
		String lClassName =  this.getClass().getName();
		String lMethodName = "getTransactionDataForPaymentBank";
		soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		try 
		{
		Header lHeader =new Header();
		lHeader.setVersion("1.0");
		MessageContext lMessageContext = new MessageContext();
		//lMessageContext.setEsbId(value);
		lMessageContext.setCorrelationId(String.valueOf(UUID.randomUUID()));
		lMessageContext.setTimeStamp(XMLGregorianCalendarUtil.convertDateToXmlGregorian(new Date() ));
		lHeader.setMessageContext(lMessageContext);
		
		Account lSavingAccount=new Account();
		lSavingAccount.setAccountId("");//Saving Account Id
		
		Account lPrepaidAccount=new Account();
		lPrepaidAccount.setAccountId("");//Prepaid Account Id
		
		Individual lIndividual = new Individual();
		lIndividual.setPartyId(customerID);//Customer ID
		
		GetBalanceCompositeRequest lGetBalanceCompositeRequest = new GetBalanceCompositeRequest();
		lGetBalanceCompositeRequest.setHeader(lHeader);
		lGetBalanceCompositeRequest.setSavingsAccount(lSavingAccount);
		lGetBalanceCompositeRequest.setPrepaidAccount(lPrepaidAccount);
		lGetBalanceCompositeRequest.setCustomer(lIndividual);
		
//		GetBalanceComposite lGetBalanceComposite =new GetBalanceComposite();
//		lGetBalanceComposite.setGetBalanceCompositeRequest(lGetBalanceCompositeRequest);
		
		
		/******************* Setting the WSDL URL ******************/

		String getCompositBalanceWsdlUrl = platformProperties
				.getProperty("GetCompositBalance");
		URL wsdlUrl = new URL(getCompositBalanceWsdlUrl);
		//1-9-2015 [AR] [CH] Change in wsdl
		//TransactionInquiryServiceAgent agent = new TransactionInquiryServiceAgent(wsdlUrl);
		CBSTxnInquiryService agent = new CBSTxnInquiryService(wsdlUrl);
		agent.setHandlerResolver(new WSHandlerResolver(agentId,requestId			));
		CustomerAccountEnquiryV0Dot1 endPoint = agent.getCustomerAccountEnquiryV0Dot1Endpoint1();
		/******************* Setting the endpoint URL For GetCompositBalance ******************/
		String endPointURL = platformProperties
				.getProperty("transactionInquiryEndPoint");
		BindingProvider bindingProvider = (BindingProvider) endPoint;
		// logger.error("findDealerProfile :- Setting the end point for dealer Inquiry");
		bindingProvider.getRequestContext().put(
				BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointURL);
		
		GetBalanceCompositeResponse response = endPoint
				.getBalanceComposite(lGetBalanceCompositeRequest);
		
		List<TransactionInquiryPaymentBank> returnList = null;
		return returnList;
		
		} catch (Throwable t) 
		{
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
