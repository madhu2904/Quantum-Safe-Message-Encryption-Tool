package com.quantum_safe.sercure_server.DTOs;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class loginResponse {
    private Long userId;
    private String name;

}
