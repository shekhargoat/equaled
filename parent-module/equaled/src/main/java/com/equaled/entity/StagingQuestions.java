package com.equaled.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "question_staging")
@Getter @Setter @NoArgsConstructor
public class StagingQuestions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String Image_path;
    private String Difficult_level;
    private String category;
    private String sub_category;
    private String Text;
    private String Option_1_text;
    private String Option_2_text;
    private String Option_3_text;
    private String Option_4_text;
    private String Correct_option;
    private String Explanation;
    private String sub_category_1;
    private String sub_category_2;
    @Column(name = "ingested_on")
    private Instant ingestedOn;

    @ManyToOne
    @JoinColumn(name = "year_group_id", referencedColumnName = "id")
    private YearGroup yearGroup;

    @ManyToOne
    @JoinColumn(name = "Subject_id", referencedColumnName = "id")
    private Subject subject;

}
