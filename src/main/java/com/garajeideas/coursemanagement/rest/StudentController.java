package com.garajeideas.coursemanagement.rest;

import com.garajeideas.coursemanagement.openapi.web.rest.StudentsApi;
import com.garajeideas.coursemanagement.openapi.web.rest.dtos.Student;
import com.garajeideas.coursemanagement.openapi.web.rest.dtos.StudentRequest;
import com.garajeideas.coursemanagement.openapi.web.rest.dtos.StudentResponse;
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
	public ResponseEntity<StudentResponse> addStudent(StudentRequest studentRequest) {
		return null;
	}

	@Override
	public ResponseEntity<Void> deleteStudent(Long id) {
		return null;
	}

	@Override
	public ResponseEntity<StudentResponse> getStudentById(Long id) {
		return null;
	}

	@Override
	public ResponseEntity<List<StudentsPageResponse>> getStudents(String name, LocalDate birthDate) {
		return null;
	}

	@Override
	public ResponseEntity<Void> updateStudent(Long id, StudentRequest studentRequest) {
		return null;
	}
}
