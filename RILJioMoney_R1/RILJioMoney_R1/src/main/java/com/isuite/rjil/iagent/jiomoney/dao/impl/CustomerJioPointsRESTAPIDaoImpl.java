package com.isuite.rjil.iagent.jiomoney.dao.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.isuite.rjil.iagent.jiomoney.common.JSONRequest;
import com.isuite.rjil.iagent.jiomoney.dao.CustomerJioPointsRESTAPIDao;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;
import com.isuite.rjil.iagent.jiomoney.util.PropertiesUtil;
import com.isuite.rjil.iagent.jiomoney.util.XMLGregorianCalendarUtil;

public class CustomerJioPointsRESTAPIDaoImpl implements CustomerJioPointsRESTAPIDao
{
	private static final Logger logger = Logger.getLogger(CustomerInquiryDaoImpl.class);
	private static final Logger soapLogger = Logger.getLogger(LOVUtil.SOAPLOGGER);
	private Properties properties = PropertiesUtil.getProperties(PropertiesUtil.platform);
	private String mstrRESTURL = "";
	public CustomerJioPointsRESTAPIDaoImpl() 
	{
		mstrRESTURL = properties.getProperty("JIOPOINTS_REST_API_URL");
		//mstrRESTURL = "";
	}
	public String getJioPointForMobileNo(String pMobileNumber, String pAgentId,String requestId) throws DataAccessException
	{
		BufferedReader lReader = null;
		Calendar lEndDate = null;
		String lClassName =  this.getClass().getName();
		String lMethodName = "getJioPointForMobileNo";
		soapLogger.info("request Id : "+requestId+"|Agent ID : "+pAgentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		HttpURLConnection lCon = null;
		OutputStreamWriter lOutputStramWriter = null;
		Object lObject = null;
		String line = null; 
		Calendar mStartTime = Calendar.getInstance();
		try 
		{
			if(mstrRESTURL == null || mstrRESTURL.equals(""))
			{
				throw new DataAccessException("Invalid URL for Service");
			}
			JSONObject lRequestWrapper = new JSONObject();
			JSONRequest lRequest = new JSONRequest();
			lRequestWrapper.put("Request", lRequest);
			lRequest.addHeaderObject("API_Name", "GetJioPointForMobileNo");
			String lTimeStamp = XMLGregorianCalendarUtil.convertDateFormat(XMLGregorianCalendarUtil.getSystemTime(),"yyyy-MM-dd HH:mm:ss", "yyyyMMddhhmmss");
			lRequest.addHeaderObject("TimeStamp", lTimeStamp);
			lRequest.addPayLoadObject("MerchantID", "100001000014068");
			JSONObject lMobileNumber = new JSONObject();
			lMobileNumber.put("MobileNo", pMobileNumber);
			lRequest.addPayLoadObject("GetJioPointForMobileNoReq", lMobileNumber);
			soapLogger.debug("Request ID : "+(requestId)+"|Request-Response: Start |Agent Id: " + pAgentId + "|Service Name:"+ lMethodName + "|Start Time:" + mStartTime.getTime());
			soapLogger.debug("Request ID : "+(requestId)+"|"+lRequestWrapper.toString());
			URL lURL = new URL(mstrRESTURL);
			lCon = (HttpURLConnection) lURL.openConnection();
			lCon.setDoOutput(true);
			lCon.setRequestMethod("POST");
			lCon.setRequestProperty("Content-Type", "application/json");
			lCon.setRequestProperty("Accept", "application/json");
			lOutputStramWriter = new OutputStreamWriter(lCon.getOutputStream());
			
			lOutputStramWriter.write(lRequestWrapper.toString());
			lOutputStramWriter.flush();
			StringBuilder lResponseString = new StringBuilder();  
			int HttpResult = lCon.getResponseCode(); 
			if (HttpResult == HttpURLConnection.HTTP_OK) 
			{
				lReader = new BufferedReader(new InputStreamReader(lCon.getInputStream()));
			    
			    
			    while ((line = lReader.readLine()) != null) 
			    {  
			        lResponseString.append(line);  
			    }
			     
			    if(lResponseString.toString().isEmpty())
			    {
			    	throw new DataAccessException("Empty JSON Response");
			    	
				}
			    lEndDate = Calendar.getInstance();
			    soapLogger.debug("Request ID : "+(requestId)+"|"+lResponseString.toString());
			    soapLogger.debug("Request ID : "+(requestId)+"|Request-Response: End Time:"+lEndDate.getTime()+"|Total Execution Time in millisecond: "+(lEndDate.getTimeInMillis()-mStartTime.getTimeInMillis())+"\n");
			    JSONObject lTempJsonObj = (JSONObject) new JSONParser().parse(lResponseString.toString());
			    JSONObject lResponseObject = (JSONObject) lTempJsonObj.get("Response");
			    if(lResponseObject == null || lResponseObject.isEmpty())
			    {
			    	throw new DataAccessException("No Response Received");
			    }
			    lObject = lResponseObject.get("Response_Header");
			    if(lObject == null || lObject instanceof JSONObject == false || ((JSONObject) lObject).isEmpty())
			    {
			    	throw new DataAccessException("No Response Received");
			    }
			    JSONObject lResponseHeader = (JSONObject) lObject;
			    if(lResponseHeader.get("API_Status")!= null && lResponseHeader.get("API_Status").equals("1"))
			    {
			    	lObject = lResponseObject.get("ErrorMsg");
			    	if(lObject == null || lObject instanceof JSONObject == false)
			    	{
			    		throw new DataAccessException("Internal Error while getting JIO Points");
			    	}
			    	JSONObject lErrorMsg = (JSONObject) lObject;
			    	String lError = (String)lErrorMsg.get("MsgCode");
			    	lError +=" - "+lErrorMsg.get("MsgDetails");
			    	throw new DataAccessException(lError);
			    }
			    else
			    {
			    	lObject = lResponseObject.get("Payload_Res");
			    	if(lObject == null || lObject instanceof JSONObject == false || ((JSONObject) lObject).isEmpty())
			    	{
			    		throw new DataAccessException("Empty Response Payload");
			    	}
			    	JSONObject lResponsePayLoad = (JSONObject) lObject;
			    	lObject  = lResponsePayLoad.get("GetJioPointForMobileNoRes");
			    	if(lObject == null || lObject instanceof JSONObject == false || ((JSONObject) lObject).isEmpty())
			    	{
			    		throw new DataAccessException("Empty Response Detais : GetJioPointForMobileNoRes");
			    	}
			    	
			    	lObject  = ((JSONObject) lObject).get("AccountDetailList");
			    	if(lObject == null || lObject instanceof JSONObject == false || ((JSONObject) lObject).isEmpty())
			    	{
			    		throw new DataAccessException("Empty Response Detais : AccountDetailList");
			    	}
			    	lObject  = ((JSONObject)lObject).get("AccountDetails");
			    	if(lObject == null || lObject instanceof JSONArray == false || ((JSONArray) lObject).isEmpty())
			    	{
			    		throw new DataAccessException("Empty Response Detais : AccountDetails");
			    	}
			    	JSONArray lAccountDetailsArray = (JSONArray) lObject;
			    	for (Object object : lAccountDetailsArray) 
			    	{
						if(((JSONObject)object).get("TypeCode").equals("50"))
						{
							return (String) ((JSONObject)object).get("AvailableJioBalance");
						}
					}
			    	
			    }
			} 
			else 
			{
				lEndDate = Calendar.getInstance();
				lReader = new BufferedReader(new InputStreamReader(lCon.getInputStream()));
				lResponseString = new StringBuilder();
				while((line = lReader.readLine()) != null) 
			    {  
			        lResponseString.append(line);  
			    }
			    soapLogger.debug("Request ID : "+(requestId)+"| Response Status : "+HttpResult+"|Response Error : "+lResponseString.toString());
			    soapLogger.debug("Request ID : "+(requestId)+"|Request-Response: End Time:"+lEndDate.getTime()+"|Total Execution Time in millisecond: "+(lEndDate.getTimeInMillis()-mStartTime.getTimeInMillis())+"\n");
			    throw new DataAccessException(lCon.getResponseMessage());  
			} 
		} 
		catch (Throwable t) 
		{

			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			logger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			throw new DataAccessException(t.getMessage(), t);
		}
		finally
		{
			try {
				if(lOutputStramWriter!=null) 
					lOutputStramWriter.close();
				if(lCon!=null)
					lCon.disconnect();
				if(lReader!=null)
					lReader.close();
			} catch (IOException e) 
			{
				e.printStackTrace();
			}
			soapLogger.info("request Id : "+requestId+"|Agent ID : "+pAgentId+"|["+lClassName +"."+lMethodName+"] | End");		
		}
		return "";
	}	
}
