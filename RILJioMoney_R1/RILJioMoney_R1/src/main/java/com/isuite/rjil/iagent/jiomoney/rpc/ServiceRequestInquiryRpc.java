package com.isuite.rjil.iagent.jiomoney.rpc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.common.CustomerProblem;
import com.isuite.rjil.iagent.jiomoney.common.ReferenceData;
import com.isuite.rjil.iagent.jiomoney.common.ServiceResponse;
import com.isuite.rjil.iagent.jiomoney.exception.ServiceException;
import com.isuite.rjil.iagent.jiomoney.services.CustomerOnboardingService;
import com.isuite.rjil.iagent.jiomoney.services.ServiceLocator;
import com.isuite.rjil.iagent.jiomoney.services.ServiceRequestInquiryService;
import com.isuite.rjil.iagent.jiomoney.services.ServiceRequestManagementService;
import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;
import com.isuite.rjil.iagent.jiomoney.util.Util;
import com.isuite.rjil.iagent.jiomoney.util.XMLGregorianCalendarUtil;

import flex.messaging.io.amf.ASObject;

/**
 * 
 * @author NovelVox
 * 
 */
public class ServiceRequestInquiryRpc 
{
	private static final Logger soapLogger = Logger.getLogger(LOVUtil.SOAPLOGGER);
	private static final String INQUIRY_SERVICE = "ServiceRequestInquiryService";
	private static final String MANAGEMENT_SERVICE = "ServiceRequestManagementService";

	/**
	 * s
	 * 
	 * @param problemRefNo
	 * @param custType
	 * @return
	 */
	public ServiceResponse viewServiceRequest(String problemRefNo,
			String custType, String agentId) {
		String lClassName =  this.getClass().getName();
		String lMethodName = "viewServiceRequest";
		soapLogger.info("************************");
		long requestId = Util.getRequestId();
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		ServiceResponse response = null;

		try {
			CustomerProblem viewServiceRequest = ((ServiceRequestInquiryService) ServiceLocator
					.getService(INQUIRY_SERVICE)).viewServiceRequest(problemRefNo,
					custType, agentId,requestId);

			if (viewServiceRequest != null) {
				response = new ServiceResponse(viewServiceRequest);
			} else {
				response = new ServiceResponse(1,
						"No record Found in Service Request Inquiry");
			}
		}  catch (Throwable t) {
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			response = new ServiceResponse(1, t.getMessage());
		}
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
		soapLogger.info("************************");
		return response;
	}

	/**
	 * 
	 * @param custId
	 * @param status
	 * @param fromDate
	 * @param toDate
	 * @param pageSize
	 * @param offSet
	 * @return
	 */
	public ServiceResponse searchServiceRequest(String entityType,
			String entityId, String urgency, String impact, String isSummaryRq,
			String owner, String serviceTeam, String status, String fromDate,
			String toDate, String pageSize, String offSet, String agentId) {
		ServiceResponse response = null;
		String lClassName =  this.getClass().getName();
		String lMethodName = "searchServiceRequest";
		long requestId = Util.getRequestId();
		soapLogger.info("************************");
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");

		try {
			if (fromDate != null && !fromDate.isEmpty() && toDate != null
					&& !toDate.isEmpty()) {
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
				try {
					Date from = format.parse(XMLGregorianCalendarUtil
							.convertDateFormat(fromDate, "dd/MM/yyyy",
									"yyyyMMdd"));
					Date to = format
							.parse(XMLGregorianCalendarUtil.convertDateFormat(
									toDate, "dd/MM/yyyy", "yyyyMMdd"));
					if (to.before(from)) {
						return new ServiceResponse(1,
								"To Date is earlier than From Date");
					} else if (from.after(to)) {
						return new ServiceResponse(1,"From Date is after To Date");
					}
				} catch (Throwable e) {
					soapLogger.error(
							"Exception catched while parsing date in Find SR",
							e);
					return new ServiceResponse(1, e.getMessage());

				}

			}

			List<CustomerProblem> searchServiceRequest = ((ServiceRequestInquiryService) ServiceLocator
					.getService(INQUIRY_SERVICE)).searchServiceRequest(entityType,
					entityId, urgency, impact, isSummaryRq, owner, serviceTeam,
					status, fromDate, toDate, pageSize, offSet, agentId,requestId);

			if (searchServiceRequest != null && searchServiceRequest.size() > 0) {
				response = new ServiceResponse(searchServiceRequest);
			} else {
				response = new ServiceResponse(1,
						"No record Found in Service Request Inquiry");
			}
		} catch (Throwable t) {
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			response = new ServiceResponse(1, t.getMessage());
		}
		finally
		{
			soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
			soapLogger.info("************************");
		}
		return response;
	}

	public ServiceResponse updateServiceRequest(String custId,
			String problemRefNo, String status, String impact, String urgency,
			String channel, String agentCode, String note, String mobileNumber,
			String email, String owner, String teamMember) {
		ServiceResponse response = null;
		String lClassName =  this.getClass().getName();
		String lMethodName = "updateServiceRequest";
		soapLogger.info("************************");
		long requestId = Util.getRequestId();
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentCode+"|["+lClassName +"."+lMethodName+" ]| Start");

		try {

			CustomerProblem updateServiceRequest = ((ServiceRequestManagementService) ServiceLocator
					.getService(MANAGEMENT_SERVICE)).updateServiceRequest(custId,
					problemRefNo, status, impact, urgency, channel, agentCode,
					note, mobileNumber, email, owner, teamMember,requestId);

			if (updateServiceRequest != null) 
			{
				
					response = new ServiceResponse(updateServiceRequest);
			} else {
				response = new ServiceResponse(1,
						"No record Found in Service Request Management");
			}
		} catch (ServiceException e) {
			soapLogger.error(
					"No record Found in Service Request Inquiry ---> updateServiceRequest",
					e);
			response = new ServiceResponse(1, e.getMessage());

		} catch (Throwable t) {
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			response = new ServiceResponse(1, t.getMessage());
		}
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentCode+"|["+lClassName +"."+lMethodName+"] | End");
		soapLogger.info("************************");
		return response;
	}

	/**
	 * @param custId
	 * @param problemRefNo
	 * @param status
	 * @return
	 */
	public ServiceResponse closeServiceRequestForLCMOutBound(String custId,String problemRefNo, String status, String userComments,String agentCode) 
	{
		ServiceResponse response = null;
		String lClassName =  this.getClass().getName();
		String lMethodName = "closeServiceRequestForLCMOutBound";
		long requestId = Util.getRequestId();
		soapLogger.info("************************");
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentCode+"|["+lClassName +"."+lMethodName+" ]| Start");

		try {

			CustomerProblem updateServiceRequest = ((ServiceRequestManagementService) ServiceLocator
					.getService(MANAGEMENT_SERVICE)).updateServiceRequest(custId,
					problemRefNo, status, "", "", "", agentCode,
					userComments,"" , "", "", "",requestId);

			if (updateServiceRequest != null) 
			{
				response = new ServiceResponse("Service Request with Id : "+problemRefNo+" has been closed sucessfuly.");
					
			} else {
				response = new ServiceResponse(1,
						"No record Found for given Service Id");
			}
		} catch (Throwable t) {
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			response = new ServiceResponse(1, t.getMessage());
		}
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentCode+"|["+lClassName +"."+lMethodName+"] | End");
		soapLogger.info("************************");
		return response;
	}

	public ServiceResponse createServiceRequest(String entityId,
			String category, String subCategory, String subSubCategory,String subSubSubCategory, 
			String description, String problemRefNo, String impact,
			String urgency, String channel, String agentCode, String note,
			String mobileNo, String emailId, String entityType, String status,
			String communicationMode, String notificationId,
			String communicationType, String notificationName,
			String notificationValue) 
	{
		String lClassName =  this.getClass().getName();
		String lMethodName = "createServiceRequest";
		soapLogger.info("************************");
		long requestId = Util.getRequestId();
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentCode+"|["+lClassName +"."+lMethodName+" ]| Start");
		List<ReferenceData> lImpactValues = LOVUtil.getLov(LOVUtil.IMPACT);
		for (ReferenceData lImpact : lImpactValues) 
		{
			if(lImpact.getDescription().equalsIgnoreCase(impact))
			{
				impact = lImpact.getCode();
				break;
			}
		}
		List<ReferenceData> lUrgencyValues = LOVUtil.getLov(LOVUtil.URGENCY);
		for (ReferenceData lUrgency : lUrgencyValues) 
		{
			if(lUrgency.getDescription().equalsIgnoreCase(urgency))
			{
				urgency = lUrgency.getCode();
				break;
			}
		}
		if("true".equalsIgnoreCase(notificationValue) == true && Util.isEmptyString(channel))
		{
			return new ServiceResponse(1, "Please Select Channel");
		}
//		if("true".equalsIgnoreCase(notificationValue) == true && Util.isEmptyString(communicationMode))
//		{
//			return new ServiceResponse(1, "Please Select Communication Mode");
//		}
		
		ServiceResponse response = null;
		try {
			CustomerProblem createServiceRequest = ((ServiceRequestManagementService) ServiceLocator
					.getService(MANAGEMENT_SERVICE)).createServiceRequest(entityId,
					category, subCategory, subSubCategory, subSubSubCategory, description,
					problemRefNo, impact, urgency, channel, agentCode, note,
					mobileNo, emailId, entityType, status, communicationMode,requestId);

			if (createServiceRequest != null) {
				// Notification submitNotification = ((NotificationMgmtService)
				// ServiceLocator
				// .getService(service)).submitNotification(
				// notificationId, channel, communicationType, mobileNo,
				// notificationName, notificationValue);
				//
				// if (submitNotification != null) {
				// logger.info("SR Ref No  "
				// + createServiceRequest.getProblemRefNo());
				// response = new ServiceResponse(
				// "SR created Successfully with Ref No "
				// + createServiceRequest.getProblemRefNo()
				// + " & Notification Status  "
				// + submitNotification.getStatus());
				// } else {
				
				if(createServiceRequest.getIsSRExist()!=null && createServiceRequest.getIsSRExist().equals("E")){
					response = new ServiceResponse(1,
							createServiceRequest.getSRExistReturnMsg());
				}else{
				response = new ServiceResponse(
						"SR created Successfully with Ref No "
								+ createServiceRequest.getProblemRefNo());
				}
				// }

			} else {
				response = new ServiceResponse(1,
						"No record Found in Service Request Inquiry");
			}
		}catch (Throwable t) {
			soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentCode+"|["+lClassName +"."+lMethodName+"] | End");
			soapLogger.info("************************");
			response = new ServiceResponse(1,"Error : "+t.getMessage());
		}
		return response;
	}

	public ServiceResponse getServiceCategory1(String entityType, String agentId) {
		ServiceResponse response = null;
		String lClassName =  this.getClass().getName();
		String lMethodName = "getServiceCategory1";
		soapLogger.info("************************");
		long requestId = Util.getRequestId();
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		try 
		{
			List<ReferenceData> getservicecategory1 = null;

			if (LOVUtil.getLov(LOVUtil.INTERACTION_CATEGORY) != null) {
				getservicecategory1 = LOVUtil
						.getLov(LOVUtil.INTERACTION_CATEGORY);
			} else {
				getservicecategory1 = ((ServiceRequestInquiryService) ServiceLocator
						.getService(INQUIRY_SERVICE)).getServiceCategory1(entityType,
						agentId,requestId);
				LOVUtil.setLov(LOVUtil.INTERACTION_CATEGORY,
						getservicecategory1);

			}

			if (getservicecategory1 != null) {
				response = new ServiceResponse(getservicecategory1);
			} else {
				response = new ServiceResponse(1,
						"No record Found in Service Request Inquiry");
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

	public ServiceResponse getServiceCategory2(String entityType,String categoryId1,String pSelectedCategory2,String pManualChange, String agentId) {
		ServiceResponse response = null;
		String lClassName =  this.getClass().getName();
		String lMethodName = "getServiceCategory2";
		soapLogger.info("************************");
		long requestId = Util.getRequestId();
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		try 
		{
			List<ReferenceData> getservicecategory2 = null;
			if (LOVUtil.getLov(categoryId1) != null) {
				getservicecategory2 = LOVUtil.getLov(categoryId1);
			} else {
				getservicecategory2 = ((ServiceRequestInquiryService) ServiceLocator
						.getService(INQUIRY_SERVICE)).getServiceCategory2(entityType,
						categoryId1, agentId,requestId);
				LOVUtil.setLov(categoryId1, getservicecategory2);
			}

			response = new ServiceResponse(getservicecategory2);
			if (getservicecategory2 != null) 
			{
				ReferenceData lReferenceData = null;
				if(Util.isEmptyString(pSelectedCategory2) == false  && "false".equalsIgnoreCase(pManualChange))
				{
					List<ReferenceData> lCategory2 = new ArrayList<ReferenceData>();
					lCategory2.addAll(getservicecategory2);
					for (ReferenceData lData : lCategory2) 
					{
						if(lData.getCode().equalsIgnoreCase(pSelectedCategory2))
						{
							lReferenceData = lData;
							break;
						}
					}
					if(lReferenceData != null)
					{
						lCategory2.remove(lReferenceData);
						lCategory2.add(0, lReferenceData);
						response.setSecodaryData("true");
					}
					response.setData(lCategory2);
				}
			} 
			else 
			{
				response = new ServiceResponse(1,
						"No record Found in Service Request Inquiry");
			}
		} catch (Throwable t) {
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			response = new ServiceResponse(1, t.getMessage());
		}
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| END");
		soapLogger.info("************************");
		return response;

	}

	public ServiceResponse getServiceCategory3(String categoryId1,String categoryId2, String entityType,String pSelectedCategory3,String pManualChange, String agentId) {
		ServiceResponse response = null;
		String lClassName =  this.getClass().getName();
		String lMethodName = "getServiceCategory3";
		soapLogger.info("************************");
		long requestId = Util.getRequestId();
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");



		try {
			List<ReferenceData> getservicecategory3 = null;
			if (LOVUtil.getLov(categoryId2) != null) {
				getservicecategory3 = LOVUtil.getLov(categoryId2);
			} else {
				getservicecategory3 = ((ServiceRequestInquiryService) ServiceLocator
						.getService(INQUIRY_SERVICE)).getServiceCategory3(categoryId1,
						categoryId2, entityType, agentId,requestId);
				LOVUtil.setLov(categoryId2, getservicecategory3);
			}
			response = new ServiceResponse(getservicecategory3);
			if (getservicecategory3 != null) 
			{

				if(Util.isEmptyString(pSelectedCategory3) == false && "false".equalsIgnoreCase(pManualChange))
				{
					ReferenceData lReferenceData = null;
					List<ReferenceData> lCategory3 = new ArrayList<ReferenceData>();
					lCategory3.addAll(getservicecategory3);
					for (ReferenceData lData : lCategory3) 
					{
						if(lData.getCode().equalsIgnoreCase(pSelectedCategory3))
						{
							lReferenceData = lData;
							break;
						}
					}
					if(lReferenceData != null)
					{
						lCategory3.remove(lReferenceData);
						lCategory3.add(0, lReferenceData);
						response.setSecodaryData("true");
					}
					response.setData(lCategory3);
				}
			} else {
				response = new ServiceResponse(1,
						"No record Found in Service Request Inquiry");
			}
		} catch (ServiceException e) {
			soapLogger.error(
					"No record Found in Service Request Inquiry ---> getservicecategory3",
					e);
			response = new ServiceResponse(1, e.getMessage());

		} catch (Throwable t) {
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			response = new ServiceResponse(1, t.getMessage());
		}
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
		soapLogger.info("************************");
		return response;

	}
	
	public ServiceResponse searchServiceRequestForFilter(String pEntityId,String pEntityType, ASObject pStatus, String pFromDate, String pToDate, ASObject pUrgency, ASObject pImpact, String pIsSummaryRq, String pOwner,String pServicTeam, String pPageSize, String pOffSet, String pAgentId) 
	{
		String lClassName =  this.getClass().getName();
		String lMethodName = "searchServiceRequestForFilter";
		soapLogger.info("************************");
		long requestId = Util.getRequestId();
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+pAgentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		if(Util.isEmptyString(pFromDate) == true && Util.isEmptyString(pToDate) == true && pStatus == null && pUrgency == null && pImpact == null)
		{
			return new ServiceResponse(1, "Please Select Atleast One Filter");
		}
		if ((Util.isEmptyString(pFromDate) == true && Util.isEmptyString(pToDate) == false) || (Util.isEmptyString(pFromDate) == false && Util.isEmptyString(pToDate) == true))
		{
			return new ServiceResponse(1, "Please Provide From and To Date");
		}
		
		try  
		{ 
			if(Util.isEmptyString(pFromDate) == false)
			{
				SimpleDateFormat lInputFormatter = new SimpleDateFormat("dd/MM/yyyy");
				
				Date lFromDate = lInputFormatter.parse(pFromDate);
				
				Date lToDate = lInputFormatter.parse(pToDate);
				if(lFromDate.after(lToDate))
				{
					return new ServiceResponse(1, "From Date cannot be after To Date");
				}
			}
		} 
		catch (ParseException e) 
		{
			soapLogger.error("Exception catched while parsing date in Find SR",e);
			soapLogger.info("Request ID : "+requestId+" |Agent ID : "+pAgentId+"|["+lClassName +"."+lMethodName+"] | End");
			soapLogger.info("************************");
			return new ServiceResponse(1, e.getMessage());
		}
		String lstrStatus = "";
		if(pStatus != null)
		{
			lstrStatus = Util.getStringRepresentation((String)pStatus.get("code"));
		}
		String lstrUrgency = "";
		if(pUrgency != null)
		{
			lstrUrgency = Util.getStringRepresentation((String)pUrgency.get("code"));
		}
		
		String lstrImpact = "";
		if(pImpact != null)
		{
			lstrImpact = Util.getStringRepresentation((String)pImpact.get("code"));
		}

		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+pAgentId+"|["+lClassName +"."+lMethodName+"] | End");
		soapLogger.info("************************");
		return searchServiceRequest(pEntityType,pEntityId,lstrUrgency, lstrImpact, pIsSummaryRq,pOwner,  pServicTeam,  lstrStatus,  pFromDate, pToDate,  pPageSize,  pOffSet,  pAgentId);
								 
	}
	public ServiceResponse getServiceCategory4(String categoryId1,String categoryId2,String categoryId3, String entityType,String pSelectedCategory4,String pManualChange, String agentId) {
		ServiceResponse response = null;
		String lClassName =  this.getClass().getName();
		String lMethodName = "getServiceCategory4";
		soapLogger.info("************************");
		long requestId = Util.getRequestId();
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");

		try 
		{
			List<ReferenceData> getservicecategory4 = null;
			if (LOVUtil.getLov(categoryId3) != null) {
				getservicecategory4 = LOVUtil.getLov(categoryId3);
			} else {
				getservicecategory4 = ((ServiceRequestInquiryService) ServiceLocator
						.getService(INQUIRY_SERVICE)).getServiceCategory4(categoryId1,
						categoryId2,categoryId3, entityType, agentId,requestId);
				LOVUtil.setLov(categoryId3, getservicecategory4);
			}
			response = new ServiceResponse(getservicecategory4);
			if (getservicecategory4 != null) 
			{

				if(Util.isEmptyString(pSelectedCategory4) == false && "false".equalsIgnoreCase(pManualChange))
				{
					ReferenceData lReferenceData = null;
					List<ReferenceData> lCategory3 = new ArrayList<ReferenceData>();
					lCategory3.addAll(getservicecategory4);
					for (ReferenceData lData : lCategory3) 
					{
						if(lData.getCode().equalsIgnoreCase(pSelectedCategory4))
						{
							lReferenceData = lData;
							break;
						}
					}
					if(lReferenceData != null)
					{
						lCategory3.remove(lReferenceData);
						lCategory3.add(0, lReferenceData);
						response.setSecodaryData("true");
					}
					response.setData(lCategory3);
				}
			} else {
				response = new ServiceResponse(1,
						"No record Found in Service Request Inquiry");
			}
		} catch (Throwable t) {
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			response = new ServiceResponse(1, t.getMessage());
		}
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
		soapLogger.info("************************");
		return response;

	}
	public ServiceResponse getStateList(String agentId) 
	{
		ServiceResponse response = null;
		String lClassName =  this.getClass().getName();
		String lMethodName = "getStateList";
		soapLogger.info("************************");
		long requestId = Util.getRequestId();
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");

		try 
		{
			List<ReferenceData> stateList = LOVUtil.getLov("IN");
			if (stateList != null && !stateList.isEmpty()  ) 
			{
				stateList = LOVUtil.getLov("IN");
			} else 
			{
				stateList = ((ServiceRequestInquiryService) ServiceLocator.getService(INQUIRY_SERVICE)).getStatesOrDistrictList(null, agentId, requestId);
				LOVUtil.setLov("IN", stateList);
			}
			if (stateList != null) 
			{
				response = new ServiceResponse(stateList);
			} else 
			{
				response = new ServiceResponse(1,
						"Error while getting list of States");
			}
		} catch (Throwable t) {
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			response = new ServiceResponse(1, t.getMessage());
		}
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
		soapLogger.info("************************");
		return response;
	}
	
	public ServiceResponse getDistrictList(String pState,String agentId) 
	{
		ServiceResponse response = null;
		String lClassName =  this.getClass().getName();
		String lMethodName = "getServiceCategory4";
		soapLogger.info("************************");
		long requestId = Util.getRequestId();
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");

		try 
		{
			List<ReferenceData> stateList = LOVUtil.getLov(pState);
			if (stateList != null && !stateList.isEmpty()  ) 
			{
				stateList = LOVUtil.getLov(pState);
			} else 
			{
				stateList = ((ServiceRequestInquiryService) ServiceLocator.getService(INQUIRY_SERVICE)).getStatesOrDistrictList(pState, agentId, requestId);
				LOVUtil.setLov(pState, stateList);
			}
			if (stateList != null) 
			{
				response = new ServiceResponse(stateList);
			} else 
			{
				response = new ServiceResponse(1,
						"Error while getting list of Districts");
			}
		} catch (Throwable t) {
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			response = new ServiceResponse(1, t.getMessage());
		}
		soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
		soapLogger.info("************************");
		return response;
	}
	
	public ServiceResponse validateServiceRequest(String entityId,
			String category, String subCategory, String subSubCategory,String subSubSubCategory, 
			String description, String problemRefNo, String impact,
			String urgency, String channel, String agentCode, String note,
			String mobileNo, String emailId, String entityType, String status,
			String communicationMode, String notificationId,
			String communicationType, String notificationName,
			String notificationValue) {
		ServiceResponse response = null;
		String lClassName =  this.getClass().getName();
		String lMethodName = "validateServiceRequest";
		long requestId = Util.getRequestId();
		
		try
		{
			String SRExistsFlag = ((ServiceRequestInquiryService) ServiceLocator.getService(INQUIRY_SERVICE)).validateServiceRequest(entityId,subSubSubCategory,entityType, agentCode,requestId);
			if(Util.isEmptyString(SRExistsFlag) == false &&  "E".equalsIgnoreCase(SRExistsFlag))
			{
				response = new ServiceResponse(2, "");
			}
			else
			{
				soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentCode+"|["+lClassName +"."+lMethodName+"] | SR Does Not Exist : Calling CreateSR");
				response = createServiceRequest( entityId,category,subCategory, subSubCategory,subSubSubCategory,
						description,problemRefNo,impact,urgency,channel,agentCode,  note,
						mobileNo,  emailId, entityType, status,
						 communicationMode,  notificationId,
						 communicationType,  notificationName,
						 notificationValue);
			}			
		}
		catch (Throwable t) 
		{
			soapLogger.info("Request ID : "+requestId+" |Agent ID : "+agentCode+"|["+lClassName +"."+lMethodName+"] | End");
			soapLogger.info("************************");
			response = new ServiceResponse(1,"Error : "+t.getMessage());
		}
		return response;
	}
}
