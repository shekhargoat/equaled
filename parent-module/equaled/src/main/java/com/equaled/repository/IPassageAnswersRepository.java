package com.equaled.repository;

import com.equaled.entity.PassageAnswers;
import com.equaled.entity.PassageQuestions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPassageAnswersRepository extends JpaRepository<PassageAnswers, Integer> {
}
