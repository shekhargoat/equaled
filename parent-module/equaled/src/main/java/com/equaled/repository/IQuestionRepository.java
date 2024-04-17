package com.equaled.repository;

import com.equaled.entity.Questions;
import com.equaled.entity.Test;
import com.equaled.value.EqualEdEnums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IQuestionRepository extends JpaRepository<Questions, Integer> {

    @Query(value = "select q from Questions q where q.subject.id = :subjectId and q.subCategory = :subcatName")
    List<Questions> getQuestionsBySubAndSubcat(Integer subjectId, String subcatName);
    List<Questions> getQuestionsBySubjectAndLearn(Integer subjectId, EqualEdEnums.LearnType learnType);
    @Query(value = "select q from Questions q where q.subject.name = :subjectName and q.yearGroupId.id = :yearGroup")
    List<Questions> getQuestionsBySubjectAndYearGroupId(String subjectName, Integer yearGroup);

    @Query(value = "select q from Questions q where q.user.id = :userId")
    List<Questions> getQuestionsByUserId(Integer userId);
}
