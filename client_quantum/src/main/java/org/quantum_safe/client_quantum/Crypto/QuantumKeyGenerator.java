package org.quantum_safe.client_quantum.Crypto;

import org.openquantumsafe.KeyEncapsulation;
import org.quantum_safe.client_quantum.Crypto.QuantumKeyPair;

import java.util.Base64;

public class QuantumKeyGenerator {

    private static final String ALGORITHM = "Kyber768";

    public static QuantumKeyPair generateKeyPair() {

        KeyEncapsulation kem = new KeyEncapsulation(ALGORITHM);

        byte[] publicKey = kem.generate_keypair();
        byte[] privateKey = kem.export_secret_key();

        String publicKeyBase64 =
                Base64.getEncoder().encodeToString(publicKey);

        String privateKeyBase64 =
                Base64.getEncoder().encodeToString(privateKey);

        return new QuantumKeyPair(publicKeyBase64, privateKeyBase64);
    }
}