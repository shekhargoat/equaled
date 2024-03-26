package com.equaled.entity;

import com.equaled.value.EqualEdEnums;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "questions")
@Getter @Setter @NoArgsConstructor
public class Questions extends BaseEntity{
    @Basic
    @Column(name = "question")
    private String question;
    @Column(name = "difficulty")
    @Enumerated(EnumType.STRING)
    private EqualEdEnums.Difficulty difficulty;
    @Basic
    @Column(name = "category")
    private String category;
    @Basic
    @Column(name = "sub_category")
    private String subCategory;
    @Basic
    @Column(name = "option_1")
    private String option1;
    @Basic
    @Column(name = "option_2")
    private String option2;
    @Basic
    @Column(name = "option_3")
    private String option3;
    @Basic
    @Column(name = "option_4")
    private String option4;
    @Basic
    @Column(name = "correct_option")
    private String correctOption;
    @Column(name = "learn")
    @Enumerated(EnumType.STRING)
    private EqualEdEnums.LearnType learn;
    @ManyToOne
    @JoinColumn(name = "year_group_id", referencedColumnName = "id")
    private YearGroup yearGroupId;
    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private Subject subject;
}
