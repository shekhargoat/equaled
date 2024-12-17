package com.equaled.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "frquestions")
@Data
public class FRQuestion extends BaseEntity{
    private String text;
    private String difficulty;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false, referencedColumnName = "id")
    private Subject subject;

    @Column(name = "creation_date")
    private Instant creationDate;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false, referencedColumnName = "id")
    private Users createdBy;

    @Column(name = "time_limit")
    private Integer timeLimit = 30;
}
