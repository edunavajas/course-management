package com.garajeideas.coursemanagement.service;

import com.garajeideas.coursemanagement.dtos.CourseStudent;

import java.util.List;

public interface CourseStudentService {
    List<CourseStudent> getStudentsByCourseId(Long courseId);

    void enrollStudentInCourse(Long courseId, Long studentId);

    void unenrollStudentFromCourse(Long courseId, Long studentId);
}
