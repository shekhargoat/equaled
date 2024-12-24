package com.equaled.repository;

import com.equaled.entity.FRQResponse;
import com.equaled.entity.FRQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IFRQuestionRepository extends JpaRepository<FRQuestion, Integer> {

    @Query(value = "select frq from FRQuestion frq where hex(frq.sid) = :sid")
    Optional<FRQuestion> findBySid(String sid);

}
