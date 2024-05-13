package com.equaled.repository;

import com.equaled.entity.PracticeUserAnswers;
import com.equaled.entity.UserAnswers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IPracticeUseranswerRepository extends JpaRepository<PracticeUserAnswers, Integer> {
    List<PracticeUserAnswers> findByExamId(String examId);
    @Query(value = "select pa from PracticeUserAnswers pa where pa.user.id = :userId")
    List<PracticeUserAnswers> findByUserId(Integer userId);

}
