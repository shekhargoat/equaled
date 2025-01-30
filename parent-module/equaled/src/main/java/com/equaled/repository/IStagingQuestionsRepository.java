package com.equaled.repository;

import com.equaled.entity.StagingQuestions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IStagingQuestionsRepository extends JpaRepository<StagingQuestions, Integer> {
}
