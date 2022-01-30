package com.isuite.rjil.iagent.jiomoney.rpc;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.common.ServiceResponse;
import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;
import com.isuite.util.crypto.ISuiteCryptoUtil;
import com.isuite.util.crypto.exception.ISuiteCryptoException;

import flex.messaging.io.amf.ASObject;

public class AgentCredentialsForLoginRPC {
	private static final Logger logger = Logger.getLogger(CommonRpc.class);
	private static final Logger soap = Logger.getLogger(LOVUtil.SOAPLOGGER);
	
	@SuppressWarnings("unchecked")
	public ServiceResponse getAgentCredentialsForLogin(Object urlParameters) 
	{
		ServiceResponse response = null;
		try 
		{
			ASObject asObject = new ASObject();
			String agentId = "";
			String pass = "";
			String extension = "";
			if (urlParameters != null) 
			{
				String encodedString = (String) urlParameters;
				try 
				{
					String decodedUrlParameters = ISuiteCryptoUtil.decode(encodedString);
					logger.debug("Decoded URL Parameters are as Follows__________________"
					+ decodedUrlParameters);
					soap.debug("Decoded URL Parameters are as Follows__________________"
							+ decodedUrlParameters);
					if (decodedUrlParameters != null
					&& !decodedUrlParameters.isEmpty()
					&& decodedUrlParameters.contains("|")) 
					{
						String[] str = decodedUrlParameters.split("\\|");
						agentId = str[2];
						pass = str[3];
						extension = str[4];
						asObject.put("agentId", agentId);
						asObject.put("pass", pass);
						asObject.put("extension", extension);
		
					}
					response = new ServiceResponse(0, null, asObject);
		
				}
				catch (ISuiteCryptoException e) 
				{
					logger.error("Exception catched while decoding URL Parameters",e);
					return new ServiceResponse(1,"Unable To Get RoleID For Specified Agent "+ ",User Rights will  not Work");
				}
	
			}

		} 
		catch (Exception e) 
		{

			logger.error(
			"Exception catched while retrieving agent credentials  ", e);
			response = new ServiceResponse(1,
			"Unable to retrieve Agent Credentials due to :"
			+ e.getMessage());
			response = new ServiceResponse(1, "Unable to get finesse details to login", null);
		}
		return response;

	}

}
