package com.garajeideas.coursemanagement.service.mapper;

import com.garajeideas.coursemanagement.dtos.Student;
import com.garajeideas.coursemanagement.openapi.web.rest.dtos.StudentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.*;

@Mapper(componentModel = "spring", imports = { OffsetDateTime.class, ZoneId.class })
public interface StudentMapper {

	// CoursesPageResponse toPageResponse(CoursePage coursePage);

	@Mapping(source = "birthDate", target = "birthDate", qualifiedByName = "instantToLocalDate")
	StudentResponse toResponse(Student student);

	@Mapping(source = "birthDate", target = "birthDate", qualifiedByName = "localDateToInstant")
	Student toDTO(StudentResponse studentResponse);

	@Named("instantToLocalDate")
	default LocalDate mapInstantToLocalDate(Instant instant) {
		return instant == null ? null : instant.atZone(ZoneId.systemDefault()).toLocalDate();
	}

	@Named("localDateToInstant")
	default Instant mapLocalDateToInstant(LocalDate localDate) {
		return localDate == null ? null : localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
	}
}
