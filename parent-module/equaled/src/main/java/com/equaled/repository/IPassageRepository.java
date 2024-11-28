package com.equaled.repository;

import com.equaled.entity.Passage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPassageRepository extends JpaRepository<Passage, Integer> {
}
