package com.garajeideas.coursemanagement.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.garajeideas.coursemanagement.CourseManagementApplication;
import com.garajeideas.coursemanagement.dtos.Course;
import com.garajeideas.coursemanagement.dtos.CoursePage;
import com.garajeideas.coursemanagement.openapi.web.rest.dtos.CourseRequest;
import com.garajeideas.coursemanagement.openapi.web.rest.dtos.CourseResponse;
import com.garajeideas.coursemanagement.openapi.web.rest.dtos.CourseStudentsResponse;
import com.garajeideas.coursemanagement.openapi.web.rest.dtos.CoursesPageResponse;
import com.garajeideas.coursemanagement.service.CourseService;
import com.garajeideas.coursemanagement.service.CourseStudentService;
import com.garajeideas.coursemanagement.service.mapper.CourseMapper;
import com.garajeideas.coursemanagement.service.mapper.CourseStudentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WithMockUser
@SpringBootTest(classes = CourseManagementApplication.class)
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @MockBean
    private CourseMapper courseMapper;

    @MockBean
    private CourseStudentService courseStudentService;

    @MockBean
    private CourseStudentMapper courseStudentMapper;

    @Test
    public void addCourseTest() throws Exception {
        CourseRequest courseRequest = CourseRequest.builder().name("Curso").maxStudentCount(5L).startDate(OffsetDateTime.now().plusDays(1)).endDate(OffsetDateTime.now().plusDays(3)).build();
        CourseResponse courseResponse = new CourseResponse();
        when(courseMapper.toDTO(any(CourseRequest.class))).thenReturn(new Course());
        when(courseService.addCourse(any(Course.class))).thenReturn(new Course());
        when(courseMapper.toResponse(any(Course.class))).thenReturn(courseResponse);

        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(courseRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(courseResponse)));
    }

    @Test
    public void deleteCourseTest() throws Exception {
        Long courseId = 1L;

        mockMvc.perform(delete("/courses/" + courseId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(courseService, times(1)).deleteCourse(courseId);
    }

    @Test
    public void getCourseByIdTest() throws Exception {
        Long courseId = 1L;
        CourseResponse courseResponse = new CourseResponse();
        when(courseService.getCourseById(courseId)).thenReturn(new Course());
        when(courseMapper.toResponse(any(Course.class))).thenReturn(courseResponse);

        mockMvc.perform(get("/courses/" + courseId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(courseResponse)));
    }

    @Test
    public void getCoursesTest() throws Exception {
        Integer page = 0;
        Integer pageSize = 10;
        CoursesPageResponse coursesPageResponse = new CoursesPageResponse();
        CourseResponse courseResponse = CourseResponse.builder().name("Curso").maxStudentCount(5L).startDate(OffsetDateTime.now().plusDays(1)).endDate(OffsetDateTime.now().plusDays(3)).build();
        List<CourseResponse> courseRequestList = Collections.singletonList(courseResponse);
        coursesPageResponse.setContent(courseRequestList);
        when(courseService.getCourses(anyInt(), anyInt(), anyString(), any(OffsetDateTime.class), any(OffsetDateTime.class))).thenReturn(new CoursePage());
        when(courseMapper.toPageResponse(any(CoursePage.class))).thenReturn(coursesPageResponse);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String response = objectMapper.writeValueAsString(coursesPageResponse);

        mockMvc.perform(get("/courses")
                        .param("page", page.toString())
                        .param("pageSize", pageSize.toString())
                        .with(user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateCourseTest() throws Exception {
        Long courseId = 1L;
        CourseRequest courseRequest = CourseRequest.builder().name("Curso").maxStudentCount(5L).startDate(OffsetDateTime.now().plusDays(1)).endDate(OffsetDateTime.now().plusDays(3)).build();
        CourseResponse courseResponse = new CourseResponse();
        when(courseMapper.toDTO(any(CourseRequest.class))).thenReturn(new Course());
        when(courseService.updateCourse(eq(courseId), any(Course.class))).thenReturn(new Course());
        when(courseMapper.toResponse(any(Course.class))).thenReturn(courseResponse);

        mockMvc.perform(put("/courses/" + courseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(courseRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(courseResponse)));
    }

    @Test
    public void enrollStudentInCourseTest() throws Exception {
        Long courseId = 1L;
        Long studentId = 1L;

        mockMvc.perform(post("/courses/" + courseId + "/students/" + studentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(courseStudentService, times(1)).enrollStudentInCourse(courseId, studentId);
    }

    @Test
    public void getStudentsInCourseTest() throws Exception {
        Long courseId = 1L;
        List<CourseStudentsResponse> courseStudentsResponseList = new ArrayList<>();
        when(courseStudentService.getStudentsByCourseId(courseId)).thenReturn(new ArrayList<>());
        when(courseStudentMapper.toResponseList(anyList())).thenReturn(courseStudentsResponseList);

        mockMvc.perform(get("/courses/" + courseId + "/students")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(courseStudentsResponseList)));
    }

    @Test
    public void unenrollStudentFromCourseTest() throws Exception {
        Long courseId = 1L;
        Long studentId = 1L;

        mockMvc.perform(delete("/courses/" + courseId + "/students/" + studentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(courseStudentService, times(1)).unenrollStudentFromCourse(courseId, studentId);
    }

}
