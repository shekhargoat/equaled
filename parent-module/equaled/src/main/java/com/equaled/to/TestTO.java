package com.equaled.to;

import com.equaled.value.EqualEdEnums;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class TestTO extends BaseTO {
    private static final long serialVersionUID = 7133570638450519644L;
    private String name;
    private String description;
    private int timeAllottedInMins;
    private EqualEdEnums.TestType testType;
    private int enabled;
    private long lastUpdatedOn;
    private YearGroupTO yearGroup;
    private SubjectTO subject;
}
