/**
 * 
 */
package com.isuite.rjil.iagent.jiomoney.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.isuite.rjil.iagent.jiomoney.common.ReferenceData;
import com.isuite.rjil.iagent.jiomoney.dao.ReferenceDataDao;
import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;
import com.isuite.rjil.iagent.jiomoney.util.PropertiesUtil;

/**
 * Title: ReferenceDataDaoImpl.java <br>
 * Company: 2014-15 NovelVox Software Pvt Ltd<br>
 * 
 * @author Anand Raman(AR)
 * @version Build 0.5 <br>
 *          Changes and requirement:<br>
 *          <ul>
 * 
 *          </ul>
 * @version ReleaseR1-Sprint 3
 * 
 */
public class ReferenceDataDaoImpl implements ReferenceDataDao {

	private Properties properties = PropertiesUtil
			.getProperties(PropertiesUtil.jiomoneyutil);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.isuite.rjil.iagent.jiomoney.dao.ReferenceDataDao#getReferenceDataValues
	 * (java.lang.String)
	 */
	@Override
	public List<ReferenceData> getReferenceDataValues(String lovType)
			throws DataAccessException {

		List<ReferenceData> returnList = new ArrayList<ReferenceData>();

		String lovTypeProp = properties.getProperty(lovType);
		List<String> statusKey = Arrays.asList(lovTypeProp.split(","));
		for (int i = 0; i < (statusKey.size() - 1); i++) {
			ReferenceData data = new ReferenceData();
			data.setCode(statusKey.get(i));
			i = i + 1;
			data.setDescription(statusKey.get(i));
			data.setValue(statusKey.get(i));

			returnList.add(data);
		}
		return returnList;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.isuite.rjil.iagent.jiomoney.dao.ReferenceDataDao#lookUpValueByCode
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public String lookUpValueByCode(String lovType, String lovCode)
			throws DataAccessException {
		// TODO Auto-generated method stub
		String returnList = "";
		String lovTypeValue = properties.getProperty(lovType);
		String[] listValue = lovTypeValue.trim().split(",", 0);
		String[] subList;

		for (int i = 0; i < listValue.length; i++) {
			boolean isparentavailable = listValue[i].toString().trim()
					.contains(lovCode);
			if (isparentavailable) {
				subList = listValue[i].split("-", 0);
				for (int j = 0; j < 1; j++) {
					returnList = subList[1];
				}
			}
		}
		return returnList;
	}

	public List<ReferenceData> getInteractionSubCategory(String lovType,
			String parentLovCode) {
		List<ReferenceData> returnList = new ArrayList<ReferenceData>();
		String lovTypeValue = properties.getProperty(lovType);
		String[] listValue = lovTypeValue.split(",", 0);
		String[] subList;
		for (int i = 0; i < listValue.length; i++) {
			boolean isavailable = listValue[i].toString().contains(
					parentLovCode);
			if (true == isavailable) {
				subList = listValue[i].split("-", 0);
				for (int j = 1; j < 2; j++) {
					ReferenceData data = new ReferenceData();
					data.setCode(subList[1]);
					data.setDescription(subList[2]);
					returnList.add(data);

				}
			}
		}
		return returnList;
	}

	@Override
	public List<ReferenceData> getInteractionSubSubCategory(
			String interactionSubSubCategory, String lovCode)
			throws DataAccessException {
		// TODO Auto-generated method stub
		List<ReferenceData> returnList = new ArrayList<ReferenceData>();
		if (lovCode != null && !lovCode.isEmpty()) {
			String subsubcategory = properties
					.getProperty(interactionSubSubCategory);

			String[] listValue = subsubcategory.trim().split(",", 0);
			String[] subList;
			for (int i = 0; i < listValue.length; i++) {
				boolean isparentavailable = listValue[i].toString().contains(
						lovCode);
				if (isparentavailable) {
					subList = listValue[i].split("-", 0);
					for (int j = 0; j < 1; j++) {
						ReferenceData data = new ReferenceData();
						data.setCode(subList[1]);
						data.setDescription(subList[2]);
						returnList.add(data);
					}
				}
			}
		}
		return returnList;
	}

	public List<ReferenceData> getPhysicalCardStatus() {
		List<ReferenceData> returnList = new ArrayList<ReferenceData>();
		String cardStatus = properties.getProperty("cardStatus");
		List<String> listValue = Arrays.asList(cardStatus.trim().split(",", 0));
		for (String values : listValue) {
			ReferenceData data = new ReferenceData();
			List<String> codeValue = Arrays.asList(values.split("\\|"));
			for (int i = 0; i < codeValue.size(); i++) {
				data.setCode(codeValue.get(0));// codeValue[0]);
				data.setDescription(codeValue.get(1));
				data.setValue(codeValue.get(1));
			}

			returnList.add(data);
		}
		return returnList;
	}

}
