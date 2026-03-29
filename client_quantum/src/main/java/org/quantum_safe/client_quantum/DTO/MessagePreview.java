package org.quantum_safe.client_quantum.DTO;

import java.time.LocalDateTime;

public class MessagePreview {

    private Long messageId;
    private String senderMail;
    private boolean isRead;
    private LocalDateTime timestamp;

    public MessagePreview(Long messageId, String senderMail,
                          boolean read, LocalDateTime timestamp) {
        this.messageId = messageId;
        this.senderMail = senderMail;
        this.isRead = read;
        this.timestamp = timestamp;
    }
public MessagePreview(){}
    public Long getMessageId() { return messageId; }
    public String getSenderMail() { return senderMail; }
    public boolean getIsRead() { return isRead; }
    public void setIsRead(boolean read) { this.isRead = read; }
    public LocalDateTime getTimestamp() { return timestamp; }
}