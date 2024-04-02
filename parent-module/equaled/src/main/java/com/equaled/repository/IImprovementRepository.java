package com.equaled.repository;

import com.equaled.entity.Improvement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IImprovementRepository extends JpaRepository<Improvement, Integer> {
    @Query(value = "select i from Improvement i where i.user.id= :userId")
    List<Improvement> getImprovementsByUserId(Integer userId);

    @Query(value = "select i from Improvement i where i.examId= :examId")
    List<Improvement> getImprovementsByExamId(String examId);

    @Query(value = "select i from Improvement i where i.user.id= :userId AND i.examId= :examId")
    List<Improvement> getImprovementsByUserIdAndExamId(Integer userId,String examId);
}
