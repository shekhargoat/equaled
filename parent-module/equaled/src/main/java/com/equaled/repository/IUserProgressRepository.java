package com.equaled.repository;

import com.equaled.entity.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUserProgressRepository extends JpaRepository<UserProgress, Integer> {

    List<UserProgress> findByUserId(int userId);

    List<UserProgress> findBySubject(String subject);

    List<UserProgress> findByUserIdAndSubject(int userId, String subject);

}
