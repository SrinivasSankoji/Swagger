/*
 * 
 */
package com.isuite.rjil.iagent.jiomoney.dao;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.dao.impl.AccountInquiryDaoImpl;
import com.isuite.rjil.iagent.jiomoney.dao.impl.AgentAssistedCustomermanagementDaoImpl;
import com.isuite.rjil.iagent.jiomoney.dao.impl.CustomerAccountManagementDaoImpl;
import com.isuite.rjil.iagent.jiomoney.dao.impl.CustomerInquiryDaoImpl;
import com.isuite.rjil.iagent.jiomoney.dao.impl.CustomerJioPointsRESTAPIDaoImpl;
import com.isuite.rjil.iagent.jiomoney.dao.impl.CustomerOnboardingDaoImpl;
import com.isuite.rjil.iagent.jiomoney.dao.impl.CustomerProfileMgmtDaoImpl;
import com.isuite.rjil.iagent.jiomoney.dao.impl.InstrumentInquiryDaoImpl;
import com.isuite.rjil.iagent.jiomoney.dao.impl.InstrumentManagementDaoImpl;
import com.isuite.rjil.iagent.jiomoney.dao.impl.InteractionInquiryDaoImpl;
import com.isuite.rjil.iagent.jiomoney.dao.impl.InteractionManagementDaoImpl;
import com.isuite.rjil.iagent.jiomoney.dao.impl.NotificationMgmtDaoImpl;
import com.isuite.rjil.iagent.jiomoney.dao.impl.ReferenceDataDaoImpl;
import com.isuite.rjil.iagent.jiomoney.dao.impl.RilLcmDaoImpl;
import com.isuite.rjil.iagent.jiomoney.dao.impl.ServiceRequesManagementDaoImpl;
import com.isuite.rjil.iagent.jiomoney.dao.impl.ServiceRequestInquiryDaoImpl;
import com.isuite.rjil.iagent.jiomoney.dao.impl.TranscationInquiryDaoImpl;

/**
 * A non-instantiable class. Placeholder for dao. <BR>
 * This class make sure that all the dao layer objects instantiates only once.
 * 
 * @author NovelVox
 * 
 */
public final class DaoLocator {

	private DaoLocator() {

	}

	private static final Logger logger = Logger.getLogger(DaoLocator.class);
	private static final ConcurrentMap<String, Object> servicesDao = new ConcurrentHashMap<String, Object>();

	public static Object getDao(final String name) {
		Object obj = null;
		if ((obj = servicesDao.get(name)) == null) {
			return putDao(name);
		}
		return obj;
	}

	/**
	 * @param name
	 * @return object
	 * 
	 */
	private static Object putDao(String name) {

		if ("AccountInquiryDao".equals(name)) {
			AccountInquiryDao accountInquiryDao = new AccountInquiryDaoImpl();
			servicesDao.put(name, accountInquiryDao);
			return accountInquiryDao;
		}
		if ("InstrumentManagementDao".equals(name)) {
			InstrumentManagementDao instrumentManagementDaoDao = new InstrumentManagementDaoImpl();
			servicesDao.put(name, instrumentManagementDaoDao);
			return instrumentManagementDaoDao;
		}
		if ("InstrumnetInquiryDao".equals(name)) {
			InstrumentInquiryDao instrumentInquiryDao = new InstrumentInquiryDaoImpl();
			servicesDao.put(name, instrumentInquiryDao);
			return instrumentInquiryDao;
		}
		if ("TranscationInquiryDao".equals(name)) {
			TranscationInquiryDao transcationInquiryDao = new TranscationInquiryDaoImpl();
			servicesDao.put(name, transcationInquiryDao);
			return transcationInquiryDao;
		}
		if ("CustomerInquiryDao".equals(name)) {
			CustomerInquiryDao customerInquiryDao = new CustomerInquiryDaoImpl();
			servicesDao.put(name, customerInquiryDao);
			return customerInquiryDao;
		}
		if ("CustomerAccountManagementDao".equals(name)) {
			CustomerAccountManagementDao CustomerAccountManagementDao = new CustomerAccountManagementDaoImpl();
			servicesDao.put(name, CustomerAccountManagementDao);
			return CustomerAccountManagementDao;
		}
		if ("NotificationMgmtDao".equals(name)) {
			NotificationMgmtDao notificationMgmtDao = new NotificationMgmtDaoImpl();
			servicesDao.put(name, notificationMgmtDao);
			return notificationMgmtDao;
		}
		if ("CustomerProfileMgmtDao".equals(name)) {
			CustomerProfileMgmtDao customerProfileMgmtDao = new CustomerProfileMgmtDaoImpl();
			servicesDao.put(name, customerProfileMgmtDao);
			return customerProfileMgmtDao;
		}
		if ("ServiceRequestInquiryDao".equals(name)) {
			ServiceRequestInquiryDao serviceRequestInquiryDaoImpl = new ServiceRequestInquiryDaoImpl();
			servicesDao.put(name, serviceRequestInquiryDaoImpl);
			return serviceRequestInquiryDaoImpl;
		}
		if ("ReferenceDataDao".equals(name)) {
			ReferenceDataDao referenceDataDao = new ReferenceDataDaoImpl();
			servicesDao.put(name, referenceDataDao);
			return referenceDataDao;
		}
		if ("InteractionManagementDao".equals(name)) {
			InteractionManagementDao interactionManagementDao = new InteractionManagementDaoImpl();
			servicesDao.put(name, interactionManagementDao);
			return interactionManagementDao;
		}
		if ("InteractionInquiryDao".equals(name)) {
			InteractionInquiryDao interactionInquiryDao = new InteractionInquiryDaoImpl();
			servicesDao.put(name, interactionInquiryDao);
			return interactionInquiryDao;
		}
		if ("AgentAssistedCustomermanagementDao".equals(name)) {
			AgentAssistedCustomermanagementDao agentAssistedCustomermanagementDao = new AgentAssistedCustomermanagementDaoImpl();
			servicesDao.put(name, agentAssistedCustomermanagementDao);
			return agentAssistedCustomermanagementDao;
		}
		if ("RilLcmServicesDao".equals(name)) {
			RilLcmDao agentAssistedCustomermanagementDao = new RilLcmDaoImpl();
			servicesDao.put(name, agentAssistedCustomermanagementDao);
			return agentAssistedCustomermanagementDao;
		}
		if ("ServiceRequestManagementDAO".equals(name)) {
			ServiceRequestManagementDao lDao = new ServiceRequesManagementDaoImpl();
			servicesDao.put(name, lDao);
			return lDao;
		}
		if ("CustomerJioPointsRESTAPIDao".equals(name)) {
			CustomerJioPointsRESTAPIDao lDao = new CustomerJioPointsRESTAPIDaoImpl();
			servicesDao.put(name, lDao);
			return lDao;
		}
		if ("CustomerOnboardingDAO".equals(name)) {
			CustomerOnboardingDao lDao = new CustomerOnboardingDaoImpl();
			servicesDao.put(name, lDao);
			return lDao;
		}	
		
		else {
			logger.error("Dao Locator is not able to create the Dao Object for "
					+ name + ". Please check the Dao name.");
			throw new IllegalStateException(
					"Dao Locator is not able to create the Dao Object for "
							+ name + ". Please check the service name.");
		}

	}

}
