package com.equaled.repository;

import com.equaled.entity.Passage;
import com.equaled.entity.PassageQuestions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPassageQuestionsRepository extends JpaRepository<PassageQuestions, Integer> {
}
