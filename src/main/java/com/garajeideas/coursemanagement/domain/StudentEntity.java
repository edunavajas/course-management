package com.garajeideas.coursemanagement.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "student")
public class StudentEntity implements Serializable {

    private static final long serialVersionUID = -6171775932178901032L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "The student's name is required")
    @Size(min = 3, message = "The student's name must be at least 3 characters long")
    @Column(name = "first_name", nullable = false, length = 255)
    private String firstName;

    @NotBlank(message = "The student's last name is required")
    @Size(min = 2, message = "The student's last name must be at least 2 characters long")
    @Column(name = "last_name", nullable = false, length = 255)
    private String lastName;

    @NotNull(message = "The student's birth date is required")
    @Past(message = "The birth date must be before the current date")
    @Column(name = "birth_date", nullable = false)
    private Instant birthDate;
}
