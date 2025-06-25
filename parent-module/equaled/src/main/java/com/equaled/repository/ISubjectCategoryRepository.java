package com.equaled.repository;

import com.equaled.entity.Subject;
import com.equaled.entity.SubjectCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ISubjectCategoryRepository extends JpaRepository<SubjectCategories, Integer> {

    @Query(value = "select s from SubjectCategories s where s.yearGroup.year = :yr")
    List<SubjectCategories> findSubjectsCategoriesByYrGroupId(Integer yr);

    @Query("select distinct s.subject from SubjectCategories s where s.yearGroup.id = :yearGroupId")
    List<Subject> findDistinctSubjectsByYearGroup(@Param("yearGroupId") Integer yearGroupId);

    @Query("select c from SubjectCategories c where c.subject.id = (select s.id from Subject s where s.name = :subjectName) and c.yearGroup.id = :yearGroupId")
    List<SubjectCategories> getSubjectCategoriesBySubjectAndYearGroupId(@Param("subjectName") String subjectName, @Param("yearGroupId") Integer yearGroupId);

    @Query("SELECT sc FROM SubjectCategories sc " +
            "WHERE sc.subject.name = :subjectName " +
            "AND sc.yearGroup.id = :yearGroup " +
            "AND sc.state = :stateCode " +
            "AND sc.countryId = :countryCode")
    List<SubjectCategories> findBySubjectYearGroupStateAndCountry(@Param("subjectName") String subjectName, @Param("yearGroup") Integer yearGroup,
                                                                  @Param("stateCode") String stateCode, @Param("countryCode") String countryCode);

    @Query("SELECT DISTINCT sc.subject FROM SubjectCategories sc " +
            "WHERE sc.yearGroup.id = :yearGroupId " +
            "AND sc.state = :stateCode " +
            "AND sc.countryId = :countryCode")
    List<Subject> findDistinctSubjectsByYearGroupAndStateAndCountry(@Param("yearGroupId") Integer yearGroupId,
                                                             @Param("stateCode") String stateCode,
                                                             @Param("countryCode") String countryCode);
}
