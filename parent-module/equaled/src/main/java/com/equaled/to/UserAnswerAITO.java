package com.equaled.to;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAnswerAITO extends BaseTO {
    private static final long serialVersionUID = -3894915800610370940L;
    @JsonProperty("answers")
    private List<UserAnswersTO> answers;
    @JsonProperty("User_id")
    private int userId;
    @JsonProperty("examId")
    private String examId;
    @JsonProperty("exam_score")
    private String examScore;
}
