package com.garajeideas.coursemanagement.service.impl;

import com.garajeideas.coursemanagement.domain.CourseEntity;
import com.garajeideas.coursemanagement.dtos.Course;
import com.garajeideas.coursemanagement.dtos.CoursePage;
import com.garajeideas.coursemanagement.dtos.CourseStudent;
import com.garajeideas.coursemanagement.jpa.mapper.CourseEntityMapper;
import com.garajeideas.coursemanagement.jpa.repository.CourseJpaRepository;
import com.garajeideas.coursemanagement.rest.exception.CourseNotDeletedException;
import com.garajeideas.coursemanagement.rest.exception.CourseNotFoundException;
import com.garajeideas.coursemanagement.rest.exception.CourseSizeException;
import com.garajeideas.coursemanagement.rest.exception.DuplicateCourseException;
import com.garajeideas.coursemanagement.service.CourseService;
import com.garajeideas.coursemanagement.service.CourseStudentService;
import com.garajeideas.coursemanagement.service.specification.CourseSpecification;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
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
    private final CourseStudentService courseStudentService;

    @Override
    public CoursePage getCourses(Integer page, Integer pageSize, String name, OffsetDateTime startDate, OffsetDateTime endDate) {
        Pageable pageable = PageRequest.of(page, pageSize);

        CourseSpecification spec = new CourseSpecification(name, startDate, endDate);
        Page<CourseEntity> coursesPage = courseJpaRepository.findAll(spec, pageable);

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
        try {
            return courseEntityMapper.toDTO(courseJpaRepository.save(courseEntity));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateCourseException();
        }
    }

    @Override
    public Course updateCourse(Long id, Course course) {
        CourseEntity courseEntity = courseJpaRepository.findById(id)
                .map(existingEntity -> {
                    CourseEntity updatedEntity = courseEntityMapper.toEntity(course);
                    updatedEntity.setId(existingEntity.getId());
                    updatedEntity.setRegistrationDate(existingEntity.getRegistrationDate());
                    updatedEntity.setCourseStudents(existingEntity.getCourseStudents());

                    // Si cambia el número de estudiantes máximos se comprueba que sea posible
                    if (!updatedEntity.getMaxStudentCount().equals(existingEntity.getMaxStudentCount())) {
                        // Obtiene los estudiantes activos
                        int activeCourseStudents = courseStudentService.getActiveStudentsNumberByCourseId(id);

                        // Si los usuarios que ya hay activos son mayores a los nuevos usuarios permitidos salta una restricción
                        if (activeCourseStudents > updatedEntity.getMaxStudentCount()) {
                            throw new CourseSizeException();
                        }
                    }
                    return updatedEntity;
                })
                .orElseThrow(() -> new CourseNotFoundException(id));
        try {
            return courseEntityMapper.toDTO(courseJpaRepository.save(courseEntity));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateCourseException();
        }
    }

    @Override
    public void deleteCourse(Long id) {
        Optional<CourseEntity> courseEntity = courseJpaRepository.findById(id);

        if (!courseEntity.isPresent()) {
            throw new CourseNotFoundException(id);
        }

        List<CourseStudent> studentsByCourseId = courseStudentService.getStudentsByCourseId(id);
        if (!studentsByCourseId.isEmpty()) {
            throw new CourseNotDeletedException(id);
        }

        courseJpaRepository.deleteById(id);
    }

    @Override
    public Course getCourseById(Long id) {
        Optional<CourseEntity> courseEntity = courseJpaRepository.findById(id);

        if (!courseEntity.isPresent()) {
            throw new CourseNotFoundException(id);
        }
        return courseEntityMapper.toDTO(courseEntity.get());
    }


}
