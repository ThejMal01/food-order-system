package org.thej.foodorder.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thej.foodorder.master.dto.auth.LoginRequest;
import org.thej.foodorder.master.dto.auth.LoginResponse;
import org.thej.foodorder.master.service.UserService;
import org.thej.foodorder.master.dto.auth.RegisterRequest;
import org.thej.foodorder.master.dto.auth.RegisterResponse;
import org.thej.foodorder.master.dto.template.response.ApiResponse;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/signup")
    public ApiResponse<RegisterResponse> signup(@RequestBody RegisterRequest registerRequest) {
        RegisterResponse registerResponse = userService.signup(registerRequest);
        return new ApiResponse<>(HttpStatus.CREATED, "User Created", registerResponse);
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = userService.login(loginRequest);
        return new ApiResponse<>(HttpStatus.OK, "Login successful", loginResponse);
    }
}
