package com.pqc.securemsg.store;

import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import com.pqc.securemsg.security.SecurityAlert;


@Component
public class InMemoryMessageStore {

    private final Map<String, MessageRecord> messages = new ConcurrentHashMap<>();

    private final List<SecurityAlert> alerts =
            Collections.synchronizedList(new ArrayList<>());



    public String save(MessageRecord record) {
        String token = UUID.randomUUID().toString();
        record.token = token;
        messages.put(token, record);
        return token;
    }

    public MessageRecord findByToken(String token) {
        return messages.get(token);
    }

    public List<MessageRecord> findInbox(String receiverId) {
        return messages.values().stream()
                .filter(m -> m.receiverId.equals(receiverId))
                .toList();
    }


    public void addAlert(SecurityAlert alert) {
        alerts.add(alert);
    }

    public List<SecurityAlert> findAlertsForSender(String senderId) {
        return alerts.stream()
                .filter(a -> a.senderId.equals(senderId))
                .toList();
    }


}
