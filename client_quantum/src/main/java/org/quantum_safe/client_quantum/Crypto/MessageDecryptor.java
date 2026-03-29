package org.quantum_safe.client_quantum.Crypto;

import org.quantum_safe.client_quantum.DTO.ReadMessageResponse;

import org.quantum_safe.client_quantum.Session;

import org.openquantumsafe.KeyEncapsulation;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;

public class MessageDecryptor {

    private static final String ALGORITHM = "Kyber768";
    private static final int AES_KEY_SIZE = 32;
    private static final int GCM_TAG_LENGTH = 128;

    public String decrypt(ReadMessageResponse response) throws Exception {

        KeyEncapsulation temp = new KeyEncapsulation("Kyber768");
        System.out.println(temp.get_keypair_seed_length()); // not secret length
        // 1️ Decode Base64
        byte[] encapsulation =
                Base64.getDecoder().decode(response.getEncapsulation());

        byte[] ciphertext =
                Base64.getDecoder().decode(response.getCiphertext());

        byte[] iv =
                Base64.getDecoder().decode(response.getIv());

        // 2️ Load private key
        byte[] privateKey = loadPrivateKey();

        // 3️ Create KEM instance
        KeyEncapsulation kem =
                new KeyEncapsulation(ALGORITHM,privateKey);

        // 4️ Import private key into KEM
        //kem.export_secret_key(privateKey);

        // 5️ Decapsulate (ONLY encapsulation param)
        byte[] sharedSecret =
                kem.decap_secret(encapsulation);

        // 6️ Derive AES key (same logic as encryptor)
        byte[] aesKeyBytes =
                Arrays.copyOf(sharedSecret, AES_KEY_SIZE);

        SecretKeySpec aesKey =
                new SecretKeySpec(aesKeyBytes, "AES");

        // 7️ AES GCM Decrypt
        Cipher cipher =
                Cipher.getInstance("AES/GCM/NoPadding");

        cipher.init(Cipher.DECRYPT_MODE,
                aesKey,
                new GCMParameterSpec(GCM_TAG_LENGTH, iv));

        byte[] decrypted =
                cipher.doFinal(ciphertext);
        kem.dispose_KEM();

        return new String(decrypted);
    }

    private byte[] loadPrivateKey() throws Exception {

        String mailId = Session.getUserEmail();

        byte[] fileBytes = Files.readAllBytes(
                Paths.get("keys/" + mailId + "_private.key")
        );

        //  Decode Base64
        return Base64.getDecoder().decode(fileBytes);
    }
}