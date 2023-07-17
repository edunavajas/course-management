package com.garajeideas.coursemanagement.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.garajeideas.coursemanagement.CourseManagementApplication;
import com.garajeideas.coursemanagement.dtos.Student;
import com.garajeideas.coursemanagement.dtos.StudentPage;
import com.garajeideas.coursemanagement.openapi.web.rest.dtos.StudentRequest;
import com.garajeideas.coursemanagement.openapi.web.rest.dtos.StudentResponse;
import com.garajeideas.coursemanagement.openapi.web.rest.dtos.StudentsPageResponse;
import com.garajeideas.coursemanagement.service.StudentService;
import com.garajeideas.coursemanagement.service.mapper.StudentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WithMockUser
@SpringBootTest(classes = CourseManagementApplication.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @MockBean
    private StudentMapper studentMapper;

    @Test
    void addStudentTest() throws Exception {
        StudentRequest studentRequest = StudentRequest.builder().firstName("Edu").lastName("Navajas").birthDate(LocalDate.of(2000, 01, 01)).documentNumber("122").build();
        StudentResponse studentResponse = new StudentResponse();
        when(studentMapper.toDTO(any(StudentRequest.class))).thenReturn(new Student());
        when(studentService.addStudent(any(Student.class))).thenReturn(new Student());
        when(studentMapper.toResponse(any(Student.class))).thenReturn(studentResponse);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String studentRequestJson = objectMapper.writeValueAsString(studentRequest);
        String studentResponseJson = objectMapper.writeValueAsString(studentResponse);

        mockMvc.perform(post("/students").contentType(MediaType.APPLICATION_JSON)
                .with(user("admin").roles("ADMIN")).content(studentRequestJson)).andExpect(status().isOk()).andExpect(content().json(studentResponseJson));
    }

    @Test
    void deleteStudentTest() throws Exception {
        Long studentId = 1L;

        doNothing().when(studentService).deleteStudent(anyLong());

        mockMvc.perform(delete("/students/{id}", studentId)
                .with(user("admin").roles("ADMIN"))).andExpect(status().isNoContent());
    }

    @Test
    void getStudentByIdTest() throws Exception {
        Long studentId = 1L;
        StudentResponse studentResponse = new StudentResponse();
        when(studentMapper.toResponse(any(Student.class))).thenReturn(studentResponse);
        when(studentService.getStudentById(anyLong())).thenReturn(new Student());

        mockMvc.perform(get("/students/{id}", studentId)
                .with(user("admin").roles("ADMIN"))).andExpect(status().isOk());
    }

    @Test
    void getStudentsTest() throws Exception {
        Integer page = 0;
        Integer pageSize = 10;
        String firstName = "John";
        String lastName = "Doe";
        LocalDate birthDate = LocalDate.of(2000, 1, 1);


        StudentsPageResponse studentsPageResponse = new StudentsPageResponse();
        when(studentMapper.toPageResponse(any(StudentPage.class))).thenReturn(studentsPageResponse);
        when(studentService.getStudents(anyInt(), anyInt(), anyString(), anyString(), any(LocalDate.class))).thenReturn(new StudentPage());

        mockMvc.perform(get("/students").param("page", page.toString()).param("pageSize", pageSize.toString()).param("firstName", firstName).param("lastName", lastName).param("birthDate", birthDate.toString())).andExpect(status().isOk());
    }

    @Test
    void updateStudentTest() throws Exception {
        Long studentId = 1L;
        StudentRequest studentRequest = StudentRequest.builder().firstName("Edu").lastName("Navajas").birthDate(LocalDate.of(2000, 01, 01)).documentNumber("122").build();
        StudentResponse studentResponse = new StudentResponse();
        when(studentMapper.toResponse(any(Student.class))).thenReturn(studentResponse);
        when(studentService.updateStudent(anyLong(), any(Student.class))).thenReturn(new Student());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String studentRequestJson = objectMapper.writeValueAsString(studentRequest);

        mockMvc.perform(put("/students/{id}", studentId)
                .with(user("admin").roles("ADMIN")).contentType(MediaType.APPLICATION_JSON).content(studentRequestJson)).andExpect(status().isOk());
    }
}
