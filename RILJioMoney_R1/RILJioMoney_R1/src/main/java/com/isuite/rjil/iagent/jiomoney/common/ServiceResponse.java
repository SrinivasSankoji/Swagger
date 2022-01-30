/*
 * 
 */
package com.isuite.rjil.iagent.jiomoney.common;

import java.io.Serializable;

/**
 * 
 * @author NovelVox
 * 
 */
public class ServiceResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4654901884844449743L;
	// actual data being transferred to flex UI
	private Object data;
	// 0 for success, 1 for fail. Default set to 0
	private int status = 0;

	// Either contains exception text,
	// or a message that will be shown as alert on flex end.
	private String msg;
	
	
	// possible values are List, Object
	// default set to Object
	private String metadata = "Object";
	
	private Object secodaryData = null;

	public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	/**
	 * Invoke in case of success when no message has to send back to flex and
	 * data is a single object not a list.
	 * 
	 * @param data
	 */
	public ServiceResponse(Object data) {
		this.data = data;
	}

	
	public ServiceResponse(int status, String msg, Object data) {
		super();
		this.data = data;
		this.status = status;
		this.msg = msg;
	}
	/**
	 * Invoke in case of success and a custom message has to send back to flex.
	 * For example, "Service Request created. Your SR ID is: xxxxxxx."
	 * 
	 * @param data
	 * @param metadata
	 * @param msg
	 */
	public ServiceResponse(final Object data, String metadata, String msg) {
		this.data = data;
		this.metadata = metadata;
		this.msg = msg;
	}

	/**
	 * Invoke in case of error or exception
	 * 
	 * @param data
	 * @param status
	 * @param error
	 */
	public ServiceResponse(int status, String error) {
		super();
		this.status = status;
		this.msg = error;
	}

	public void setSecodaryData(Object secodaryData) {
		this.secodaryData = secodaryData;
	}
	
	public Object getData() {
		return data;
	}
	
	public Object getSecodaryData() {
		return secodaryData;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
