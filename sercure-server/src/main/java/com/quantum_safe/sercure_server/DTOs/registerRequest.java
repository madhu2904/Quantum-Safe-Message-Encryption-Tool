package com.quantum_safe.sercure_server.DTOs;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class registerRequest
{
    private String name;
    private String mailId;
    private String password;
    private byte[] publicKey;
}
