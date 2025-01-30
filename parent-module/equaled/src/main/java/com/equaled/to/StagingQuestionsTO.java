package com.equaled.to;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dozer.Mapping;

@Getter @Setter @NoArgsConstructor
public class StagingQuestionsTO {

    private String image_path;
    private String difficult_level;
    private String category;
    private String sub_category;
    private String text;
    private String option_1_text;
    private String option_2_text;
    private String option_3_text;
    private String option_4_text;
    private String correct_option;
    private String explanation;
    private String sub_category_1;
    private String sub_category_2;

    private Integer year_group_id;
    private Integer subject_id;

}
