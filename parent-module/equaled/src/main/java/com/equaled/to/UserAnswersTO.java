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
    private String correctOption;
    private String userOption;
    private String explanation;
    private int timeSpent;
    private long answerDate;
    private String examId;
    private SubjectTO subject;
    private QuestionsTO question;
}
