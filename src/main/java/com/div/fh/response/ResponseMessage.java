/**
 * 
 */
package com.div.fh.response;

/**
 * @author pks
 *
 */
public class ResponseMessage {
	
	public ResponseMessage(String message) {
		this.message = message;
	}

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
