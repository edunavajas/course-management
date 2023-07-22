package com.edunavajas.coursemanagement.rest.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class DuplicateCourseException extends AbstractThrowableProblem {

    public DuplicateCourseException() {
        super(null, "Not created", Status.NOT_MODIFIED, "A course with the same name already exists");
    }
}
