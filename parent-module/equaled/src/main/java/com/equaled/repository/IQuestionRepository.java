package com.equaled.repository;

import com.equaled.entity.Questions;
import com.equaled.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IQuestionRepository extends JpaRepository<Questions, Integer> {

    @Query(value = "select q from Questions q where q.subject.id = :subjectId and q.subCategory = :subcatName")
    List<Test> getQuestionsBySubAndSubcat(Integer subjectId, String subcatName);
    @Query(value = "select q from Questions q where q.subject.id = :subjectId and q.learn = :learnType")
    List<Test> getQuestionsBySubjectAndLearn(Integer subjectId, String learnType);
}
