package com.garajeideas.coursemanagement.service.mapper;

import com.garajeideas.coursemanagement.domain.CourseEntity;
import com.garajeideas.coursemanagement.dtos.CoursePage;
import com.garajeideas.coursemanagement.openapi.web.rest.dtos.Course;
import com.garajeideas.coursemanagement.openapi.web.rest.dtos.CoursesPageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring", imports = { java.time.OffsetDateTime.class, java.time.ZoneId.class })
public interface CourseMapper {

	CoursesPageResponse toPageResponse(CoursePage coursePage);

	@Mapping(source = "startDate", target = "startDate", qualifiedByName = "instantToOffsetDateTime")
	@Mapping(source = "endDate", target = "endDate", qualifiedByName = "instantToOffsetDateTime")
	Course toCourse(CourseEntity courseEntity);

	@Named("instantToOffsetDateTime")
	default OffsetDateTime map(Instant instant) {
		return instant == null ? null : OffsetDateTime.ofInstant(instant, ZoneOffset.UTC);
	}

	@Named("offsetDateTimeToInstant")
	default Instant map(OffsetDateTime offsetDateTime) {
		return offsetDateTime == null ? null : offsetDateTime.toInstant();
	}

}
