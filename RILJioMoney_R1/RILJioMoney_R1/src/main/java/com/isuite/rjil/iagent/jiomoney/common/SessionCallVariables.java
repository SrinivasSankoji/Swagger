package com.isuite.rjil.iagent.jiomoney.common;

public class SessionCallVariables 
{
	private String mstrPhoneNumber = "";
	private String mstrEntityId = "";
	private String mstrIsAuthenticated = "false";
	
	public String getPhoneNumber() 
	{
		return mstrPhoneNumber;
	}
	public void setPhoneNumber(String pPhoneNumber) 
	{
		mstrPhoneNumber = pPhoneNumber;
	}
	public String getEntityId() 
	{
		return mstrEntityId;
	}
	public void setEntityId(String pEntityId) 
	{
		mstrEntityId = pEntityId;
	}
	public String getIsAuthenticated() {
		return mstrIsAuthenticated;
	}
	public void setIsAuthenticated(String mstrIsAuthenticated) {
		this.mstrIsAuthenticated = mstrIsAuthenticated;
	}	
}