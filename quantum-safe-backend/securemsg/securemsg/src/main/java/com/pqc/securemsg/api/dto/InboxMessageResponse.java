package com.pqc.securemsg.api.dto;

import com.pqc.securemsg.store.MessageStatus;
import java.time.Instant;

public record InboxMessageResponse(
        String messageId,
        String token,
        String senderId,
        Instant expiresAt,
        MessageStatus status
) {}
