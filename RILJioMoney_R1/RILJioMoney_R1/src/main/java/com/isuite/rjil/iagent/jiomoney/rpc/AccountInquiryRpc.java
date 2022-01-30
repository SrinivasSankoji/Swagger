/**
 * 
 */
package com.isuite.rjil.iagent.jiomoney.rpc;

import java.util.List;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.common.Account;
import com.isuite.rjil.iagent.jiomoney.common.Bank;
import com.isuite.rjil.iagent.jiomoney.common.ServiceResponse;
import com.isuite.rjil.iagent.jiomoney.services.AccountInquiryService;
import com.isuite.rjil.iagent.jiomoney.services.ServiceLocator;
import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;
import com.isuite.rjil.iagent.jiomoney.util.Util;

/**
 * @author NovelVox
 * 
 */
public class AccountInquiryRpc {

	private static final Logger soapLogger = Logger.getLogger(LOVUtil.SOAPLOGGER);
	private static final String service = "AccountInquiryService";

	public ServiceResponse getAccountBalance(String accountId, String custId,
			String currency, String branchId, String institutionCode,String agentId) 
	{
		
		String lClassName =  this.getClass().getName();
		String lMethodName = "getAccountBalance";
		long requestId = Util.getRequestId();
		soapLogger.info("************************");
		soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");
		ServiceResponse response = null;
		try 
		{
			Account accountBalance = ((AccountInquiryService) ServiceLocator
					.getService(service)).getAccountBalance(accountId, custId,
					currency, branchId, institutionCode,agentId,requestId);

			if (accountBalance != null) {
				response = new ServiceResponse(accountBalance);
			} else {
				response = new ServiceResponse(1,
						"No record Found in Account Inquiry");
			}
		}
		catch (Throwable t) 
		{
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			response = new ServiceResponse(1, t.getMessage());
		}
		soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
		soapLogger.info("************************");
		return response;
	}

	public ServiceResponse varifierAccountBalanceForWalletClosure(
			String accountId, String custId, String currency, String branchId,
			String institutionCode,String agentId) {
		String lClassName =  this.getClass().getName();
		long requestId = Util.getRequestId();
		String lMethodName = "varifierAccountBalanceForWalletClosure";
		soapLogger.info("************************");
		soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");


		ServiceResponse response = null;
		try {
			Account accountBalance = ((AccountInquiryService) ServiceLocator
					.getService(service)).getAccountBalance(accountId, custId,
					currency, branchId, institutionCode,agentId,requestId); 

			if (accountBalance != null) 
			{
				try {
					if (Double.parseDouble(accountBalance.getAccountBalance()) > 0) 
					{
						response = new ServiceResponse("Since balance is "
								+ accountBalance.getAccountBalance()
								+ ", we cannot terminate the Wallet");
					} 
					else 
					{
						response = new ServiceResponse(1,"Successfully Wallet Close");
					}
				} 
				catch (NumberFormatException e) 
				{
					soapLogger.error("NumberFormatException :AccountInquiryRpc:  varifierAccountBalanceForWalletClosure"); 
				}
				
			} else {
				response = new ServiceResponse(2,
						"No record Found in Account Inquiry");
			}
		}catch (Throwable t) 
		{
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			response = new ServiceResponse(2, t.getMessage());
		}
		soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
		soapLogger.info("************************");
		return response;
	}

	public ServiceResponse validateLoadMoney(String custId, String accountNo,
			String mobileNo, String amount, String loadType, String loadSource,
			String currency, String branchId, String institutionCode,String agentId) {
		String lClassName =  this.getClass().getName();
		String lMethodName = "validateLoadMoney";
		long requestId = Util.getRequestId();
		soapLogger.info("************************");
		soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+" ]| Start");

		ServiceResponse response = null;
		try {
			List<Bank> validateLoadMoney = ((AccountInquiryService) ServiceLocator
					.getService(service)).validateLoadMoney(custId, accountNo,
					mobileNo, amount, loadType, loadSource, currency, branchId,
					institutionCode,agentId,requestId);

			if (validateLoadMoney != null && validateLoadMoney.isEmpty() == false) {
				response = new ServiceResponse(validateLoadMoney);
			} else {
				response = new ServiceResponse(1,
						"No record Found in Account Inquiry");
			}
		} 
		catch (Throwable t) 
		{
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			response = new ServiceResponse(1, t.getMessage());
		}
		soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
		soapLogger.info("************************");
		return response;
	}

	
	public ServiceResponse validateLoadMoneyForAggAmount(String custId, String accountNo,
			String mobileNo, String amount, String loadType, String loadSource,
			String currency, String branchId, String institutionCode,String agentId) 
	{
		String lClassName =  this.getClass().getName();
		String lMethodName = "validateLoadMoney";
		long requestId = Util.getRequestId();
		ServiceResponse response = null;
		try
		{
		
			response = validateLoadMoney( custId,  accountNo,
					 mobileNo,  amount,  loadType,  loadSource,
					 currency,  branchId,  institutionCode, agentId);
			if(response.getStatus() == 0 && response.getData() != null)
			{
				List<Bank> lBankLis = (List<Bank>)response.getData();
				Bank lBank = lBankLis.get(0);
				response.setData(lBank);
			}
		}catch (Throwable t) {
			soapLogger.error("Exception in ["+lClassName+"."+lMethodName+"]", t);
			response = new ServiceResponse(1, t.getMessage());
		}
		soapLogger.info("request Id : "+requestId+"|Agent ID : "+agentId+"|["+lClassName +"."+lMethodName+"] | End");
		soapLogger.info("************************");
		return response;
	}
}
