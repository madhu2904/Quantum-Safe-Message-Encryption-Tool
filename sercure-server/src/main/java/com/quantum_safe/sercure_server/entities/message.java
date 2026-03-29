package com.quantum_safe.sercure_server.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Setter @Getter
public class message
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="message_id")
    private Long messageId;
    @Column(name="receiver_id")
    private long receiverId;
    @Column(name="sender_id")
    private long senderId;
    @Column(name="is_read")
    private Boolean read=false;
    @Lob
    @Column(name = "ciphertext", columnDefinition = "LONGBLOB")
    private byte[] ciphertext;
    @Lob
    @Column(name = "encapsulation", columnDefinition = "LONGBLOB")
    private byte[] encapsulation;
    @Lob
    @Column(name = "iv", columnDefinition = "BLOB")
    private byte[] iv;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime timestamp;


}
