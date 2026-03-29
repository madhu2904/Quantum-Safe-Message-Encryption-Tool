package com.quantum_safe.sercure_server.DTOs;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Transactional
public class readMessage
{
    private byte[] ciphertext;
    private byte[] encapsulation;
    private byte[] iv;
}