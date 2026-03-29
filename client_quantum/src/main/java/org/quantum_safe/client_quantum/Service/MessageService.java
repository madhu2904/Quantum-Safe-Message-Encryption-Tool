package org.quantum_safe.client_quantum.Service;

import org.quantum_safe.client_quantum.DTO.EncryptedMessage;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MessageService
{
    private static final String MESSAGE_SEND_URL = "http://localhost:8080/message/send";
    public static Integer  sendMessage(EncryptedMessage encrMsg) throws Exception
    {
        HttpClient client = HttpClient.newHttpClient();

        String requestBody=String.format(
                "{\"encapsulation\":\"%s\" , \"ciphertext\":\"%s\" , \"iv\":\"%s\" ,\"senderMailId\":\"%s\" ,\"receiverMailId\":\"%s\"}"
       ,encrMsg.getEncapsulation(),encrMsg.getCiphertext(),encrMsg.getIv(),encrMsg.getSenderMailId(),encrMsg.getReceiverMailId() );

        HttpRequest request= HttpRequest.newBuilder()
                .uri(URI.create(MESSAGE_SEND_URL)).header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();

        HttpResponse<String> response=client.send(request,HttpResponse.BodyHandlers.ofString());
        return response.statusCode();


    }

}
