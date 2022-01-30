package com.isuite.rjil.iagent.jiomoney.services.impl;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.common.Notification;
import com.isuite.rjil.iagent.jiomoney.dao.DaoLocator;
import com.isuite.rjil.iagent.jiomoney.dao.NotificationMgmtDao;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.exception.ServiceException;
import com.isuite.rjil.iagent.jiomoney.services.NotificationMgmtService;

public class NotificationMgmtServiceImpl implements NotificationMgmtService {
	private static final Logger logger = Logger
			.getLogger(NotificationMgmtServiceImpl.class);

	private static final String service = "NotificationMgmtDao";

	@Override
	public Notification submitNotification(String notification_Template_ID,
			String channel, String communication_Type,
			String destination_Address, String name, String value,String agentId,long requestId)
			throws DataAccessException {
		Notification submitNotification = null;
		try {
			// if (notification_Template_ID == null ||
			// notification_Template_ID.isEmpty()) {
			//
			// throw new
			// ServiceException("Mandatory Parameter require for Notification Template Id");
			// }
			submitNotification = ((NotificationMgmtDao) DaoLocator
					.getDao(service)).submitNotification(
					notification_Template_ID, channel, communication_Type,
					destination_Address, name, value,agentId,requestId);

		} catch (DataAccessException e) {
			logger.error("Exception catched while doing SubmitNotification ", e);
			throw e;
		}
		return submitNotification;

	}

	@Override
	public Notification submitNotificationbyEmailId(
			String notification_Template_ID, String channel,
			String communication_Type, String destination_Address, String name,
			String value, String smsemailidname, String smsemailidvalue,String agentId,long requestId)
			throws DataAccessException, ServiceException {
		Notification submitNotification = null;
		try {
			// if (notification_Template_ID == null ||
			// notification_Template_ID.isEmpty()) {
			//
			// throw new
			// ServiceException("Mandatory Parameter require for Notification Template Id");
			// }
			submitNotification = ((NotificationMgmtDao) DaoLocator
					.getDao(service)).submitNotificationbyEmailId(
					notification_Template_ID, channel, communication_Type,
					destination_Address, name, value,smsemailidname,smsemailidvalue,agentId,requestId);

		} catch (DataAccessException e) {
			logger.error("Exception catched while doing SubmitNotification ", e);
			throw e;
		}
		return submitNotification;

	}

}
