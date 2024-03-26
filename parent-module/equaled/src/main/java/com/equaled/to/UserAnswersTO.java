package com.equaled.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
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
