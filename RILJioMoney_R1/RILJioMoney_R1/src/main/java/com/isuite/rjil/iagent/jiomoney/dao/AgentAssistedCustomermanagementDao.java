package com.isuite.rjil.iagent.jiomoney.dao;

import com.isuite.rjil.iagent.jiomoney.common.Customer;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;

/**
 * @author NovelVox
 * 
 */
public interface AgentAssistedCustomermanagementDao {
	/**
	 * 
	 * @param orderRefNum
	 * @param orderAction
	 * @param orderCategory
	 * @param orderType
	 * @param salutation
	 * @param firstName
	 * @param middleName
	 * @param lastname
	 * @param dob
	 * @param gender
	 * @param nationality
	 * @param status
	 * @param maritalStatus
	 * @param relationshipType
	 * @param relFirstName
	 * @param relMiddleName
	 * @param relLastName
	 * @param primMobNum
	 * @param secMobNum
	 * @param primEmailId
	 * @param addressType
	 * @param city
	 * @param postCode
	 * @param houseNo
	 * @param streetName
	 * @param locality
	 * @param country
	 * @param state
	 * @param agentfirstName
	 * @param agentlastName
	 * @param hasNominee
	 * @param nomineefirstName
	 * @param nomineelastName
	 * @param relationship
	 * @param nomineeage
	 * @param nomineedob
	 * @param bankId
	 * @param branchName
	 * @param branchCode
	 * @param guardianFirstName
	 * @param guardianLastName
	 * @param guardianAge
	 * @param guardianaddressType
	 * @param guardiancity
	 * @param guardianpostCode
	 * @param guardianhouseNo
	 * @param guardianstreetName
	 * @param guardianlocality
	 * @param guardiancountry
	 * @param guardianstate
	 * @param pan
	 * @param docType
	 * @param type
	 * @param number
	 * @param issuedate
	 * @param placeOfIssue
	 * @return Customer
	 * @throws DataAccessException
	 */
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
			String number, String issuedate, String placeOfIssue,String agentId,long requestId)
			throws DataAccessException;
}
