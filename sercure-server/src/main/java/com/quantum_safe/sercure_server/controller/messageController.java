package com.quantum_safe.sercure_server.controller;

import com.quantum_safe.sercure_server.DTOs.inboxPreview;
import com.quantum_safe.sercure_server.DTOs.readMessage;
import com.quantum_safe.sercure_server.DTOs.sendMessage;
import com.quantum_safe.sercure_server.entities.message;
import com.quantum_safe.sercure_server.service.messageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
public class messageController
{
    private final messageService messageService;
    public messageController(messageService messageService)
    {
        this.messageService = messageService;
    }
    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody sendMessage message)
    {
        return ResponseEntity.ok(messageService.sendMessage(message));
    }

    @GetMapping("/inbox/{receiverId}")
    public List<inboxPreview> inbox(@PathVariable Long receiverId)
    {
        return messageService.getInbox(receiverId);
    }

    @PostMapping("/read/{messageId}")
    public readMessage read( @PathVariable Long messageId, @RequestParam Long currentUserId)
    {
        return messageService.readMessage(messageId, currentUserId);
    }

}

