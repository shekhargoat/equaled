package com.equaled.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "frqresponse")
@Getter@Setter@NoArgsConstructor
public class FRQResponse extends BaseEntity{

    private static final long serialVersionUID = -4574546942998490233L;

    private String text;
    @Column(name = "submission_date")
    private Instant submissionDate;
    private String grade;
    private String status;
    private String strengths;
    private String improvement;
    @Column(name="section_marks")
    private String sectionMarks;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false, referencedColumnName = "id")
    private FRQuestion question;

}
