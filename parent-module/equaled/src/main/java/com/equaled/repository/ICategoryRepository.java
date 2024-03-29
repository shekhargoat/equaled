package com.equaled.repository;

import com.equaled.entity.Dashboard;
import com.equaled.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICategoryRepository extends JpaRepository<Subject,Integer> {
    @Query(value = "select s from Subject s where s.id = :id")
    List<Subject> findSubjectssByYrGroupId(Integer id);

}
