/*
 *
 *  CallComm Technologies, Inc. Confidential
 *
 *
 *  Copyright (c) CallComm Technologies, Inc. 2020  2020. All rights reserved
 *
 *  NOTICE:  All information contained herein is, and remains
 *  the property of CallComm Technologies, Inc. and its suppliers,
 *  if any.  The intellectual and technical concepts contained
 *  herein are proprietary to CallComm Technologies, Inc.
 *  and its suppliers and may be covered by U.S. and Foreign Patents,
 *  patents in process, and are protected by trade secret or copyright law.
 *  Dissemination of this information or reproduction of this material
 *  is strictly forbidden unless prior written permission is obtained
 *  from CallComm Technologies, Inc.
 *
 */

package com.equaled.eserve.common.exception;

import com.equaled.eserve.exception.errorcode.ErrorCodes;
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
	public BaseException(String message){
		super(message);
	}
}
