package org.quantum_safe.client_quantum.Crypto;


public class QuantumKeyPair {

    private final String publicKey;
    private final String privateKey;

    public QuantumKeyPair(String publicKey, String privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }
}