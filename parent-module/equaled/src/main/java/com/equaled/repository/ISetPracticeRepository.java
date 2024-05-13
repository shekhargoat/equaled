package com.equaled.repository;

import com.equaled.entity.Setpractice;
import com.equaled.value.EqualEdEnums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ISetPracticeRepository extends JpaRepository<Setpractice, Integer> {

    @Query(value = "select sp from Setpractice sp where sp.user.id = :userId and sp.practiceName = :practiceName and sp.subject.name = :subjectName")
    List<Setpractice> getSetpracticeByUserIdSubjectName(Integer userId, String practiceName, String subjectName);

    @Transactional
    @Modifying
    @Query(value = "update Setpractice twh set twh.status= :status where twh.sid = unhex(:sid)")
    void markStatus(String sid, EqualEdEnums.SetpracticeStatus status);

    @Query(value = "select sp from Setpractice sp where sp.user.id = :userId and sp.status = :status")
    List<Setpractice> getSetpracticeByUserAndStatus(Integer userId,EqualEdEnums.SetpracticeStatus status);
}
