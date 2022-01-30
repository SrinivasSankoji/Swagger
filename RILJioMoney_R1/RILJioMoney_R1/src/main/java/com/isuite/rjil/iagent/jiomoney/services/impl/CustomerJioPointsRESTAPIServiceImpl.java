package com.isuite.rjil.iagent.jiomoney.services.impl;


import com.isuite.rjil.iagent.jiomoney.dao.CustomerJioPointsRESTAPIDao;
import com.isuite.rjil.iagent.jiomoney.dao.DaoLocator;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.services.CustomerJioPointsRESTAPIService;



public class CustomerJioPointsRESTAPIServiceImpl implements CustomerJioPointsRESTAPIService
{
	public String getJioPointForMobileNo(String pMobileNumber, String pAgentId,String requestId) throws DataAccessException
	{
		return ((CustomerJioPointsRESTAPIDao)DaoLocator.getDao("CustomerJioPointsRESTAPIDao")).getJioPointForMobileNo(pMobileNumber,pAgentId,requestId);
	}
}
