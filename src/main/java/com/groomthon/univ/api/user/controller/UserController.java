package com.groomthon.univ.api.user.controller;


import com.groomthon.univ.api.user.service.UserService;
import com.groomthon.univ.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public ResponseEntity<ApiResponse<Void>> signup() {

    }
}
