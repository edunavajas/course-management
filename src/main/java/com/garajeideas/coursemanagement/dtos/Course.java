package com.garajeideas.coursemanagement.dtos;

import lombok.*;

import java.time.Instant;
import java.util.List;

@NoArgsConstructor
public class Course {

	private Long id;
	private String name;
	private Integer maxStudentCount;
	private Instant startDate;
	private Instant endDate;
	private Instant registrationDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMaxStudentCount() {
		return maxStudentCount;
	}

	public void setMaxStudentCount(Integer maxStudentCount) {
		this.maxStudentCount = maxStudentCount;
	}

	public Instant getStartDate() {
		return startDate;
	}

	public void setStartDate(Instant startDate) {
		this.startDate = startDate;
	}

	public Instant getEndDate() {
		return endDate;
	}

	public void setEndDate(Instant endDate) {
		this.endDate = endDate;
	}

	public Instant getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Instant registrationDate) {
		this.registrationDate = registrationDate;
	}
}
