package com.equaled.service;

import com.equaled.to.CommonV2Response;

import java.util.List;
import java.util.Map;

public interface IPassageV2 {

    Map<String, List<CommonV2Response>> createPassage(Map<String, String> request);

    Map<String, List<CommonV2Response>> createPassageQuestions(Map<String, String> request);

    Map<String, List<CommonV2Response>> createPassageAnswer(Map<String, String> request);

    Map<String, List<CommonV2Response>> getPassageAnswersByStatus(String status);

    Map<String, List<CommonV2Response>> getPassageAnswersByStatusAndUserId(String status,Integer userId);

    Map<String, List<CommonV2Response>> getPassageAnswersByUserIdAndExamId(Integer userId, String examId);

    Map<String, List<CommonV2Response>> updatePassageAnswers(String answerSid, Map<String, String> requests);
}
