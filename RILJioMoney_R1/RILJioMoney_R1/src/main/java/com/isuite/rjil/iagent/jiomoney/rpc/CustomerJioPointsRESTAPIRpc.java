package com.isuite.rjil.iagent.jiomoney.rpc;



import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.common.ServiceResponse;
import com.isuite.rjil.iagent.jiomoney.services.CustomerJioPointsRESTAPIService;
import com.isuite.rjil.iagent.jiomoney.services.ServiceLocator;
import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;
import com.isuite.rjil.iagent.jiomoney.util.Util;

public class CustomerJioPointsRESTAPIRpc 
{
	private static final Logger soapLogger = Logger.getLogger(LOVUtil.SOAPLOGGER);
	public ServiceResponse getJioPointForMobileNo(String pMobileNumber, String pAgentId)
	{
		String lClassName =  this.getClass().getName();
		String lMethodName = "getJioPointForMobileNo";
		String lRequestId = ""+Util.getRequestId();
		soapLogger.info("************************");
		soapLogger.info("Request Id : |Agent ID : "+pAgentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		ServiceResponse response = null;
		try 
		{
			String lCustomerPoints = ((CustomerJioPointsRESTAPIService)ServiceLocator.getService("CustomerJioPointsRESTAPIService")).getJioPointForMobileNo(pMobileNumber,pAgentId,lRequestId);
			if(lCustomerPoints == null || "".equals(lCustomerPoints))
			{
				response = new ServiceResponse("Jio Points Account Not Found");
			}
			else
			{
				response = new ServiceResponse(lCustomerPoints);
			}
		} 
		catch (Throwable e) 
		{
			soapLogger.error("Request Id :"+lRequestId+"|Exception in ["+lClassName+"."+lMethodName+"]", e);
			response = new ServiceResponse("Jio Points Account Not Found");
		}
		soapLogger.info("Request Id :"+lRequestId+"|Agent ID : "+pAgentId+"|["+lClassName +"."+lMethodName+"] | End");
		soapLogger.info("************************");
		return response;
	}
}
