package com.perez.tdduniversity.student.converters;

import com.perez.tdduniversity.student.dtos.StudentResponseDto;
import com.perez.tdduniversity.student.Student;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StudentToStudentResponseDto implements Converter<Student, StudentResponseDto>{

        @Override
        public StudentResponseDto convert(Student student) {
            return new StudentResponseDto(
                    student.getId(),
                    student.getName(),
                    student.getEmail(),
                    student.getPhone(),
                    student.getAddress(),
                    student.getNumberOfCourses()
            );
        }
}
