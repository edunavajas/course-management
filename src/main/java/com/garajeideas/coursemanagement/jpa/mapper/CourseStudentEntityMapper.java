package com.garajeideas.coursemanagement.jpa.mapper;

import com.garajeideas.coursemanagement.domain.CourseStudentEntity;
import com.garajeideas.coursemanagement.dtos.CourseStudent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseStudentEntityMapper {

    CourseStudentEntityMapper INSTANCE = Mappers.getMapper(CourseStudentEntityMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "courseId", target = "courseId")
    @Mapping(source = "student", target = "student")
    @Mapping(source = "enrollmentDate", target = "enrollmentDate")
    @Mapping(source = "dropDate", target = "dropDate")
    List<CourseStudent> toDTOList(List<CourseStudentEntity> courseStudentEntityList);

    CourseStudentEntity toEntity(CourseStudent courseStudent);

    CourseStudent toDTO(CourseStudentEntity courseStudentEntity);
}
