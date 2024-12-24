package com.equaled.repository;

import com.equaled.entity.PassageAnswers;
import com.equaled.entity.PassageQuestions;
import com.equaled.entity.Users;
import org.apache.tinkerpop.gremlin.process.traversal.P;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IPassageAnswersRepository extends JpaRepository<PassageAnswers, Integer> {

    List<PassageAnswers> findPassageAnswersByStatus(String status);
    List<PassageAnswers> findPassageAnswersByStatusAndAndUser(String status, Users user);
    List<PassageAnswers> findPassageAnswersByUserAndUserExamId(Users user, String examId);

    @Query(value = "select pa from PassageAnswers  pa where hex(pa.sid) = :sid")
    Optional<PassageAnswers> findBySid(String sid);

}
