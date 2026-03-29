package org.quantum_safe.client_quantum.Crypto;

import org.openquantumsafe.KeyEncapsulation;

import java.util.Base64;

public class TestOQS {

    public static void main(String[] args) {

        KeyEncapsulation kem = new KeyEncapsulation("Kyber768");

        byte[] publicKey = kem.generate_keypair();
        byte[] privateKey = kem.export_secret_key();

        System.out.println("===== KEY PAIR GENERATED =====");
        System.out.println("Public Key (Base64):");
        System.out.println(Base64.getEncoder().encodeToString(publicKey));

        System.out.println("\nPrivate Key (Base64):");
        System.out.println(Base64.getEncoder().encodeToString(privateKey));
    }
}
