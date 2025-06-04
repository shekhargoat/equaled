package com.equaled.repository;

import com.equaled.entity.LLMUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface LLMUsageRepository extends JpaRepository<LLMUsage, Long> {

    Optional<LLMUsage> findByUserIdAndWeekStartGreaterThanEqualAndWeekEndLessThanEqual(String userId, LocalDateTime weekStart, LocalDateTime weekEnd);

}
