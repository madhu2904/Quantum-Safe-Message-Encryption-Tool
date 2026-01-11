package com.pqc.securemsg.security;

import java.time.Instant;

public class SecurityAlert {

    public String messageId;
    public String senderId;
    public String receiverId;
    public int accessAttempts;
    public Instant detectedAt;

    public SecurityAlert(
            String messageId,
            String senderId,
            String receiverId,
            int accessAttempts
    ) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.accessAttempts = accessAttempts;
        this.detectedAt = Instant.now();
    }
}
