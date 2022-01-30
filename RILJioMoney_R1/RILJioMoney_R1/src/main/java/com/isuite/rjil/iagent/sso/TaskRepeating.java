package com.isuite.rjil.iagent.sso;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.util.PropertiesUtil;

public class TaskRepeating extends Thread {

	private static final Logger LOGGER = Logger.getLogger(TaskRepeating.class);

	private static Properties properties;

	// This task will repeat every five seconds
	@Override
	public void run() {

		properties = PropertiesUtil.getProperties(PropertiesUtil.platform);

		// Declared for time delay
		long timeoutDelay = 5000;
		timeoutDelay = Long.parseLong(properties
				.getProperty("SSOTimeoutDelayHours"));

		String timeoutSec = properties.getProperty("SSOTimeoutSec");
		LOGGER.info("Property - timeout delay - " + timeoutDelay);
		LOGGER.info("Property - timeout: " + timeoutSec);
		while (true) {
			try {
				LOGGER.info("UNIQUE ID THREAD IS RUNNUNG IN WHILE");

				ManageUniqueIdUtil.INSTANCE.removeUniqueId(timeoutSec);
				Thread.sleep(timeoutDelay);

			} catch (InterruptedException e) {
				LOGGER.error("Error in Remove Thread", e);
				e.printStackTrace();
			}
		}
	}
}