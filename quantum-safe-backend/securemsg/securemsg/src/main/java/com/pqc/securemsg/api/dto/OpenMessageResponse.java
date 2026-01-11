package com.pqc.securemsg.api.dto;

import java.time.Instant;

public class OpenMessageResponse {

    private String message;
    private Instant readAt;

    public OpenMessageResponse(String message, Instant readAt) {
        this.message = message;
        this.readAt = readAt;
    }

    public String getMessage() {
        return message;
    }

    public Instant getReadAt() {
        return readAt;
    }
}
