package com.equaled.controller;

import com.equaled.service.IEqualEdServiceV2;
import com.equaled.to.CommonV2Request;
import com.equaled.to.CreateProfileRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
        log.debug(String.format("Request received : User %s for GET /dashboard/user/{userId} for particular ", userId));
        return ResponseEntity.ok(service.getDashboardsByUser(userId));
    }

    @GetMapping("/category/year/{yearId}")
    public ResponseEntity<?> getSubjectCategoryByYearGroup(
            @ApiParam(value = "Year id", required = true) @PathVariable("yearId") Integer yearId){
        log.debug(String.format("Request received : User %s for GET /category/year/{yearId} for particular ", yearId));
        return ResponseEntity.ok(service.getSubjectCategoriedByYear(yearId));
    }

    @GetMapping("/tests/year/{yearId}/subject/{subjectName}")
    public ResponseEntity<?> getTestsByYearGroupAndSubject(
            @ApiParam(value = "Year id", required = true) @PathVariable("yearId") Integer yearId,
            @ApiParam(value = "Subject Name", required = true) @PathVariable("subjectName") String subjectName){
        log.debug(String.format("Request received : User %s for GET /tests/year/{yearId}/subject/{subjectName} " +
                "for particular ", yearId,subjectName));
        return ResponseEntity.ok(service.getTestsByYearAndSubjectName(yearId,subjectName));
    }

    @GetMapping("/questions/subject/{subjectId}/subcat/{subcat}")
    @ApiOperation(value = "get Questions by Subject and Sub category",
            notes = "API to get Questions by Subject and Sub category")
    public ResponseEntity<?> getQuestionsBySubAndSubcat(
            @ApiParam(value = "Year id", required = true) @PathVariable("subjectId") Integer subjectId,
            @ApiParam(value = "Subject Name", required = true) @PathVariable("subcat") String subcat){
        log.debug(String.format("Request received : User %s for GET /questions/subject/{subjectId}/subcat/{subcat} " +
                "for particular ", subjectId,subcat));
        return ResponseEntity.ok(service.getQuestionsBySubAndSubcat(subjectId, subcat));
    }

    @GetMapping("/user/{userId}")
    @ApiOperation(value = "get User by userId",
            notes = "API to get User by userId")
    public ResponseEntity<?> getUserById(
            @ApiParam(value = "User id", required = true) @PathVariable("userId") Integer userId){
        log.debug(String.format("Request received : Users %s for GET /user/{userId} " +
                "for particular ", userId));
        return ResponseEntity.ok(service.getUserById(userId));

    }

    @GetMapping("/setpractice/user/{userId}/practice/{practiceName}/{subjectName}")
    public ResponseEntity<?> getSetpracticeByUserIdSubjectName(@ApiParam(value = "User id", required = true)
                                                               @PathVariable("userId") Integer userId,
                                                               @PathVariable("practiceName") String practiceName,
                                                               @PathVariable("subjectName") String subjectName){

        log.debug(String.format("Request received : Users %s for GET  " +
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
        log.debug(String.format("Request received : Users %s for update last login /user/{userId} " +
                "for particular ", userId));
        return ResponseEntity.ok(service.updateUserLastLogin(userId,request));
    }
    @GetMapping("/improvement/user/{userId}")
    @ApiOperation(value = "get improvement by userId", notes = "API to get all improvements by UserId")
    public ResponseEntity<?> getImprovementByUser(
            @ApiParam(value = "User id", required = true) @PathVariable("userId") Integer userId) {
        log.debug(String.format("Request received : Improvement %s for GET /improvement/user/{userId} for particular ", userId));
        return ResponseEntity.ok(service.getImprovementsByUser(userId));
    }

    @GetMapping("/improvement/exam/{examId}")
    @ApiOperation(value = "get improvement by examId", notes = "API to get all improvements by examId")
    public ResponseEntity<?> getImprovementByExamId(
            @ApiParam(value = "Exam id", required = true) @PathVariable("examId") String examId) {
        log.debug(String.format("Request received : Improvement %s for GET /improvement/exam/{examId} for particular ", examId));
        return ResponseEntity.ok(service.getImprovementsByExam(examId));
    }

    @GetMapping("/improvement/user/{userId}/exam/{examId}")
    @ApiOperation(value = "get improvement by userId and examId", notes = "API to get all improvements for user by examId")
    public ResponseEntity<?> getImprovementForUserByExamId(
            @ApiParam(value = "User id", required = true) @PathVariable("userId") Integer userId,
            @ApiParam(value = "Exam id", required = true) @PathVariable("examId") String examId) {
        log.debug(String.format("Request received : Improvement %s %s for GET /improvement/user/{userId}/exam/{examId} for particular ", userId,examId));
        return ResponseEntity.ok(service.getImprovementsByUserIdAndExam(userId,examId));
    }

    @GetMapping("/useranswers/exam/{examId}")
    @ApiOperation(value = "get user answers by examId", notes = "API to get all user answers by examId")
    public ResponseEntity<?> getUserAnswersByExamId(
            @ApiParam(value = "Exam id", required = true) @PathVariable("examId") String examId) {
        log.debug(String.format("Request received : User answers %s for GET /user/answers/exam/{examId} for particular ", examId));
        return ResponseEntity.ok(service.getUserAnswersByExamId(examId));
    }

    @GetMapping("/test/subject/{subjectName}/yeargroup/{yearGroupId}")
    @ApiOperation(value = "get user answers by examId", notes = "API to get all user answers by examId")
    public ResponseEntity<?> getTestBySubjectAndYearGroup(
            @PathVariable("subjectName") String subjectName, @PathVariable("yearGroupId") Integer yearGroupId) {
        log.debug(String.format("Request received : User answers %s for GET /test/subject/{subjectName}/yeargroup/{yearGroupId}" +
                " for particular ", subjectName));
        return ResponseEntity.ok(service.getTestsByYearAndSubjectName(yearGroupId, subjectName));
    }

    @PostMapping("/practice/useranswers")
    public ResponseEntity<?> submitPracticeAnswer(@RequestBody CommonV2Request request){
        return ResponseEntity.ok(service.submitPracticeAnswer(request.getFields()));
    }
    @GetMapping("/questions/subject/{subjectId}/learn/{learnType}")
    @ApiOperation(value = "get Questions by Subject and learn type",
            notes = "API to get Questions by Subject and learnType(LEARN, TEST)")
    public ResponseEntity<?> getQuestionsBySubAndLearnType(
            @ApiParam(value = "Subject id", required = true) @PathVariable("subjectId") Integer subjectId,
            @ApiParam(value = "Learn type", required = true) @PathVariable("learnType") String learnType){
        log.debug(String.format("Request received : User %s for GET /questions/subject/{subjectId}/learn/{learnType} " +
                "for particular ", subjectId,learnType));
        return ResponseEntity.ok(service.getQuestionsBySubAndLearnType(subjectId, learnType));
    }

    @GetMapping("/setpractice/user/{userId}/status/{status}")
    public ResponseEntity<?> getSetpracticeByUserIdAndStatus(@ApiParam(value = "User id", required = true)
                                                             @PathVariable("userId") Integer userId,
                                                             @PathVariable("status") String status){

        log.debug(String.format("Request received : Users %s for GET  " +
                "/setpractice/user/{userId}/status/{status}/" +
                "for particular ", userId,status));
        return ResponseEntity.ok(service.getSetpracticeByUserIdAndStatus(userId, status));
    }

    @GetMapping("/tests/year/{yearGroup}")
    public ResponseEntity<?> getTestsByYearGroup(
            @ApiParam(value = "Year id", required = true) @PathVariable("yearGroup") Integer yearGroup){
        log.debug(String.format("Request received : Tests %s for GET  " +
                "/tests/year/{yearGroup}" +
                "for particular ", yearGroup));
        return ResponseEntity.ok(service.getTestsByYearGroup(yearGroup));
    }

    @GetMapping("/user/name/{username}")
    @ApiOperation(value = "get User by username",
            notes = "API to get User by userId")
    public ResponseEntity<?> getUserByUsername(
            @ApiParam(value = "User id", required = true) @PathVariable("username") String userName){
        log.debug(String.format("Request received : Users %s for GET /user/{userId} " +
                "for particular ", userName));
        return ResponseEntity.ok(service.getUserByUserName(userName));

    }


    @GetMapping("/questions/subject/{subjectName}/yeargroup/{yearGroupId}")
    @ApiOperation(value = "get Questions by Subject name and year group id",
            notes = "API to get Questions by Subject name and year group id")
    public ResponseEntity<?> getQuestionsBySubAndYearGroupId(
            @ApiParam(value = "Subject Name", required = true) @PathVariable("subjectName") String subjectName,
            @ApiParam(value = "Learn type", required = true) @PathVariable("yearGroupId") Integer yearGroupId){
        log.debug(String.format("Request received : User %s for GET /questions/subject/{subjectName}/yeargroup/{yearGroupId} " +
                "for particular ", subjectName,yearGroupId));
        return ResponseEntity.ok(service.getQuestionsBySubAndYearGroup(subjectName, yearGroupId));
    }

    @GetMapping("/useranswers/user/{userId}/exam/{examId}")
    @ApiOperation(value = "get user answers by userId and examId", notes = "API to get all user answers by userId and examId")
    public ResponseEntity<?> getUserAnswersByUserAndExamId(
            @ApiParam(value = "User id", required = true) @PathVariable("userId") Integer userId,
            @ApiParam(value = "Exam id", required = true) @PathVariable("examId") String examId) {
        log.debug(String.format("Request received : User answers %s for GET /user/answers/user/{userId}/exam/{examId} for particular ", userId,examId));
        return ResponseEntity.ok(service.getUserAnswersByUserAndExamId(userId,examId));
    }

    @GetMapping("/user/sid/{sid}")
    @ApiOperation(value = "get User by sid",
            notes = "API to get User by sid")
    public ResponseEntity<?> getUserBySid(
            @ApiParam(value = "User sid", required = true) @PathVariable("sid") String sid){
        log.debug(String.format("Request received : Users %s for GET /user/sid/{sid} " +
                "for particular ", sid));
        return ResponseEntity.ok(service.getUserBySid(sid));

    }

    @PostMapping("/setpractice")
    public ResponseEntity<?> submitSetpractice(@RequestBody Map<String, List<CommonV2Request>> request){
        return ResponseEntity.ok(service.submitSetpractice(request));
    }

    @GetMapping("/user/email/{email}")
    @ApiOperation(value = "get User by username",
            notes = "API to get User by userId")
    public ResponseEntity<?> getUserByEmail(
            @ApiParam(value = "User id", required = true) @PathVariable("email") String email){
        log.debug(String.format("Request received : Users %s for GET /user/{userId} " +
                "for particular ", email));
        return ResponseEntity.ok(service.getUserByEmail(email));

    }

    @GetMapping("/dashboard/suggested/{userId}")
    public ResponseEntity<?> getSuggestedDashboardByUserId(@PathVariable("userId") Integer userId){
        return ResponseEntity.ok(service.getSuggedtedDashboardsByUser(userId));
    }

    @GetMapping("/useranswers/user/{userId}")
    public ResponseEntity<?> getUserAnswersByUserId(@PathVariable("userId") Integer userId){
        return ResponseEntity.ok(service.getUserAnswersByUserId(userId));
    }

    @GetMapping("/practice/answers/{userId}")
    public ResponseEntity<?> getPracticeAnswersByUserId(@PathVariable("userId")Integer userId){
        return ResponseEntity.ok(service.getPracticeAnswersByUserId(userId));
    }

    @GetMapping("/improvement/user/{userId}/nonweak")
    public ResponseEntity<?> getImprovementByUserNonWeakCat(
            @ApiParam(value = "User id", required = true) @PathVariable("userId") Integer userId) {
        log.debug(String.format("Request received : Improvement %s for GET /improvement/user/{userId} for particular ", userId));
        return ResponseEntity.ok(service.getNonWeakCategoryImprovementsByUserId(userId));
    }

    @GetMapping("/improvement/user/{userId}/nonstrong")
    public ResponseEntity<?> getImprovementByUserNonStrongCat(
            @ApiParam(value = "User id", required = true) @PathVariable("userId") Integer userId) {
        log.debug(String.format("Request received : Improvement %s for GET /improvement/user/{userId} for particular ", userId));
        return ResponseEntity.ok(service.getNonStrongCategoryImprovementsByUserId(userId));
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity.ok(service.getAllUsers());
    }

    @PostMapping("/users/teacher/{teacherId}/students")
    public ResponseEntity<?> updateTeacherRelation(@PathVariable("teacherId") Integer teacher_id,@RequestBody List<Integer> students){
        return ResponseEntity.ok(service.addStudentsForTeacher(teacher_id, students));
    }

    @GetMapping("/questions/users/{userId}")
    @ApiOperation(value = "get Questions by user id",
            notes = "API to get Questions by user id")
    public ResponseEntity<?> getQuestionsByUser(
            @ApiParam(value = "User id", required = true) @PathVariable("userId") Integer userId){
        log.info(String.format("Request received : User %s for GET /questions/users/{userId} " +
                "for particular ", userId));
        return ResponseEntity.ok(service.getQuestionsByUser(userId));
    }

    @PostMapping("/questions/subcategories")
    @ApiOperation(value = "get Questions by user id",
            notes = "API to get Questions by user id")
    public ResponseEntity<?> getQuestionsBySubcategories(@RequestBody List<String> subcategories){
        log.info(String.format("Request received : User %s for GET /questions/subcategories " +
                "for subcategories {}", subcategories));
        return ResponseEntity.ok(service.getQuestionsBySubcategories(subcategories));
    }


}
