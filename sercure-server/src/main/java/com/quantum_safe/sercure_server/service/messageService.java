package com.quantum_safe.sercure_server.service;

import com.quantum_safe.sercure_server.DTOs.inboxPreview;
import com.quantum_safe.sercure_server.DTOs.readMessage;
import com.quantum_safe.sercure_server.DTOs.sendMessage;
import com.quantum_safe.sercure_server.entities.message;
import com.quantum_safe.sercure_server.entities.messageRepository;
import com.quantum_safe.sercure_server.entities.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
public class messageService
{

    private final messageRepository messageRepository;
    private final mailService emailService;
    @Autowired
    private  userRepository userRepository;


    public messageService(messageRepository mesgRepo, mailService emailService)
    {
        this.messageRepository=mesgRepo;
        this.emailService = emailService;
    }
    public String sendMessage(sendMessage m)
    {
        Long senderId=userRepository.findByMailId(m.getSenderMailId()).get().getId();
        Long receiverId=userRepository.findByMailId(m.getReceiverMailId()).get().getId();
        System.out.println("SenderMail: " + m.getSenderMailId());
        System.out.println("ReceiverMail: " + m.getReceiverMailId());
        message fullMessage=new message();
        fullMessage.setSenderId(senderId);
        fullMessage.setReceiverId(receiverId);
        fullMessage.setIv(Base64.getDecoder().decode(m.getIv()));
        fullMessage.setCiphertext(Base64.getDecoder().decode(m.getCiphertext()));
        fullMessage.setEncapsulation(Base64.getDecoder().decode(m.getEncapsulation()));

        messageRepository.save(fullMessage);
        return "Message Sent Successfully";
    }
    public List<inboxPreview> getInbox(Long receiverId) {
        return messageRepository.findByReceiverId(receiverId).stream()
                .map(msg -> new inboxPreview(
                        msg.getMessageId(),
//                        msg.getSenderId()
                        (userRepository.findById(msg.getSenderId())).get().getMailId(),
                        msg.getRead(),
                        msg.getTimestamp()
                ))
                .toList();
    }
    public readMessage readMessage(Long messageId, Long currentUserId) {

        message msg = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));


        if (!(((Long)msg.getReceiverId()).equals(currentUserId))) {
            throw new RuntimeException("Unauthorized access");
        }


        if (msg.getRead()) {
            // later → send mail alert here
            //throw new RuntimeException("Message already read");
            String receiverEmail = userRepository
                    .findById(msg.getReceiverId())
                    .get()
                    .getMailId();

            emailService.sendSecurityAlert(receiverEmail, messageId);

            throw new RuntimeException("Security alert sent. Message already read.");

        }


        readMessage dto = new readMessage(
                msg.getCiphertext(),
                msg.getEncapsulation(),
                msg.getIv()
        );

        msg.setRead(true);
        msg.setCiphertext(null);
        msg.setEncapsulation(null);

        messageRepository.save(msg);

        return dto;
    }


}
