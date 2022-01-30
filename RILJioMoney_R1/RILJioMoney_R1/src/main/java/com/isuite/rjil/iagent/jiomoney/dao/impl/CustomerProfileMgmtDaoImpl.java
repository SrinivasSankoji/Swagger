package com.isuite.rjil.iagent.jiomoney.dao.impl;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.UUID;

import javax.xml.ws.BindingProvider;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.common.Address;
import com.isuite.rjil.iagent.jiomoney.common.Customer;
import com.isuite.rjil.iagent.jiomoney.common.NomineeDetails;
import com.isuite.rjil.iagent.jiomoney.dao.CustomerProfileMgmtDao;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;
import com.isuite.rjil.iagent.jiomoney.util.PropertiesUtil;
import com.isuite.rjil.iagent.jiomoney.util.WSHandlerResolver;
import com.isuite.rjil.iagent.jiomoney.util.XMLGregorianCalendarUtil;

import cpm.com.ril.rpsl.entities.commontypes_v1_0.TEmail;
import cpm.com.ril.rpsl.entities.header_v1_0.TBusinessApplicationContext;
import cpm.com.ril.rpsl.entities.header_v1_0.TMessageContext;
import cpm.com.ril.rpsl.entities.header_v1_0.TRqHeader;
import cpm.com.ril.rpsl.entities.individual_v1_0.TIndividual;
import cpm.com.ril.rpsl.services.customerprofilemanagement_v1_0.RetrieveCustomerDetailsRq;
import cpm.com.ril.rpsl.services.customerprofilemanagement_v1_0.RetrieveCustomerDetailsRs;
import cpm.com.ril.rpsl.services.customerprofilemanagement_v1_0.UpdateCustomerProfileRq;
import cpm.com.ril.rpsl.services.customerprofilemanagement_v1_0.UpdateCustomerProfileRq.CustomerDetails;
import cpm.com.ril.rpsl.services.customerprofilemanagement_v1_0.UpdateCustomerProfileRq.OldCustomerDetails;
import cpm.com.ril.rpsl.services.customerprofilemanagement_v1_0.UpdateCustomerProfileRs;
import cpm.com.ril.rpsl.wsdls.customerprofilemanagement_v1_0.CustomerProfileManagement;
import cpm.com.ril.rpsl.wsdls.customerprofilemanagement_v1_0.CustomerProfileManagementFault;
import cpm.com.ril.rpsl.wsdls.customerprofilemanagement_v1_0.Operations;

/**
 * Title: CustomerProfileMgmtDaoImpl.java <br>
 * Company: 2014-15 NovelVox Software Pvt Ltd<br>
 * 
 * @author Anand Raman(AR)
 * @version Build 0.5 <br>
 *          Changes and requirement:<br>
 *          <ul>
 * 
 *          </ul>
 * @version ReleaseR1-Sprint 3
 * 
 */
public class CustomerProfileMgmtDaoImpl implements CustomerProfileMgmtDao {
	private static Logger logger = Logger
			.getLogger(CustomerProfileMgmtDaoImpl.class);
	private static final Logger soapLogger = Logger
			.getLogger(LOVUtil.SOAPLOGGER);

	private Properties platformProperties = PropertiesUtil
			.getProperties(PropertiesUtil.platform);
/*Start Abhishek 23 Jan 2017*/
	String lClassName;
	String lMethodName;
	/*End Abhishek*/
	@Override
	public Customer updateCustomerProfile(String entityId, String oldEmailId,
			String addressLine, String country, String mobileNo,
			String newEmailId, String agentId,long requestId) throws DataAccessException {
		String lClassName =  this.getClass().getName();
		String lMethodName = "updateCustomerProfile";
		
		soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		try {
			/******************* Setting the Header Request ******************/
			TRqHeader headerRequest = new TRqHeader();
			TMessageContext messageRequest = new TMessageContext();
			messageRequest.setMessageID(String.valueOf(UUID.randomUUID()));
			messageRequest.setCorrelationID(String.valueOf(UUID.randomUUID()));
			messageRequest.setTimeStamp(XMLGregorianCalendarUtil
					.getSystemTime());
			headerRequest.setMessageContext(messageRequest);

			TBusinessApplicationContext businessApplicationRequest = new TBusinessApplicationContext();
			businessApplicationRequest.setOperationName("");
			businessApplicationRequest.setMessageType("REQ");
			businessApplicationRequest.setSrcApplicationname("WEB");
			headerRequest
					.setBusinessApplicationContext(businessApplicationRequest);
			/***************************************************************/
			// Setting Customer Details
			CustomerDetails newCustomerDetails = new CustomerDetails();
			TIndividual newCustomerReq = new TIndividual();
			newCustomerReq.setEntityId(entityId.toUpperCase());

			TEmail newEmailIdReq = new TEmail();
			/*
			 * Changes done by Anand Dated 7-4-2015 Adding new fields new
			 * EmailId
			 */
			// Start
			newEmailIdReq.setPrimEmailId(newEmailId);
			// End
			newCustomerDetails.setEmailDetails(newEmailIdReq);
			newCustomerDetails.setCustomer(newCustomerReq);

			/*
			 * Changes done by Anand Dated 7-4-2015 Adding new fields EmailId
			 * and Entity Id
			 */
			// Start
			OldCustomerDetails oldCustomerDetails = new OldCustomerDetails();

			TIndividual oldCustomerReq = new TIndividual();
			oldCustomerReq.setEntityId(entityId);

			TEmail oldEmailIdReq = new TEmail();
			oldEmailIdReq.setPrimEmailId(oldEmailId);

			oldCustomerDetails.setEmailDetails(oldEmailIdReq);
			oldCustomerDetails.setCustomer(oldCustomerReq);
			// End

			UpdateCustomerProfileRq request = new UpdateCustomerProfileRq();
			request.setRqHeader(headerRequest);
			request.setMob(mobileNo);
			request.setCustomerDetails(newCustomerDetails);
			request.setOldCustomerDetails(oldCustomerDetails);
			/******************* Setting the WSDL URL ******************/
			String customerProfileManagementWsdlUrl = platformProperties
					.getProperty("customerProfileManagement");
			URL wsdlUrl = new URL(customerProfileManagementWsdlUrl);
			CustomerProfileManagement agent = new CustomerProfileManagement(
					wsdlUrl);
			agent.setHandlerResolver(new WSHandlerResolver(agentId,requestId				));
			Operations endPoint = agent.getOperationsEndpoint();
			/******************* Setting the endpoint URL For Dealer Inquiry ******************/
			String endPointURL = platformProperties
					.getProperty("customerProfileManagementEndPoint");
			BindingProvider bindingProvider = (BindingProvider) endPoint;
			bindingProvider.getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointURL);

			/******************************* Response ****************************************/
			UpdateCustomerProfileRs response = endPoint
					.updateCustomerProfile(request);

			if (response != null
					&& response.getResponseDetails() != null
					&& response.getResponseDetails().getStatus() != null
					&& response.getResponseDetails().getStatus()
							.equalsIgnoreCase("FAILED")) {
				if (response.getResponseDetails().getErrors() != null
						&& response.getResponseDetails().getErrors().getError() != null
						&& response.getResponseDetails().getErrors().getError()
								.size() > 0
						&& response.getResponseDetails().getErrors().getError()
								.get(0).getErrorMsg() != null) {
					throw new DataAccessException(response.getResponseDetails()
							.getErrors().getError().get(0).getErrorMsg());
				}
			}
			Customer customerProfilePojo = null;
			if (response != null) {
				customerProfilePojo = new Customer();
				
				/*************************** ResponseDetails **************************/
				/****************************** ResponseStatus *******************
				if (response.getResponseDetails() != null) {
					if (response.getResponseDetails().getInfos() != null) {
						for (TInfo tInfoResponse : response
								.getResponseDetails().getInfos().getInfo()) {

							customerProfilePojo.setProviderStatus(HeaderStatus
									.setStatus(tInfoResponse.getCode(),
											tInfoResponse.getMsg(),
											tInfoResponse.getProvider()
													.getProviderName(),
											tInfoResponse.getProvider()
													.getProviderCode()));
						}
					}
				}
				if (response.getRsHeader() != null) {
					/****************************** MessageContext *******************
					if (response.getRsHeader().getMessageContext() != null) {
						customerProfilePojo
								.setHeaderMessage(HeaderStatus
										.setMessage(response.getRsHeader()
												.getMessageContext()
												.getBusinessID(),
												response.getRsHeader()
														.getMessageContext()
														.getEsbID(), response
														.getRsHeader()
														.getMessageContext()
														.getMessageID(),
												response.getRsHeader()
														.getMessageContext()
														.getCorrelationID(),
												response.getRsHeader()
														.getMessageContext()
														.getExtCorrelationID(),
												response.getRsHeader()
														.getMessageContext()
														.getTimeStamp()));
					}

					/********************** BusinessApplicationContext *******************
					if (response.getRsHeader().getBusinessApplicationContext() != null) {

						customerProfilePojo.setBusinessApplication(HeaderStatus
								.setBusinessApplication(response.getRsHeader()
										.getBusinessApplicationContext()
										.getServiceVersion(), response
										.getRsHeader()
										.getBusinessApplicationContext()
										.getOperationVersion(), response
										.getRsHeader()
										.getBusinessApplicationContext()
										.getOperationName(), response
										.getRsHeader()
										.getBusinessApplicationContext()
										.getMessageType(), response
										.getRsHeader()
										.getBusinessApplicationContext()
										.getSrcApplicationname()));
					}
				}*/
			}
			return customerProfilePojo;
		} catch (CustomerProfileManagementFault e) {
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", e);
			logger.error("Exception in ["+lClassName+"."+lMethodName+"]", e);
			String exception = "";
			if (e.getFaultInfo() != null
					&& e.getFaultInfo().getErrors() != null
					&& e.getFaultInfo().getErrors().getError() != null
					&& e.getFaultInfo().getErrors().getError().size() > 0
					&& e.getFaultInfo().getErrors().getError().get(0)
							.getErrorMsg() != null) {
				exception = e.getFaultInfo().getErrors().getError().get(0)
						.getErrorMsg();
				throw new DataAccessException(exception, e);
			} else {
				throw new DataAccessException(e.getMessage(), e);
			}
		} catch (Throwable t) {
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			logger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			throw new DataAccessException(t.getMessage(), t);
		}
		finally
		{
			soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
			
		}
	}
/*start Abhishek*/
	@Override
	public Customer retrieveCustomerDetails(String entityId, String mobileNo,
			String accountType,String orderRefNo,String agentId,long requestId) throws DataAccessException {
		// TODO Auto-generated method stub
		 lClassName =  this.getClass().getName();
		 lMethodName = "retrieveCustomerDetails";
		 soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
			try {
				/******************* Setting the Header Request ******************/
				TRqHeader headerRequest = new TRqHeader();
				TMessageContext messageRequest = new TMessageContext();
				messageRequest.setMessageID(String.valueOf(UUID.randomUUID()));
				messageRequest.setCorrelationID(String.valueOf(UUID.randomUUID()));
				messageRequest.setTimeStamp(XMLGregorianCalendarUtil
						.getSystemTime());
				headerRequest.setMessageContext(messageRequest);

				TBusinessApplicationContext businessApplicationRequest = new TBusinessApplicationContext();
				businessApplicationRequest.setOperationName("");
				businessApplicationRequest.setMessageType("REQ");
				businessApplicationRequest.setSrcApplicationname("WEB");
				headerRequest
						.setBusinessApplicationContext(businessApplicationRequest);
				/***************************************************************/
				// Retrieve Customer Details
				
				RetrieveCustomerDetailsRq retrieveCustomerReq = new RetrieveCustomerDetailsRq();
				CustomerDetails retrieveCustomerDetails = new CustomerDetails();
				//TIndividual retrieveCustomerReq = new TIndividual();
				retrieveCustomerReq.setRqHeader(headerRequest);
				retrieveCustomerReq.setCustomerID(entityId);
				retrieveCustomerReq.setAccountType(accountType);
				retrieveCustomerReq.setMobileNo(mobileNo);
				retrieveCustomerReq.setOrderRefNo(orderRefNo);
				
				/*UpdateCustomerProfileRq request = new UpdateCustomerProfileRq();
				request.setRqHeader(headerRequest);
				request.setMob(mobileNo);
				request.setCustomerDetails(newCustomerDetails);
				request.setOldCustomerDetails(oldCustomerDetails);
				/******************* Setting the WSDL URL ******************/
				String customerProfileManagementWsdlUrl = platformProperties
						.getProperty("customerProfileManagement");
				URL wsdlUrl = new URL(customerProfileManagementWsdlUrl);
				CustomerProfileManagement agent = new CustomerProfileManagement(
						wsdlUrl);
				agent.setHandlerResolver(new WSHandlerResolver(agentId,requestId				));
				Operations endPoint = agent.getOperationsEndpoint();
				/******************* Setting the endpoint URL For Dealer Inquiry ******************/
				String endPointURL = platformProperties
						.getProperty("customerProfileManagementEndPoint");
				BindingProvider bindingProvider = (BindingProvider) endPoint;
				bindingProvider.getRequestContext().put(
						BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointURL);

				/******************************* Response ****************************************/
				RetrieveCustomerDetailsRs response = endPoint.retrieveCustomerDetails(retrieveCustomerReq);

				if (response != null
						&& response.getResponseDetails() != null
						&& response.getResponseDetails().getStatus() != null
						&& response.getResponseDetails().getStatus()
								.equalsIgnoreCase("FAILED")) {
					if (response.getResponseDetails().getErrors() != null
							&& response.getResponseDetails().getErrors().getError() != null
							&& response.getResponseDetails().getErrors().getError()
									.size() > 0
							&& response.getResponseDetails().getErrors().getError()
									.get(0).getErrorMsg() != null) {
						throw new DataAccessException(response.getResponseDetails()
								.getErrors().getError().get(0).getErrorMsg());
					}
				}
				Customer customerProfilePojo = null;
				SimpleDateFormat simpleDF = new SimpleDateFormat("dd-MM-yyyy"); 
				if (response != null) {
					customerProfilePojo = new Customer();
					if(response.getCustomer()!=null && response.getCustomer().getName()!=null){
						customerProfilePojo.setFirstName(response.getCustomer().getName().getFirstName() != null ? response.getCustomer().getName().getFirstName() : "");
						customerProfilePojo.setMiddleName(response.getCustomer().getName().getMiddleName() != null ? response.getCustomer().getName().getMiddleName() : "");
						customerProfilePojo.setLastName(response.getCustomer().getName().getLastName() != null ? response.getCustomer().getName().getLastName() : "");
					}
					if(response.getEmailDetails()!=null){
						customerProfilePojo.setPrimEmailId(response.getEmailDetails().getPrimEmailId() != null ? response.getEmailDetails().getPrimEmailId() :"");
					}
					
					if(response.getPhoneDetails()!=null){
						customerProfilePojo.setMobileNo(response.getPhoneDetails().getPrimMobNum() != null ? response.getPhoneDetails().getPrimMobNum() :"");
					}
					
					NomineeDetails lNomineeDetails = new NomineeDetails();
					if(response.getCustomerNomineeDetails()!=null){
						lNomineeDetails.setAge(response.getCustomerNomineeDetails().getAge() != null ?response.getCustomerNomineeDetails().getAge()+"" : "");
						lNomineeDetails.setDob(response.getCustomerNomineeDetails().getDOB() != null ? XMLGregorianCalendarUtil.toDate(response.getCustomerNomineeDetails().getDOB(), simpleDF): "");
						lNomineeDetails.setGuardianFName(response.getCustomerNomineeDetails().getGuardianFName() != null ?response.getCustomerNomineeDetails().getGuardianFName() : "");
						lNomineeDetails.setGuardianLName(response.getCustomerNomineeDetails().getGuardianLName() != null ?response.getCustomerNomineeDetails().getGuardianLName() : "");
						lNomineeDetails.setGuardianAge(response.getCustomerNomineeDetails().getGuardianAge() != null ?response.getCustomerNomineeDetails().getGuardianAge()+"" : "");
						lNomineeDetails.setGuardianAdd(response.getCustomerNomineeDetails().getGuardianAdd() != null ?response.getCustomerNomineeDetails().getGuardianAdd() : "");
						lNomineeDetails.setName(response.getCustomerNomineeDetails().getName() != null ?response.getCustomerNomineeDetails().getName(): "");
						lNomineeDetails.setRelationship(response.getCustomerNomineeDetails().getRelationShip() != null ?response.getCustomerNomineeDetails().getRelationShip(): "");
						customerProfilePojo.setNomineeDetails(lNomineeDetails);
					}
					
					
					
					
					
					
					
					//customerProfilePojo.setAddress(response.getAddressDetails(). != null ? response.getCustomer().getName().getLastName() : "");
	}
				
	return customerProfilePojo;
} catch (CustomerProfileManagementFault e) {
	soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", e);
	logger.error("Exception in ["+lClassName+"."+lMethodName+"]", e);
	String exception = "";
	if (e.getFaultInfo() != null
			&& e.getFaultInfo().getErrors() != null
			&& e.getFaultInfo().getErrors().getError() != null
			&& e.getFaultInfo().getErrors().getError().size() > 0
			&& e.getFaultInfo().getErrors().getError().get(0)
					.getErrorMsg() != null) {
		exception = e.getFaultInfo().getErrors().getError().get(0)
				.getErrorMsg();
		throw new DataAccessException(exception, e);
	} else {
		throw new DataAccessException(e.getMessage(), e);
	}
} catch (Throwable t) {
	soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
	logger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
	throw new DataAccessException(t.getMessage(), t);
}
finally
{
	soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
	
}
		
	}
	/*End Abhishek*/
	/*public static void main(String[] args) throws DataAccessException {
		System.out.println("Welcome");
		new CustomerProfileMgmtDaoImpl().retrieveCustomerDetails("1100039234", "", "A", "", "12", 4655L);
	}*/
}
