package com.equaled.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class DashboardTO extends BaseTO {

    private static final long serialVersionUID = 2525625637775416429L;
    private UsersTO user;
    private SubjectTO subject;
    private String examId;
    private long startTime;
    private String title;

}
