package com.equaled.repository;

import com.equaled.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ITestRepository extends JpaRepository<Test,Integer> {

    @Query(value = "select t from Test t where t.yearGroupId.year = :yearGroupId and t.subject.name = :subjectName")
    List<Test> getTestByYearAndSubject(Integer yearGroupId, String subjectName);
}
