package com.garajeideas.coursemanagement.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_seq")
    @SequenceGenerator(name = "student_seq", sequenceName = "student_seq", allocationSize = 1, initialValue = 21)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "The student's name is required")
    @Size(min = 3, message = "The student's name must be at least 3 characters long")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "The student's last name is required")
    @Size(min = 2, message = "The student's last name must be at least 2 characters long")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "document_number")
    private String documentNumber;

    @NotNull(message = "The student's birth date is required")
    @Past(message = "The birth date must be before the current date")
    @Column(name = "birth_date", nullable = false)
    private Instant birthDate;

    @CreationTimestamp
    @Column(name = "registration_date", nullable = false)
    private Instant registrationDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public Instant getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Instant birthDate) {
        this.birthDate = birthDate;
    }

    public Instant getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Instant registrationDate) {
        this.registrationDate = registrationDate;
    }
}
