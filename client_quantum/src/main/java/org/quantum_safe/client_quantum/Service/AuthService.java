package org.quantum_safe.client_quantum.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class AuthService {

    private static final String LOGIN_URL = "http://localhost:8080/auth/login";

    public static String login(String email, String password) throws Exception {

        HttpClient client = HttpClient.newHttpClient();

        String requestBody = String.format(
                "{\"mailId\":\"%s\",\"password\":\"%s\"}",
                email, password
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(LOGIN_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        String responseBody = response.body();
        System.out.println("Server Response: " + responseBody);

        // ---- SIMPLE PARSING (temporary) ----
        if (responseBody.contains("userId")) {

            String userIdPart = responseBody.split("\"userId\":")[1];

            // userIdPart will look like: 13,"name":"hello"}
            String userId = userIdPart.split(",")[0];

            return userId.trim();
        }

        return null;
    }
    public static String register(String name,
                                  String email,
                                  String password,
                                  String publicKey) throws Exception {

        String requestBody = String.format(
                "{\"name\":\"%s\",\"mailId\":\"%s\",\"password\":\"%s\",\"publicKey\":\"%s\"}",
                name, email, password, publicKey
        );

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/auth/register"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
}