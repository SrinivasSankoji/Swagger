/**
 * 
 */
package com.isuite.rjil.iagent.jiomoney.services;

import java.util.List;

import com.isuite.rjil.iagent.jiomoney.common.ReferenceData;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.exception.ServiceException;

/**
 * @author NovelVox
 * 
 */
public interface ReferenceDataService {
	/**
	 * 
	 * @param lovType
	 * @return
	 * @throws DataAccessException
	 * @throws ServiceException
	 */
	public List<ReferenceData> getReferenceDataValues(String lovType)
			throws DataAccessException, ServiceException;


	/**
	 * 
	 * @param lovType
	 * @param lovCode
	 * @return
	 * @throws DataAccessException
	 * @throws ServiceException
	 */
	public List<ReferenceData> getInteractionsubcategory(String lovType, String lovCode)
			throws DataAccessException, ServiceException;
	
	/**
	 * 
	 * @throws DataAccessException
	 * @throws ServiceException
	 */
	public List<ReferenceData> getPhysicalCardStatus()throws DataAccessException, ServiceException;
	
	/**
	 * 
	 * @param lovType
	 * @param lovCode
	 * @return
	 * @throws DataAccessException
	 * @throws ServiceException
	 */
	public String lookUpValueByCode(String lovType, String lovCode)
			throws DataAccessException, ServiceException;

	public List<ReferenceData> getInteractionSubSubCategory(
			String interactionSubSubCategory, String lovCode) throws DataAccessException, ServiceException;

	public List<ReferenceData> getInteractionCategory(String interactionCategory) throws DataAccessException, ServiceException;
}
