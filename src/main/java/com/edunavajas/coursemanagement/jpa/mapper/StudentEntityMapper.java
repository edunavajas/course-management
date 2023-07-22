package com.edunavajas.coursemanagement.jpa.mapper;

import com.edunavajas.coursemanagement.dtos.Student;
import com.edunavajas.coursemanagement.domain.StudentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentEntityMapper {

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
