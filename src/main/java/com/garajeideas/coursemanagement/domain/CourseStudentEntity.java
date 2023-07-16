package com.garajeideas.coursemanagement.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
