package com.edunavajas.coursemanagement.rest.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class CourseStudentDuplicityException extends AbstractThrowableProblem {

    public CourseStudentDuplicityException(Long studentId, Long courseId) {
        super(null, "Student found in this course", Status.ALREADY_REPORTED, String.format("Student with id %s already belongs to course with id %s", studentId, courseId));
    }
}
