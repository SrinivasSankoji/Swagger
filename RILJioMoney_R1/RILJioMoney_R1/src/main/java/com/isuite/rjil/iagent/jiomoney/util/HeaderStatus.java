/**
 * 
 */
package com.isuite.rjil.iagent.jiomoney.util;

import com.isuite.rjil.iagent.jiomoney.common.BusinessApplication;
import com.isuite.rjil.iagent.jiomoney.common.Message;
import com.isuite.rjil.iagent.jiomoney.common.Status;

/**
 * @author Novelvox
 * 
 */
public final class HeaderStatus {
	/*private static String agentId;
	*/
	private HeaderStatus(){
		
	}

	public static Message setMessage(String businessId, String esbId,
			String messageId, String correlationId, String extCorrelationId,
			String timeStamp) {
		Message messagePojo = new Message();
		messagePojo.setBusinessId(businessId != null ? businessId : "");
		messagePojo
				.setExtCorrelationId(extCorrelationId != null ? extCorrelationId
						: "");
		messagePojo
				.setCorrelationId(correlationId != null ? correlationId : "");
		messagePojo.setEsbId(esbId != null ? esbId : "");
		messagePojo.setTimeStamp(timeStamp != null ? timeStamp : "");
		return messagePojo;
	}

	public static BusinessApplication setBusinessApplication(
			String serviceVersion, String operationVersion,
			String operationName, String messageType, String srcApplicationname) {
		BusinessApplication businessApplicationPojo = new BusinessApplication();
		businessApplicationPojo.setServiceVersion(serviceVersion);
		businessApplicationPojo.setOperationVersion(operationVersion);
		businessApplicationPojo.setMessageType(messageType);
		businessApplicationPojo.setSrcApplicationname(srcApplicationname);

		return businessApplicationPojo;

	}


	public static Status setStatus(String code, String msg,
			String providerName, String providerCode) {
		Status providerStatus = new Status();
		providerStatus.setCode(code != null ? code : "");
		providerStatus.setMessage(msg != null ? msg : "");
		providerStatus
				.setProviderName(providerName != null ? providerName : "");
		providerStatus
				.setProviderCode(providerCode != null ? providerCode : "");
		return providerStatus;
	}

}
