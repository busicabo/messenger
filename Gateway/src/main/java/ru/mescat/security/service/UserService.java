package ru.mescat.security.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import ru.mescat.security.User;
import ru.mescat.security.dto.RegDto;
import ru.mescat.security.exception.RemoteServiceException;

import java.util.UUID;

@Service
public class UserService {

    private RestClient restClient;

    public UserService(@Qualifier("user") RestClient restClient){
        this.restClient=restClient;
    }

    public User registration(RegDto regDto){
        try{
            return restClient.post()
                    .uri("/auth/reg")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(regDto)
                    .retrieve()
                    .body(User.class);
        } catch (RestClientResponseException e) {
            int status = e.getStatusCode().value();
            String message = e.getResponseBodyAsString();

            throw new RemoteServiceException(
                    status,message
            );
        }
    }

    public User info(String username){
        try{
            return restClient.get()
                    .uri("/auth/info/{username}",username)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(User.class);
        } catch (RestClientResponseException e) {
            int status = e.getStatusCode().value();
            String message = e.getResponseBodyAsString();

            throw new RemoteServiceException(
                    status,message
            );
        }
    }

    public User infoById(UUID id){
        try{
            return restClient.get()
                    .uri("/auth/info/id/{id}", id)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(User.class);
        } catch (RestClientResponseException e) {
            int status = e.getStatusCode().value();
            String message = e.getResponseBodyAsString();

            throw new RemoteServiceException(
                    status,message
            );
        }
    }
}
