
package com.equaled.customexception;

public enum ErrorCodes {

	C001("Number is not valid"),
	C002("Jwt token not valid"),
	C003("Customers not found"),
	C004("You are not authorized to perform this action"),
	C005("Jwt token is expired"),
	C006("Incorrect project sid"),
	C007("Incorrect CompanySid"),
	C008("Addition to company or Department Failed");


	ErrorCodes(String msg) {
		this.msg = msg;
	}

	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}