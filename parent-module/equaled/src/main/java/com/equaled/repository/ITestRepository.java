package com.equaled.repository;

import com.equaled.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ITestRepository extends JpaRepository<Test,Integer> {

    @Query(value = "select t from Test t where t.yearGroupId.year = :yearGroupId and t.subject.name = :subjectName")
    List<Test> getTestByYearAndSubject(Integer yearGroupId, String subjectName);

    @Query(value = "select t from Test t where t.yearGroupId.year = :yearGroupId")
    List<Test> getTestByYearGroupId(Integer yearGroupId);

    @Query("SELECT t FROM Test t WHERE t.yearGroupId.id = :yearGroupId AND t.state = :state AND t.countryId = :countryId")
    List<Test> findByYearGroupStateAndCountry(Integer yearGroupId, String state, String countryId);
}
