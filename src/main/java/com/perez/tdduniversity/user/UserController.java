package com.perez.tdduniversity.user;

import com.perez.tdduniversity.system.Result;
import com.perez.tdduniversity.system.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.endpoint.base-url}/users")
public class UserController {

    private final UserService userService;
    private final UserToUserDto userToUserDto;

    public UserController(UserService userService, UserToUserDto userToUserDto) {
        this.userService = userService;
        this.userToUserDto = userToUserDto;
    }

    @GetMapping
    public Result<List<UserDto>> findAll() {
        return null;
    }

    @GetMapping("/{userId}")
    public Result<UserDto> findById(@PathVariable Long userId) {
        User user = userService.findById(userId);
        UserDto userDto = userToUserDto.convert(user);
        return new Result<>(true, HttpStatus.OK.value(), Constants.USER_FIND_ONE_SUCCESS, userDto);
    }

}
