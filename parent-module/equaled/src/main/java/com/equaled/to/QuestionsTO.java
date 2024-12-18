package com.equaled.to;

import com.equaled.value.EqualEdEnums;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionsTO extends BaseTO {
    private static final long serialVersionUID = 5717623117786267960L;
    @JsonProperty("question")
    private String question;
    @JsonProperty("difficulty")
    private EqualEdEnums.Difficulty difficulty;
    @JsonProperty("category")
    private String category;
    @JsonProperty("sub_category")
    private String subCategory;
    @JsonProperty("option1")
    private String option1;
    @JsonProperty("option2")
    private String option2;
    @JsonProperty("option3")
    private String option3;
    @JsonProperty("option4")
    private String option4;
    @JsonProperty("correct_option")
    private String correctOption;
    @JsonProperty("learn")
    private EqualEdEnums.LearnType learn;
    @JsonProperty("year_group")
    private YearGroupTO yearGroup;
    @JsonProperty("subject")
    private SubjectTO subject;
    private String imagePath;
    @JsonProperty("user")
    private UsersTO user;
    @JsonProperty("queston_ai_id")
    private String questionAiId;
    @JsonProperty("subjectId")
    private int subjectId;
    @JsonProperty("year_group_id")
    private int year_group_id;
}
