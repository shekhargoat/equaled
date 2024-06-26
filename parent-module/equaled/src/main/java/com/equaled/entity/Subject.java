package com.equaled.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "subject")
@Getter @Setter @NoArgsConstructor
public class Subject extends BaseEntity{

    private static final long serialVersionUID = -7219416006713047882L;
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "last_updated_on")
    private Instant lastUpdatedOn;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return id == subject.id && Arrays.equals(sid, subject.sid) && Objects.equals(name, subject.name) && Objects.equals(description, subject.description) && Objects.equals(createdOn, subject.createdOn) && Objects.equals(lastUpdatedOn, subject.lastUpdatedOn);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, description, createdOn, lastUpdatedOn);
        result = 31 * result + Arrays.hashCode(sid);
        return result;
    }
}