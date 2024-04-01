package com.equaled.repository;

import com.equaled.entity.UserAnswers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUseranswerRepository extends JpaRepository<UserAnswers, Integer> {

}
