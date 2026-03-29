package com.quantum_safe.sercure_server.DTOs;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class sendMessage
{
    private final String encapsulation;
    private final String ciphertext;
    private final String iv;
    private final String senderMailId;
    private final String receiverMailId;
}
