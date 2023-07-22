package com.edunavajas.coursemanagement.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Table(name = "course_student")
public class CourseStudentEntity implements Serializable {

    private static final long serialVersionUID = -6171775932178901032L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_student_seq")
    @SequenceGenerator(name = "course_student_seq", sequenceName = "course_student_seq", allocationSize = 1, initialValue = 11)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "course_id", nullable = false)
    private Long courseId;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private StudentEntity student;

    @NotNull(message = "La fecha de inscripción es requerida")
    @Column(name = "enrollment_date", nullable = false)
    private Instant enrollmentDate;

    @Column(name = "drop_date")
    private Instant dropDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public StudentEntity getStudent() {
        return student;
    }

    public void setStudent(StudentEntity student) {
        this.student = student;
    }

    public Instant getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(Instant enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public Instant getDropDate() {
        return dropDate;
    }

    public void setDropDate(Instant dropDate) {
        this.dropDate = dropDate;
    }
}
