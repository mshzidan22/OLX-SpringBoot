package com.olx.util;


import com.google.gson.JsonObject;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Schedule {
    private String token;
    @Value("${clientId}")
    private String clientId;
    @Value("${clientSecret}")
    private String clientSecret;

    @Scheduled(fixedRate = 300000)  //5 min
    public String getToken() {
        HttpResponse<String> response = Unirest.post("https://api.sirv.com/v2/token")
                .header("content-type", "application/json")
                .body("{\"clientId\":\"" + clientId + "\"," +
                        "\"clientSecret\":\"" + clientSecret + "\"}")
                .asString();
        String body = response.getBody();
        JSONObject jsonObject = new JSONObject(body);
        System.out.println("Token has been updated");
        return jsonObject.getString("token");

    }
}