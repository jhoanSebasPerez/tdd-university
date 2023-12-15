package com.perez.tdduniversity.course;

import com.perez.tdduniversity.course.converters.CourseRequestDtoToCourse;
import com.perez.tdduniversity.course.converters.CourseToCourseResponseDto;
import com.perez.tdduniversity.course.dtos.CourseResponseDto;
import com.perez.tdduniversity.course.dtos.CourseRequestDto;
import com.perez.tdduniversity.system.Result;
import com.perez.tdduniversity.system.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("${api.endpoint.base-url}/courses")
@Tag(name = "courses", description = "the courses API")
public class CourseController {

    private final CourseService courseService;
    private final CourseToCourseResponseDto courseToCourseResponseDto;
    private final CourseRequestDtoToCourse courseRequestDtoToCourse;

    public CourseController(CourseService courseService, CourseToCourseResponseDto courseToCourseResponseDto, CourseRequestDtoToCourse courseRequestDtoToCourse) {
        this.courseService = courseService;
        this.courseToCourseResponseDto = courseToCourseResponseDto;
        this.courseRequestDtoToCourse = courseRequestDtoToCourse;
    }

    @Operation(summary = "Find by id", description = "Get course by id")
    @ApiResponse(responseCode = "200", description = Constants.COURSE_FIND_ONE_SUCCESS)
    @ApiResponse(responseCode = "404", description = Constants.COURSE_NOT_FOUND)
    @PreAuthorize("hasAnyRole('student', 'admin')") // Admin and Students can access this endpoint
    @GetMapping("/{courseId}")
    public Result<CourseResponseDto> findCourseById(@PathVariable String courseId) {
        Course foundCourse = courseService.findById(courseId);
        CourseResponseDto courseResponseDto = courseToCourseResponseDto.convert(foundCourse);
        return new Result<>(true, HttpStatus.OK.value(), Constants.COURSE_FIND_ONE_SUCCESS, courseResponseDto);

    }

    @Operation(summary = "Create course", description = "Create a new course")
    @ApiResponse(responseCode = "201", description = Constants.COURSE_CREATE_SUCCESS)
    @ApiResponse(responseCode = "400", description = Constants.COURSE_ALREADY_EXISTS)
    @PreAuthorize("hasRole('admin')") // Only users with role admin can access this endpoint
    @PostMapping()
    public Result<CourseResponseDto> save(@RequestBody CourseRequestDto courseRequestDto) {
        Course course = courseRequestDtoToCourse.convert(courseRequestDto);
        Course savedCourse = courseService.save(Objects.requireNonNull(course));
        CourseResponseDto savedCourseResponseDto = courseToCourseResponseDto.convert(savedCourse);
        return new Result<>(true, HttpStatus.CREATED.value(), Constants.COURSE_CREATE_SUCCESS, savedCourseResponseDto);
    }


}
