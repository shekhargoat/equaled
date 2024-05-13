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
package com.equaled.eserve.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
@Builder
@Getter
public class CommonError {
	
	   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	   private LocalDateTime timestamp;
	   private String message;
	   private String debugMessage;
	   private String errorCode;

}
