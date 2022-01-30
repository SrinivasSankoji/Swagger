package com.isuite.rjil.iagent.jiomoney.rpc;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.common.ServiceResponse;
import com.isuite.rjil.iagent.jiomoney.common.SessionCallVariables;
import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;
import com.isuite.rjil.iagent.jiomoney.util.TimeUtil;
import com.isuite.rjil.iagent.jiomoney.util.Util;

 

public class CommonRpc 
{
	private static final Logger logger = Logger.getLogger(CommonRpc.class);
	private static final Logger soapLogger = Logger.getLogger(LOVUtil.SOAPLOGGER);
	public ServiceResponse getAgentLoginTime() 
	{
		ServiceResponse response = null;
		try 
		{
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy hh:mm");
			String toReturn = format.format(new Date());
			response = new ServiceResponse("Login Since  " + toReturn);

		} catch (Exception e) {
			logger.error("Exception catched while getting Agent Login Time", e);
			response = new ServiceResponse(1,
					"Exception catched while getting agent login time"
							+ e.getMessage());
		}
		return response;
	}
	
	public ServiceResponse getDateRangeForTransactionFilter(String pDifferenceInDays) 
	{
		
		TimeUtil lTimeUtil = new TimeUtil();
		Calendar startTime = Calendar.getInstance();
		int liDay=0; 
		if(Util.isValidNumber(pDifferenceInDays))
		{
			liDay = Integer.parseInt(pDifferenceInDays);
		}
		startTime.add(Calendar.DATE,-1*liDay);
		lTimeUtil.setStartTime(startTime.get(Calendar.DATE) + "/"
		        + (startTime.get(Calendar.MONTH)+1) + "/" + startTime.get(Calendar.YEAR));
		Calendar endTime = Calendar.getInstance();
		lTimeUtil.setEndTime(endTime.get(Calendar.DATE) + "/"
		        + (endTime.get(Calendar.MONTH)+1) + "/" + endTime.get(Calendar.YEAR));
		ServiceResponse lResponse = new ServiceResponse(lTimeUtil);
		return lResponse;
	}
	
	public ServiceResponse getCallVariableObject(String pVar1,String pVar2,String pVar3,String pVar4,String pVar5,String pVar6,String pVar7,String pVar8,String pVar9,String pVar10)
	{
		logger.info("IVR CALL Variables : Var1 : "+pVar1+", Var2 : "+pVar2+", Var3 : "+ pVar3+", Var4 : "+pVar4+", pVar5 : "+pVar5+", pVar6 : "+pVar6+", pVar7 : "+pVar7+", pVar8 : "+pVar8+", Var9 : "+pVar9+", Var10 : "+pVar10);
		soapLogger.info("IVR CALL Variables : Var1 : "+pVar1+", Var2 : "+pVar2+", Var3 : "+ pVar3+", Var4 : "+pVar4+", pVar5 : "+pVar5+", pVar6 : "+pVar6+", pVar7 : "+pVar7+", pVar8 : "+pVar8+", Var9 : "+pVar9+", Var10 : "+pVar10);
		ServiceResponse lResponse = null;
		try
		{
			SessionCallVariables lCallVariables  = new SessionCallVariables();
			//Logic to check if customer Id is present in the call variables
			//format for Var4 
			//<CUST_FOUND_FLAG>|<CUSTOMER_ID>|<WALLET_FOUND_FLAG>|<PREFFERED_LANG>|<SELECTED_LANG>
			if(Util.isEmptyString(pVar4) == false)
			{
				String lCustomerDetails[] = pVar4.split("\\|");
				
				if(lCustomerDetails.length > 0 && lCustomerDetails[0].equalsIgnoreCase("y"))
				{
					lCallVariables.setEntityId(Util.getStringRepresentation(lCustomerDetails[1]));
				}	
			}
			//remove CISCO number which is appended for Circle identification
			if(Util.isEmptyString(pVar1) == false && pVar1.length()>9)
			{
				StringBuilder lBuilder = new StringBuilder(pVar1);
				lBuilder.reverse();
				lBuilder = new StringBuilder(lBuilder.substring(0, 10));
				lBuilder.reverse();
				lCallVariables.setPhoneNumber(lBuilder.toString());
			}
			
			//Set is Authenticated
			lResponse = new ServiceResponse(lCallVariables);
		}
		catch(Exception e)
		{
			logger.error("Error while processing Call Variables : "+e.getMessage(), e);
			lResponse = new ServiceResponse(1, "Error while processing Call Variables : "+e.getMessage());
		}
		return lResponse;
	}
}