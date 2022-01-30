package com.isuite.rjil.iagent.jiomoney.rpc;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.common.Customer;
import com.isuite.rjil.iagent.jiomoney.common.ServiceResponse;
import com.isuite.rjil.iagent.jiomoney.exception.ServiceException;
import com.isuite.rjil.iagent.jiomoney.services.CustomerProfileMgmtService;
import com.isuite.rjil.iagent.jiomoney.services.ServiceLocator;
import com.isuite.rjil.iagent.jiomoney.util.EmailValidator;
import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;
import com.isuite.rjil.iagent.jiomoney.util.Util;

public class CustomerProfileMgmtRPC {
	private static final Logger soapLogger = Logger.getLogger(LOVUtil.SOAPLOGGER);
	private static final String service = "CustomerProfileMgmtService";
	/*declared By Abhishek*/
	String lClassName ;
	String lMethodName ;
	long requestId ;
	/*End by ABhishek*/

	public ServiceResponse updateCustomerProfile(String entityId,
			String oldEmailId, String addressLine, String country,
			String mobileNo, String newEmailId,String agentId)  
	{
		long requestId = Util.getRequestId();
		String lClassName =  this.getClass().getName();
		String lMethodName = "updateCustomerProfile";
		soapLogger.info("************************");
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		ServiceResponse response = null;
		try 
		{
			if (newEmailId != null && !newEmailId.isEmpty()){
				if (!new EmailValidator().validate(newEmailId)) {
					return new ServiceResponse(2,"Please enter a valid Email Id");
				}
			}
			
			Customer updateCustomerProfile = ((CustomerProfileMgmtService) ServiceLocator
					.getService(service)).updateCustomerProfile(entityId,
					oldEmailId, addressLine, country, mobileNo, newEmailId,agentId,requestId);

			if (updateCustomerProfile != null) {
				response = new ServiceResponse(
						"SuccessFully Update the Email Id");
			} else {
				response = new ServiceResponse(1,
						"No record Found in Customer Profile mgmt ");
			}
		} catch (Throwable t) 
		{
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			response = new ServiceResponse(1, t.getMessage());

		}
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
		soapLogger.info("************************");
		return response;
	}
	/*Abhishek Start 18-Jan-2017 for retrieveCustomerDetails*/
	public ServiceResponse retrieveCustomerDetails(String entityId, String mobileNo,
			String accountType,String orderRefNo,String agentId){
		 lClassName =  this.getClass().getName();
		 lMethodName = "retrieveCustomerDetails";
		 requestId = Util.getRequestId();
		 soapLogger.info("************************");
			soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
			ServiceResponse response = null;
			try 
			{
				
				
				Customer retrievecustomerdetails = ((CustomerProfileMgmtService) ServiceLocator
						.getService(service)).retrieveCustomerDetails(entityId, mobileNo, accountType, orderRefNo, agentId, requestId);
				
				if (retrievecustomerdetails != null) {
					response=new ServiceResponse(retrievecustomerdetails);
				} else {
					response = new ServiceResponse(1,
							"No record Found in Customer Profile mgmt ");
				}
			} catch (Throwable t) 
			{
				soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
				response = new ServiceResponse(1, t.getMessage());

			}
			soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
			soapLogger.info("************************");	
		return response;
	}
	/*Abhishek End for retrieveCustomerDetails*/
}
