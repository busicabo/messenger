package ru.mescat.user.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import ru.mescat.user.dto.User;
import ru.mescat.message.exception.RemoteServiceException;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final RestClient restClient;

    public UserService(@Qualifier("user") RestClient restClient) {
        this.restClient = restClient;
    }

    public User save(User user) {
        try {
            return restClient.post()
                    .uri("/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(user)
                    .retrieve()
                    .body(User.class);
        } catch (RestClientResponseException e) {
            int status = e.getStatusCode().value();
            String message = e.getResponseBodyAsString();

            throw new RemoteServiceException(status, message);
        }
    }

    public User findById(UUID id) {
        try {
            return restClient.get()
                    .uri("/user/{id}", id)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(User.class);
        } catch (RestClientResponseException e) {
            int status = e.getStatusCode().value();
            String message = e.getResponseBodyAsString();

            throw new RemoteServiceException(status, message);
        }
    }

    public void delete(UUID id) {
        try {
            restClient.delete()
                    .uri("/user/{id}", id)
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientResponseException e) {
            int status = e.getStatusCode().value();
            String message = e.getResponseBodyAsString();

            throw new RemoteServiceException(status, message);
        }
    }

    public User searchByUsername(String username) {
        try {
            return restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/user/search")
                            .queryParam("username", username)
                            .build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(User.class);
        } catch (RestClientResponseException e) {
            int status = e.getStatusCode().value();
            String message = e.getResponseBodyAsString();

            throw new RemoteServiceException(status, message);
        }
    }

    public List<User> findAllByIds(List<UUID> userIds) {
        try {
            return restClient.post()
                    .uri("/user/getAllById")
                    .body(userIds)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<User>>() {});
        } catch (RestClientResponseException e) {
            int status = e.getStatusCode().value();
            String message = e.getResponseBodyAsString();

            throw new RemoteServiceException(status, message);
        }
    }

    public List<User> findByUsernameContaining(String username) {
        try {
            return restClient.get()
                    .uri("/user/search/contains/{username}", username)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<User>>() {});
        } catch (RestClientResponseException e) {
            int status = e.getStatusCode().value();
            String message = e.getResponseBodyAsString();

            throw new RemoteServiceException(status, message);
        }
    }

    public void updatePassword(UUID id, String password) {
        try {
            restClient.patch()
                    .uri(uriBuilder -> uriBuilder
                            .path("/user/{id}/password")
                            .queryParam("password", password)
                            .build(id))
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientResponseException e) {
            int status = e.getStatusCode().value();
            String message = e.getResponseBodyAsString();

            throw new RemoteServiceException(status, message);
        }
    }

    public void updateBlocked(UUID id, boolean blocked) {
        try {
            restClient.patch()
                    .uri(uriBuilder -> uriBuilder
                            .path("/user/{id}/blocked")
                            .queryParam("blocked", blocked)
                            .build(id))
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientResponseException e) {
            int status = e.getStatusCode().value();
            String message = e.getResponseBodyAsString();

            throw new RemoteServiceException(status, message);
        }
    }

    public void updateOnline(UUID id, boolean online) {
        try {
            restClient.patch()
                    .uri(uriBuilder -> uriBuilder
                            .path("/user/{id}/online")
                            .queryParam("online", online)
                            .build(id))
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientResponseException e) {
            int status = e.getStatusCode().value();
            String message = e.getResponseBodyAsString();

            throw new RemoteServiceException(status, message);
        }
    }

    public void updateUsername(UUID id, String username) {
        try {
            restClient.patch()
                    .uri(uriBuilder -> uriBuilder
                            .path("/user/{id}/username")
                            .queryParam("username", username)
                            .build(id))
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientResponseException e) {
            int status = e.getStatusCode().value();
            String message = e.getResponseBodyAsString();

            throw new RemoteServiceException(status, message);
        }
    }
}