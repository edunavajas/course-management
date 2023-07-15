package com.garajeideas.coursemanagement.rest;

import com.garajeideas.coursemanagement.openapi.web.rest.StudentsApi;
import com.garajeideas.coursemanagement.openapi.web.rest.dtos.Student;
import com.garajeideas.coursemanagement.openapi.web.rest.dtos.StudentsPageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class StudentController implements StudentsApi {


	@Override
	public ResponseEntity<Student> addStudent(Student student) {
		return null;
	}

	@Override
	public ResponseEntity<Void> deleteStudent(Long id) {
		return null;
	}

	@Override
	public ResponseEntity<Student> getStudentById(Long id) {
		return null;
	}

	@Override
	public ResponseEntity<List<StudentsPageResponse>> getStudents(String name, LocalDate birthDate) {
		return null;
	}

	@Override
	public ResponseEntity<Void> updateStudent(Long id, Student student) {
		return null;
	}
}
