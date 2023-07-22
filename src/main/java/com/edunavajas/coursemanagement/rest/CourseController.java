package com.edunavajas.coursemanagement.rest;

import com.edunavajas.coursemanagement.openapi.web.rest.CoursesApi;
import com.edunavajas.coursemanagement.openapi.web.rest.dtos.CourseRequest;
import com.edunavajas.coursemanagement.openapi.web.rest.dtos.CourseResponse;
import com.edunavajas.coursemanagement.openapi.web.rest.dtos.CourseStudentsResponse;
import com.edunavajas.coursemanagement.openapi.web.rest.dtos.CoursesPageResponse;
import com.edunavajas.coursemanagement.rest.validator.CourseRequestValidator;
import com.edunavajas.coursemanagement.security.AuthoritiesConstants;
import com.edunavajas.coursemanagement.service.CourseService;
import com.edunavajas.coursemanagement.service.CourseStudentService;
import com.edunavajas.coursemanagement.service.mapper.CourseMapper;
import com.edunavajas.coursemanagement.service.mapper.CourseStudentMapper;
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
    @PreAuthorize(value = "hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<CourseResponse> addCourse(@Valid CourseRequest courseRequest) {
        CourseResponse courseResponse = (courseMapper.toResponse(
                (courseService.addCourse(courseMapper.toDTO(courseRequest)))));
        return ResponseEntity.ok().body(courseResponse);
    }

    @Override
    @PreAuthorize(value = "hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteCourse(Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<CourseResponse> getCourseById(Long id) {
        return ResponseEntity.ok(courseMapper.toResponse(courseService.getCourseById(id)));
    }

    @Override
    public ResponseEntity<CoursesPageResponse> getCourses(Integer page, Integer pageSize, String name, OffsetDateTime startDate, OffsetDateTime endDate) {
        return ResponseEntity
                .ok(courseMapper.toPageResponse(courseService.getCourses(page, pageSize, name, startDate, endDate)));
    }

    @Override
    @PreAuthorize(value = "hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<CourseResponse> updateCourse(Long id, @Valid CourseRequest courseRequest) {
        return ResponseEntity.ok(courseMapper.toResponse(courseService.updateCourse(id, courseMapper.toDTO(courseRequest))));
    }

    @Override
    @PreAuthorize(value = "hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> enrollStudentInCourse(Long courseId, Long studentId) {
        courseStudentService.enrollStudentInCourse(courseId, studentId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<CourseStudentsResponse>> getStudentsInCourse(Long courseId) {
        return ResponseEntity.ok(courseStudentMapper.toResponseList(courseStudentService.getStudentsByCourseId(courseId)));
    }

    @Override
    @PreAuthorize(value = "hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> unenrollStudentFromCourse(Long courseId, Long studentId) {
        courseStudentService.unenrollStudentFromCourse(courseId, studentId);
        return ResponseEntity.noContent().build();
    }


}
