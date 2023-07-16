package com.garajeideas.coursemanagement.rest;

import com.garajeideas.coursemanagement.openapi.web.rest.CoursesApi;
import com.garajeideas.coursemanagement.openapi.web.rest.dtos.CourseRequest;
import com.garajeideas.coursemanagement.openapi.web.rest.dtos.CourseResponse;
import com.garajeideas.coursemanagement.openapi.web.rest.dtos.CourseStudentsResponse;
import com.garajeideas.coursemanagement.openapi.web.rest.dtos.CoursesPageResponse;
import com.garajeideas.coursemanagement.rest.validator.CourseRequestValidator;
import com.garajeideas.coursemanagement.security.AuthoritiesConstants;
import com.garajeideas.coursemanagement.service.CourseService;
import com.garajeideas.coursemanagement.service.CourseStudentService;
import com.garajeideas.coursemanagement.service.mapper.CourseMapper;
import com.garajeideas.coursemanagement.service.mapper.CourseStudentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CourseController implements CoursesApi {


    private final CourseMapper courseMapper;
    private final CourseService courseService;
    private final CourseStudentService courseStudentService;
    private final CourseStudentMapper courseStudentMapper;

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
    public ResponseEntity<CourseResponse> updateCourse(Long id, @Valid CourseRequest courseRequest) {
        return ResponseEntity.ok(courseMapper.toResponse(courseService.updateCourse(id, courseMapper.toDTO(courseRequest))));
    }

    @Override
    public ResponseEntity<Void> enrollStudentInCourse(Long courseId, Long studentId) {
        courseStudentService.enrollStudentInCourse(courseId, studentId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<CourseStudentsResponse>> getStudentsInCourse(Long courseId) {
        return ResponseEntity.ok(courseStudentMapper.toResponseList(courseStudentService.getStudentsByCourseId(courseId)));
    }

    @Override
    public ResponseEntity<Void> unenrollStudentFromCourse(Long courseId, Long studentId) {
        courseStudentService.unenrollStudentFromCourse(courseId, studentId);
        return ResponseEntity.noContent().build();
    }


}
