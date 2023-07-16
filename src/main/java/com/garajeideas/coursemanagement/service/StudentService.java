package com.garajeideas.coursemanagement.service;

import com.garajeideas.coursemanagement.dtos.Student;
import com.garajeideas.coursemanagement.dtos.StudentPage;

import java.time.LocalDate;

public interface StudentService {
    StudentPage getStudents(Integer page, Integer pageSize, String firstName, String lastName, LocalDate birthDate);

    Student addStudent(Student student);

    Student updateStudent(Long id, Student student);

    void deleteStudent(Long id);

    Student getStudentById(Long id);
}
