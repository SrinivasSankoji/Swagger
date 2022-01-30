package com.isuite.rjil.iagent.jiomoney.rpc;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.common.EgainKMPortalUtil;
import com.isuite.rjil.iagent.jiomoney.common.ServiceResponse;
import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;
import com.isuite.rjil.iagent.jiomoney.util.Util;

public class EgainKMPortalRPC 
{
	private static final Logger soapLogger = Logger.getLogger(LOVUtil.SOAPLOGGER);
	
	public ServiceResponse encryptEgainKMDetails(String pAgentID)
	{
		long requestId = Util.getRequestId();
		String lClassName =  this.getClass().getName();
		String lMethodName = "encryptEgainKMDetails";
		soapLogger.info("************************");
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+pAgentID+"|["+lClassName +"."+lMethodName+" ]| Start");
		ServiceResponse lResponse = null;
		try 
		{
			EgainKMPortalUtil lUtil  = new EgainKMPortalUtil();
			lUtil.encryptEgainKMDetails(pAgentID,""+requestId);
			lResponse = new ServiceResponse(lUtil);
		}  
		catch (Throwable e) 
		{
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", e);
			lResponse = new ServiceResponse(1, e.getMessage());
		}
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+pAgentID+"|["+lClassName +"."+lMethodName+"] | End");
		soapLogger.info("************************");
		return lResponse;
	}
}