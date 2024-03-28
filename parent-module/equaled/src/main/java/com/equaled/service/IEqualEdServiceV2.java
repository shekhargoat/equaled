package com.equaled.service;

import com.equaled.to.CommonV2Response;
import com.equaled.to.DashboardTO;

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
}
