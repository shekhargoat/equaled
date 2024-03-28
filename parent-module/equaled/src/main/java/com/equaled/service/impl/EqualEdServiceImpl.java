package com.equaled.service.impl;

import com.equaled.dozer.DozerUtils;
import com.equaled.entity.Dashboard;
import com.equaled.entity.SubjectCategories;
import com.equaled.entity.Test;
import com.equaled.repository.IDashboardRepository;
import com.equaled.repository.ISubjectCategoryRepository;
import com.equaled.repository.ITestRepository;
import com.equaled.service.IEqualEdService;
import com.equaled.to.DashboardTO;
import com.equaled.to.SubjectCategoriesTO;
import com.equaled.to.TestTO;
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

}
