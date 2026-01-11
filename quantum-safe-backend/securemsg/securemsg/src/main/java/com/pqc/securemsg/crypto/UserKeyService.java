package com.pqc.securemsg.crypto;

import org.openquantumsafe.KeyEncapsulation;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserKeyService {

    private static class UserKeys {
        byte[] publicKey;
        byte[] privateKey;
    }

    private final ConcurrentHashMap<String, UserKeys> keyStore = new ConcurrentHashMap<>();

    /**
     * Ensure a user has a persistent ML-KEM keypair.
     */
    public void ensureUserKeys(String userId) {
        keyStore.computeIfAbsent(userId, id -> {
            try {
                KeyEncapsulation kem = new KeyEncapsulation("ML-KEM-768");
                kem.generate_keypair();

                UserKeys keys = new UserKeys();
                keys.publicKey = kem.export_public_key();
                keys.privateKey = kem.export_secret_key();

                return keys;
            } catch (Exception e) {
                throw new RuntimeException("Failed to generate ML-KEM keys for user " + userId, e);
            }
        });
    }

    public byte[] getPublicKey(String userId) {
        ensureUserKeys(userId);
        return keyStore.get(userId).publicKey;
    }

    public byte[] getPrivateKey(String userId) {
        ensureUserKeys(userId);
        return keyStore.get(userId).privateKey;
    }
}
