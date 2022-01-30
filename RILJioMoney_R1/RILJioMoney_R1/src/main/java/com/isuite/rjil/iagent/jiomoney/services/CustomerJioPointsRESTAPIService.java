package com.isuite.rjil.iagent.jiomoney.services;

import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;

public interface CustomerJioPointsRESTAPIService 
{
	public String getJioPointForMobileNo(String pMobileNumber, String pAgentId,String requestId) throws DataAccessException;
}
