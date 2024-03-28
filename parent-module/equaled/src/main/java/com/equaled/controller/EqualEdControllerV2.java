package com.equaled.controller;

import com.equaled.service.IEqualEdService;
import com.equaled.service.IEqualEdServiceV2;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@Api(value = "EqualED API's")
@RequestMapping("/v2")
public class EqualEdControllerV2 {

    private final IEqualEdServiceV2 service;

    @GetMapping("/dashboard/user/{userId}")
    @ApiOperation(value = "get dashboard by userId", notes = "API to get all dashboards by UserId")
    public ResponseEntity<?> getDashboardByUser(
            @ApiParam(value = "User id", required = true) @PathVariable("userId") Integer userId) {
        log.info(String.format("Request received : User %s for GET /dashboard/user/{userId} for particular ", userId));
        return ResponseEntity.ok(service.getDashboardsByUser(userId));
    }

    @GetMapping("/category/year/{yearId}")
    public ResponseEntity<?> getSubjectCategoryByYearGroup(
            @ApiParam(value = "Year id", required = true) @PathVariable("yearId") Integer yearId){
        log.info(String.format("Request received : User %s for GET /category/year/{yearId} for particular ", yearId));
        return ResponseEntity.ok(service.getSubjectCategoriedByYear(yearId));
    }

    @GetMapping("/tests/year/{yearId}/subject/{subjectName}")
    public ResponseEntity<?> getTestsByYearGroupAndSubject(
            @ApiParam(value = "Year id", required = true) @PathVariable("yearId") Integer yearId,
            @ApiParam(value = "Subject Name", required = true) @PathVariable("subjectName") String subjectName){
        log.info(String.format("Request received : User %s for GET /tests/year/{yearId}/subject/{subjectName} " +
                "for particular ", yearId,subjectName));
        return ResponseEntity.ok(service.getTestsByYearAndSubjectName(yearId,subjectName));
    }
}
