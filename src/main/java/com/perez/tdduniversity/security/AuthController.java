package com.perez.tdduniversity.security;

import com.perez.tdduniversity.system.Constants;
import com.perez.tdduniversity.system.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("${api.endpoint.base-url}/auth")
@Tag(name = "auth", description = "the auth API")
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Authenticate user", description = "Authenticate user")
    @ApiResponse(responseCode = "200", description = Constants.AUTH_SUCCESS)
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @PostMapping("/login")
    public Result<Map<String, Object>> getLoginInfo(Authentication authentication) {
        LOGGER.debug("Authenticated user: '{}'", authentication.getName());
        return new Result<>(true, HttpStatus.OK.value(), Constants.AUTH_SUCCESS, authService.getLoginInfo(authentication));
    }
}
