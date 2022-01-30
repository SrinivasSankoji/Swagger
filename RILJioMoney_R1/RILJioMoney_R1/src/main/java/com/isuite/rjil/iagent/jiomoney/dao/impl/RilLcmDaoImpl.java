package com.isuite.rjil.iagent.jiomoney.dao.impl;

import java.io.StringReader;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.ws.Holder;

import lcm.org.tempuri.ArrayOfBOGroupParent;
import lcm.org.tempuri.ArrayOfBusinessOutcomeDetails;
import lcm.org.tempuri.ArrayOfKeyValuePairOfStringStaticFields;
import lcm.org.tempuri.ArrayOfModesWithCValue;
import lcm.org.tempuri.ArrayOfOutcome;
import lcm.org.tempuri.ArrayOfPCBList;
import lcm.org.tempuri.ArrayOfString;
import lcm.org.tempuri.BOGroupParent;
import lcm.org.tempuri.BusinessOutcomeDetails;
import lcm.org.tempuri.KeyValuePairOfStringStaticFields;
import lcm.org.tempuri.LCMClientService;
import lcm.org.tempuri.LCMClientServiceSoap;
import lcm.org.tempuri.ModesWithCValue;
import lcm.org.tempuri.Outcome;
import lcm.org.tempuri.PCBList;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.isuite.rjil.iagent.jiomoney.common.SDLCampaign;
import com.isuite.rjil.iagent.jiomoney.common.lcm.LcmBOGroupParent;
import com.isuite.rjil.iagent.jiomoney.common.lcm.LcmBusinessOutcome;
import com.isuite.rjil.iagent.jiomoney.common.lcm.LcmBusinessOutcomeDetails;
import com.isuite.rjil.iagent.jiomoney.common.lcm.LcmContactDetail;
import com.isuite.rjil.iagent.jiomoney.common.lcm.LcmModesWithCValue;
import com.isuite.rjil.iagent.jiomoney.common.lcm.LcmPCBList;
import com.isuite.rjil.iagent.jiomoney.dao.RilLcmDao;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;
import com.isuite.rjil.iagent.jiomoney.util.PropertiesUtil;
import com.isuite.rjil.iagent.jiomoney.util.WSHandlerResolver;

public class RilLcmDaoImpl implements RilLcmDao {

	private static final Logger logger = Logger.getLogger(RilLcmDaoImpl.class);
	private static final Logger soapLogger = Logger.getLogger(LOVUtil.SOAPLOGGER);
	@Override
	public List<LcmContactDetail> getLcmContactDetail(String lcmCallKey,String pAgentId ,long requestId)
			throws DataAccessException 
			{

		List<LcmContactDetail> returnList = null;
		try 
		{
			Calendar startDate = Calendar.getInstance();
			soapLogger.debug("Request-Response: Start : Agent Id is " + pAgentId + " Service Name is RilLcmDaoImpl.getLcmContactDetail(). Date and Time is " + startDate);
			Properties properties = PropertiesUtil.getProperties(PropertiesUtil.platform);
			String lcmUrl = properties.getProperty("lcmClient");
			LCMClientService service = null;
			LCMClientServiceSoap sopService = null;
			service = new LCMClientService(new URL(lcmUrl));
			sopService = service.getLCMClientServiceSoap();
			Holder<String> xmlOutput = new Holder<String>();
			Holder<Long> responseStatus = new Holder<Long>();
			soapLogger.debug("RilLcmDaoImpl.getLcmContactDetail(): LcmCallKey : "+lcmCallKey);
			sopService.getContactDetail(lcmCallKey, xmlOutput, responseStatus);
			soapLogger.debug("Lcm Contact Details Xml  : " +xmlOutput.value);
			soapLogger.debug("Request-Response: End : |Total Execution Time in millisecond: "+(Calendar.getInstance().getTimeInMillis()-startDate.getTimeInMillis())+"\n");
			if (responseStatus != null && responseStatus.value != null
					&& responseStatus.value == 1)
			{
				String xmlResponse = xmlOutput.value;
				
				if (xmlResponse != null)
				{
					returnList = getBusinessFieldsfromContactDetail(xmlResponse);
				}

			} else
			{
				throw new DataAccessException(
						"Unable to Retrieve Contact Detail for the Specified LCM Key");

			}

		} catch (Exception e) {
			logger.error("Exception catched while Getting Contact Detail", e);
			throw new DataAccessException(e.getMessage(), e);
		}

		return returnList;
	}

	private List<LcmContactDetail> getBusinessFieldsfromContactDetail(
			String responseXml) {

		List<LcmContactDetail> returnList = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder db = factory.newDocumentBuilder();

			String xmlHeader = "<?xml version='1.0' encoding='UTF-8'?>";
			responseXml = xmlHeader + responseXml;
			InputSource inputSource = new InputSource(new StringReader(
					responseXml));
			Document doc = db.parse(inputSource);
			NodeList firstLevelNodeList = doc.getDocumentElement()
					.getChildNodes();

			if (firstLevelNodeList != null
					&& firstLevelNodeList.getLength() > 0) {

				returnList = new ArrayList<LcmContactDetail>();
				for (int itr = 0; itr < firstLevelNodeList.getLength(); itr++) {
					Element businessElement = (Element) firstLevelNodeList
							.item(itr);
					LcmContactDetail detail = new LcmContactDetail();
					detail.setFieldName(getValueFromXmlNode(businessElement,
							"FieldName"));
					
						detail.setValue(getValueFromXmlNode(businessElement,
								"Value"));
						
					detail.setDataType(getValueFromXmlNode(businessElement,
							"DataType"));
					detail.setFormat(getValueFromXmlNode(businessElement,
							"Format"));

					returnList.add(detail);
				}

			}

		} catch (Throwable t) {
			logger.error("Exception catched while Parsing Xml Response", t);

		}

		return returnList;

	}

	private String getValueFromXmlNode(Element elment, String tagName) {
		String toReturn = "";
		if (elment.getElementsByTagName(tagName) != null
				&& elment.getElementsByTagName(tagName).getLength() > 0) {
			Element fieldElement = (Element) elment.getElementsByTagName(
					tagName).item(0);
			if (fieldElement.getFirstChild() != null
					&& fieldElement.getFirstChild().getNodeValue() != null) {
				toReturn = fieldElement.getFirstChild().getNodeValue();
			} else {
				toReturn = "";
			}

		} else {
			toReturn = "";
		}

		return toReturn;
	}

	@Override
	public List<LcmBusinessOutcome> getBusinessOutcome(String lcmCallKey,long requestId)
			throws DataAccessException {
		List<LcmBusinessOutcome> returnList = null;
		try {
			soapLogger
			.info("Request & Response for ---- getBusinessOutcome--->>> getBusinessOutcome()");
			Properties properties = PropertiesUtil
					.getProperties(PropertiesUtil.platform);
			String lcmUrl = properties.getProperty("lcmClient");
			LCMClientService service = new LCMClientService(new URL(lcmUrl));
			LCMClientServiceSoap sopService = service.getLCMClientServiceSoap();
			service.setHandlerResolver(new WSHandlerResolver("",requestId));
			ArrayOfOutcome pojo1 = new ArrayOfOutcome();
			Holder<ArrayOfOutcome> array = new Holder<ArrayOfOutcome>(pojo1);
 			Holder<Long> responseStatus = new Holder<Long>();
			sopService.getBusinessOutcome(lcmCallKey, array,
					responseStatus);
			if (responseStatus != null && responseStatus.value != null
					&& responseStatus.value == 1) {

				if (array.value.getOutcome() != null
						&& array.value.getOutcome().size() > 0) {
					returnList = new ArrayList<LcmBusinessOutcome>();
					for (Outcome outcome : array.value
							.getOutcome()) {
						LcmBusinessOutcome pojo = new LcmBusinessOutcome();
						pojo.setCampaignId(outcome.getCampaignID() != null ? outcome
								.getCampaignID() : "");
						pojo.setDescription(outcome.getDesc() != null ? outcome
								.getDesc() : "");
						pojo.setOutcomeId(outcome.getOutcomeID() != null ? outcome
								.getOutcomeID() : "");

						returnList.add(pojo);
					}
				}
				soapLogger
				.info("Request & Response for ---- responseStatus--->>> responseStatus"+responseStatus.value);
			} else {
				throw new DataAccessException(
						"Unable To Retrieve Business Outcomes");
			}

		} catch (Exception e) {
			logger.error("Exception catched while Getting BusinessOutcomes", e);
			throw new DataAccessException(e.getMessage(), e);
		}

		return returnList;

	}

	@Override
	public void setBusinessOutcome(String lcmCallKey, String outcome,
			String callId, String agentId,long requestId) throws DataAccessException {

		try 
		{
			Calendar lCalendar = Calendar.getInstance();
			soapLogger.debug("Request-Response: Start : Agent Id is " + agentId + " Service Name is RilLcmDaoImpl.setBusinessOutcome() | Date and Time is " + lCalendar);
			Properties properties = PropertiesUtil
					.getProperties(PropertiesUtil.platform);
			String lcmUrl = properties.getProperty("lcmClient");
			LCMClientService service = new LCMClientService(new URL(lcmUrl));
			LCMClientServiceSoap sopService = service.getLCMClientServiceSoap();
			service.setHandlerResolver(new WSHandlerResolver(agentId,requestId));
			
			
			Long result = sopService.setBusinessOutcome(lcmCallKey, outcome,
					"", "");
			
			soapLogger.debug("Input - LCMCallKey : "+lcmCallKey+"| Outcome : "+outcome+" | Call ID : "+callId);
			soapLogger.info("Result : "+result);
			soapLogger.info("Service : "+service);
			soapLogger.debug("Request-Response: End : |Total Execution Time in millisecond: "+(Calendar.getInstance().getTimeInMillis()-lCalendar.getTimeInMillis())+"\n");
			
			if (result == null || result != 1)
			{
				throw new DataAccessException("Unable to Update the Business Outcome");
			}

		} catch (Exception e) {
			logger.error("Exception catched while Setting BusinessOutcomes", e);
			throw new DataAccessException(e.getMessage(), e);
		}

	}

	@Override
	public List<LcmBusinessOutcome> getCallOutcome() throws DataAccessException {
		List<LcmBusinessOutcome> returnList = null;
		try {

			Properties properties = PropertiesUtil
					.getProperties(PropertiesUtil.platform);
			String lcmUrl = properties.getProperty("lcmClient");
			LCMClientService service = new LCMClientService(new URL(lcmUrl));
			LCMClientServiceSoap sopService = service.getLCMClientServiceSoap();
			ArrayOfOutcome outcome = new ArrayOfOutcome();
			Holder<ArrayOfOutcome> array = new Holder<ArrayOfOutcome>(outcome);
			Holder<Long> responseStatus = new Holder<Long>();
			sopService.getCallOutcome(array, responseStatus);

			if (responseStatus != null && responseStatus.value != null
					&& responseStatus.value == 1) {
				returnList = new ArrayList<LcmBusinessOutcome>();
				for (Outcome entity : array.value.getOutcome()) {
					LcmBusinessOutcome pojo = new LcmBusinessOutcome();
					pojo.setCampaignId(entity.getCampaignID() != null ? entity
							.getCampaignID() : "");
					pojo.setDescription(entity.getDesc() != null ? entity
							.getDesc() : "");
					pojo.setOutcomeId(entity.getOutcomeID() != null ? entity
							.getOutcomeID() : "");

					returnList.add(pojo);
				}

			}

		} catch (Exception e) {
			logger.error("Exception catched while Getting Call Outcomes", e);
			throw new DataAccessException(e.getMessage(), e);
		}

		return returnList;
	}

	@Override
	public void setCallOutcome(String lcmcallKey, String outcome,
			String dncInfo, String callId, String agentId,long requestId)
			throws DataAccessException {

		try {

			Properties properties = PropertiesUtil
					.getProperties(PropertiesUtil.platform);
			String lcmUrl = properties.getProperty("lcmClient");
			LCMClientService service = new LCMClientService(new URL(lcmUrl));
			LCMClientServiceSoap sopService = service.getLCMClientServiceSoap();
			Long result = sopService.setCallOutcome(lcmcallKey, outcome,
					",", "", "");

			if (result == null || result != 1) {
				throw new DataAccessException("Unable to Set the Call Outcome");
			}

		} catch (Exception e) {
			logger.error("Exception catched while Setting Call Outcomes", e);
			throw new DataAccessException(e.getMessage(), e);
		}

	}

	@Override
	public String getValueFromLcmBusinessFields(String lcmKey, String fieldName,long requestId)
			throws DataAccessException {

		String toReturn = "";
		try {

			List<LcmContactDetail> fieldList = getLcmContactDetail(lcmKey,"",requestId);
			if (fieldList != null && fieldList.size() > 0) {

				for (LcmContactDetail field : fieldList) {
					if (field.getFieldName().equalsIgnoreCase(fieldName)) {
						toReturn = field.getValue();
						break;

					}

				}

			}

		} catch (Exception e) {
			logger.error("Exception catched while Setting Call Outcomes", e);
			throw new DataAccessException(e.getMessage(), e);
		}
		return toReturn;
	}

	@Override
	public List<LcmBusinessOutcome> getBusinessOutcomeParent(String lcmCallKey,
			int parentBOId) throws DataAccessException {
		List<LcmBusinessOutcome> returnList = null;
		try {

			Properties properties = PropertiesUtil
					.getProperties(PropertiesUtil.platform);
			String lcmUrl = properties.getProperty("lcmClient");
			LCMClientService service = new LCMClientService(new URL(lcmUrl));
			LCMClientServiceSoap sopService = service.getLCMClientServiceSoap();

			Holder<ArrayOfOutcome> businessOutcomeHolder = new Holder<>();
			Holder<Long> responseStatus = new Holder<Long>();
			sopService.getBusinessOutcomeParent(lcmCallKey, parentBOId,
					businessOutcomeHolder, responseStatus);
			if (responseStatus != null && responseStatus.value != null
					&& responseStatus.value == 1) {

				if (businessOutcomeHolder.value.getOutcome() != null
						&& businessOutcomeHolder.value.getOutcome().size() > 0) {
					returnList = new ArrayList<LcmBusinessOutcome>();
					for (Outcome outcome : businessOutcomeHolder.value
							.getOutcome()) {
						LcmBusinessOutcome pojo = new LcmBusinessOutcome();
						pojo.setCampaignId(outcome.getCampaignID() != null ? outcome
								.getCampaignID() : "");
						pojo.setDescription(outcome.getDesc() != null ? outcome
								.getDesc() : "");
						pojo.setOutcomeId(outcome.getOutcomeID() != null ? outcome
								.getOutcomeID() : "");

						returnList.add(pojo);
					}
				}

			} else {
				throw new DataAccessException(
						"Unable To Retrieve Business Outcome Parent");
			}

		} catch (Exception e) {
			logger.error(
					"Exception catched while Getting BusinessOutcomeParent", e);
			throw new DataAccessException(e.getMessage(), e);
		}

		return returnList;

	}

	@Override
	public List<LcmBusinessOutcome> getModes(String lcmCallKey)
			throws DataAccessException {
		List<LcmBusinessOutcome> returnList = null;
		try {

			Properties properties = PropertiesUtil
					.getProperties(PropertiesUtil.platform);
			String lcmUrl = properties.getProperty("lcmClient");
			LCMClientService service = new LCMClientService(new URL(lcmUrl));
			LCMClientServiceSoap sopService = service.getLCMClientServiceSoap();

			Holder<ArrayOfOutcome> businessOutcomeHolder = new Holder<>();
			Holder<Long> responseStatus = new Holder<Long>();
			sopService.getModes(lcmCallKey, businessOutcomeHolder,
					responseStatus);
			if (responseStatus != null && responseStatus.value != null
					&& responseStatus.value == 1) {

				if (businessOutcomeHolder.value.getOutcome() != null
						&& businessOutcomeHolder.value.getOutcome().size() > 0) {
					returnList = new ArrayList<LcmBusinessOutcome>();
					for (Outcome outcome : businessOutcomeHolder.value
							.getOutcome()) {
						LcmBusinessOutcome pojo = new LcmBusinessOutcome();
						pojo.setCampaignId(outcome.getCampaignID() != null ? outcome
								.getCampaignID() : "");
						pojo.setDescription(outcome.getDesc() != null ? outcome
								.getDesc() : "");
						pojo.setOutcomeId(outcome.getOutcomeID() != null ? outcome
								.getOutcomeID() : "");

						returnList.add(pojo);
					}
				}

			} else {
				throw new DataAccessException("Unable To Retrieve Modes");
			}

		} catch (Exception e) {
			logger.error("Exception catched while Getting Modes", e);
			throw new DataAccessException(e.getMessage(), e);
		}

		return returnList;
	}

	@Override
	public List<LcmBusinessOutcome> getCampaignModes(String lcmCallKey)
			throws DataAccessException {
		List<LcmBusinessOutcome> returnList = null;
		try {

			Properties properties = PropertiesUtil
					.getProperties(PropertiesUtil.platform);
			String lcmUrl = properties.getProperty("lcmClient");
			LCMClientService service = new LCMClientService(new URL(lcmUrl));
			LCMClientServiceSoap sopService = service.getLCMClientServiceSoap();

			Holder<ArrayOfOutcome> businessOutcomeHolder = new Holder<>();
			Holder<Long> responseStatus = new Holder<Long>();
			sopService.getCampaignModes(lcmCallKey, businessOutcomeHolder,
					responseStatus);
			if (responseStatus != null && responseStatus.value != null
					&& responseStatus.value == 1) {

				if (businessOutcomeHolder.value.getOutcome() != null
						&& businessOutcomeHolder.value.getOutcome().size() > 0) {
					returnList = new ArrayList<LcmBusinessOutcome>();
					for (Outcome outcome : businessOutcomeHolder.value
							.getOutcome()) {
						LcmBusinessOutcome pojo = new LcmBusinessOutcome();
						pojo.setCampaignId(outcome.getCampaignID() != null ? outcome
								.getCampaignID() : "");
						pojo.setDescription(outcome.getDesc() != null ? outcome
								.getDesc() : "");
						pojo.setOutcomeId(outcome.getOutcomeID() != null ? outcome
								.getOutcomeID() : "");

						returnList.add(pojo);
					}
				}

			} else {
				throw new DataAccessException(
						"Unable To Retrieve Campaign Modes");
			}

		} catch (Exception e) {
			logger.error("Exception catched while Getting CampaignModes", e);
			throw new DataAccessException(e.getMessage(), e);
		}

		return returnList;
	}

	@Override
	public void setBusinessOutcomeWithComment(String strAcNo, String outCome,
			String callId, String userId, String agentComment,
			BigDecimal targetAmount) throws DataAccessException {

		try {

			Properties properties = PropertiesUtil
					.getProperties(PropertiesUtil.platform);
			String lcmUrl = properties.getProperty("lcmClient");
			LCMClientService service = new LCMClientService(new URL(lcmUrl));
			LCMClientServiceSoap sopService = service.getLCMClientServiceSoap();
			Long result = sopService.setBusinessOutcomeWithComments(strAcNo,
					outCome, callId, userId, agentComment, targetAmount);

			if (result == null || result != 1) {
				throw new DataAccessException(
						"Unable to Update the Business Outcome with Comment");
			}

		} catch (Exception e) {
			logger.error(
					"Exception catched while Setting BusinessOutcomesWithComment",
					e);
			throw new DataAccessException(e.getMessage(), e);
		}

	}

	@Override
	public void setCallOutcomeWithComment(String strAcNo, String outCome,
			String dNCInfo, String callId, String userId, String agentComment,
			BigDecimal targetAmount) throws DataAccessException {

		try {

			Properties properties = PropertiesUtil
					.getProperties(PropertiesUtil.platform);
			String lcmUrl = properties.getProperty("lcmClient");
			LCMClientService service = new LCMClientService(new URL(lcmUrl));
			LCMClientServiceSoap sopService = service.getLCMClientServiceSoap();
			Long result = sopService.setCallOutcomeWithComments(strAcNo,
					outCome, dNCInfo, callId, userId, agentComment,
					targetAmount);

			if (result == null || result != 1) {
				throw new DataAccessException(
						"Unable to Set the Call Outcome with Comment");
			}

		} catch (Exception e) {
			logger.error(
					"Exception catched while Setting Call Outcomes with Comment",
					e);
			throw new DataAccessException(e.getMessage(), e);
		}

	}

	@Override
	public void setCallback(String lcmCallKey, String callBackStartDate,
			String callBackEndDate, String callBackStartTime,
			String callBackEndTime, int modeId, String callId)
			throws DataAccessException {

		try {

			Properties properties = PropertiesUtil
					.getProperties(PropertiesUtil.platform);
			String lcmUrl = properties.getProperty("lcmClient");
			LCMClientService service = new LCMClientService(new URL(lcmUrl));
			LCMClientServiceSoap sopService = service.getLCMClientServiceSoap();
			Long result = sopService.setCallback(lcmCallKey, callBackStartDate,
					callBackEndDate, callBackStartTime, callBackEndTime,
					modeId, callId);

			if (result == null || result != 1) {
				throw new DataAccessException("Unable to Set the Callback");
			}

		} catch (Exception e) {
			logger.error("Exception catched while Setting Callback", e);
			throw new DataAccessException(e.getMessage(), e);
		}

	}

	@Override
	public void setCallbackWithComment(String lcmCallKey,
			String callBackStartDate, String callBackEndDate,
			String callBackStartTime, String callBackEndTime, int nModeId,
			String callId, String agentComment, BigDecimal targetAmount)
			throws DataAccessException {

		try {

			Properties properties = PropertiesUtil
					.getProperties(PropertiesUtil.platform);
			String lcmUrl = properties.getProperty("lcmClient");
			LCMClientService service = new LCMClientService(new URL(lcmUrl));
			LCMClientServiceSoap sopService = service.getLCMClientServiceSoap();
			Long result = sopService.setCallbackWithComments(lcmCallKey,
					callBackStartDate, callBackEndDate, callBackStartTime,
					callBackEndTime, nModeId, callId, agentComment,
					targetAmount);

			if (result == null || result != 1) {
				throw new DataAccessException(
						"Unable to Set the Callback With Comment");
			}

		} catch (Exception e) {
			logger.error(
					"Exception catched while Setting Callback With Comment", e);
			throw new DataAccessException(e.getMessage(), e);
		}

	}

	@Override
	public void setPersonalCallback(String lcmCallKey,
			String callBackStartDate, String callBackEndDate,
			String callBackStartTime, String callBackEndTime, int modeId,
			String userId, String callId) throws DataAccessException {

		try {

			Properties properties = PropertiesUtil
					.getProperties(PropertiesUtil.platform);
			String lcmUrl = properties.getProperty("lcmClient");
			LCMClientService service = new LCMClientService(new URL(lcmUrl));
			LCMClientServiceSoap sopService = service.getLCMClientServiceSoap();
			Long result = sopService.setPersonalCallback(lcmCallKey,
					callBackStartDate, callBackEndDate, callBackStartTime,
					callBackEndTime, modeId, userId, callId);

			if (result == null || result != 1) {
				throw new DataAccessException(
						"Unable to Set the Personal Callback");
			}

		} catch (Exception e) {
			logger.error("Exception catched while Setting Personal Callback", e);
			throw new DataAccessException(e.getMessage(), e);
		}

	}

	@Override
	public void setPersonalCallbackWithComment(String lcmCallKey,
			String callBackStartDate, String callBackEndDate,
			String callBackStartTime, String callBackEndTime, int modeId,
			String callId, String userId, String agentComment,
			BigDecimal targetAmount) throws DataAccessException {

		try {

			Properties properties = PropertiesUtil
					.getProperties(PropertiesUtil.platform);
			String lcmUrl = properties.getProperty("lcmClient");
			LCMClientService service = new LCMClientService(new URL(lcmUrl));
			LCMClientServiceSoap sopService = service.getLCMClientServiceSoap();
			Long result = sopService.setPersonalCallbackWithComments(
					lcmCallKey, callBackStartDate, callBackEndDate,
					callBackStartTime, callBackEndTime, modeId, callId, userId,
					agentComment, targetAmount);

			if (result == null || result != 1) {
				throw new DataAccessException(
						"Unable to Set the Personal Callback with Comment");
			}

		} catch (Exception e) {
			logger.error(
					"Exception catched while Setting Personal Callback with Comment",
					e);
			throw new DataAccessException(e.getMessage(), e);
		}
	}

	@Override
	public void setNewModeNumber(String lcmCallKey, int modelId,
			String contactNumber, boolean deleteExistingModes)
			throws DataAccessException {
		try {

			Properties properties = PropertiesUtil
					.getProperties(PropertiesUtil.platform);
			String lcmUrl = properties.getProperty("lcmClient");
			LCMClientService service = new LCMClientService(new URL(lcmUrl));
			LCMClientServiceSoap sopService = service.getLCMClientServiceSoap();
			Long result = sopService.setNewModeNumber(lcmCallKey, modelId,
					contactNumber, deleteExistingModes);

			if (result == null || result != 1) {
				throw new DataAccessException(
						"Unable to Set the New Mode Number");
			}

		} catch (Exception e) {
			logger.error("Exception catched while Setting NewModeNumber", e);
			throw new DataAccessException(e.getMessage(), e);
		}
	}

	@Override
	public String getCallGuideName(String lcmCallKey)
			throws DataAccessException {

		String response = "";
		try {
			Properties properties = PropertiesUtil
					.getProperties(PropertiesUtil.platform);
			String lcmUrl = properties.getProperty("lcmClient");
			LCMClientService service = new LCMClientService(new URL(lcmUrl));
			LCMClientServiceSoap sopService = service.getLCMClientServiceSoap();

			Holder<String> callGuideName = new Holder<String>();
			Holder<Long> responseStatus = new Holder<Long>();
			sopService.getCallGuideName(lcmCallKey, callGuideName,
					responseStatus);

			if (responseStatus != null && responseStatus.value != null
					&& responseStatus.value == 1 && callGuideName != null
					&& callGuideName.value != null) {
				response = callGuideName.value;

			} else {
				throw new DataAccessException(
						"Unable to Retrieve Call Guide Name");
			}

		} catch (Exception e) {
			logger.error("Exception catched while Getting Call Guide Name", e);
			throw new DataAccessException(e.getMessage(), e);
		}

		return response;
	}

	@Override
	public List<LcmPCBList> getPersonalCallbackList(String userId,
			String fromDate, String toDate, int startingNo, int noOfRecords)
			throws DataAccessException {

		List<LcmPCBList> returnList = null;
		try {

			Properties properties = PropertiesUtil
					.getProperties(PropertiesUtil.platform);
			String lcmUrl = properties.getProperty("lcmClient");
			LCMClientService service = new LCMClientService(new URL(lcmUrl));
			LCMClientServiceSoap sopService = service.getLCMClientServiceSoap();

			Holder<ArrayOfPCBList> arrayOfPCBListHolder = new Holder<>();
			Holder<Long> responseStatus = new Holder<Long>();
			sopService.getPersonalCallbackList(userId, fromDate, toDate,
					startingNo, noOfRecords, arrayOfPCBListHolder,
					responseStatus);
			if (responseStatus != null && responseStatus.value != null
					&& responseStatus.value == 1) {
				if (arrayOfPCBListHolder.value.getPCBList() != null
						&& arrayOfPCBListHolder.value.getPCBList().size() > 0) {
					returnList = new ArrayList<LcmPCBList>();

					for (PCBList outcomePCBList : arrayOfPCBListHolder.value
							.getPCBList()) {

						LcmPCBList pojo = new LcmPCBList();
						pojo.setRowNumber(outcomePCBList.getRowNumber());
						pojo.setCampaignID(outcomePCBList.getCampaignID() != null ? outcomePCBList
								.getCampaignID() : "");
						pojo.setContactID(outcomePCBList.getContactID());
						pojo.setModeNumber(outcomePCBList.getModeNumber());
						pojo.setModeDesc(outcomePCBList.getModeDesc() != null ? outcomePCBList
								.getModeDesc() : "");
						pojo.setcValue(outcomePCBList.getCValue() != null ? outcomePCBList
								.getCValue() : "");
						pojo.setCallStartDate(outcomePCBList.getCallStartDate() != null ? outcomePCBList
								.getCallStartDate() : "");
						pojo.setCallStartTime(outcomePCBList.getCallStartTime() != null ? outcomePCBList
								.getCallStartTime() : "");
						pojo.setCallEndDate(outcomePCBList.getCallEndDate() != null ? outcomePCBList
								.getCallEndDate() : "");
						pojo.setCallEndTime(outcomePCBList.getCallEndTime() != null ? outcomePCBList
								.getCallEndTime() : "");
						returnList.add(pojo);
					}
				}

			} else {
				throw new DataAccessException(
						"Unable To Retrieve Personal Callback List");
			}

		} catch (Exception e) {
			logger.error(
					"Exception catched while Getting Personal Callback List", e);
			throw new DataAccessException(e.getMessage(), e);
		}

		return returnList;
	}

	@Override
	public String getCampaigns() throws DataAccessException {

		String response = "";
		try {
			Properties properties = PropertiesUtil
					.getProperties(PropertiesUtil.platform);
			String lcmUrl = properties.getProperty("lcmClient");
			LCMClientService service = new LCMClientService(new URL(lcmUrl));
			LCMClientServiceSoap sopService = service.getLCMClientServiceSoap();

			Holder<String> XMLCampaignList = new Holder<String>();
			Holder<Long> responseStatus = new Holder<Long>();
			sopService.getCampaigns(XMLCampaignList, responseStatus);

			if (responseStatus != null && responseStatus.value != null
					&& responseStatus.value == 1 && XMLCampaignList != null
					&& XMLCampaignList.value != null) {
				response = XMLCampaignList.value;

			} else {
				throw new DataAccessException("Unable to Retrieve Campaigns");
			}

		} catch (Exception e) {
			logger.error("Exception catched while Getting Campaigns", e);
			throw new DataAccessException(e.getMessage(), e);
		}
		return response;
	}

	@Override
	public String getCampaignBusinessFields(String campaignName)
			throws DataAccessException {
		String response = "";
		try {
			Properties properties = PropertiesUtil
					.getProperties(PropertiesUtil.platform);
			String lcmUrl = properties.getProperty("lcmClient");
			LCMClientService service = new LCMClientService(new URL(lcmUrl));
			LCMClientServiceSoap sopService = service.getLCMClientServiceSoap();

			Holder<String> XMLCampaignList = new Holder<String>();
			Holder<Long> responseStatus = new Holder<Long>();
			sopService.getCampaignBusinessFields(campaignName, XMLCampaignList,
					responseStatus);

			if (responseStatus != null && responseStatus.value != null
					&& responseStatus.value == 1 && XMLCampaignList != null
					&& XMLCampaignList.value != null) {
				response = XMLCampaignList.value;

			} else {
				throw new DataAccessException(
						"Unable to Retrieve CampaignBusinessFields");
			}

		} catch (Exception e) {
			logger.error(
					"Exception catched while Getting CampaignBusinessFields", e);
			throw new DataAccessException(e.getMessage(), e);
		}
		return response;
	}

	@Override
	public String getBusinessOutcomeforCampaign(String campaignName)
			throws DataAccessException {
		String response = "";
		try {
			Properties properties = PropertiesUtil
					.getProperties(PropertiesUtil.platform);
			String lcmUrl = properties.getProperty("lcmClient");
			LCMClientService service = new LCMClientService(new URL(lcmUrl));
			LCMClientServiceSoap sopService = service.getLCMClientServiceSoap();

			Holder<String> XMLCampaignList = new Holder<String>();
			Holder<Long> responseStatus = new Holder<Long>();
			sopService.getBusinessOutcomeforCampaign(campaignName,
					XMLCampaignList, responseStatus);

			if (responseStatus != null && responseStatus.value != null
					&& responseStatus.value == 1 && XMLCampaignList != null
					&& XMLCampaignList.value != null) {
				response = XMLCampaignList.value;

			} else {
				throw new DataAccessException(
						"Unable to Retrieve Business Outcome for Campaign");
			}

		} catch (Exception e) {
			logger.error(
					"Exception catched while Getting  Business Outcome for Campaign",
					e);
			throw new DataAccessException(e.getMessage(), e);
		}
		return response;
	}

	@Override
	public String getContact(String campaignName, String phoneNo,
			String condition, int noOfRows) throws DataAccessException {
		String response = "";
		try {
			Properties properties = PropertiesUtil
					.getProperties(PropertiesUtil.platform);
			String lcmUrl = properties.getProperty("lcmClient");
			LCMClientService service = new LCMClientService(new URL(lcmUrl));
			LCMClientServiceSoap sopService = service.getLCMClientServiceSoap();

			Holder<String> contactData = new Holder<String>();
			Holder<Long> responseStatus = new Holder<Long>();
			sopService.getContact(campaignName, phoneNo, condition, noOfRows,
					contactData, responseStatus);

			if (responseStatus != null && responseStatus.value != null
					&& responseStatus.value == 1 && contactData != null
					&& contactData.value != null) {
				response = contactData.value;

			} else {
				throw new DataAccessException(
						"Unable to Retrieve Contact for Campaign");
			}

		} catch (Exception e) {
			logger.error(
					"Exception catched while Getting Contact for Campaign", e);
			throw new DataAccessException(e.getMessage(), e);
		}
		return response;
	}

	@Override
	public void setContactStatus(String campaignId, int contactId)
			throws DataAccessException {

		try {

			Properties properties = PropertiesUtil
					.getProperties(PropertiesUtil.platform);
			String lcmUrl = properties.getProperty("lcmClient");
			LCMClientService service = new LCMClientService(new URL(lcmUrl));
			LCMClientServiceSoap sopService = service.getLCMClientServiceSoap();
			Long result = sopService.setContactStatus(campaignId, contactId);

			if (result == null || result != 1) {
				throw new DataAccessException("Unable to Set the ContactStatus");
			}

		} catch (Exception e) {
			logger.error("Exception catched while Setting Contact Status", e);
			throw new DataAccessException(e.getMessage(), e);
		}
	}

	@Override
	public void setContactBusinessFields(String lcmCallKey, String xmlData)
			throws DataAccessException {

		try {

			Properties properties = PropertiesUtil
					.getProperties(PropertiesUtil.platform);
			String lcmUrl = properties.getProperty("lcmClient");
			LCMClientService service = new LCMClientService(new URL(lcmUrl));
			LCMClientServiceSoap sopService = service.getLCMClientServiceSoap();
			Long result = sopService.setContactBusinessFields(lcmCallKey,
					xmlData);

			if (result == null || result != 1) {
				throw new DataAccessException(
						"Unable to Set the Contact Business Fields");
			}

		} catch (Exception e) {
			logger.error(
					"Exception catched while Setting Contact Business Fields",
					e);
			throw new DataAccessException(e.getMessage(), e);
		}
	}

	@Override
	public void setContactOutcome(String contactId, String campaignId,
			String modeDescription, String outcomeId)
			throws DataAccessException {
		try {

			Properties properties = PropertiesUtil
					.getProperties(PropertiesUtil.platform);
			String lcmUrl = properties.getProperty("lcmClient");
			LCMClientService service = new LCMClientService(new URL(lcmUrl));
			LCMClientServiceSoap sopService = service.getLCMClientServiceSoap();
			ArrayOfString strArrContactInfo = new ArrayOfString();
			strArrContactInfo.getString().add(contactId);
			strArrContactInfo.getString().add(campaignId);
			strArrContactInfo.getString().add(modeDescription);
			Long result = sopService.setContactOutcome(strArrContactInfo,
					outcomeId);

			if (result == null || result != 1) {
				throw new DataAccessException(
						"Unable to Set the Contact Outcome");
			}

		} catch (Exception e) {
			logger.error("Exception catched while Setting Contact Outcome", e);
			throw new DataAccessException(e.getMessage(), e);
		}
	}

	@Override
	public String getCommonBussFields(String campaignId)
			throws DataAccessException {
		String response = "";
		try {
			Properties properties = PropertiesUtil
					.getProperties(PropertiesUtil.platform);
			String lcmUrl = properties.getProperty("lcmClient");
			LCMClientService service = new LCMClientService(new URL(lcmUrl));
			LCMClientServiceSoap sopService = service.getLCMClientServiceSoap();

			Holder<String> commonBussflds = new Holder<String>();
			Holder<Long> responseStatus = new Holder<Long>();
			sopService.getCommonBussFields(campaignId, commonBussflds,
					responseStatus);

			if (responseStatus != null && responseStatus.value != null
					&& responseStatus.value == 1 && commonBussflds != null
					&& commonBussflds.value != null) {
				response = commonBussflds.value;

			} else {
				throw new DataAccessException(
						"Unable to Retrieve Contact for Campaign");
			}

		} catch (Exception e) {
			logger.error(
					"Exception catched while Getting Contact for Campaign", e);
			throw new DataAccessException(e.getMessage(), e);
		}
		return response;
	}

	@Override
	public List<LcmBusinessOutcome> getBussOutcomeForGroup(String outcomeGroup)
			throws DataAccessException {

		List<LcmBusinessOutcome> returnList = null;
		try {
			Properties properties = PropertiesUtil
					.getProperties(PropertiesUtil.platform);
			String lcmUrl = properties.getProperty("lcmClient");
			LCMClientService service = new LCMClientService(new URL(lcmUrl));
			LCMClientServiceSoap sopService = service.getLCMClientServiceSoap();

			Holder<String> xmlBussOutcome = new Holder<String>();
			Holder<Long> responseStatus = new Holder<Long>();
			sopService.getBussOutcomeForGroup(outcomeGroup, xmlBussOutcome,
					responseStatus);

			if (responseStatus != null && responseStatus.value != null
					&& responseStatus.value == 1) {
				String xmlResponse = xmlBussOutcome.value;
				if (xmlResponse != null) {
					// returnList =
					// getBusinessFieldsfromContactDetail(xmlResponse);
				}

			} else {

				throw new DataAccessException(
						"Unable to Retrieve BussOutcomeForGroup for outcomeGroup");

			}

		} catch (Exception e) {
			logger.error(
					"Exception catched while Getting Buss Outcome For Group Detail",
					e);
			throw new DataAccessException(e.getMessage(), e);
		}

		return returnList;
	}

	@Override
	public String getContactAcrossCampaign(String phoneNo, String condition,
			int noOfRows) throws DataAccessException {
		String response = "";
		try {
			Properties properties = PropertiesUtil
					.getProperties(PropertiesUtil.platform);
			String lcmUrl = properties.getProperty("lcmClient");
			LCMClientService service = new LCMClientService(new URL(lcmUrl));
			LCMClientServiceSoap sopService = service.getLCMClientServiceSoap();

			Holder<String> strContactData = new Holder<String>();
			Holder<Long> responseStatus = new Holder<Long>();
			sopService.getContactAcrossCampaign(phoneNo, condition, noOfRows,
					strContactData, responseStatus);

			if (responseStatus != null && responseStatus.value != null
					&& responseStatus.value == 1 && strContactData != null
					&& strContactData.value != null) {
				response = strContactData.value;

			} else {
				throw new DataAccessException(
						"Unable to Retrieve Contact for Campaign");
			}

		} catch (Exception e) {
			logger.error(
					"Exception catched while Getting Contact for Campaign", e);
			throw new DataAccessException(e.getMessage(), e);
		}
		return response;
	}

	@Override
	public String getCallHistory(String lcmCallKey) throws DataAccessException {
		String response = "";
		try {
			Properties properties = PropertiesUtil
					.getProperties(PropertiesUtil.platform);
			String lcmUrl = properties.getProperty("lcmClient");
			LCMClientService service = new LCMClientService(new URL(lcmUrl));
			LCMClientServiceSoap sopService = service.getLCMClientServiceSoap();

			Holder<String> callHistoryDetail = new Holder<String>();
			Holder<Long> responseStatus = new Holder<Long>();
			sopService.getCallHistory(lcmCallKey, callHistoryDetail,
					responseStatus);

			if (responseStatus != null && responseStatus.value != null
					&& responseStatus.value == 1 && callHistoryDetail != null
					&& callHistoryDetail.value != null) {
				response = callHistoryDetail.value;

			} else {
				throw new DataAccessException("Unable to Retrieve Call History");
			}

		} catch (Exception e) {
			logger.error("Exception catched while Getting Call History", e);
			throw new DataAccessException(e.getMessage(), e);
		}
		return response;
	}

	@Override
	public String getScreenPopData(String lcmCallKey)
			throws DataAccessException {
		String response = "";
		try {
			Properties properties = PropertiesUtil
					.getProperties(PropertiesUtil.platform);
			String lcmUrl = properties.getProperty("lcmClient");
			LCMClientService service = new LCMClientService(new URL(lcmUrl));
			LCMClientServiceSoap sopService = service.getLCMClientServiceSoap();

			Holder<String> callHistoryDetail = new Holder<String>();
			Holder<Long> responseStatus = new Holder<Long>();
			sopService.getScreenPopData(lcmCallKey, callHistoryDetail,
					responseStatus);

			if (responseStatus != null && responseStatus.value != null
					&& responseStatus.value == 1 && callHistoryDetail != null
					&& callHistoryDetail.value != null) {
				response = callHistoryDetail.value;

			} else {
				throw new DataAccessException(
						"Unable to Retrieve Screen PopUp Data");
			}

		} catch (Exception e) {
			logger.error("Exception catched while Getting ScreenPopUpData", e);
			throw new DataAccessException(e.getMessage(), e);
		}
		return response;
	}

	@Override
	public String getMandatoryBussFields(String lcmCallKey)
			throws DataAccessException {
		String response = "";
		try {
			Properties properties = PropertiesUtil
					.getProperties(PropertiesUtil.platform);
			String lcmUrl = properties.getProperty("lcmClient");
			LCMClientService service = new LCMClientService(new URL(lcmUrl));
			LCMClientServiceSoap sopService = service.getLCMClientServiceSoap();

			Holder<String> bussFlds = new Holder<String>();
			Holder<Long> responseStatus = new Holder<Long>();
			sopService.getMandatoryBussFields(lcmCallKey, bussFlds,
					responseStatus);

			if (responseStatus != null && responseStatus.value != null
					&& responseStatus.value == 1 && bussFlds != null
					&& bussFlds.value != null) {
				response = bussFlds.value;

			} else {
				throw new DataAccessException(
						"Unable to Retrieve Mandatory Buss Fields");
			}

		} catch (Exception e) {
			logger.error("Exception catched while Getting MandatoryBussFields",
					e);
			throw new DataAccessException(e.getMessage(), e);
		}
		return response;
	}

	@Override
	public int generateListInfo(String campaignId) throws DataAccessException {
		int response;
		try {
			Properties properties = PropertiesUtil
					.getProperties(PropertiesUtil.platform);
			String lcmUrl = properties.getProperty("lcmClient");
			LCMClientService service = new LCMClientService(new URL(lcmUrl));
			LCMClientServiceSoap sopService = service.getLCMClientServiceSoap();

			Holder<Integer> listId = new Holder<Integer>();
			Holder<Long> responseStatus = new Holder<Long>();
			sopService.generateListInfo(campaignId, listId, responseStatus);

			if (responseStatus != null && responseStatus.value != null
					&& responseStatus.value == 1 && listId != null
					&& listId.value != null) {
				response = listId.value;

			} else {
				throw new DataAccessException("Unable to Retrieve ListInfo");
			}

		} catch (Exception e) {
			logger.error("Exception catched while Getting Generate List Info",
					e);
			throw new DataAccessException(e.getMessage(), e);
		}
		return response;
	}

	@Override
	public void addContact(String campaignId, String callType,
			String callStartDateTime, String callEndDateTime,
			String bussinessFields, int priority, String modes, String agentId,
			String smsData, String mailSubject, String mailMsg,
			String mailAttachment, String zipCode, String contactDetail,
			int listId) throws DataAccessException {
		try {

			Properties properties = PropertiesUtil
					.getProperties(PropertiesUtil.platform);
			String lcmUrl = properties.getProperty("lcmClient");
			LCMClientService service = new LCMClientService(new URL(lcmUrl));
			LCMClientServiceSoap sopService = service.getLCMClientServiceSoap();
			Long result = sopService.addContact(campaignId, callType,
					callStartDateTime, callEndDateTime, bussinessFields,
					priority, modes, agentId, smsData, mailSubject, mailMsg,
					mailAttachment, zipCode, contactDetail, listId);

			if (result == null || result != 1) {
				throw new DataAccessException(
						"Unable to Set the Add Contact Values");
			}

		} catch (Exception e) {
			logger.error("Exception catched while Setting New Contact", e);
			throw new DataAccessException(e.getMessage(), e);
		}
	}

	@Override
	public List<String> getCallGuideByGroupName(String groupName)
			throws DataAccessException {

		ArrayOfString result = null;
		try {

			Properties properties = PropertiesUtil
					.getProperties(PropertiesUtil.platform);
			String lcmUrl = properties.getProperty("lcmClient");
			LCMClientService service = new LCMClientService(new URL(lcmUrl));
			LCMClientServiceSoap sopService = service.getLCMClientServiceSoap();
			result = sopService.getCallGuideByGroupName(groupName);

			if (result == null) {
				throw new DataAccessException(
						"Unable to get the Call Guide  Group by GroupName");
			}

		} catch (Exception e) {
			logger.error(
					"Exception catched while Getting CallGuideByGroupName", e);
			throw new DataAccessException(e.getMessage(), e);
		}
		return result.getString();
	}

	@Override
	public List<LcmBOGroupParent> getBOGroupParent(String callKey)
			throws DataAccessException {

		List<LcmBOGroupParent> result = new ArrayList<LcmBOGroupParent>();
		try {

			Properties properties = PropertiesUtil
					.getProperties(PropertiesUtil.platform);
			String lcmUrl = properties.getProperty("lcmClient");
			LCMClientService service = new LCMClientService(new URL(lcmUrl));
			LCMClientServiceSoap sopService = service.getLCMClientServiceSoap();
			Holder<ArrayOfBOGroupParent> objparentInfo = new Holder<ArrayOfBOGroupParent>();
			Holder<Long> responseStatus = new Holder<Long>();
			sopService.getBOGroupParent(callKey, objparentInfo, responseStatus);

			if (responseStatus != null && responseStatus.value != null
					&& responseStatus.value == 1 && objparentInfo != null
					&& objparentInfo.value != null) {
				LcmBOGroupParent lcmBOGroupParent = null;
				for (BOGroupParent bOGroupParentObj : objparentInfo.value
						.getBOGroupParent()) {
					lcmBOGroupParent = new LcmBOGroupParent();
					lcmBOGroupParent
							.setParentId(bOGroupParentObj.getParentId());
					lcmBOGroupParent.setParentName(bOGroupParentObj
							.getParentName() != null ? bOGroupParentObj
							.getParentName() : "");
					result.add(lcmBOGroupParent);
				}

			} else {
				throw new DataAccessException(
						"Unable to Retrieve BOGroupParent Info");
			}

		} catch (Exception e) {
			logger.error("Exception catched while Getting BOGroupParent", e);
			throw new DataAccessException(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public List<LcmBusinessOutcomeDetails> getBusinessOutcomeDetails(
			String callKey) throws DataAccessException {

		List<LcmBusinessOutcomeDetails> result = new ArrayList<LcmBusinessOutcomeDetails>();
		try {

			Properties properties = PropertiesUtil
					.getProperties(PropertiesUtil.platform);
			String lcmUrl = properties.getProperty("lcmClient");
			LCMClientService service = new LCMClientService(new URL(lcmUrl));
			LCMClientServiceSoap sopService = service.getLCMClientServiceSoap();
			Holder<ArrayOfBusinessOutcomeDetails> bussOutcomeDetails = new Holder<ArrayOfBusinessOutcomeDetails>();
			Holder<Long> responseStatus = new Holder<Long>();
			sopService.getBusinessOutcomeDetails(callKey, bussOutcomeDetails,
					responseStatus);

			if (responseStatus != null && responseStatus.value != null
					&& responseStatus.value == 1 && bussOutcomeDetails != null
					&& bussOutcomeDetails.value != null) {
				LcmBusinessOutcomeDetails lcmBussOutcomeParent = null;
				for (BusinessOutcomeDetails bOutcomeDetails : bussOutcomeDetails.value
						.getBusinessOutcomeDetails()) {
					lcmBussOutcomeParent = new LcmBusinessOutcomeDetails();
					lcmBussOutcomeParent.setParentId(bOutcomeDetails
							.getParentId());
					lcmBussOutcomeParent.setParentName(bOutcomeDetails
							.getParentName() != null ? bOutcomeDetails
							.getParentName() : "");
					lcmBussOutcomeParent.setOutComeId(bOutcomeDetails
							.getOutComeId());
					lcmBussOutcomeParent.setOutcomeGroup(bOutcomeDetails
							.getOutcomeGroup() != null ? bOutcomeDetails
							.getOutcomeGroup() : "");
					lcmBussOutcomeParent.setGroupDescription(bOutcomeDetails
							.getGroupDescription() != null ? bOutcomeDetails
							.getGroupDescription() : "");
					lcmBussOutcomeParent.setDescription(bOutcomeDetails
							.getDescription() != null ? bOutcomeDetails
							.getDescription() : "");
					result.add(lcmBussOutcomeParent);
				}

			} else {
				throw new DataAccessException(
						"Unable to Retrieve Business Outcome Details");
			}

		} catch (Exception e) {
			logger.error(
					"Exception catched while Getting BusinessOutcomeDetails", e);
			throw new DataAccessException(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public List<String> getCampaignStaticBusinessFields(String campaignName)
			throws DataAccessException {

		List<String> stringStaticFieldList = new ArrayList<String>();
		try {
			Properties properties = PropertiesUtil
					.getProperties(PropertiesUtil.platform);
			String lcmUrl = properties.getProperty("lcmClient");
			LCMClientService service = new LCMClientService(new URL(lcmUrl));
			LCMClientServiceSoap sopService = service.getLCMClientServiceSoap();
			Holder<ArrayOfKeyValuePairOfStringStaticFields> listStaticFields = new Holder<ArrayOfKeyValuePairOfStringStaticFields>();
			Holder<Long> responseStatus = new Holder<Long>();
			sopService.getCampaignStaticBusinessFields(campaignName,
					listStaticFields, responseStatus);

			if (responseStatus != null && responseStatus.value != null
					&& responseStatus.value == 1 && listStaticFields != null
					&& listStaticFields.value != null) {
				for (KeyValuePairOfStringStaticFields outcome : listStaticFields.value
						.getKeyValuePairOfStringStaticFields()) {
					stringStaticFieldList.add(outcome.toString());
				}
			} else {
				throw new DataAccessException(
						"Unable to Retrieve Campaign Static Business Fields");
			}

		} catch (Exception e) {
			logger.error(
					"Exception catched while Getting CampaignStaticBusinessFields",
					e);
			throw new DataAccessException(e.getMessage(), e);
		}
		return stringStaticFieldList;
	}

	@Override
	public List<LcmModesWithCValue> getContactModes(String lcmKey)
			throws DataAccessException {

		List<LcmModesWithCValue> modeWithCValueList = new ArrayList<LcmModesWithCValue>();
		try {
			Properties properties = PropertiesUtil
					.getProperties(PropertiesUtil.platform);
			String lcmUrl = properties.getProperty("lcmClient");
			LCMClientService service = new LCMClientService(new URL(lcmUrl));
			LCMClientServiceSoap sopService = service.getLCMClientServiceSoap();
			Holder<ArrayOfModesWithCValue> lstGetModes = new Holder<ArrayOfModesWithCValue>();
			ArrayOfModesWithCValue values = new ArrayOfModesWithCValue();
			ModesWithCValue modesWithCValue = new ModesWithCValue();
			values.getModesWithCValue().add(modesWithCValue);
			lstGetModes.value = values;	
			
			Holder<Long> responseStatus = new Holder<Long>();
			sopService.getContactModes(lcmKey, lstGetModes, responseStatus);

			if (responseStatus != null && responseStatus.value != null
					&& responseStatus.value == 1 && lstGetModes != null
					&& lstGetModes.value != null) {
				LcmModesWithCValue lcmModesWithCValue;
				for (ModesWithCValue outcome : lstGetModes.value
						.getModesWithCValue()) {
					lcmModesWithCValue = new LcmModesWithCValue();
					lcmModesWithCValue
							.setOutcomeID(outcome.getOutcomeID() != null ? outcome
									.getOutcomeID() : "");
					lcmModesWithCValue
							.setDesc(outcome.getDesc() != null ? outcome
									.getDesc() : "");
					lcmModesWithCValue
							.setCampaignID(outcome.getCampaignID() != null ? outcome
									.getCampaignID() : "");
					lcmModesWithCValue
							.setcValue(outcome.getCValue() != null ? outcome
									.getCValue() : "");
					modeWithCValueList.add(lcmModesWithCValue);
				}
			} else {
				throw new DataAccessException(
						"Unable to Retrieve Contact Modes with cValues");
			}

		} catch (Exception e) {
			logger.error(
					"Exception catched while Getting ContactModesWithCValues",
					e);
			throw new DataAccessException(e.getMessage(), e);
		}
		return modeWithCValueList;
	}

	@Override
	public SDLCampaign getSDLCampaignDetails(String lcmCallKey,long requestId)
			throws DataAccessException {

		SDLCampaign campaign = null;
		try {
			List<LcmContactDetail> lcmContactDeatilList = getLcmContactDetail(lcmCallKey,"",requestId);
			if(lcmContactDeatilList != null){
				campaign = new SDLCampaign();
				for(LcmContactDetail detail : lcmContactDeatilList){
				if(detail.getFieldName().equalsIgnoreCase("customerPhNo")){
					campaign.setCustomerPhNo(detail.getValue());
				}
				else if(detail.getFieldName().equalsIgnoreCase("type")){
					campaign.setCustomerPhNo(detail.getValue());
				}
				else if(detail.getFieldName().equalsIgnoreCase("customerId")){
					campaign.setCustomerPhNo(detail.getValue());
				}
				else if(detail.getFieldName().equalsIgnoreCase("entityId")){
					campaign.setCustomerPhNo(detail.getValue());
				}
				else if(detail.getFieldName().equalsIgnoreCase("circleId")){
					campaign.setCustomerPhNo(detail.getValue());
				}
				else if(detail.getFieldName().equalsIgnoreCase("zone")){
					campaign.setCustomerPhNo(detail.getValue());
				}
				else if(detail.getFieldName().equalsIgnoreCase("timeZone")){
					campaign.setCustomerPhNo(detail.getValue());
				}
				else if(detail.getFieldName().equalsIgnoreCase("languagePref")){
					campaign.setCustomerPhNo(detail.getValue());
				}
				else if(detail.getFieldName().equalsIgnoreCase("campaignKey")){
					campaign.setCustomerPhNo(detail.getValue());
				}
				else if(detail.getFieldName().equalsIgnoreCase("campaignId")){
					campaign.setCustomerPhNo(detail.getValue());
				}
				else if(detail.getFieldName().equalsIgnoreCase("campaignEventSource")){
					campaign.setCustomerPhNo(detail.getValue());
				}
				else if(detail.getFieldName().equalsIgnoreCase("product")){
					campaign.setCustomerPhNo(detail.getValue());
				}
				else if(detail.getFieldName().equalsIgnoreCase("offerType")){
					campaign.setCustomerPhNo(detail.getValue());
				}
				else if(detail.getFieldName().equalsIgnoreCase("promoCode")){
					campaign.setCustomerPhNo(detail.getValue());
				}
				else if(detail.getFieldName().equalsIgnoreCase("scriptName")){
					campaign.setCustomerPhNo(detail.getValue());
				}
				else if(detail.getFieldName().equalsIgnoreCase("expiry")){
					campaign.setCustomerPhNo(detail.getValue());
				}

				}
			}

		} catch (Exception e) {
			logger.error("Exception catched while Getting Contact Detail", e);
			throw new DataAccessException(e.getMessage(), e);
		}

		return campaign;
	}
	
}
