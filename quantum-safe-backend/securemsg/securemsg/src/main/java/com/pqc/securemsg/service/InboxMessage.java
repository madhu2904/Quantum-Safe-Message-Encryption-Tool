package com.pqc.securemsg.service;

import java.time.Instant;

public record InboxMessage(
        String messageId,
        String token,
        String senderId,
        Instant expiresAt,
        String status
) {}
