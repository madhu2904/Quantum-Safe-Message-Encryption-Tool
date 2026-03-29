package com.quantum_safe.sercure_server.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface messageRepository extends JpaRepository<message, Long> {

    List<message> findByReceiverIdAndReadFalse(Long receiverId);
    List<message> findByReceiverId(Long receiverId);
}

