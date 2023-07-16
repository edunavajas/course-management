package com.garajeideas.coursemanagement.rest.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class StudentNotFoundException extends AbstractThrowableProblem {

    public StudentNotFoundException(Long studentId) {
        super(null, "Not found", Status.NOT_FOUND, String.format("Student with id %s not found", studentId));
    }

    public StudentNotFoundException(Long studentId, String customMessage, Status status) {

        super(null, "Not found", status, String.format(customMessage, studentId));
    }
}
