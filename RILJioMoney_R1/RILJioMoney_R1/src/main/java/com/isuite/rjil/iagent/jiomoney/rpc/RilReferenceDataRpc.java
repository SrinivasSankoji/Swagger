package com.isuite.rjil.iagent.jiomoney.rpc;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.common.ReferenceData;
import com.isuite.rjil.iagent.jiomoney.common.ServiceResponse;
import com.isuite.rjil.iagent.jiomoney.services.ReferenceDataService;
import com.isuite.rjil.iagent.jiomoney.services.ServiceLocator;
import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;
import com.isuite.rjil.iagent.jiomoney.util.Util;

public class RilReferenceDataRpc {

	private static final Logger logger = Logger
			.getLogger(RilReferenceDataRpc.class);
	private static final String service = "ReferenceDataService";

	public ServiceResponse getStatus() {
		ServiceResponse response = null;
		try {
			List<ReferenceData> returnList = null;
			if (LOVUtil.getLov(LOVUtil.STATUS) != null) {
				returnList = LOVUtil.getLov(LOVUtil.STATUS);
			} else {
				returnList = ((ReferenceDataService) ServiceLocator
						.getService(service))
						.getReferenceDataValues(LOVUtil.STATUS);
				LOVUtil.setLov(LOVUtil.STATUS, returnList);
			}
			if (returnList != null && returnList.size() > 0) {

				response = new ServiceResponse(returnList);

			} else {
				response = new ServiceResponse(1, "Unable To Retrieve STATUS");
			}
		} catch (Throwable e) {
			logger.error("Exception catched while doing get Status", e);
			response = new ServiceResponse(1, e.getMessage());
		}
		return response;
	}

	public ServiceResponse getCategory() {
		// method start here
		ServiceResponse response = null;
		try {
			List<ReferenceData> returnList = null;
			if (LOVUtil.getLov(LOVUtil.CATEGORY) != null) {
				returnList = LOVUtil.getLov(LOVUtil.CATEGORY);
			} else {
				returnList = ((ReferenceDataService) ServiceLocator
						.getService(service))
						.getReferenceDataValues(LOVUtil.CATEGORY);
				LOVUtil.setLov(LOVUtil.CATEGORY, returnList);
			}
			if (returnList != null && returnList.size() > 0) {

				response = new ServiceResponse(returnList);

			} else {
				response = new ServiceResponse(1,
						"Unable To Retrieve PROBLEM_CATEGORY");
			}

		} catch (Throwable e) {
			logger.error("Exception catched while doing get Dealer Status", e);
			response = new ServiceResponse(1, e.getMessage());
		}
		// method end here
		// TODO logging response aspect on/off mode
		return response;
	}

	public ServiceResponse getInteractionSubCategory(String lovCode) {

		ServiceResponse response = null;
		try {
			List<ReferenceData> returnList = null;

			if (lovCode != null) {
				returnList = ((ReferenceDataService) ServiceLocator
						.getService(service)).getInteractionsubcategory(
						LOVUtil.INTERACTION_SUB_CATEGORY, lovCode);
				LOVUtil.setLov(LOVUtil.INTERACTION_SUB_CATEGORY, returnList);
			}
			if (returnList != null && returnList.size() > 0) {

				response = new ServiceResponse(returnList);

			} else {
				response = new ServiceResponse(1,
						"Unable To Retrieve INTERACTION_SUB_CATEGORY");
			}
		} catch (Throwable e) {
			logger.error(
					"Exception catched while doing get INTERACTION_SUB_CATEGORY ",
					e);
			response = new ServiceResponse(1, e.getMessage());
		}
		return response;
	}

	public ServiceResponse getInteractionCategory() {
		ServiceResponse response = null;
		try {
			List<ReferenceData> returnList = null;
			if (LOVUtil.getLov(LOVUtil.INTERACTION_CATEGORY) != null) {
				returnList = LOVUtil.getLov(LOVUtil.INTERACTION_CATEGORY);
			} else {
				returnList = ((ReferenceDataService) ServiceLocator
						.getService(service))
						.getReferenceDataValues(LOVUtil.INTERACTION_CATEGORY);
				LOVUtil.setLov(LOVUtil.INTERACTION_CATEGORY, returnList);
			}
			if (returnList != null && returnList.size() > 0) {

				response = new ServiceResponse(returnList);

			} else {
				response = new ServiceResponse(1,
						"Unable To Retrieve INTERACTION_CATEGORY");
			}

		} catch (Throwable e) {
			logger.error(
					"Exception catched while doing get INTERACTION_CATEGORY", e);
			response = new ServiceResponse(1, e.getMessage());
		}
		// method end here
		// TODO logging response aspect on/off mode
		return response;
	}

	public ServiceResponse getInteractionSubSubCategory(String lovCode) {
		ServiceResponse response = null;
		try {
			List<ReferenceData> returnList = null;
			if (lovCode != null) {
				returnList = ((ReferenceDataService) ServiceLocator
						.getService(service)).getInteractionSubSubCategory(
						LOVUtil.INTERACTION_SUB_SUB_CATEGORY, lovCode);
				LOVUtil.setLov(LOVUtil.INTERACTION_SUB_SUB_CATEGORY, returnList);
			}
			if (returnList != null && returnList.size() > 0) {

				response = new ServiceResponse(returnList);

			} else {
				response = new ServiceResponse(1,
						"Unable To Retrieve INTERACTION_SUB_SUB_CATEGORY");
			}
		} catch (Throwable e) {
			logger.error(
					"Exception catched while doing get INTERACTION_SUB_SUB_CATEGORY ",
					e);
			response = new ServiceResponse(1, e.getMessage());
		}
		return response;
	}

	// get interacton description
	public ServiceResponse lookUpValueByCode(String lovCode) {
		ServiceResponse response = null;
		try {
			String returnList = null;
			if (lovCode != null) {
				returnList = ((ReferenceDataService) ServiceLocator
						.getService(service)).lookUpValueByCode(
						LOVUtil.INTERACTION_DESCRIPTION, lovCode);
			}
			if (returnList != null) {
				response = new ServiceResponse(returnList);

			} else {
				response = new ServiceResponse(1,
						"Unable To Retrieve INTERACTION_Descriptions");
			}
		} catch (Throwable e) {
			logger.error(
					"Exception catched while doing get INTERACTION_Descriptions ",
					e);
			response = new ServiceResponse(1, e.getMessage());
		}
		return response;
	}

	public ServiceResponse getImpact(String pSelectedImpact) {
		// method start here
		ServiceResponse response = null;
		try {
			List<ReferenceData> returnList = null;
			if (LOVUtil.getLov(LOVUtil.IMPACT) != null) {
				returnList = LOVUtil.getLov(LOVUtil.IMPACT);
			} else {
				returnList = ((ReferenceDataService) ServiceLocator
						.getService(service))
						.getReferenceDataValues(LOVUtil.IMPACT);
				LOVUtil.setLov(LOVUtil.IMPACT, returnList);
			}
			if (returnList != null && returnList.size() > 0) 
			{
				List<ReferenceData> lResponseList = new ArrayList<ReferenceData>();
				lResponseList.addAll(returnList);
				response = new ServiceResponse(lResponseList);
				ReferenceData lReferenceData = null;
				if(Util.isEmptyString(pSelectedImpact) == false)
				{
					
					for (ReferenceData lData : lResponseList) 
					{
						if(lData.getDescription().equalsIgnoreCase(pSelectedImpact))
						{
							lReferenceData = lData;
							break;
						}
					}
				}
				if(lReferenceData != null)
				{
					lResponseList.remove(lReferenceData);
					lResponseList.add(0,lReferenceData);
					response.setSecodaryData("true");
				}

			} else {
				response = new ServiceResponse(1, "Unable To Retrieve impact");
			}

		} catch (Throwable e) {
			logger.error("Exception catched while doing get impact", e);
			response = new ServiceResponse(1, e.getMessage());
		}
		// method end here
		// TODO logging response aspect on/off mode
		return response;
	}

	public ServiceResponse getUrgency(String pSelectedUrgency) {
		// method start here
		ServiceResponse response = null;
		try {
			List<ReferenceData> returnList = null;
			if (LOVUtil.getLov(LOVUtil.URGENCY) != null) {
				returnList = LOVUtil.getLov(LOVUtil.URGENCY);
			} else {
				returnList = ((ReferenceDataService) ServiceLocator
						.getService(service))
						.getReferenceDataValues(LOVUtil.URGENCY);
				LOVUtil.setLov(LOVUtil.URGENCY, returnList);
			}
			if (returnList != null && returnList.size() > 0) 
			{
				List<ReferenceData> lResponseList = new ArrayList<ReferenceData>();
				lResponseList.addAll(returnList);
				response = new ServiceResponse(lResponseList);
				ReferenceData lReferenceData = null;
				if(Util.isEmptyString(pSelectedUrgency) == false)
				{
					
					for (ReferenceData lData : lResponseList) 
					{
						if(lData.getDescription().equalsIgnoreCase(pSelectedUrgency))
						{
							lReferenceData = lData;
							break;
						}
					}
				}
				if(lReferenceData != null)
				{
					lResponseList.remove(lReferenceData);
					lResponseList.add(0,lReferenceData);
					response.setSecodaryData("true");
				}

			} else {
				response = new ServiceResponse(1, "Unable To Retrieve Urgency");
			}

		} catch (Throwable e) {
			logger.error("Exception catched while doing get Urgency", e);
			response = new ServiceResponse(1, e.getMessage());
		}
		// method end here
		// TODO logging response aspect on/off mode
		return response;
	}

	public ServiceResponse getPhysicalCardStatus() {
		ServiceResponse response = null;
		List<ReferenceData> returnList = null;
		try {
			if (LOVUtil.getLov(LOVUtil.CARD_STATUS) != null) {
				returnList = LOVUtil.getLov(LOVUtil.CARD_STATUS);
			} else {
				returnList = ((ReferenceDataService) ServiceLocator
						.getService(service)).getPhysicalCardStatus();
				LOVUtil.setLov(LOVUtil.CARD_STATUS, returnList);
			}
			if (returnList != null && returnList.size() > 0) {
				response = new ServiceResponse(returnList);
			} else {
				response = new ServiceResponse(1,
						"Unable To Retrieve Physical Card Status");
			}
		} catch (Throwable e) {
			logger.error(
					"Exception catched while doing get Physical Card Status", e);
			response = new ServiceResponse(1, e.getMessage());
		}
		// method end here
		// TODO logging response aspect on/off mode
		return response;
	}

}
