package com.equaled.repository;

import com.equaled.entity.Passage;
import com.equaled.entity.PassageQuestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IPassageQuestionsRepository extends JpaRepository<PassageQuestions, Integer> {

    @Query("select pq from PassageQuestions pq where hex(pq.sid)=:sid")
    Optional<PassageQuestions> findBySid(String sid);
}
