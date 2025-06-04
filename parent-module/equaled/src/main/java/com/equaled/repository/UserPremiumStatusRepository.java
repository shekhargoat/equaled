package com.equaled.repository;

import com.equaled.entity.UserPremiumStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPremiumStatusRepository extends JpaRepository<UserPremiumStatus, Long> {

    Optional<UserPremiumStatus> findByUserId(String userId);

}

