package com.pqc.securemsg.store;

import com.pqc.securemsg.crypto.EncryptedPayload;
import java.time.Instant;

public class MessageRecord {

    public String messageId;
    public String token;
    public String senderId;
    public String receiverId;
    public EncryptedPayload payload;
    public Instant expiryTime;

    public boolean used = false;
    public Instant readAt;

    // 🔐 Security tracking
    public int accessAttempts = 0;
    public boolean senderAlerted = false;

    public MessageRecord(
            String messageId,
            String senderId,
            String receiverId,
            EncryptedPayload payload,
            Instant expiryTime
    ) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.payload = payload;
        this.expiryTime = expiryTime;
    }

    // ✅ CENTRALIZED STATUS LOGIC
    public MessageStatus getStatus() {
        if (Instant.now().isAfter(expiryTime)) {
            return MessageStatus.EXPIRED;
        }
        if (used && accessAttempts > 1) {
            return MessageStatus.REPLAY_DETECTED;
        }
        if (used) {
            return MessageStatus.BURNED;
        }
        return MessageStatus.UNREAD;
    }
}
