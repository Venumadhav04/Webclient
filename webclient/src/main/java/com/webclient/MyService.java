package com.webclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class MyService {

    private final WebClient webClient;
    private final OAuth2AuthorizedClientManager authorizedClientManager;
    private final ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    public MyService(WebClient webClient,
                     OAuth2AuthorizedClientManager authorizedClientManager,
                     ClientRegistrationRepository clientRegistrationRepository) {
        this.webClient = webClient;
        this.authorizedClientManager = authorizedClientManager;
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    public void getBearerToken() {
        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId("my-client");

        OAuth2AuthorizedClient authorizedClient = authorizedClientManager.authorize(
                OAuth2AuthorizeRequest.withClientRegistrationId(clientRegistration.getRegistrationId()).principal("principal").build());

        if (authorizedClient != null) {
            OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
            System.out.println("Bearer Token: " + accessToken.getTokenValue());
        } else {
            System.out.println("Failed to get the Bearer Token.");
        }
    }

    public void getProtectedResource() {
        String response = this.webClient
                .get()
                .uri("https://api.protected-resource.com/data")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println("Response: " + response);
    }
}
