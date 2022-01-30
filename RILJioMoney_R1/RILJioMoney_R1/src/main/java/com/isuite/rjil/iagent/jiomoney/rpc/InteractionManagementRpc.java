package com.isuite.rjil.iagent.jiomoney.rpc;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.common.CustomerInteraction;
import com.isuite.rjil.iagent.jiomoney.common.InteractionResponse;
import com.isuite.rjil.iagent.jiomoney.common.ReferenceData;
import com.isuite.rjil.iagent.jiomoney.common.ServiceResponse;
import com.isuite.rjil.iagent.jiomoney.exception.ServiceException;
import com.isuite.rjil.iagent.jiomoney.services.InteractionManagementService;
import com.isuite.rjil.iagent.jiomoney.services.ServiceLocator;
import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;
import com.isuite.rjil.iagent.jiomoney.util.PropertiesUtil;
import com.isuite.rjil.iagent.jiomoney.util.Util;

import flex.messaging.io.amf.ASObject;

/**
 * 
 * @author NovelVox
 * 
 */

public class InteractionManagementRpc {
	private static final Logger soapLogger = Logger.getLogger(LOVUtil.SOAPLOGGER);
	private static final String service = "InteractionManagementService";
	
	@SuppressWarnings("unchecked")
	private InteractionResponse getInterationresponse(InteractionResponse pResponse,CustomerInteraction pInteraction,ASObject pCategory,ASObject pSubCategory,ASObject pSubSubCategory,ASObject pCategory4,String pDescription,
			String pNotes,String pAgentCode)throws ServiceException
	{
		try
		{
			ServiceRequestInquiryRpc lRpc = new ServiceRequestInquiryRpc();
			String lCategoryCode1 = (String)pCategory.get("type");
			List<ReferenceData> llistServiceCategory1=new ArrayList<ReferenceData>();
			ReferenceData lData = null;
			///Get List Of Category 1 Details and set in Response
			ServiceResponse lServiceResponse = lRpc.getServiceCategory1("C", pAgentCode);
			llistServiceCategory1.addAll((List<ReferenceData>)lServiceResponse.getData());
			if(llistServiceCategory1!=null && llistServiceCategory1.isEmpty() == false && Util.isEmptyString(lCategoryCode1)==false)
			{
				for (ReferenceData referenceData : llistServiceCategory1) 
				{
					if(referenceData.getDescription().equals((String)pCategory.get("description")))
					{
						lData = referenceData;
						break;
					}
				}
			}
			else
			{
				throw new ServiceException("Exception Caught While Fetching Service Category 1");
			}
			llistServiceCategory1.remove(lData);
			llistServiceCategory1.add(0, lData);
			RilReferenceDataRpc lReferenceDataRpc = new RilReferenceDataRpc();
			lServiceResponse = lReferenceDataRpc.getImpact((String)pCategory4.get("impact"));
			List<ReferenceData> lImpact = new ArrayList<ReferenceData>();
			lImpact.addAll((List<ReferenceData>)lServiceResponse.getData());
			lServiceResponse = lReferenceDataRpc.getUrgency((String)pCategory4.get("urgency"));
			List<ReferenceData> Urgency = new ArrayList<ReferenceData>();
			Urgency.addAll((List<ReferenceData>)lServiceResponse.getData());
			pResponse.setCategory1(llistServiceCategory1);
			pResponse.setCategory2((String)pSubCategory.get("code"));
			pResponse.setCategory3((String)pSubSubCategory.get("code"));
			pResponse.setCategory4((String)pCategory4.get("code"));
			pResponse.setImpact(lImpact);
			pResponse.setUrgency(Urgency);
			pResponse.setDesc(pDescription);
			pResponse.setNotes(pNotes);
		}
		catch(Exception lException)
		{
			throw new ServiceException(lException.getMessage());
		}
		return pResponse;
	}
	/**
	 * 
	 * @param interactionRefNo
	 * @param interactionDate
	 * @param category
	 * @param subCategory
	 * @param subSubCategory
	 * @param description
	 * @param callDuration
	 * @param custId
	 * @param channel
	 * @param agentCode
	 * @param verificationQuestionAir
	 * @param action
	 * @param notes
	 * @return ServiceResponse
	 * @throws ServiceException
	 */
	
	public ServiceResponse createInteraction(String interactionRefNo,String interactionDate, ASObject category, ASObject subCategory,ASObject subSubCategory, ASObject category4,String description, String callDuration,
			String custId, String channel, String agentCode,String verificationQuestionAir, String action, String notes,List<ASObject> verificationQuestion,ASObject questionAnsMapping) throws ServiceException {
		ServiceResponse response = null;
		String lClassName =  this.getClass().getName();
		String lMethodName = "createInteraction";
		soapLogger.info("************************");
		long requestId = Util.getRequestId();
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentCode+"|["+lClassName +"."+lMethodName+" ]| Start");
		try 
		{
			int ycount = 0;
			Properties lProperties = PropertiesUtil.getProperties(PropertiesUtil.jiomoneyutil);
			String questionWithNoAns = lProperties.getProperty("QUESTIONS_WITOUT_ANS");
			List<String> selectedQuestion = null;
			if("N".equalsIgnoreCase((String)category4.get("verifyUser")))
			{
				//verifications questions are missing for this particular sub-sub category
				ycount = 4;
				selectedQuestion = new ArrayList<String>();
				selectedQuestion.add("");
			}
			else
			{
				if (verificationQuestion != null && verificationQuestion.size() > 0) 
				{
					selectedQuestion = new ArrayList<String>();
					String verifiationQustionResult = "";
					
					for (ASObject asObj : verificationQuestion) 
					{
						if (asObj != null) 
						{
							if ((Boolean)asObj.get("selectValue"))  
							{
								if(questionWithNoAns.contains((String)asObj.get("questionId")))
								{
									//Validate if DOB entered is correct
									String lstrAns = (String)questionAnsMapping.get((String)asObj.get("questionId"));
									if(Util.isEmptyString(lstrAns) == false && !lstrAns.equalsIgnoreCase((String)asObj.get("custAnswer")))
									{
										return new ServiceResponse(1,"Invalid Answer for DOB");
									}
								}
								ycount++;
								verifiationQustionResult += "C";
							}
							else
							{
								verifiationQustionResult += "N";
							}
						}
					}
					selectedQuestion.add(verifiationQustionResult);
				} 
				else
				{
					response = new ServiceResponse(1,"Verification Question Not Available");
				}
			}
			if(ycount >=3)
			{

				CustomerInteraction createInteraction = ((InteractionManagementService) ServiceLocator
						.getService(service)).createInteraction(
						interactionRefNo, interactionDate, (String)category.get("code"),
						(String)subCategory.get("code"), (String)subSubCategory.get("code"), (String)category4.get("code"),description,
						callDuration, custId, channel, agentCode,
						verificationQuestionAir, action, notes,
						selectedQuestion,"",requestId);

				if (createInteraction != null) 
				{
					InteractionResponse lResponse = new  InteractionResponse();
					try
					{
						lResponse = getInterationresponse(lResponse,createInteraction, category, subCategory, subSubCategory, category4, description, notes, agentCode);
					}
					catch(Exception lException)
					{
						soapLogger.error("Error while creating IR response", lException);
						lResponse.setCategoriesAvailable("false");
					}
					lResponse.setInteractionRefNumber(createInteraction.getInteractionRefNo());
					lResponse.setMsg("Interaction Created with Reference No. "+ createInteraction.getInteractionRefNo());
					response = new ServiceResponse(lResponse);
				} 
				else 
				{
					response = new ServiceResponse(1,
							"No record Found in  create Interaction");
				}
			
			}
			else
			{

				response = new ServiceResponse(1,
						"Please Select at least 3 Correct Answers");
			}

		} 
		catch (Throwable t) 
		{
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			response = new ServiceResponse(1, t.getMessage());
		}
		finally
		{
			soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentCode+"|["+lClassName +"."+lMethodName+"] | End");
			soapLogger.info("************************");
		}

		return response;
	}

	/**
	 * 
	 * @param interactionRefNo
	 * @param interactionDate
	 * @param category
	 * @param subCategory
	 * @param subSubCategory
	 * @param description
	 * @param callDuration
	 * @param custId
	 * @param channel
	 * @param agentCode
	 * @param verificationQuestionAir
	 * @param action
	 * @param notes
	 * @return
	 * @throws ServiceException
	 */
	public ServiceResponse createInteractionWithNoVerificationQuestion(String interactionRefNo, String interactionDate, ASObject category,ASObject subCategory, ASObject subSubCategory,ASObject category4, String description,
			String callDuration, String custId, String channel,String agentCode, String verificationQuestionAir, String action,String notes,String pCommunicationMode,String pSkipVerificationFlag) throws ServiceException {
		ServiceResponse response = null;
		List<String>  verificationQuestion=null;
		String lClassName =  this.getClass().getName();
		long requestId = Util.getRequestId();
		String lMethodName = "createInteractionWithNoVerificationQuestion";
		soapLogger.info("************************");
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentCode+"|["+lClassName +"."+lMethodName+" ]| Start");
		try 
		{

			if("true".equalsIgnoreCase(pSkipVerificationFlag) == true && Util.isEmptyString(channel))
			{
				return new ServiceResponse(1, "Please Select Channel");
			}
			verificationQuestion=new ArrayList<String>();
			CustomerInteraction createInteraction = ((InteractionManagementService) ServiceLocator
					.getService(service)).createInteraction(interactionRefNo,
					interactionDate,(String) category.get("code"),(String) subCategory.get("code"),(String) subSubCategory.get("code"),(String)category4.get("code"),
					description, callDuration, custId, channel, agentCode,
					verificationQuestionAir, action, notes, verificationQuestion,pCommunicationMode,requestId);

			if (createInteraction != null) 
			{
				InteractionResponse lResponse = new  InteractionResponse();
				try
				{
					lResponse = getInterationresponse(lResponse,createInteraction, category, subCategory, subSubCategory, category4, description, notes, agentCode);
				}
				catch(Exception lException)
				{
					soapLogger.error("Error while creating IR response", lException);
					lResponse.setCategoriesAvailable("false");
				}
				lResponse.setInteractionRefNumber(createInteraction.getInteractionRefNo());
				lResponse.setMsg("Interaction Created with Reference No. "+ createInteraction.getInteractionRefNo());
				response = new ServiceResponse(lResponse);
			} 
			else 
			{
				response = new ServiceResponse(1,
						"No record Found in createInteractionWithNoVerificationQuestion");
			}
		} catch (Throwable t) 
		{
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			response = new ServiceResponse(1, t.getMessage());
		}
		finally
		{
			soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentCode+"|["+lClassName +"."+lMethodName+"] | End");
			soapLogger.info("************************");
		}
		return response;
	}

}
