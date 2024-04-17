package com.equaled.entity;

import com.equaled.value.EqualEdEnums;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;
@Entity
@Table(name = "questions")
@Getter @Setter @NoArgsConstructor
public class Questions extends BaseEntity{
    private static final long serialVersionUID = 5327772368345977985L;
    @Column(name = "question")
    private String question;
    @Column(name = "difficulty")
    @Enumerated(EnumType.STRING)
    private EqualEdEnums.Difficulty difficulty;
    @Column(name = "category")
    private String category;
    @Column(name = "sub_category")
    private String subCategory;
    @Column(name = "option_1")
    private String option1;
    @Column(name = "option_2")
    private String option2;
    @Column(name = "option_3")
    private String option3;
    @Column(name = "option_4")
    private String option4;
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
    @Column(name = "image_path")
    private String imagePath;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Questions questions = (Questions) o;
        return id == questions.id && Arrays.equals(sid, questions.sid) && Objects.equals(question, questions.question) && difficulty == questions.difficulty && Objects.equals(category, questions.category) && Objects.equals(subCategory, questions.subCategory) && Objects.equals(option1, questions.option1) && Objects.equals(option2, questions.option2) && Objects.equals(option3, questions.option3) && Objects.equals(option4, questions.option4) && Objects.equals(correctOption, questions.correctOption) && learn == questions.learn && Objects.equals(yearGroupId, questions.yearGroupId) && Objects.equals(subject, questions.subject);
    }

    @Override
    public int hashCode() {
        int result =  Objects.hash(id,question, difficulty, category, subCategory, option1, option2, option3, option4, correctOption, learn, yearGroupId, subject);
        result = 31 * result + Arrays.hashCode(sid);
        return result;
    }
}
