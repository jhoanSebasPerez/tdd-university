package com.perez.tdduniversity.system;


import com.perez.tdduniversity.course.exceptions.CourseAlreadyExistsException;
import com.perez.tdduniversity.course.exceptions.CourseNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(CourseNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result<String> handleCourseNotFoundException(CourseNotFoundException e){
        return new Result<>(false, HttpStatus.NOT_FOUND.value(),"Course does not exist", e.getMessage());
    }

    @ExceptionHandler({UsernameNotFoundException.class, BadCredentialsException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result<String> handleUsernameNotFoundException(Exception e){
        return new Result<>(false, HttpStatus.UNAUTHORIZED.value(), "User with that username not exists", e.getMessage());
    }

    @ExceptionHandler(CourseAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<String> handleCourseAlreadyExists(Exception e){
        return new Result<>(false, HttpStatus.BAD_REQUEST.value(), "Course already exists", e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Result<String> handleAccessDeniedException(AccessDeniedException ex) {
        return new Result<>(false, HttpStatus.FORBIDDEN.value(), Constants.USER_HAS_NOT_PERMISSION, ex.getMessage());
    }
}
