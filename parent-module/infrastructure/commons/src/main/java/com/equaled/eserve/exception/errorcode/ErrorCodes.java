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

public enum ErrorCodes {

	C001("Number is not valid"),
	C002("Jwt token not valid"),
	C003("Request failed! please try after sometime"),
	C004("Input data can't be null"),
	C005("Not connecting to server to serve request"),
	C006("Customers not found"),
	C007("You are not authorized to perform this action"),
	C008("Customer is not deleted, please try again"),
	C009("Number is not oppeted for sms"),
	C010("Customer creation or updation failed"),
	C011("Jwt token is expired"),
	C012("Incorrect project sid"),
	C013("Invalid Customer Sid"),
	C014("Invalid Campaign Sid"),
	C015("Entity not assigned to project"),
	C016("Incorrect CompanySid"),
	C017("Addition to company or Project Failed"),
	C018("The requesting attributes cannot be null"),
	C019("The requested edge label is not valid or this operation"),
	C020("No edge between Project/campaign to the customer"),
	C021("The Single opt in consent for the customer is not available"),
	C022("The consent request has been sent already"),
	C023("The consent request or the mandatory attributes cannot be null"),
	C024("A Customer or Lead exists for the mobile or email"),
	
	L001("Unsupported vertex label to create or update"),
	L010("Lead creation or updation failed"),
	L013("Invalid Lead Sid"),

	SF001("Invalid smart filter payload"),

	// Shopify related Errors
	SH001("Products out of stock "),
	SH002("Order is not associated with customer"),
	SH003("Something went wrong while create/update Shopify Customer"),
	SH004("Existing Shopify Customer not found"),
	SH005("Check Email/Phone, looks like this Customer already registered with Shopify store"),
	SH006("No Shopify products/variants found for populate data"),
	SH007("While doing DATA SYNC,Something went wrong while getting Product/Variant data"),
	SH008("No Shopify Order found for populate data"),
	SH009("Something wrong with dataSyncDetails parameter in body while doing Shopify Data Manual Sync !"),
	SH010("Error while updating quantity of line items"),
	SH011("Error while doing refund"),
	SH012("Error in shopify customer payload"),
	SH013("Existing order not found"),
	SH014("Shopify store not integrated"),

	U001("User not found"),

	U002("Invalid user id"),

	S001("Subject not found");



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