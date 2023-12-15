package com.perez.tdduniversity.security;

import com.perez.tdduniversity.user.UserToUserDto;
import com.perez.tdduniversity.user.UserDto;
import com.perez.tdduniversity.user.MyUserPrincipal;
import com.perez.tdduniversity.security.JwtProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {

    private final JwtProvider jwtProvider;
    private final UserToUserDto userToUserDto;

    public AuthService(JwtProvider jwtProvider,
                       UserToUserDto userToUserDto) {
        this.jwtProvider = jwtProvider;
        this.userToUserDto = userToUserDto;
    }

    public Map<String, Object> getLoginInfo(Authentication authentication) {
        //create user info
        MyUserPrincipal userPrincipal = (MyUserPrincipal) authentication.getPrincipal();
        UserDto userDTO = userToUserDto.convert(userPrincipal.getUser());

        //create a jwt
        String token = jwtProvider.generateToken(authentication);

        assert userDTO != null;
        return Map.of("userInfo", userDTO, "token", token);
    }
}
