package com.isuite.rjil.iagent.jiomoney.common;

public enum CommunicationMode
{
	CALL("01","Call"),CHAT("02","Chat"),EMAIL("03","Email");
	public static final String COMMUNICATION_MODE_PARAM_NAME = "COMMUNICATION_MODE";
	private String mstrValue;
	private String mstrDesc;
	public String getValue() {
		return mstrValue;
	}
	public String getDesc() {
		return mstrDesc;
	}
	private CommunicationMode(String pValue, String pDesc)
	{
		mstrValue = pValue;
		mstrDesc = pDesc;
	}
	public static String getValue(String pDesc)
	{
		CommunicationMode[] lValues  = values();
		for (CommunicationMode lCommunicationMode : lValues) 
		{
			if(lCommunicationMode.mstrDesc.equalsIgnoreCase(pDesc))
			{
				return lCommunicationMode.mstrValue;
			}
		}
		return "";
	}
	
	public static String getDesc(String pValue)
	{
		CommunicationMode[] lValues  = values();
		for (CommunicationMode lCommunicationMode : lValues) 
		{
			if(lCommunicationMode.mstrValue.equalsIgnoreCase(pValue))
			{
				return lCommunicationMode.mstrDesc;
			}
		}
		return "";
	}
}