package com.isuite.rjil.iagent.jiomoney.services.impl;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.common.Customer;
import com.isuite.rjil.iagent.jiomoney.dao.AgentAssistedCustomermanagementDao;
import com.isuite.rjil.iagent.jiomoney.dao.DaoLocator;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.exception.ServiceException;
import com.isuite.rjil.iagent.jiomoney.services.AgentAssistedCustomermanagementService;

public class AgentAssistedCustomermanagementServiceImpl implements
		AgentAssistedCustomermanagementService {
	private static final Logger logger = Logger
			.getLogger(AgentAssistedCustomermanagementServiceImpl.class);

	private static final String dao = "AgentAssistedCustomermanagementDao";

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
			String number, String issuedate, String placeOfIssue,String agentId,long requestId)
			throws DataAccessException, ServiceException {

		try {

			// if (salutation == null || salutation.isEmpty() || firstName ==
			// null
			// || firstName.isEmpty() || lastname == null
			// || lastname.isEmpty() || dob == null || dob.isEmpty()
			// || gender == null || gender.isEmpty() || primMobNum == null
			// || primMobNum.isEmpty() || primEmailId == null
			// || primEmailId.isEmpty() || nationality == null
			// || nationality.isEmpty() || addressType == null
			// || addressType.isEmpty() || houseNo == null
			// || houseNo.isEmpty() || streetName == null
			// || streetName.isEmpty() || locality == null
			// || locality.isEmpty() || country == null
			// || country.isEmpty() || state == null || state.isEmpty()
			// || postCode == null || postCode.isEmpty()) {
			// throw new ServiceException("Mandatory Parameter are required");
			// }
			if (!primMobNum.startsWith("+91")) {
				primMobNum = "+91" + primMobNum;
			}

			Customer submitcustomerorder = ((AgentAssistedCustomermanagementDao) DaoLocator
					.getDao(dao)).submitCustomerOrder(orderRefNum, orderAction,
					orderCategory, orderType, salutation, firstName,
					middleName, lastname, dob, gender, nationality, status,
					maritalStatus, relationshipType, relFirstName,
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
					placeOfIssue,agentId,requestId);

			return submitcustomerorder;
		} catch (DataAccessException e) {
			logger.error("Exception catched while doing submitcustomerorder ",
					e);
			throw e;
		}
	}

}
