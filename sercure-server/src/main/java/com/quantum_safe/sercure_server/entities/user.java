package com.quantum_safe.sercure_server.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Entity
@Setter
@Getter
public class user
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    @Column(name="mail_id")
    private String mailId;

    private String password;
    @Lob
    private byte[] publicKey;

}
