package com.perez.tdduniversity.course;

import com.perez.tdduniversity.course.CourseRepository;
import com.perez.tdduniversity.course.exceptions.CourseAlreadyExistsException;
import com.perez.tdduniversity.course.exceptions.CourseNotFoundException;
import com.perez.tdduniversity.course.Course;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course findById(String courseId) {
        return courseRepository.findById(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));
    }

    public List<Course> findAll(){
        return courseRepository.findAll();
    }

    public Course save(Course course){
        courseRepository.findById(course.getCourseId()).ifPresent(c -> {
            throw new CourseAlreadyExistsException(course.getCourseId());
        });
        return courseRepository.save(course);
    }
}
