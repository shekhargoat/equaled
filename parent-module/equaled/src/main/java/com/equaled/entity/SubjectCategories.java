package com.equaled.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "subject_categories")
@Getter @Setter @NoArgsConstructor
public class SubjectCategories extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id", nullable = false)
    private Subject subject;
    @Column(name = "sub_category")
    private String subCategory;
    @Column(name = "sub_category_1")
    private String subCategory1;

    @ManyToOne
    @JoinColumn(name = "year_group_id", referencedColumnName = "id", nullable = false)
    private YearGroup yearGroup;

    @Column(name = "video_url")
    private String videoUrl;
    @Column(name = "country_id")
    private Integer countryId;
    @Column(name = "sort_order")
    private Integer sortOrder;
    @Column(name = "curriculum")
    private String curriculum;
    @Column(name = "state")
    private String state;
    @Column(name = "language")
    private String language;
    @Column(name = "notes")
    private String notes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectCategories that = (SubjectCategories) o;
        return Objects.equals(subject, that.subject) &&  Objects.equals(subCategory, that.subCategory) && Objects.equals(subCategory1, that.subCategory1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, subCategory, subCategory1);
    }
}
