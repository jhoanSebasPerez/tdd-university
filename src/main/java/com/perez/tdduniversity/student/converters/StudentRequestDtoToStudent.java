package com.perez.tdduniversity.student.converters;

import com.perez.tdduniversity.student.dtos.StudentRequestDto;
import org.springframework.core.convert.converter.Converter;
import com.perez.tdduniversity.student.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentRequestDtoToStudent implements Converter<StudentRequestDto, Student> {

    @Override
    public Student convert(StudentRequestDto studentRequestDto) {
        Student student = new Student();
        student.setName(studentRequestDto.name());
        student.setEmail(studentRequestDto.email());
        student.setPhone(studentRequestDto.phone());
        student.setAddress(studentRequestDto.address());
        return student;
    }
}
