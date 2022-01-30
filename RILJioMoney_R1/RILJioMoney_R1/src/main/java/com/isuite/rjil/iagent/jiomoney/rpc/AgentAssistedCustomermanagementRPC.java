package com.isuite.rjil.iagent.jiomoney.rpc;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.common.Customer;
import com.isuite.rjil.iagent.jiomoney.common.ServiceResponse;
import com.isuite.rjil.iagent.jiomoney.services.AgentAssistedCustomermanagementService;
import com.isuite.rjil.iagent.jiomoney.services.ServiceLocator;
import com.isuite.rjil.iagent.jiomoney.util.EmailValidator;
import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;
import com.isuite.rjil.iagent.jiomoney.util.Util;

public class AgentAssistedCustomermanagementRPC {
	private static final Logger soapLogger = Logger.getLogger(LOVUtil.SOAPLOGGER);
	
	private static final String service = "AgentAssistedCustomermanagementService";

	public ServiceResponse submitCustomerOrder(String orderRefNum,
			String orderAction, String orderCategory, String orderType,
			String salutation, String firstName, String middleName,
			String lastname, String dob, String gender, String nationality,
			String status, String maritalStatus, String relationshipType,
			String relFirstName, String relMiddleName, String relLastName,
			String primMobNum, String secMobNum, String primEmailId,
			String addressType, String city, String postCode, String houseNo,
			String streetName, String locality, String country, String state,
			String agentfirstName, String agentlastName, String hasNominee,
			String nomineefirstName, String nomineelastName,
			String relationship, String nomineeage, String nomineedob,
			String bankId, String branchName, String branchCode,
			String guardianFirstName, String guardianLastName,
			String guardianAge, String guardianaddressType,
			String guardiancity, String guardianpostCode,
			String guardianhouseNo, String guardianstreetName,
			String guardianlocality, String guardiancountry,
			String guardianstate, String pan, String docType, String type,
			String number, String issuedate, String placeOfIssue, String issuer,String agentId) 
	{
		long requestID = Util.getRequestId();
		ServiceResponse response = null;
		String lClassName =  this.getClass().getName();
		String lMethodName = "submitCustomerOrder";
		soapLogger.info("************************");
		soapLogger.info("Request ID : "+requestID+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		try 
		{
			if (primMobNum != null && !primMobNum.isEmpty()) {
				
				   if (primMobNum.trim().length() < 10) {
				    return new ServiceResponse(
				      2,
				      "Please enter a valid Mobile Number to Search, Mobile Number should be 10 digit");
				   }
				   
				  }
			if (primMobNum != null && !primMobNum.isEmpty()
					&& !primMobNum.startsWith("+91")) {
				primMobNum = "+91" + primMobNum;
			}
			
			if (primEmailId != null && !primEmailId.isEmpty()){
				if (!new EmailValidator().validate(primEmailId)) {
					return new ServiceResponse(2,"Please enter a valid Email Id");
				}

			}
				
			Customer submitcustomerorder = ((AgentAssistedCustomermanagementService) ServiceLocator
					.getService(service)).submitCustomerOrder(orderRefNum,
					orderAction, orderCategory, orderType, salutation,
					firstName, middleName, lastname, dob, gender, nationality,
					status, maritalStatus, relationshipType, relFirstName,
					relMiddleName, relLastName, primMobNum, secMobNum,
					primEmailId, addressType, city, postCode, houseNo,
					streetName, locality, country, state, agentfirstName,
					agentlastName, hasNominee, nomineefirstName,
					nomineelastName, relationship, nomineeage, nomineedob,
					bankId, branchName, branchCode, guardianFirstName,
					guardianLastName, guardianAge, guardianaddressType,
					guardiancity, guardianpostCode, guardianhouseNo,
					guardianstreetName, guardianlocality, guardiancountry,
					guardianstate, pan, docType, type, number, issuedate,
					placeOfIssue,agentId,requestID);
			if (submitcustomerorder != null) 
			{
				response = new ServiceResponse("Lead Create Successfully Reference No is "+submitcustomerorder.getOrderRefNum());
			} else 
			{
				response = new ServiceResponse(1,
						"No record Found in Agent Customer Order management");
			}
			
		} catch (Throwable t) {
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			response = new ServiceResponse(1, t.getMessage());
		}finally
		{
			soapLogger.info("Request ID : "+requestID+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
			soapLogger.info("************************");
		}
		return response;

	}
}
