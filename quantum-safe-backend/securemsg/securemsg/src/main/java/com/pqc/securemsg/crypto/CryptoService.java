package com.pqc.securemsg.crypto;

import org.openquantumsafe.KeyEncapsulation;
import org.openquantumsafe.Pair;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

@Service
public class CryptoService {

    private static final String KEM_ALG = "ML-KEM-768";
    private static final int GCM_TAG_LENGTH = 128;

    public EncryptedPayload encrypt(String plaintext, byte[] receiverPublicKey) throws Exception {

        // 1. ML-KEM encapsulation
        KeyEncapsulation kem = new KeyEncapsulation(KEM_ALG);
        Pair<byte[], byte[]> encap = kem.encap_secret(receiverPublicKey);

        byte[] kemCiphertext = encap.getLeft();
        byte[] sharedSecret = encap.getRight();

        // 2. Derive AES key
        SecretKey aesKey = new SecretKeySpec(
                Arrays.copyOf(sharedSecret, 32),
                "AES"
        );

        // 3. AES-GCM encryption
        byte[] iv = new byte[12];
        new SecureRandom().nextBytes(iv);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(
                Cipher.ENCRYPT_MODE,
                aesKey,
                new GCMParameterSpec(GCM_TAG_LENGTH, iv)
        );

        byte[] ciphertext = cipher.doFinal(plaintext.getBytes());

        return new EncryptedPayload(
                Base64.getEncoder().encodeToString(ciphertext),
                Base64.getEncoder().encodeToString(iv),
                Base64.getEncoder().encodeToString(kemCiphertext)
        );
    }

    public String decrypt(EncryptedPayload payload, byte[] receiverSecretKey) throws Exception {

        // 1. ML-KEM decapsulation
        KeyEncapsulation kem = new KeyEncapsulation(KEM_ALG, receiverSecretKey);
        byte[] sharedSecret = kem.decap_secret(
                Base64.getDecoder().decode(payload.getKemCiphertext())
        );

        // 2. AES key
        SecretKey aesKey = new SecretKeySpec(
                Arrays.copyOf(sharedSecret, 32),
                "AES"
        );

        // 3. AES-GCM decryption
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(
                Cipher.DECRYPT_MODE,
                aesKey,
                new GCMParameterSpec(
                        GCM_TAG_LENGTH,
                        Base64.getDecoder().decode(payload.getIv())
                )
        );

        byte[] plaintext = cipher.doFinal(
                Base64.getDecoder().decode(payload.getCiphertext())
        );

        return new String(plaintext);
    }
}
