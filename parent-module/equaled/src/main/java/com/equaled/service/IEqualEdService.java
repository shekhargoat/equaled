package com.equaled.service;

import com.equaled.to.*;

import java.util.List;
import java.util.Map;

public interface IEqualEdService {

    List<DashboardTO> getDashboardsByUser(Integer userId);
    List<DashboardTO> getDashboardsByUser(String userId);

    List<SubjectCategoriesTO> getSubjectCategoriedByYear(Integer yearGroupId);

    List<TestTO> getTestsByYearAndSubjectName(Integer yearGroupId, String subjectName);

    List<QuestionsTO> getQuestionsBySubAndSubcat(Integer subjectId, String subcatName);

    UsersTO getUserById(Integer id);

    List<SetpracticeTO> getSetpracticeByUserIdSubjectName(Integer userId, String practiceName, String subjectName);
}
