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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;
import org.hornetq.utils.json.JSONArray;
import org.hornetq.utils.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.*;

public class JsonUtils {
	
	private final static Logger logger=LoggerFactory.getLogger(JsonUtils.class);
	private static final Gson gson = new Gson();
	/**
	 * API to convert Pojo object to String
	 * @return String
	 */
	public static <T> String toJsonString(T t){
		try {
			String json = gson.toJson(t);
			return json;
		}
		catch (Exception e) {
			logger.error("Error at toJsonString - {}",e);
			return null;
		}
	}
	/**
	 * API to convert String Json to Pojo object
	 * @return Class<T>
	 */
	public static <T> T fromJson(String json, Class<T> clazz){
		try {
			T t = gson.fromJson(json, clazz);
			return t;
		}
		catch (Exception e) {
			logger.error("Error at fromJsonObject - {}",e);
			return null;
		}
	}

	public static <T> T fromJson(String json, Type clazz){
		try {
			T t = gson.fromJson(json, clazz);
			return t;
		}
		catch (Exception e) {
			logger.error("Error at fromJsonObject - {}",e);
			return null;
		}
	}

	@SneakyThrows
	public static Map<String,String> convertStringToMap(String json){
		return new ObjectMapper().readValue(json, HashMap.class);
	}
	@SneakyThrows
	public static Map<String,Object> convertJsonObjectToMap(Object json){
		Map<String, Object> retMap = new Gson().fromJson(
				String.valueOf(json), new TypeToken<HashMap<String, Object>>() {}.getType()
		);
		return retMap;
	}
	@SneakyThrows
	public static List convertStringToList(String json,String key) {
		JSONObject jsnobject = new JSONObject(json);
		JSONArray jsonArray = jsnobject.getJSONArray(key);
		ArrayList<Object> listdata = new ArrayList<Object>();
		if (jsonArray != null) {
			for (int i=0;i<jsonArray.length();i++){
				listdata.add(jsonArray.get(i));
			}
		}
		return listdata;
	}
	public static <T> T convertMapToObject(Object objToConvert,Class<T> returnType){
		String strMapJson=new Gson().toJson(objToConvert);
		return fromJson(strMapJson, returnType);
	}

}
