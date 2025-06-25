package com.equaled.repository;

import com.equaled.entity.Passage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IPassageRepository extends JpaRepository<Passage, Integer> {

    @Query("select p from Passage p where p.sid = decode(:sid,'hex')")
    Optional<Passage> findBySid(String sid);

    @Query(value = "SELECT * FROM passages p " +
            "JOIN users u ON p.author = u.id " +
            "WHERE u.year_group_id = :yearGroupId", nativeQuery = true)
    List<Passage> findByYearGroupId(@Param("yearGroupId") String yearGroupId);
}
