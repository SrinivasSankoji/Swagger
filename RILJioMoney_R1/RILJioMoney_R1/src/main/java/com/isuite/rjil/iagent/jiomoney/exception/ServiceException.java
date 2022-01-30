/*
 * 
 */
package com.isuite.rjil.iagent.jiomoney.exception;

public class ServiceException extends Throwable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1500048729996078739L;

	public ServiceException() {

	}

	public ServiceException(Throwable t) {
		super(t);
	}

	public ServiceException(String msg, Throwable t) {
		super(msg, t);
	}

	public ServiceException(String msg) {
		super(msg);
	}
}
