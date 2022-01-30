package com.isuite.rjil.iagent.jiomoney.services;


import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.exception.ServiceException;

public interface CustomerOnboardingService {
	public String createProspect(String firstName,
			String lastName, String dob, String mobileNo,String emailId, 
			String gender, String state, String district,
			String pincode, String channel, String agentCode,long requestId)
			throws DataAccessException, ServiceException;

}
