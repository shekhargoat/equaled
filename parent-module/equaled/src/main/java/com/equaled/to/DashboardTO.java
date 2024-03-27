package com.equaled.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class DashboardTO extends BaseTO {

    private Users user;
    private Subject subject;
    private String examId;
    private long startTime;
    private String title;

}
