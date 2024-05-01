package com.equaled.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "exam_score")
@Getter @Setter @NoArgsConstructor
public class ExamScore extends BaseEntity{
    private static final long serialVersionUID = 5829831702036945849L;
    @Column(name = "exam_id")
    private String examId;
    @Column(name = "exam_score")
    private String examScore;
    @Column(name = "created_on")
    private Instant createdOn;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExamScore score = (ExamScore) o;
        return id == score.id && Arrays.equals(sid,score.sid) && createdOn == score.createdOn && Objects.equals(examId, score.examId) && Objects.equals(user, score.user) && Objects.equals(examScore, score.examScore);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(examId, examScore, createdOn, user);
        result = 31 * result + Arrays.hashCode(sid);
        return result;
    }
}
