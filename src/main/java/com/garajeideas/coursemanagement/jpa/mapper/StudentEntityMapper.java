package com.garajeideas.coursemanagement.jpa.mapper;

import com.garajeideas.coursemanagement.domain.StudentEntity;
import com.garajeideas.coursemanagement.dtos.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentEntityMapper {

    StudentEntityMapper INSTANCE = Mappers.getMapper(StudentEntityMapper.class);

    List<Student> toDTOList(List<StudentEntity> studentEntityList);

    StudentEntity toEntity(Student student);

    @Mapping(source = "studentEntity.id", target = "id")
    @Mapping(source = "studentEntity.firstName", target = "firstName")
    @Mapping(source = "studentEntity.lastName", target = "lastName")
    @Mapping(source = "studentEntity.documentNumber", target = "documentNumber")
    @Mapping(source = "studentEntity.birthDate", target = "birthDate")
    @Mapping(source = "studentEntity.registrationDate", target = "registrationDate")
    Student toDTO(StudentEntity studentEntity);
}
