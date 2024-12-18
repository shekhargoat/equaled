package com.equaled.to;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAnswersTO extends BaseTO {
    private static final long serialVersionUID = -5249867812742441052L;
    @JsonProperty("Correct_option")
    private String correctOption;
    @JsonProperty("User_option")
    private String userOption;
    @JsonProperty("Explanation")
    private String explanation;
    @JsonProperty("Time_Spent")
    private int timeSpent;
    @JsonProperty("date")
    private long answerDate;
    @JsonProperty("examId")
    private String examId;
    @JsonProperty("Subject")
    private SubjectTO subject;
    @JsonProperty("Question")
    private QuestionsTO question;
}
