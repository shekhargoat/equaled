package com.equaled.repository;

import com.equaled.entity.Questions;
import com.equaled.entity.Test;
import com.equaled.value.EqualEdEnums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IQuestionRepository extends JpaRepository<Questions, Integer> {

    @Query(value = "select q from Questions q where q.subject.id = :subjectId and q.subCategory = :subcatName")
    List<Questions> getQuestionsBySubAndSubcat(Integer subjectId, String subcatName);
    @Query(value = "select q from Questions q where q.subject.id = :subjectId and q.learn = :learnType ")
    List<Questions> getQuestionsBySubjectAndLearn(Integer subjectId, EqualEdEnums.LearnType learnType);
    @Query(value = "select q from Questions q where q.subject.name = :subjectName and q.yearGroupId.id = :yearGroup")
    List<Questions> getQuestionsBySubjectAndYearGroupId(String subjectName, Integer yearGroup);

    @Query(value = "select q from Questions q where q.user.id = :userId")
    List<Questions> getQuestionsByUserId(Integer userId);

//    @Query(value = "select q from Questions q where q.subCategory IN (:subacts)")
    List<Questions> getQuestionsBySubCategoryIn(List<String> subcats);

    List<Questions> getQuestionsByQuestion(String question);

    Optional<Questions> findQuestionsByQuestionAiId(String questionAiId);
}
