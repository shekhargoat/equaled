package com.equaled.controller;

import com.equaled.service.IEqualEdService;
import com.equaled.service.IEqualEdServiceV2;
import com.equaled.to.CommonV2Request;
import com.equaled.to.CreateProfileRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/questions/subject/{subjectId}/subcat/{subcat}")
    @ApiOperation(value = "get Questions by Subject and Sub category",
            notes = "API to get Questions by Subject and Sub category")
    public ResponseEntity<?> getQuestionsBySubAndSubcat(
            @ApiParam(value = "Year id", required = true) @PathVariable("yearId") Integer subjectId,
            @ApiParam(value = "Subject Name", required = true) @PathVariable("subjectName") String subcat){
        log.info(String.format("Request received : User %s for GET /questions/subject/{subjectId}/subcate/{subcat} " +
                "for particular ", subjectId,subcat));
        return ResponseEntity.ok(service.getQuestionsBySubAndSubcat(subjectId, subcat));
    }

    @GetMapping("/user/{userId}")
    @ApiOperation(value = "get User by userId",
            notes = "API to get User by userId")
    public ResponseEntity<?> getUserById(
            @ApiParam(value = "User id", required = true) @PathVariable("userId") Integer userId){
        log.info(String.format("Request received : Users %s for GET /user/{userId} " +
                "for particular ", userId));
        return ResponseEntity.ok(service.getUserById(userId));

    }

    @GetMapping("/setpractice/user/{userId}/practice/{practiceName}/{subjectName}")
    public ResponseEntity<?> getSetpracticeByUserIdSubjectName(@ApiParam(value = "User id", required = true)
                                                               @PathVariable("userId") Integer userId,
                                                               @PathVariable("practiceName") String practiceName,
                                                               @PathVariable("subjecName") String subjectName){

        log.info(String.format("Request received : Users %s for GET  " +
                "/setpractice/user/{userId}/practice/{practiceName}/{subjectName}" +
                "for particular ", userId,practiceName, subjectName));
        return ResponseEntity.ok(service.getSetpracticeByUserIdSubjectName(userId, practiceName, subjectName));
    }

    @PostMapping("/create/profile")
    public ResponseEntity<?> createProfile(@RequestBody CreateProfileRequest request){
        return ResponseEntity.ok(service.createProfile(request));
    }

    @PostMapping("/create/dashboard")
    public ResponseEntity<?> createDashboard(@RequestBody CommonV2Request request){
        return ResponseEntity.ok(service.createDashboard(request));
    }

    @PostMapping("/useranswers")
    public ResponseEntity<?> submitAnswer(@RequestBody CommonV2Request request){
        return ResponseEntity.ok(service.submitAnswer(request.getFields()));
    }


    @PostMapping("/improvement")
    public ResponseEntity<?> submitImprovement(@RequestBody CommonV2Request request){
        return ResponseEntity.ok(service.saveImprovement(request.getFields()));
    }

    @GetMapping("/setpractice/{sid}/completed")
    public ResponseEntity<?> markSetpracticeAsClose(@PathVariable String sid){
        service.markSetpracticeClose(sid);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/user/{userId}")
    @ApiOperation(value = "update user last login",
            notes = "API to update last login time for a User by userId")
    public ResponseEntity<?> updateLastLogin(
            @ApiParam(value = "User id", required = true) @PathVariable("userId") Integer userId,
            @RequestBody CommonV2Request request){
        log.info(String.format("Request received : Users %s for update last login /user/{userId} " +
                "for particular ", userId));
        return ResponseEntity.ok(service.updateUserLastLogin(userId,request));
    }
    @GetMapping("/improvement/user/{userId}")
    @ApiOperation(value = "get improvement by userId", notes = "API to get all improvements by UserId")
    public ResponseEntity<?> getImprovementByUser(
            @ApiParam(value = "User id", required = true) @PathVariable("userId") Integer userId) {
        log.info(String.format("Request received : Improvement %s for GET /improvement/user/{userId} for particular ", userId));
        return ResponseEntity.ok(service.getImprovementsByUser(userId));
    }

    @GetMapping("/improvement/exam/{examId}")
    @ApiOperation(value = "get improvement by examId", notes = "API to get all improvements by examId")
    public ResponseEntity<?> getImprovementByExamId(
            @ApiParam(value = "Exam id", required = true) @PathVariable("examId") String examId) {
        log.info(String.format("Request received : Improvement %s for GET /improvement/exam/{examId} for particular ", examId));
        return ResponseEntity.ok(service.getImprovementsByExam(examId));
    }

    @GetMapping("/improvement/user/{userId}/exam/{examId}")
    @ApiOperation(value = "get improvement by userId and examId", notes = "API to get all improvements for user by examId")
    public ResponseEntity<?> getImprovementForUserByExamId(
            @ApiParam(value = "User id", required = true) @PathVariable("userId") Integer userId,
            @ApiParam(value = "Exam id", required = true) @PathVariable("examId") String examId) {
        log.info(String.format("Request received : Improvement %s %s for GET /improvement/user/{userId}/exam/{examId} for particular ", userId,examId));
        return ResponseEntity.ok(service.getImprovementsByUserIdAndExam(userId,examId));
    }

    @GetMapping("/useranswers/exam/{examId}")
    @ApiOperation(value = "get user answers by examId", notes = "API to get all user answers by examId")
    public ResponseEntity<?> getUserAnswersByExamId(
            @ApiParam(value = "Exam id", required = true) @PathVariable("examId") String examId) {
        log.info(String.format("Request received : User answers %s for GET /user/answers/exam/{examId} for particular ", examId));
        return ResponseEntity.ok(service.getUserAnswersByExamId(examId));
    }

    @GetMapping("/test/subject/{subjectName}}/yeargroup/{yearGroupId}")
    @ApiOperation(value = "get user answers by examId", notes = "API to get all user answers by examId")
    public ResponseEntity<?> getTestBySubjectAndYearGroup(
            @ApiParam(value = "Exam id", required = true) @PathVariable("examId") String examId) {
        log.info(String.format("Request received : User answers %s for GET /user/answers/exam/{examId} for particular ", examId));
        return ResponseEntity.ok(service.getUserAnswersByExamId(examId));
    }

    @PostMapping("/practice/useranswers")
    public ResponseEntity<?> submitPracticeAnswer(@RequestBody CommonV2Request request){
        return ResponseEntity.ok(service.submitPracticeAnswer(request.getFields()));
    }

}
