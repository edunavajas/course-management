package com.garajeideas.coursemanagement.dtos;


import com.garajeideas.coursemanagement.openapi.web.rest.dtos.Course;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Value
@Builder
public class CoursePage {

	List<Course> content;

	private Integer page;

	private Integer pageSize;

	private Integer totalItems;

	private Integer totalPages;
}
