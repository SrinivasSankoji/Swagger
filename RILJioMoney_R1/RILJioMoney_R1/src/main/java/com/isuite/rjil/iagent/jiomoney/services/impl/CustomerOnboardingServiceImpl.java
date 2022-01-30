package com.isuite.rjil.iagent.jiomoney.services.impl;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.dao.DaoLocator;
import com.isuite.rjil.iagent.jiomoney.dao.CustomerOnboardingDao;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.exception.ServiceException;
import com.isuite.rjil.iagent.jiomoney.services.CustomerOnboardingService;

public class CustomerOnboardingServiceImpl implements CustomerOnboardingService{

	private static final Logger logger = Logger.getLogger(CustomerOnboardingServiceImpl.class);

	private static String DAO = "CustomerOnboardingDAO";
	public String createProspect(String firstName, String lastName,
			String dob, String mobileNo, String emailId, String gender,
			String state, String district, String pincode, String channel,
			String agentCode, long requestId) throws DataAccessException,
			ServiceException {
		
			return ((CustomerOnboardingDao) DaoLocator
					.getDao(DAO)).createProspect(firstName,
							lastName,dob, mobileNo, emailId,gender,state,district,pincode, channel,agentCode,requestId);
	}

}
 