package com.isuite.rjil.iagent.sso;

import java.util.Calendar;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

/**
 * This class will manage all random generated unique id for generated URL in
 * login servlet
 * 
 * @author BAL VIKAS NIRALA
 */
public enum ManageUniqueIdUtil {

	INSTANCE;

	private static final Logger LOGGER = Logger
			.getLogger(ManageUniqueIdUtil.class);

	private CopyOnWriteArrayList<String> uniqueIdList = new CopyOnWriteArrayList<>();

	public int getUniqueCount() {
		if (null != uniqueIdList
				&& (uniqueIdList.size() > 0 || uniqueIdList.size() == 0)) {

			return uniqueIdList.size();

		}
		return 0;
	}

	public void clearUniqueIdSessions() {

		LOGGER.debug("Before - clearUniqueIdSessions : " + uniqueIdList);
		if (null != uniqueIdList && (uniqueIdList.size() > 0)) {

			uniqueIdList.removeAll(uniqueIdList);

		}
		LOGGER.debug("After clearUniqueIdSessions : " + uniqueIdList);
	}

	/**
	 * Add in unique id list
	 * 
	 * @param uniqueId
	 */
	public boolean addUniqueId(String uniqueId) {

		boolean exist = false;
		if (null != uniqueIdList
				&& (uniqueIdList.size() > 0 || uniqueIdList.size() == 0)) {
			if (uniqueIdList.contains(uniqueId)) {
				exist = true;
			} else {
				uniqueIdList.add(uniqueId);
			}
		}
		return exist;

	}

	/**
	 * Remove from unique id list
	 * 
	 * @param uniqueId
	 */
	public void removeUniqueId(String timeoutSec) {
		try {
			LOGGER.debug("UNIQUE ID LIST : " + uniqueIdList);
			Calendar calendar1 = Calendar.getInstance();
			Calendar calendar2 = Calendar.getInstance();
			for (String uniqueId : uniqueIdList) {
				calendar1.setTimeInMillis(System.currentTimeMillis());
				calendar2.setTimeInMillis(Long.parseLong(uniqueId));
				long diffInSec = TimeUnit.MILLISECONDS.toSeconds(Math
						.abs(calendar1.getTimeInMillis()
								- calendar2.getTimeInMillis()));
				long timeout = Long.parseLong(timeoutSec);
				LOGGER.debug("TIMEOUT:" + timeoutSec + ":Diff-" + diffInSec);
				if (diffInSec > timeout) {
					if (uniqueIdList.contains(uniqueId))
						uniqueIdList.remove(uniqueId);
				}
			}
		} catch (Throwable th) {
			LOGGER.error("error while remoing unique id from list ", th);
			th.printStackTrace();
		}
	}

}
