package com.equaled.repository;

import com.equaled.entity.FRQResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IFRQResponseRepository extends JpaRepository<FRQResponse, Integer> {

    @Query(value = "select frq from FRQResponse frq where hex(frq.sid) = :sid")
    Optional<FRQResponse> findBySid(String sid);

    @Query(value = "select frq from FRQResponse frq where frq.user.id = :userId and frq.status=:status")
    List<FRQResponse> findByUserIdAndStatus(Integer userId, String status);
}
