package com.garajeideas.coursemanagement.service.mapper;

import com.garajeideas.coursemanagement.domain.CourseEntity;
import com.garajeideas.coursemanagement.dtos.CoursePage;
import com.garajeideas.coursemanagement.openapi.web.rest.dtos.Course;
import com.garajeideas.coursemanagement.openapi.web.rest.dtos.CoursesPageResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", imports = { java.time.OffsetDateTime.class, java.time.ZoneId.class })
public interface CourseMapper {

	CoursesPageResponse toPageResponse(CoursePage coursePage);

	Course toCourse(CourseEntity courseEntity);

}
