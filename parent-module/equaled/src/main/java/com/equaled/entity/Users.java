package com.equaled.entity;

import com.equaled.value.EqualEdEnums;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

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
    private long lastLogin;
    @Column(name = "last_updated_on")
    private long lastUpdatedOn;

    @ManyToOne
    @JoinColumn(name = "related_account", referencedColumnName = "id", nullable = false)
    private Accounts relatedAccount;

    @ManyToOne
    @JoinColumn(name = "year_group_id", referencedColumnName = "id", nullable = true)
    private YearGroup yearGroup;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return id == users.id && enabled == users.enabled && relatedAccount == users.relatedAccount
                && Arrays.equals(sid, users.sid) && Objects.equals(username, users.username)
                && Objects.equals(password, users.password) && Objects.equals(email, users.email)
                && Objects.equals(role, users.role) && Objects.equals(lastLogin, users.lastLogin)
                && Objects.equals(lastUpdatedOn, users.lastUpdatedOn) && Objects.equals(yearGroup, users.yearGroup);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, username, password, email, role, enabled, lastLogin, lastUpdatedOn, relatedAccount, yearGroup);
        result = 31 * result + Arrays.hashCode(sid);
        return result;
    }
}
