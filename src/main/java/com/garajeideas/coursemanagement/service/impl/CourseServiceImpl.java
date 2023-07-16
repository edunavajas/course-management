package com.garajeideas.coursemanagement.service.impl;

import com.garajeideas.coursemanagement.domain.CourseEntity;
import com.garajeideas.coursemanagement.dtos.Course;
import com.garajeideas.coursemanagement.dtos.CoursePage;
import com.garajeideas.coursemanagement.jpa.mapper.CourseEntityMapper;
import com.garajeideas.coursemanagement.jpa.repository.CourseJpaRepository;
import com.garajeideas.coursemanagement.rest.exception.CourseNotFoundException;
import com.garajeideas.coursemanagement.service.CourseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {
	
	private final CourseJpaRepository courseJpaRepository;
	private final CourseEntityMapper courseEntityMapper;

	@Override
	public CoursePage getCourses(Integer page, Integer pageSize, String name, OffsetDateTime startDate, OffsetDateTime endDate) {
		Pageable pageable = PageRequest.of(page, pageSize);

		Page<CourseEntity> coursesPage = courseJpaRepository.findAll(pageable);

		List<CourseEntity> courses = coursesPage.getContent();

		return CoursePage.builder()
				.content(courseEntityMapper.toDTOList(courses))
				.page(coursesPage.getNumber())
				.pageSize(coursesPage.getSize())
				.totalItems((int) coursesPage.getTotalElements())
				.totalPages(coursesPage.getTotalPages())
				.build();
	}

	@Override
	public Course addCourse(Course course) {
		CourseEntity courseEntity = courseEntityMapper.toEntity(course);
		return courseEntityMapper.toDTO(courseJpaRepository.save(courseEntity));
	}

	@Override
	public Course updateCourse(Long id, Course course) {
		CourseEntity courseEntity = courseJpaRepository.findById(id)
				.map(existingEntity -> {
					CourseEntity updatedEntity = courseEntityMapper.toEntity(course);
					updatedEntity.setId(existingEntity.getId());
					return updatedEntity;
				})
				.orElseThrow(() -> new CourseNotFoundException(id));
		return courseEntityMapper.toDTO(courseJpaRepository.save(courseEntity));
	}
	@Override
	public void deleteCourse(Long id) {
		Optional<CourseEntity> courseEntity = courseJpaRepository.findById(id);

		if(!courseEntity.isPresent()) {
			throw new CourseNotFoundException(id);
		}
		courseJpaRepository.deleteById(id);
	}

	@Override
	public Course getCourseById(Long id) {
		Optional<CourseEntity> courseEntity = courseJpaRepository.findById(id);

		if(!courseEntity.isPresent()) {
			throw new CourseNotFoundException(id);
		}
		return courseEntityMapper.toDTO(courseEntity.get());
	}



}
