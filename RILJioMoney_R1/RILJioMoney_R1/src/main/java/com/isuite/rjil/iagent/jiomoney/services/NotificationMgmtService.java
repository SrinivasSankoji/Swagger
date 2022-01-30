package com.isuite.rjil.iagent.jiomoney.services;

import com.isuite.rjil.iagent.jiomoney.common.Notification;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.exception.ServiceException;

public interface NotificationMgmtService {

	/**
	 * 
	 * @param notification_Template_ID
	 * @param channel
	 * @param communication_Type
	 * @param destination_Address
	 * @param name
	 * @param value
	 * @return Notification
	 * @throws DataAccessException
	 * @throws ServiceException
	 */
	public Notification submitNotification(String notification_Template_ID,
			String channel, String communication_Type,
			String destination_Address, String name, String value,String agentId,long requestId)
			throws DataAccessException, ServiceException;
	
	
	public Notification submitNotificationbyEmailId(String notification_Template_ID,
			String channel, String communication_Type,
			String destination_Address, String name, String value,String smsemailidname,String smsemailidvalue,String agentId,long requestId)
			throws DataAccessException, ServiceException;

}
