package com.example.demo.domain.course.dto;

import jakarta.validation.constraints.Size;

public record CourseUpdateReq(
        @Size(max = 200)
        String courseName,
        Long price,
        @Size(max = 1000)
        String description
) {}
