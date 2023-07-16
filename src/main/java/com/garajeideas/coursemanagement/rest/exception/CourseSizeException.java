package com.garajeideas.coursemanagement.rest.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class CourseSizeException extends AbstractThrowableProblem {

    public CourseSizeException() {
        super(null, "Full course", Status.CONFLICT, "The course size cannot be reduced that much because there are more active students currently taking them.");
    }
}
