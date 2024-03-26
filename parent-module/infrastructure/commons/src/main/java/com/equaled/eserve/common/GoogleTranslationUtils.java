package com.equaled.eserve.common;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author hirshendu@callcomm.com
 *
 */
public class GoogleTranslationUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(GoogleTranslationUtils.class);
	private static final GoogleTranslationUtils httpUtils = new GoogleTranslationUtils();
	
	private static String DETECT_URL="https://translation.googleapis.com/language/translate/v2"
			+ "/detect?key=";
	
	private static String TRANSLATE_URL="https://translation.googleapis.com/language/translate"
			+ "/v2?key=";
	
//	private static String googleKey="AIzaSyCxGPUdDqV7dpeUXDHDsM-LTN6lnj88EHk";
	private static String googleKey="AIzaSyCGbetZTGoxgqUskHg-aWcLTnOWjyd-B5M";


	private GoogleTranslationUtils(){	}
	public static GoogleTranslationUtils getInstance(){
		return httpUtils;
	}

	public static String detectLanguage(String sourceText){
		if(CommonUtils.isEmpty(sourceText))
			return null;
		Map<String,String> requestMap=new HashMap<String,String>();
		requestMap.put("q", sourceText);
		String googleResponse=HttpUtils.postJsonUrl(requestMap, DETECT_URL.concat(googleKey), null);

		Map<String,Object> responseMap=CommonUtils.gsonSupplier.get().fromJson(googleResponse,Map.class);
		Map map2 = MapUtils.getMap(responseMap, "data");
		List<List<Object>> detectionList=(List<List<Object>>) map2.get("detections");
		logger.error("Map Object",detectionList);
		if(detectionList==null || detectionList.isEmpty()){
			return null;
		}
		Object object=detectionList.get(0).get(0);
		logger.error("Final Object",CommonUtils.toJsonFunction.apply(object));
		Map<String,Object> finalMap=(Map<String, Object>) object;
		return finalMap.get("language").toString();
	}
		
	public static String translateText(
		      String sourceText,
		      String targetLang) {
			
			if(CommonUtils.isEmpty(sourceText))
				return null;
			Map<String,String> requestMap=new HashMap<String,String>();
			requestMap.put("q", sourceText);
			requestMap.put("target", Optional.ofNullable(targetLang).filter(StringUtils::isNotEmpty)
					.map(str -> StringUtils.replace(str,"-Latn","")).orElse("en"));
			String googleResponse=HttpUtils.postJsonUrl(requestMap, TRANSLATE_URL.concat(googleKey), null);
//		restTemplateConfig.restTemplate().postForEntity(intentDetectionUrl, intentDetectionPayload, Map.class)
			Map<String,Object> responseMap=CommonUtils.gsonSupplier.get().fromJson(googleResponse,Map.class);
			Map data = MapUtils.getMap(responseMap,"data");
			logger.info("Map Object",CommonUtils.toJsonFunction.apply(data));
			List<Object> detections=(List<Object>) data.get("translations");
			if(detections==null || detections.isEmpty()){
				return null;
			}
			Object object=detections.get(0);
			Map<String,Object> translation=(Map<String, Object>) object;
		    return translation.get("translatedText").toString();
	 }


}
