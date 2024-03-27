package com.equaled.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table (name = "dashboard")
@Getter @Setter @NoArgsConstructor
public class Dashboard extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id", nullable = false)
    private Subject subject;

    @Column(name = "exam_id")
    private String examId;

    @Column(name = "start_time")
    private long startTime;

    @Column(name = "title")
    private String title;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dashboard dashboard = (Dashboard) o;
        return user == dashboard.user && subject == dashboard.subject && Objects.equals(examId, dashboard.examId) && Objects.equals(startTime, dashboard.startTime) && Objects.equals(title, dashboard.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, subject, examId, startTime, title);
    }
}
