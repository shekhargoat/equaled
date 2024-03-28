package com.equaled.service.impl;

import com.equaled.dozer.DozerUtils;
import com.equaled.entity.Dashboard;
import com.equaled.entity.Questions;
import com.equaled.entity.SubjectCategories;
import com.equaled.entity.Test;
import com.equaled.repository.*;
import com.equaled.service.IEqualEdServiceV2;
import com.equaled.to.CommonV2Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.ListUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class EqualEdServiceImplV2 implements IEqualEdServiceV2 {

    IDashboardRepository dashboardRepository;
    ISubjectCategoryRepository subjectCategoryRepository;
    ITestRepository testRepository;
    IQuestionRepository questionRepository;
    IUserRepository userRepository;

    DozerUtils mapper;

    @Override
    public Map<String,List<CommonV2Response>> getDashboardsByUser(Integer userId) {
        log.trace("Finding Dashboard By User: {}",userId);

        List<Dashboard> dashboardsByUserId = Optional.ofNullable(dashboardRepository.findDashboardsByUserId(userId))
                .orElse(ListUtils.EMPTY_LIST);
        log.debug("Dashboard fetched for User {} = {}",userId, dashboardsByUserId.size());

        List<CommonV2Response> commonV2Responses = dashboardsByUserId.stream().map(dashboard -> {
            CommonV2Response commonV2Response = new CommonV2Response();
            commonV2Response.setId(dashboard.getStringSid());
            commonV2Response.putField("exam_id", dashboard.getExamId());
            commonV2Response.putField("user_id",String.valueOf(dashboard.getUser().getId()));
            commonV2Response.putField("subject_name",dashboard.getSubject().getName());
            commonV2Response.putField("title", dashboard.getTitle());
            commonV2Response.putField("start_time",
                    LocalDateTime.ofInstant(Instant.ofEpochSecond(dashboard.getStartTime()),
                            ZoneId.of("UTC")).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            return commonV2Response;
        }).collect(Collectors.toList());


        return generateResponse(commonV2Responses);
    }

    @Override
    public Map<String,List<CommonV2Response>> getDashboardsByUser(String userId) {
        log.trace("Finding Dashboard By User: {}",userId);
        List<Dashboard> dashboardsByUserId = Optional.ofNullable(dashboardRepository.findDashboardsByUserSid(userId))
                .orElse(ListUtils.EMPTY_LIST);
        log.debug("Dashboard fetched for User {} = {}",userId, dashboardsByUserId.size());
        List<CommonV2Response> commonV2Responses = dashboardsByUserId.stream().map(dashboard -> {
            CommonV2Response commonV2Response = new CommonV2Response();
            commonV2Response.setId(dashboard.getStringSid());
            commonV2Response.putField("exam_id", dashboard.getExamId());
            commonV2Response.putField("user_id",String.valueOf(dashboard.getUser().getId()));
            commonV2Response.putField("subject_name",dashboard.getSubject().getName());
            commonV2Response.putField("title", dashboard.getTitle());
            commonV2Response.putField("start_time",
                    LocalDateTime.ofInstant(Instant.ofEpochSecond(dashboard.getStartTime()),
                            ZoneId.of("UTC")).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            return commonV2Response;
        }).collect(Collectors.toList());


        return generateResponse(commonV2Responses);    }

    @Override
    public Map<String,List<CommonV2Response>> getSubjectCategoriedByYear(Integer yearGroupId){
        log.trace("Finding Catgories by Year: {}",yearGroupId);
        List<SubjectCategories> categories = Optional.ofNullable(subjectCategoryRepository
                        .findSubjectsCategoriesByYrGroupId(yearGroupId))
                .orElse(ListUtils.EMPTY_LIST);
        log.debug("Categories fetched for year {} = {}",yearGroupId, categories.size());

        List<CommonV2Response> commonV2Responses = categories.stream().map(category -> {
            CommonV2Response commonV2Response = new CommonV2Response();
            commonV2Response.setId(category.getStringSid());
            commonV2Response.putField("year_group_id", String.valueOf(category.getYearGroup().getYear()));
            commonV2Response.putField("category",category.getSubject().getName());
            commonV2Response.putField("sub-Category", category.getSubCategory());
            commonV2Response.putField("sub-Category_1", category.getSubCategory1());
            return commonV2Response;
        }).collect(Collectors.toList());


        return generateResponse(commonV2Responses);
    }

    @Override
    public Map<String,List<CommonV2Response>> getTestsByYearAndSubjectName(Integer yearGroupId, String subjectName){
        log.trace("Finding Tests for Year {} and subjectName {}",yearGroupId, subjectName);
        List<Test> tests = Optional.ofNullable(testRepository.getTestByYearAndSubject(yearGroupId, subjectName))
                .orElse(ListUtils.EMPTY_LIST);
        log.debug("Tests fetched for year {} = {}",yearGroupId, tests.size());

        List<CommonV2Response> commonV2Responses = tests.stream().map(test -> {
            CommonV2Response commonV2Response = new CommonV2Response();
            commonV2Response.setId(test.getStringSid());
            commonV2Response.putField("test_id", String.valueOf(test.id));
            commonV2Response.putField("Test Description", test.getDescription());
            commonV2Response.putField("Time", String.valueOf(test.getTimeAllottedInMins()));
            commonV2Response.putField("Subject", test.getSubject().getName());
            commonV2Response.putField("Subject_id", String.valueOf(test.getSubject().id));
            commonV2Response.putField("year_group_id", String.valueOf(test.getYearGroupId().getYear()));
            commonV2Response.putField("No_Questions", String.valueOf(test.getNoOfQuestions()));
            commonV2Response.putField("Learn", String.valueOf(test.getTestType()));
            return commonV2Response;
        }).collect(Collectors.toList());

        return generateResponse(commonV2Responses);
    }

    @Override
    public Map<String,List<CommonV2Response>> getQuestionsBySubAndSubcat(Integer subjectId, String subcatName){
        log.trace("Finding Questions for subject {} and subcat Name {}",subjectId, subcatName);
        List<Questions> questions = Optional.ofNullable(questionRepository
                .getQuestionsBySubAndSubcat(subjectId, subcatName)).orElse(ListUtils.EMPTY_LIST);
        log.debug("Finding Questions for subject {} and subcat Name {} = {}",subjectId, subcatName, questions.size());
        List<CommonV2Response> commonV2Responses = questions.stream().map(question -> {
            CommonV2Response commonV2Response = new CommonV2Response();
            commonV2Response.setId(question.getStringSid());
            commonV2Response.putField("question_id", String.valueOf(question.id));
            commonV2Response.putField("Text", question.getQuestion());
            commonV2Response.putField("Subject_id", String.valueOf(question.getSubject().id));
            commonV2Response.putField("year_group_id", String.valueOf(question.getYearGroupId().id));
            commonV2Response.putField("Difficulty_level", String.valueOf(question.getDifficulty()));
            commonV2Response.putField("category", String.valueOf(question.getCategory()));
            commonV2Response.putField("sub_category", String.valueOf(question.getSubCategory()));
            commonV2Response.putField("Correct_option", String.valueOf(question.getCorrectOption()));
            commonV2Response.putField("Learn", String.valueOf(question.getLearn()));

            return commonV2Response;
        }).collect(Collectors.toList());

        return generateResponse(commonV2Responses);
    }

    @Override
    public Map<String,List<CommonV2Response>> getUserById(Integer id){
        log.trace("Finding user by id : {}",id);
        List<CommonV2Response> commonV2Responses = new ArrayList<>();
        userRepository.findById(id).ifPresent(users -> {
            CommonV2Response commonV2Response = new CommonV2Response();
            commonV2Response.setId(users.getStringSid());
            commonV2Response.putField("Username", users.getUsername());
            commonV2Response.putField("Email", users.getEmail());
            commonV2Response.putField("Password", users.getPassword());
            commonV2Response.putField("User_id", String.valueOf(users.id));
            commonV2Response.putField("year_group_id", String.valueOf(users.getYearGroup().getId()));
            commonV2Response.putField("role", users.getRole().name());
            commonV2Response.putField("lastlogin", LocalDateTime.ofInstant(Instant.ofEpochSecond(users.getLastLogin()),
                    ZoneId.of("UTC")).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            commonV2Response.putField("Username", users.getUsername());
            commonV2Responses.add(commonV2Response);
        });
        return generateResponse(commonV2Responses);
    }


    public Map<String,List<CommonV2Response>> generateResponse(List<CommonV2Response> commonV2Responses){
        Map<String,List<CommonV2Response>> response = new HashMap<>();
        response.put("records",commonV2Responses);
        return response;
    }
}
