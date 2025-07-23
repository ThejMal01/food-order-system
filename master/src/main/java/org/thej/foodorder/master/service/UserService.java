package org.thej.foodorder.master.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.thej.foodorder.master.dto.auth.LoginRequest;
import org.thej.foodorder.master.dto.auth.LoginResponse;
import org.thej.foodorder.master.dto.auth.RegisterRequest;
import org.thej.foodorder.master.dto.auth.RegisterResponse;

public interface UserService extends UserDetailsService {
    RegisterResponse signup(RegisterRequest registerRequest);

    LoginResponse login(LoginRequest loginRequest);
}
