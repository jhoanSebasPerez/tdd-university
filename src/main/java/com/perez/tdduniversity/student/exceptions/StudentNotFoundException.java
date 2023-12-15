package com.perez.tdduniversity.student.exceptions;

import com.perez.tdduniversity.system.Constants;

public class StudentNotFoundException extends RuntimeException{

    public StudentNotFoundException(Integer idStudent){
        super(String.format(Constants.STUDENT_NOT_FOUND, idStudent));
    }
}
