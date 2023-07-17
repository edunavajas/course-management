package com.garajeideas.coursemanagement.rest.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class CourseNotFoundException extends AbstractThrowableProblem {

    public CourseNotFoundException(Long courseId) {
        super(null, "Not found", Status.NOT_FOUND, String.format("Course with id %s not found", courseId));
    }
}
