package com.isuite.rjil.iagent.jiomoney.common;

public enum Channel 
{
	CALL_CENTRE("01","Call"),WEB_PORTAL("02","Web Portal"),MOBILE_APP("03","App"),CHAT("04","Chat"),EMAIL("05","Email"),SOCIAL_MEDIA("06","Social Media");
	
	
	private String mstrValue;
	private String mstrDesc;
	public String getValue() 
	{
		return mstrValue;
	}
	public String getDesc() 
	{
		return mstrDesc;
	}
	private Channel(String pValue, String pDesc)
	{
		mstrValue = pValue;
		mstrDesc = pDesc;
	}
	public static String getValue(String pDesc)
	{
		Channel[] lValues  = values();
		for (Channel lChanel : lValues) 
		{
			if(lChanel.mstrDesc.equalsIgnoreCase(pDesc))
			{
				return lChanel.mstrValue;
			}
		}
		return "";
	}

	public static String getDesc(String pValue)
	{
		Channel[] lValues  = values();
		for (Channel lChanel : lValues) 
		{
			if(lChanel.mstrValue.equalsIgnoreCase(pValue))
			{
				return lChanel.mstrDesc;
			}
		}
		return "";
	}

}
