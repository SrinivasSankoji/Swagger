package com.isuite.rjil.iagent.jiomoney.services.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.common.SDLCampaign;
import com.isuite.rjil.iagent.jiomoney.common.lcm.LcmBOGroupParent;
import com.isuite.rjil.iagent.jiomoney.common.lcm.LcmBusinessOutcome;
import com.isuite.rjil.iagent.jiomoney.common.lcm.LcmBusinessOutcomeDetails;
import com.isuite.rjil.iagent.jiomoney.common.lcm.LcmContactDetail;
import com.isuite.rjil.iagent.jiomoney.common.lcm.LcmModesWithCValue;
import com.isuite.rjil.iagent.jiomoney.common.lcm.LcmPCBList;
import com.isuite.rjil.iagent.jiomoney.dao.DaoLocator;
import com.isuite.rjil.iagent.jiomoney.dao.RilLcmDao;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.exception.ServiceException;
import com.isuite.rjil.iagent.jiomoney.services.RilLcmService;

public class RilLcmServiceImpl implements RilLcmService {

	private static final Logger logger = Logger
			.getLogger(RilLcmServiceImpl.class);
	private static final String dao = "RilLcmServicesDao";

	@Override
	public List<LcmContactDetail> getLcmContactDetail(String lcmCallKey,String pAgentID,long requestId)
			throws DataAccessException, ServiceException {

		List<LcmContactDetail> returnList = null;
		try {

			returnList = ((RilLcmDao) DaoLocator.getDao(dao))
					.getLcmContactDetail(lcmCallKey,pAgentID,requestId);

		} catch (Exception e) {
			logger.error(
					"Exception catched while retrieving Lcm Contact Details", e);
			throw new ServiceException(e.getMessage(), e);
		}

		return returnList;
	}

	@Override
	public List<LcmBusinessOutcome> getBusinessOutcome(String lcmCallKey)
			throws DataAccessException, ServiceException {

		List<LcmBusinessOutcome> returnList = null;

		try {

			returnList = ((RilLcmDao) DaoLocator.getDao(dao))
					.getBusinessOutcome(lcmCallKey,-1000);

		} catch (Exception e) {
			logger.error(
					"Exception catched while retrieving Business Outcomes", e);
			throw new ServiceException(e.getMessage(), e);
		}

		return returnList;
	}

	@Override
	public void setBusinessOutcome(String lcmCallKey, String outcome,
			String callId, String agentId,long requestId) throws DataAccessException,
			ServiceException {

		try {
			((RilLcmDao) DaoLocator.getDao(dao)).setBusinessOutcome(lcmCallKey,
					outcome, callId, agentId,requestId);

		} catch (Exception e) {
			logger.error(
					"Exception catched while retrieving Business Outcomes", e);
			throw new ServiceException(e.getMessage(), e);
		}

	}

	@Override
	public List<LcmBusinessOutcome> getCallOutcome()
			throws DataAccessException, ServiceException {

		List<LcmBusinessOutcome> returnList = null;

		try {

			returnList = ((RilLcmDao) DaoLocator.getDao(dao)).getCallOutcome();

		} catch (Exception e) {
			logger.error("Exception catched while retrieving Call Outcomes", e);
			throw new ServiceException(e.getMessage(), e);
		}

		return returnList;
	}

	@Override
	public void setCallOutcome(String lcmcallKey, String outcome,
			String dncInfo, String callId, String agentId,long requestId)
			throws DataAccessException, ServiceException {
		try {
			((RilLcmDao) DaoLocator.getDao(dao)).setCallOutcome(lcmcallKey,
					outcome, dncInfo, callId, agentId,requestId);

		} catch (Exception e) {
			logger.error("Exception catched while retrieving Call Outcomes", e);
			throw new ServiceException(e.getMessage(), e);
		}

	}

	@Override
	public String getValueFromLcmBusinessFields(String lcmKey, String fieldName)
			throws DataAccessException, ServiceException {

		String toReturn = "";
		try {
			toReturn = ((RilLcmDao) DaoLocator.getDao(dao))
					.getValueFromLcmBusinessFields(lcmKey, fieldName,-1000);

		} catch (Exception e) {
			logger.error(
					"Exception catched while retrieving Business Feld Value By Field Name",
					e);
			throw new ServiceException(e.getMessage(), e);
		}
		return toReturn;
	}

	@Override
	public List<LcmBusinessOutcome> getBusinessOutcomeParent(String lcmCallKey,
			int parentBOId) throws DataAccessException, ServiceException {

		List<LcmBusinessOutcome> returnList = null;

		try {

			returnList = ((RilLcmDao) DaoLocator.getDao(dao))
					.getBusinessOutcomeParent(lcmCallKey, parentBOId);

		} catch (Exception e) {
			logger.error(
					"Exception catched while retrieving Business Outcome Parent",
					e);
			throw new ServiceException(e.getMessage(), e);
		}

		return returnList;
	}

	@Override
	public List<LcmBusinessOutcome> getModes(String lcmCallKey)
			throws DataAccessException, ServiceException {

		List<LcmBusinessOutcome> returnList = null;

		try {

			returnList = ((RilLcmDao) DaoLocator.getDao(dao))
					.getModes(lcmCallKey);

		} catch (Exception e) {
			logger.error("Exception catched while retrieving Modes", e);
			throw new ServiceException(e.getMessage(), e);
		}

		return returnList;
	}

	@Override
	public List<LcmBusinessOutcome> getCampaignModes(String lcmCallKey)
			throws DataAccessException, ServiceException {

		List<LcmBusinessOutcome> returnList = null;

		try {

			returnList = ((RilLcmDao) DaoLocator.getDao(dao))
					.getCampaignModes(lcmCallKey);

		} catch (Exception e) {
			logger.error("Exception catched while retrieving Campaign Modes", e);
			throw new ServiceException(e.getMessage(), e);
		}

		return returnList;
	}

	@Override
	public void setBusinessOutcomeWithComment(String strAcNo, String outCome,
			String callId, String userId, String agentComment,
			String targetAmount) throws DataAccessException, ServiceException {

		try {
			BigDecimal targetAmnt = new BigDecimal(
					targetAmount.toString() != null ? targetAmount.toString()
							.trim() : "0");
			((RilLcmDao) DaoLocator.getDao(dao)).setBusinessOutcomeWithComment(
					strAcNo, outCome, callId, userId, agentComment, targetAmnt);

		} catch (Exception e) {
			logger.error(
					"Exception catched while retrieving Business Outcomes With Comments",
					e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public void setCallOutcomeWithComments(String strAcNo, String outCome,
			String dNCInfo, String callId, String userId, String agentComment,
			String targetAmount) throws DataAccessException, ServiceException {
		try {
			BigDecimal targetAmnt = new BigDecimal(
					targetAmount.toString() != null ? targetAmount.toString()
							.trim() : "0");
			((RilLcmDao) DaoLocator.getDao(dao)).setCallOutcomeWithComment(
					strAcNo, outCome, dNCInfo, callId, userId, agentComment,
					targetAmnt);

		} catch (Exception e) {
			logger.error(
					"Exception catched while retrieving Call Outcomes with Comments",
					e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public void setCallback(String lcmCallKey, String callBackStartDate,
			String callBackEndDate, String callBackStartTime,
			String callBackEndTime, String modeID, String callId)
			throws DataAccessException, ServiceException {
		try {
			int modeId = -1;
			if (!modeID.isEmpty()) {
				modeId = Integer.parseInt(modeID.toString().trim());
			}

			((RilLcmDao) DaoLocator.getDao(dao)).setCallback(lcmCallKey,
					callBackStartDate, callBackEndDate, callBackStartTime,
					callBackEndTime, modeId, callId);

		} catch (Exception e) {
			logger.error("Exception catched while retrieving CallBack", e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public void setCallbackWithComment(String lcmCallKey,
			String callBackStartDate, String callBackEndDate,
			String callBackStartTime, String callBackEndTime, String modeId,
			String callId, String agentComment, String targetAmount)
			throws DataAccessException, ServiceException {
		try {
			BigDecimal targetAmnt = new BigDecimal(
					targetAmount.toString() != null ? targetAmount.toString()
							.trim() : "0");
			int nModeId = Integer.parseInt(modeId.toString().trim());
			((RilLcmDao) DaoLocator.getDao(dao)).setCallbackWithComment(
					lcmCallKey, callBackStartDate, callBackEndDate,
					callBackStartTime, callBackEndTime, nModeId, callId,
					agentComment, targetAmnt);

		} catch (Exception e) {
			logger.error(
					"Exception catched while retrieving Callback With Comment",
					e);
			throw new ServiceException(e.getMessage(), e);
		}

	}

	@Override
	public void setPersonalCallback(String lcmCallKey,
			String callBackStartDate, String callBackEndDate,
			String callBackStartTime, String callBackEndTime, String modeId,
			String userId, String callId) throws DataAccessException,
			ServiceException {

		try {
			int modeID = -1;
			if (!modeId.isEmpty()) {
				modeID = Integer.parseInt(modeId.toString().trim());
			}

			((RilLcmDao) DaoLocator.getDao(dao)).setPersonalCallback(
					lcmCallKey, callBackStartDate, callBackEndDate,
					callBackStartTime, callBackEndTime, modeID, userId, callId);

		} catch (Exception e) {
			logger.error(
					"Exception catched while retrieving Personal Callback", e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public void setPersonalCallbackWithComment(String lcmCallKey,
			String callBackStartDate, String callBackEndDate,
			String callBackStartTime, String callBackEndTime, String modeId,
			String callId, String userId, String agentComment,
			String targetAmount) throws DataAccessException, ServiceException {
		try {
			BigDecimal targetAmnt = new BigDecimal(
					targetAmount.toString() != null ? targetAmount.toString()
							.trim() : "0");
			int nModeId = Integer.parseInt(modeId.toString().trim());

			((RilLcmDao) DaoLocator.getDao(dao))
					.setPersonalCallbackWithComment(lcmCallKey,
							callBackStartDate, callBackEndDate,
							callBackStartTime, callBackEndTime, nModeId,
							callId, userId, agentComment, targetAmnt);

		} catch (Exception e) {
			logger.error(
					"Exception catched while retrieving Personal Callback  With Comment",
					e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public void setNewModeNumber(String lcmCallKey, String modeId,
			String contactNumber, String deleteExistingModes)
			throws DataAccessException, ServiceException {
		try {
			int modelID = Integer.parseInt(modeId.toString().trim());
			boolean deleteExistingMode = (deleteExistingModes
					.equalsIgnoreCase("true") ? true : false);
			((RilLcmDao) DaoLocator.getDao(dao)).setNewModeNumber(lcmCallKey,
					modelID, contactNumber, deleteExistingMode);

		} catch (Exception e) {
			logger.error(
					"Exception catched while retrieving Personal Callback  With Comment",
					e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public String getCallGuideName(String lcmCallKey)
			throws DataAccessException, ServiceException {

		String result = "";
		try {
			result = ((RilLcmDao) DaoLocator.getDao(dao))
					.getCallGuideName(lcmCallKey);

		} catch (Exception e) {
			logger.error("Exception catched while retrieving Call Guide Name",
					e);
			throw new ServiceException(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public List<LcmPCBList> getPersonalCallbackList(String userId,
			String fromDate, String toDate, String startingNo,
			String noOfRecords) throws DataAccessException, ServiceException {

		List<LcmPCBList> returnList = null;

		try {
			int startNo = Integer.parseInt(startingNo.toString().trim());
			int noOfRecord = Integer.parseInt(noOfRecords.toString().trim());
			returnList = ((RilLcmDao) DaoLocator.getDao(dao))
					.getPersonalCallbackList(userId, fromDate, toDate, startNo,
							noOfRecord);

		} catch (Exception e) {
			logger.error(
					"Exception catched while retrieving Personal Callback List",
					e);
			throw new ServiceException(e.getMessage(), e);
		}
		return returnList;
	}

	@Override
	public String getCampaigns() throws DataAccessException, ServiceException {

		String result = "";
		try {
			result = ((RilLcmDao) DaoLocator.getDao(dao)).getCampaigns();

		} catch (Exception e) {
			logger.error("Exception catched while retrieving Campaigns", e);
			throw new ServiceException(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public String getCampaignBusinessFields(String campaignName)
			throws DataAccessException, ServiceException {

		String result = "";
		try {
			result = ((RilLcmDao) DaoLocator.getDao(dao))
					.getCampaignBusinessFields(campaignName);

		} catch (Exception e) {
			logger.error(
					"Exception catched while retrieving Campaign Business Fields",
					e);
			throw new ServiceException(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public String getBusinessOutcomeforCampaign(String campaignName)
			throws DataAccessException, ServiceException {

		String result = "";
		try {
			result = ((RilLcmDao) DaoLocator.getDao(dao))
					.getBusinessOutcomeforCampaign(campaignName);

		} catch (Exception e) {
			logger.error(
					"Exception catched while retrieving BusinessOutcome for Campaign",
					e);
			throw new ServiceException(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public String getContact(String campaignName, String phoneNo,
			String condition, String noOfRows) throws DataAccessException,
			ServiceException {
		String result = "";
		try {
			int noOfRow = Integer.parseInt(noOfRows.toString().trim());
			result = ((RilLcmDao) DaoLocator.getDao(dao)).getContact(
					campaignName, phoneNo, condition, noOfRow);

		} catch (Exception e) {
			logger.error(
					"Exception catched while retrieving Contact for Campaign",
					e);
			throw new ServiceException(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public void setContactStatus(String campaignId, String contactId)
			throws DataAccessException, ServiceException {

		try {
			int contactID = Integer.parseInt(contactId.toString().trim());
			((RilLcmDao) DaoLocator.getDao(dao)).setContactStatus(campaignId,
					contactID);

		} catch (Exception e) {
			logger.error("Exception catched while retrieving Contact Status", e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public void setContactBusinessFields(String lcmCallKey, String xmldata)
			throws DataAccessException, ServiceException {

		try {
			((RilLcmDao) DaoLocator.getDao(dao)).setContactBusinessFields(
					lcmCallKey, xmldata);

		} catch (Exception e) {
			logger.error(
					"Exception catched while retrieving Contact Business Fields",
					e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public void setContactOutcome(String contactId, String campaignId,
			String modeDescription, String outcomeId)
			throws DataAccessException, ServiceException {

		try {
			((RilLcmDao) DaoLocator.getDao(dao)).setContactOutcome(contactId,
					campaignId, modeDescription, outcomeId);

		} catch (Exception e) {
			logger.error("Exception catched while retrieving Contact Outcome",
					e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public String getCommonBussFields(String campaignId)
			throws DataAccessException, ServiceException {
		String result = "";
		try {
			result = ((RilLcmDao) DaoLocator.getDao(dao))
					.getCommonBussFields(campaignId);

		} catch (Exception e) {
			logger.error("Exception catched while retrieving CommonBussFields",
					e);
			throw new ServiceException(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public List<LcmBusinessOutcome> getBussOutcomeForGroup(String outcomeGroup)
			throws DataAccessException, ServiceException {

		List<LcmBusinessOutcome> returnList = null;

		try {

			returnList = ((RilLcmDao) DaoLocator.getDao(dao))
					.getBussOutcomeForGroup(outcomeGroup);

		} catch (Exception e) {
			logger.error(
					"Exception catched while retrieving Business Outcomes For Group",
					e);
			throw new ServiceException(e.getMessage(), e);
		}

		return returnList;
	}

	@Override
	public String getContactAcrossCampaign(String phoneNo, String condition,
			String noOfRows) throws DataAccessException, ServiceException {
		String result = "";
		try {
			int noOfRow = Integer.parseInt(noOfRows.toString().trim());
			result = ((RilLcmDao) DaoLocator.getDao(dao))
					.getContactAcrossCampaign(phoneNo, condition, noOfRow);

		} catch (Exception e) {
			logger.error(
					"Exception catched while retrieving Contact Across Campaign",
					e);
			throw new ServiceException(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public String getCallHistory(String lcmCallKey) throws DataAccessException,
			ServiceException {
		String result = "";
		try {
			result = ((RilLcmDao) DaoLocator.getDao(dao))
					.getCallHistory(lcmCallKey);

		} catch (Exception e) {
			logger.error("Exception catched while retrieving Call History", e);
			throw new ServiceException(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public String getScreenPopData(String lcmCallKey)
			throws DataAccessException, ServiceException {
		String result = "";
		try {
			result = ((RilLcmDao) DaoLocator.getDao(dao))
					.getScreenPopData(lcmCallKey);

		} catch (Exception e) {
			logger.error(
					"Exception catched while retrieving Screen PopUp Data", e);
			throw new ServiceException(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public String getMandatoryBussFields(String lcmCallKey)
			throws DataAccessException, ServiceException {
		String result = "";
		try {
			result = ((RilLcmDao) DaoLocator.getDao(dao))
					.getMandatoryBussFields(lcmCallKey);

		} catch (Exception e) {
			logger.error(
					"Exception catched while retrieving Mandatory Buss Fields",
					e);
			throw new ServiceException(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public int generateListInfo(String campaignId) throws DataAccessException,
			ServiceException {
		int result;
		try {
			result = ((RilLcmDao) DaoLocator.getDao(dao))
					.generateListInfo(campaignId);

		} catch (Exception e) {
			logger.error(
					"Exception catched while retrieving Mandatory Buss Fields",
					e);
			throw new ServiceException(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public void addContact(String campaignId, String callType,
			String callStartDateTime, String callEndDateTime,
			String bussinessFields, String priority, String modes,
			String agentId, String smsData, String mailSubject, String mailMsg,
			String mailAttachment, String zipCode, String contactDetail,
			String listId) throws DataAccessException, ServiceException {

		try {
			int priorit = Integer.parseInt((priority != null
					&& !priority.equalsIgnoreCase("") ? priority.toString()
					.trim() : "0"));
			int listID = Integer.parseInt((listId != null
					&& !listId.equalsIgnoreCase("") ? listId.toString().trim()
					: "0"));
			((RilLcmDao) DaoLocator.getDao(dao)).addContact(campaignId,
					callType, callStartDateTime, callEndDateTime,
					bussinessFields, priorit, modes, agentId, smsData,
					mailSubject, mailMsg, mailAttachment, zipCode,
					contactDetail, listID);

		} catch (Exception e) {
			logger.error("Exception catched while retrieving Contact", e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<String> getCallGuideByGroupName(String groupName)
			throws DataAccessException, ServiceException {
		List<String> result;
		try {
			result = ((RilLcmDao) DaoLocator.getDao(dao))
					.getCallGuideByGroupName(groupName);

		} catch (Exception e) {
			logger.error(
					"Exception catched while retrieving Call Guide by Group Name",
					e);
			throw new ServiceException(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public List<LcmBOGroupParent> getBOGroupParent(String callKey)
			throws DataAccessException, ServiceException {

		List<LcmBOGroupParent> result;

		try {
			result = ((RilLcmDao) DaoLocator.getDao(dao))
					.getBOGroupParent(callKey);

		} catch (Exception e) {
			logger.error("Exception catched while retrieving BOGroupParent", e);
			throw new ServiceException(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public List<LcmBusinessOutcomeDetails> getBusinessOutcomeDetails(
			String callKey) throws DataAccessException, ServiceException {

		List<LcmBusinessOutcomeDetails> result;

		try {
			result = ((RilLcmDao) DaoLocator.getDao(dao))
					.getBusinessOutcomeDetails(callKey);

		} catch (Exception e) {
			logger.error(
					"Exception catched while retrieving Business Outcome Details",
					e);
			throw new ServiceException(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public List<String> getCampaignStaticBusinessFields(String campaignName)
			throws DataAccessException, ServiceException {

		List<String> listOfStaticBusinessFields;
		try {
			listOfStaticBusinessFields = ((RilLcmDao) DaoLocator.getDao(dao))
					.getCampaignStaticBusinessFields(campaignName);

		} catch (Exception e) {
			logger.error(
					"Exception catched while retrieving Campaign Static Business Fields",
					e);
			throw new ServiceException(e.getMessage(), e);
		}
		return listOfStaticBusinessFields;
	}

	@Override
	public List<LcmModesWithCValue> getContactModes(String lcmKey)
			throws DataAccessException, ServiceException {

		List<LcmModesWithCValue> listOfModesWithCValues;
		try {
			listOfModesWithCValues = ((RilLcmDao) DaoLocator.getDao(dao))
					.getContactModes(lcmKey);

		} catch (Exception e) {
			logger.error("Exception catched while retrieving Contact Modes", e);
			throw new ServiceException(e.getMessage(), e);
		}
		return listOfModesWithCValues;
	}

	@Override
	public HashMap<String, String> getLcmAllContactDetail(String lcmCallKey,String pAgentID,long requestId)
			throws DataAccessException, ServiceException {

		HashMap<String, String> returnMap = null;
		try {

			List<LcmContactDetail> returnList = ((RilLcmDao) DaoLocator
					.getDao(dao)).getLcmContactDetail(lcmCallKey,pAgentID,requestId);
			if (returnList != null && returnList.size() > 0) {
				returnMap = new HashMap<String, String>();
				for (LcmContactDetail contactDetail : returnList) 
				{
					returnMap.put(contactDetail.getFieldName(),contactDetail.getValue());
					
				}
				if (returnMap.containsKey("ServiceRequestID") && returnMap.get("ServiceRequestID") != null)
				{
					returnMap.put("CampaignType", "SR");
				}
				logger.debug("Lcm All Contact Details Map -> " + returnMap);
			}

		} catch (Exception e) {
			logger.error(
					"Exception catched while retrieving Lcm Contact Details", e);
			throw new ServiceException(e.getMessage(), e);
		}

		return returnMap;
	}

	public SDLCampaign getSDLCampaignDetails(String lcmCallKey)
			throws DataAccessException, ServiceException {

		SDLCampaign campaign = null;
		try {

			campaign = ((RilLcmDao) DaoLocator.getDao(dao))
					.getSDLCampaignDetails(lcmCallKey,-1000);

		} catch (Exception e) {
			logger.error(
					"Exception catched while retrieving Lcm Contact Details", e);
			throw new ServiceException(e.getMessage(), e);
		}

		return campaign;
	}
}
