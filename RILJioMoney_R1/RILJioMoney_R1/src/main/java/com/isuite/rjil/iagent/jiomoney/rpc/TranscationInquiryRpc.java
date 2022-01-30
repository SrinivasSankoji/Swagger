package com.isuite.rjil.iagent.jiomoney.rpc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.common.PaginationValues;
import com.isuite.rjil.iagent.jiomoney.common.PaginationWraper;
import com.isuite.rjil.iagent.jiomoney.common.ServiceResponse;
import com.isuite.rjil.iagent.jiomoney.common.TranscationInquiry;
import com.isuite.rjil.iagent.jiomoney.services.ServiceLocator;
import com.isuite.rjil.iagent.jiomoney.services.TranscationInquiryService;
import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;
import com.isuite.rjil.iagent.jiomoney.util.Util;

import flex.messaging.io.amf.ASObject;

public class TranscationInquiryRpc 
{
	private static final Logger soapLogger = Logger.getLogger(LOVUtil.SOAPLOGGER);
	private static final String service = "TranscationInquiryService";

	public ServiceResponse getTransactionDataForFilters(String fromDate, String toDate,String customerID, String cardNumber,List<ASObject> pPageOffsetMapping,String pCurrentPage,String pButtonClickFlag,String pMaxRecords, String agentId)
	{	
		ServiceResponse response = null;
		String lClassName =  this.getClass().getName();
		String lMethodName = "getTransactionDataForFilters";
		soapLogger.info("************************");
		long requestId = Util.getRequestId();
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		try{
			if (fromDate != null && !fromDate.isEmpty() && toDate != null && !toDate.isEmpty()) 
		
			{
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				try
				{
				   	Date date=new Date();
					Date from = format.parse(fromDate);
					Date to = format.parse(toDate);
//					   long ONE_DAY = 1000 * 60 * 60 * 24;
//					   long difference_ms = Math.abs(to.getTime() - from.getTime());
//					   long	sixMonthDefference=Math.abs(date.getTime()-from.getTime());
//					   int 	noOfDays=Math.round(difference_ms/ONE_DAY); 
//					   int 	sixMonthDefferenceCount=Math.round(sixMonthDefference/ONE_DAY); 
					   
					   Calendar cal=Calendar.getInstance();
						cal.setTime(from);
						int frmMonth = cal.get(Calendar.MONTH);
						int frmYear =  cal.get(Calendar.YEAR);
						
						cal.setTime(to);
						int toMonth = cal.get(Calendar.MONTH);
						int toYear =  cal.get(Calendar.YEAR);
					   
					if (to.before(from)) {
						return new ServiceResponse(1, "End Date cannot be before Start Date");
					}  else if (from.after(new Date())) {
						return new ServiceResponse(1, "Start Date cannot be after current date");
					} else if (to.after(new Date())) {
						return new ServiceResponse(1, "End Date cannot be after current date");
					}else if(frmYear!=toYear || frmMonth!=toMonth)	{
						return new ServiceResponse(1, "Start Date and End Date should be in same calendar month");
					}
//					else if(noOfDays>31)
//					{
//						return new ServiceResponse(1, "Difference between Start Date and End Date cannot be more than 31 days");
//					}else if(sixMonthDefferenceCount>180)
//					{
//						return new ServiceResponse(1, "Transactions Can be searched for last 180 days");	
//					}
				
				} 
				catch(Exception e)
				{
					 
					return new ServiceResponse(1, e.getMessage());
				}
				
			}
			else if((Util.isEmptyString(fromDate) && Util.isEmptyString(toDate) == false)||(Util.isEmptyString(fromDate) == false && Util.isEmptyString(toDate) )) 
	        {
	              return new ServiceResponse(1, "Please Select Start Date & End Date");
	        }
	
			else
			{
				return new ServiceResponse(1, "Please select Start date and End date ");
			}
			List<PaginationValues> llistPageIndex = new ArrayList<PaginationValues>();
			if(!"next".equalsIgnoreCase(pButtonClickFlag)&& !"previous".equalsIgnoreCase(pButtonClickFlag))
			{
				pPageOffsetMapping = null;
			}
			if(pPageOffsetMapping != null)
			{
				/*
				 * [{inputForCurrentPage="NA", outputPageIndex="12345:123", currentPageNumber=1}
				 * {inputForCurrentPage="12345:123", outputPageIndex="09876:123", currentPageNumber=2}]
				 * */
				
				PaginationValues lPage = null;
				for (ASObject lObject: pPageOffsetMapping) 
				{
					lPage = new PaginationValues();
					lPage.setCurrentPageNumber((String)lObject.get("currentPageNumber"));
					lPage.setInputForCurrentPage((String)lObject.get("inputForCurrentPage"));
					lPage.setOutputPageIndex((String)lObject.get("outputPageIndex"));
					llistPageIndex.add(lPage);
				}
			}
			
			try
			{
				/*
				 * If 1st time load,on-click of re-set, onlick of search : send value for pCurrentPage as NA,pButtonClickFlag any value other than next and previous
				 **/
				String lstrNextPageOffset = "NA";
				int liNextPageNumber = 0;
				PaginationWraper lWraper = new PaginationWraper();
				PaginationValues lPage = new PaginationValues();
				if(Util.isEmptyString(pCurrentPage) == false && Util.isValidNumber(pCurrentPage))
				{
					liNextPageNumber = Integer.parseInt(pCurrentPage);
				}
				if("next".equalsIgnoreCase(pButtonClickFlag))
				{
					liNextPageNumber = liNextPageNumber+1;
				}
				else if("previous".equalsIgnoreCase(pButtonClickFlag))
				{
					liNextPageNumber = liNextPageNumber - 1;
				}
				lWraper.setPageNumber(""+(liNextPageNumber));
				lPage.setCurrentPageNumber(""+(liNextPageNumber));
				if(llistPageIndex.contains(lPage))
				{
					PaginationValues lPage2 = llistPageIndex.get(llistPageIndex.indexOf(lPage));
					lstrNextPageOffset =  lPage2.getInputForCurrentPage();
				}
				else
				{
					lPage.setCurrentPageNumber(""+(liNextPageNumber-1));
					if(llistPageIndex.contains(lPage))
					{
						PaginationValues lPage2 = llistPageIndex.get(llistPageIndex.indexOf(lPage));
						lstrNextPageOffset =  lPage2.getOutputPageIndex();
					}
				}
				PaginationValues lValues = new PaginationValues();
				List<TranscationInquiry> getTransactionData = ((TranscationInquiryService) ServiceLocator.getService(service)).getTransactionData(fromDate, toDate,customerID, 
						cardNumber,lstrNextPageOffset,pMaxRecords,lValues, agentId,false,requestId);
	
				if (getTransactionData != null && getTransactionData.size() > 0) 
				{
					response = new ServiceResponse(getTransactionData);
					
					if("next".equalsIgnoreCase(pButtonClickFlag) || "previous".equalsIgnoreCase(pButtonClickFlag))
					{
						lValues.setCurrentPageNumber(""+(liNextPageNumber));
						lWraper.setPageNumber(""+(liNextPageNumber));
					}
					else
					{
						//initial load
						lValues.setCurrentPageNumber(""+(liNextPageNumber+1));
						lWraper.setPageNumber(""+(liNextPageNumber+1));
					}				
					lValues.setInputForCurrentPage(lstrNextPageOffset);
					if(llistPageIndex.contains(lValues))
					{
						llistPageIndex.add(llistPageIndex.indexOf(lValues),lValues);
					}
					else
					{
						llistPageIndex.add(lValues);
					}
					if(lValues.getOutputPageIndex().equalsIgnoreCase("na"))
					{
						lWraper.setHasNext("false");
					}
					if(lValues.getInputForCurrentPage().equalsIgnoreCase("na"))
					{
						lWraper.setHasPrevious("false");
					}
					lWraper.setPages(llistPageIndex);	
					response.setSecodaryData(lWraper);
				} 
				else 
				{
					response = new ServiceResponse(1,"No record Found in TransactionInquiry ");
				}
			} 
			catch (Throwable e) 
			{
				soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", e);
				response = new ServiceResponse(1,"No record Found in Transcation Inquiry");
			}
		}finally
		{
			soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
			soapLogger.info("************************");
		}

		return response;
	}
	
	
	
	public ServiceResponse getTransactionData(String fromDate, String toDate,
			String customerID, String cardNumber, String agentId) {
		String lClassName =  this.getClass().getName();
		String lMethodName = "getTransactionData";
		soapLogger.info("************************");
		long requestId = Util.getRequestId();
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		ServiceResponse response = null;
		try {

			List<TranscationInquiry> getTransactionData = ((TranscationInquiryService) ServiceLocator
					.getService(service)).getTransactionData(fromDate, toDate,
					customerID, cardNumber,"","",null, agentId,false,requestId);

			if (getTransactionData != null && getTransactionData.size() > 0) {
				response = new ServiceResponse(getTransactionData);
			} else
			{
				response = new ServiceResponse(1,
						"No record Found in TransactionInquiry ");
			}

		} catch (Throwable e) {
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", e);
			response = new ServiceResponse(1,
					"No record Found in Transcation Inquiry");
		}
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
		soapLogger.info("************************");
		return response;
	}

	public ServiceResponse getTransactionSummary(String startDateTime,
			String endDateTime, String merchantIdoperator,
			String terminalIdoperator, String agentId) {
		ServiceResponse response = null;
		try {

			List<TranscationInquiry> getTransactionSummary = ((TranscationInquiryService) ServiceLocator
					.getService(service)).getTransactionSummary(startDateTime,
					endDateTime, merchantIdoperator, terminalIdoperator,
					agentId,-9000);

			if (getTransactionSummary != null) {
				response = new ServiceResponse(getTransactionSummary);
			} else {
				response = new ServiceResponse(1,
						"No record Found in TranscationInquiry ");
			}

		} catch (Throwable e) {
			soapLogger.error(
					"Exception catched while doing get TranscationInquiry ", e);
			response = new ServiceResponse(1,
					"No record Found in Transcation Inquiry");
		}

		return response;
	}

	public ServiceResponse getTransactionId(List<ASObject> transactionGridList) {
		ServiceResponse response = null;
		String transactionList = "";
		try {

			if (transactionGridList != null && transactionGridList.size() > 0) {
				for (ASObject asObj : transactionGridList) {
					transactionList += asObj.get("primaryKey") + ",";
				}
			}
			if (transactionList != null && !transactionList.isEmpty()) {
				response = new ServiceResponse(transactionList.substring(0,
						transactionList.length() - 1));
			} else {
				response = new ServiceResponse(1,
						"No record Found in getTransactionId ");
			}

		} catch (Throwable e) {
			soapLogger.error("Exception catched while doing get TransactionId ", e);
			response = new ServiceResponse(1,
					"No record Found in Get TransactionId");
		}

		return response;
	}
}