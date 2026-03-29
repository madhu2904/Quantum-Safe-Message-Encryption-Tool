package org.quantum_safe.client_quantum.Service;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;


public class GetUserService
{
    private static final String AllMails="http://localhost:8080/users/getAll";
    private static String publicKeyReq="http://localhost:8080/users/public-key?mailId=";
    public static List<String> getAllMailIds() throws Exception {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(AllMails))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();

        return Arrays.asList(
                mapper.readValue(response.body(), String[].class)
        );
    }
    public static String getPublicKey(String mailId) throws Exception
    {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(publicKeyReq+mailId))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to fetch public key");
        }
        return response.body();
    }

}
