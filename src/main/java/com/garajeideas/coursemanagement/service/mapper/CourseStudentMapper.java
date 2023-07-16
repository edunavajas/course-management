package com.garajeideas.coursemanagement.service.mapper;

import com.garajeideas.coursemanagement.dtos.CourseStudent;
import com.garajeideas.coursemanagement.openapi.web.rest.dtos.CourseStudentsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.*;
import java.util.List;

@Mapper(componentModel = "spring", imports = {OffsetDateTime.class, ZoneId.class})
public interface CourseStudentMapper {


    @Mapping(source = "enrollmentDate", target = "enrollmentDate", qualifiedByName = "instantToOffsetDateTime")
    @Mapping(source = "dropDate", target = "dropDate", qualifiedByName = "instantToOffsetDateTime")
    @Mapping(source = "student.birthDate", target = "student.birthDate", qualifiedByName = "instantToLocalDate")
    @Mapping(source = "student.registrationDate", target = "student.registrationDate", qualifiedByName = "instantToLocalDate")
    CourseStudentsResponse toResponse(CourseStudent courseStudent);

    @Mapping(source = "enrollmentDate", target = "enrollmentDate", qualifiedByName = "instantToOffsetDateTime")
    @Mapping(source = "dropDate", target = "dropDate", qualifiedByName = "instantToOffsetDateTime")
    @Mapping(source = "student.birthDate", target = "student.birthDate", qualifiedByName = "instantToLocalDate")
    @Mapping(source = "student.registrationDate", target = "student.registrationDate", qualifiedByName = "instantToLocalDate")
    List<CourseStudentsResponse> toResponseList(List<CourseStudent> courseStudent);

    @Named("instantToOffsetDateTime")
    default OffsetDateTime map(Instant instant) {
        return instant == null ? null : OffsetDateTime.ofInstant(instant, ZoneOffset.UTC);
    }

    @Named("offsetDateTimeToInstant")
    default Instant map(OffsetDateTime offsetDateTime) {
        return offsetDateTime == null ? null : offsetDateTime.toInstant();
    }

    @Named("instantToLocalDate")
    default LocalDate mapInstantToLocalDate(Instant instant) {
        return instant == null ? null : instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @Named("localDateToInstant")
    default Instant mapLocalDateToInstant(LocalDate localDate) {
        return localDate == null ? null : localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
    }

}
