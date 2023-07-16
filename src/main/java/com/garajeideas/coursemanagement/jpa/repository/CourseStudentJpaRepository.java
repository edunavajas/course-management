package com.garajeideas.coursemanagement.jpa.repository;

import com.garajeideas.coursemanagement.domain.CourseStudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseStudentJpaRepository extends JpaRepository<CourseStudentEntity, Long> {


    List<CourseStudentEntity> getCourseStudentEntitiesByCourseId(Long courseId);

    Optional<CourseStudentEntity> getCourseStudentEntitiesByStudent_IdAndCourseId(Long studentId, Long courseId);
}
