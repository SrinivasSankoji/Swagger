package com.isuite.rjil.iagent.jiomoney.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.log4j.Logger;

public class WSHandler implements SOAPHandler<SOAPMessageContext> 
{

	private static final Logger soap = Logger.getLogger(LOVUtil.SOAPLOGGER);
	private String agentId;
	private Calendar mStartTime;
	private  String operationName=null;
    private long mlRequestId;
    
    
	public WSHandler(String agentId,long RequestId) 
	{
		this.agentId = agentId;
		mStartTime = Calendar.getInstance();
		mlRequestId = RequestId;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Set getHeaders() {
		return null;
	}

	public boolean handleFault(SOAPMessageContext context) {
		SOAPMessage message = context.getMessage();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
	    try 
		{
			soap.error("Request ID : "+(mlRequestId)+"|Error : \n");
			Calendar lEndDate = Calendar.getInstance();
			message.writeTo(out);
			soap.error("Request ID : "+(mlRequestId)+"|"+out.toString());
			soap.error("Request-Response: End : Request ID : "+(mlRequestId)+"|End Time:"+lEndDate.getTime()+"|Total Execution Time in millisecond: "+(lEndDate.getTimeInMillis()-mStartTime.getTimeInMillis())+"\n");
			out.flush();
			out.close();
		} catch (SOAPException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public void close(MessageContext context) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.xml.ws.handler.Handler#handleMessage(javax.xml.ws.handler.
	 * MessageContext)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public  boolean handleMessage(SOAPMessageContext context) 
	{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try
		{
			boolean isOutboundMessage = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
			SOAPMessage message = context.getMessage();
			Iterator i = message.getSOAPPart().getEnvelope().getBody().getChildElements();
			if (!isOutboundMessage) 
			{
				
				while (i.hasNext()) 
				{
					Object obj = i.next();
					if (obj instanceof SOAPElement) 
					{
						SOAPElement e = (SOAPElement) obj;
						operationName = e.getElementName().getLocalName();
					}
				}
				message.writeTo(out);
			   soap.debug("Request ID : "+(mlRequestId)+"|"+out.toString());
			   soap.debug("Request ID : "+(mlRequestId)+"|Response Header : "+ getHeaders((Map<String, List<String>>) context.get(SOAPMessageContext.HTTP_RESPONSE_HEADERS)));
			   Calendar lEndDate = Calendar.getInstance();
			   soap.debug("Request ID : "+(mlRequestId)+"|Request-Response: End Time:"+lEndDate.getTime()+"|Total Execution Time in millisecond: "+(lEndDate.getTimeInMillis()-mStartTime.getTimeInMillis())+"\n");
			}
			else
			{
				while (i.hasNext())
				{
					Object obj = i.next();
					if (obj instanceof SOAPElement) 
					{
						SOAPElement e = (SOAPElement) obj;
						operationName = e.getElementName().getLocalName();
					}
				}
				message.writeTo(out);
				  soap.debug("Request ID : "+(mlRequestId)+"|Request-Response: Start |Agent Id: " + this.agentId + "|Service Name:"+ operationName + "|Start Time:" + this.mStartTime.getTime());
				  soap.debug("Request ID : "+(mlRequestId)+"|Request Header : "+ getHeaders((Map<String, List<String>>) context.get(SOAPMessageContext.HTTP_REQUEST_HEADERS)));
				  soap.debug("Request ID : "+(mlRequestId)+"|"+out.toString());
				  message.writeTo(out);
			}
		}
		catch (SOAPException e) {
		   e.printStackTrace();
		  } catch (IOException e) {
		   e.printStackTrace();
		  }
		  finally{
		   try {
		    out.flush();
		    out.close();
		   } catch (IOException e) 
		   {
			   e.printStackTrace();
		   }
		   
		  }
		  return true;
		 }
	private String getHeaders(Map<String, List<String>> headers)
			throws IOException 	
	{

		StringBuffer result = new StringBuffer();

		if (headers != null) {

			for (Entry<String, List<String>> header : headers.entrySet()) {

				if (header.getValue().isEmpty()) {

					result.append(header.getValue());

				} else {

					for (String value : header.getValue()) {

						result.append(header.getKey() + " : " + value);

					}
				}
				result.append("\n											");
			}
		}
		return result.toString();
	}
	}