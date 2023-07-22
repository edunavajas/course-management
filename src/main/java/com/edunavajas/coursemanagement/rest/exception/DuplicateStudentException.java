package com.edunavajas.coursemanagement.rest.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class DuplicateStudentException extends AbstractThrowableProblem {

    public DuplicateStudentException() {
        super(null, "Not created", Status.NOT_MODIFIED, "A student with the same name already exists");
    }
}
