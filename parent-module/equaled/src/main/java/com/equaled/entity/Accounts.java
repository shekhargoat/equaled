package com.equaled.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "accounts")
@Getter @Setter @NoArgsConstructor
public class Accounts extends BaseEntity{
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "enabled")
    private Integer enabled;
    @Basic
    @Column(name = "created_on")
    private Timestamp createdOn;
    @Basic
    @Column(name = "last_updated_on")
    private Timestamp lastUpdatedOn;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Accounts accounts = (Accounts) o;
        return id == accounts.id && Arrays.equals(sid, accounts.sid) && Objects.equals(name, accounts.name) && Objects.equals(description, accounts.description) && Objects.equals(enabled, accounts.enabled) && Objects.equals(createdOn, accounts.createdOn) && Objects.equals(lastUpdatedOn, accounts.lastUpdatedOn);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, description, enabled, createdOn, lastUpdatedOn);
        result = 31 * result + Arrays.hashCode(sid);
        return result;
    }
}
