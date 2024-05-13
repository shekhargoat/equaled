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

package com.equaled.eserve.exception.errorcode;

public enum ImportErrorEnum {
	
	I001("Requested data is not valid");
	
	
	ImportErrorEnum(String msg) {
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
