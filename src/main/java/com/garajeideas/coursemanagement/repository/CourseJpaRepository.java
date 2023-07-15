package com.garajeideas.coursemanagement.repository;

import com.garajeideas.coursemanagement.domain.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseJpaRepository extends JpaRepository<CourseEntity, Long> {


}
