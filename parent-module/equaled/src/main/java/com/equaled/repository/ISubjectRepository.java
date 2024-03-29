package com.equaled.repository;

import com.equaled.entity.Accounts;
import com.equaled.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ISubjectRepository extends JpaRepository<Subject, Integer> {

    @Override
    Optional<Subject> findById(Integer integer);

    List<Subject> findByName(String name);
}
