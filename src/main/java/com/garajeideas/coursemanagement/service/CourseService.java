package com.garajeideas.coursemanagement.service;

import com.garajeideas.coursemanagement.dtos.CoursePage;

import java.time.OffsetDateTime;

public interface CourseService {
    CoursePage getCourses(Integer page, Integer pageSize, String name, OffsetDateTime startDate, OffsetDateTime endDate);

}
