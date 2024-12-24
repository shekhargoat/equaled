package com.equaled.repository;

import com.equaled.entity.Subject;
import com.equaled.entity.Users;
import com.equaled.entity.YearGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IYearGroupRepository extends JpaRepository<YearGroup, Integer> {

    @Override
    Optional<YearGroup> findById(Integer integer);

    Optional<YearGroup> findByYear(Integer year);
}
