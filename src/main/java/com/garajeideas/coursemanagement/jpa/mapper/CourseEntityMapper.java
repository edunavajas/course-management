package com.garajeideas.coursemanagement.jpa.mapper;

import com.garajeideas.coursemanagement.domain.CourseEntity;
import com.garajeideas.coursemanagement.dtos.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseEntityMapper {

    CourseEntityMapper INSTANCE = Mappers.getMapper(CourseEntityMapper.class);

    List<Course> toDTOList(List<CourseEntity> courseEntityList);

    @Mapping(source = "registrationDate", target = "registrationDate")
    CourseEntity toEntity(Course course);

    Course toDTO(CourseEntity courseEntity);
}
