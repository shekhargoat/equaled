package com.equaled.repository;

import com.equaled.entity.Setpractice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ISetPracticeRepository extends JpaRepository<Setpractice, Integer> {

    @Query(value = "select sp from Setpractice sp where sp.user.id = :userId and sp.practiceName = :practiceName and sp.subject.name = :subjectName")
    List<Setpractice> getSetpracticeByUserIdSubjectName(Integer userId, String practiceName, String subjectName);
}
