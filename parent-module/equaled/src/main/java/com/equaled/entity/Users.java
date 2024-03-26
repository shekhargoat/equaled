package com.equaled.entity;

import com.equaled.value.EqualEdEnums;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor
public class Users extends BaseEntity{
    @Basic
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "password")
    private String password;
    @Column(name = "roles")
    @Enumerated(EnumType.STRING)
    private EqualEdEnums.UserRoleType roles;
    @Basic
    @Column(name = "enabled")
    private Integer enabled;
    @Basic
    @Column(name = "last_login")
    private Timestamp lastLogin;
    @Basic
    @Column(name = "last_updated_on")
    private Timestamp lastUpdatedOn;
    @ManyToOne
    @JoinColumn(name = "year_group_id", referencedColumnName = "id")
    private YearGroup yearGroupId;
    @ManyToOne
    @JoinColumn(name = "related_account", referencedColumnName = "id")
    private Accounts relatedAccount;

    @ManyToMany
    @JoinTable(
            name = "teacher_has_students",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Users> students;

    @ManyToMany
    @JoinTable(
            name = "teacher_has_students",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    private Set<Users> teachers;
}
