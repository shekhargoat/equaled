package com.equaled.service;

import com.equaled.to.DashboardTO;
import com.equaled.to.SubjectCategoriesTO;
import com.equaled.to.TestTO;

import java.util.List;

public interface IEqualEdService {

    List<DashboardTO> getDashboardsByUser(Integer userId);
    List<DashboardTO> getDashboardsByUser(String userId);

    List<SubjectCategoriesTO> getSubjectCategoriedByYear(Integer yearGroupId);

    List<TestTO> getTestsByYearAndSubjectName(Integer yearGroupId, String subjectName);
}
