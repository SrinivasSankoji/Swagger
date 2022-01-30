package com.isuite.rjil.iagent.jiomoney.rpc;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.common.Customer;
import com.isuite.rjil.iagent.jiomoney.common.ServiceResponse;
import com.isuite.rjil.iagent.jiomoney.services.InstrumentInquiryService;
import com.isuite.rjil.iagent.jiomoney.services.ServiceLocator;
import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;
import com.isuite.rjil.iagent.jiomoney.util.Util;

public class InstrumentInquiryRpc {

	private static final Logger soapLogger = Logger.getLogger(LOVUtil.SOAPLOGGER);
	private static final String service = "InstrumentInquiryService";

	public ServiceResponse getPhysicalCardList(String custId,String agentId) {
		long requestId = Util.getRequestId();
		ServiceResponse response = null;
		String lClassName =  this.getClass().getName();
		String lMethodName = "getPhysicalCardList";
		soapLogger.info("************************");
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		try {
			Customer getPhysicalCardList = ((InstrumentInquiryService) ServiceLocator
					.getService(service)).getPhysicalCardList(custId, agentId,requestId);

			if (getPhysicalCardList != null) {
				response = new ServiceResponse(getPhysicalCardList);
			} else {
				response = new ServiceResponse(1,
						"No record Found in Instrument Enquiry ");
			}
		} catch (Throwable t) {
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			response = new ServiceResponse(1, t.getMessage());
		}
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
		soapLogger.info("************************");
		return response;
	}

	public ServiceResponse getCardList(String custId,String agentId) {
		ServiceResponse response = null;
		String lClassName =  this.getClass().getName();
		String lMethodName = "getCardList";
		long requestId = Util.getRequestId();
		soapLogger.info("************************");
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");

		try {
			Customer getCardList = ((InstrumentInquiryService) ServiceLocator
					.getService(service)).getCardList(custId,agentId,requestId);

			if (getCardList != null) {
				response = new ServiceResponse(getCardList);
			} else {
				response = new ServiceResponse(1,
						"No record Found in Instrument Enquiry ");
			}
		} catch (Throwable t) {
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			response = new ServiceResponse(1, t.getMessage());
		}

		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
		soapLogger.info("************************");
		return response;
	}
}
