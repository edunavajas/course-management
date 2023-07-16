package com.garajeideas.coursemanagement.service.impl;

import com.garajeideas.coursemanagement.domain.CourseEntity;
import com.garajeideas.coursemanagement.dtos.CoursePage;
import com.garajeideas.coursemanagement.repository.CourseJpaRepository;
import com.garajeideas.coursemanagement.service.CourseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;

@Component
@Slf4j
@AllArgsConstructor

public class CourseServiceImpl implements CourseService {
	
	private final CourseJpaRepository courseJpaRepository;
	@Override
	public CoursePage getCourses(Integer page, Integer pageSize, String name, OffsetDateTime startDate, OffsetDateTime endDate) {
		Pageable pageable = PageRequest.of(page, pageSize);

		Page<CourseEntity> coursesPage = courseJpaRepository.findAll(pageable);

		List<CourseEntity> courses = coursesPage.getContent();

		return CoursePage.builder()
				.content(courses)
				.page(coursesPage.getNumber())
				.pageSize(coursesPage.getSize())
				.totalItems((int) coursesPage.getTotalElements())
				.totalPages(coursesPage.getTotalPages())
				.build();
	}
}
