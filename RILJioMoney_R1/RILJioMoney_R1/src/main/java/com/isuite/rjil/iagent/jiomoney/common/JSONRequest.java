package com.isuite.rjil.iagent.jiomoney.common;

import org.json.simple.JSONObject;

public class JSONRequest extends JSONObject
{
	private JSONObject mPayLoad = null; 
	private JSONObject mRequestHeader = null;
	private static final String PAYLOAD_KEY =  "Payload_Req";
	private static final String HEADER_KEY =  "Request_Header";
	
	public JSONRequest()
	{
		mPayLoad = new JSONObject();
		mRequestHeader = new JSONObject();
		this.put(PAYLOAD_KEY, mPayLoad);
		this.put(HEADER_KEY, mRequestHeader);
	}
	
	public void addPayLoadObject(Object pKey,Object pValue)
	{
		mPayLoad.put(pKey, pValue);
	}
	public void addHeaderObject(Object pKey,Object pValue)
	{
		mRequestHeader.put(pKey, pValue);
	}
	public JSONObject getPayLoad()
	{
		return mPayLoad;
	}
	public JSONObject getRequestHeader()
	{
		return mRequestHeader;
	}
}
