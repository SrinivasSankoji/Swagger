package com.isuite.rjil.iagent.jiomoney.rpc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

//import com.isuite.rjil.iagent.common.SDLCampaign;
//import com.isuite.rjil.iagent.common.ServiceResponse;
//import com.isuite.rjil.iagent.common.lcm.LcmBOGroupParent;
//import com.isuite.rjil.iagent.common.lcm.LcmBusinessOutcome;
//import com.isuite.rjil.iagent.common.lcm.LcmBusinessOutcomeDetails;
//import com.isuite.rjil.iagent.common.lcm.LcmContactDetail;
//import com.isuite.rjil.iagent.common.lcm.LcmModesWithCValue;
//import com.isuite.rjil.iagent.common.lcm.LcmPCBList;
//import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;
//import com.isuite.rjil.iagent.services.RilLcmService;
//import com.isuite.rjil.iagent.services.ServiceLocator;
//import com.isuite.rjil.iagent.util.LOVUtil;















import com.isuite.rjil.iagent.jiomoney.common.SDLCampaign;
import com.isuite.rjil.iagent.jiomoney.common.ServiceResponse;
import com.isuite.rjil.iagent.jiomoney.common.lcm.LcmBOGroupParent;
import com.isuite.rjil.iagent.jiomoney.common.lcm.LcmBusinessOutcome;
import com.isuite.rjil.iagent.jiomoney.common.lcm.LcmBusinessOutcomeDetails;
import com.isuite.rjil.iagent.jiomoney.common.lcm.LcmContactDetail;
import com.isuite.rjil.iagent.jiomoney.common.lcm.LcmModesWithCValue;
import com.isuite.rjil.iagent.jiomoney.common.lcm.LcmPCBList;
import com.isuite.rjil.iagent.jiomoney.services.RilLcmService;
import com.isuite.rjil.iagent.jiomoney.services.ServiceLocator;
import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;
import com.isuite.rjil.iagent.jiomoney.util.Util;

import flex.messaging.io.amf.ASObject;

public class RilLcmRpc {

	private static final Logger logger = Logger.getLogger(RilLcmRpc.class);
	private static final String service = "RilLcmService";
	private static Logger soapLogger = Logger.getLogger(LOVUtil.SOAPLOGGER);

	public ServiceResponse getLcmContactDetails(String lcmCallKey,String pAgentID) {

		ServiceResponse response = null;
		String lClassName =  this.getClass().getName();
		String lMethodName = "getLcmContactDetails";
		soapLogger.info("************************");
		long requestId = Util.getRequestId();
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+pAgentID+"|["+lClassName +"."+lMethodName+" ]| Start");
		
		try {

			List<LcmContactDetail> returnList = ((RilLcmService) ServiceLocator.getService(service)).getLcmContactDetail(lcmCallKey,pAgentID,requestId);

			response = new ServiceResponse(returnList);

		} catch (Throwable e) {
			logger.error(
					"Exception catched while retrieveing Lcm contact Details",
					e);
			response = new ServiceResponse(1, e.getMessage());
		}
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+pAgentID+"|["+lClassName +"."+lMethodName+"] | End");
		soapLogger.info("************************");
		return response;
	}

	public ServiceResponse getLcmAllContactDetails(String lcmCallKey, String pAgentId) 
	{
		String lClassName =  this.getClass().getName();
		String lMethodName = "getLcmAllContactDetails";
		soapLogger.info("************************");
		long requestId = Util.getRequestId();
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+pAgentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		ServiceResponse response = null;
		try {

			HashMap<String, String> returnObject = ((RilLcmService) ServiceLocator
					.getService(service)).getLcmAllContactDetail(lcmCallKey,pAgentId,requestId);
			

			response = new ServiceResponse(returnObject);

		} catch (Throwable e) {
			logger.error(
					"Exception catched while retrieveing Lcm All contact Details",
					e);
			response = new ServiceResponse(1, e.getMessage());
		}
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+pAgentId+"|["+lClassName +"."+lMethodName+"] | End");
		soapLogger.info("************************");	
		return response;
	}

	public ServiceResponse getLcmBusinessOutcomes(String lcmCallKey) {

		ServiceResponse response = null;
		try {

			List<LcmBusinessOutcome> returnList = ((RilLcmService) ServiceLocator
					.getService(service)).getBusinessOutcome(lcmCallKey);
			response = new ServiceResponse(returnList);

		} catch (Throwable e) {
			logger.error(
					"Exception catched while retrieveing Lcm contact Details",
					e);
			response = new ServiceResponse(1, e.getMessage());
		}

		return response;
	}

	public ServiceResponse setLcmBusinessOutcome(String lcmCallKey,
			String outcome, String callId, String agentId) 
	{
		String lClassName =  this.getClass().getName();
		String lMethodName = "setLcmBusinessOutcome";
		soapLogger.info("************************");
		long requestId = Util.getRequestId();
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		ServiceResponse response = null;
		try 
		{
			((RilLcmService) ServiceLocator.getService(service)).setBusinessOutcome(lcmCallKey, outcome, callId, agentId,requestId);

			response = new ServiceResponse("Business Outcome Saved Successfully");

		} catch (Throwable e) 
		{
			logger.error(
					"Exception catched while retrieveing Lcm contact Details",
					e);
			response = new ServiceResponse(1, e.getMessage());
		}
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
		soapLogger.info("************************");
		return response;

	}

	/**
	 * 
	 * @param lcmCallKey
	 * @param businessOutcome
	 * @param callOutcome
	 * @param callId
	 * @param agentId
	 * @return
	 * 
	 * 
	 *         We are only setting the business outcome and hence the code for
	 *         setting Call Outcome has been committed.
	 */
	public ServiceResponse setCallAndBusinessOutcome(String lcmCallKey,
			String businessOutcome, String callOutcome, String callId,
			String agentId, String callBack, String callStartDate,
			String startTimeHr, String startTimeMin, String startTimeAm,
			String endTimeHr, String endTimeMin, String endTimeAm) {

		long starttime = System.currentTimeMillis();
		soapLogger
				.debug(" RilLcmRpc *********************************************** setCallAndBusinessOutcome (Time in millis)");
		soapLogger.debug(" StartTime(SV) 	: " + starttime);

		String startTime = "";
		String endTime = "";
		if (!callBack.equalsIgnoreCase("No")) {
			if (callStartDate == null || callStartDate.isEmpty()
					|| startTimeHr == null || startTimeHr.isEmpty()
					|| startTimeMin == null || startTimeMin.isEmpty()
					|| startTimeAm == null || startTimeAm.isEmpty()
					|| endTimeHr == null || endTimeHr.isEmpty()
					|| endTimeMin == null || endTimeMin.isEmpty()
					|| endTimeAm == null || endTimeAm.isEmpty()) {

				return new ServiceResponse(1,
						"Please select the Call Date,Start and End Time");

			} else {
				
				if (callStartDate != null && !callStartDate.isEmpty()) {

					SimpleDateFormat currentFormat = new SimpleDateFormat("dd/MM/yyyy");
					SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");

					try {
						Date newDate = currentFormat.parse(callStartDate);
						
						String toDate = newFormat.format(newDate);
						Date callDate = newFormat.parse(toDate);
						
						String todayDate = newFormat.format(new Date());
						Date todayDateFormat = newFormat.parse(todayDate);

						if (callDate.before(todayDateFormat)
								&& !todayDate.equals(callDate)) {
							return new ServiceResponse(
									1,
									"Please enter a valid Call Date, Call date can not be earlier than Today Date");
						}

					} catch (ParseException e) {
						logger.error(
								"Exception catched while parsing Call date",
								e);
						return new ServiceResponse(1, e.getMessage());
					}
				}
				
				
				int startHr = Integer.parseInt(startTimeHr);
				
				if (startTimeAm.equalsIgnoreCase("PM")) {
					
					if(startHr!=12){
						startHr = startHr + 12;
					}
					startTime = startHr + ":" + startTimeMin + ":00";
				} else {
					
					if(startHr==12){
						startHr = startHr - 12;
					}
					
					startTime = startHr + ":" + startTimeMin + ":00";
				}
				
				int endHr = Integer.parseInt(endTimeHr);
				if (endTimeAm.equalsIgnoreCase("PM")) {
					
					if(endHr!=12){
						endHr = endHr + 12;
					}
					endTime = endHr + ":" + endTimeMin + ":00";
				} else {
					if(endHr==12){
						endHr = endHr - 12;
					}

					endTime = endHr + ":" + endTimeMin + ":00";
				}
			}

		}

		ServiceResponse response = null;
		try {
			((RilLcmService) ServiceLocator.getService(service))
					.setBusinessOutcome(lcmCallKey, businessOutcome, callId,
							agentId,-1000);

			String mode = "";
			if (lcmCallKey != null && !lcmCallKey.isEmpty()) {
				String[] callArray = lcmCallKey.split("\\|");
				if (callArray != null && callArray.length > 0) {
					mode = callArray[1];

				}
			}
			// Adding 7 days in callStartDate to get callEndDate
			String callEndDate = getEndDateByAdding7days(callStartDate);
			if (callBack.equalsIgnoreCase("Normal CallBack")) {
				((RilLcmService) ServiceLocator.getService(service))
						.setPersonalCallback(lcmCallKey, callStartDate,
								callEndDate, startTime, endTime, mode, "LCM",
								"");

			} else if (callBack.equalsIgnoreCase("Personal CallBack")) {
				((RilLcmService) ServiceLocator.getService(service))
						.setPersonalCallback(lcmCallKey, callStartDate,
								callEndDate, startTime, endTime, mode,
								agentId, "");
			}

			// ((RilLcmService) ServiceLocator.getService(service))
			// .setCallOutcome(lcmCallKey, callOutcome, ",", callId,
			// agentId);

			response = new ServiceResponse(
					"Business Outcome Saved Successfully");

		} catch (Throwable e) {
			logger.error(
					"Exception catched while retrieveing Lcm contact Details",
					e);
			response = new ServiceResponse(1, e.getMessage());
		}

		long endtime = System.currentTimeMillis();
		soapLogger
				.debug(" ________________________________________________________________________ ");
		soapLogger.debug(" EndTime(SV) 		: " + endtime);
		soapLogger.debug(" TimeDiff(SV) 	: " + (endtime - starttime));
		soapLogger
				.debug(" ****************************************************************************** ");

		return response;

	}

	private String getEndDateByAdding7days(String callStartDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(callStartDate));
		} catch (ParseException e) {
			logger.error(
					"Exception catched while retrieveing Lcm Call Outcomes ", e);
		}
		c.add(Calendar.DATE, 7); // Adding 7 days
		String output = sdf.format(c.getTime());
		return output;
	}

	public ServiceResponse getCallOutcomes() {
		ServiceResponse response = null;
		try {

			List<LcmBusinessOutcome> returnList = ((RilLcmService) ServiceLocator
					.getService(service)).getCallOutcome();
			response = new ServiceResponse(returnList);

		} catch (Throwable e) {
			logger.error(
					"Exception catched while retrieveing Lcm Call Outcomes ", e);
			response = new ServiceResponse(1, e.getMessage());
		}

		return response;
	}

	public ServiceResponse setLcmCallOutcome(String lcmcallKey, String outcome,
			String dncInfo, String callId, String agentId) {

		ServiceResponse response = null;
		try {
			((RilLcmService) ServiceLocator.getService(service))
					.setCallOutcome(lcmcallKey, outcome, dncInfo, callId,
							agentId,Util.getRequestId());

			response = new ServiceResponse("Call  Outcome Saved Successfully");

		} catch (Throwable e) {
			logger.error("Exception catched while Saving Lcm Call Outcome", e);
			response = new ServiceResponse(1, e.getMessage());
		}

		return response;

	}

	public ServiceResponse getBusinessFieldValueByFieldName(String lcmKey,
			String fieldName) {

		long startTime = System.currentTimeMillis();
		soapLogger
				.debug(" RilLcmRpc ********** getBusinessFieldValueByFieldName ******** "
						+ fieldName + "***** (Time in millis)");
		soapLogger.debug(" StartTime(SV) 	: " + startTime);

		ServiceResponse response = null;
		try {

			String toReturn = ((RilLcmService) ServiceLocator
					.getService(service)).getValueFromLcmBusinessFields(lcmKey,
					fieldName);

			if (toReturn != null && !toReturn.isEmpty()) {

				response = new ServiceResponse(toReturn);
			} else {
				response = new ServiceResponse(1, "No Field Found");
			}

		} catch (Throwable e) {
			logger.error(
					"Exception catched getting Business Field value By FieldName",
					e);
			response = new ServiceResponse(1, e.getMessage());
		}

		long endTime = System.currentTimeMillis();
		soapLogger
				.debug(" ________________________________________________________________________ ");
		soapLogger.debug(" EndTime(SV) 		: " + endTime);
		soapLogger.debug(" TimeDiff(SV) 	: " + (endTime - startTime));
		soapLogger
				.debug(" ****************************************************************************** ");

		return response;

	}

	/**
 * 
 * */

	public ServiceResponse getModes(String lcmKey) {
		ServiceResponse response = null;
		try {

			List<LcmBusinessOutcome> returnList = ((RilLcmService) ServiceLocator
					.getService(service)).getModes(lcmKey);
			response = new ServiceResponse(returnList);

		} catch (Throwable e) {
			logger.error("Exception catched while retrieveing Modes ", e);
			response = new ServiceResponse(1, e.getMessage());
		}

		return response;
	}

	public ServiceResponse getContactModes(String strKey) {
		ServiceResponse response = null;
		try {

			List<LcmModesWithCValue> returnList = ((RilLcmService) ServiceLocator
					.getService(service)).getContactModes(strKey);
			response = new ServiceResponse(returnList);

		} catch (Throwable e) {
			logger.error("Exception catched while retrieveing ContactModes ", e);
			response = new ServiceResponse(1, e.getMessage());
		}

		return response;
	}

	public ServiceResponse getCampaignModes(String strKey) {
		ServiceResponse response = null;
		try {

			List<LcmBusinessOutcome> returnList = ((RilLcmService) ServiceLocator
					.getService(service)).getCampaignModes(strKey);
			response = new ServiceResponse(returnList);

		} catch (Throwable e) {
			logger.error("Exception catched while retrieveing CampaignModes ",
					e);
			response = new ServiceResponse(1, e.getMessage());
		}

		return response;
	}

	public ServiceResponse setBusinessOutcomeWithComment(String lcmKey,
			String outCome, String callId, String userId, String agentComment,
			String targetAmount) {

		ServiceResponse response = null;
		try {

			((RilLcmService) ServiceLocator.getService(service))
					.setBusinessOutcomeWithComment(lcmKey, outCome, callId,
							userId, agentComment, targetAmount);
			response = new ServiceResponse(
					"Business OutcomeWithComments Saved Successfully");

		} catch (Throwable e) {
			logger.error(
					"Exception catched while Saving Lcm Business OutcomeWithComments",
					e);
			response = new ServiceResponse(1, e.getMessage());
		}
		return response;
	}

	public ServiceResponse setCallOutcomeWithComments(String lcmKey,
			String outcome, String dncInfo, String callId, String userId,
			String agentComment, String targetAmount) {

		ServiceResponse response = null;
		try {
			((RilLcmService) ServiceLocator.getService(service))
					.setCallOutcomeWithComments(lcmKey, outcome, dncInfo,
							callId, userId, agentComment, targetAmount);
			response = new ServiceResponse(
					"Call  Outcome with Comments Saved Successfully");

		} catch (Throwable e) {
			logger.error(
					"Exception catched while Saving Lcm Call Outcome with Comments",
					e);
			response = new ServiceResponse(1, e.getMessage());
		}
		return response;
	}

	public ServiceResponse setCallback(String lcmCallKey,
			String callBackStartDate, String callBackEndDate,
			String callBackStartTime, String callBackEndTime, String modeId,
			String callId) {

		long startTime = System.currentTimeMillis();
		soapLogger
				.debug(" RilLcmRpc *************************************************** setCallback (Time in millis)");
		soapLogger.debug(" StartTime(SV) 	: " + startTime);

		ServiceResponse response = null;
		try {
			((RilLcmService) ServiceLocator.getService(service)).setCallback(
					lcmCallKey, callBackStartDate, callBackEndDate,
					callBackStartTime, callBackEndTime, modeId, callId);
			response = new ServiceResponse("Callback Saved Successfully");

		} catch (Throwable e) {
			logger.error("Exception catched while Saving Callback", e);
			response = new ServiceResponse(1, e.getMessage());
		}

		long endTime = System.currentTimeMillis();
		soapLogger
				.debug(" ________________________________________________________________________ ");
		soapLogger.debug(" EndTime(SV) 		: " + endTime);
		soapLogger.debug(" TimeDiff(SV) 	: " + (endTime - startTime));
		soapLogger
				.debug(" ****************************************************************************** ");

		return response;
	}

	public ServiceResponse setCallbackWithComment(String lcmCallKey,
			String callBackStartDate, String callBackEndDate,
			String callBackStartTime, String callBackEndTime, String nModeId,
			String callId, String agentComment, String targetAmount) {

		ServiceResponse response = null;
		try {
			((RilLcmService) ServiceLocator.getService(service))
					.setCallbackWithComment(lcmCallKey, callBackStartDate,
							callBackEndDate, callBackStartTime,
							callBackEndTime, nModeId, callId, agentComment,
							targetAmount);
			response = new ServiceResponse(
					"CallbackWithComment Saved Successfully");

		} catch (Throwable e) {
			logger.error("Exception catched while Saving CallbackWithComment",
					e);
			response = new ServiceResponse(1, e.getMessage());
		}
		return response;
	}

	public ServiceResponse setPersonalCallback(String lcmCallKey,
			String callBackStartDate, String callBackEndDate,
			String callBackStartTime, String callBackEndTime, String nModeId,
			String userId, String callId) {

		long startTime = System.currentTimeMillis();
		soapLogger
				.debug(" RilLcmRpc *************************************************** setPersonalCallback (Time in millis)");
		soapLogger.debug(" StartTime(SV) 	: " + startTime);

		ServiceResponse response = null;
		try {
			((RilLcmService) ServiceLocator.getService(service))
					.setPersonalCallback(lcmCallKey, callBackStartDate,
							callBackEndDate, callBackStartTime,
							callBackEndTime, nModeId, userId, callId);
			response = new ServiceResponse(
					"Personal Callback Saved Successfully");

		} catch (Throwable e) {
			logger.error("Exception catched while Saving Personal Callback", e);
			response = new ServiceResponse(1, e.getMessage());
		}

		long endTime = System.currentTimeMillis();
		soapLogger
				.debug(" ________________________________________________________________________ ");
		soapLogger.debug(" EndTime(SV) 		: " + endTime);
		soapLogger.debug(" TimeDiff(SV) 	: " + (endTime - startTime));
		soapLogger
				.debug(" ****************************************************************************** ");

		return response;
	}

	public ServiceResponse setPersonalCallbackWithComment(String lcmCallKey,
			String callBackStartDate, String callBackEndDate,
			String callBackStartTime, String callBackEndTime, String nModeId,
			String callId, String userId, String agentComment,
			String targetAmount) {

		ServiceResponse response = null;
		try {
			((RilLcmService) ServiceLocator.getService(service))
					.setPersonalCallbackWithComment(lcmCallKey,
							callBackStartDate, callBackEndDate,
							callBackStartTime, callBackEndTime, nModeId,
							callId, userId, agentComment, targetAmount);
			response = new ServiceResponse(
					"Personal Callback With Comment Saved Successfully");

		} catch (Throwable e) {
			logger.error(
					"Exception catched while Saving Personal Callback With Comment",
					e);
			response = new ServiceResponse(1, e.getMessage());
		}
		return response;
	}

	public ServiceResponse setNewModeNumber(String lcmCallKey, String modeId,
			String contactNo, String deleteExistingModes) {

		ServiceResponse response = null;
		try {
			((RilLcmService) ServiceLocator.getService(service))
					.setNewModeNumber(lcmCallKey, modeId, contactNo,
							deleteExistingModes);
			response = new ServiceResponse("New ModeNumber Saved Successfully");

		} catch (Throwable e) {
			logger.error("Exception catched while Saving New ModeNumber", e);
			response = new ServiceResponse(1, e.getMessage());
		}
		return response;
	}

	public ServiceResponse getCallGuideName(String lcmCallKey) {
		ServiceResponse response = null;
		try {

			String returnList = ((RilLcmService) ServiceLocator
					.getService(service)).getCallGuideName(lcmCallKey);
			response = new ServiceResponse(returnList);

		} catch (Throwable e) {
			logger.error("Exception catched while retrieveing Call GuideName ",
					e);
			response = new ServiceResponse(1, e.getMessage());
		}

		return response;
	}

	public ServiceResponse getPersonalCallbackList(String userId,
			String fromDate, String toDate, String stNo, String noOfRecords) {
		ServiceResponse response = null;
		try {

			List<LcmPCBList> returnList = ((RilLcmService) ServiceLocator
					.getService(service)).getPersonalCallbackList(userId,
					fromDate, toDate, stNo, noOfRecords);
			response = new ServiceResponse(returnList);

		} catch (Throwable e) {
			logger.error(
					"Exception catched while retrieveing Personal Callback List ",
					e);
			response = new ServiceResponse(1, e.getMessage());
		}

		return response;
	}

	public ServiceResponse setContactStatus(String campaignId, String contactId) {

		ServiceResponse response = null;
		try {
			((RilLcmService) ServiceLocator.getService(service))
					.setContactStatus(campaignId, contactId);
			response = new ServiceResponse("Contact Status Saved Successfully");

		} catch (Throwable e) {
			logger.error("Exception catched while Saving Contact Status", e);
			response = new ServiceResponse(1, e.getMessage());
		}
		return response;
	}

	public ServiceResponse setContactBusinessFields(String lcmCallKey,
			String xmlData) {

		ServiceResponse response = null;
		try {
			((RilLcmService) ServiceLocator.getService(service))
					.setContactBusinessFields(lcmCallKey, xmlData);
			response = new ServiceResponse(
					"Contact Business Fields Saved Successfully");

		} catch (Throwable e) {
			logger.error(
					"Exception catched while Saving Contact Business Fields", e);
			response = new ServiceResponse(1, e.getMessage());
		}
		return response;
	}

	public ServiceResponse setContactOutcome(String contactId,
			String campaignId, String modeDescription, String outcomeId) {

		ServiceResponse response = null;
		try {
			((RilLcmService) ServiceLocator.getService(service))
					.setContactOutcome(contactId, campaignId, modeDescription,
							outcomeId);
			response = new ServiceResponse("Contact Outcome Saved Successfully");

		} catch (Throwable e) {
			logger.error("Exception catched while Saving Contact Outcome", e);
			response = new ServiceResponse(1, e.getMessage());
		}
		return response;
	}

	public ServiceResponse generateListInfo(String campaignId) {
		ServiceResponse response = null;
		try {

			int returnList = ((RilLcmService) ServiceLocator
					.getService(service)).generateListInfo(campaignId);
			response = new ServiceResponse(returnList);

		} catch (Throwable e) {
			logger.error(
					"Exception catched while retrieveing generateListInfo ", e);
			response = new ServiceResponse(1, e.getMessage());
		}

		return response;
	}

	public ServiceResponse addContact(String campaignId, String callType,
			String callStartDateTime, String callEndDateTime,
			String bussinessFields, String priority, String modes,
			String agentId, String smsData, String mailSubject, String mailMsg,
			String mailAttachment, String zipCode, String contactDetail,
			String listId) {

		ServiceResponse response = null;
		try {
			((RilLcmService) ServiceLocator.getService(service)).addContact(
					campaignId, callType, callStartDateTime, callEndDateTime,
					bussinessFields, priority, modes, agentId, smsData,
					mailSubject, mailMsg, mailAttachment, zipCode,
					contactDetail, listId);
			response = new ServiceResponse("Contact added Saved Successfully");

		} catch (Throwable e) {
			logger.error("Exception catched while Saving Contact", e);
			response = new ServiceResponse(1, e.getMessage());
		}
		return response;
	}

	public ServiceResponse getCallGuideByGroupName(String groupName) {
		ServiceResponse response = null;
		try {

			List<String> returnList = ((RilLcmService) ServiceLocator
					.getService(service)).getCallGuideByGroupName(groupName);
			response = new ServiceResponse(returnList);

		} catch (Throwable e) {
			logger.error(
					"Exception catched while retrieveing CallGuide By GroupName ",
					e);
			response = new ServiceResponse(1, e.getMessage());
		}

		return response;
	}

	public ServiceResponse getBOGroupParent(String callKey) {
		ServiceResponse response = null;
		try {

			List<LcmBOGroupParent> returnList = ((RilLcmService) ServiceLocator
					.getService(service)).getBOGroupParent(callKey);
			response = new ServiceResponse(returnList);

		} catch (Throwable e) {
			logger.error("Exception catched while retrieveing BOGroupParent ",
					e);
			response = new ServiceResponse(1, e.getMessage());
		}

		return response;
	}

	public ServiceResponse getBusinessOutcomeDetails(String strKey) {
		ServiceResponse response = null;
		try {

			List<LcmBusinessOutcomeDetails> returnList = ((RilLcmService) ServiceLocator
					.getService(service)).getBusinessOutcomeDetails(strKey);
			response = new ServiceResponse(returnList);

		} catch (Throwable e) {
			logger.error(
					"Exception catched while retrieveing Business Outcome Details ",
					e);
			response = new ServiceResponse(1, e.getMessage());
		}

		return response;
	}

	public ServiceResponse getCampaigns() {
		ServiceResponse response = null;
		try {

			String returnList = ((RilLcmService) ServiceLocator
					.getService(service)).getCampaigns();
			response = new ServiceResponse(returnList);

		} catch (Throwable e) {
			logger.error("Exception catched while retrieveing Campaigns ", e);
			response = new ServiceResponse(1, e.getMessage());
		}

		return response;
	}

	public ServiceResponse getCampaignBusinessFields(String campaignName) {
		ServiceResponse response = null;
		try {

			String returnList = ((RilLcmService) ServiceLocator
					.getService(service))
					.getCampaignBusinessFields(campaignName);
			response = new ServiceResponse(returnList);

		} catch (Throwable e) {
			logger.error(
					"Exception catched while retrieveing Campaign Business Fields ",
					e);
			response = new ServiceResponse(1, e.getMessage());
		}

		return response;
	}

	public ServiceResponse getBusinessOutcomeforCampaign(String campaignName) {

		long startTime = System.currentTimeMillis();
		soapLogger
				.debug(" RilLcmRpc ************************************** getBusinessOutcomeforCampaign (Time in millis)");
		soapLogger.debug(" StartTime(SV) 	: " + startTime);

		ServiceResponse response = null;
		try {

			String returnList = ((RilLcmService) ServiceLocator
					.getService(service))
					.getBusinessOutcomeforCampaign(campaignName);
			response = new ServiceResponse(returnList);

		} catch (Throwable e) {
			logger.error(
					"Exception catched while retrieveing Business Outcome for Campaign ",
					e);
			response = new ServiceResponse(1, e.getMessage());
		}

		return response;
	}

	public ServiceResponse getContact(String campaignName, String phoneNo,
			String condition, String noOfRows) {
		ServiceResponse response = null;
		try {

			String returnList = ((RilLcmService) ServiceLocator
					.getService(service)).getContact(campaignName, phoneNo,
					condition, noOfRows);
			response = new ServiceResponse(returnList);

		} catch (Throwable e) {
			logger.error("Exception catched while retrieveing Contact ", e);
			response = new ServiceResponse(1, e.getMessage());
		}

		return response;
	}

	public ServiceResponse getCommonBussFields(String campaignId) {
		ServiceResponse response = null;
		try {

			String returnList = ((RilLcmService) ServiceLocator
					.getService(service)).getCommonBussFields(campaignId);
			response = new ServiceResponse(returnList);

		} catch (Throwable e) {
			logger.error(
					"Exception catched while retrieveing Common Buss Fields ",
					e);
			response = new ServiceResponse(1, e.getMessage());
		}

		return response;
	}

	public ServiceResponse getBussOutcomeForGroup(String outcomeGroupName) {
		ServiceResponse response = null;
		try {

			List<LcmBusinessOutcome> returnList = ((RilLcmService) ServiceLocator
					.getService(service))
					.getBussOutcomeForGroup(outcomeGroupName);
			response = new ServiceResponse(returnList);

		} catch (Throwable e) {
			logger.error(
					"Exception catched while retrieveing BussOutcome For Group ",
					e);
			response = new ServiceResponse(1, e.getMessage());
		}

		return response;
	}

	public ServiceResponse getContactAcrossCampaign(String phoneNo,
			String condition, String noOfRows) {
		ServiceResponse response = null;
		try {

			String returnList = ((RilLcmService) ServiceLocator
					.getService(service)).getContactAcrossCampaign(phoneNo,
					condition, noOfRows);
			response = new ServiceResponse(returnList);

		} catch (Throwable e) {
			logger.error(
					"Exception catched while retrieveing Contact Across Campaign ",
					e);
			response = new ServiceResponse(1, e.getMessage());
		}

		return response;
	}

	public ServiceResponse getCallHistory(String lcmCallKey) {
		ServiceResponse response = null;
		try {

			String returnList = ((RilLcmService) ServiceLocator
					.getService(service)).getCallHistory(lcmCallKey);
			response = new ServiceResponse(returnList);

		} catch (Throwable e) {
			logger.error("Exception catched while retrieveing Call History ", e);
			response = new ServiceResponse(1, e.getMessage());
		}

		return response;
	}

	public ServiceResponse getScreenPopData(String lcmCallKey) {
		ServiceResponse response = null;
		try {

			String returnList = ((RilLcmService) ServiceLocator
					.getService(service)).getScreenPopData(lcmCallKey);
			response = new ServiceResponse(returnList);

		} catch (Throwable e) {
			logger.error("Exception catched while retrieveing ScreenPopData ",
					e);
			response = new ServiceResponse(1, e.getMessage());
		}

		return response;
	}

	public ServiceResponse getMandatoryBussFields(String lcmCallKey) {

		ServiceResponse response = null;

		try {

			String returnList = ((RilLcmService) ServiceLocator
					.getService(service)).getMandatoryBussFields(lcmCallKey);
			response = new ServiceResponse(returnList);

		} catch (Throwable e) {
			logger.error(
					"Exception catched while retrieveing Mandatory Buss Fields ",
					e);
			response = new ServiceResponse(1, e.getMessage());
		}

		return response;
	}
	

	@SuppressWarnings("unchecked")
	public ServiceResponse getLcmCallkeyFromOutboundVariables(
			Object callVariableObj) 
	{
		long startTime = System.currentTimeMillis();
		soapLogger.debug(" RilLcmRpc *************************************************** getLcmCallkeyFromOutboundVariables (Time in millis)");
		soapLogger.debug(" StartTime(SV) 	: " + startTime);

		ServiceResponse response = null;
		try 
		{
			if(callVariableObj == null)
			{
				response = new ServiceResponse(1,"Unable to Retrieve LCM Call Key : Call Veriables are Empty");
			}
			else
			{	
				String toReturn = "";
				List<ASObject> callVaraibles = (ArrayList<ASObject>) callVariableObj;
				soapLogger.debug("Call Veriables For Outbound Call : "+callVaraibles);
				for (ASObject obj : callVaraibles)
				{
	
					if (obj.get("key") != null
							&& (obj.get("key") + "")
									.equalsIgnoreCase("BAAccountNumber")) {
						toReturn = obj.get("value") + "";
						break;
					}
				}
	
				if (toReturn != null && !toReturn.isEmpty()) 
				{
					response = new ServiceResponse(toReturn);
				} else 
				{
					response = new ServiceResponse(1,
							"Unable to Retrieve LCM Call Key");
				}
			}

		} catch (Throwable e) {
			logger.error("Exception catched while retrieveing LCM Call Key ", e);
			response = new ServiceResponse(1, e.getMessage());
		}

		long endTime = System.currentTimeMillis();
		soapLogger
				.debug(" ________________________________________________________________________ ");
		soapLogger.debug(" EndTime(SV) 		: " + endTime);
		soapLogger.debug(" TimeDiff(SV) 	: " + (endTime - startTime));
		soapLogger
				.debug(" ****************************************************************************** ");

		return response;

	}

	public ServiceResponse getCallingNumberFromLCMcallKey(String lcmKey,
			String fieldName) {
		ServiceResponse response = null;

		try {

			String toReturn = ((RilLcmService) ServiceLocator
					.getService(service)).getValueFromLcmBusinessFields(lcmKey,
					fieldName);

			if (toReturn != null && !toReturn.isEmpty()) {

				if (toReturn.startsWith("115")) {
					toReturn = toReturn.substring(3);
				}

				response = new ServiceResponse(toReturn);
			} else {
				response = new ServiceResponse(1, "No Field Found");
			}

		} catch (Throwable e) {
			logger.error("Exception catched while retrieveing LCM Call Key ", e);
			response = new ServiceResponse(1, e.getMessage());
		}
		return response;
	}

	public ServiceResponse getSDLCampaignDetails(String lcmCallKey) {
		ServiceResponse response = null;
		try {

			SDLCampaign campaign = ((RilLcmService) ServiceLocator
					.getService(service)).getSDLCampaignDetails(lcmCallKey);

			response = new ServiceResponse(campaign);

		} catch (Throwable e) {
			logger.error(
					"Exception catched while retrieveing Lcm contact Details",
					e);
			response = new ServiceResponse(1, e.getMessage());
		}

		return response;

	}
}
