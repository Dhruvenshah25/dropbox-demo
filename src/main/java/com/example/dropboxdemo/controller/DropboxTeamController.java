package com.example.dropboxdemo.controller;

import com.example.dropboxdemo.model.DropboxToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/dropbox")
public class DropboxTeamController {

    @Autowired
    private DropboxOAuthController authController;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/team-members")
    public ResponseEntity<String> getTeamMembers() {
        try {
            String url = "https://api.dropboxapi.com/2/team/members/list_v2";

            RestTemplate restTemplate = new RestTemplate();

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("limit", 10);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(authController.getToken().getAccessToken());
            //headers.set("Authorization", "Bearer "+authController.getToken().getAccessToken());

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response =
                    restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            System.out.println("Team members info:"+response.getBody());
            return ResponseEntity.ok(response.getBody());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }
}
