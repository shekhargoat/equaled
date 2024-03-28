package com.equaled.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "useranswers")
@Getter @Setter @NoArgsConstructor
public class UserAnswers extends BaseEntity{
    private static final long serialVersionUID = -7054486446353183741L;
    @Column(name = "correct_option")
    private String correctOption;
    @Column(name = "user_option")
    private String userOption;
    @Column(name = "explanation")
    private String explanation;
    @Column(name = "time_spent")
    private int timeSpent;
    @Column(name = "answer_date")
    private long answerDate;
    @Column(name = "exam_id")
    private String examId;
    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private Subject subject;
    @ManyToOne
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    private Questions question;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAnswers that = (UserAnswers) o;
        return id == that.id && Arrays.equals(sid,that.sid) && timeSpent == that.timeSpent && answerDate == that.answerDate && Objects.equals(correctOption, that.correctOption) && Objects.equals(userOption, that.userOption) && Objects.equals(explanation, that.explanation) && Objects.equals(examId, that.examId) && Objects.equals(subject, that.subject) && Objects.equals(question, that.question);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(correctOption, userOption, explanation, timeSpent, answerDate, examId, subject, question);
        result = 31 * result + Arrays.hashCode(sid);
        return result;
    }
}
