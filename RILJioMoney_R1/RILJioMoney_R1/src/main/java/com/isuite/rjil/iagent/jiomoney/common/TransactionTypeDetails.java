package com.isuite.rjil.iagent.jiomoney.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.util.PropertiesUtil;
import com.isuite.rjil.iagent.jiomoney.util.Util;

public class TransactionTypeDetails 
{
	private static final Logger LOGGER = Logger.getLogger(TransactionTypeDetails.class);
	private static Map<String,TransactionTypeDetails> mmapTransactiondetails = null;
	private String mstrTransactionType="";
	private String mstrTransactionDescShort="";
	private String mstrTransactionDesc="";
	private String mstrTransactionCategory="";
	
	private TransactionTypeDetails(){}
	
	public String getTransactionType() {
		return mstrTransactionType;
	}
	public void setTransactionType(String pTransactionType) {
		mstrTransactionType = pTransactionType;
	}
	public String getTransactionDescShort() {
		return mstrTransactionDescShort;
	}
	public void setTransactionDescShort(String pTransactionTypeDesc) {
		mstrTransactionDescShort = pTransactionTypeDesc;
	}
	public String getTransactionDesc() {
		return mstrTransactionDesc;
	}
	public void setTransactionDesc(String pTransactionDesc) {
		mstrTransactionDesc = pTransactionDesc;
	}
	public String getTransactionCategory() {
		return mstrTransactionCategory;
	}
	public void setTransactionCategory(String pTransactionCategory) {
		mstrTransactionCategory = pTransactionCategory;
	}
	
	public static TransactionTypeDetails getTransactionTypeDetails(String pTransactionType)
	{
		if(mmapTransactiondetails == null)
		{
			loadTransactionInfo();
		}
		return mmapTransactiondetails.get(pTransactionType);
	}
	private static void loadTransactionInfo()
	{
		try
		{
			//<TransactionType>|<TransactionDescShort>|<TransactionDesc>|<TransactionCategory>||<TransactionType>|<TransactionDescShort>|<TransactionDesc>|<TransactionCategory>||
			TransactionTypeDetails lDetails = null; 
			mmapTransactiondetails = new HashMap<String, TransactionTypeDetails>();
			Properties jioMoneyUtilProperty=PropertiesUtil.getProperties(PropertiesUtil.jiomoneyutil);
			String lstrTransactionTypeDesc =PropertiesUtil.getPropertyValue(jioMoneyUtilProperty,"ConsumerTransactionTypeDescMapping");
			if(!Util.isEmptyString(lstrTransactionTypeDesc))
			{
				String[] TransactionTypes = lstrTransactionTypeDesc.split("\\|\\|");
				if(TransactionTypes != null && TransactionTypes.length >0)
				{
					for (String lstrTransactionTypeRow : TransactionTypes) 
					{
						if(!Util.isEmptyString(lstrTransactionTypeRow))
						{
							String[] singleTransactionDetail = lstrTransactionTypeRow.split("\\|");
							if(singleTransactionDetail == null || singleTransactionDetail.length != 4)
							{
								continue;
							}
							if(Util.isEmptyString(singleTransactionDetail[0])== false)
							{
								lDetails = new TransactionTypeDetails();
								lDetails.setTransactionType(singleTransactionDetail[0]);
								lDetails.setTransactionDescShort(singleTransactionDetail[1]);
								lDetails.setTransactionDesc(singleTransactionDetail[2]);
								lDetails.setTransactionCategory(singleTransactionDetail[3]);
								//mmapTransactiondetails.put(lDetails.getTransactionType(), lDetails);
								mmapTransactiondetails.put(lDetails.getTransactionType()+"|"+lDetails.getTransactionDescShort(), lDetails);
							}
						}					 
					}
				}
			}
		}
		catch(Exception e)
		{
			LOGGER.error("Unable to load transaction type details", e);
		}
	}
	
}
