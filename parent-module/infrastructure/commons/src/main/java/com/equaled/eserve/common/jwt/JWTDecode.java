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
package com.equaled.eserve.common.jwt;

import com.equaled.eserve.common.JsonUtils;
import com.equaled.eserve.common.exception.ApplicationException;
import com.equaled.eserve.common.exception.InvalidTokenException;
import com.equaled.eserve.common.to.JWTTokenTO;
import com.equaled.eserve.exception.errorcode.ErrorCodes;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;

public class JWTDecode {
	private static final Logger logger = LoggerFactory.getLogger(JWTDecode.class);
	private static final String JWT_TOKEN = "equaled";

	// This is to decode jwt token and get UserBasicsTO object.
	public static JWTTokenTO parseJWT(String jwtToken) throws InvalidTokenException, ApplicationException {
		try {
			Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(JWT_TOKEN))
					.parseClaimsJws(jwtToken).getBody();
			return JsonUtils.fromJson(claims.getSubject(), JWTTokenTO.class);
		} catch (ExpiredJwtException e) {
			throw new InvalidTokenException(e, ErrorCodes.C011);
		} catch (JwtException e) {
			throw new InvalidTokenException(e, ErrorCodes.C002);
		} catch (Exception e) {
			logger.error("While parsing jwt token, throwing error {}", e);
			throw new ApplicationException(e, "There are some issue, while processing jwt token");
		}
	}
}
