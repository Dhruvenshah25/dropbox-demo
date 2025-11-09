package com.example.dropboxdemo.controller;

import com.example.dropboxdemo.model.DropboxToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/oauth")
public class DropboxOAuthController {

    @Value("${dropbox.client-id}")
    private String clientId;

    @Value("${dropbox.client-secret}")
    private String clientSecret;

    @Value("${dropbox.redirect-uri}")
    private String redirectUri;

    private final RestTemplate restTemplate = new RestTemplate();
    private DropboxToken tokenStore;

    @GetMapping("/login")
    public void login(HttpServletResponse response) throws IOException {
        String authUrl = "https://www.dropbox.com/oauth2/authorize"
                + "?client_id=" + clientId
                + "&response_type=code"
                + "&token_access_type=offline"
                + "&redirect_uri=" + redirectUri;
        response.sendRedirect(authUrl);
    }

    @GetMapping("/callback")
    public ResponseEntity<?> callback(@RequestParam("code") String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("code", code);
        form.add("grant_type", "authorization_code");
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);
        form.add("redirect_uri", redirectUri);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(
                "https://api.dropboxapi.com/oauth2/token", request, Map.class);

        String accessToken = (String) response.getBody().get("access_token");
        String refreshToken = (String) response.getBody().get("refresh_token");
        Integer expiresIn = (Integer) response.getBody().get("expires_in");

        tokenStore = new DropboxToken();
        tokenStore.setAccessToken(accessToken);
        tokenStore.setRefreshToken(refreshToken);
        tokenStore.setExpiresIn(expiresIn);

        return ResponseEntity.ok(Map.of(
                "message", "Authentication successful. Now call /dropbox/team-info",
                "access_token", accessToken
        ));
    }

    public DropboxToken getToken() { return tokenStore; }
}
