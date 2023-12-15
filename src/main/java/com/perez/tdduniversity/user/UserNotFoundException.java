package com.perez.tdduniversity.user;

import com.perez.tdduniversity.system.Constants;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(Long id) {
        super(String.format(Constants.USER_NOT_FOUND, id));
    }
}
