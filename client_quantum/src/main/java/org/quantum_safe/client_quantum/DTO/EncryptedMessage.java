package org.quantum_safe.client_quantum.DTO;


public class EncryptedMessage
{
    private final String encapsulation;
    private final String ciphertext;
    private final String iv;
    private final String senderMailId;
    private final String receiverMailId;

    public EncryptedMessage(String encapsulation, String ciphertext, String iv,String mailId,String senderMail) {
        this.encapsulation = encapsulation;
        this.ciphertext = ciphertext;
        this.iv = iv;
        this.receiverMailId=mailId;
        this.senderMailId=senderMail;
    }

    public String getEncapsulation() { return encapsulation; }
    public String getCiphertext() { return ciphertext; }
    public String getIv() { return iv; }
    public String getReceiverMailId() {return receiverMailId;}
    public String getSenderMailId() {return senderMailId;}
}
