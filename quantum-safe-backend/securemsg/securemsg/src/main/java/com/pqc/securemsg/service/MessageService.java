package com.pqc.securemsg.service;

import com.pqc.securemsg.crypto.*;
import com.pqc.securemsg.store.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import com.pqc.securemsg.api.dto.InboxMessageResponse;
import com.pqc.securemsg.security.SecurityAlert;


@Service
public class MessageService {

    private final CryptoService cryptoService;
    private final InMemoryMessageStore store;
    private final UserKeyService userKeyService;

    public MessageService(
            CryptoService cryptoService,
            InMemoryMessageStore store,
            UserKeyService userKeyService
    ) {
        this.cryptoService = cryptoService;
        this.store = store;
        this.userKeyService = userKeyService;
    }

    public MessageResult send(
            String senderId,
            String receiverId,
            String plaintext,
            int expiryMinutes
    ) throws Exception {

        byte[] receiverPublicKey = userKeyService.getPublicKey(receiverId);
        EncryptedPayload payload = cryptoService.encrypt(plaintext, receiverPublicKey);

        MessageRecord record = new MessageRecord(
                UUID.randomUUID().toString(),
                senderId,
                receiverId,
                payload,
                Instant.now().plus(expiryMinutes, ChronoUnit.MINUTES)
        );

        String token = store.save(record);
        return new MessageResult(record.messageId, token, record.expiryTime);
    }

    public String open(String token) throws Exception {
        MessageRecord record = store.findByToken(token);

        if (record == null) throw new IllegalStateException("Invalid token");

        record.accessAttempts++;

        if (record.used) {
            if (!record.senderAlerted) {
                alertSender(record);
                System.out.println("⚠ Replay detected for message " + record.messageId);
                record.senderAlerted = true;
            }
            throw new IllegalStateException("Message already read");
        }

        byte[] sk = userKeyService.getPrivateKey(record.receiverId);
        String plaintext = cryptoService.decrypt(record.payload, sk);

        record.used = true;
        record.readAt = Instant.now();

        return plaintext;
    }

    public List<InboxMessageResponse> inbox(String receiverId) {

        return store.findInbox(receiverId).stream()
                .map(record -> new InboxMessageResponse(
                        record.messageId,
                        record.token,
                        record.senderId,
                        record.expiryTime,
                        record.getStatus()   // 🔥 backend status
                ))
                .toList();
    }

    private void alertSender(MessageRecord record) {

        SecurityAlert alert = new SecurityAlert(
                record.messageId,
                record.senderId,
                record.receiverId,
                record.accessAttempts
        );

        store.addAlert(alert);

        System.out.println(
                "⚠️ SECURITY ALERT: Message " + record.messageId +
                        " replay detected. Attempts: " + record.accessAttempts
        );
    }



    public List<SecurityAlert> alerts(String senderId) {
        return store.findAlertsForSender(senderId);
    }


    public record MessageResult(String messageId, String token, Instant expiresAt) {}
    public record InboxMessage(String messageId, String token, String senderId, Instant expiresAt) {}
}
