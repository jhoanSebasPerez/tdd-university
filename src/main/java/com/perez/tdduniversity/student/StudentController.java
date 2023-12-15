package com.perez.tdduniversity.student;

import com.perez.tdduniversity.course.Course;
import com.perez.tdduniversity.course.dtos.CourseRequestDto;
import com.perez.tdduniversity.course.dtos.CourseResponseDto;
import com.perez.tdduniversity.student.converters.StudentRequestDtoToStudent;
import com.perez.tdduniversity.student.converters.StudentToStudentResponseDto;
import com.perez.tdduniversity.student.dtos.StudentRequestDto;
import com.perez.tdduniversity.student.dtos.StudentResponseDto;
import com.perez.tdduniversity.system.Constants;
import com.perez.tdduniversity.system.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("${api.endpoint.base-url}/students")
@Tag(name = "students", description = "the students API")
public class StudentController {

    private final StudentService studentService;
    private final StudentRequestDtoToStudent studentRequestDtoToStudent;
    private final StudentToStudentResponseDto studentToStudentResponseDto;

    public StudentController(StudentService studentService, StudentRequestDtoToStudent studentRequestDtoToStudent, StudentToStudentResponseDto studentToStudentResponseDto) {
        this.studentService = studentService;
        this.studentRequestDtoToStudent = studentRequestDtoToStudent;
        this.studentToStudentResponseDto = studentToStudentResponseDto;
    }

    @Operation(summary = "Create student", description = "Create a new student")
    @ApiResponse(responseCode = "201", description = Constants.STUDENT_CREATE_SUCCESS)
    @PreAuthorize("hasRole('admin')") // Only users with role admin can access this endpoint
    @PostMapping()
    public Result<StudentResponseDto> save(@RequestBody StudentRequestDto studentRequestDto) {
        Student student = studentRequestDtoToStudent.convert(studentRequestDto);
        Student savedStudent = studentService.save(Objects.requireNonNull(student));
        StudentResponseDto savedStudentResponseDto = studentToStudentResponseDto.convert(savedStudent);
        return new Result<>(true, HttpStatus.CREATED.value(), Constants.STUDENT_CREATE_SUCCESS, savedStudentResponseDto);
    }

}
