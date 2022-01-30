package com.isuite.rjil.iagent.jiomoney.rpc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.common.Account;
import com.isuite.rjil.iagent.jiomoney.common.Customer;
import com.isuite.rjil.iagent.jiomoney.common.ServiceResponse;
import com.isuite.rjil.iagent.jiomoney.common.TranscationInquiry;
import com.isuite.rjil.iagent.jiomoney.common.VerificationQuestion;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.exception.ServiceException;
import com.isuite.rjil.iagent.jiomoney.services.AccountInquiryService;
import com.isuite.rjil.iagent.jiomoney.services.CustomerInquiryService;
import com.isuite.rjil.iagent.jiomoney.services.InstrumentInquiryService;
import com.isuite.rjil.iagent.jiomoney.services.InteractionInquiryService;
import com.isuite.rjil.iagent.jiomoney.services.ServiceLocator;
import com.isuite.rjil.iagent.jiomoney.services.TranscationInquiryService;
import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;
import com.isuite.rjil.iagent.jiomoney.util.PropertiesUtil;
import com.isuite.rjil.iagent.jiomoney.util.Util;

import static com.isuite.rjil.iagent.jiomoney.util.Util.isEmptyString;
import flex.messaging.io.amf.ASObject;

/**
 * 
 * @author NovelVox
 * 
 */

public class InteractionInquiryRpc {
	private static final Logger soapLogger = Logger.getLogger(LOVUtil.SOAPLOGGER);
	private static final String service = "InteractionInquiryService";

	public ServiceResponse viewInteraction(
			String interactionRefNo, String entityType,String agentId) {
		long requestId = Util.getRequestId();
		String lClassName =  this.getClass().getName();
		String lMethodName = "viewInteraction";
		soapLogger.info("************************");
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		ServiceResponse response = null;
		try {
			com.isuite.rjil.iagent.jiomoney.common.CustomerInteraction viewInteractionRequest = ((InteractionInquiryService) ServiceLocator
					.getService(service)).viewInteractionRequest(
					interactionRefNo, entityType, agentId,requestId);

			if (viewInteractionRequest != null) {
				response = new ServiceResponse(
						viewInteractionRequest);
			} else {
				response = new ServiceResponse(
						1, "No record Found in Interaction Inquiry Request");
			}
		} catch (Throwable t) {
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			response = new ServiceResponse(1, t.getMessage());
		}
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
		soapLogger.info("************************");
		return response;
	}

	public ServiceResponse searchInteraction(
			String custId, String entityType, String pageSize, String offSet,String agentId) 
	{
		long requestId = Util.getRequestId();
		String lClassName =  this.getClass().getName();
		String lMethodName = "searchInteraction";
		soapLogger.info("************************");
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		ServiceResponse response = null;
		try {
			List<com.isuite.rjil.iagent.jiomoney.common.CustomerInteraction> searchInteractionRequest = (((InteractionInquiryService) ServiceLocator
					.getService(service)).searchInteractionRequest(custId,
					entityType, pageSize, offSet, agentId,requestId));

			if (searchInteractionRequest != null
					&& searchInteractionRequest.size() > 0) {
				response = new ServiceResponse(
						searchInteractionRequest);
			} else {
				response = new ServiceResponse(
						1, "No record Found in Interaction Inquiry Request");
			}
		} catch (Throwable t) {
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			response = new ServiceResponse(
					1, t.getMessage());
		}
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
		soapLogger.info("************************");
		return response;
	}
	/**
	 * @param verificationQuestion Verification questions from UI
	 * @param questionAnsMapping Map of questionID - Answer mapping 
	 * @param selectedRow Row which is selected
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public ServiceResponse updateVerificationQuestions(List<ASObject> verificationQuestion,ASObject questionAnsMapping,ASObject selectedRow)
	{
		long requestId = Util.getRequestId();
		String lClassName =  this.getClass().getName();
		String lMethodName = "updateVerificationQuestions";
		soapLogger.info("************************");
		soapLogger.info("Request ID : "+requestId+" |Agent ID : |["+lClassName +"."+lMethodName+" ]| Start");
		if(questionAnsMapping == null || questionAnsMapping.isEmpty())
		{
			//Status is 2 so we can identify this particular condition from error condition
			return new ServiceResponse(2,"");
		}
		if(verificationQuestion == null || verificationQuestion.isEmpty())
		{
			return new ServiceResponse(1,"Verification Questions Not Available.");
		}
		Set lKeys = questionAnsMapping.keySet();
		List<VerificationQuestion> getVerificationQuestionAnswerList  = new ArrayList<VerificationQuestion>();
		for (ASObject asObject : verificationQuestion) 
		{
			//get current state of verifications questions on UI
			VerificationQuestion lQuestion = new VerificationQuestion();
			lQuestion.setCustAnswer((String)asObject.get("custAnswer"));
			lQuestion.setQuestionId((String)asObject.get("questionId"));
			lQuestion.setQuestion((String)asObject.get("question"));
			lQuestion.setSelectValue(((Boolean)asObject.get("selectValue")));
			if(((String)selectedRow.get("questionId")).equalsIgnoreCase(lQuestion.getQuestionId()) && lKeys.contains(lQuestion.getQuestionId()))//if selected question is same as current question and it is present in questionAnsMapping 
			{
				if(isEmptyString(lQuestion.getCustAnswer()))
				{
					//If customer ans is blank
					return new ServiceResponse(1, "Please enter an Answer and then press Enter.");
				}
				if(lQuestion.getCustAnswer().equalsIgnoreCase((String)questionAnsMapping.get(lQuestion.getQuestionId())))
				{
					//if customer ans is correct
					lQuestion.setSelectValue(true);
				}
				else
				{
					//if customer ans is incorrect
					lQuestion.setSelectValue(false);
				}
			}
			getVerificationQuestionAnswerList.add(lQuestion);
			
		}
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+lClassName+"|["+lClassName +"."+lMethodName+"] | End");
		soapLogger.info("************************");
		return new ServiceResponse(getVerificationQuestionAnswerList);
	}

	public ServiceResponse getVerificationQuestionList(String categoryId2,
			String categoryId3, String entityType, String custId,
			String accountId, String agentId) 
	{
		long requestId = Util.getRequestId();
		/*Changes 20-04-2016 Mahesh
		 *  
		 *  Now we have tow type of questions
		 *  1. Questions for which ans will be displayed on UI and agent will select correct or incorrect
		 *  2. Questions for which ans will be blank and agent will enter the ans in textbox and we will verify the and in backend
		 *  
		 *  for 2nd type of questions we are maintaining a list having question ID and its ans which will be sent as secondary data on UI. When agent enters 
		 *  the ans on UI this list wiil be sent back and ans will be compared
		 */
		String lClassName =  this.getClass().getName();
		String lMethodName = "getVerificationQuestionList";
		soapLogger.info("************************");
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		ServiceResponse response = null;
		try
		{
			
			if (categoryId3 != null && !categoryId3.isEmpty()) {

				List<VerificationQuestion> lVerificationQuestionListResponse = ((InteractionInquiryService) ServiceLocator.getService(service)).getVerificationQuestionList(categoryId2, categoryId3, entityType, custId,agentId,requestId);

				List<VerificationQuestion> lVerificationQuetionList = null;
				Properties properties = PropertiesUtil.getProperties(PropertiesUtil.jiomoneyutil);
				
				String q1 = properties.getProperty("Question1");
				String q2 = properties.getProperty("Question2");
				String q3 = properties.getProperty("Question3");
				String q4 = properties.getProperty("Question4");
				String q5 = properties.getProperty("Question5");
				String q6 = properties.getProperty("Question6");
				String q7 = properties.getProperty("Question7");
				String q8 = properties.getProperty("Question8");
				String q9 = properties.getProperty("Question9");
				
				String questionWithNoAns = properties.getProperty("QUESTIONS_WITOUT_ANS");
				Map<String, String> lmapQAns = new HashMap<String, String>();
				Customer diplaycustomer = null;
				List<TranscationInquiry> lTransactionList = null;
				int lAnsCount = 0;
				String lError = "";
				if (lVerificationQuestionListResponse != null) 
				{
					lVerificationQuetionList = new ArrayList<VerificationQuestion>();
					for (VerificationQuestion lQuestion : lVerificationQuestionListResponse) 
					{
						//try
						//{
							lVerificationQuetionList.add(lQuestion);
							if (!isEmptyString(q9) && q9.contains(lQuestion.getQuestionId())) 
							{
								if( lTransactionList == null)
								{
									lTransactionList = getTransactionList(custId,agentId,requestId); 
								}
								if (lTransactionList != null && lTransactionList.size() > 0) 
								{
									lQuestion.setCustAnswer(lTransactionList.get(0).getMerchant().getName());
									lAnsCount++;
										
								} else 
								{
									lQuestion.setCustAnswer("");
								}
								continue;
							}
							if (!isEmptyString(q1) && q1.contains(lQuestion.getQuestionId())) 
							{
								if(diplaycustomer == null)
								{
									diplaycustomer = ((CustomerInquiryService) ServiceLocator.getService("CustomerInquiryService")).displayCustomer(custId,agentId,requestId);
								}
								
								if (diplaycustomer != null) 
								{
									
									lQuestion.setCustAnswer(diplaycustomer.getFirstName());
									lAnsCount++;
								} else 
								{
									lQuestion.setCustAnswer("");
								}
								continue;
							}
							if (!isEmptyString(q2) && q2.contains(lQuestion.getQuestionId())) 
							{
								if(diplaycustomer == null)
								{
									diplaycustomer = ((CustomerInquiryService) ServiceLocator.getService("CustomerInquiryService")).displayCustomer(custId,agentId,requestId);
								}
								if (diplaycustomer != null) 
								{
									if(isEmptyString(questionWithNoAns)==false && questionWithNoAns.contains(lQuestion.getQuestionId())) 
									{
										lQuestion.setCustAnswer("DD/MM/YYYY");
										lmapQAns.put(lQuestion.getQuestionId(), diplaycustomer.getDob());
										lAnsCount++;
									}
									else 
									{
										lQuestion.setCustAnswer(diplaycustomer.getDob());
									}
								} else 
								{
									lQuestion.setCustAnswer("");
								}
								continue;
							}
							if (!isEmptyString(q3) && q3.contains(lQuestion.getQuestionId())) 
							{
								if( lTransactionList == null)
								{
									lTransactionList = getTransactionList(custId,agentId,requestId); 
								}
								if (lTransactionList != null && lTransactionList.size() > 0) 
								{
								
									if (lTransactionList.get(0) != null && lTransactionList.get(0).getTransactionType() != null)
									{
										String transactionTypeDescription = convertTransactionTypeIntoItsDiscription(lTransactionList.get(0).getTransactionType());
										if (transactionTypeDescription != null && !transactionTypeDescription .isEmpty())
										{
											lQuestion.setCustAnswer(transactionTypeDescription);
											lAnsCount++;
										} 
										else 
										{
											lQuestion.setCustAnswer("");
										}
										
									} else 
									{
										lQuestion.setCustAnswer("");
									}
								} else 
								{
									lQuestion.setCustAnswer("");
								}
								continue;
							}
							if (!isEmptyString(q4) && q4.contains(lQuestion.getQuestionId())) 
							{
								if( lTransactionList == null) 
								{
									lTransactionList = getTransactionList(custId,agentId,requestId); 
								}
								if (lTransactionList != null && lTransactionList.size() > 0)
								{
									lQuestion.setCustAnswer(lTransactionList.get(0).getTransactionDateTime());
									lAnsCount++;
									
								} 
								else 
								{
									lQuestion.setCustAnswer("");
								}
								continue;
								
							}
							if (!isEmptyString(q5) && q5.contains(lQuestion.getQuestionId())) 
							{
								Customer getPhysicalCardList = ((InstrumentInquiryService) ServiceLocator
										.getService("InstrumentInquiryService"))
										.getPhysicalCardList(custId,agentId,requestId);
								if (getPhysicalCardList != null)
								{
	
									if (getPhysicalCardList.getCardDetails() != null && getPhysicalCardList.getCardDetails().size() > 0) 
									{
												lQuestion.setCustAnswer(getPhysicalCardList.getCardDetails().get(0).getCardNo().substring(7));
												lAnsCount++;
									}
								} else {
									lQuestion.setCustAnswer("NA");
								}
								continue;
							}
							if (!isEmptyString(q6) && q6.contains(lQuestion.getQuestionId())) 
							{
								if( lTransactionList == null) 
								{
									lTransactionList = getTransactionList(custId,agentId,requestId); 
								}
								if (lTransactionList != null && lTransactionList.size() > 0) 
								{
									lQuestion.setCustAnswer(lTransactionList.get(0).getTransactionAmount());
									lAnsCount++;
								} else 
								{
									lQuestion.setCustAnswer("");
								}
								continue;
							}
							if (!isEmptyString(q7) && q7.contains(lQuestion.getQuestionId())) 
							{
								Account accountBalance = ((AccountInquiryService) ServiceLocator
										.getService("AccountInquiryService"))
										.getAccountBalance(accountId, "", "", "",
												"", agentId,requestId);
								if (accountBalance != null) {
									lQuestion.setCustAnswer(accountBalance
											.getAccountBalance());
									lAnsCount++;
								} else {
									lQuestion.setCustAnswer("");
								}
								continue;
							}
							if (!isEmptyString(q8) && q8.contains(lQuestion.getQuestionId())) 
							{
								if(diplaycustomer == null)
								{
									diplaycustomer = ((CustomerInquiryService) ServiceLocator.getService("CustomerInquiryService")).displayCustomer(custId,agentId,requestId);
								}
								if (diplaycustomer != null) {
									lQuestion
											.setCustAnswer(diplaycustomer
													.getPrimEmailId() != null ? diplaycustomer
													.getPrimEmailId() : "");
									lAnsCount++;
								} else {
									lQuestion.setCustAnswer("");
								}
								continue;
							}
//						}catch(Throwable t)
//						{
//							lError = t.getMessage();
//							soapLogger.error("Request Id :"+requestId+"|Exception in ["+lClassName+"."+lMethodName+"]|Error While Fetching Ans for Question : "+lQuestion.getQuestion()+"|"+lError);
//							
//						}
						
						
						lVerificationQuetionList.remove(lQuestion);
						}
				} else 
				{
					response = new ServiceResponse(1,
							"No record Found in Innteraction Inquiry ");
				}
				if (lVerificationQuetionList != null)
				{
					//if(lAnsCount >=3)
					//{
						response = new ServiceResponse(lVerificationQuetionList);
						response.setSecodaryData(lmapQAns);
					//}
					//else 
					//{
					//	soapLogger.error("Request Id :"+requestId+"|Exception in ["+lClassName+"."+lMethodName+"]|Error While Fetching Ans for Question : Less than 3 Answsers");
					//	response = new ServiceResponse(1, lError);
					//}
					
				} else 
				{
					response = new ServiceResponse(1,
							"No record Found For Verification Questions");
				}

			} else {
				return new ServiceResponse(2, "Please Select  Sub Sub Category");
			}

		} catch (Throwable t) 
		{
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			response = new ServiceResponse(1, t.getMessage());
		}
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
		soapLogger.info("************************");
		return response;
	}

	private static String getFormattedDate(Calendar pCalendar, String pDateFormat)
	{
		SimpleDateFormat lDateFormat = new SimpleDateFormat(pDateFormat);
		return lDateFormat.format(pCalendar.getTime());
	}
	private List<TranscationInquiry> getTransactionList(String custId,String agentId, long requestId) throws DataAccessException, ServiceException 
	{
		Calendar lStartDate = Calendar.getInstance();
		lStartDate.set(Calendar.DAY_OF_MONTH, 1);
		Calendar lEndDate = Calendar.getInstance();
		String lFromDate = getFormattedDate(lStartDate,"dd/MM/yyyy");
		String lToDate = getFormattedDate(lEndDate,"dd/MM/yyyy");
		
		List<TranscationInquiry> lList = ((TranscationInquiryService) ServiceLocator.getService("TranscationInquiryService")).getTransactionData(lFromDate, lToDate, custId, "","","1",null, agentId,true,requestId);	
		
		if(lList == null || lList.isEmpty())
		{
			for (int i = 0; i < 2; i++) 
			{
				lStartDate.add(Calendar.MONTH, -1);
				lEndDate.add(Calendar.MONTH, -1);
				lEndDate.set(Calendar.DAY_OF_MONTH, lEndDate.getActualMaximum(Calendar.DAY_OF_MONTH));
				lFromDate = getFormattedDate(lStartDate,"dd/MM/yyyy");
				lToDate = getFormattedDate(lEndDate,"dd/MM/yyyy");
				lList = ((TranscationInquiryService) ServiceLocator.getService("TranscationInquiryService")).getTransactionData(lFromDate, lToDate, custId, "","","1",null, agentId,true,requestId);
				if(lList != null &&  !lList.isEmpty())
				{
					break;
				}	
			}
			return lList;
		}
		else
		{
			return lList;
		}
	}

	/**
	 * This method is use to convert the Transaction type into the description.
	 * This is dependent on the property file.
	 * 
	 * @param transactionType
	 *            : transaction Type
	 * @return String
	 */
	private String convertTransactionTypeIntoItsDiscription(
			String transactionType) {
		String convertTransactionTypeIdIntoValue = null;
		if (transactionType != null && !transactionType.isEmpty()) {
			//PropertiesUtil.getProperties(PropertiesUtil.platform);
			Properties jioMoneyUtilProperty=PropertiesUtil.getProperties(PropertiesUtil.jiomoneyutil);
			Set<Object> keys = PropertiesUtil.getAllKeys(jioMoneyUtilProperty);
			if (keys != null && keys.size() > 0) {
				for (Object transactionId : keys) {
					if (transactionType.substring(0, 2).equals(
							(String) transactionId)) {
						soapLogger.info("--TransactionType ID is ---"
								+ transactionId
								+ " Its Discription "
								+ PropertiesUtil
										.getPropertyValue(jioMoneyUtilProperty,(String) transactionId));
						convertTransactionTypeIdIntoValue = PropertiesUtil
								.getPropertyValue(jioMoneyUtilProperty,(String) transactionId);
						return convertTransactionTypeIdIntoValue;
					}
				}
			} else {
				return convertTransactionTypeIdIntoValue;
			}
		}
		return convertTransactionTypeIdIntoValue;
	}

	public static String getVerificationAnswer(String myString) {
		String fieldValue = null;
		String[] fieldName;
		String serviceFields = myString;
		fieldName = serviceFields.split(",");
		for (int k = 0; k < fieldName.length - 1; k++) {
			fieldValue = fieldName[k];
		}
		return fieldValue;
	}
}