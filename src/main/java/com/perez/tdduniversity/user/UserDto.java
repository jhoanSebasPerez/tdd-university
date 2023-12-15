package com.perez.tdduniversity.user;

import jakarta.validation.constraints.NotEmpty;

public record UserDto (Long id,
                       @NotEmpty(message = "Username is required")
                       String username,
                       @NotEmpty(message = "Password is required")
                       String roles,
                       Boolean enabled){
}
