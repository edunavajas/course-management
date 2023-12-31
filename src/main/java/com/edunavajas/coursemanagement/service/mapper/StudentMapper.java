package com.edunavajas.coursemanagement.service.mapper;

import com.edunavajas.coursemanagement.dtos.Student;
import com.edunavajas.coursemanagement.dtos.StudentPage;
import com.edunavajas.coursemanagement.openapi.web.rest.dtos.StudentRequest;
import com.edunavajas.coursemanagement.openapi.web.rest.dtos.StudentResponse;
import com.edunavajas.coursemanagement.openapi.web.rest.dtos.StudentsPageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@Mapper(componentModel = "spring", imports = {OffsetDateTime.class, ZoneId.class})
public interface StudentMapper {

    StudentsPageResponse toPageResponse(StudentPage coursePage);

    @Mapping(source = "birthDate", target = "birthDate", qualifiedByName = "instantToLocalDate")
    @Mapping(source = "registrationDate", target = "registrationDate", qualifiedByName = "instantToLocalDate")
    StudentResponse toResponse(Student student);

    @Mapping(source = "birthDate", target = "birthDate", qualifiedByName = "localDateToInstant")
    Student toDTO(StudentRequest studentRequest);

    @Named("instantToLocalDate")
    default LocalDate mapInstantToLocalDate(Instant instant) {
        return instant == null ? null : instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @Named("localDateToInstant")
    default Instant mapLocalDateToInstant(LocalDate localDate) {
        return localDate == null ? null : localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
    }
}
