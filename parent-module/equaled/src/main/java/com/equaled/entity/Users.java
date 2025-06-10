package com.equaled.entity;

import com.equaled.value.EqualEdEnums;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor
public class Users extends BaseEntity{
    private static final long serialVersionUID = 7158036017058596386L;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private EqualEdEnums.UserRole role;
    @Column(name = "enabled")
    private int enabled;
    @Column(name = "last_login")
    private Instant lastLogin;
    @Column(name = "last_updated_on")
    private Instant lastUpdatedOn;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "dob")
    private LocalDate dob;
    @Column(name = "country_code")
    private String countryCode;
    @Column(name = "state_code")
    private String stateCode;
    @Column(name = "school_name")
    private String schoolName;

    @ManyToOne
    @JoinColumn(name = "related_account", referencedColumnName = "id", nullable = false)
    private Accounts relatedAccount;

    @ManyToOne
    @JoinColumn(name = "year_group_id", referencedColumnName = "id", nullable = true)
    private YearGroup yearGroup;

    @ManyToMany(cascade={CascadeType.ALL})
    @JoinTable(
            name = "teacher_has_students",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Users> students;

    @ManyToMany(mappedBy="students")
    private Set<Users> teachers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Users)) return false;
        Users users = (Users) o;
        return id == users.id &&
                enabled == users.enabled &&
                Objects.equals(username, users.username) &&
                Objects.equals(password, users.password) &&
                Objects.equals(email, users.email) &&
                role == users.role &&
                Objects.equals(firstname, users.firstname) &&
                Objects.equals(lastname, users.lastname) &&
                Objects.equals(dob, users.dob) &&
                Objects.equals(countryCode, users.countryCode) &&
                Objects.equals(schoolName, users.schoolName) &&
                Objects.equals(stateCode, users.stateCode) &&
                Objects.equals(lastLogin, users.lastLogin) &&
                Objects.equals(lastUpdatedOn, users.lastUpdatedOn) &&
                Objects.equals(relatedAccount, users.relatedAccount) &&
                Objects.equals(yearGroup, users.yearGroup) &&
                Arrays.equals(sid, users.sid);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, username, password, email, role, enabled, firstname, lastname,
                dob, countryCode, schoolName, stateCode, lastLogin, lastUpdatedOn, relatedAccount, yearGroup);
        result = 31 * result + Arrays.hashCode(sid);
        return result;
    }
}