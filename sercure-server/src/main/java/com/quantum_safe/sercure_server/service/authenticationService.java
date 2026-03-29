package com.quantum_safe.sercure_server.service;

import com.quantum_safe.sercure_server.DTOs.loginRequest;
import com.quantum_safe.sercure_server.DTOs.loginResponse;
import com.quantum_safe.sercure_server.DTOs.registerRequest;
import com.quantum_safe.sercure_server.entities.user;

import com.quantum_safe.sercure_server.entities.userRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class authenticationService
{
    private final userRepository userRepository;
    private final BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();

    public authenticationService(userRepository userRepository) {
        this.userRepository = userRepository;
    }
    public loginResponse login(loginRequest request)
    {
        user user=userRepository.findByMailId(request.getMailId()).orElseThrow(() -> new RuntimeException("User not found"));
        if(!encoder.matches(request.getPassword(),user.getPassword()))
        {
            throw new RuntimeException("Invalid password");
        }
        return new loginResponse(user.getId(), user.getName());
    }
    public String register(registerRequest request)
    {
        user user=new user();
        user.setName(request.getName());
        user.setMailId(request.getMailId());
        user.setPassword(request.getPassword());

        String encodedPassword = encoder.encode(request.getPassword());
        user.setPassword(encodedPassword);


        user.setPublicKey(request.getPublicKey());
        userRepository.save(user);
        return "User Registered";
    }

}
