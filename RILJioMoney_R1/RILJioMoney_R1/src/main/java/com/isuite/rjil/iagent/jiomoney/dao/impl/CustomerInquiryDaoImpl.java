package com.isuite.rjil.iagent.jiomoney.dao.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.xml.ws.BindingProvider;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.common.Account;
import com.isuite.rjil.iagent.jiomoney.common.Address;
import com.isuite.rjil.iagent.jiomoney.common.Bank;
import com.isuite.rjil.iagent.jiomoney.common.Customer;
import com.isuite.rjil.iagent.jiomoney.common.Document;
import com.isuite.rjil.iagent.jiomoney.common.ReferenceData;
import com.isuite.rjil.iagent.jiomoney.dao.CustomerInquiryDao;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.exception.ServiceException;
import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;
import com.isuite.rjil.iagent.jiomoney.util.PropertiesUtil;
import com.isuite.rjil.iagent.jiomoney.util.Util;
import com.isuite.rjil.iagent.jiomoney.util.WSHandlerResolver;
import com.isuite.rjil.iagent.jiomoney.util.XMLGregorianCalendarUtil;

import cusinq.com.ril.rpsl.entities.commontypes_v1_0.TAddress;
import cusinq.com.ril.rpsl.entities.commontypes_v1_0.TDataRecord;
import cusinq.com.ril.rpsl.entities.commontypes_v1_0.TEmail;
import cusinq.com.ril.rpsl.entities.commontypes_v1_0.TInfo;
import cusinq.com.ril.rpsl.entities.commontypes_v1_0.TMiscEntities;
import cusinq.com.ril.rpsl.entities.commontypes_v1_0.TNameDetails;
import cusinq.com.ril.rpsl.entities.commontypes_v1_0.TNameValue;
import cusinq.com.ril.rpsl.entities.commontypes_v1_0.TPhone;
import cusinq.com.ril.rpsl.entities.header_v1_0.TBusinessApplicationContext;
import cusinq.com.ril.rpsl.entities.header_v1_0.TMessageContext;
import cusinq.com.ril.rpsl.entities.header_v1_0.TRqHeader;
import cusinq.com.ril.rpsl.services.customerinquiry_v1_0.AccountDetails;
import cusinq.com.ril.rpsl.services.customerinquiry_v1_0.CustomerDetails;
import cusinq.com.ril.rpsl.services.customerinquiry_v1_0.CustomerDetails.Documents;
import cusinq.com.ril.rpsl.services.customerinquiry_v1_0.GetCustomerDetailsRq;
import cusinq.com.ril.rpsl.services.customerinquiry_v1_0.GetCustomerDetailsRs;
import cusinq.com.ril.rpsl.services.customerinquiry_v1_0.GetPrePaidAccountInfoRq;
import cusinq.com.ril.rpsl.services.customerinquiry_v1_0.GetPrePaidAccountInfoRs;
import cusinq.com.ril.rpsl.services.customerinquiry_v1_0.SearchCustomerRq;
import cusinq.com.ril.rpsl.services.customerinquiry_v1_0.SearchCustomerRs;
import cusinq.com.ril.rpsl.wsdls.customerinquiry_v1_0.CustomerInquiry;
import cusinq.com.ril.rpsl.wsdls.customerinquiry_v1_0.CustomerInquiryFault;
import cusinq.com.ril.rpsl.wsdls.customerinquiry_v1_0.Operations;

/**
 * Title: CustomerInquiryDaoImpl.java <br>
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
public class CustomerInquiryDaoImpl implements CustomerInquiryDao {
	private static final Logger logger = Logger
			.getLogger(CustomerInquiryDaoImpl.class);
	private static final Logger soapLogger = Logger
			.getLogger(LOVUtil.SOAPLOGGER);

	private Properties platformProperties = PropertiesUtil
			.getProperties(PropertiesUtil.platform);

	@Override
	public Customer getPrepaidAccountInfo(String customerId, String accountId,
			String currency, String branchId, String institutionCode,
			String agentId,long requestId) throws DataAccessException 
	{
		String lClassName =  this.getClass().getName();
		String lMethodName = "getPrepaidAccountInfo";
		
		soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		try {

			/******************* Setting the Header Request ******************/
			TRqHeader headerRequest = new TRqHeader();
			TMessageContext messageRequest = new TMessageContext();
			messageRequest.setMessageID(String.valueOf(UUID.randomUUID()));
			messageRequest.setCorrelationID(String.valueOf(UUID.randomUUID()));
			headerRequest.setMessageContext(messageRequest);
			TBusinessApplicationContext businessApplicationRequest = new TBusinessApplicationContext();

			businessApplicationRequest.setOperationName("");
			businessApplicationRequest.setMessageType("");
			businessApplicationRequest.setSrcApplicationname("");
			headerRequest
					.setBusinessApplicationContext(businessApplicationRequest);
			/*************************** Setting the Request ***********************************/
			GetPrePaidAccountInfoRq request = new GetPrePaidAccountInfoRq();
			request.setCustomerId(customerId.toUpperCase());
			request.setRqHeader(headerRequest);
			request.setInstitutionId("");
			/******************* Setting the WSDL URL ******************/

			String customerInquiryWsdlUrl = platformProperties
					.getProperty("customerInquiry");

			String endPointURL = platformProperties
					.getProperty("customerInquiryEndPoint");
			String initiatedBy = platformProperties.getProperty("initiatedBy");
			URL wsdlUrl = new URL(customerInquiryWsdlUrl);
			// change ajay from 15-April-2015
			CustomerInquiry agent = new CustomerInquiry(wsdlUrl);
			agent.setHandlerResolver(new WSHandlerResolver(agentId,requestId				));
			Operations endPoint = agent.getOperationsEndpoint();
			/******************* Setting the endpoint URL For CustomerInquiry ******************/
			BindingProvider bindingProvider = (BindingProvider) endPoint;
			bindingProvider.getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointURL);

			/************************ Response ************************************************/
			GetPrePaidAccountInfoRs response = endPoint
					.getPrepaidAccountInfo(request);

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

				} else {
					throw new DataAccessException(
							"Unable to search the customer i.e " + customerId);
				}
			}
			Customer custumerPojo = null;
			if (response != null) {
				custumerPojo = new Customer();
				/******************* Account Info ******************/
				if (response.getCustomer() != null) {
					/******************* customerPersonalDetails Info ******************/
					custumerPojo.setCustId(customerId.toUpperCase());
					custumerPojo.setInitiatedBy(initiatedBy);
					custumerPojo
							.setSalutation(response.getCustomer().getName() != null ? response
									.getCustomer().getName().getSalutation() != null ? response
									.getCustomer().getName().getSalutation()
									: ""
									: "");
					custumerPojo
							.setFirstName(response.getCustomer().getName() != null ? response
									.getCustomer().getName().getFirstName() != null ? response
									.getCustomer().getName().getFirstName()
									: ""
									: "");
					custumerPojo
							.setLastName(response.getCustomer().getName() != null ? response
									.getCustomer().getName().getLastName() != null ? response
									.getCustomer().getName().getLastName()
									: ""
									: "");
					/*
					 * Changes done by Anand Dated 7-4-2015 Adding new fields
					 * Middle Name
					 */
					// Start
					custumerPojo
							.setMiddleName(response.getCustomer().getName() != null ? response
									.getCustomer().getName().getMiddleName() != null ? response
									.getCustomer().getName().getMiddleName()
									: ""
									: "");
					// End
					custumerPojo.setRelationshipType(response.getCustomer()
							.getRelation() != null ? response.getCustomer()
							.getRelation().getType() != null ? response
							.getCustomer().getRelation().getType() : "" : "");
					custumerPojo.setRelFirstName(response.getCustomer()
							.getRelation() != null ? response.getCustomer()
							.getRelation().getName() != null ? response
							.getCustomer().getRelation().getName()
							.getFirstName() != null ? response.getCustomer()
							.getRelation().getName().getFirstName() : "" : ""
							: "");
					custumerPojo.setRelLastName(response.getCustomer()
							.getRelation() != null ? response.getCustomer()
							.getRelation().getName() != null ? response
							.getCustomer().getRelation().getName()
							.getLastName() != null ? response.getCustomer()
							.getRelation().getName().getLastName() : "" : ""
							: "");
					/*
					 * Changes done by Anand Dated 7-4-2015 Adding new fields
					 * Relationship Type--> Middle Name
					 */
					// Start
					custumerPojo.setRelMiddleName(response.getCustomer()
							.getRelation() != null ? response.getCustomer()
							.getRelation().getName() != null ? response
							.getCustomer().getRelation().getName()
							.getMiddleName() != null ? response.getCustomer()
							.getRelation().getName().getMiddleName() : "" : ""
							: "");
					// End
					if (response.getCustomer().getDob() != null) {
						if ("00000000".equalsIgnoreCase(response.getCustomer()
								.getDob())) {
							custumerPojo.setDob("");
						} else {
							custumerPojo
									.setDob(response.getCustomer().getDob() != null ? XMLGregorianCalendarUtil
											.convertDateFormat(response
													.getCustomer().getDob(),
													"yyyy-mm-dd", "dd-MM-yyyy")
											: "");
						}
					} else {
						custumerPojo.setDob("");
					}

					custumerPojo
							.setGender(response.getCustomer().getGender() != null ? response
									.getCustomer().getGender() : "");
					custumerPojo.setNationality(response.getCustomer()
							.getNationality() != null ? response.getCustomer()
							.getNationality() : "");

					custumerPojo.setOccupation(response.getCustomer()
							.getOccupation() != null ? response.getCustomer()
							.getOccupation() : "");
					custumerPojo.setMaritalStatus(response.getCustomer()
							.getMaritalStatus() != null ? response
							.getCustomer().getMaritalStatus() : "");
					if (response.getPhoneDetails() != null) {
						custumerPojo
								.setAltNoHome(response.getPhoneDetails() != null ? (response
										.getPhoneDetails().getSecMobNum() != null ? response
										.getPhoneDetails().getSecMobNum() : "")
										: "");
						custumerPojo
								.setMobileNo(response.getPhoneDetails() != null ? response
										.getPhoneDetails().getPrimMobNum() != null ? response
										.getPhoneDetails().getPrimMobNum() : ""
										: "");
						/*
						 * Changes done by Anand Dated 7-4-2015 Adding new
						 * fields Alt No Work
						 */
						// Start
						custumerPojo
								.setAltNoWork(response.getPhoneDetails() != null ? response
										.getPhoneDetails().getPhoneNum() != null ? response
										.getPhoneDetails().getPhoneNum() : ""
										: "");
						// End
						custumerPojo
								.setPrimEmailId(response.getEmailDetails() != null ? response
										.getEmailDetails().getPrimEmailId() != null ? response
										.getEmailDetails().getPrimEmailId()
										: ""
										: "");
					} else {
						custumerPojo.setAltNoHome("");
						custumerPojo.setMobileNo("");
						custumerPojo.setPrimEmailId("");
						custumerPojo.setAltNoWork("");
					}
					if (response.getCustomerIdentity() != null) {
						/*
						 * custumerPojo.setPanNo(response.getCustomerIdentity().
						 * getPanIdentification() != null ?
						 * (response.getCustomerIdentity
						 * ().getPanIdentification().getPanNumber() != null ?
						 * response
						 * .getCustomerIdentity().getPanIdentification().
						 * getPanNumber() : "") : "");
						 * custumerPojo.setVisaNo(response
						 * .getCustomerIdentity().
						 * getPassportIdentification().getVisaNum() != null ?
						 * response
						 * .getCustomerIdentity().getPassportIdentification
						 * ().getVisaNum() : "");
						 * custumerPojo.setPassportNo(response
						 * .getCustomerIdentity().getPassportIdentification() !=
						 * null ?
						 * (response.getCustomerIdentity().getPassportIdentification
						 * ().getPassportNumber() != null ?
						 * response.getCustomerIdentity
						 * ().getPassportIdentification().getPassportNumber() :
						 * ""): ""); custumerPojo.setVisaValidityDate(response.
						 * getCustomerIdentity
						 * ().getPassportIdentification().getVisaValidity() !=
						 * null ?
						 * response.getCustomerIdentity().getPassportIdentification
						 * ().getVisaValidity() : ""); if
						 * (response.getCustomerIdentity().getIdentityDetails()
						 * != null &&
						 * response.getCustomerIdentity().getIdentityDetails
						 * ().size() > 0) { for (IdentityDetails
						 * identityDetailsResponse :
						 * response.getCustomerIdentity().getIdentityDetails())
						 * { custumerPojo.setAadhaarNo(identityDetailsResponse.
						 * getNumber() != null ?
						 * identityDetailsResponse.getNumber() : ""); } } else {
						 * custumerPojo.setAadhaarNo(""); }
						 */
					} else {
						custumerPojo.setPanNo("");
						custumerPojo.setVisaNo("");
						custumerPojo.setPassportNo("");
						custumerPojo.setVisaValidityDate("");
					}

				} else {
					custumerPojo.setSalutation("");
					custumerPojo.setFirstName("");
					custumerPojo.setLastName("");
					custumerPojo.setDob("");
					custumerPojo.setGender("");
					custumerPojo.setOccupation("");
					custumerPojo.setMaritalStatus("");
					custumerPojo.setNationality("");
					custumerPojo.setRelationshipType("");
					custumerPojo.setRelFirstName("");
					custumerPojo.setRelLastName("");
					custumerPojo.setAltNoHome("");
					custumerPojo.setMobileNo("");
					custumerPojo.setPrimEmailId("");
				}
				/******************* setAddress details ******************/
				if (response.getAddressList() != null) {
					if (response.getAddressList().getAddressDetails() != null
							&& response.getAddressList().getAddressDetails()
									.size() > 0) {
						for (cusinq.com.ril.rpsl.entities.commontypes_v1_0.TAddress addressResponse : response
								.getAddressList().getAddressDetails()) {
							/**************** Setting Address *******************/
							custumerPojo
									.setAddress(setAddress(addressResponse));
							custumerPojo.setJioCenterId(addressResponse
									.getJioCenterId() != null ? addressResponse
									.getJioCenterId() : "");
						}
					}
				}

				/******************* getAccountDetails Info ******************/
				if (response.getAccountList() != null) {
					if (response.getAccountList().getAccountDetails() != null
							&& response.getAccountList().getAccountDetails()
									.size() > 0) {
						for (AccountDetails accountDetailsResponse : response
								.getAccountList().getAccountDetails()) {
							if (accountDetailsResponse.getAccount() != null) {
								/*************** Setting Account *****************/
								custumerPojo
										.setAccount(setAccount(accountDetailsResponse));
							}
						}

					}
				}
				/*************************** ResponseDetails **************************/
				/****************************** ResponseStatus ******************
				if (response.getResponseDetails() != null) {
					if (response.getResponseDetails().getInfos() != null) {
						for (cusinq.com.ril.rpsl.entities.commontypes_v1_0.TInfo tInfoResponse : response
								.getResponseDetails().getInfos().getInfo()) {
							custumerPojo.setProviderStatus(HeaderStatus
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
					/****************************** MessageContext ******************
					custumerPojo.setHeaderMessage(HeaderStatus.setMessage(
							response.getRsHeader().getMessageContext()
									.getBusinessID(), response.getRsHeader()
									.getMessageContext().getEsbID(), response
									.getRsHeader().getMessageContext()
									.getMessageID(), response.getRsHeader()
									.getMessageContext().getCorrelationID(),
							response.getRsHeader().getMessageContext()
									.getExtCorrelationID(), response
									.getRsHeader().getMessageContext()
									.getTimeStamp()));
				}

				/********************** BusinessApplicationContext *****************
				if (response.getRsHeader().getBusinessApplicationContext() != null) {
					custumerPojo.setBusinessApplication(HeaderStatus
							.setBusinessApplication(response.getRsHeader()
									.getBusinessApplicationContext()
									.getServiceVersion(), response
									.getRsHeader()
									.getBusinessApplicationContext()
									.getOperationVersion(), response
									.getRsHeader()
									.getBusinessApplicationContext()
									.getOperationName(), response.getRsHeader()
									.getBusinessApplicationContext()
									.getMessageType(), response.getRsHeader()
									.getBusinessApplicationContext()
									.getSrcApplicationname()));
				}*/
			}
			return custumerPojo;
		} catch (CustomerInquiryFault e) {
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

	/**
	 * This method is used to set the Address Details
	 * 
	 * @param addressResponse
	 * @return Address
	 */
	private Address setAddress(TAddress addressResponse) {
		Address addressPojo = new Address();
		addressPojo
				.setAddressType(addressResponse.getAddressType() != null ? addressResponse
						.getAddressType() : "");
		
		/*
		 * Changes done by Anand Dated 7-4-2015 Adding new fields House No ,
		 * Building Name,Society Name
		 */
		// Start
		String lstrAddressLine ="";
		if (addressResponse.getAddressLine() != null && addressResponse.getAddressLine().size() > 0) 
		{
			List<String> listaddressLines = addressResponse.getAddressLine();
			for (String line:listaddressLines) 
			{
				lstrAddressLine = lstrAddressLine+" "+ Util.getStringRepresentation(line);
			}
		}

		// End
		addressPojo.setHouseNo(Util.getStringRepresentation(addressResponse.getHouseNo()));
		addressPojo.setBuildingName(Util.getStringRepresentation(addressResponse.getBuildingName()));
		addressPojo.setSocietyName(Util.getStringRepresentation(addressResponse.getSocietyName()));
		addressPojo.setCareOf(addressResponse.getCareOf() != null ? addressResponse.getCareOf() : "");
		addressPojo
				.setLocality(addressResponse.getLocality() != null ? addressResponse
						.getLocality() : "");
		addressPojo
				.setSubLocality(addressResponse.getSubLocality() != null ? addressResponse
						.getSubLocality() : "");
		addressPojo
				.setStreet(addressResponse.getStreetName() != null ? addressResponse
						.getStreetName() : "");
		addressPojo
				.setLandmark(addressResponse.getLandmark() != null ? addressResponse
						.getLandmark() : "");
		addressPojo
				.setPostCode(addressResponse.getPostCode() != null ? addressResponse
						.getPostCode() : "");
		addressPojo.setCity(addressResponse.getCity() != null ? addressResponse
				.getCity() : "");
		addressPojo
				.setDistrict(addressResponse.getDistrict() != null ? addressResponse
						.getDistrict() : "");
		
		if(addressResponse.getState() !=null){
		List<ReferenceData> stateList = LOVUtil.getLov("IN");
		ReferenceData referenceDataPojo = null;
		if (stateList != null && !stateList.isEmpty()  ) 
		{
			stateList = LOVUtil.getLov("IN");
			for(int stateCount=0; stateCount< stateList.size(); stateCount++){
				referenceDataPojo = stateList.get(stateCount);
				if(referenceDataPojo!=null && referenceDataPojo.getCode()!=null && referenceDataPojo.getCode().equalsIgnoreCase(addressResponse.getState())){
					addressPojo.setState(referenceDataPojo.getDescription());
				}
			}
			
		}
		}else{
			addressPojo.setState("");
		}
		
//		addressPojo
//				.setState(addressResponse.getState() != null ? addressResponse
//						.getState() : "");
		addressPojo
				.setCountry(addressResponse.getCountry() != null ? addressResponse
						.getCountry() : "");
		/*
		 * Changes done by Anand Dated 7-4-2015 Adding new fields HouseType
		 */
		// Start
		addressPojo
				.setHouseType(addressResponse.getHouseType() != null ? addressResponse
						.getHouseType() : "");
		// End
		// addressPojo.setAddressType(addressResponse.getAddressType()!=null
		// ? addressResponse.getAddressType() :"");
		// addressPojo.setBuildingName(addressResponse.getBuildingName()!=null
		// ? addressResponse.getBuildingName():"");
		// addressPojo.setSocietyName(addressResponse.getSocietyName()!=null
		// ? addressResponse.getSocietyName() :"");
		// addressPojo.setHouseNo(addressResponse.getHouseType()
		// != null ? addressResponse.getHouseType(): "");
		return addressPojo;

	}

	/**
	 * This method is used to set the Account Details
	 * 
	 * @param accountDetailsResponse
	 * @return Account
	 */
	private Account setAccount(AccountDetails accountDetailsResponse) {
		Account accountPojo = new Account();
		accountPojo.setAccountNumber(accountDetailsResponse.getAccount()
				.getAccountNumber() != null ? accountDetailsResponse
				.getAccount().getAccountNumber() : "");
		accountPojo.setAccountType(accountDetailsResponse.getAccount()
				.getAccountType() != null ? accountDetailsResponse.getAccount()
				.getAccountType() : "");
		accountPojo.setStatus(accountDetailsResponse.getAccount()
				.getAccountStatus() != null ? accountDetailsResponse
				.getAccount().getAccountStatus() : "");
		accountPojo.setCurrency(accountDetailsResponse.getAccount()
				.getCurrency() != null ? accountDetailsResponse.getAccount()
				.getCurrency() : "");
		accountPojo.setAccountBalance(accountDetailsResponse.getAccount()
				.getAccountBalance() != null ? accountDetailsResponse
				.getAccount().getAccountBalance() : "");
		accountPojo.setInstitutionId(accountDetailsResponse.getAccount()
				.getInstitutionId() != null ? accountDetailsResponse
				.getAccount().getInstitutionId() : "");
		return accountPojo;
	}

	@Override
	public Customer displayCustomer(String customerId, String agentId,long requestId)
			throws DataAccessException {
		String lClassName =  this.getClass().getName();
		String lMethodName = "displayCustomer";
		
		soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		try {
			/******************* Setting the Header Request ******************/
			TRqHeader headerRequest = new TRqHeader();
			TMessageContext messageRequest = new TMessageContext();
			messageRequest.setMessageID(String.valueOf(UUID.randomUUID()));
			messageRequest.setCorrelationID(String.valueOf(UUID.randomUUID()));
			headerRequest.setMessageContext(messageRequest);
			TBusinessApplicationContext businessApplicationRequest = new TBusinessApplicationContext();
			businessApplicationRequest.setOperationName(platformProperties
					.getProperty("searchCustomer_OperationName"));
			businessApplicationRequest.setMessageType(platformProperties
					.getProperty("searchCustomer_messageType"));
			businessApplicationRequest.setSrcApplicationname(platformProperties
					.getProperty("searchCustomer_applicationName"));
			headerRequest
					.setBusinessApplicationContext(businessApplicationRequest);
			/******************* Setting the WSDL URL ******************/
			String customerInquiryWsdlUrl = platformProperties
					.getProperty("customerInquiry");

			String endPointURL = platformProperties
					.getProperty("customerInquiryEndPoint");

			URL wsdlUrl = new URL(customerInquiryWsdlUrl);
			// change ajay from 15-April-2015
			CustomerInquiry agent = new CustomerInquiry(wsdlUrl);
			agent.setHandlerResolver(new WSHandlerResolver(agentId,requestId				));
			Operations endPoint = agent.getOperationsEndpoint();
			/******************* Setting the endpoint URL For CustomerInquiry ******************/
			BindingProvider bindingProvider = (BindingProvider) endPoint;
			bindingProvider.getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointURL);
			GetCustomerDetailsRq request = new GetCustomerDetailsRq();
			request.setCustomerId(customerId);
			request.setRqHeader(headerRequest);
			/************************ Response ************************************************/
			GetCustomerDetailsRs response = endPoint
					.getCustomerDetails(request);

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

				} else {
					throw new DataAccessException(
							"Unable to search the customer i.e " + customerId);
				}
			}

			if (response != null
					&& response.getResponseDetails() != null
					&& response.getResponseDetails().getStatus() != null
					&& response.getResponseDetails().getStatus()
							.equalsIgnoreCase("SUCCESS")) {
				if (response.getResponseDetails().getInfos().getInfo() != null
						&& response.getResponseDetails().getInfos().getInfo()
								.size() > 0) {
					for (TInfo getinfoDetails : response.getResponseDetails()
							.getInfos().getInfo()) {
						if ("RPSL_CRM_BUS_NOT_FOUND_001"
								.equalsIgnoreCase(getinfoDetails.getCode())) 
						{
							return null;
						}
					}
				}
			}

			Customer custumerPojo = null;
			if (response != null && response.getCustomerDetails() != null) {
				custumerPojo = new Customer();
				/******************* Account Info ******************/
				/******************* setAddress details ******************/
				custumerPojo
						.setCustId(response.getCustomerId() != null ? response
								.getCustomerId() : "");
				
				if (response.getCustomerDetails() != null) {
					if (response.getCustomerDetails().getAddressDetails() != null
							&& response.getCustomerDetails()
									.getAddressDetails().size() > 0) {
						for (cusinq.com.ril.rpsl.entities.commontypes_v1_0.TAddress addressResponse : response
								.getCustomerDetails().getAddressDetails()) {
							/************** Setting Address ****************/
							custumerPojo
									.setAddress(setAddress(addressResponse));
						}
					} else {
						Address addressPojo = new Address();
						addressPojo.setAddressType("");
						addressPojo.setHouseNo("");
						addressPojo.setStreet("");
						addressPojo.setLocality("");
						addressPojo.setCity("");
						addressPojo.setState("");
						addressPojo.setPostCode("");
						addressPojo.setSocietyName("");
						addressPojo.setBuildingName("");
						custumerPojo.setAddress(addressPojo);
					}
					/******************* getPersonal Details Document Info ******************/
					if (response.getCustomerDetails().getDocuments() != null
							&& response.getCustomerDetails().getDocuments()
									.size() > 0) {
						for (Documents documentDetails : response
								.getCustomerDetails().getDocuments()) {
							Document documentPojo = new Document();
							documentPojo
									.setNumber(documentDetails.getNumber() != null ? documentDetails
											.getNumber() : "");
							documentPojo
									.setType(documentDetails.getType() != null ? documentDetails
											.getType() : "");
							if (documentDetails.getIssuedate() != null) {
								documentPojo
										.setIssuedate(XMLGregorianCalendarUtil
												.getProperDateFormat(
														documentDetails
																.getIssuedate()
																+ "000000")
												.substring(0, 10));
							} else {
								documentPojo.setIssuedate("");
							}
							custumerPojo.setDocument(documentPojo);
						}
					} else {
						Document documentPojo = new Document();
						documentPojo.setNumber("");
						documentPojo.setType("");
						documentPojo.setIssuedate("");
						custumerPojo.setDocument(documentPojo);
					}
					/******************* getPersonal Details Info ******************/
					if (response.getCustomerDetails().getEmailDetails() != null) {
						custumerPojo.setPrimEmailId(response
								.getCustomerDetails().getEmailDetails()
								.getPrimEmailId() != null ? response
								.getCustomerDetails().getEmailDetails()
								.getPrimEmailId() : "");
					} else {
						custumerPojo.setPrimEmailId("");
					}
					if (response.getCustomerDetails().getBankAccountDetails() != null) {
						/******************* getBank Details Info ******************/
						Bank bankPojo = new Bank();
						bankPojo.setBankId(response.getCustomerDetails()
								.getBankAccountDetails().getBankId() != null ? response
								.getCustomerDetails().getBankAccountDetails()
								.getBankId()
								: "");
						bankPojo.setBranchCode(response.getCustomerDetails()
								.getBankAccountDetails().getBranchCode() != null ? response
								.getCustomerDetails().getBankAccountDetails()
								.getBranchCode()
								: "");
						custumerPojo
								.setHasnominee(response.getCustomerDetails()
										.getBankAccountDetails().getTNominee() != null ? response
										.getCustomerDetails()
										.getBankAccountDetails().getTNominee()
										.getHasNominee() != null ? response
										.getCustomerDetails()
										.getBankAccountDetails().getTNominee()
										.getHasNominee() : ""
										: "");
						custumerPojo
								.setNomineelegalname(response
										.getCustomerDetails()
										.getBankAccountDetails().getTNominee() != null ? response
										.getCustomerDetails()
										.getBankAccountDetails().getTNominee()
										.getLegalName() != null ? response
										.getCustomerDetails()
										.getBankAccountDetails().getTNominee()
										.getLegalName() : ""
										: "");
						custumerPojo
								.setNomineerelationship(response
										.getCustomerDetails()
										.getBankAccountDetails().getTNominee() != null ? response
										.getCustomerDetails()
										.getBankAccountDetails().getTNominee()
										.getRelationship() != null ? response
										.getCustomerDetails()
										.getBankAccountDetails().getTNominee()
										.getRelationship() : ""
										: "");
						custumerPojo
								.setNomineeAge(response.getCustomerDetails()
										.getBankAccountDetails().getTNominee() != null ? response
										.getCustomerDetails()
										.getBankAccountDetails().getTNominee()
										.getAge().toString() != null ? String
										.valueOf(response.getCustomerDetails()
												.getBankAccountDetails()
												.getTNominee().getAge()) : ""
										: "");

						Account accountPojo = new Account();
						accountPojo.setAccountType(response
								.getCustomerDetails().getBankAccountDetails()
								.getAccountType() != null ? response
								.getCustomerDetails().getBankAccountDetails()
								.getAccountType() : "");
						accountPojo
								.setBranchName(response.getCustomerDetails()
										.getBankAccountDetails()
										.getBranchName() != null ? response
										.getCustomerDetails()
										.getBankAccountDetails()
										.getBranchName() : "");
						custumerPojo.setAccount(accountPojo);
						custumerPojo.setBank(bankPojo);

						/******************* getGuardianDetails Details Info ******************/
						if (response.getCustomerDetails()
								.getCustomerPreferences() != null) {
							custumerPojo
									.setPreflanguage(response
											.getCustomerDetails()
											.getCustomerPreferences()
											.getPrefLang() != null ? response
											.getCustomerDetails()
											.getCustomerPreferences()
											.getPrefLang() : "");
							custumerPojo
									.setPrefCommChannel(response
											.getCustomerDetails()
											.getCustomerPreferences()
											.getPrefComChnl() != null ? response
											.getCustomerDetails()
											.getCustomerPreferences()
											.getPrefComChnl()
											: "");
						} else {
							custumerPojo.setPreflanguage("");
							custumerPojo.setPrefCommChannel("");
						}
						if (response.getCustomerDetails().getPhoneDetails() != null) {
							custumerPojo.setMobileNo(response
									.getCustomerDetails().getPhoneDetails()
									.getPrimMobNum() != null ? response
									.getCustomerDetails().getPhoneDetails()
									.getPrimMobNum() : "");
						} else {
							custumerPojo.setMobileNo("");
						}
						if (response.getCustomerDetails() != null) {
							if (response.getCustomerDetails().getCustomer() != null) {
								/******************* getCustomerDetails Details Info ******************/
								if (response.getCustomerDetails().getCustomer()
										.getDob() != null) {
									if ("00000000".equalsIgnoreCase(response
											.getCustomerDetails().getCustomer()
											.getDob())) {
										custumerPojo.setDob("");
									} else {
										custumerPojo
												.setDob(response
														.getCustomerDetails()
														.getCustomer().getDob() != null ? XMLGregorianCalendarUtil
														.convertDateFormat(
																response.getCustomerDetails()
																		.getCustomer()
																		.getDob(),
																"yyyyMMdd",
																"dd/MM/yyyy")
														: "");
									}
								} else {
									custumerPojo.setDob("");
								}
								custumerPojo
										.setDob(response.getCustomerDetails()
												.getCustomer().getDob() != null ? XMLGregorianCalendarUtil
												.convertDateFormat(
														response.getCustomerDetails()
																.getCustomer()
																.getDob(),
														"yyyyMMdd",
														"dd/MM/yyyy")
												: "");
								
								if(response
										.getCustomerDetails().getCustomer()
										.getGender()!= null){
								if(response
										.getCustomerDetails().getCustomer()
										.getGender().equals("2")  ){
									custumerPojo.setGender("Male");
								}else if(response
										.getCustomerDetails().getCustomer()
										.getGender().equals("1")  ){
									custumerPojo.setGender("Female");
								}else{
									custumerPojo.setGender(response
											.getCustomerDetails().getCustomer()
											.getGender());
								}
								}
								else{
									custumerPojo.setGender("");
								}
//								custumerPojo.setGender(response
//										.getCustomerDetails().getCustomer()
//										.getGender() != null ? response
//										.getCustomerDetails().getCustomer()
//										.getGender() : "");
								custumerPojo.setMaritalStatus(response
										.getCustomerDetails().getCustomer()
										.getMaritalStatus() != null ? response
										.getCustomerDetails().getCustomer()
										.getMaritalStatus() : "");
								custumerPojo.setNationality(response
										.getCustomerDetails().getCustomer()
										.getNationality() != null ? response
										.getCustomerDetails().getCustomer()
										.getNationality() : "");
								custumerPojo.setPlaceOfBirth(response
										.getCustomerDetails().getCustomer()
										.getPlaceOfBirth() != null ? response
										.getCustomerDetails().getCustomer()
										.getPlaceOfBirth() : "");
								custumerPojo.setStatus(response
										.getCustomerDetails().getCustomer()
										.getStatus() != null ? response
										.getCustomerDetails().getCustomer()
										.getStatus() : "");
								if (response.getCustomerDetails().getCustomer()
										.getRelation() != null) {
									if (response.getCustomerDetails()
											.getCustomer().getRelation()
											.getName() != null) {
										custumerPojo
												.setRelSalutation(response
														.getCustomerDetails()
														.getCustomer()
														.getRelation()
														.getName()
														.getSalutation() != null ? response
														.getCustomerDetails()
														.getCustomer()
														.getRelation()
														.getName()
														.getSalutation()
														: "");
										custumerPojo
												.setRelFirstName(response
														.getCustomerDetails()
														.getCustomer()
														.getRelation()
														.getName()
														.getFirstName() != null ? response
														.getCustomerDetails()
														.getCustomer()
														.getRelation()
														.getName()
														.getFirstName()
														: "");
										custumerPojo
												.setRelLastName(response
														.getCustomerDetails()
														.getCustomer()
														.getRelation()
														.getName()
														.getLastName() != null ? response
														.getCustomerDetails()
														.getCustomer()
														.getRelation()
														.getName()
														.getLastName()
														: "");
										custumerPojo
												.setRelMiddleName(response
														.getCustomerDetails()
														.getCustomer()
														.getRelation()
														.getName()
														.getMiddleName() != null ? response
														.getCustomerDetails()
														.getCustomer()
														.getRelation()
														.getName()
														.getMiddleName()
														: "");
										custumerPojo
												.setRelLegalName(response
														.getCustomerDetails()
														.getCustomer()
														.getRelation()
														.getName()
														.getLegalName() != null ? response
														.getCustomerDetails()
														.getCustomer()
														.getRelation()
														.getName()
														.getLegalName()
														: "");
									} else {
										custumerPojo.setRelSalutation("");
										custumerPojo.setRelFirstName("");
										custumerPojo.setRelLastName("");
										custumerPojo.setRelMiddleName("");
										custumerPojo.setRelLegalName("");
									}
								}
								if (response.getCustomerDetails().getCustomer()
										.getName() != null) {
									custumerPojo
											.setFirstName(response
													.getCustomerDetails()
													.getCustomer().getName()
													.getFirstName() != null ? response
													.getCustomerDetails()
													.getCustomer().getName()
													.getFirstName()
													: "");
									custumerPojo
											.setMiddleName(response
													.getCustomerDetails()
													.getCustomer().getName()
													.getMiddleName() != null ? response
													.getCustomerDetails()
													.getCustomer().getName()
													.getMiddleName()
													: "");
									custumerPojo
											.setLastName(response
													.getCustomerDetails()
													.getCustomer().getName()
													.getLastName() != null ? response
													.getCustomerDetails()
													.getCustomer().getName()
													.getLastName()
													: "");
									custumerPojo
											.setLegalName(response
													.getCustomerDetails()
													.getCustomer().getName()
													.getLegalName() != null ? response
													.getCustomerDetails()
													.getCustomer().getName()
													.getLegalName()
													: "");
									custumerPojo
											.setSalutation(response
													.getCustomerDetails()
													.getCustomer().getName()
													.getSalutation() != null ? response
													.getCustomerDetails()
													.getCustomer().getName()
													.getSalutation()
													: "");
								} else {
									custumerPojo.setFirstName("");
									custumerPojo.setMiddleName("");
									custumerPojo.setLastName("");
									custumerPojo.setLegalName("");
									custumerPojo.setSalutation("");
								}
								if (response.getCustomerDetails()
										.getDealerDetails() != null) {
									custumerPojo
											.setAgentId(response
													.getCustomerDetails()
													.getDealerDetails()
													.getAgentCode() != null ? response
													.getCustomerDetails()
													.getDealerDetails()
													.getAgentCode()
													: "");

									if (response.getCustomerDetails()
											.getDealerDetails().getName() != null) {
										custumerPojo.setAgentName(response
												.getCustomerDetails()
												.getDealerDetails().getName()
												.getLegalName());
									} else {
										custumerPojo.setAgentName("");
									}
								} else {
									custumerPojo.setAgentId("");

								}
							} else {

								custumerPojo.setDob("");
								custumerPojo.setGender("");
								custumerPojo.setMaritalStatus("");
								custumerPojo.setPlaceOfBirth("");
							}
						}
					}
					
					
					TMiscEntities lMiscEntity   = response.getCustomerDetails().getMiscEntities();
					if(lMiscEntity != null && lMiscEntity.getNameValue() != null && lMiscEntity.getNameValue().isEmpty() == false)
					{
						List<TNameValue> lNameValues = lMiscEntity.getNameValue();
						for (TNameValue lTNameValue : lNameValues) 
						{
							if(lTNameValue != null && "STATUS_REASON".equalsIgnoreCase(lTNameValue.getName()))
							{
								Customer.getCustomerStatusReson();
								custumerPojo.setStatusReasonCode(Util.isEmptyString(lTNameValue.getValue())== false ? Customer.getStatusReasonCode(lTNameValue.getValue()) :"");
							}
							if(lTNameValue != null && "STATUS_DATE".equalsIgnoreCase(lTNameValue.getName()))
							{
								custumerPojo.setStatusUpdateDate(Util.isEmptyString(lTNameValue.getValue()) == false ? XMLGregorianCalendarUtil
										.convertDateFormat(
												lTNameValue.getValue(),
									"yyyyMMdd",
									"dd/MM/yyyy")
									
									: "");
							}
							if(lTNameValue != null && "BPROLE".equalsIgnoreCase(lTNameValue.getName()) && "BUP002".equals(lTNameValue.getValue()))
							{
								custumerPojo.setProspect("true");
							}
						}
					}
					
					
					TMiscEntities lEntities = response.getMiscEntities();
					if(lEntities != null && lEntities.getNameValue() != null && lEntities.getNameValue().isEmpty() == false)
					{
						List<TNameValue> lNameValues = lEntities.getNameValue();
						for (TNameValue lTNameValue : lNameValues) 
						{
							if(lTNameValue != null && "Account_Status".equalsIgnoreCase(lTNameValue.getName()))
							{
								custumerPojo.setStatus(lTNameValue.getValue());
							}
							
						}
					}
				}

				/*************************** ResponseDetails **************************/
				/****************************** ResponseStatus ******************
				if (response.getResponseDetails() != null) {
					if (response.getResponseDetails().getInfos() != null) {
						for (cusinq.com.ril.rpsl.entities.commontypes_v1_0.TInfo tInfoResponse : response
								.getResponseDetails().getInfos().getInfo()) {
							custumerPojo.setProviderStatus(HeaderStatus
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
					custumerPojo.setHeaderMessage(HeaderStatus.setMessage(
							response.getRsHeader().getMessageContext()
									.getBusinessID(), response.getRsHeader()
									.getMessageContext().getEsbID(), response
									.getRsHeader().getMessageContext()
									.getMessageID(), response.getRsHeader()
									.getMessageContext().getCorrelationID(),
							response.getRsHeader().getMessageContext()
									.getExtCorrelationID(), response
									.getRsHeader().getMessageContext()
									.getTimeStamp()));
				}

				/********************** BusinessApplicationContext *******************

				if (response.getRsHeader().getBusinessApplicationContext() != null) {
					custumerPojo.setBusinessApplication(HeaderStatus
							.setBusinessApplication(response.getRsHeader()
									.getBusinessApplicationContext()
									.getServiceVersion(), response
									.getRsHeader()
									.getBusinessApplicationContext()
									.getOperationVersion(), response
									.getRsHeader()
									.getBusinessApplicationContext()
									.getOperationName(), response.getRsHeader()
									.getBusinessApplicationContext()
									.getMessageType(), response.getRsHeader()
									.getBusinessApplicationContext()
									.getSrcApplicationname()));
				}*/

			}
			return custumerPojo;
		} catch (CustomerInquiryFault e) {
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

	public List<Customer> searchCustomer(String mobileNo, String emailAddress,
			String panNumber, String firstName, String lastName,
			String dateofBirth, String customerId, String agentId,long requestId,boolean prospectSearch)
			throws DataAccessException, ServiceException 
	{
		String lClassName =  this.getClass().getName();
		String lMethodName = "searchCustomer";
		
		soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		try 
		{
			/******************* Setting the Header Request ******************/
			TRqHeader headerRequest = new TRqHeader();
			TMessageContext messageRequest = new TMessageContext();
			messageRequest.setMessageID(String.valueOf(UUID.randomUUID()));
			messageRequest.setCorrelationID(String.valueOf(UUID.randomUUID()));
			headerRequest.setMessageContext(messageRequest);
			TBusinessApplicationContext businessApplicationRequest = new TBusinessApplicationContext();
			businessApplicationRequest = new TBusinessApplicationContext();
			// businessApplicationRequest.setOperationName(properties
			// .getProperty("searchCustomer_OperationName"));
			businessApplicationRequest.setMessageType(platformProperties
					.getProperty("searchCustomer_messageType"));
			businessApplicationRequest.setSrcApplicationname(platformProperties
					.getProperty("searchCustomer_applicationName"));
			headerRequest
					.setBusinessApplicationContext(businessApplicationRequest);
			/**************************************************************/

			SearchCustomerRq request = new SearchCustomerRq();
			request.setRqHeader(headerRequest);
			if (emailAddress != null && !emailAddress.isEmpty()) 
			{
				TEmail tEmailRequest = new TEmail();
				tEmailRequest.setPrimEmailId(emailAddress);
				request.setEmailDetails(tEmailRequest);
			}
			TNameDetails tNameDetailsRequest = new TNameDetails();
			if (firstName != null && !firstName.isEmpty()) 
			{
				tNameDetailsRequest.setFirstName(firstName);
			}
			if (lastName != null && !lastName.isEmpty()) 
			{
				tNameDetailsRequest.setLastName(lastName);
			}

			// customerName is not coming
			request.setCustomerName(tNameDetailsRequest);
			if (mobileNo != null && !mobileNo.isEmpty()) 
			{
				TPhone tPhoneRequest = new TPhone();
				tPhoneRequest.setPrimMobNum(mobileNo);
				request.setPhoneDetails(tPhoneRequest);
			}

			
			if (dateofBirth != null && !dateofBirth.isEmpty()) {
				// XMLGregorianCalendarUtil.setTime(true);
				//criteriaRequest = new FilterCriteria();
				request.setDob(XMLGregorianCalendarUtil
						.convertDateFormat(dateofBirth, "dd/MM/yyyy", "yyyyMMdd"));
			}
			
//			if (dateofBirth != null && !dateofBirth.isEmpty()) 
//			{
//				request.setDob(dateofBirth);
//			}
			if (panNumber != null && !panNumber.isEmpty()) 
			{
				request.setPanNum(panNumber);
			}
			
			//Setting IV_BPROLE for prospect search
			if(prospectSearch){
			TNameValue tNameValue = new TNameValue();
			tNameValue.setName("BPROLE");
			tNameValue.setValue("BUP002");
			
			TMiscEntities lEntitiesRequest = new TMiscEntities();
			lEntitiesRequest.getNameValue();
			lEntitiesRequest.getNameValue().add(tNameValue);
			request.setMiscEntities(lEntitiesRequest);
			}
			
			TDataRecord dataRecordRequest = new TDataRecord();
			dataRecordRequest.setPagingSize(platformProperties
					.getProperty("maxRecord"));
			dataRecordRequest.setOffsetValue(platformProperties
					.getProperty("pageOffSet"));
			request.setPageData(dataRecordRequest);
			/******************* Setting the WSDL URL ******************/
			String customerInquiryWsdlUrl = platformProperties
					.getProperty("customerInquiry");

			String endPointURL = platformProperties
					.getProperty("customerInquiryEndPoint");
			URL wsdlUrl = new URL(customerInquiryWsdlUrl);
			// change ajay from 15-April-2015
			CustomerInquiry agent = new CustomerInquiry(wsdlUrl);
			agent.setHandlerResolver(new WSHandlerResolver(agentId,requestId	));
			Operations endPoint = agent.getOperationsEndpoint();
			/******************* Setting the endpoint URL For CustomerInquiry ******************/
			BindingProvider bindingProvider = (BindingProvider) endPoint;
			bindingProvider.getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointURL);
			/************************ Response ************************************************/
			SearchCustomerRs response = endPoint.searchCustomer(request);
			List<Customer> searchCustomerList = null;
			if (response != null) {
				if (response.getResponseDetails() != null
						&& response.getResponseDetails().getStatus() != null
						&& response.getResponseDetails().getStatus()
								.equalsIgnoreCase("FAILED")) {

					if (response.getResponseDetails().getErrors() != null
							&& response.getResponseDetails().getErrors()
									.getError() != null
							&& response.getResponseDetails().getErrors()
									.getError().size() > 0
							&& response.getResponseDetails().getErrors()
									.getError().get(0).getErrorMsg() != null) {
						throw new DataAccessException(response
								.getResponseDetails().getErrors().getError()
								.get(0).getErrorMsg());
					}

				}

				if (response.getCustomerDetails() != null
						&& response.getCustomerDetails().size() > 0) {
					searchCustomerList = new ArrayList<Customer>();
					for (CustomerDetails customer : response
							.getCustomerDetails()) {
						Customer pojo = new Customer();
						if (customer.getCustomer() != null) {
							if (customer.getCustomer().getName() != null) {
								pojo.setFirstName(customer.getCustomer()
										.getName().getFirstName() != null ? customer
										.getCustomer().getName().getFirstName()
										: "");
								pojo.setLastName(customer.getCustomer()
										.getName().getLastName() != null ? customer
										.getCustomer().getName().getLastName()
										: "");
							} else {
								pojo.setFirstName("");
								pojo.setLastName("");
							}
							pojo.setCustId(customer.getCustomer().getEntityId() != null ? customer
									.getCustomer().getEntityId() : "");
							pojo.setStatus(customer.getCustomer().getStatus() != null ? customer
									.getCustomer().getStatus() : "");
						}

						if (customer.getEmailDetails() != null) {
							pojo.setPrimEmailId(customer.getEmailDetails()
									.getPrimEmailId() != null ? customer
									.getEmailDetails().getPrimEmailId() : "");
						} else {
							pojo.setPrimEmailId("");
						}

						if (customer.getPhoneDetails() != null) {
							pojo.setMobileNo(customer.getPhoneDetails()
									.getPrimMobNum() != null ? customer
									.getPhoneDetails().getPrimMobNum() : "");

						} else {
							pojo.setMobileNo("");
						}

						/*if (response.getResponseDetails() != null) {
							if (response.getResponseDetails().getInfos() != null) {
								for (cusinq.com.ril.rpsl.entities.commontypes_v1_0.TInfo tInfoResponse : response
										.getResponseDetails().getInfos()
										.getInfo()) {
									pojo.setProviderStatus(HeaderStatus
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
							/****************************** MessageContext *****************
							pojo.setHeaderMessage(HeaderStatus.setMessage(
									response.getRsHeader().getMessageContext()
											.getBusinessID(), response
											.getRsHeader().getMessageContext()
											.getEsbID(),
									response.getRsHeader().getMessageContext()
											.getMessageID(), response
											.getRsHeader().getMessageContext()
											.getCorrelationID(), response
											.getRsHeader().getMessageContext()
											.getExtCorrelationID(), response
											.getRsHeader().getMessageContext()
											.getTimeStamp()));
						}

						/********************** BusinessApplicationContext *******************
						if (response.getRsHeader()
								.getBusinessApplicationContext() != null) {
							pojo.setBusinessApplication(HeaderStatus
									.setBusinessApplication(response
											.getRsHeader()
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
						}*/
						searchCustomerList.add(pojo);
					}
				}
			}
			return searchCustomerList;
		} catch (CustomerInquiryFault e) {
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
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]",t);
			logger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			throw new DataAccessException(t.getMessage(), t);
		}
		finally
		{
			soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
			
		}
	}
		}
