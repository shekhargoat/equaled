package com.equaled.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "passage_answers")
@Data
public class PassageAnswers extends BaseEntity {

    private static final long serialVersionUID = -7127565214053962076L;

    @Column(name = "user_answer")
    private String userAnswer;

    @Column(name = "correct_option")
    private String correctOption;

    @Column(name = "user_option")
    private String userOption;

    @Column(name = "user_explanation")
    private String userExplanation;

    @Column(name = "answer_date")
    private Instant dateofAnswer;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "passage_question_id", referencedColumnName = "id")
    PassageQuestions passageQuestion;

    private String status;

    @Column(name = "user_exam_id")
    private String userExamId;
}
