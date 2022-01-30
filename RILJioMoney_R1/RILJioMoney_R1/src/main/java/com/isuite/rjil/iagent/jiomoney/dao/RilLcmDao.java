package com.isuite.rjil.iagent.jiomoney.dao;


import java.math.BigDecimal;
import java.util.List;

import com.isuite.rjil.iagent.jiomoney.common.SDLCampaign;
import com.isuite.rjil.iagent.jiomoney.common.lcm.LcmBOGroupParent;
import com.isuite.rjil.iagent.jiomoney.common.lcm.LcmBusinessOutcome;
import com.isuite.rjil.iagent.jiomoney.common.lcm.LcmBusinessOutcomeDetails;
import com.isuite.rjil.iagent.jiomoney.common.lcm.LcmModesWithCValue;
import com.isuite.rjil.iagent.jiomoney.common.lcm.LcmPCBList;
import com.isuite.rjil.iagent.jiomoney.common.lcm.LcmContactDetail;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;

public interface RilLcmDao {

	public List<LcmContactDetail> getLcmContactDetail(String lcmCallKey,String pAgentID,long requestId)
			throws DataAccessException;

	public List<LcmBusinessOutcome> getBusinessOutcome(String lcmCallKey,long requestId)
			throws DataAccessException;

	public void setBusinessOutcome(String lcmCallKey, String outcome,
			String callId, String agentId,long requestId) throws DataAccessException;

	public List<LcmBusinessOutcome> getCallOutcome() throws DataAccessException;

	public void setCallOutcome(String lcmcallKey, String outcome,
			String dncInfo, String callId, String agentId,long requestId)
			throws DataAccessException;

	public String getValueFromLcmBusinessFields(String lcmKey, String fieldName,long requestId)
			throws DataAccessException;

	/**
	 * 
	 */
	public List<LcmBusinessOutcome> getBusinessOutcomeParent(String lcmCallKey,
			int parentBOId) throws DataAccessException;

	public List<LcmBusinessOutcome> getModes(String lcmCallKey)
			throws DataAccessException;

	public List<LcmModesWithCValue> getContactModes(String lcmKey)
			throws DataAccessException;

	public List<LcmBusinessOutcome> getCampaignModes(String lcmCallKey)
			throws DataAccessException;

	public void setBusinessOutcomeWithComment(String strAcNo, String outCome,
			String callId, String userId, String agentComment,
			BigDecimal targetAmount) throws DataAccessException;

	public void setCallOutcomeWithComment(String strAcNo, String outCome,
			String dNCInfo, String callId, String userId, String agentComment,
			BigDecimal targetAmount) throws DataAccessException;

	public void setCallback(String lcmCallKey, String callBackStartDate,
			String callBackEndDate, String callBackStartTime,
			String callBackEndTime, int modeId, String callId)
			throws DataAccessException;

	public void setCallbackWithComment(String lcmCallKey,
			String callBackStartDate, String callBackEndDate,
			String callBackStartTime, String callBackEndTime, int nModeId,
			String callId, String agentComment, BigDecimal targetAmount)
			throws DataAccessException;

	public void setPersonalCallback(String lcmCallKey,
			String callBackStartDate, String callBackEndDate,
			String callBackStartTime, String callBackEndTime, int modeId,
			String userId, String callId) throws DataAccessException;

	public void setPersonalCallbackWithComment(String lcmCallKey,
			String callBackStartDate, String callBackEndDate,
			String callBackStartTime, String callBackEndTime, int modeId,
			String callId, String userId, String agentComment,
			BigDecimal targetAmount) throws DataAccessException;

	public void setNewModeNumber(String lcmCallKey, int modelId,
			String contactNumber, boolean deleteExistingModes)
			throws DataAccessException;

	public String getCallGuideName(String lcmCallKey)
			throws DataAccessException;

	public List<LcmPCBList> getPersonalCallbackList(String userId,
			String fromDate, String toDate, int startingNo, int noOfRecords)
			throws DataAccessException;

	public String getCampaigns() throws DataAccessException;

	public String getCampaignBusinessFields(String campaignName)
			throws DataAccessException;

	public String getBusinessOutcomeforCampaign(String campaignName)
			throws DataAccessException;

	public String getContact(String campaignName, String phoneNo,
			String condition, int noOfRows) throws DataAccessException;

	public void setContactStatus(String campaignId, int contactId)
			throws DataAccessException;

	public void setContactBusinessFields(String lcmCallKey, String xmldata)
			throws DataAccessException;

	public void setContactOutcome(String contactId, String campaignId,
			String modeDescription, String outcomeId)
			throws DataAccessException;

	public String getCommonBussFields(String campaignId)
			throws DataAccessException;

	public List<LcmBusinessOutcome> getBussOutcomeForGroup(String outcomeGroup)
			throws DataAccessException;

	public String getContactAcrossCampaign(String phoneNo, String condition,
			int noOfRows) throws DataAccessException;

	public String getCallHistory(String lcmCallKey) throws DataAccessException;

	public String getScreenPopData(String lcmCallKey)
			throws DataAccessException;

	public String getMandatoryBussFields(String lcmCallKey)
			throws DataAccessException;

	public int generateListInfo(String campaignId) throws DataAccessException;

	public void addContact(String campaignId, String callType,
			String callStartDateTime, String callEndDateTime,
			String bussinessFields, int priority, String modes, String agentId,
			String smsData, String mailSubject, String mailMsg,
			String mailAttachment, String zipCode, String contactDetail,
			int listId) throws DataAccessException;

	public List<String> getCallGuideByGroupName(String groupName)
			throws DataAccessException;

	public List<LcmBOGroupParent> getBOGroupParent(String callKey)
			throws DataAccessException;

	public List<LcmBusinessOutcomeDetails> getBusinessOutcomeDetails(
			String callKey) throws DataAccessException;

	public List<String> getCampaignStaticBusinessFields(String campaignName)
			throws DataAccessException;

	public SDLCampaign getSDLCampaignDetails(String lcmCallKey,long requestId)
			throws DataAccessException;
}
