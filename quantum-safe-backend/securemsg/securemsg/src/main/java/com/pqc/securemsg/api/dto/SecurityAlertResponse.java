package com.pqc.securemsg.api.dto;

import java.time.Instant;

public record SecurityAlertResponse(
        String messageId,
        String receiverId,
        int accessAttempts,
        Instant firstReadAt
) {}
