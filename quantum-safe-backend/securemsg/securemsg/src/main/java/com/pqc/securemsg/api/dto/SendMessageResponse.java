package com.pqc.securemsg.api.dto;

import java.time.Instant;

public class SendMessageResponse {

    private String messageId;
    private String token;
    private Instant expiresAt;

    public SendMessageResponse(String messageId, String token, Instant expiresAt) {
        this.messageId = messageId;
        this.token = token;
        this.expiresAt = expiresAt;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getToken() {
        return token;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }
}
