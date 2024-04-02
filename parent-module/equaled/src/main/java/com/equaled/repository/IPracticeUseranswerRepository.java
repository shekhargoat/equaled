package com.equaled.repository;

import com.equaled.entity.PracticeUserAnswers;
import com.equaled.entity.UserAnswers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPracticeUseranswerRepository extends JpaRepository<PracticeUserAnswers, Integer> {
    List<PracticeUserAnswers> findByExamId(String examId);

}
