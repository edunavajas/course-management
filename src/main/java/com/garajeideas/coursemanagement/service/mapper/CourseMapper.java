package com.garajeideas.coursemanagement.service.mapper;

import com.garajeideas.coursemanagement.dtos.Course;
import com.garajeideas.coursemanagement.dtos.CoursePage;
import com.garajeideas.coursemanagement.openapi.web.rest.dtos.CourseRequest;
import com.garajeideas.coursemanagement.openapi.web.rest.dtos.CourseResponse;
import com.garajeideas.coursemanagement.openapi.web.rest.dtos.CoursesPageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.*;

@Mapper(componentModel = "spring", imports = {java.time.OffsetDateTime.class, java.time.ZoneId.class})
public interface CourseMapper {

    CoursesPageResponse toPageResponse(CoursePage coursePage);

    @Mapping(source = "startDate", target = "startDate", qualifiedByName = "instantToOffsetDateTime")
    @Mapping(source = "endDate", target = "endDate", qualifiedByName = "instantToOffsetDateTime")
    @Mapping(source = "registrationDate", target = "registrationDate", qualifiedByName = "instantToOffsetDateTime")
    CourseResponse toResponse(Course course);

    @Mapping(source = "startDate", target = "startDate", qualifiedByName = "offsetDateTimeToInstant")
    @Mapping(source = "endDate", target = "endDate", qualifiedByName = "offsetDateTimeToInstant")
    Course toDTO(CourseRequest courseRequest);

    @Named("instantToLocalDate")
    default LocalDate mapInstantToLocalDate(Instant instant) {
        return instant == null ? null : instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @Named("instantToOffsetDateTime")
    default OffsetDateTime map(Instant instant) {
        return instant == null ? null : OffsetDateTime.ofInstant(instant, ZoneOffset.UTC);
    }

    @Named("offsetDateTimeToInstant")
    default Instant map(OffsetDateTime offsetDateTime) {
        return offsetDateTime == null ? null : offsetDateTime.toInstant();
    }

}
