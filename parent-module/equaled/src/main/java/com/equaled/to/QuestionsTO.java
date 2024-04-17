package com.equaled.to;

import com.equaled.value.EqualEdEnums;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class QuestionsTO extends BaseTO {
    private static final long serialVersionUID = 5717623117786267960L;
    private String question;
    private EqualEdEnums.Difficulty difficulty;
    private String category;
    private String subCategory;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String correctOption;
    private EqualEdEnums.LearnType learn;
    private YearGroupTO yearGroup;
    private SubjectTO subject;
    private String imagePath;
    private UsersTO user;
}
