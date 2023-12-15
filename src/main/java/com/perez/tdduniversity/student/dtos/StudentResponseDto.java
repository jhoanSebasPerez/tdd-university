package com.perez.tdduniversity.student.dtos;

public record StudentResponseDto(Integer id,
                                 String name,
                                 String email,
                                 String phone,
                                 String address,
                                 Integer numberOfCourses) {
}