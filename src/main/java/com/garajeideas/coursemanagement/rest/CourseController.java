package com.garajeideas.coursemanagement.rest;

import com.garajeideas.coursemanagement.openapi.web.rest.CoursesApi;
import com.garajeideas.coursemanagement.openapi.web.rest.dtos.Course;
import com.garajeideas.coursemanagement.openapi.web.rest.dtos.CoursesPageResponse;
import com.garajeideas.coursemanagement.openapi.web.rest.dtos.Student;
import com.garajeideas.coursemanagement.openapi.web.rest.dtos.StudentsPageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;


import java.time.OffsetDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CourseController implements CoursesApi {


	@Override
	public ResponseEntity<Course> addCourse(Course course) {
		return null;
	}

	@Override
	public ResponseEntity<Void> deleteCourse(Long id) {
		return null;
	}

	@Override
	public ResponseEntity<Void> enrollStudentInCourse(Long courseId, StudentsPageResponse studentsPageResponse) {
		return null;
	}

	@Override
	public ResponseEntity<Course> getCourseById(Long id) {
		return null;
	}

	@Override
	public ResponseEntity<List<CoursesPageResponse>> getCourses(Integer page, Integer pageSize, String name, OffsetDateTime startDate, OffsetDateTime endDate) {
		return null;
	}

	@Override
	public ResponseEntity<List<Student>> getStudentsInCourse(Long courseId) {
		return null;
	}

	@Override
	public ResponseEntity<Void> unenrollStudentFromCourse(Long courseId, Long studentId) {
		return null;
	}

	@Override
	public ResponseEntity<Void> updateCourse(Long id, Course course) {
		return null;
	}
}
