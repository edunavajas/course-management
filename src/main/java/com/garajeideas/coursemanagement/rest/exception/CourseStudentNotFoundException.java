package com.garajeideas.coursemanagement.rest.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class CourseStudentNotFoundException extends AbstractThrowableProblem {

    public CourseStudentNotFoundException(Long studentId, Long courseId) {
        super(null, "Not found", Status.NOT_FOUND, String.format("Student with id %s not found in course with id %s", studentId, courseId));
    }
}
