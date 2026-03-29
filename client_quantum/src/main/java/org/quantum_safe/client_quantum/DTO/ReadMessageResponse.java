package org.quantum_safe.client_quantum.DTO;

public class ReadMessageResponse
{
    private String encapsulation;
    private String ciphertext;
    private String iv;

    public String getEncapsulation() {
        return encapsulation;
    }
    public void setEncapsulation(String encapsulation) {
        this.encapsulation = encapsulation;
    }
    public String getCiphertext() {
        return ciphertext;
    }
    public void setCiphertext(String ciphertext) {
        this.ciphertext = ciphertext;
    }
    public String getIv() {
        return iv;
    }
    public void setIv(String iv) {
        this.iv = iv;
    }
}
