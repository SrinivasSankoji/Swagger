package com.isuite.rjil.iagent.jiomoney.rpc;

import java.util.List;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.common.ServiceResponse;
import com.isuite.rjil.iagent.jiomoney.common.TransactionInquiryPaymentBank;
import com.isuite.rjil.iagent.jiomoney.services.ServiceLocator;
import com.isuite.rjil.iagent.jiomoney.services.TranscationInquiryServicePaymentBank;
import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;
import com.isuite.rjil.iagent.jiomoney.util.Util;

import flex.messaging.io.amf.ASObject;



public class GetCompositeBalanceRpc {
	private static final Logger soapLogger = Logger.getLogger(LOVUtil.SOAPLOGGER);
	private static final String service = "TranscationInquiryServicePaymentBank";
	public ServiceResponse getTransactionDataForJioPaymentBank(String fromDate, String toDate,String customerID, String cardNumber,List<ASObject> pPageOffsetMapping,String pCurrentPage,String pButtonClickFlag,String pMaxRecords, String agentId)
	{
		ServiceResponse response = null;
		String lClassName =  this.getClass().getName();
		String lMethodName = "getTransactionDataForJioPaymentBank";
		soapLogger.info("************************");
		long requestId = Util.getRequestId();
		try{
		List<TransactionInquiryPaymentBank> getTransactionData = ((TranscationInquiryServicePaymentBank) ServiceLocator.getService(service)).getTransactionDataForPaymentBank(fromDate, toDate,customerID, 
				cardNumber,"",pMaxRecords,"", agentId,false,requestId);
		}catch (Throwable e) 
		{
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", e);
			response = new ServiceResponse(1,"No record Found in Transcation Inquiry");
		}finally
		{
			soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
			soapLogger.info("************************");
		}
		
		return response;
	}

}
