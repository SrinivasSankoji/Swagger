package com.isuite.rjil.iagent.jiomoney.dao.impl;

import java.net.URL;
import java.util.Properties;
import java.util.UUID;

import javax.xml.ws.BindingProvider;

import org.apache.log4j.Logger;

import aacm.com.ril.rpsl.entities.commontypes_v1_0.TAddress;
import aacm.com.ril.rpsl.entities.commontypes_v1_0.TEmail;
import aacm.com.ril.rpsl.entities.commontypes_v1_0.TNameDetails;
import aacm.com.ril.rpsl.entities.commontypes_v1_0.TPhone;
import aacm.com.ril.rpsl.entities.header_v1_0.TBusinessApplicationContext;
import aacm.com.ril.rpsl.entities.header_v1_0.TMessageContext;
import aacm.com.ril.rpsl.entities.header_v1_0.TRqHeader;
import aacm.com.ril.rpsl.entities.individual_v1_0.TIndividual;
import aacm.com.ril.rpsl.entities.orderheader_v1_0.TOrderHeader;
import aacm.com.ril.rpsl.services.agentassistedcustomermanagement_v1_0.CustomerDetails;
import aacm.com.ril.rpsl.services.agentassistedcustomermanagement_v1_0.SubmitCustomerOrderRq;
import aacm.com.ril.rpsl.services.agentassistedcustomermanagement_v1_0.SubmitCustomerOrderRs;
import aacm.com.ril.rpsl.wsdls.agentassistedcustomermanagement_v1_0.AgentAssistedCustomerManagementFault;
import aacm.com.ril.rpsl.wsdls.agentassistedcustomermanagement_v1_0.AgentAssistedCustomerManagementV10Serviceagent;
import aacm.com.ril.rpsl.wsdls.agentassistedcustomermanagement_v1_0.Operations;

import com.isuite.rjil.iagent.jiomoney.common.Customer;
import com.isuite.rjil.iagent.jiomoney.dao.AgentAssistedCustomermanagementDao;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;
import com.isuite.rjil.iagent.jiomoney.util.PropertiesUtil;
import com.isuite.rjil.iagent.jiomoney.util.WSHandlerResolver;
import com.isuite.rjil.iagent.jiomoney.util.XMLGregorianCalendarUtil;

/**
 * Title: AgentAssistedCustomermanagementDaoImpl.java <br>
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
public class AgentAssistedCustomermanagementDaoImpl implements
		AgentAssistedCustomermanagementDao {
	private static Logger logger = Logger
			.getLogger(AgentAssistedCustomermanagementDaoImpl.class);
	private static final Logger soapLogger = Logger
			.getLogger(LOVUtil.SOAPLOGGER);
	private Properties platformProperties = PropertiesUtil
			.getProperties(PropertiesUtil.platform);

	@Override
	public Customer submitCustomerOrder(String orderRefNum, String orderAction,
			String orderCategory, String orderType, String salutation,
			String firstName, String middleName, String lastname, String dob,
			String gender, String nationality, String status,
			String maritalStatus, String relationshipType, String relFirstName,
			String relMiddleName, String relLastName, String primMobNum,
			String secMobNum, String primEmailId, String addressType,
			String city, String postCode, String houseNo, String streetName,
			String locality, String country, String state,
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
			String number, String issuedate, String placeOfIssue, String agentId,long requestId)
			throws DataAccessException {
		String lClassName =  this.getClass().getName();
		String lMethodName = "submitCustomerOrder";
		
		soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		try {
			

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
					.getProperty("submitCustomerOrder_messageType"));
			businessApplicationRequest.setSrcApplicationname(platformProperties
					.getProperty("submitCustomerOrder_applicationName"));
			headerRequest
					.setBusinessApplicationContext(businessApplicationRequest);

			/*
			 * String agentAssistedCustomerManagementWsdlUrl =
			 * platformProperties
			 * .getProperty("agentAssistedCustomerManagement"); URL wsdlUrl =
			 * new URL(agentAssistedCustomerManagementWsdlUrl);
			 */
			/*
			 * AgentAssistedCustomerManagementV10Serviceagent serviceagent = new
			 * AgentAssistedCustomerManagementV10Serviceagent( wsdlUrl);
			 * serviceagent.setHandlerResolver(new WSHandlerResolver(agentId			 * XMLGregorianCalendarUtil.getSystemTime())); endPoint =
			 * serviceagent.getOperationsEndpoint();
			 */

			/***************** Order Header *****************************/
			TOrderHeader orderHeaderRequest = new TOrderHeader();
			orderHeaderRequest.setOrderRefNum(orderRefNum);
			orderHeaderRequest.setOrderAction(orderAction);
			orderHeaderRequest.setOrderCategory(orderCategory);
			orderHeaderRequest.setOrderType(orderType);
			/*
			 * Added by Anand on 29-04-2015 new fields are added by TIBCO Adding
			 * new field srcChannel
			 */
			// Start
			orderHeaderRequest.setSrcChannel(platformProperties
					.getProperty("submitCustomerOrder_srcChannel"));
			// End
			/******************** Set Customer Basic Details ************************/

			TIndividual customerPersonalDetailsReq = new TIndividual();
			customerPersonalDetailsReq.setDob(XMLGregorianCalendarUtil
					.convertDateFormat(dob, "dd/MM/yyyy", "yyyyMMdd"));
			// customerPersonalDetailsReq.setDob(converDateFormat(dob));
			customerPersonalDetailsReq.setGender(gender);
			// customerPersonalDetailsReq.setStatus(status);
			// customerPersonalDetailsReq.setMaritalStatus(maritalStatus);
			if (nationality != null && !nationality.isEmpty()) {
				customerPersonalDetailsReq.setNationality(nationality);
			}
			/***************** Setting Customer name ********/
			TNameDetails customerNameDetailsRequest = new TNameDetails();
			customerNameDetailsRequest.setSalutation(salutation);
			customerNameDetailsRequest.setFirstName(firstName);
			customerNameDetailsRequest.setMiddleName(middleName);
			customerNameDetailsRequest.setLastName(lastname);
			customerPersonalDetailsReq.setName(customerNameDetailsRequest);

			CustomerDetails customerDetailsRequest = new CustomerDetails();
			customerDetailsRequest.setCustomer(customerPersonalDetailsReq);
			/******************** Set Customer Contact Details ************************/
			TPhone phoneNoRequest = new TPhone();
			phoneNoRequest.setPrimMobNum(primMobNum);
			// if (secMobNum != null && !secMobNum.isEmpty()) {
			// // phoneNoRequest.setSecMobNum(secMobNum);
			// }
			customerDetailsRequest.setPhoneDetails(phoneNoRequest);
			/******************** Setting E-mail Id *****************************/
			TEmail emailIdRequest = new TEmail();
			emailIdRequest.setPrimEmailId(primEmailId);
			customerDetailsRequest.setEmailDetails(emailIdRequest);

			/*********** Setting Customer Address Details *********************/
			TAddress addressRequest = new TAddress();
			addressRequest.setAddressType(addressType);
			addressRequest.setCity(city);
			addressRequest.setPostCode(postCode);
			addressRequest.setHouseNo(houseNo);
			addressRequest.setSocietyName(streetName);
			addressRequest.setLocality(locality);
			// addressRequest.setCountry(country);
			addressRequest.setState(state);
			customerDetailsRequest.getAddressDetails().add(addressRequest);

			SubmitCustomerOrderRq request = new SubmitCustomerOrderRq();
			// request.setBankAccountDetails(bankAccountDetails);
			// request.setTaxPayee(tTaxPayeeInformation);
			// request.setDealerDetails(tSalesChannel);
			request.setCustomerDetails(customerDetailsRequest);
			request.setOrderHeader(orderHeaderRequest);
			request.setRqHeader(headerRequest);

			// individualRequest.setName(nameDetailsRequest);
			/******************* Setting the WSDL URL ******************/
			String agentAssistedCustomerManagementWsdlUrl = platformProperties
					.getProperty("agentAssistedCustomerManagement");
			URL wsdlUrl = new URL(agentAssistedCustomerManagementWsdlUrl);
			AgentAssistedCustomerManagementV10Serviceagent serviceagent = new AgentAssistedCustomerManagementV10Serviceagent(
					wsdlUrl);
			serviceagent.setHandlerResolver(new WSHandlerResolver(agentId,requestId));
			Operations endPoint = serviceagent.getOperationsEndpoint();
			/******************* Setting the endpoint URL For AgentAssistedCustomermanagement ******************/
			String endPointURL = platformProperties
					.getProperty("agentAssistedCustomerManagementEndPoint");
			BindingProvider bindingProvider = (BindingProvider) endPoint;
			bindingProvider.getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointURL);
			/********************************** Response ****************************/
			SubmitCustomerOrderRs response = endPoint
					.submitCustomerOrder(request);

			if (response != null
					&& response.getResponseStatus() != null
					&& response.getResponseStatus().getStatus() != null
					&& response.getResponseStatus().getStatus()
							.equalsIgnoreCase("FAILED")) {
				if (response.getResponseStatus().getErrors() != null
						&& response.getResponseStatus().getErrors().getError() != null
						&& response.getResponseStatus().getErrors().getError()
								.size() > 0
						&& response.getResponseStatus().getErrors().getError()
								.get(0).getErrorMsg() != null) {
					throw new DataAccessException(response.getResponseStatus()
							.getErrors().getError().get(0).getErrorMsg());
				}
			}

			Customer customerPojo = null;
			if (response != null) {
				customerPojo = new Customer();
				customerPojo
						.setOrderRefNum(response.getOrderRefNum() != null ? response
								.getOrderRefNum() : "");

			}
			/****************************** ResponseStatus *******

			if (response != null && response.getResponseStatus() != null) {
				if (response.getResponseStatus().getInfos() != null) {
					for (aacm.com.ril.rpsl.entities.commontypes_v1_0.TInfo tinfo : response
							.getResponseStatus().getInfos().getInfo()) {
						customerPojo.setProviderStatus(HeaderStatus.setStatus(
								tinfo.getCode(), tinfo.getMsg(), tinfo
										.getProvider().getProviderCode(), tinfo
										.getProvider().getProviderName()));
					}
				}
				if (response.getRsHeader() != null) {
					customerPojo.setHeaderMessage(HeaderStatus.setMessage(
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
				
				if (response.getRsHeader().getBusinessApplicationContext() != null) {
					customerPojo.setBusinessApplication(HeaderStatus
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
				}
			}*/
			return customerPojo;
		} catch (AgentAssistedCustomerManagementFault e) {
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
}
