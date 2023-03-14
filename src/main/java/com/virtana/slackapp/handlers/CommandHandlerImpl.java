package com.virtana.slackapp.handlers;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class CommandHandlerImpl implements CommandHandler{
    private static final String URL = "https://dev.cloud.virtana.com/cloudcapi/applications?timerange=1";
    private static final String xIdToken = "x-id-token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjdXN0b206YWN0aXZlT3JnYW5pemF0aW9uSWQiOiJjMWIxYmVlMi00MTcxLTRmOWUtYjc3OC0wMDkzY2E2ZGY2ZDIiLCJjdXN0b206YWN0aXZlT3JnYW5pemF0aW9uTmFtZSI6IlZpcnRhbmEiLCJjdXN0b206YXNzdW1lcklkIjoiIiwiY3VzdG9tOmVtYWlsIjoiaGVsZW4uam9obkB2aXJ0YW5hLmNvbSIsImN1c3RvbTpnbG9iYWxSb2xlcyI6WyJHbG9iYWwtQWRtaW5zIiwiR2xvYmFsLVJlYWRPbmx5Il0sImN1c3RvbTpuYW1lIjoiSGVsZW4gSm9obiIsImN1c3RvbTpvcmdhbml6YXRpb25zIjpbeyJpZCI6ImMxYjFiZWUyLTQxNzEtNGY5ZS1iNzc4LTAwOTNjYTZkZjZkMiIsInJvbGVzIjpbeyJpZCI6NDkzM31dfV0sImN1c3RvbTpwYXJlbnRPcmdJZCI6IjAwMDAwMDAwLTAwMDAtMDAwMC0wMDAwLTAwMDAwMDAwMDAwMCIsImV4cCI6MTY4MTMzNzczMCwiaWF0IjoxNjc4NzQ1NzMwLCJpc3MiOiJhdXRoLXNlcnZpY2UiLCJuYmYiOjE2Nzg3NDU3MzAsInN1YiI6IjgxMTJkMGYzLTk0ODItNGVhZC1hOTYxLTU5OWVhYmRkNjJjYyJ9.drRXkQjHnLKH0AWDieho_GuW5ZZfPl9IeSpdQWIdeRA";
    @Override
    public String ipmDashboard() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .header("CONTENT_TYPE", "application/json")
                .header("COOKIE", xIdToken)
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        //String respData =  new GsonBuilder().create().fromJson(response.body(), String.class);

        return response.body().toString();
    }
}

