package com.quantum_safe.sercure_server.service;

import com.quantum_safe.sercure_server.entities.user;
import com.quantum_safe.sercure_server.entities.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class userService
{
    @Autowired
    userRepository userRepository;
    public byte[] getPublicKey(String mail)
    {
        user user = userRepository.findByMailId(mail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getPublicKey();
    }

}
