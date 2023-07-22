package com.edunavajas.coursemanagement.jpa.mapper;

import com.edunavajas.coursemanagement.domain.CourseStudentEntity;
import com.edunavajas.coursemanagement.dtos.CourseStudent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseStudentEntityMapper {


    @Mapping(source = "id", target = "id")
    @Mapping(source = "courseId", target = "courseId")
    @Mapping(source = "student", target = "student")
    @Mapping(source = "enrollmentDate", target = "enrollmentDate")
    @Mapping(source = "dropDate", target = "dropDate")
    List<CourseStudent> toDTOList(List<CourseStudentEntity> courseStudentEntityList);

    CourseStudentEntity toEntity(CourseStudent courseStudent);

    CourseStudent toDTO(CourseStudentEntity courseStudentEntity);
}
