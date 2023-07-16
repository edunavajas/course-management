package com.garajeideas.coursemanagement.rest.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class CourseFullException extends AbstractThrowableProblem {

    public CourseFullException() {
        super(null, "Full course", Status.CONFLICT, "The limit of students allowed in this course has been reached");
    }
}
