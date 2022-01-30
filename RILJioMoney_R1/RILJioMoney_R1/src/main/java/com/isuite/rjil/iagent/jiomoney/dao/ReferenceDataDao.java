/**
 * 
 */
package com.isuite.rjil.iagent.jiomoney.dao;

import java.util.List;

import com.isuite.rjil.iagent.jiomoney.common.ReferenceData;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;

/**
 * @author NovelVox
 * 
 */
public interface ReferenceDataDao {

	public List<ReferenceData> getReferenceDataValues(String lovType)
			throws DataAccessException;


	public String lookUpValueByCode(String lovType, String lovCode)
			throws DataAccessException;

	public List<ReferenceData> getInteractionSubCategory(String lovType, String lovCode)
			throws DataAccessException;

	public List<ReferenceData> getInteractionSubSubCategory(
			String interactionSubSubCategory, String lovCode)throws DataAccessException;

	public List<ReferenceData> getPhysicalCardStatus()throws DataAccessException;
	
}
