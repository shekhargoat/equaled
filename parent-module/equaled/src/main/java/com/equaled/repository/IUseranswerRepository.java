package com.equaled.repository;

import com.equaled.entity.UserAnswers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface IUseranswerRepository extends JpaRepository<UserAnswers, Integer> {
    List<UserAnswers> findByExamId(String examId);

    @Query(value = "select ua from UserAnswers ua where ua.user.id = :userId and ua.examId = :examId")
    List<UserAnswers> findByUserAndExamId(Integer userId,String examId);

    @Query(value = "select ua from UserAnswers ua where ua.user.id = :userId")
    List<UserAnswers> findByUserId(Integer userId);

    @Query(value = "SELECT ua.user_id, q.difficulty " +
            "FROM user_answers ua " +
            "JOIN questions q ON ua.question_id = q.id " +
            "JOIN year_group yg ON q.year_group_id = yg.id " +
            "WHERE yg.year = :yearGroupId " +
            "AND ua.answer_date BETWEEN :startOfWeek AND :endOfWeek",
            nativeQuery = true)
    List<Object[]> findWeeklyUserDifficultiesNative(@Param("yearGroupId") Integer yearGroupId,
                                                    @Param("startOfWeek") Instant startOfWeek,
                                                    @Param("endOfWeek") Instant endOfWeek);
}
