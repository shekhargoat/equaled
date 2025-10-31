package com.equaled.controller;

import com.equaled.service.AWSUploadClient;
import com.equaled.service.IEqualEdServiceV2;
import com.equaled.service.IPassageV2;
import com.equaled.to.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@AllArgsConstructor
@RestController
@Api(value = "EqualED API's")
@RequestMapping("/v3")
public class EqualEdControllerV3 {

    private final IEqualEdServiceV2 service;
//    private final IPassageV2 passage;
//    private final AWSUploadClient awsUploadClient;


    @GetMapping("/user/email/{email}")
    @ApiOperation(value = "get User by username",
            notes = "API to get User by userId")
    public ResponseEntity<?> getUserByEmail(
            @ApiParam(value = "User id", required = true) @PathVariable("email") String email){
        log.debug(String.format("Request received : Users %s for GET /user/{userId} " +
                "for particular ", email));
        return ResponseEntity.ok(service.getUserByEmail(email));

    }

    @GetMapping("/subject/{name}")
    @ApiOperation(value = "Get subject ID by Name",
            notes = "Get subject ID by Name")
    public ResponseEntity<?> getSubjectIdByName(
            @ApiParam(value = "Exam id", required = true) @PathVariable("name") String subjectName){
        log.info(String.format("Request received : getting subject name for %s " +
                "for particular ", subjectName));
        CommonV2Response commonV2Response = new CommonV2Response();
        commonV2Response.setId(UUID.randomUUID().toString());
        commonV2Response.putField("Subject_Id",String.valueOf(service.getSubjectIdByName(subjectName)));
        return ResponseEntity.ok(commonV2Response);
    }


}