package com.garajeideas.coursemanagement.rest;

import com.garajeideas.coursemanagement.openapi.web.rest.CoursesApi;
import com.garajeideas.coursemanagement.openapi.web.rest.dtos.*;
import com.garajeideas.coursemanagement.rest.validator.CourseRequestValidator;
import com.garajeideas.coursemanagement.security.AuthoritiesConstants;
import com.garajeideas.coursemanagement.service.CourseService;
import com.garajeideas.coursemanagement.service.mapper.CourseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;


import javax.validation.Valid;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CourseController implements CoursesApi {


    private final CourseMapper courseMapper;
    private final CourseService courseService;

    @InitBinder("courseRequest")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new CourseRequestValidator());
    }

    @Override
    public ResponseEntity<CourseResponse> addCourse(@Valid CourseRequest courseRequest) {
        CourseResponse courseResponse = (courseMapper.toResponse(
                (courseService.addCourse(courseMapper.toDTO(courseRequest)))));
        return ResponseEntity.ok().body(courseResponse);
    }

    @Override
    public ResponseEntity<Void> deleteCourse(Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> enrollStudentInCourse(Long courseId, StudentsPageResponse studentsPageResponse) {
        return null;
    }

    @Override
    public ResponseEntity<CourseResponse> getCourseById(Long id) {
        return ResponseEntity.ok(courseMapper.toResponse(courseService.getCourseById(id)));
    }

    @Override
    @PreAuthorize(value = "hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<CoursesPageResponse> getCourses(Integer page, Integer pageSize, String name, OffsetDateTime startDate, OffsetDateTime endDate) {
        return ResponseEntity
                .ok(courseMapper.toPageResponse(courseService.getCourses(page, pageSize, name, startDate, endDate)));
    }

    @Override
    public ResponseEntity<List<StudentResponse>> getStudentsInCourse(Long courseId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> unenrollStudentFromCourse(Long courseId, Long studentId) {
        return null;
    }

    @Override
    public ResponseEntity<CourseResponse> updateCourse(Long id,@Valid CourseRequest courseRequest) {
        return ResponseEntity.ok(courseMapper.toResponse(courseService.updateCourse(id, courseMapper.toDTO(courseRequest))));
    }
}
