package com.isuite.rjil.iagent.jiomoney.rpc;

import java.util.List;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.common.Customer;
import com.isuite.rjil.iagent.jiomoney.common.ServiceResponse;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.exception.ServiceException;
import com.isuite.rjil.iagent.jiomoney.services.CustomerInquiryService;
import com.isuite.rjil.iagent.jiomoney.services.ServiceLocator;
import com.isuite.rjil.iagent.jiomoney.util.EmailValidator;
import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;
import com.isuite.rjil.iagent.jiomoney.util.Util;

public class CustomerInquiryRpc 
{
	private static final Logger soapLogger = Logger.getLogger(LOVUtil.SOAPLOGGER);

	private static final String service = "CustomerInquiryService";

	public ServiceResponse getPrepaidAccountInfo(String customerId,
			String prepaidAccountId, String currency, String branchId,
			String institutionCode,String agentId) {
		long requestId = Util.getRequestId();
		String lClassName =  this.getClass().getName();
		String lMethodName = "getPrepaidAccountInfo";
		soapLogger.info("************************");
		soapLogger.info("Request Id :"+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		ServiceResponse response = null;
		try {
			Customer getPrepaidAccountInfo = ((CustomerInquiryService) ServiceLocator
					.getService(service)).getPrepaidAccountInfo(customerId,
					prepaidAccountId, currency, branchId, institutionCode, agentId,requestId);

			if (getPrepaidAccountInfo != null) {
				response = new ServiceResponse(getPrepaidAccountInfo);
			} else {
				response = new ServiceResponse(1,
						"No record Found in Customer Inquiry");
			}
		}catch (Throwable t) 
		{
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			response = new ServiceResponse(1, t.getMessage());
		}
		soapLogger.info("Request Id :"+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
		soapLogger.info("************************");
		return response;
	}

	public ServiceResponse searchCustomer(String mobileNo, String emailAddress,
			String panNumber, String firstName, String lastName,
			String dateofBirth, String customerId,String agentId) throws ServiceException 
	{
		long requestId = Util.getRequestId();
		String lClassName =  this.getClass().getName();
		String lMethodName = "searchCustomer";
		soapLogger.info("************************");
		soapLogger.info("Request Id :"+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		ServiceResponse response = null;
		List<Customer> searchcustomer = null;
		try 
		{
			if (mobileNo != null && !mobileNo.isEmpty()&& !mobileNo.startsWith("+91")) 
			{
				mobileNo = "+91" + mobileNo;
			}
			if (emailAddress != null && !emailAddress.isEmpty()){
				if (!new EmailValidator().validate(emailAddress)) {
					return new ServiceResponse(2,"Please enter a valid Email Id");
				}

			}
			
			searchcustomer = ((CustomerInquiryService) ServiceLocator
					.getService(service)).searchCustomer(mobileNo,
					emailAddress, panNumber, firstName, lastName, dateofBirth,
					customerId, agentId,requestId,false);
			if (searchcustomer != null && searchcustomer.isEmpty() == false)
			{
				if (searchcustomer.size() > 1) 
				{
					response = new ServiceResponse(searchcustomer);
					response.setMetadata("list");
				} else 
				{
					response = new ServiceResponse(searchcustomer.get(0));
				}
			} 
			else 
			{
				soapLogger.info("Request Id :"+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Calling SearchCustomer For Searching Prospect");
				 searchcustomer  = searchProspect(mobileNo, emailAddress, panNumber, firstName, lastName, dateofBirth, customerId,requestId, agentId);
				 if(searchcustomer == null || searchcustomer.isEmpty())
				 {
					 return new ServiceResponse(1,"No record Found in Customer Inquiry");
				 }
				 
				 
				 if (searchcustomer.size() > 1)
				 {
					 response = new ServiceResponse(searchcustomer);
					 response.setMetadata("list");
				 } 
				 else
				 {
					 Customer lCustomer = searchcustomer.get(0);
					 lCustomer.setProspect("true");
					 response = new ServiceResponse(lCustomer);
				 }
			}
		} catch (Throwable t) 
		{
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			response = new ServiceResponse(2, t.getMessage());
		}
		soapLogger.info("Request Id :"+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
		soapLogger.info("************************");
		return response;
	}
	
	private List<Customer> searchProspect(String mobileNo, String emailAddress,
			String panNumber, String firstName, String lastName,
			String dateofBirth, String customerId,long requestId,String agentId) throws ServiceException, DataAccessException 
	{
			return ((CustomerInquiryService) ServiceLocator
					.getService(service)).searchCustomer(mobileNo,
					emailAddress, panNumber, firstName, lastName, dateofBirth,
					customerId, agentId,requestId,true);
			
	
	}

	public ServiceResponse displayCustomer(String customerId,String agentId)
			throws ServiceException 
	{
		long requestId = Util.getRequestId();
		String lClassName =  this.getClass().getName();
		String lMethodName = "displayCustomer";
		soapLogger.info("************************");
		soapLogger.info("Request Id :"+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		ServiceResponse response = null;
		try {
			Customer lCustomer = ((CustomerInquiryService) ServiceLocator
					.getService(service)).displayCustomer(customerId,agentId,requestId);

			if (lCustomer != null) 
			{
				response = new ServiceResponse(lCustomer);
				if(lCustomer.getProspect().equals("true"))
					response.setStatus(3);
			} else {
				response = new ServiceResponse(1,
						"No record Found in Customer Inquiry");
			}
		} catch (Throwable t) {
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			response = new ServiceResponse(2, t.getMessage());
		}
		soapLogger.info("Request Id :"+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
		soapLogger.info("************************");
		return response;
	}
}
