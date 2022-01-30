package com.isuite.rjil.iagent.jiomoney.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.isuite.rjil.iagent.jiomoney.util.*;

/**
 * 
 * @author Novelvox
 * 
 */
public class Customer implements Serializable {
	
	/**
	 * 
	 */
	//Start: Showing Status reason code
	private static Map<String, String> customerStatusReson = null;
	public static Map getCustomerStatusReson()
	{
		if(customerStatusReson == null)
		{
			customerStatusReson = new HashMap<String, String>();
			
			Properties jioMoneyUtilProperty=PropertiesUtil.getProperties(PropertiesUtil.jiomoneyutil);
			String str=jioMoneyUtilProperty.getProperty("CONSUMER_STATUS_REASON_CODE");
			
			if(Util.isEmptyString(str) == false)
	         {
	        	 String[] lstrStatus = str.split("\\|\\|");
	        	 if(lstrStatus != null && lstrStatus.length >0)
	        	 {
	        		for (String lstrTemp : lstrStatus) 
	        		{
						String[] lstrKeyVal = lstrTemp.split("\\|");
						if(lstrKeyVal!=null && lstrKeyVal.length == 2)
						{
							customerStatusReson.put(lstrKeyVal[0], lstrKeyVal[1]);
						}
					} 
	        	 }
	         }
			
		}
		return customerStatusReson;
	}
	
	public static String getStatusReasonCode(String pStatusKey)
	{
		return customerStatusReson.get(pStatusKey);
	}
	//End: Showing Status reason code
	
	private static final long serialVersionUID = -5655672690836550367L;

	private String custId;
	private String salutation;
	private String firstName;
	private String middleName;
	private String lastName;
	private String maritalStatus;
	private String occupation;
	private String mobileNo;
	private String primEmailId;
	private String dob;
	private String gender;
	private String visaValidityDate;
	private String visaNo;
	private String initiatedBy;
	private String nationality;
	private String partner;
	private String title;
	private String status;
	
	private String blackListFlag;
	private String category;
	private String isvip;
	private String imageURL;
	private String occupationDesc;
	private String placeOfBirth;
	private String legalName;
	private String secEmailId;
	private String secMobNum;
	private String faxNo;
	/******************* Father/ Husband's Name ******************/
	private String relSalutation;
	private String relLegalName;
	private String relFirstName;
	private String relMiddleName;
	private String relLastName;
	private String altNoHome;
	private String altNoWork;
	private String relationshipType;
	
	/****Gaurdian Details************/
	private String gaurdianFirstName;
	private String gaurdianLastName;
	private String gaurdianAge;
	private String gaurdianAddress;
	/******************* Other information ******************/

	private String prefCommChannel;
	private String preflanguage;
	private String aadhaarNo;
	private String panNo;
	private String anniversaryDate;
	private String passportNo;

	private String jioCenterId;

	private Account account;
	private Bank bank;
	private Document document;
	private Address address;
	private Status providerStatus;
	private Message headerMessage;
	private BusinessApplication businessApplication;
	private List<CardDetails> cardDetails;
	
	private String agentId;
	private String agentName;
	private String nomineelegalname;
	private String nomineerelationship;
	private String nomineeAge;
	private String hasnominee;
	
	private String statusReasonCode;
	private String statusUpdateDate;
	private String mstrProspect = "false";
	
	private NomineeDetails nomineeDetails;
	
	public NomineeDetails getNomineeDetails() {
		return nomineeDetails;
	}

	public void setNomineeDetails(NomineeDetails nomineeDetails) {
		this.nomineeDetails = nomineeDetails;
	}

	public String getProspect() {
		return mstrProspect;
	}

	public void setProspect(String prospect) {
		this.mstrProspect = prospect;
	}
	
	
	public String getStatusReasonCode() {
		return statusReasonCode;
	}

	public void setStatusReasonCode(String statusReasonCode) {
		this.statusReasonCode = statusReasonCode;
	}

	public String getStatusUpdateDate() {
		return statusUpdateDate;
	}

	public void setStatusUpdateDate(String statusUpdateDate) {
		this.statusUpdateDate = statusUpdateDate;
	}

	/**********************For Order Ref Num****************************/
	private String orderRefNum;
	
	public String getOrderRefNum() {
		return orderRefNum;
	}

	public void setOrderRefNum(String orderRefNum) {
		this.orderRefNum = orderRefNum;
	}

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getGaurdianFirstName() {
		return gaurdianFirstName;
	}

	public void setGaurdianFirstName(String gaurdianFirstName) {
		this.gaurdianFirstName = gaurdianFirstName;
	}

	public String getGaurdianLastName() {
		return gaurdianLastName;
	}

	public void setGaurdianLastName(String gaurdianLastName) {
		this.gaurdianLastName = gaurdianLastName;
	}

	public String getGaurdianAge() {
		return gaurdianAge;
	}

	public void setGaurdianAge(String gaurdianAge) {
		this.gaurdianAge = gaurdianAge;
	}

	public String getGaurdianAddress() {
		return gaurdianAddress;
	}

	public void setGaurdianAddress(String gaurdianAddress) {
		this.gaurdianAddress = gaurdianAddress;
	}

		
	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	/**
	 * @return the custId
	 */
	public String getCustId() {
		return custId;
	}

	/**
	 * @param custId
	 *            the custId to set
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}

	/**
	 * @return the salutation
	 */
	public String getSalutation() {
		return salutation;
	}

	/**
	 * @param salutation
	 *            the salutation to set
	 */
	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * @param middleName
	 *            the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the maritalStatus
	 */
	public String getMaritalStatus() {
		return maritalStatus;
	}

	/**
	 * @param maritalStatus
	 *            the maritalStatus to set
	 */
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	/**
	 * @return the occupation
	 */
	public String getOccupation() {
		return occupation;
	}

	/**
	 * @param occupation
	 *            the occupation to set
	 */
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	/**
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * @param mobileNo
	 *            the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}


	/**
	 * @return the dob
	 */
	public String getDob() {
		return dob;
	}

	/**
	 * @param dob
	 *            the dob to set
	 */
	public void setDob(String dob) {
		this.dob = dob;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the visaValidityDate
	 */
	public String getVisaValidityDate() {
		return visaValidityDate;
	}

	/**
	 * @param visaValidityDate
	 *            the visaValidityDate to set
	 */
	public void setVisaValidityDate(String visaValidityDate) {
		this.visaValidityDate = visaValidityDate;
	}

	/**
	 * @return the visaNo
	 */
	public String getVisaNo() {
		return visaNo;
	}

	/**
	 * @param visaNo
	 *            the visaNo to set
	 */
	public void setVisaNo(String visaNo) {
		this.visaNo = visaNo;
	}

	/**
	 * @return the nationality
	 */
	public String getNationality() {
		return nationality;
	}

	/**
	 * @param nationality
	 *            the nationality to set
	 */
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	/**
	 * @return the relFirstName
	 */
	public String getRelFirstName() {
		return relFirstName;
	}

	/**
	 * @param relFirstName
	 *            the relFirstName to set
	 */
	public void setRelFirstName(String relFirstName) {
		this.relFirstName = relFirstName;
	}

	/**
	 * @return the relMiddleName
	 */
	public String getRelMiddleName() {
		return relMiddleName;
	}

	/**
	 * @param relMiddleName
	 *            the relMiddleName to set
	 */
	public void setRelMiddleName(String relMiddleName) {
		this.relMiddleName = relMiddleName;
	}

	/**
	 * @return the relLastName
	 */
	public String getRelLastName() {
		return relLastName;
	}

	/**
	 * @param relLastName
	 *            the relLastName to set
	 */
	public void setRelLastName(String relLastName) {
		this.relLastName = relLastName;
	}

	/**
	 * @return the altNoHome
	 */
	public String getAltNoHome() {
		return altNoHome;
	}

	/**
	 * @param altNoHome
	 *            the altNoHome to set
	 */
	public void setAltNoHome(String altNoHome) {
		this.altNoHome = altNoHome;
	}

	/**
	 * @return the altNoWork
	 */
	public String getAltNoWork() {
		return altNoWork;
	}

	/**
	 * @param altNoWork
	 *            the altNoWork to set
	 */
	public void setAltNoWork(String altNoWork) {
		this.altNoWork = altNoWork;
	}

	/**
	 * @return the relationshipType
	 */
	public String getRelationshipType() {
		return relationshipType;
	}

	/**
	 * @param relationshipType
	 *            the relationshipType to set
	 */
	public void setRelationshipType(String relationshipType) {
		this.relationshipType = relationshipType;
	}

	/**
	 * @return the prefCommChannel
	 */
	public String getPrefCommChannel() {
		return prefCommChannel;
	}

	/**
	 * @param prefCommChannel
	 *            the prefCommChannel to set
	 */
	public void setPrefCommChannel(String prefCommChannel) {
		this.prefCommChannel = prefCommChannel;
	}

	/**
	 * @return the preflanguage
	 */
	public String getPreflanguage() {
		return preflanguage;
	}

	/**
	 * @param preflanguage
	 *            the preflanguage to set
	 */
	public void setPreflanguage(String preflanguage) {
		this.preflanguage = preflanguage;
	}

	/**
	 * @return the aadhaarNo
	 */
	public String getAadhaarNo() {
		return aadhaarNo;
	}

	/**
	 * @param aadhaarNo
	 *            the aadhaarNo to set
	 */
	public void setAadhaarNo(String aadhaarNo) {
		this.aadhaarNo = aadhaarNo;
	}


	/**
	 * @return the anniversaryDate
	 */
	public String getAnniversaryDate() {
		return anniversaryDate;
	}

	/**
	 * @param anniversaryDate
	 *            the anniversaryDate to set
	 */
	public void setAnniversaryDate(String anniversaryDate) {
		this.anniversaryDate = anniversaryDate;
	}

	/**
	 * @return the passportNo
	 */
	public String getPassportNo() {
		return passportNo;
	}

	/**
	 * @param passportNo
	 *            the passportNo to set
	 */
	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	/**
	 * @return the jioCenterId
	 */
	public String getJioCenterId() {
		return jioCenterId;
	}

	/**
	 * @param jioCenterId
	 *            the jioCenterId to set
	 */
	public void setJioCenterId(String jioCenterId) {
		this.jioCenterId = jioCenterId;
	}

	/**
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            the account to set
	 */
	public void setAccount(Account account) {
		this.account = account;
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * @return the providerStatus
	 */
	public Status getProviderStatus() {
		return providerStatus;
	}

	/**
	 * @param providerStatus
	 *            the providerStatus to set
	 */
	public void setProviderStatus(Status providerStatus) {
		this.providerStatus = providerStatus;
	}

	/**
	 * @return the headerMessage
	 */
	public Message getHeaderMessage() {
		return headerMessage;
	}

	/**
	 * @param headerMessage
	 *            the headerMessage to set
	 */
	public void setHeaderMessage(Message headerMessage) {
		this.headerMessage = headerMessage;
	}

	/**
	 * @return the businessApplication
	 */
	public BusinessApplication getBusinessApplication() {
		return businessApplication;
	}

	/**
	 * @param businessApplication
	 *            the businessApplication to set
	 */
	public void setBusinessApplication(BusinessApplication businessApplication) {
		this.businessApplication = businessApplication;
	}

	/**
	 * @return the cardDetails
	 */
	public List<CardDetails> getCardDetails() {
		return cardDetails;
	}

	/**
	 * @param cardDetails
	 *            the cardDetails to set
	 */
	public void setCardDetails(List<CardDetails> cardDetails) {
		this.cardDetails = cardDetails;
	}


	/**
	 * @return the primEmailId
	 */
	public String getPrimEmailId() {
		return primEmailId;
	}

	/**
	 * @param primEmailId the primEmailId to set
	 */
	public void setPrimEmailId(String primEmailId) {
		this.primEmailId = primEmailId;
	}

	/**
	 * @return the panNo
	 */
	public String getPanNo() {
		return panNo;
	}

	/**
	 * @param panNo the panNo to set
	 */
	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public String getInitiatedBy() {
		return initiatedBy;
	}

	public void setInitiatedBy(String initiatedBy) {
		this.initiatedBy = initiatedBy;
	}

	public String getRelSalutation() {
		return relSalutation;
	}

	public void setRelSalutation(String relSalutation) {
		this.relSalutation = relSalutation;
	}

	public String getRelLegalName() {
		return relLegalName;
	}

	public void setRelLegalName(String relLegalName) {
		this.relLegalName = relLegalName;
	}

	public String getBlackListFlag() {
		return blackListFlag;
	}

	public void setBlackListFlag(String blackListFlag) {
		this.blackListFlag = blackListFlag;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getIsvip() {
		return isvip;
	}

	public void setIsvip(String isvip) {
		this.isvip = isvip;
	}

	public String getOccupationDesc() {
		return occupationDesc;
	}

	public void setOccupationDesc(String occupationDesc) {
		this.occupationDesc = occupationDesc;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getSecEmailId() {
		return secEmailId;
	}

	public void setSecEmailId(String secEmailId) {
		this.secEmailId = secEmailId;
	}

	public String getSecMobNum() {
		return secMobNum;
	}

	public void setSecMobNum(String secMobNum) {
		this.secMobNum = secMobNum;
	}

	public String getFaxNo() {
		return faxNo;
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getNomineelegalname() {
		return nomineelegalname;
	}

	public void setNomineelegalname(String nomineelegalname) {
		this.nomineelegalname = nomineelegalname;
	}

	public String getNomineerelationship() {
		return nomineerelationship;
	}

	public void setNomineerelationship(String nomineerelationship) {
		this.nomineerelationship = nomineerelationship;
	}

	public String getNomineeAge() {
		return nomineeAge;
	}

	public void setNomineeAge(String nomineeAge) {
		this.nomineeAge = nomineeAge;
	}

	public String getHasnominee() {
		return hasnominee;
	}

	public void setHasnominee(String hasnominee) {
		this.hasnominee = hasnominee;
	}

	
}
