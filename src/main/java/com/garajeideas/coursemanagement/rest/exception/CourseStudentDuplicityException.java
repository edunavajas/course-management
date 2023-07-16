package com.garajeideas.coursemanagement.rest.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class CourseStudentDuplicityException extends AbstractThrowableProblem {

    public CourseStudentDuplicityException(Long studentId, Long courseId) {
        super(null, "Student found in this course", Status.ALREADY_REPORTED, String.format("Student with id %s already belongs to course with id %s", studentId, courseId));
    }

    public CourseStudentDuplicityException(Long studentId, String customMessage, Status status) {

        super(null, "Student found in this course", status, String.format(customMessage, studentId));
    }
}
