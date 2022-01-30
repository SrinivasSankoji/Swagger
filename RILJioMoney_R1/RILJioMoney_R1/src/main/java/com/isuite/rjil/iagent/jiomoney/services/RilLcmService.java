package com.isuite.rjil.iagent.jiomoney.services;

import java.util.HashMap;
import java.util.List;

import com.isuite.rjil.iagent.jiomoney.common.SDLCampaign;
import com.isuite.rjil.iagent.jiomoney.common.lcm.LcmBOGroupParent;
import com.isuite.rjil.iagent.jiomoney.common.lcm.LcmBusinessOutcome;
import com.isuite.rjil.iagent.jiomoney.common.lcm.LcmBusinessOutcomeDetails;
import com.isuite.rjil.iagent.jiomoney.common.lcm.LcmContactDetail;
import com.isuite.rjil.iagent.jiomoney.common.lcm.LcmModesWithCValue;
import com.isuite.rjil.iagent.jiomoney.common.lcm.LcmPCBList;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.exception.ServiceException;

//import com.isuite.rjil.iagent.common.SDLCampaign;
//import com.isuite.rjil.iagent.common.lcm.LcmBOGroupParent;
//import com.isuite.rjil.iagent.common.lcm.LcmBusinessOutcome;
//import com.isuite.rjil.iagent.common.lcm.LcmBusinessOutcomeDetails;
//import com.isuite.rjil.iagent.common.lcm.LcmContactDetail;
//import com.isuite.rjil.iagent.common.lcm.LcmModesWithCValue;
//import com.isuite.rjil.iagent.common.lcm.LcmPCBList;
//import com.isuite.rjil.iagent.exception.DataAccessException;
//import com.isuite.rjil.iagent.exception.ServiceException;

public interface RilLcmService {

	public List<LcmContactDetail> getLcmContactDetail(String lcmCallKey,String pAgentID,long requestId)
			throws DataAccessException, ServiceException;
	
	public HashMap<String, String> getLcmAllContactDetail(String lcmCallKey,String pAgentId,long requestId)
			throws DataAccessException, ServiceException;

	public List<LcmBusinessOutcome> getBusinessOutcome(String lcmCallKey)
			throws DataAccessException, ServiceException;

	public void setBusinessOutcome(String lcmCallKey, String outcome,
			String callId, String agentId,long requestId) throws DataAccessException,
			ServiceException;

	public List<LcmBusinessOutcome> getCallOutcome()
			throws DataAccessException, ServiceException;

	public void setCallOutcome(String lcmcallKey, String outcome,
			String dncInfo, String callId, String agentId,long requestId)
			throws DataAccessException, ServiceException;

	public String getValueFromLcmBusinessFields(String lcmKey, String fieldName)
			throws DataAccessException, ServiceException;

	/**
	 * 
	 */
	public List<LcmBusinessOutcome> getBusinessOutcomeParent(String lcmCallKey,
			int parentBOId) throws DataAccessException, ServiceException;

	public List<LcmBusinessOutcome> getModes(String lcmCallKey)
			throws DataAccessException, ServiceException;

	public List<LcmModesWithCValue> getContactModes(String lcmKey)
			throws DataAccessException, ServiceException;

	public List<LcmBusinessOutcome> getCampaignModes(String lcmCallKey)
			throws DataAccessException, ServiceException;

	public void setBusinessOutcomeWithComment(String strAcNo, String outCome,
			String callId, String userId, String agentComment,
			String targetAmount) throws DataAccessException, ServiceException;

	public void setCallOutcomeWithComments(String strAcNo, String outCome,
			String dNCInfo, String callId, String userId, String agentComment,
			String targetAmount) throws DataAccessException, ServiceException;

	public void setCallback(String lcmCallKey, String callBackStartDate,
			String callBackEndDate, String callBackStartTime,
			String callBackEndTime, String modeId, String callId)
			throws DataAccessException, ServiceException;

	public void setCallbackWithComment(String lcmCallKey,
			String callBackStartDate, String callBackEndDate,
			String callBackStartTime, String callBackEndTime, String nModeId,
			String callId, String agentComment, String targetAmount)
			throws DataAccessException, ServiceException;

	public void setPersonalCallback(String lcmCallKey,
			String callBackStartDate, String callBackEndDate,
			String callBackStartTime, String callBackEndTime, String modeId,
			String userId, String callId) throws DataAccessException,
			ServiceException;

	public void setPersonalCallbackWithComment(String lcmCallKey,
			String callBackStartDate, String callBackEndDate,
			String callBackStartTime, String callBackEndTime, String modeId,
			String callId, String userId, String agentComment,
			String targetAmount) throws DataAccessException, ServiceException;

	public void setNewModeNumber(String lcmCallKey, String modelId,
			String contactNumber, String deleteExistingModes)
			throws DataAccessException, ServiceException;

	public String getCallGuideName(String lcmCallKey)
			throws DataAccessException, ServiceException;

	public List<LcmPCBList> getPersonalCallbackList(String userId,
			String fromDate, String toDate, String startingNo,
			String noOfRecords) throws DataAccessException, ServiceException;

	public String getCampaigns() throws DataAccessException, ServiceException;

	public String getCampaignBusinessFields(String campaignName)
			throws DataAccessException, ServiceException;

	public String getBusinessOutcomeforCampaign(String campaignName)
			throws DataAccessException, ServiceException;

	public String getContact(String campaignName, String phoneNo,
			String condition, String noOfRows) throws DataAccessException,
			ServiceException;

	public void setContactStatus(String campaignId, String contactId)
			throws DataAccessException, ServiceException;

	public void setContactBusinessFields(String lcmCallKey, String xmldata)
			throws DataAccessException, ServiceException;

	public void setContactOutcome(String contactId, String campaignId,
			String modeDescription, String outcomeId)
			throws DataAccessException, ServiceException;

	public String getCommonBussFields(String campaignId)
			throws DataAccessException, ServiceException;

	public List<LcmBusinessOutcome> getBussOutcomeForGroup(String outcomeGroup)
			throws DataAccessException, ServiceException;

	public String getContactAcrossCampaign(String phoneNo, String condition,
			String noOfRows) throws DataAccessException, ServiceException;

	public String getCallHistory(String lcmCallKey) throws DataAccessException,
			ServiceException;

	public String getScreenPopData(String lcmCallKey)
			throws DataAccessException, ServiceException;

	public String getMandatoryBussFields(String lcmCallKey)
			throws DataAccessException, ServiceException;

	public int generateListInfo(String campaignId) throws DataAccessException,
			ServiceException;

	public void addContact(String campaignId, String callType,
			String callStartDateTime, String callEndDateTime,
			String bussinessFields, String priority, String modes,
			String agentId, String smsData, String mailSubject, String mailMsg,
			String mailAttachment, String zipCode, String contactDetail,
			String listId) throws DataAccessException, ServiceException;

	public List<String> getCallGuideByGroupName(String groupName)
			throws DataAccessException, ServiceException;

	public List<LcmBOGroupParent> getBOGroupParent(String callKey)
			throws DataAccessException, ServiceException;

	public List<LcmBusinessOutcomeDetails> getBusinessOutcomeDetails(
			String callKey) throws DataAccessException, ServiceException;

	public List<String> getCampaignStaticBusinessFields(String campaignName)
			throws DataAccessException, ServiceException;

	public SDLCampaign getSDLCampaignDetails(String lcmCallKey)
			throws DataAccessException, ServiceException;
}
