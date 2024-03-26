package com.equaled.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "teacher_has_students")
public class TeacherHasStudents  {
    @Basic
    @Column(name = "teacher_id")
    private Integer teacherId;
    @Basic
    @Column(name = "student_id")
    private Integer studentId;

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeacherHasStudents that = (TeacherHasStudents) o;
        return Objects.equals(teacherId, that.teacherId) && Objects.equals(studentId, that.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teacherId, studentId);
    }
}
