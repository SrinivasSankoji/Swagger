/*
 * 
 */
package com.isuite.rjil.iagent.jiomoney.exception;

/**
 * 
 * @author NovelVox
 * 
 */
public class DataAccessException extends Throwable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 8320458789094678765L;
	private String messaage;

	public DataAccessException() {
		super();
	}

	public DataAccessException(Throwable t) {
		super(t);
	}

	public DataAccessException(String msg, Throwable t) {
		super(msg, t);
	}

	/**
	 * @param string
	 */
	public DataAccessException(String messaage) {
		super(messaage);
		this.setMessaage(messaage);
	}

	public String getMessaage() {
		return messaage;
	}

	public void setMessaage(String messaage) {
		this.messaage = messaage;
	}
}
