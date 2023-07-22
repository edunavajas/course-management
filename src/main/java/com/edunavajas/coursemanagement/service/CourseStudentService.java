package com.edunavajas.coursemanagement.service;

import com.edunavajas.coursemanagement.dtos.CourseStudent;

import java.util.List;

public interface CourseStudentService {
    List<CourseStudent> getStudentsByCourseId(Long courseId);

    void enrollStudentInCourse(Long courseId, Long studentId);

    int getActiveStudentsNumberByCourseId(Long courseId);

    void unenrollStudentFromCourse(Long courseId, Long studentId);
}
