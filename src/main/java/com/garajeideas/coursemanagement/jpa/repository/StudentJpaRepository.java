package com.garajeideas.coursemanagement.jpa.repository;

import com.garajeideas.coursemanagement.domain.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentJpaRepository extends JpaRepository<StudentEntity, Long> {


}
