package com.groomthon.univ.api.user.controller;


import com.groomthon.univ.api.user.dto.LoginRequestDto;
import com.groomthon.univ.api.user.dto.SignupRequestDto;
import com.groomthon.univ.api.user.service.UserService;
import com.groomthon.univ.common.response.ApiResponse;
import com.groomthon.univ.common.response.SuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signup(
            @Validated @RequestBody SignupRequestDto signupRequestDto) {
        userService.signupUser(signupRequestDto);

        return ApiResponse.success_only(SuccessStatus.SEND_SIGNUP_SUCCESS);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(
            @Validated @RequestBody LoginRequestDto loginRequestDto) {
        String token = userService.loginUser(loginRequestDto);

        return ApiResponse.success(SuccessStatus.SEND_LOGIN_SUCCESS, token);
    }
}
