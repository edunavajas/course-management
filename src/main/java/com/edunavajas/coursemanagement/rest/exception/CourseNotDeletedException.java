package com.edunavajas.coursemanagement.rest.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class CourseNotDeletedException extends AbstractThrowableProblem {

    public CourseNotDeletedException(Long courseId) {
        super(null, "Not deleted", Status.NOT_MODIFIED, String.format("Course with id %s can not be deleted because already have students", courseId));
    }
}
