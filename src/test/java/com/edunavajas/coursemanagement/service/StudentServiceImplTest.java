package com.edunavajas.coursemanagement.service;

import com.edunavajas.coursemanagement.dtos.Student;
import com.edunavajas.coursemanagement.dtos.StudentPage;
import com.edunavajas.coursemanagement.jpa.mapper.StudentEntityMapper;
import com.edunavajas.coursemanagement.jpa.repository.StudentJpaRepository;
import com.edunavajas.coursemanagement.rest.exception.StudentNotFoundException;
import com.edunavajas.coursemanagement.domain.StudentEntity;
import com.edunavajas.coursemanagement.rest.exception.DuplicateStudentException;
import com.edunavajas.coursemanagement.service.impl.StudentServiceImpl;
import com.edunavajas.coursemanagement.service.specification.StudentSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentJpaRepository studentJpaRepository;

    @Mock
    private StudentEntityMapper studentEntityMapper;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    void getStudentsTest() {
        Page<StudentEntity> mockPage = new PageImpl<>(Collections.singletonList(new StudentEntity()));
        when(studentJpaRepository.findAll(any(StudentSpecification.class), any(Pageable.class))).thenReturn(mockPage);

        StudentPage result = studentService.getStudents(0, 1, "firstName", "lastName", LocalDate.now());

        verify(studentJpaRepository).findAll(any(StudentSpecification.class), any(Pageable.class));
        verify(studentEntityMapper).toDTOList(anyList());
        assertNotNull(result);
    }

    @Test
    void addStudentTest() {
        Student student = new Student();
        StudentEntity studentEntity = new StudentEntity();
        when(studentEntityMapper.toEntity(any(Student.class))).thenReturn(studentEntity);
        when(studentJpaRepository.save(any(StudentEntity.class))).thenReturn(studentEntity);
        when(studentEntityMapper.toDTO(any(StudentEntity.class))).thenReturn(student);

        Student result = studentService.addStudent(student);

        verify(studentEntityMapper).toEntity(any(Student.class));
        verify(studentJpaRepository).save(any(StudentEntity.class));
        verify(studentEntityMapper).toDTO(any(StudentEntity.class));
        assertNotNull(result);
    }

    @Test
    void updateStudentTest() {
        StudentEntity existingEntity = new StudentEntity();
        StudentEntity updatedEntity = new StudentEntity();
        Student student = new Student();

        when(studentJpaRepository.findById(anyLong())).thenReturn(Optional.of(existingEntity));
        when(studentEntityMapper.toEntity(any(Student.class))).thenReturn(updatedEntity);
        when(studentJpaRepository.save(any(StudentEntity.class))).thenReturn(updatedEntity);
        when(studentEntityMapper.toDTO(any(StudentEntity.class))).thenReturn(student);


        Student result = studentService.updateStudent(1L, student);

        verify(studentJpaRepository).findById(anyLong());
        verify(studentEntityMapper).toEntity(any(Student.class));
        verify(studentJpaRepository).save(any(StudentEntity.class));
        verify(studentEntityMapper).toDTO(any(StudentEntity.class));
        assertNotNull(result);
    }

    @Test
    void deleteStudentTest() {
        when(studentJpaRepository.findById(anyLong())).thenReturn(Optional.of(new StudentEntity()));

        studentService.deleteStudent(1L);

        verify(studentJpaRepository).findById(anyLong());
        verify(studentJpaRepository).deleteById(anyLong());
    }

    @Test
    void getStudentByIdTest() {
        Student student = new Student();
        when(studentJpaRepository.findById(anyLong())).thenReturn(Optional.of(new StudentEntity()));
        when(studentEntityMapper.toDTO(any(StudentEntity.class))).thenReturn(student);
        Student result = studentService.getStudentById(1L);

        verify(studentJpaRepository).findById(anyLong());
        verify(studentEntityMapper).toDTO(any(StudentEntity.class));
        assertNotNull(result);
    }

    @Test
    void addStudentThrowsExceptionWhenDuplicatesFoundTest() {
        StudentEntity studentEntity = new StudentEntity();
        when(studentEntityMapper.toEntity(any(Student.class))).thenReturn(studentEntity);
        when(studentJpaRepository.findAll(any(StudentSpecification.class))).thenReturn(Collections.singletonList(studentEntity));

        Student student = new Student();

        assertThrows(DuplicateStudentException.class, () -> studentService.addStudent(student));
    }

    @Test
    void updateStudentThrowsExceptionWhenStudentNotFoundTest() {
        when(studentJpaRepository.findById(anyLong())).thenReturn(Optional.empty());

        Student student = new Student();

        assertThrows(StudentNotFoundException.class, () -> studentService.updateStudent(1L, student));
    }

    @Test
    void updateStudentThrowsExceptionWhenDuplicatesFoundTest() {
        StudentEntity existingEntity = new StudentEntity();
        StudentEntity updatedEntity = new StudentEntity();
        when(studentJpaRepository.findById(anyLong())).thenReturn(Optional.of(existingEntity));
        when(studentEntityMapper.toEntity(any(Student.class))).thenReturn(updatedEntity);
        when(studentJpaRepository.findAll(any(StudentSpecification.class))).thenReturn(Collections.singletonList(updatedEntity));

        Student student = new Student();

        assertThrows(DuplicateStudentException.class, () -> studentService.updateStudent(1L, student));
    }

    @Test
    void deleteStudentThrowsExceptionWhenStudentNotFoundTest() {
        when(studentJpaRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> studentService.deleteStudent(1L));
    }

    @Test
    void getStudentByIdThrowsExceptionWhenStudentNotFoundTest() {
        when(studentJpaRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> studentService.getStudentById(1L));
    }

}
