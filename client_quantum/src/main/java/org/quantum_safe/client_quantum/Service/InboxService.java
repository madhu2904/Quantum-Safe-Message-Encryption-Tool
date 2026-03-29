package org.quantum_safe.client_quantum.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.quantum_safe.client_quantum.DTO.MessagePreview;
import org.quantum_safe.client_quantum.DTO.ReadMessageResponse;
import org.quantum_safe.client_quantum.Session;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class InboxService {

    private static final String BASE_URL =
            "http://localhost:8080/message";

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper =
            new ObjectMapper()
                    .registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());


    public List<MessagePreview> fetchInbox()
            throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/inbox/" + Session.getUserId()))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request,
                        HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {

            MessagePreview[] previews =
                    mapper.readValue(response.body(),
                            MessagePreview[].class);

            return Arrays.asList(previews);

        } else {
            throw new RuntimeException(response.body());
        }
    }



    public ReadMessageResponse readMessage(Long messageId)
            throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/read/"
                        + messageId
                        + "?currentUserId=" + Session.getUserId()))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response =
                client.send(request,
                        HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {

            return mapper.readValue(response.body(),
                    ReadMessageResponse.class);

        } else {
            throw new RuntimeException(response.body());
        }
    }
}