package com.equaled.to;

import com.equaled.value.EqualEdEnums;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter @Setter @NoArgsConstructor
public class SetpracticeTO extends BaseTO {

    private static final long serialVersionUID = 7862862664683853572L;
    private UsersTO user;
    private String practiceName;
    private Integer timeLimit;
    private String questions;
    private Integer noOfQ;
    private SubjectTO subject;
    private EqualEdEnums.SetpracticeStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SetpracticeTO that = (SetpracticeTO) o;
        return user == that.user && Objects.equals(practiceName, that.practiceName) && Objects.equals(timeLimit, that.timeLimit) && Objects.equals(questions, that.questions) && Objects.equals(noOfQ, that.noOfQ) && Objects.equals(subject, that.subject) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, practiceName, timeLimit, questions, noOfQ, subject, status);
    }
}
