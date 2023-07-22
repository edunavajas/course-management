package com.edunavajas.coursemanagement.rest.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class StudentNotFoundException extends AbstractThrowableProblem {

    public StudentNotFoundException(Long studentId) {
        super(null, "Not found", Status.NOT_FOUND, String.format("Student with id %s not found", studentId));
    }
}
