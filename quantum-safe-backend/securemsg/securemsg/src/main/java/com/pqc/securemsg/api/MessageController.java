package com.pqc.securemsg.api;

import com.pqc.securemsg.api.dto.*;
import com.pqc.securemsg.service.MessageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService service;

    public MessageController(MessageService service) {
        this.service = service;
    }

    @PostMapping("/send")
    public ResponseEntity<?> send(@RequestBody SendMessageRequest req) throws Exception {
        var res = service.send(
                req.getSenderId(),
                req.getReceiverId(),
                req.getMessage(),
                req.getExpiryMinutes()
        );
        return ResponseEntity.ok(
                new SendMessageResponse(res.messageId(), res.token(), res.expiresAt())
        );
    }

    @GetMapping("/open/{token}")
    public ResponseEntity<?> open(@PathVariable String token) {
        try {
            return ResponseEntity.ok(
                    new OpenMessageResponse(service.open(token), Instant.now())
            );
        } catch (Exception e) {
            return ResponseEntity.status(410).body(e.getMessage());
        }
    }

    @GetMapping("/inbox/{receiverId}")
    public ResponseEntity<?> inbox(@PathVariable String receiverId) {
        return ResponseEntity.ok(service.inbox(receiverId));
    }

    @GetMapping("/alerts/{senderId}")
    public ResponseEntity<?> alerts(@PathVariable String senderId) {
        return ResponseEntity.ok(
                service.alerts(senderId)
        );
    }

}
