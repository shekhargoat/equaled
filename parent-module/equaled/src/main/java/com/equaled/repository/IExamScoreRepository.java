package com.equaled.repository;

import com.equaled.entity.Accounts;
import com.equaled.entity.ExamScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface IExamScoreRepository extends JpaRepository<ExamScore, Integer> {
    @Query(value = "select e from ExamScore e where e.user.id = :userId and e.examId = :examId")
    List<ExamScore> getExamScoreByUserAndExamId(Integer userId, String examId);

    List<ExamScore> getExamScoreByExamId(String examId);
}
