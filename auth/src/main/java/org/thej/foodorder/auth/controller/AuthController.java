package org.thej.foodorder.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/public")
    @PreAuthorize("hasRole('USER')")
    public ApiResponse<String> publicEndpoint() {
        return new ApiResponse<>(HttpStatus.OK, "This is a public endpoint", "Public data");
    }

    @GetMapping("/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> roleEndpoint() {
        return new ApiResponse<>(HttpStatus.OK, "This is a role-based endpoint", "Role data");
    }

    @GetMapping("/privilege")
    @PreAuthorize("hasAuthority('PRIVILEGE_VIEW')")
    public ApiResponse<String> privilegeEndpoint() {
        return new ApiResponse<>(HttpStatus.OK, "This is a privilege-based endpoint", "Privilege data");
    }
}
