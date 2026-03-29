package org.quantum_safe.client_quantum.Crypto;

import org.openquantumsafe.KeyEncapsulation;
import org.openquantumsafe.Pair;
import org.quantum_safe.client_quantum.DTO.EncryptedMessage;
import org.quantum_safe.client_quantum.Session;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class MessageEncryptor {

    private static final String ALGORITHM = "Kyber768";
    private static final int AES_KEY_SIZE = 32;
    private static final int GCM_IV_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 128;

    public static EncryptedMessage encrypt(String receiverPublicKeyBase64, String message, String mailId) throws Exception {

        byte[] receiverPublicKey = Base64.getDecoder().decode(receiverPublicKeyBase64);
        KeyEncapsulation kem = new KeyEncapsulation(ALGORITHM);
        Pair<byte[], byte[]> enc = kem.encap_secret(receiverPublicKey);

        byte[] encapsulation = enc.getLeft();
        byte[] sharedSecret = enc.getRight();

        byte[] aesKeyBytes = Arrays.copyOf(sharedSecret, AES_KEY_SIZE);
        SecretKeySpec aesKey = new SecretKeySpec(aesKeyBytes, "AES");

        byte[] iv = new byte[GCM_IV_LENGTH];
        new SecureRandom().nextBytes(iv);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, aesKey, new GCMParameterSpec(GCM_TAG_LENGTH, iv));

        byte[] ciphertext = cipher.doFinal(message.getBytes());

        return new EncryptedMessage(
                Base64.getEncoder().encodeToString(encapsulation),
                Base64.getEncoder().encodeToString(ciphertext),
                Base64.getEncoder().encodeToString(iv),mailId, Session.getUserEmail()
        );
    }
}