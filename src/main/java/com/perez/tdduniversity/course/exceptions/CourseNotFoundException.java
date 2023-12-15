package com.perez.tdduniversity.course.exceptions;

import com.perez.tdduniversity.system.Constants;

public class CourseNotFoundException extends RuntimeException{

    public CourseNotFoundException(String courseId) {
        super(String.format(Constants.COURSE_NOT_FOUND, courseId));
    }
}
