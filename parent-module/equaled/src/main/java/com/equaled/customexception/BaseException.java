package com.equaled.customexception;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class BaseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 635732921917927686L;
	public String devMessage;
	public ErrorCodes errorCode;
	public Throwable e;

	public BaseException(Throwable e) {
		this.e = e;
	}

	public BaseException(ErrorCodes errorCoEnum) {
		this.errorCode = errorCoEnum;
	}
	public BaseException(Throwable e,ErrorCodes error) {
		this.e=e;
		this.errorCode=error;
	}

}
