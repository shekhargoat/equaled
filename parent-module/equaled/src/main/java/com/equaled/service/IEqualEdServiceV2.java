package com.equaled.service;

import com.equaled.to.CommonV2Request;
import com.equaled.to.CommonV2Response;
import com.equaled.to.CreateProfileRequest;
import com.equaled.to.UserAnswerAITO;

import java.util.List;
import java.util.Map;

public interface IEqualEdServiceV2 {

    Map<String,List<CommonV2Response>> getDashboardsByUser(Integer userId);
    Map<String,List<CommonV2Response>> getDashboardsByUser(String userId);

    Map<String,List<CommonV2Response>> getSubjectCategoriedByYear(Integer yearGroupId);

    Map<String,List<CommonV2Response>> getTestsByYearAndSubjectName(Integer yearGroupId, String subjectName);

    Map<String,List<CommonV2Response>> getQuestionsBySubAndSubcat(Integer subjectId, String subcatName);

    Map<String,List<CommonV2Response>> getUserById(Integer id);

    Map<String, List<CommonV2Response>> getSetpracticeByUserIdSubjectName(Integer userId, String practiceName, String subjectName);

    Map<String, List<CommonV2Response>> createProfile(CreateProfileRequest request);

    Map<String, List<CommonV2Response>> createDashboard(CommonV2Request request);

    Map<String, List<CommonV2Response>> submitAnswer(Map<String, String> answer);

    CommonV2Response saveImprovement(Map<String, String> improvement);

    void markSetpracticeClose(String setPracticeSid);

    CommonV2Response updateUserLastLogin(Integer userId, CommonV2Request request);

    Map<String,List<CommonV2Response>> getImprovementsByUser(Integer userId);

    Map<String,List<CommonV2Response>> getImprovementsByExam(String examId);

    Map<String, List<CommonV2Response>> getImprovementsByUserIdAndExam(Integer userId,String examId);

    Map<String, List<CommonV2Response>> getUserAnswersByExamId(String examId);

    Map<String, List<CommonV2Response>> submitPracticeAnswer(Map<String, String> answer);

    Map<String,List<CommonV2Response>> getQuestionsBySubAndLearnType(Integer subjectId, String learnType);

    Map<String, List<CommonV2Response>> getSetpracticeByUserIdAndStatus(Integer userId, String status);

    Map<String,List<CommonV2Response>> getTestsByYearGroup(Integer yearGroupId);

    Map<String,List<CommonV2Response>> getUserByUserName(String username);

    Map<String, List<CommonV2Response>> getQuestionsBySubAndYearGroup(String subjectName, Integer yearGroup);

    Map<String, List<CommonV2Response>> getUserAnswersByUserAndExamId(Integer userId,String examId);

    Map<String,List<CommonV2Response>> getUserBySid(String sid);

    Map<String, List<CommonV2Response>> submitSetpractice(Map<String, List<CommonV2Request>> practiceRequest);

    Map<String,List<CommonV2Response>> getUserByEmail(String email);

    Map<String,List<CommonV2Response>> getSuggedtedDashboardsByUser(Integer userId);

    Map<String, List<CommonV2Response>> getUserAnswersByUserId(Integer userId);

    Map<String, List<CommonV2Response>> getPracticeAnswersByUserId(Integer userId);

    Map<String, List<CommonV2Response>> getNonWeakCategoryImprovementsByUserId(Integer userId);

    Map<String, List<CommonV2Response>> getNonStrongCategoryImprovementsByUserId(Integer userId);

    Map<String,List<CommonV2Response>> getAllUsers();

    Map<String, List<CommonV2Response>> addStudentsForTeacher(Integer teacherId, List<Integer> students);

    Map<String, List<CommonV2Response>> getQuestionsByUser(Integer userId);

    Map<String, List<CommonV2Response>> getQuestionsBySubcategories(List<String> subcategories);

    UserAnswerAITO submitUserAnswerAI(UserAnswerAITO request);

    Map<String,Object> getExamScore(Integer examScoreId);
}
