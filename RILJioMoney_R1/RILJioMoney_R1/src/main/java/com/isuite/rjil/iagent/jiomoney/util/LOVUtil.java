/*
 * 
 */
package com.isuite.rjil.iagent.jiomoney.util;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.isuite.rjil.iagent.jiomoney.common.ReferenceData;

/**
 * A non-instantiable class, placeholder for LOVs
 * 
 * @author NovelVox
 * 
 */
public final class LOVUtil {
	
	private LOVUtil() {

	}

	// define LOV types here
	/************************* Jio Money *****************************************/
	public static final String STATUS = "status";
	public static final String CATEGORY = "category";
	public static final String CHANNEL = "channel";
	public static final String IMPACT = "Consumer_impact";
	public static final String URGENCY = "Consumer_Urgency";
	public static final String CARD_STATUS = "CardStatus";
	
	public static final String VERIFICATIONQUESTION = "verificationquestion";
	
	
	public static final String INTERACTION_CATEGORY="interactioncategory";
	public static final String INTERACTION_SUB_CATEGORY ="interactionsubcategory";
	public static final String INTERACTION_SUB_SUB_CATEGORY="interactionsubsubcategory";
	public static final String INTERACTION_DESCRIPTION="interactionDescriptions";
	public static final String SOAPLOGGER = "soapLogger";
	public static final String SERVICE_TIME_LOGGER="serviceTimeLogger";
	public static final String consumerLogger = "consumerLogger";
	/*************************************************************/

	private static Map<String, List<ReferenceData>> lov = new ConcurrentHashMap<String, List<ReferenceData>>();

	/**
	 * This method assuming that Map will Contain the values for every key. The
	 * lov Map will be filled with values at the time of
	 * ServletContextInitialization. The key provided should belongs from the
	 * class itself.
	 * 
	 * Those methods are provided as a wrapper to avoid the uncontrolled access
	 * of lov cache map.
	 * 
	 * @param type
	 * @return
	 */
	public static List<ReferenceData> getLov(final String type) {
		return lov.get(type);
	}
	
	public static Map<String, List<ReferenceData>> getLov() {
		return lov;
	}

	/**
	 * This method will be called from two places. From Context initialization
	 * listener and Web service data change notification listener.
	 * 
	 * @param key
	 * @param values
	 */
	public static void setLov(String key, List<ReferenceData> values) {
		lov.put(key, values);
	}

	/**
	 * returns null string if provided code is null or empty.
	 * 
	 * @param code
	 * @param lovType
	 * @return
	 */
	public static String getValue(String code, String lovType) {
		/*
		 * if (code == null || code.isEmpty()) return ""; List<ReferenceData>
		 * lovs = getLov(lovType); if (lovs == null || lovs.size() == 0) throw
		 * new IllegalStateException(" LOVs for " + lovType + " is not found.");
		 * int i = lovs.indexOf(new ReferenceData(code)); if (i != -1)
		 */
		ReferenceData lovs = getLovObject(code, lovType);
		if (lovs != null)
			return lovs.getValue();
		return "";

	}

	public static ReferenceData getLovObject(String code, String lovType) {
		if (code == null || code.isEmpty())
			return null;
		List<ReferenceData> lovs = getLov(lovType);
		if (lovs == null || lovs.size() == 0)
			throw new IllegalStateException(" LOVs for " + lovType
					+ " is not found.");
		int i = lovs.indexOf(new ReferenceData(code));
		if (i != -1)
			return lovs.get(i);
		return null;

	}

}
