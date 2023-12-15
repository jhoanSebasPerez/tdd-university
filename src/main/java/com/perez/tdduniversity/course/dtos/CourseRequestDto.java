package com.perez.tdduniversity.course.dtos;

public record CourseRequestDto (String courseId,
        String name,
        String description,
        Integer credits,
        Integer weeklyHours
        ){}