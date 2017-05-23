package com.lfwer.common;


/**
 * 封装数据.
 * 
 * @author Administrator
 *
 */
public class Valid {
	private boolean valid;
	private String message;

	public Valid() {

	}

	public Valid(boolean valid, String message) {
		this.valid = valid;
		this.message = message;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	 
}
