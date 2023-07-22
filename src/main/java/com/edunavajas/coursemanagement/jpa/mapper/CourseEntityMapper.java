package com.edunavajas.coursemanagement.jpa.mapper;

import com.edunavajas.coursemanagement.dtos.Course;
import com.edunavajas.coursemanagement.domain.CourseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseEntityMapper {

    List<Course> toDTOList(List<CourseEntity> courseEntityList);

    @Mapping(source = "registrationDate", target = "registrationDate")
    CourseEntity toEntity(Course course);

    Course toDTO(CourseEntity courseEntity);
}
