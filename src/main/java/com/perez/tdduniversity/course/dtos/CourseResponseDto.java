package com.perez.tdduniversity.course.dtos;
import com.perez.tdduniversity.student.dtos.StudentResponseDto;

import java.util.List;

public record CourseResponseDto(String courseId,
                                String name,
                                String description,
                                Integer credits,
                                Integer weeklyHours,
                                List<StudentResponseDto> students){
}
