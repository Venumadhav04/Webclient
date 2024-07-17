package com.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${spring.security.oauth2.client.registration.my-client.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.my-client.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.my-client.token-uri}")
    private String tokenUri;

    @Bean
    public WebClient webClient(ClientRegistrationRepository clientRegistrationRepository) {
        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 =
                new ServletOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrationRepository, null);
        oauth2.setDefaultClientRegistrationId("my-client");

        return WebClient.builder()
                .apply(oauth2.oauth2Configuration())
                .build();
    }
}
