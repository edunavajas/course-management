package com.garajeideas.coursemanagement.rest;

import com.garajeideas.coursemanagement.openapi.web.rest.StudentsApi;
import com.garajeideas.coursemanagement.openapi.web.rest.dtos.StudentRequest;
import com.garajeideas.coursemanagement.openapi.web.rest.dtos.StudentResponse;
import com.garajeideas.coursemanagement.openapi.web.rest.dtos.StudentsPageResponse;
import com.garajeideas.coursemanagement.rest.validator.StudentRequestValidator;
import com.garajeideas.coursemanagement.security.AuthoritiesConstants;
import com.garajeideas.coursemanagement.service.StudentService;
import com.garajeideas.coursemanagement.service.mapper.StudentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@Slf4j
public class StudentController implements StudentsApi {

    private final StudentMapper studentMapper;
    private final StudentService studentService;

    @InitBinder("studentRequest")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new StudentRequestValidator());
    }

    @Override
    @PreAuthorize(value = "hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<StudentResponse> addStudent(@Valid StudentRequest studentRequest) {
        StudentResponse studentResponse = (studentMapper.toResponse(
                (studentService.addStudent(studentMapper.toDTO(studentRequest)))));
        return ResponseEntity.ok().body(studentResponse);
    }

    @Override
    @PreAuthorize(value = "hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteStudent(Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<StudentResponse> getStudentById(Long id) {
        return ResponseEntity.ok(studentMapper.toResponse(studentService.getStudentById(id)));
    }

    @Override
    public ResponseEntity<StudentsPageResponse> getStudents(Integer page, Integer pageSize, String firstName, String lastName, LocalDate birthDate) {
        return ResponseEntity
                .ok(studentMapper.toPageResponse(studentService.getStudents(page, pageSize, firstName, lastName, birthDate)));

    }

    @Override
    @PreAuthorize(value = "hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<StudentResponse> updateStudent(Long id, @Valid StudentRequest studentRequest) {
        return ResponseEntity.ok(studentMapper.toResponse(studentService.updateStudent(id, studentMapper.toDTO(studentRequest))));
    }
}
