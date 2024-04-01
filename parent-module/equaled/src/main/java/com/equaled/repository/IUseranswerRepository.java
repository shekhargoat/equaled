package com.equaled.repository;

import com.equaled.entity.UserAnswers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUseranswerRepository extends JpaRepository<UserAnswers, Integer> {
    List<UserAnswers> findByExamId(String examId);

}
