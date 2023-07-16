package com.garajeideas.coursemanagement.service.impl;

import com.garajeideas.coursemanagement.domain.CourseEntity;
import com.garajeideas.coursemanagement.domain.CourseStudentEntity;
import com.garajeideas.coursemanagement.domain.StudentEntity;
import com.garajeideas.coursemanagement.dtos.CourseStudent;
import com.garajeideas.coursemanagement.jpa.mapper.CourseStudentEntityMapper;
import com.garajeideas.coursemanagement.jpa.repository.CourseJpaRepository;
import com.garajeideas.coursemanagement.jpa.repository.CourseStudentJpaRepository;
import com.garajeideas.coursemanagement.jpa.repository.StudentJpaRepository;
import com.garajeideas.coursemanagement.rest.exception.CourseNotFoundException;
import com.garajeideas.coursemanagement.rest.exception.CourseStudentDuplicityException;
import com.garajeideas.coursemanagement.rest.exception.CourseStudentNotFoundException;
import com.garajeideas.coursemanagement.rest.exception.StudentNotFoundException;
import com.garajeideas.coursemanagement.service.CourseStudentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
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

        Optional<CourseStudentEntity> courseStudentExisting = courseStudentJpaRepository.getCourseStudentEntitiesByStudent_IdAndCourseId(studentId, courseId);
        if (courseStudentExisting.isPresent()) {
            throw new CourseStudentDuplicityException(studentId, courseId);
        }
        courseStudentJpaRepository.save(CourseStudentEntity.builder().courseId(courseId).student(studentEntity.get()).enrollmentDate(Instant.now()).build());
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
