package com.isuite.rjil.iagent.jiomoney.common;

public class PaginationValues 
{
	private String mstrCurrentPageNumber;
	private String mstrInputForCurrentPage;
	private String mstrOutputPageIndex;
	
	public String getCurrentPageNumber() 
	{
		return mstrCurrentPageNumber;
	}
	public void setCurrentPageNumber(String mstrCurrentPageNumber)
	{
		this.mstrCurrentPageNumber = mstrCurrentPageNumber;
	}
	public String getInputForCurrentPage() 
	{
		return mstrInputForCurrentPage;
	}
	public void setInputForCurrentPage(String mstrInputForCurrentPage)
	{
		this.mstrInputForCurrentPage = mstrInputForCurrentPage;
	}
	public String getOutputPageIndex() {
		return mstrOutputPageIndex;
	}
	public void setOutputPageIndex(String mstrOutputPageIndex)
	{
		this.mstrOutputPageIndex = mstrOutputPageIndex;
	}
	@Override
	public boolean equals(Object arg0) 
	{
		if(arg0 == null)
			return false;
		if(arg0 instanceof PaginationValues == false)
			return false;
		PaginationValues lValue = (PaginationValues)arg0;
		if(!lValue.getCurrentPageNumber().equalsIgnoreCase(this.getCurrentPageNumber()))
			return false;
		return true;
	}	
}
