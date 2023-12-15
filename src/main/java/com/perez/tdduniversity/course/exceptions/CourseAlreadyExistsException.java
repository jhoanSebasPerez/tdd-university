package com.perez.tdduniversity.course.exceptions;

import com.perez.tdduniversity.system.Constants;
import org.springframework.dao.DataIntegrityViolationException;

public class CourseAlreadyExistsException extends DataIntegrityViolationException {

        public CourseAlreadyExistsException(String courseId) {
            super(String.format(Constants.COURSE_ALREADY_EXISTS, courseId));
        }
}
