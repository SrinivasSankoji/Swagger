package com.isuite.rjil.iagent.jiomoney.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Util
{
	private Util() {}
	private static long msRequestId;
	public static long getRequestId()
    {
    	if(msRequestId == Long.MAX_VALUE - 1)
			msRequestId = 0;
		return msRequestId++;
    }
	
	public static boolean isValidNumber(String pNumber)
	{
		try 
		{
			Double.parseDouble(pNumber);
		} catch (Exception e) 
		{
			return false;
		}
		return true;
	}
	
	public static boolean isEmptyString(String pString)
	{
		if(pString == null)
		{
			return true;
		}
		if(pString.trim().equals(""))
		{
			return true;
		}
		if(pString.trim().equalsIgnoreCase("null"))
		{
			return true;
		}
		return false;	
	}
	
	public static String getStringRepresentation(Object pObject)
	{
		if(pObject == null)
		{
			return "";
		}
		if(pObject instanceof String)
		{
			if(((String)pObject).trim().equalsIgnoreCase("null"))
			{
				return "";
			}
			return (String)pObject;
		}
		return pObject.toString();
	}
	
	public static boolean isValidMobileNumber(String pMobileNumber)
	{
		if(isEmptyString(pMobileNumber))
		{
			return false;
		}
		
		if (pMobileNumber.length() < 10 || pMobileNumber.length() > 15)
		{
			return false;
		}
		if(pMobileNumber.length() > 10 && pMobileNumber.contains("+91") == false)
		{
			return false;
		}
		return true;
	}
	
	public static boolean isValidEmail(String pEmail)
	{
		if (isEmptyString(pEmail) == false)
		{
			if (new EmailValidator().validate(pEmail))
			{
				return true;
			}
		}
		return false;
	}
	
	public static List<String> getListFromCommaSeperatedValues(String pValues)
	{
		List<String> lValues = new ArrayList<String>();
		if(isEmptyString(pValues) == true)
		{
			return lValues;
		}
		String[] arrValues = pValues.split(",");
		lValues = Arrays.asList(arrValues);
		return lValues;
	}
}
