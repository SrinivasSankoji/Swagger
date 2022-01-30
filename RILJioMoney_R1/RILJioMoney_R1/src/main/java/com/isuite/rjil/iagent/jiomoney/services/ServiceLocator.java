/*
 * 
 */
package com.isuite.rjil.iagent.jiomoney.services;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.services.impl.AccountInquiryServiceImpl;
import com.isuite.rjil.iagent.jiomoney.services.impl.AgentAssistedCustomermanagementServiceImpl;
import com.isuite.rjil.iagent.jiomoney.services.impl.CustomerAccountManagementServiceImpl;
import com.isuite.rjil.iagent.jiomoney.services.impl.CustomerInquiryServiceImpl;
import com.isuite.rjil.iagent.jiomoney.services.impl.CustomerJioPointsRESTAPIServiceImpl;
import com.isuite.rjil.iagent.jiomoney.services.impl.CustomerOnboardingServiceImpl;
import com.isuite.rjil.iagent.jiomoney.services.impl.CustomerProfileMgmtServiceImpl;
import com.isuite.rjil.iagent.jiomoney.services.impl.InstrumentInquiryServiceImpl;
import com.isuite.rjil.iagent.jiomoney.services.impl.InstrumentManagementServiceImpl;
import com.isuite.rjil.iagent.jiomoney.services.impl.InteractionInquiryServiceImpl;
import com.isuite.rjil.iagent.jiomoney.services.impl.InteractionManagementServiceImpl;
import com.isuite.rjil.iagent.jiomoney.services.impl.NotificationMgmtServiceImpl;
import com.isuite.rjil.iagent.jiomoney.services.impl.ReferenceDataServiceImpl;
import com.isuite.rjil.iagent.jiomoney.services.impl.RilLcmServiceImpl;
import com.isuite.rjil.iagent.jiomoney.services.impl.ServiceRequestInquiryServiceImpl;
import com.isuite.rjil.iagent.jiomoney.services.impl.ServiceRequestManagementServiceImpl;
import com.isuite.rjil.iagent.jiomoney.services.impl.TranscationInquiryPaymentBankServiceImpl;
import com.isuite.rjil.iagent.jiomoney.services.impl.TranscationInquiryServiceImpl;

/**
 * A non-instantiable class. Placeholder for Services. <BR>
 * This class make sure that all the service layer objects instantiates only
 * once.
 * 
 * @author NovelVox
 * 
 */
public final class ServiceLocator {

	private ServiceLocator() {

	}

	private static final ConcurrentMap<String, Object> services = new ConcurrentHashMap<String, Object>();
	private static final Logger logger = Logger.getLogger(ServiceLocator.class);

	public static Object getService(final String name) {
		Object obj = null;
		if ((obj = services.get(name)) == null) {
			return putService(name);
		}
		return obj;
	}

	private static Object putService(String name) {

		if ("AccountInquiryService".equals(name)) {
			AccountInquiryService accountInquiryService = new AccountInquiryServiceImpl();
			services.put(name, accountInquiryService);
			return accountInquiryService;
		}
		if ("InstrumentManagementService".equals(name)) {
			InstrumentManagementService instrumentManagementService = new InstrumentManagementServiceImpl();
			services.put(name, instrumentManagementService);
			return instrumentManagementService;
		}
		if ("CustomerInquiryService".equals(name)) {
			CustomerInquiryService customerInquiryService = new CustomerInquiryServiceImpl();
			services.put(name, customerInquiryService);
			return customerInquiryService;
		}
		if ("CustomerAccountManagementService".equals(name)) {
			CustomerAccountManagementService customerAccountManagementService = new CustomerAccountManagementServiceImpl();
			services.put(name, customerAccountManagementService);
			return customerAccountManagementService;
		}

		if ("InstrumentInquiryService".equals(name)) {
			InstrumentInquiryService transactionInquiryService = new InstrumentInquiryServiceImpl();
			services.put(name, transactionInquiryService);
			return transactionInquiryService;
		}
		if ("TranscationInquiryService".equals(name)) {
			TranscationInquiryService transactionInquiryService = new TranscationInquiryServiceImpl();
			services.put(name, transactionInquiryService);
			return transactionInquiryService;
		}
		if ("InstrumenttManagementService".equals(name)) {
			InstrumentInquiryService transactionInquiryService = new InstrumentInquiryServiceImpl();
			services.put(name, transactionInquiryService);
			return transactionInquiryService;
		}
		if ("NotificationMgmtService".equals(name)) {
			NotificationMgmtService notificationMgmtservice = new NotificationMgmtServiceImpl();
			services.put(name, notificationMgmtservice);
			return notificationMgmtservice;
		}
		if ("CustomerProfileMgmtService".equals(name)) {
			CustomerProfileMgmtService CustomerProfileMgmtService = new CustomerProfileMgmtServiceImpl();
			services.put(name, CustomerProfileMgmtService);
			return CustomerProfileMgmtService;
		}
		
		if ("ServiceRequestInquiryService".equals(name)) {
			ServiceRequestInquiryService serviceRequestInquiryService = new ServiceRequestInquiryServiceImpl();
			services.put(name, serviceRequestInquiryService);
			return serviceRequestInquiryService;
		}
		if ("ReferenceDataService".equals(name)) {
			ReferenceDataService referenceDataService = new ReferenceDataServiceImpl();
			services.put(name, referenceDataService);
			return referenceDataService;
		}
		if ("InteractionManagementService".equals(name)) {
			InteractionManagementService interactionManagementService = new InteractionManagementServiceImpl();
			services.put(name, interactionManagementService);
			return interactionManagementService;
		}
		if ("InteractionInquiryService".equals(name)) {
			InteractionInquiryService interactionInquiryService = new InteractionInquiryServiceImpl();
			services.put(name, interactionInquiryService);
			return interactionInquiryService;
		}
		if ("AgentAssistedCustomermanagementService".equals(name))
		{
			AgentAssistedCustomermanagementService agentAssistedCustomermanagementService = new AgentAssistedCustomermanagementServiceImpl();
			services.put(name, agentAssistedCustomermanagementService);
			return agentAssistedCustomermanagementService;
		}
		
		if ("RilLcmService".equals(name))
		{
			RilLcmService agentAssistedCustomermanagementService = new RilLcmServiceImpl();
			services.put(name, agentAssistedCustomermanagementService);
			return agentAssistedCustomermanagementService;
		}
		if ("ServiceRequestManagementService".equals(name))
		{
			ServiceRequestManagementService lService = new ServiceRequestManagementServiceImpl();
			services.put(name, lService);
			return lService;
		}
		if ("CustomerJioPointsRESTAPIService".equals(name))
		{
			CustomerJioPointsRESTAPIService lService = new CustomerJioPointsRESTAPIServiceImpl();
			services.put(name, lService);
			return lService;
		}
		if ("CustomerOnboardingService".equals(name))
		{
			CustomerOnboardingService lService = new CustomerOnboardingServiceImpl();
			services.put(name, lService);
			return lService;
		}
		if ("TranscationInquiryServicePaymentBank".equals(name))
		{
			TranscationInquiryServicePaymentBank lService = new TranscationInquiryPaymentBankServiceImpl();
			services.put(name, lService);
			return lService;
		}
		
		else {
			logger.error("Service Locator is not able to create the Service Object. Please check the service name.");
			throw new IllegalStateException(
					"Service Locator is not able to create the Service Object. Please check the service name.");
		}

	}
}
