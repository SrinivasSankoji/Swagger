package com.isuite.rjil.iagent.jiomoney.rpc;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;





import com.isuite.rjil.iagent.jiomoney.common.ServiceResponse;
import com.isuite.rjil.iagent.jiomoney.services.CustomerOnboardingService;
import com.isuite.rjil.iagent.jiomoney.services.ServiceLocator;
import com.isuite.rjil.iagent.jiomoney.util.EmailValidator;
import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;
import com.isuite.rjil.iagent.jiomoney.util.Util;

import flex.messaging.io.amf.ASObject;

public class CustomerOnboardingRpc 
{
	private static final Logger soapLogger = Logger.getLogger(LOVUtil.SOAPLOGGER);

	private static final String service = "CustomerOnboardingService";
	public ServiceResponse createProspect(String firstName,
			String lastName, String dob, String mobileNo,String emailId, 
			String gender, ASObject pState, ASObject pDistrict,
			String pincode, String channel, String agentCode) 
	{
		ServiceResponse response = null;
		String lClassName =  this.getClass().getName();
		String lMethodName = "createProspect";
		long requestId = Util.getRequestId();
		if (!Util.isValidMobileNumber(mobileNo)) 
		{
			return new ServiceResponse(1,"Please enter a valid Mobile Number");
		}
		mobileNo = "+91"+mobileNo;
		if (!Util.isEmptyString(emailId))
		{
			if (!new EmailValidator().validate(emailId)) 
			{
				return new ServiceResponse(1,"Please enter a valid Email Id");
			}
		}
		try
		{
			if(Util.isEmptyString(dob) == false)
			{
				SimpleDateFormat lInputFormatter = new SimpleDateFormat("dd/MM/yyyy");
				
				Date lDateOfBirth = lInputFormatter.parse(dob);
				Date lDate = new Date();
				if(lDateOfBirth.after(lDate))
				{
					return new ServiceResponse(1, "Invalid Date Of Birth");
				}
			}
		}
		catch(Exception e)
		{
			soapLogger.error("Exception catched while parsing date",e);
			soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentCode+"|["+lClassName +"."+lMethodName+"] | End");
			soapLogger.info("************************");
			return new ServiceResponse(1, e.getMessage());
		}		
		try
		{
			String state = "",district="";
			if(pState!=null)
				state = (String)pState.get("code");
			if(pDistrict!=null)
				district = (String)pDistrict.get("code");
			String lstrProspectId = ((CustomerOnboardingService) ServiceLocator.getService(service)).createProspect(firstName,lastName,dob, mobileNo, emailId,gender,state,district,pincode, channel,agentCode,requestId);
			response = new ServiceResponse("Prospect Created with ID : "+lstrProspectId);
		}
		catch (Throwable t) 
		{
			soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentCode+"|["+lClassName +"."+lMethodName+"] | End");
			soapLogger.info("************************");
			response = new ServiceResponse(1,"Error : "+t.getMessage());
		}
		return response;
	}
}