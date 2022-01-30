/**
 * 
 */
package com.isuite.rjil.iagent.jiomoney.services.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.common.ReferenceData;
import com.isuite.rjil.iagent.jiomoney.dao.DaoLocator;
import com.isuite.rjil.iagent.jiomoney.dao.ReferenceDataDao;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.exception.ServiceException;
import com.isuite.rjil.iagent.jiomoney.services.ReferenceDataService;
import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;

/**
 * @author NovelVox
 * 
 */
public class ReferenceDataServiceImpl implements ReferenceDataService {

	private static final Logger logger = Logger
			.getLogger(ReferenceDataServiceImpl.class);
	private static final String dao = "ReferenceDataDao";

	@Override
	public List<ReferenceData> getReferenceDataValues(String lovType)
			throws DataAccessException, ServiceException {
		List<ReferenceData> returnList = null;
		try {

			if (lovType != null && !lovType.isEmpty()) {
				if (LOVUtil.getLov(lovType) != null) {
					returnList = LOVUtil.getLov(lovType);
				} else {
					returnList = ((ReferenceDataDao) DaoLocator.getDao(dao))
							.getReferenceDataValues(lovType);
					LOVUtil.setLov(lovType, returnList);
				}

			} else {
				throw new ServiceException("Please provie the Lov Type");
			}

		} catch (DataAccessException e) {
			logger.error(
					"Exception while accessing CustomerInquiryService:findCustomer ",
					e);
			throw e;
		}
		return returnList;
	}

	

	@Override
	public List<ReferenceData> getInteractionsubcategory(String lovType,
			String lovCode) throws DataAccessException, ServiceException {
		

		List<ReferenceData> returnList = null;
		try {

			if (lovType != null && !lovType.isEmpty()) {
				returnList = ((ReferenceDataDao) DaoLocator.getDao(dao))
						.getInteractionSubCategory(lovType, lovCode);

			} else {
				throw new ServiceException("Please provie the Lov Type");
			}

		} catch (DataAccessException e) {
			logger.error(
					"Exception while accessing CustomerInquiryService:findCustomer ",
					e);
			throw e;
		}
		return returnList;
	}

	@Override
	public String lookUpValueByCode(String lovType, String lovCode)
			throws DataAccessException, ServiceException 
			{
		// 

		String returnList = null;
		try {

			if (lovType != null && !lovType.isEmpty()) {
				returnList = ((ReferenceDataDao) DaoLocator.getDao(dao))
						.lookUpValueByCode(lovType, lovCode);

			} else {
				throw new ServiceException("Please provie the Lov Type");
			}

		} catch (DataAccessException e) {
			logger.error(
					"Exception while accessing CustomerInquiryService:findCustomer ",
					e);
			throw e;
		}
		return returnList;
	}

	@Override
	public List<ReferenceData> getInteractionSubSubCategory(
			String interactionSubSubCategory, String lovCode)
			throws DataAccessException, ServiceException {
		// 
		List<ReferenceData> returnList = null;
		try 
		{
			if (lovCode != null && !lovCode.isEmpty()) 
			{
				returnList = ((ReferenceDataDao) DaoLocator.getDao(dao)).getInteractionSubSubCategory(interactionSubSubCategory, lovCode);

			}
			else 
			{
				throw new ServiceException("Please provie the Lov Type");
			}

		} catch (DataAccessException e) {
			logger.error("Exception while accessing CustomerInquiryService:findCustomer ",	e);
			throw e;
		}
		return returnList;
	}

	@Override
	public List<ReferenceData> getInteractionCategory(String interactionCategory)
			throws DataAccessException, ServiceException {
		// 
		List<ReferenceData> returnList = null;
		
		try 
		{

			if (interactionCategory != null && !interactionCategory.isEmpty()) {
				returnList = ((ReferenceDataDao) DaoLocator.getDao(dao))
						.getReferenceDataValues(interactionCategory);

			} else {
				throw new ServiceException("Please provie the Lov Type");
			}

		} catch (DataAccessException e) {
			logger.error(
					"Exception while accessing CustomerInquiryService:findCustomer ",
					e);
			throw e;
		}
		return returnList;
	}

	@Override
	public List<ReferenceData> getPhysicalCardStatus()
			throws DataAccessException, ServiceException 
			{
		// 
		List<ReferenceData> returnList=null;
		try 
		{
			returnList = ((ReferenceDataDao) DaoLocator.getDao(dao)).getPhysicalCardStatus();
		}
		catch (DataAccessException e)
		{
			logger.error("Exception while accessing PhysicalCardStatus ",e);
			throw e;
		}
		
		return returnList;
	}

}
