package com.quantum_safe.sercure_server.controller;

import com.quantum_safe.sercure_server.DTOs.loginRequest;
import com.quantum_safe.sercure_server.DTOs.loginResponse;
import com.quantum_safe.sercure_server.DTOs.registerRequest;
import com.quantum_safe.sercure_server.service.authenticationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class authController {

    private final authenticationService authService;

    public authController(authenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public loginResponse login(@RequestBody loginRequest request) {
        return authService.login(request);
    }
    @PostMapping("/register")
    public String register(@RequestBody registerRequest request)
    {
            return authService.register(request);
    }
    

}
