package com.garajeideas.coursemanagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class ErrorDetails {
    private String title;
    private String message;
    private int status;
    private ZonedDateTime timestamp;
}
