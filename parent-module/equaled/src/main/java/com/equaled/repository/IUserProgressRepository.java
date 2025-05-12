package com.equaled.repository;

import com.equaled.entity.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserProgressRepository extends JpaRepository<UserProgress, Integer> {
}
