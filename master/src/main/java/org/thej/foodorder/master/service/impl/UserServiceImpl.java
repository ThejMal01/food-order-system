package org.thej.foodorder.master.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.thej.foodorder.commons.exception.CustomException;
import org.thej.foodorder.commons.exception.ErrorCode;
import org.thej.foodorder.master.dao.Role;
import org.thej.foodorder.master.dao.User;
import org.thej.foodorder.master.dto.auth.LoginRequest;
import org.thej.foodorder.master.dto.auth.LoginResponse;
import org.thej.foodorder.master.dto.auth.RegisterRequest;
import org.thej.foodorder.master.dto.auth.RegisterResponse;
import org.thej.foodorder.master.repository.RoleRepository;
import org.thej.foodorder.master.repository.UserRepository;
import org.thej.foodorder.master.service.UserService;

import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtService = jwtService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found with username: " + username));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getAuthorities())
                .accountExpired(!user.isActive())
                .accountLocked(!user.isActive())
                .credentialsExpired(false)
                .disabled(!user.isActive())
                .build();
    }

    @Override
    public RegisterResponse signup(RegisterRequest registerRequest) {
        Optional<User> existingUser = userRepository.findByUsername(registerRequest.getUsername());

        if (existingUser.isPresent()) {
            throw new CustomException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }

        Role role = roleRepository.findByRoleName("ROLE_USER")
                .orElseThrow(() -> new CustomException(ErrorCode.ROLE_NOT_FOUND));

        User newUser = new User();
        newUser.setUsername(registerRequest.getUsername());
        newUser.setPassword(registerRequest.getPassword());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setActive(true);
        newUser.setRoles(Set.of(role));

        userRepository.save(newUser);

        String token = jwtService.generateToken(newUser);

        return new RegisterResponse(newUser, token);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
        }

        String token = jwtService.generateToken(user);

        return new LoginResponse(user, token);
    }
}
