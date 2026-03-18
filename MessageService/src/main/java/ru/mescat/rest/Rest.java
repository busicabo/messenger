package ru.mescat.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class Rest {

    @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

    @Bean("user")
    public RestClient userServiceRestClient(RestClient.Builder builder) {
        return builder
                .baseUrl("http://localhost:8081")
                .build();
    }

    @Bean("key_vault")
    public RestClient keyvaultRestClient(RestClient.Builder builder) {
        return builder
                .baseUrl("http://localhost:8085")
                .build();
    }
}
