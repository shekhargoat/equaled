package com.equaled.to;

import com.equaled.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class SubjectTO extends BaseEntity {

    private static final long serialVersionUID = -1201834045984641653L;
    private String name;
    private String description;
    private long createdOn;
    private long lastUpdatedOn;

}
