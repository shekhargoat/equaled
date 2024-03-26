package com.equaled.entity;

import com.equaled.value.EqualEdEnums;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "test")
@Getter @Setter @NoArgsConstructor
public class Test extends BaseEntity{
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "time_allotted_in_mins")
    private Integer timeAllottedInMins;
    @Column(name = "test_type")
    @Enumerated(EnumType.STRING)
    private EqualEdEnums.TestType testType;
    @Basic
    @Column(name = "enabled")
    private Integer enabled;
    @Basic
    @Column(name = "last_updated_on")
    private Timestamp lastUpdatedOn;
    @ManyToOne
    @JoinColumn(name = "year_group_id", referencedColumnName = "id")
    private YearGroup yearGroupId;
    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private Subject subject;
}
