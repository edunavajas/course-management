package com.edunavajas.coursemanagement.service;

import com.edunavajas.coursemanagement.dtos.Course;
import com.edunavajas.coursemanagement.dtos.CoursePage;

import java.time.OffsetDateTime;

public interface CourseService {
    CoursePage getCourses(Integer page, Integer pageSize, String name, OffsetDateTime startDate, OffsetDateTime endDate);

    Course addCourse(Course course);

    Course updateCourse(Long id, Course course);

    void deleteCourse(Long id);

    Course getCourseById(Long id);
    
}
