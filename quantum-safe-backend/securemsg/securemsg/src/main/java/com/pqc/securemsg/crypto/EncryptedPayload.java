package com.pqc.securemsg.crypto;

public class EncryptedPayload {

    private String ciphertext;
    private String iv;
    private String kemCiphertext;

    public EncryptedPayload(String ciphertext, String iv, String kemCiphertext) {
        this.ciphertext = ciphertext;
        this.iv = iv;
        this.kemCiphertext = kemCiphertext;
    }

    public String getCiphertext() {
        return ciphertext;
    }

    public String getIv() {
        return iv;
    }

    public String getKemCiphertext() {
        return kemCiphertext;
    }
}
