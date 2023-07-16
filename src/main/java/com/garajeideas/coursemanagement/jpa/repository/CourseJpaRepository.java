package com.garajeideas.coursemanagement.jpa.repository;

import com.garajeideas.coursemanagement.domain.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CourseJpaRepository extends JpaRepository<CourseEntity, Long> {


}
