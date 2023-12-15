package com.perez.tdduniversity.course.converters;

import com.perez.tdduniversity.course.dtos.CourseResponseDto;
import com.perez.tdduniversity.course.Course;
import com.perez.tdduniversity.student.converters.StudentToStudentResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class CourseToCourseResponseDto implements Converter<Course, CourseResponseDto> {

    @Autowired
    private final StudentToStudentResponseDto studentToStudentResponseDto;

    public CourseToCourseResponseDto(StudentToStudentResponseDto studentToStudentResponseDto) {
        this.studentToStudentResponseDto = studentToStudentResponseDto;
    }

    @Override
    public CourseResponseDto convert(Course course) {
        return new CourseResponseDto(
                course.getCourseId(),
                course.getName(),
                course.getDescription(),
                course.getCredits(),
                course.getWeeklyHours(),
                course.getStudents().stream().map(studentToStudentResponseDto::convert).toList()
        );
    }
}
