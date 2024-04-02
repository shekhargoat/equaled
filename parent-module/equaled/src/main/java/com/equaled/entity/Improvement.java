package com.equaled.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "improvement")
@Getter @Setter @NoArgsConstructor
public class Improvement extends BaseEntity{
    private static final long serialVersionUID = -2694424193251859875L;
    @Column(name = "exam_id")
    private String examId;
    @Column(name = "strong_category")
    private String strongCategory;
    @Column(name = "weak_category")
    private String weakCategory;
    @Column(name = "score")
    private int score;
    @Column(name = "total_questions")
    private int totalQuestions;
    @Column(name = "created_on")
    private Instant createdOn;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;
    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private Subject subject;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Improvement that = (Improvement) o;
        return id == that.id && Arrays.equals(sid, that.sid) && score == that.score && totalQuestions == that.totalQuestions && Objects.equals(examId, that.examId) && Objects.equals(strongCategory, that.strongCategory) && Objects.equals(weakCategory, that.weakCategory) && Objects.equals(user, that.user) && Objects.equals(subject, that.subject);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(examId, strongCategory, weakCategory, score, totalQuestions, user, subject);
        result = 31 * result + Arrays.hashCode(sid);
        return result;
    }
}
