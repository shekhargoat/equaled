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
package com.equaled.eserve.common.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JWTTokenTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8084246161578674999L;
	private String companySid;
	private String virtualAccountSid;
	private String userSid;
	private String displayName;
	private String projectSid;
	private String emailId;
	private String userRoles;
	private String primaryContact;
	private String companyRole;
	private String teamSid;
}
