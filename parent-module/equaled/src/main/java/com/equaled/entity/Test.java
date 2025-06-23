package com.equaled.entity;

import com.equaled.value.EqualEdEnums;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "test")
@Getter @Setter @NoArgsConstructor
public class Test extends BaseEntity{
    private static final long serialVersionUID = -6128049585156966510L;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "time_allotted_in_mins")
    private int timeAllottedInMins;
    @Column(name = "test_type")
    @Enumerated(EnumType.STRING)
    private EqualEdEnums.TestType testType;
    @Column(name = "enabled")
    private int enabled;
    @Column(name = "last_updated_on")
    private Instant lastUpdatedOn;
    @ManyToOne
    @JoinColumn(name = "year_group_id", referencedColumnName = "id")
    private YearGroup yearGroupId;
    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private Subject subject;
    @Column(name = "no_of_q")
    private int noOfQuestions;
    @Column(name = "state")
    private String state;
    @Column(name = "country_id")
    private String countryId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Test)) return false;
        Test test = (Test) o;
        return Objects.equals(id, test.id) &&
                Arrays.equals(sid, test.sid) &&
                Objects.equals(name, test.name) &&
                Objects.equals(description, test.description) &&
                timeAllottedInMins == test.timeAllottedInMins &&
                testType == test.testType &&
                enabled == test.enabled &&
                Objects.equals(lastUpdatedOn, test.lastUpdatedOn) &&
                Objects.equals(yearGroupId, test.yearGroupId) &&
                Objects.equals(subject, test.subject) &&
                Objects.equals(state, test.state) &&
                Objects.equals(countryId, test.countryId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, description, timeAllottedInMins, testType, enabled, lastUpdatedOn, yearGroupId, subject, state, countryId);
        result = 31 * result + Arrays.hashCode(sid);
        return result;
    }
}
