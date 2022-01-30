package com.isuite.rjil.iagent.jiomoney.dao;

import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;

public interface CustomerJioPointsRESTAPIDao
{
	public String getJioPointForMobileNo(String pMobileNumber, String pAgentId,String requestId) throws DataAccessException;
}
