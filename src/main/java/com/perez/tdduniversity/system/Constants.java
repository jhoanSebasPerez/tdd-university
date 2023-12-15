package com.perez.tdduniversity.system;

public class Constants {

    /**
     * Exception messages
     **/

    // Course messages
    public static final String COURSE_NOT_FOUND = "Course not found with id: %s";
    public static final String COURSE_ALREADY_EXISTS = "Course already exists with id: %s";

    // User messages
    public static final String USER_NOT_FOUND = "User not found with id: %s";
    public static final String USER_BY_USERNAME_NOT_FOUND = "User not found with username: %s";
    public static final String USER_HAS_NOT_PERMISSION = "User has not permission to access this resource";

    // Student messages
    public static final String STUDENT_NOT_FOUND = "Student not found with id: %s";

    /**
     * Response messages
     **/

    // Course messages
    public static final String COURSE_FIND_ONE_SUCCESS = "Course found successfully";
    public static final String COURSE_CREATE_SUCCESS = "Course created successfully";

    // User messages
    public static final String USER_FIND_ONE_SUCCESS = "User found successfully";

    // Authentication messages
    public static final String AUTH_SUCCESS = "User authenticated successfully";

    // Student messages
    public static final String STUDENT_CREATE_SUCCESS = "Student created successfully";


    private Constants() {
    }
}
