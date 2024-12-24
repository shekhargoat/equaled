package com.equaled.repository;

import com.equaled.entity.Passage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IPassageRepository extends JpaRepository<Passage, Integer> {

    @Query("select p from Passage p where hex(p.sid) = :sid")
    Optional<Passage> findBySid(String sid);
}
