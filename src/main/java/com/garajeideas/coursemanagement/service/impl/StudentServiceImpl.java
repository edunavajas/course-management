package com.garajeideas.coursemanagement.service.impl;

import com.garajeideas.coursemanagement.domain.StudentEntity;
import com.garajeideas.coursemanagement.dtos.Student;
import com.garajeideas.coursemanagement.dtos.StudentPage;
import com.garajeideas.coursemanagement.jpa.mapper.StudentEntityMapper;
import com.garajeideas.coursemanagement.jpa.repository.StudentJpaRepository;
import com.garajeideas.coursemanagement.rest.exception.StudentNotFoundException;
import com.garajeideas.coursemanagement.service.StudentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentJpaRepository studentJpaRepository;
    private final StudentEntityMapper studentEntityMapper;


    @Override
    public StudentPage getStudents(Integer page, Integer pageSize, String firstName, String lastName, LocalDate birthDate) {
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<StudentEntity> studentsPage = studentJpaRepository.findAll(pageable);

        List<StudentEntity> students = studentsPage.getContent();

        return StudentPage.builder()
                .content(studentEntityMapper.toDTOList(students))
                .page(studentsPage.getNumber())
                .pageSize(studentsPage.getSize())
                .totalItems((int) studentsPage.getTotalElements())
                .totalPages(studentsPage.getTotalPages())
                .build();
    }

    @Override
    public Student addStudent(Student student) {
        StudentEntity studentEntity = studentEntityMapper.toEntity(student);
        return studentEntityMapper.toDTO(studentJpaRepository.save(studentEntity));
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        StudentEntity studentEntity = studentJpaRepository.findById(id)
                .map(existingEntity -> {
                    StudentEntity updatedEntity = studentEntityMapper.toEntity(student);
                    updatedEntity.setId(existingEntity.getId());
                    return updatedEntity;
                })
                .orElseThrow(() -> new StudentNotFoundException(id));
        return studentEntityMapper.toDTO(studentJpaRepository.save(studentEntity));
    }

    @Override
    public void deleteStudent(Long id) {
        Optional<StudentEntity> studentEntity = studentJpaRepository.findById(id);

        if (!studentEntity.isPresent()) {
            throw new StudentNotFoundException(id);
        }
        studentJpaRepository.deleteById(id);
    }

    @Override
    public Student getStudentById(Long id) {
        Optional<StudentEntity> studentEntity = studentJpaRepository.findById(id);

        if (!studentEntity.isPresent()) {
            throw new StudentNotFoundException(id);
        }
        return studentEntityMapper.toDTO(studentEntity.get());
    }
}