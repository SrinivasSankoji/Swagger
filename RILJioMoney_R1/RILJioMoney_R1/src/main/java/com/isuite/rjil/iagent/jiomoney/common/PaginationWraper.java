package com.isuite.rjil.iagent.jiomoney.common;

import java.util.List;

public class PaginationWraper 
{
	private String hasNext = "true";
	private String hasPrevious = "true";
	private String pageNumber = "";
	public String getPageNumber() 
	{
		return pageNumber;
	}
	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}
	private List<PaginationValues> mlistPages ;
	public String getHasNext() {
		return hasNext;
	}
	public void setHasNext(String hasNext) {
		this.hasNext = hasNext;
	}
	public String getHasPrevious() {
		return hasPrevious;
	}
	public void setHasPrevious(String hasPrevious) {
		this.hasPrevious = hasPrevious;
	}
	public List<PaginationValues> getPages() {
		return mlistPages;
	}
	public void setPages(List<PaginationValues> mlistPages) {
		this.mlistPages = mlistPages;
	}
	

}
