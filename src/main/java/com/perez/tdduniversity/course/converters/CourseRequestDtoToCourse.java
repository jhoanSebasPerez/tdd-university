package com.perez.tdduniversity.course.converters;

import com.perez.tdduniversity.course.dtos.CourseRequestDto;
import com.perez.tdduniversity.course.Course;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;




@Component
public class CourseRequestDtoToCourse implements Converter<CourseRequestDto, Course> {


    @Override
    public Course convert(CourseRequestDto courseDto) {
        return new Course(
                courseDto.courseId(),
                courseDto.name(),
                courseDto.description(),
                courseDto.credits(),
                courseDto.weeklyHours()
        );

    }
}
