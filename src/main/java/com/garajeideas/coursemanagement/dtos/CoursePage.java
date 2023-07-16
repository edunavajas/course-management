package com.garajeideas.coursemanagement.dtos;


import com.garajeideas.coursemanagement.domain.CourseEntity;
import lombok.*;


import java.util.List;

@Builder
@AllArgsConstructor
public class CoursePage {

	List<CourseEntity> content;

	private Integer page;

	private Integer pageSize;

	private Integer totalItems;

	private Integer totalPages;

	// Añadidos getters y setters por un conflicto del lombok con el mapper

	public List<CourseEntity> getContent() {
		return content;
	}

	public void setContent(List<CourseEntity> content) {
		this.content = content;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(Integer totalItems) {
		this.totalItems = totalItems;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
}
