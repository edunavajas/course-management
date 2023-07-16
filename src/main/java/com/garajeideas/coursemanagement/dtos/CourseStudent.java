package com.garajeideas.coursemanagement.dtos;

import lombok.NoArgsConstructor;

import java.time.Instant;


@NoArgsConstructor
public class CourseStudent {

	private Long id;
	private Long courseId;
	private Student student;
	private Instant enrollmentDate;
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

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
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
