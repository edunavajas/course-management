package com.garajeideas.coursemanagement.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Table(name = "course")
public class CourseEntity implements Serializable {

    private static final long serialVersionUID = -6171775932178901032L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_seq")
    @SequenceGenerator(name = "course_seq", sequenceName = "course_seq", allocationSize = 1, initialValue = 11)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "The course name is required")
    @Size(min = 3, message = "The course name must be at least 3 characters long")
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @NotNull(message = "The maximum number of students is required")
    @Min(value = 1, message = "The maximum number of students must be greater than 0")
    @Column(name = "max_student_count", nullable = false)
    private Integer maxStudentCount;

    @NotNull(message = "The start date is required")
    @FutureOrPresent(message = "The start date must be equal to or later than the current date")
    @Column(name = "start_date", nullable = false)
    private Instant startDate;

    @NotNull(message = "The end date is required")
    @Column(name = "end_date", nullable = false)
    private Instant endDate;

    @NotNull(message = "The registration date is required")
    @Column(name = "registration_date", nullable = false)
    private Instant registrationDate;

    @OneToMany(mappedBy = "courseId", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    private Set<CourseStudentEntity> courseStudents;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMaxStudentCount() {
        return maxStudentCount;
    }

    public void setMaxStudentCount(Integer maxStudentCount) {
        this.maxStudentCount = maxStudentCount;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Instant getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Instant registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Set<CourseStudentEntity> getCourseStudents() {
        return courseStudents;
    }

    public void setCourseStudents(Set<CourseStudentEntity> courseStudents) {
        this.courseStudents = courseStudents;
    }
}
