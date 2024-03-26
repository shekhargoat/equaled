package com.equaled.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ImprovementTO extends BaseTO {
    private static final long serialVersionUID = -2021386537490205189L;

    private String examId;
    private String strongCategory;
    private String weakCategory;
    private int score;
    private int totalQuestions;
    private UsersTO user;
    private SubjectTO subject;
}
