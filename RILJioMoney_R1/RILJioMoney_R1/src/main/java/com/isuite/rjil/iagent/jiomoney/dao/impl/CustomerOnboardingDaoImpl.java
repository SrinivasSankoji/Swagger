package com.isuite.rjil.iagent.jiomoney.dao.impl;

import static com.isuite.rjil.iagent.common.util.RequestUtils.getRequestHeader;

import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

import javax.xml.ws.BindingProvider;

import org.apache.log4j.Logger;



import com.isuite.rjil.iagent.jiomoney.dao.CustomerOnboardingDao;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.exception.ServiceException;
import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;
import com.isuite.rjil.iagent.jiomoney.util.PropertiesUtil;
import com.isuite.rjil.iagent.jiomoney.util.Util;
import com.isuite.rjil.iagent.jiomoney.util.WSHandlerResolver;
import com.isuite.rjil.iagent.jiomoney.util.XMLGregorianCalendarUtil;
import com.ril.rpsl.entities.commontypes_v1_0.TAddress;
import com.ril.rpsl.entities.commontypes_v1_0.TEmail;
import com.ril.rpsl.entities.commontypes_v1_0.TMiscEntities;
import com.ril.rpsl.entities.commontypes_v1_0.TNameDetails;
import com.ril.rpsl.entities.commontypes_v1_0.TNameValue;
import com.ril.rpsl.entities.commontypes_v1_0.TPhone;
import com.ril.rpsl.entities.individual_v1_0.TIndividual;
import com.ril.rpsl.services.customeronboarding_v1_0.CustomerDetails;
import com.ril.rpsl.services.customeronboarding_v1_0.RegisterProspectRq;
import com.ril.rpsl.services.customeronboarding_v1_0.RegisterProspectRs;
import com.ril.rpsl.wsdls.customeronboarding_v1_0.CustomerOnboarding;


public class CustomerOnboardingDaoImpl implements CustomerOnboardingDao
{

	private static final Logger logger = Logger.getLogger(CustomerOnboardingDaoImpl.class);
	private static final Logger soapLogger = Logger.getLogger(LOVUtil.SOAPLOGGER);
	private Properties platformProperties = PropertiesUtil.getProperties(PropertiesUtil.platform);
	
	private URL mWSDLLocation = null;
	private String mServiceEndPointURL = "";
	public CustomerOnboardingDaoImpl() 
	{
		try 
		{
			String serviceRequestInquiryWS_URL = platformProperties.getProperty("CustomerOnboarding");
			mWSDLLocation = new URL(serviceRequestInquiryWS_URL);
			mServiceEndPointURL = platformProperties.getProperty("CustomerOnboardingEndPoint");
		} 
		catch (Exception e) 
		{
			soapLogger.error("Error While Initializing ServiceRequestInquiryDaoImpl",e);
		}
	}
	public String createProspect(String firstName, String lastName,
			String dob, String mobileNo, String emailId, String gender,
			String state, String district, String pincode, String channel,
			String agentCode, long requestId) throws DataAccessException,
			ServiceException {
		
		String lClassName =  this.getClass().getName();
		String lMethodName = "createProspect";
		
		soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentCode+"|["+lClassName +"."+lMethodName+" ]| Start");
		
		try
		{
			RegisterProspectRq createProspectRequest =new RegisterProspectRq();
			
			CustomerDetails custDetails=new CustomerDetails();
			createProspectRequest.setRqHeader(getRequestHeader());
			
			TIndividual tCustomer= new TIndividual();
			TNameDetails tNameDetails =new TNameDetails();
			tNameDetails.setFirstName(firstName);
			tNameDetails.setLastName(lastName);
			tCustomer.setName(tNameDetails);
			tCustomer.setDob(XMLGregorianCalendarUtil.convertDateFormat(dob, "dd/MM/yyyy", "dd-MM-yy"));
			if(gender!= null && gender.equalsIgnoreCase("Male")){
				tCustomer.setGender("M");
			}else if(gender!= null && gender.equalsIgnoreCase("Female")){
				tCustomer.setGender("F");
			}
			
			custDetails.setCustomer(tCustomer);
			
			TPhone objTPhone =new TPhone();
			objTPhone.setPrimMobNum(mobileNo);
			custDetails.setPhoneDetails(objTPhone);
			
			TEmail objEmail = new TEmail();
			objEmail.setPrimEmailId(emailId);
			custDetails.setEmailDetails(objEmail);
			
			TAddress objTAddress =new TAddress();
			objTAddress.setPostCode(pincode);
			objTAddress.setCountry("356");
			objTAddress.setState(state);
			objTAddress.setDistrict(district);
			
			ArrayList<String> addressLine=new ArrayList<String>();
			addressLine.add("123");
			if(objTAddress.getAddressLine()!= null){
				objTAddress.getAddressLine().add("123");
			}
			custDetails.setAddressDetails(objTAddress);
			
			
			createProspectRequest.setCustomerDetails(custDetails);
			
			TMiscEntities objTMisc =new TMiscEntities();
			
			TNameValue tNameValue = new TNameValue();
			tNameValue.setName("PROSPECT");
			tNameValue.setValue("Y");
			objTMisc.getNameValue().add(tNameValue);
			
			createProspectRequest.setMiscEntities(objTMisc);
			
			/************************ Response ************************************************/
			CustomerOnboarding  serviceAgent = new CustomerOnboarding(mWSDLLocation);
			serviceAgent.setHandlerResolver(new WSHandlerResolver(agentCode,requestId));
			com.ril.rpsl.wsdls.customeronboarding_v1_0.Operations serviceEndPoint = serviceAgent.getOperationsEndpoint();
			/******************* Setting the END POINT URL For SR Inquiry ******************/
			BindingProvider bindingProvider = (BindingProvider) serviceEndPoint;
			bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, mServiceEndPointURL);
			/************************ Response ************************************************/
			RegisterProspectRs response = serviceEndPoint.registerProspect(createProspectRequest);
			if (response != null&& response.getResponseDetails() != null && response.getResponseDetails().getStatus() != null && response.getResponseDetails().getStatus().equalsIgnoreCase("FAILED")) 
			{
				if (response.getResponseDetails().getErrors() != null && response.getResponseDetails().getErrors().getError() != null && response.getResponseDetails().getErrors().getError().size() > 0
						&& response.getResponseDetails().getErrors().getError().get(0).getErrorMsg() != null) 
				{
					throw new DataAccessException(response.getResponseDetails().getErrors().getError().get(0).getErrorMsg());
				}
			}
			String lstrProspectID = response.getProspectId();
			if(Util.isEmptyString(lstrProspectID))
				throw new DataAccessException("Error while creating Prospect.");
			return lstrProspectID;
			
			
		}catch (Throwable t) {
			logger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			throw new DataAccessException(t.getMessage(), t);
		}
		
	}
}
