package com.isuite.rjil.iagent.jiomoney.services.impl;

import java.util.List;

import com.isuite.rjil.iagent.jiomoney.common.CardDetails;
import com.isuite.rjil.iagent.jiomoney.common.Customer;
import com.isuite.rjil.iagent.jiomoney.common.ReferenceData;
import com.isuite.rjil.iagent.jiomoney.dao.DaoLocator;
import com.isuite.rjil.iagent.jiomoney.dao.InstrumentInquiryDao;
import com.isuite.rjil.iagent.jiomoney.dao.ReferenceDataDao;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.exception.ServiceException;
import com.isuite.rjil.iagent.jiomoney.services.InstrumentInquiryService;
import com.isuite.rjil.iagent.jiomoney.util.LOVUtil;

/**
 * @author NovelVox
 * 
 */
public class InstrumentInquiryServiceImpl implements InstrumentInquiryService {

	private static final String service = "InstrumnetInquiryDao";

	@Override
	public Customer getPhysicalCardList(String custtId,String agentId,long requestId)
			throws DataAccessException, ServiceException 
	{
		Customer getPhysicalCardList = ((InstrumentInquiryDao) DaoLocator
				.getDao(service)).getPhysicalCardList(custtId, agentId,requestId);

		if (getPhysicalCardList != null
				&& getPhysicalCardList.getCardDetails() != null
				&& getPhysicalCardList.getCardDetails().size() > 0) {
			if (LOVUtil.getLov(LOVUtil.CARD_STATUS) == null) {
				List<ReferenceData> statusList = ((ReferenceDataDao) DaoLocator
						.getDao("ReferenceDataDao"))
						.getPhysicalCardStatus();
				LOVUtil.setLov(LOVUtil.CARD_STATUS, statusList);
			}
			for (CardDetails details : getPhysicalCardList.getCardDetails()) {
				if (details.getStatus() != null) {
					details.setStatus(LOVUtil.getValue(details.getStatus(),
							LOVUtil.CARD_STATUS));
				}
			}
		}
		return getPhysicalCardList;
		
	}

	@Override
	public Customer getCardList(String custtId,String agentId,long requestId) throws DataAccessException,
			ServiceException {
		Customer getCardList = ((InstrumentInquiryDao) DaoLocator
					.getDao(service)).getCardList(custtId, agentId,requestId);
			return getCardList;
	}
}
