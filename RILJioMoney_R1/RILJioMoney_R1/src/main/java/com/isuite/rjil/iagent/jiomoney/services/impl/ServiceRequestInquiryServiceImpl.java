package com.isuite.rjil.iagent.jiomoney.services.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.common.CustomerProblem;
import com.isuite.rjil.iagent.jiomoney.common.ReferenceData;
import com.isuite.rjil.iagent.jiomoney.dao.CustomerOnboardingDao;
import com.isuite.rjil.iagent.jiomoney.dao.DaoLocator;
import com.isuite.rjil.iagent.jiomoney.dao.ServiceRequestInquiryDao;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.exception.ServiceException;
import com.isuite.rjil.iagent.jiomoney.services.ReferenceDataService;
import com.isuite.rjil.iagent.jiomoney.services.ServiceLocator;
import com.isuite.rjil.iagent.jiomoney.services.ServiceRequestInquiryService;
import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;

/**
 * 
 * @author NovelVox
 * 
 */
public class ServiceRequestInquiryServiceImpl implements ServiceRequestInquiryService {
	private static final Logger logger = Logger
			.getLogger(ServiceRequestInquiryServiceImpl.class);

	private static final String dao = "ServiceRequestInquiryDao";

	@Override
	public CustomerProblem viewServiceRequest(String problemRefNo,
			String custType, String agentId,long requestId) throws DataAccessException,
			ServiceException {
		CustomerProblem viewServiceRequest = null;
		try {
			if (problemRefNo == null || problemRefNo.isEmpty()) {
				throw new ServiceException(
						"Problem Reference can not be blank ");
			}
			viewServiceRequest = ((ServiceRequestInquiryDao) DaoLocator
					.getDao(dao)).viewServiceRequest(problemRefNo, custType,
					agentId,requestId);

			if (viewServiceRequest != null
					&& viewServiceRequest.getSubSubCategory() != null
					&& !viewServiceRequest.getSubSubCategory().isEmpty()) {
				if (LOVUtil.getLov(LOVUtil.CATEGORY) != null) {
					viewServiceRequest.setSubSubCategory(LOVUtil.getValue(
							viewServiceRequest.getSubSubCategory(),
							LOVUtil.CATEGORY));

				} else {
					List<ReferenceData> customerCategory = ((ReferenceDataService) ServiceLocator
							.getService("ReferenceDataService"))
							.getReferenceDataValues(LOVUtil.CATEGORY);
					LOVUtil.setLov(LOVUtil.CATEGORY, customerCategory);
					viewServiceRequest.setSubSubCategory(LOVUtil.getValue(
							viewServiceRequest.getSubSubCategory(),
							LOVUtil.CATEGORY));

				}
			}

			if (viewServiceRequest != null
					&& viewServiceRequest.getStatus() != null
					&& !viewServiceRequest.getStatus().isEmpty()) {
				if (LOVUtil.getLov(LOVUtil.STATUS) != null) {
					viewServiceRequest.setStatus(LOVUtil.getValue(
							viewServiceRequest.getStatus(), LOVUtil.STATUS));

				} else {
					List<ReferenceData> customerStatus = ((ReferenceDataService) ServiceLocator
							.getService("ReferenceDataService"))
							.getReferenceDataValues(LOVUtil.STATUS);
					LOVUtil.setLov(LOVUtil.STATUS, customerStatus);
					viewServiceRequest.setStatus(LOVUtil.getValue(
							viewServiceRequest.getStatus(), LOVUtil.STATUS));

				}
			}
			if (viewServiceRequest != null
					&& viewServiceRequest.getImpact() != null
					&& !viewServiceRequest.getImpact().isEmpty()) {
				if (LOVUtil.getLov(LOVUtil.IMPACT) != null) {
					viewServiceRequest.setImpact(LOVUtil.getValue(
							viewServiceRequest.getImpact(), LOVUtil.IMPACT));

				} else {
					List<ReferenceData> customerImpact = ((ReferenceDataService) ServiceLocator
							.getService("ReferenceDataService"))
							.getReferenceDataValues(LOVUtil.IMPACT);
					LOVUtil.setLov(LOVUtil.IMPACT, customerImpact);
					viewServiceRequest.setImpact(LOVUtil.getValue(
							viewServiceRequest.getImpact(), LOVUtil.IMPACT));

				}
			}

			if (viewServiceRequest != null
					&& viewServiceRequest.getUrgency() != null
					&& !viewServiceRequest.getUrgency().isEmpty()) {
				if (LOVUtil.getLov(LOVUtil.URGENCY) != null) {
					viewServiceRequest.setUrgency(LOVUtil.getValue(
							viewServiceRequest.getUrgency(), LOVUtil.URGENCY));

				} else {
					List<ReferenceData> customerUrgency = ((ReferenceDataService) ServiceLocator
							.getService("ReferenceDataService"))
							.getReferenceDataValues(LOVUtil.URGENCY);
					LOVUtil.setLov(LOVUtil.URGENCY, customerUrgency);
					viewServiceRequest.setUrgency(LOVUtil.getValue(
							viewServiceRequest.getUrgency(), LOVUtil.URGENCY));

				}
			}

		} catch (DataAccessException e) {
			logger.error("Exception catched while doing viewServiceRequest ", e);
			throw e;
		}
		return viewServiceRequest;

	}

	@Override
	public List<CustomerProblem> searchServiceRequest(String entityType,
			String entityId, String urgency, String impact, String isSummaryRq,
			String owner, String serviceTeam, String status, String fromDate,
			String toDate, String pageSize, String offSet, String agentId,long requestId)
			throws DataAccessException, ServiceException {
		List<CustomerProblem> searchServiceRequest = null;
		try {

			searchServiceRequest = ((ServiceRequestInquiryDao) DaoLocator
					.getDao(dao)).searchServiceRequest(entityType, entityId,
					urgency, impact, isSummaryRq, owner, serviceTeam, status,
					fromDate, toDate, pageSize, offSet, agentId,requestId);

			if (searchServiceRequest != null && searchServiceRequest.size() > 0) {
				for (CustomerProblem custProb : searchServiceRequest) {
					if (custProb.getImpact() != null
							&& !custProb.getImpact().isEmpty()) {
						if (LOVUtil.getLov(LOVUtil.IMPACT) != null) {
							custProb.setImpact(LOVUtil.getValue(
									custProb.getImpact(), LOVUtil.IMPACT));

						}
					} else {
						List<ReferenceData> customerImpact = ((ReferenceDataService) ServiceLocator
								.getService("ReferenceDataService"))
								.getReferenceDataValues(LOVUtil.IMPACT);
						LOVUtil.setLov(LOVUtil.IMPACT, customerImpact);
						custProb.setImpact(LOVUtil.getValue(
								custProb.getImpact(), LOVUtil.IMPACT));
					}
					if (custProb.getUrgency() != null
							&& !custProb.getUrgency().isEmpty()) {
						if (LOVUtil.getLov(LOVUtil.URGENCY) != null) {
							custProb.setUrgency(LOVUtil.getValue(
									custProb.getUrgency(), LOVUtil.URGENCY));

						}
					} else {
						List<ReferenceData> customerImpact = ((ReferenceDataService) ServiceLocator
								.getService("ReferenceDataService"))
								.getReferenceDataValues(LOVUtil.URGENCY);
						LOVUtil.setLov(LOVUtil.URGENCY, customerImpact);
						custProb.setUrgency(LOVUtil.getValue(
								custProb.getUrgency(), LOVUtil.URGENCY));
					}

				}
			}

		} catch (DataAccessException e) {
			logger.error("Exception catched while doing searchServiceRequest ",
					e);
			throw e;
		}
		return searchServiceRequest;
	}


	@Override
	public List<ReferenceData> getServiceCategory1(String entityType,
			String agentId,long requestId) throws DataAccessException, ServiceException {
		List<ReferenceData> getservicecategory1 = null;
		try {

			getservicecategory1 = ((ServiceRequestInquiryDao) DaoLocator
					.getDao(dao)).getServiceCategory1(entityType, agentId,requestId);

		} catch (DataAccessException e) {
			logger.error("Exception catched while doing getServiceCategory1 ",
					e);
			throw e;
		}
		return getservicecategory1;

	}

	@Override
	public List<ReferenceData> getServiceCategory2(String entityType,
			String categoryId1, String agentId,long requestId) throws DataAccessException,
			ServiceException {
		List<ReferenceData> getservicecategory2 = null;
		try {

			getservicecategory2 = ((ServiceRequestInquiryDao) DaoLocator
					.getDao(dao)).getServiceCategory2(entityType, categoryId1,
					agentId,requestId);

		} catch (DataAccessException e) {
			logger.error("Exception catched while doing getServiceCategory2 ",
					e);
			throw e;
		}
		return getservicecategory2;

	}

	public List<ReferenceData> getServiceCategory3(String categoryId1,
			String categoryId2, String entityType, String agentId,long requestId)
			throws DataAccessException, ServiceException {
		List<ReferenceData> getservicecategory3 = null;
		try {

			getservicecategory3 = ((ServiceRequestInquiryDao) DaoLocator
					.getDao(dao)).getServiceCategory3(categoryId1, categoryId2,
					entityType, agentId,requestId);

		} catch (DataAccessException e) {
			logger.error("Exception catched while doing getservicecategory3 ",
					e);
			throw e;
		}
		return getservicecategory3;

	}
	
	public List<ReferenceData> getServiceCategory4(String categoryId1,
			String categoryId2,String categoryId3, String entityType, String agentId,long requestId)
			throws DataAccessException, ServiceException {
		List<ReferenceData> getservicecategory4 = null;
		try {

			getservicecategory4 = ((ServiceRequestInquiryDao) DaoLocator
					.getDao(dao)).getServiceCategory4(categoryId1, categoryId2,categoryId3,
					entityType, agentId,requestId);

		} catch (DataAccessException e) {
			logger.error("Exception catched while doing getservicecategory4 ",
					e);
			throw e;
		}
		return getservicecategory4;

	}
	
	

	@Override
	public List<ReferenceData> getStatesOrDistrictList(String pState,String agentId,long requestId) throws DataAccessException, ServiceException 
	{
		List<ReferenceData> list = null;
		try {

			list = ((ServiceRequestInquiryDao) DaoLocator.getDao(dao)).getStatesOrDistrictList(pState,agentId,requestId);

		} catch (DataAccessException e) {
			logger.error("Exception catched while doing getservicecategory4 ",
					e);
			throw e;
		}
		return list;
		
	}
	
	public String validateServiceRequest(String entityId,String subSubSubCategory, 
			String entityType, String agentCode,long requestId) throws DataAccessException,
			ServiceException {
		
			return ((ServiceRequestInquiryDao) DaoLocator
					.getDao(dao)).validateServiceRequest(entityId,subSubSubCategory, 
							 entityType,  agentCode, requestId);
	}

}
