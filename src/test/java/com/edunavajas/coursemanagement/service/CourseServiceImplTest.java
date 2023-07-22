package com.edunavajas.coursemanagement.service;

import com.edunavajas.coursemanagement.dtos.Course;
import com.edunavajas.coursemanagement.dtos.CoursePage;
import com.edunavajas.coursemanagement.jpa.mapper.CourseEntityMapper;
import com.edunavajas.coursemanagement.jpa.repository.CourseJpaRepository;
import com.edunavajas.coursemanagement.rest.exception.CourseNotFoundException;
import com.edunavajas.coursemanagement.domain.CourseEntity;
import com.edunavajas.coursemanagement.service.impl.CourseServiceImpl;
import com.edunavajas.coursemanagement.service.specification.CourseSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    private CourseJpaRepository courseJpaRepository;

    @Mock
    private CourseEntityMapper courseEntityMapper;

    @Mock
    private CourseStudentService courseStudentService;

    @InjectMocks
    private CourseServiceImpl courseService;

    @Test
    void getCoursesTest() {

        Page<CourseEntity> mockPage = new PageImpl<>(Collections.singletonList(new CourseEntity()));
        when(courseJpaRepository.findAll(any(CourseSpecification.class), any(Pageable.class))).thenReturn(mockPage);


        CoursePage result = courseService.getCourses(0, 1, "name", OffsetDateTime.now(), OffsetDateTime.now());


        verify(courseJpaRepository).findAll(any(CourseSpecification.class), any(Pageable.class));
        verify(courseEntityMapper).toDTOList(anyList());
        assertNotNull(result);
    }

    @Test
    void addCourseTest() {

        Course course = new Course();
        CourseEntity courseEntity = new CourseEntity();

        // Configurar el mock para devolver un courseEntity no nulo
        when(courseEntityMapper.toEntity(any(Course.class))).thenReturn(courseEntity);
        when(courseJpaRepository.save(any(CourseEntity.class))).thenReturn(courseEntity);

        // Configurar el mock para que el courseEntityMapper devuelva un DTO válido
        when(courseEntityMapper.toDTO(any(CourseEntity.class))).thenReturn(course);


        Course result = courseService.addCourse(course);


        verify(courseEntityMapper).toEntity(any(Course.class));
        verify(courseJpaRepository).save(any(CourseEntity.class));
        verify(courseEntityMapper).toDTO(any(CourseEntity.class));
        assertNotNull(result);
    }

    @Test
    void updateCourseTest_CourseNotFoundException() {

        when(courseJpaRepository.findById(anyLong())).thenReturn(Optional.empty());


        Assertions.assertThrows(CourseNotFoundException.class, () -> {
            courseService.updateCourse(1L, new Course());
        });
    }

    @Test
    void deleteCourseTest_CourseNotFoundException() {

        when(courseJpaRepository.findById(anyLong())).thenReturn(Optional.empty());


        assertThrows(CourseNotFoundException.class, () -> courseService.deleteCourse(1L));
    }

    @Test
    void getCourseByIdTest_CourseNotFoundException() {

        when(courseJpaRepository.findById(anyLong())).thenReturn(Optional.empty());


        assertThrows(CourseNotFoundException.class, () -> courseService.getCourseById(1L));
    }

    @Test
    void updateCourseTest() {

        CourseEntity existingEntity = new CourseEntity();
        existingEntity.setMaxStudentCount(10);

        CourseEntity updatedEntity = new CourseEntity();
        updatedEntity.setMaxStudentCount(10);
        updatedEntity.setName("Test");
        updatedEntity.setId(1L);
        updatedEntity.setStartDate(Instant.now());
        updatedEntity.setEndDate(Instant.now());
        updatedEntity.setRegistrationDate(Instant.now());

        when(courseJpaRepository.findById(anyLong())).thenReturn(Optional.of(existingEntity));
        when(courseEntityMapper.toEntity(any(Course.class))).thenReturn(updatedEntity);
        when(courseEntityMapper.toDTO(any(CourseEntity.class))).thenReturn(new Course());
        when(courseJpaRepository.save(any(CourseEntity.class))).thenReturn(updatedEntity);


        Course course = new Course();
        Course result = courseService.updateCourse(1L, course);


        verify(courseJpaRepository).findById(anyLong());
        verify(courseEntityMapper).toEntity(any(Course.class));
        verify(courseJpaRepository).save(any(CourseEntity.class));

        Assertions.assertNotNull(result);
    }

    @Test
    void deleteCourseTest() {

        when(courseJpaRepository.findById(anyLong())).thenReturn(Optional.of(new CourseEntity()));
        when(courseStudentService.getStudentsByCourseId(anyLong())).thenReturn(Collections.emptyList());


        courseService.deleteCourse(1L);


        verify(courseJpaRepository).findById(anyLong());
        verify(courseStudentService).getStudentsByCourseId(anyLong());
        verify(courseJpaRepository).deleteById(anyLong());
    }

    @Test
    void getCourseByIdTest() {

        when(courseJpaRepository.findById(anyLong())).thenReturn(Optional.of(new CourseEntity()));
        when(courseEntityMapper.toDTO(any(CourseEntity.class))).thenReturn(new Course());

        Course result = courseService.getCourseById(1L);


        verify(courseJpaRepository).findById(anyLong());
        verify(courseEntityMapper).toDTO(any(CourseEntity.class));
        assertNotNull(result);
    }

}

