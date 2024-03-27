package com.equaled.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "subject")
@Getter @Setter @NoArgsConstructor
public class Subject extends BaseEntity{
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "created_on")
    private long createdOn;
    
    @Column(name = "last_updated_on")
    private long lastUpdatedOn;

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
