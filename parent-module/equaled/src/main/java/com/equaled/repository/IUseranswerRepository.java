package com.equaled.repository;

import com.equaled.entity.UserAnswers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IUseranswerRepository extends JpaRepository<UserAnswers, Integer> {
    List<UserAnswers> findByExamId(String examId);

    @Query(value = "select ua from UserAnswers ua where ua.user.id = :userId and ua.examId = :examId")
    List<UserAnswers> findByUserAndExamId(Integer userId,String examId);
}
