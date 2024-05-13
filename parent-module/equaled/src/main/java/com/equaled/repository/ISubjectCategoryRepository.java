package com.equaled.repository;

import com.equaled.entity.Subject;
import com.equaled.entity.SubjectCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ISubjectCategoryRepository extends JpaRepository<SubjectCategories, Integer> {

    @Query(value = "select s from SubjectCategories s where s.yearGroup.year = :yr")
    List<SubjectCategories> findSubjectsCategoriesByYrGroupId(Integer yr);

}
