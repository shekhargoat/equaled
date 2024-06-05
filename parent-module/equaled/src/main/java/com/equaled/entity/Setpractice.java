package com.equaled.entity;

import com.equaled.value.EqualEdEnums;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "setpractice")
@Getter @Setter @NoArgsConstructor
public class Setpractice extends BaseEntity{

    private static final long serialVersionUID = 4242155259914800213L;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private Users user;

    @Column(name = "practice_name")
    private String practiceName;
    @Column(name = "time_limit")
    private Integer timeLimit;
    @Column(name = "questions")
    private String questions;
    @Column(name = "no_of_q")
    private Integer noOfQ;
    
    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id", nullable = false)
    private Subject subject;
    
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EqualEdEnums.SetpracticeStatus status;

    @ManyToOne
    @JoinColumn(name = "year_group_id", referencedColumnName = "id", nullable = false)
    private YearGroup yearGroup;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Setpractice that = (Setpractice) o;
        return user == that.user && Objects.equals(practiceName, that.practiceName) && Objects.equals(timeLimit, that.timeLimit) && Objects.equals(questions, that.questions) && Objects.equals(noOfQ, that.noOfQ) && Objects.equals(subject, that.subject) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, practiceName, timeLimit, questions, noOfQ, subject, status);
    }
}
