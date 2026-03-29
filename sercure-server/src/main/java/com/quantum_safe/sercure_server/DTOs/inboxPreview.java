package com.quantum_safe.sercure_server.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class inboxPreview
{
    private Long messageId;
    //private Long senderId;
    private String senderMail;
    private Boolean isRead;
    private LocalDateTime timestamp;
}
