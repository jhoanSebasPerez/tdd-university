package com.perez.tdduniversity.course;

import com.perez.tdduniversity.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, String> {
}
