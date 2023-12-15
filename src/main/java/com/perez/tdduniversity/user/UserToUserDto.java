package com.perez.tdduniversity.user;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDto implements Converter<User, UserDto> {

    @Override
    public UserDto convert(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getRoles(), user.getEnabled());
    }
}
