package com.quantum_safe.sercure_server.controller;

import com.quantum_safe.sercure_server.entities.userRepository;
import com.quantum_safe.sercure_server.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


//BOTH CONTROLLER AND SERVICE LOGIC IS PRESENT IN HERE ITSELF
@RestController
@RequestMapping("/users")
public class userController
{
    @Autowired
    userRepository userRepository;
    @Autowired
    userService userService;
    @GetMapping("/getAll")
    public List<String> getUsers()
    {
        List<String> mails= userRepository.findAllMailId().orElse(new ArrayList<>());;
        return mails;
    }
    @GetMapping("/public-key")
    public String getPublicKey(@RequestParam String mailId) {
        byte[] key = userService.getPublicKey(mailId);

        return Base64.getEncoder().encodeToString(key);
    }
}
