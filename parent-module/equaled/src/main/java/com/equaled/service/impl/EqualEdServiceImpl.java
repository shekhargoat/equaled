package com.equaled.service.impl;

import com.equaled.dozer.DozerUtils;
import com.equaled.entity.*;
import com.equaled.repository.*;
import com.equaled.service.IEqualEdService;
import com.equaled.to.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.ListUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class EqualEdServiceImpl implements IEqualEdService {

    IDashboardRepository dashboardRepository;
    ISubjectCategoryRepository subjectCategoryRepository;
    ITestRepository testRepository;
    IQuestionRepository questionRepository;
    IUserRepository userRepository;
    ISetPracticeRepository setPracticeRepository;

    DozerUtils mapper;

    @Override
    public List<DashboardTO> getDashboardsByUser(Integer userId) {
        log.trace("Finding Dashboard By User: {}",userId);

        List<Dashboard> dashboardsByUserId = Optional.ofNullable(dashboardRepository.findDashboardsByUserId(userId))
                .orElse(ListUtils.EMPTY_LIST);
        log.debug("Dashboard fetched for User {} = {}",userId, dashboardsByUserId.size());
        return mapper.convertList(dashboardsByUserId, DashboardTO.class);
    }

    @Override
    public List<DashboardTO> getDashboardsByUser(String userId) {
        log.trace("Finding Dashboard By User: {}",userId);
        List<Dashboard> dashboardsByUserId = Optional.ofNullable(dashboardRepository.findDashboardsByUserSid(userId))
                .orElse(ListUtils.EMPTY_LIST);
        log.debug("Dashboard fetched for User {} = {}",userId, dashboardsByUserId.size());
        return mapper.convertList(dashboardsByUserId, DashboardTO.class);
    }

    @Override
    public List<SubjectCategoriesTO> getSubjectCategoriedByYear(Integer yearGroupId){
        log.trace("Finding Catgories by Year: {}",yearGroupId);
        List<SubjectCategories> categories = Optional.ofNullable(subjectCategoryRepository
                        .findSubjectsCategoriesByYrGroupId(yearGroupId))
                .orElse(ListUtils.EMPTY_LIST);
        log.debug("Categories fetched for year {} = {}",yearGroupId, categories.size());
        return mapper.convertList(categories, SubjectCategoriesTO.class);

    }

    @Override
    public List<TestTO> getTestsByYearAndSubjectName(Integer yearGroupId, String subjectName){
        log.trace("Finding Tests for Year {} and subjectName {}",yearGroupId, subjectName);
        List<Test> tests = Optional.ofNullable(testRepository.getTestByYearAndSubject(yearGroupId, subjectName))
                .orElse(ListUtils.EMPTY_LIST);
        log.debug("Tests fetched for year {} = {}",yearGroupId, tests.size());
        return mapper.convertList(tests, TestTO.class);

    }

    @Override
    public List<QuestionsTO> getQuestionsBySubAndSubcat(Integer subjectId, String subcatName){
        log.trace("Finding Questions for subject {} and subcat Name {}",subjectId, subcatName);
        List<Questions> questions = Optional.ofNullable(questionRepository
                .getQuestionsBySubAndSubcat(subjectId, subcatName)).orElse(ListUtils.EMPTY_LIST);
        log.debug("Finding Questions for subject {} and subcat Name {} = {}",subjectId, subcatName, questions.size());
        return mapper.convertList(questions, QuestionsTO.class);

    }

    @Override
    public UsersTO getUserById(Integer id){
        log.trace("Finding user by id : {}",id);
        return userRepository.findById(id).map(users -> mapper.convert(users, UsersTO.class)).orElse(null);
    }

    @Override
    public List<SetpracticeTO> getSetpracticeByUserIdSubjectName(Integer userId, String practiceName, String subjectName){
        log.trace("Finding Set practice for user {} and practiceName = {}, subject = {}", userId, practiceName, subjectName);
        List<Setpractice> setpractices = Optional.ofNullable(setPracticeRepository
                .getSetpracticeByUserIdSubjectName(userId, practiceName, subjectName)).orElse(ListUtils.EMPTY_LIST);
        log.debug("Set Practices found  for user id {} subject {} and practice {} = {}",userId, subjectName, practiceName, setpractices.size());
        return mapper.convertList(setpractices, SetpracticeTO.class);
    }

}
