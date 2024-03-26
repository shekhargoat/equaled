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

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.equaled.eserve.common.exception.ApplicationException;
import com.intuit.fuzzymatcher.component.MatchService;
import com.intuit.fuzzymatcher.domain.Document;
import com.intuit.fuzzymatcher.domain.Element;
import com.intuit.fuzzymatcher.domain.ElementType;
import com.intuit.fuzzymatcher.domain.Match;
import io.dataapps.chlorine.finder.Finder;
import io.dataapps.chlorine.finder.FinderEngine;
import io.dataapps.chlorine.pattern.RegexFinder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class CommonUtils {
	private static final String REGEX = "^\\+(?:[0-9] ?){6,14}[0-9]$";
	private static final String USA_DIALING_CODE = "+1";
	private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);

	private static FinderEngine finderEngine = null;

	private CommonUtils() {
	}

	/**
	 * API to check whether String is empty or not.
	 *
	 * @param name
	 * @return boolean
	 */
	public static boolean isEmpty(String name) {
		boolean flag = true;
		if (name != null && !name.trim().isEmpty()) {
			flag = false;
		}
		return flag;
	}

	public static boolean isNotEmpty(String name) {
		return !isEmpty(name);
	}

	public static boolean isEmpty(Integer value) {
		boolean flag = true;
		if (value != null && value > 0) {
			flag = false;
		}
		return flag;
	}

	public static boolean isEmpty(Double value) {
		boolean flag = true;
		if (value != null && value > 0D) {
			flag = false;
		}
		return flag;
	}

	public static String generateUniqueNumber() {
		int inc = 0;
		Long id = Long
				.parseLong(String.valueOf(System.currentTimeMillis()).substring(1, 10).concat(String.valueOf(inc)));
		inc = (inc + 1) % 10;
		return id.toString();
	}

	public static String generateTwoDegitsRandomNumber() {
		String no = String.valueOf(Math.round(Math.random() * 100));
		if (String.valueOf(no).length() != 2)
			no = generateTwoDegitsRandomNumber();
		return no;
	}

	public static String getRandomNumber(int length) {
		logger.trace("Random number length {}", length);
		return RandomStringUtils.randomNumeric(length);
	}

	public static Function<String, String> addUSDialingCode = phoneNumber -> Pattern.compile(REGEX).asPredicate()
			.test(phoneNumber) ? phoneNumber : USA_DIALING_CODE.concat(phoneNumber);

	/**
	 * Verify that the param value for all the items in the list is not empty.
	 */
	public static Consumer<List<StringParamNotNullValidator>> verifyAllStringParams = e -> {

		e.stream().forEach(param -> Optional.ofNullable(param.getParam()).map(paramValue -> {
			if (StringUtils.isEmpty(paramValue))
				throw new IllegalArgumentException(String.format("%s must not be empty", param.getParamName()));
			return true;
		}).orElseThrow(() -> new IllegalArgumentException(String.format("%s must not be empty", param.getParamName()))));
	};


	public static Supplier<Gson> gsonSupplier = Gson::new;

	public static Function<Object, String> toJsonFunction = gsonSupplier.get()::toJson;

	public static BiFunction<String, String, String> getProjectAttrName =
			(prefix, attributeName) -> prefix.concat("-").concat(attributeName);

	public static List<String> extractTokensByRegexp(String mainString, String regexp) {

		verifyAllStringParams.accept(Arrays.asList(StringParamNotNullValidator.builder().param(mainString).paramName("mainString").build(),
				StringParamNotNullValidator.builder().param(regexp).paramName("regExp").build()));

		if (finderEngine == null) {
			finderEngine = new FinderEngine();
		}
		List<Finder> finders = finderEngine.getFinders();
		finderEngine.getFinders().removeAll(finders);// concurrent access exception may happen
		finderEngine.add(new RegexFinder("Placeholder", regexp));
		return finderEngine.find(mainString);
	}

	public static Map<String, String> convertMapFromString(String text) {
		return Arrays.stream(text.split(","))
			.map(s -> s.split("="))
			.collect(Collectors.toMap(s -> s[0], s -> s[1]));
	}

	public static List<String> fuzzyIntentMatching(List<String> intentStrings, String toMatch) {

		List<String> matchedString= new ArrayList<>();
		List<Document> documentList = intentStrings.stream().map(intentString -> {
			return new Document.Builder(String.valueOf(intentString.hashCode()))
					.addElement(new Element.Builder<String>().setValue(intentString).setType(ElementType.NAME).createElement())
					.createDocument();
		}).collect(Collectors.toList());

		Document document=  new Document.Builder(toMatch)
				.addElement(new Element.Builder<String>().setValue(toMatch).setType(ElementType.NAME).createElement())
				.createDocument();

		MatchService matchService = new MatchService();
		Map<String, List<Match<Document>>> result = matchService.applyMatchByDocId(document,documentList);

		result.entrySet().forEach(entry -> {
			entry.getValue().forEach(match -> {
				match.getMatchedWith().getElements().forEach(element -> {
					matchedString.add(String.valueOf(element.getValue()));
				});
			});
		});

		return matchedString;
	}
	public static String replacePlaceHolderInString(String template, Map<String, Object> map) {
		Pattern pattern = Pattern.compile("\\{\\{(.+?)\\}");
		Matcher matcher = pattern.matcher(template);
		List<String> keyList = new ArrayList<>();
		while (matcher.find()) {
			keyList.add(matcher.group(1));
		}
		for (String key : keyList) {
			String value = (String) map.get(key);
			if (value != null)
				template = template.replaceFirst("\\{\\{(.+?)\\}\\}", value);
			else
				template = template.replaceFirst("\\{\\{(.+?)\\}\\}", "");
		}
		return template;
	}

	public static String replacePlaceHolderInSingleBracketString(String template, Map<String, Object> map) {
		Pattern pattern = Pattern.compile("\\{(.+?)\\}");
		Matcher matcher = pattern.matcher(template);
		ArrayList keyList = new ArrayList();

		while(matcher.find()) {
			keyList.add(matcher.group(1));
		}
		Iterator iterator = keyList.iterator();

		while(iterator.hasNext()) {
			String key = (String)iterator.next();
			String value = (String)map.get(key);
			if (value != null) {
				template = template.replaceFirst("\\{(.+?)\\}", value);
			} else {
				template = template.replaceFirst("\\{(.+?)\\}", "");
			}
		}
		return template;
	}

	private static String convertStringToCurrency(String amount,String numberSystem){

//		if(StringUtils.isEmpty(amount) || !StringUtils.isNumeric(amount))return amount;
		if(org.apache.commons.lang3.StringUtils.equalsIgnoreCase(numberSystem,"UK")){
			String[] formatSplit = splitToNChar(amount,3);
			CollectionUtils.reverseArray(formatSplit);
			return String.join(",",Arrays.asList(formatSplit));
		}

		return amount;
	}


	public static String convertStringToCurrency(String amount){
		String[] splits = org.apache.commons.lang3.StringUtils.split(amount,".");

		return convertStringToCurrency(splits[0],"UK").concat(splits.length==2?".".concat(splits[1]):"");
	}

	private static String[] splitToNChar(String text, int size) {
		List<String> parts = new ArrayList<>();
		int length = text.length();
		for (int i = length; i >0; i -= size) {
			parts.add(text.substring((i < size)?0:i - size, i));
		}
		return parts.toArray(new String[0]);
	}

	public static String html2text(String html) {
		return Jsoup.parse(html).text();
	}

	public static String getEighteenDigitRandomString() {
		char randomizedCharacter = (char) (new Random().nextInt(26) + 'a');
		String ran=randomizedCharacter+"";
		ran+=UUID.randomUUID().toString().substring(0,17);
		return ran;
	}



	public static  Map<String, String> areEqualKeyValues(Map<String, String> first, Map<String, String> second) {

		Map<String,String> updatedMap= new HashMap<>();

		if(first.entrySet().containsAll(second.entrySet())){
			return Collections.EMPTY_MAP;
		}

		else if(first.size()==second.size() && !first.values().equals(second.values())){
			for (Map.Entry<String, String> secondEntry: second.entrySet()) {
				for (Map.Entry<String, String> firstEntry: first.entrySet()) {
					if(secondEntry.getValue().equals(firstEntry.getValue())){
						break;
					}
					else if(first.containsValue(secondEntry.getValue())){
						break;
					}else {
						updatedMap.put(secondEntry.getKey(),secondEntry.getValue());
					}
				}
			}
			return updatedMap;
		}

		else if(second.size()>first.size() && !first.values().equals(second.values())){
			for (Map.Entry<String, String> secondEntry: second.entrySet()) {
				for (Map.Entry<String, String> firstEntry: first.entrySet()) {
					if(secondEntry.getValue().equals(firstEntry.getValue())){
						break;
					}
					else if(first.containsValue(secondEntry.getValue())) {
						break;
					}
					else {
						updatedMap.put(secondEntry.getKey(),secondEntry.getValue());
					}
				}
			}
			return updatedMap;
		}
		return null;
	}

	public static final Function<String, List<Map>> generateFunctionParamMapList = str -> {
		List gettingParamInfo = CommonUtils.gsonSupplier.get().fromJson(str,List.class);
		return (List<Map>) gettingParamInfo.stream().map(o->{
			return (Map)o;
		}).collect(Collectors.toList());
	};

	public static final BiFunction<List<Map>,String, Optional<String>> getKeyFromMapList = (list, name)-> Optional.ofNullable(list).map(m-> m.stream().filter(map -> {
		// filter from list of maps where the paramname equals to the name that is being passed
		String paramName = (String) map.get("attributeName");
		return org.apache.commons.lang3.StringUtils.isNotBlank(paramName) && paramName.equalsIgnoreCase(name);
	}).findFirst().map(map -> String.valueOf(map.get("value")))).orElseThrow(()-> new ApplicationException("Error"));


	public final static Supplier<String> UCBId = () -> {
		StringBuilder stbUCBID = new StringBuilder("UBCID").append(UUID
				.randomUUID().toString());
		if(stbUCBID.toString().contains("a5"))return null;
		return stbUCBID.toString();
	};

}
