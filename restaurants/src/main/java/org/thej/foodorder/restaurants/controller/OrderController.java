package org.thej.foodorder.restaurants.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thej.foodorder.master.dto.template.response.ApiResponse;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    @ModelAttribute
    public void printAuthorities() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            System.out.println("Principal: " + auth.getPrincipal());
            System.out.println("Authorities:");
            for (GrantedAuthority authority : auth.getAuthorities()) {
                System.out.println(authority.getAuthority());
            }
        }
    }

    @GetMapping("/privilege")
    @PreAuthorize("hasAuthority('PRIVILEGE_VIEW')")
    public ApiResponse<String> privilegeEndpoint() {
        return new ApiResponse<>(HttpStatus.OK, "This is a privilege-based endpoint", "Privilege data");
    }

    @GetMapping("/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> roleEndpoint() {
        return new ApiResponse<>(HttpStatus.OK, "This is a role-based endpoint", "Role data");
    }

    @GetMapping("/public")
    @PreAuthorize("hasRole('USER')")
    public ApiResponse<String> publicEndpoint() {
        return new ApiResponse<>(HttpStatus.OK, "This is a public endpoint", "Public data");
    }
}
