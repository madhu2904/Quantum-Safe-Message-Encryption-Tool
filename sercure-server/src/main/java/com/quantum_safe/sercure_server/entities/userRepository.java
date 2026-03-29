package com.quantum_safe.sercure_server.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface userRepository extends JpaRepository<user,Long>
{
    Optional<user> findByMailId(String mailId);
    @Query("SELECT u.mailId FROM user u")
    Optional<List<String>> findAllMailId();
}
