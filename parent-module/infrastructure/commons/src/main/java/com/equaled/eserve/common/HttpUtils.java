/*
 *  CallComm Technologies, Inc. Confidential
 *
 *  Copyright (c) CallComm Technologies, Inc. 2020. All rights reserved
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

import com.equaled.eserve.common.exception.ApplicationException;
import com.equaled.eserve.common.exception.CommonHttpException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.util.*;

/*
 * This has utility methods to handle http functions
 *
 */
@Slf4j
public final class HttpUtils {

    public static String postJsonUrl(Map body, String postUrl, Map<String,String> headers){
        try(CloseableHttpClient client = HttpClients.createDefault()){
            CommonUtils.verifyAllStringParams.accept(Collections.singletonList(StringParamNotNullValidator.builder().param(postUrl).paramName("Post URL").build()));
            HttpPost httpPost = new HttpPost(postUrl);
            return getString(body,headers,client,httpPost);
        }catch (Exception e){
            e.printStackTrace();
            throw new ApplicationException(e);
        }
    }
    public static String postJsonUrlWithListBody(List body, String postUrl, Map<String,String> headers){
        try(CloseableHttpClient client = HttpClients.createDefault()){
            CommonUtils.verifyAllStringParams.accept(Collections.singletonList(StringParamNotNullValidator.builder().param(postUrl).paramName("Post URL").build()));
            HttpPost httpPost = new HttpPost(postUrl);
            return getStringWithList(body,headers,client,httpPost);
        }catch (Exception e){
            e.printStackTrace();
            throw new ApplicationException(e);
        }
    }

    public static String deleteJsonUrl(Map body, String deleteUrl, Map<String,String> headers){
        try(CloseableHttpClient client = HttpClients.createDefault()){
            CommonUtils.verifyAllStringParams.accept(Collections.singletonList(StringParamNotNullValidator.builder().param(deleteUrl).paramName("Delete URL").build()));
            HttpDelete httpDelete = new HttpDelete(deleteUrl);
            return  getDeleteResponse(body,headers,client,httpDelete);
        }catch (Exception e){
            throw new ApplicationException(e);
        }
    }



    public static String getJsonUrl(String getUrl, Map<String,String> headers){
        try(CloseableHttpClient client = HttpClients.createDefault()){

            CommonUtils.verifyAllStringParams.accept(Collections.singletonList(StringParamNotNullValidator.builder()
                    .param(getUrl).paramName("Post URL").build()));
            HttpGet httpGet = new HttpGet(getUrl);
            Optional.ofNullable(headers).filter(m-> !m.isEmpty())
                    .ifPresent(maps-> maps.forEach(httpGet::addHeader));
            //adding default content type header
            httpGet.addHeader("Content-type", "application/json");
            try (CloseableHttpResponse response = client.execute(httpGet)) {
                HttpEntity entityResponse;
                StatusLine sl= response.getStatusLine();
                // TODO code to handle success and failure responses
                switch (sl.getStatusCode()){
                    case 200:
                    case 201:
                    case 202:
                    case 203:
                    case 204:
                    case 205:
                    case 206:
                        entityResponse = response.getEntity();
                        return Optional.ofNullable(entityResponse).map(er -> {
                            try{ return EntityUtils.toString(er); }catch(Exception e){ return null; }
                        }).orElseThrow(ApplicationException::new);
                    default:
                        throw CommonHttpException.builder().responseCode(sl.getStatusCode()).responseMessage(sl.getReasonPhrase()).build();
                }

            } catch (Exception e) {
                throw new ApplicationException(e);
            }
        }catch (Exception e){
            throw new ApplicationException(e);
        }
    }

    public static String putJsonUrl(Map body, String putUrl, Map<String,String> headers){
        try(CloseableHttpClient client = HttpClients.createDefault()){

            CommonUtils.verifyAllStringParams.accept(Collections.singletonList(StringParamNotNullValidator.builder().param(putUrl).paramName("Post URL").build()));
            HttpPut httpPost = new HttpPut(putUrl);
            return getString(body, headers, client, httpPost);
        }catch (Exception e){
            throw new ApplicationException(e);
        }
    }
    private static String getStringWithList(List body, Map<String, String> headers, CloseableHttpClient client, HttpEntityEnclosingRequestBase httpPost) throws UnsupportedEncodingException {
        if(null!= body)
            httpPost.setEntity(new StringEntity(CommonUtils.gsonSupplier.get().toJson(body)));
        //adding default content type header
        httpPost.addHeader("Content-type", "application/json");
        Optional.ofNullable(headers).filter(MapUtils::isNotEmpty)
                .ifPresent(maps-> maps.forEach(httpPost::addHeader));
        try (CloseableHttpResponse response = client.execute(httpPost)) {
            HttpEntity entityResponse;
            StatusLine sl= response.getStatusLine();
            // TODO code to handle success and failure responses
            switch (sl.getStatusCode()){
                case 200:
                case 201:
                case 202:
                case 203:
                case 204:
                case 205:
                case 206:
                    entityResponse = response.getEntity();
                    return Optional.ofNullable(entityResponse).map(er -> {
                        try{ return EntityUtils.toString(er); }catch(Exception e){ return null; }
                    }).orElseThrow(ApplicationException::new);
                // To handle shopify errors
                case 422:
                case 404:
                    entityResponse = response.getEntity();
                    return  EntityUtils.toString(entityResponse);
                default:
                    throw CommonHttpException.builder().responseCode(sl.getStatusCode()).responseMessage(sl.getReasonPhrase()).build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
    }

    private static String getString(Map body, Map<String, String> headers, CloseableHttpClient client,
                                    HttpEntityEnclosingRequestBase httpPost) throws UnsupportedEncodingException {
        if(null!= body)
            httpPost.setEntity(new StringEntity(CommonUtils.gsonSupplier.get().toJson(body)));
        //adding default content type header
        httpPost.addHeader("Content-type", "application/json; charset=UTF-8");
        Optional.ofNullable(headers).filter(MapUtils::isNotEmpty)
                .ifPresent(maps-> maps.forEach(httpPost::addHeader));
        try (CloseableHttpResponse response = client.execute(httpPost)) {
            HttpEntity entityResponse;
            StatusLine sl= response.getStatusLine();
            // TODO code to handle success and failure responses
            switch (sl.getStatusCode()){
                case 200:
                case 201:
                case 202:
                case 203:
                case 204:
                case 205:
                case 206:
                    entityResponse = response.getEntity();
                    return Optional.ofNullable(entityResponse).map(er -> {
                        try{
                            return Optional.ofNullable(EntityUtils.toString(er)).filter(StringUtils::isNotEmpty)
                                    .orElse(String.valueOf(sl.getStatusCode()));
                        } catch(Exception e){ return null; }
                    }).orElseThrow(ApplicationException::new);
                // To handle shopify errors
                case 422:
                case 404:
                    entityResponse = response.getEntity();
                    return  EntityUtils.toString(entityResponse);
                case 500:
                case 504:
                    return String.valueOf(sl.getStatusCode());
                default:
                    log.error("Status code response: {} and reasonPhrase {}",sl.getStatusCode(),sl.getReasonPhrase());
                    throw CommonHttpException.builder().responseCode(sl.getStatusCode()).responseMessage(sl.getReasonPhrase()).build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
    }


    private static String getDeleteResponse(Map body, Map<String, String> headers, CloseableHttpClient client, HttpDelete httpDelete) throws UnsupportedEncodingException
    {
            Optional.ofNullable(headers).filter(m-> !m.isEmpty())
                .ifPresent(maps-> maps.forEach(httpDelete::addHeader));
        //adding default content type header
        httpDelete.addHeader("Content-type", "application/json");
        try (CloseableHttpResponse response = client.execute(httpDelete)) {
            HttpEntity entityResponse;
            StatusLine sl= response.getStatusLine();
            // TODO code to handle success and failure responses
            switch (sl.getStatusCode()){
                case 200:
                case 201:
                case 202:
                case 203:
                case 204:
                case 205:
                case 206:
                    entityResponse = response.getEntity();
                    return Optional.ofNullable(entityResponse).map(er -> {
                        try{ return EntityUtils.toString(er); }catch(Exception e){ return null; }
                    }).orElseThrow(ApplicationException::new);
                // To handle shopify errors
                case 422:
                case 404:
                    entityResponse = response.getEntity();
                    return  EntityUtils.toString(entityResponse);
                default:
                    throw CommonHttpException.builder().responseCode(sl.getStatusCode()).responseMessage(sl.getReasonPhrase()).build();
            }

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    // Handle Shopify bulk data sync
    public static Map<String,Object> processShopifyBulkProcess(String getUrl, Map<String,String> headers)
    {
        try(CloseableHttpClient client = HttpClients.createDefault()){
            CommonUtils.verifyAllStringParams.accept(Collections.singletonList(StringParamNotNullValidator.builder().param(getUrl).paramName("Post URL").build()));
            HttpGet httpGet = new HttpGet(getUrl);
            Optional.ofNullable(headers).filter(m-> !m.isEmpty())
                    .ifPresent(maps-> maps.forEach(httpGet::addHeader));
            //adding default content type header
            httpGet.addHeader("Content-type", "application/json");
            try (CloseableHttpResponse response = client.execute(httpGet)) {
                StatusLine sl= response.getStatusLine();
                switch (sl.getStatusCode()){
                    case 200:
                    case 201:
                    case 202:
                    case 203:
                    case 204:
                    case 205:
                    case 206:
                        HttpEntity entityResponse = response.getEntity();
                        Header headerResponse  = response.getFirstHeader("Link");
                        Map<String,Object> shopifyResponseMap = new HashMap<>();
                        if(headerResponse != null){
                            shopifyResponseMap.put("link",headerResponse.getValue());
                        }
                        String shopifyResponse = Optional.ofNullable(entityResponse).map(er -> {
                            try{ return EntityUtils.toString(er); }catch(Exception e){ return null; }
                        }).orElseThrow(ApplicationException::new);
                        try {
                            shopifyResponseMap.put("response",new ObjectMapper().readValue(shopifyResponse, Map.class));
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        return shopifyResponseMap;
                    default:
                        throw CommonHttpException.builder().responseCode(sl.getStatusCode()).responseMessage(sl.getReasonPhrase()).build();
                }
            } catch (Exception e) {
                throw new ApplicationException(e);
            }
        }catch (Exception e){
            throw new ApplicationException(e);
        }
    }

}
