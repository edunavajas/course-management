package com.edunavajas.coursemanagement.service.impl;

import com.edunavajas.coursemanagement.dtos.CourseStudent;
import com.edunavajas.coursemanagement.jpa.mapper.CourseStudentEntityMapper;
import com.edunavajas.coursemanagement.jpa.repository.CourseJpaRepository;
import com.edunavajas.coursemanagement.jpa.repository.CourseStudentJpaRepository;
import com.edunavajas.coursemanagement.jpa.repository.StudentJpaRepository;
import com.edunavajas.coursemanagement.rest.exception.*;
import com.edunavajas.coursemanagement.domain.CourseEntity;
import com.edunavajas.coursemanagement.domain.CourseStudentEntity;
import com.edunavajas.coursemanagement.domain.StudentEntity;
import com.edunavajas.coursemanagement.rest.exception.*;
import com.edunavajas.coursemanagement.service.CourseStudentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
@AllArgsConstructor
public class CourseStudentServiceImpl implements CourseStudentService {

    private final CourseJpaRepository courseJpaRepository;
    private final StudentJpaRepository studentJpaRepository;
    private final CourseStudentEntityMapper courseStudentEntityMapper;
    private final CourseStudentJpaRepository courseStudentJpaRepository;


    @Override
    public List<CourseStudent> getStudentsByCourseId(Long courseId) {
        Optional<CourseEntity> courseEntity = courseJpaRepository.findById(courseId);

        if (!courseEntity.isPresent()) {
            throw new CourseNotFoundException(courseId);
        }

        List<CourseStudentEntity> courseStudentEntitiesByCourseId = courseStudentJpaRepository.getCourseStudentEntitiesByCourseId(courseId);

        return courseStudentEntityMapper.toDTOList(courseStudentEntitiesByCourseId);
    }

    @Override
    public void enrollStudentInCourse(Long courseId, Long studentId) {
        Optional<CourseEntity> courseEntity = courseJpaRepository.findById(courseId);

        if (!courseEntity.isPresent()) {
            throw new CourseNotFoundException(courseId);
        }

        Optional<StudentEntity> studentEntity = studentJpaRepository.findById(studentId);

        if (!studentEntity.isPresent()) {
            throw new StudentNotFoundException(studentId);
        }

        // Buscar si el alumno ya pertenece al curso y no tiene fecha de baja ( es decir, no se ha desapuntado nunca)
        Optional<CourseStudentEntity> courseStudentExisting = courseStudentJpaRepository.getCourseStudentEntitiesByStudent_IdAndCourseId(studentId, courseId);
        if (courseStudentExisting.isPresent() && Objects.isNull(courseStudentExisting.get().getDropDate())) {
            throw new CourseStudentDuplicityException(studentId, courseId);
        }

        // Obtener todos los alumnos que están apuntados al curso y no se han dado de baja
        List<CourseStudentEntity> courseStudentEntitiesByCourseId = courseStudentJpaRepository.getCourseStudentEntitiesByCourseIdAndDropDateIsNull(courseId);

        if (courseEntity.get().getMaxStudentCount() < courseStudentEntitiesByCourseId.size() + 1) {
            throw new CourseFullException();
        }

        // Si el alumno ya estaba apuntado pero con fecha de baja le quitamos la fecha de baja
        if (courseStudentExisting.isPresent() && Objects.nonNull(courseStudentExisting.get().getDropDate())) {
            courseStudentJpaRepository.save(CourseStudentEntity.builder().id(courseStudentExisting.get().getId()).courseId(courseId).student(studentEntity.get()).enrollmentDate(Instant.now()).dropDate(null).build());
        } else { // Sino se apunta
            courseStudentJpaRepository.save(CourseStudentEntity.builder().courseId(courseId).student(studentEntity.get()).enrollmentDate(Instant.now()).build());

        }
    }

    @Override
    public int getActiveStudentsNumberByCourseId(Long courseId) {
        List<CourseStudentEntity> courseStudentEntitiesByCourseId = courseStudentJpaRepository.getCourseStudentEntitiesByCourseIdAndDropDateIsNull(courseId);
        return courseStudentEntitiesByCourseId.size();
    }

    @Override
    public void unenrollStudentFromCourse(Long courseId, Long studentId) {
        Optional<CourseEntity> courseEntity = courseJpaRepository.findById(courseId);

        if (!courseEntity.isPresent()) {
            throw new CourseNotFoundException(courseId);
        }

        Optional<StudentEntity> studentEntity = studentJpaRepository.findById(studentId);

        if (!studentEntity.isPresent()) {
            throw new StudentNotFoundException(studentId);
        }

        Optional<CourseStudentEntity> courseStudentExisting = courseStudentJpaRepository.getCourseStudentEntitiesByStudent_IdAndCourseId(studentId, courseId);
        if (!courseStudentExisting.isPresent()) {
            throw new CourseStudentNotFoundException(studentId, courseId);
        }

        courseStudentExisting.get().setDropDate(Instant.now());

        courseStudentJpaRepository.save(courseStudentExisting.get());
    }

}
