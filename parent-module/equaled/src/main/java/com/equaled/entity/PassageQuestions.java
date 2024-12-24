package com.equaled.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "passage_questions")
@Data
public class PassageQuestions extends BaseEntity{
    private static final long serialVersionUID = 4985470065437090633L;
    private String text;
    private String explanation;
    @Column(name = "option_1_text")
    private String option1Text;
    @Column(name = "option_2_text")
    private String option2Text;
    @Column(name = "option_3_text")
    private String option3Text;
    @Column(name = "option_4_text")
    private String option4Text;
    @Column(name = "option_5_text")
    private String option5Text;
    @Column(name = "correct_option")
    private String correctOption;
    private Integer score;
    private String difficulty;

    @ManyToOne
    @JoinColumn(name = "passage_id", referencedColumnName = "id")
    Passage passage;
}
