package com.edunavajas.coursemanagement.service.impl;

import com.edunavajas.coursemanagement.dtos.Student;
import com.edunavajas.coursemanagement.dtos.StudentPage;
import com.edunavajas.coursemanagement.jpa.mapper.StudentEntityMapper;
import com.edunavajas.coursemanagement.jpa.repository.StudentJpaRepository;
import com.edunavajas.coursemanagement.rest.exception.StudentNotFoundException;
import com.edunavajas.coursemanagement.domain.StudentEntity;
import com.edunavajas.coursemanagement.rest.exception.DuplicateStudentException;
import com.edunavajas.coursemanagement.service.StudentService;
import com.edunavajas.coursemanagement.service.specification.StudentSpecification;
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

        StudentSpecification spec = new StudentSpecification(firstName, lastName, birthDate);
        Page<StudentEntity> studentsPage = studentJpaRepository.findAll(spec, pageable);

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
        findDuplicates(studentEntity);
        return studentEntityMapper.toDTO(studentJpaRepository.save(studentEntity));
    }

    private void findDuplicates(StudentEntity studentEntity) {
        StudentSpecification spec = new StudentSpecification(studentEntity.getFirstName(), studentEntity.getLastName(), null);
        List<StudentEntity> students = studentJpaRepository.findAll(spec);
        if (!students.isEmpty()) {
            throw new DuplicateStudentException();
        }
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        StudentEntity studentEntity = studentJpaRepository.findById(id)
                .map(existingEntity -> {
                    StudentEntity updatedEntity = studentEntityMapper.toEntity(student);
                    updatedEntity.setId(existingEntity.getId());
                    updatedEntity.setRegistrationDate(existingEntity.getRegistrationDate());
                    findDuplicates(updatedEntity);
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
