package com.isuite.rjil.iagent.jiomoney.dao.impl;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.xml.ws.BindingProvider;

import org.apache.log4j.Logger;

import ti.com.ril.rpsl.entities.commontypes_v1_0.TInfo;
import ti.com.ril.rpsl.entities.commontypes_v1_0.TInfos;
import ti.com.ril.rpsl.entities.commontypes_v1_0.TResponse;
//import ti.com.ril.rpsl.entities.header_v2_0.TBusinessApplicationContext;
//import ti.com.ril.rpsl.entities.header_v2_0.TMessageContext;
//import ti.com.ril.rpsl.entities.header_v2_0.TRqHeader;
import ti.com.ril.rpsl.entities.transaction_v1_0.TAdvancedFilter;
import ti.com.ril.rpsl.entities.transaction_v1_0.TBasicFilter;
import ti.com.ril.rpsl.entities.transaction_v1_0.TDataRecordSets;
import ti.com.ril.rpsl.entities.transaction_v1_0.TLGTPredicate;
import ti.com.ril.rpsl.entities.transaction_v1_0.TPredicate;
import ti.com.ril.rpsl.entities.transaction_v1_0.TTransactionRq;
import ti.com.ril.rpsl.entities.transaction_v1_0.TTransactionRs;
import ti.com.ril.rpsl.entities.transaction_v1_0.TTransactionSummaryBasicFilter;
import ti.com.ril.rpsl.entities.transaction_v1_0.TTransactionSummaryRs;
import ti.com.ril.rpsl.services.transactioninquiry_v1_0.GetTransactionDataRq;
import ti.com.ril.rpsl.services.transactioninquiry_v1_0.GetTransactionDataRs;
import ti.com.ril.rpsl.services.transactioninquiry_v1_0.GetTransactionSummaryRq;
import ti.com.ril.rpsl.services.transactioninquiry_v1_0.GetTransactionSummaryRs;
import ti.com.ril.rpsl.services.transactioninquiry_v1_0.SearchCriteria;
import ti.com.ril.rpsl.services.transactioninquiry_v1_0.TransactionSummaryCriteria;
import ti.com.ril.rpsl.wsdls.transactioninquiry_v1_0.GetTransactionDataFault;
import ti.com.ril.rpsl.wsdls.transactioninquiry_v1_0.Operations;
import ti.com.ril.rpsl.wsdls.transactioninquiry_v1_0.TransactionInquiry;

import com.isuite.rjil.iagent.jiomoney.common.Merchant;
import com.isuite.rjil.iagent.jiomoney.common.PaginationValues;
import com.isuite.rjil.iagent.jiomoney.common.Status;
import com.isuite.rjil.iagent.jiomoney.common.Transaction;
import com.isuite.rjil.iagent.jiomoney.common.TransactionTypeDetails;
import com.isuite.rjil.iagent.jiomoney.common.TranscationInquiry;
import com.isuite.rjil.iagent.jiomoney.dao.TranscationInquiryDao;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.util.HeaderStatus;
import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;
import com.isuite.rjil.iagent.jiomoney.util.PropertiesUtil;
import com.isuite.rjil.iagent.common.util.RequestUtils;
import com.isuite.rjil.iagent.jiomoney.util.Util;
import com.isuite.rjil.iagent.jiomoney.util.WSHandlerResolver;
import com.isuite.rjil.iagent.jiomoney.util.XMLGregorianCalendarUtil;

/**
 * Title: TranscationInquiryDaoImpl.java <br>
 * Company: 2014-15 NovelVox Software Pvt Ltd<br>
 * 
 * @author Anand Raman[AR]
 * @version Build 0.5 <br>
 *          Changes and requirement:<br>
 *          <ul>
 * 
 *          </ul>
 * @version ReleaseR1-Sprint 3 Changes and requirement:<br>
 *          <ul>
 * 			<li> 1-9-20115 [AR] [CR] Change in WSDL end Point
 *          </ul>
 * 
 */
public class TranscationInquiryDaoImpl implements TranscationInquiryDao {
	private static final Logger logger = Logger.getLogger(TranscationInquiryDaoImpl.class);
	private static final Logger soapLogger = Logger.getLogger(LOVUtil.SOAPLOGGER);
	private Properties platformProperties = PropertiesUtil.getProperties(PropertiesUtil.platform);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.isuite.rjil.iagent.jiomoney.dao.TranscationInquiryDao#getTransactionData
	 * (java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<TranscationInquiry> getTransactionData(String fromDate,String toDate, String customerID, String cardNumber,String pPageIndex,String pMaxRecords,PaginationValues lValues,  String agentCode,boolean isVerificationRequest,long requestId)
			throws DataAccessException 
	{
		String lClassName =  this.getClass().getName();
		String lMethodName = "getTransactionData";
		
		soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentCode+"|["+lClassName +"."+lMethodName+" ]| Start");
		try 
		{
			TDataRecordSets lRecordSets = null;
			/************************ Setting searchCriteria Values ************************************************/
			SearchCriteria criteria = new SearchCriteria();

			/************************ Set TBasic Filter ************************************************/
			TBasicFilter filter = new TBasicFilter();
			TPredicate customer = new TPredicate();
			customer.setValue(customerID);
			customer.setOperator("=");
			filter.setCustomerId(customer);
			SimpleDateFormat lRequestFormatter  = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat lInputFormatter = new SimpleDateFormat("dd/MM/yyyy");
			//swapping start date and end date as suggested by tibco to get data in ascending order
			if(Util.isEmptyString(fromDate) == false)
			{
				Date lDate = lInputFormatter.parse(fromDate);
				fromDate = lRequestFormatter.format(lDate);
				filter.setEndDateTime(fromDate+"00000000");
			}
			if(Util.isEmptyString(toDate) == false)
			{
				Date lDate = lInputFormatter.parse(toDate);
				toDate = lRequestFormatter.format(lDate);
				filter.setStartDateTime(toDate+"23595959");
			}
			criteria.setBasicFilter(filter);

			TDataRecordSets dataRecord = new TDataRecordSets();
			if(Util.isEmptyString(pMaxRecords)==false && Util.isValidNumber(pMaxRecords) == false)
			{
				dataRecord.setMaxRecords((String) platformProperties.getProperty("maxRecord"));
			}
			else
			{
				dataRecord.setMaxRecords(pMaxRecords);
			}
			if(Util.isEmptyString(pPageIndex)==false && pPageIndex.equalsIgnoreCase("NA") == false)
			{
				TLGTPredicate lPredicate = new TLGTPredicate();
				lPredicate.setValue(pPageIndex);
				lPredicate.setOperator(">");
				dataRecord.setPageOffset(lPredicate);
			}
			
			// TLGTPredicate tlgtPredicate=new TLGTPredicate();
			// tlgtPredicate.setOperator("=");
			// tlgtPredicate.setValue((String)platformProperties.getProperty("pageOffset"));
			// dataRecord.setPageOffset(tlgtPredicate);

			/************************ Set TAdvancedFilter Filter ************************************************/
			TAdvancedFilter advancedFilter = new TAdvancedFilter();
			criteria.setAdvancedFilter(advancedFilter);
			//boolean isVerificationRequest = false;
			TTransactionRq tTransactionRq = new TTransactionRq();
			tTransactionRq.setMerchantName("y");
			tTransactionRq.setTransactionType("y");
			tTransactionRq.setTransactionDateTime("y");
			tTransactionRq.setTransactionAmount("y");
			tTransactionRq.setTransactionClass("y");
			if(isVerificationRequest == false)
			{
				tTransactionRq.setAccountNumber("y");
				tTransactionRq.setAcquirerBank("y");
				//tTransactionRq.setAcquirerImpact("y");
				tTransactionRq.setAcquirerInstitution("y");
				//tTransactionRq.setAuthCode("y");
				tTransactionRq.setCardNumber("y");
				tTransactionRq.setCardNumberPrefix("y");
				tTransactionRq.setCardNumberSuffix("y");
				tTransactionRq.setCardScheme("y");
				tTransactionRq.setCardType("y");
				tTransactionRq.setCustomerId("y");
				tTransactionRq.setInstrumentType("y");
				tTransactionRq.setInvoiceNumber("y");
				tTransactionRq.setIssuerBank("y");
				//tTransactionRq.setIssuerImpact("y");
				tTransactionRq.setIssuerInstitution("y");
				tTransactionRq.setMCC("y");
				tTransactionRq.setMerchantCommission("y");
				tTransactionRq.setMerchantId("y");
				tTransactionRq.setMerchantRRN("y");
				tTransactionRq.setMerchantServiceTax("y");
				//tTransactionRq.setMerchantSettlementAccount("y");
				//tTransactionRq.setMerchantSettlementBank("y");
				tTransactionRq.setMerchantSettlementDateTime("y");
				tTransactionRq.setMerchantTransactionAmount("y");
				//tTransactionRq.setPaymentReferenceNumber("y");
				tTransactionRq.setPosReadingMethod("y");
				tTransactionRq.setPosVerficationMethod("y");
				tTransactionRq.setPrimaryKey("y");
				tTransactionRq.setRetrievalReferenceNumber("y");
				tTransactionRq.setTerminalId("y");
				tTransactionRq.setTransactionChannel("y");
				
				tTransactionRq.setTransactionDisposition("y");
				tTransactionRq.setTransactionId("y");
				tTransactionRq.setTransactionNote("y");
				//tTransactionRq.setTransactionRecipient("y");
				tTransactionRq.setTransactionStatus("y");
				tTransactionRq.setP2UStatus("y");
			}
			
				
			
			/******************* Setting the WSDL URL ******************/

			String instrumentInquiryWsdlUrl = platformProperties
					.getProperty("transactionInquiry");
			URL wsdlUrl = new URL(instrumentInquiryWsdlUrl);
			//1-9-2015 [AR] [CH] Change in wsdl
			//TransactionInquiryServiceAgent agent = new TransactionInquiryServiceAgent(wsdlUrl);
			TransactionInquiry agent = new TransactionInquiry(wsdlUrl);
			agent.setHandlerResolver(new WSHandlerResolver(agentCode,requestId			));
			Operations endPoint = agent.getOperationsEndpoint();
			/******************* Setting the endpoint URL For Dealer Inquiry ******************/
			String endPointURL = platformProperties
					.getProperty("transactionInquiryEndPoint");
			BindingProvider bindingProvider = (BindingProvider) endPoint;
			// logger.error("findDealerProfile :- Setting the end point for dealer Inquiry");
			bindingProvider.getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointURL);
			/************************ Passing Request parameter ************************************************/
			GetTransactionDataRq request = new GetTransactionDataRq();
			request.setDataRecord(dataRecord);
			request.setSearchCriteria(criteria);
			request.setTransactionRqElements(tTransactionRq);
			request.setRqHeader(RequestUtils.getRequestHeader());
//			TRqHeader lHeader = new TRqHeader();
//			lHeader.setVersion("1.0");
//			TBusinessApplicationContext businessApplicationRequest = new TBusinessApplicationContext();
//			businessApplicationRequest.setSrcApplicationname("PORTAL_NOVELVOX_NA_001");
//			businessApplicationRequest.setServiceVersion("1.0");
//			businessApplicationRequest.setOperationVersion("1.0");
//			businessApplicationRequest.setOperationName("getTransactionSummary");
//			businessApplicationRequest.setMessageType("REQ");
//			lHeader.setBusinessApplicationContext(businessApplicationRequest);
//			TMessageContext lMsgContext =new TMessageContext();
//			lMsgContext.setMessageID(String.valueOf(UUID.randomUUID()));
//			lMsgContext.setCorrelationID(String.valueOf(UUID.randomUUID()));
//			lMsgContext.setTimeStamp(new Date().toString());
//			lMsgContext.setEsbID(String.valueOf(UUID.randomUUID()));
//			lMsgContext.setBusinessID("123");
//			lHeader.setMessageContext(lMsgContext);
//			request.setRqHeader(lHeader);

			/************************ Response ************************************************/
			GetTransactionDataRs response = endPoint
					.getTransactionData(request);
			if (response.getResponseDetails() != null
					&& response.getResponseDetails().getStatus() != null
					&& response.getResponseDetails().getStatus()
							.equalsIgnoreCase("FAILED")) {
				if (response.getResponseDetails().getErrors() != null
						&& response.getResponseDetails().getErrors().getError() != null
						&& response.getResponseDetails().getErrors().getError()
								.size() > 0
						&& response.getResponseDetails().getErrors().getError()
								.get(0).getErrorMsg() != null) {
					
					throw new DataAccessException(response.getResponseDetails()
							.getErrors().getError().get(0).getErrorMsg());
				}
			}
			
			List<TranscationInquiry> returnList = null;
			if (response != null) 
			{
				returnList = new ArrayList<TranscationInquiry>();

				// if (response.getTransactionList().getDataRecord() != null)
				//
				// {
				// transcationInquiryPojo.setPageOffset(response.getTransactionList()!=null
				// ?
				// response.getTransactionList().getDataRecord().getPageOffset()
				// != null ?
				// response.getTransactionList().getDataRecord().getPageOffset():
				// "" :"");
				// }

				if (response.getTransactionList() != null) {
					if (response.getTransactionList()
							.getTransactionRsElements() != null
							&& response.getTransactionList()
									.getTransactionRsElements().size() > 0) 
					{
						List<TTransactionRs> llistTransactions = response
						.getTransactionList()
						.getTransactionRsElements();
						/************************ Setting TransactionRsElements ************************************************/
						lRecordSets = response.getTransactionList().getDataRecord();
						for (TTransactionRs transactionResponse : llistTransactions)
						{
							TranscationInquiry transcationInquiryPojo = new TranscationInquiry();
							if(transactionResponse.getTransactionAmount()!=null && Util.isEmptyString(transactionResponse.getTransactionAmount().getValue()) == false)
							{
								try 
								{
									transcationInquiryPojo.setTransactionAmount(""+(Double.parseDouble(transactionResponse.getTransactionAmount().getValue())/100));
								} catch (Exception e) 
								{
									logger.error("Error in get transaction data : Transaction amount is not a number", e);
									transcationInquiryPojo.setTransactionAmount("");
								}
							}


			
			
			
						transcationInquiryPojo
						.setTransactionDateTime(transactionResponse
								.getTransactionDateTime() != null ? XMLGregorianCalendarUtil.convertDateFormat(
								transactionResponse
										.getTransactionDateTime(),
								"yyyyMMddhhmmss",
								"dd-MM-yyyy HH:mm:ss")
								: "");
							Merchant merchantPojo = new Merchant();
							merchantPojo
							.setName(transactionResponse
									.getMerchantName() != null ? transactionResponse
									.getMerchantName() : "");
							String lstrTransactionType = transactionResponse.getTransactionType().getValue();
							if(!Util.isEmptyString(lstrTransactionType))
							{
								if(transactionResponse.getTransactionClass() != null && transactionResponse.getTransactionType() != null && transactionResponse.getTransactionType().getValue() != null && TransactionTypeDetails.getTransactionTypeDetails(transactionResponse.getTransactionType().getValue()+"|"+transactionResponse.getTransactionClass()) != null)
								{
									TransactionTypeDetails lTypeDetails = TransactionTypeDetails.getTransactionTypeDetails(transactionResponse.getTransactionType().getValue()+
											"|"+transactionResponse.getTransactionClass());
									if(lTypeDetails != null)
									{
										transcationInquiryPojo.setTransactionType(lTypeDetails.getTransactionType());
										transcationInquiryPojo.setTransactionDesc(lTypeDetails.getTransactionDesc());
										transcationInquiryPojo.setTransactionDescShort(lTypeDetails.getTransactionDescShort());
										transcationInquiryPojo.setTransactionCategory(lTypeDetails.getTransactionCategory());
										
										String transactionClass = lTypeDetails.getTransactionDescShort();
										String transactionTypeTemp = lTypeDetails.getTransactionType();
										if(transactionTypeTemp.equals("1090001000")&& (transactionClass.equals("UDP")||transactionClass.equals("UDW")||transactionClass.equals("UDU"))){
											if(transactionResponse.getP2UStatus()!=null){
											if(transactionResponse.getP2UStatus().equals("030") )
												transcationInquiryPojo.setTransactionDesc("PENDING "+lTypeDetails.getTransactionDesc());
											else if(transactionResponse.getP2UStatus().equals("040"))
												transcationInquiryPojo.setTransactionDesc("SUCESS "+lTypeDetails.getTransactionDesc());
											else if(transactionResponse.getP2UStatus().equals("050"))
												transcationInquiryPojo.setTransactionDesc("Expired "+lTypeDetails.getTransactionDesc());
											}
											
										}//End of P2U Transaction
										
										if(transactionTypeTemp.equals("2000002000")&& (transactionClass.equals("MPR")||transactionClass.equals("NRC")||transactionClass.equals("BRC")
												||transactionClass.equals("WRO")||transactionClass.equals("WRC")||transactionClass.equals("R2P")
												||transactionClass.equals("QRC")||transactionClass.equals("R2M")||transactionClass.equals("RPR")
												||transactionClass.equals("RBR"))){
											if(transactionResponse.getP2UStatus()!=null){
												if(transactionResponse.getP2UStatus().equals("032") )
													transcationInquiryPojo.setTransactionDesc("PENDING "+lTypeDetails.getTransactionDesc());
												else if(transactionResponse.getP2UStatus().equals("042"))
													transcationInquiryPojo.setTransactionDesc("SUCESS "+lTypeDetails.getTransactionDesc());
												else if(transactionResponse.getP2UStatus().equals("052"))
													transcationInquiryPojo.setTransactionDesc("Expired "+lTypeDetails.getTransactionDesc());
												}
											
										}//End Of Refund
										
									}
								}
								
							}
							transcationInquiryPojo.setMerchant(merchantPojo);
							if(isVerificationRequest==false)
							{
								
							

							/******************* TransactionRsInfo *******************************/
							
							transcationInquiryPojo.setMcc(transactionResponse
									.getMCC() != null ? transactionResponse
									.getMCC() : "");
							transcationInquiryPojo
									.setCustomerId(transactionResponse
											.getCustomerId() != null ? transactionResponse
											.getCustomerId() : "");
							transcationInquiryPojo
									.setCardNumber(transactionResponse
											.getCardNumber() != null ? transactionResponse
											.getCardNumber() : "");
							transcationInquiryPojo
									.setCardNumberSuffix(transactionResponse
											.getCardNumberSuffix() != null ? transactionResponse
											.getCardNumberSuffix() : "");
							transcationInquiryPojo
									.setCardType(transactionResponse
											.getCardType() != null ? transactionResponse
											.getCardType() : "");
							transcationInquiryPojo
									.setCardNumberPrefix(transactionResponse
											.getCardNumberPrefix() != null ? transactionResponse
											.getCardNumberPrefix() : "");
							transcationInquiryPojo
									.setCardScheme(transactionResponse
											.getCardScheme() != null ? transactionResponse
											.getCardScheme() : "");
							transcationInquiryPojo
									.setTerminalId(transactionResponse
											.getTerminalId() != null ? transactionResponse
											.getTerminalId() : "");
							transcationInquiryPojo
									.setInstrumentType(transactionResponse
											.getInstrumentType() != null ? transactionResponse
											.getInstrumentType() : "");
							transcationInquiryPojo
									.setIssuerImpact(transactionResponse
											.getIssuerImpact() != null ? transactionResponse
											.getIssuerImpact() : "");
							transcationInquiryPojo
									.setIssuerInstitution(transactionResponse
											.getIssuerInstitution() != null ? transactionResponse
											.getIssuerInstitution() : "");
							transcationInquiryPojo
									.setIssuerBank(transactionResponse
											.getIssuerBank() != null ? transactionResponse
											.getIssuerBank() : "");
							transcationInquiryPojo
									.setInvoiceNumber(transactionResponse
											.getInvoiceNumber() != null ? transactionResponse
											.getInvoiceNumber() : "");
							transcationInquiryPojo
									.setAccountNumber(transactionResponse
											.getAccountNumber() != null ? transactionResponse
											.getAccountNumber() : "");
							transcationInquiryPojo
									.setAcquirerInstitution(transactionResponse
											.getAcquirerInstitution() != null ? transactionResponse
											.getAcquirerInstitution() : "");
							transcationInquiryPojo
									.setAcquirerBank(transactionResponse
											.getAcquirerBank() != null ? transactionResponse
											.getAcquirerBank() : "");
							transcationInquiryPojo
									.setAuthCode(transactionResponse
											.getAuthCode() != null ? transactionResponse
											.getAuthCode() : "");
							transcationInquiryPojo
									.setPosVerficationMethod(transactionResponse
											.getPosVerficationMethod() != null ? transactionResponse
											.getPosVerficationMethod() : "");
							transcationInquiryPojo
									.setPosReadingMethod(transactionResponse
											.getPosReadingMethod() != null ? transactionResponse
											.getPosReadingMethod() : "");
							transcationInquiryPojo
									.setPrimaryKey(transactionResponse
											.getPrimaryKey() != null ? transactionResponse
											.getPrimaryKey() : "");
							transcationInquiryPojo
									.setPaymentReferenceNumber(transactionResponse
											.getPaymentReferenceNumber() != null ? transactionResponse
											.getPaymentReferenceNumber() : "");

							/******************* getMerchantInfo *******************************/
							
							merchantPojo
									.setId(transactionResponse.getMerchantId() != null ? transactionResponse
											.getMerchantId() : "");
							
							merchantPojo
									.setSettlementDate(transactionResponse
											.getMerchantSettlementDateTime() != null ? transactionResponse
											.getMerchantSettlementDateTime()
											: "");
							merchantPojo
									.setSettlementAccount(transactionResponse
											.getMerchantSettlementAccount() != null ? transactionResponse
											.getMerchantSettlementAccount()
											: "");
							merchantPojo
									.setSettlementBank(transactionResponse
											.getMerchantSettlementBank() != null ? transactionResponse
											.getMerchantSettlementBank() : "");
							merchantPojo
									.setMerchantRRN(transactionResponse
											.getMerchantRRN() != null ? transactionResponse
											.getMerchantRRN() : "");
							if(Util.isEmptyString(transactionResponse.getMerchantTransactionAmount()) == false)
							{
								try 
								{
									merchantPojo.setTransactionAmount(""+(Double.parseDouble(transactionResponse.getMerchantTransactionAmount())/100));
								} catch (Exception e) 
								{
									
									merchantPojo.setTransactionAmount("");
								}
							}
							else
							{
								merchantPojo.setTransactionAmount("");
							}
							merchantPojo
									.setCommission(transactionResponse
											.getMerchantCommission() != null ? transactionResponse
											.getMerchantCommission() : "");
							merchantPojo
									.setServiceTax(transactionResponse
											.getMerchantServiceTax() != null ? transactionResponse
											.getMerchantServiceTax() : "");
							

							/******************* transactionInfo *******************************/
							transcationInquiryPojo
									.setTransactionRecipient(transactionResponse
											.getTransactionRecipient() != null ? transactionResponse
											.getTransactionRecipient() : "");
							// getTransactionDateList(transactionResponse.getTransactionDateTime(),transactionResponse
							// .getTransactionType().getValue());
							
							//
//							transcationInquiryPojo
//									.setTransactionType(transactionResponse
//											.getTransactionType().getValue() != null ? transactionResponse
//											.getTransactionType().getValue()
//											: "");
							
							
								//TransactionTypeDetails lTypeDetails = TransactionTypeDetails.getTransactionTypeDetails(transactionResponse.getTransactionType().getValue()+"|"+transactionResponse.getTransactionClass());
								
							
							transcationInquiryPojo
									.setTransactionId(transactionResponse
											.getTransactionId().getValue() != null ? transactionResponse
											.getTransactionId().getValue() : "");
							transcationInquiryPojo
									.setTransactionChannel(transactionResponse
											.getTransactionChannel() != null ? transactionResponse
											.getTransactionChannel() : "");
							
//							transcationInquiryPojo
//									.setTransactionAmount(transactionResponse
//											.getTransactionAmount().getValue() != null ? transactionResponse
//											.getTransactionAmount().getValue()
//											: "");
							
							transcationInquiryPojo
									.setTransactionDisposition(transactionResponse
											.getTransactionDisposition()
											.getValue() != null ? transactionResponse
											.getTransactionDisposition()
											.getValue() : "");
//							transcationInquiryPojo
//									.setTransactionStatus(transactionResponse
//											.getTransactionStatus().getValue() != null ? transactionResponse
//											.getTransactionStatus().getValue()
//											: "");
							
							String lstrStatus = "";
							if(transactionResponse.getTransactionStatus()!=null)
								lstrStatus = transactionResponse.getTransactionStatus().getValue();
							transcationInquiryPojo.setTransactionStatus("Failed");
							if(Util.isEmptyString(lstrStatus) == false && (lstrStatus.equals("400") || lstrStatus.equals("000") ))
							{
								transcationInquiryPojo.setTransactionStatus("Success");
							}
							
							transcationInquiryPojo
									.setTransactionNote(transactionResponse
											.getTransactionNote() != null ? transactionResponse
											.getTransactionNote() : "");

							// new field added getRetrievalReferenceNumber
							// 6-5-2015
							transcationInquiryPojo
									.setRetrievalReferenceNumber(transactionResponse
											.getRetrievalReferenceNumber() != null ? transactionResponse
											.getRetrievalReferenceNumber() : "");
							
						}
						returnList.add(transcationInquiryPojo);
					}
				}

					TResponse lResponse =  response.getResponseDetails();
					if(lResponse != null)
					{
						TInfos lInfos = lResponse.getInfos();
						if(lValues!= null && lInfos!= null && lInfos.getInfo() != null && lInfos.getInfo().isEmpty() == false)
						{
							List<TInfo> llistTInfo = lInfos.getInfo();
							for (TInfo tInfo : llistTInfo) 
							{
								if(Util.isEmptyString(tInfo.getCode()) == false && tInfo.getCode().equalsIgnoreCase("RPSL_NAVI_BUS_RETURNED_ALL_001") == false && lRecordSets != null && lRecordSets.getPageOffset()!=null)
								{
									lValues.setOutputPageIndex(lRecordSets.getPageOffset().getValue());
									
								}
								else
								{
									lValues.setOutputPageIndex("NA");
								}
							}
						}
					}
				}
			}
			
			return returnList;
		} catch (GetTransactionDataFault e) 
		{
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", e);
			logger.error("Exception in ["+lClassName+"."+lMethodName+"]", e);
			String exception = "";
			if (e.getFaultInfo() != null
					&& e.getFaultInfo().getErrors() != null
					&& e.getFaultInfo().getErrors().getError() != null
					&& e.getFaultInfo().getErrors().getError().size() > 0
					&& e.getFaultInfo().getErrors().getError().get(0)
							.getErrorMsg() != null) {
				exception = e.getFaultInfo().getErrors().getError().get(0)
						.getErrorMsg();
				throw new DataAccessException(exception, e);
			} else {
				throw new DataAccessException(e.getMessage(), e);
			}
		} catch (Throwable t) 
		{
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			logger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			throw new DataAccessException(t.getMessage(), t);
		}
		finally
		{
			soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentCode+"|["+lClassName +"."+lMethodName+"] | End");
			
		}

	}

	
	@Override
	public List<TranscationInquiry> getTransactionSummary(String startDateTime,
			String endDateTime, String merchantIdoperator,
			String terminalIdoperator, String agentId,long requestId)
			throws DataAccessException {
		try {
			
			/******************* set the Request from GetTransactionData ******************/
			//1-9-2015 [AR] [CH] Change in wsdl
			//TransactionInquiryServiceAgent agent = new TransactionInquiryServiceAgent(wsdlUrl);
			TransactionInquiry inquiry = new TransactionInquiry();

			Operations operation = inquiry.getOperationsEndpoint();
			GetTransactionSummaryRq request = new GetTransactionSummaryRq();
			TransactionSummaryCriteria criteria = new TransactionSummaryCriteria();
			TTransactionSummaryBasicFilter basicFilter = new TTransactionSummaryBasicFilter();
			criteria.setTransactionSummaryBasicCriteria(basicFilter);

			basicFilter.setStartDateTime(startDateTime);
			basicFilter.setEndDateTime(endDateTime);

			TPredicate predicate = new TPredicate();

			predicate.setOperator(merchantIdoperator);
			TPredicate terminal = new TPredicate();
			terminal.setOperator(terminalIdoperator);
			request.setTransactionSummaryCriteria(criteria);
		
			
			/******************* Setting the WSDL URL ******************/
			Properties properties = PropertiesUtil
					.getProperties(PropertiesUtil.platform);

			String transactionInquiryUrl = properties
					.getProperty("transactionInquiry");
			URL transactionInquiryWsdlUrl = new URL(transactionInquiryUrl);
			//1-9-2015 [AR] [CH] Change in wsdl
			//TransactionInquiryServiceAgent agent = new TransactionInquiryServiceAgent(wsdlUrl);
			TransactionInquiry agent = new TransactionInquiry(
					transactionInquiryWsdlUrl);
			agent.setHandlerResolver(new WSHandlerResolver(agentId,-1000			));
			
			Operations opeartions = agent.getOperationsEndpoint();

			/******************* Setting the Hear Request ******************/
			request.setRqHeader(RequestUtils.getRequestHeader());
//			TRqHeader lHeader = new TRqHeader();
//			lHeader.setVersion("1.0");
//			TBusinessApplicationContext businessApplicationRequest = new TBusinessApplicationContext();
//			businessApplicationRequest.setSrcApplicationname("PORTAL_NOVELVOX_NA_001");
//			businessApplicationRequest.setServiceVersion("1.0");
//			businessApplicationRequest.setOperationVersion("1.0");
//			businessApplicationRequest.setOperationName("getTransactionSummary");
//			businessApplicationRequest.setMessageType("REQ");
//			lHeader.setBusinessApplicationContext(businessApplicationRequest);
//			TMessageContext lMsgContext =new TMessageContext();
//			lMsgContext.setMessageID(String.valueOf(UUID.randomUUID()));
//			lMsgContext.setCorrelationID(String.valueOf(UUID.randomUUID()));
//			lMsgContext.setTimeStamp(new Date().toString());
//			lMsgContext.setEsbID(String.valueOf(UUID.randomUUID()));
//			lMsgContext.setBusinessID("123");
//			lHeader.setMessageContext(lMsgContext);
//			request.setRqHeader(lHeader);

			/******************* Setting the endpoint URL For Dealer Inquiry ******************/
			String endPointURL = properties
					.getProperty("TransactionInquiryEndPoint");
			BindingProvider bindingProvider = (BindingProvider) opeartions;
			bindingProvider.getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointURL);
			/************************ Response ************************************************/
			GetTransactionSummaryRs response = operation
					.getTransactionSummary(request);

			if (response != null
					&& response.getResponseDetails() != null
					&& response.getResponseDetails().getStatus() != null
					&& response.getResponseDetails().getStatus()
							.equalsIgnoreCase("FAILED")) {
				if (response.getResponseDetails().getErrors() != null
						&& response.getResponseDetails().getErrors().getError() != null
						&& response.getResponseDetails().getErrors().getError()
								.size() > 0
						&& response.getResponseDetails().getErrors().getError()
								.get(0).getErrorMsg() != null) {
					
					throw new DataAccessException(response.getResponseDetails()
							.getErrors().getError().get(0).getErrorMsg());
				}
			}
			List<TranscationInquiry> getTransactionSummaryList = null;
			if (response != null) {
				if (response.getTransactionSummaryList() != null) {
					if (response.getTransactionSummaryList()
							.getTransactionSummaryRsElements() != null
							&& response.getTransactionSummaryList()
									.getTransactionSummaryRsElements().size() > 0) {
						getTransactionSummaryList = new ArrayList<TranscationInquiry>();
						TranscationInquiry traninqpojo = new TranscationInquiry();

						for (TTransactionSummaryRs transactionSummaryResponse : response
								.getTransactionSummaryList()
								.getTransactionSummaryRsElements()) {
							Transaction transactionPojo = new Transaction();

							/******************* transactionInfo *******************************/
							transactionPojo
									.setTransactionDate(transactionSummaryResponse
											.getTransactionDate() != null ? transactionSummaryResponse
											.getTransactionDate() : "");
							transactionPojo
									.setTransactionCount(transactionSummaryResponse
											.getTransactionCount() != null ? transactionSummaryResponse
											.getTransactionCount() : "");
							transactionPojo
									.setTransactionAmount(transactionSummaryResponse
											.getTransactionAmount() != null ? transactionSummaryResponse
											.getTransactionAmount() : "");
							traninqpojo.setTransaction(transactionPojo);
							/******************* MerchantInfo *******************************/
							Merchant merchantPojo = new Merchant();
							merchantPojo
									.setId(transactionSummaryResponse
											.getMerchantId() != null ? transactionSummaryResponse
											.getMerchantId() : "");
							traninqpojo.setMerchant(merchantPojo);

							traninqpojo
									.setTerminalId(transactionSummaryResponse
											.getTerminalId() != null ? transactionSummaryResponse
											.getTerminalId() : "");
						}
						if (response.getResponseDetails() != null) {
							ti.com.ril.rpsl.entities.commontypes_v1_0.TResponse tResponse = response
									.getResponseDetails();
							/******************* transactionInfo *******************************/
							for (TInfo transactionInfo : tResponse.getInfos()
									.getInfo()) {

								Status statusPojo = new Status();
								statusPojo
										.setCode(transactionInfo.getCode() != null ? transactionInfo
												.getCode() : "");
								statusPojo
										.setMessage(transactionInfo.getMsg() != null ? transactionInfo
												.getMsg() : "");
								statusPojo
										.setProviderName(transactionInfo
												.getProvider() != null ? transactionInfo
												.getProvider()
												.getProviderName() != null ? transactionInfo
												.getProvider()
												.getProviderName() : ""
												: "");
								statusPojo
										.setProviderCode(transactionInfo
												.getProvider() != null ? transactionInfo
												.getProvider()
												.getProviderCode() != null ? transactionInfo
												.getProvider()
												.getProviderCode() : ""
												: "");
								traninqpojo.setStatus(statusPojo);

							}

						} else {
							Status providerStatus = new Status();
							providerStatus.setCode("");
							providerStatus.setMessage("");
							providerStatus.setProviderName("");
							providerStatus.setProviderCode("");
							traninqpojo.setStatus(providerStatus);
						}

						if (response.getRsHeader() != null) {
							/****************************** MessageContext ********************/
							if (response.getRsHeader().getMessageContext() != null) {
								traninqpojo.setHeaderMessage(HeaderStatus
										.setMessage(response.getRsHeader()
												.getMessageContext()
												.getBusinessID(),
												response.getRsHeader()
														.getMessageContext()
														.getEsbID(), response
														.getRsHeader()
														.getMessageContext()
														.getMessageID(),
												response.getRsHeader()
														.getMessageContext()
														.getCorrelationID(),
												response.getRsHeader()
														.getMessageContext()
														.getExtCorrelationID(),
												response.getRsHeader()
														.getMessageContext()
														.getTimeStamp()));
							}

							/********************** BusinessApplicationContext ********************/
							if (response.getRsHeader()
									.getBusinessApplicationContext() != null) {
								traninqpojo
										.setBusinessApplication(HeaderStatus
												.setBusinessApplication(
														response.getRsHeader()
																.getBusinessApplicationContext()
																.getServiceVersion(),
														response.getRsHeader()
																.getBusinessApplicationContext()
																.getOperationVersion(),
														response.getRsHeader()
																.getBusinessApplicationContext()
																.getOperationName(),
														response.getRsHeader()
																.getBusinessApplicationContext()
																.getMessageType(),
														response.getRsHeader()
																.getBusinessApplicationContext()
																.getSrcApplicationname()));
							}
						}
						getTransactionSummaryList.add(traninqpojo);
					}
				}
			}
			
			return getTransactionSummaryList;
		} catch (GetTransactionDataFault e) {
			String exception = "";
			if (e.getFaultInfo() != null
					&& e.getFaultInfo().getErrors() != null
					&& e.getFaultInfo().getErrors().getError() != null
					&& e.getFaultInfo().getErrors().getError().size() > 0
					&& e.getFaultInfo().getErrors().getError().get(0)
							.getErrorMsg() != null) {
				exception = e.getFaultInfo().getErrors().getError().get(0)
						.getErrorMsg();
				throw new DataAccessException(exception, e);
			} else {
				throw new DataAccessException(e.getMessage(), e);
			}
		} catch (Throwable t) {
			throw new DataAccessException(t.getMessage(), t);
		}
	}
}
